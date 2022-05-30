import Solver.MySolverAbstract;

public class Main {
    /**
     * https://www.csplib.org/Problems/prob007/
     */

    public static void main(String[] args) {
        String solverDefault = MySolverAbstract.all;
        int minN = 3;
        int maxN = 13;
        int step = 1;
        Integer n = null;
        boolean printSolution = false;
        boolean applyStrategy = true;
        for (String current : args) {
            if (current.startsWith("-n")) {
                try {
                    checkNonEmptyParameter(current);
                    n = Integer.parseInt(current.substring(2));
                    if (n <= 2)
                        throw new RuntimeException("After -n you should write an Integer bigger then 2");
                } catch (NumberFormatException err) {
                    throw new RuntimeException("After -n you should enter a positive integer");
                }
            } else if (current.startsWith("-a")) {
                checkNonEmptyParameter(current);
                solverDefault = current.substring(2);
            } else if (current.startsWith("-M")) {
                checkNonEmptyParameter(current);
                try {
                    maxN = Integer.parseInt(current.substring(2));
                } catch (Exception err) {
                    throw new RuntimeException("After -M you should write an Integer");
                }
            } else if (current.startsWith("-m")) {
                checkNonEmptyParameter(current);
                try {
                    minN = Integer.parseInt(current.substring(2));
                    if (minN <= 2) {
                        throw new RuntimeException("After -m you should write an Integer bigger then 2");
                    }
                } catch (Exception err) {
                    throw new RuntimeException("After -m you should write an Integer");
                }
            } else if (current.startsWith("-p")) {
                printSolution = true;
            } else if (current.startsWith("-h")) {
                System.out.println("This java executable allows you to compare some algorithm that I have implemented to solve the All-Series Interval problem.\n" +
                        "There are 4 main algorithms which are :\n" +
                        "1 : A-Classic -> Finds All solutions \n" +
                        "2 : A-FirstLast -> s[0] < s[s.length - 1] \n" +
                        "3 : A-FirstSecond -> s[0] < s[1] \n" +
                        "4 : A-Sh -> dist[0] < dist[dist.length - 1] \n" +
                        "5 : A-Double -> A-Sh + A-FirstSecond \n" +
                        "A : T-Classic -> Break of the symmetry : v[0] < v[1] \n" +
                        "B : T-FirstLast -> Break of the symmetry : v[0] < v[1] \n" +
                        "C : T-FirstSecond -> Break of the symmetry : v[0] < v[1] \n" +
                        "D : T-Sh -> Break of the symmetry : v[0] < v[1] \n" +
                        "E : T-Double -> Break of the symmetry : v[0] < v[1] \n" +
                        "Note : A-... Solvers use only arithmetic constraints\n" +
                        "       T-... Solvers use only table as constraints\n" +
                        "By default the jar works in this way : Print in csv format the statistic of the run of all algorithms with n going from 3 to 12 and the search strategy applied.\n" +
                        "If you want you can parametrize the execution via some command flags :\n" +
                        "-Mxxx : set the max bound of n to xxx where xxx is a positive integer (example -M8)\n" +
                        "-mxxx : set the min bound of n to xxx where xxx is a positive integer (example -m4)\n" +
                        "-nxxx : set the value n and run the algorithms where n is fixed to xxx (example -n6), if n is set, -M and -m are neglected\n" +
                        "-aX : allows you to run your chosen algorithms, X is the string containing the id of the solvers you want to run, for example -a34A will execute solver 3 then solver 4 and finally solver A\n" +
                        "--no-strat : remove the strategy from all solvers\n" +
                        "-p : print all found solutions in the console\n" +
                        "-h : display the help message");
                return;
            } else if (current.equals("--no-strat")) {
                applyStrategy = false;
            }
        }

        if (args.length == 0) {
            System.out.println("You can enter -h in the command line to see the help message for more options");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("+-------------------------------------------------------------------------+\n");
        sb.append("| The algorithms ").append(solverDefault).append(" are going to be run ");
        if (n != null) {
            minN = n;
            maxN = n;
            sb.append("with n = ").append(n);
        } else {
            sb.append("with n in [").append(minN).append("..").append(maxN).append("]");
        }
        sb.append(applyStrategy ? " w/firstFail strategy" : " w/o firstFail strategy");
        sb.append("\n| Add -h flag in line command to get the help menu");
        sb.append("\n+-------------------------------------------------------------------------+");
        System.out.println(sb);
        System.out.println(MySolverAbstract.header());
        for (int i = minN; i <= maxN; i += step) {
            boolean finalPrintSolution1 = printSolution;
            MySolverAbstract.getSolversFromId(solverDefault, i, applyStrategy).forEach(e ->
                    System.out.println(e.printStatistics(finalPrintSolution1)));
        }
    }

    public static void checkNonEmptyParameter(String s) {
        if (s.substring(2).length() == 0)
            throw new RuntimeException("After " + s.substring(0, 2) + " you should enter an integer");
    }
}
