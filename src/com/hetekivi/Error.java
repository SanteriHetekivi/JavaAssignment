package com.hetekivi;

/**
 * Created by Santeri Hetekivi on 2/15/16.
 */

import javax.swing.*;

/**
 * Class for application Errors.
 */
public class Error
{
    // Variables to store error data.
    String File;
    String Class;
    String Function;
    String Message;

    /**
     * Making the Error with given data.
     * @param _file File where error happened.
     * @param _class Class where error happened.
     * @param _function Function where error happened.
     * @param _message  Message where error happened.
     */
    public Error(String _file, String _class, String _function, String _message)
    {
        this.File = _file;
        this.Class = _class;
        this.Function = _function;
        this.Message = _message;
    }

    /**
     * Printing the error data to console.
     */
    public void Print()
    {
        System.err.println("-----ERROR------");
        System.err.println("File:     " + this.File);
        System.err.println("Class:    " + this.Class);
        System.err.println("Function: " + this.Function);
        System.err.println("Message:  " + this.Message);
        System.err.println("-----ERROR-------");
    }

    /**
     * Showing error dialog in given frame.
     * @param frame Frame to show dialog in.
     */
    public void Show(JFrame frame)
    {
        JOptionPane.showMessageDialog(frame, this.Message, "ERROR", JOptionPane.ERROR_MESSAGE);
    }
}
