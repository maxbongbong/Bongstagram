package com.bong.bongstagram.Main.Ui.Main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> items = new ArrayList<>();
     public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
         super(fm, behavior);
     }

     public void addItem(Fragment item){
         items.add(item);
     }

     @NonNull
     @Override
     public Fragment getItem(int position) {
         return items.get(position);
     }

     @Override
     public int getCount() {
         return items.size();
     }
 }
