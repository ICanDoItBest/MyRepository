package com.fourapeteam.snacksmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fourapeteam.snacksmall.CommodityListActivity;
import com.fourapeteam.snacksmall.R;
import com.fourapeteam.snacksmall.bean.SpecialHeadBean;
import com.fourapeteam.snacksmall.bean.SpecialListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jun on 2016/10/31.
 */
public class SpecialListAdapter extends BaseAdapter {

    private final LayoutInflater layoutInflater;
    Context context;
    private List<SpecialHeadBean.DataBean.ItemsBean> items;
    private List<SpecialListBean.DataBean.ItemsBean> listItems;

    public SpecialListAdapter(List<SpecialListBean.DataBean.ItemsBean> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (listItems != null) {
            return listItems.size() + 1;
        }
        return 0;
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
        HeadHolder headHolder;
        ListHolder listHolder = null;
        if (convertView == null || position < 8) {
            convertView = layoutInflater.inflate(R.layout.item_special_list, parent, false);
            listHolder = new ListHolder(convertView);
            convertView.setTag(listHolder);
        } else {
            listHolder = (ListHolder) convertView.getTag();
        }

        if (position == 0) {
            View view = layoutInflater.inflate(R.layout.item_special_head, parent, false);
            headHolder = new HeadHolder(view);

            View[] ivProHeads = new View[]{headHolder.ivProHead1, headHolder.ivProHead2, headHolder.ivProHead3, headHolder.ivProHead4,};
            if (items != null) {
                for (int i = 0; i < 4; i++) {
                    Glide.with(context)
                            //支持动图
                            .load(Uri.parse(items.get(i).getImg().getImg_url()))
                            //占位符
                            // .placeholder(R.mipmap.icon_feedback_logo)
                            //.error(R.mipmap.icon_feedback_logo)
                            .centerCrop()
                            //缓存策略
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into((ImageView) ivProHeads[i]);
                }
            }

            headHolder.ivProHead1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CommodityListActivity.class);
                    intent.putExtra("listId",524);
                    context.startActivity(intent);
                }
            });
            headHolder.ivProHead2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CommodityListActivity.class);
                    intent.putExtra("listId",523);
                    context.startActivity(intent);
                }
            });
            headHolder.ivProHead3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CommodityListActivity.class);
                    intent.putExtra("listId",533);
                    context.startActivity(intent);
                }
            });
            headHolder.ivProHead4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CommodityListActivity.class);
                    intent.putExtra("listId",5);
                    context.startActivity(intent);
                }
            });

            return view;
        }
        String img_url = listItems.get(position - 1).getImg().getImg_url();

        try {
            Glide.with(context)
                    //支持动图
                    .load(Uri.parse(img_url))
                    //占位符
                    // .placeholder(R.mipmap.icon_feedback_logo)
                    // .error(R.mipmap.icon_feedback_logo)
                    .centerCrop()
                    //缓存策略
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(listHolder.iv);
            listHolder.tvLabel.setText(listItems.get(position - 1).getTitle());
            listHolder.tvHeartNumber.setText(listItems.get(position-1).getHotindex()+"");

    } catch (Exception e) {
        e.printStackTrace();
    }

        return convertView;
    }

    public void setHeadData(List<SpecialHeadBean.DataBean.ItemsBean> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setListData(List<SpecialListBean.DataBean.ItemsBean> listItems) {
        this.listItems = listItems;
        notifyDataSetChanged();
    }

    static class HeadHolder {
        @BindView(R.id.ivProHead1)
        ImageView ivProHead1;
        @BindView(R.id.ivProHead2)
        ImageView ivProHead2;
        @BindView(R.id.ivProHead3)
        ImageView ivProHead3;
        @BindView(R.id.ivProHead4)
        ImageView ivProHead4;

        HeadHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ListHolder {
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.tvLabel)
        TextView tvLabel;
        @BindView(R.id.tvHeartNumber)
        TextView tvHeartNumber;
        ListHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
