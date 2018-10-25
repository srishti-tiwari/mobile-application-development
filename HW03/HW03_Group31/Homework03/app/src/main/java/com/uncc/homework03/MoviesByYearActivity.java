/* Group 31 - Homework 03
Name:
Pawan Bole
Srishti Tiwari
*/

package com.uncc.homework03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MoviesByYearActivity extends AppCompatActivity {


    Button buttonFinish;
    Movie firstMovie;
    ImageView nextItem, prevItem, firstItem, lastItem;
    List<Movie> movieList = new ArrayList<>();
    int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_by_year);
        setTitle("Movies By Rating");

        final TextView textViewMBRTitleValue = findViewById(R.id.textViewMBRTitleValue);
        final EditText editTextMBRDescriptionValue = findViewById(R.id.editTextMBRDescriptionValue);
        final TextView textViewMBRGenreValue = findViewById(R.id.textViewMBRGenreValue);
        final TextView textViewMBRRatingValue = findViewById(R.id.textViewMBRRatingValue);
        final TextView textViewMBRYearValue = findViewById(R.id.textViewMBRYearValue);
        final TextView textViewMBRImdbValue = findViewById(R.id.textViewMBRImdbValue);
        buttonFinish = findViewById(R.id.buttonMBRFinish);
        firstItem = findViewById(R.id.imageViewMBRFirst);
        lastItem = findViewById(R.id.imageViewMBRLast);
        prevItem = findViewById(R.id.imageViewMBRPrev);
        nextItem = findViewById(R.id.imageViewMBRNext);

        movieList = (ArrayList<Movie>)getIntent().getExtras().getSerializable("movieListByYear");
        int size = movieList.size();
        firstMovie = movieList.get(0);

        textViewMBRTitleValue.setText(firstMovie.getName().toString());
        editTextMBRDescriptionValue.setText(firstMovie.getDescription().toString());
        textViewMBRGenreValue.setText(firstMovie.getGenre().toString());
        textViewMBRRatingValue.setText(firstMovie.getRating().toString()+ " / 5");
        textViewMBRYearValue.setText(firstMovie.getYear().toString());
        textViewMBRImdbValue.setText(firstMovie.getImdb().toString());

        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int resultCode=1;
                Intent i = new Intent();
                setResult(resultCode, i);
                finish();

            }
        });

        firstItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex=0;
                firstMovie=movieList.get(currentIndex);

                textViewMBRTitleValue.setText(firstMovie.getName().toString());
                editTextMBRDescriptionValue.setText(firstMovie.getDescription().toString());
                textViewMBRGenreValue.setText(firstMovie.getGenre().toString());
                textViewMBRRatingValue.setText(""+firstMovie.getRating().toString() + " / 5");
                textViewMBRYearValue.setText(""+firstMovie.getYear().toString());
                textViewMBRImdbValue.setText(firstMovie.getImdb().toString());

                Toast.makeText(MoviesByYearActivity.this, "This is the first movie in the list.", Toast.LENGTH_SHORT).show();

            }
        });

        lastItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex=movieList.size()-1;
                firstMovie=movieList.get(currentIndex);

                textViewMBRTitleValue.setText(firstMovie.getName().toString());
                editTextMBRDescriptionValue.setText(firstMovie.getDescription().toString());
                textViewMBRGenreValue.setText(firstMovie.getGenre().toString());
                textViewMBRRatingValue.setText(""+firstMovie.getRating().toString() + " / 5");
                textViewMBRYearValue.setText(""+firstMovie.getYear().toString());
                textViewMBRImdbValue.setText(firstMovie.getImdb().toString());

                Toast.makeText(MoviesByYearActivity.this, "This is the last movie in the list.", Toast.LENGTH_SHORT).show();
            }
        });

        prevItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIndex>0){
                    currentIndex=currentIndex-1;
                    firstMovie=movieList.get(currentIndex);
                    textViewMBRTitleValue.setText(firstMovie.getName().toString());
                    editTextMBRDescriptionValue.setText(firstMovie.getDescription().toString());
                    textViewMBRGenreValue.setText(firstMovie.getGenre().toString());
                    textViewMBRRatingValue.setText(""+firstMovie.getRating().toString() + " / 5");
                    textViewMBRYearValue.setText(""+firstMovie.getYear().toString());
                    textViewMBRImdbValue.setText(firstMovie.getImdb().toString());

                }
                if(currentIndex==0)
                    Toast.makeText(MoviesByYearActivity.this, "This is the first movie in the list.", Toast.LENGTH_SHORT).show();

            }
        });

        nextItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIndex<movieList.size()-1){
                    currentIndex=currentIndex+1;
                    firstMovie=movieList.get(currentIndex);

                    textViewMBRTitleValue.setText(firstMovie.getName().toString());
                    editTextMBRDescriptionValue.setText(firstMovie.getDescription().toString());
                    textViewMBRGenreValue.setText(firstMovie.getGenre().toString());
                    textViewMBRRatingValue.setText(""+firstMovie.getRating().toString() + " / 5");
                    textViewMBRYearValue.setText(""+firstMovie.getYear().toString());
                    textViewMBRImdbValue.setText(firstMovie.getImdb().toString());

                }
                if (currentIndex==movieList.size()-1)
                    Toast.makeText(MoviesByYearActivity.this, "This is the last movie in the list.", Toast.LENGTH_SHORT).show();


            }
        });

    }
}