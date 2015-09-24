package com.android.BL;

import com.android.BE.MyAccountBE;

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
 * Created by Balram on 7/8/2015.
 */
public class MyAccountBL {
    MyAccountBE objMyAccountBE;

    public String getAccount(String deviceID,MyAccountBE myAccountBE)
    {
        objMyAccountBE=myAccountBE;
        String result=getUpdateDataJson(deviceID);
        String status=validateUpdated(result);
        return status;
    }

    private String getUpdateDataJson(String deviceId)
    {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String text = null;
        String url="regID="+deviceId;

        try {
            URI uri = new URI("http", "www.newsd.in", "/demo1/ws/selected_category",url, null);
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

    public String validateUpdated(String result)
    {
        // System.out.println("RESULT------------------>" + result);

        String status="";
        try
        {
            JSONParser parser=new JSONParser();
            Object jsonObject=parser.parse(result);

            JSONArray jsonArray= (JSONArray) jsonObject;

            JSONObject jsonObjected = (JSONObject) parser.parse(jsonArray.get(0).toString());
            //  System.out.println("Content--->" +jsonObjected.get("content").toString());
             status=jsonObjected.get("status").toString();
                if(status.equals("1"))
                {
                    objMyAccountBE.setCategory(jsonObjected.get("category").toString());
                    objMyAccountBE.setStates(jsonObjected.get("state").toString());
                }
                else
                {

                }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return status;
    }

}
