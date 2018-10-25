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

public class ImagesInfo extends AsyncTask<String, Void, String> {


    final String api = "http://dev.theappsdr.com/apis/photos/index.php?keyword=";

    MainActivity currentActivity;

    public ImagesInfo(MainActivity currentActivity) {
        this.currentActivity = currentActivity;
    }

    @Override
    protected String doInBackground(String... params) {

        BufferedReader reader = null;

        try {

            URL url = new URL(api + params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            //String data = "";
            while (reader.readLine() != null) {
                sb.append(reader.readLine());
                sb.append(";");

            }
            return sb.toString();
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
    protected void onPreExecute() {
        super.onPreExecute();
        currentActivity.progressDialogLoading.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        currentActivity.progressDialogLoading.dismiss();
        currentActivity.getImagesFromDictionary(s);
    }

}
