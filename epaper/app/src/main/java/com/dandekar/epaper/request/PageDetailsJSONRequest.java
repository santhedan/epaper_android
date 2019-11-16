package com.dandekar.epaper.request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.dandekar.epaper.data.Constants;
import com.dandekar.epaper.data.CurrentSelection;
import com.dandekar.epaper.data.displaymodel.Article;
import com.dandekar.epaper.data.toimodel.MetaInfo;
import com.dandekar.epaper.data.toimodel.Page;
import com.dandekar.epaper.data.toimodel.PageItem;
import com.dandekar.epaper.data.toimodel.Publication;
import com.dandekar.epaper.data.toimodel.Section;
import com.dandekar.epaper.util.ApplicationCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageDetailsJSONRequest extends GsonRequest<Publication> {

    private static final String USER_AGENT = "User-Agent";
    private static final String UA_VALUE = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36";
    private String cookie = "";

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url           URL of the request to make
     * @param listener
     * @param errorListener
     */
    public PageDetailsJSONRequest(String url, String cookies, Response.Listener<Publication> listener, Response.ErrorListener errorListener) {
        super(url, Publication.class, null, listener, errorListener);
        Log.d(Constants.TAG, "URL -> " + url);
        this.cookie = cookies;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        final Map<String, String> headers = new HashMap<String, String>();
        Log.d(Constants.TAG, "Cookie being sent -> " + this.cookie);
        headers.put("Cookie", this.cookie);
        headers.put(USER_AGENT, UA_VALUE);
        return headers;
    }

    @Override
    protected Response<Publication> parseNetworkResponse(NetworkResponse response) {
        Response<Publication> publicationResponse = super.parseNetworkResponse(response);
        //
        Publication publication = publicationResponse.result;
        String[] pages = null;
        //
        if (publication.getSections() != null && publication.getSections().size() > 0) {
            //
            pages = new String[publication.getPagesCount()];
            for (Section section : publication.getSections()) {
                // how many sub section
                int subSection = section.getPages().size() / 2;
                for (int counter = 0; counter < subSection; counter++) {
                    int from = section.getPages().get(2 * counter) - 1;
                    int size = section.getPages().get(2 * counter + 1);
                    //
                    for (int subcounter = 0; subcounter < size; subcounter++) {
                        pages[from + subcounter] = section.getName();
                    }
                }
            }
            //
            int counter = 1;
            for (String secName: pages) {
                Log.d(Constants.TAG, counter + " -> " + secName);
                counter++;
            }
            publication.setPageNames(pages);
        }
        //
        List<com.dandekar.epaper.data.displaymodel.Page> displayPages = new ArrayList<>();
        int position = 0;
        CurrentSelection curSel = ApplicationCache.curSel;
        if (publication.getPages() != null && publication.getPages().size() > 0) {
            for (Page page : publication.getPages()) {
                //
                List<Article> articles = new ArrayList<>();
                for (PageItem pageItem : page.getEn()) {
                    if (isArticle(pageItem, pages[position])) {
                      Log.d(Constants.TAG, "Real article -> " + pageItem.toString());
                      String articleURL = String.format(ApplicationCache.articleURL, curSel.getSkin(), curSel.getShortPath(), curSel.getYear(), curSel.getMonth(), curSel.getDay(), curSel.getShortPath(), curSel.getYear(), curSel.getMonth(), curSel.getDay(), pageItem.getId(), curSel.getRandom());
                      List<MetaInfo> metaInfo = (pageItem.getMeta() == null? new ArrayList<MetaInfo>(): pageItem.getMeta());
                      String articleSN = "";
                      if (pageItem.getSn() != null)
                          articleSN = android.text.Html.fromHtml(pageItem.getSn()).toString();
                      Article article = new Article(pageItem.getId(), pageItem.getN(), articleSN, articleURL, metaInfo);
                      articles.add(article);
                    }
                }
                //"https://epaper.timesgroup.com/Olive/ODN/%s/get/%s-%d-%02d-%02d/image.ashx?kind=preview&href=%s/%d/%02d/%02d&page=%d&ts=%d"
                String tnURL = String.format(ApplicationCache.thumbnailURL, curSel.getSkin(), curSel.getShortPath(), curSel.getYear(), curSel.getMonth(), curSel.getDay(), curSel.getShortPath(), curSel.getYear(), curSel.getMonth(), curSel.getDay(), position+1, curSel.getRandom());
                com.dandekar.epaper.data.displaymodel.Page displayPage = new com.dandekar.epaper.data.displaymodel.Page(position, pages[position], tnURL, articles);
                displayPages.add(displayPage);
                //
                position++;
            }
        }
        for (com.dandekar.epaper.data.displaymodel.Page p : displayPages) {
            Log.d(Constants.TAG, p.toString());
        }
        publication.setDisplayPages(displayPages);
        //
        return publicationResponse;
    }

    private boolean isArticle(PageItem pageItem, String page) {
        // T will be null
        boolean flag1 = (pageItem.getT() == null);
        boolean flag2 = (!"AHMEDABAD MIRROR".equalsIgnoreCase(pageItem.getN()) &&
                !"MUMBAI MIRROR".equalsIgnoreCase(pageItem.getN()) &&
                !"BANGALORE MIRROR".equalsIgnoreCase(pageItem.getN()) &&
                !"PUNE MIRROR".equalsIgnoreCase(pageItem.getN()) &&
                !"THE ECONOMIC TIMES".equalsIgnoreCase(pageItem.getN()) &&
                !"THE TIMES OF INDIA".equalsIgnoreCase(pageItem.getN()) &&
                (!"DATELINE".equals(pageItem.getN())) &&
                (!page.equalsIgnoreCase(pageItem.getN())));
        return (flag1 && flag2);
    }
}
