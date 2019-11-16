package com.dandekar.epaper.activity;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dandekar.epaper.R;
import com.dandekar.epaper.data.Constants;
import com.dandekar.epaper.data.CurrentSelection;
import com.dandekar.epaper.request.GetArticleContent;
import com.dandekar.epaper.util.ApplicationCache;
import com.dandekar.epaper.util.FileUtils;
import com.dandekar.epaper.util.VolleySingleton;

public class DisplayArticleActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<String> {

    private VolleySingleton volley;
    private WebView wv;
    private String articleID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_article);
        //
        wv = findViewById(R.id.articleWebView);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setUseWideViewPort(true);
        wv.getSettings().setSupportZoom(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.getSettings().setDisplayZoomControls(false);
        wv.setVerticalScrollBarEnabled(true);
        wv.setHorizontalScrollBarEnabled(true);
        //
        String articleUrl = getIntent().getStringExtra(Constants.ARTICLE_URL);
        articleID = getIntent().getStringExtra(Constants.ARTICLE_ID);
        // Create the volly instance
        volley = VolleySingleton.getInstance(getApplicationContext());
        GetArticleContent request = new GetArticleContent(articleUrl, this, this);
        volley.getRequestQueue().add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        StringBuilder sb = new StringBuilder();
        sb.append("<HTML><HEAD></HEAD><body>");
        sb.append("<h1>Error loading article</h1>");
        sb.append("</body></HTML>");
    }

    @Override
    public void onResponse(String response) {
        final StringBuilder sb = new StringBuilder();
        //<LINK href="toi.css" type="text/css" rel="stylesheet"/>
        sb.append("<HTML><HEAD><meta name=\"viewport\" content=\"width=device-width,height=device-height,target-densityDpi=device-dpi,user-scalable=yes,initial-scale=0.5, maximum-scale=2, minimum-scale=0.5\" /></HEAD><body>");
        sb.append(response);
        sb.append("</body></HTML>");
        wv.loadDataWithBaseURL("file:///android_asset/", sb.toString(), "text/html", "utf-8", null);
        // Initiate save of article data
        CurrentSelection curSelTN = ApplicationCache.curSel;
        final String articleFileName = String.format(Constants.ArticleFileNameFormat, curSelTN.getShortPath(), curSelTN.getYear(), curSelTN.getMonth(), curSelTN.getDay(), articleID);
        Thread tSaveArt = new Thread() {
            @Override
            public void run() {
                FileUtils.writeStringToFile(getApplicationContext(), sb.toString(), articleFileName);
            }
        };
        //
        tSaveArt.start();
    }

}
