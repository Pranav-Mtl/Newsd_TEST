package com.android.appslure.newsd;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.android.CONSTANTS.Constant;


public class MapFragmentThree extends Fragment {

    LinearLayout llTelangana,llKarnataka,llKerala;
    ImageButton telangana,karnatka,kerla;
    Button btnDone;
    Intent intent;

    public MapFragmentThree() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_map_fragment_three, container, false);

        llTelangana= (LinearLayout) view.findViewById(R.id.eleven);
        llKarnataka= (LinearLayout) view.findViewById(R.id.twelve);
        llKerala= (LinearLayout) view.findViewById(R.id.thirteen);

        telangana=(ImageButton)view.findViewById(R.id.telangna);
        karnatka=(ImageButton)view.findViewById(R.id.karnatka);
        kerla=(ImageButton)view.findViewById(R.id.kerala);

        intent=getActivity().getIntent();




        btnDone= (Button) view.findViewById(R.id.map_btn_done);

        if(Constant.TELANGANA) {
            telangana.setBackgroundResource(R.drawable.telangnaselected);
        }
        if(Constant.KARNATAKA) {
            karnatka.setBackgroundResource(R.drawable.karnatkaselected);
        }
        if(Constant.KERALA) {
            kerla.setBackgroundResource(R.drawable.keralaselected);
        }

        llTelangana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constant.TELANGANA)
                {
                    telangana.setBackgroundResource(R.drawable.telangna);
                    Constant.TELANGANA=false;
                    Constant.statesCounter--;
                }
                else
                {
                    telangana.setBackgroundResource(R.drawable.telangnaselected);
                    Constant.TELANGANA=true;
                    Constant.statesCounter++;
                }

            }
        });

        llKarnataka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constant.KARNATAKA)
                {
                    karnatka.setBackgroundResource(R.drawable.karnatka);
                    Constant.KARNATAKA=false;
                    Constant.statesCounter--;
                }
                else
                {
                    karnatka.setBackgroundResource(R.drawable.karnatkaselected);
                    Constant.KARNATAKA=true;
                    Constant.statesCounter++;
                }

            }
        });

        llKerala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constant.KERALA)
                {
                    kerla.setBackgroundResource(R.drawable.kerala);
                    Constant.KERALA=false;
                    Constant.statesCounter--;
                }
                else
                {
                    kerla.setBackgroundResource(R.drawable.keralaselected);
                    Constant.KERALA=true;
                    Constant.statesCounter++;
                }

            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });



        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(this.isVisible()) {

            if (Constant.TELANGANA) {
                telangana.setBackgroundResource(R.drawable.telangnaselected);
            } else {
                telangana.setBackgroundResource(R.drawable.telangna);
            }
            if (Constant.KARNATAKA) {
                karnatka.setBackgroundResource(R.drawable.karnatkaselected);
            } else {
                karnatka.setBackgroundResource(R.drawable.karnatka);
            }
            if (Constant.KERALA) {
                kerla.setBackgroundResource(R.drawable.keralaselected);
            } else {
                kerla.setBackgroundResource(R.drawable.kerala);
            }
        }
    }
}
