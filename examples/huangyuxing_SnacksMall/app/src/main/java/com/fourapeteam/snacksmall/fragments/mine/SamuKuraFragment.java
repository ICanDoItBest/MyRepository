package com.fourapeteam.snacksmall.fragments.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fourapeteam.snacksmall.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by PC-20160823 on 2016/11/3.
 */
public class SamuKuraFragment extends Fragment {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_samukura, container, false);
            ButterKnife.bind(this, view);
        }
        return view;
    }
    @OnClick(R.id.ivBack)
    public void onivBackSamuClick(View view) {
        listener.onivBackSamuClick();
    }
    onCertificatesFragmentClickListener listener;

    public void setListener(onCertificatesFragmentClickListener listener) {
        this.listener = listener;
    }

    public interface onCertificatesFragmentClickListener {
        void onivBackSamuClick();
    }
}
