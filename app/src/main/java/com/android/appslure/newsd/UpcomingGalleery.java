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
public class UpcomingGalleery {


    String result;
    String status;
    String Id;
    public String getGalleryImages(String imgId) {
        Id=imgId;
        System.out.println("inside the wedSERCES-----"+imgId);
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

        //http://newsd.in/demo1/ws/event_detail.php?news_id=6
        //http://newsd.in/demo1/ws/event.php?type=previous
        //http://newsd.in/demo1/ws/event.php?type=up
        //http://unitedbysport.in/demo19/index.php/webservice/login?email=ballu@ballu.com&password=ballu
        //?fullname=Balram%20patel&email=ballu@ballu.com&password=dfss&age=21&gender=male&location=delhi";
        String url="news_id="+Id;
        try
        {
            URI uri = new URI("http", "www.newsd.in", "/demo1/ws/event_detail",url, null);
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
            Constant.detail=new String[jsonArrayObject.size()];
            Constant.gellery=new String[jsonArrayObject.size()];
            Constant.imgOne=new String[jsonArrayObject.size()];
            Constant.imgTwo=new String[jsonArrayObject.size()];
            Constant.imgThree=new String[jsonArrayObject.size()];
            Constant.imgFour=new String[jsonArrayObject.size()];
            Constant.imgFive=new String[jsonArrayObject.size()];
            Constant.imgSix=new String[jsonArrayObject.size()];
            Constant.imgSever=new String[jsonArrayObject.size()];

            for(int i=0;i<=jsonArrayObject.size()-1;i++)
            {
                JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(i).toString());
                Constant.detail[i] = jsonObject.get("description").toString();
                Constant.gellery[i]=jsonObject.get("image1").toString();
                Constant.imgOne[i]=jsonObject.get("image2").toString();
                Constant.imgTwo[i]=jsonObject.get("image3").toString();
                Constant.imgFour[i]=jsonObject.get("image4").toString();
                Constant.imgFive[i]=jsonObject.get("image5").toString();
                Constant.imgSix[i]=jsonObject.get("image6").toString();
                Constant.imgSever[i]=jsonObject.get("image7").toString();
            }

        } catch (Exception e) {
            System.out.println("in second catch block");
            e.printStackTrace();
        }
        return result;
    }



}

















