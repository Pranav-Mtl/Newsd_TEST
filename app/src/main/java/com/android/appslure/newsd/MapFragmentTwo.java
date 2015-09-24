package com.android.appslure.newsd;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.android.CONSTANTS.Constant;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragmentTwo extends Fragment {

    LinearLayout llMp,llBihar,llWb,llAssam,llMaharashtra;
    ImageButton madhyapradesh,bihar,westBangal,assam,maharatra;


    public MapFragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_map_fragment_two, container, false);

        madhyapradesh=(ImageButton)view.findViewById(R.id.mp);
        bihar =(ImageButton) view.findViewById(R.id.bihar);
        westBangal=(ImageButton)view.findViewById(R.id.bangal);
        assam=(ImageButton)view.findViewById(R.id.assam);
        maharatra=(ImageButton)view.findViewById(R.id.maharastra);

        llMp= (LinearLayout) view.findViewById(R.id.six);
        llBihar= (LinearLayout) view.findViewById(R.id.seven);
        llWb= (LinearLayout) view.findViewById(R.id.eight);
        llAssam= (LinearLayout) view.findViewById(R.id.nine);
        llMaharashtra= (LinearLayout) view.findViewById(R.id.ten);

        if(Constant.MADHYAPRADESH) {
            madhyapradesh.setBackgroundResource(R.drawable.maharastraselected);
        }
        if(Constant.BIHAR) {
            bihar.setBackgroundResource(R.drawable.biharselected);
        }
        if(Constant.WB) {
            westBangal.setBackgroundResource(R.drawable.westbangalselected);
        }
        if(Constant.ASSAM) {
            assam.setBackgroundResource(R.drawable.assamselected);
        }
        if(Constant.MAHARASHTRA) {
            maharatra.setBackgroundResource(R.drawable.maharastraselected);
        }

        llMp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constant.MADHYAPRADESH)
                {
                    madhyapradesh.setBackgroundResource(R.drawable.mp2);
                    Constant.MADHYAPRADESH=false;
                    Constant.statesCounter--;
                }
                else
                {
                    madhyapradesh.setBackgroundResource(R.drawable.mp2selected);
                    Constant.MADHYAPRADESH=true;
                    Constant.statesCounter++;
                }

            }
        });

        llBihar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constant.BIHAR)
                {
                    bihar.setBackgroundResource(R.drawable.bihar);
                    Constant.BIHAR=false;
                    Constant.statesCounter--;
                }
                else
                {
                    bihar.setBackgroundResource(R.drawable.biharselected);
                    Constant.BIHAR=true;
                    Constant.statesCounter++;
                }

            }
        });

        llWb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constant.WB)
                {
                    westBangal.setBackgroundResource(R.drawable.westbangal);
                    Constant.WB=false;
                    Constant.statesCounter--;
                }
                else
                {
                    westBangal.setBackgroundResource(R.drawable.westbangalselected);
                    Constant.WB=true;
                    Constant.statesCounter++;
                }

            }
        });

        llAssam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constant.ASSAM)
                {
                    assam.setBackgroundResource(R.drawable.assam);
                    Constant.ASSAM=false;
                    Constant.statesCounter--;
                }
                else
                {
                    assam.setBackgroundResource(R.drawable.assamselected);
                    Constant.ASSAM=true;
                    Constant.statesCounter++;
                }

            }
        });

        llMaharashtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.MAHARASHTRA) {
                    maharatra.setBackgroundResource(R.drawable.maharastra);
                    Constant.MAHARASHTRA = false;
                    Constant.statesCounter--;
                } else {
                    maharatra.setBackgroundResource(R.drawable.maharastraselected);
                    Constant.MAHARASHTRA = true;
                    Constant.statesCounter++;
                }

            }
        });

        return view;
    }

    @Override
    public void onResume() {

        super.onResume();

        System.out.println("Two RESUME Method");
       /* if(Constant.MADHYAPRADESH) {
            madhyapradesh.setBackgroundResource(R.drawable.maharastraselected);
        }
        if(Constant.BIHAR) {
            bihar.setBackgroundResource(R.drawable.biharselected);
        }
        if(Constant.WB) {
            westBangal.setBackgroundResource(R.drawable.westbangalselected);
        }
        if(Constant.ASSAM) {
            assam.setBackgroundResource(R.drawable.assamselected);
        }
        if(Constant.MAHARASHTRA) {
            maharatra.setBackgroundResource(R.drawable.maharastraselected);
        }*/
    }

    @Override
    public void onStart() {
        super.onStart();

        System.out.println("Two Start Method");
    }

    @Override
    public void onPause() {
        super.onPause();

        System.out.println("Two Pause Method");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(this.isVisible())
        {
            System.out.println("TWO IS VISIBLE");
            if(Constant.MADHYAPRADESH) {
                madhyapradesh.setBackgroundResource(R.drawable.maharastraselected);
            }
            else {
                madhyapradesh.setBackgroundResource(R.drawable.maharastra);
            }
            if(Constant.BIHAR) {
                bihar.setBackgroundResource(R.drawable.biharselected);
            }
            else
            {
                bihar.setBackgroundResource(R.drawable.bihar);
            }
            if(Constant.WB) {
                westBangal.setBackgroundResource(R.drawable.westbangalselected);
            }
            else
            {
                westBangal.setBackgroundResource(R.drawable.westbangal);
            }
            if(Constant.ASSAM) {
                assam.setBackgroundResource(R.drawable.assamselected);
            }
            else
            {
                assam.setBackgroundResource(R.drawable.assam);
            }
            if(Constant.MAHARASHTRA) {
                maharatra.setBackgroundResource(R.drawable.maharastraselected);
            }
            else
            {
                maharatra.setBackgroundResource(R.drawable.maharastra);
            }
        }
    }
}
