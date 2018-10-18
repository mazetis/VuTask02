package lt.vu.mif.jate.tasks.task02.search.operation;

/**
 * Conjunction.
 *
 * @author valdo
 */
public class Conjunction extends Junction {

    @Override
    public boolean interpret(Object o) {
        for (Operation e : operations) {
            if (!e.interpret(o)) {
                return false;
            }
        }
        return true;
    }
}
