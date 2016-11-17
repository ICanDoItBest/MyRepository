package com.fourapeteam.snacksmall.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.fourapeteam.snacksmall.LargeActivity;
import com.fourapeteam.snacksmall.R;
import com.fourapeteam.snacksmall.SettingActivity;
import com.fourapeteam.snacksmall.ShoppingCatActivity;
import com.fourapeteam.snacksmall.datas.CartProData;
import com.fourapeteam.snacksmall.fragments.mine.SignFragment;
import com.fourapeteam.snacksmall.zxing.activity.CaptureActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jun on 2016/10/31.
 */
public class MineFragment extends Fragment {

    private static final String TAG = "test";
    @BindView(R.id.ivSetting)
    TextView ivSetting;
    @BindView(R.id.tvShoppingCar)
    TextView tvShoppingCar;
    @BindView(R.id.llLogin)
    LinearLayout llLogin;
    @BindView(R.id.rbMonye)
    RadioButton rbMonye;
    @BindView(R.id.rbSendgoods)
    RadioButton rbSendgoods;
    @BindView(R.id.rbForgoods)
    RadioButton rbForgoods;
    @BindView(R.id.rbEvaluation)
    RadioButton rbEvaluation;
    @BindView(R.id.llRev)
    LinearLayout llRev;
    @BindView(R.id.llCertificates)
    LinearLayout llCertificates;
    @BindView(R.id.llSamukura)
    LinearLayout llSamukura;
    @BindView(R.id.llClothing)
    LinearLayout llClothing;
    @BindView(R.id.llPinion)
    LinearLayout llPinion;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.tvHotBasketNumber)
    TextView tvHotBasketNumber;
    @BindView(R.id.tvDL)
    TextView tvDL;
    @BindView(R.id.tvUSName)
    TextView tvUSName;
    @BindView(R.id.llSYS)
    LinearLayout llSYS;
    private View view;
    private CartProData cartProData;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_mine, container, false);
            ButterKnife.bind(this, view);
            cartProData = CartProData.getInstance();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (SignFragment.phoneNumber == null) {
            tvDL.setVisibility(View.VISIBLE);
            tvUSName.setVisibility(View.GONE);
        } else {
            tvUSName.setText(SignFragment.userName);
            tvDL.setVisibility(View.GONE);
            tvUSName.setVisibility(View.VISIBLE);
        }

        int number = 0;
        int size = cartProData.getTestCartPros().size();
        for (int i = 0; i < size; i++) {
            number += cartProData.getTestCartPros().get(i).getNumber();
        }
        if (size == 0) {
            tvHotBasketNumber.setVisibility(View.GONE);
        } else {
            tvHotBasketNumber.setVisibility(View.VISIBLE);
        }
        tvHotBasketNumber.setText(number + "");
    }

    //点击扫一扫
    @OnClick(R.id.llSYS)
    public void onLlSYSClick(View view) {
        startActivityForResult(new Intent(getActivity(), CaptureActivity.class), 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == -1) {
            String result = intent.getExtras().getString("result");
            String substring = result.substring(0, 4);
            if ("http".equals(substring)) {
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_VIEW);
                intent1.setData(Uri.parse(result));
                startActivity(intent1);
            } else {
                dolog(result);
            }
        }
    }

    private void dolog(String result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("得到了字符串")
                .setMessage(result)
                .create().show();
    }

    //跳到购物车
    @OnClick(R.id.tvShoppingCar)
    public void OnClickShopp(View v) {
        Intent intent = new Intent(getActivity(), ShoppingCatActivity.class);
        startActivity(intent);
    }

    //跳到订单
    @OnClick(R.id.rbMonye)
    public void OnClickrbMonye(View v) {
        Intent intent = new Intent(getActivity(), SettingActivity.class);
        int key = 1;
        intent.putExtra("key", key);
        startActivity(intent);
    }

    //跳到订单
    @OnClick(R.id.rbSendgoods)
    public void OnClickbrbSendgoods(View v) {
        Intent intent = new Intent(getActivity(), SettingActivity.class);
        int key = 2;
        intent.putExtra("key", key);
        startActivity(intent);
    }

    //跳到订单
    @OnClick(R.id.rbForgoods)
    public void OnClickbrbForgoods(View v) {
        Intent intent = new Intent(getActivity(), SettingActivity.class);
        int key = 3;
        intent.putExtra("key", key);
        startActivity(intent);
    }

    //跳到订单
    @OnClick(R.id.rbEvaluation)
    public void OnClickrbEvaluation(View v) {
        Intent intent = new Intent(getActivity(), SettingActivity.class);
        int key = 4;
        intent.putExtra("key", key);
        startActivity(intent);
    }

    //跳到订单
    @OnClick(R.id.llRev)
    public void OnClickllRev(View v) {
        Intent intent = new Intent(getActivity(), SettingActivity.class);
        int key = 0;
        intent.putExtra("key", key);
        startActivity(intent);
    }

    //跳到我的设置
    @OnClick(R.id.ivSetting)
    public void OnClickSetting(View v) {
        Intent intent = new Intent(getActivity(), LargeActivity.class);
        intent.putExtra("key", 0);
        startActivity(intent);
    }

    //跳到我的优惠卷
    @OnClick(R.id.llCertificates)
    public void OnClickllCertificates(View v) {
        Intent intent = new Intent(getActivity(), LargeActivity.class);
        intent.putExtra("key", 1);
        startActivity(intent);
    }

    //跳到我的收藏
    @OnClick(R.id.llSamukura)
    public void OnClickllSamukura(View v) {
        Intent intent = new Intent(getActivity(), LargeActivity.class);
        intent.putExtra("key", 2);
        startActivity(intent);
    }

    //跳到登录
    @OnClick(R.id.tvDL)
    public void OnClickllLogin(View v) {
        Intent intent = new Intent(getActivity(), LargeActivity.class);
        intent.putExtra("key", 3);
        startActivity(intent);
    }

    @OnClick(R.id.llClothing)
    public void OnClickllClothing(View v) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_dialog, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setNegativeButton("拨打电话", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:18475453284"));
                        startActivity(intent);
                    }
                })
                .setPositiveButton("发送邮箱", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("mailto:563076584@qq.com"));
                        startActivity(intent);
                    }
                })
                .create()
                .show();

    }

    //意见
    @OnClick(R.id.llPinion)
    public void OnClickllPinion(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("smsto:18475453284"));
        startActivity(intent);
    }
}
