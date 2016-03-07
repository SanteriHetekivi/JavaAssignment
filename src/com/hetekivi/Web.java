package com.hetekivi;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Santeri Hetekivi on 2/15/16.
 */

/**
 * Thread class for accessing the Web.
 */
public class Web extends ThreadRoot
{
    /**
     * Initializer for the FILE and CLASS.
     */
    @Override
    protected void init() {
        super.init();
        this.FILE = "Web.java";
        this.CLASS = "Web";
    }

    protected final String UTF8 = "UTF8";   // Encoding of the data.

    /**
     * Url where to connect
     * and different type of setters and getters.
     */
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

    /**
     * Function to get Json from the data.
     * @return Json parsed JsonObject from the data String.
     */
    public JsonObject Json()
    {
        return new JsonParser().parse(this.Data()).getAsJsonObject();
    }

    private String encoding = this.UTF8;
    private String Encoding(){ return this.encoding; }
    private void Encoding(String encoding){ this.encoding = encoding; }

    /**
     * Constructor
     * @param url Url String to connect to.
     */
    public Web(String url)
    {
        final String FUNCTION = "Web(String url)";
        try {
            this.Url(url);
        } catch (MalformedURLException e) {
            this.addError(FUNCTION, e.getLocalizedMessage());
        }
    }

    /**
     * Constructor
     * @param url Url String to connect to.
     * @param encoding Site encoding to use.
     */
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

    /**
     * Constructor
     * @param url Url String to connect to.
     * @param listener Listener to attach and listen.
     */
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

    /**
     * Constructor
     * @param url Url String to connect to.
     * @param listener Listener to attach and listen.
     * @param encoding Site encoding to use.
     */
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

    /**
     * Function to read reader to string.
     * @param rd Reader where reading are doing.
     * @return String of the content of the reader.
     * @throws IOException Reading trow exception.
     */
    private String readToString(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();

        int cp;
        while((cp = rd.read()) != -1) {
            sb.append((char)cp);
        }

        return sb.toString();
    }
}
