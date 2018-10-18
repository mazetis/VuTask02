package lt.vu.mif.jate.tasks.task02.search.operation;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * Junction operation.
 *
 * @author valdo
 */
@Getter(AccessLevel.PROTECTED)
public abstract class Junction implements Operation {

    /**
     * A list of operations to connect with the junction.
     */
    public final List<Operation> operations = new ArrayList<>();

    /**
     * Add operation to the junction.
     *
     * @param op operation.
     * @return this class.
     */
    public final Junction add(final Operation op) {
        operations.add(op);
        return this;
    }
}
