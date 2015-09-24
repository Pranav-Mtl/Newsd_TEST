package com.android.appslure.newsd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;


public class NewsScreenActivity extends DrawerActivity {

    Button btn_contact, btn_audio, btn_bookmark, btn_search;
    ImageView contact_tab, audio_tab, bookmark_tab, search_tab,news_img;
    ImageButton img_btn_add_post_bookmark, img_btn_post_setting, img_btn_post_share;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_screen);

/*

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.nav_toggle);
        toolbar.setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(true)
                .withSliderBackgroundColorRes(R.color.white)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("CATEGORIES").withTextColor(R.color.black).withIcon(R.drawable.icon_category),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("INDIA").withTextColor(R.color.black).withIcon(R.drawable.icon_india),
                        new SecondaryDrawerItem().withIcon(R.drawable.icon_world).withName("WORLD").withTextColor(R.color.black),
                        new SecondaryDrawerItem().withIcon(R.drawable.icon_biz).withName("BIZ N MONEY").withTextColor(R.color.black),
                        new SecondaryDrawerItem().withIcon(R.drawable.icon_lifestyle).withName("LIFESTYLE").withTextColor(R.color.black).withBadge("3").withBadgeTextColor(R.color.white),
                        new SecondaryDrawerItem().withIcon(R.drawable.icon_entertainment).withName("ENTERTAINMENT").withTextColor(R.color.black),
                        new SecondaryDrawerItem().withIcon(R.drawable.icon_trending).withName("TRENDING").withTextColor(R.color.black),
                        new SecondaryDrawerItem().withIcon(R.drawable.icon_tech).withName("TECH+").withTextColor(R.color.black),
                        new SecondaryDrawerItem().withIcon(R.drawable.icon_sports).withName("SPORTS").withTextColor(R.color.black),
                        new SecondaryDrawerItem().withIcon(R.drawable.icon_humor).withName("HUMOR N RUMOR").withTextColor(R.color.black),
                        new SecondaryDrawerItem().withIcon(R.drawable.icon_people).withName("PEOPLE").withTextColor(R.color.black)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        return true;
                    }
                })
                .build();

        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);

//use the result object to get different views of the drawer or modify it's data
//some sample calls
        result.setSelectionByIdentifier(1);
        result.openDrawer();
        result.closeDrawer();
        result.isDrawerOpen();

*/


        btn_contact = (Button) findViewById(R.id.btn_contact);
        btn_audio = (Button) findViewById(R.id.btn_audio);
        btn_bookmark = (Button) findViewById(R.id.btn_bookmark);
        btn_search = (Button) findViewById(R.id.btn_search);

        contact_tab = (ImageView) findViewById(R.id.contact_tab);
        audio_tab = (ImageView) findViewById(R.id.audio_tab);
        bookmark_tab = (ImageView) findViewById(R.id.bookmark_tab);
        search_tab = (ImageView) findViewById(R.id.search_tab);

        img_btn_post_share = (ImageButton) findViewById(R.id.img_btn_post_share);
        img_btn_post_setting = (ImageButton) findViewById(R.id.img_btn_post_setting);
        img_btn_add_post_bookmark = (ImageButton) findViewById(R.id.img_btn_add_post_bookmark);


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
                Toast.makeText(getApplicationContext(), "Contact Page Clicked", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "Audio Page Clicked", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "Bookmark Page Clicked", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "Search Page Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        img_btn_post_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Share post clicked", Toast.LENGTH_SHORT).show();
            }
        });


        img_btn_post_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Post settings clicked", Toast.LENGTH_SHORT).show();
            }
        });


        img_btn_add_post_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Post bookmark clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
