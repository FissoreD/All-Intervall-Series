package Strategy;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainMax;
import org.chocosolver.solver.search.strategy.selectors.variables.FirstFail;
import org.chocosolver.solver.variables.IntVar;

public class MySearchStrategy {
    public static void applyFirstFail(Model model, IntVar[] s, IntVar[] dist) {
        model.getSolver().setSearch(Search.intVarSearch(
                new FirstFail(model),
                new IntDomainMax(),
                s
        ));
    }
}
