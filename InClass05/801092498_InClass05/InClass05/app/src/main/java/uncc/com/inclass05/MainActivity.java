/*
* Assignment # : InClass05
* File Name : 801092498_InClass05.zip
* Name : Srishti Tiwari
*/
package uncc.com.inclass05;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewsTask.IGetData {


    TextView textViewTitle,textViewDate,textViewDescriptionData;
    ImageView imageView,imageViewPrev,imageViewNext;
    Button buttonQuit;
    ProgressBar progressBarLoadData;
    int currentNews=0;
    List<News> newsList = null;
    AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        setTitle( "Buzzfeed Headlines" );

        textViewDate = findViewById( R.id.textViewDate );
        textViewTitle = findViewById( R.id.textTitle );
        textViewDescriptionData = findViewById( R.id.textViewDescriptionData );
        imageView =  findViewById( R.id.imageView );
        imageViewPrev = findViewById( R.id.imageViewPrev );
        imageViewNext = findViewById( R.id.imageViewNext );

        buttonQuit = findViewById( R.id.buttonQuit );
        progressBarLoadData = findViewById( R.id.progressBarLoadData );

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setTitle("Loading").setView(inflater.inflate(R.layout.dialog_bar, null));
        dialog = builder.create();
        dialog.show();

        imageViewNext.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentNews == newsList.size()-1){
                    Toast.makeText( MainActivity.this, "This is end of news!", Toast.LENGTH_LONG ).show();
                }else{
                    currentNews++;
                    populateData( newsList );
                }
            }
        } );

        imageViewPrev.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentNews==0){
                    Toast.makeText( MainActivity.this, "You are on the first news!", Toast.LENGTH_LONG ).show();
                }else{
                    currentNews--;
                    populateData( newsList );
                }
            }
        } );

        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        new NewsTask(MainActivity.this).execute("https://newsapi.org/v2/top-headlines?sources=buzzfeed&apiKey=e5ec77a5057b4da0b716e3ff08d1b391");
    }


    @Override
    public void populateData(List<News> newsList) {
        this.newsList = newsList;
        News news = newsList.get(currentNews);
        textViewTitle.setText(news.title);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String sCertDate = dateFormat.format(news.publishedAt);
        textViewDate.setText(sCertDate);
        Picasso.get().load(news.urlToImage).into(imageView);
        textViewDescriptionData.setText(news.description);
        dialog.hide();
        progressBarLoadData.setVisibility(View.INVISIBLE);
    }
}
