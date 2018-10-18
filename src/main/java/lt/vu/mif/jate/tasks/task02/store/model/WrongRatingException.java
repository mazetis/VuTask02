/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.jate.tasks.task02.store.model;

/**
 *
 * @author Svajunas
 */
public class WrongRatingException extends RuntimeException {

    public String message;

    public WrongRatingException(int number) {
        this.message = "Rating value must be between 1 and 5. Got "
                + Integer.toString(number);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
