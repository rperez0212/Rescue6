package com.example.rescue6;

import android.os.Bundle;

import com.example.rescue6.ui.home.HomeFragment;
import com.example.rescue6.ui.newmisson.NewMissionDialogFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;


import com.example.rescue6.ui.main.SectionsPagerAdapter;

public class TabbedActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,NewMissionDialogFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public void onStartMissionButtonPressed() {
        NewMissionDialogFragment.newInstance().show(getSupportFragmentManager(), NewMissionDialogFragment.class.getSimpleName());
    }

    @Override
    public void onStartMission(int objectCount, int minutes) {
        Fragment fragment = getSupportFragmentManager().getFragments().get(0);
        if(fragment instanceof HomeFragment){
            ((HomeFragment)fragment).onStartMission(objectCount,minutes);
        }
    }
}