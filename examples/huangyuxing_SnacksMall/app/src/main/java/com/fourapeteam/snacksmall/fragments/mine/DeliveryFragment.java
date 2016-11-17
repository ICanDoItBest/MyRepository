package com.fourapeteam.snacksmall.fragments.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fourapeteam.snacksmall.CommodityListActivity;
import com.fourapeteam.snacksmall.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by PC-20160823 on 2016/11/7.
 */
public class DeliveryFragment extends Fragment {

    @BindView(R.id.tvGoShopping)
    TextView tvGoShopping;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_delivery, container, false);
            ButterKnife.bind(this, view);
        }
        return view;
    }

    @OnClick(R.id.tvGoShopping)
    public void onTvGoShoppingClick(View v) {
        startActivity(new Intent(getActivity(), CommodityListActivity.class));
    }
}
