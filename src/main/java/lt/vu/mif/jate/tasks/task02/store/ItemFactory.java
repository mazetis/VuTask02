/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.jate.tasks.task02.store;

import lt.vu.mif.jate.tasks.task02.store.model.Book;
import lt.vu.mif.jate.tasks.task02.store.model.Movie;
import lt.vu.mif.jate.tasks.task02.store.model.WrongItemFormatException;
import org.json.JSONObject;

/**
 *
 * @author Svajunas
 */
public class ItemFactory {

    private ItemFactory() {
    }

    /**
     *
     * @param book
     * @return
     */
    public static Book createBook(JSONObject book) {
        if (book.has("isbn")) {
            Book newBook = new Book.Builder(book.getBigInteger("isbn"),
                    book.getString("title"),
                    book.getString("description"))
                    .publisher(book.getString("publisher"))
                    .rating(book.getDouble("averageRating"),
                            book.getInt("ratingsCount"))
                    .category(book.getJSONArray("categories").get(0).toString())
                    .author(book.getJSONArray("authors").get(0).toString())
                    .pages(book.getInt("pageCount"))
                    .build();
            return newBook;
        } else {
            throw new WrongItemFormatException();
        }
    }

    public static Movie createMovie(JSONObject movie) {
        if (movie.has("id")) {
            String categories = movie.getString("categoryPath");
            String[] parts = categories.split("/");
            String category1 = parts[1];
            String category2 = parts[2];
            Movie newMovie = new Movie.Builder(movie.getBigInteger("id"),
                    movie.getString("name"),
                    movie.getString("longDescription"))
                    .rating(movie.getDouble("customerRating"),
                            movie.getInt("numReviews"))
                    .category(category1)
                    .category(category2)
                    .build();
            return newMovie;
        } else {
            throw new WrongItemFormatException();
        }
    }
}
