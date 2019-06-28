package com.app_web.technology.groceryshopper.Activitys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.app_web.technology.groceryshopper.Addapter.CategoryAddpter;

import com.app_web.technology.groceryshopper.Database.DataBaseHelper;
import com.app_web.technology.groceryshopper.Model.Services;
import com.app_web.technology.groceryshopper.R;
import com.app_web.technology.groceryshopper.util.Constants;
import com.app_web.technology.groceryshopper.util.VolleyMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ItemMainCotegory extends AppCompatActivity implements View.OnClickListener {
    List<Services> GetDataAdapter1;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecyclerView.Adapter recyclerViewadapter;
    Button button;
    ProgressDialog pDialog;

   public static DataBaseHelper dbHelper;
    LinearLayout wishlist,shop_cart,mainmenu,search,contact_us,more,notification,menu,search_boxd;
    private TextView txt_item_quantity,wishtext,name,txt_item_price;
    LinearLayout menusub;
    ImageView cancked;
    Button Search_button;
    EditText search_box ;
    JsonArrayRequest jsonArrayRequest ;
    RequestQueue requestQueue ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_main);
        init();
        name.setText(getIntent().getExtras().get("name").toString());
        getSupportActionBar().hide();
        GetDataAdapter1 = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewmain);
        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        JSON_PARSE_DATA_AFTER_WEBCALL();
        menusub.setOnClickListener(this);
        shop_cart.setOnClickListener(this);
        wishlist.setOnClickListener(this);
        notification.setOnClickListener(this);
        more.setOnClickListener(this);
        menu.setOnClickListener(this);
        mainmenu.setOnClickListener(this);
        search.setOnClickListener(this);
        cancked.setOnClickListener(this);
       // search_box.addTextChangedListener(new MyTextWatcher(search_box));

        Search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search_box.getText().toString().length()>=3) {

                    Constants.itemhaddername=new String("");
                    Constants.itemhaddername=search_box.getText().toString();
                    VolleyMethods vm = new VolleyMethods(ItemMainCotegory.this);
                    vm.search(search_box.getText().toString());

                } else {

                }

            }
        });



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
        dbHelper = new DataBaseHelper(this);
        Bundle bundle = dbHelper.getItemQuantity();
        txt_item_price.setText("£" + bundle.getString("TotalPrice"));
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
            wishtext.setText(j);
        }

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.menusub: {
                finish();

            }
            break;
            case R.id.shop_cart: {
                Intent intent = new Intent(ItemMainCotegory.this, CheckOut.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
            break;
            case R.id.notification: {
                if(Constants.isUserLogIn){
                    Intent intent = new Intent(ItemMainCotegory.this, Myorder.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }else {
                    Constants.logincontrol=1;
                    Intent intent = new Intent(ItemMainCotegory.this, Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
            }
            break;
            case R.id.wishlist: {

                if(Constants.isUserLogIn){
                    Intent intent = new Intent(ItemMainCotegory.this, Wishlistactivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }else {
                    Constants.logincontrol=2;
                    Intent intent = new Intent(ItemMainCotegory.this, Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }

            }
            break;
            case R.id.more: {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, R.string.app_name);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "click here to visit  https://play.google.com/store/apps/details?id=com.app_web.technology.groceryshopper");
                startActivity(Intent.createChooser(sharingIntent, "Share via"));


            }
            break;
            case R.id.home: {
                Intent intent = new Intent(ItemMainCotegory.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            }
            break;
            case R.id.search: {
                search_boxd.setVisibility(View.VISIBLE);


            }
            break;
            case R.id.cancked: {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                search_boxd.setVisibility(View.GONE);
               // recreate();
            }
            break;
        }
    }
    private void validateName() {

        if (search_box.getText().toString().length()>=3) {
            VolleyMethods vm = new VolleyMethods(this);
            vm.search(search_box.getText().toString());

        } else {

        }



    }
    public  void init(){
        txt_item_price=(TextView)findViewById(R.id.txt_item_price);
        shop_cart=(LinearLayout)findViewById(R.id.shop_cart);
        wishlist=(LinearLayout)findViewById(R.id.wishlist);
        notification=(LinearLayout)findViewById(R.id.notification);
        more=(LinearLayout)findViewById(R.id.more);
        menu=(LinearLayout)findViewById(R.id.home);
        menusub=(LinearLayout)findViewById(R.id.menusub);
        txt_item_quantity=(TextView)findViewById(R.id.txt_item_quantity);
        wishtext=(TextView)findViewById(R.id.wishquanity);
        name=(TextView)findViewById(R.id.name);
        mainmenu=(LinearLayout)findViewById(R.id.home);
        search=(LinearLayout)findViewById(R.id.search);
        search_boxd=(LinearLayout)findViewById(R.id.search_boxd);
        cancked=(ImageView)findViewById(R.id.cancked);
        search_boxd.setVisibility(View.GONE);
        search_box=(EditText)findViewById(R.id.search_box);
        Search_button=(Button)findViewById(R.id.search_button);
    }



    public void JSON_PARSE_DATA_AFTER_WEBCALL(){

        try{
        JSONArray array=new JSONArray(Constants.category);
        for(int i = 0; i<array.length(); i++) {
            Services GetDataAdapter2 = new Services();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                GetDataAdapter2.setcat_id(json.getString("cat_id"));
                GetDataAdapter2.setcountry_id(json.getString("country_id"));
                GetDataAdapter2.setcat_order(json.getString("cat_order"));
                GetDataAdapter2.setcat_name(json.getString("cat_name"));
                GetDataAdapter2.setis_promo(json.getString("is_promo"));
                GetDataAdapter2.setcat_image(json.getString("cat_image"));
                GetDataAdapter2.setcat_desc(json.getString("cat_desc"));
                GetDataAdapter2.setstatus(json.getString("status"));
                GetDataAdapter2.sethas_subcat(json.getString("has_subcat"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);
        }}catch (JSONException e){}

        recyclerViewadapter = new CategoryAddpter(GetDataAdapter1, this);
        recyclerView.setAdapter(recyclerViewadapter);
    }



    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.search_box:
                    validateName();
                    break;



            }
        }
    }
}