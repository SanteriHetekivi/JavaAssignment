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

    protected void init()
    {
        this.FILE = "";
        this.CLASS = "";
    }

    public ThreadRoot()
    {
        init();
    }

    public ErrorCollection Errors = new ErrorCollection();

    private Listener listener = null;
    public Listener Listener(){ return this.listener; }
    public void Listener(Listener listener){ this.listener = listener; }

    protected void END()
    {
        if(this.Listener() != null)
        {
            if(this.Errors.HasErrors()) this.Listener().Failed();
            else this.Listener().Complete();
        }
    }

    protected void addError(String function, String message)
    {
        this.Errors.Add(new Error(this.FILE, this.CLASS, function, message));
    }

    protected void addErrors(ErrorCollection errors)
    {
        this.Errors.Add(errors);
    }
}
