package lt.vu.mif.jate.tasks.task02.store.model;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lt.vu.mif.jate.tasks.task02.search.Searchable;
import lt.vu.mif.jate.tasks.task02.store.Store;

/**
 * Item class.
 *
 * @author valdo
 */
@Getter
@RequiredArgsConstructor
public abstract class Item implements Comparable<Item> {

    private final BigInteger id;
    private final Set<Integer> ratings = new HashSet<>();
    @Searchable(field = Store.TITLE_FIELD)
    private final String title;
    @Searchable(field = Store.DESCRIPTION_FIELD)
    private final String description;

    @Override
    public final int compareTo(final Item o) {
        int c = o.title.compareTo(title);
        if (c == 0) {
            c = o.id.compareTo(id);
        }
        return c;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.title);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Item) {
            Item item = (Item) o;
            if (item.id.equals(this.id)) {
                return true;
            }
        }
        return false;
    }

    public Double getRating() {
        if (ratings.size() > 0) {
            Double average = ratings.stream().mapToDouble(val -> val)
                    .average().getAsDouble();
            return average;
        } else {
            return 0.0;
        }
    }

    public void addRatingValue(int i) throws WrongRatingException {
        if (i > 0 && i < 6) {
            ratings.add(i);
        } else {
            throw new WrongRatingException(i);
        }
    }

    public Integer getRatingsCount() {
        return ratings.size();
    }

    @Override
    public final String toString() {
        return "Item(id=" + id.toString() + ")";
    }
}
