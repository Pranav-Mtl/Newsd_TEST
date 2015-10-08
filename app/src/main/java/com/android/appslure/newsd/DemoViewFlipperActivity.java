package com.android.appslure.newsd;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.BE.CategoryBE;
import com.android.BL.CategoryBL;
import com.android.BL.DrawerCategoryBL;
import com.android.CONSTANTS.Constant;
import com.android.Configuration.AppRater;
import com.android.Configuration.Configuration;
import com.android.DB.ChatPeopleBE;
import com.android.DB.DBOperation;
import com.android.appslure.newsd.FlipAdapter.Callback;
import com.facebook.appevents.AppEventsLogger;
import com.flurry.android.FlurryAgent;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.loopj.android.image.SmartImageView;
import com.mikepenz.materialdrawer.Drawer;
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

/**
 * Created by appslure on 5/28/2015.
 */
public class DemoViewFlipperActivity extends AppCompatActivity implements Callback, FlipView.OnFlipListener{
    private ViewFlipper viewFlipper;
    private float lastX;

    private FlipView mFlipView;
    private FlipAdapter mAdapter;

    DrawerCategoryBL objDrawerCategoryBL;
    DBOperation dbOperation;


    Button btn_contact, btn_audio, btn_bookmark, btn_search,btnRefresh;
    ImageView contact_tab, audio_tab, bookmark_tab, search_tab;
    ImageButton img_btn_add_post_bookmark, img_btn_post_setting, img_btn_post_share;
    TextView post_heading, post_text, ticker_text, post_publisher;
    SmartImageView news_img;
    CategoryBL objCategoryBL;
    CategoryBE categoryBE_obj;
    private ProgressDialog mProgressDialog;
    boolean under;
    Drawer result;
    String deviceID;
    String regId;



    String clickedCategory;

    String TITLES[] = {"INDIA", "WORLD", "BUSINESS", "LIFESTYLE", "ENTERTAINMENT", "TRENDING", "TECH+", "SPORTS", "HUMOUR & RUMOUR", "PEOPLE","STATES     ^","J&K. Punjab. HP","Haryana. Punjab","Delhi. NCR","UP. Uttarakhand","Gujarat","MP. Chhatishgarh","Bihar. Jharkhand","WB. Orissa","Assam. North East","Maharashtra. Goa. Pondicherry","Telangana. AP. Tamilnadu","Karnataka","Kerala"};
   // int ICONS[] = {R.drawable.icon_india, R.drawable.icon_world, R.drawable.icon_biz, R.drawable.icon_lifestyle, R.drawable.icon_entertainment, R.drawable.icon_trending, R.drawable.icon_tech, R.drawable.icon_sports, R.drawable.icon_humor, R.drawable.icon_people};


    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter adapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout
    ActionBarDrawerToggle mDrawerToggle;

                 // Declaring Action Bar Drawer Toggle

    private DrawerLayout drawer;
    private ListView leftList;
    private ListView rightList;
    private String[] leftListStrings;
    private String[] rightListStrings;
    TextView tvTicker;

    ChatPeopleBE objChatPeopleBE;

    GoogleCloudMessaging gcmObj;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_flipper_layout);

        AppEventsLogger logger = AppEventsLogger.newLogger(this);

        logger.logEvent("NewsFlip Screen");




       /* try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.media.newsd",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }*/


        categoryBE_obj = new CategoryBE();
        objCategoryBL = new CategoryBL();
        objChatPeopleBE=new ChatPeopleBE();
        objDrawerCategoryBL=new DrawerCategoryBL();

        mProgressDialog=new ProgressDialog(DemoViewFlipperActivity.this);

        mFlipView = (FlipView) findViewById(R.id.flip_view);

       // mFlipView.setOnOverFlipListener(this);

        tvTicker= (TextView) findViewById(R.id.ticker_text);

        mProgressDialog=new ProgressDialog(DemoViewFlipperActivity.this);

        regId=Configuration.getSharedPrefrenceValue(DemoViewFlipperActivity.this,Constant.SHARED_PREFERENCE_UpdateRegistrationID);

        if(regId==null)
        {
            registerInBackground();
        }

        deviceID= Configuration.getSharedPrefrenceValue(getApplicationContext(),Constant.SHARED_PREFERENCE_ANDROID_ID);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo);

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

        dbOperation = new DBOperation(this);
       // dbOperation.createTablesIndia();



        // toolbar.setNavigationIcon(R.drawable.nav_toggle);
        // getSupportActionBar().setDisplayShowTitleEnabled(true);

        post_heading = (TextView) findViewById(R.id.post_heading);
        post_text = (TextView) findViewById(R.id.post_text);

 /*       ticker_text.setSelected(true);
        ticker_text.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        ticker_text.setSingleLine(true);
*/
        btn_contact = (Button) findViewById(R.id.btn_contact);
        btn_audio = (Button) findViewById(R.id.btn_audio);
        btn_bookmark = (Button) findViewById(R.id.btn_bookmark);
        btn_search = (Button) findViewById(R.id.btn_search);
        btnRefresh= (Button) findViewById(R.id.btn_refresh);

        contact_tab = (ImageView) findViewById(R.id.contact_tab);
        audio_tab = (ImageView) findViewById(R.id.audio_tab);
        bookmark_tab = (ImageView) findViewById(R.id.bookmark_tab);
        search_tab = (ImageView) findViewById(R.id.search_tab);
        news_img = (SmartImageView) findViewById(R.id.news_img);

        img_btn_post_share = (ImageButton) findViewById(R.id.img_btn_post_share);
        img_btn_post_setting = (ImageButton) findViewById(R.id.img_btn_post_setting);
        img_btn_add_post_bookmark = (ImageButton) findViewById(R.id.img_btn_add_post_bookmark);

        Bundle bundle = getIntent().getExtras();
        //CategoryBE obj = bundle.getParcelable("CategoryBE_OBJ");

        /*****************************************************************************/
        try {
            if (Configuration.isInternetConnection(DemoViewFlipperActivity.this))
            {
                String newsText = new GetTickerNews().execute().get();
                setData(newsText);
            }
            else
            {
                Constant.tickerTitle="You are offline. Please connect to the internet";
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //dbOperation= (DBOperation) getIntent().getSerializableExtra("DBOperation");


        drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        leftList = (ListView) findViewById(R.id.left_list);
        rightList = (ListView) findViewById(R.id.right_list);
        rightList.setVisibility(View.GONE);

        AppRater.app_launched(this);

        rightListStrings = getResources().getStringArray(R.array.right3);
        List<String> stringList = Arrays.asList(rightListStrings);

        rightList.setAdapter(new MayAdapter(DemoViewFlipperActivity.this, R.layout.drawer_list_raw_right, stringList));

        leftListStrings = getResources().getStringArray(R.array.left);



        leftList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_raw, Constant.leftDrawer));


        rightList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Constant.Categorypage_no = 0;

                if (position == 1) {

                    if (rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory = Constant.Jammu;
                    new GetCategoryCategory().execute(clickedCategory, Constant.Categorypage_no + "");
                }
                if (position == 2) {

                    if (rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory = Constant.UP;
                    new GetCategoryCategory().execute(clickedCategory, Constant.Categorypage_no + "");
                }
                if (position == 3) {

                    if (rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory = Constant.Delhi;
                    new GetCategoryCategory().execute(clickedCategory, Constant.Categorypage_no + "");
                }
                if (position == 4) {

                    if (rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory = Constant.Haryane;
                    new GetCategoryCategory().execute(clickedCategory, Constant.Categorypage_no + "");
                }
                if (position == 5) {

                    if (rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory = Constant.Gujarat;
                    new GetCategoryCategory().execute(clickedCategory, Constant.Categorypage_no + "");
                }
                if (position == 6) {

                    if (rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory = Constant.MP;
                    new GetCategoryCategory().execute(clickedCategory, Constant.Categorypage_no + "");
                }
                if (position == 7) {

                    if (rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory = Constant.Bihar;
                    new GetCategoryCategory().execute(clickedCategory, Constant.Categorypage_no + "");
                }
                if (position == 8) {

                    if (rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory = Constant.wb;
                    new GetCategoryCategory().execute(clickedCategory, Constant.Categorypage_no + "");
                }
                if (position == 9) {

                    if (rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory = Constant.Assam;
                    new GetCategoryCategory().execute(clickedCategory, Constant.Categorypage_no + "");
                }
                if (position == 10) {

                    if (rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory = Constant.Maharashtra;
                    new GetCategoryCategory().execute(clickedCategory, Constant.Categorypage_no + "");
                }
                if (position == 11) {

                    if (rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory = Constant.Telangana;
                    new GetCategoryCategory().execute(clickedCategory, Constant.Categorypage_no + "");
                }
                if (position == 12) {

                    if (rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory = Constant.Karnataka;
                    new GetCategoryCategory().execute(clickedCategory, Constant.Categorypage_no + "");
                }
                if (position == 13) {

                    if (rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory = Constant.Kerala;
                    new GetCategoryCategory().execute(clickedCategory, Constant.Categorypage_no + "");
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
                    if(Configuration.isInternetConnection(DemoViewFlipperActivity.this)) {
                        dbOperation.open();
                        dbOperation.delete(objChatPeopleBE.getTableIndia());
                        dbOperation.close();
                        new GetCategoryCategory().execute(clickedCategory, Constant.Categorypage_no + "");
                    }
                    else
                    {
                        getData(objChatPeopleBE.getTableIndia());
                        Intent intent = new Intent(DemoViewFlipperActivity.this, CategoryFlipper.class);
                        intent.putExtra("Category", clickedCategory);
                        startActivity(intent);
                    }
                }
                if (arg2 == 2) {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.World;
                    if(Configuration.isInternetConnection(DemoViewFlipperActivity.this)) {
                        dbOperation.open();
                        dbOperation.delete(objChatPeopleBE.getTableWorld());
                        dbOperation.close();
                        new GetCategoryCategory().execute(clickedCategory, Constant.Categorypage_no + "");

                    }
                    else
                    {
                        getData(objChatPeopleBE.getTableWorld());
                        Intent intent = new Intent(DemoViewFlipperActivity.this, CategoryFlipper.class);
                        intent.putExtra("Category", clickedCategory);
                        startActivity(intent);
                    }

                }
                if (arg2 == 3) {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.Trending;
                    //dbOperation.createTablesTrending();
                    if(Configuration.isInternetConnection(DemoViewFlipperActivity.this)) {
                        dbOperation.open();
                        dbOperation.delete(objChatPeopleBE.getTABLE_Trending());
                        dbOperation.close();
                        new GetCategoryCategory().execute(clickedCategory, Constant.Categorypage_no + "");
                    }
                    else
                    {
                        getData(objChatPeopleBE.getTABLE_Trending());
                        Intent intent = new Intent(DemoViewFlipperActivity.this, CategoryFlipper.class);
                        intent.putExtra("Category", clickedCategory);
                        startActivity(intent);
                    }
                }
                if (arg2 == 4) {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.Humour_Rumour;
                    //dbOperation.createTablesHumour();

                    if(Configuration.isInternetConnection(DemoViewFlipperActivity.this)) {
                        dbOperation.open();
                        dbOperation.delete(objChatPeopleBE.getTABLE_Humour());
                        dbOperation.close();
                    new GetCategoryCategory().execute(clickedCategory,Constant.Categorypage_no+"");
                    }
                    else
                    {
                        getData(objChatPeopleBE.getTABLE_Humour());
                        Intent intent = new Intent(DemoViewFlipperActivity.this, CategoryFlipper.class);
                        intent.putExtra("Category", clickedCategory);
                        startActivity(intent);
                    }
                }

                if (arg2 == 5) {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }
                    else
                    {
                        rightList.setVisibility(View.VISIBLE);
                    }

                }

                if (arg2 == 6) {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.Economy;
                   // dbOperation.createTablesEconomy();
                    if(Configuration.isInternetConnection(DemoViewFlipperActivity.this)) {
                        dbOperation.open();
                        dbOperation.delete(objChatPeopleBE.getTABLE_Economy());
                        dbOperation.close();

                    new GetCategoryCategory().execute(clickedCategory,Constant.Categorypage_no+"");
                }
                else
                {
                    getData(objChatPeopleBE.getTABLE_Economy());
                    Intent intent = new Intent(DemoViewFlipperActivity.this, CategoryFlipper.class);
                    intent.putExtra("Category", clickedCategory);
                    startActivity(intent);
                }

                }

                if (arg2 == 7) {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.Bussiness;
                   // dbOperation.createTablesBussiness();
                    if(Configuration.isInternetConnection(DemoViewFlipperActivity.this)) {
                        dbOperation.open();
                        dbOperation.delete(objChatPeopleBE.getTABLE_Bussiness());
                        dbOperation.close();
                    new GetCategoryCategory().execute(clickedCategory,Constant.Categorypage_no+"");
                    }
                    else
                    {
                        getData(objChatPeopleBE.getTABLE_Bussiness());
                        Intent intent = new Intent(DemoViewFlipperActivity.this, CategoryFlipper.class);
                        intent.putExtra("Category", clickedCategory);
                        startActivity(intent);
                    }


                } if (arg2 == 8) {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.Tech;
                    //dbOperation.createTablesTech();
                    if(Configuration.isInternetConnection(DemoViewFlipperActivity.this)) {
                        dbOperation.open();
                        dbOperation.delete(objChatPeopleBE.getTABLE_Tech());
                        dbOperation.close();
                    new GetCategoryCategory().execute(clickedCategory,Constant.Categorypage_no+"");
                }
                else
                {
                    getData(objChatPeopleBE.getTABLE_Tech());
                    Intent intent = new Intent(DemoViewFlipperActivity.this, CategoryFlipper.class);
                    intent.putExtra("Category", clickedCategory);
                    startActivity(intent);
                }


            }

                ///////////

                if (arg2 == 9) {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.Sport;
                    //dbOperation.createTablesSports();
                    if(Configuration.isInternetConnection(DemoViewFlipperActivity.this)) {
                        dbOperation.open();
                        dbOperation.delete(objChatPeopleBE.getTABLE_Sports());
                        dbOperation.close();
                    new GetCategoryCategory().execute(clickedCategory,Constant.Categorypage_no+"");
                    }
                    else
                    {
                        getData(objChatPeopleBE.getTABLE_Sports());
                        Intent intent = new Intent(DemoViewFlipperActivity.this, CategoryFlipper.class);
                        intent.putExtra("Category", clickedCategory);
                        startActivity(intent);
                    }


                } if (arg2 == 10) {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.Entertainment;
                    //dbOperation.createTablesEntertainment();
                    if(Configuration.isInternetConnection(DemoViewFlipperActivity.this)) {
                        dbOperation.open();
                        dbOperation.delete(objChatPeopleBE.getTABLE_EnterTainment());
                        dbOperation.close();
                    new GetCategoryCategory().execute(clickedCategory,Constant.Categorypage_no+"");
                }
                else
                {
                    getData(objChatPeopleBE.getTABLE_EnterTainment());
                    Intent intent = new Intent(DemoViewFlipperActivity.this, CategoryFlipper.class);
                    intent.putExtra("Category", clickedCategory);
                    startActivity(intent);
                }


                }

                if (arg2 == 11) {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.People;

                    //dbOperation.createTablesLifeStyle();
                    if(Configuration.isInternetConnection(DemoViewFlipperActivity.this)) {
                        dbOperation.open();
                        dbOperation.delete(objChatPeopleBE.getTABLE_People());
                        dbOperation.close();
                        startActivity(new Intent(DemoViewFlipperActivity.this,PeopleList.class));
                    }
                    else
                    {
                        getData(objChatPeopleBE.getTABLE_People());
                        Intent intent = new Intent(DemoViewFlipperActivity.this, CategoryFlipper.class);
                        intent.putExtra("Category", clickedCategory);
                        startActivity(intent);
                    }

                    // new GetCategoryCategory().execute(clickedCategory,Constant.page_no+"");


                }
                if (arg2 == 12) {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.LifeStyle;
                    //dbOperation.createTablesLifeStyle();
                    if(Configuration.isInternetConnection(DemoViewFlipperActivity.this)) {
                        dbOperation.open();
                        dbOperation.delete(objChatPeopleBE.getTABLE_LifeStyle());
                        dbOperation.close();
                    new GetCategoryCategory().execute(clickedCategory,Constant.Categorypage_no+"");
                    }
                    else
                    {
                        getData(objChatPeopleBE.getTABLE_LifeStyle());
                        Intent intent = new Intent(DemoViewFlipperActivity.this, CategoryFlipper.class);
                        intent.putExtra("Category", clickedCategory);
                        startActivity(intent);
                    }

                }
                if (arg2 == 13) {

                    if(rightList.isShown()) {
                        rightList.setVisibility(View.GONE);
                    }

                    clickedCategory=Constant.StartUp;

                    //dbOperation.createTablesLifeStyle();
                    if(Configuration.isInternetConnection(DemoViewFlipperActivity.this)) {
                        dbOperation.open();
                        dbOperation.delete(objChatPeopleBE.getTABLE_Special());
                        dbOperation.close();
                        new GetSpecialCategory().execute(clickedCategory);
                    }
                    else
                    {
                        getData(objChatPeopleBE.getTABLE_Special());
                        Intent intent = new Intent(DemoViewFlipperActivity.this, CategoryFlipper.class);
                        intent.putExtra("Category", clickedCategory);
                        startActivity(intent);
                    }


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
        if(Configuration.isInternetConnection(DemoViewFlipperActivity.this)) {
            new GetFirstTimeNews().execute(regId);
        }
        else
        {
            getData();
        }

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
                if (Configuration.isInternetConnection(DemoViewFlipperActivity.this)) {
                    startActivity(new Intent(DemoViewFlipperActivity.this, MyAccount.class));
                }
                else
                {
                    AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                            DemoViewFlipperActivity.this);

// Setting Dialog Title
                    alertDialog2.setTitle("No Internet Connection");

// Setting Dialog Message
                    alertDialog2.setMessage("Internet Connection Not Found");

// Setting Icon to Dialog


// Setting Positive "Yes" Btn
                    alertDialog2.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog
                                    dialog.cancel();

                                }
                            });
// Setting Negative "NO" Btn


// Showing Alert Dialog
                    alertDialog2.show();

                }
            }
        });


        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*RotateAnimation ra =new RotateAnimation(0, 360);
                ra.setFillAfter(true);
                ra.setDuration(1000);
                btnRefresh.startAnimation(ra);
*/

                btnRefresh.startAnimation(
                        AnimationUtils.loadAnimation(DemoViewFlipperActivity.this, R.anim.btn_refresh));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(DemoViewFlipperActivity.this, DemoViewFlipperActivity.class);
                        //intent.putExtra("DBOperation",dbOperation);
                        startActivity(intent);
                        finish();
                    }
                },500);



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
                if (Configuration.isInternetConnection(DemoViewFlipperActivity.this)) {
                    startActivity(new Intent(getApplicationContext(),EventNews.class));
                }
                else
                {
                    AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                            DemoViewFlipperActivity.this);

// Setting Dialog Title
                    alertDialog2.setTitle("No Internet Connection");

// Setting Dialog Message
                    alertDialog2.setMessage("Internet Connection Not Found");

// Setting Icon to Dialog


// Setting Positive "Yes" Btn
                    alertDialog2.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog
                                    dialog.cancel();

                                }
                            });
// Setting Negative "NO" Btn



// Showing Alert Dialog
                    alertDialog2.show();

                }
                //Toast.makeText(getApplicationContext(), "Audio Page Clicked", Toast.LENGTH_SHORT).show();
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
                if (Configuration.isInternetConnection(DemoViewFlipperActivity.this)) {
                    startActivity(new Intent(getApplicationContext(), BookMarkList.class));
                }
                else
                {
                    AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                            DemoViewFlipperActivity.this);

// Setting Dialog Title
                    alertDialog2.setTitle("No Internet Connection");

// Setting Dialog Message
                    alertDialog2.setMessage("Internet Connection Not Found");

// Setting Icon to Dialog


// Setting Positive "Yes" Btn
                    alertDialog2.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog
                                    dialog.cancel();

                                }
                            });
// Setting Negative "NO" Btn


// Showing Alert Dialog
                    alertDialog2.show();

                }

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
                if (Configuration.isInternetConnection(DemoViewFlipperActivity.this)) {
                    startActivity(new Intent(getApplicationContext(), Search.class));
                }
                else
                {
                    AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                            DemoViewFlipperActivity.this);

// Setting Dialog Title
                    alertDialog2.setTitle("No Internet Connection");

// Setting Dialog Message
                    alertDialog2.setMessage("Internet Connection Not Found");

// Setting Icon to Dialog


// Setting Positive "Yes" Btn
                    alertDialog2.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog
                                    dialog.cancel();
                                }
                            });
// Setting Negative "NO" Btn


// Showing Alert Dialog
                    alertDialog2.show();

                }
            }
        });


        View logoView = getToolbarLogoView(toolbar);

        logoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logo clicked
                try {
                    if(Configuration.isInternetConnection(DemoViewFlipperActivity.this)) {
                        Intent intent = new Intent(DemoViewFlipperActivity.this, DemoViewFlipperActivity.class);
                        //intent.putExtra("DBOperation",dbOperation);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        mAdapter.callback.onPageRequested(0);
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });

    }


    @Override
    public void onPageRequested(int page) {

        mFlipView.smoothFlipTo(page);


    }

    @Override
    public void onFlippedToPage(FlipView v, int position, long id) {
        Log.i("pageflip", "Page: " + position);

        if (position > mFlipView.getPageCount()-3 && mFlipView.getPageCount() < Constant.newsSize+12) {
            System.out.println("UNDER FLIPPED if Condition");
           /* mAdapter = new FlipAdapter(DemoViewFlipperActivity.this,12);
                mFlipView.setAdapter(mAdapter);*/



           // mAdapter.addItems(4);
            under=true;
        }
        else
        {
            System.out.println("UNDER FLIPPED else Condition");
            System.out.println("CONSTANT NEWS SIZE" + Constant.newsSize);

            Map<String, String> articleParams = new HashMap<String, String>();

//param keys and values have to be of String type
            articleParams.put("Title", Constant.title[position]);
            articleParams.put("NewsID", Constant.newsID[position]);

//up to 10 params can be logged with each event
            FlurryAgent.logEvent("News Readed", articleParams);

            if(position==Constant.newsSize-3)
            {

                try {
                    Constant.page_no++;
                    new GetMoreData().execute(deviceID, String.valueOf(Constant.page_no),regId);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            under=true;
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
            String reult=objCategoryBL.getMoreDATA(params[0], params[1], dbOperation,params[2]);
            return reult;
        }

        @Override
        protected void onPostExecute(String s) {
            mAdapter.addItems(Constant.lastArraySize);
            mAdapter.notifyDataSetChanged();
            mProgressDialog.dismiss();

          //  Toast.makeText(getApplicationContext(),"PULL TO REFRESH WILL COME HERE",Toast.LENGTH_SHORT).show();


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
            Intent intent = new Intent(DemoViewFlipperActivity.this, CategoryFlipper.class);
            intent.putExtra("Category", clickedCategory);
            startActivity(intent);
        }
    }

    //////////////    GET NEWS ON PEOPLE TAB CLICK     //////////////////////////


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
            Intent intent = new Intent(DemoViewFlipperActivity.this, CategoryFlipper.class);
            intent.putExtra("Category", clickedCategory);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        contact_tab.setVisibility(View.INVISIBLE);
        audio_tab.setVisibility(View.INVISIBLE);
        bookmark_tab.setVisibility(View.INVISIBLE);
        search_tab.setVisibility(View.INVISIBLE);

        AppEventsLogger.activateApp(this);

            drawer.closeDrawers();






    }

    @Override
    protected void onPause() {
        super.onPause();

        AppEventsLogger.deactivateApp(this);
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

            Constant.tickerTitleArray=new String[jsonArray.size()];
            Constant.tickerBookmarkStatus=new int[jsonArray.size()];
            Constant.tickerContent=new String[jsonArray.size()];
            Constant.tickerDate=new String[jsonArray.size()];
            Constant.tickerFollowStatus=new int[jsonArray.size()];
            Constant.tickerImageURL=new String[jsonArray.size()];
            Constant.tickerNewsID=new String[jsonArray.size()];
            Constant.tickerNewsURL=new String[jsonArray.size()];
            Constant.tickerTag=new String[jsonArray.size()];
            Constant.tickerPublisher=new String[jsonArray.size()];


            for(int i=0;i<jsonArray.size();i++) {
                JSONObject jsonObjected = (JSONObject) parser.parse(jsonArray.get(i).toString());
                Constant.tickerTitleArray[i]=jsonObjected.get("title").toString();
                Constant.tickerContent[i]=jsonObjected.get("content").toString();
                Constant.tickerDate[i]=jsonObjected.get("date").toString();
                Constant.tickerImageURL[i]=jsonObjected.get("image").toString();
                Constant.tickerNewsID[i]=jsonObjected.get("id").toString();
                Constant.tickerNewsURL[i]=jsonObjected.get("link").toString();
                Constant.tickerTag[i]=jsonObjected.get("tag").toString();
                Constant.tickerPublisher[i]=jsonObjected.get("publisher").toString();

                    if(i==0) {
                        Constant.tickerTitle = jsonObjected.get("title").toString();
                    }
                else
                    {
                        String text = "&#183;";
                        System.out.println(text);
                        Constant.tickerTitle =Constant.tickerTitle +"  "+ Html.fromHtml(text)+"  "+ jsonObjected.get("title").toString();
                    }

            }

            tvTicker.setText(Constant.tickerTitle);


            //  System.out.println("Content--->" +jsonObjected.get("content").toString());


        }
        catch (Exception e)
        {
            e.printStackTrace();
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



    /////////////////////// GET OFFLINE DATA    /////////////////////////

    private class InsertSelectedCategory extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            mProgressDialog.show();
            mProgressDialog.setMessage("Refreshing...");
            mProgressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {

            String result=objCategoryBL.getCategoryData(params[0], params[1],dbOperation,params[2]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            mProgressDialog.dismiss();
            Intent intent = new Intent(DemoViewFlipperActivity.this, DemoViewFlipperActivity.class);
            //intent.putExtra("DBOperation",dbOperation);
            startActivity(intent);
            finish();

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
            Intent intent = new Intent(DemoViewFlipperActivity.this, CategoryFlipper.class);
            intent.putExtra("Category", Constant.StartUp);
            startActivity(intent);

        }
    }
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcmObj == null) {
                        gcmObj = GoogleCloudMessaging
                                .getInstance(getApplicationContext());
                    }
                    regId = gcmObj
                            .register(Constant.GOOGLE_PROJ_ID);
                    msg = "Registration ID :" + regId;

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {

                System.out.println(msg);
                if (!TextUtils.isEmpty(regId)) {
                    //storeRegIdinSharedPref(applicationContext, regId, emailID);
                    //savedRegID=regId;
                    Configuration.setSharedPrefrenceValue(getApplicationContext(), Constant.PREFS_NAME, Constant.SHARED_PREFERENCE_UpdateRegistrationID, regId);
                   /* Toast.makeText(
                            applicationContext,
                            "Registered with GCM Server successfully.\n\n"
                                    + msg, Toast.LENGTH_SHORT).show();*/
                } else {
                    /*Toast.makeText(
                            applicationContext,
                            "Reg ID Creation Failed.\n\nEither you haven't enabled Internet or GCM server is busy right now. Make sure you enabled Internet and try registering again after some time."
                                    + msg, Toast.LENGTH_LONG).show();*/
                }
            }
        }.execute(null, null, null);
    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    private class GetFirstTimeNews extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPreExecute() {

            mProgressDialog.show();
            mProgressDialog.setMessage("Loading News");
            mProgressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {

            mAdapter = new FlipAdapter(DemoViewFlipperActivity.this,mProgressDialog,DemoViewFlipperActivity.this,objCategoryBL,dbOperation,params[0]);

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                mAdapter.setCallback(DemoViewFlipperActivity.this);
                mFlipView.setAdapter(mAdapter);
                mFlipView.setOnFlipListener(DemoViewFlipperActivity.this);
                mFlipView.peakNext(true);
                mFlipView.setOverFlipMode(OverFlipMode.RUBBER_BAND);
                mFlipView.setEmptyView(findViewById(R.id.empty_view));

                Toast.makeText(DemoViewFlipperActivity.this,"News updated..",Toast.LENGTH_LONG).show();
            }
            catch (NullPointerException e){
                e.printStackTrace();
            }
            finally {
                mProgressDialog.dismiss();
            }



        }
    }

    void getData() {
        Cursor cursor = dbOperation.getDataFromTable();
        System.out.println("CURSOR COUNT"+cursor.getCount());

        Constant.newsSize=cursor.getCount();

        Constant.title=new String[cursor.getCount()];
        Constant.content=new String[cursor.getCount()];
        Constant.publisher=new String[cursor.getCount()];
        Constant.date=new String[cursor.getCount()];
        Constant.imageURL=new String[cursor.getCount()];
        Constant.newsURL=new String[cursor.getCount()];
        Constant.newsID=new String[cursor.getCount()];
        Constant.tag=new String[cursor.getCount()];
        Constant.followStatus=new int[cursor.getCount()];
        Constant.bookmarkStatus=new int[cursor.getCount()];

        int i=0;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
               /* ChatPeopleBE people = addToChat(cursor.getString(0),
                        cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4), cursor.getString(5),cursor.getString(6), cursor.getString(7),cursor.getString(8));*/
                //ChatPeoples.add(people);

                Constant.title[i] =cursor.getString(1);
                Constant.content[i] =cursor.getString(2);
                Constant.publisher[i] = cursor.getString(5);
                // Constant.date[i] = jsonObjected.get("date").toString();
                Constant.imageURL[i] = cursor.getString(3);
                Constant.newsID[i] = cursor.getString(0);
                Constant.newsURL[i] = cursor.getString(4);
                Constant.tag[i] =cursor.getString(6);


                i++;


            } while (cursor.moveToNext());
        }
        cursor.close();

        mAdapter = new FlipAdapter(DemoViewFlipperActivity.this,mProgressDialog,DemoViewFlipperActivity.this);
        mAdapter.setCallback(DemoViewFlipperActivity.this);
        mFlipView.setAdapter(mAdapter);
        mFlipView.setOnFlipListener(DemoViewFlipperActivity.this);
        mFlipView.peakNext(true);
        mFlipView.setOverFlipMode(OverFlipMode.RUBBER_BAND);
        mFlipView.setEmptyView(findViewById(R.id.empty_view));
    }

    public void startAnimation(Button ivDH)
    {
        System.out.println("Inside startAnimation()");
        Animation scaleAnim = new ScaleAnimation(0, 2, 0, 2);
        scaleAnim.setDuration(5000);
        scaleAnim.setRepeatCount(1);
        scaleAnim.setInterpolator(new AccelerateInterpolator());
        scaleAnim.setRepeatMode(Animation.REVERSE);

        Animation rotateAnim = new RotateAnimation(0, 360, Animation.ABSOLUTE, Animation.ABSOLUTE, Animation.ABSOLUTE, Animation.RELATIVE_TO_SELF);
        rotateAnim.setDuration(5000);
        rotateAnim.setRepeatCount(1);
        rotateAnim.setInterpolator(new AccelerateInterpolator());
        rotateAnim.setRepeatMode(Animation.REVERSE);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(scaleAnim);
        animationSet.addAnimation(rotateAnim);

        ivDH.startAnimation(animationSet);
    }
}