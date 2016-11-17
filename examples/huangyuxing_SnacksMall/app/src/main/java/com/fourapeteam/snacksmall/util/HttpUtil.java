package com.fourapeteam.snacksmall.util;

import android.content.Context;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jun on 2016/10/8.
 */
public class HttpUtil {

    public static final String APP_SIGN = "709ec01514ae4d4a954d17bd406bd594";
    public static final int APP_ID = 25282;

    /**
     * 热卖
     * @return
     */
    public static String getHotJosn(Context context){
        String json = getStringByOkhttp("http://api.ds.lingshi.cccwei.com/?cid=830847&uid=200703&tms=20161102170642&sig=d826f34c88014010&wssig=692f796997ca633a&os_type=3&version=20&channel_name=xiaomi&srv=2301&since_id=0&pg_cur=1&pg_size=20",context);
        return json;
    }

    public static String getProductDetailsJson(String good_Id,Context context){

        String json = getStringByOkhttp("http://ds.lingshi.cccwei.com/api.php?apptype=0&srv=2502&cid=10002&uid=200703&tms=20150713151836&sig=e9f75a3bdae950ea&wssig=9de6f856100a21ab&os_type=3&version=20&opt=1&add_id=8&goods_id=" + good_Id,context);
        return json;
    }

    /*
    * 专题
    * */
    public static String getSpecialHead(Context context) {
        String url = "http://api.ds.lingshi.cccwei.com/?cid=831066&uid=0&tms=20161103111530&sig=28d4f126e8563dca&wssig=aa8f6d26818f6cf7&os_type=3&version=18&channel_name=feibo&srv=2401";
        return getStringByOkhttp(url,context);
    }

    public static String getSpecialList(Context context,int pag) {
        String url = "http://api.ds.lingshi.cccwei.com/?cid=831066&uid=0&tms=20161103140452&sig=a2de774b6a537bec&wssig=cca189726b0a6861&os_type=3&version=18&channel_name=feibo&srv=2405&pg_cur="+pag+"&pg_size=20&since_id=0";
        return getStringByOkhttp(url,context);
    }


    /*public static String getTops(String topId) {
        String url = "https://route.showapi.com/213-4?showapi_appid=" + APP_ID + "&topid=" + topId + "&showapi_sign=" + APP_SIGN;
        return getStringByOkhttp(url);
    }*/


    public static String getStringByOkhttp(String urlstr,Context context) {
        String json = "";

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
               .connectTimeout(10, TimeUnit.SECONDS)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Response response = chain.proceed(request).newBuilder()
                                .removeHeader("Pragma")
                                .removeHeader("Cache-Control")
                                .header("Cache-Control", "max-age=" + 3600 * 24 * 7)
                                .build();
                        return response;
                    }
                })
                .cache(new Cache(context.getExternalCacheDir(),10240*1024))
                .build();
         Request request = new Request.Builder()
                 .url(urlstr)
                 .tag("snack")
                 .build();

        try {
            Response execute = okHttpClient.newCall(request).execute();
            if (execute.isSuccessful()){
                json=execute.body().string();
            }else {
                json = "response not successful";
            }

        } catch (IOException e) {
            e.printStackTrace();
            json = "IOException";
        }
        return json;
    }

}
