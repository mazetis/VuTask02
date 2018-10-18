package lt.vu.mif.jate.tasks.task02.search.operation.operand;

/**
 * Operand super type.
 *
 * @author valdo
 */
public interface Operand {

    public Object getValue();

    public Object getFieldValue(Object object);
}
