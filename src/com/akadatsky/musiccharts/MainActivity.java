package com.akadatsky.musiccharts;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ActionBar actionbar = getActionBar();
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab worldTab = actionbar.newTab().setText(getString(R.string.world));
        ActionBar.Tab ukraineTab = actionbar.newTab().setText(getString(R.string.ukraine));
        ActionBar.Tab denmarkTab = actionbar.newTab().setText(getString(R.string.denmark));

        Fragment worldFragment = new WorldFragment();
        Fragment ukraineFragment = new UkraineFragment();
        Fragment denmarkFragment = new DenmarkFragment();

        worldTab.setTabListener(new MyTabsListener(worldFragment));
        ukraineTab.setTabListener(new MyTabsListener(ukraineFragment));
        denmarkTab.setTabListener(new MyTabsListener(denmarkFragment));

        actionbar.addTab(worldTab);
        actionbar.addTab(ukraineTab);
        actionbar.addTab(denmarkTab);
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
