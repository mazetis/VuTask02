package lt.vu.mif.jate.tasks.task02.base;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

import java.util.SortedSet;

import lt.vu.mif.jate.tasks.task02.search.operation.Operation;
import lt.vu.mif.jate.tasks.task02.store.Store;
import lt.vu.mif.jate.tasks.task02.store.model.Item;
import lt.vu.mif.jate.tasks.task02.store.model.WrongItemFormatException;

public abstract class SearchManagerTestBase implements TestBase {

    protected final Store store = new Store();
    
    public SearchManagerTestBase() {
        try {
            this.store.load(this.getClass().getResourceAsStream(BOOKS_RESOURCE));
            this.store.load(this.getClass().getResourceAsStream(MOVIES_RESOURCE));
            this.store.load(this.getClass().getResourceAsStream(BOOKS_RESOURCE));
        } catch (WrongItemFormatException ex) {
            fail(ex.getMessage());
        }
    } 
    
    protected SortedSet<Item> opTestZero(Operation operation) {
        return opTest(operation, 0);
    }

    protected SortedSet<Item> opTestAll(Operation operation) {
        return opTest(operation, store.getItems().size());
    }
    
    protected SortedSet<Item> opTest(Operation operation, int sizeExpected) {
        SortedSet<Item> items = store.findItems(operation);
        assertEquals(sizeExpected, items.size());
        return items;
    }

    protected Item opTestFirst(Operation operation, int sizeExpected) {
        SortedSet<Item> items = store.findItems(operation);
        assertEquals(sizeExpected, items.size());
        return items.iterator().next();
    }
    
}
