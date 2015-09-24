package com.android.BL;

import android.content.ContentValues;

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

/**
 * Created by Balram on 7/17/2015.
 */
public class DrawerCategoryBL {

    ////////////////////////////  Get CATEGORY NEWS FIRST TIME  //////////////////////////////////
    DBOperation dbOperation;
    String categoryName;

    public String getCategoryNews(String category,String page,DBOperation db)
    {
        categoryName=category;
        dbOperation=db;
        String result=getCategoryJson(category, page);
        String status=validateUpdatedData(result);
        return status;
    }

    public String getPeopleCategoryNews(DBOperation db,String category,String deviceID)
    {
        dbOperation=db;
        categoryName=category;
        String result=getPeopleCategoryJson(deviceID);
        String status=validateUpdatedData(result);
        return status;
    }

    public String getSpecialCategoryNews(DBOperation db,String category)
    {
        dbOperation=db;
        categoryName=category;
        String result=getSpecialCategoryJson();
        String status=validateUpdatedData(result);
        return status;
    }

    public String getMoreDATA(String category,String page,DBOperation db)
    {
        categoryName=category;
        dbOperation=db;
        String result=getCategoryJson(category, page);
        String status=validateUpdated(result);
        return status;
    }


    ///////////////////////////// GET CATEGORY JSON FIRST TIME ///////////////////////////////////

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

    /////////////////////////////// PARSE JSON ON MORE NEWS REQUEST  //////////////////////////////

    public String validateUpdated(String result)
    {
        // System.out.println("RESULT------------------>" + result);
        try
        {
            JSONParser parser=new JSONParser();
            Object jsonObject=parser.parse(result);

            JSONArray jsonArray= (JSONArray) jsonObject;

            System.out.println("SIZEEEEE" + jsonArray.size());
            Constant.categoryLastArraySize=jsonArray.size();
            Constant.categoryLast=Constant.categoryNewsSize;
            Constant.categoryNewsSize=Constant.categoryNewsSize+jsonArray.size();


            Constant.categoryTitle=createNewArray(Constant.categoryTitle,jsonArray.size());
            Constant.categoryContent=createNewArray(Constant.categoryContent,jsonArray.size());
            Constant.categoryPublisher=createNewArray(Constant.categoryPublisher,jsonArray.size());
            Constant.categoryDate=createNewArray(Constant.categoryDate,jsonArray.size());
            Constant.categoryImageURL=createNewArray(Constant.categoryImageURL,jsonArray.size());
            Constant.categoryNewsID=createNewArray(Constant.categoryNewsID,jsonArray.size());
            Constant.categoryNewsURL=createNewArray(Constant.categoryNewsURL, jsonArray.size());
            Constant.categoryTag=createNewArray(Constant.categoryTag, jsonArray.size());
            Constant.categoryFollowStatus=createNewIntegerArray(Constant.categoryFollowStatus, jsonArray.size());
            Constant.categoryBookmarkStatus=createNewIntegerArray(Constant.categoryBookmarkStatus,jsonArray.size());
            // Constant.bookmarkStatus=new int[Constant.newsSize];
            System.out.println("LAST" + Constant.categoryLast);

            for(int i=0;i<jsonArray.size();i++)
            {

                JSONObject jsonObjected = (JSONObject) parser.parse(jsonArray.get(i).toString());
                //  System.out.println("Content--->" +jsonObjected.get("content").toString());
                Constant.categoryTitle[Constant.categoryLast+i]=jsonObjected.get("title").toString();
                Constant.categoryContent[Constant.categoryLast+i]=jsonObjected.get("content").toString();
                Constant.categoryPublisher[Constant.categoryLast+i] =jsonObjected.get("source").toString();
                Constant.categoryDate[Constant.categoryLast+i]=jsonObjected.get("date").toString();
                Constant.categoryImageURL[Constant.categoryLast+i]=jsonObjected.get("image").toString();
                Constant.categoryNewsID[Constant.categoryLast+i]=jsonObjected.get("news_id").toString();
                Constant.categoryNewsURL[Constant.categoryLast+i]=jsonObjected.get("main_news").toString();
                Constant.categoryTag[Constant.categoryLast+i]=jsonObjected.get("tag").toString();
                Constant.categoryFollowStatus[Constant.categoryLast+i]=Integer.valueOf(jsonObjected.get("follow_status").toString());
                Constant.categoryBookmarkStatus[Constant.categoryLast+i]=Integer.valueOf(jsonObjected.get("bookmark_status").toString());
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

    //////////////////////////////// PARSE JSON /////////////////////////////////


    public String validateUpdatedData(String result)
    {
        // System.out.println("RESULT------------------>" + result);
        try
        {
            JSONParser parser=new JSONParser();
            Object jsonObject=parser.parse(result);

            JSONArray jsonArray= (JSONArray) jsonObject;

            System.out.println("SIZEEEEE" + jsonArray.size());


            Constant.categoryNewsSize=jsonArray.size();

            Constant.categoryTitle=new String[Constant.categoryNewsSize];
            Constant.categoryContent=new String[Constant.categoryNewsSize];
            Constant.categoryPublisher=new String[Constant.categoryNewsSize];
            Constant.categoryDate=new String[Constant.categoryNewsSize];
            Constant.categoryImageURL=new String[Constant.categoryNewsSize];
            Constant.categoryNewsURL=new String[Constant.categoryNewsSize];
            Constant.categoryNewsID=new String[Constant.categoryNewsSize];
            Constant.categoryTag=new String[Constant.categoryNewsSize];
            Constant.categoryFollowStatus=new int[Constant.categoryNewsSize];
            Constant.categoryBookmarkStatus=new int[Constant.categoryNewsSize];


            for(int i=0;i<Constant.categoryNewsSize;i++)
            {
                JSONObject jsonObjected = (JSONObject) parser.parse(jsonArray.get(i).toString());
                //  System.out.println("Content--->" +jsonObjected.get("content").toString());
                Constant.categoryTitle[i]=jsonObjected.get("title").toString();
                Constant.categoryContent[i]=jsonObjected.get("content").toString();
                Constant.categoryPublisher[i] =jsonObjected.get("source").toString();
                Constant.categoryDate[i]=jsonObjected.get("date").toString();
                Constant.categoryImageURL[i]=jsonObjected.get("image").toString();
                Constant.categoryNewsID[i]=jsonObjected.get("news_id").toString();
                Constant.categoryNewsURL[i]=jsonObjected.get("main_news").toString();
                Constant.categoryTag[i]=jsonObjected.get("tag").toString();
                Constant.categoryFollowStatus[i]=Integer.valueOf(jsonObjected.get("follow_status").toString());
                Constant.categoryBookmarkStatus[i]=Integer.valueOf(jsonObjected.get("bookmark_status").toString());

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


    ///////////////////////////////////////////////////////////

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
        values.put(people.getNewsLink(), curChatObj.getNewsLink());
        values.put(people.getNewsImage(), curChatObj.getNewsImage());
        values.put(people.getNewsFollow(), curChatObj.getNewsFollow());
        values.put(people.getNewsBookmark(), curChatObj.getNewsBookmark());


        dbOperation.open();
        long id=0;
        if(categoryName.equals(Constant.India)) {
             id = dbOperation.insertTableData(people.getTableIndia(), values);
        }
        else if(categoryName.equals(Constant.World))
        {
            id = dbOperation.insertTableData(people.getTableWorld(), values);
        }
        else if(categoryName.equals(Constant.Trending))
        {
            id = dbOperation.insertTableData(people.getTABLE_Trending(), values);
        }
        else if(categoryName.equals(Constant.Humour_Rumour))
        {
            id = dbOperation.insertTableData(people.getTABLE_Humour(), values);
        }
        else if(categoryName.equals(Constant.Economy))
        {
            id = dbOperation.insertTableData(people.getTABLE_Economy(), values);
        }
        else if(categoryName.equals(Constant.Bussiness))
        {
            id = dbOperation.insertTableData(people.getTABLE_Bussiness(), values);
        }///
        else if(categoryName.equals(Constant.Tech))
        {
            id = dbOperation.insertTableData(people.getTABLE_Tech(), values);
        }
        else if(categoryName.equals(Constant.Sport))
        {
            id = dbOperation.insertTableData(people.getTABLE_Sports(), values);
        }
        else if(categoryName.equals(Constant.Entertainment))
        {
            id = dbOperation.insertTableData(people.getTABLE_EnterTainment(), values);
        }//////////
        else if(categoryName.equals(Constant.LifeStyle))
        {
            id = dbOperation.insertTableData(people.getTABLE_LifeStyle(), values);
        }
        else if(categoryName.equals(Constant.StartUp))
        {
            id = dbOperation.insertTableData(people.getTABLE_Special(), values);
        }
        else if(categoryName.equals(Constant.People))
        {
            id = dbOperation.insertTableData(people.getTABLE_People(), values);
        }




        dbOperation.close();
        if (id != -1) {
            System.out.print("Successfully Inserted");
        }

        //populateChatMessages("ADD to DB");
    }


    /////////////////////     CALL WEBSERVICE FOR PEOPLE JSON     //////////////////////////

    private String getPeopleCategoryJson(String people)
    {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String text = null;
        String url="keyword="+people;

        try {
            URI uri = new URI("http", "www.newsd.in", "/demo1/ws/event/add_people",url, null);
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


    private String getSpecialCategoryJson()
    {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String text = null;
        String url="";

        try {
            URI uri = new URI("http", "www.newsd.in", "/demo1/ws/special_tag",url, null);
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
}
