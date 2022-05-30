package Solver.Arithm;

public class A_FirstLast extends A_Classic {
    public A_FirstLast(int n, boolean applyStrat) {
        super(n, applyStrat);
        model.setName("A-FirstLast");

        model.arithm(s[0], "<", s[s.length - 1]).post();
    }
}
