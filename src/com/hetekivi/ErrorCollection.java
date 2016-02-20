package com.hetekivi;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by santeri on 2/15/16.
 */
public class ErrorCollection
{
    private Collection<Error> errors = new LinkedList<>();
    private Collection<Error> Errors(){ return this.errors; }
    private void Errors(Collection<Error> errors) { this.errors = errors; }

    public void Add(Error error) { this.errors.add(error); }
    public void Add(ErrorCollection errorCollection)
    {
        if(errorCollection.HasErrors())
        {
            this.errors.addAll(errorCollection.Errors());
        }
    }
    public boolean Remove(Error error)
    {
        if(this.errors.contains(error))
        {
            return this.errors.remove(error);
        }
        return false;
    }

    public boolean HasErrors() { return !this.Errors().isEmpty(); }

    public void Print()
    {
        this.Errors().forEach(Error::Print);
    }
}
