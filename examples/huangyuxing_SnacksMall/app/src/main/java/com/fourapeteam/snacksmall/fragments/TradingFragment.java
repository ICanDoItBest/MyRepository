package com.fourapeteam.snacksmall.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fourapeteam.snacksmall.CommodityListActivity;
import com.fourapeteam.snacksmall.R;
import com.fourapeteam.snacksmall.adapter.TradingAdapter;
import com.fourapeteam.snacksmall.bean.TestCartPro;
import com.fourapeteam.snacksmall.datas.CartProData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by PC-20160823 on 2016/11/2.
 */
public class TradingFragment extends Fragment {

    @BindView(R.id.tvGoShopping)
    TextView tvGoShopping;
    @BindView(R.id.llNoPro)
    LinearLayout llNoPro;
    @BindView(R.id.lvCartPro)
    ListView lvCartPro;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.llNormal)
    LinearLayout llNormal;
    @BindView(R.id.llFull)
    LinearLayout llFull;
    @BindView(R.id.tvPayment)
    TextView tvPayment;
    private View view;
    private CartProData data;
    private List<TestCartPro> testCartPros;
    private List<TestCartPro> myTestCartPros=new ArrayList<>();

    private TradingAdapter tradingAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_trading, container, false);
            ButterKnife.bind(this, view);
        }

        initData();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (testCartPros.size() == 0) {
            llNoPro.setVisibility(View.VISIBLE);
            llFull.setVisibility(View.GONE);
        } else {
            llNoPro.setVisibility(View.GONE);
            llFull.setVisibility(View.VISIBLE);
        }
        getTotal();
        tradingAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        myTestCartPros.clear();
    }

    private void initData() {

        data = CartProData.getInstance();
        testCartPros = data.getTestCartPros();

//        myTestCartPros=new ArrayList<>();
        for (int i = 0; i < testCartPros.size(); i++) {
           myTestCartPros.add(testCartPros.get(i));
        }

        for (int i = 0; i < myTestCartPros.size(); i++) {
            if (!myTestCartPros.get(i).isChecked()){
                myTestCartPros.remove(myTestCartPros.get(i));
                i--;
                continue;
            }
        }

        tradingAdapter = new TradingAdapter(myTestCartPros, getActivity());
        lvCartPro.setAdapter(tradingAdapter);

    }

    @OnClick(R.id.tvPayment)
    public void onTvPaymentClick(View v) {
        Toast.makeText(getActivity(), "還沒有錢買", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.tvGoShopping)
    public void onTvGoShoppingClick(View v) {
        startActivity(new Intent(getActivity(), CommodityListActivity.class));
    }


    private void getTotal() {
        float total = 0;
        for (int i = 0; i < testCartPros.size(); i++) {
            if (testCartPros.get(i).isChecked()) {
                total += testCartPros.get(i).getCurrentPrice() * testCartPros.get(i).getNumber();
            }
        }

        BigDecimal b = new BigDecimal(total);
        float f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
//        b.setScale(2, BigDecimal.ROUND_HALF_UP);//表明四舍五入，保留两位小数
        tvTotal.setText("¥" + f1 + "");
    }

}
