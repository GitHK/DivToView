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
        AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(
                width*2, height*2, left*2, top*2);
        webView.addView(ViewBuilder.createExampleView(this), params);
        Log.d("webview", "(norm)--> w"+width*2
                +" h"+height*2
                +" l"+left*2
                +" t"+top*2);

    }

    private void buildBrowserPercentage(final float top, final float left, final float height, final float width) {
        final Context localContext = this;
        ViewTreeObserver viewTreeObserver  = webView.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                int webViewContentHeight = webView.getContentHeight();
                int webViewContentWidth = webView.getWidth();

                if( webViewContentHeight != 0 ){
                    AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(
                            (int) width * webViewContentWidth / 100,
                            (int) height * webViewContentHeight / 100 * 2,
                            (int) left * webViewContentWidth / 100,
                            (int) top * webViewContentHeight / 100 * 2);
                    webView.addView(ViewBuilder.createExampleView(localContext), params);

                    Log.d("webview", "(perc)--> w"+width
                            +" h"+height
                            +" l"+left
                            +" t"+top);

                    Log.d("webview", "(pcon)--> w"+(int) width * webViewContentWidth / 100
                                    +" h"+(int) height * webViewContentHeight / 100 *2
                                    +" l"+(int) left * webViewContentWidth / 100
                                    +" t"+(int) top * webViewContentHeight / 100 *2);

                    webView.getViewTreeObserver().removeOnPreDrawListener(this);
                }
                return false;
            }
        });
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

        @JavascriptInterface
        public void buildWidgetPercentage(final float top, final float left, final float height, final float width) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    buildBrowserPercentage(top, left, height, width);
                }
            });
        }
    }

}
