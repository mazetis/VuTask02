package lt.vu.mif.jate.tasks.task02;

import lt.vu.mif.jate.tasks.task02.base.GeneratorTestBase;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.SortedSet;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static lt.vu.mif.jate.tasks.task02.search.SearchHelper.*;
import lt.vu.mif.jate.tasks.task02.search.SearchManager;
import lt.vu.mif.jate.tasks.task02.store.model.WrongItemFormatException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Test search manager with dynamic Items.
 * This test generates 
 *  - class with random name, 
 *  - PROPERTIES_COUNT properties with randomNames,
 *  - random number of class instances with random values
 *  Executes some tests on known values
 * 
 * @author valdo
 */
@RunWith(JUnit4.class)
@SuppressWarnings({"rawtypes", "unchecked"})
public class TestSuite07 extends GeneratorTestBase {

    public TestSuite07() throws CannotCompileException, NotFoundException, InstantiationException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        super();
    }

    @Test
    public void findTest() throws WrongItemFormatException {

        SearchManager sm = new SearchManager() {

            @Override
            protected Collection getSearchItems() {
                return items;
            }
            
        };
        
        {
            
            String value1 = values.next();
            values.get(value1).entrySet().stream().forEach(e1 -> {
                PropertyDescr p1 = e1.getKey();

                SortedSet<?> found1 = sm.findItems(
                    opEquals(opField(p1.getFieldName()), opLiteral(value1))
                );
                assertEquals((int) e1.getValue(), (int) found1.size());
                
                String value2 = values.next();
                values.get(value2).entrySet().stream().forEach(e2 -> {
                    PropertyDescr p2 = e2.getKey();
                
                    int sum = e1.getValue() + e2.getValue();
                    
                    SortedSet<?> found2 = sm.findItems(
                        opOr(
                            opEquals(opField(p1.getFieldName()), opLiteral(value1)),
                            opEquals(opField(p2.getFieldName()), opLiteral(value2))
                        )
                    );
                    
                    int size = found2.size();
                    
                    assertTrue(size <= sum && size >= e1.getValue() && size >= e2.getValue());
                    
                });
                
            });

        }
    } 
        
}
