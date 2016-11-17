package com.fourapeteam.snacksmall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.fourapeteam.snacksmall.datas.CartProData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SpecialDetailsActivity extends AppCompatActivity {


    @BindView(R.id.wvInfo)
    WebView wvInfo;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvBasket)
    TextView tvBasket;
    @BindView(R.id.tvBasketNumber)
    TextView tvBasketNumber;
    private CartProData data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_details);
        ButterKnife.bind(this);
        data = CartProData.getInstance();

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 1);
        initWebView(id);

    }

    private void initWebView(int id) {
        String url = "http://ds.lingshi.cccwei.com/api.php?active=0&apptype=0&srv=2414&subject_id=" + id + "&cid=10002&uid=0&tms=20150721190147&sig=8c35f5a024148111&wssig=308efe4382a088e0&os_type=3&version=12";
        wvInfo.getSettings().setJavaScriptEnabled(true);
        wvInfo.loadUrl(url);
        wvInfo.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        tvBasketNumber.setText(data.getTestCartPros().size() + "");
    }

    @OnClick(R.id.ivBack)
    public void onIvBackClick(View v) {
        finish();
    }

    @OnClick(R.id.tvBasket)
    public void ontvBasketClick(View v) {
        Intent intent = new Intent(SpecialDetailsActivity.this, ShoppingCatActivity.class);
        startActivity(intent);
    }

}
