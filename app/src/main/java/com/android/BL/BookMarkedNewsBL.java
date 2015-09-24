package com.android.BL;

import com.android.BE.BookMarkedNewsBE;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * Created by Balram on 7/14/2015.
 */
public class BookMarkedNewsBL {
    BookMarkedNewsBE objBookMarkedNewsBE;

    public String getBookMarkList(String newsID,BookMarkedNewsBE BookMarkedNewsBE)
    {
        objBookMarkedNewsBE=BookMarkedNewsBE;
        String result=getBookmarkJson(newsID);
        String status=validateUpdatedData(result);


        return status;
    }
    protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
        InputStream in = entity.getContent();
        StringBuffer out = new StringBuffer();
        int n = 1;
        while (n>0) {
            byte[] b = new byte[4096];
            n =  in.read(b);
            if (n>0) out.append(new String(b, 0, n));
        }
        return out.toString();
    }

    private String getBookmarkJson(String deviceID)
    {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();

        String url="news_id="+deviceID;
        String text = null;

        try {

            URI uri = new URI("http", "www.newsd.in", "/demo1/ws/open_bookmark_news",url, null);
            String ll = uri.toASCIIString();

            System.out.println("BookMark URL"+ll);
            HttpGet httpGet = new HttpGet(ll);

            HttpResponse response = httpClient.execute(httpGet, localContext);
            HttpEntity entity = response.getEntity();
            text = getASCIIContentFromEntity(entity);
        } catch (Exception e) {
            System.out.println("in last catch block");
            e.printStackTrace();
            return e.getLocalizedMessage();
        }
        return text;
    }
    public String validateUpdatedData(String result)
    {
        // System.out.println("RESULT------------------>" + result);
        String status="0";
        try
        {
            JSONParser parser=new JSONParser();
            Object jsonObject=parser.parse(result);

            JSONArray jsonArray= (JSONArray) jsonObject;

            System.out.println("Bookmark SIZEEEEE" + jsonArray.size());

            JSONObject jsonObjected = (JSONObject) parser.parse(jsonArray.get(0).toString());

            objBookMarkedNewsBE.setNewsId(jsonObjected.get("news_id").toString());
            objBookMarkedNewsBE.setImage(jsonObjected.get("image").toString());
            objBookMarkedNewsBE.setContent(jsonObjected.get("content").toString());
            objBookMarkedNewsBE.setSource(jsonObjected.get("source").toString());
            objBookMarkedNewsBE.setDate(jsonObjected.get("date").toString());
            objBookMarkedNewsBE.setTag(jsonObjected.get("tag").toString());
            objBookMarkedNewsBE.setTitle(jsonObjected.get("title").toString());
            objBookMarkedNewsBE.setUrl(jsonObjected.get("main_news").toString());


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return status;
    }

}
