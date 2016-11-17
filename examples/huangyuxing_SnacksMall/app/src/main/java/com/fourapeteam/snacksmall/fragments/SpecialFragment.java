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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fourapeteam.snacksmall.R;
import com.fourapeteam.snacksmall.ShoppingCatActivity;
import com.fourapeteam.snacksmall.SpecialDetailsActivity;
import com.fourapeteam.snacksmall.adapter.SpecialListAdapter;
import com.fourapeteam.snacksmall.bean.SpecialHeadBean;
import com.fourapeteam.snacksmall.bean.SpecialListBean;
import com.fourapeteam.snacksmall.bean.TestPro;
import com.fourapeteam.snacksmall.datas.CartProData;
import com.fourapeteam.snacksmall.util.HttpUtil;
import com.fourapeteam.snacksmall.util.JsonUtil;
import com.fourapeteam.snacksmall.util.ThreadUtil;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jun on 2016/10/31.
 */
public class SpecialFragment extends Fragment {
    public static final int SPECIAL_HEAD_JSON = 1;
    public static final int SPECIAL_LIST_JSON = 2;
    private static final int SPECIAL_ADD_JSON = 3;
    @BindView(R.id.tvBasket)
    TextView tvBasket;
    @BindView(R.id.tvBasketNumber)
    TextView tvBasketNumber;
    @BindView(R.id.lvSpecial)
    PullToRefreshListView lvSpecial;
    @BindView(R.id.ivToTop)
    ImageView ivToTop;
    private View view;
    List<TestPro> testPros = new ArrayList<>();
    private SpecialListAdapter specialListAdapter;
    private CartProData data;
    private List<SpecialListBean.DataBean.ItemsBean> listItems;
    private SpecialListBean specialListBean;
    private SpecialHeadBean specialHeadBean;
    private int pag=1;
    boolean isFirst=true;

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SPECIAL_HEAD_JSON:
                    specialHeadBean = JsonUtil.parseSpecialHeadBean(((String) msg.obj));
                    if(specialHeadBean !=null) {
                        List<SpecialHeadBean.DataBean.ItemsBean> items = specialHeadBean.getData().getItems();
                        specialListAdapter.setHeadData(items);
                    }else{
                        Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case SPECIAL_LIST_JSON:
                    specialListBean = JsonUtil.parseSpecialListBean(((String) msg.obj));
                    if(specialListBean!=null){
                        listItems = specialListBean.getData().getItems();
                        specialListAdapter.setListData(listItems);
                    }else{
                        Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case SPECIAL_ADD_JSON:
                    List<SpecialListBean.DataBean.ItemsBean> addItem = JsonUtil.parseSpecialListBean(((String) msg.obj)).getData().getItems();
                    if(addItem.size()!=0) {
                        listItems.addAll(addItem);
                        specialListAdapter.setListData(listItems);
                    }else{
                        Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_special, container, false);
            ButterKnife.bind(this, view);
        }

        if (isFirst){
            isFirst=false;
            initData();
        }
        lvSpecialSL();
        lvSpecialICL();
        setRefresh();
        return view;
    }

    private void setRefresh() {
        lvSpecial.setMode(PullToRefreshBase.Mode.BOTH);
        ILoadingLayout loadingLayoutProxy = lvSpecial.getLoadingLayoutProxy();
        loadingLayoutProxy.setRefreshingLabel("加载数据中...");
        lvSpecial.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        specialListAdapter.notifyDataSetChanged();
                        lvSpecial.onRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (pag>5){
                    Toast.makeText(getContext(), "没有可加载数据！", Toast.LENGTH_SHORT).show();
                    lvSpecial.onRefreshComplete();
                    return;
                }
                ThreadUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        String json = HttpUtil.getSpecialList(getContext(),++pag);
                        Message message = handler.obtainMessage();
                        message.what = SPECIAL_ADD_JSON;
                        message.obj = json;
                        handler.sendMessage(message);
                        lvSpecial.onRefreshComplete();
                    }
                });
            }
        });
    }

    private void lvSpecialICL() {
        lvSpecial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != 1) {
                    Intent intent = new Intent(getActivity(), SpecialDetailsActivity.class);
                    int liatId = specialListBean.getData().getItems().get(position - 2).getId();
                    intent.putExtra("id",liatId);
                    startActivity(intent);
                }
            }
        });
    }

    private void lvSpecialSL() {
        lvSpecial.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem <= 6) {
                    ivToTop.setVisibility(View.GONE);
                } else {
                    ivToTop.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setNumber();
    }

    private void setNumber() {
        int number=0;
        for (int i = 0; i < data.getTestCartPros().size(); i++) {
            number+=data.getTestCartPros().get(i).getNumber();
        }
        tvBasketNumber.setText(number + "");
        if (data.getTestCartPros().size()==0){
            tvBasketNumber.setVisibility(View.INVISIBLE);
        }else {
            tvBasketNumber.setVisibility(View.VISIBLE);
        }
    }

    private void initData() {
        data = CartProData.getInstance();
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String json = HttpUtil.getSpecialHead(getContext());
                Message message = handler.obtainMessage();
                message.what = SPECIAL_HEAD_JSON;
                message.obj = json;
                handler.sendMessage(message);
            }
        });

        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String json = HttpUtil.getSpecialList(getContext(),pag);
                Message message = handler.obtainMessage();
                message.what = SPECIAL_LIST_JSON;
                message.obj = json;
                handler.sendMessage(message);
            }
        });
            specialListAdapter = new SpecialListAdapter(listItems, getContext());
            lvSpecial.setAdapter(specialListAdapter);
    }

    @OnClick(R.id.tvBasket)
    public void ontvBasketClick(View v) {
        Intent intent = new Intent(getActivity(), ShoppingCatActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ivToTop)
    public void onIvToTopClick(View v) {
        lvSpecial.getRefreshableView().setSelection(0);
        Toast.makeText(getContext(), "到顶", Toast.LENGTH_SHORT).show();
    }

}
