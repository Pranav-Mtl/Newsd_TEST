package com.android.BL;

import android.content.ContentValues;

import com.android.BE.CategoryBE;
import com.android.CONSTANTS.Constant;
import com.android.DB.ChatPeopleBE;
import com.android.DB.DBOperation;

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
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by appslure on 6/19/2015.
 */
public class CategoryBL {

    private static ArrayList<HashMap<String, String>> arraylist;
    private static JSONArray jsonarray;
    private static JSONObject jsonobject;
    private  static String url ="http://ajax.googleapis.com/ajax/services/search/news?v=1.0&topic=h&ned=in&rsz=large";
    private  static CategoryBE  categoryBE_obj;
    private String[] formattedContent;
    DBOperation dbOperation;

   /* public  String GetJson(CategoryBE categoryBE){
        categoryBE_obj=categoryBE;
        arraylist = new ArrayList<HashMap<String, String>>();

        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String text = null;

        try {
            URI uri = new URI("http", "www.ajax.googleapis.com", "/ajax/services/search/news",Constant.url, null);
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

            Constant.title=new String[jsonarray.length()];
            Constant.content=new String[jsonarray.length()];
            Constant.publisher=new String[jsonarray.length()];
            Constant.date=new String[jsonarray.length()];
            Constant.imageURL=new String[jsonarray.length()];
            Constant.newsURL=new String[jsonarray.length()];

            for (int i = 0; i < jsonarray.length(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                jsonobject = jsonarray.getJSONObject(i);
                // Retrive JSON Objects
                map.put("title", jsonobject.getString("title"));
                map.put("url", jsonobject.getString("url"));
                map.put("content", jsonobject.getString("content"));
                JSONObject image = jsonobject.getJSONObject("image");
                String news_image=image.getString("url");

                System.out.println("TITLE--------->" + jsonobject.getString("titleNoFormatting").toString());

                System.out.println("Redirect_URL--------->" + jsonobject.getString("signedRedirectUrl").toString());

                System.out.println("CONTENT--------->" + jsonobject.getString("content").toString());

                System.out.println("PUBLISH_DATE--------->" + jsonobject.getString("publishedDate").toString());

                System.out.println("PUBLISHER--------->" + jsonobject.getString("publisher").toString());

                System.out.println("IMAGE--------->" + news_image);

                Constant.title[i]=jsonobject.getString("titleNoFormatting").toString().replace("&nbsp;", "").replace("&#39;", "'").replace("amp;","");
                Constant.content[i]=jsonobject.getString("content").toString().replaceAll("&#39;", "").replace("&nbsp;","").replace("&quot;","");
                Constant.publisher[i] = jsonobject.getString("publisher").toString();
                Constant.date[i]=jsonobject.getString("publishedDate").toString().replace("-0700", "");
                Constant.imageURL[i]=news_image;
                Constant.newsURL[i]=jsonobject.getString("signedRedirectUrl").toString();

            }
        } catch (JSONException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }*/
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

    public String insertCategory(String deviceId,String category,String page,String state,String gcmID,DBOperation db,String param_value)
    {
        dbOperation=db;
        String result=getUpdateDataJson(deviceId,category,page,state,gcmID,param_value);
        String status=validateUpdatedData(result);
        return status;
    }

    public String getCategoryData(String deviceId,String page,DBOperation db,String regID)
    {
        dbOperation=db;
        String result=getDataJson(deviceId,page,regID);
        String status=validateUpdatedData(result);
        return status;
    }
  /*  public String getCategoryNews(String category,String page)
    {
        String result=getCategoryJson(category, page);
        String status=validateUpdatedData(result);
        return status;
    }*/

    public String getMoreDATA(String deviceId,String page,DBOperation db,String regID)
    {
        dbOperation=db;
        String result=getDataJson(deviceId, page,regID);
        String status=validateUpdated(result);
        return status;
    }
    private String getUpdateDataJson(String deviceId,String categoryList,String pageno,String state,String gcmID,String param_value)
    {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String text = null;
        String url="regID="+deviceId+"&category="+categoryList+"&page_no="+pageno+"&state="+state+"&gcm_id="+gcmID+"&param_value="+param_value;

        try {
            URI uri = new URI("http", "www.newsd.in", "/demo1/ws/insert_user",url, null);
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
    private String getCategoryJson(String categoryList,String pageno)
    {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String text = null;
        String url="category="+categoryList+"&page_no="+pageno;

        try {
            URI uri = new URI("http", "www.newsd.in", "/demo1/ws/category_news",url, null);
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
    public String validateUpdatedData(String result)
    {
       // System.out.println("RESULT------------------>" + result);
        try
        {
            JSONParser parser=new JSONParser();
            Object jsonObject=parser.parse(result);

            JSONArray jsonArray= (JSONArray) jsonObject;

            System.out.println("SIZEEEEE" + jsonArray.size());


            Constant.newsSize=jsonArray.size();

            Constant.title=new String[Constant.newsSize];
            Constant.content=new String[Constant.newsSize];
            Constant.publisher=new String[Constant.newsSize];
            Constant.date=new String[Constant.newsSize];
            Constant.imageURL=new String[Constant.newsSize];
            Constant.newsURL=new String[Constant.newsSize];
            Constant.newsID=new String[Constant.newsSize];
            Constant.tag=new String[Constant.newsSize];
            Constant.video=new String[Constant.newsSize];
            Constant.followStatus=new int[Constant.newsSize];
            Constant.bookmarkStatus=new int[Constant.newsSize];
            ChatPeopleBE people = new ChatPeopleBE();
            dbOperation.open();
            dbOperation.delete(people.getTableName());
            dbOperation.close();
            for(int i=0;i<Constant.newsSize;i++)
            {
                JSONObject jsonObjected = (JSONObject) parser.parse(jsonArray.get(i).toString());
                    //  System.out.println("Content--->" +jsonObjected.get("content").toString());
                Constant.title[i]=jsonObjected.get("title").toString();
                Constant.content[i]=jsonObjected.get("content").toString();
                Constant.publisher[i] =jsonObjected.get("source").toString();
                Constant.date[i]=jsonObjected.get("date").toString();
                Constant.imageURL[i]=jsonObjected.get("image").toString();
                Constant.newsID[i]=jsonObjected.get("news_id").toString();
                Constant.newsURL[i]=jsonObjected.get("main_news").toString();
                Constant.video[i]=jsonObjected.get("video").toString();
                Constant.tag[i]=jsonObjected.get("tag").toString();
                Constant.followStatus[i]=Integer.valueOf(jsonObjected.get("follow_status").toString());
                Constant.bookmarkStatus[i]=Integer.valueOf(jsonObjected.get("bookmark_status").toString());
                ChatPeopleBE curChatObj = addToChat(jsonObjected.get("news_id").toString(), jsonObjected.get("title").toString(),jsonObjected.get("content").toString(),jsonObjected.get("tag").toString(),jsonObjected.get("source").toString(),jsonObjected.get("image").toString(),jsonObjected.get("main_news").toString(),jsonObjected.get("follow_status").toString(),jsonObjected.get("bookmark_status").toString());

                addToDB(curChatObj);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
       return "";
    }

    public String[] createNewArray(String[] oldArray,int size){
        String[] newArray = new String[oldArray.length + size];
        for(int i = 0; i < oldArray.length; i++) {
            newArray[i] = oldArray[i];
        }

        return newArray;
    }

    public int[] createNewIntegerArray(int[] oldArray,int size){
        int[] newArray = new int[oldArray.length + size];
        for(int i = 0; i < oldArray.length; i++) {
            newArray[i] = oldArray[i];
        }

        return newArray;
    }

    private String getDataJson(String deviceId,String pageno,String regID)
    {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String text = null;
        String url="regID="+deviceId+"&page_no="+pageno+"&gcm_id="+regID;

        try {
            URI uri = new URI("http", "www.newsd.in", "/demo1/ws/update_user.php",url, null);
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
    public String validateUpdated(String result)
    {
        // System.out.println("RESULT------------------>" + result);
        try
        {
            JSONParser parser=new JSONParser();
            Object jsonObject=parser.parse(result);

            JSONArray jsonArray= (JSONArray) jsonObject;

            System.out.println("SIZEEEEE" + jsonArray.size());
            Constant.lastArraySize=jsonArray.size();
            Constant.last=Constant.newsSize;
            Constant.newsSize=Constant.newsSize+jsonArray.size();


            Constant.title=createNewArray(Constant.title,jsonArray.size());
            Constant.content=createNewArray(Constant.content,jsonArray.size());
            Constant.publisher=createNewArray(Constant.publisher,jsonArray.size());
            Constant.date=createNewArray(Constant.date,jsonArray.size());
            Constant.imageURL=createNewArray(Constant.imageURL,jsonArray.size());
            Constant.newsID=createNewArray(Constant.newsID,jsonArray.size());
            Constant.newsURL=createNewArray(Constant.newsURL, jsonArray.size());
            Constant.tag=createNewArray(Constant.tag, jsonArray.size());
            Constant.video=createNewArray(Constant.video, jsonArray.size());
            Constant.followStatus=createNewIntegerArray(Constant.followStatus, jsonArray.size());
            Constant.bookmarkStatus=createNewIntegerArray(Constant.bookmarkStatus,jsonArray.size());
           // Constant.bookmarkStatus=new int[Constant.newsSize];
            System.out.println("LAST" + Constant.last);

            for(int i=0;i<jsonArray.size();i++)
            {

                JSONObject jsonObjected = (JSONObject) parser.parse(jsonArray.get(i).toString());
                //  System.out.println("Content--->" +jsonObjected.get("content").toString());
                Constant.title[Constant.last+i]=jsonObjected.get("title").toString();
                Constant.content[Constant.last+i]=jsonObjected.get("content").toString();
                Constant.publisher[Constant.last+i] =jsonObjected.get("source").toString();
                Constant.date[Constant.last+i]=jsonObjected.get("date").toString();
                Constant.imageURL[Constant.last+i]=jsonObjected.get("image").toString();
                Constant.newsID[Constant.last+i]=jsonObjected.get("news_id").toString();
                Constant.newsURL[Constant.last+i]=jsonObjected.get("main_news").toString();
                Constant.tag[Constant.last+i]=jsonObjected.get("tag").toString();
                Constant.video[Constant.last+i]=jsonObjected.get("video").toString();
                Constant.followStatus[Constant.last+i]=Integer.valueOf(jsonObjected.get("follow_status").toString());
                Constant.bookmarkStatus[Constant.last+i]=Integer.valueOf(jsonObjected.get("bookmark_status").toString());
                ChatPeopleBE curChatObj = addToChat(jsonObjected.get("news_id").toString(), jsonObjected.get("title").toString(),jsonObjected.get("content").toString(),jsonObjected.get("tag").toString(),jsonObjected.get("source").toString(),jsonObjected.get("image").toString(),jsonObjected.get("main_news").toString(),jsonObjected.get("follow_status").toString(),jsonObjected.get("bookmark_status").toString());

                addToDB(curChatObj);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

    ChatPeopleBE addToChat(String newsdID, String newsdTitle, String newsdContent,String newsdTag,String newsdPublisher,String newsdImage,String newsdLink,String newsdFollow,String newsdBookmarked) {

        /*Log.i(TAG, "inserting : " + personName + ", " + chatMessage + ", "
                + toOrFrom + " , " + chattingToDeviceID);*/
        ChatPeopleBE curChatObj = new ChatPeopleBE();
        curChatObj.setNewdID(newsdID);
        curChatObj.setNewsTitle(newsdTitle);
        curChatObj.setNewsContent(newsdContent);
        curChatObj.setNewsTag(newsdTag);

        curChatObj.setNewsPublisher(newsdPublisher);
        curChatObj.setNewsLink(newsdLink);
        curChatObj.setNewsImage(newsdImage);
        curChatObj.setNewsFollow(newsdFollow);
        curChatObj.setNewsBookmark(newsdBookmarked);

        return curChatObj;

    }

    void addToDB(ChatPeopleBE curChatObj) {

        ChatPeopleBE people = new ChatPeopleBE();
        ContentValues values = new ContentValues();
        values.put(people.getNewdID(), curChatObj.getNewdID());
        values.put(people.getNewsTitle(),curChatObj.getNewsTitle());
        values.put(people.getNewsContent(),curChatObj.getNewsContent());
        values.put(people.getNewsTag(), curChatObj.getNewsTag());

        values.put(people.getNewsPublisher(), curChatObj.getNewsPublisher());
        values.put(people.getNewsLink(),curChatObj.getNewsLink());
        values.put(people.getNewsImage(),curChatObj.getNewsImage());
        values.put(people.getNewsFollow(), curChatObj.getNewsFollow());
        values.put(people.getNewsBookmark(), curChatObj.getNewsBookmark());


        dbOperation.open();

        long id = dbOperation.insertTableData(people.getTableName(), values);
        dbOperation.close();
        if (id != -1) {
            System.out.print("Successfully Inserted");
        }

        //populateChatMessages("ADD to DB");
    }


}
