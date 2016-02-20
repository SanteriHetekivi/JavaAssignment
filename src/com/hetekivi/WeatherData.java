package com.hetekivi;

import com.google.gson.JsonObject;

import java.util.Date;

/**
 * Created by santeri on 2/15/16.
 */
public class WeatherData
{
    public Double Temp = 0.00;
    public Double TempMin = 0.00;
    public Double TempMax = 0.00;
    public Double Pressure = 0.00;
    public Double PressureSea = 0.00;
    public Double PressureGround = 0.00;
    public Double Humidity = 0.00;

    private Long time;
    public Date Time()
    {
        return new Date(this.time*1000);
    }
    public WeatherData(Long time, JsonObject json)
    {
        this.time = time;
        String name = "temp";
        if (json.has(name)) this.Temp = json.get(name).getAsDouble();
        name = "temp_min";
        if (json.has(name)) this.TempMin = json.get(name).getAsDouble();
        name = "temp_max";
        if (json.has(name)) this.TempMax = json.get(name).getAsDouble();
        name = "pressure";
        if (json.has(name)) this.Pressure = json.get(name).getAsDouble();
        name = "sea_level";
        if (json.has(name)) this.PressureSea = json.get(name).getAsDouble();
        name = "grnd_level";
        if (json.has(name)) this.PressureGround = json.get(name).getAsDouble();
        name = "humidity";
        if (json.has(name)) this.Humidity = json.get(name).getAsDouble();
    }

    public void Print()
    {
        System.out.println(this.Time().toString());
        System.out.println("The temperature was "       + this.Temp);
        System.out.println("The max temperature was "   + this.TempMax);
        System.out.println("The min temperature was "   + this.TempMin);
        System.out.println("The pressure was "          + this.Pressure);
        System.out.println("The sea pressure was "      + this.PressureSea);
        System.out.println("The ground pressure was "   + this.PressureGround);
        System.out.println("");
    }

    public Object[] Row()
    {
        Object[] row = {
                this.Time().toString(), this.Temp, this.TempMax, this.TempMin,
                this.Pressure, this.PressureSea, this.PressureGround
        };
        return row;
    }
    public static String[] ColumnNames()
    {
        String[] columnNames = {"Temperature", "Max Temperature",  "Min Temperature",
                "Pressure", "Sea Pressure", "Ground Pressure"};
        return columnNames;
    }
}
