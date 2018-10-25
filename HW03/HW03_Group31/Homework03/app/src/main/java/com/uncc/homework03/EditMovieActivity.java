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

public class EditMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);
        setTitle("Edit Movie");

        final Button buttonSaveChanges = findViewById(R.id.buttonSaveChanges);
        final EditText editTextEditName = findViewById(R.id.editTextEditName);
        final EditText editTextEditDescription = findViewById(R.id.editTextEditDescription);
        final EditText editTextEditImdb = findViewById(R.id.editTextEditImdb);
        final EditText editTextEditYear = findViewById(R.id.editTextEditYear);
        final Spinner spinnerEditGenre = findViewById(R.id.spinnerEditGenre);
        final SeekBar seekBarEditRating = findViewById(R.id.seekBarEditRating);
        final TextView labelSeekEditText = findViewById(R.id.labelSeekEditText);

        Intent editViewIntent = getIntent();
        Movie editMovie = (Movie) editViewIntent.getSerializableExtra("editMovie");
        if (editMovie != null && editMovie.getName() != null && !editMovie.getName().toString().isEmpty()) {
            editTextEditName.setText(editMovie.getName().toString());
            editTextEditDescription.setText(editMovie.getDescription().toString());
            editTextEditImdb.setText(editMovie.getImdb().toString());
            editTextEditYear.setText(editMovie.getYear().toString());
            spinnerEditGenre.setPrompt(editMovie.getGenre().toString());
            seekBarEditRating.setProgress(editMovie.getRating());
            labelSeekEditText.setText(editMovie.getRating().toString());
            buttonSaveChanges.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editTextEditName.getText().toString() == null || editTextEditName.getText().toString().isEmpty() ||
                            editTextEditDescription.getText().toString() == null || editTextEditDescription.getText().toString().isEmpty() ||
                            spinnerEditGenre.getSelectedItem().toString() == null || spinnerEditGenre.getSelectedItem().toString().isEmpty() ||
                            editTextEditImdb.getText().toString() == null || editTextEditImdb.getText().toString().isEmpty() ||
                            labelSeekEditText.getText().toString() == null || labelSeekEditText.getText().toString().isEmpty() ||
                            editTextEditYear.getText().toString() == null || editTextEditYear.getText().toString().isEmpty()) {

                        Toast.makeText(EditMovieActivity.this, "Please fill all the details", Toast.LENGTH_LONG).show();
                    } else {
                        Movie movie = new Movie();
                        movie.setDescription(editTextEditDescription.getText().toString());
                        movie.setName(editTextEditName.getText().toString());
                        movie.setImdb(editTextEditImdb.getText().toString());
                        movie.setYear(Integer.parseInt(editTextEditYear.getText().toString()));
                        movie.setGenre(spinnerEditGenre.getSelectedItem().toString());
                        movie.setRating(seekBarEditRating.getProgress());

                        Toast.makeText(EditMovieActivity.this, "movie saved" + movie.getName() + movie.getYear().toString() + movie.getGenre() + movie.getRating(), Toast.LENGTH_LONG).show();

                        Intent editMovieIntent = new Intent();
                        editMovieIntent.putExtra("editMovie", movie);
                        setResult(RESULT_OK, editMovieIntent);
                        finish();
                    }
                }
            });

            seekBarEditRating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    labelSeekEditText.setText("" + progress);
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
}