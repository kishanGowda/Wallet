package com.example.wallet.Fragmets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.wallet.R;

public class Photo extends AppCompatActivity {
    WebView webView;
    String key;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            key= extras.getString("key");
            Log.i("TAG", key);

        }
        back=findViewById(R.id.back_youtube_pdf);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        webView=(WebView)findViewById(R.id.webview);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.videos);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        webView.getSettings().setLoadsImagesAutomatically(true);
                        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                        webView.loadUrl(key);

                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(key);
    }
}