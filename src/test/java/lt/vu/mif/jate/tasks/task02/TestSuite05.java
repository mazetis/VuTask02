package lt.vu.mif.jate.tasks.task02;

import lt.vu.mif.jate.tasks.task02.base.SearchManagerTestBase;
import static junit.framework.TestCase.*;
import static lt.vu.mif.jate.tasks.task02.search.SearchHelper.*;

import java.math.BigInteger;
import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import lt.vu.mif.jate.tasks.task02.store.Store;
import lt.vu.mif.jate.tasks.task02.store.model.Item;

/**
 * Test search manager: Binary operations.
 * @author valdo
 */
@RunWith(JUnit4.class)
public class TestSuite05 extends SearchManagerTestBase {
   
    @Test
    public void simpleLiteralTest() {
        
        // EQUALS operation
        
        opTestAll(
            opEquals(opLiteral("Labas"), opLiteral("Labas"))
        );
        
        opTestZero(
            opEquals(opLiteral("Labas"), opLiteral("rytas"))
        );

        opTestZero(
            opEquals(opLiteral(null), opLiteral(null))
        );
        
        opTestZero(
            opEquals(opLiteral(null), opLiteral("rytas"))
        );

        opTestAll(
            opNot(opEquals(opLiteral(null), opLiteral("rytas")))
        );
        
        // CONTAINS operation
        
        opTestZero(
            opContains(opLiteral("vienas"), opLiteral("A"))
        );

        opTestAll(
            opContains(opLiteral("dviratis"), opLiteral("vi"))
        );
        
        opTestZero(
            opContains(opLiteral(null), opLiteral(null))
        );

        opTestAll(
            opNot(opContains(opLiteral(null), opLiteral(null)))
        );
        
        // MATCHES operation
        
        opTestZero(
            opMatches(opLiteral("vienas"), opLiteral("A"))
        );

        opTestAll(
            opMatches(opLiteral("dviratis"), opLiteral("^dvi.*"))
        );

        opTestZero(
            opNot(opMatches(opLiteral("dviratis"), opLiteral("^dvi.*")))
        );
        
        opTestAll(
            opMatches(opLiteral("valdas.rapsevicius@mif.vu.lt"), opLiteral(".+@.+"))
        );
        
        opTestZero(
            opMatches(opLiteral(null), opLiteral(null))
        );
        
    }
    
    @Test
    public void junctionLiteralTest() {
        
        opTestAll(
            opOr(opEquals(opLiteral("Labas"), opLiteral("Labas")), opEquals(opLiteral("Labas"), opLiteral("rytas")))
        );

        opTestZero(
            opAnd(opEquals(opLiteral("Labas"), opLiteral("Labas")), opEquals(opLiteral("Labas"), opLiteral("rytas")))
        );
        
        opTestAll(
            opAnd(
                opOr(
                    opNot(opEquals(opLiteral(null), opLiteral("rytas"))),
                    opContains(opLiteral("vienas"), opLiteral("A"))
                ),
                opEquals(opLiteral("Labas"), opLiteral("Labas"))
            )
        );

    }
    
    @Test
    public void simpleFieldTest() {
        
        // EQUALS operation

        {
            Item item = opTestFirst(
                opEquals(opField(Store.AUTHOR_FIELD), opLiteral("Peter Toren")),
                1
            );
            assertEquals(new BigInteger("9781588521187"), item.getId());
        }

        {
            Item item = opTestFirst(
                opNot(opEquals(opField(Store.TITLE_FIELD), opLiteral("Wesley Willis's Joy Rides"))),
                248
            );
            assertEquals("Understanding Computers and Cognition", item.getTitle());
        }
        
        // CONTAINS operation
        
        {
            Item item = opTestFirst(
                opContains(opField(Store.TITLE_FIELD), opLiteral("Computer")),
                175
            );
            assertEquals("Understanding Computers and Cognition", item.getTitle());
        }

        {
            Item item = opTestFirst(
                opNot(opContains(opField(Store.TITLE_FIELD), opLiteral("Computer"))),
                74);
            assertEquals("Wesley Willis's Joy Rides", item.getTitle());
        }
        
        // MATCHES operation
        
        {
            Item item = opTestFirst(
                opMatches(opField(Store.TITLE_FIELD), opLiteral(".*A.*")),
                74
            );
            assertEquals("The Interaction of Compilation Technology and Computer Architecture", item.getTitle());
        }

        {
            Item item = opTestFirst(
                opMatches(opField(Store.TITLE_FIELD), opLiteral(".*[0-9].*")),
                25
            );
            assertEquals("Thriller 4-Pack: Swimfan / Wrong Turn / Joy Ride / The Vanishing", item.getTitle());
        }

        {
            Item item = opTestFirst(
                opNot(opMatches(opField(Store.TITLE_FIELD), opLiteral("[A-Z].*"))),
                3
            );
            assertEquals("3D computer graphics", item.getTitle());
        }
        
        {
            Item item = opTestFirst(
                opContains(opField(Store.DESCRIPTION_FIELD), opField(Store.TITLE_FIELD)),
                41
            );
            assertEquals("The Interaction of Compilation Technology and Computer Architecture", item.getTitle());
        }

        {
            Item item = opTestFirst(
                opContains(opField(Store.DESCRIPTION_FIELD), opField(Store.AUTHOR_FIELD)),
                13
            );
            assertEquals("Three-dimensional Computer Vision", item.getTitle());
        }
        
    }
    
    @Test
    public void junctionFieldTest() {
        
        {
            Item item = opTestFirst(
                opOr(
                    opEquals(opField(Store.AUTHOR_FIELD), opLiteral("Peter Toren")),
                    opNot(opEquals(opField(Store.TITLE_FIELD), opLiteral("Wesley Willis's Joy Rides")))
                ),
                248
            );
            assertEquals(new BigInteger("9780893910501"), item.getId());
        }

        {
            Iterator<Item> iter = opTest(
                opAnd(
                    opContains(opField(Store.TITLE_FIELD), opLiteral("Computer")),
                    opContains(opField(Store.TITLE_FIELD), opLiteral("Vision"))
                ),
                11
            ).iterator();
            assertEquals("Three-dimensional Computer Vision", iter.next().getTitle());
            assertEquals("OpenCV 2 Computer Vision Application Programming Cookbook", iter.next().getTitle());
            assertEquals("Multiple View Geometry in Computer Vision", iter.next().getTitle());
            assertEquals("Machine Learning in Computer Vision", iter.next().getTitle());
            assertEquals("Computer and Machine Vision", iter.next().getTitle());
            assertEquals("Computer Vision", iter.next().getTitle());
            assertEquals("Computer Perceptual Organization in Computer Vision", iter.next().getTitle());
            assertEquals("Calibration and Orientation of Cameras in Computer Vision", iter.next().getTitle());
            assertEquals("Applied Graph Theory in Computer Vision and Pattern Recognition", iter.next().getTitle());
            assertEquals("Algorithms for Image Processing and Computer Vision", iter.next().getTitle());
            assertEquals("Advances in Computer Vision and Information Technology", iter.next().getTitle());
        }

        {
            opTestZero(
                opAnd(
                    opNot(opContains(opField(Store.TITLE_FIELD), opLiteral("Computer"))),
                    opContains(opField(Store.TITLE_FIELD), opLiteral("Vision"))
                )
            );
        }
        
        {
            opTest(
                opAnd(
                    opContains(opField(Store.TITLE_FIELD), opLiteral("Computer")),
                    opContains(opField(Store.TITLE_FIELD), opLiteral("Vision")),
                    opContains(opField(Store.TITLE_FIELD), opLiteral("Machine"))
                ),
                2
            );
        }
        
    }
    
}
