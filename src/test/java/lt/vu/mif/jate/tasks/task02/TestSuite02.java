package lt.vu.mif.jate.tasks.task02;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import lt.vu.mif.jate.tasks.task02.base.TestBase;
import lt.vu.mif.jate.tasks.task02.store.ItemFactory;
import lt.vu.mif.jate.tasks.task02.store.Store;
import static junit.framework.TestCase.*;
import lt.vu.mif.jate.tasks.task02.store.model.WrongItemFormatException;
import lt.vu.mif.jate.tasks.task02.store.model.Book;
import lt.vu.mif.jate.tasks.task02.store.model.Movie;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Test ItemFactory class.
 * @author valdo
 */
@RunWith(JUnit4.class)
public class TestSuite02 implements TestBase {

    @Test
    public void ItemFactoryTest() {
        
        Store store = new Store();
        
        JSONObject bookJson = new JSONObject(BOOK_JSON);
        JSONObject movieJson = new JSONObject(MOVIE_JSON);
        
        try {

            Book book = (Book) ItemFactory.createBook(bookJson);
            
            assertEquals(new BigInteger("9780262680820"), book.getId());
            assertEquals("The Computer Music Tutorial", book.getTitle());
            assertNull(book.getSubtitle());
            assertEquals(4.5D, book.getRating());
            assertEquals((Integer) 19, book.getRatingsCount());
            assertEquals(1, book.getCategories().size());
            assertEquals("Art", book.getCategories().iterator().next());
            assertEquals(1, book.getAuthors().size());
            assertEquals("Curtis Roads", book.getAuthors().iterator().next());
            assertEquals((Integer) 1234, book.getPages());
            assertEquals("MIT Press", book.getPublisher());
            
            store.getItems().add(book);
        
            Movie movie = (Movie) ItemFactory.createMovie(movieJson);
            
            assertEquals(new BigInteger("10793915"), movie.getId());
            assertEquals("Joy Ride (Widescreen)", movie.getTitle());
            assertEquals(5.0D, movie.getRating());
            assertEquals((Integer) 3, movie.getRatingsCount());
            assertEquals(2, movie.getCategories().size());
            
            Set<String> CATS = new HashSet<>();
            CATS.add("Action & Adventure");
            CATS.add("Art");
            
            assertTrue(CATS.containsAll(movie.getCategories()));
            assertTrue(movie.getCategories().containsAll(CATS));
            
            store.getItems().add(movie);
            
        } catch (WrongItemFormatException ex) {
            fail(ex.getMessage());
        }
        
        try {
            ItemFactory.createBook(movieJson);
            fail("Must thrown WrongItemFormatException");
        } catch (WrongItemFormatException ex) { }

        try {
            ItemFactory.createMovie(bookJson);
            fail("Must thrown WrongItemFormatException");
        } catch (WrongItemFormatException ex) { }

    }
    
}
