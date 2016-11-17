package com.fourapeteam.snacksmall.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fourapeteam.snacksmall.CommodityListActivity;
import com.fourapeteam.snacksmall.ProductDetailsActivity;
import com.fourapeteam.snacksmall.R;
import com.fourapeteam.snacksmall.ShoppingCatActivity;
import com.fourapeteam.snacksmall.adapter.GridViewAdapter;
import com.fourapeteam.snacksmall.bean.BaseProGridBean;
import com.fourapeteam.snacksmall.bean.HotBean;
import com.fourapeteam.snacksmall.datas.CartProData;
import com.fourapeteam.snacksmall.util.HttpUtil;
import com.fourapeteam.snacksmall.util.JsonUtil;
import com.fourapeteam.snacksmall.util.ThreadUtil;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jun on 2016/10/31.
 */
public class HotFragment extends Fragment implements GridViewAdapter.CallChange {
    public static final int GET_HOT_JSON = 100;
    private static final String TAG = "test";
    @BindView(R.id.ivTorunTop)
    ImageView ivTorunTop;
    @BindView(R.id.tvHotBasketNumber)
    TextView tvHotBasketNumber;
    @BindView(R.id.gv)
    PullToRefreshGridView gv;
    @BindView(R.id.llnullShuju)
    LinearLayout llnullShuju;
    @BindView(R.id.GridViewLayout)
    RelativeLayout GridViewLayout;
    @BindView(R.id.goToAnthare)
    TextView goToAnthare;
    private View view;
    HotBean hotBean;
    private GridViewAdapter gridViewAdapter;
    private List<HotBean.DataBean.ItemsBean> items;
    List<BaseProGridBean> data = new ArrayList<>();
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_HOT_JSON:
                    String hotJson = (String) msg.obj;
                    hotBean = JsonUtil.parseHotQuery(hotJson);
                    if (hotBean != null) {
                        llnullShuju.setVisibility(View.GONE);
                        GridViewLayout.setVisibility(View.VISIBLE);
                        items = hotBean.getData().getItems();
                        inintItemsBeanInfo();
                        gridViewAdapter.notifyDataSetChanged();

                    }
                    if (hotBean == null) {
                        GridViewLayout.setVisibility(View.GONE);
                        llnullShuju.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    };

    private CartProData cartData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_hot, container, false);
            ButterKnife.bind(this, view);
            getHotJson();
            gridViewAdapter = new GridViewAdapter(getActivity(), data);
            gv.setAdapter(gridViewAdapter);
            gridViewAdapter.setCallChange(this);
            cartData = CartProData.getInstance();
            onPullToRefresh();
            runToTop();
            OpenProDetaandSendId();
        }
        return view;
    }

    private void onPullToRefresh() {
        gv.setMode(PullToRefreshBase.Mode.BOTH);
        ILoadingLayout loadingLayoutProxy = gv.getLoadingLayoutProxy();
//        loadingLayoutProxy.setPullLabel("1setPullLabel" + getResources().getDrawable(R.drawable.et_home_bg));
//        loadingLayoutProxy.setReleaseLabel("2setReleaseLabel");
        loadingLayoutProxy.setRefreshingLabel("加载数据中.....");
        loadingLayoutProxy.setLastUpdatedLabel("" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
//        loadingLayoutProxy.setLoadingDrawable(getResources().getDrawable(R.mipmap.ic_launcher));

        gv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                        data.clear();
                        getHotJson();
                        gridViewAdapter.notifyDataSetChanged();
                        gv.onRefreshComplete();
                    }
                }, 3000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                        gv.onRefreshComplete();
                    }
                }, 3000);
            }
        });
    }

    private void OpenProDetaandSendId() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int positison, long l) {
                int id = items.get(positison).getId();
                Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    private void getHotJson() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String url = "http://api.ds.lingshi.cccwei.com/?cid=831391&uid=0&tms=20161103162348&sig=7ba7d0ab119cdf0e&wssig=3fb00a2523939d4c&os_type=3&version=23&channel_name=qihoo&srv=2407&pg_cur=1&pg_size=20&subject_id=5&since_id=0";
                String hotJosn = HttpUtil.getStringByOkhttp(url, getContext());
                Message msg = handler.obtainMessage();
                msg.what = GET_HOT_JSON;
                msg.obj = hotJosn;
                handler.sendMessage(msg);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        changeCart();
    }

    private void changeCart() {
        int number = 0;
        int size = cartData.getTestCartPros().size();
        for (int i = 0; i < size; i++) {
            number += cartData.getTestCartPros().get(i).getNumber();
        }
        if (size == 0) {
            tvHotBasketNumber.setVisibility(View.GONE);
        } else {
            tvHotBasketNumber.setVisibility(View.VISIBLE);
        }
        tvHotBasketNumber.setText(number + "");
    }


    private void runToTop() {
        gv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem <= 6) {
                    ivTorunTop.setVisibility(View.GONE);
                } else {
                    ivTorunTop.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @OnClick(R.id.goToAnthare)
    public  void ongoToAnthareClick(View view){
        Intent intent = new Intent(getActivity(), CommodityListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ivShoppingcar)
    public void onivShoppingcarClick(View view) {
        Toast.makeText(getActivity(), "打开购物车", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), ShoppingCatActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ivTorunTop)
    public void onIvToTopClick(View v) {
        gv.getRefreshableView().setSelection(0);
    }

    @Override
    public void getCallChange() {
        changeCart();
    }

    private void inintItemsBeanInfo() {
        for (int i = 0; i < items.size(); i++) {
            HotBean.DataBean.ItemsBean itemsBean = items.get(i);
            String img_url = itemsBean.getImg().getImg_url();
            String title = itemsBean.getTitle();
            int id = itemsBean.getId();
            double current = itemsBean.getPrice().getCurrent();
            double prime = itemsBean.getPrice().getPrime();
            BaseProGridBean baseProGridBean = new BaseProGridBean(img_url, title, current, prime,id);
            data.add(baseProGridBean);
        }
    }
}
