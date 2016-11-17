package com.fourapeteam.snacksmall.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fourapeteam.snacksmall.CommodityListActivity;
import com.fourapeteam.snacksmall.ProductDetailsActivity;
import com.fourapeteam.snacksmall.R;
import com.fourapeteam.snacksmall.adapter.GridViewAdapter;
import com.fourapeteam.snacksmall.bean.BaseProGridBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by Administrator on 2016/11/2.
 */
public class CommodityTypeFragment extends Fragment implements GridViewAdapter.CallChange {

    @BindView(R.id.gvcomm)
    GridView gvcomm;
    @BindView(R.id.goToAnthare)
    TextView goToAnthare;
    @BindView(R.id.llnull)
    LinearLayout llnull;
    private View view;
    private GridViewAdapter gridViewAdapter;
    private List<BaseProGridBean> data = new ArrayList<>();

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 10, sticky = true)
    public void dealBooleanEventMain(List<BaseProGridBean> list) {
        data = list;
        if(list.size() == 0){
            gvcomm.setVisibility(View.GONE);
            llnull.setVisibility(View.VISIBLE);
            goToAnthare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), CommodityListActivity.class);
                    intent.putExtra("listId",610);
                    startActivity(intent);
                }
            });
        }else{
            gvcomm.setVisibility(View.VISIBLE);
            llnull.setVisibility(View.GONE);
            if (gridViewAdapter != null) {
                gridViewAdapter.setData(list);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_commoditytype, container, false);
            ButterKnife.bind(this, view);
            EventBus.getDefault().register(this);

            gridViewAdapter = new GridViewAdapter(getActivity(), data);
            gridViewAdapter.setCallChange(this);
            gvcomm.setAdapter(gridViewAdapter);
        }
        ButterKnife.bind(this, view);
        return view;
    }

    @OnItemClick(R.id.gvcomm)
    public void onGvcommItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(data!=null){
            int id1 = data.get(position).getId();
            Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
            intent.putExtra("id",id1);
            startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void getCallChange() {
        EventBus.getDefault().post(true);
    }
}
