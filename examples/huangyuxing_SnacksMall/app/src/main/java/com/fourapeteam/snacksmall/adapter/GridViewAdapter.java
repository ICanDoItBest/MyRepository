package com.fourapeteam.snacksmall.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fourapeteam.snacksmall.R;
import com.fourapeteam.snacksmall.bean.BaseProGridBean;
import com.fourapeteam.snacksmall.bean.TestCartPro;
import com.fourapeteam.snacksmall.datas.CartProData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by qf on 2016/10/31.
 */
public class GridViewAdapter extends BaseAdapter {
    private static final String TAG = "test";
    private final LayoutInflater inflater;
    Context context;
    List<BaseProGridBean> data;

    public void setData(List<BaseProGridBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public GridViewAdapter(Context context, List<BaseProGridBean> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (data ==null){
            return 0;
        }
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
    public View getView(final int position, View converView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (converView == null) {
            converView = inflater.inflate(R.layout.gridviewhot_item, viewGroup, false);
            holder = new ViewHolder(converView);
            converView.setTag(holder);
        }else {
            holder = (ViewHolder) converView.getTag();
        }

        if (data !=null){
            final BaseProGridBean proGridBean = data.get(position);
            final String img_url = proGridBean.getImg_url();
            Glide.with(context)
                    .load(img_url)
                    //缓存策略
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.CoverIV);
            final String title = proGridBean.getTitle();
            holder.tvInfo.setText(title);
            final double current = proGridBean.getCurrent();
            holder.tvCurrentprice.setText("¥"+ current);
            holder.tvOriginalprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            final double prime = proGridBean.getPrime();
            holder.tvOriginalprice.setText("¥"+prime);
            holder.ivaddcart.setOnClickListener(new View.OnClickListener() {
                private CartProData instance;

                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "加入购物车成功", Toast.LENGTH_SHORT).show();
                    int currentId = data.get(position).getId();
                    TestCartPro testCartPro = new TestCartPro( ((float)current),((float)prime),title,img_url, currentId);
                    instance = CartProData.getInstance();
                    instance.addTestCartPros(testCartPro);
                    if (callChange != null){
                        callChange.getCallChange();
                    }
                }
            });
        }
        return converView;
    }

    static class ViewHolder {
        @BindView(R.id.CoverIV)
        ImageView CoverIV;
        @BindView(R.id.tvInfo)
        TextView tvInfo;
        @BindView(R.id.tvCurrentprice)
        TextView tvCurrentprice;
        @BindView(R.id.tvOriginalprice)
        TextView tvOriginalprice;
        @BindView(R.id.ivaddcart)
        ImageView ivaddcart;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    CallChange callChange;
    public void setCallChange(CallChange callChange) {
        this.callChange = callChange;
    }
    public interface CallChange{
        void getCallChange();
    }

}
