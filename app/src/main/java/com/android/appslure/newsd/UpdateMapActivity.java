package com.android.appslure.newsd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.BE.MyAccountBE;
import com.android.CONSTANTS.Constant;


public class UpdateMapActivity extends AppCompatActivity {

    MyAccountBE objMyAccountBE;
    String states[];
    ImageView JK,HARYANA,ASSAM,MAHARASHTRA,ANDRAPRADESH,MP,WB,DELHI,UP,GUJARAT,BIHAR,KARNATAKA,KERALA;

    LinearLayout jk,haryana,assam,maharashtra,andraPradesh,madhyaPradesh,westBaengal,ncr,uttarPradesh,gujarat,bihar,karnataka,kerala;

    int counter=0;

    Button btnUpdate;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_map);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        btnUpdate= (Button) findViewById(R.id.btn_update_map);

        JK= (ImageView) findViewById(R.id.states_jk_checked);
        HARYANA= (ImageView) findViewById(R.id.states_haryana_checked);
        ASSAM= (ImageView) findViewById(R.id.states_assam_checked);
        MAHARASHTRA= (ImageView) findViewById(R.id.states_maharashtra_checked);
        ANDRAPRADESH= (ImageView) findViewById(R.id.states_andhra_checked);
        MP= (ImageView) findViewById(R.id.states_mp_checked);
        DELHI= (ImageView) findViewById(R.id.states_delhi_checked);
        UP= (ImageView) findViewById(R.id.states_up_checked);
        GUJARAT= (ImageView) findViewById(R.id.states_gujrat_checked);
        BIHAR= (ImageView) findViewById(R.id.states_bihar_checked);
        KARNATAKA= (ImageView) findViewById(R.id.states_karnataka_checked);
        KERALA= (ImageView) findViewById(R.id.states_kerala_checked);
        WB= (ImageView) findViewById(R.id.states_wb_checked);

        jk= (LinearLayout) findViewById(R.id.update_map_jk);
        haryana= (LinearLayout) findViewById(R.id.update_map_haryana);
        assam= (LinearLayout) findViewById(R.id.update_map_assam);
        maharashtra= (LinearLayout) findViewById(R.id.update_map_maharashtra);
        andraPradesh= (LinearLayout) findViewById(R.id.update_map_telengana);
        madhyaPradesh= (LinearLayout) findViewById(R.id.update_map_mp);
        ncr= (LinearLayout) findViewById(R.id.update_map_delhi);
        uttarPradesh= (LinearLayout) findViewById(R.id.update_map_up);
        gujarat= (LinearLayout) findViewById(R.id.update_map_gujrat);
        bihar= (LinearLayout) findViewById(R.id.update_map_bihar);
        karnataka= (LinearLayout) findViewById(R.id.update_map_karnataka);
        kerala= (LinearLayout) findViewById(R.id.update_map_kerala);
        westBaengal= (LinearLayout) findViewById(R.id.update_map_wb);

         intent=getIntent();
        objMyAccountBE= (MyAccountBE) intent.getSerializableExtra("MyAccountBE");
        states=objMyAccountBE.getStates().split(",");

        for(int i=0;i<states.length;i++)
        {
            if(states[i].equalsIgnoreCase(Constant.Jammu))
            {
                Constant.JAMMU_UPDATED=true;
                JK.setVisibility(View.VISIBLE);
            }
            else if(states[i].equalsIgnoreCase(Constant.Haryane))
            {
                Constant.HARYANA_UPDATED=true;
                HARYANA.setVisibility(View.VISIBLE);
            }
            else if(states[i].equalsIgnoreCase(Constant.Assam))
            {
                Constant.ASSAM_UPDATED=true;
                ASSAM.setVisibility(View.VISIBLE);
            }
            else if(states[i].equalsIgnoreCase(Constant.Maharashtra))
            {
                Constant.MAHARASHTRA_UPDATED=true;
                MAHARASHTRA.setVisibility(View.VISIBLE);
            }
            else if(states[i].equalsIgnoreCase(Constant.Telangana))
            {
                Constant.TELANGANA_UPDATED=true;
                ANDRAPRADESH.setVisibility(View.VISIBLE);
            }
            else if(states[i].equalsIgnoreCase(Constant.MP))
            {
                Constant.MADHYAPRADESH_UPDATED=true;
                MP.setVisibility(View.VISIBLE);
            }
            else if(states[i].equalsIgnoreCase(Constant.wb))
            {
                Constant.WB_UPDATED=true;
                WB.setVisibility(View.VISIBLE);
            }
            //////

            else if(states[i].equalsIgnoreCase(Constant.Delhi))
            {
                Constant.DELHI_UPDATED=true;
                DELHI.setVisibility(View.VISIBLE);
            }
            else if(states[i].equalsIgnoreCase(Constant.UP))
            {
                Constant.UTTARPRADESH_UPDATED=true;
                UP.setVisibility(View.VISIBLE);
            }
            else if(states[i].equalsIgnoreCase(Constant.Gujarat))
            {
                Constant.GUJARAT_UPDATED=true;
                GUJARAT.setVisibility(View.VISIBLE);
            }
            else if(states[i].equalsIgnoreCase(Constant.Bihar))
            {
                Constant.BIHAR_UPDATED=true;
                BIHAR.setVisibility(View.VISIBLE);
            }
            else if(states[i].equalsIgnoreCase(Constant.Karnataka))
            {
                Constant.KARNATAKA_UPDATED=true;
                KARNATAKA.setVisibility(View.VISIBLE);
            }
            else if(states[i].equalsIgnoreCase(Constant.Kerala))
            {
                Constant.KERALA_UPDATED=true;
                KERALA.setVisibility(View.VISIBLE);
            }


        }


        //JK,HARYANA,ASSAM,MAHARASHTRA,ANDRAPRADESH,MP,WB,DELHI,UP,GUJARAT,BIHAR,KARNATAKA,KERALA

        kerala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Constant.KERALA_UPDATED)
                {
                    Constant.KERALA_UPDATED=false;
                    KERALA.setVisibility(View.INVISIBLE);
                }
                else
                {
                    Constant.KERALA_UPDATED=true;
                    KERALA.setVisibility(View.VISIBLE);
                }
            }

        });
        karnataka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(Constant.KARNATAKA_UPDATED)
                {
                    Constant.KARNATAKA_UPDATED=false;
                    KARNATAKA.setVisibility(View.INVISIBLE);
                }
                else
                {
                    Constant.KARNATAKA_UPDATED=true;
                    KARNATAKA.setVisibility(View.VISIBLE);
                }

            }

        });
        bihar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Constant.BIHAR_UPDATED)
                {
                    Constant.BIHAR_UPDATED=false;
                    BIHAR.setVisibility(View.INVISIBLE);
                }
                else
                {
                    Constant.BIHAR_UPDATED=true;
                    BIHAR.setVisibility(View.VISIBLE);
                }
            }

        });
        gujarat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(Constant.GUJARAT_UPDATED)
                {
                    Constant.GUJARAT_UPDATED=false;
                    GUJARAT.setVisibility(View.INVISIBLE);
                }
                else
                {
                    Constant.GUJARAT_UPDATED=true;
                    GUJARAT.setVisibility(View.VISIBLE);
                }
            }

        });
        uttarPradesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Constant.UTTARPRADESH_UPDATED)
                {
                    Constant.UTTARPRADESH_UPDATED=false;
                    UP.setVisibility(View.INVISIBLE);
                }
                else
                {
                    Constant.UTTARPRADESH_UPDATED=true;
                    UP.setVisibility(View.VISIBLE);
                }
            }

        });
        ncr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Constant.DELHI_UPDATED)
                {
                    Constant.DELHI_UPDATED=false;
                    DELHI.setVisibility(View.INVISIBLE);
                }
                else
                {
                    Constant.DELHI_UPDATED=true;
                    DELHI.setVisibility(View.VISIBLE);
                }
            }

        });
        westBaengal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Constant.WB_UPDATED)
                {
                    Constant.WB_UPDATED=false;
                    WB.setVisibility(View.INVISIBLE);
                }
                else
                {
                    Constant.WB_UPDATED=true;
                    WB.setVisibility(View.VISIBLE);
                }
            }

        });
        madhyaPradesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Constant.MADHYAPRADESH_UPDATED)
                {
                    Constant.MADHYAPRADESH_UPDATED=false;
                    MP.setVisibility(View.INVISIBLE);
                }
                else
                {
                    Constant.MADHYAPRADESH_UPDATED=true;
                    MP.setVisibility(View.VISIBLE);
                }
            }

        });
        andraPradesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Constant.TELANGANA_UPDATED)
                {
                    Constant.TELANGANA_UPDATED=false;
                    ANDRAPRADESH.setVisibility(View.INVISIBLE);
                }
                else
                {
                    Constant.TELANGANA_UPDATED=true;
                    ANDRAPRADESH.setVisibility(View.VISIBLE);
                }
            }

        });
        maharashtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Constant.MAHARASHTRA_UPDATED)
                {
                    Constant.MAHARASHTRA_UPDATED=false;
                    MAHARASHTRA.setVisibility(View.INVISIBLE);
                }
                else
                {
                    Constant.MAHARASHTRA_UPDATED=true;
                    MAHARASHTRA.setVisibility(View.VISIBLE);
                }
            }


        });

        assam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Constant.ASSAM_UPDATED)
                {
                    Constant.ASSAM_UPDATED=false;
                    ASSAM.setVisibility(View.INVISIBLE);
                }
                else
                {
                    Constant.ASSAM_UPDATED=true;
                    ASSAM.setVisibility(View.VISIBLE);
                }
            }

        });


        jk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Constant.JAMMU_UPDATED)
                {
                    Constant.JAMMU_UPDATED=false;
                    JK.setVisibility(View.INVISIBLE);
                }
                else
                {
                    Constant.JAMMU_UPDATED=true;
                    JK.setVisibility(View.VISIBLE);
                }
            }

        });


        haryana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(Constant.HARYANA_UPDATED)
                {
                    Constant.HARYANA_UPDATED=false;
                    HARYANA.setVisibility(View.INVISIBLE);
                }
                else
                {
                    Constant.HARYANA_UPDATED=true;
                    HARYANA.setVisibility(View.VISIBLE);
                }
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.selectedStates="";
                int counter=0;
                if (Constant.JAMMU_UPDATED) {
                    Constant.selectedStates=Constant.Jammu;
                    counter++;
                }
                if(Constant.HARYANA_UPDATED)
                {
                    if(counter==0) {
                        Constant.selectedStates = Constant.Haryane;
                        counter++;
                    }
                    else
                        Constant.selectedStates = Constant.selectedStates +","+ Constant.Haryane;
                }
                if(Constant.DELHI_UPDATED)
                {
                    if(counter==0) {
                        Constant.selectedStates =Constant.Delhi;
                        counter++;
                    }
                    else
                        Constant.selectedStates = Constant.selectedStates +","+ Constant.Delhi;
                }
                ///////
                if(Constant.UTTARPRADESH_UPDATED)
                {
                    if(counter==0) {
                        Constant.selectedStates = Constant.UP;
                        counter++;
                    }
                    else
                        Constant.selectedStates = Constant.selectedStates +","+ Constant.UP;
                }
                if(Constant.GUJARAT_UPDATED)
                {
                    if(counter==0) {
                        Constant.selectedStates =Constant.Gujarat;
                        counter++;
                    }
                    else
                        Constant.selectedStates = Constant.selectedStates +","+ Constant.Gujarat;
                }

                ////
                if(Constant.MADHYAPRADESH_UPDATED)
                {
                    if(counter==0) {
                        Constant.selectedStates = Constant.MP;
                        counter++;
                    }
                    else
                        Constant.selectedStates = Constant.selectedStates +","+ Constant.MP;
                }
                if(Constant.BIHAR_UPDATED)
                {
                    if(counter==0) {
                        Constant.selectedStates =Constant.Bihar;
                        counter++;
                    }
                    else
                        Constant.selectedStates = Constant.selectedStates +","+ Constant.Bihar;
                }
                ///////////////////////////

                if(Constant.WB_UPDATED)
                {
                    if(counter==0) {
                        Constant.selectedStates = Constant.wb;
                        counter++;
                    }
                    else
                        Constant.selectedStates = Constant.selectedStates +","+ Constant.wb;
                }
                if(Constant.ASSAM_UPDATED)
                {
                    if(counter==0) {
                        Constant.selectedStates =Constant.Assam;
                        counter++;
                    }
                    else
                        Constant.selectedStates = Constant.selectedStates +","+ Constant.Assam;
                }

                ////////////////////////////

                if(Constant.MAHARASHTRA_UPDATED)
                {
                    if(counter==0) {
                        Constant.selectedStates = Constant.Maharashtra;
                        counter++;
                    }
                    else
                        Constant.selectedStates = Constant.selectedStates +","+ Constant.Maharashtra;
                }
                if(Constant.TELANGANA_UPDATED)
                {
                    if(counter==0) {
                        Constant.selectedStates =Constant.Telangana;
                        counter++;
                    }
                    else
                        Constant.selectedStates = Constant.selectedStates +","+ Constant.Telangana;
                }

                ////////////////////////////


                if(Constant.KARNATAKA_UPDATED)
                {
                    if(counter==0) {
                        Constant.selectedStates = Constant.Karnataka;
                        counter++;
                    }
                    else
                        Constant.selectedStates = Constant.selectedStates +","+ Constant.Karnataka;
                }
                if(Constant.KERALA_UPDATED)
                {
                    if(counter==0) {
                        Constant.selectedStates =Constant.Kerala;
                        counter++;
                    }
                    else
                        Constant.selectedStates = Constant.selectedStates +","+ Constant.Kerala;
                }

                System.out.println("SELECTED STATED" + Constant.selectedStates);

                intent.putExtra("STATES",Constant.selectedStates);
                setResult(RESULT_OK,intent);
                finish();
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
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
