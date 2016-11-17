package com.fourapeteam.snacksmall.fragments.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fourapeteam.snacksmall.LargeActivity;
import com.fourapeteam.snacksmall.R;
import com.fourapeteam.snacksmall.bean.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by PC-20160823 on 2016/11/4.
 */
public class SignFragment extends Fragment {
    private static final String TAG = "test";
    public static String userName = null;
    public static String objectId = null;
    public static String phoneNumber = null;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.btLogin)
    Button btLogin;
    @BindView(R.id.tvRegister)
    TextView tvRegister;
    @BindView(R.id.etphoneNumber)
    EditText etphoneNumber;
    @BindView(R.id.etpassWord)
    EditText etpassWord;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_sign, container, false);
            ButterKnife.bind(this, view);
        }
        return view;
    }

    @OnClick(R.id.btLogin)
    public void onBtLoginClick(View view){
        if(!TextUtils.isEmpty(etphoneNumber.getText()) && !TextUtils.isEmpty(etpassWord.getText())){
            String phoneNumber = etphoneNumber.getText().toString();
            String passWord = etpassWord.getText().toString();
            queryRow(phoneNumber,passWord);
        }else{
            Toast.makeText(getContext(), "请填写信息完整", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.tvRegister)
    public void ontvRegister(View view) {
        Intent intent = new Intent(getActivity(), LargeActivity.class);
        intent.putExtra("key", 11);
        startActivity(intent);
    }


    @OnClick(R.id.ivBack)
    public void onivBackSettingClick(View view) {
        listener.onivBacSignClick();
    }

    //查询指定列
    public void queryRow( String phoneNumber,String passWord){
        //只返回Lost表的objectId这列的值
        final String pb = phoneNumber;
        final String pw = passWord;
        BmobQuery<User> bmobQuery = new BmobQuery<User>();
        bmobQuery.addQueryKeys("objectId");
        bmobQuery.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> object, BmobException e) {
                        if (e == null) {
                            Log.e(TAG, "object.size(): "+object.size());
                            for (int i = 0; i < object.size(); i++) {
                               queryUserInfo(pb,pw,object.get(i).getObjectId(),i,object.size()-1);
                            }
                        } else {
                            Toast.makeText(getActivity(), "查询失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void queryUserInfo(final String phoneNumber1, final String passWord, String objId, final int i, final int size){
        BmobQuery<User> query = new BmobQuery<User>();
        query.getObject(objId, new QueryListener<User>() {
            @Override
            public void done(User object, BmobException e) {
                if(e==null){
                    String phoneNum = object.getPhoneNumber();
                    String passWord1 = object.getPassWord();
                    if(phoneNumber1.equals(phoneNum)&&passWord.equals(passWord1)){
                        Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT).show();
                        //获得playerName的信息
                        userName = object.getUserName();
                        objectId = object.getObjectId();
                        phoneNumber = phoneNumber1;
                        Log.e(TAG, "userName: "+userName );
                        getActivity().finish();
                    }
                    if(i==size && phoneNumber1 != phoneNum&&passWord!=passWord1){
                        Toast.makeText(getActivity(), "登录失败", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }

        });
    }

    onCertificatesFragmentClickListener listener;

    public void setListener(onCertificatesFragmentClickListener listener) {
        this.listener = listener;
    }

    public interface onCertificatesFragmentClickListener {
        void onivBacSignClick();
    }
}
