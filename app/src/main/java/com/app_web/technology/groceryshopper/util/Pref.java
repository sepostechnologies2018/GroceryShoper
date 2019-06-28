package com.app_web.technology.groceryshopper.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;

import static com.app_web.technology.groceryshopper.Activitys.DeliveryOption.checkInTime;

/**
 * Created by Seo on 29-12-2017.
 */

public class Pref {

    private static final String TAG= Pref.class.getSimpleName();

    private static String PREF_NAME="GroceryShoper";
    private Context context;
    private int PRIVATE_MODE=0;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static String USER_ID="userId";
    private static String POST_CODE="postcode";
    private static String INSTRUCATION="instrucation";
    private static String IS_CREDIT_CARD="iscreditcard";
    private static String CHECK_IN_TIME="checkInTime";
    private static String ISuserLogin="isUserLogin";


    public Pref(Context context) {
        this.context = context;

        sharedPreferences= context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor= sharedPreferences.edit();
    }

    public void setISuserLogin(String loginUser)
    {
        editor.putString(ISuserLogin,loginUser);
        editor.commit();
    }


    public void setUserId(String userId)
    {
        editor.putString(USER_ID,userId);
        editor.commit();
    }

    public void setPostCode(String postcode)
    {
        editor.putString(POST_CODE,postcode);
        editor.commit();
    }

    public void setINSTRUCATION(String instrucation)
    {
        editor.putString(INSTRUCATION,instrucation);
        editor.commit();
    }

    public void setIsCreditCard(boolean iscreditCard)
    {
        editor.putBoolean(IS_CREDIT_CARD,iscreditCard);
        editor.commit();
    }

    public void setCheckInTime(String checktime)
    {
        editor.putString(checkInTime,checktime);
        editor.commit();
    }


    public String getUserId()
    {
        return sharedPreferences.getString(USER_ID,null);
    }

  public String getPostCode()
  {
      return sharedPreferences.getString(POST_CODE,null);
  }

  public String getINSTRUCATION()
  {
       return sharedPreferences.getString(INSTRUCATION, null);
  }

  public boolean getIsCreditCard()
  {
     return sharedPreferences.getBoolean(IS_CREDIT_CARD,false);
  }

  public String getCheckInTime()
  {
      return sharedPreferences.getString(CHECK_IN_TIME,null);
  }

  public String getISuserLogin()
  {
      return sharedPreferences.getString(ISuserLogin,"0");
  }

    public void clear()
    {
        editor.clear();
        editor.commit();
    }




}
