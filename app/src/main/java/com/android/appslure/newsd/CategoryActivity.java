package com.android.appslure.newsd;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.DB.DBOperation;
import com.facebook.appevents.AppEventsLogger;
import com.flurry.android.FlurryAgent;
import com.google.android.gms.analytics.HitBuilders;
import com.android.BE.CategoryBE;
import com.android.BL.CategoryBL;
import com.android.CONSTANTS.Constant;
import com.android.Configuration.Configuration;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.twotoasters.android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CategoryActivity extends AppCompatActivity {

    Button btn_india, btn_world, btn_biz, btn_tech, btn_lifestyle, btn_entertainment, btn_sports, btn_humor, btn_trending, btn_people;
    LinearLayout cat_drop_view, category1, category2;
    TextView txt_1;
    ImageButton btn_getnews, cat_india, cat_business, cat_tech, cat_startup, cat_metros,cat_people, cat_world, cat_humor, cat_economy, cat_trending, cat_entertainment, cat_lifestyle, cat_sports;
    int counter = 0;
    private boolean india_clicked = false, world_clicked = false, biz_clicked = false, lifestyle_clicked = false, entertainment_clicked = false, trending_clicked = false, tech_clicked = false, sports_clicked = false, humor_clicked = false, people_clicked = false, tech_txt = false, india_txt = false, world_txt = false, people_txt = false, sports_txt = false, biz_txt = false, lifestyle_txt = false, entertainment_txt = false, trending_txt = false, humor_txt = false;
    private ProgressDialog mProgressDialog;
    private ArrayList<HashMap<String, String>> arraylist;
    String TITLE = "title";
    String IMAGE = "image";
    String CONTENT = "content";
    String FLAG = "flag";
    private JSONObject jsonobject;
    private ListView listview;
    private JSONArray jsonarray;
    private String url = "http://ajax.googleapis.com/ajax/services/search/news?v=1.0&topic=h&ned=in&rsz=large";
    CategoryBE categoryBE_obj;
    private AnimatorSet set;
    private boolean cat_metros_selected,cat_india_selected, cat_business_selected, cat_tech_selected, cat_startup_selected, cat_people_selected, cat_world_selected, cat_humor_selected, cat_economy_selected, cat_trending_selected, cat_entertainment_selected, cat_lifestyle_selected, cat_sports_selected;
    ToggleButton cat_select_all_toggle;
    private AnimatorSet set1;
    private AnimatorSet set_metros,set_india,set_business,set_tech,set_startup,set_people,set_world,set_humor,set_economy,set_trending,set_entertainment,set_lifestyle,set_sports;

    String deviceID;
    String regID;
    GoogleCloudMessaging gcmObj;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

   // String TITLES[] = {"INDIA", "WORLD","BUSINESS","LIFESTYLE","ENTERTAINMENT","TRENDING","TECH+","SPORTS","HUMOUR N RUMOUR", "PEOPLE"};
   // int ICONS[] = {R.drawable.icon_india, R.drawable.icon_world,R.drawable.icon_biz,R.drawable.icon_lifestyle,R.drawable.icon_entertainment,R.drawable.icon_trending,R.drawable.icon_tech,R.drawable.icon_sports,R.drawable.icon_humor,R.drawable.icon_people};


    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter adapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout
    ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer Toggle

    String [] categoryList;
    String  categoryListSelected;
    CategoryBL objCategoryBL;
    DBOperation dbOperation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoryBE_obj = new CategoryBE();

        Typeface lato_font = Typeface.createFromAsset(this.getAssets(), "lato_light.ttf");


        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        logger.logEvent("Category Selection Screen");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.test_logo);
        toolbar.setNavigationIcon(R.drawable.nav_toggle);

        regID=Configuration.getSharedPrefrenceValue(CategoryActivity.this,Constant.SHARED_PREFERENCE_UpdateRegistrationID);

        try {


            System.out.println("ffffff" + regID);

            if (regID==null)  {

                if (checkPlayServices()) {
                    registerInBackground();

                    System.out.println("REGIGGG"+regID);
                }
            }
            else
            {
                System.out.println("REGIGGG"+regID);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        dbOperation=new DBOperation(this);
        cat_select_all_toggle = (ToggleButton)findViewById(R.id.cat_select_all_toggle);

        cat_india = (ImageButton) findViewById(R.id.cat_india);
        cat_business = (ImageButton) findViewById(R.id.cat_business);
        cat_tech = (ImageButton) findViewById(R.id.cat_tech);
        cat_startup = (ImageButton) findViewById(R.id.cat_startup);
        cat_people = (ImageButton) findViewById(R.id.cat_people);
        cat_world = (ImageButton) findViewById(R.id.cat_world);
        cat_humor = (ImageButton) findViewById(R.id.cat_humor);
        cat_economy = (ImageButton) findViewById(R.id.cat_economy);
        cat_trending = (ImageButton) findViewById(R.id.cat_trending);
        cat_entertainment = (ImageButton) findViewById(R.id.cat_entertainment);
        cat_lifestyle = (ImageButton) findViewById(R.id.cat_lifestyle);
        cat_sports = (ImageButton) findViewById(R.id.cat_sports);
        cat_metros= (ImageButton) findViewById(R.id.cat_metros);

        try {
            MyApp.tracker().setScreenName("Category List");
            MyApp.tracker().send(new HitBuilders.EventBuilder("UI", "OPEN")
                    .setLabel("Category List")
                    .build());

            // AffleInAppTracker.inAppTrackerViewName(getApplicationContext(), "Landing Screen", "App First Screen", "APP Open", null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        objCategoryBL = new CategoryBL();
        mProgressDialog = new ProgressDialog(CategoryActivity.this);

        deviceID= Configuration.getSharedPrefrenceValue(CategoryActivity.this,Constant.SHARED_PREFERENCE_ANDROID_ID);

        //final Animation flip_left = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.flip_left);


        cat_india.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cat_india.startAnimation(flip_left);
            }
        });

/********************************************************************************************/




        final Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        final Animation pop_up = AnimationUtils.loadAnimation(this, R.anim.bounce);

        btn_getnews = (ImageButton) findViewById(R.id.btn_getnews);
        btn_getnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new ShowNews().execute();

                System.out.println("COUNTER" + counter);
                if(counter==0 || counter==12)
                {
                    categoryList=new String[12];
                    categoryListSelected=Constant.India;
                    categoryListSelected=categoryListSelected+","+Constant.Bussiness;
                    categoryListSelected=categoryListSelected+","+Constant.Tech;
                    categoryListSelected=categoryListSelected+","+Constant.StartUp;
                    categoryListSelected=categoryListSelected+","+Constant.People;
                    categoryListSelected=categoryListSelected+","+Constant.World;
                    categoryListSelected=categoryListSelected+","+Constant.Humour_Rumour;
                    categoryListSelected=categoryListSelected+","+Constant.Economy;
                    categoryListSelected=categoryListSelected+","+Constant.Trending;
                    categoryListSelected=categoryListSelected+","+Constant.Entertainment;
                    categoryListSelected=categoryListSelected+","+Constant.LifeStyle;
                    categoryListSelected=categoryListSelected+","+Constant.Sport;

               /*     categoryList[0]="India";
                    categoryList[1]="Bussiness";
                    categoryList[2]="Tech";
                    categoryList[3]="Startup";
                    categoryList[4]="People";
                    categoryList[5]="World";
                    categoryList[6]="Humour & Rumour";
                    categoryList[7]="Economy";
                    categoryList[8]="Trending";
                    categoryList[9]="Entertainment";
                    categoryList[10]="Lifestyle";
                    categoryList[11]="Sports";*/

                }
                else
                {
                    categoryList=new String[counter];
                    int i=0;
                    if(Constant.INDIA_SELECTED.equalsIgnoreCase("selected"))
                    {
                        /*categoryList[i]="India";
                        i++;*/

                        categoryListSelected=Constant.India;
                        i++;
                    }
                    if(Constant.BUSINESS_SELECTED.equalsIgnoreCase("selected"))
                    {
                      /*  categoryList[i]="Bussiness";
                        i++;*/
                        if(i==0) {
                            categoryListSelected = Constant.Bussiness;
                            i++;
                        }
                        else
                            categoryListSelected=categoryListSelected+","+Constant.Bussiness;
                    }
                    if(Constant.TECH_SELECTED.equalsIgnoreCase("selected"))
                    {
                        if(i==0) {
                            categoryListSelected = Constant.Tech;
                            i++;
                        }
                        else
                        categoryListSelected=categoryListSelected+","+ Constant.Tech;
                       /* categoryList[i]="Tech";
                        i++;*/
                    }
                    if(Constant.STARTUP_SELECTED.equalsIgnoreCase("selected"))
                    {
                        if(i==0) {
                            categoryListSelected = Constant.StartUp;
                            i++;
                        }
                        else
                        categoryListSelected=categoryListSelected+","+ Constant.StartUp;
                        /*categoryList[i]="Startup";
                        i++;*/
                    }
                    if(Constant.PEOPLE_SELECTED.equalsIgnoreCase("selected"))
                    {
                        if(i==0) {
                            categoryListSelected = Constant.People;
                            i++;
                        }
                        else
                        categoryListSelected=categoryListSelected+","+Constant.People;
                        /*categoryList[i]="People";
                        i++;*/
                    }
                    if(Constant.WORLD_SELECTED.equalsIgnoreCase("selected"))
                    {
                        if(i==0) {
                            categoryListSelected =Constant.World;
                            i++;
                        }
                        else
                            categoryListSelected=categoryListSelected+","+Constant.World;
                        /*categoryList[i]="World";
                        i++;*/
                    }
                    if(Constant.HUMOR_SELECTED.equalsIgnoreCase("selected"))
                    {
                        if(i==0) {
                            categoryListSelected = Constant.Humour_Rumour;
                            i++;
                        }
                        else
                        categoryListSelected=categoryListSelected+","+Constant.Humour_Rumour;
                       /* categoryList[i]="Humour & Rumour";
                        i++;*/
                    }

                    if(Constant.ECONOMY_SELECTED.equalsIgnoreCase("selected"))
                    {
                        if(i==0) {
                            categoryListSelected = Constant.Economy;
                            i++;
                        }
                        else
                            categoryListSelected=categoryListSelected+","+Constant.Economy;
                       /* categoryList[i]="Economy";
                        i++;*/
                    }
                    if(Constant.TRENDING_SELECTED.equalsIgnoreCase("selected"))
                    {
                        if(i==0) {
                            categoryListSelected = Constant.Trending;
                            i++;
                        }
                        else
                        categoryListSelected=categoryListSelected+","+Constant.Trending;
                        /*categoryList[i]="Trending";
                        i++;*/
                    }
                    if(Constant.ENTERTAINMENT_SELECTED.equalsIgnoreCase("selected"))
                    {
                        if(i==0) {
                            categoryListSelected = Constant.Entertainment;
                            i++;
                        }
                        else
                            categoryListSelected=categoryListSelected+","+Constant.Entertainment;
                        /*categoryList[i]="Entertainment";
                        i++;*/
                    }
                    if(Constant.LIFESTYLE_SELECTED.equalsIgnoreCase("selected"))
                    {
                        if(i==0) {
                            categoryListSelected =Constant.LifeStyle;
                            i++;
                        }
                        else
                            categoryListSelected=categoryListSelected+","+Constant.LifeStyle;
                        /*categoryList[i]="Lifestyle";
                        i++;*/
                    }
                    if(Constant.SPORTS_SELECTED.equalsIgnoreCase("selected"))
                    {
                        if(i==0) {
                            categoryListSelected = Constant.Sport;
                            i++;
                        }
                        else
                        categoryListSelected=categoryListSelected+","+Constant.Sport;
                        /*categoryList[i]="Sports";
                        i++;*/
                    }

                }


               if(Constant.statesCounter>0) {
                    int counter=0;
                    if (Constant.JAMMU) {
                        Constant.selectedStates=Constant.Jammu;
                        counter++;
                    }
                    if(Constant.HARYANA)
                    {
                        if(counter==0) {
                            Constant.selectedStates = Constant.Haryane;
                            counter++;
                        }
                        else
                            Constant.selectedStates = Constant.selectedStates +","+ Constant.Haryane;
                    }
                    if(Constant.DELHI)
                    {
                        if(counter==0) {
                            Constant.selectedStates =Constant.Delhi;
                            counter++;
                        }
                        else
                            Constant.selectedStates = Constant.selectedStates +","+ Constant.Delhi;
                    }
                    ///////
                    if(Constant.UTTARPRADESH)
                    {
                        if(counter==0) {
                            Constant.selectedStates = Constant.UP;
                            counter++;
                        }
                        else
                            Constant.selectedStates = Constant.selectedStates +","+ Constant.UP;
                    }
                    if(Constant.GUJARAT)
                    {
                        if(counter==0) {
                            Constant.selectedStates =Constant.Gujarat;
                            counter++;
                        }
                        else
                            Constant.selectedStates = Constant.selectedStates +","+ Constant.Gujarat;
                    }

                    ////
                    if(Constant.MADHYAPRADESH)
                    {
                        if(counter==0) {
                            Constant.selectedStates = Constant.MP;
                            counter++;
                        }
                        else
                            Constant.selectedStates = Constant.selectedStates +","+ Constant.MP;
                    }
                    if(Constant.BIHAR)
                    {
                        if(counter==0) {
                            Constant.selectedStates =Constant.Bihar;
                            counter++;
                        }
                        else
                            Constant.selectedStates = Constant.selectedStates +","+ Constant.Bihar;
                    }
                    ///////////////////////////

                    if(Constant.WB)
                    {
                        if(counter==0) {
                            Constant.selectedStates = Constant.wb;
                            counter++;
                        }
                        else
                            Constant.selectedStates = Constant.selectedStates +","+ Constant.wb;
                    }
                    if(Constant.ASSAM)
                    {
                        if(counter==0) {
                            Constant.selectedStates =Constant.Assam;
                            counter++;
                        }
                        else
                            Constant.selectedStates = Constant.selectedStates +","+ Constant.Assam;
                    }

                    ////////////////////////////

                    if(Constant.MAHARASHTRA)
                    {
                        if(counter==0) {
                            Constant.selectedStates = Constant.Maharashtra;
                            counter++;
                        }
                        else
                            Constant.selectedStates = Constant.selectedStates +","+ Constant.Maharashtra;
                    }
                    if(Constant.TELANGANA)
                    {
                        if(counter==0) {
                            Constant.selectedStates =Constant.Telangana;
                            counter++;
                        }
                        else
                            Constant.selectedStates = Constant.selectedStates +","+ Constant.Telangana;
                    }

                    ////////////////////////////


                    if(Constant.KARNATAKA)
                    {
                        if(counter==0) {
                            Constant.selectedStates = Constant.Karnataka;
                            counter++;
                        }
                        else
                            Constant.selectedStates = Constant.selectedStates +","+ Constant.Karnataka;
                    }
                    if(Constant.KERALA)
                    {
                        if(counter==0) {
                            Constant.selectedStates =Constant.Kerala;
                            counter++;
                        }
                        else
                            Constant.selectedStates = Constant.selectedStates +","+ Constant.Kerala;
                    }
                }
                System.out.println("CATEGORY LIST SELECTED" + categoryListSelected);
                System.out.println("STATES LIST SELECTED" + Constant.selectedStates);

                Map<String, String> articleParams = new HashMap<String, String>();
//param keys and values have to be of String type
                articleParams.put("Categories",categoryListSelected );
                articleParams.put("States", Constant.selectedStates);
                articleParams.put("DeviceID", deviceID);

//up to 10 params can be logged with each event
                FlurryAgent.logEvent("Category_states_selected", articleParams);

                String param_value=Configuration.getSharedPrefrenceValue(getApplicationContext(),Constant.CampaignLadoo);
                try {
                    if (param_value == null) {
                        param_value = "Direct.Installation";
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                 new InsertSelectedCategory().execute(deviceID, categoryListSelected, "0", Constant.selectedStates,regID,param_value);



            }
        });

        cat_select_all_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cat_select_all_toggle.isChecked()){

                    Constant.flag=true;

                    counter=12;

                    set_india = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set_india.setTarget(cat_india);
                    set_india.start();
                    cat_india.setBackgroundResource(R.drawable.cat_bg_india_selected1);
                    cat_india_selected = true;
                    Constant.INDIA_SELECTED = "selected";



                    set_business = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set_business.setTarget(cat_business);
                    set_business.start();
                    cat_business.setBackgroundResource(R.drawable.cat_bg_business_selected1);
                    cat_business_selected = true;
                    Constant.BUSINESS_SELECTED = "selected";


                    set_tech = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set_tech.setTarget(cat_tech);
                    set_tech.start();
                    cat_tech.setBackgroundResource(R.drawable.cat_bg_tech_selected1);
                    cat_tech_selected = true;
                    Constant.TECH_SELECTED = "selected";


                    set_startup= (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set_startup.setTarget(cat_startup);
                    set_startup.start();
                    cat_startup.setBackgroundResource(R.drawable.cat_bg_startup_selected1);
                    cat_startup_selected = true;
                    Constant.STARTUP_SELECTED = "selected";

                    Constant.statesCounter=13;
                    Constant.JAMMU=true;
                    Constant.HARYANA=true;
                    Constant.DELHI=true;
                    Constant.UTTARPRADESH=true;
                    Constant.GUJARAT=true;
                    Constant.MADHYAPRADESH=true;
                    Constant.BIHAR=true;
                    Constant.WB=true;
                    Constant.ASSAM=true;
                    Constant.MAHARASHTRA=true;
                    Constant.TELANGANA=true;
                    Constant.KARNATAKA=true;
                    Constant.KERALA=true;
                    Constant.flag=true;


                    set_metros= (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set_metros.setTarget(cat_metros);
                    set_metros.start();
                    cat_metros.setBackgroundResource(R.drawable.cat_bg_metros_selected1);
                    cat_metros_selected = true;
                    Constant.METROS_SELECTED = "selected";



                    set_world= (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set_world.setTarget(cat_world);
                    set_world.start();
                    cat_world.setBackgroundResource(R.drawable.cat_bg_world_selected1);
                    cat_world_selected = true;
                    Constant.WORLD_SELECTED = "selected";


                    set_humor= (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set_humor.setTarget(cat_humor);
                    set_humor.start();
                    cat_humor.setBackgroundResource(R.drawable.cat_bg_humor_selected1);
                    cat_humor_selected = true;
                    Constant.HUMOR_SELECTED = "selected";


                    set_economy= (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set_economy.setTarget(cat_economy);
                    set_economy.start();
                    cat_economy.setBackgroundResource(R.drawable.cat_bg_economy_selected1);
                    cat_economy_selected = true;
                    Constant.ECONOMY_SELECTED = "selected";


                    set_trending= (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set_trending.setTarget(cat_trending);
                    set_trending.start();
                    cat_trending.setBackgroundResource(R.drawable.cat_bg_trending_selected1);
                    cat_trending_selected = true;
                    Constant.TRENDING_SELECTED = "selected";


                    set_entertainment= (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set_entertainment.setTarget(cat_entertainment);
                    set_entertainment.start();
                    cat_entertainment.setBackgroundResource(R.drawable.cat_bg_entertainment_selected1);
                    cat_entertainment_selected = true;
                    Constant.ENTERTAINMENT_SELECTED = "selected";


                    set_lifestyle= (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set_lifestyle.setTarget(cat_lifestyle);
                    set_lifestyle.start();
                    cat_lifestyle.setBackgroundResource(R.drawable.cat_bg_lifestyle_selected1);
                    cat_lifestyle_selected = true;
                    Constant.LIFESTYLE_SELECTED = "selected";


                    set_sports= (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set_sports.setTarget(cat_sports);
                    set_sports.start();
                    cat_sports.setBackgroundResource(R.drawable.cat_bg_sports_selected1);
                    cat_sports_selected = true;
                    Constant.SPORTS_SELECTED = "selected";
                }
                else {

                    counter=0;
                    set_india = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set_india.setTarget(cat_india);
                    set_india.start();
                    cat_india.setBackgroundResource(R.drawable.cat_bg_india);
                    cat_india_selected = false;
                    Constant.INDIA_SELECTED = "unselected";


                    set_business= (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set_business.setTarget(cat_business);
                    set_business.start();
                    cat_business.setBackgroundResource(R.drawable.cat_bg_business);
                    cat_business_selected = false;
                    Constant.BUSINESS_SELECTED = "unselected";


                    set_tech= (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set_tech.setTarget(cat_tech);
                    set_tech.start();
                    cat_tech.setBackgroundResource(R.drawable.cat_bg_tech);
                    cat_tech_selected = false;
                    Constant.TECH_SELECTED = "unselected";


                    set_startup= (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set_startup.setTarget(cat_startup);
                    set_startup.start();
                    cat_startup.setBackgroundResource(R.drawable.cat_bg_startup);
                    cat_startup_selected = false;
                    Constant.STARTUP_SELECTED = "unselected";

                    Constant.statesCounter=0;
                    Constant.JAMMU=false;
                    Constant.HARYANA=false;
                    Constant.DELHI=false;
                    Constant.UTTARPRADESH=false;
                    Constant.GUJARAT=false;
                    Constant.MADHYAPRADESH=false;
                    Constant.BIHAR=false;
                    Constant.WB=false;
                    Constant.ASSAM=false;
                    Constant.MAHARASHTRA=false;
                    Constant.TELANGANA=false;
                    Constant.KARNATAKA=false;
                    Constant.KERALA=false;
                    Constant.flag=false;


                    set_metros= (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set_metros.setTarget(cat_metros);
                    set_metros.start();
                    cat_metros.setBackgroundResource(R.drawable.cat_bg_metros);
                    cat_metros_selected = false;
                    Constant.METROS_SELECTED = "unselected";


                    set_world= (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set_world.setTarget(cat_world);
                    set_world.start();
                    cat_world.setBackgroundResource(R.drawable.cat_bg_world);
                    cat_world_selected = false;
                    Constant.WORLD_SELECTED = "unselected";


                    set_humor= (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set_humor.setTarget(cat_humor);
                    set_humor.start();
                    cat_humor.setBackgroundResource(R.drawable.cat_bg_humor);
                    cat_humor_selected = false;
                    Constant.HUMOR_SELECTED = "unselected";


                    set_economy= (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set_economy.setTarget(cat_economy);
                    set_economy.start();
                    cat_economy.setBackgroundResource(R.drawable.cat_bg_economy);
                    cat_economy_selected = false;
                    Constant.ECONOMY_SELECTED = "unselected";


                    set_trending= (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set_trending.setTarget(cat_trending);
                    set_trending.start();
                    cat_trending.setBackgroundResource(R.drawable.cat_bg_trending);
                    cat_trending_selected = false;
                    Constant.TRENDING_SELECTED = "unselected";


                    set_entertainment= (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set_entertainment.setTarget(cat_entertainment);
                    set_entertainment.start();
                    cat_entertainment.setBackgroundResource(R.drawable.cat_bg_entertainment);
                    cat_entertainment_selected = false;
                    Constant.ENTERTAINMENT_SELECTED = "unselected";


                    set_lifestyle= (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set_lifestyle.setTarget(cat_lifestyle);
                    set_lifestyle.start();
                    cat_lifestyle.setBackgroundResource(R.drawable.cat_bg_lifestyle);
                    cat_lifestyle_selected = false;
                    Constant.LIFESTYLE_SELECTED = "unselected";


                    set_sports= (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set_sports.setTarget(cat_sports);
                    set_sports.start();
                    cat_sports.setBackgroundResource(R.drawable.cat_bg_sports);
                    cat_sports_selected = false;
                    Constant.SPORTS_SELECTED = "unselected";

                }


            }
        });

        cat_india.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (cat_india_selected == false) {
                    set = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set.setTarget(cat_india);
                    set.start();
                    cat_india.setBackgroundResource(R.drawable.cat_bg_india_selected1);
                    cat_india_selected = true;
                    Constant.INDIA_SELECTED = "selected";
                    counter++;
                } else {
                    set = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set.setTarget(cat_india);
                    set.start();
                    cat_india.setBackgroundResource(R.drawable.cat_bg_india);
                    cat_india_selected = false;
                    Constant.INDIA_SELECTED = "unselected";
                    counter--;
                }

            }
        });


        cat_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (cat_business_selected == false) {
                    set = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set.setTarget(cat_business);
                    set.start();
                    cat_business.setBackgroundResource(R.drawable.cat_bg_business_selected1);
                    cat_business_selected = true;
                    Constant.BUSINESS_SELECTED = "selected";
                    counter++;
                } else {
                    set = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set.setTarget(cat_business);
                    set.start();
                    cat_business.setBackgroundResource(R.drawable.cat_bg_business);
                    cat_business_selected = false;
                    Constant.BUSINESS_SELECTED = "unselected";
                    counter--;
                }

            }
        });


        cat_tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (cat_tech_selected == false) {
                    set = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set.setTarget(cat_tech);
                    set.start();
                    cat_tech.setBackgroundResource(R.drawable.cat_bg_tech_selected1);
                    cat_tech_selected = true;
                    Constant.TECH_SELECTED = "selected";
                    counter++;
                   // startActivity(new Intent(CategoryActivity.this,MapSelection.class));
                } else {
                    set = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set.setTarget(cat_tech);
                    set.start();
                    cat_tech.setBackgroundResource(R.drawable.cat_bg_tech);
                    cat_tech_selected = false;
                    Constant.TECH_SELECTED = "unselected";
                    counter--;
                }

            }
        });
        cat_metros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    set = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set.setTarget(cat_metros);
                    set.start();
                    startActivity(new Intent(CategoryActivity.this, MapMainActivity.class));


            }
        });


        cat_startup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (cat_startup_selected == false) {
                    set = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set.setTarget(cat_startup);
                    set.start();
                    cat_startup.setBackgroundResource(R.drawable.cat_bg_startup_selected1);
                    cat_startup_selected = true;
                    Constant.STARTUP_SELECTED = "selected";
                    counter++;
                } else {
                    System.out.println("else condtiton is goin eeeeee");
                    set = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set.setTarget(cat_startup);
                    set.start();
                    cat_startup.setBackgroundResource(R.drawable.cat_bg_startup);
                    cat_startup_selected = false;
                    Constant.STARTUP_SELECTED = "unselected";
                    counter--;
                }

            }
        });


        cat_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),PeopleSelection.class));

               /* if (cat_people_selected == false) {
                    set = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set.setTarget(cat_people);
                    set.start();
                    cat_people.setBackgroundResource(R.drawable.cat_bg_people_selected1);
                    cat_people_selected = true;
                    Constant.PEOPLE_SELECTED = "selected";

                    counter++;
                } else {
                    set = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set.setTarget(cat_people);
                    set.start();
                    cat_people.setBackgroundResource(R.drawable.cat_bg_people);
                    cat_people_selected = false;
                    Constant.PEOPLE_SELECTED = "unselected";
                    counter--;
                }*/

            }
        });


        cat_world.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (cat_world_selected == false) {
                    set = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set.setTarget(cat_world);
                    set.start();
                    cat_world.setBackgroundResource(R.drawable.cat_bg_world_selected1);
                    cat_world_selected = true;
                    Constant.WORLD_SELECTED = "selected";
                    counter++;
                } else {
                    set = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set.setTarget(cat_world);
                    set.start();
                    cat_world.setBackgroundResource(R.drawable.cat_bg_world);
                    cat_world_selected = false;
                    Constant.WORLD_SELECTED = "unselected";
                    counter--;
                }

            }
        });


        cat_humor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (cat_humor_selected == false) {
                    set = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set.setTarget(cat_humor);
                    set.start();
                    cat_humor.setBackgroundResource(R.drawable.cat_bg_humor_selected1);
                    cat_humor_selected = true;
                    Constant.HUMOR_SELECTED = "selected";
                    counter++;
                } else {
                    set = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set.setTarget(cat_humor);
                    set.start();
                    cat_humor.setBackgroundResource(R.drawable.cat_bg_humor);
                    cat_humor_selected = false;
                    Constant.HUMOR_SELECTED = "unselected";
                    counter--;
                }

            }
        });


        cat_economy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (cat_economy_selected == false) {
                    set = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set.setTarget(cat_economy);
                    set.start();
                    cat_economy.setBackgroundResource(R.drawable.cat_bg_economy_selected1);
                    cat_economy_selected = true;
                    Constant.ECONOMY_SELECTED = "selected";
                    counter++;
                } else {
                    set = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set.setTarget(cat_economy);
                    set.start();
                    cat_economy.setBackgroundResource(R.drawable.cat_bg_economy);
                    cat_economy_selected = false;
                    Constant.ECONOMY_SELECTED = "unselected";
                    counter--;
                }

            }
        });


        cat_trending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (cat_trending_selected == false) {
                    set = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set.setTarget(cat_trending);
                    set.start();
                    cat_trending.setBackgroundResource(R.drawable.cat_bg_trending_selected1);
                    cat_trending_selected = true;
                    Constant.TRENDING_SELECTED = "selected";
                    counter++;
                } else {
                    set = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set.setTarget(cat_trending);
                    set.start();
                    cat_trending.setBackgroundResource(R.drawable.cat_bg_trending);
                    cat_trending_selected = false;
                    Constant.TRENDING_SELECTED = "unselected";
                    counter--;
                }

            }
        });


        cat_entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (cat_entertainment_selected == false) {
                    set = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set.setTarget(cat_entertainment);
                    set.start();
                    cat_entertainment.setBackgroundResource(R.drawable.cat_bg_entertainment_selected1);
                    cat_entertainment_selected = true;
                    Constant.ENTERTAINMENT_SELECTED = "selected";
                    counter++;
                } else {
                    set = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set.setTarget(cat_entertainment);
                    set.start();
                    cat_entertainment.setBackgroundResource(R.drawable.cat_bg_entertainment);
                    cat_entertainment_selected = false;
                    Constant.ENTERTAINMENT_SELECTED = "unselected";
                    counter--;
                }

            }
        });


        cat_lifestyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (cat_lifestyle_selected == false) {
                    set = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set.setTarget(cat_lifestyle);
                    set.start();
                    cat_lifestyle.setBackgroundResource(R.drawable.cat_bg_lifestyle_selected1);
                    cat_lifestyle_selected = true;
                    Constant.LIFESTYLE_SELECTED = "selected";
                    counter++;
                } else {
                    set = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set.setTarget(cat_lifestyle);
                    set.start();
                    cat_lifestyle.setBackgroundResource(R.drawable.cat_bg_lifestyle);
                    cat_lifestyle_selected = false;
                    Constant.LIFESTYLE_SELECTED = "unselected";
                    counter--;
                }

            }
        });

        cat_sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (cat_sports_selected == false) {
                    set = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set.setTarget(cat_sports);
                    set.start();
                    cat_sports.setBackgroundResource(R.drawable.cat_bg_sports_selected1);
                    cat_sports_selected = true;
                    Constant.SPORTS_SELECTED = "selected";
                    counter++;
                } else {
                    set = (AnimatorSet) AnimatorInflater.loadAnimator(CategoryActivity.this, R.animator.flip_left);
                    set.setTarget(cat_sports);
                    set.start();
                    cat_sports.setBackgroundResource(R.drawable.cat_bg_sports);
                    cat_sports_selected = false;
                    Constant.SPORTS_SELECTED = "unselected";
                    counter--;
                }

            }
        });



    }

    private class ShowNews extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog

            // Set progressdialog title
            mProgressDialog.setTitle("Getting News");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

           // objCategoryBL.GetJson(categoryBE_obj);

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            Intent intent = new Intent(CategoryActivity.this, DemoViewFlipperActivity.class);
            intent.putExtra("CategoryBE_OBJ", categoryBE_obj);
            startActivity(intent);

            mProgressDialog.dismiss();
        }
    }

    private class InsertSelectedCategory extends AsyncTask<String,String,String>
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

            String result=objCategoryBL.insertCategory(params[0],params[1],params[2],params[3],params[4],dbOperation,params[5]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            mProgressDialog.dismiss();
            Intent intent = new Intent(CategoryActivity.this, DemoViewFlipperActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK)
        {
            if(Constant.statesCounter>0)
            {
                cat_metros.setBackgroundResource(R.drawable.cat_bg_metros_selected1);
            }
            else
            {
                cat_metros.setBackgroundResource(R.drawable.cat_bg_metros);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppEventsLogger.activateApp(this);

        /*String firstTimeSwipe=Configuration.getSharedPrefrenceValue(CategoryActivity.this,Constant.SHARED_PREFERENCE_Swipe_Category);
        if(firstTimeSwipe==null) {
           // startActivity(new Intent(CategoryActivity.this, CategorySwipe.class));
        }*/

        if(Constant.statesCounter>0)
        {
            cat_metros.setBackgroundResource(R.drawable.cat_bg_metros_selected1);
        }
        else
        {
            cat_metros.setBackgroundResource(R.drawable.cat_bg_metros);
        }

        if(Constant.counterPeople>0)
        {
            cat_people.setBackgroundResource(R.drawable.cat_bg_people_selected1);
        }
        else
        {
            cat_people.setBackgroundResource(R.drawable.cat_bg_people);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        // When Play services not found in device
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                // Show Error dialog to install Play services
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                /*Toast.makeText(
                        getApplicationContext(),
                        "This device doesn't support Play services, App will not work normally",
                        Toast.LENGTH_LONG).show();*/
                finish();
            }
            return false;
        } else {
           /* Toast.makeText(
                    getApplicationContext(),
                    "This device supports Play services, App will work normally",
                    Toast.LENGTH_LONG).show();*/
        }
        return true;
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
                    regID = gcmObj
                            .register(Constant.GOOGLE_PROJ_ID);
                    msg = "Registration ID :" + regID;

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {

                System.out.println(msg);
                if (!TextUtils.isEmpty(regID)) {
                    //storeRegIdinSharedPref(applicationContext, regId, emailID);

                    Configuration.setSharedPrefrenceValue(getApplicationContext(), Constant.PREFS_NAME, Constant.SHARED_PREFERENCE_UpdateRegistrationID, regID);
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

}
