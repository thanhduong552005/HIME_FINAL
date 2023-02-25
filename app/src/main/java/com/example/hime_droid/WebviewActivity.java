package com.example.hime_droid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.webkit.WebView;

public class WebviewActivity extends AppCompatActivity {

    String image_path = "";
    String html = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_webview);

        Bundle extras = getIntent().getExtras();
        Log.d("vao duoc webview", "start ne");
        if (extras == null) {
            image_path = null;
            html = null;

        } else {
            image_path = extras.getString("IMAGE_PATH");
            html = extras.getString("HTML");
        }
        Log.d("vao duoc webview", "get extra ne");
        Log.d("vao duoc webview", html);

        WebView myWebView = new WebView(this);
        String encodedHtml = Base64.encodeToString(html.getBytes(),
                Base64.NO_PADDING);
        myWebView.loadData(encodedHtml, "text/html", "base64");
        setContentView(myWebView);
    }
}