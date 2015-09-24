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
 * Created by Balram on 7/16/2015.
 */
public class UpdateAccountBL {


    public String updateAccount(String deviceID,String category,String states,String regID)
    {

        String result=getUpdateDataJson(deviceID,category,states,regID);
        String status=validateUpdated(result);
        return status;
    }

    private String getUpdateDataJson(String deviceId,String category,String states,String regID)
    {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String text = null;
        String url="state="+states+"&regID="+deviceId+"&category="+category+"&gcm_id="+regID;

        try {
            URI uri = new URI("http", "www.newsd.in", "/demo1/ws/update_category",url, null);
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

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return status;
    }
}
