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
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);
        setTitle("Add Movie");

        final Button buttonAddMovieSave = findViewById(R.id.buttonAddMovie);
        final EditText editTextName = findViewById(R.id.editTextName);
        final EditText editTextDescription = findViewById(R.id.editTextDescription);
        final EditText editTextImdb = findViewById(R.id.editTextImdb);
        final EditText editTextYear = findViewById(R.id.editTextYear);
        final Spinner spinnerGenre = findViewById(R.id.spinnerGenre);
        final SeekBar seekBarRating = findViewById(R.id.seekBarRating);
        final TextView labelSeekText = findViewById(R.id.labelSeekText);

        buttonAddMovieSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextName.getText().toString() == null || editTextName.getText().toString().isEmpty() ||
                        editTextDescription.getText().toString() == null || editTextDescription.getText().toString().isEmpty() ||
                        spinnerGenre.getSelectedItem().toString() == null || spinnerGenre.getSelectedItem().toString().isEmpty() ||
                        editTextImdb.getText().toString() == null || editTextImdb.getText().toString().isEmpty() ||
                        labelSeekText.getText().toString() == null || labelSeekText.getText().toString().isEmpty() ||
                        editTextYear.getText().toString() == null || editTextYear.getText().toString().isEmpty()) {

                    Toast.makeText(AddMovieActivity.this, "Please fill all the details", Toast.LENGTH_LONG).show();
                } else {
                    Movie movie = new Movie();
                    movie.setDescription(editTextDescription.getText().toString());
                    movie.setName(editTextName.getText().toString());
                    movie.setImdb(editTextImdb.getText().toString());
                    movie.setYear(Integer.parseInt(editTextYear.getText().toString()));
                    movie.setGenre(spinnerGenre.getSelectedItem().toString());
                    movie.setRating(seekBarRating.getProgress());

                    Toast.makeText(AddMovieActivity.this, "movie saved " + movie.getName() + " " + movie.getYear().toString() + " " + movie.getGenre()+" " + movie.getRating(), Toast.LENGTH_LONG).show();

                    Intent addMovieIntent = new Intent();
                    addMovieIntent.putExtra("movie", movie);
                    setResult(RESULT_OK, addMovieIntent);
                    finish();
                }
            }
        });

        seekBarRating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                labelSeekText.setText(""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


}
