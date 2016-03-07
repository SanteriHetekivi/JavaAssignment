package com.hetekivi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

/**
 * Created by Santeri Hetekivi on 2/15/16.
 */

/**
 * Class to handle UI for the application.
 */
public class App
{
    // Final values for UI
    private final Object[] menuItems = {
            "Set City",
            "Quit"
    };
    private final String TITLE_MENU = "Menu";
    private final String MESSAGE_MENU = "Select:";
    private final String TITLE_CITY = "City";
    private final String MESSAGE_CITY = "Give city for weather.";

    private final String API_ID = "44db6a862fba0b067b1930da0d769e98";   // API ID for OpenWeatherMap

    // UI variables
    private JFrame frame;
    private DefaultTableModel model;

    private Weather weather = null; // Variable for Weather class

    // Listener for the Weather class
    private final Listener listener = new Listener()
    {
        // When all is done
        @Override
        public void Complete()
        {
            weather = (Weather) timedRunner.Runnable();
            weather.Print();                    // Printing weather data to console.
            setRowsTable(weather.Rows());       // Setting table rows to weather data rows.
            frame.setTitle(weather.CityName);   // Setting Frame title to weather city.
            weather.Finished(true);
        }

        //If something failed
        @Override
        public void Failed()
        {
            weather = (Weather) timedRunner.Runnable();
            weather.Errors.Print();             // Printing errors to console.
            weather.Errors.Show(frame);         // Showing error dialog in frame.
            weather = null;                     // Setting weather variable to null this allows new search
            timedRunner.Runnable(weather);
        }
    };

    // TimedRunner for running timed calls
    private TimedRunner timedRunner;
    // Amount of seconds that timedRunner sleeps
    private final int SECONDS = 60;
    /**
     * Initialiser for Class
     * Initialising all variables for UI and data.
     */
    public App()
    {
        String[] columnNames    = WeatherData.ColumnNames();    // Getting column names for JTable.
        this.weather            = null;                         // Initialising weather class to null.
        model                   = new DefaultTableModel(0, 0);  // Initialising model for the table.
        JTable table            = new JTable();                 // Initialising the table for weather data.
        JScrollPane panel       = new JScrollPane(table);       // Initialising panel for the container.
        frame                   = new JFrame();                 // Initialising frame as JFrame.
        Container container     = frame.getContentPane();       // Initialising container from frame.

        table.setModel(model);
        this.setColumns(columnNames);
        table.setFillsViewportHeight(true);
        frame.setSize(1000,1000);
        container.add(panel);
        frame.setVisible(true);
        this.timedRunner = new TimedRunner();
        this.timedRunner.Time(this.SECONDS);
    }

    public void Start()
    {
        Menu(); // Running the menu.
    }

    /**
     * Handling the menu screen.
     * Returning when user has selected the exit value.
     */
    private void Menu()
    {
        int selection;              // Variable for saving user selection.
        boolean running = true;     // Variable for running state.
        // Looping while menu is running.
        while(running)
        {
            selection = this.getMenu(); // Getting menu selection.
            // Handling selection.
            switch (selection)
            {
                // This case is for getting weather data.
                case 0:
                    this.WeatherData();
                    break;
                // This case is for exit.
                case 1:
                    running = false;    // Stopping the run of the menu.
                    break;
            }
        }
    }

    /**
     * Printing menu and returning selection.
     * @return Integer representing menu selection.
     */
    private int getMenu()
    {
        int n = JOptionPane.showOptionDialog(this.frame,
                this.MESSAGE_MENU,
                this.TITLE_MENU,
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                this.menuItems,
                menuItems[1]);
        return n;
    }

    /**
     * Running weather data search.
     */
    private void WeatherData()
    {
        // Getting city name from user.
        String city = JOptionPane.showInputDialog(
                 frame,
                 MESSAGE_CITY,
                 TITLE_CITY,
                 JOptionPane.QUESTION_MESSAGE
        );
        this.weather = new Weather(city, API_ID, listener); // Initialising Weather class with data.
        this.timedRunner.Runnable(this.weather);
        if(this.timedRunner.getState() == Thread.State.NEW) this.timedRunner.start();  // Start the timed runner.
        else this.timedRunner.Runnable().start();   // Or if runner is already running start runnable.
        /*// There is weather search running.
        else
        {
            // Showing error to user.
            final String title = "ERROR";
            final String message = "There is already ongoing weather search!";
            JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);    // Error dialog.
            System.err.println(message);                                                        // Console message.
        }*/
    }

    /**
     * Setting given rows to table model.
     * @param rows Weather data rows.
     */
    private void setRowsTable(Vector<Object[]> rows)
    {
        this.model.setRowCount(0);  // Removing all rows.
        // Adding rows to model one by one.
        for (Object[] row: rows)
        {
            model.addRow(row);
        }
    }

    /**
     * Setting given column to table model.
     * @param columns Weather data columns.
     */
    private void setColumns(String[] columns)
    {
        this.model.setColumnCount(0);   // Removing all column.
        // Adding columns to model one by one.
        for (String column: columns)
        {
            this.model.addColumn(column);
        }
    }
}
