package com.dandekar.epaper.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.dandekar.epaper.data.Publication;
import com.dandekar.epaper.util.ApplicationCache;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PageImageDisplayActivity extends AppCompatActivity {

    private static String htmlContent = "<html>\n" +
            "<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
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

    private static int mirrorRes = 136;
    private static int otherRes = 120;
    private int curPage;
    private int totalPages;
    private String[] pageNames;
    //
    private MenuItem previousMenu;
    private MenuItem nextMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_image_display);
        pageImageDisplay = findViewById(R.id.pageImageDisplay);
        pageImageDisplay.getSettings().setLoadWithOverviewMode(true);
        pageImageDisplay.getSettings().setUseWideViewPort(true);
        pageImageDisplay.getSettings().setSupportZoom(true);
        pageImageDisplay.getSettings().setBuiltInZoomControls(true);
        pageImageDisplay.getSettings().setDisplayZoomControls(false);
        pageImageDisplay.setVerticalScrollBarEnabled(true);
        pageImageDisplay.setHorizontalScrollBarEnabled(true);
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
        curPage = getIntent().getIntExtra(Constants.PAGE_NUMBER, -1);
        totalPages = getIntent().getIntExtra(Constants.PAGE_COUNT, -1);
        if (totalPages > 0) {
            totalPages--;
        }
        pageNames = getIntent().getStringArrayExtra(Constants.PAGE_NAMES);
        //
        loadPage();
        //
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadPage() {
        getSupportActionBar().setSubtitle(pageNames[curPage]);
        //
        CurrentSelection curSel = ApplicationCache.curSel;
        String imageURL = "";
        String textURL = "";
        if (ApplicationCache.publication == Publication.Mirror) {
            // Get the image URL
            imageURL = String.format(Constants.FORMAT_URL_PICTURE, curSel.getSkin(), curSel.getShortPath(), curSel.getYear(), curSel.getMonth(), curSel.getDay(), curSel.getShortPath(), curSel.getYear(), curSel.getMonth(), curSel.getDay(), Integer.valueOf(curPage + 1), mirrorRes);
            textURL = String.format(Constants.FORMAT_URL_TEXT, curSel.getSkin(), curSel.getShortPath(), curSel.getYear(), curSel.getMonth(), curSel.getDay(), curSel.getShortPath(), curSel.getYear(), curSel.getMonth(), curSel.getDay(), Integer.valueOf(curPage + 1), mirrorRes);
        } else {
            // Get the image URL
            imageURL = String.format(Constants.FORMAT_URL_PICTURE, curSel.getSkin(), curSel.getShortPath(), curSel.getYear(), curSel.getMonth(), curSel.getDay(), curSel.getShortPath(), curSel.getYear(), curSel.getMonth(), curSel.getDay(), Integer.valueOf(curPage + 1), otherRes);
            textURL = String.format(Constants.FORMAT_URL_TEXT, curSel.getSkin(), curSel.getShortPath(), curSel.getYear(), curSel.getMonth(), curSel.getDay(), curSel.getShortPath(), curSel.getYear(), curSel.getMonth(), curSel.getDay(), Integer.valueOf(curPage + 1), otherRes);
        }
        //
        String htmlText = String.format(htmlContent, imageURL, textURL);
        pageImageDisplay.loadData(htmlText, "text/html", "UTF-8");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.next_prev_menu, menu);
        previousMenu = menu.getItem(0);
        nextMenu = menu.getItem(1);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (curPage == totalPages) {
            nextMenu.setEnabled(false);
        }if (curPage == 0) {
            previousMenu.setEnabled(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_next:
            {
                if (curPage < totalPages) {
                    curPage++;
                    loadPage();
                }
                if (curPage == totalPages) {
                    nextMenu.setEnabled(false);
                } else {
                    nextMenu.setEnabled(true);
                }
                return true;
            }
            case R.id.action_previous:
            {
                if (curPage > 0) {
                    curPage--;
                    loadPage();
                }
                if (curPage == 0) {
                    previousMenu.setEnabled(false);
                } else {
                    previousMenu.setEnabled(true);
                }
                return true;
            }
            default: {
                finish();
                return super.onOptionsItemSelected(item);
            }
        }
    }

}
