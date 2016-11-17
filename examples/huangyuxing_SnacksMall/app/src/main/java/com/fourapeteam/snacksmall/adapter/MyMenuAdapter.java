package com.fourapeteam.snacksmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.fourapeteam.snacksmall.R;
import com.fourapeteam.snacksmall.bean.HomeMenuBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/1.
 */
public class MyMenuAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private Context context;
    private List<HomeMenuBean> data;

    public void setData(List<HomeMenuBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public MyMenuAdapter(Context context, List<HomeMenuBean> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_menu, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        if (data != null) {
            HomeMenuBean homeMenuBean = data.get(position);
            Glide.with(context).load(homeMenuBean.getImg_url()).into(holder.sdvTypePic);
            holder.tvTypeName.setText(homeMenuBean.getTitle());
            holder.tvTypes.setText(homeMenuBean.getDesc());
        }
        return convertView;
    }

    static class Holder {
        @BindView(R.id.sdvTypePic)
        SimpleDraweeView sdvTypePic;
        @BindView(R.id.tvTypeName)
        TextView tvTypeName;
        @BindView(R.id.tvTypes)
        TextView tvTypes;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
