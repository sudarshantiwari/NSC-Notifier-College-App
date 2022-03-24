package com.ursaccharine.nscnotifier;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.net.URLEncoder;

public class viewpdf extends AppCompatActivity {

    WebView pdfview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpdf);

        pdfview = (WebView) findViewById(R.id.viewpdf);
        pdfview.getSettings().setJavaScriptEnabled(true);

        String name = getIntent().getStringExtra("name");
        String url = getIntent().getStringExtra("url");

      final  ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle(name);
        pd.setMessage("Opening...");

        pdfview.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pd.show();
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pd.dismiss();
            }
        });
        String fileurl = "";
        try {
            fileurl = URLEncoder.encode(fileurl,"UTF-8");

        }catch (Exception ex)
        {
        }
        pdfview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + fileurl);
    }

}