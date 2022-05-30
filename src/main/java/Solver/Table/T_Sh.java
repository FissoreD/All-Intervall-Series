package Solver.Table;

import Solver.MySolverAbstract;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.variables.IntVar;

public class T_Sh extends MySolverAbstract {
    IntVar pos;

    public T_Sh(int n, boolean applyStrat) {
        super(new T_Classic(n, applyStrat));
        model.setName("T-Sh");

        // Tuple saying that s[0] < s[n-1]
        Tuples firstLastD = new Tuples(true);
        firstLastD.setUniversalValue(-1);

        // forall 0 <= i < j < n : arr[0] = i and arr[n-1] = j
        for (int inf = 1; inf < n; inf++) {
            for (int sup = inf + 1; sup < n; sup++) {
                var arr = createArrayOfN(n - 1);
                arr[0] = inf;
                arr[n - 2] = sup;
                firstLastD.add(arr);
            }
        }

        model.table(dist, firstLastD, "CT+").post();
    }

    @Override
    public int getId() {
        return 5;
    }
}
