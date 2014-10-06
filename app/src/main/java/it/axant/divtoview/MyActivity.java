package it.axant.divtoview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;


public class MyActivity extends Activity {
    private WebView webView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        webView = (WebView) findViewById(R.id.webView);
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebInterface(this), "Android");
        webView.loadUrl("file:///android_asset/index.html");
    }

    private void buildBrowser(int top, int left, final int height, final int width) {
        float density = getResources().getDisplayMetrics().density;
        AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(
                (int) (width*density), (int) (height*density), (int) (left*density), (int) (top*density));
        webView.addView(ViewBuilder.createExampleView(this), params);
        Log.d("webview", "(norm)--> w"+(int)(width*density)
                +" h"+(int)(height*density)
                +" l"+(int)(left*density)
                +" t"+(int)(top*density));
    }

    private class WebInterface{
        Context context;

        WebInterface(Context c) {
            context = c;
        }

        @JavascriptInterface
        public void buildWidget(final int top, final int left, final int height, final int width) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    buildBrowser(top, left, height, width);
                }
            });
        }

    }

}
