package Solver.Arithm;

public class A_Sh extends A_Classic {
    public A_Sh(int n, boolean applyStrat) {
        super(n, applyStrat);
        model.setName("A-Sh");
        model.arithm(dist[0], "<", dist[dist.length - 1]).post();
    }
}
