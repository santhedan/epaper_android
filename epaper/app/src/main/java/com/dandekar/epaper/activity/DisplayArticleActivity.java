package com.dandekar.epaper.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

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
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_article);
        //
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
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
        // Check if the article is available on disk
        final String articleFileName = getArticleFileName(ApplicationCache.curSel);
        if (FileUtils.fileExists(getApplicationContext(), articleFileName)) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    readArticleFileFromDisk(articleFileName);
                }
            };
            t.start();
        } else {
            // Create the volly instance
            volley = VolleySingleton.getInstance(getApplicationContext());
            GetArticleContent request = new GetArticleContent(articleUrl, this, this);
            volley.getRequestQueue().add(request);
        }
    }

    private void readArticleFileFromDisk(String fileName) {
        final String articleText = FileUtils.readStringFromFile(getApplicationContext(), fileName);
        // Call the onReasponse from UI thread
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                // Code here will run in UI thread
                onResponse(articleText);
            }
        });
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        StringBuilder sb = new StringBuilder();
        sb.append("<HTML><HEAD></HEAD><body>");
        sb.append("<h1>Error loading article</h1>");
        sb.append("</body></HTML>");
        wv.loadData(sb.toString(), "text/html", "UTF-8");
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onResponse(String response) {
        final StringBuilder sb = new StringBuilder();
        //<LINK href="toi.css" type="text/css" rel="stylesheet"/>
        sb.append("<HTML><HEAD><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />");
        sb.append("<style>\n" +
                ".indent {\n" +
                "  word-wrap: break-word;\n" +
                "}\n" +
                "</style>");
        sb.append("</HEAD><body>");
        sb.append(response);
        sb.append("</body></HTML>");
        wv.loadDataWithBaseURL("file:///android_asset/", sb.toString(), "text/html", "utf-8", null);
        progressBar.setVisibility(View.GONE);
        // Initiate save of article data
        final String articleFileName = getArticleFileName(ApplicationCache.curSel);
        Thread tSaveArt = new Thread() {
            @Override
            public void run() {
                FileUtils.writeStringToFile(getApplicationContext(), sb.toString(), articleFileName);
            }
        };
        //
        tSaveArt.start();
    }

    private String getArticleFileName(CurrentSelection curSelTN) {
        return String.format(Constants.ArticleFileNameFormat, curSelTN.getShortPath(), curSelTN.getYear(), curSelTN.getMonth(), curSelTN.getDay(), articleID);
    }

}
