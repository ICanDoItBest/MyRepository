package com.fourapeteam.snacksmall.fragments.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fourapeteam.snacksmall.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by PC-20160823 on 2016/11/2.
 */
public class SettingFragment extends Fragment {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.cbPush)
    CheckBox cbPush;
    @BindView(R.id.btNoLogin)
    Button btNoLogin;
    @BindView(R.id.llMy)
    LinearLayout llMy;
    @BindView(R.id.llData)
    LinearLayout llData;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_setting, container, false);
            ButterKnife.bind(this, view);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (SignFragment.phoneNumber == null) {
            btNoLogin.setVisibility(View.GONE);
        } else {
            btNoLogin.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.llData)
    public void OnllDataClick(View v) {
        String userName = SignFragment.userName;
        String phoneNumber = SignFragment.phoneNumber;
        if (userName==null) {
            Toast.makeText(getActivity(), "请登陆帐号！", Toast.LENGTH_SHORT).show();
        }
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle(userName)
                    .setMessage(phoneNumber)
                    .create()
                    .show();

    }
    @OnClick(R.id.llMy)
    public void OnllMyClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("關於我們，沒什麽好説，不說你們都會原諒我們的,因爲長得帥")
                .create()
                .show();
    }


    @OnClick(R.id.btNoLogin)
    public void onbtNoLoginClick(View view) {
        btNoLogin.setVisibility(View.GONE);
        SignFragment.userName = null;
        SignFragment.objectId = null;
        SignFragment.phoneNumber = null;
    }

    @OnClick(R.id.ivBack)
    public void onivBackSettingClick(View view) {
        listener.onivBackSettingClick();
    }

    onCertificatesFragmentClickListener listener;

    public void setListener(onCertificatesFragmentClickListener listener) {
        this.listener = listener;
    }

    public interface onCertificatesFragmentClickListener {
        void onivBackSettingClick();
    }
}
