/*
a. Assignment # : Homework 04
b. File Name : 801091205_HW04.zip
c. Name : Pawan Ramesh Bole.
          Srishti Tiwari
d. Group Number : 31
*/


package com.uncc.homework04;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;

public class Images extends AsyncTask<String, Void, Bitmap> {

    MainActivity currentActivity;


    public Images(MainActivity currentActivity) {
        this.currentActivity = currentActivity;
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        try
        {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            Bitmap image = BitmapFactory.decodeStream(con.getInputStream());
            return image;
        }
        catch (Exception ex)
        {

        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        currentActivity.progressDialogImageLoading.show();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        currentActivity.progressDialogImageLoading.dismiss();
        currentActivity.imageViewPhotos.setImageBitmap(bitmap);
    }
}