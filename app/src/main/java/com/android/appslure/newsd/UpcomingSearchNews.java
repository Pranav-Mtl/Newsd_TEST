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

import com.android.BL.UpcomingSearchBL;
import com.android.CONSTANTS.Constant;
import com.flurry.android.FlurryAgent;

import java.util.HashMap;
import java.util.Map;

public class UpcomingSearchNews extends ActionBarActivity {

   UpcomingSearchBL upcomingSearchBL;

    EditText editText;
    String textValue;
    Button searcbtn;
    ProgressDialog pd;
    UpcomingSearchAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upcoming_search_news);

        pd=new ProgressDialog(UpcomingSearchNews.this);
        searcbtn=(Button)findViewById(R.id.up_search);
        editText=(EditText)findViewById(R.id.upcomingSearch);

         listView = (ListView) findViewById(R.id.searchImg);

               searcbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textValue = editText.getText().toString();

                Map<String, String> articleParams = new HashMap<String, String>();
//param keys and values have to be of String type
                articleParams.put("Title", textValue);

//up to 10 params can be logged with each event
                FlurryAgent.logEvent("Upcoming_Event_Searched", articleParams);
                adapter =new UpcomingSearchAdapter(getApplicationContext(), textValue);

                listView.setAdapter(adapter);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), UpcomingNews.class);
                //System.out.println("at time of puttin value--------->"+Constant.upnewsId[pos]);
                intent.putExtra("position", Constant.upnewsId[position]);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
