package com.fourapeteam.snacksmall.util;

import com.fourapeteam.snacksmall.bean.CommodityListBean;
import com.fourapeteam.snacksmall.bean.HomeCommodityList;
import com.fourapeteam.snacksmall.bean.HomeItemBean;
import com.fourapeteam.snacksmall.bean.HotBean;
import com.fourapeteam.snacksmall.bean.MenuTypeBean;
import com.fourapeteam.snacksmall.bean.ProductDetailsBean;
import com.fourapeteam.snacksmall.bean.SpecialHeadBean;
import com.fourapeteam.snacksmall.bean.SpecialListBean;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by jun on 2016/10/8.
 */
public class JsonUtil {

    public static HomeCommodityList parseCommodityList(String json) {
        if("IOException".equals(json)){
            return null;
        }
        try {
            JSONObject jsonObject = new JSONObject(json);
            String rs_msg = jsonObject.getString("rs_msg");
            if ("success".equals(rs_msg)) {
                return new Gson().fromJson(json, HomeCommodityList.class);
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static HomeItemBean parseHomeItemBean(String json) {
        if("IOException".equals(json)){
            return null;
        }
        try {
            JSONObject jsonObject = new JSONObject(json);
            String rs_msg = jsonObject.getString("rs_msg");
            if ("success".equals(rs_msg)) {
                return new Gson().fromJson(json, HomeItemBean.class);
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 热卖
     * @param json
     * @return
     */
    public static HotBean parseHotQuery(String json){

        if("IOException".equals(json)){
            return null;
        }
        try {
            JSONObject jsonObject = new JSONObject(json);
            String rs_msg = jsonObject.getString("rs_msg");
            if ("success".equals(rs_msg)) {
                return new Gson().fromJson(json, HotBean.class);
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CommodityListBean parseCommodityListBean(String json){
        if("IOException".equals(json)){
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(json);
            String rs_msg = jsonObject.getString("rs_msg");
            if ("success".equals(rs_msg)) {
                return new Gson().fromJson(json, CommodityListBean.class);
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ProductDetailsBean parseProductDtailsQuery(String json){
        if("IOException".equals(json)){
            return null;
        }
        ProductDetailsBean productDetailsBean =new Gson().fromJson(json, ProductDetailsBean.class);
        return productDetailsBean;
    }
    public static MenuTypeBean parseMenuTypeBean(String json){
        if("IOException".equals(json)){
            return null;
        }
        MenuTypeBean menuTypeBean =new Gson().fromJson(json, MenuTypeBean.class);
        return menuTypeBean;
    }

    public static SpecialHeadBean parseSpecialHeadBean(String json){
        if("IOException".equals(json)){
            return null;
        }
        SpecialHeadBean specialHeadBean = new Gson().fromJson(json, SpecialHeadBean.class);
        return specialHeadBean;
    }

    public static SpecialListBean parseSpecialListBean(String json){
        if("IOException".equals(json)){
            return null;
        }
        SpecialListBean specialListBean = new Gson().fromJson(json, SpecialListBean.class);
        return specialListBean;
    }


   /* public static JokeTextBean parseJokeTextBean(String json) {
        if (!"IOException".equals(json)) {
            JokeTextBean jokeTextBean = new Gson().fromJson(json, JokeTextBean.class);
            return jokeTextBean;
        }
        return null;
    }*/

}
