package lt.vu.mif.jate.tasks.task02.search.operation;

/**
 * Operation.
 *
 * @author valdo
 */
public interface Operation {

    public abstract boolean interpret(Object o);

    @Override
    public abstract String toString();
}
