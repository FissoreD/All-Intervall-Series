package Solver.Table;

import org.chocosolver.solver.constraints.extension.Tuples;

/**
 * Creates two variables
 * - s : the vector containing a solution
 * - dist = vector of distances
 * - tuple = [s_i, s_(i+1), |s_i - s_(i+1)|]
 * ----------------------------------------------
 * Same as Solver with table which is faster than
 * the classic solver.
 * The problem has plenty of symmetries, and I'm going
 * to break one of them.
 * In fact, every solution s_i has a solution s_i^(-1) [the reversal of the array]
 * which  is also a solution. It should be good to keep in memory all
 * past solutions and reuse them not to find a symmetric solution.
 * ------------------------------------------------
 * In this case I will force v[0] to be strictly smaller than v[n-1].
 * Since we know that for every solution S having S[0] = x and S[n-1] = y there is a
 * solution S' having S'[0] = y and S'[n-1] = x where S[i] = S[n-i-1] for i in [0..n-1].
 */
public class T_FirstLast extends T_Classic {
    public T_FirstLast(int n, boolean applyStrat) {
        super(n, applyStrat);
        model.setName("T-FirstLast");
        // Tuple saying that s[0] < s[n-1]
        Tuples firstLastVars = new Tuples(true);
        // -1 is the universal value of the array : [1,-1,-1,2] means that the second and
        // third element of the array can take all value of the domain
        firstLastVars.setUniversalValue(-1);

        // forall 0 <= i < j < n : arr[0] = i and arr[n-1] = j
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                var arr = createArrayOfN(n);
                arr[0] = i;
                arr[n - 1] = j;
                firstLastVars.add(arr);
            }
        }

        model.table(s, firstLastVars, "CT+").post();
    }

    @Override
    public int getId() {
        return 3;
    }
}
