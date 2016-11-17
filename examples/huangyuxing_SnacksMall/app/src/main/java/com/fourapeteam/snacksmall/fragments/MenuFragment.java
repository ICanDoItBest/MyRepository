package com.fourapeteam.snacksmall.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fourapeteam.snacksmall.CommodityTypeActivity;
import com.fourapeteam.snacksmall.LargeActivity;
import com.fourapeteam.snacksmall.R;
import com.fourapeteam.snacksmall.adapter.MyMenuAdapter;
import com.fourapeteam.snacksmall.bean.HomeMenuBean;
import com.fourapeteam.snacksmall.fragments.mine.SignFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by idea on 2016/10/19.
 */
public class MenuFragment extends Fragment {

    private static final String TAG = "test";
    @BindView(R.id.sdvCover)
    SimpleDraweeView sdvCover;
    @BindView(R.id.lvMenu)
    ListView lvMenu;
    @BindView(R.id.tvDengLu)
    TextView tvDengLu;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    private View view;
    private Handler handler = new Handler();
    private MyMenuAdapter myMenuAdapter;
    private List<HomeMenuBean> list = new ArrayList<>();

    SlidingMenu slidingMenu;

    public void setList(List<HomeMenuBean> list) {
        this.list = list;
        if (myMenuAdapter != null) {
            myMenuAdapter.setData(list);
        }
    }

    public MenuFragment(SlidingMenu slidingMenu) {
        this.slidingMenu = slidingMenu;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_menu, container, false);
            ButterKnife.bind(this, view);

            myMenuAdapter = new MyMenuAdapter(getContext(), list);
            lvMenu.setAdapter(myMenuAdapter);
        }
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (SignFragment.phoneNumber == null) {
            tvDengLu.setVisibility(View.VISIBLE);
            tvUserName.setVisibility(View.GONE);
        }else{
            tvUserName.setText(SignFragment.userName);
            tvDengLu.setVisibility(View.GONE);
            tvUserName.setVisibility(View.VISIBLE);
        }
    }

    //跳到登录
    @OnClick(R.id.tvDengLu)
    public void OnClickDengLu(View v) {
        Intent intent = new Intent(getActivity(), LargeActivity.class);
        intent.putExtra("key", 3);
        startActivity(intent);
    }

    @OnItemClick(R.id.lvMenu)
    public void onLvItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(getActivity(), CommodityTypeActivity.class);
        intent.putExtra("id", list.get(position).getId());
        intent.putExtra("title", list.get(position).getTitle());
        startActivity(intent);
        if (slidingMenu != null) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    slidingMenu.toggle();
                }
            }, 1000);
        }
    }
}
