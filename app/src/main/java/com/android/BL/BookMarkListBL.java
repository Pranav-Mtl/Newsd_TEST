package com.android.BL;

import com.android.CONSTANTS.Constant;

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
 * Created by Balram on 7/6/2015.
 */
public class BookMarkListBL {

    String bookmarkList;

    public String getBookMarkList(String deviceID)
    {
        String result=getBookmarkJson(deviceID);
        String status=validateUpdatedData(result);
            if(status.equals("1"))
            {
                getFinalList(bookmarkList);
            }

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

        String url="regID="+deviceID;
        String text = null;

        try {

            URI uri = new URI("http", "www.newsd.in", "/demo1/ws/bookmark_list",url, null);
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

                status=jsonObjected.get("status").toString();

                if(status.equals("1"))
                {
                    bookmarkList=jsonObjected.get("news_list").toString();
                    System.out.println("BookMark List" + bookmarkList);
                }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return status;
    }

    private void getFinalList(String newsList)
    {
        try
        {
            JSONParser parser=new JSONParser();
            Object jsonObject=parser.parse(newsList);

            JSONArray jsonArray= (JSONArray) jsonObject;

            System.out.println("SIZEEEEE" + jsonArray.size());

            Constant.BookMarked_id=new String[jsonArray.size()];
            Constant.BookMarked_date=new String[jsonArray.size()];
            Constant.BookMarked_Title=new String[jsonArray.size()];
            Constant.BookMarked_Url=new String[jsonArray.size()];
            Constant.BookMarked_Publisher=new String[jsonArray.size()];
            Constant.BookMarked_Category=new String[jsonArray.size()];


            for(int i=0;i<jsonArray.size();i++)
            {
                JSONObject jsonObjected = (JSONObject) parser.parse(jsonArray.get(i).toString());
                //  System.out.println("Content--->" +jsonObjected.get("content").toString());
               Constant.BookMarked_id[i]=jsonObjected.get("list").toString();
                Constant.BookMarked_date[i]=jsonObjected.get("date").toString();
                Constant.BookMarked_Title[i]=jsonObjected.get("title").toString();
                Constant.BookMarked_Url[i]=jsonObjected.get("url").toString();
                Constant.BookMarked_Publisher[i]=jsonObjected.get("publisher").toString();
                Constant.BookMarked_Category[i]=jsonObjected.get("category").toString();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
