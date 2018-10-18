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
 * Book builder.
 *
 * @author Svajunas
 */
public class Book extends Item {

    @Searchable(field = Store.SUBTITLE_FIELD)
    private final String subtitle;
    @Searchable(field = Store.PUBLISHER_FIELD)
    private final String publisher;
    private final Integer pages;
    private final Double initialRating;
    private final Integer initialRatingCount;
    @Searchable(field = Store.CATEGORY_FIELD)
    private Set<String> categories = new HashSet<>();
    @Searchable(field = Store.AUTHOR_FIELD)
    private Set<String> authors = new HashSet<>();

    private Book(Book.Builder builder) {
        super(builder.ID, builder.title, builder.description);
        this.subtitle = builder.subtitle;
        this.publisher = builder.publisher;
        this.pages = builder.pages;
        this.initialRating = builder.initialRating;
        this.initialRatingCount = builder.initialRatingCount;
        this.categories = builder.categories;
        this.authors = builder.authors;
    }

    public String getSubtitle() {
        return subtitle;
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

    public Set getAuthors() {
        return authors;
    }

    public Integer getPages() {
        return pages;
    }

    public String getPublisher() {
        return publisher;
    }

    public static class Builder {
        
        private final BigInteger ID;
        private final String title;
        @Searchable(field = Store.DESCRIPTION_FIELD)
        private final String description;
        private String subtitle;
        private String publisher;
        private Integer pages;
        private Double initialRating;
        private Integer initialRatingCount;
        private Set<String> categories = new HashSet<>();
        private Set<String> authors = new HashSet<>();

        public Builder(BigInteger ID, String title, String description) {
            this.ID = ID;
            this.title = title;
            this.description = description;
        }

        public Builder subtitle(String subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public Builder publisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public Builder category(String category) {
            categories.add(category);
            return this;
        }

        public Builder author(String author) {
            authors.add(author);
            return this;
        }

        public Builder pages(Integer pages) {
            this.pages = pages;
            return this;
        }

        public Builder rating(double initialRating, int initialRatingCount) {
            this.initialRating = initialRating;
            this.initialRatingCount = initialRatingCount;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }
}
