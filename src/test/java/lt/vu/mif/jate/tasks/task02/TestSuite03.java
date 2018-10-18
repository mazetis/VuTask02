package lt.vu.mif.jate.tasks.task02;

import lt.vu.mif.jate.tasks.task02.base.TestBase;
import static junit.framework.TestCase.*;
import lt.vu.mif.jate.tasks.task02.search.BeanProcessingException;
import lt.vu.mif.jate.tasks.task02.store.ItemFactory;
import lt.vu.mif.jate.tasks.task02.store.Store;
import lt.vu.mif.jate.tasks.task02.store.model.WrongItemFormatException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Test Store class.
 * @author valdo
 */
@RunWith(JUnit4.class)
public class TestSuite03 implements TestBase {

    @Test
    public void basicTest() throws BeanProcessingException, WrongItemFormatException {
            
        Store store = new Store();
        store.getItems().add(ItemFactory.createBook(new JSONObject(BOOK_JSON)));
        store.getItems().add(ItemFactory.createMovie(new JSONObject(MOVIE_JSON)));
        
        assertEquals(2, store.getItems().size());
        assertEquals(1, store.getValueList(Store.AUTHOR_FIELD).size());
        assertEquals(2, store.getValueList(Store.CATEGORY_FIELD).size());
        assertEquals(2, store.getValueList(Store.DESCRIPTION_FIELD).size());
        assertEquals(1, store.getValueList(Store.PUBLISHER_FIELD).size());
        assertEquals(2, store.getValueList(Store.TITLE_FIELD).size());
        assertEquals(0, store.getValueList(Store.SUBTITLE_FIELD).size());
        
    }
    
    @Test
    public void loadingTest() throws BeanProcessingException, WrongItemFormatException {

        Store store = new Store();
        
        store.load(this.getClass().getResourceAsStream(BOOKS_RESOURCE));
        store.load(this.getClass().getResourceAsStream(MOVIES_RESOURCE));
        store.load(this.getClass().getResourceAsStream(BOOKS_RESOURCE));
        
        assertEquals(249, store.getItems().size());
        assertEquals(285, store.getValueList(Store.AUTHOR_FIELD).size());
        assertEquals(66, store.getValueList(Store.CATEGORY_FIELD).size());
        assertEquals(224, store.getValueList(Store.DESCRIPTION_FIELD).size());
        assertEquals(100, store.getValueList(Store.PUBLISHER_FIELD).size());
        assertEquals(243, store.getValueList(Store.TITLE_FIELD).size());
        assertEquals(81, store.getValueList(Store.SUBTITLE_FIELD).size());
        
    }
    
}
