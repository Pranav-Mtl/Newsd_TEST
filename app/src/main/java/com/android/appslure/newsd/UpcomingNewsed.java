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
 * Created by appslure on 7/14/2015.
 */
public class UpcomingNewsed {
    String result;
    String status;
    String upComingJson;
    String previousJSON;
    public String getNewsed() {
        String data="";

        try {
            String result = fetRecord();
            status = validate(result);
            if(!upComingJson.equals("[]"))
            {
                validateUpcoming(upComingJson);
                data="upComingNotEmpty";
            }
            else
            {
                data="upComingEmpty";
            }
            if(!previousJSON.equals("[]"))
            {
                validatePrevious(previousJSON);
                data=data+","+"previousNotEmpty";
            }
            else
            {
                data=data+","+"previousEmpty";
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private String fetRecord()
    {
        String text = null;
        String type="up";
        //http://newsd.in/demo1/ws/event.php?type=up
        //http://unitedbysport.in/demo19/index.php/webservice/login?email=ballu@ballu.com&password=ballu
        //?fullname=Balram%20patel&email=ballu@ballu.com&password=dfss&age=21&gender=male&location=delhi";
        String url="";
        try
        {
            URI uri = new URI("http", "www.newsd.in", "/demo1/ws/event/event",url, null);
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
           /* Constant.imgUrl=new String[jsonArrayObject.size()];
            Constant.upcomingdate=new String[jsonArrayObject.size()];
            Constant.upcomingNewsID=new String[jsonArrayObject.size()];*/

            System.out.println("SIZE" + jsonArrayObject.size());

                JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(0).toString());
                upComingJson=jsonObject.get("upcoming").toString();
                previousJSON=jsonObject.get("previous").toString();

             /*   Constant.imgUrl[i] = jsonObject.get("image1").toString();
                Constant.upcomingdate[i]=jsonObject.get("event_date").toString();
                Constant.upcomingNewsID[i]=jsonObject.get("news_id").toString();*/

            /*for(int i=0;i<=jsonArrayObject.size()-1;i++)
            {
                System.out.println("iage-----"+ Constant.imgUrl[i]+" date  "+ Constant.upcomingdate[i]);
            }
*/

        } catch (Exception e) {
            System.out.println("in second catch block");
            e.printStackTrace();
        }
        return result;
    }

    void validateUpcoming(String upComing)
    {
        JSONParser jsonP=new JSONParser();
        try {
            Object obj = jsonP.parse(upComing);
            JSONArray jsonArrayObject = (JSONArray) obj;


            Constant.imgUrl=new String[jsonArrayObject.size()];
            Constant.upcomingdate=new String[jsonArrayObject.size()];
            Constant.upcomingNewsID=new String[jsonArrayObject.size()];

            for (int i=0;i<jsonArrayObject.size();i++)
            {
                JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(i).toString());
                Constant.imgUrl[i]=jsonObject.get("image1").toString();
                Constant.upcomingdate[i]=jsonObject.get("event_date").toString();
                Constant.upcomingNewsID[i]=jsonObject.get("news_id").toString();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    void validatePrevious(String previous)
    {
        JSONParser jsonP=new JSONParser();
        try {
            Object obj = jsonP.parse(previous);
            JSONArray jsonArrayObject = (JSONArray) obj;


            Constant.preImgUrl=new String[jsonArrayObject.size()];
            Constant.preDate=new String[jsonArrayObject.size()];
            Constant.preId=new String[jsonArrayObject.size()];

            for (int i=0;i<jsonArrayObject.size();i++)
            {
                JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(i).toString());
                Constant.preImgUrl[i]=jsonObject.get("image1").toString();
                Constant.preDate[i]=jsonObject.get("event_date").toString();
                Constant.preId[i]=jsonObject.get("news_id").toString();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}





