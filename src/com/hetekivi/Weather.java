package com.hetekivi;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.*;

/**
 * Created by santeri on 2/15/16.
 */
public class Weather extends ThreadRoot
{

    @Override
    protected void init() {
        super.init();
        this.FILE = "Weather.java";
        this.CLASS = "Weather";
    }

    private Listener webListener = null;
    private Listener WebListener(){ return this.webListener; }
    private void WebListener(Listener listener){ this.webListener = listener; }

    private Web web = null;
    private Web Web() { return this.web; }
    private void Web(Web web){ this.web = web; }

    private String city;
    private String City() { return this.city.replace(" ", "%20"); }
    private void City(String city) { this.city = city; }

    public String CityName;

    private String apiId;
    private String ApiId() { return this.apiId; }
    private void ApiId(String apiId) { this.apiId = apiId; }

    private String Url(){ return "http://api.openweathermap.org/data/2.5/forecast?q=" + this.City() + "&mode=json&units=metric&appid=" + this.ApiId() ; }

    private Map<Long, WeatherData> data = new LinkedHashMap<>();
    private Map<Long, WeatherData> Data() { return this.data; }
    private void Data(Map<Long, WeatherData> data) { this.data = data; }

    public Weather(String city, String apiId)
    {
        super.init();
        this.City(city);
        this.ApiId(apiId);
    }

    public Weather(String city, String apiId, Listener listener)
    {
        this.City(city);
        this.ApiId(apiId);
        this.Listener(listener);
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public void run()
    {
        super.run();
        this.getJson();
    }

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

    public Vector<Object[]> Rows()
    {
        Vector<Object[]> rows = new Vector<>();
        if(this.Data().isEmpty() == false)
        {
            for(Map.Entry<Long, WeatherData> entry : this.Data().entrySet())
            {
                rows.add(entry.getValue().Row());
            }
        }
        return rows;
    }

}
