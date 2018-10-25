/*
* Name : Srishti Tiwari
* Name : Pawan Bole
*/
package uncc.com.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;


import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {



    Handler handler;
    ImageView imageView;

    ExecutorService service = Executors.newFixedThreadPool( 5 );
    ProgressBar progressBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        imageView = findViewById( R.id.imageView );
        setTitle( "Display Image" );
        final Button threadButton = findViewById( R.id.threadButton );
        final Button asyncButton  = findViewById( R.id.buttonAsync );
        progressBar = findViewById( R.id.progressBar );
        progressBar.setVisibility(View.INVISIBLE);

        //new Thread( new ThreadClass() ).start();
        handler = new Handler( new Handler.Callback(){

            @Override
            public boolean handleMessage(Message msg) {
                if(msg.obj!=null){
                    imageView.setImageBitmap( (Bitmap) msg.obj );
                    progressBar.setVisibility(View.INVISIBLE);
                }

                return false;
            }
        } );



        threadButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                service.execute( new ThreadClass() );
            }
        } );

        asyncButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imageView.setImageBitmap(  );

                new GetImageClass().execute( "https://cdn.pixabay.com/photo/2014/12/16/22/25/youth-570881_960_720.jpg " );
                progressBar.setVisibility( View.VISIBLE );
            }
        } );
    }


    class ThreadClass implements Runnable{
        @Override
        public void run() {
            HttpURLConnection connection = null;
            Bitmap bitmap = null;
            try {
                URL url = new URL("https://cdn.pixabay.com/photo/2017/12/31/06/16/boats-3051610_960_720.jpg");
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                    Message msg = new Message();
                    msg.obj = bitmap;
                    handler.sendMessage( msg );
                }

            } catch (Exception e) {
                //Handle the exceptions
                e.printStackTrace();
            } finally {
                //Close open connection
            }
        }
    }

    private class GetImageClass extends AsyncTask<String, Integer, Bitmap>{
        ImageView imageView;
        Bitmap bitmap = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // progressBar.setVisibility( View.VISIBLE );
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            HttpURLConnection connection = null;
            bitmap = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    Log.d("demo","received msg");
                    bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                }
            } catch (Exception e) {
                //Handle the exceptions
                e.printStackTrace();
            } finally {
                //Close open connection
            }
            return bitmap;

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            progressBar.setVisibility( View.INVISIBLE );
            Log.d("print obj", "Entered");
            imageView = findViewById( R.id.imageView );
            imageView.setImageBitmap(bitmap);


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate( values );
        }
    }


}
