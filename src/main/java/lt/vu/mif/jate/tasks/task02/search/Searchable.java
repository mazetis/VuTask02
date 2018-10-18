package lt.vu.mif.jate.tasks.task02.search;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates Searchable field.
 * @author valdo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Searchable {
    
    /**
     * Defines keyword for the field.
     * @return 
     */
    String field();
    
}
