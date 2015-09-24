package com.android.BL;

import android.util.Log;

import com.android.CONSTANTS.Constant;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * Created by Balram on 7/5/2015.
 */
public class SearchBL {
    private static JSONArray jsonarray;
    private static JSONObject jsonobject;

    public  String GetJson(String title,String pageNo){


        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String text = null;

        String url="v=1.0&rsz=large&q="+title+"&start="+pageNo;

        try {
            URI uri = new URI("http", "www.ajax.googleapis.com", "/ajax/services/search/news",url, null);
            System.out.println("URLLLLL"+uri);
            String ll=uri.toASCIIString();
            System.out.println("gggg"+ll);
            HttpGet httpGet = new HttpGet(ll);
            try {
                HttpResponse response = httpClient.execute(httpGet, localContext);
                HttpEntity entity = response.getEntity();
                text = getASCIIContentFromEntity(entity);
            } catch (Exception e) {
                return e.getLocalizedMessage();
            }
        }catch (IllegalArgumentException e)
        {
            System.out.println("exxmccc"+e);
        }
        catch (Exception e)
        {

        }
        Log.d("Response: ", "> " + text);

        try {
            JSONObject jsonObj = new JSONObject(text);
            JSONObject responseData = jsonObj.getJSONObject("responseData");
            //  JSONObject results = responseData.getJSONObject("results");

           jsonarray = responseData.getJSONArray("results");
           System.out.println("JSON ARRAY LENGTH"+jsonarray.length());

            Constant.lastSearchValue=Constant.totalSearchValue;
            Constant.totalSearchValue=Constant.totalSearchValue+jsonarray.length();


            Constant.titleSearch=new String[jsonarray.length()];
            Constant.contentSearch=new String[jsonarray.length()];
            Constant.publisherSearch=new String[jsonarray.length()];
            Constant.dateSearch=new String[jsonarray.length()];
            Constant.imageURLSearch=new String[jsonarray.length()];
            Constant.newsURLSearch=new String[jsonarray.length()];

            for (int i = 0; i < jsonarray.length(); i++) {

                jsonobject = jsonarray.getJSONObject(i);

                /*JSONObject image = jsonobject.getJSONObject("image");
                String news_image=image.getString("url");*/

                System.out.println("TITLE--------->" + jsonobject.getString("titleNoFormatting").toString());

                System.out.println("Redirect_URL--------->" + jsonobject.getString("signedRedirectUrl").toString());

                System.out.println("CONTENT--------->" + jsonobject.getString("content").toString());

                System.out.println("PUBLISH_DATE--------->" + jsonobject.getString("publishedDate").toString());

                System.out.println("PUBLISHER--------->" + jsonobject.getString("publisher").toString());

                //System.out.println("IMAGE--------->" + news_image);

                Constant.titleSearch[Constant.lastSearchValue+i]=jsonobject.getString("titleNoFormatting").toString().replace("&nbsp;", "").replace("&#39;", "'").replace("amp;","");
                Constant.contentSearch[Constant.lastSearchValue+i]=jsonobject.getString("content").toString().replaceAll("&#39;", "").replace("&nbsp;","").replace("&quot;","");
                Constant.publisherSearch[Constant.lastSearchValue+i] = jsonobject.getString("publisher").toString();
                Constant.dateSearch[Constant.lastSearchValue+i]=jsonobject.getString("publishedDate").toString().replace("-0700", "");
               // Constant.imageURLSearch[i]=news_image;
                Constant.newsURLSearch[Constant.lastSearchValue+i]=jsonobject.getString("signedRedirectUrl").toString();

            }
        } catch (JSONException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return null;
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

    public String[] createNewArray(String[] oldArray,int size){
        String[] newArray = new String[oldArray.length + size];
        for(int i = 0; i < oldArray.length; i++) {
            newArray[i] = oldArray[i];
        }

        return newArray;
    }

    public  String GetJsonOnLoad(String title,String pageNo){


        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String text = null;

        String url="v=1.0&rsz=large&q="+title+"&start="+pageNo;

        try {
            URI uri = new URI("http", "www.ajax.googleapis.com", "/ajax/services/search/news",url, null);
            System.out.println("URLLLLL"+uri);
            String ll=uri.toASCIIString();
            System.out.println("gggg"+ll);
            HttpGet httpGet = new HttpGet(ll);
            try {
                HttpResponse response = httpClient.execute(httpGet, localContext);
                HttpEntity entity = response.getEntity();
                text = getASCIIContentFromEntity(entity);
            } catch (Exception e) {
                return e.getLocalizedMessage();
            }
        }catch (IllegalArgumentException e)
        {
            System.out.println("exxmccc"+e);
        }
        catch (Exception e)
        {

        }
        Log.d("Response: ", "> " + text);

        try {
            JSONObject jsonObj = new JSONObject(text);
            JSONObject responseData = jsonObj.getJSONObject("responseData");
            //  JSONObject results = responseData.getJSONObject("results");

            jsonarray = responseData.getJSONArray("results");
            System.out.println("JSON ARRAY LENGTH"+jsonarray.length());

            Constant.lastSearchValue=Constant.totalSearchValue;
            Constant.totalSearchValue=Constant.totalSearchValue+jsonarray.length();

            Constant.titleSearch=createNewArray(Constant.titleSearch, jsonarray.length());
            Constant.contentSearch=createNewArray(Constant.contentSearch, jsonarray.length());
            Constant.publisherSearch=createNewArray(Constant.publisherSearch, jsonarray.length());
            Constant.dateSearch=createNewArray(Constant.dateSearch, jsonarray.length());
            Constant.imageURLSearch=createNewArray(Constant.imageURLSearch, jsonarray.length());
            Constant.newsURLSearch=createNewArray(Constant.newsURLSearch, jsonarray.length());

    /*        Constant.titleSearch=new String[jsonarray.length()];
            Constant.contentSearch=new String[jsonarray.length()];
            Constant.publisherSearch=new String[jsonarray.length()];
            Constant.dateSearch=new String[jsonarray.length()];
            Constant.imageURLSearch=new String[jsonarray.length()];
            Constant.newsURLSearch=new String[jsonarray.length()];*/

            for (int i = 0; i < jsonarray.length(); i++) {

                jsonobject = jsonarray.getJSONObject(i);

                /*JSONObject image = jsonobject.getJSONObject("image");
                String news_image=image.getString("url");*/

                System.out.println("TITLE--------->" + jsonobject.getString("titleNoFormatting").toString());

                System.out.println("Redirect_URL--------->" + jsonobject.getString("signedRedirectUrl").toString());

                System.out.println("CONTENT--------->" + jsonobject.getString("content").toString());

                System.out.println("PUBLISH_DATE--------->" + jsonobject.getString("publishedDate").toString());

                System.out.println("PUBLISHER--------->" + jsonobject.getString("publisher").toString());

                //System.out.println("IMAGE--------->" + news_image);

                Constant.titleSearch[Constant.lastSearchValue+i]=jsonobject.getString("titleNoFormatting").toString().replace("&nbsp;", "").replace("&#39;", "'").replace("amp;","");
                Constant.contentSearch[Constant.lastSearchValue+i]=jsonobject.getString("content").toString().replaceAll("&#39;", "").replace("&nbsp;","").replace("&quot;","");
                Constant.publisherSearch[Constant.lastSearchValue+i] = jsonobject.getString("publisher").toString();
                Constant.dateSearch[Constant.lastSearchValue+i]=jsonobject.getString("publishedDate").toString().replace("-0700", "");
                // Constant.imageURLSearch[i]=news_image;
                Constant.newsURLSearch[Constant.lastSearchValue+i]=jsonobject.getString("signedRedirectUrl").toString();

            }
        } catch (JSONException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }



        }
