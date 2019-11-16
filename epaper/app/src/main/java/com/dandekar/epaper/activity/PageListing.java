package com.dandekar.epaper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dandekar.epaper.R;
import com.dandekar.epaper.adapter.ArticleAdapter;
import com.dandekar.epaper.adapter.PageThumbnailAdapter;
import com.dandekar.epaper.data.BitmapHolder;
import com.dandekar.epaper.data.Constants;
import com.dandekar.epaper.data.CurrentSelection;
import com.dandekar.epaper.data.displaymodel.Article;
import com.dandekar.epaper.data.displaymodel.Page;
import com.dandekar.epaper.data.toimodel.Publication;
import com.dandekar.epaper.request.BitmapRequest;
import com.dandekar.epaper.request.PageDetailsJSONRequest;
import com.dandekar.epaper.util.ApplicationCache;
import com.dandekar.epaper.util.FileUtils;
import com.dandekar.epaper.util.VolleySingleton;

import java.util.List;

public class PageListing extends AppCompatActivity implements Response.ErrorListener, Response.Listener, View.OnClickListener {

    private ProgressBar progressbar;
    private TextView tvPlsWait;

    private RecyclerView recyclerView;
    private RecyclerView articleRecyclerView;
    private PageThumbnailAdapter adapter;
    private ArticleAdapter articleAdapter;
    private TextView messageText;

    private String titleFormat = "%s - %s";
    private Publication publication;

    private static final String REQ_TAG = "PageListingRequest";
    private static final String pageNameFormat = "%s (%d)";
    private int pageNo = -1;

    private enum RequestType {
        Publication,
        Thumbnail
    }

    private VolleySingleton volley;

    private RequestType requestType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_listing);
        //
        recyclerView = findViewById(R.id.pageThumbnails);
        articleRecyclerView = findViewById(R.id.pageArticles);
        messageText = findViewById(R.id.messageText);
        //
        setTitle(String.format(titleFormat, ApplicationCache.publication.toString(), ApplicationCache.edition.toString()));
        //
        progressbar = findViewById(R.id.progressbar);
        tvPlsWait = findViewById(R.id.pleaseWaitText);
        // Get the URL from intent
        String URL = getIntent().getStringExtra(Constants.XMLURL);
        // Create the volly instance
        volley = VolleySingleton.getInstance(getApplicationContext());
        // Create the request
        //PageDetailsRequest request = new PageDetailsRequest(URL, ApplicationCache.cookie, this, this);
        PageDetailsJSONRequest request = new PageDetailsJSONRequest(URL, ApplicationCache.cookie, this, this);
        volley.getRequestQueue().add(request);
        this.requestType = RequestType.Publication;
        //
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progressbar.setVisibility(View.GONE);
        tvPlsWait.setVisibility(View.GONE);
    }

    @Override
    public void onResponse(Object response) {
        switch (this.requestType) {
            case Publication:
                this.publication = (Publication) response;
                progressbar.setVisibility(View.GONE);
                tvPlsWait.setVisibility(View.GONE);
                //
                adapter = new PageThumbnailAdapter(this.publication.getDisplayPages(), this);
                recyclerView.setAdapter(adapter);
                //
                recyclerView.setVisibility(View.VISIBLE);
                //
                messageText.setText(R.string.help_message);
                messageText.setVisibility(View.VISIBLE);
                // Initiate thumbnail download
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        initiateTNDownload();
                    }
                };
                t.start();
                // Initiate save of the publication object
                CurrentSelection curSel = ApplicationCache.curSel;
                final String pubFileName = String.format(Constants.PublicationFileNameFormat, curSel.getShortPath(), curSel.getYear(), curSel.getMonth(), curSel.getDay());
                Thread tSave = new Thread() {
                    @Override
                    public void run() {
                        FileUtils.writeObjectToFile(getApplicationContext(), publication, pubFileName);
                    }
                };
                tSave.start();
                break;
            case Thumbnail:
                final BitmapHolder holder = (BitmapHolder) response;
                Log.d(Constants.TAG, "Got reqponse -> " + holder.getIntTag());
                this.publication.getDisplayPages().get(holder.getIntTag()).setThumbnail(holder.getBitmap());
                adapter.notifyItemChanged(holder.getIntTag());
                // Initiate save of bitmap data
                CurrentSelection curSelTN = ApplicationCache.curSel;
                final String TNFileName = String.format(Constants.ThumbnailFileNameFormat, curSelTN.getShortPath(), curSelTN.getYear(), curSelTN.getMonth(), curSelTN.getDay(), holder.getIntTag());
                Thread tSaveTN = new Thread() {
                    @Override
                    public void run() {
                        FileUtils.writeBytesToFile(getApplicationContext(), holder.getBitmapData(), TNFileName);
                    }
                };
                tSaveTN.start();
                break;
        }
    }

    private void initiateTNDownload() {
        this.requestType = RequestType.Thumbnail;
        int counter = 0;
        for (Page page : this.publication.getDisplayPages()) {
            Log.d(Constants.TAG, "Requesting TN -> " + page.getThumbnailURL());
            BitmapRequest request = new BitmapRequest(page.getThumbnailURL(), counter, ApplicationCache.cookie, this, this);
            request.setTag(REQ_TAG);
            volley.getRequestQueue().add(request);
            counter++;
        }
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        Log.d(Constants.TAG, "Clicked -> " + tag);
        if (tag.startsWith("Page:")) {
            String pageNumber = tag.split(":")[1];
            int pageNoTemp = Integer.parseInt(pageNumber);
            if (pageNo != pageNoTemp) {
                // Page changed
                pageNo = pageNoTemp;
                //
                List<Article> articles = publication.getDisplayPages().get(pageNo).getArticles();
                if (articles != null && articles.size() > 0) {
                    //
                    String pageName = String.format(pageNameFormat, publication.getPageNames()[pageNo], pageNo + 1);
                    messageText.setText(pageName);
                } else {
                    messageText.setText(R.string.no_article_message);
                }
                // Create the adapter and refresh article listing
                articleAdapter = new ArticleAdapter(articles, this);
                articleRecyclerView.setAdapter(articleAdapter);
                articleRecyclerView.setVisibility(View.VISIBLE);
            }
        } else {
            // Handle article click
            String articleURL = tag.split("@")[1];
            String articleID = tag.split("@")[2];
            Intent intent = new Intent(this, DisplayArticleActivity.class);
            intent.putExtra(Constants.ARTICLE_URL, articleURL);
            intent.putExtra(Constants.ARTICLE_ID, articleID);
            startActivity(intent);
        }
    }
}
