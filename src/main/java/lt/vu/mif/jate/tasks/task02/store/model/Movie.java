/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.jate.tasks.task02.store.model;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import lt.vu.mif.jate.tasks.task02.search.Searchable;
import lt.vu.mif.jate.tasks.task02.store.Store;

/**
 * Movie builder.
 *
 * @author Svajunas
 */
public class Movie extends Item {

    private final double initialRating;
    private final Integer initialRatingCount;
    @Searchable(field = Store.CATEGORY_FIELD)
    private Set<String> categories = new HashSet<>();

    private Movie(Builder builder) {
        super(builder.ID, builder.title, builder.description);
        this.initialRating = builder.initialRating;
        this.initialRatingCount = builder.initialRatingCount;
        this.categories = builder.categories;
    }

    @Override
    public Double getRating() {
        return initialRating;
    }

    @Override
    public Integer getRatingsCount() {
        return initialRatingCount;
    }

    public Set getCategories() {
        return categories;
    }

    public static class Builder {

        private final BigInteger ID;
        private final String title;
        @Searchable(field = Store.DESCRIPTION_FIELD)
        private final String description;
        private double initialRating;
        private Integer initialRatingCount;
        private Set<String> categories = new HashSet<>();

        public Builder(BigInteger ID, String title, String description) {
            this.ID = ID;
            this.title = title;
            this.description = description;
        }

        public Builder category(String category) {
            categories.add(category);
            return this;
        }

        public Builder rating(double initialRating, int initialRatingCount) {
            this.initialRating = initialRating;
            this.initialRatingCount = initialRatingCount;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }
}
