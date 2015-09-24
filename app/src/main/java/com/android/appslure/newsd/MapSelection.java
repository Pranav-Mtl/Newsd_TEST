package com.android.appslure.newsd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.CONSTANTS.Constant;


public class MapSelection extends AppCompatActivity {
    Button next,jammu,haryana,delhi,utterpradesh,gujrat,madhyapradesh,bihar;
    boolean flag_jammu=true,flag_haryana=true,flag_delhi=true,flag_utterakhand=true,flag_gujrat=true,flag_madhyapradesh=true,flag_bihar=true;
    boolean flag_utterpradesh=true;
    String selectedState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_selection);
        selectedState="Delhi,NCR";
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.test_logo);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

      //  gettingStateNews=new GettingStateNews();

        jammu=(Button)findViewById(R.id.jammu);
        haryana=(Button)findViewById(R.id.haryana);
        delhi=(Button)findViewById(R.id.delhi);
        utterpradesh=(Button)findViewById(R.id.utterakhand);
        gujrat=(Button)findViewById(R.id.gurjat);
        madhyapradesh=(Button)findViewById(R.id.mp);
        bihar =(Button)findViewById(R.id.bihar);
        next=(Button)findViewById(R.id.nextbutton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag_jammu==false)
                {
                    Constant.selectedStates=Constant.selectedStates+","+Constant.Jammu;
                }
                if(flag_haryana==false)
                {
                    Constant.selectedStates=Constant.selectedStates+","+Constant.Haryane;
                }
                if(flag_delhi==false)
                {
                    Constant.selectedStates=Constant.selectedStates+","+Constant.Delhi;
                }
                if(flag_gujrat==false)
                {
                    Constant.selectedStates=Constant.selectedStates+","+Constant.Gujarat;
                }
                if(flag_utterpradesh==false)
                {
                    Constant.selectedStates=Constant.selectedStates+","+Constant.UP;
                }
                if(flag_bihar==false)
                {
                    Constant.selectedStates=Constant.selectedStates+","+Constant.Bihar;
                }
                if(flag_madhyapradesh==false)
                {
                    Constant.selectedStates=Constant.selectedStates+","+Constant.MP;
                }
                System.out.println("FIRST MAP SELECTION" + Constant.selectedStates);
                Intent intent=new Intent(getApplicationContext(),NextMapSelection.class);
                startActivityForResult(intent, 1);
               // gettingStateNews.getNews(selectedState);

            }
        });
        jammu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_jammu)
                {
                    jammu.setBackgroundResource(R.drawable.jammuselected);
                    flag_jammu=false;
                }
                else{
                    jammu.setBackgroundResource(R.drawable.jammu);
                    flag_jammu=true;
                }
            }
        });

        haryana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_haryana) {
                    haryana.setBackgroundResource(R.drawable.haryanarahasthan);
                    flag_haryana = false;
                } else {
                    haryana.setBackgroundResource(R.drawable.haryanarahasthanselected);
                    flag_haryana = true;
                }
            }
        });

        delhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_delhi) {
                    delhi.setBackgroundResource(R.drawable.delhincrselected);
                    flag_delhi = false;
                } else {
                    delhi.setBackgroundResource(R.drawable.delhincr);
                    flag_delhi = true;
                }
            }
        });
        utterpradesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_utterpradesh) {
                    utterpradesh.setBackgroundResource(R.drawable.utterpradeshselected);
                    flag_utterpradesh = false;
                } else {
                    utterpradesh.setBackgroundResource(R.drawable.utterpradesh);
                    flag_utterpradesh = true;
                }
            }
        });
        gujrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_gujrat) {
                    gujrat.setBackgroundResource(R.drawable.gujratselected);
                    flag_gujrat = false;
                } else {
                    gujrat.setBackgroundResource(R.drawable.gujrat);
                    flag_gujrat = true;
                }
            }
        });
        madhyapradesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_madhyapradesh) {
                    madhyapradesh.setBackgroundResource(R.drawable.mp2selected);
                    flag_madhyapradesh = false;
                } else {
                    madhyapradesh.setBackgroundResource(R.drawable.mp2);
                    flag_madhyapradesh = true;
                }
            }
        });
        bihar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_bihar) {
                    bihar.setBackgroundResource(R.drawable.biharselected);
                    flag_bihar = false;
                } else {
                    bihar.setBackgroundResource(R.drawable.bihar);
                    flag_bihar = true;
                }
            }
        });
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
        if (id == android.R.id.home) {

            //Toast.makeText(getApplicationContext(),"BAck Clicked",Toast.LENGTH_SHORT).show();
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            this.finish();
    }
}
