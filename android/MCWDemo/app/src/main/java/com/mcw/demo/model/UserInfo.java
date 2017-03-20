package com.mcw.demo.model;

/**
 * Created by racoon on 16/7/12.
 */

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.mcw.demo.BaseApplication;
import com.mcw.demo.DemoApplication;


/**
 * 用户数据
 */
public class UserInfo {

    private String id = "d17948d8-0802-11e7-93ae-92361f002671";
//    private String id = "d179431a-0802-11e7-93ae-92361f002671";
    private String userSig;
    private String token;


    private static UserInfo ourInstance;

    public static UserInfo getInstance() {
        if (ourInstance==null){
            synchronized (UserInfo.class){
                if (ourInstance == null){
                    SharedPreferences pref = DemoApplication.getInstance().getSharedPreferences("data", BaseApplication.MODE_PRIVATE);
                    String userInfoString = pref.getString("user-info",null);
                    if (userInfoString!=null&&!"".equals(userInfoString)){
                        ourInstance = new Gson().fromJson(userInfoString,UserInfo.class);
                    }else{
                        ourInstance = new UserInfo();
                    }
                }
            }
        }
        return ourInstance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (!this.id.equals(id)){
            this.id = id;
            updateInfo();
        }
    }

    public String getUserSig() {
        return userSig;
    }

    public void setUserSig(String userSig) {
        if (userSig!=null&&!"".equals(userSig)&&!userSig.equals(this.userSig)){
            this.userSig = userSig;
            updateInfo();
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        if (token!=null&&!"".equals(token)&&!token.equals(this.token)){
            this.token = token;
            updateInfo();
        }
    }

    private void updateInfo(){
        SharedPreferences pref = DemoApplication.getInstance().getSharedPreferences("data", BaseApplication.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String instance = this.ourInstance.toString();
        editor.putString("user-info", instance);
        editor.commit();
    }

    public void clear(){
        this.ourInstance = null;
        updateInfo();
    }

    @Override
    public String toString() {
        return "{\"id\":\""+getId()+"\"}";
    }
}