/* Group 31 - Homework 03
Name:
Pawan Bole
Srishti Tiwari
*/


package com.uncc.homework03;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Movie> movieList = new ArrayList<Movie>();
    static final int ADD_MOVIE_REQUEST = 1;
    static final int EDIT_MOVIE_REQUEST = 2;
    static final int MOVIE_LIST_REQUEST = 3;
    static final int MOVIE_LIST_REQUEST2 = 4;
    String[] movies;
    int editItemIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button buttonAddMovie = findViewById(R.id.buttonAddMovie);
        final Button buttonDeleteMovie = findViewById(R.id.buttonDeleteMovie);
        final Button buttonEdit = findViewById(R.id.buttonEdit);
        final Button buttonShowListByRating = findViewById(R.id.buttonShowListByRating);
        final Button buttonShowListByYear = findViewById(R.id.buttonShowListByYear);


        buttonAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addMovieIntent = new Intent(MainActivity.this, AddMovieActivity.class);
                startActivityForResult(addMovieIntent, ADD_MOVIE_REQUEST);
            }
        });

        buttonDeleteMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Pick a movie");

                movies =new String[movieList.size()];
                for(int i=0;i<movieList.size();i++)
                    movies[i] = movieList.get(i).getName();

                builder.setItems(movies, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        String s = movieList.get(item).getName();
                        movieList.remove(item);
                        Toast.makeText(MainActivity.this, s+" is Deleted", Toast.LENGTH_LONG).show();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Pick a movie");

                movies =new String[movieList.size()];
                for(int i=0;i<movieList.size();i++)
                    movies[i] = movieList.get(i).getName();

                builder.setItems(movies, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        editItemIndex = item;
                        Intent i = new Intent(MainActivity.this, EditMovieActivity.class);
                        i.putExtra("editMovie", movieList.get(item));
                        startActivityForResult(i, EDIT_MOVIE_REQUEST);

                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        buttonShowListByRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(movieList.size()==0){
                    Toast.makeText(MainActivity.this,"Movie list is not present",Toast.LENGTH_LONG).show();
                }
                else{
                    Collections.sort(movieList, ratingComparator);
                    Intent intent =new Intent(MainActivity.this,MoviesByRatingActivity.class);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.putExtra("movieListByRating", (ArrayList<Movie>) movieList);
                    startActivityForResult(intent, MOVIE_LIST_REQUEST);
                }
            }
        });

        buttonShowListByYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(movieList.size()==0){
                    Toast.makeText(MainActivity.this,"Movie list is not present",Toast.LENGTH_LONG).show();
                }
                else{
                    Collections.sort(movieList, yearComparator);
                    Intent intent =new Intent(MainActivity.this,MoviesByYearActivity.class);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.putExtra("movieListByYear", (ArrayList<Movie>) movieList);
                    startActivityForResult(intent, MOVIE_LIST_REQUEST2);
                }

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ADD_MOVIE_REQUEST) {
            if(resultCode == RESULT_OK && data != null && data.hasExtra("movie"))
            {
                Movie m =(Movie)data.getExtras().getSerializable("movie");
                movieList.add(m);
            }
        }
        if(requestCode == MainActivity.EDIT_MOVIE_REQUEST && data != null && data.hasExtra("editMovie")){
            if(resultCode == RESULT_OK && data != null && data.hasExtra("editMovie"))
            {
                Movie m = (Movie)data.getExtras().getSerializable("editMovie");
                movieList.set(editItemIndex,m);
            }
        }
    }

    public static Comparator<Movie> ratingComparator = new Comparator<Movie>() {

        public int compare(Movie movie, Movie movie2) {
            Double rating1 = Double.valueOf(movie.getRating());
            Double rating2 = Double.valueOf(movie2.getRating());
            //descending order
            return rating2.compareTo(rating1);
        }};

    public static Comparator<Movie> yearComparator = new Comparator<Movie>() {

        public int compare(Movie movie, Movie movie2) {
            Double year1 = Double.valueOf(movie.getYear());
            Double year2 = Double.valueOf(movie2.getYear());
            //ascending order
            return year1.compareTo(year2);
        }};
}
