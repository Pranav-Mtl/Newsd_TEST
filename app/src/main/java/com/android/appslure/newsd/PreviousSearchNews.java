package com.android.appslure.newsd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.BL.PreviousSearchBL;
import com.android.CONSTANTS.Constant;
import com.flurry.android.FlurryAgent;

import java.util.HashMap;
import java.util.Map;


public class PreviousSearchNews extends ActionBarActivity {
    EditText newsText;
    Button serach;
    PreviousSearchBL previousSearchBL;
    ProgressDialog pd;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.previous_search_news);
        newsText=(EditText)findViewById(R.id.previousNews);
        serach=(Button)findViewById(R.id.previous_search);
        pd=new ProgressDialog(PreviousSearchNews.this);
        //newsUrl="previous=1"+news;
        previousSearchBL=new PreviousSearchBL();
        listView=(ListView)findViewById(R.id.prevsearchImg);

        serach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String newsUrl=newsText.getText().toString();
              PreviousSearchAdapter adapter=new PreviousSearchAdapter(getApplicationContext(),newsUrl);

                Map<String, String> articleParams = new HashMap<String, String>();
//param keys and values have to be of String type
                articleParams.put("Title", newsUrl);

//up to 10 params can be logged with each event
                FlurryAgent.logEvent("Previous_Event_Searched", articleParams);

                 listView.setAdapter(adapter);


            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), PreviousNews.class);
                //System.out.println("at time of puttin value--------->" + Constant.prenewsId[position]);
                intent.putExtra("position", Constant.prenewsId[position]);

                startActivity(intent);
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

        return super.onOptionsItemSelected(item);
    }
}
