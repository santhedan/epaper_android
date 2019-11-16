package com.dandekar.epaper.request;

import android.util.Base64;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.dandekar.epaper.data.Constants;
import com.dandekar.epaper.data.CurrentSelection;
import com.dandekar.epaper.util.ApplicationCache;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public final class GetArticleContent extends StringRequest {

    private static final String USER_AGENT = "User-Agent";
    private static final String UA_VALUE = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36";
    private static final String XMLPI = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n";

    private static final String searchTag1 = "<div class=\"placeholder img\" data-id=\"";
    private static final String searchTag2 = "\"";
    private static final String searchTag3 = "<!--image-->";
    private static final String replaceTag3 = "InsertImageLinkHere";

    private static final String imageArticle = "<div data-view-mode=\"image\"";

    private static final String imgSearchTag1 = "<div data-id=\"";
    private static final String imgSearchTag3 = "<!---->";

    private static final String HREFFormat = "%s/%d/%02d/%02d";
    private static final String imageURL = "https://epaper.timesgroup.com/Olive/ODN/%s/get/%s-%d-%02d-%02d/image.ashx?kind=block&href=%s&id=%s&ext=.jpg&ts=%d";
    private static final String imageURLForImgArt = "https://epaper.timesgroup.com/Olive/ODN/%s/get/%s-%d-%02d-%02d/image.ashx?kind=block&href=%s&id=%s&ext=.png&ts=%d";

    public GetArticleContent(String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.GET, url, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        final Map<String, String> headers = new HashMap<String, String>();
        Log.d(Constants.TAG, "Cookie being sent -> " + ApplicationCache.cookie);
        headers.put("Cookie", ApplicationCache.cookie);
        headers.put(USER_AGENT, UA_VALUE);
        return headers;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String articleContent = new String(response.data);
        articleContent = articleContent.replace(XMLPI, "");
        if (articleContent.startsWith(imageArticle)) {
            articleContent = getImageEmbeddedArticleContent(articleContent, imgSearchTag3, imgSearchTag1, imageURLForImgArt);
        } else {
            articleContent = getImageEmbeddedArticleContent(articleContent, searchTag3, searchTag1, imageURL);
        }
        //
        Log.d(Constants.TAG, "articleContent -> " + articleContent);
        return Response.success(articleContent, null);
    }

    private String getImageEmbeddedArticleContent(String articleContent, String placeHolderTag, String searchTag, String formatURL) {
        // Article has image and text
        articleContent = articleContent.replace(placeHolderTag, replaceTag3);
        //
        int position = articleContent.indexOf(searchTag);
        while (position > 0) {
            int secondPos = articleContent.indexOf(searchTag2, position + searchTag.length());
            String imageID = articleContent.substring(position + searchTag.length(), secondPos);
            CurrentSelection curSel = ApplicationCache.curSel;
            String href = String.format(HREFFormat, curSel.getShortPath(), curSel.getYear(), curSel.getMonth(), curSel.getDay());
            href = href.replace("/", "%2F");
            String url = String.format(formatURL, curSel.getSkin(), curSel.getShortPath(), curSel.getYear(), curSel.getMonth(), curSel.getDay(), href, imageID, curSel.getRandom());
            String formattedImageTag = getImageDataTag(url);
            //
            Log.d(Constants.TAG, "formattedImageTag -> " + formattedImageTag);
            //
            articleContent = articleContent.replaceFirst(replaceTag3, formattedImageTag);
            //
            position = articleContent.indexOf(searchTag, position + searchTag.length());
        }
        return articleContent;
    }

    private String getImageDataTag(String uri) {
        String imageTag = "<img src=\"data:image/jpeg;base64,%s\" />";
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(uri);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Cookie",ApplicationCache.cookie);
            BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int read = in.read(buffer);
            while (read != -1) {
                byteArrayOutputStream.write(buffer, 0, read);
                read = in.read(buffer);
            }
            byte[] bytes = byteArrayOutputStream.toByteArray();
            String base64img = Base64.encodeToString(bytes, Base64.NO_WRAP);
            String tag = String.format(imageTag, base64img);
            return tag;
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return "";
    }
}
