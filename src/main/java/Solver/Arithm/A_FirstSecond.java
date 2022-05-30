package Solver.Arithm;

public class A_FirstSecond extends A_Classic {
    public A_FirstSecond(int n, boolean applyStrat) {
        super(n, applyStrat);
        model.setName("A-FirstSecond");
        model.arithm(s[0], "<", s[1]).post();
    }
}
