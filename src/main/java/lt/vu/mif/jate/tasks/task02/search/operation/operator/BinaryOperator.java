package lt.vu.mif.jate.tasks.task02.search.operation.operator;

/**
 * Binary operator class.
 *
 * @author valdo
 */
public enum BinaryOperator implements Operator {

    /**
     * Equals operator: 'a' == 'b'.
     */
    EQUALS,
    /**
     * Contains operator: 'abc' contains 'c'.
     */
    CONTAINS,
    /**
     * Matches operator: 'abc' matches '.*c$.
     */
    MATCHES;

    /**
     *
     * @return
     */
}
