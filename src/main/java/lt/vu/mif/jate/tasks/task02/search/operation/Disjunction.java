package lt.vu.mif.jate.tasks.task02.search.operation;

/**
 * Disjunction operation.
 * @author valdo
 */
public class Disjunction extends Junction {
    
    @Override
    public boolean interpret(Object o) {
        for (Operation e: operations) {
            if (e.interpret(o)) return true;
        }
        return false;
    }
}
