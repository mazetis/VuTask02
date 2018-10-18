package lt.vu.mif.jate.tasks.task02.search.operation;

import lombok.Getter;
import lt.vu.mif.jate.tasks.task02.search.operation.operand.Operand;
import lt.vu.mif.jate.tasks.task02.search.operation.operator.SingularOperator;

/**
 * Singular Operation that takes one operand, i.e. isnull(x)
 *
 * @author valdo
 */
@Getter
public class SingularOperation extends FinalOperation<SingularOperator> {

    /**
     * Singular operand.
     */
    private final Operand operand;
    //private SingularOperator operator;

    /**
     * Constructor.
     *
     * @param operator singular operator.
     * @param opd single operand.
     */
    public SingularOperation(final SingularOperator operator, final Operand opd) {
        super(operator);
        this.operand = opd;
    }

    @Override
    public boolean interpret(Object o) {
        Object opdFldVal = operand.getFieldValue(o);
        switch (operator) {
            case NULL:
                return opdFldVal == null;
            case EMPTY:
                if (opdFldVal == null) {
                    return false;
                }
                if (opdFldVal.equals("")) {
                    return true;
                }
        }
        return false;
    }
}
