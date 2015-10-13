package com.android.appslure.newsd;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.BL.DrawerCategoryBL;
import com.android.CONSTANTS.Constant;
import com.android.Configuration.Configuration;
import com.android.DB.ChatPeopleBE;
import com.android.DB.DBOperation;
import com.android.LeftDrawerAdapter;
import com.android.appslure.newsd.CategoryFlipAdapter.Callback;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.flurry.android.FlurryAgent;
import com.google.android.gms.analytics.HitBuilders;
import com.loopj.android.image.SmartImageView;
import com.twotoasters.android.support.v7.widget.RecyclerView;

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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.emilsjolander.flipview.FlipView;
import se.emilsjolander.flipview.OverFlipMode;


public class CategoryFlipper extends AppCompatActivity implements  Callback, FlipView.OnFlipListener {

    private FlipView mFlipView;
    private CategoryFlipAdapter mAdapter;
    ProgressDialog mProgressDialog;
    boolean under;
    String deviceID;

    String clickedCategory;

    DrawerCategoryBL objDrawerCategoryBL;

    SmartImageView news_img;

    ChatPeopleBE objChatPeopleBE;

    Button btn_contact, btn_audio, btn_bookmark, btn_search;
    ImageView contact_tab, audio_tab, bookmark_tab, search_tab;

    String TITLES[] = {"INDIA", "WORLD", "BUSINESS", "LIFESTYLE", "ENTERTAINMENT", "TRENDING", "TECH+", "SPORTS", "HUMOUR & RUMOUR", "PEOPLE"};
    // int ICONS[] = {R.drawable.icon_india, R.drawable.icon_world, R.drawable.icon_biz, R.drawable.icon_lifestyle, R.drawable.icon_entertainment, R.drawable.icon_trending, R.drawable.icon_tech, R.drawable.icon_sports, R.drawable.icon_humor, R.drawable.icon_people};


    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter adapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout
    ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout drawer;
    private ListView leftList;
    private ListView rightList;
    private String[] leftListStrings;
    private String[] rightListStrings;
    DBOperation dbOperation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_flipper);

        try {
            MyApp.tracker().setScreenName("Flip Screen");
            MyApp.tracker().send(new HitBuilders.EventBuilder("UI", "OPEN")
                    .setLabel("Flip screen")
                    .build());

            // AffleInAppTracker.inAppTrackerViewName(getApplicationContext(), "Landing Screen", "App First Screen", "APP Open", null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        dbOperation=new DBOperation(this);
        mFlipView = (FlipView) findViewById(R.id.flip_view);

        mProgressDialog=new ProgressDialog(CategoryFlipper.this);

        objDrawerCategoryBL=new DrawerCategoryBL();

        mAdapter = new CategoryFlipAdapter(this,mProgressDialog,CategoryFlipper.this);
        mAdapter.setCallback(this);
        mFlipView.setAdapter(mAdapter);
        mFlipView.setOnFlipListener(this);
        mFlipView.peakNext(true);
        mFlipView.setOverFlipMode(OverFlipMode.RUBBER_BAND);
        mFlipView.setEmptyView(findViewById(R.id.empty_view));
        //mFlipView.setOnOverFlipListener(this);

        deviceID= Configuration.getSharedPrefrenceValue(getApplicationContext(), Constant.SHARED_PREFERENCE_ANDROID_ID);

        clickedCategory=getIntent().getStringExtra("Category");

        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        Bundle parameters = new Bundle();
        parameters.putString(AppEventsConstants.EVENT_PARAM_DESCRIPTION, "Clicked Category is "+clickedCategory+" by "+deviceID);
        logger.logEvent("CategoryNews Screen",parameters);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo);

        objChatPeopleBE=new ChatPeopleBE();
        btn_contact = (Button) findViewById(R.id.btn_contact);
        btn_audio = (Button) findViewById(R.id.btn_audio);
        btn_bookmark = (Button) findViewById(R.id.btn_bookmark);
        btn_search = (Button) findViewById(R.id.btn_search);

        contact_tab = (ImageView) findViewById(R.id.contact_tab);
        audio_tab = (ImageView) findViewById(R.id.audio_tab);
        bookmark_tab = (ImageView) findViewById(R.id.bookmark_tab);
        search_tab = (ImageView) findViewById(R.id.search_tab);
        news_img = (SmartImageView) findViewById(R.id.news_img);


/*
        if(Configuration.isInternetConnection(CategoryFlipper.this)) {
            try {
                String newsText = new GetTickerNews().execute().get();
                setData(newsText);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            Constant.tickerTitle="You are offline. Please connect to internet";
        }*/


       /* mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View
        mRecyclerView.setHasFixedSize(true);                            // Letting the s0ystem know that the list objects are of fixed size
        adapter = new MyAdapter(TITLES, getApplicationContext());       // Creating the Adapter of com.example.balram.sampleactionbar.MyAdapter class(which we are going to see in a bit)

        // And passing the titles,icons,header view name, header view email,
        // and header  view profile picture
        mRecyclerView.setAdapter(adapter);                              // Setting the adapter to RecyclerView
        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view

*/

        drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        leftList = (ListView) findViewById(R.id.left_list);
        rightList = (ListView) findViewById(R.id.right_list);
        rightList.setVisibility(View.GONE);

        rightListStrings = getResources().getStringArray(R.array.right3);
        List<String> stringList = Arrays.asList(rightListStrings);

        rightList.setAdapter(new MayAdapter(CategoryFlipper.this, R.layout.drawer_list_raw_right, stringList));

        leftListStrings = getResources().getStringArray(R.array.left);

        leftList.setAdapter(new LeftDrawerAdapter(getApplicationContext()));

        rightList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Constant.Categorypage_no=0;

                if(position==1)
                {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.Jammu;
                    new GetCategoryCategory().execute(clickedCategory,Constant.Categorypage_no+"");
                }
                if(position==2)
                {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.UP;
                    new GetCategoryCategory().execute(clickedCategory,Constant.Categorypage_no+"");
                }
                if(position==3)
                {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.Delhi;
                    new GetCategoryCategory().execute(clickedCategory,Constant.Categorypage_no+"");
                }
                if(position==4)
                {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.Haryane;
                    new GetCategoryCategory().execute(clickedCategory,Constant.Categorypage_no+"");
                }
                if(position==5)
                {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.Gujarat;
                    new GetCategoryCategory().execute(clickedCategory,Constant.Categorypage_no+"");
                }
                if(position==6)
                {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.MP;
                    new GetCategoryCategory().execute(clickedCategory,Constant.Categorypage_no+"");
                }
                if(position==7)
                {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.Bihar;
                    new GetCategoryCategory().execute(clickedCategory,Constant.Categorypage_no+"");
                }
                if(position==8)
                {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.wb;
                    new GetCategoryCategory().execute(clickedCategory,Constant.Categorypage_no+"");
                }
                if(position==9)
                {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.Assam;
                    new GetCategoryCategory().execute(clickedCategory,Constant.Categorypage_no+"");
                }
                if(position==10)
                {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.Maharashtra;
                    new GetCategoryCategory().execute(clickedCategory,Constant.Categorypage_no+"");
                }
                if(position==11)
                {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.Telangana;
                    new GetCategoryCategory().execute(clickedCategory,Constant.Categorypage_no+"");
                }
                if(position==12)
                {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.Karnataka;
                    new GetCategoryCategory().execute(clickedCategory,Constant.Categorypage_no+"");
                }
                if(position==13)
                {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.Kerala;
                    new GetCategoryCategory().execute(clickedCategory,Constant.Categorypage_no+"");
                }
            }
        });

        leftList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                Constant.Categorypage_no=0;

                if (arg2 == 1) {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.India;
                    //dbOperation.createTablesIndia();
                    if(Configuration.isInternetConnection(CategoryFlipper.this)) {
                        dbOperation.open();
                        dbOperation.delete(objChatPeopleBE.getTableIndia());
                        dbOperation.close();
                        new GetCategoryCategory().execute(clickedCategory, Constant.Categorypage_no + "");
                    }
                    else
                    {
                        getData(objChatPeopleBE.getTableIndia());
                        Intent intent = new Intent(CategoryFlipper.this, CategoryFlipper.class);
                        intent.putExtra("Category", clickedCategory);
                        startActivity(intent);
                    }
                }
                if (arg2 == 2) {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.World;
                    if(Configuration.isInternetConnection(CategoryFlipper.this)) {
                        dbOperation.open();
                        dbOperation.delete(objChatPeopleBE.getTableWorld());
                        dbOperation.close();
                        new GetCategoryCategory().execute(clickedCategory, Constant.Categorypage_no + "");

                    }
                    else
                    {
                        getData(objChatPeopleBE.getTableWorld());
                        Intent intent = new Intent(CategoryFlipper.this, CategoryFlipper.class);
                        intent.putExtra("Category", clickedCategory);
                        startActivity(intent);
                    }

                }
               /* if (arg2 == 3) {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.Trending;
                    //dbOperation.createTablesTrending();
                    if(Configuration.isInternetConnection(CategoryFlipper.this)) {
                        dbOperation.open();
                        dbOperation.delete(objChatPeopleBE.getTABLE_Trending());
                        dbOperation.close();
                        new GetCategoryCategory().execute(clickedCategory, Constant.Categorypage_no + "");
                    }
                    else
                    {
                        getData(objChatPeopleBE.getTABLE_Trending());
                        Intent intent = new Intent(CategoryFlipper.this, CategoryFlipper.class);
                        intent.putExtra("Category", clickedCategory);
                        startActivity(intent);
                    }
                }*/
                if (arg2 == 3) {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.Humour_Rumour;
                    //dbOperation.createTablesHumour();

                    if(Configuration.isInternetConnection(CategoryFlipper.this)) {
                        dbOperation.open();
                        dbOperation.delete(objChatPeopleBE.getTABLE_Humour());
                        dbOperation.close();
                        new GetCategoryCategory().execute(clickedCategory,Constant.Categorypage_no+"");
                    }
                    else
                    {
                        getData(objChatPeopleBE.getTABLE_Humour());
                        Intent intent = new Intent(CategoryFlipper.this, CategoryFlipper.class);
                        intent.putExtra("Category", clickedCategory);
                        startActivity(intent);
                    }
                }

                if (arg2 == 4) {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }
                    else
                    {
                        rightList.setVisibility(View.VISIBLE);
                    }

                }

                if (arg2 == 5) {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.Economy;
                    // dbOperation.createTablesEconomy();
                    if(Configuration.isInternetConnection(CategoryFlipper.this)) {
                        dbOperation.open();
                        dbOperation.delete(objChatPeopleBE.getTABLE_Economy());
                        dbOperation.close();

                        new GetCategoryCategory().execute(clickedCategory,Constant.Categorypage_no+"");
                    }
                    else
                    {
                        getData(objChatPeopleBE.getTABLE_Economy());
                        Intent intent = new Intent(CategoryFlipper.this, CategoryFlipper.class);
                        intent.putExtra("Category", clickedCategory);
                        startActivity(intent);
                    }

                }

                if (arg2 == 6) {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.Bussiness;
                    // dbOperation.createTablesBussiness();
                    if(Configuration.isInternetConnection(CategoryFlipper.this)) {
                        dbOperation.open();
                        dbOperation.delete(objChatPeopleBE.getTABLE_Bussiness());
                        dbOperation.close();
                        new GetCategoryCategory().execute(clickedCategory,Constant.Categorypage_no+"");
                    }
                    else
                    {
                        getData(objChatPeopleBE.getTABLE_Bussiness());
                        Intent intent = new Intent(CategoryFlipper.this, CategoryFlipper.class);
                        intent.putExtra("Category", clickedCategory);
                        startActivity(intent);
                    }


                } if (arg2 == 7) {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.Tech;
                    //dbOperation.createTablesTech();
                    if(Configuration.isInternetConnection(CategoryFlipper.this)) {
                        dbOperation.open();
                        dbOperation.delete(objChatPeopleBE.getTABLE_Tech());
                        dbOperation.close();
                        new GetCategoryCategory().execute(clickedCategory,Constant.Categorypage_no+"");
                    }
                    else
                    {
                        getData(objChatPeopleBE.getTABLE_Tech());
                        Intent intent = new Intent(CategoryFlipper.this, CategoryFlipper.class);
                        intent.putExtra("Category", clickedCategory);
                        startActivity(intent);
                    }


                }

                ///////////

                if (arg2 == 8) {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.Sport;
                    //dbOperation.createTablesSports();
                    if(Configuration.isInternetConnection(CategoryFlipper.this)) {
                        dbOperation.open();
                        dbOperation.delete(objChatPeopleBE.getTABLE_Sports());
                        dbOperation.close();
                        new GetCategoryCategory().execute(clickedCategory,Constant.Categorypage_no+"");
                    }
                    else
                    {
                        getData(objChatPeopleBE.getTABLE_Sports());
                        Intent intent = new Intent(CategoryFlipper.this, CategoryFlipper.class);
                        intent.putExtra("Category", clickedCategory);
                        startActivity(intent);
                    }


                } if (arg2 == 9) {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.Entertainment;
                    //dbOperation.createTablesEntertainment();
                    if(Configuration.isInternetConnection(CategoryFlipper.this)) {
                        dbOperation.open();
                        dbOperation.delete(objChatPeopleBE.getTABLE_EnterTainment());
                        dbOperation.close();
                        new GetCategoryCategory().execute(clickedCategory,Constant.Categorypage_no+"");
                    }
                    else
                    {
                        getData(objChatPeopleBE.getTABLE_EnterTainment());
                        Intent intent = new Intent(CategoryFlipper.this, CategoryFlipper.class);
                        intent.putExtra("Category", clickedCategory);
                        startActivity(intent);
                    }


                }

                if (arg2 == 10) {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.People;

                    //dbOperation.createTablesLifeStyle();
                    if(Configuration.isInternetConnection(CategoryFlipper.this)) {
                        dbOperation.open();
                        dbOperation.delete(objChatPeopleBE.getTABLE_People());
                        dbOperation.close();
                        startActivity(new Intent(CategoryFlipper.this, PeopleList.class));
                    }
                    else
                    {
                        getData(objChatPeopleBE.getTABLE_People());
                        Intent intent = new Intent(CategoryFlipper.this, CategoryFlipper.class);
                        intent.putExtra("Category", clickedCategory);
                        startActivity(intent);
                    }
                    // new GetCategoryCategory().execute(clickedCategory,Constant.page_no+"");


                }
                if (arg2 == 11) {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.LifeStyle;
                    //dbOperation.createTablesLifeStyle();
                    if(Configuration.isInternetConnection(CategoryFlipper.this)) {
                        dbOperation.open();
                        dbOperation.delete(objChatPeopleBE.getTABLE_LifeStyle());
                        dbOperation.close();
                        new GetCategoryCategory().execute(clickedCategory,Constant.Categorypage_no+"");
                    }
                    else
                    {
                        getData(objChatPeopleBE.getTABLE_LifeStyle());
                        Intent intent = new Intent(CategoryFlipper.this, CategoryFlipper.class);
                        intent.putExtra("Category", clickedCategory);
                        startActivity(intent);
                    }

                }
                if (arg2 == 12) {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.StartUp;

                    //dbOperation.createTablesLifeStyle();
                    if(Configuration.isInternetConnection(CategoryFlipper.this)) {
                        dbOperation.open();
                        dbOperation.delete(objChatPeopleBE.getTABLE_Special());
                        dbOperation.close();
                        new GetSpecialCategory().execute(clickedCategory);
                    }
                    else
                    {
                        getData(objChatPeopleBE.getTABLE_Special());
                        Intent intent = new Intent(CategoryFlipper.this, CategoryFlipper.class);
                        intent.putExtra("Category", clickedCategory);
                        startActivity(intent);
                    }




                }

                if (arg2 == 13) {

                   startActivity(new Intent(getApplicationContext(),Setting.class));



                }
            }

        });



        mDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }


        }; // Drawer Toggle Object Made
        drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();





        btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contact_tab.getVisibility() == View.VISIBLE) {
                    contact_tab.setVisibility(View.INVISIBLE);
                } else {
                    contact_tab.setVisibility(View.VISIBLE);
                    audio_tab.setVisibility(View.INVISIBLE);
                    bookmark_tab.setVisibility(View.INVISIBLE);
                    search_tab.setVisibility(View.INVISIBLE);
                }
                startActivity(new Intent(CategoryFlipper.this, MyAccount.class));


            }
        });

        btn_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (audio_tab.getVisibility() == View.VISIBLE) {
                    audio_tab.setVisibility(View.INVISIBLE);
                } else {
                    audio_tab.setVisibility(View.VISIBLE);
                    contact_tab.setVisibility(View.INVISIBLE);

                    bookmark_tab.setVisibility(View.INVISIBLE);
                    search_tab.setVisibility(View.INVISIBLE);
                }
                startActivity(new Intent(getApplicationContext(),EventNews.class));
               // Toast.makeText(getApplicationContext(), "Audio Page Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btn_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookmark_tab.getVisibility() == View.VISIBLE) {
                    bookmark_tab.setVisibility(View.INVISIBLE);
                } else {
                    bookmark_tab.setVisibility(View.VISIBLE);
                    contact_tab.setVisibility(View.INVISIBLE);
                    audio_tab.setVisibility(View.INVISIBLE);

                    search_tab.setVisibility(View.INVISIBLE);
                }
                startActivity(new Intent(getApplicationContext(), BookMarkList.class));

            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search_tab.getVisibility() == View.VISIBLE) {
                    search_tab.setVisibility(View.INVISIBLE);
                } else {
                    search_tab.setVisibility(View.VISIBLE);
                    contact_tab.setVisibility(View.INVISIBLE);
                    audio_tab.setVisibility(View.INVISIBLE);
                    bookmark_tab.setVisibility(View.INVISIBLE);

                }
                startActivity(new Intent(getApplicationContext(), Search.class));
            }
        });


/*
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //Toast.makeText(getApplicationContext(),"Position"+position,Toast.LENGTH_SHORT).show();

                        if (position == 1) {
                            clickedCategory = Constant.India;
                            new GetCategoryCategory().execute(Constant.India, "0");
                        }
                        if (position == 2) {
                            clickedCategory = Constant.World;
                            new GetCategoryCategory().execute(Constant.World, "0");
                        }
                        if (position == 3) {
                            clickedCategory = Constant.Bussiness;
                            new GetCategoryCategory().execute(Constant.Bussiness, "0");
                        }
                        if (position == 4) {
                            clickedCategory = Constant.LifeStyle;
                            new GetCategoryCategory().execute(Constant.LifeStyle, "0");
                        }
                        if (position == 5) {
                            clickedCategory = Constant.Entertainment;
                            new GetCategoryCategory().execute(Constant.Entertainment, "0");
                        }
                        if (position == 6) {
                            clickedCategory = Constant.Trending;
                            new GetCategoryCategory().execute(Constant.Trending, "0");
                        }
                        if (position == 7) {
                            clickedCategory = Constant.Tech;
                            new GetCategoryCategory().execute(Constant.Tech, "0");
                        }
                        if (position == 8) {
                            clickedCategory = Constant.Sport;
                            new GetCategoryCategory().execute(Constant.Sport, "0");
                        }
                        if (position == 9) {
                            clickedCategory = Constant.Humour_Rumour;
                            new GetCategoryCategory().execute(Constant.Humour_Rumour, "0");
                        }


                    }
                })
        );

*/

        View logoView = getToolbarLogoView(toolbar);

        logoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logo clicked
                finish();
            }
        });



    }


    public  View getToolbarLogoView(Toolbar toolbar){
        //check if contentDescription previously was set
        boolean hadContentDescription = android.text.TextUtils.isEmpty(toolbar.getLogoDescription());
        String contentDescription = String.valueOf(!hadContentDescription ? toolbar.getLogoDescription() : "logoContentDescription");
        toolbar.setLogoDescription(contentDescription);
        ArrayList<View> potentialViews = new ArrayList<View>();
        //find the view based on it's content description, set programatically or with android:contentDescription
        toolbar.findViewsWithText(potentialViews,contentDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        //Nav icon is always instantiated at this point because calling setLogoDescription ensures its existence
        View logoIcon = null;
        if(potentialViews.size() > 0){
            logoIcon = potentialViews.get(0);
        }
        //Clear content description if not previously present
        if(hadContentDescription)
            toolbar.setLogoDescription(null);
        return logoIcon;
    }

    @Override
    public void onPageRequested(int page) {

        mFlipView.smoothFlipTo(page);


    }

    @Override
    public void onFlippedToPage(FlipView v, int position, long id) {
        Log.i("pageflip", "Page: " + position);

        if (position > mFlipView.getPageCount()-3 && mFlipView.getPageCount() < Constant.categoryNewsSize+12) {
            System.out.println("UNDER FLIPPED if Condition");
           /* mAdapter = new FlipAdapter(DemoViewFlipperActivity.this,12);
            mFlipView.setAdapter(mAdapter);*/



            // mAdapter.addItems(4);
            under=true;
        }
        else
        {
            if(Configuration.isInternetConnection(CategoryFlipper.this)) {
                System.out.println("UNDER FLIPPED else Condition");
                System.out.println("CONSTANT NEWS SIZE" + Constant.categoryNewsSize);


                Map<String, String> articleParams = new HashMap<String, String>();

//param keys and values have to be of String type
                articleParams.put("Title", Constant.categoryTitle[position]);
                articleParams.put("NewsID", Constant.categoryNewsID[position]);

//up to 10 params can be logged with each event
                FlurryAgent.logEvent("News Readed", articleParams);
                if (position == Constant.categoryNewsSize - 3) {

                    try {
                        Constant.Categorypage_no++;
                        new GetMoreData().execute(clickedCategory, String.valueOf(Constant.Categorypage_no));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                under = true;
            }
        }

    }

  /*  @Override
    public void onOverFlip(FlipView v, OverFlipMode mode, boolean overFlippingPrevious, float overFlipDistance, float flipDistancePerPage) {
        Log.i("overflip", "overFlipDistance = " + overFlipDistance);


        if (overFlipDistance > 100) {

        }

    }*/


    class GetMoreData extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            mProgressDialog.show();
            mProgressDialog.setMessage("Fetching news");
            mProgressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
           // String reult=objCategoryBL.getMoreDATA(params[0],params[1]);
            String result=objDrawerCategoryBL.getMoreDATA(params[0],params[1],dbOperation);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            mAdapter.addItems(Constant.categoryLastArraySize);
            mAdapter.notifyDataSetChanged();
            mProgressDialog.dismiss();

            //  Toast.makeText(getApplicationContext(),"PULL TO REFRESH WILL COME HERE",Toast.LENGTH_SHORT).show();


        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetTickerNews extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            String text = null;

            String url="";

            try {
                URI uri = new URI("http", "www.newsd.in", "/demo1/ws/special_five",url, null);
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


            return text;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
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

    void setData(String s)
    {
        String status="";
        try
        {
            JSONParser parser=new JSONParser();
            Object jsonObject=parser.parse(s);

            JSONArray jsonArray= (JSONArray) jsonObject;

            for(int i=0;i<jsonArray.size();i++) {
                JSONObject jsonObjected = (JSONObject) parser.parse(jsonArray.get(i).toString());
                Constant.tickerID = jsonObjected.get("id").toString();

                if (i == 0) {
                    Constant.tickerTitle = jsonObjected.get("title").toString();
                } else {
                    String text = "&#183;";
                    System.out.println(text);
                    Constant.tickerTitle = Constant.tickerTitle + "  " + Html.fromHtml(text) + "  " + jsonObjected.get("title").toString();
                }
            }

            //  System.out.println("Content--->" +jsonObjected.get("content").toString());


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);

        contact_tab.setVisibility(View.INVISIBLE);
        audio_tab.setVisibility(View.INVISIBLE);
        bookmark_tab.setVisibility(View.INVISIBLE);
        search_tab.setVisibility(View.INVISIBLE);


        if(Configuration.isInternetConnection(CategoryFlipper.this))
        {

        }
        else {
        }

    }


    private class GetCategoryCategory extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            mProgressDialog.setTitle("Getting News");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            String result=objDrawerCategoryBL.getCategoryNews(params[0], params[1],dbOperation);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            mProgressDialog.dismiss();
            Intent intent = new Intent(CategoryFlipper.this, CategoryFlipper.class);
            intent.putExtra("Category",clickedCategory);
            startActivity(intent);
            finish();
        }
    }


    class MayAdapter extends ArrayAdapter<String>{

        List<String> myList = null;
        public MayAdapter(Context context, int resource,List<String> objects) {
            super(context, resource, objects);
            myList = objects;
        }
        public List<String> getMyList() {
            return myList;
        }
        public void setMyList(List<String> myList) {
            this.myList = myList;
        }
    }


    void getData(String tableName) {
        Cursor cursor = dbOperation.getDataFromTableCategory(tableName);
        System.out.println("CURSOR COUNT"+cursor.getCount());

        Constant.categoryNewsSize=cursor.getCount();

        Constant.categoryTitle=new String[cursor.getCount()];
        Constant.categoryContent=new String[cursor.getCount()];
        Constant.categoryPublisher=new String[cursor.getCount()];
        Constant.categoryDate=new String[cursor.getCount()];
        Constant.categoryImageURL=new String[cursor.getCount()];
        Constant.categoryNewsURL=new String[cursor.getCount()];
        Constant.categoryNewsID=new String[cursor.getCount()];
        Constant.categoryTag=new String[cursor.getCount()];
        Constant.categoryFollowStatus=new int[cursor.getCount()];
        Constant.categoryBookmarkStatus=new int[cursor.getCount()];

        int i=0;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
               /* ChatPeopleBE people = addToChat(cursor.getString(0),
                        cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4), cursor.getString(5),cursor.getString(6), cursor.getString(7),cursor.getString(8));*/
                //ChatPeoples.add(people);

                Constant.categoryTitle[i] =cursor.getString(1);
                Constant.categoryContent[i] =cursor.getString(2);
                Constant.categoryPublisher[i] = cursor.getString(5);
                // Constant.date[i] = jsonObjected.get("date").toString();
                Constant.categoryImageURL[i] = cursor.getString(3);
                Constant.categoryNewsID[i] = cursor.getString(0);
                Constant.categoryNewsURL[i] = cursor.getString(4);
                Constant.categoryTag[i] =cursor.getString(6);


                i++;


            } while (cursor.moveToNext());
        }
        cursor.close();

    }

    private class GetPeopleCategory extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            mProgressDialog.setTitle("Getting News");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            String result=objDrawerCategoryBL.getPeopleCategoryNews(dbOperation,params[0],params[1]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            mProgressDialog.dismiss();
            Intent intent = new Intent(CategoryFlipper.this, CategoryFlipper.class);
            intent.putExtra("Category", clickedCategory);
            startActivity(intent);
        }
    }

    private class GetSpecialCategory extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            mProgressDialog.setTitle("Getting News");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            String result=objDrawerCategoryBL.getSpecialCategoryNews(dbOperation, params[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            mProgressDialog.dismiss();
            Intent intent = new Intent(CategoryFlipper.this, CategoryFlipper.class);
            intent.putExtra("Category", Constant.StartUp);
            startActivity(intent);
            finish();
        }
    }


}
