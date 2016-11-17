package com.fourapeteam.snacksmall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fourapeteam.snacksmall.adapter.CartProAdapter;
import com.fourapeteam.snacksmall.bean.TestCartPro;
import com.fourapeteam.snacksmall.datas.CartProData;
import com.fourapeteam.snacksmall.fragments.mine.SignFragment;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShoppingCatActivity extends AppCompatActivity implements CartProAdapter.CallBack {

    private static final String TAG = "test";
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ProNumber)
    TextView ProNumber;
    @BindView(R.id.llCart)
    LinearLayout llCart;
    @BindView(R.id.tvGoShopping)
    TextView tvGoShopping;
    @BindView(R.id.llNoPro)
    LinearLayout llNoPro;
    @BindView(R.id.lvCartPro)
    ListView lvCartPro;
    @BindView(R.id.cbCartPro)
    CheckBox cbCartPro;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.tvPayment)
    TextView tvPayment;
    @BindView(R.id.cbEdit)
    CheckBox cbEdit;
    @BindView(R.id.llNormal)
    LinearLayout llNormal;
    @BindView(R.id.tvDelete)
    TextView tvDelete;
    @BindView(R.id.llFull)
    LinearLayout llFull;

    private CartProAdapter cartProAdapter;
    private CartProData data;
    private List<TestCartPro> testCartPros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cat);
        ButterKnife.bind(this);
        initData();

        cbCartPro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < testCartPros.size(); i++) {
                        testCartPros.get(i).setChecked(true);
                        cartProAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        cbCartPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbCartPro.isChecked()) {
                    for (int i = 0; i < testCartPros.size(); i++) {
                        testCartPros.get(i).setChecked(true);
                        cartProAdapter.notifyDataSetChanged();
                    }
                }else {
                    for (int i = 0; i < testCartPros.size(); i++) {
                        testCartPros.get(i).setChecked(false);
                        cartProAdapter.notifyDataSetChanged();
                    }
                }

            }
        });

        cbEdit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbEdit.setText("完成");
                    cbCartPro.setChecked(false);
                    for (int i = 0; i < testCartPros.size(); i++) {
                        testCartPros.get(i).setChecked(false);
                        cartProAdapter.notifyDataSetChanged();
                    }
                    llNormal.setVisibility(View.GONE);
                    tvDelete.setVisibility(View.VISIBLE);
                } else {
                    cbEdit.setText("编辑");
                    cbCartPro.setChecked(true);
                    llNormal.setVisibility(View.VISIBLE);
                    tvDelete.setVisibility(View.GONE);
                    cartProAdapter.notifyDataSetChanged();
                    getTotal();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNumber();
        if (testCartPros.size()==0) {
            llNoPro.setVisibility(View.VISIBLE);
            llFull.setVisibility(View.GONE);
            cbEdit.setVisibility(View.INVISIBLE);
        }else {
            llNoPro.setVisibility(View.GONE);
            llFull.setVisibility(View.VISIBLE);
            cbEdit.setVisibility(View.VISIBLE);
        }
        getTotal();
    }

    private void initData() {
        data = CartProData.getInstance();
        testCartPros = data.getTestCartPros();
        cartProAdapter = new CartProAdapter(testCartPros, this);
        cartProAdapter.setCallBack(this);
        lvCartPro.setAdapter(cartProAdapter);
    }

    @OnClick(R.id.ivBack)
    public void onIvBackClick(View v) {
        finish();
    }

    @OnClick(R.id.tvPayment)
    public void onTvPaymentClick(View v) {
        if (SignFragment.phoneNumber == null){
            Toast.makeText(ShoppingCatActivity.this, "小伙子，你还没登陆呢！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LargeActivity.class);
            intent.putExtra("key", 3);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        }

    }

    @OnClick(R.id.tvDelete)
    public void onTvDeleteClick(View v) {
        Toast.makeText(ShoppingCatActivity.this, "那么好吃的都不要嘛", Toast.LENGTH_SHORT).show();
        for (int i = 0; i < testCartPros.size(); i++) {
            if (testCartPros.get(i).isChecked()) {
                Log.d(TAG, "onTvDeleteClick: " + i + "/" + testCartPros.get(i).isChecked());
                testCartPros.remove(i);
                i = -1;
                continue;
            }
        }
        cartProAdapter.notifyDataSetChanged();
        if (testCartPros.size()==0) {
            llNoPro.setVisibility(View.VISIBLE);
            llFull.setVisibility(View.GONE);
            cbEdit.setVisibility(View.INVISIBLE);
        }

        getNumber();

    }

    @OnClick(R.id.tvGoShopping)
    public void onTvGoShoppingClick(View v) {
        Intent intent = new Intent(this, CommodityListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void dataChange() {
        getTotal();
        getNumber();
        setCBAll();
    }

    private void setCBAll() {
        for (int i = 0; i < testCartPros.size(); i++) {
            if (!testCartPros.get(i).isChecked()){
                cbCartPro.setChecked(false);
                return;
            }
        }
        cbCartPro.setChecked(true);
    }

    private void getTotal() {
        float total=0;
        for (int i = 0; i < testCartPros.size(); i++) {
            if (testCartPros.get(i).isChecked()){
                total+=testCartPros.get(i).getCurrentPrice()*testCartPros.get(i).getNumber();
            }
        }

        BigDecimal b = new BigDecimal(total);
        float f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
//        b.setScale(2, BigDecimal.ROUND_HALF_UP);//表明四舍五入，保留两位小数
        tvTotal.setText("¥"+f1+"");
    }

    private void getNumber() {
        int x=0;
        for (int i = 0; i < testCartPros.size(); i++) {
                x+=testCartPros.get(i).getNumber();
        }
        ProNumber.setText("共" + x + "件商品");
        int y=0;
        for (int i = 0; i < testCartPros.size(); i++) {
            if (testCartPros.get(i).isChecked()){
                y++;
            }
        }
        tvPayment.setText("结算(" + y + ")");
    }
}
