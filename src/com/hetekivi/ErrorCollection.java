package com.hetekivi;

import javax.swing.*;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by Santeri Hetekivi on 2/15/16.
 */

/**
 * Collection to store and handle Errors.
 */
public class ErrorCollection
{
    // LinkedList to store Errors.
    private Collection<Error> errors = new LinkedList<>();

    /**
     * Getter for errors.
     * @return Collection of errors.
     */
    private Collection<Error> Errors(){ return this.errors; }

    /**
     * Setter for errors.
     * @param errors Collection of Error classes.
     */
    private void Errors(Collection<Error> errors) { this.errors = errors; }

    /**
     * Adding single Error to collection.
     * @param error Error to add to the collection.
     */
    public void Add(Error error) { this.errors.add(error); }

    /**
     * Adding collection of errors.
     * @param errorCollection Another instance of ErrorCollection class.
     */
    public void Add(ErrorCollection errorCollection)
    {
        if(errorCollection.HasErrors())
        {
            this.errors.addAll(errorCollection.Errors());
        }
    }

    /**
     * Removing given error from collection.
     * @param error Error that needs removing.
     * @return Error removing was success.
     */
    public boolean Remove(Error error) {
        return this.errors.contains(error) && this.errors.remove(error);
    }

    /**
     * Checking if collection has errors.
     * @return Does collection has errors.
     */
    public boolean HasErrors() { return !this.Errors().isEmpty(); }

    /**
     * Printing all errors, by calling Print function for all Error classes.
     */
    public void Print()
    {
        this.Errors().forEach(Error::Print);
    }

    /**
      * Showing the errors in UI Dialog.
      * @param frame Frame where to show frame.
     */
    public void Show(JFrame frame)
    {
        String message = "";
        for (Error error: this.Errors())
        {
            message += error.Message + "\n";
        }
        JOptionPane.showMessageDialog(frame, message, "ERROR", JOptionPane.ERROR_MESSAGE);
    }
}
