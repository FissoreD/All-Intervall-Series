package Solver.Arithm;

import Solver.MySolverAbstract;
import org.chocosolver.solver.variables.IntVar;

/**
 * Creates three variables
 * - s = the solution
 * - v = s_i - v_(s-1) forall i in [1..|s|-1]
 * - dist = abs(v_i) forall v_i in v
 */
public class A_Classic extends MySolverAbstract {
    public A_Classic(int n, boolean applyStrat) {
        super(n, "A-Classic", applyStrat);
        IntVar[] diff = new IntVar[n - 1];

        for (int i = 0; i < n - 1; i++) {
            diff[i] = model.intVar("V_" + i, -n, n);
        }

        for (int i = 0; i < diff.length; i++) {
            model.arithm(s[i + 1], "-",
                    s[i], "=", diff[i]).post();
            model.absolute(dist[i], diff[i]).post();
        }
    }


    @Override
    public int getId() {
        return 1;
    }
}
