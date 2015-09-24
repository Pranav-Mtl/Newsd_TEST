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
 * Created by appslure on 7/17/2015.
 */
public class PreviousSearchBL {

    String status;
    String searchText;
    public String preSearch(String string) {

        try {
            String result = fetRecord(string);
            status = validate(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }


    private String fetRecord(String text)
    {

        //http://newsd.in/demo1/ws/event_search.php?search=newsd
        //http://newsd.in/demo1/ws/event_detail.php?news_id=6
        //http://newsd.in/demo1/ws/event.php?type=previous
        //http://newsd.in/demo1/ws/event.php?type=up
        //http://unitedbysport.in/demo19/index.php/webservice/login?email=ballu@ballu.com&password=ballu
        //?fullname=Balram%20patel&email=ballu@ballu.com&password=dfss&age=21&gender=male&location=delhi";
        String url="search="+text;
        try
        {
            URI uri = new URI("http", "www.newsd.in", "/demo1/ws/event_search",url, null);
            System.out.println("URI"+uri);
            String value=uri.toASCIIString();
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpGet httpGet=new HttpGet(value);
            HttpResponse response = httpClient.execute(httpGet, localContext);
            HttpEntity entity = response.getEntity();
            text = getASCIIContentFromEntity(entity);
            System.out.println("TEXT RETURN BY THE------------>"+text);
        }
        catch (Exception e)
        {
            System.out.println("in web services catch block");
            e.printStackTrace();
            return e.getLocalizedMessage();
        }
        return text;
    }


    public String validate(String strValue)
    {

        System.out.println("the value of strValue===  "+strValue);
        String  status="";
        if(strValue.equals("[]"))
        {
            status="empty";
        }
        else {
            status="data";
            JSONParser jsonP = new JSONParser();
            try {
                Object obj = jsonP.parse(strValue);
                JSONArray jsonArrayObject = (JSONArray) obj;
                Constant.predescription=new String[jsonArrayObject.size()];
                Constant.prenewsId=new String[jsonArrayObject.size()];
                Constant.preimg=new String[jsonArrayObject.size()];
                Constant.prenews_date=new String[jsonArrayObject.size()];

                for(int i=0;i<jsonArrayObject.size();i++) {
                    JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(i).toString());
                    Constant.predescription[i]=jsonObject.get("description").toString();
                    Constant.prenewsId[i]=jsonObject.get("news_id").toString();
                    Constant.preimg[i]=jsonObject.get("image1").toString();
                    Constant.prenews_date[i]=jsonObject.get("event_date").toString();
                }

            } catch (Exception e) {
                System.out.println("in second catch block");
                e.printStackTrace();
            }
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

}
