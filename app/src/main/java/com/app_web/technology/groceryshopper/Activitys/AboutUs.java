package com.app_web.technology.groceryshopper.Activitys;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.app_web.technology.groceryshopper.R;
import com.app_web.technology.groceryshopper.util.AppController;

import org.json.JSONException;
import org.json.JSONObject;

public class AboutUs extends AppCompatActivity {
    LinearLayout wishlist,shop_cart,search,contact_us,more,notification,home,back;
    TextView content,txt_item_quantity,name;
    NetworkImageView img;
    ImageLoader imageLoader;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getSupportActionBar().hide();
        back=(LinearLayout)findViewById(R.id.menusub);
        search=(LinearLayout)findViewById(R.id.search);
        name=(TextView)findViewById(R.id.name);
        name.setText("About Us");
        shop_cart=(LinearLayout)findViewById(R.id.shop_cart);
        wishlist=(LinearLayout)findViewById(R.id.wishlist);
        content=(TextView)findViewById(R.id.content);
        img=(NetworkImageView)findViewById(R.id.profilePic) ;
        imageView=(ImageView)findViewById(R.id.speec);
        imageView.setVisibility(View.GONE);
        wishlist.setVisibility(View.GONE);
        getdata();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
    }




    public  void getdata() {

        {
            String CategoryRequest = "CategoryRequest";
            String url = "https://www.groceryshopper.info/apps_data/about_us.php";
            final ProgressDialog progressDialog = new ProgressDialog(AboutUs.this);
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
                           JSONObject  jsonObject=response.getJSONObject("About_us");
                            String imeges=jsonObject.getString("image");
                            String content=jsonObject.getString("content");

                            setdata(imeges,content);

                        } catch (JSONException e) {
                            setdata("","");

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


    public  void  setdata(String im,String  data){

        imageLoader = AppController.getInstance().getImageLoader();
        img.setImageUrl("https://www.groceryshopper.info/apps_data/banners/"+im, imageLoader);
        content.setText(data);


    }
}
