package com.akadatsky.musiccharts;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends Activity {

    private ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        initTab(R.string.world, new WorldFragment());
        initTab(R.string.ukraine, new UkraineFragment());
        initTab(R.string.denmark, new DenmarkFragment());
    }

    private void initTab( int tabName, Fragment fragment) {
        ActionBar.Tab tab = actionBar.newTab().setText(getString(tabName));
        tab.setTabListener(new MyTabsListener(fragment));
        actionBar.addTab(tab);
    }

    private class MyTabsListener implements ActionBar.TabListener {

        public Fragment fragment;

        public MyTabsListener(Fragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            ft.replace(R.id.fragment_container, fragment);
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            ft.remove(fragment);
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        }
    }

}
