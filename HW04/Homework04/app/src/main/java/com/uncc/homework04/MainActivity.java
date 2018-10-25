/*
a. Assignment # : Homework 04
b. File Name : 801091205_HW04.zip
c. Name : Pawan Ramesh Bole.
          Srishti Tiwari
d. Group Number : 31
*/


package com.uncc.homework04;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textViewKeyword;
    Button buttonGo;
    ImageView imageViewPhotos;
    ImageView imageViewPrev;
    ImageView imageViewNext;
    ArrayList<String> imageURLs;
    AlertDialog alertDialogImage;
    ProgressDialog progressDialogLoading , progressDialogImageLoading;

    int photoNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Main Activity");

        textViewKeyword =  findViewById(R.id.textViewKeyword);
        buttonGo =  findViewById(R.id.buttonGo);
        imageViewPhotos = findViewById(R.id.imageViewPhotos);
        imageViewPrev = findViewById(R.id.imageViewPrev);
        imageViewNext = findViewById(R.id.imageViewNext);

        imageViewPrev.setEnabled(false);
        imageViewNext.setEnabled(false);


        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new KeyWords(MainActivity.this).execute();
            }
        });

        imageViewPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(photoNumber == 0)
                {
                    photoNumber = (imageURLs.size()-1);
                }
                else
                {
                    photoNumber = photoNumber - 1;
                }
                new Images(MainActivity.this).execute(imageURLs.get(photoNumber));
            }
        });

        imageViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(photoNumber == imageURLs.size()-1)
                {
                    photoNumber = 0;
                }
                else
                {
                    photoNumber = photoNumber + 1;
                }

                new Images(MainActivity.this).execute(imageURLs.get(photoNumber));
            }
        });

    }

    public boolean isConnectedOnline()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            return true;
        }
        else
        {
            return false;
        }
    }



    public void getImagesFromDictionary(String result) {
        imageURLs = new ArrayList<String>();
        String[] images = result.split(";");
        for(int i=0; i<images.length;i++)
        {
            if(images[i]!= null && !images[i].isEmpty() && !images[i].equals("null"))
            {
                imageURLs.add(images[i]);
            }
        }

        photoNumber = 0;

        if (!isConnectedOnline())
        {
            Toast.makeText(MainActivity.this, "Check your internet connection.", Toast.LENGTH_SHORT).show();
            return;
        }


        if (imageURLs.size() == 0)
        {
            imageViewPrev.setEnabled(false);
            imageViewNext.setEnabled(false);
            imageViewPhotos.setImageBitmap(null);
            Toast.makeText(MainActivity.this, "No images found.", Toast.LENGTH_SHORT).show();
        }
        else if (imageURLs.size() == 1)
        {
            imageViewPrev.setEnabled(false);
            imageViewNext.setEnabled(false);
            new Images(MainActivity.this).execute(imageURLs.get(photoNumber));
        }
        else
        {
            imageViewPrev.setEnabled(true);
            imageViewNext.setEnabled(true);
            new Images(MainActivity.this).execute(imageURLs.get(photoNumber));
        }

    }

    public void getKeywords(List<String> keywords) {

        final CharSequence[] imageKeywords = keywords.toArray(new CharSequence[keywords.size()]);
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this).
                setTitle("Choose a Keyword").
                setItems(imageKeywords, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isConnectedOnline())
                        {
                            textViewKeyword.setText(imageKeywords[which].toString());
                            new ImagesInfo(MainActivity.this).execute(imageKeywords[which].toString());
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Check your internet connection.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        alertDialogImage = alertBuilder.create();
        alertDialogImage.show();


        progressDialogLoading = new ProgressDialog(this);
        progressDialogLoading.setMessage(this.getResources().getString(R.string.loading));

        progressDialogImageLoading = new ProgressDialog(this);
        progressDialogImageLoading.setMessage(this.getResources().getString(R.string.loadingPhoto));
    }
}
