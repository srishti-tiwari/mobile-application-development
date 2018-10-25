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

public class MoviesByRatingActivity extends AppCompatActivity {


    Button buttonFinish;
    Movie firstMovie;
    ImageView nextItem, prevItem, firstItem, lastItem;
    List<Movie> movieList = new ArrayList<>();
    int currentIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_by_rating);
        setTitle("Movies By Rating");

        final TextView textViewMBYTitleValue = findViewById(R.id.textViewMBYTitleValue);
        final EditText editTextMBYDescriptionValue = findViewById(R.id.editTextMBYDescriptionValue);
        final TextView textViewMBYGenreValue = findViewById(R.id.textViewMBYGenreValue);
        final TextView textViewMBYRatingValue = findViewById(R.id.textViewMBYRatingValue);
        final TextView textViewMBYYearValue = findViewById(R.id.textViewMBYYearValue);
        final TextView textViewMBYImdbValue = findViewById(R.id.textViewMBYImdbValue);
        buttonFinish = findViewById(R.id.buttonMBYFinish);
        firstItem = findViewById(R.id.imageViewMBYFirst);
        lastItem = findViewById(R.id.imageViewMBYLast);
        prevItem = findViewById(R.id.imageViewMBYPrev);
        nextItem = findViewById(R.id.imageViewMBYNext);

        movieList = (ArrayList<Movie>)getIntent().getExtras().getSerializable("movieListByRating");
        int size = movieList.size();
        firstMovie = movieList.get(0);

        textViewMBYTitleValue.setText(firstMovie.getName().toString());
        editTextMBYDescriptionValue.setText(firstMovie.getDescription().toString());
        textViewMBYGenreValue.setText(firstMovie.getGenre().toString());
        textViewMBYRatingValue.setText(firstMovie.getRating().toString()+ " / 5");
        textViewMBYYearValue.setText(firstMovie.getYear().toString());
        textViewMBYImdbValue.setText(firstMovie.getImdb().toString());

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

                textViewMBYTitleValue.setText(firstMovie.getName().toString());
                editTextMBYDescriptionValue.setText(firstMovie.getDescription().toString());
                textViewMBYGenreValue.setText(firstMovie.getGenre().toString());
                textViewMBYRatingValue.setText(""+firstMovie.getRating().toString() + " / 5");
                textViewMBYYearValue.setText(""+firstMovie.getYear().toString());
                textViewMBYImdbValue.setText(firstMovie.getImdb().toString());

                Toast.makeText(MoviesByRatingActivity.this, "This is the first movie in the list.", Toast.LENGTH_SHORT).show();

            }
        });

        lastItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentIndex=movieList.size()-1;
                firstMovie=movieList.get(currentIndex);

                textViewMBYTitleValue.setText(firstMovie.getName().toString());
                editTextMBYDescriptionValue.setText(firstMovie.getDescription().toString());
                textViewMBYGenreValue.setText(firstMovie.getGenre().toString());
                textViewMBYRatingValue.setText(""+firstMovie.getRating().toString() + " / 5");
                textViewMBYYearValue.setText(""+firstMovie.getYear().toString());
                textViewMBYImdbValue.setText(firstMovie.getImdb().toString());

                Toast.makeText(MoviesByRatingActivity.this, "This is the last movie in the list.", Toast.LENGTH_SHORT).show();
            }
        });

        prevItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIndex>0){
                    currentIndex=currentIndex-1;
                    firstMovie=movieList.get(currentIndex);

                    textViewMBYTitleValue.setText(firstMovie.getName().toString());
                    editTextMBYDescriptionValue.setText(firstMovie.getDescription().toString());
                    textViewMBYGenreValue.setText(firstMovie.getGenre().toString());
                    textViewMBYRatingValue.setText(""+firstMovie.getRating().toString() + " / 5");
                    textViewMBYYearValue.setText(""+firstMovie.getYear().toString());
                    textViewMBYImdbValue.setText(firstMovie.getImdb().toString());
                }
                if(currentIndex==0)
                    Toast.makeText(MoviesByRatingActivity.this, "This is the first movie in the list.", Toast.LENGTH_SHORT).show();

            }
        });

        nextItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIndex<movieList.size()-1){
                    currentIndex=currentIndex+1;
                    firstMovie=movieList.get(currentIndex);

                    textViewMBYTitleValue.setText(firstMovie.getName().toString());
                    editTextMBYDescriptionValue.setText(firstMovie.getDescription().toString());
                    textViewMBYGenreValue.setText(firstMovie.getGenre().toString());
                    textViewMBYRatingValue.setText(""+firstMovie.getRating().toString() + " / 5");
                    textViewMBYYearValue.setText(""+firstMovie.getYear().toString());
                    textViewMBYImdbValue.setText(firstMovie.getImdb().toString());

                }
                if (currentIndex==movieList.size()-1)
                    Toast.makeText(MoviesByRatingActivity.this, "This is the last movie in the list.", Toast.LENGTH_SHORT).show();


            }
        });

    }
}
