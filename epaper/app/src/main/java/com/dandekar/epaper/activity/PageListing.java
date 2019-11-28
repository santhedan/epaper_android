package com.dandekar.epaper.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.dandekar.epaper.divider.GrayDividerDecoration;
import com.dandekar.epaper.divider.GrayVerticalDividerDecoration;
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
        //
        recyclerView.addItemDecoration(new GrayVerticalDividerDecoration(getResources()));
        articleRecyclerView.addItemDecoration(new GrayDividerDecoration(getResources()));
        //
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
        // Check if the publication object is available on disk
        final String pubFileName = getPublicationFileName(ApplicationCache.curSel);
        if (FileUtils.fileExists(getApplicationContext(), pubFileName)) {
            // Read file from the disk
            Thread t = new Thread() {
                @Override
                public void run() {
                    readPubFileFromDisk(pubFileName);
                }
            };
            t.start();
        } else {
            // Create the request
            PageDetailsJSONRequest request = new PageDetailsJSONRequest(URL, ApplicationCache.cookie, this, this);
            volley.getRequestQueue().add(request);
            this.requestType = RequestType.Publication;
        }
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
                final String pubFileName = getPublicationFileName(ApplicationCache.curSel);
                if (!FileUtils.fileExists(getApplicationContext(), pubFileName)) {
                    Thread tSave = new Thread() {
                        @Override
                        public void run() {
                            FileUtils.writeObjectToFile(getApplicationContext(), publication, pubFileName);
                        }
                    };
                    tSave.start();
                }
                break;
            case Thumbnail:
                final BitmapHolder holder = (BitmapHolder) response;
                Log.d(Constants.TAG, "Got reqponse -> " + holder.getIntTag());
                this.publication.getDisplayPages().get(holder.getIntTag()).setThumbnail(holder.getBitmap());
                adapter.notifyItemChanged(holder.getIntTag());
                // Initiate save of bitmap data
                final String TNFileName = getTNFileName(holder.getIntTag(), ApplicationCache.curSel);
                if (!FileUtils.fileExists(getApplicationContext(), TNFileName)) {
                    Thread tSaveTN = new Thread() {
                        @Override
                        public void run() {
                            FileUtils.writeBytesToFile(getApplicationContext(), holder.getBitmapData(), TNFileName);
                        }
                    };
                    tSaveTN.start();
                }
                break;
        }
    }

    private String getPublicationFileName(CurrentSelection curSel) {
        return String.format(Constants.PublicationFileNameFormat, curSel.getShortPath(), curSel.getYear(), curSel.getMonth(), curSel.getDay());
    }

    private String getTNFileName(int pageNo, CurrentSelection curSelTN) {
        return String.format(Constants.ThumbnailFileNameFormat, curSelTN.getShortPath(), curSelTN.getYear(), curSelTN.getMonth(), curSelTN.getDay(), pageNo);
    }

    private void initiateTNDownload() {
        this.requestType = RequestType.Thumbnail;
        int counter = 0;
        for (final Page page : this.publication.getDisplayPages()) {
            // Check if the file exists on the disk or not
            final String tnfile = getTNFileName(counter, ApplicationCache.curSel);
            if (FileUtils.fileExists(getApplicationContext(), tnfile)) {
                // Start reading of the file in different thread
                final int pageNo = counter;
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        readTNFromDisk(tnfile, pageNo);
                    }
                };
                t.start();
            } else {
                Log.d(Constants.TAG, "Requesting TN -> " + page.getThumbnailURL());
                BitmapRequest request = new BitmapRequest(page.getThumbnailURL(), counter, ApplicationCache.cookie, this, this);
                request.setTag(REQ_TAG);
                volley.getRequestQueue().add(request);
            }
            counter++;
        }
    }

    private void readTNFromDisk(String tnFile, int pageNo) {
        // Read the file from disk
        byte[] thumbnailBytes = FileUtils.readBytesFromFile(getApplicationContext(), tnFile);
        // Convert the byte array to bitmap
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inScaled = false;   // We want to get the original image not scaled image
        Bitmap bitmap = BitmapFactory.decodeByteArray(thumbnailBytes, 0, thumbnailBytes.length, opt);
        final BitmapHolder holder = new BitmapHolder(pageNo, null, thumbnailBytes, bitmap);
        // Call the onReasponse from UI thread
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                requestType = RequestType.Thumbnail;
                // Code here will run in UI thread
                onResponse(holder);
            }
        });
    }

    private void readPubFileFromDisk(String pubFileName) {
        final Publication pub = (Publication) FileUtils.readObjectFromFile(getApplicationContext(), pubFileName);
        // Call the onReasponse from UI thread
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                // Code here will run in UI thread
                requestType = RequestType.Publication;
                onResponse(pub);
            }
        });
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
