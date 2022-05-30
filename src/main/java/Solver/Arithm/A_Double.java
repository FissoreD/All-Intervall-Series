package Solver.Arithm;

/**
 * Creates three variables
 * - s = the solution
 * - v = s_i - v_(s-1) forall i in [1..|s|-1]
 * - dist = abs(v_i) forall v_i in v
 */
public class A_Double extends A_FirstSecond {
    public A_Double(int n, boolean applyStrat) {
        super(n, applyStrat);
        model.setName("A-Double");

        model.arithm(dist[0], "<", dist[dist.length - 1]).post();
    }


    @Override
    public int getId() {
        return 1;
    }
}
