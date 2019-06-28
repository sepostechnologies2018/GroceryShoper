package com.app_web.technology.groceryshopper.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.app_web.technology.groceryshopper.Addapter.Myorderaddapter;
import com.app_web.technology.groceryshopper.Fragments.Contactus;
import com.app_web.technology.groceryshopper.Fragments.Notification;
import com.app_web.technology.groceryshopper.GetterSetter.myordergettersetter;
import com.app_web.technology.groceryshopper.R;
import com.app_web.technology.groceryshopper.util.AppController;
import com.app_web.technology.groceryshopper.util.Constants;
import com.app_web.technology.groceryshopper.util.Pref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Myorder extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG= Myorder.class.getCanonicalName();
    LinearLayout wishlist,shop_cart,search,contact_us,more,notification,home,menu;
    LinearLayout back,order_items,main_recycle;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    ProgressDialog pDialog;
    RecyclerView.Adapter recyclerViewadapter;
    public TextView txt_item_quantity,wishtext,name;
    List<myordergettersetter> feedItems;
    ImageView imageView;
    Pref pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);
        getSupportActionBar().hide();
        pref= new Pref(this);
        pDialog = new ProgressDialog(Myorder.this);
        init();
        more.setOnClickListener(this);
        back.setOnClickListener(this);
        wishlist.setOnClickListener(this);
        home.setOnClickListener(this);
        shop_cart.setOnClickListener(this);
        fillUserData();
    }



    public void fillUserData(){
        try {
            JSONObject jObj=new JSONObject(Constants.userData);
            JSON_DATA_WEB_CALL(pref.getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void JSON_DATA_WEB_CALL(final  String number) {

        final String tag_string_req = "string_req";
        String url = "https://www.groceryshopper.info/apps_data/my_orders.php";
        pDialog.setTitle("loading...");
        pDialog.show();

        final StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG@123", response.toString());
                pDialog.dismiss();

                if (!response.toString().equals("")&&!response.toString().equals("null")) {

                    feedItems=new ArrayList<myordergettersetter>();

                    try{
                        JSONArray jsonArray=new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++){
                            myordergettersetter myordergettersetter=new myordergettersetter();
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            myordergettersetter.setamount(jsonObject.getString("amount"));
                            myordergettersetter.setDelivery_Address(jsonObject.getJSONObject("Delivery_Address").toString());
                            myordergettersetter.setdelivery_note(jsonObject.getString("delivery_note"));
                            myordergettersetter.setdelivery_type(jsonObject.getString("delivery_type"));
                            myordergettersetter.setdiscount(jsonObject.getString("discount"));
                            myordergettersetter.setemail(jsonObject.getString("email"));
                            myordergettersetter.setfull_name(jsonObject.getString("full_name"));
                            myordergettersetter.setItems(jsonObject.getJSONArray("Items").toString());
                            myordergettersetter.setnet_amount(jsonObject.getString("net_amount"));
                            myordergettersetter.setphone(jsonObject.getString("phone"));
                            myordergettersetter.setshipping_cost(jsonObject.getString("shipping_cost"));
                            myordergettersetter.settrans_id(jsonObject.getString("trans_id"));
                            myordergettersetter.settrans_on(jsonObject.getString("trans_on"));
                            myordergettersetter.settrans_status(jsonObject.getString("trans_status"));
                            myordergettersetter.settrans_type(jsonObject.getString("trans_type"));
                            myordergettersetter.setuser_id(jsonObject.getString("user_id"));
                            feedItems.add(myordergettersetter);
                        }
                        recyclerViewadapter = new Myorderaddapter(feedItems, Myorder.this);
                        recyclerView.setAdapter(recyclerViewadapter);

                    }catch(JSONException e){

                        order_items.setVisibility(View.VISIBLE);
                        main_recycle.setVisibility(View.GONE);

                    }

                }
                else {
                 //   pDialog.hide();
                    order_items.setVisibility(View.VISIBLE);
                    main_recycle.setVisibility(View.GONE);
                    Toast.makeText(Myorder.this, "Empty Found", Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               Log.d(TAG,"ERORRRRRR:"+ error.getMessage());
                Toast.makeText(Myorder.this, "Internet Connection Not Working Properly", Toast.LENGTH_SHORT).show();
                VolleyLog.d("TAG", "Error: " + error.getMessage());
               // pDialog.hide();
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",pref.getUserId());
                String d=params.toString();
                Log.d("TAG@123", d);
                return params;
            }
        };
        strReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shop_cart: {

                Intent intent = new Intent(Myorder.this, CheckOut.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            }
            break;

            case R.id.wishlist: {



                if(Constants.isUserLogIn){
                    Intent intent = new Intent(Myorder.this, Wishlistactivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                }else {


                    Constants.logincontrol=2;
                    Intent intent = new Intent(Myorder.this, Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }

            }
            break;

            case R.id.contact_us: {
                Intent intent = new Intent(Myorder.this, Contact.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
            break;

            case R.id.more: {
              /*  Intent intent = new Intent(Myorder.this, AboutUs.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, R.string.app_name);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "click here to visit  https://play.google.com/store/apps/details?id=com.app_web.technology.groceryshopper");
                startActivity(Intent.createChooser(sharingIntent, "Share via"));


            }
            break;
            case R.id.notification: {




            }
            break;
            case R.id.home: {
                Intent intent = new Intent(Myorder.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            break;
            case R.id.menusub: {
                Intent intent = new Intent(Myorder.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
            break;

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Constants cons = new Constants();
        String i=cons.getQuantity(this);
        if(i.equals("")){
            txt_item_quantity.setVisibility(View.GONE);
        }
        else {
            txt_item_quantity.setText(cons.getQuantity(this));}
        String j=cons.getQuantitywish(this);
        if(j.equals("")){
            wishtext.setVisibility(View.GONE);
        }
        else {
            wishtext.setText(j);}

    }
    public void init(){
        name=(TextView)findViewById(R.id.name);
        shop_cart=(LinearLayout)findViewById(R.id.shop_cart);
        wishlist=(LinearLayout)findViewById(R.id.wishlist);
        search=(LinearLayout)findViewById(R.id.search);
        more=(LinearLayout)findViewById(R.id.more);
        notification=(LinearLayout)findViewById(R.id.notification);
        home=(LinearLayout)findViewById(R.id.home);
        menu=(LinearLayout)findViewById(R.id.menu);
        txt_item_quantity=(TextView)findViewById(R.id.txt_item_quantity);
        back=(LinearLayout)findViewById(R.id.menusub);
        wishtext=(TextView)findViewById(R.id.wishquanity);
        imageView=(ImageView)findViewById(R.id.speec);
        imageView.setVisibility(View.GONE);
        order_items=(LinearLayout)findViewById(R.id.order_items);
        order_items.setVisibility(View.GONE);
        main_recycle=(LinearLayout)findViewById(R.id.main_recycle);
        main_recycle.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) findViewById(R.id.order);
        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        name.setText("My Orders");
    }
}
