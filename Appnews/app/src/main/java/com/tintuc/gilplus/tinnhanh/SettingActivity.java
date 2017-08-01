package com.tintuc.gilplus.tinnhanh;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.Set;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingFragment()).commit();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public static class SettingFragment extends PreferenceFragment {
        private MultiSelectListPreference greetingPreference = null;
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            greetingPreference = (MultiSelectListPreference)findPreference("greetingFiles");
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            Set<String> selections = sharedPrefs.getStringSet("sourceseleced", null);


            sharedPrefs.edit().putBoolean("firstactive", true).commit();
//            String[] selected = selections.toArray(new String[] {});
//
//            Log.d("GEEE", selected[0]+"");
//            Preference myPref = (Preference) findPreference("1234");
//            myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//                public boolean onPreferenceClick(Preference preference) {
//                    //open browser or intent here
//                    Intent intent = new Intent(getActivity(), SettingNameActivity.class);
//
//// Start boardgame
//                    startActivity(intent);
//                    Log.d("GEEE",  " A");
//                    return true;
//                }
//            });

        }
    }

}
