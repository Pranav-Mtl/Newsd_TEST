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


public class NextMapSelection extends AppCompatActivity {
    Button westBangal,assam,maharatra,telangana,karnatka,kerla,btnDone;
    Intent intent;
    boolean flag_westbangal=true,flag_assam=true,flag_maharastra=true,flag_telangan=true,flag_karnatka=true,flag_keerla=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_map_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.test_logo);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        westBangal=(Button)findViewById(R.id.bangal);
        assam=(Button)findViewById(R.id.assam);
        maharatra=(Button)findViewById(R.id.maharastra);
        telangana=(Button)findViewById(R.id.telangna);
        karnatka=(Button)findViewById(R.id.karnatka);
        kerla=(Button)findViewById(R.id.kerala);
        btnDone= (Button) findViewById(R.id.nextMapDone);
         intent=getIntent();

        westBangal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_westbangal)
                {
                    westBangal.setBackgroundResource(R.drawable.westbangalselected);
                    flag_westbangal=false;
                }
                else
                {
                    westBangal.setBackgroundResource(R.drawable.westbangal);
                    flag_westbangal=true;
                }
            }
        });
        assam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_assam)
                {
                    assam.setBackgroundResource(R.drawable.assamselected);
                    flag_assam=false;
                }
                else
                {
                    assam.setBackgroundResource(R.drawable.assam);
                    flag_assam=true;
                }
            }
        });
        maharatra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_maharastra)
                {
                    maharatra.setBackgroundResource(R.drawable.maharastraselected);
                    flag_maharastra=false;
                }
                else
                {
                    maharatra.setBackgroundResource(R.drawable.maharastra);
                    flag_maharastra=true;
                }
            }
        });
        telangana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_telangan)
                {
                    telangana.setBackgroundResource(R.drawable.telangnaselected);
                    flag_telangan=false;
                }
                else
                {
                    telangana.setBackgroundResource(R.drawable.telangna);
                    flag_telangan=true;
                }
            }
        });
        karnatka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_karnatka)
                {
                    karnatka.setBackgroundResource(R.drawable.karnatkaselected);
                    flag_karnatka=false;
                }
                else
                {
                    karnatka.setBackgroundResource(R.drawable.karnatka);
                    flag_karnatka=true;
                }
            }
        });
        kerla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_keerla)
                {
                    kerla.setBackgroundResource(R.drawable.keralaselected);
                    flag_keerla=false;
                }
                else
                {
                    kerla.setBackgroundResource(R.drawable.kerala);
                    flag_keerla=true;
                }
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag_assam==false)
                {
                    Constant.selectedStates=Constant.selectedStates+","+Constant.Assam;
                }
                if(flag_karnatka==false)
                {
                    Constant.selectedStates=Constant.selectedStates+","+Constant.Karnataka;
                }
                if(flag_keerla==false)
                {
                    Constant.selectedStates=Constant.selectedStates+","+Constant.Kerala;
                }
                if(flag_maharastra==false)
                {
                    Constant.selectedStates=Constant.selectedStates+","+Constant.Maharashtra;
                }
                if(flag_telangan==false)
                {
                    Constant.selectedStates=Constant.selectedStates+","+Constant.Telangana;
                }
                if(flag_westbangal==false)
                {
                    Constant.selectedStates=Constant.selectedStates+","+Constant.wb;
                }
                System.out.println("SECOND MAP SELECTION" + Constant.selectedStates);
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
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
