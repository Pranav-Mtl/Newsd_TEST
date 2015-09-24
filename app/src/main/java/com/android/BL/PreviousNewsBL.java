package com.android.BL;

import com.android.BE.PreviousNewsBE;

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
public class PreviousNewsBL {


    String status;

    PreviousNewsBE objPreviousNewsBE;
    public String getPreviousEventData(String eventID,PreviousNewsBE objPreviousNewsBE) {

        this.objPreviousNewsBE=objPreviousNewsBE;

        System.out.println("inside the wedSERCES-----"+eventID);
        try {
            String result = fetRecord(eventID);
            status = validate(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }


    private String fetRecord(String eventID)
    {
        String text = null;

        //http://newsd.in/demo1/ws/event_detail.php?news_id=6
        //http://newsd.in/demo1/ws/event.php?type=previous
        //http://newsd.in/demo1/ws/event.php?type=up
        //http://unitedbysport.in/demo19/index.php/webservice/login?email=ballu@ballu.com&password=ballu
        //?fullname=Balram%20patel&email=ballu@ballu.com&password=dfss&age=21&gender=male&location=delhi";
        String url="news_id="+eventID;
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

                JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(0).toString());

                objPreviousNewsBE.setDescription(jsonObject.get("description").toString());
                objPreviousNewsBE.setTitle(jsonObject.get("title").toString());
                objPreviousNewsBE.setHeaderImage(jsonObject.get("header_image").toString());
                objPreviousNewsBE.setGalleryImageOne(jsonObject.get("image1").toString());
                objPreviousNewsBE.setGalleryImageTwe(jsonObject.get("image2").toString());
                objPreviousNewsBE.setGalleryImageThree(jsonObject.get("image3").toString());
                objPreviousNewsBE.setGalleryImageFour(jsonObject.get("image4").toString());
                objPreviousNewsBE.setGalleryImageFive(jsonObject.get("image5").toString());
                objPreviousNewsBE.setGalleryImageSix(jsonObject.get("image6").toString());
                objPreviousNewsBE.setGalleryImageSeven(jsonObject.get("image7").toString());
                objPreviousNewsBE.setGalleryImageEight(jsonObject.get("image8").toString());
                objPreviousNewsBE.setEventdate(jsonObject.get("event_date").toString());


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
