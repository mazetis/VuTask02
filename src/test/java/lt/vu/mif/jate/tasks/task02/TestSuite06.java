package lt.vu.mif.jate.tasks.task02;

import lt.vu.mif.jate.tasks.task02.base.SearchManagerTestBase;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static lt.vu.mif.jate.tasks.task02.search.SearchHelper.opAnd;
import static lt.vu.mif.jate.tasks.task02.search.SearchHelper.opEquals;
import static lt.vu.mif.jate.tasks.task02.search.SearchHelper.opField;
import static lt.vu.mif.jate.tasks.task02.search.SearchHelper.opLiteral;
import static lt.vu.mif.jate.tasks.task02.search.SearchHelper.opOr;

import java.math.BigInteger;
import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import lt.vu.mif.jate.tasks.task02.store.Store;
import lt.vu.mif.jate.tasks.task02.store.model.Item;
import lt.vu.mif.jate.tasks.task02.store.model.WrongItemFormatException;

/**
 * Test search manager: some more tests.
 * @author valdo
 */
@RunWith(JUnit4.class)
public class TestSuite06 extends SearchManagerTestBase {
    
    @Test
    public void findTest() throws WrongItemFormatException {

        {
            Item item = opTestFirst(
                opEquals(opField(Store.AUTHOR_FIELD), opLiteral("Bruce S. Davie")),
                1
            );
            assertEquals(new BigInteger("9780123850607"), item.getId());
        }

        {
            Iterator<Item> iter = opTest(
                opEquals(opField(Store.CATEGORY_FIELD), opLiteral("Drama")), 
                12).iterator();
            assertEquals(new BigInteger("1942865"), iter.next().getId());
            assertEquals(new BigInteger("22001235"), iter.next().getId());
            assertEquals(new BigInteger("16775500"), iter.next().getId());
            assertEquals(new BigInteger("12554158"), iter.next().getId());
            assertEquals(new BigInteger("17655358"), iter.next().getId());
            assertEquals(new BigInteger("5366981"), iter.next().getId());
            assertEquals(new BigInteger("10737024"), iter.next().getId());
            assertEquals(new BigInteger("2458147"), iter.next().getId());
            assertEquals(new BigInteger("2534844"), iter.next().getId());
            assertEquals(new BigInteger("27859828"), iter.next().getId());
            assertEquals(new BigInteger("1078695"), iter.next().getId());
            assertEquals(new BigInteger("16904751"), iter.next().getId());
            assertFalse(iter.hasNext());
        }
        
        {
            Item item = opTestFirst(
                opOr(
                    opEquals(opField(Store.CATEGORY_FIELD), opLiteral("Drama")),
                    opEquals(opField(Store.CATEGORY_FIELD), opLiteral("Westerns"))
                ),
                12
            );
            assertEquals(new BigInteger("1942865"), item.getId());
        }

        {
            Item item = opTestFirst(
                opAnd(
                    opEquals(opField(Store.CATEGORY_FIELD), opLiteral("Drama")),
                    opEquals(opField(Store.CATEGORY_FIELD), opLiteral("Westerns"))
                ),
                1
            );
            assertEquals(new BigInteger("5366981"), item.getId());
        }
        
        {
            Item item = opTestFirst(
                opAnd(
                    opEquals(opField(Store.CATEGORY_FIELD), opLiteral("Design"))
                ),
                2
            );
            assertEquals(new BigInteger("9783764384142"), item.getId());
        }

        opTest(
            opAnd(
                opEquals(opField(Store.PUBLISHER_FIELD), opLiteral("MIT Press"))
            ),
            9
        );

        opTestZero(
            opAnd(
                opEquals(opField(Store.PUBLISHER_FIELD), opLiteral("MIT Press")),
                opEquals(opField(Store.CATEGORY_FIELD), opLiteral("Computers")),
                opEquals(opField(Store.CATEGORY_FIELD), opLiteral("Art"))
            )
        );

        opTest(
            opOr(
                opEquals(opField(Store.PUBLISHER_FIELD), opLiteral("MIT Press")),
                opEquals(opField(Store.CATEGORY_FIELD), opLiteral("Computers")),
                opEquals(opField(Store.CATEGORY_FIELD), opLiteral("Art"))
            ),
            144
        );

        opTest(
            opOr(
                opEquals(opField(Store.PUBLISHER_FIELD), opLiteral("MIT Press")),
                opEquals(opField(Store.CATEGORY_FIELD), opLiteral("Art"))
            ),
            9
        );

        opTest(
            opOr(
                opEquals(opField(Store.PUBLISHER_FIELD), opLiteral("MIT Press")),
                opEquals(opField(Store.CATEGORY_FIELD), opLiteral("Computers"))
            ),
            144
        );
        
        opTestZero(
            opEquals(opField(Store.DESCRIPTION_FIELD), opLiteral(""))
        );

    } 
    
}
