package com.webview;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.DoubleBounce;

public class MainActivity extends AppCompatActivity {

    private Dialog dialog;
    private DoubleBounce doubleBounce;
    private ProgressBar progressBar;
    private String url = "https://www.uxmatters.com/";
    private WebView webView;
    private Button tryAgainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActivity();
        chkNET();
    }

    public void onClick(View view) {
        chkNET();
    }

    public void chkNET() {
        if (InternetHelper.isOnline(getApplicationContext())) {
            webView.loadUrl(url);
            tryAgainBtn.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            tryAgainBtn.setVisibility(View.VISIBLE);
            Toast.makeText(this, "NET Checked", Toast.LENGTH_SHORT).show();
        }
    }

    private void initActivity() {
        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        dialog = new Dialog(this, R.style.myDialogStyle);
        dialog.setContentView(R.layout.dialog);
        tryAgainBtn = dialog.findViewById(R.id.button);
        progressBar = dialog.findViewById(R.id.spin_kit);
        doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        dialog.show();

        showDilog();

        //set webview customized client
        webView.setWebViewClient(new MyWebViewClient());
        //set JS handler client
        webSettings.setJavaScriptEnabled(true);
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

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, final int newProgress) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (newProgress == 100)
                            dissmisDilog();
                    }
                });
                super.onProgressChanged(view, newProgress);

            }
        });
    }

    public void showDilog(){
        progressBar.setIndeterminate(true);
        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void dissmisDilog(){
        dialog.dismiss();
    }
}
