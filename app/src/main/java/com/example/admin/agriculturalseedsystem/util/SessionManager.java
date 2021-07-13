package com.example.admin.agriculturalseedsystem.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String KEY_FULLNAME = "fullName";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONENUMBER = "phoneNumber";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_ADDRESS = "address";
    //private static final String KEY_USERNAME = "username";

    public SessionManager(Context _context){
        context = _context;
        userSession = _context.getSharedPreferences("userLoginSession",Context.MODE_PRIVATE);
        editor = userSession.edit();
    }

    public void createLoginSession(String fullname,String username,String email,String phoneno,String pass,String address){
        editor.putBoolean(IS_LOGIN,true);

        editor.putString(KEY_FULLNAME,fullname);
        editor.putString(KEY_USERNAME,username);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_PHONENUMBER,phoneno);
        editor.putString(KEY_PASSWORD,pass);
        editor.putString(KEY_ADDRESS,address);

        editor.commit();
    }

    public HashMap<String, String> getUserDetailFromSession(){
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_FULLNAME,userSession.getString(KEY_FULLNAME,null));
        userData.put(KEY_USERNAME,userSession.getString(KEY_USERNAME,null));
        userData.put(KEY_EMAIL,userSession.getString(KEY_EMAIL,null));
        userData.put(KEY_PHONENUMBER,userSession.getString(KEY_PHONENUMBER,null));
        userData.put(KEY_PASSWORD,userSession.getString(KEY_PASSWORD,null));
        userData.put(KEY_ADDRESS,userSession.getString(KEY_ADDRESS,null));

        return userData;
    }

    public boolean checkLogin(){
        return (userSession.getBoolean(IS_LOGIN,false));
    }

    public void logoutUserFromSession(){
        editor.clear();
        editor.commit();
    }
}
