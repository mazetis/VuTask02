package lt.vu.mif.jate.tasks.task02;

import java.math.BigInteger;
import java.util.Set;
import lt.vu.mif.jate.tasks.task02.base.TestBase;
import static junit.framework.TestCase.*;
import lt.vu.mif.jate.tasks.task02.store.model.WrongRatingException;
import lt.vu.mif.jate.tasks.task02.store.model.Book;
import lt.vu.mif.jate.tasks.task02.store.model.Item;
import lt.vu.mif.jate.tasks.task02.store.model.Movie;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests class hierarchy.
 * @author valdo
 */
@RunWith(JUnit4.class)
public class TestSuite01 implements TestBase {

    @Test
    public void ItemTest() {
        
        // Basic properties
        
        Item i = new Item(ID, TITLE, DESCRIPTION) { };
        assertEquals(ID, i.getId());
        assertEquals(TITLE, i.getTitle());
        assertEquals(DESCRIPTION, i.getDescription());
        
        assertEquals(0.0D, i.getRating());
        
        // Rating
        
        try {
            
            i.addRatingValue(1);
            i.addRatingValue(2);
            i.addRatingValue(3);
            i.addRatingValue(4);
            i.addRatingValue(5);
            i.addRatingValue(6);
            
            fail("Expected to fail here");
            
        } catch (WrongRatingException ex) {
            assertEquals("Rating value must be between 1 and 5. Got 6", ex.getMessage());
        }
        
        assertEquals((Integer) 5, i.getRatingsCount());
        assertEquals(3.0D, i.getRating());
        
        // Check Object stuff
        
        Item i2 = new Item(ID, "", "") { };
        assertEquals("Item(id=1)", i.toString());
        assertEquals("Item(id=1)", i2.toString());
        assertNotSame(i, i2);
        assertEquals(i, i2);
        assertThat(0, not(equalTo(i.compareTo(i2))));
        
        Item i3 = new Item(BigInteger.TEN, "", "") { };
        assertEquals("Item(id=10)", i3.toString());
        assertNotSame(i, i3);
        assertThat(i, not(equalTo(i3)));
        
    }
    
    @Test
    public void movieTest() {
        
        Movie movie = new Movie.Builder(ID, TITLE, DESCRIPTION)
            .category(CATEGORY1)
            .category(CATEGORY2)
            .category(CATEGORY2)
            .rating(INITIAL_RATING, INITIAL_RATING_COUNT)
            .build();

        assertEquals(ID, movie.getId());
        assertEquals(TITLE, movie.getTitle());
        assertEquals(DESCRIPTION, movie.getDescription());
        assertEquals(INITIAL_RATING, movie.getRating());
        assertEquals((Integer) INITIAL_RATING_COUNT, movie.getRatingsCount());
        assertEquals(2, movie.getCategories().size());
        assertTrue(movie.getCategories() instanceof Set);
        
    }

    @Test
    public void bookTest() {
        
        Book book = new Book.Builder(ID, TITLE, DESCRIPTION)
            .subtitle(SUBTITLE)
            .category(CATEGORY1)
            .category(CATEGORY2)
            .category(CATEGORY2)
            .rating(INITIAL_RATING, INITIAL_RATING_COUNT)
            .publisher(PUBLISHER)
            .pages(PAGES)
            .author(AUTHOR1)
            .author(AUTHOR2)
            .author(AUTHOR2)
            .build();
        
        assertEquals(ID, book.getId());
        assertEquals(TITLE, book.getTitle());
        assertEquals(SUBTITLE, book.getSubtitle());
        assertEquals(DESCRIPTION, book.getDescription());
        assertEquals(INITIAL_RATING, book.getRating());
        assertEquals((Integer) INITIAL_RATING_COUNT, book.getRatingsCount());
        assertEquals(2, book.getCategories().size());
        assertTrue(book.getCategories() instanceof Set);
        assertEquals(2, book.getAuthors().size());
        assertTrue(book.getAuthors() instanceof Set);
        assertEquals((Integer) PAGES, book.getPages());
        assertEquals(PUBLISHER, book.getPublisher());
        
    }
    
}
