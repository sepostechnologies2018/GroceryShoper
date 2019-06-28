package com.app_web.technology.groceryshopper.Activitys;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app_web.technology.groceryshopper.R;
import com.app_web.technology.groceryshopper.util.AppController;
import com.app_web.technology.groceryshopper.util.Constants;
import org.json.JSONException;
import org.json.JSONObject;
public class Contact extends AppCompatActivity {
    WebView viewInMap;
    ImageView imageView;
    LinearLayout wishlist,shop_cart,search,contact_us,more,notification,home,back;
    TextView txtWebsite,txt_item_quantity,address,email,phone,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        getSupportActionBar().hide();
        back=(LinearLayout)findViewById(R.id.menusub);
        name=(TextView)findViewById(R.id.name);
        name.setText("Contact Us");
        address=(TextView)findViewById(R.id.addres);
        email=(TextView)findViewById(R.id.email);
        phone=(TextView)findViewById(R.id.phone);
        imageView=(ImageView)findViewById(R.id.speec);
        imageView.setVisibility(View.GONE);
        search=(LinearLayout)findViewById(R.id.search);
        shop_cart=(LinearLayout)findViewById(R.id.shop_cart);
        wishlist=(LinearLayout)findViewById(R.id.wishlist);
        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Constants.isUserLogIn){
                    Intent intent = new Intent(Contact.this, Wishlistactivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }else {
                    Constants.logincontrol=2;
                    Intent intent = new Intent(Contact.this, Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }



            }
        });
        viewInMap = (WebView) findViewById(R.id.web_view_on_map1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        viewInMap.getSettings().setJavaScriptEnabled(true);
        viewInMap.setWebViewClient(new WebViewClient());
        viewInMap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        getdata();
    }


    public  void getdata() {

        {
            String CategoryRequest = "CategoryRequest";
            String url = "https://www.groceryshopper.info/apps_data/contact_ios_api.php";
            final ProgressDialog progressDialog = new ProgressDialog(Contact.this);
            progressDialog.setMessage("Loading");
            progressDialog.show();
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d("TAG@123", "Response: " + response.toString());
                    if (response != null) {

                        try {
                            progressDialog.dismiss();
                            JSONObject  jsonObject=response.getJSONObject("success");
                            String address=jsonObject.getString("address");
                            String email=jsonObject.getString("email");
                            String map_url=jsonObject.getString("map_url");
                            String phone=jsonObject.getString("phone");
                            setdata(address,email,phone,map_url);
                        } catch (JSONException e) {
                           setdata("","","","");

                        }


                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    VolleyLog.d("TAG@123", "Error: " + error.getMessage());
                }
            });


            AppController.getInstance().addToRequestQueue(jsonReq);
        }
    }

    public  void  setdata(String addressg,String  emailu,String phonenu,String map_url){
        Typeface face1= Typeface.createFromAsset(Contact.this.getAssets(), "fonts/aaa.ttf");

        viewInMap.loadUrl(map_url);
        phone.setText(phonenu);
        address.setText(addressg);
        email.setText(emailu);
        phone.setTypeface(face1);
        address.setTypeface(face1);
        email.setTypeface(face1);
    }

}
