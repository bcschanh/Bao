package com.example.admin.rss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        webView=(WebView)findViewById(R.id.wvtintuc);
        Intent intent=getIntent();
        String link= intent.getStringExtra("linktintuc");
//        Toast.makeText(this, link, Toast.LENGTH_SHORT).show();
        webView.loadUrl(link);
        webView.setWebViewClient(new WebViewClient());
    }
}
