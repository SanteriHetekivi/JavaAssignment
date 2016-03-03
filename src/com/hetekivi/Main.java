package com.hetekivi;
/**
 * Created by Santeri Hetekivi on 2/15/16.
 */

/**
 * Main for application.
 */
public class Main {
    /**
     * Main runner.
     * @param args Command arguments.
     */
    public static void main(String[] args)
    {
        App app = new App();    // Initialising app.
        app.Start();            // And starting it.
        System.exit(0);         // Exiting from application.
    }
}
