package com.tintuc.gilplus.tinnhanh;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Content extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private String TAG = Content.class.getSimpleName();

    private String itemid;
    private String urlviever;
    private String soucre;
    private ProgressDialog pDialog;
    private int fontsize;
    private int[] fontzone ={100,110,120,130,140};
    private WebSettings webSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        SharedPreferences myPrefs = getSharedPreferences("fontsize",
                Activity.MODE_PRIVATE);

        fontsize = myPrefs.getInt("fontpos", 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        Intent intent = this.getIntent();

        itemid= intent.getStringExtra("itemid");
        soucre= intent.getStringExtra("source");
        urlviever= intent.getStringExtra("url");
        Log.d(TAG,soucre);
//        Toast.makeText(getApplication(),itemid,Toast.LENGTH_SHORT).show();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        fab.setVisibility(View.INVISIBLE);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_content, menu);
//        Log.d(TAG,"on menu"+itemid);
        new GetContent(0,itemid).execute();
        return true;
    }

    private class GetContent extends AsyncTask<Void, Void, Void> {
        ArrayList<ItemList> tweets = new ArrayList<ItemList>();
        int position;
        String linkrss="";
        String itemid;
        String getcontent="";
        String morestart="";
        String moreend="";
        public GetContent(int pos,String id){
            position = pos;
            this.itemid=id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Content.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg) {


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog

            if (pDialog.isShowing())
                pDialog.dismiss();
//            Log.d(TAG,getcontent);
            Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + position);
            WebView webview=(WebView)getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + position).getView().findViewById(R.id.webcontent);

            webview.setWebViewClient(new WebViewClient());

            webview.loadUrl(urlviever);

//            webview.loadData(sb+"", "text/html; charset=utf-8","UTF-8");
            webSettings = webview.getSettings();

//            if(fontzie==0)
            webSettings.setTextZoom(fontzone[fontsize]);

//            webSettings.setTextSize(WebSettings.TextSize.NORMAL);
//            Log.d(TAG,"here "+lstView+mViewPager.getCurrentItem());


            /**
             * Updating parsed JSON data into ListView
             * */

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
            showAlertDialogLogOut();
            return true;
        }

        else if(id ==android.R.id.home){
            finish();
            Toast.makeText(getApplication(),"ABC",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    private void showAlertDialogLogOut() {


        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        CharSequence items[] = new CharSequence[] {"Nhỏ", "Trung bình", "Vừa","Lớn","Rất lớn"};

        int choose;
        adb.setSingleChoiceItems(items, fontsize, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface d, int n) {

                // ...
            }

        });
        adb.setPositiveButton("Xác Nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                ListView lv = ((AlertDialog)dialog).getListView();
                Object checkedItem = lv.getAdapter().getItem(lv.getCheckedItemPosition());
// User clicked OK, so save the result somewhere
// or return them to the component that opened the dialog
                SharedPreferences myPrefs = getSharedPreferences("fontsize",
                        Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefs.edit();
                editor.putInt("fontpos", lv.getCheckedItemPosition());
                editor.commit();
                fontsize=lv.getCheckedItemPosition();
                webSettings.setTextZoom(fontzone[lv.getCheckedItemPosition()]);
                Log.d(TAG,lv.getCheckedItemPosition()+"");
            }
        });
        adb.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        adb.setTitle("Chọn Kích thước chữ?");
        adb.show();

    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_content, container, false);
////            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
////            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
//
//            WebView webview=(WebView) rootView.findViewById(R.id.webcontent);
//            webview.setWebViewClient(new WebViewClient());
//
//            String title = "<html><body>Day la tieu de cua bai viet  <b>192</br> </body></html>";
//            String info= "<html><body>Thoi gian :12/23/2017 /zing.vn/Xem nguon  <b>192</b> </br></body></html>";
//            String header= "<html><body>Day la noi dung header <b>192</b> </br></body></html>";
//            String content="<html><body>Day laf noi dung bai viet<b>192</b></br> </body></html>";
//            String related="";
//            String comment="";
////            webview.loadUrl("http://www.google.com");
//
//            webview.loadData(title+info+header+content, "text/html", null);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
