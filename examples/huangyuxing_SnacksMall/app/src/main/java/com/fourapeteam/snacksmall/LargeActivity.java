package com.fourapeteam.snacksmall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fourapeteam.snacksmall.fragments.mine.CertificatesFragment;
import com.fourapeteam.snacksmall.fragments.mine.RegisterIIFragment;
import com.fourapeteam.snacksmall.fragments.mine.SamuKuraFragment;
import com.fourapeteam.snacksmall.fragments.mine.SettingFragment;
import com.fourapeteam.snacksmall.fragments.mine.SignFragment;

import butterknife.ButterKnife;


public class LargeActivity extends AppCompatActivity implements SettingFragment.onCertificatesFragmentClickListener
        ,CertificatesFragment.onCertificatesFragmentClickListener,SamuKuraFragment.onCertificatesFragmentClickListener
        ,SignFragment.onCertificatesFragmentClickListener{


    private static final String TAG = "test";
    private Fragment currentFragment;
    private SettingFragment settingFragment;
    private CertificatesFragment certificatesFragment;
    private SamuKuraFragment samuKuraFragment;
    private SignFragment signFragment;
    private RegisterIIFragment registerIIFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large);
        ButterKnife.bind(this);

        settingFragment = new SettingFragment();
        certificatesFragment = new CertificatesFragment();
        samuKuraFragment = new SamuKuraFragment();
        registerIIFragment = new RegisterIIFragment();
        signFragment = new SignFragment();
        signFragment.setListener(this);
        settingFragment.setListener(this);
        certificatesFragment.setListener(this);
        samuKuraFragment.setListener(this);
        currentFragment=settingFragment;

        Intent intent = getIntent();
        int key = intent.getIntExtra("key", -1);
        Log.e(TAG, "key: "+key);
        switch (key){
            case 0:
                showFragment(settingFragment);
                break;
            case 1:
                showFragment(certificatesFragment);
                break;
            case 2:
                showFragment(samuKuraFragment);
                break;
            case 3:
                showFragment(signFragment);
                break;
            case 11:
                showFragment(registerIIFragment);
                break;
        }

    }
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
    public void onivBackSettingClick() {
        finish();
    }

    @Override
    public void onivBackCerClick() {
        finish();
    }

    @Override
    public void onivBackSamuClick() {
        finish();
    }

    @Override
    public void onivBacSignClick() {
        finish();
    }
}
