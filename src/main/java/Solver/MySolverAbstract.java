package Solver;

import Solver.Arithm.*;
import Solver.Table.*;
import Strategy.MySearchStrategy;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public abstract class MySolverAbstract {
    public static String all = "12345ABCDE";
    public final Model model;
    public final IntVar[] s;
    public final IntVar[] dist;
    public final int n;

    public MySolverAbstract(int n, String name, boolean applyStrat) {
        this.s = new IntVar[n];
        this.dist = new IntVar[n - 1];
        this.n = n;
        this.model = new Model(name);

        for (int i = 0; i < n; i++) {
            s[i] = model.intVar("Q_" + i, 0, n - 1);
        }

        for (int i = 0; i < n - 1; i++) {
            dist[i] = model.intVar("A_" + i, 1, n - 1);
        }

        model.allDifferent(s, "CT+").post();
        model.allDifferent(dist, "CT+").post();
        if (applyStrat) {
            MySearchStrategy.applyFirstFail(model, s, dist);
        }

    }

    public MySolverAbstract(MySolverAbstract s) {
        this.model = s.model;
        this.s = s.s;
        this.dist = s.dist;
        this.n = s.n;
    }

    public static String header() {
        return padLeft("AlgoName", 20) + ", " + padLeft("n", 4) + ", " +
                padLeft("SolCount", 8) + ", VarNb, CstNb, BackTrack,     Fails, ResTime";
    }

    public static MySolverAbstract getSolverFromId(char solverId, int n, boolean applyStrat) {
        return switch (solverId) {
            case '1' -> new A_Classic(n, applyStrat);
            case '2' -> new A_FirstLast(n, applyStrat);
            case '3' -> new A_FirstSecond(n, applyStrat);
            case '4' -> new A_Sh(n, applyStrat);
            case '5' -> new A_Double(n, applyStrat);
            case 'A' -> new T_Classic(n, applyStrat);
            case 'B' -> new T_FirstLast(n, applyStrat);
            case 'C' -> new T_FirstSecond(n, applyStrat);
            case 'D' -> new T_Sh(n, applyStrat);
            case 'E' -> new T_Double(n, applyStrat);
            default -> throw new RuntimeException("Solver " + solverId + " doesn't exist. Try with " + all);
        };
    }

    public static List<MySolverAbstract> getSolversFromId(String solverId, int n, boolean strat) {
        List<MySolverAbstract> arr = new ArrayList<>();
        for (int i = 0; i < solverId.length(); i++) {
            arr.add(getSolverFromId(solverId.charAt(i), n, strat));
        }
        return arr;
    }

    public static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }

    public static int[] createArrayOfN(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = -1;
        }
        return arr;
    }

    static public <T> T[] concatenate(T[] a, T[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }

    public String printStatistics(boolean printSolutions) {
        Model model = getModel();
        var solver = model.getSolver();
        var s = solver.findAllSolutions();
        String res = String.format("%s, %s, %s, %s, %s, %s, %s, %s",
                padLeft(model.getName(), 20),
                padLeft(n + "", 4),
                padLeft(solver.getSolutionCount() + "", 8),
                padLeft(model.getNbVars() + "", 5),
                padLeft(model.getNbCstrs() + "", 5),
                padLeft(solver.getBackTrackCount() + "", 9),
                padLeft(solver.getFailCount() + "", 9),
                solver.getTimeCount() + "");
        if (printSolutions) {
//            solver.printStatistics();
            StringBuilder sb = new StringBuilder();
            s.forEach(c -> sb.append(c).append("\n"));
            res = res + "\n" + sb;
        }
        return res;
    }

    public String printStatistics() {
        return printStatistics(false);
    }

    public Model getModel() {
        return model;
    }

    public Solver getSolver() {
        return getModel().getSolver();
    }

    public abstract int getId();
}
