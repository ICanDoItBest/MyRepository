package com.fourapeteam.snacksmall;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fourapeteam.snacksmall.bean.ProductDetailsBean;
import com.fourapeteam.snacksmall.bean.TestCartPro;
import com.fourapeteam.snacksmall.datas.CartProData;
import com.fourapeteam.snacksmall.util.HttpUtil;
import com.fourapeteam.snacksmall.util.JsonUtil;
import com.fourapeteam.snacksmall.util.ThreadUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class ProductDetailsActivity extends AppCompatActivity {

    private static final String TAG = "test";
    public static final int GET_GOOD_JSON = 99;
    @BindView(R.id.ivTitleBack)
    TextView ivTitleBack;
    @BindView(R.id.ivTitleShare)
    ImageView ivTitleShare;
    @BindView(R.id.rbCollection)
    RadioButton rbCollection;
    @BindView(R.id.wb)
    WebView wb;
    @BindView(R.id.btnAddCart)
    Button btnAddCart;
    @BindView(R.id.shoppingcart)
    ImageView shoppingcart;
    @BindView(R.id.vpPD)
    ViewPager vpPD;
    @BindView(R.id.tvProductinformation)
    TextView tvProductinformation;
    @BindView(R.id.tvCommodityprices)
    TextView tvCommodityprices;
    @BindView(R.id.tvMerchandisediscount)
    TextView tvMerchandisediscount;
    @BindView(R.id.tvYuanjia)
    TextView tvYuanjia;
    @BindView(R.id.tvProDeraBasketNumber)
    TextView tvProDeraBasketNumber;
    private WebView webview;
    private ProductDetailsBean productDetailsBean;
    private ProductDetailsBean.DataBean data;
    private double current;
    private double prime;
    private String title;
    private String img_url;
    private Handler handler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_GOOD_JSON:
                    String json = (String) msg.obj;
                    productDetailsBean = JsonUtil.parseProductDtailsQuery(json);
                    if(productDetailsBean!=null) {
                        data = productDetailsBean.getData();
                        inintInfo();
                    }else{
                        Toast.makeText(ProductDetailsActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    private int id;

    private void inintInfo() {
        if (data !=null){
            current = data.getPrice().getCurrent();
            prime = data.getPrice().getPrime();
            id = data.getId();
            title = data.getTitle();
            img_url = data.getMain_imgs().get(0).getImg_url();
        }

    }
    private String goodId;
    private CartProData instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);
        webview = ((WebView) findViewById(R.id.wb));
        int Id = getIntent().getIntExtra("id", -1);
        goodId = Id + "";
        Log.e(TAG, "onCreate: " + goodId);
        getGoodJsonId();
        getWebViewtest(goodId);
        instance = CartProData.getInstance();
        ShareSDK.initSDK(this);
    }
    private void showShare() {
        ShareSDK.initSDK(this);

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(title);

        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://ds.lingshi.cccwei.com/api.php?apptype=0&srv=2506&goods_id=" + goodId + "&cid=10002&uid=0&tms=20150721190147&sig=8c35f5a024148111&wssig=308efe4382a088e0&os_type=3&version=18");

        // text是分享文本，所有平台都需要这个字段
        oks.setText("零食小喵分享");

        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"share_pic.jpg");//确保SDcard下面存在此张图片
        oks.setImageUrl(img_url);

        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://ds.lingshi.cccwei.com/api.php?apptype=0&srv=2506&goods_id=" + goodId + "&cid=10002&uid=0&tms=20150721190147&sig=8c35f5a024148111&wssig=308efe4382a088e0&os_type=3&version=18");

        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");

        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));

        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://ds.lingshi.cccwei.com/api.php?apptype=0&srv=2506&goods_id=" + goodId + "&cid=10002&uid=0&tms=20150721190147&sig=8c35f5a024148111&wssig=308efe4382a088e0&os_type=3&version=18");

        // 启动分享GUI
        oks.show(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    private void onRefresh() {
        int number = 0;
        int size = instance.getTestCartPros().size();
        for (int i = 0; i < size; i++) {
            number += instance.getTestCartPros().get(i).getNumber();
        }
        if (size == 0){
            tvProDeraBasketNumber.setVisibility(View.GONE);
        }else {
            tvProDeraBasketNumber.setVisibility(View.VISIBLE);
        }
        tvProDeraBasketNumber.setText(number +"");



    }

    private void getGoodJsonId() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String goodJson = HttpUtil.getProductDetailsJson(goodId,ProductDetailsActivity.this);
                Message msg = handler.obtainMessage();
                msg.what = GET_GOOD_JSON;
                msg.obj = goodJson;
                handler.sendMessage(msg);
            }
        });
    }

    private void getWebViewtest(String goodId) {
        String url = "http://ds.lingshi.cccwei.com/api.php?apptype=0&srv=2506&goods_id=" + goodId + "&cid=10002&uid=0&tms=20150721190147&sig=8c35f5a024148111&wssig=308efe4382a088e0&os_type=3&version=18";
        if (goodId == null) {
            webview.setVisibility(View.GONE);
        }
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(url);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为 false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    @OnClick(R.id.btnAddCart)
    public void addCartOnClick(View view) {
        Toast.makeText(ProductDetailsActivity.this, "成功加入购物车", Toast.LENGTH_SHORT).show();
        TestCartPro testCartPro = new TestCartPro(((float) current), ((float) prime), title, img_url,id);
        instance.addTestCartPros(testCartPro);
        onRefresh();

    }

    @OnCheckedChanged(R.id.rbCollection)
    public void onrbCollectionCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            Toast.makeText(ProductDetailsActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
            rbCollection.setChecked(true);
        }
    }

    @OnClick(R.id.ivTitleBack)
    public void onivTitleBackClick(View view) {
        finish();
    }

    @OnClick(R.id.ivTitleShare)
    public void onivTitleShareClick(View view) {
        showShare();
//        Toast.makeText(ProductDetailsActivity.this, "分享成功", Toast.LENGTH_SHORT).show();


    }

    @OnClick(R.id.shoppingcart)
    public void onivShoppingcarClick(View view) {
        Toast.makeText(ProductDetailsActivity.this, "打开购物车", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, ShoppingCatActivity.class));
    }

}
