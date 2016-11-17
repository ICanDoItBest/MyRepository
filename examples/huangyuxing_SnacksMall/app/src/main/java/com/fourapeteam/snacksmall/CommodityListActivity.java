package com.fourapeteam.snacksmall;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

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

public class CommodityListActivity extends AppCompatActivity implements GridViewAdapter.CallChange {

    public static final int MSG_COMMLIST_JSON_GOT = 30;
    private static final String TAG = "test";
    @BindView(R.id.tvBack)
    TextView tvBack;
    @BindView(R.id.tvCommName)
    TextView tvCommName;
    @BindView(R.id.tvspCart)
    TextView tvspCart;
    @BindView(R.id.gvCommList)
    GridView gvCommList;
    @BindView(R.id.tvBasketNumber4)
    TextView tvBasketNumber4;
    private GridViewAdapter gridViewAdapter;
    private int id;
    private CartProData date;
    private List<BaseProGridBean> data = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_COMMLIST_JSON_GOT) {
                String json = (String) msg.obj;
                HotBean hotBean = JsonUtil.parseHotQuery(json);
                if (hotBean != null) {
                    List<HotBean.DataBean.ItemsBean> items = hotBean.getData().getItems();
                    for (int i = 0; i < items.size(); i++) {
                        String title = items.get(i).getTitle();
                        double prime = items.get(i).getPrice().getPrime();
                        double current = items.get(i).getPrice().getCurrent();
                        String img_url = items.get(i).getImg().getImg_url();
                        id = items.get(i).getId();
                        BaseProGridBean baseProGridBean = new BaseProGridBean(img_url, title, current, prime,id);
                        data.add(baseProGridBean);
                    }
                    if (gridViewAdapter != null) {
                        gridViewAdapter.setData(data);
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_list);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        int listId = intent.getIntExtra("listId", -1);
        String url = null;
        if(listId==-1){
            url = "http://api.ds.lingshi.cccwei.com/?cid=831391&uid=0&tms=20161102192855&sig=34e41511513350f8&wssig=0d77f083c2a0b8e6&os_type=3&version=23&channel_name=qihoo&srv=2406&pg_cur=1&pg_size=20&sub_id=0&parent_id=32&since_id=0";
        }else {
            url = "http://api.ds.lingshi.cccwei.com/?cid=831391&uid=0&tms=20161103162348&sig=7ba7d0ab119cdf0e&wssig=3fb00a2523939d4c&os_type=3&version=23&channel_name=qihoo&srv=2407&pg_cur=1&pg_size=20&subject_id=" + listId + "&since_id=0";
        }
        tvCommName.setText("商品列表");


        final String ztUrl = url;
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String json = HttpUtil.getStringByOkhttp(ztUrl, CommodityListActivity.this);
                Message message = handler.obtainMessage();
                message.what = MSG_COMMLIST_JSON_GOT;
                message.obj = json;
                handler.sendMessage(message);
            }
        });
        gridViewAdapter = new GridViewAdapter(this, data);
        gridViewAdapter.setCallChange(this);
        gvCommList.setAdapter(gridViewAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        shoppingCartChange();
    }

    private void shoppingCartChange() {
        int number=0;
        for (int i = 0; i < CartProData.getInstance().getTestCartPros().size(); i++) {
            number+=CartProData.getInstance().getTestCartPros().get(i).getNumber();
        }
        tvBasketNumber4.setText(number + "");
        if (CartProData.getInstance().getTestCartPros().size()==0){
            tvBasketNumber4.setVisibility(View.GONE);
        }else {
            tvBasketNumber4.setVisibility(View.VISIBLE);
        }
    }

    @OnItemClick(R.id.gvCommList)
    public void onItemClick(AdapterView<?> parent, View view, int position, long i) {
        int currentId = data.get(position).getId();
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra("id", currentId);
        Log.e(TAG, "iddddddddd: "+ currentId);
        startActivity(intent);
    }

    @OnClick(R.id.tvspCart)
    public void onTvspCartClick(View view) {
        startActivity(new Intent(this, ShoppingCatActivity.class));
    }

    @OnClick(R.id.tvBack)
    public void onTvBackClick(View view) {
        finish();
    }

    @Override
    public void getCallChange() {
        shoppingCartChange();
    }
}
