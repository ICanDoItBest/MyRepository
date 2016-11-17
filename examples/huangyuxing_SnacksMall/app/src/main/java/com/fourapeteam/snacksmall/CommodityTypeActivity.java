package com.fourapeteam.snacksmall;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fourapeteam.snacksmall.bean.BaseProGridBean;
import com.fourapeteam.snacksmall.bean.HotBean;
import com.fourapeteam.snacksmall.bean.MenuTypeBean;
import com.fourapeteam.snacksmall.datas.CartProData;
import com.fourapeteam.snacksmall.fragments.CommodityTypeFragment;
import com.fourapeteam.snacksmall.util.HttpUtil;
import com.fourapeteam.snacksmall.util.JsonUtil;
import com.fourapeteam.snacksmall.util.ThreadUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommodityTypeActivity extends AppCompatActivity{

    public static final int MSG_JSON_GOT = 20;
    private static final String TAG = "test";
    public static final int MSG_TYPE_JSON_GOT = 22;
    @BindView(R.id.tvBack)
    TextView tvBack;
    @BindView(R.id.tvType)
    TextView tvType;
    @BindView(R.id.tvShoppCart)
    TextView tvShoppCart;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.vpcomtype)
    ViewPager vpcomtype;
    @BindView(R.id.tvBasketNumber3)
    TextView tvBasketNumber3;
    private List<BaseProGridBean> list = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private int count;
    private int id;
    private List<Map<String, Object>> tabs = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_JSON_GOT:
                    Bundle bundle = (Bundle) msg.obj;
                    String json = bundle.getString("json");
                    String json_type = bundle.getString("json_type");
                    MenuTypeBean menuTypeBean = JsonUtil.parseMenuTypeBean(json_type);
                    if (menuTypeBean != null) {
                        count = menuTypeBean.getData().getCount();
                        List<MenuTypeBean.DataBean.ItemsBean> items = menuTypeBean.getData().getItems();
                        for (int i = 0; i < items.size(); i++) {
                            int id = items.get(i).getId();
                            String title = items.get(i).getTitle();
                            Map<String, Object> map = new HashMap<>();
                            map.put("id", id);
                            map.put("title", title);
                            tabs.add(i + 1, map);
                        }
                        initVP();
                    }
                    getData(json);
                    break;
                case MSG_TYPE_JSON_GOT:
                    String typeJson = (String) msg.obj;
                    Log.d(TAG, "handleMessage: ");
                    getData(typeJson);
                    break;
            }
        }
    };
    private List<HotBean.DataBean.ItemsBean> items;

    private void getData(String json) {
        HotBean hotBean = JsonUtil.parseHotQuery(json);
        list.clear();
        if (hotBean != null) {
            items = hotBean.getData().getItems();
            for (int i = 0; i < items.size(); i++) {
                String title = items.get(i).getTitle();
                String img_url = items.get(i).getImg().getImg_url();
                int id = items.get(i).getId();
                double current = items.get(i).getPrice().getCurrent();
                double prime = items.get(i).getPrice().getPrime();
                BaseProGridBean baseProGridBean = new BaseProGridBean(img_url, title, current, prime,id);
                list.add(baseProGridBean);
            }
        } else {
            Toast.makeText(CommodityTypeActivity.this, "去看看别的吧", Toast.LENGTH_SHORT).show();
        }
        EventBus.getDefault().postSticky(list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_type);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        String title = intent.getStringExtra("title");
        tvType.setText(title);
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", "0");
        map.put("title", "全部");
        tabs.add(0, map);

        getJson(id);
    }

    private void getJson(final int id) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String url = "http://api.ds.lingshi.cccwei.com/?cid=831391&uid=0&tms=20161102192855&sig=34e41511513350f8&wssig=0d77f083c2a0b8e6&os_type=3&version=23&channel_name=qihoo&srv=2406&pg_cur=1&pg_size=20&sub_id=0&parent_id=" + id + "&since_id=0";
                String type_url = "http://api.ds.lingshi.cccwei.com/?cid=830847&uid=200703&tms=20161103174403&sig=6fe9307070211cfd&wssig=517a5a032a3c1591&os_type=3&version=20&channel_name=xiaomi&srv=2402&classify_id=" + id;
                String json = HttpUtil.getStringByOkhttp(url, CommodityTypeActivity.this);
                String json_type = HttpUtil.getStringByOkhttp(type_url, CommodityTypeActivity.this);
                Message message = handler.obtainMessage();
                message.what = MSG_JSON_GOT;
                Bundle bundle = new Bundle();
                bundle.putString("json", json);
                bundle.putString("json_type", json_type);
                message.obj = bundle;
                handler.sendMessage(message);
            }
        });
    }

    @OnClick(R.id.tvBack)
    public void onTvBackClick(View view) {
        startActivity(new Intent(this, MallActivity.class));
    }

    @OnClick(R.id.tvShoppCart)
    public void onTvShoppCartClick(View view) {
        Intent intent = new Intent(this, ShoppingCatActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        shoppingcartChange();
    }
    @Subscribe(threadMode = ThreadMode.MAIN,priority = 0,sticky = false)
    public void dealBooleanBooleanEventMain(Boolean frag) {
       if(frag == true){
           shoppingcartChange();
       }
    }

    private void shoppingcartChange() {
        int number=0;
        for (int i = 0; i < CartProData.getInstance().getTestCartPros().size(); i++) {
            number+=CartProData.getInstance().getTestCartPros().get(i).getNumber();
        }
        tvBasketNumber3.setText(number + "");
        if (CartProData.getInstance().getTestCartPros().size()==0){
            tvBasketNumber3.setVisibility(View.GONE);
        }else {
            tvBasketNumber3.setVisibility(View.VISIBLE);
        }
    }

    private void initVP() {
        for (int i = 0; i < count; i++) {
            CommodityTypeFragment fragment = new CommodityTypeFragment();
            fragments.add(fragment);
        }
        vpcomtype.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return ((String) tabs.get(position).get("title"));
            }
        });
        tabLayout.setupWithViewPager(vpcomtype);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String tabName = tab.getText().toString();
                int position = tab.getPosition();
                Log.e(TAG, "id+tabName+position:" + id + "/" + tabName + "/" + position);
                int typeiId = 0;
                if (position != 0) {
                    typeiId = (int) tabs.get(position).get("id");
                }
                Log.e(TAG, "typeiId: " + typeiId);
                final String url1 = "http://api.ds.lingshi.cccwei.com/?cid=831391&uid=0&tms=20161102192855&sig=34e41511513350f8&wssig=0d77f083c2a0b8e6&os_type=3&version=23&channel_name=qihoo&srv=2406&pg_cur=1&pg_size=20&sub_id=" + typeiId + "&parent_id=" + id + "&since_id=0";
                ThreadUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        String json = HttpUtil.getStringByOkhttp(url1, CommodityTypeActivity.this);
                        Message message = handler.obtainMessage();
                        message.what = MSG_TYPE_JSON_GOT;
                        message.obj = json;
                        handler.sendMessage(message);
                    }
                });
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
