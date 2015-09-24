package com.android.appslure.newsd;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.android.CONSTANTS.Constant;


public class MapFragmentOne extends Fragment {

    LinearLayout llJammu,llHaryana,llDelhi,llUp,llGujarat;

    ImageButton jammu,haryana,delhi,utterpradesh,gujrat;



    public MapFragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_map_fragment_one, container, false);

        llJammu= (LinearLayout) view.findViewById(R.id.first);
        llHaryana= (LinearLayout) view.findViewById(R.id.second);
        llDelhi= (LinearLayout) view.findViewById(R.id.third);
        llUp= (LinearLayout) view.findViewById(R.id.four);
        llGujarat= (LinearLayout) view.findViewById(R.id.five);

        jammu=(ImageButton)view.findViewById(R.id.jammu);
        haryana=(ImageButton)view.findViewById(R.id.haryana);
        delhi=(ImageButton)view.findViewById(R.id.delhi);
        utterpradesh=(ImageButton)view.findViewById(R.id.utterakhand);
        gujrat=(ImageButton)view.findViewById(R.id.gurjat);

        if(Constant.JAMMU) {
            jammu.setBackgroundResource(R.drawable.jammuselected);
        }
        if(Constant.HARYANA) {
            haryana.setBackgroundResource(R.drawable.haryanarahasthanselected);
        }
        if(Constant.DELHI) {
            delhi.setBackgroundResource(R.drawable.delhincrselected);
        }
        if(Constant.UTTARPRADESH) {
            utterpradesh.setBackgroundResource(R.drawable.utterpradeshselected);
        }
        if(Constant.GUJARAT) {
            gujrat.setBackgroundResource(R.drawable.gujratselected);
        }


        // JAMMU and Kashmir state Listener

        llJammu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constant.JAMMU)
                {
                    jammu.setBackgroundResource(R.drawable.jammu);
                    Constant.JAMMU=false;
                    Constant.statesCounter--;
                }
                else
                {
                    jammu.setBackgroundResource(R.drawable.jammuselected);
                    Constant.JAMMU=true;
                    Constant.statesCounter++;
                }

            }
        });

        // Haryana
        llHaryana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constant.HARYANA)
                {
                    haryana.setBackgroundResource(R.drawable.haryanarahasthan);
                    Constant.HARYANA=false;
                    Constant.statesCounter--;
                }
                else
                {
                    haryana.setBackgroundResource(R.drawable.haryanarahasthanselected);
                    Constant.HARYANA=true;
                    Constant.statesCounter++;
                }

            }
        });

        //Delhi
        llDelhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constant.DELHI)
                {
                    delhi.setBackgroundResource(R.drawable.delhincr);
                    Constant.DELHI=false;
                    Constant.statesCounter--;
                }
                else
                {
                    delhi.setBackgroundResource(R.drawable.delhincrselected);
                    Constant.DELHI=true;
                    Constant.statesCounter++;
                }

            }
        });

        llUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constant.UTTARPRADESH)
                {
                    utterpradesh.setBackgroundResource(R.drawable.utterpradesh);
                    Constant.UTTARPRADESH=false;
                    Constant.statesCounter--;
                }
                else
                {
                    utterpradesh.setBackgroundResource(R.drawable.utterpradeshselected);
                    Constant.UTTARPRADESH=true;
                    Constant.statesCounter++;
                }

            }
        });

        llGujarat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constant.GUJARAT)
                {
                    gujrat.setBackgroundResource(R.drawable.gujrat);
                    Constant.GUJARAT=false;
                    Constant.statesCounter--;
                }
                else
                {
                    gujrat.setBackgroundResource(R.drawable.gujratselected);
                    Constant.GUJARAT=true;
                    Constant.statesCounter++;
                }

            }
        });





        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        System.out.println("One Resume Method");

      /*  if(Constant.JAMMU) {
            jammu.setBackgroundResource(R.drawable.jammuselected);
        }
        if(Constant.HARYANA) {
            haryana.setBackgroundResource(R.drawable.haryanarahasthanselected);
        }
        if(Constant.DELHI) {
            delhi.setBackgroundResource(R.drawable.delhincrselected);
        }
        if(Constant.UTTARPRADESH) {
            utterpradesh.setBackgroundResource(R.drawable.utterpradeshselected);
        }
        if(Constant.GUJARAT) {
            gujrat.setBackgroundResource(R.drawable.gujratselected);
        }
*/
    }

    @Override
    public void onStart() {
        super.onStart();

        System.out.println("One Start Method");
    }

    @Override
    public void onPause() {
        super.onPause();

        System.out.println("One Pause Method");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(this.isVisible()) {

            if (Constant.JAMMU) {
                jammu.setBackgroundResource(R.drawable.jammuselected);
            } else {
                jammu.setBackgroundResource(R.drawable.jammu);
            }

            if (Constant.HARYANA) {
                haryana.setBackgroundResource(R.drawable.haryanarahasthanselected);
            } else
                haryana.setBackgroundResource(R.drawable.haryanarahasthan);

            if (Constant.DELHI) {
                delhi.setBackgroundResource(R.drawable.delhincrselected);
            } else
                delhi.setBackgroundResource(R.drawable.delhincr);

            if (Constant.UTTARPRADESH) {
                utterpradesh.setBackgroundResource(R.drawable.utterpradeshselected);
            } else
                utterpradesh.setBackgroundResource(R.drawable.utterpradesh);

            if (Constant.GUJARAT) {
                gujrat.setBackgroundResource(R.drawable.gujratselected);
            } else
                gujrat.setBackgroundResource(R.drawable.gujrat);
        }

    }
}
