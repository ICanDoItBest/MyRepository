package com.fourapeteam.snacksmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fourapeteam.snacksmall.ProductDetailsActivity;
import com.fourapeteam.snacksmall.R;
import com.fourapeteam.snacksmall.bean.TestCartPro;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jun on 2016/11/2.
 */
public class CartProAdapter extends BaseAdapter {

    private final LayoutInflater layoutInflater;
    List<TestCartPro> data;
    Context context;
    boolean aBoolean;
    private AlertDialog dialog;
    public CartProAdapter(List<TestCartPro> data, Context context) {
        this.data = data;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_cart_pro, parent, false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        final TestCartPro cartPro = data.get(position);


        if (!aBoolean&&cartPro.getUrl()!=null){
            Glide.with(context)
                    //支持动图
                    .load(Uri.parse(cartPro.getUrl()))
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

       // Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_feedback_logo);
       // viewHolder.ivCartPro.setImageBitmap(bitmap);
        viewHolder.tvCurrentPrice.setText("¥"+cartPro.getCurrentPrice());
        viewHolder.tvOldPrice.setText("¥"+cartPro.getOldPrice());
        viewHolder.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.tvTitle.setText(cartPro.getTitle());
        viewHolder.tvTaste.setText("口味:"+cartPro.getTaste());
        viewHolder.tvNumber.setText(cartPro.getNumber()+"");
        if (cartPro.isChecked()){
            viewHolder.cbCartPro.setChecked(true);
        }else {
            viewHolder.cbCartPro.setChecked(false);
        }

        viewHolder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartPro.setNumber(cartPro.getNumber()+1);
                aBoolean=true;
               notifyDataSetChanged();
                if (callBack!=null){
                    callBack.dataChange();
                }

            }
        });

        viewHolder.tvReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).getNumber()>1){
                    data.get(position).setNumber(data.get(position).getNumber()-1);
                    aBoolean=true;
                    notifyDataSetChanged();
                }else{
                    Toast.makeText(context, "再少还买个鸡吧", Toast.LENGTH_SHORT).show();
                }
                if (callBack!=null){
                    callBack.dataChange();
                }
            }
        });

        viewHolder.tvNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int number = cartPro.getNumber();
                View contentView = LayoutInflater.from(context).inflate(R.layout.content_alert, null, false);
                TextView tvAdd2 = (TextView) contentView.findViewById(R.id.tvAdd2);
                TextView tvReduce2 = (TextView) contentView.findViewById(R.id.tvReduce2);
                final EditText etIn = (EditText) contentView.findViewById(R.id.etIn);
                TextView tvNo = (TextView) contentView.findViewById(R.id.tvNo);
                TextView tvYes = (TextView) contentView.findViewById(R.id.tvYes);
                etIn.setText( number+"");

                tvNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cartPro.setNumber(number);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                tvYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str = etIn.getText().toString();
                        if ("".equals(str)){
                            Toast.makeText(context, "输入有误！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        int number = Integer.parseInt(str);
                      /*  if (number<1){
                            Toast.makeText(context, "输入有误！", Toast.LENGTH_SHORT).show();
                            return;
                        }*/
                        cartPro.setNumber(number);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                tvAdd2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cartPro.setNumber(cartPro.getNumber()+1);
                        etIn.setText( cartPro.getNumber()+"");
                        notifyDataSetChanged();
                    }
                });

                tvReduce2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (data.get(position).getNumber()>1){
                            data.get(position).setNumber(data.get(position).getNumber()-1);
                            aBoolean=true;
                            notifyDataSetChanged();
                        }else{
                            Toast.makeText(context, "再少还买个鸡吧", Toast.LENGTH_SHORT).show();
                        }
                        etIn.setText( cartPro.getNumber()+"");
                    }
                });


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                dialog = builder
                        .setView(contentView)
                        .create();
                dialog.show();
            }
        });

        viewHolder.cbCartPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.cbCartPro.isChecked()){
                    cartPro.setChecked(true);
                }else {
                    cartPro.setChecked(false);
                }
                aBoolean=true;
                notifyDataSetChanged();

                if (callBack!=null){
                    callBack.dataChange();
                }

            }
        });

        viewHolder.ivCartPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                int id = cartPro.getId();
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });

        if (callBack!=null){
            callBack.dataChange();
        }

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.cbCartPro)
        CheckBox cbCartPro;
        @BindView(R.id.ivCartPro)
        ImageView ivCartPro;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvTaste)
        TextView tvTaste;
        @BindView(R.id.tvReduce)
        TextView tvReduce;
        @BindView(R.id.tvAdd)
        TextView tvAdd;
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

    CallBack callBack;
    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack{
        void dataChange();
    }
}
