/*
 * Assignment # : InClass05
 * File Name : 801092498_InClass05.zip
 * Name : Srishti Tiwari
 */
package uncc.com.inclass05;

import android.os.AsyncTask;
import android.view.View;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class NewsTask extends AsyncTask<String, Void, List<News>> {
    MainActivity currentActivity;
    public NewsTask(MainActivity currentActivity) {
        this.currentActivity = currentActivity;
    }

    @Override
    protected void onPreExecute() {
        currentActivity.progressBarLoadData.setVisibility( View.VISIBLE );
    }

    @Override
    protected void onPostExecute(List<News> newsList) {

        currentActivity.populateData( newsList );
    }

    @Override
    protected List<News> doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode= con.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK)
            {
                InputStream in = con.getInputStream();
                return JsonParsing.parseNews(in);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    public interface IGetData{
        void populateData(List<News> arrayList);
    }
}