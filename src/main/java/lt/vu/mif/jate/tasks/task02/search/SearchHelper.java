package lt.vu.mif.jate.tasks.task02.search;

import lt.vu.mif.jate.tasks.task02.search.operation.BinaryOperation;
import lt.vu.mif.jate.tasks.task02.search.operation.Conjunction;
import lt.vu.mif.jate.tasks.task02.search.operation.Disjunction;
import lt.vu.mif.jate.tasks.task02.search.operation.Junction;
import lt.vu.mif.jate.tasks.task02.search.operation.Negation;
import lt.vu.mif.jate.tasks.task02.search.operation.Operation;
import lt.vu.mif.jate.tasks.task02.search.operation.SingularOperation;
import lt.vu.mif.jate.tasks.task02.search.operation.operand.Field;
import lt.vu.mif.jate.tasks.task02.search.operation.operand.Literal;
import lt.vu.mif.jate.tasks.task02.search.operation.operand.Operand;
import lt.vu.mif.jate.tasks.task02.search.operation.operator.BinaryOperator;
import lt.vu.mif.jate.tasks.task02.search.operation.operator.SingularOperator;

/**
 * Search Helper class.
 *
 * @author valdo
 */
public final class SearchHelper {

    /**
     * Constructor.
     */
    private SearchHelper() {
    }

    /**
     * Create NULL operation.
     *
     * @param operand operand.
     * @return operation.
     */
    public static Operation opNull(final Operand operand) {
        return new SingularOperation(SingularOperator.NULL, operand);
    }

    /**
     * Create EMPTY operation.
     *
     * @param operand operand.
     * @return operation.
     */
    public static Operation opEmpty(final Operand operand) {
        return new SingularOperation(SingularOperator.EMPTY, operand);
    }

    /**
     * Create EQUALS operation.
     *
     * @param operand1 first operand.
     * @param operand2 second operand.
     * @return operation.
     */
    public static Operation opEquals(final Operand operand1, final Operand operand2) {
        return new BinaryOperation(BinaryOperator.EQUALS, operand1, operand2);
    }

    /**
     * Create CONTAINS operation.
     *
     * @param operand1 first operand.
     * @param operand2 second operand.
     * @return operation.
     */
    public static Operation opContains(final Operand operand1, final Operand operand2) {
        return new BinaryOperation(BinaryOperator.CONTAINS, operand1, operand2);
    }

    /**
     * Create MATCHES operation.
     *
     * @param operand1 first operand.
     * @param operand2 second operand.
     * @return operation.
     */
    public static Operation opMatches(final Operand operand1, final Operand operand2) {
        return new BinaryOperation(BinaryOperator.MATCHES, operand1, operand2);
    }

    /**
     * Create NOT operation.
     *
     * @param operation operations.
     * @return operation.
     */
    public static Operation opNot(final Operation operation) {
        return new Negation(operation);
    }

    /**
     * Create AND operation.
     *
     * @param operation operations.
     * @return operation.
     */
    public static Operation opAnd(final Operation... operation) {
        return junction(new Conjunction(), operation);
    }

    /**
     * Create OR operation.
     *
     * @param operation operations.
     * @return operation.
     */
    public static Operation opOr(final Operation... operation) {
        return junction(new Disjunction(), operation);
    }

    /**
     * Create FIELD operand.
     *
     * @param name field name.
     * @return operand.
     */
    public static Field opField(final String name) {
        return new Field(name);
    }

    /**
     * Create LITERAL operand.
     *
     * @param value value.
     * @return operand.
     */
    public static Literal opLiteral(final String value) {
        return new Literal(value);
    }

    /**
     * Utility method to create Junction object.
     *
     * @param <J> Junction type.
     * @param junction Junction object.
     * @param operation operations.
     * @return Junction object.
     */
    private static <J extends Junction> J junction(final J junction, final Operation... operation) {
        for (Operation op : operation) {
            junction.add(op);
        }
        return junction;
    }
}
