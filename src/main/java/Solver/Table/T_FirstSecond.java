package Solver.Table;

import org.chocosolver.solver.constraints.extension.Tuples;

/**
 * Same as ++ but the constraint of strict inequality is
 * made between the first and the second variable, while making
 * a depth first search.
 * This solution similarly to FirstLast, break a symmetry, since
 * having a solution S where s[0] < s[1].
 */
public class T_FirstSecond extends T_Classic {

    public T_FirstSecond(int n, boolean applyStrat) {
        super(n, applyStrat);
        model.setName("T-FirstSecond");

        // Tuple saying that s[0] < s[n-1]
        Tuples firstSecondVars = new Tuples(true);
        // -1 is the universal value of the array : [1,2,-1,-1] means that the fourth and
        // third element of the array can take all value of the domain
        firstSecondVars.setUniversalValue(-1);

        // forall 0 <= i < j < n : arr[0] = i and arr[1] = j
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                var arr = createArrayOfN(n);
                arr[0] = i;
                arr[1] = j;
                firstSecondVars.add(arr);
            }
        }

        model.table(s, firstSecondVars, "CT+").post();
    }

    @Override
    public int getId() {
        return 4;
    }
}
