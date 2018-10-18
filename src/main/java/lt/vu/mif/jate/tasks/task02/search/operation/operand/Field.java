package lt.vu.mif.jate.tasks.task02.search.operation.operand;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lt.vu.mif.jate.tasks.task02.search.Searchable;

/**
 * Field operand to indicate searchable field.
 *
 * @author valdo
 */
@Getter
@RequiredArgsConstructor
public class Field implements Operand {

    /**
     * Field name.
     *
     * @return name.
     */
    private final String name;

    @Override
    public Object getValue() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public Object getFieldValue(Object object) {
        java.lang.reflect.Field[] fields;
        fields = object.getClass().getDeclaredFields();
        for (java.lang.reflect.Field fieldl : fields) {
            try {
                AccessController.doPrivileged((PrivilegedAction) () -> {
                    fieldl.setAccessible(true);
                    return null;
                });
                Object fldValue = fieldl.get(object);
                if (fldValue != null) {
                    Searchable annotation = fieldl.getAnnotation(Searchable.class);
                    if (annotation != null) {
                        String nameAnnotation = annotation.field();
                        if (nameAnnotation.equals(name)) {
                            return fldValue;
                        }
                    }
                }
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Field.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Field.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        java.lang.reflect.Field[] fieldsSuper;
        fieldsSuper = object.getClass().getSuperclass().getDeclaredFields();
        for (java.lang.reflect.Field fieldl : fieldsSuper) {
            try {
                AccessController.doPrivileged((PrivilegedAction) () -> {
                    fieldl.setAccessible(true);
                    return null;
                });
                Object sprFldValue = fieldl.get(object);
                if (sprFldValue != null) {
                    Searchable annotation = fieldl.getAnnotation(Searchable.class);
                    if (annotation != null) {
                        String nameAnnotation = annotation.field();
                        if (nameAnnotation.equals(name)) {
                            return sprFldValue;
                        }
                    }
                }
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Field.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Field.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
}
