package com.android.CONSTANTS;


import com.android.appslure.newsd.R;

/**
 * Created by appslure on 6/20/2015.
 */
public class Constant {

    public static  String PREFS_NAME = "MyPrefsFile";
    public static String SHARED_PREFERENCE_ANDROID_ID="androidId";
    public static final String MSG_KEY = "m";

    public static String SHARED_PREFERENCE_Swipe_Category="Category";

    public static String SHARED_PREFERENCE_Swipe_Story_One="StoryOne";

    public static String SHARED_PREFERENCE_Swipe_Story_Two="StoryTwo";

    public static String CampaignLadoo="ladooo";



    public static String SHARED_PREFERENCE_UpdateRegistrationID="REGISTRATIONID";

    public static final String GOOGLE_PROJ_ID = "569382529492";  //"871793772386";//378376053947  //38650527038

    public static  String STREMAILADDREGEX_FORGOTPASSWORD="^[_A-Za-z0-9]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$";

    public static String title[];
    public static String content[];
    public static String publisher[];
    public static String date[];
    public static String imageURL[];
    public static String newsURL[];
    public static String newsID[];
    public static String tag[];
    public static int followStatus[];
    public static int bookmarkStatus[];

    public static String titleSearch[];
    public static String contentSearch[];
    public static String publisherSearch[];
    public static String dateSearch[];
    public static String imageURLSearch[];
    public static String newsURLSearch[];
    public static String newsIDSearch[];
    public static String tagSearch[];

    public static int totalSearchValue=0;
    public static int lastSearchValue=0;

    public static int next_pages=8;
    public static int flipped_pages=8;
    public static String url ="v=1.0&topic=h&ned=in&rsz=large";
    private static String first_letter;
    private static String result="";

    public static int newsSize=0;

    public static int categoryNewsSize=0;

    public static int categoryLast=0;

    public static int last=0;

    public static int page_no=0;
    public static int Categorypage_no=0;

    public static int currentPage;

    public static int counterPeople;

    public static String tickerTitle;
    public static String tickerID;

    public static String INDIA_SELECTED="unselected";
    public static String BUSINESS_SELECTED="unselected";
    public static String TECH_SELECTED="unselected";
    public static String STARTUP_SELECTED="unselected";
    public static String PEOPLE_SELECTED="unselected";
    public static String WORLD_SELECTED="unselected";
    public static String HUMOR_SELECTED="unselected";
    public static String ECONOMY_SELECTED="unselected";
    public static String TRENDING_SELECTED="unselected";
    public static String ENTERTAINMENT_SELECTED="unselected";
    public static String LIFESTYLE_SELECTED="unselected";
    public static String SPORTS_SELECTED="unselected";
    public static String METROS_SELECTED="unselected";

    public static String BookMarked_id[];
    public static String BookMarked_Title[];
    public static String BookMarked_date[];
    public static String BookMarked_Url[];
    public static String BookMarked_Publisher[];
    public static String BookMarked_Category[];

    public static String FollowedStory[];
    public static String FollowedNewsID[];
    public static String FollowedCategory[];
    public static String FollowedTime[];
    public static String FollowedPublisher[];
    public static String FollowedTitle[];

    public static String FollowedNews[];
    public static String FollowedID[];

    public static int lastArraySize;

    public static int categoryLastArraySize;

    public static String PeopleTagId[];
    public static String PeopleTagName[];


    public static String India="india";
    public static String World="world";
    public static String Bussiness="biz_money";
    public static String LifeStyle="lifestyle";

    public static String Entertainment="entertainment";
    public static String Tech="tech";
    public static String Sport="sport";
    public static String Economy="economy";

    public static String Trending="trending";
    public static String StartUp="specials";
    public static String People="people";
    public static String Humour_Rumour="humour_rumor";

    public static String Jammu="Jammu and Kashmir-Punjab-Himachal Pradesh";
    public static String Haryane="Haryana-Rajasthan";
    public static String Delhi="Delhi-NCR";
    public static String UP="Uttar Pradesh-Uttarakhand";
    public static String Gujarat="Gujarat";
    public static String MP="Madhya Pradesh-Chhattisgarh";
    public static String Bihar="Bihar-Jharkhand";
    public static String wb="West Bengal-Orissa";
    public static String Assam="Assam-North East India";
    public static String Maharashtra="Maharashtra-Goa-Pondicherry";
    public static String Telangana="Telangana-Andhra Pradesh-Tamilnadu";
    public static String Karnataka="Karnataka";
    public static String Kerala="Kerala";

    public static String JammuText[]={"Jammu and Kashmir-Punjab-Himachal Pradesh","Madhya Pradesh-Chhattisgarh","Telangana-Andhra Pradesh-Tamilnadu"};
    public static String HaryaneText[]={"Haryana-Punjab","Bihar-Jharkhand","Karnataka"};
    public static String DelhiText[]={"Delhi-NCR","West Bengal-Orissa","Kerala"};
    public static String UPText[]={"Uttar Pradesh-Uttarakhand","Assam-North East India",""};
    public static String GujaratText[]={"Gujarat","Maharashtra-Goa-Pondicherry",""};

    public static int ImageJammu[]={R.drawable.jammu, R.drawable.mp2,R.drawable.telangna};
    public static int ImageHaryana[]={R.drawable.haryanarahasthan,R.drawable.bihar,R.drawable.karnatka};
    public static int ImageDelhi[]={R.drawable.delhincr,R.drawable.westbangal,R.drawable.kerala};
    public static int ImageUP[]={R.drawable.utterpradesh,R.drawable.assam,};
    public static int ImageGujrat[]={R.drawable.gujrat,R.drawable.maharastra,};

    public static int ImageJammuSelected[]={R.drawable.jammuselected,R.drawable.mp2selected,R.drawable.telangnaselected};
    public static int ImageHaryanaSelected[]={R.drawable.haryanarahasthanselected,R.drawable.biharselected,R.drawable.karnatkaselected};
    public static int ImageDelhiSelected[]={R.drawable.delhincrselected,R.drawable.westbangalselected,R.drawable.keralaselected};
    public static int ImageUPSelected[]={R.drawable.utterpradeshselected,R.drawable.assamselected,};
    public static int ImageGujratSelected[]={R.drawable.gujratselected,R.drawable.maharastraselected,};

    /*public static String MP="Madhya Pradesh-Chhattisgarh";
    public static String Bihar="Bihar-Jharkhand";
    public static String wb="West Bengal-Orissa";
    public static String Assam="Assam-North East India";
    public static String Maharashtra="Maharashtra-Goa-Pondicherry";
    public static String Telangana="Telangana-Andhra Pradesh-Tamilnadu";
    public static String Karnataka="Karnataka";
    public static String Kerala="Kerala";*/
    public  static boolean flag=false;
    public static boolean JAMMU,HARYANA,DELHI,UTTARPRADESH,GUJARAT,MADHYAPRADESH,BIHAR,WB,ASSAM,MAHARASHTRA,TELANGANA,KARNATAKA,KERALA;

    public static boolean JAMMU_UPDATED,HARYANA_UPDATED,DELHI_UPDATED,UTTARPRADESH_UPDATED,GUJARAT_UPDATED,MADHYAPRADESH_UPDATED,BIHAR_UPDATED,WB_UPDATED,ASSAM_UPDATED,MAHARASHTRA_UPDATED,TELANGANA_UPDATED,KARNATAKA_UPDATED,KERALA_UPDATED;

    public static boolean allChecked;

    public static int statesCounter=0;

    public static String selectedStates="";

    public static int selectedBookMarkPosition[];


    //upcoming news
    public static String imgUrl[];
    public static String upcomingdate[];
    public static String upcomingNewsID[];

    public static String preImgUrl[];
    public static String preDate[];
    public static String preId[];


    //FOR GALLERY IMAGES

    public static String detail[];
    public static String gellery[];
    public static String imgOne[];
    public static String imgTwo[];
    public static String imgThree[];
    public static String imgFour[];
    public static String imgFive[];
    public static String imgSix[];
    public static String imgSever[];


    ////Category News side drawer

    public static String categoryTitle[];
    public static String categoryContent[];
    public static String categoryPublisher[];
    public static String categoryDate[];
    public static String categoryImageURL[];
    public static String categoryNewsURL[];
    public static String categoryNewsID[];
    public static String categoryTag[];
    public static int categoryFollowStatus[];
    public static int categoryBookmarkStatus[];


    ////////////    SEARCH EVENTS  //////////////////////////

    public static String updescription[];
    public static String upimg[];
    public static String upnewsId[];
    public static String upnews_date[];

    //previous search

    public static String predescription[];
    public static String preimg[];
    public static String prenewsId[];
    public static String prenews_date[];


    ///////////  TICKER NEWS //////////////////

    public static String tickerTitleArray[];
    public static String tickerContent[];
    public static String tickerPublisher[];
    public static String tickerDate[];
    public static String tickerImageURL[];
    public static String tickerNewsURL[];
    public static String tickerNewsID[];
    public static String tickerTag[];
    public static int tickerFollowStatus[];
    public static int tickerBookmarkStatus[];


}
