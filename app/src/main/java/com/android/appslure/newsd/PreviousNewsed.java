package com.android.appslure.newsd;

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
 * Created by appslure on 7/15/2015.
 */
public class PreviousNewsed {
    String result;
    String status;
    public String getPreviousNewsed() {

        try {
            String result = fetRecord();
            status = validate(result);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    private String fetRecord()
    {
        String text = null;
        String type="previous";
        //http://newsd.in/demo1/ws/event.php?type=previous
        //http://newsd.in/demo1/ws/event.php?type=up
        //http://unitedbysport.in/demo19/index.php/webservice/login?email=ballu@ballu.com&password=ballu
        //?fullname=Balram%20patel&email=ballu@ballu.com&password=dfss&age=21&gender=male&location=delhi";
        String url="type="+type;
        try
        {
            URI uri = new URI("http", "www.newsd.in", "/demo1/ws/event",url, null);
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

    public String validate(String strValue)
    {

        System.out.println("the value of strValue===  "+strValue);
        String  status;

        JSONParser jsonP=new JSONParser();
        try {
            Object obj =jsonP.parse(strValue);
            JSONArray jsonArrayObject = (JSONArray)obj;
            Constant.preImgUrl=new String[jsonArrayObject.size()];
            Constant.preDate=new String[jsonArrayObject.size()];
            Constant.preId=new String[jsonArrayObject.size()];

            for(int i=0;i<=jsonArrayObject.size()-1;i++)
            {
                JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(i).toString());
                Constant.preImgUrl[i] = jsonObject.get("image1").toString();
                Constant.preDate[i]=jsonObject.get("event_date").toString();
                Constant.preId[i]=jsonObject.get("news_id").toString();
            }

        } catch (Exception e) {
            System.out.println("in second catch block");
            e.printStackTrace();
        }
        return result;
    }



}





