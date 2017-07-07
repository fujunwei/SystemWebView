package com.example.systemwebview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    private static final int INPUT_URL = 1;
    private WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mWebview = (WebView) findViewById(R.id.webview);
        mWebview.setWebViewClient(new MyWebViewClient());
        mWebview.setWebChromeClient(new MyWebChromeClient());
        initSetting();
        setKeyListener();

        mWebview.loadUrl("http://w3c-test.org/XMLHttpRequest/");
//        mWebview.loadDataWithBaseURL(null, "<img src=\"http://rss.cbs.baidu.com/onlinetopic/relatedZhidao.png\">11111", "text/html", null, null);
    }

    private void setKeyListener() {
        //点击后退按钮,让WebView后退一页(也可以覆写Activity的onKeyDown方法)
        mWebview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {  //表示按返回键
                        Log.d("fujunwei", "========setKeyListener");
                        return true;    //已处理
                    }
                }
                return false;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                Log.d("====KEYCODE_MENU", "KEYCODE_MENU");
                Intent addressIntent = new Intent("com.baidu.browser.INPUTURL");
                startActivityForResult(addressIntent, INPUT_URL);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case INPUT_URL: {
                if (data != null) {
                    Bundle extras = data.getExtras();
                    String url = extras.getString("URL");
                    mWebview.loadUrl(url);
                }
                break;
            }
        }
    }

    private void initSetting() {
        WebSettings settings = mWebview.getSettings();
        settings.setNeedInitialFocus(false);

        settings.setLoadsImagesAutomatically(true);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setDefaultTextEncodingName("GBK");
        settings.setUseWideViewPort(true);
        settings.setSupportMultipleWindows(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d("fujunwei", "======onPageStarted " + url);
        }

        @Override
        public void onPageFinished(WebView view, String urls) {
            Log.d("fujunwei", "======onPageFinished " + urls);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            Log.i("Shell", "loading: " + newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            Log.d("fujunwei", "====== the title " + title);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                }
            }, 2000);
        }
    }
}
