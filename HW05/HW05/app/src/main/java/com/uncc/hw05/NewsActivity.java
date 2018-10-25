/*
a. Assignment # : Homework 05
b. File Name : Group31_HW05.zip
c. Name : Pawan Ramesh Bole.
          Srishti Tiwari
*/

package com.uncc.hw05;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity implements INewsData{

    ListView newsListView;
    String apiToLoadNews = "https://newsapi.org/v2/top-headlines?sources=%s&apiKey=%s";
    String apiKey = "3e918825a3c44bbb9c181b193e9b9a43";
    AlertDialog dialog;
    ArrayList<News> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        newsListView = findViewById(R.id.newsListView);

        Intent sourceIdItent = getIntent();
        Source source = (Source)sourceIdItent.getSerializableExtra("source");
        setTitle(source.name);
        String newsAPI = String.format(apiToLoadNews,source.id,apiKey);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setTitle("Loading Stories").setView(inflater.inflate(R.layout.dialog_bar, null));
        dialog = builder.create();
        dialog.show();


        new NewsAsyncTask(NewsActivity.this).execute(newsAPI);


        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = newsList.get(position);
                Intent webViewIntent = new Intent(NewsActivity.this,WebViewNewsActivity.class);
                webViewIntent.putExtra("webViewNewsItem",news);
                startActivity(webViewIntent);

            }
        });

    }

    @Override
    public void populateNewsData(ArrayList<News> newsList) {
        this.newsList = newsList;
        NewsApiAdapter newsApiAdapter = new NewsApiAdapter(NewsActivity.this,newsList);
        newsListView.setAdapter(newsApiAdapter);

        dialog.hide();
    }
}
