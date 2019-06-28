package com.app_web.technology.groceryshopper.util;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app_web.technology.groceryshopper.Activitys.CheckOut;
import com.app_web.technology.groceryshopper.Activitys.DeliveryOption;
import com.app_web.technology.groceryshopper.Activitys.FirstMain;
import com.app_web.technology.groceryshopper.Activitys.Home;
import com.app_web.technology.groceryshopper.Activitys.ItemMainCotegory;
import com.app_web.technology.groceryshopper.Activitys.Items;
import com.app_web.technology.groceryshopper.Activitys.Login;
import com.app_web.technology.groceryshopper.Activitys.Myorder;
import com.app_web.technology.groceryshopper.Activitys.OfferItem;
import com.app_web.technology.groceryshopper.Activitys.OrderReviewPage;
import com.app_web.technology.groceryshopper.Activitys.Wishlistactivity;
import com.app_web.technology.groceryshopper.Addapter.AlbumsAdapter;
import com.app_web.technology.groceryshopper.Database.DataBaseHelper;
import com.app_web.technology.groceryshopper.Database.Wishlistdatabase;
import com.app_web.technology.groceryshopper.GetterSetter.ItemModel;
import com.app_web.technology.groceryshopper.MainActivity;
import com.app_web.technology.groceryshopper.Model.WishlistOrderItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

/**
 * Created by Rachit on 6/5/2017.
 */
public class VolleyMethods {
    private static final String TAG= VolleyMethods.class.getSimpleName();

    Context context;
    ProgressDialog pDialog;
 public  String wishlist_add_item_api="https://www.groceryshopper.info/apps_data/add_wishlist_ios_api.php";
 public  String wishlist_api="https://www.groceryshopper.info/apps_data/wishlist_list_android_api.php";
 public  String item_images_url="https://www.groceryshopper.info/apps_data/item_images/";
 public  String time_api= "https://www.groceryshopper.info/apps_data/check_time_for_sale.php";
 public  String offetitem="https://www.groceryshopper.info/apps_data/cart_api_android.php";
 public  String reset= "https://www.groceryshopper.info/apps_data/reset_pass_api.php";
 public  String login_new_api= "https://www.groceryshopper.info/apps_data/login_api.php";
 public  String sign_up= "https://www.groceryshopper.info/apps_data/register_api.php";
 //public  String order_complete_api="http://grocerydrop.co.uk/grocery_shopper/apps_data/order_complete_api.php";
   public  String order_complete_api="https://www.groceryshopper.info/apps_data/order_complete_api.php";

   // public  String order_complete_api="https://www.groceryshopper.info/apps_data/order_complete_ios_api.php";

    public  String postal_code= "https://www.groceryshopper.info/apps_data/check_postcode_api.php";
    public  String SubItemAPI= "https://www.groceryshopper.info/apps_data/sub_category.php";
    public  String items_api= "https://www.groceryshopper.info/apps_data/items_api.php";
    public  String search_api= "https://www.groceryshopper.info/apps_data/search_api.php";
    public  String ShopStatusAPI = "https://www.groceryshopper.info/apps_data/checkout_timing.php";

 public VolleyMethods(Context context) {
        this.context = context;
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
    }


    public void wishlistitem(final  String user_id) {
        final String tag_string_req = "string_req";
        String url =wishlist_api ;
        Constants.itemdata = new String("");
        pDialog.show();
        final StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG@123", response.toString());
              try{
                  String res=response.toString();
                  JSONArray jsonArray=new JSONArray(response.toString());
                for(int i = 0; i<jsonArray.length(); i++) {

                    JSONObject json = null;
                    try {
                        json = jsonArray.getJSONObject(i);
                        Wishlistdatabase dbHelper = new Wishlistdatabase(context);
                        WishlistOrderItemModel item = new WishlistOrderItemModel();
                        item.setItemId(json.getString("item_id"));
                        item.setItemQuantity("1");
                        item.setItemName(json.getString("item_name"));
                        item.setItemPrice(json.getString("price"));
                        item.setoffer_type(json.getString("offer_type"));
                        item.setoffer_typetwo(json.getString("OfferId"));
                        item.setoffer_typethree(json.getString("OfferDeatils"));
                        item.setItemParentCategory(json.getString("isTimeRistrictedItem"));
                        item.setoffer_complete(json.getString("basket_limit"));
                        item.setSize(json.getString("baseket_limit_msg"));
                        item.setSubItems(json.getString("out_of_stock"));
                        item.setItemBase(json.getString("restrict_sales"));
                        item.setItemBasePrice(json.getString("restrict_sales_msg"));
                        item.setItemBasePizzaIndex(json.getString("age_restriction"));
                        item.setBase(json.getString("age_restrict_msg"));
                        item.setID(json.getString("baseket_limit_msg"));
                        item.setFree_toppings(json.getString("basket_limit"));
                        item.setSpecial_instruction(json.getString("item_pic"));
                        item.setSpecialTips("0.0");
                        dbHelper.addItemToOrder(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                  ((Wishlistactivity)context).bindAdapter();
                  pDialog.hide();

              }catch (JSONException e){}



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Internet Connection Not Working Properly", Toast.LENGTH_SHORT).show();
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                pDialog.hide();
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                Pref pref= new Pref(context);
                params.put("user_id",pref.getUserId());
                Log.d("TAG@123", params.toString());
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    public  void getinfo(){
        String ShopStatus = "shop_status_status";
        String url = time_api;
        pDialog.show();
        Constants.time=new String("");
        Constants.messages=new String("");
        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG@123", response.toString());
                try {
                    JSONArray jsonArray=new JSONArray(response.toString());
                    Constants.time=jsonArray.get(0).toString();
                    Constants.messages=jsonArray.get(1).toString();
                    if(Constants.time.equals("0")){
                        Constants.salesrestricted=false;
                    }
                    ((CheckOut)context).bindAdapter();
                    pDialog.hide();
                }catch (Exception r)   {
                    pDialog.hide();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.hide();
                VolleyLog.d("TAG", "Error: " + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(strReq, ShopStatus);
    }
    public void resetpasword(final Map<String,String> userRegisterData) {

        String user_register_req = "user_tablebooking_request";
        String url = reset;
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", response.toString());


                try {
                    JSONObject jObj=new JSONObject(response);
                    String responce=jObj.getString("response");
                    String mes=jObj.getString("msg");
                    if(jObj.getString("response").equals("1")){
                        if(context instanceof Login){
                            String d=jObj.getString("msg");
                            ((Login)context).popup(d);
                        }

                    }else{
                        if(context instanceof Login){
                            String d=jObj.getString("msg");
                            ((Login)context).popuptext(d);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                String error1=error.getMessage();
                Toast.makeText(context, "Internet Connection Not Working Properly", Toast.LENGTH_SHORT).show();
                Toast.makeText(context,"Error: " + error.getMessage(),Toast.LENGTH_LONG).show();
                pDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", userRegisterData.get("User_Email"));

                return params;
            }
        };


        AppController.getInstance().addToRequestQueue(strReq, user_register_req);
    }
    public void postalcode(final Map<String,String> userRegisterData,final  String j) {
        String user_register_req = "user_tablebooking_request";
        String url = postal_code;
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", response.toString());


                try {
                    JSONObject jObj=new JSONObject(response);
                    String responce=jObj.getString("response");
                    String mes=jObj.getString("msg");
                    if(jObj.getString("response").equals("1"))
                    {
                        Constants.DeliveryCharge= jObj.getString("Delivery_Charge");

                        if(j.equals("0")){
                            Constants.type=false;
                            Intent in=new Intent(context,Home.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(in);
                            Toast.makeText(context, mes, Toast.LENGTH_SHORT).show();
                        }
                        else {
                             Constants.type=false;
                            ((DeliveryOption)context).popup(jObj.getString("msg").toString(),jObj.getString("response").toString());
                        }

                        /*Intent in=new Intent(context,Home.class);
                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(in);
                        Toast.makeText(context, mes, Toast.LENGTH_SHORT).show();*/

                    }

                    else{
                       // ((DeliveryOption)context).popup(jObj.getString("msg").toString(),jObj.getString("response").toString());
                        Toast.makeText(context, mes, Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                String error1=error.getMessage();
                Toast.makeText(context, "Internet Connection Not Working Properly", Toast.LENGTH_SHORT).show();
                Toast.makeText(context,"Error: " + error.getMessage(),Toast.LENGTH_LONG).show();
                pDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("postcode", userRegisterData.get("postcode"));

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, user_register_req);
    }

    //Method to get Response of special Cases
    public void getSpecialCases(final  String number, final String country_id) {
        // Tag used to cancel the request
        final String tag_string_req = "string_req";
        String url = SubItemAPI;
        Constants.sub_category = new String("");
        pDialog.show();
        final StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG@123", response.toString());

                String res=response.toString();
                if (!response.toString().equals("")) {
                    pDialog.hide();

                    if(response.equals("null")||response.equals("[]")){
                        Constants.sub_category=response.toString();
                        Log.d(TAG,"0000000000"+Constants.sub_category);
                        Intent in = new Intent(context, FirstMain.class);
                        in.putExtra("data","N");
                        in.putExtra("country_id",country_id);
                        context.startActivity(in);
                    }else {
                        Constants.sub_category=response.toString();
                        Log.d(TAG,"222222222222222"+Constants.sub_category);
                        Intent in = new Intent(context, FirstMain.class);
                        in.putExtra("data","Y");
                        in.putExtra("country_id",country_id);
                        context.startActivity(in);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Internet Connection Not Working Properly", Toast.LENGTH_SHORT).show();
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                pDialog.hide();
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cat_id", number);
               // params.put("country_id", country_id);
                String d=params.toString();
                Log.d("TAG@123", params.toString());
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    public void search(final  String item) {
        // Tag used to cancel the request
        final String tag_string_req = "string_req";
        String url =search_api ;
        Constants.itemdata = new String("");
        pDialog.show();
        final StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG@123", response.toString());

                String res=response.toString();
                if (!response.toString().equals("")) {
                    pDialog.hide();

                    if(response.equals("null")||response.equals("[]")){
                        Constants.itemdata=response.toString();
                        Intent in = new Intent(context, Items.class);
                        in.putExtra("data","N");
                        context.startActivity(in);
                    }else {
                        Constants.itemdata=response.toString();
                        Intent in = new Intent(context, Items.class);
                        in.putExtra("data", "Y");
                        context.startActivity(in);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Internet Connection Not Working Properly", Toast.LENGTH_SHORT).show();
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                pDialog.hide();
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("search", item);
                Log.d("TAG@123", params.toString());
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void sub_cat_id(final  String cat_id,final  String Subcat_id, final String country_id) {
        // Tag used to cancel the request
        final String tag_string_req = "string_req";
        String url =items_api ;
        Constants.itemdata = new String("");
        pDialog.show();
        final StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG@123", response.toString());

                String res=response.toString();
                if (!response.toString().equals("")) {
                    pDialog.hide();

                    if(response.equals("null")||response.equals("[]")){
                        Constants.itemdata=response.toString();
                        Intent in = new Intent(context, Items.class);
                        in.putExtra("data","N");
                        context.startActivity(in);
                    }else {
                        Constants.itemdata=response.toString();
                        Intent in = new Intent(context, Items.class);
                        in.putExtra("data", "Y");
                        context.startActivity(in);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Internet Connection Not Working Properly", Toast.LENGTH_SHORT).show();
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                pDialog.hide();
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                if(country_id.equals("")){
                    params.put("cat_id", cat_id);
                    params.put("sub_cat_id", Subcat_id);
                }else {
                    params.put("country_id", country_id);
                }


                Log.d("TAG@123", params.toString());
                String d=params.toString();
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void mainitem(final  String Country_id,final  String name ) {
        // Tag used to cancel the request
        final String tag_string_req = "string_req";
        String url ="https://www.groceryshopper.info/apps_data/category.php";
        Constants.category=  new String("");
        pDialog.show();
        final StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG@123", response.toString());
                if (response!=null) {
                    Constants.category=response.toString();
                    Log.d(TAG,"111111111111111111"+Constants.category);
                    pDialog.hide();
                    Intent in = new Intent(context, ItemMainCotegory.class);
                    in.putExtra("name",name);
                    context.startActivity(in);

                }
                else {
                    Toast.makeText(context, "Internet Connection Not Working Properly", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Internet Connection Not Working Properly", Toast.LENGTH_SHORT).show();
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                pDialog.hide();
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("country_id", Country_id);
                Log.d("TAG@123", params.toString());
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }



    public void offeritem(final  String offer_type,final  String OfferId,int i) {
        // Tag used to cancel the request
        final String tag_string_req = "string_req";
        String url =offetitem;
        Constants.offeritemdata=  new String("");
        pDialog.show();
        final StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG@123", response.toString());
                if (!response.toString().equals("")) {
                    Constants.offeritemdata=response.toString();
                    pDialog.hide();
                    Intent in = new Intent(context, OfferItem.class);
                    context.startActivity(in);




                }
                else {
                    Toast.makeText(context, "Internet Connection Not Working Properly", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Internet Connection Not Working Properly", Toast.LENGTH_SHORT).show();
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                pDialog.hide();
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("offer_type", offer_type);
                params.put("OfferId", OfferId);
                Log.d("TAG@123", params.toString());
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void registerUser(final Map<String,String> userRegisterData) {
        // Tag used to cancel the request
        pDialog.show();
        String user_register_req = "user_register_request";
        String url = sign_up;
//        ItemActivity.itemList=new ArrayList<ItemModel>();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG@123", response.toString());

                //Code to Handle Response
                try {
                    JSONObject jObj=new JSONObject(response);
                    if(jObj.getString("response").equals("1")){
                        Toast.makeText(context,jObj.getString("msg"),Toast.LENGTH_LONG).show();
                        Intent in=new Intent(context,Login.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        context.startActivity(in);
                    }else{
                        //Registration Not complete Response
                        Toast.makeText(context,jObj.getString("msg"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Internet Connection Not Working Properly", Toast.LENGTH_SHORT).show();
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                pDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("full_name", userRegisterData.get("full_name"));
                params.put("mail_id", userRegisterData.get("mail_id"));
                params.put("password", userRegisterData.get("password"));
                params.put("contact_no", userRegisterData.get("contact_no"));
                params.put("house_no", userRegisterData.get("house_no"));
                params.put("street", userRegisterData.get("street"));
                params.put("city", userRegisterData.get("city"));
                params.put("post", userRegisterData.get("post"));
                Log.d("TAG@123", params.toString());

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, user_register_req);
    }
    public void changepasswword(final Map<String,String> userRegisterData) {

        String user_register_req = "user_tablebooking_request";
        String url = reset;
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG@123", response.toString());
                try {
                    JSONObject jObj=new JSONObject(response);
                    String responce=jObj.getString("response");
                    String mes=jObj.getString("msg");
                    if(jObj.getString("response").equals("1")){
                        if(context instanceof Login){
                            String d=jObj.getString("msg");
                            ((Login)context).popuptext(d);
                        }

                    }else{
                        if(context instanceof Login){
                            String d=jObj.getString("msg");
                            Login.otp="";
                            ((Login)context).popup(d);


                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                String error1=error.getMessage();
                Toast.makeText(context, "Internet Connection Not Working Properly", Toast.LENGTH_SHORT).show();
                Toast.makeText(context,"Error: " + error.getMessage(),Toast.LENGTH_LONG).show();
                pDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("otp", userRegisterData.get("User_otp"));
                params.put("password", userRegisterData.get("User_passo"));
                String t=params.toString();
                Log.d("TAG@123", t);
                return params;
            }
        };


        AppController.getInstance().addToRequestQueue(strReq, user_register_req);
    }
    public void userLogIn(final Map<String,String> userLogInData) {
        // Tag used to cancel the request
        String user_log_in_req = "login_req";
        String url = login_new_api;
//        ItemActivity.itemList=new ArrayList<ItemModel>();
        Constants.userData=new String("");
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG@123", response.toString());

                //Code To Handle Response
                try {
                    JSONObject jsonObject=new JSONObject(response.toString());
                    if(jsonObject.getString("response").toString().equals("1")){
                            Constants.userData=jsonObject.getString("address").toString();
                            Log.d("TAG@123", Constants.userData);
                            JSONObject json=new JSONObject(Constants.userData);

                            Pref pref= new Pref(context);
                            pref.setISuserLogin("1");

                              pref.setUserId(json.getString("user_id"));
                            SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editor = shared.edit();
                            editor.putString("Address",  Constants.userData);
                            editor.commit();
                            Constants.isUserLogIn=true;
                        if(Constants.logincontrol==0){
                            Intent in=new Intent(context, Home.class);
                            context.startActivity(in);
                        }else if(Constants.logincontrol==1) {
                            Intent in=new Intent(context, CheckOut.class);
                            context.startActivity(in);
                        }
                        else if(Constants.logincontrol==2){
                            Intent in=new Intent(context, Wishlistactivity.class);
                            context.startActivity(in);
                        }
                        /*else {
                            Intent in=new Intent(context, Myorder.class);
                            context.startActivity(in);
                        }*/


                    }else{
                        //If Mail Id Password is not Verified from Server
                        Toast.makeText(context,jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VolleyM:userLogIn", "Error: " + error.getMessage());
                pDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("MailId", userLogInData.get("MailId"));
                params.put("Password", userLogInData.get("Password"));
                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, user_log_in_req);
    }



    public void placeOrder(final String deliveryMethod, final String paymentMethod,final String time,final String tablenumber) {
        String tag_string_req = "string_req";
        String url = order_complete_api;
        pDialog.show();
        OrderInfo order = new OrderInfo(context);
        DataBaseHelper dbHelper = new DataBaseHelper(context);
        Bundle bundle = dbHelper.getItemQuantity();
        JSONObject js = new JSONObject();
        JSONObject jobj = new JSONObject();

        try {



            jobj.put("Address",order.getAddress());

            String s= deliveryMethod;

            jobj.put("DeliveryType", deliveryMethod);

            if(deliveryMethod.equals("C"))
            {
                jobj.put("shipping_cost","0");



            }
            else
            {
                jobj.put("shipping_cost",Constants.DeliveryCharge);



            }
            jobj.put("GrandTotal", "" +bundle.getString("TotalPrice"));
            jobj.put("items", order.getOrder());
            jobj.put("PaymentType", paymentMethod);
            jobj.put("Time", "Button");
            jobj.put("transaction_id",Constants.Transaction_id);
            js.put("json_data", jobj);
            Log.d("TAG@123", js.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest postRequest = new JsonObjectRequest( Request.Method.POST, url,

                js, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("TAG@123", response.toString());
                        completeAction(response.toString());
                        pDialog.hide();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("TAG@123", "Error: " + error.getMessage());
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            ((OrderReviewPage)context).popuptext1("This indicates that the reuest has either time out or there is no connection");
                        } else if (error instanceof AuthFailureError) {
                            ((OrderReviewPage)context).popuptext1("Error indicating that there was an Authentication Failure while performing the request");
                        } else if (error instanceof ServerError) {
                            ((OrderReviewPage)context).popuptext1("Indicates that the server responded with a error response");
                        } else if (error instanceof NetworkError) {
                            ((OrderReviewPage)context).popuptext1("Indicates that there was network error while performing the request");
                        } else if (error instanceof ParseError) {
                            ((OrderReviewPage)context).popuptext1("Indicates that the server response could not be parsed");}
                        else {
                            ((OrderReviewPage)context).popuptext1("Oops! Please Try Again. Server Error ");
                        }
                        pDialog.hide();
                    }
                })

        {
          /*  @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept","application/json");
                headers.put("Content-Type","application/json");
                return headers;
            }*/

        };

        AppController.getInstance().addToRequestQueue(postRequest, tag_string_req);
    }















    //Method to Place Order


/*
    public void placeOrder(final String deliveryMethod, final String paymentMethod,final String time,final String tablenumber) {
        String tag_string_req = "string_req";
        String url = order_complete_api;
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG@123", response.toString());
                completeAction(response.toString());
                pDialog.hide();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                VolleyLog.d("TAG@123", "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    ((OrderReviewPage)context).popuptext1("This indicates that the reuest has either time out or there is no connection");
                } else if (error instanceof AuthFailureError) {
                    ((OrderReviewPage)context).popuptext1("Error indicating that there was an Authentication Failure while performing the request");
                } else if (error instanceof ServerError) {
                    ((OrderReviewPage)context).popuptext1("Indicates that the server responded with a error response");
                } else if (error instanceof NetworkError) {
                    ((OrderReviewPage)context).popuptext1("Indicates that there was network error while performing the request");
                } else if (error instanceof ParseError) {
                    ((OrderReviewPage)context).popuptext1("Indicates that the server response could not be parsed");}
                else {
                    ((OrderReviewPage)context).popuptext1("Oops! Please Try Again. Server Error ");
                }



                pDialog.hide();
            }
        }) {






            @Override
            protected Map<String, String> getParams() {
                DataBaseHelper dbHelper = new DataBaseHelper(context);
                Bundle bundle = dbHelper.getItemQuantity();
                OrderInfo order = new OrderInfo(context);
                Map<String, String> params = new HashMap<String, String>();
                params.put("GrandTotal", "" + bundle.getString("TotalPrice"));
                params.put("DeliveryType", deliveryMethod);
                params.put("PaymentType", paymentMethod);
                params.put("transaction_id",Constants.Transaction_id);
                params.put("Status",Constants.paymentStatus);
                params.put("Time", time);
                params.put("Address",order.getAddress().toString());
                params.put("Items", order.getOrder().toString());
                params.put("table_number",tablenumber);
                String data=params.toString();
                Log.d("TAG@123", data);
                return params;

            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
*/


    public void completeAction(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            Log.d("TAG@123", response);

           // ((OrderReviewPage)context).popuptext1(jsonResponse.getString("msg").toString());
           if (jsonResponse.getString("response").toString().equals("1")) {
                Constants.type=true;
                Constants.agerestricted=false;
                DataBaseHelper dbHelper = new DataBaseHelper(context);
                dbHelper.deleteOrder();
                Pref pref= new Pref(context);
                pref.clear();
                Wishlistdatabase wishlistdatabase=new Wishlistdatabase(context);
                wishlistdatabase.deleteOrder();
                Constants cons = new Constants();
                ((OrderReviewPage)context).popuptext1(jsonResponse.getString("msg").toString());
            } else {
                ((OrderReviewPage)context).popuptext1(jsonResponse.getString("msg").toString());

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public void update(final String AppName,final String version) {

        String tag_string_req = "update";
        pDialog.show();
        String url ="http://www.satyamtechnologies.co.uk/Dashboard/apps_data/check_app_version_api.php";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,"TAG@123:"+response);
                String op=response;


                try{
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.getString("IsHardStop").toString().equals("0"))
                    {
                        pDialog.hide();
                        if(jsonResponse.getString("maintenance").toString().equals("0"))
                        {



                            Constants.minimum_delivery_amount= jsonResponse.getString("minimum_delivery_amount");
                            Constants.phoneNo=  jsonResponse.getString("phone_number");
                            Constants.SAND_BOX_ID= jsonResponse.getString("sendBoxId");
                            Constants.PRODUCTION_ID= jsonResponse.getString("productionId");
                            Constants.isTifinEnable= jsonResponse.getString("isPayPalEnvironmentProduction");
                            Constants.OnlinePaymentEnable= jsonResponse.getString("OnlinePaymentEnable");


                            if(jsonResponse.getString("isPayPalEnvironmentProduction").equals("1"))
                            {
                                Constants.Paypal_id= jsonResponse.getString("productionId");

                            }
                            else
                            {
                                Constants.mode=false;
                                Constants.Paypal_id= "AUgrvDW4858xrcFHQCC6LDYF4Fkf63S7VNsUOGVLPCZrV3ZELJZKoiTWgKTkT-LySbfS103cGdBR5bwJ";

                            }



                     /*       if(parm.length()==0){
                                Constants.isUserLogIn=false;
                                context.startActivity(new Intent(context, Home.class));
                            }
                            else {
                                Constants.userData=parm;
                                Constants.isUserLogIn=true;
                                context.startActivity(new Intent(context, Home.class));
                            }*/



                        }else {

                            ((MainActivity)context).maintenance();

                        }


                    }
                    else {
                        pDialog.hide();


                        if(jsonResponse.getString("maintenance").toString().equals("0")){

                            String PlayStoreVersion=jsonResponse.getString("PlayStoreVersion").toString();
                          //  ((MainActivity)context).cartitemsheet(PlayStoreVersion);

                        }

                        else {
                            ((MainActivity)context).maintenance();
                        }

                    }
                }catch (JSONException e){

                }



                pDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                pDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("AppName", AppName);
                params.put("version", version);
                params.put("os", "Android");
                String dd=params.toString();
                Log.d("TAG", dd);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    public void getStoreStatus() {
      //  pDialog.show();
        String ShopStatus = "shop_status_status";
        String url = ShopStatusAPI;
        Constants.dis=new String("");
        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("TAG@123", response.toString());
                try {
                     Pref pref= new Pref(context);

                    JSONObject jsonResponse = new JSONObject(response);

                   Constants.ShopOpenCloseStatus= jsonResponse.getString("response");

                    Constants.time= jsonResponse.getString("checkInTime");
                    DeliveryOption.checkInTime = jsonResponse.getString("checkInTime");
                    Home.checkInTimeHome=jsonResponse.getString("checkInTime");
                    DeliveryOption.checkOutTime = jsonResponse.getString("checkOutTime");
                    DeliveryOption.CollectionCycle = jsonResponse.getString("CollectionCycle");
                    DeliveryOption.DeliveryCycle = jsonResponse.getString("DeliveryCycle");
                    Constants.dis= jsonResponse.getString("discount").toString();
                    CheckOut.shopStatus = "1";
                    Constants.OrderDisabled=jsonResponse.getString("OrderDisabled");
                    Constants.status = jsonResponse.getString("response");

                  //  pDialog.hide();

                }catch (JSONException r)   {
                  //  pDialog.hide();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.hide();
                VolleyLog.d("TAG", "Error: " + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(strReq, ShopStatus);
    }











}
