package com.fourapeteam.snacksmall.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fourapeteam.snacksmall.CommodityListActivity;
import com.fourapeteam.snacksmall.ProductDetailsActivity;
import com.fourapeteam.snacksmall.R;
import com.fourapeteam.snacksmall.SearchActivity;
import com.fourapeteam.snacksmall.ShoppingCatActivity;
import com.fourapeteam.snacksmall.adapter.GridViewAdapter;
import com.fourapeteam.snacksmall.adapter.HomeListAdapter;
import com.fourapeteam.snacksmall.bean.BaseProGridBean;
import com.fourapeteam.snacksmall.bean.HomeCommodityList;
import com.fourapeteam.snacksmall.bean.HomeItemBean;
import com.fourapeteam.snacksmall.bean.HomeMenuBean;
import com.fourapeteam.snacksmall.datas.CartProData;
import com.fourapeteam.snacksmall.util.HttpUtil;
import com.fourapeteam.snacksmall.util.JsonUtil;
import com.fourapeteam.snacksmall.util.ThreadUtil;
import com.fourapeteam.snacksmall.widget.MyListView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by jun on 2016/10/31.
 */
public class HomeFragment extends Fragment implements GridViewAdapter.CallChange{
    private static final String TAG = "test";
    public static final int MSG_COM_JSON_GOT = 10;
    @BindView(R.id.tvMenu)
    TextView tvMenu;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.tvShoppingCar)
    TextView tvShoppingCar;
    @BindView(R.id.ivPic)
    ImageView ivPic;
    @BindView(R.id.lv)
    MyListView lv;
    @BindView(R.id.tvBasketNumber1)
    TextView tvBasketNumber1;
    private View view;
    private HomeListAdapter homeListAdapter;
    private List<BaseProGridBean> data = new ArrayList<>();
    private List<HomeMenuBean> list = new ArrayList<>();
    public SlidingMenu slidingMenu;
    public MenuFragment menuFragment;
    private int listId;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_COM_JSON_GOT:
                    Bundle bundle = (Bundle) msg.obj;
                    String json = bundle.getString("json");
                    String jsonItem = bundle.getString("jsonItem");
                    updateData(jsonItem);
                    updateDataHCL(json);
                    break;
            }
        }
    };
    private List<HomeItemBean.DataBean.ItemsBean> items;

    private void updateDataHCL(String json) {
        HomeCommodityList homeCommodityList = JsonUtil.parseCommodityList(json);
        if (homeCommodityList != null) {
            List<HomeCommodityList.DataBean.ClassifiesBean> classifies = homeCommodityList.getData().getClassifies();
            for (int i = 0; i < classifies.size(); i++) {
                int id = classifies.get(i).getId();
                String title = classifies.get(i).getTitle();
                String desc = classifies.get(i).getDesc();
                String img_url = classifies.get(i).getImg().getImg_url();
                HomeMenuBean homeMenuBean = new HomeMenuBean(id, title, desc, img_url);
                list.add(homeMenuBean);
            }
            menuFragment.setList(list);

            String img_url = homeCommodityList.getData().getTopics().get(0).getImg().getImg_url();
            listId = Integer.parseInt(homeCommodityList.getData().getTopics().get(0).getAction().getInfo());
            Glide.with(getContext()).load(img_url).into(ivPic);
            if (homeListAdapter != null) {
                homeListAdapter.setHomeCommodityList(homeCommodityList);
            }
        }
    }

    private void updateData(String jsonItem) {
        HomeItemBean homeItemBean = JsonUtil.parseHomeItemBean(jsonItem);
        if (homeItemBean != null) {
            items = homeItemBean.getData().getItems();
            for (int i = 0; i < items.size(); i++) {
                String title = items.get(i).getTitle();
                int commodityId = items.get(i).getId();
                double current = items.get(i).getPrice().getCurrent();//现价
                double prime = (double) items.get(i).getPrice().getPrime();//原价
                String img_url = items.get(i).getImg().getImg_url();
                BaseProGridBean baseProGridBean = new BaseProGridBean(img_url, title, current, prime,commodityId);
                data.add(baseProGridBean);
            }
            homeListAdapter.setData(data);
        }
    }


    private String url = "http://api.ds.lingshi.cccwei.com/?cid=831391&uid=0&tms=20161102191205&sig=1268c6c92fd67e14&wssig=e90c88696e1d088e&os_type=3&version=23&channel_name=qihoo&srv=2201";
    private String urlItem = "http://api.ds.lingshi.cccwei.com/?cid=831391&uid=0&tms=20161102191206&sig=a93db27f86fe3a4b&wssig=a7468ede22ba4539&os_type=3&version=23&channel_name=qihoo&srv=2206&since_id=0&pg_cur=1&pg_size=20";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String json = HttpUtil.getStringByOkhttp(url, getContext());
                String jsonItem = HttpUtil.getStringByOkhttp(urlItem, getContext());
                Bundle bundle = new Bundle();
                bundle.putString("json", json);
                bundle.putString("jsonItem", jsonItem);
                Message message = handler.obtainMessage();
                message.what = MSG_COM_JSON_GOT;
                message.obj = bundle;
                handler.sendMessage(message);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home, container, false);
            ButterKnife.bind(this, view);
            etSearch.setInputType(InputType.TYPE_NULL);

            initSlidingMenu();
            homeListAdapter = new HomeListAdapter(getContext(), data);
            lv.setAdapter(homeListAdapter);
        }
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        shoppingCartChange();
    }

    private void shoppingCartChange() {
        int number=0;
        for (int i = 0; i < CartProData.getInstance().getTestCartPros().size(); i++) {
            number+=CartProData.getInstance().getTestCartPros().get(i).getNumber();
        }
        tvBasketNumber1.setText(number + "");
        if (CartProData.getInstance().getTestCartPros().size()==0){
            tvBasketNumber1.setVisibility(View.GONE);
        }else {
            tvBasketNumber1.setVisibility(View.VISIBLE);
        }
    }

    //点击跳转购物车
    @OnClick(R.id.tvShoppingCar)
    public void onTvShoppingCarClick(View view) {
        startActivity(new Intent(getActivity(), ShoppingCatActivity.class));
    }

    //点击跳转商品列表
    @OnClick(R.id.ivPic)
    public void onTvPicClick(View view) {
        Intent intent = new Intent(getActivity(), CommodityListActivity.class);
        //intent.putExtra("listId", listId);
        startActivity(intent);
    }

    //点击跳转搜索界面
    @OnClick(R.id.etSearch)
    public void onEtSearchClick(View view) {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
    }

    //点击商品条目跳转详情页
    @OnItemClick(R.id.lv)
    public void onLvItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
        Log.e(TAG, "vitems.get(position).getId():id "+items.get(position).getId() );
        intent.putExtra("id", items.get(position-1).getId());
        startActivity(intent);
    }

    //点击跳转菜单界面
    @OnClick(R.id.tvMenu)
    public void onTvMenu(View view) {
        slidingMenu.showMenu();
    }


    private void initSlidingMenu() {
        // 菜单方式、菜单布局、宽度、阴影、视差、滑屏模式、绑定方式
        slidingMenu = new SlidingMenu(getContext());
        menuFragment = new MenuFragment(slidingMenu);
        slidingMenu.setMode(SlidingMenu.LEFT);
        //替换菜单布局为碎片
        slidingMenu.setMenu(R.layout.container_slidingmenu);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flRoot, menuFragment).commit();

        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        slidingMenu.setBehindWidth(width*3/4);
        //阴影和宽度
        slidingMenu.setShadowWidth(20);
        slidingMenu.setShadowDrawable(R.drawable.shape_shadow_slidingmenu);
        slidingMenu.setFadeDegree(0.7f);//视差
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        slidingMenu.attachToActivity(getActivity(), SlidingMenu.SLIDING_WINDOW);//在actionBar下面
    }

    @Override
    public void getCallChange() {
        shoppingCartChange();
    }
}
