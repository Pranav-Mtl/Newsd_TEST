package com.android.DB;

import com.android.CONSTANTS.Constant;

/**
 * Created by Balram on 5/19/2015.
 */
public class ChatPeopleBE {

    private String TABLE_PEOPLE ="news_table";
    private String TABLE_India = "india_table";
    private String TABLE_World =Constant.World;
    private String TABLE_Bussiness =Constant.Bussiness;
    private String TABLE_LifeStyle =Constant.LifeStyle;
    private String TABLE_EnterTainment =Constant.Entertainment;
    private String TABLE_Trending =Constant.Trending;
    private String TABLE_Tech =Constant.Tech;
    private String TABLE_Sports =Constant.Sport;
    private String TABLE_Humour =Constant.Humour_Rumour;
    private String TABLE_Economy =Constant.Economy;
    private String TABLE_Special =Constant.StartUp;
    private String TABLE_People=Constant.People;


    int DATABASE_VERSION = 1;

    private String ID="id";
    private String newdID="newsid";
    private String newsTitle="title";
    private String newsContent="content";
    private String newsTag="tag";
    private String newsPublisher="publisher";
    private String newsImage="image";
    private String newsFollow="follow";
    private String newsBookmark="bookmarkTEXT";



    public String getTABLE_People() {
        return TABLE_People;
    }

    public String getTableName() {
        return TABLE_PEOPLE;
    }

    public String getTableIndia() {
        return TABLE_India;
    }

    public String getTableWorld() {
        return TABLE_World;
    }

    public String getTABLE_Trending() {
        return TABLE_Trending;
    }

    public String getTABLE_Humour() {
        return TABLE_Humour;
    }

    public String getTABLE_Economy() {
        return TABLE_Economy;
    }

    public String getTABLE_Bussiness() {
        return TABLE_Bussiness;
    }

    public String getTABLE_Tech() {
        return TABLE_Tech;
    }

    public String getTABLE_Sports() {
        return TABLE_Sports;
    }

    public String getTABLE_EnterTainment() {
        return TABLE_EnterTainment;
    }

    public String getTABLE_LifeStyle() {
        return TABLE_LifeStyle;
    }

    public String getTABLE_Special() {
        return TABLE_Special;
    }



    private String newsLink="link";

    public String getFilename() {
        return newdID;
    }

    public String getNewdID() {
        return newdID;
    }

    public void setNewdID(String newdID) {
        this.newdID = newdID;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getNewsTag() {
        return newsTag;
    }

    public void setNewsTag(String newsTag) {
        this.newsTag = newsTag;
    }

    public String getNewsPublisher() {
        return newsPublisher;
    }

    public void setNewsPublisher(String newsPublisher) {
        this.newsPublisher = newsPublisher;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }

    public String getNewsFollow() {
        return newsFollow;
    }

    public void setNewsFollow(String newsFollow) {
        this.newsFollow = newsFollow;
    }

    public String getNewsBookmark() {
        return newsBookmark;
    }

    public void setNewsBookmark(String newsBookmark) {
        this.newsBookmark = newsBookmark;
    }

    public String getNewsLink() {
        return newsLink;
    }

    public void setNewsLink(String newsLink) {
        this.newsLink = newsLink;
    }

    public String getDatabaseCreateQuery() {
        final String DATABASE_CREATE = "create table IF NOT EXISTS "
                + TABLE_PEOPLE + " (" + ID + " INTEGER PRIMARY KEY, "
                + newdID + " TEXT NOT NULL, "
                + newsTitle + " TEXT NOT NULL, "
                + newsContent + " TEXT NOT NULL ,"
                + newsTag+" TEXT NOT NULL,"
                + newsPublisher+" TEXT NOT NULL,"
                + newsImage+" TEXT NOT NULL ,"
                + newsFollow+" TEXT NOT NULL ,"
                + newsBookmark+" TEXT NOT NULL ,"
                + newsLink+" TEXT NOT NULL)";


        System.out.println(DATABASE_CREATE);
        return DATABASE_CREATE;
    }

    /////////////////////    INDIA    ///////////////////

    public String getDatabaseCreateQueryIndia() {
        final String DATABASE_CREATE = "create table IF NOT EXISTS "
                + TABLE_India+ " (" + ID + " INTEGER PRIMARY KEY, "
                + newdID + " TEXT NOT NULL, "
                + newsTitle + " TEXT NOT NULL, "
                + newsContent + " TEXT NOT NULL ,"
                + newsTag+" TEXT NOT NULL,"
                + newsPublisher+" TEXT NOT NULL,"
                + newsImage+" TEXT NOT NULL ,"
                + newsFollow+" TEXT NOT NULL ,"
                + newsBookmark+" TEXT NOT NULL ,"
                + newsLink+" TEXT NOT NULL)";


        System.out.println(DATABASE_CREATE);
        return DATABASE_CREATE;
    }

    //////////////////////     WORLD     ////////////////////

    public String getDatabaseCreateQueryWorld() {
    final String DATABASE_CREATE = "create table IF NOT EXISTS "
            + TABLE_World+ " (" + ID + " INTEGER PRIMARY KEY, "
            + newdID + " TEXT NOT NULL, "
            + newsTitle + " TEXT NOT NULL, "
            + newsContent + " TEXT NOT NULL ,"
            + newsTag+" TEXT NOT NULL,"
            + newsPublisher+" TEXT NOT NULL,"
            + newsImage+" TEXT NOT NULL ,"
            + newsFollow+" TEXT NOT NULL ,"
            + newsBookmark+" TEXT NOT NULL ,"
            + newsLink+" TEXT NOT NULL)";


    System.out.println(DATABASE_CREATE);
    return DATABASE_CREATE;
}

    //////////////////    TRENDING    ////////////////////////

    public String getDatabaseCreateQueryTrending() {
        final String DATABASE_CREATE = "create table IF NOT EXISTS "
                + TABLE_Trending+ " (" + ID + " INTEGER PRIMARY KEY, "
                + newdID + " TEXT NOT NULL, "
                + newsTitle + " TEXT NOT NULL, "
                + newsContent + " TEXT NOT NULL ,"
                + newsTag+" TEXT NOT NULL,"
                + newsPublisher+" TEXT NOT NULL,"
                + newsImage+" TEXT NOT NULL ,"
                + newsFollow+" TEXT NOT NULL ,"
                + newsBookmark+" TEXT NOT NULL ,"
                + newsLink+" TEXT NOT NULL)";


        System.out.println(DATABASE_CREATE);
        return DATABASE_CREATE;
    }

    ///////////////////  HUMOUR AND RUMOUR   /////////////////////////

    public String getDatabaseCreateQueryHumour() {
        final String DATABASE_CREATE = "create table IF NOT EXISTS "
                + TABLE_Humour+ " (" + ID + " INTEGER PRIMARY KEY, "
                + newdID + " TEXT NOT NULL, "
                + newsTitle + " TEXT NOT NULL, "
                + newsContent + " TEXT NOT NULL ,"
                + newsTag+" TEXT NOT NULL,"
                + newsPublisher+" TEXT NOT NULL,"
                + newsImage+" TEXT NOT NULL ,"
                + newsFollow+" TEXT NOT NULL ,"
                + newsBookmark+" TEXT NOT NULL ,"
                + newsLink+" TEXT NOT NULL)";


        System.out.println(DATABASE_CREATE);
        return DATABASE_CREATE;
    }

    ///////////////////////  ECONOMY   //////////////////////
    public String getDatabaseCreateQueryEconomy() {
        final String DATABASE_CREATE = "create table IF NOT EXISTS "
                + TABLE_Economy+ " (" + ID + " INTEGER PRIMARY KEY, "
                + newdID + " TEXT NOT NULL, "
                + newsTitle + " TEXT NOT NULL, "
                + newsContent + " TEXT NOT NULL ,"
                + newsTag+" TEXT NOT NULL,"
                + newsPublisher+" TEXT NOT NULL,"
                + newsImage+" TEXT NOT NULL ,"
                + newsFollow+" TEXT NOT NULL ,"
                + newsBookmark+" TEXT NOT NULL ,"
                + newsLink+" TEXT NOT NULL)";


        System.out.println(DATABASE_CREATE);
        return DATABASE_CREATE;
    }
////////////////////     BUSSINESS    ////////////////////////

    public String getDatabaseCreateQueryBussiness() {
        final String DATABASE_CREATE = "create table IF NOT EXISTS "
                + TABLE_Bussiness+ " (" + ID + " INTEGER PRIMARY KEY, "
                + newdID + " TEXT NOT NULL, "
                + newsTitle + " TEXT NOT NULL, "
                + newsContent + " TEXT NOT NULL ,"
                + newsTag+" TEXT NOT NULL,"
                + newsPublisher+" TEXT NOT NULL,"
                + newsImage+" TEXT NOT NULL ,"
                + newsFollow+" TEXT NOT NULL ,"
                + newsBookmark+" TEXT NOT NULL ,"
                + newsLink+" TEXT NOT NULL)";


        System.out.println(DATABASE_CREATE);
        return DATABASE_CREATE;
    }


    public String getDatabaseCreateQueryTech() {
        final String DATABASE_CREATE = "create table IF NOT EXISTS "
                + TABLE_Tech+ " (" + ID + " INTEGER PRIMARY KEY, "
                + newdID + " TEXT NOT NULL, "
                + newsTitle + " TEXT NOT NULL, "
                + newsContent + " TEXT NOT NULL ,"
                + newsTag+" TEXT NOT NULL,"
                + newsPublisher+" TEXT NOT NULL,"
                + newsImage+" TEXT NOT NULL ,"
                + newsFollow+" TEXT NOT NULL ,"
                + newsBookmark+" TEXT NOT NULL ,"
                + newsLink+" TEXT NOT NULL)";


        System.out.println(DATABASE_CREATE);
        return DATABASE_CREATE;
    }

    public String getDatabaseCreateQuerySport() {
        final String DATABASE_CREATE = "create table IF NOT EXISTS "
                + TABLE_Sports+ " (" + ID + " INTEGER PRIMARY KEY, "
                + newdID + " TEXT NOT NULL, "
                + newsTitle + " TEXT NOT NULL, "
                + newsContent + " TEXT NOT NULL ,"
                + newsTag+" TEXT NOT NULL,"
                + newsPublisher+" TEXT NOT NULL,"
                + newsImage+" TEXT NOT NULL ,"
                + newsFollow+" TEXT NOT NULL ,"
                + newsBookmark+" TEXT NOT NULL ,"
                + newsLink+" TEXT NOT NULL)";


        System.out.println(DATABASE_CREATE);
        return DATABASE_CREATE;
    }

    public String getDatabaseCreateQueryEntertainment() {
        final String DATABASE_CREATE = "create table IF NOT EXISTS "
                + TABLE_EnterTainment+ " (" + ID + " INTEGER PRIMARY KEY, "
                + newdID + " TEXT NOT NULL, "
                + newsTitle + " TEXT NOT NULL, "
                + newsContent + " TEXT NOT NULL ,"
                + newsTag+" TEXT NOT NULL,"
                + newsPublisher+" TEXT NOT NULL,"
                + newsImage+" TEXT NOT NULL ,"
                + newsFollow+" TEXT NOT NULL ,"
                + newsBookmark+" TEXT NOT NULL ,"
                + newsLink+" TEXT NOT NULL)";


        System.out.println(DATABASE_CREATE);
        return DATABASE_CREATE;
    }

    public String getDatabaseCreateQueryLifestyle() {
        final String DATABASE_CREATE = "create table IF NOT EXISTS "
                + TABLE_LifeStyle+ " (" + ID + " INTEGER PRIMARY KEY, "
                + newdID + " TEXT NOT NULL, "
                + newsTitle + " TEXT NOT NULL, "
                + newsContent + " TEXT NOT NULL ,"
                + newsTag+" TEXT NOT NULL,"
                + newsPublisher+" TEXT NOT NULL,"
                + newsImage+" TEXT NOT NULL ,"
                + newsFollow+" TEXT NOT NULL ,"
                + newsBookmark+" TEXT NOT NULL ,"
                + newsLink+" TEXT NOT NULL)";


        System.out.println(DATABASE_CREATE);
        return DATABASE_CREATE;
    }

    public String getDatabaseCreateQueryPeople() {
        final String DATABASE_CREATE = "create table IF NOT EXISTS "
                + TABLE_People+ " (" + ID + " INTEGER PRIMARY KEY, "
                + newdID + " TEXT NOT NULL, "
                + newsTitle + " TEXT NOT NULL, "
                + newsContent + " TEXT NOT NULL ,"
                + newsTag+" TEXT NOT NULL,"
                + newsPublisher+" TEXT NOT NULL,"
                + newsImage+" TEXT NOT NULL ,"
                + newsFollow+" TEXT NOT NULL ,"
                + newsBookmark+" TEXT NOT NULL ,"
                + newsLink+" TEXT NOT NULL)";


        System.out.println(DATABASE_CREATE);
        return DATABASE_CREATE;
    }

    public String getDatabaseCreateQuerySpecial() {
        final String DATABASE_CREATE = "create table IF NOT EXISTS "
                + TABLE_Special+ " (" + ID + " INTEGER PRIMARY KEY, "
                + newdID + " TEXT NOT NULL, "
                + newsTitle + " TEXT NOT NULL, "
                + newsContent + " TEXT NOT NULL ,"
                + newsTag+" TEXT NOT NULL,"
                + newsPublisher+" TEXT NOT NULL,"
                + newsImage+" TEXT NOT NULL ,"
                + newsFollow+" TEXT NOT NULL ,"
                + newsBookmark+" TEXT NOT NULL ,"
                + newsLink+" TEXT NOT NULL)";


        System.out.println(DATABASE_CREATE);
        return DATABASE_CREATE;
    }


}
