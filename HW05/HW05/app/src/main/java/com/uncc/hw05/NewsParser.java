/*
a. Assignment # : Homework 05
b. File Name : Group31_HW05.zip
c. Name : Pawan Ramesh Bole.
          Srishti Tiwari
*/

package com.uncc.hw05;

import android.util.MalformedJsonException;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class NewsParser {
    static  ArrayList<News> newsList = null;
    static  ArrayList<News> parseSources(InputStream in) throws MalformedJsonException, IOException
    {
        try {
            newsList = new ArrayList<News>();
            String json = IOUtils.toString(in, "UTF8");
            JSONObject root = new JSONObject(json);
            JSONArray sourcesArray = root.getJSONArray("articles");
            for (int i=0;i<sourcesArray.length();i++)
            {
                JSONObject sourceJson = sourcesArray.getJSONObject(i);
                News news = new News();
                news.title = sourceJson.getString("title");
                news.urlToImage = sourceJson.getString("urlToImage");
                news.author = sourceJson.getString("author");
                news.url = sourceJson.getString("url");
                news.publishedAt =new SimpleDateFormat("yyyy-MM-dd").parse( sourceJson.getString("publishedAt"));
                newsList.add(news);
            }
        }
        catch (Exception e)
        {

        }
        finally {

        }
        return newsList;
    }

}
