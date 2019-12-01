package com.dandekar.epaper.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dandekar.epaper.R;
import com.dandekar.epaper.data.Constants;
import com.dandekar.epaper.data.CurrentSelection;
import com.dandekar.epaper.util.ApplicationCache;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PageImageDisplayActivity extends AppCompatActivity {

    private static String htmlContent = "<html>\n" +
            "<head>\n" +
            "<style>\n" +
            "img {\n" +
            "  position: absolute;\n" +
            "  top: 10px;\n" +
            "  left: 10px;\n" +
            "}\n" +
            ".imgPicture {\n" +
            "  z-index: 1;\n" +
            "}\n" +
            ".imgText {\n" +
            "  z-index: 3;\n" +
            "}\n" +
            "</style>\n" +
            "</head>\n" +
            "<body>\n" +
            "\t<img class=\"imgPicture\" src=\"%s\" />\n" +
            "\t<img class=\"imgText\" src=\"%s\" />\n" +
            "</body>\n" +
            "</html>";

    private WebView pageImageDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_image_display);
        pageImageDisplay = findViewById(R.id.pageImageDisplay);
        //
        pageImageDisplay.setWebViewClient(new WebViewClient() {
            @Override
            public void onLoadResource(WebView view, String url) {
                Log.d(Constants.TAG, "URL to load -> " + url);
                super.onLoadResource(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.d(Constants.TAG, "URL to load (2) -> " + request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                Log.d(Constants.TAG, "URL to load (3) -> " + request.getUrl().toString());
                String url = request.getUrl().toString();
                return getNewResponse(url);
            }

            private WebResourceResponse getNewResponse(String url) {

                try {
                    OkHttpClient httpClient = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url(url.trim())
                            .addHeader(Constants.COOKIE_KEY, ApplicationCache.cookie)
                            .build();

                    Response response = httpClient.newCall(request).execute();

                    return new WebResourceResponse(
                            null,
                            response.header("content-encoding", "utf-8"),
                            response.body().byteStream()
                    );

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

            }

        });
        // Get page number
        String pageNo = getIntent().getStringExtra(Constants.PAGE_NUMBER);
        //
        CurrentSelection curSel = ApplicationCache.curSel;
        // Get the image URL
        String imageURL = String.format(Constants.FORMAT_URL_PICTURE, curSel.getSkin(), curSel.getShortPath(), curSel.getYear(), curSel.getMonth(), curSel.getDay(), curSel.getShortPath(), curSel.getYear(), curSel.getMonth(), curSel.getDay(), pageNo);
        String textURL = String.format(Constants.FORMAT_URL_TEXT, curSel.getSkin(), curSel.getShortPath(), curSel.getYear(), curSel.getMonth(), curSel.getDay(), curSel.getShortPath(), curSel.getYear(), curSel.getMonth(), curSel.getDay(), pageNo);
        //
        String htmlText = String.format(htmlContent, imageURL, textURL);
        pageImageDisplay.loadData(htmlText, "text/html", "UTF-8");
        //
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Only one menu item - so no check required
        finish();
        return super.onOptionsItemSelected(item);
    }

}
