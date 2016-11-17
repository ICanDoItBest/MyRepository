package com.fourapeteam.snacksmall.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fourapeteam.snacksmall.R;
import com.fourapeteam.snacksmall.bean.TestCartPro;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PC-20160823 on 2016/11/6.
 */
public class TradingAdapter extends BaseAdapter {

    private  LayoutInflater layoutInflater;
    List<TestCartPro> data;
    Context context;
    boolean aBoolean;
    public TradingAdapter(List<TestCartPro> data, Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_trading, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=((ViewHolder) convertView.getTag());
        }

        final TestCartPro testCartPro = data.get(position);


        if (!aBoolean&&testCartPro.getUrl()!=null){
            Glide.with(context)
                    //支持动图
                    .load(Uri.parse(testCartPro.getUrl()))
                    //占位符
                    .placeholder(R.mipmap.icon_feedback_logo)
                    .error(R.mipmap.icon_feedback_logo)
                    //大小，scaleType
//                .override(300,300)
                    // .fitCenter()
                    .centerCrop()
                    //缓存策略
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.ivCartPro);

        }
        viewHolder.tvTitle.setText(testCartPro.getTitle());
        viewHolder.tvTaste.setText("口味"+testCartPro.getTaste());
        viewHolder.tvCurrentPrice.setText("¥"+testCartPro.getCurrentPrice());
        viewHolder.tvOldPrice.setText("¥"+testCartPro.getOldPrice());
        viewHolder.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.tvNumber.setText("*"+testCartPro.getNumber());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.ivCartPro)
        ImageView ivCartPro;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvTaste)
        TextView tvTaste;
        @BindView(R.id.tvCurrentPrice)
        TextView tvCurrentPrice;
        @BindView(R.id.tvOldPrice)
        TextView tvOldPrice;
        @BindView(R.id.tvNumber)
        TextView tvNumber;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
