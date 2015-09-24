package com.android.appslure.newsd;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.BL.DrawerCategoryBL;
import com.android.BL.PeopleAddBL;
import com.android.CONSTANTS.Constant;
import com.android.Configuration.Configuration;
import com.android.DB.DBOperation;

public class PeopleList extends AppCompatActivity {

    PeopleSelectionAdapter objPeopleSelectionAdapter;
    String listID[];
    String result;

    PeopleAddBL objPeopleAddBL;
    String deviceID;

    ProgressDialog progressDialog;
    DrawerCategoryBL objDrawerCategoryBL;
    DBOperation dbOperation;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_people_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        lv= (ListView) findViewById(R.id.people_result_tv);

        objPeopleAddBL=new PeopleAddBL();
        objDrawerCategoryBL=new DrawerCategoryBL();
        dbOperation = new DBOperation(this);



        progressDialog=new ProgressDialog(PeopleList.this);

        deviceID= Configuration.getSharedPrefrenceValue(PeopleList.this, Constant.SHARED_PREFERENCE_ANDROID_ID);

        new FatchSelectedPeople().execute(deviceID,"");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {

            //Toast.makeText(getApplicationContext(),"BAck Clicked",Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private class PeopleSelectionAdapter extends BaseAdapter {
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
                gridView= infalInflater.inflate(R.layout.people_list_raw, null);

            }

            title= (TextView) gridView.findViewById(R.id.followed_people_tag);


            title.setText(Constant.PeopleTagName[position]);


            gridView.setLayoutParams(new ListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT, 120));
            return gridView;

        }


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

                lv.setAdapter(objPeopleSelectionAdapter);



                //Toast.makeText(getApplicationContext(),"Person Added Successfully...",Toast.LENGTH_LONG).show();

            }

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String people = Constant.PeopleTagName[position];
                    new GetPeopleCategory().execute(Constant.People, people);
                }
            });
        }
    }

    private class GetPeopleCategory extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("Getting News");
            // Set progressdialog message
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(false);
            // Show progressdialog
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            String result=objDrawerCategoryBL.getPeopleCategoryNews(dbOperation,params[0],params[1]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            progressDialog.dismiss();
            Intent intent = new Intent(PeopleList.this, CategoryFlipper.class);
            intent.putExtra("Category", Constant.People);
            startActivity(intent);
            finish();
        }
    }

}
