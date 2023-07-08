package com.example.group5_project.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {

    public static String getDecodedBody(String JWTEncoded) throws Exception {
        try {
            String[] split = JWTEncoded.split("\\.");
            return getJson(split[1]);
        } catch (UnsupportedEncodingException e) {
            //Error
        }
        return null;
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException{
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }

    public static String getHeaderAuthorization(Context context){
        SharedPreferences mPrefs = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        String access_token = mPrefs.getString("access_token", "defvalue");
        String header_authorization = "Bearer " + access_token;
        return header_authorization;
    }

    public static String getRoleFromJsonToken(String jsonToken){
        Gson gson = new Gson();
        Map<String, Object> retMap = gson.fromJson(
                jsonToken, new TypeToken<HashMap<String, Object>>() {}.getType()
        );
        Log.d("JWT_DECODED", "ROLE: " + retMap.get("http://schemas.microsoft.com/ws/2008/06/identity/claims/role").toString());
        return retMap.get("http://schemas.microsoft.com/ws/2008/06/identity/claims/role").toString();
    }

    public static void clearToken(Context context){
        SharedPreferences mPrefs = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        mPrefs.edit().remove("access_token").commit();

    }
}
