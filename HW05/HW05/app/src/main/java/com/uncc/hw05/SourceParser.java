/*
a. Assignment # : Homework 05
b. File Name : Group31_HW05.zip
c. Name : Pawan Ramesh Bole.
          Srishti Tiwari
*/

package com.uncc.hw05;

import android.util.MalformedJsonException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.apache.commons.io.IOUtils;

public class SourceParser {
    static ArrayList<Source> sourceList = new ArrayList<Source>();

    static ArrayList<Source> parseSources(InputStream in) throws MalformedJsonException, IOException
    {
        try {
            String json = IOUtils.toString(in, "UTF8");
            JSONObject root = new JSONObject(json);
            JSONArray sourcesArray = root.getJSONArray("sources");
            for (int i=0;i<sourcesArray.length();i++)
            {
                JSONObject sourceJson = sourcesArray.getJSONObject(i);
                Source source = new Source();
                source.id = sourceJson.getString("id");
                source.name = sourceJson.getString("name");
                sourceList.add(source);
            }
        }
        catch (Exception e)
        {

        }
        finally {

        }
        return sourceList;
    }
}
