package com.fourapeteam.snacksmall.datas;

import android.util.Log;

import com.fourapeteam.snacksmall.bean.TestCartPro;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jun on 2016/11/2.
 */
public class CartProData {


    private static final String TAG = "juntest";
    private static CartProData ourInstance = new CartProData();

    public static CartProData getInstance() {
        return ourInstance;
    }

    private CartProData() {
    }
    static List<TestCartPro> testCartPros = new ArrayList<>();

    static {
       /* String url="http://img.lingshi.cccwei.com/lingshi/3ec/ec/c/a440cf62eec6d1382de39b062d8e83ec.jpeg";
        for (int i = 0; i < 10; i++) {
            testCartPros.add(new TestCartPro(10.0f+i,20.0f+i,"[美国纷时乐]蜂蜜烤碧根果仁美式风味"+i, url));
        }*/
    }

    public List<TestCartPro> getTestCartPros() {
        return testCartPros;
    }

    public void setTestCartPros(List<TestCartPro> testCartPros) {
        this.testCartPros = testCartPros;
    }

    public void addTestCartPros(TestCartPro testCartPro){
        Log.d(TAG, "testCartPro="+testCartPro);
        Log.d(TAG, "testCartPro="+testCartPro.getId());
        if (testCartPros!=null){
            for (int i = 0; i < testCartPros.size(); i++) {
                if (testCartPros.get(i).getId()==testCartPro.getId()){
                    Log.d(TAG, "addTestCartPros:"+testCartPros.get(i).getId());
                    testCartPros.get(i).setNumber(testCartPros.get(i).getNumber()+1);
                    return;
                }
            }
        }
        testCartPros.add(testCartPro);
    }

}
