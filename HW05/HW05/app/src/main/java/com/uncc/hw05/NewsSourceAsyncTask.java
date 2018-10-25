/*
a. Assignment # : Homework 05
b. File Name : Group31_HW05.zip
c. Name : Pawan Ramesh Bole.
          Srishti Tiwari
*/

package com.uncc.hw05;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NewsSourceAsyncTask extends AsyncTask<String, Void, ArrayList<Source>> {

    MainActivity currentActivity;

    public NewsSourceAsyncTask(MainActivity currentActivity){
        this.currentActivity = currentActivity;
    }

    @Override
    protected void onPostExecute(ArrayList<Source> sources) {
        super.onPostExecute(sources);
        currentActivity.populateSourceData(sources);
    }

    @Override
    protected ArrayList<Source> doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode= con.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK)
            {
                InputStream in = con.getInputStream();
                return SourceParser.parseSources(in);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
