/*
a. Assignment # : Homework 04
b. File Name : 801091205_HW04.zip
c. Name : Pawan Ramesh Bole.
          Srishti Tiwari
d. Group Number : 31
*/

package com.uncc.homework04;


import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KeyWords extends AsyncTask<String, Void, List<String>> {

    MainActivity currentActivity;


    final String api = "http://dev.theappsdr.com/apis/photos/keywords.php";

    public KeyWords(MainActivity currentActivity) {
        this.currentActivity = currentActivity;
    }

    @Override
    protected List<String> doInBackground(String... strings) {

        BufferedReader reader = null;

        try {

            URL url = new URL(api );
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            List<String> list = new ArrayList<String >();
            String data = "";
            while ((data = reader.readLine()) != null) {

                String[] str = data.split(";");

                list = Arrays.asList(str);
            }
            return list;
        } catch (Exception ex) {

        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    @Override
    protected void onPostExecute(List<String> keywords) {

        currentActivity.getKeywords(keywords);

    }

}
