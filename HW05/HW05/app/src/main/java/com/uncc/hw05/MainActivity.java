/*
a. Assignment # : Homework 05
b. File Name : Group31_HW05.zip
c. Name : Pawan Ramesh Bole.
          Srishti Tiwari
*/

package com.uncc.hw05;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ISourceData {

    ListView sourceListView;
    AlertDialog dialog;
    String url = "https://newsapi.org/v2/sources?apiKey=";
    String apiKey = "3e918825a3c44bbb9c181b193e9b9a43";
    ArrayList<Source> sourceList;
    String[] sourceIds;
    String[] sourceNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (internetConnectionEnabled()) {

            setTitle("Main Activity");
            sourceListView = findViewById(R.id.sourceListView);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            builder.setTitle("Loading Sources").setView(inflater.inflate(R.layout.dialog_bar, null));
            dialog = builder.create();
            dialog.show();

            new NewsSourceAsyncTask(MainActivity.this).execute(url + apiKey);

            sourceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Source source = sourceList.get(position);
                    Intent sourceIdIntent = new Intent(MainActivity.this, NewsActivity.class);
                    sourceIdIntent.putExtra("source", source);
                    startActivity(sourceIdIntent);
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "No internet connection available", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void populateSourceData(ArrayList<Source> sources) {
        this.sourceList = sources;
        sourceIds = new String[sources.size()];
        sourceNames = new String[sources.size()];
        for (int i = 0; i < sources.size(); i++) {
            sourceIds[i] = sources.get(i).id;
            sourceNames[i] = sources.get(i).name;
        }
        ArrayAdapter<String> sourceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, sourceNames);
        sourceListView.setAdapter(sourceAdapter);
        dialog.hide();
    }

    public boolean internetConnectionEnabled() {
        ConnectivityManager cm =
                (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
