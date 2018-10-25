/*
a. Assignment # : Homework 05
b. File Name : Group31_HW05.zip
c. Name : Pawan Ramesh Bole.
          Srishti Tiwari
*/

package com.uncc.hw05;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class NewsApiAdapter extends ArrayAdapter<News> {
    Context context;
    ArrayList<News> newsList;


    public NewsApiAdapter(Context context, ArrayList<News> newsList) {
        super(context, R.layout.news_item, newsList);
        this.context = context;
        this.newsList = newsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news = getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.news_item, parent, false);
        }

        ImageView imageViewUrlToImage = convertView.findViewById(R.id.imageViewUrlToImage);
        TextView textViewNewsAuthor = convertView.findViewById(R.id.textViewNewsAuthor);
        TextView textViewNewsTitle = convertView.findViewById(R.id.textViewNewsTitle);
        TextView textViewPublishedAt = convertView.findViewById(R.id.textViewPublishedAt);

        //set the data from the news object

        if (!(news.getAuthor() == null && news.getAuthor().isEmpty())) {
            textViewNewsAuthor.setText(news.getAuthor());
        } else {
            textViewNewsAuthor.setText(" ");
        }

        if (!(news.getTitle() == null && news.getTitle().isEmpty())) {
            textViewNewsTitle.setText(news.getTitle());
        } else {
            textViewNewsTitle.setText(" ");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        String publishedAtFormattedDate = dateFormat.format(news.publishedAt);
        textViewPublishedAt.setText(publishedAtFormattedDate);
        Picasso.get().load(news.urlToImage).into(imageViewUrlToImage);
        return convertView;
    }

}
