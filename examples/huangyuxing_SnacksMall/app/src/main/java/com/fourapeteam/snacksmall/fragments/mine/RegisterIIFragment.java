package com.fourapeteam.snacksmall.fragments.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fourapeteam.snacksmall.R;
import com.fourapeteam.snacksmall.bean.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by PC-20160823 on 2016/11/5.
 */
public class RegisterIIFragment extends Fragment {
    private static final String TAG = "test";
    @BindView(R.id.etuserName)
    EditText etuserName;
    @BindView(R.id.etEmil)
    EditText etEmil;
    @BindView(R.id.etphoneNumber)
    EditText etphoneNumber;
    @BindView(R.id.etpassWord)
    EditText etpassWord;
    @BindView(R.id.etVpassWord)
    EditText etVpassWord;
    @BindView(R.id.tvRegister)
    Button tvRegister;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_registerii, container, false);
            ButterKnife.bind(this, view);
        }
        return view;
    }

    @OnClick(R.id.tvRegister)
    public void onBtnRegisterClick(View view){
        String userName = etuserName.getText().toString();
        String passWord = etpassWord.getText().toString();
        String etVpassWord = this.etVpassWord.getText().toString();
        String Emil = etEmil.getText().toString();
        String phoneNumber = etphoneNumber.getText().toString();
        if(userName.trim().length() != 0&&passWord.trim().length() != 0&&etVpassWord.trim().length() != 0&&Emil.trim().length() != 0&&phoneNumber.trim().length() != 0){
            User p2 = new User(userName,passWord,Emil,phoneNumber);
            p2.save(new SaveListener<String>() {
                @Override
                public void done(String objectId,BmobException e) {
                    if(e==null){
                        Toast.makeText(getContext(), "注册成功", Toast.LENGTH_SHORT).show();
                        Log.e(TAG,"添加数据成功，返回objectId为："+objectId );
                        getActivity().finish();
                    }else{
                        Toast.makeText(getContext(), "注册失败，是否已经注册过了？", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(getContext(), "请填写完整", Toast.LENGTH_SHORT).show();
        }
    }
}
