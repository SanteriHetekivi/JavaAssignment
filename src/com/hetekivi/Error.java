package com.hetekivi;

/**
 * Created by santeri on 2/15/16.
 */
public class Error
{
    String File;
    String Class;
    String Function;
    String Message;

    public Error(String _file, String _class, String _function, String _message)
    {
        this.File = _file;
        this.Class = _class;
        this.Function = _function;
        this.Message = _message;
    }

    public void Print()
    {
        System.err.println("-----ERROR------");
        System.err.println("File:     " + this.File);
        System.err.println("Class:    " + this.Class);
        System.err.println("Function: " + this.Function);
        System.err.println("Message:  " + this.Message);
        System.err.println("-----ERROR-------");
    }
}
