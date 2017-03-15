package com.mcw.demo.api;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.mcw.demo.BaseApplication;
import com.mcw.demo.DemoApplication;

/**
 * Created by racoon on 16/8/23.
 */
public class NetworkConfigManager {
    private static NetworkConfigManager mInstance;
    private String refreshToken;
    private String rootUrl;


    public static NetworkConfigManager getInstance() {
        if (mInstance==null){
            synchronized (NetworkConfigManager.class){
                if (mInstance == null){
                    SharedPreferences pref = DemoApplication.getInstance().getSharedPreferences("data", BaseApplication.MODE_PRIVATE);
                    String networkConfig = pref.getString("network-config",null);
                    if (networkConfig!=null&&!"".equals(networkConfig)){
                        mInstance = new Gson().fromJson(networkConfig,NetworkConfigManager.class);
                    }else{
                        mInstance = new NetworkConfigManager();
                    }
                }
            }
        }
        return mInstance;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        if (refreshToken!=null&&!"".equals(refreshToken)&&!refreshToken.equals(this.refreshToken)){
            this.refreshToken = refreshToken;
            updateInfo();
        }
    }

    public String getRootUrl() {
        return rootUrl;
    }

    public void setRootUrl(String rootUrl) {
        if (rootUrl!=null&&!"".equals(rootUrl)&&!rootUrl.equals(this.rootUrl)){
            this.rootUrl = rootUrl;
            updateInfo();
        }
    }

    private void updateInfo(){
        SharedPreferences pref = DemoApplication.getInstance().getSharedPreferences("data", BaseApplication.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String instance = this.mInstance.toString();
        editor.putString("network-config", instance);
        editor.commit();
    }

    public void clear(){
        this.mInstance = null;
        updateInfo();
    }

    @Override
    public String toString() {
        return "{\"refreshToken\":"+getRefreshToken()+",\"rootUrl\":\""+getRootUrl()+"\"}";
    }
}
