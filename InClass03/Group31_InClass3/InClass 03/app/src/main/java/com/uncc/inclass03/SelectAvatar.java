package com.uncc.inclass03;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SelectAvatar extends AppCompatActivity {


    private ImageView selectedImage;

    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_avatar);

        final ImageView imageViewBoy1 = findViewById(R.id.imageViewBoy1);
        final ImageView imageViewBoy2 = findViewById(R.id.imageViewBoy2);
        final ImageView imageViewBoy3 = findViewById(R.id.imageViewBoy3);
        final ImageView imageViewGirl1 = findViewById(R.id.imageViewGirl1);
        final ImageView imageViewGirl2 = findViewById(R.id.imageViewGirl2);
        final ImageView imageViewGirl3 = findViewById(R.id.imageViewGirl3);

        imageViewBoy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImage = imageViewBoy1;
                Intent imageIdIntent = new Intent();
                imageIdIntent.putExtra("selectedImage", R.drawable.avatar_m_1);
                setResult(RESULT_OK, imageIdIntent);
                finish();
            }
        });

        imageViewBoy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImage = imageViewBoy2;
                Intent imageIdIntent = new Intent();
                imageIdIntent.putExtra("selectedImage", R.drawable.avatar_m_2);
                setResult(RESULT_OK, imageIdIntent);
                finish();
            }
        });

        imageViewBoy3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImage = imageViewBoy3;
                Intent imageIdIntent = new Intent();
                imageIdIntent.putExtra("selectedImage", R.drawable.avatar_m_3);
                setResult(RESULT_OK, imageIdIntent);
                finish();
            }
        });

        imageViewGirl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImage = imageViewGirl1;
                Intent imageIdIntent = new Intent();
                imageIdIntent.putExtra("selectedImage", R.drawable.avatar_f_1);
                setResult(RESULT_OK, imageIdIntent);
                finish();
            }
        });

        imageViewGirl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImage = imageViewGirl2;
                Intent imageIdIntent = new Intent();
                imageIdIntent.putExtra("selectedImage", R.drawable.avatar_f_2);
                setResult(RESULT_OK, imageIdIntent);
                finish();
            }
        });

        imageViewGirl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImage = imageViewGirl3;
                Intent imageIdIntent = new Intent();
                imageIdIntent.putExtra("selectedImage", R.drawable.avatar_f_3);
                setResult(RESULT_OK, imageIdIntent);
                finish();

            }
        });

    }


}
