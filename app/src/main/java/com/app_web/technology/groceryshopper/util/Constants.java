package com.app_web.technology.groceryshopper.util;

import android.content.Context;
import android.os.Bundle;

import com.app_web.technology.groceryshopper.Database.DataBaseHelper;
import com.app_web.technology.groceryshopper.Database.Wishlistdatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tejveer on 4/10/2017.
 */
public class Constants {




 /*
    */
 public static  String dis;


 public static String totalAmount;
    public static String imei,charges;
    public static    boolean type=true;
    public static HashMap<String, String> addressMap;
    public  static  ArrayList<String> offerid;
    public  static  ArrayList<String> offersprice;
    public  static  String OfferPakagePrice;
    public  static String noOfProductInPackage;
    public  static  ArrayList<String> offersdis;
    public static boolean isUserLogIn=false;
    public static boolean salesrestricted=false;
    public static boolean agerestricted=true;
    public static String userData;
    public static String User_id="";
    public static int logincontrol;
    public static String country_id;
    public  static  String  responce;
    public  static  String  offeritemdata;
    public  static  String  sub_category;
    public  static  String  itemdata;
    public static  String   discription;
    public  static  String  postal;
    public  static  String   category;
    public  static  String  banners;
    public  static  String  time;
    public  static  String  messages;
    public  static  String  itemname="";
    public  static  String  itemhaddername="";
    public  static  String freeprice="";
    public static  String suboitems;
    public static String DeliveryCharge;
    public static String Instraction;
    public static String OrderDisabled;
    public static String status;
    public static String phoneNo;
    public static String SAND_BOX_ID;
    public static String PRODUCTION_ID;
    public  static String isTifinEnable;
    public static  String PAYPAL_CLIENT_ID = null;
    public static String OnlinePaymentEnable;
    public static String Paypal_id;
    public static boolean mode=true;
    public static String ShopOpenCloseStatus;

    public static String minimum_delivery_amount;
    public String getQuantity(Context context) {
        DataBaseHelper helper = new DataBaseHelper(context);
        Bundle bundle = helper.getItemQuantity();
        if (bundle.getInt("TotalItem") > 0) {
            return "" + bundle.getInt("TotalItem");
        } else {
            return "";
        }
    }
    public String singleitemquntity(Context context,String id) {
        DataBaseHelper helper = new DataBaseHelper(context);
        Bundle bundle = helper.singleitemquntity(id);
        if (bundle.getInt("TotalItem") > 0) {
            return "" + bundle.getInt("TotalItem");
        } else {
            return "0";
        }
    }
    public String singleitemquntitytime(Context context,String id) {
        DataBaseHelper helper = new DataBaseHelper(context);
        Bundle bundle = helper.singleitemtimerestrict();
        if (bundle.getInt("TotalItem") > 0) {
            return "" + bundle.getInt("TotalItem");
        } else {
            return "0";
        }
    }




    public String offersquanity(Context context,String offer_type,String offer_id) {
        DataBaseHelper helper = new DataBaseHelper(context);
        Bundle bundle = helper.offertype(offer_type,offer_id);
        if (bundle.getInt("TotalItem") > 0) {
            return "" + bundle.getInt("TotalItem");
        } else {
            return "";
        }
    }
    public static String paymentStatus = "offline";
    public static String Transaction_id = "";
    public String getQuantitywish(Context context) {
        Wishlistdatabase helper = new Wishlistdatabase(context);
        Bundle bundle = helper.getItemQuantity();
        if (bundle.getInt("TotalItem") > 0) {
            return "" + bundle.getInt("TotalItem");
        } else {
            return "";
        }
    }
}
