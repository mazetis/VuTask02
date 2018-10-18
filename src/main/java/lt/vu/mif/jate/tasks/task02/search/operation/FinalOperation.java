package lt.vu.mif.jate.tasks.task02.search.operation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lt.vu.mif.jate.tasks.task02.search.operation.operator.Operator;

/**
 * Leaf Operation.
 *
 * @author valdo
 * @param <O> operator class.
 */
@Getter
@RequiredArgsConstructor
public abstract class FinalOperation<O extends Operator> implements Operation {

    /**
     * Operation operator.
     */
    public O operator;

    public FinalOperation(Operator operator) {
        this.operator = (O) operator;
    }
}
