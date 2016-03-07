package com.hetekivi;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.print.attribute.standard.MediaSize;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Santeri Hetekivi on 2/15/16.
 */

/**
 * Class for getting weather data.
 */
public class Weather extends ThreadRoot
{

    /**
     * Copy constructor.
     * @param other Weather class to copy.
     */
    public Weather(Weather other) {
        this.City(other.City());
        this.ApiId(other.ApiId());
        this.Listener(other.Listener());
    }

    /**
     * Cloning function.
     * @return Copy of current class.
     */
    public Weather Clone()
    {
        return new Weather(this);
    }

    /**
     * Initializer that sets FILE and CLASS variables
     */
    @Override
    protected void init() {
        super.init();
        this.FILE = "Weather.java";
        this.CLASS = "Weather";
    }

    /**
     * Listener for the web query
     * and it's setter and getter.
     */
    private Listener webListener = null;
    private Listener WebListener(){ return this.webListener; }
    private void WebListener(Listener listener){ this.webListener = listener; }

    /**
     * Web class variable to make url query.
     * and it's setter and getter.
     */
    private Web web = null;
    private Web Web() { return this.web; }
    private void Web(Web web){ this.web = web; }

    /**
     * City name for the weather query.
     * and it's setter and getter.
     */
    private String city;
    private String City() { return this.city.replace(" ", "%20"); }
    private void City(String city) { this.city = city; }

    /**
     * City name we get from the query.
     */
    public String CityName;

    /**
     * API ID for the weather query.
     * and it's setter and getter.
     */
    private String apiId;
    private String ApiId() { return this.apiId; }
    private void ApiId(String apiId) { this.apiId = apiId; }

    /**
     * Function to build the query url.
     * @return Url for the query.
     */
    private String Url(){ return "http://api.openweathermap.org/data/2.5/forecast?q=" + this.City() + "&mode=json&units=metric&appid=" + this.ApiId() ; }

    /**
     * Map of weather data.
     * and it's setter and getter.
     */
    private Map<Long, WeatherData> data = new LinkedHashMap<>();
    private Map<Long, WeatherData> Data() { return this.data; }
    private void Data(Map<Long, WeatherData> data) { this.data = data; }

    /**
     * Constructor for the class.
     * @param city City for the weather query,
     * @param apiId API ID for the weather query.
     */
    public Weather(String city, String apiId)
    {
        super.init();
        this.City(city);
        this.ApiId(apiId);
    }

    /**
     * Constructor for the class.
     * @param city City for the weather query,
     * @param apiId API ID for the weather query.
     * @param listener Listener for the thread.
     */
    public Weather(String city, String apiId, Listener listener)
    {
        this.City(city);
        this.ApiId(apiId);
        this.Listener(listener);
    }

    /**
     * Starter for the class.
     */
    @Override
    public synchronized void start() {
        super.start();
    }

    /**
     * Runner for the class.
     */
    @Override
    public void run()
    {
        super.run();
        this.getJson();
    }

    /**
     * Function for getting JSON data from the url.
     */
    private void getJson()
    {
        this.WebListener(new Listener() {
            @Override
            public void Complete() {
                makeData();
            }

            @Override
            public void Failed() {
                addErrors(Web().Errors);
                END();
            }
        });
        this.Web(new Web(this.Url(), this.WebListener()));
        this.Web().start();
    }

    /**
     * Function for making WeatherData out of JSON.
     */
    private void makeData()
    {
        final String FUNCTION = "makeData()";
        JsonObject json = this.Web().Json();
        Map<Long, WeatherData> d = new LinkedHashMap<>();
        if(json.has("message"))
        {
            String message = json.get("message").getAsString();
            if(message.contains("Error"))
            {
                this.addError(FUNCTION, message);
            }
        }
        if(json.has("city") && json.getAsJsonObject("city").has("name"))
        {
            this.CityName = json.getAsJsonObject("city").get("name").getAsString();
        }
        if(json.has("list")) {
            JsonArray list = json.getAsJsonArray("list");
            for (JsonElement je : list) {
                JsonObject jo = je.getAsJsonObject();
                if (jo.has("main")) {
                    JsonObject main = jo.getAsJsonObject("main");
                    if (jo.has("dt")) {
                        Long time = jo.get("dt").getAsLong();
                        WeatherData tmp = new WeatherData(time, main);
                        d.put(time, tmp);
                    }
                }
            }
        }
        if(d.isEmpty())
        {
            this.addError(FUNCTION, "Weather data empty!");
        }
        else
        {
            this.Data(d);
        }
        this.END();
    }

    /**
     * Function for printing data to console.
     */
    public void Print()
    {
        if(this.Data().isEmpty() == false)
        {
            for(Map.Entry<Long, WeatherData> entry : this.Data().entrySet())
            {
                entry.getValue().Print();
            }
        }
    }

    /**
     * Function to get rows of data.
     * @return Vector full of weather data arrays.
     */
    public Vector<Object[]> Rows()
    {
        Vector<Object[]> rows = new Vector<>();
        if(this.Data().isEmpty() == false)
        {
            rows.addAll(this.Data().entrySet().stream().map(entry -> entry.getValue().Row()).collect(Collectors.toList()));
        }
        return rows;
    }

}
