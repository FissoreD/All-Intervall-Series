package Solver.Table;

import Solver.MySolverAbstract;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.variables.IntVar;

/**
 * Creates two variables
 * - s : the solution
 * - dist = vector of distances
 * - triples = [s_i, s_(i+1), dist_i]
 * - tuples = the relationship between every triple in triples
 */
public class T_Classic extends MySolverAbstract {
    public T_Classic(int n, boolean applyStrat) {
        super(n, "T-Classic", applyStrat);

        IntVar[][] tuple = new IntVar[n - 1][];

        for (int i = 0; i < n - 1; i++) {
            tuple[i] = new IntVar[]{s[i], s[i + 1], dist[i]};
        }

        Tuples relationship = new Tuples(true);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                relationship.add(i, j, Math.abs(i - j));
                relationship.add(j, i, Math.abs(i - j));
            }
        }
        for (var tup : tuple)
            model.table(tup, relationship, "CT+").post();
    }

    @Override
    public int getId() {
        return 2;
    }
}
