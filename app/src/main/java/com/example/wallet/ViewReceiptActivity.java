package com.example.wallet;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.wallet.Fragmets.FeeDirections;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ViewReceiptActivity extends AppCompatActivity {

    PDFView pdfView;
    private String BASE_URL = "https://s3.ap-south-1.amazonaws.com/test.files.classroom.digital/";
    private String pdf;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_receipt);
        pdfView = findViewById(R.id.invoice_pdf);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pdf = extras.getString("pdfUrl");
            //The key argument here must match that used in the other activity
        }
        Log.i("TAG", "onCreate: " + pdf);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.videos);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        webView();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

        webView();


    }

    class RetrivePDFfromUrl extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            // we are using inputstream
            // for getting out PDF.
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                // below is the step where we are
                // creating our connection.
                HttpURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    // response is success.
                    // we are getting input stream from url
                    // and storing it in our variable.
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }

            } catch (IOException e) {
                // this is the method
                // to handle errors.
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            // after the execution of our async
            // task we are loading our pdf in our pdf view.
            pdfView.fromStream(inputStream).load();
        }
    }

    public void webView() {
        new RetrivePDFfromUrl().execute(BASE_URL+pdf);
//        android.webkit.WebView webView = findViewById(R.id.webview);
//        webView.setWebViewClient(new WebViewClient());
//        webView.setWebChromeClient(new FullScreenClient(ViewReceiptActivity.this) {
//            @Override
//            public void onHideCustomView() {
//                hideFullScreen(webView);
//            }
//
//            @Override
//            public Bitmap getDefaultVideoPoster() {
//                return videoBitmap();
//            }
//
//            @Override
//            public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
//                showFullScreen(view, callback);
//            }
//
//        });
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl(BASE_URL+pdf);
    }
}
