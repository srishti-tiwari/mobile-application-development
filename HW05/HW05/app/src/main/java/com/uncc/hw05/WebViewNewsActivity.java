/*
a. Assignment # : Homework 05
b. File Name : Group31_HW05.zip
c. Name : Pawan Ramesh Bole.
          Srishti Tiwari
*/

package com.uncc.hw05;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewNewsActivity extends AppCompatActivity {

    WebView webView;
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_news);

        setTitle("Web View");

        Intent newsItemIntent = getIntent();
        News newsItem = (News) newsItemIntent.getSerializableExtra("webViewNewsItem");

        webView = (WebView)findViewById(R.id.webViewNews);

        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(newsItem.url);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }
}

