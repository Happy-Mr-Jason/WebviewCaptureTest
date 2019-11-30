package com.example.webviewcapturetest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    EditText edtURL;
    Button btnOpen, btnCapture;
    WebView webView;
    ImageView imageView;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtURL = (EditText)findViewById(R.id.edtURL);
        btnOpen = (Button)findViewById(R.id.btnOpen);
        btnCapture = (Button)findViewById(R.id.btnCapture);
        webView = (WebView)findViewById(R.id.webView);
        imageView = (ImageView)findViewById(R.id.ivResult);



        webView.setWebViewClient(new WebViewClient());

        edtURL.setText("https://diabetes.or.kr");

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                url = edtURL.getText().toString();
                if(url.startsWith("http://") || url.startsWith("https://")){

                } else{
                    url = "https://" + url;
                }

                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setUseWideViewPort(true);
                webSettings.setLoadWithOverviewMode(true);  // 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
                webSettings.setJavaScriptEnabled(true);
                webSettings.setPluginState(WebSettings.PluginState.ON);
                webSettings.setAllowFileAccess(true);
                webSettings.setDomStorageEnabled(true);
                webSettings.setAllowContentAccess(true);
                webSettings.setSupportZoom(true);
                webSettings.setBuiltInZoomControls(true);

                Log.i("Url Address : ", url);
                webView.loadUrl(url);
                webView.zoomOut();
                webView.zoomOut();
                webView.zoomOut();
                webView.zoomOut();
                webView.zoomOut();
            }
        });

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int widtth = webView.getWidth();
                int height = webView.getHeight();
                Bitmap bmp = Bitmap.createBitmap(widtth,height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bmp);

                webView.draw(canvas);
                imageView.setImageBitmap(bmp);

            }
        });
    }
}
