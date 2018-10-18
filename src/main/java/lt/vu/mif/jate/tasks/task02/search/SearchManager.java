package lt.vu.mif.jate.tasks.task02.search;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import lt.vu.mif.jate.tasks.task02.search.operation.Operation;
import java.util.Locale;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Search Manager to be extended by domain class.
 *
 * @author valdo
 * @param <T> collection element type.
 */
public abstract class SearchManager<T extends Comparable<T>> {

    /**
     * Get collection of elements to search in.
     *
     * @return collection.
     */
    protected abstract Collection<T> getSearchItems();

    /**
     * Search for items in collection.
     *
     * @param operation operation for the search.
     * @return found elements.
     */
    public final SortedSet<T> findItems(final Operation operation) {
        SortedSet<T> result = new TreeSet<>();
        for (T item : getSearchItems()) {
            if (operation.interpret(item)) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Return the list of unique values indicated by the keywords.
     *
     * @param field field to search.
     * @return set of values.
     */
    public final Set<String> getValueList(final String field) {
        Set<String> result = new HashSet<>();
        for (T item : getSearchItems()) {
            Field[] fields = item.getClass().getDeclaredFields();
            for (Field fieldl : fields) {
                try {
                    AccessController.doPrivileged((PrivilegedAction) () -> {
                        fieldl.setAccessible(true);
                        return null;
                    });
                    Object fldValue = fieldl.get(item);
                    if (fldValue != null) {
                        Searchable annotation = fieldl.getAnnotation(Searchable.class);
                        if (annotation != null) {
                            String nameAnnotation = annotation.field();
                            if (nameAnnotation.equals(field)) {
                                if (fldValue instanceof Set<?>) {
                                    Set<String> subValues = (Set<String>) fldValue;
                                    for (String subValue : subValues) {
                                        if (subValue.matches(".*[a-z].*")) {
                                            result.add(subValue);
                                        } else {
                                            String subValAdj = subValue.substring(0, 1)
                                                    .toUpperCase(Locale.ROOT) + subValue
                                                    .toLowerCase(Locale.ROOT).substring(1);
                                            result.add(subValAdj);
                                        }
                                    }
                                } else {
                                    result.add(fldValue.toString());
                                }
                            }
                        }
                    }
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(SearchManager.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(SearchManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Field[] fieldsSuper = item.getClass().getSuperclass().getDeclaredFields();
            for (Field fieldl : fieldsSuper) {
                try {
                    AccessController.doPrivileged((PrivilegedAction) () -> {
                        fieldl.setAccessible(true);
                        return null;
                    });
                    Object sprFldValue = fieldl.get(item);
                    if (sprFldValue != null) {
                        Searchable annotation = fieldl.getAnnotation(Searchable.class);
                        if (annotation != null) {
                            String nameAnnotation = annotation.field();
                            if (nameAnnotation.equals(field)) {
                                result.add(sprFldValue.toString());
                            }
                        }
                    }
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(SearchManager.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(SearchManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }
}
