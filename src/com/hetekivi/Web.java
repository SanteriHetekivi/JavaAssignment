package com.hetekivi;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by santeri on 2/15/16.
 */
public class Web extends ThreadRoot
{
    @Override
    protected void init() {
        super.init();
        this.FILE = "Web.java";
        this.CLASS = "Web";
    }

    protected final String UTF8 = "UTF8";

    private URL url;
    private URL Url() { return this.url; }
    private void Url(String url) throws MalformedURLException { this.url = new URL(url); }
    private void Url(URL url){ this.url = url; }
    private String UrlString() { return this.url.toString(); }

    private Listener listener = null;
    public Listener Listener(){ return this.listener; }
    public void Listener(Listener listener){ this.listener = listener; }

    private String data;
    private String Data(){ return this.data; }
    private void Data(String data){ this.data = data; }

    public JsonObject Json()
    {
        return new JsonParser().parse(this.Data()).getAsJsonObject();
    }

    private String encoding = this.UTF8;
    private String Encoding(){ return this.encoding; }
    private void Encoding(String encoding){ this.encoding = encoding; }



    public Web(String url)
    {
        final String FUNCTION = "Web(String url)";
        try {
            this.Url(url);
        } catch (MalformedURLException e) {
            this.addError(FUNCTION, e.getLocalizedMessage());
        }
    }

    public Web(String url, String encoding)
    {
        final String FUNCTION = "Web(String url, String encoding)";
        try {
            this.Url(url);
        } catch (MalformedURLException e) {
            this.addError(FUNCTION, e.getLocalizedMessage());
        }
        this.Encoding(encoding);
    }

    public Web(String url, Listener listener)
    {
        final String FUNCTION = "Web(String url, WebListener listener)";
        try {
            this.Url(url);
        } catch (MalformedURLException e) {
            this.addError(FUNCTION, e.getLocalizedMessage());
        }
        this.Listener(listener);
    }

    public Web(String url, Listener listener, String encoding)
    {
        final String FUNCTION = "Web(String url, WebListener listener, String encoding)";
        try {
            this.Url(url);
        } catch (MalformedURLException e)
        {
            this.addError(FUNCTION, e.getLocalizedMessage());
        }
        this.Listener(listener);
        this.Encoding(encoding);
    }

    @Override
    public synchronized void start()
    {
        super.start();
    }

    @Override
    public void run()
    {
        final String FUNCTION = "run()";
        super.run();
        String result = "";
        try (InputStream is = this.Url().openStream())
        {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            result = readToString(rd);
            is.close();
        } catch (IOException e)
        {
            this.addError(FUNCTION, e.getLocalizedMessage());
        }
        if(this.Errors.HasErrors() == false && result.isEmpty() == false)
        {
            this.Data(result);
        }
        this.END();
    }

    private String readToString(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();

        int cp;
        while((cp = rd.read()) != -1) {
            sb.append((char)cp);
        }

        return sb.toString();
    }
}
