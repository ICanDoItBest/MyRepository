package com.fourapeteam.snacksmall;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.fourapeteam.snacksmall.fragments.SearchComFragment;
import com.fourapeteam.snacksmall.fragments.SearchFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements SearchFragment.OnSearchFragmentClickListener, SearchComFragment.OnSearchComFragmentClickListener {

    @BindView(R.id.fl)
    FrameLayout fl;
    private SearchFragment searchFragment;
    private Fragment currentFragment;
    private SearchComFragment searchComFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        searchFragment = new SearchFragment();
        searchComFragment = new SearchComFragment();
        searchComFragment.setListener(this);
        searchFragment.setListener(this);

        currentFragment = searchFragment;
        showFragment(searchFragment);
    }

    /**
     * 用碎片替换布局
     *
     * @param fragment 要替换布局的碎片
     */
    private void showFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            ft.add(R.id.fl, fragment);
        }
        ft.hide(currentFragment).show(fragment);
        ft.commit();
        currentFragment = fragment;
    }

    @Override
    public void onTvSearchClick(String key) {
        searchComFragment.setKey(key);
        showFragment(searchComFragment);
    }

    @Override
    public void onTvSearchComClick() {
        showFragment(searchFragment);
    }
}
