package com.fourapeteam.snacksmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fourapeteam.snacksmall.ProductDetailsActivity;
import com.fourapeteam.snacksmall.R;
import com.fourapeteam.snacksmall.bean.BaseProGridBean;
import com.fourapeteam.snacksmall.bean.HomeCommodityList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/31.
 */
public class HomeListAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private Context context;
    private List<BaseProGridBean> data;
    private HomeCommodityList homeCommodityList;

    public void setData(List<BaseProGridBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setHomeCommodityList(HomeCommodityList homeCommodityList) {
        this.homeCommodityList = homeCommodityList;
        notifyDataSetChanged();
    }

    public HomeListAdapter(Context context, List<BaseProGridBean> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if(data == null){
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
            convertView = inflater.inflate(R.layout.item_commodity_home, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        if(data!=null) {
            if (position == 0) {
                holder.lltop.setVisibility(View.VISIBLE);
                holder.llbottom.setVisibility(View.GONE);
                if (homeCommodityList != null) {
                    holder.tvMainTitle.setText(homeCommodityList.getData().getNew_title_big());
                    holder.tvMainInfo.setText(homeCommodityList.getData().getNew_title_sml());
                    List<HomeCommodityList.DataBean.SpecialsBean> specials = homeCommodityList.getData().getSpecials();
                    Glide.with(context).load(specials.get(0).getImg().getImg_url()).into(holder.ivpic1);
                    Glide.with(context).load(specials.get(1).getImg().getImg_url()).into(holder.ivpic2);
                    Glide.with(context).load(specials.get(2).getImg().getImg_url()).into(holder.ivpic3);
                    Glide.with(context).load(specials.get(3).getImg().getImg_url()).into(holder.ivpic4);
                }
            } else {
                holder.llbottom.setVisibility(View.VISIBLE);
                holder.lltop.setVisibility(View.GONE);
                BaseProGridBean baseProGridBean = data.get(position-1);
                Glide.with(context).load(baseProGridBean.getImg_url()).into(holder.ivPic);
                holder.tvTitle.setText(baseProGridBean.getTitle());
                holder.tvPrice.setText("￥ " + baseProGridBean.getCurrent());
            }
            rlOnClick(holder);
        }
        return convertView;
    }

    private void rlOnClick(Holder holder) {
        final List<HomeCommodityList.DataBean.SpecialsBean> specials = homeCommodityList.getData().getSpecials();
        holder.rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                Log.e("test", "id: "+Integer.parseInt(specials.get(0).getAction().getInfo()));
                intent.putExtra("id",Integer.parseInt(specials.get(0).getAction().getInfo()));
                context.startActivity(intent);
            }
        });
        holder.rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("id",Integer.parseInt(specials.get(1).getAction().getInfo()));
                context.startActivity(intent);
            }
        });
        holder.rl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("id",Integer.parseInt(specials.get(2).getAction().getInfo()));
                context.startActivity(intent);
            }
        });
        holder.rl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("id",Integer.parseInt(specials.get(3).getAction().getInfo()));
                context.startActivity(intent);
            }
        });
    }

     class Holder {
        @BindView(R.id.ivpic1)
        ImageView ivpic1;
        @BindView(R.id.rl1)
        RelativeLayout rl1;
        @BindView(R.id.v1)
        View v1;
        @BindView(R.id.ivpic2)
        ImageView ivpic2;
        @BindView(R.id.rl2)
        RelativeLayout rl2;
        @BindView(R.id.v2)
        View v2;
        @BindView(R.id.ivpic3)
        ImageView ivpic3;
        @BindView(R.id.rl3)
        RelativeLayout rl3;
        @BindView(R.id.v3)
        View v3;
        @BindView(R.id.ivpic4)
        ImageView ivpic4;
        @BindView(R.id.rl4)
        RelativeLayout rl4;
        @BindView(R.id.rl)
        RelativeLayout rl;
        @BindView(R.id.tvMainpiv)
        TextView tvMainpiv;
        @BindView(R.id.tvMainTitle)
        TextView tvMainTitle;
        @BindView(R.id.tvMainInfo)
        TextView tvMainInfo;
        @BindView(R.id.lltop)
        LinearLayout lltop;
        @BindView(R.id.ivPic)
        ImageView ivPic;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.llbottom)
        LinearLayout llbottom;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
