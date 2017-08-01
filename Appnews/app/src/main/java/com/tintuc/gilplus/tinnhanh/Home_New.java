package com.tintuc.gilplus.tinnhanh;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class Home_New extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
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
    ArrayList<HashMap<String, String>> contactList;
    public  int aa=0;
    private ViewPager mViewPager;
//    private static String url = "http://api.androidhive.info/contacts/";

    private static String url ="http://10.0.2.2:8000/mobile";

    public static boolean AddmoreitemtaskFinished = true;
    private FloatingActionButton fab;
    private boolean readytab=false;
    private int timepickfiller=0;

    private long[] timefiller ={0,1800,3600,7200,14400,28800,43200,86400};
    private List<Integer> listFragment = new ArrayList<Integer>();
//    private int[] listFragment={};
    private int currentfortab;
    private String getfitler ="";

    private boolean bgetfitler=false;
    private String sessionfitler ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__new);
        checkfirst();


//
//
//        Toast.makeText(getApplicationContext(),selected[0] + " YOU time",Toast.LENGTH_SHORT).show();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_logo);
        SharedPreferences myPrefs = getSharedPreferences("youtime",
                Activity.MODE_PRIVATE);
        timepickfiller = myPrefs.getInt("numberPicker",0);

//        Toast.makeText(getApplicationContext(),timepickfiller + " YOU time",Toast.LENGTH_SHORT).show();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + mViewPager.getCurrentItem());
//                new GetContacts(0).execute();
                currentfortab=mViewPager.getCurrentItem();
                final AlertDialog.Builder d = new AlertDialog.Builder(Home_New.this);
                LayoutInflater inflater = (LayoutInflater)
                getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//                view.getLayoutI

                String[] mins5 = { "Mới nhất","30 phút trước", "1 giờ trước", "2 giờ trước", "4 giờ trước", "8 giờ trước", "12 giờ trước", "1 ngày trước"};
                View dialogView = inflater.inflate(R.layout.number_picker_dialog, null);
                d.setTitle("Lọc tin theo thời gian");
                d.setMessage("Chọn mốc hiển thị");
                d.setView(dialogView);
                final NumberPicker numberPicker = (NumberPicker) dialogView.findViewById(R.id.dialog_number_picker);
//                numberPicker.setMaxValue(50);
//                numberPicker.setMinValue(1);
//                numberPicker.setMinValue(0);

                SharedPreferences myPrefs = getSharedPreferences("youtime",
                        Activity.MODE_PRIVATE);

                int pick = myPrefs.getInt("numberPicker", 0);

                Log.d(TAG,pick+"");
                numberPicker.setMaxValue(mins5.length-1);
                numberPicker.setDisplayedValues(mins5);
//                numberPicker.setMaxValue(5);

                numberPicker.setValue(pick);
                numberPicker.setWrapSelectorWheel(false);
                numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        Log.d(TAG, "onValueChange: ");
                    }
                });
                d.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "onClick: " + numberPicker.getValue());

                            timepickfiller=numberPicker.getValue();
                            SharedPreferences myPrefs = getSharedPreferences("youtime",
                                    Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = myPrefs.edit();
                            editor.putInt("numberPicker", numberPicker.getValue());
                            editor.commit();
                            listFragment.clear();

                            if(!listFragment.equals(currentfortab)) {
                                Log.d(TAG, "when listFragment: " + listFragment);
                                new GetContacts(currentfortab).execute();
                                listFragment.add(currentfortab);
                                Log.d(TAG, "after listFragment: " + listFragment);


                             }

                    }
                });
                d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog alertDialog = d.create();
                alertDialog.show();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        int limit = (mSectionsPagerAdapter.getCount() > 1 ? mSectionsPagerAdapter.getCount() - 1 : 1);
        mViewPager.setOffscreenPageLimit(limit);
//        mViewPager.setOffscreenPageLimit(-1);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //do stuff here



                Log.d(TAG,"Thoi gian "+listFragment+" check "+listFragment.contains(tab.getPosition()));
                Log.d(TAG,"CHECK "+(readytab==true  && listFragment.equals(tab.getPosition())==false));
                    if(readytab==true  && listFragment.contains(tab.getPosition())==false)
                    {
                        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + tab.getPosition());
                        ListView lstView=(ListView)getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + tab.getPosition()).getView().findViewById(R.id.lstView);
                        new GetContacts(tab.getPosition()).execute();
                        listFragment.add(tab.getPosition());
//                        if(!listFragment.equals(currentfortab)) {
//
//
//
//
//                        }


                    }



//                finish();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                Log.d(TAG,"ABC");

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//                Log.d(TAG,"ABC");

            }
        });

//        new GetContacts().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkfirst();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sessionfitler=getfitler;
    }

    private  void checkfirst(){
        Log.d(TAG,"Again");
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean a=sharedPrefs.getBoolean("firstactive",false);

        if( a==true)
        {
            Set<String> selections = sharedPrefs.getStringSet("sourceseleced", null);
            if (selections != null)
            {
                String[] selected = selections.toArray(new String[] {});
                if (selected.length==3)
                {
                    bgetfitler=false;

                }
                else
                {
                    bgetfitler=true;
                    getfitler="";
                    for (String s : selected) {
                        getfitler=getfitler+s;
                        if (selected[selected.length-1]!=s)
                        {
                            getfitler=getfitler+",";
                        }

                    }

                    if(sessionfitler != "" && sessionfitler != getfitler)
                    {
                        currentfortab=mViewPager.getCurrentItem();
                        listFragment.clear();

                        if(!listFragment.equals(currentfortab)) {
                            Log.d(TAG, "when listFragment: " + listFragment);
                            new GetContacts(currentfortab).execute();
                            listFragment.add(currentfortab);
                            Log.d(TAG, "after listFragment: " + listFragment);


                        }
                    }



//
                }

            }

        }

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home__new, menu);
//        getMenuInflater().inflate(R.menu.menu_reload, menu);
        new GetContacts(0).execute();
        readytab=true;
        listFragment.add(0);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
//            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private ProgressDialog pDialog;
    private ListView lv;
    private String TAG = Home_New.class.getSimpleName();

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        ArrayList<ItemList> tweets = new ArrayList<ItemList>();
        int position;
        public GetContacts(int pos){
            position = pos;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... arg) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
//            long timenow =
            String urlrequest="";
            if (bgetfitler==true)
            {
                urlrequest=url+"/"+position+"/"+((System.currentTimeMillis()/1000)-timefiller[timepickfiller])+"/filter="+getfitler;
            }
            else
            {
                urlrequest=url+"/"+position+"/"+((System.currentTimeMillis()/1000)-timefiller[timepickfiller]);
            }
            String jsonStr = sh.makeServiceCall(urlrequest);
            Log.e(TAG, "Response from url: " + urlrequest);

            Log.e(TAG, "Response from url: " + jsonStr);
//            jsonStr=jsonStr.substring(1,jsonStr.length()-2);
            JSONObject c;
            if (jsonStr != null) {
                try {
                    JSONArray array = new JSONArray(jsonStr);
                    Long timeStamp = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
                    long timenow = System.currentTimeMillis()/1000;


//                    Log.e(TAG, "Time: " +result);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject acc = array.getJSONObject(i);

                        String title = acc.getString("title");
                        String img = acc.getString("thumb_img");
                        int  id = Integer.parseInt(acc.getString("id"));
                        String url_viewer = acc.getString("url");
                        String source = acc.getString("name_new__name");
                        long  timesource = Long.parseLong(acc.getString("timestamp_source"));

//                        gettimespan(timesource,timenow);
                        ItemList a =new ItemList(img,title,id,DateUtils.getRelativeTimeSpanString(timesource*1000,timenow*1000,0).toString(),source,0,timesource,url_viewer);
                        tweets.add(a);

                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog

//            if (pDialog.isShowing())
//                pDialog.dismiss();

            Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + position);
            final ListView lstView=(ListView)getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + position).getView().findViewById(R.id.lstView);
            ;
            Log.d(TAG,"here  ID" +page.getArguments().getInt("section_number"));
            final View footer = ((LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_view, null, false);

//            lstView.setEnabled(false);
//            ArrayList<ItemList> arrayList = new ArrayList<>();
////            arrayList.add(new ItemList(R.drawable.ic_test_list, "Day la 3 dung tieu de cua mot tin tuc,Day la noi dung ",10));
////            arrayList.add(new ItemList(R.drawable.ic_test_list, "Day la noi dung tieu de cua mot tin tuc,Day la noi dung2 ",11));
//
//
//            lstView.addFooterView(footer);
            CustomAdapter customAdapter = new CustomAdapter(Home_New.this, R.layout.listview_custom, tweets);
//            lstView.setAdapter(customAdapter);
            View v = getLayoutInflater().inflate(R.layout.footer_view, null);
            lstView.addFooterView(v);
            lstView.setAdapter(customAdapter);

            lstView.removeFooterView(v);
            lstView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    Log.d(TAG,lstView.getFooterViewsCount()+"");
                    if(lstView.getFooterViewsCount()==0)
                    {

                    }

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    final int total=firstVisibleItem+visibleItemCount;

                    int one=0;
                    if(view.getAdapter().getCount()!=0)
                    {


                        Object im=view.getAdapter().getItem(view.getAdapter().getCount()-1);
                        if(total==totalItemCount && lstView.getFooterViewsCount()==0 && AddmoreitemtaskFinished==true )
                        {
                            AddmoreitemtaskFinished=false;
    //                        Log.d(TAG,"ABC");
                            Toast.makeText(getApplicationContext(),((ItemList) im).getItemid()+"",Toast.LENGTH_SHORT).show();
                            lstView.addFooterView(footer);
    //
                            ArrayList<ItemList> ab=new ArrayList<ItemList>();
                            AddmoreItem task = new AddmoreItem(tweets,((ItemList) im).getTimesource(),position);

                            task.execute();
    //


                        }
                        else
                        {
    //                        Log.d(TAG,lstView.getFooterViewsCount()+" else");
                            if(lstView.getFooterViewsCount()==1 && AddmoreitemtaskFinished==true)
                            {
                                lstView.removeFooterView(footer);
                            }



                        }
                    }
                }
            });


        }

    }

    class AddmoreItem extends AsyncTask<Void, Void, Void> {
//        ArrayList<ItemList> array;
        ArrayList<ItemList> tweets = new ArrayList<ItemList>();
        long timesource;
        int position;

        public AddmoreItem(ArrayList<ItemList> array, long timesource, int pos) {
            this.tweets = array;
            this.timesource = timesource;
            this.position= pos;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + position);
            final ListView lstView=(ListView)getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + position).getView().findViewById(R.id.lstView);
//            Log.d(TAG,"here "+lstView+mViewPager.getCurrentItem());
            CustomAdapter customAdapter = new CustomAdapter(Home_New.this, R.layout.listview_custom, tweets);
            View v = getLayoutInflater().inflate(R.layout.footer_view, null);
            lstView.setAdapter(customAdapter);
            lstView.setSelection(tweets.size()-11);
            AddmoreitemtaskFinished=true;

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected Void doInBackground(Void... arg) {
            HttpHandler sh = new HttpHandler();
            String urlrequest="";
            if (bgetfitler==true)
            {
                urlrequest=url + "/" + position + "/t="+timesource+"/filter="+getfitler;
            }
            else
            {
                urlrequest=url + "/" + position + "/t="+timesource;
            }
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(urlrequest);
            Log.e(TAG, "Response from url: " + urlrequest);

            Log.e(TAG, "Response from url: " + jsonStr);
//            jsonStr=jsonStr.substring(1,jsonStr.length()-2);
            JSONObject c;
            if (jsonStr != null) {
                try {
                    JSONArray array = new JSONArray(jsonStr);
                    Long timeStamp = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
                    long timenow = System.currentTimeMillis() / 1000;


//                    Log.e(TAG, "Time: " +result);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject acc = array.getJSONObject(i);

                        String title = acc.getString("title");
                        String img = acc.getString("thumb_img");
                        int id = Integer.parseInt(acc.getString("id"));

                        String source = acc.getString("name_new__name");
                        long timesource = Long.parseLong(acc.getString("timestamp_source"));
//                        gettimespan(timesource, timenow);
                        String url_viewer = acc.getString("url");
                        ItemList a = new ItemList(img, title, id,DateUtils.getRelativeTimeSpanString(timesource*1000,timenow*1000,0).toString() , source, 0,timesource,url_viewer);
                        tweets.add(a);

                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }
    }

    public String gettimespan(long s,long e)
    {
        long diff = e - (s-150);
        long diffSeconds = diff ;
        long diffMinutes = diff / (60 );
        long diffHours = diff / (60 * 60 );
        long diffDays = diff / (24 * 60 * 60 );
        long diffMonth = diff / (24 * 60 * 60* 30 );
//        Log.d(TAG,s+" "+e);
        String relativeTime ="";
        if (diffMonth > 0) {
            relativeTime = diffMonth + " tháng";
        } else if (diffDays > 0) {
            relativeTime = diffDays + " ngày";
        } else if (diffHours > 0) {
            relativeTime = diffHours + " giờ";
        } else if (diffMinutes > 0) {
            relativeTime = diffMinutes + " phút";
        } else if (diffSeconds >= 0)
        {
            relativeTime = diffSeconds + " giây";
        }
        else
        {
            relativeTime= "Vừa xong";
        }
        return relativeTime;
    }
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
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);


//            So 2

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            final ListView lstView = (ListView)rootView.findViewById(R.id.lstView);
            final View footer = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_view, null, false);
//            lstView.addFooterView(footer);

            lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    Object im=(parent).getAdapter().getItem(position);

                        Toast.makeText(getActivity(),((ItemList) im).getItemid()+"",Toast.LENGTH_SHORT).show();
                    Intent intent_content=new Intent(getActivity(), Content.class);
                    intent_content.putExtra("itemid",((ItemList) im).getItemid()+"");
                    intent_content.putExtra("source",((ItemList) im).getSource()+"");
                    intent_content.putExtra("url",((ItemList) im).getUrlviewer()+"");
                    startActivity(intent_content);
                }
            });

            return rootView;
        }


    }

    public static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {

        HttpURLConnection urlConnection = null;

        URL url = new URL(urlString);

        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */);
        urlConnection.setConnectTimeout(15000 /* milliseconds */);

        urlConnection.setDoOutput(true);

        urlConnection.connect();

        BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));

        char[] buffer = new char[1024];

        String jsonString = new String();

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line+"\n");
        }
        br.close();

        jsonString = sb.toString();

        System.out.println("JSON: " + jsonString);

        return new JSONObject(jsonString);
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
            return 13;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:

                    return "Nổi Bật";
                case 1:
                    return "Thời sự";
                case 2:
                    return "Thế giới";
                case 3:
                    return "Kinh doanh";
                case 4:
                    return "Giải trí";
                case 5:
                    return "Thể thao";
                case 6:
                    return "Pháp luật";
                case 7:
                    return "Giáo dục";
                case 8:
                    return "Sức khỏe";
                case 9:
                    return "Du lịch";
                case 10:
                    return "Khoa học";
                case 11:
                    return "Số hóa";
                case 12:
                    return "Công nghệ";
                case 13:
                    return "Xe";


            }
            return null;
        }
    }

}
