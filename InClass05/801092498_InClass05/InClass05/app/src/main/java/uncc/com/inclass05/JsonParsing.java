/*
 * Assignment # : InClass05
 * File Name : 801092498_InClass05.zip
 * Name : Srishti Tiwari
 */
package uncc.com.inclass05;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

class JsonParsing {

    public static List<News> parseNews(InputStream in) {
        ArrayList<News> result = new ArrayList<>();
        try {
            String json = IOUtils.toString( in, "UTF8" );
            JSONObject root = new JSONObject( json );
            JSONArray newsList = root.getJSONArray( "articles" );
            for (int i = 0; i < newsList.length(); i++) {
                JSONObject newsJson = newsList.getJSONObject( i );
                News news = new News();
                news.description = newsJson.getString( "description" );
                news.title = newsJson.getString( "title" );
                news.urlToImage = newsJson.getString( "urlToImage" );
                String dateStr = newsJson.getString( "publishedAt" );
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                news.publishedAt = sdf.parse(dateStr);

                result.add( news );

            }
        }catch(Exception e){

        }

        return result;
    }
}
