package lt.vu.mif.jate.tasks.task02;

import lt.vu.mif.jate.tasks.task02.base.SearchManagerTestBase;
import static junit.framework.TestCase.*;
import static lt.vu.mif.jate.tasks.task02.search.SearchHelper.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import lt.vu.mif.jate.tasks.task02.store.Store;
import lt.vu.mif.jate.tasks.task02.store.model.Book;
import lt.vu.mif.jate.tasks.task02.store.model.Item;
import lt.vu.mif.jate.tasks.task02.store.model.Movie;

/**
 * Test search manager: Singular operations.
 * @author valdo
 */
@RunWith(JUnit4.class)
public class TestSuite04 extends SearchManagerTestBase {

    @Test
    public void simpleLiteralTest() {
        
        opTestAll(
            opEmpty(opLiteral(""))
        );

        opTestZero(
            opEmpty(opLiteral("Value"))
        );

        opTestZero(
            opEmpty(opLiteral(null))
        );
        
        opTestZero(
            opNot(opEmpty(opLiteral("")))
        );

        opTestAll(
            opNot(opEmpty(opLiteral("Value")))
        );

        opTestAll(
            opNull(opLiteral(null))
        );

        opTestZero(
            opNull(opLiteral(""))
        );

        opTestZero(
            opNot(opNull(opLiteral(null)))
        );

        opTestAll(
            opNot(opNull(opLiteral("")))
        );
        
        opTestAll(
            opNot(opNot(opNot(opNot(opNot(opNull(opLiteral("")))))))
        );
        
    }
        
    @Test
    public void junctionLiteralTest() {
        
        opTestAll(
            opAnd(opEmpty(opLiteral("")))
        );

        opTestAll(
            opOr(opEmpty(opLiteral("")))
        );
        
        opTestAll(
            opAnd(opEmpty(opLiteral("")), opEmpty(opLiteral("")))
        );

        opTestZero(
            opAnd(opEmpty(opLiteral("")), opEmpty(opLiteral("Value")))
        );

        opTestAll(
            opOr(opEmpty(opLiteral("")), opEmpty(opLiteral("Value")))
        );

        opTestAll(
            opOr(opEmpty(opLiteral("")), opEmpty(opLiteral("")))
        );
        
        opTestZero(
            opOr(opEmpty(opLiteral("Value")), opEmpty(opLiteral("Value")))
        );

        opTestAll(
            opOr(opEmpty(opLiteral("Value")), opEmpty(opLiteral("Value")), opEmpty(opLiteral("")))
        );

        opTestAll(
            opOr(opEmpty(opLiteral("Value")), opEmpty(opLiteral("Value")), opNull(opLiteral(null)))
        );

        opTestZero(
            opAnd(opEmpty(opLiteral("Value")), opEmpty(opLiteral("Value")), opNull(opLiteral("")))
        );

        opTestZero(
            opAnd(opEmpty(opLiteral("")), opEmpty(opLiteral("")), opNot(opNull(opLiteral(null))))
        );

        opTestAll(
            opNot(opAnd(opEmpty(opLiteral("")), opEmpty(opLiteral("")), opNot(opNull(opLiteral(null)))))
        );
        
    }
    
    @Test
    public void simpleFieldTest() {
        
        {
            Item item = opTestFirst(
                opEmpty(opField(Store.SUBTITLE_FIELD)),
                1
            );
            assertEquals("Computer. Illustrierte Geschic", item.getTitle());
        }

        {
            Item item = opTestFirst(
                opNot(opEmpty(opField(Store.SUBTITLE_FIELD))),
                248
            );
            assertEquals("Wesley Willis's Joy Rides", item.getTitle());
        }

        {
            Item item = opTestFirst(
                opNull(opField(Store.SUBTITLE_FIELD)),
                167
            );
            assertEquals("Wesley Willis's Joy Rides", item.getTitle());
            assertTrue(item instanceof Movie);
        }

        {
            Item item = opTestFirst(
                opNot(opNull(opField(Store.SUBTITLE_FIELD))),
                82
            );
            assertEquals("Understanding Computers and Cognition", item.getTitle());
            assertTrue(item instanceof Book);
        }
        
    }
    
    @Test
    public void junctionFieldTest() {
        
        {
            Item item = opTestFirst(
                opOr(opEmpty(opField(Store.SUBTITLE_FIELD)), opNull(opField(Store.SUBTITLE_FIELD))),
                168
            );
            assertEquals("Wesley Willis's Joy Rides", item.getTitle());
        }

        {
            Item item = opTestFirst(
                opNot(opOr(opEmpty(opField(Store.SUBTITLE_FIELD)), opNull(opField(Store.SUBTITLE_FIELD)))),
                81
            );
            assertEquals("Understanding Computers and Cognition", item.getTitle());
        }
        
    }
    
}
