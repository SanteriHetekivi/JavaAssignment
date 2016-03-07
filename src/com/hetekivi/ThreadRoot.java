package com.hetekivi;

/**
 * Created by Santeri Hetekivi on 2/15/16.
 */

/**
 * Root class for all Thread classes.
 */
public class ThreadRoot extends Thread
{

    protected String FILE;
    protected String CLASS;

    public ErrorCollection Errors = new ErrorCollection();

    private Listener listener = null;
    public Listener Listener(){ return this.listener; }
    public void Listener(Listener listener){ this.listener = listener; }

    private boolean finished = false;
    public boolean Finished(){ return this.finished; }
    public void Finished(boolean finished) { this.finished = finished; }

    /**
     * Copy constructor for the class.
     * @param other Other ThreadRoot object where to copy
     */
    protected ThreadRoot(ThreadRoot other)
    {
        this.FILE = other.FILE;
        this.CLASS = other.CLASS;
        this.Listener(other.Listener());
    }

    /**
     * Clone function for the class.
     * @return Clone of object.
     */
    public ThreadRoot Clone()
    {
        return new ThreadRoot(this);
    }

    /**
     * Initializer that sets FILE and CLASS variables
     */
    protected void init()
    {
        this.FILE = "";
        this.CLASS = "";
    }

    /**
     * Constructor for the class that calls init function
     */
    public ThreadRoot()
    {
        init();
    }

    /**
     * Function to call when tread ends.
     * Tells Listener what happened.
     */
    protected void END()
    {
        if(this.Listener() != null)
        {
            if(this.Errors.HasErrors()) this.Listener().Failed();
            else this.Listener().Complete();
        }
    }

    /**
     * Adding new error.
     * @param function Function where error happened.
     * @param message Message that includes info about error.
     */
    protected void addError(String function, String message)
    {
        this.Errors.Add(new Error(this.FILE, this.CLASS, function, message));
    }

    /**
     * Adding collection of errors
     * @param errors Collection of errors that will be added.
     */
    protected void addErrors(ErrorCollection errors)
    {
        this.Errors.Add(errors);
    }
}
