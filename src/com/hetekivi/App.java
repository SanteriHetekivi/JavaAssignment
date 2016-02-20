package com.hetekivi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by santeri on 2/15/16.
 */
public class App
{
    private final Object[] menuItems = {
            "Search",
            "Quit"
    };
    private String TITLE_MENU = "Menu";
    private String MESSAGE_MENU = "Select:";
    private final String TITLE_CITY = "City";
    private final String MESSAGE_CITY = "Give city for weather.";
    private JFrame frame;
    private JScrollPane panel;
    private final Listener listener = new Listener()
    {
        @Override
        public void Complete() {
            weather.Print();
            setRowsTable(weather.Rows());
            frame.setTitle(weather.CityName);
            weather = null;
        }

        @Override
        public void Failed() {
            weather.Errors.Print();
            weather = null;
        }
    };
    private final String API_ID = "44db6a862fba0b067b1930da0d769e98";
    private Weather weather = null;
    private Scanner reader;
    private Container container;
    private JTable table;
    private String[] columnNames;
    private DefaultTableModel model;
    public App()
    {
        columnNames = WeatherData.ColumnNames();
        weather = null;

        model = new DefaultTableModel(0, 0);
        table = new JTable();
        table.setModel(model);
        this.setColumns(columnNames);
        panel = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        frame = new JFrame();
        frame.setSize(1000,1000);
        container = frame.getContentPane();
        container.add(panel);
        frame.setVisible(true);
        Menu();
        System.exit(0);
    }

    private void Menu()
    {
        int selection;
        boolean running = true;
        while(running)
        {
            selection = this.getMenu();
            switch (selection)
            {
                case 0:
                    this.WeatherData();
                    break;
                case 1:
                    running = false;
                    break;
            }
        }
    }

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

    private void WeatherData()
    {
        if(this.weather == null)
        {
            String city = JOptionPane.showInputDialog(
                        frame,
                        MESSAGE_CITY,
                        TITLE_CITY,
                        JOptionPane.QUESTION_MESSAGE
            );
            this.weather = new Weather(city, API_ID, listener);
            this.weather.start();
        }
        else
        {
            System.err.println("There is already ongoing weather search!");
        }
    }

    private void setRowsTable(Vector<Object[]> rows)
    {
        this.model.setRowCount(0);
        for (Object[] row: rows)
        {
            model.addRow(row);
        }
    }

    private void setColumns(String[] columns)
    {
        for (String column: columns)
        {
            this.model.addColumn(column);
        }
    }
}
