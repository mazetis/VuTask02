package lt.vu.mif.jate.tasks.task02.search.operation.operand;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Literal operand.
 *
 * @author valdo
 */
@Getter
@RequiredArgsConstructor
public class Literal implements Operand {

    /**
     * Literal value.
     */
    private final String value;

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public Object getFieldValue(Object object) {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
