package lt.vu.mif.jate.tasks.task02.search.operation.operator;

/**
 * Singular operator class.
 *
 * @author valdo
 */
public enum SingularOperator implements Operator {

    /**
     * NULL operator: isNull(a).
     */
    NULL,
    /**
     * Empty operator: a == ''.
     */
    EMPTY

}
