package com.android.appslure.newsd;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.BL.PeopleAddBL;
import com.android.CONSTANTS.Constant;
import com.android.Configuration.Configuration;
import com.flurry.android.FlurryAgent;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PeopleSelection extends AppCompatActivity implements OnItemClickListener {

    int pop_height,pop_width;

    ListView tvPeople;
    AutoCompleteTextView autoCompleteTextView;

    ArrayList<String> adapterList = new ArrayList<String>();

    PeopleSelectionAdapter objPeopleSelectionAdapter;
    String listID[];
    String result;

    PeopleAddBL objPeopleAddBL;
    String deviceID;

    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_selection);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



        autoCompleteTextView= (AutoCompleteTextView) findViewById(R.id.people_text);

        objPeopleAddBL=new PeopleAddBL();

        tvPeople= (ListView) findViewById(R.id.people_result_tv);

        progressDialog=new ProgressDialog(PeopleSelection.this);

        deviceID= Configuration.getSharedPrefrenceValue(PeopleSelection.this,Constant.SHARED_PREFERENCE_ANDROID_ID);

        new FatchSelectedPeople().execute(deviceID,"");

        autoCompleteTextView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item= (String) parent.getItemAtPosition(position);
                String idNew=listID[position];
               // Toast.makeText(getApplicationContext(),item+"///"+idNew,Toast.LENGTH_LONG).show();
                Constant.counterPeople++;
                new FatchSelectedPeople().execute(deviceID,idNew);

            }
        });


        autoCompleteTextView.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>1 && s.length()<=8)
                {
                    // Toast.makeText(getApplicationContext(),"char:"+s,Toast.LENGTH_LONG).show();
                    new RunForGetPeople().execute(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

     /*   overridePendingTransition(R.animator.anim_in, R.animator.anim_out);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

       /// progressDialog=new ProgressDialog(Follow.this);

        if (width > 700) {

            pop_height = 1000;
            pop_width = 650;

        } else if (width > 500) {
            pop_height = 800;
            pop_width = 600;
        } else {
            pop_height = 600;
            pop_width = 400;
        }

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = pop_height;
        params.width = pop_width;
        this.getWindow().setAttributes(params);
        this.getWindow().setGravity(Gravity.CENTER);*/

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item= (String) parent.getItemAtPosition(position);
        Toast.makeText(getApplicationContext(),item,Toast.LENGTH_LONG).show();
    }

    private class PeopleSelectionAdapter extends BaseAdapter implements View.OnClickListener{
        PeopleAddBL objFollowBL;
        Context mContext;
        TextView title;
        ImageButton btnDelete;
        public PeopleSelectionAdapter(Context context,PeopleAddBL followBL,String deviceId,String newsID)
        {
            mContext=context;
            objFollowBL=followBL;
            result=objFollowBL.getTag(deviceId,newsID);
        }

        @Override
        public int getCount() {
            return Constant.PeopleTagId.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(mContext.LAYOUT_INFLATER_SERVICE);

            View gridView=null;
            //TextView tv,tv1;
            if (convertView != null){

                gridView=convertView;

            }else{
                gridView = new View(mContext);
                gridView= infalInflater.inflate(R.layout.add_people_raw, null);

            }

            title= (TextView) gridView.findViewById(R.id.followed_people_tag);
            btnDelete= (ImageButton) gridView.findViewById(R.id.followed_people_delete);

            title.setText(Constant.PeopleTagName[position]);

            btnDelete.setTag(position);

            btnDelete.setOnClickListener(this);

            gridView.setLayoutParams(new ListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT, 120));
            return gridView;

        }


        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.followed_people_delete:
                    int pos= (int) v.getTag();
                    new DeleteSelectedPeople().execute(Constant.PeopleTagId[pos]);


            }
        }
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

    private class RunForGetPeople extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

           /* progressDialog.show();
            progressDialog.setMessage("Loading");*/
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();

            String text = null;
            String url="search="+params[0];
            try
            {
                URI uri = new URI("http", "www.newsd.in", "/demo1/ws/search_people",url, null);
                System.out.println("URI"+uri);
                String value=uri.toASCIIString();

            HttpGet httpGet = new HttpGet(value);


                HttpResponse response = httpClient.execute(httpGet, localContext);


                HttpEntity entity = response.getEntity();


                text = getASCIIContentFromEntity(entity);


            } catch (Exception e) {
                return e.getLocalizedMessage();
            }


            return text;

        }

        @Override
        protected void onPostExecute(String result) {    //set adapter here

            adapterList.clear();
            validateLocation(result);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.gender_spinner_item, adapterList);
            autoCompleteTextView.setAdapter(adapter);
            autoCompleteTextView.showDropDown();
            //progressDialog.dismiss();
        }
    }
    protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
        InputStream in = entity.getContent();


        StringBuffer out = new StringBuffer();
        int n = 1;
        while (n>0) {
            byte[] b = new byte[8192];
            n =  in.read(b);
            if (n>0) out.append(new String(b, 0, n));
        }


        return out.toString();
    }

    public String validateLocation(String strValue) {
        System.out.println("Complete json" + strValue);
        Long status;
        String result = null;

        JSONParser jsonP = new JSONParser();

        try {

            Object obj = jsonP.parse(strValue);


            JSONArray jsonArrayObject = (JSONArray) obj;

            //System.out.println("SIZEEEEE"+jsonArrayObject.size());

            listID=new String[jsonArrayObject.size()];

            for (int i = 0; i < jsonArrayObject.size(); i++) {

                JSONObject jsonObject=(JSONObject)jsonP.parse(jsonArrayObject.get(i).toString());
                JSONObject jsonObjectByIndex =(JSONObject)jsonObject;
                adapterList.add(jsonObjectByIndex.get("suggestion").toString());
                listID[i]=jsonObjectByIndex.get("id").toString();

            }
        } catch (Exception e) {
        }
        return "";
    }


    private class FatchSelectedPeople extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {

            objPeopleSelectionAdapter=new PeopleSelectionAdapter(getApplicationContext(),objPeopleAddBL,params[0],params[1]);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if(result.equals("[]")) {

            }
            else
            {
                if(autoCompleteTextView.getText().toString().trim().length()==0)
                {

                }
                else {
                    Map<String, String> articleParams = new HashMap<String, String>();
//param keys and values have to be of String type
                    articleParams.put("People Name", autoCompleteTextView.getText().toString());
//up to 10 params can be logged with each event
                    FlurryAgent.logEvent("New People Added", articleParams);
                }
                autoCompleteTextView.setText("");
                tvPeople.setAdapter(objPeopleSelectionAdapter);

                hideKeyboard();

                //Toast.makeText(getApplicationContext(),"Person Added Successfully...",Toast.LENGTH_LONG).show();

            }
        }
    }

    private class DeleteSelectedPeople extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {

            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            String result=objPeopleAddBL.removeTag(params[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            progressDialog.dismiss();
            if(s.equals("1"))
            {
                Constant.counterPeople--;
                Toast.makeText(getApplicationContext(),"Removed Successfully...",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),PeopleSelection.class));
                finish();
            }
            else
            {
                startActivity(new Intent(getApplicationContext(),PeopleSelection.class));
                finish();
            }
        }
    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
