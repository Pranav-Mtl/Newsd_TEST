package com.android.appslure.newsd;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;


public class NavigationTest extends AppCompatActivity {


    private DrawerLayout drawer;
    private ListView leftList;
    private ListView rightList;
    private String[] leftListStrings;
    private String[] rightListStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_test);

        drawer = (DrawerLayout) findViewById(R.id.drawer);
        leftList = (ListView) findViewById(R.id.left_list);
        rightList = (ListView) findViewById(R.id.right_list);
        rightList.setVisibility(View.GONE);

        leftListStrings = getResources().getStringArray(R.array.left);

        leftList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,leftListStrings));

        leftList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {


                arg1.setBackgroundColor(Color.RED);

                if (arg2 == 0) {

                    rightListStrings = getResources().getStringArray(R.array.right1);

                    List<String> stringList = Arrays.asList(rightListStrings);

                    rightList.setAdapter(new MayAdapter(NavigationTest.this, android.R.layout.simple_list_item_1, stringList));
                    rightList.setVisibility(View.VISIBLE);
                }
                if (arg2 == 1) {

                    rightListStrings = getResources().getStringArray(R.array.right2);
                    List<String> stringList = Arrays.asList(rightListStrings);

                    rightList.setAdapter(new MayAdapter(NavigationTest.this, android.R.layout.simple_list_item_1, stringList));
                    rightList.setVisibility(View.VISIBLE);
                }
                if (arg2 == 2) {

                    rightListStrings = getResources().getStringArray(R.array.right3);
                    List<String> stringList = Arrays.asList(rightListStrings);

                    rightList.setAdapter(new MayAdapter(NavigationTest.this, android.R.layout.simple_list_item_1, stringList));
                    rightList.setVisibility(View.VISIBLE);

                }
            }

        });

        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {

            @Override
            public void onDrawerStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDrawerSlide(View arg0, float arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDrawerOpened(View arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDrawerClosed(View arg0) {
                // TODO Auto-generated method stub
                rightList.setVisibility(View.INVISIBLE);
            }
        });


    }


    class MayAdapter extends ArrayAdapter<String>{

        List<String> myList = null;
        public MayAdapter(Context context, int resource,List<String> objects) {
            super(context, resource,  objects);
            myList = objects;
        }
        public List<String> getMyList() {
            return myList;
        }
        public void setMyList(List<String> myList) {
            this.myList = myList;
        }
    }





}
