package com.fourapeteam.snacksmall;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fourapeteam.snacksmall.fragments.TradingFragment;
import com.fourapeteam.snacksmall.fragments.mine.DeliveryFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    private static final String TAG = "asd";
    @BindView(R.id.fl)
    LinearLayout fl;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.TL)
    TabLayout TL;
    @BindView(R.id.VP)
    ViewPager VP;
    private List<Fragment> fragments = new ArrayList<>();
    private String [] tabs1 = new String[]{"全部","待付款","待发货","待收货","待评价"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        int key = intent.getIntExtra("key",-1);

        Log.d(TAG, "key: "+key);
        inllvp();
        TL.setupWithViewPager(VP);
        VP.setCurrentItem(key);

    }


    private void inllvp() {
        for (int i = 0; i < tabs1.length-3; i++) {
            TradingFragment fragment = new TradingFragment();
            fragments.add(fragment);
        }
        for (int i = 2; i < tabs1.length; i++) {
            DeliveryFragment deliveryFragment = new DeliveryFragment();
            fragments.add(deliveryFragment);
        }
        VP.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int key) {
                return fragments.get(key);
            }
            @Override
            public int getCount() {
                return fragments.size();
            }
            @Override
            public CharSequence getPageTitle(int key) {
                return tabs1[key];
            }
        });
    }


    @OnClick(R.id.ivBack)
    public void onIvBackClick(View v) {
        finish();
    }
}

