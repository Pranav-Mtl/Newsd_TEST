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
public class FollowBL {

    public String insertTag(String deviceID,String tag)
    {
        String result =getDataJson(deviceID,tag);
        return "";
    }
    public String insertStory(String deviceID,String tag)
    {
        String result =getStoryDataJson(deviceID, tag);
        return "";
    }

    public String getTag(String deviceID)
    {
        String result=getFollowedJson(deviceID);
        String status=getFollowList(result);
        return status;
    }
    public String getStory(String deviceID)
    {
        String result=getStoryJson(deviceID);
        String status=getStoryList(result);
        return status;
    }
    private String getDataJson(String deviceId,String tag)
    {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String text = null;
        String url="regID="+deviceId+"&pagmark="+tag;

        try {
            URI uri = new URI("http", "www.newsd.in", "/demo1/ws/pagmark",url, null);
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

    private String getStoryDataJson(String deviceId,String tag)
    {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String text = null;
        String url="regID="+deviceId+"&news_id="+tag;

        try {
            URI uri = new URI("http", "www.newsd.in", "/demo1/ws/follow_news",url, null);
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

    private String getFollowedJson(String deviceId)
    {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String text = null;
        String url="regID="+deviceId;

        try {
            URI uri = new URI("http", "www.newsd.in", "/demo1/ws/fetch_pagmark",url, null);
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
    private String getStoryJson(String deviceId)
    {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String text = null;
        String url="regID="+deviceId;

        try {
            URI uri = new URI("http", "www.newsd.in", "/demo1/ws/fetch_follow_news",url, null);
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

    private String getFollowList(String newsList)
    {
        String status="";
        if(newsList.equals("[]"))
        {
          status="empty";
        }
        else {
            status="data";
            try {
                JSONParser parser = new JSONParser();
                Object jsonObject = parser.parse(newsList);

                JSONArray jsonArray = (JSONArray) jsonObject;

                System.out.println("SIZEEEEE" + jsonArray.size());

                Constant.FollowedNews = new String[jsonArray.size()];
                Constant.FollowedID=new String[jsonArray.size()];

                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObjected = (JSONObject) parser.parse(jsonArray.get(i).toString());
                    //  System.out.println("Content--->" +jsonObjected.get("content").toString());
                    Constant.FollowedNews[i] = jsonObjected.get("pag").toString();
                    Constant.FollowedID[i]=jsonObjected.get("id").toString();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return status;

    }
    private String getStoryList(String newsList)
    {
        String status="";
        if(newsList.equals("[]"))
        {
            status="empty";
        }
        else {
            status="data";
            try {
                JSONParser parser = new JSONParser();
                Object jsonObject = parser.parse(newsList);

                JSONArray jsonArray = (JSONArray) jsonObject;

                System.out.println("SIZEEEEE" + jsonArray.size());

                Constant.FollowedStory = new String[jsonArray.size()];
                Constant.FollowedNewsID = new String[jsonArray.size()];
                Constant.FollowedPublisher = new String[jsonArray.size()];
                Constant.FollowedCategory = new String[jsonArray.size()];
                Constant.FollowedTime = new String[jsonArray.size()];
                Constant.FollowedTitle = new String[jsonArray.size()];

                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObjected = (JSONObject) parser.parse(jsonArray.get(i).toString());
                    //  System.out.println("Content--->" +jsonObjected.get("content").toString());
                    Constant.FollowedNewsID[i] = jsonObjected.get("id").toString();
                    Constant.FollowedStory[i] = jsonObjected.get("tag").toString();
                    Constant.FollowedPublisher[i]=jsonObjected.get("publisher").toString();
                    Constant.FollowedCategory[i]=jsonObjected.get("category").toString();
                    Constant.FollowedTime[i]=jsonObjected.get("time").toString();
                    Constant.FollowedTitle[i] = jsonObjected.get("title").toString();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return status;

    }

}
