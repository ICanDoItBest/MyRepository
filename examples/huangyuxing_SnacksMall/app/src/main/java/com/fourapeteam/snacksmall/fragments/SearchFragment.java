package com.fourapeteam.snacksmall.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.fourapeteam.snacksmall.MallActivity;
import com.fourapeteam.snacksmall.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by Administrator on 2016/11/1.
 */
public class SearchFragment extends Fragment {

    @BindView(R.id.tvBack)
    TextView tvBack;
    @BindView(R.id.etSearchLove)
    EditText etSearchLove;
    @BindView(R.id.resetOrSearch)
    TextView resetOrSearch;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_search, container, false);
            ButterKnife.bind(this, view);
        }
        return view;
    }

    //点击回退
    @OnClick(R.id.tvBack)
    public void onTvBackClick(View view) {
        startActivity(new Intent(getActivity(), MallActivity.class));
    }

    //搜索框
    @OnTextChanged(R.id.etSearchLove)
    public void etSearchLoveAfterTextChanged(Editable s) {
        if (s.length() > 0) {
            resetOrSearch.setText("搜索");
        } else {
            resetOrSearch.setText("取消");
        }
    }

    //点击搜索
    @OnClick(R.id.resetOrSearch)
    public void onResetOrSearchClick(View view) {
        String key = etSearchLove.getText().toString();
        if ("搜索".equals(resetOrSearch.getText().toString())) {
            if (listener != null) {
                Log.e("test", "key: "+key);
                listener.onTvSearchClick(key);
            }
        } else if ("取消".equals(resetOrSearch.getText().toString())) {
            startActivity(new Intent(getActivity(), MallActivity.class));
        }
    }

    OnSearchFragmentClickListener listener;

    public void setListener(OnSearchFragmentClickListener listener) {
        this.listener = listener;
    }

    public interface OnSearchFragmentClickListener {
        void onTvSearchClick(String key);
    }
}
