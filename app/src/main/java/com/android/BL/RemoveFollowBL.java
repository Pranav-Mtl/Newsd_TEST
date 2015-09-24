package com.android.BL;

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
 * Created by Balram on 7/13/2015.
 */
public class RemoveFollowBL {

    public String getNewsStoryId(String newsID)
    {
        String status=getDataJson(newsID);
        String result=validate(status);
        return result;
    }



    private String getDataJson(String newsid)
    {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String text = null;
        String url="id="+newsid;

        try {
            URI uri = new URI("http", "www.newsd.in", "/demo1/ws/delete_follow_news",url, null);
            String ll = uri.toASCIIString();

            System.out.println("Contact URL"+ll);
            HttpGet httpGet = new HttpGet(ll);



            try {
                HttpResponse response = httpClient.execute(httpGet, localContext);


                HttpEntity entity = response.getEntity();


                text = getASCIIContentFromEntity(entity);


            } catch (Exception e) {
                return e.getLocalizedMessage();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return text;


    }

    private String validate(String result)
    {
        String status="";
            try {
                JSONParser parser = new JSONParser();
                Object jsonObject = parser.parse(result);

                JSONArray jsonArray = (JSONArray) jsonObject;

                System.out.println("SIZEEEEE" + jsonArray.size());
               // Constant.FollowedNews = new String[jsonArray.size()];

                    JSONObject jsonObjected = (JSONObject) parser.parse(jsonArray.get(0).toString());
                    //  System.out.println("Content--->" +jsonObjected.get("content").toString());
                    //Constant.FollowedNews[i] = jsonObjected.get("pag").toString();
                status=jsonObjected.get("status").toString();


            } catch (Exception e) {
                e.printStackTrace();

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

    public String getNewsId(String newsID)
    {
        String status=getNewsDataJson(newsID);
        String result=validate(status);
        return result;
    }



    private String getNewsDataJson(String newsid)
    {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String text = null;
        String url="id="+newsid;

        try {
            URI uri = new URI("http", "www.newsd.in", "/demo1/ws/delete_pagmark",url, null);
            String ll = uri.toASCIIString();

            System.out.println("Contact URL"+ll);
            HttpGet httpGet = new HttpGet(ll);



            try {
                HttpResponse response = httpClient.execute(httpGet, localContext);


                HttpEntity entity = response.getEntity();


                text = getASCIIContentFromEntity(entity);


            } catch (Exception e) {
                return e.getLocalizedMessage();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return text;


    }

}
