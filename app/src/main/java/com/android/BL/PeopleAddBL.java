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
 * Created by Balram on 7/16/2015.
 */
public class PeopleAddBL {

    public String getTag(String deviceID,String newsID)
    {
        String result=getStoryDataJson(deviceID, newsID);
        String status=validateUpdated(result);
        return status;
    }

    public String removeTag(String newsID)
    {
        String result=getDeleteDataJson(newsID);
        String status=validateDelete(result);
        return status;
    }

    private String validateDelete(String result)
    {
        // System.out.println("RESULT------------------>" + result);

        String status="";
        try
        {
            JSONParser parser=new JSONParser();
            Object jsonObject=parser.parse(result);

            JSONArray jsonArray= (JSONArray) jsonObject;
            Constant.PeopleTagId=new String[jsonArray.size()];
            Constant.PeopleTagName=new String[jsonArray.size()];


                JSONObject jsonObjected = (JSONObject) parser.parse(jsonArray.get(0).toString());
                status=jsonObjected.get("status").toString();

            //  System.out.println("Content--->" +jsonObjected.get("content").toString());


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return status;
    }

    private String validateUpdated(String result)
    {
        System.out.println("RESULT------------------>" + result);

        String status="";
        try
        {
            JSONParser parser=new JSONParser();
            Object jsonObject=parser.parse(result);

            JSONArray jsonArray= (JSONArray) jsonObject;
            Constant.PeopleTagId=new String[jsonArray.size()];
            Constant.PeopleTagName=new String[jsonArray.size()];

            for(int i=0;i<jsonArray.size();i++) {
                JSONObject jsonObjected = (JSONObject) parser.parse(jsonArray.get(i).toString());
                Constant.PeopleTagId[i]=jsonObjected.get("id").toString();
                Constant.PeopleTagName[i]=jsonObjected.get("people").toString();
            }
            //  System.out.println("Content--->" +jsonObjected.get("content").toString());


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return status;
    }
    private String getStoryDataJson(String deviceId,String tag)
    {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String text = null;
        String url="regID="+deviceId+"&news_id="+tag;

        try {
            URI uri = new URI("http", "www.newsd.in", "/demo1/ws/add_people",url, null);
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

    private String getDeleteDataJson(String tag)
    {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String text = null;
        String url="id="+tag;

        try {
            URI uri = new URI("http", "www.newsd.in", "/demo1/ws/delete_people.php",url, null);
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

}
