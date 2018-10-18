package lt.vu.mif.jate.tasks.task02.search.operation;

import java.util.Set;
import lombok.Getter;
import lt.vu.mif.jate.tasks.task02.search.operation.operand.Field;
import lt.vu.mif.jate.tasks.task02.search.operation.operand.Literal;
import lt.vu.mif.jate.tasks.task02.search.operation.operand.Operand;
import lt.vu.mif.jate.tasks.task02.search.operation.operator.BinaryOperator;

/**
 * Binary operation, i.e. x = y. Takes 2 operands and Operation.
 *
 * @author valdo
 */
@Getter
public class BinaryOperation extends FinalOperation<BinaryOperator> {

    /**
     * First operand.
     */
    private final Operand operand1;

    /**
     * Second operand.
     */
    private final Operand operand2;

    /**
     * Constructor.
     *
     * @param operator operator.
     * @param opd1 first operand.
     * @param opd2 second operand.
     */
    public BinaryOperation(final BinaryOperator operator, final Operand opd1, final Operand opd2) {
        super(operator);
        this.operand1 = opd1;
        this.operand2 = opd2;
    }

    @Override
    public boolean interpret(Object o) {
        if (operand1.getValue() == null || operand2.getValue() == null) {
            return false;
        }
        Object opd1FldVal = operand1.getFieldValue(o);
        Object opd2FldVal = operand2.getFieldValue(o);
        switch (operator) {
            case EQUALS:
                if (operand1 instanceof Field) {
                    if (opd1FldVal != null) {
                        if (opd1FldVal instanceof Set) {
                            return ((Set) opd1FldVal).contains(opd2FldVal);
                        }
                        return opd1FldVal.equals(opd2FldVal);
                    }
                }
                return (operand1.getValue().equals(opd2FldVal));
            case CONTAINS:
                if (operand1 instanceof Field && operand2 instanceof Literal) {
                    if (opd1FldVal != null) {
                        if (opd1FldVal instanceof Set) {
                            return ((Set) opd1FldVal).contains(opd2FldVal);
                        }
                        return opd1FldVal.toString().contains(opd2FldVal.toString());
                    }
                }
                if (operand1 instanceof Field && operand2 instanceof Field) {
                    if (opd1FldVal != null && opd2FldVal != null) {
                        if (opd1FldVal instanceof Set && !(opd2FldVal instanceof Set)) {
                            return ((Set) opd1FldVal).contains(opd2FldVal.toString());
                        }
                        if (!(opd1FldVal instanceof Set) && opd2FldVal instanceof Set) {
                            Set<String> opd2List = (Set<String>) opd2FldVal;
                            for (String opd2SubVal : opd2List) {
                                if (opd1FldVal.toString().contains(opd2SubVal)) {
                                    return true;
                                }
                            }
                        }
                        if (!(opd1FldVal instanceof Set) && !(opd2FldVal instanceof Set)) {
                            return opd1FldVal.toString().contains(opd2FldVal.toString());
                        }
                    }
                }
                return (operand1.toString().contains(operand2.toString()));
            case MATCHES:
                if (operand1 instanceof Field) {
                    if (opd1FldVal != null) {
                        if (opd1FldVal instanceof Set) {
                            return ((Set) opd1FldVal).contains(opd2FldVal);
                        }
                        if (opd1FldVal.toString().matches(operand2.toString())) {
                            return true;
                        }
                    }
                }
                if (operand1 instanceof Literal) {
                    return operand1.toString().matches(operand2.toString());
                }
        }
        return false;
    }
}
