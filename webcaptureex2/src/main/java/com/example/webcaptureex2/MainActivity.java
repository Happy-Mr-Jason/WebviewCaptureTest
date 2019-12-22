package com.example.webcaptureex2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebIconDatabase;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText edtUrl;
    private WebView webMain;
    private Boolean isFaviconExist;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtUrl = findViewById(R.id.edtUrl);
        webMain = findViewById(R.id.webMain);

        WebIconDatabase.getInstance().open(getDir("icons", MODE_PRIVATE).getPath());
        edtUrl.setInputType(EditorInfo.TYPE_TEXT_VARIATION_URI);
        edtUrl.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                webMain.loadUrl("http://" + edtUrl.getText().toString());
            }
        });

        webMain.getSettings().setLoadWithOverviewMode(true);
        webMain.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        webMain.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                Log.d("ReceivedIcon", "Called onReceivedIcon");
                //String url = view.getUrl();
                Uri uri = Uri.parse(view.getUrl());
                String host = uri.getHost();
                String filePath = getDataDir().getAbsolutePath() + "/webimages";
                File fileFolder = new File(filePath);
                if (!fileFolder.exists()) {
                    fileFolder.mkdir();
                    Log.d("SavetoFilePath", filePath);
                }
                String fileName = filePath + "/" + host + ".png";
                File file = new File(fileName);
                if (file.exists()) {
                    return;
                } else {
                    Bitmap favicon = icon;
                    Log.d("ReceivedIcon", favicon == null ? "icon is null" : "icon has something");
                    onCreateFaviconFile(file, favicon);
                }
            }
        });
    }

    private void onCreateFaviconFile(File file, Bitmap favicon) {
        final Bitmap ficon = favicon;
        final File fileName = file;
        isFaviconExist = false;
        if (favicon == null) {
            //webMain.reload();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    FileOutputStream output = new FileOutputStream(fileName);
                    ficon.compress(Bitmap.CompressFormat.PNG, 100, output);
                    //output.flush();
                    output.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();

    }


}
