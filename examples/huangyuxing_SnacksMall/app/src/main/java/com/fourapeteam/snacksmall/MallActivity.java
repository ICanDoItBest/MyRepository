package com.fourapeteam.snacksmall;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fourapeteam.snacksmall.fragments.HomeFragment;
import com.fourapeteam.snacksmall.fragments.HotFragment;
import com.fourapeteam.snacksmall.fragments.MineFragment;
import com.fourapeteam.snacksmall.fragments.SpecialFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MallActivity extends AppCompatActivity {

    private static final String TAG = "juntest";
    @BindView(R.id.vpMall)
    ViewPager vpMall;
    @BindView(R.id.rbHome)
    RadioButton rbHome;
    @BindView(R.id.rbHot)
    RadioButton rbHot;
    @BindView(R.id.rbSpecial)
    RadioButton rbSpecial;
    @BindView(R.id.rbMine)
    RadioButton rbMine;
    @BindView(R.id.rgMall)
    RadioGroup rgMall;
    List<Fragment> fragments=new ArrayList<>();
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall);
        ButterKnife.bind(this);


        HomeFragment homeFragment = new HomeFragment();
        HotFragment hotFragment = new HotFragment();
        SpecialFragment specialFragment = new SpecialFragment();
        MineFragment mineFragment = new MineFragment();

        fragments.add(homeFragment);
        fragments.add(hotFragment);
        fragments.add(specialFragment);
        fragments.add(mineFragment);

        vpMall.setCurrentItem(0);
        vpMall.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });

        vpMall.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton) rgMall.getChildAt(position)).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        rgMall.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbHome:
                        vpMall.setCurrentItem(0);
                        break;
                    case R.id.rbHot:
                        vpMall.setCurrentItem(1);
                        break;
                    case R.id.rbSpecial:
                        vpMall.setCurrentItem(2);
                        break;
                    case R.id.rbMine:
                        vpMall.setCurrentItem(3);
                        break;

                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
