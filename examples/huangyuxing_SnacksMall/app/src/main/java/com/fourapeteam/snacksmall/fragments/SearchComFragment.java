package com.fourapeteam.snacksmall.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by Administrator on 2016/11/1.
 */
public class SearchComFragment extends Fragment implements GridViewAdapter.CallChange {

    public static final int MSG_SEARCH_JSON_GOT = 44;
    private static final String TAG = "test";
    @BindView(R.id.tvBack)
    TextView tvBack;
    @BindView(R.id.tvFoodName)
    TextView tvFoodName;
    @BindView(R.id.tvShopCart)
    TextView tvShopCart;
    @BindView(R.id.gvFood)
    GridView gvFood;
    @BindView(R.id.tvBasketNumber2)
    TextView tvBasketNumber2;
    @BindView(R.id.goToAnthare)
    TextView goToAnthare;
    @BindView(R.id.llnullSearch)
    LinearLayout llnullSearch;
    private String key;
    private View view;
    private GridViewAdapter gridViewAdapter;
    private List<BaseProGridBean> data = new ArrayList<>();

    public void setKey(String key) {
        this.key = key;
        Log.e("test", "key2: "+key);
        getData(key);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SEARCH_JSON_GOT:
                    String json = (String) msg.obj;
                    data.clear();
                    HotBean hotBean = JsonUtil.parseHotQuery(json);
                    if (hotBean != null) {
                        gvFood.setVisibility(View.VISIBLE);
                        llnullSearch.setVisibility(View.GONE);
                        List<HotBean.DataBean.ItemsBean> items = hotBean.getData().getItems();
                        for (int i = 0; i < items.size(); i++) {
                            String title = items.get(i).getTitle();
                            double prime = items.get(i).getPrice().getPrime();
                            double current = items.get(i).getPrice().getCurrent();
                            String img_url = items.get(i).getImg().getImg_url();
                            int id = items.get(i).getId();
                            Log.e(TAG, "id: search"+id );
                            BaseProGridBean baseProGridBean = new BaseProGridBean(img_url, title, current, prime, id);
                            data.add(baseProGridBean);
                        }
                        if (gridViewAdapter != null) {
                            gridViewAdapter.setData(data);
                        }
                    } else {
                        gvFood.setVisibility(View.GONE);
                        llnullSearch.setVisibility(View.VISIBLE);
                        goToAnthare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), CommodityListActivity.class);
                                intent.putExtra("listId",610);
                                startActivity(intent);
                            }
                        });
                        Toast.makeText(getContext(), "什么鬼都没有，你搜别的看看呗", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_searchcom, container, false);
            ButterKnife.bind(this, view);

            getData(key);
            gridViewAdapter = new GridViewAdapter(getActivity(), data);
            gridViewAdapter.setCallChange(this);
            gvFood.setAdapter(gridViewAdapter);
        }
        return view;
    }
    private void getData(String key) {
        if(view!=null) {
            tvFoodName.setText(key);
        }
        final String url = "http://api.ds.lingshi.cccwei.com/?cid=831391&uid=0&tms=20161102192400&sig=608a4d73a88c7ad3&wssig=e48d29f49ec84e7c&os_type=3&version=23&channel_name=qihoo&srv=2204&since_id=0&pg_cur=1&pg_size=20&keyword=" + key;
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String json = HttpUtil.getStringByOkhttp(url, getContext());
                Message message = handler.obtainMessage();
                message.what = MSG_SEARCH_JSON_GOT;
                message.obj = json;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        shopCartChange();
    }

    private void shopCartChange() {
        int number=0;
        for (int i = 0; i < CartProData.getInstance().getTestCartPros().size(); i++) {
            number+=CartProData.getInstance().getTestCartPros().get(i).getNumber();
        }
        tvBasketNumber2.setText(number + "");
        if (CartProData.getInstance().getTestCartPros().size()==0){
            tvBasketNumber2.setVisibility(View.GONE);
        }else {
            tvBasketNumber2.setVisibility(View.VISIBLE);
        }
    }

    @OnItemClick(R.id.gvFood)
    public void onItemClick(AdapterView<?> adapterView, View view, int positison, long l) {
        int id = data.get(positison).getId();
        Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @OnClick(R.id.tvBack)
    public void onTvBackClick(View view) {
        if (listener != null) {
            listener.onTvSearchComClick();
        }
    }

    @OnClick(R.id.tvShopCart)
    public void onTvShopCartClick(View view) {
        startActivity(new Intent(getActivity(), ShoppingCatActivity.class));
    }

    OnSearchComFragmentClickListener listener;

    public void setListener(OnSearchComFragmentClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void getCallChange() {
        shopCartChange();
    }

    public interface OnSearchComFragmentClickListener {
        void onTvSearchComClick();
    }
}
