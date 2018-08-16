package com.webview;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url = "https://www.uxmatters.com/";
        WebView webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();

        //set webview customized client
        webView.setWebViewClient(new MyWebViewClient());
        //set JS handler client
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setBackgroundColor(Color.TRANSPARENT);

        //config for zoom
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);//allow pinch to zoom
        webSettings.setDisplayZoomControls(false);//disable default zoom control on the page

        //enable responsive layout
        webSettings.setUseWideViewPort(true);
        //zoom out if the content with is greater than the width of the view port
        webSettings.setLoadWithOverviewMode(true);

        webView.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);

        if (Build.VERSION.SDK_INT >= 19) webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        else webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);


    }
}
