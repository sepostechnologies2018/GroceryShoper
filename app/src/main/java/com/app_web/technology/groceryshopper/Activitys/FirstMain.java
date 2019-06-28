package com.app_web.technology.groceryshopper.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.app_web.technology.groceryshopper.Addapter.RecyclerViewAdapterFirst;
import com.app_web.technology.groceryshopper.Database.DataBaseHelper;
import com.app_web.technology.groceryshopper.Model.GetDataAdapterFirst;
import com.app_web.technology.groceryshopper.R;
import com.app_web.technology.groceryshopper.util.Constants;
import com.app_web.technology.groceryshopper.util.VolleyMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FirstMain extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG= FirstMain.class.getSimpleName();

    List<GetDataAdapterFirst> GetDataAdapter1;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecyclerView.Adapter recyclerViewadapter;
    Button button;
    ImageView cancked;
    Button Search_button;
    EditText search_box ;
    public static DataBaseHelper dbHelper;
    LinearLayout wishlist,shop_cart,mainmenu,search,contact_us,more,notification,menu,search_boxd;
    private TextView txt_item_quantity,wishtext,name,txt_item_price;
    LinearLayout menusub;
    JsonArrayRequest jsonArrayRequest ;
    RequestQueue requestQueue ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_main);
         init();
        name.setText(Constants.itemname);
        getSupportActionBar().hide();
        GetDataAdapter1 = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewmain);
        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        JSON_DATA_WEB_CALL();
        Constants.country_id=new String("");
        Constants.country_id=getIntent().getExtras().get("country_id").toString();
        if(getIntent().getExtras().get("data").toString().equals("N")){
            popup();
        }
        menusub.setOnClickListener(this);
        shop_cart.setOnClickListener(this);
        wishlist.setOnClickListener(this);
        notification.setOnClickListener(this);
        more.setOnClickListener(this);
        menu.setOnClickListener(this);
        mainmenu.setOnClickListener(this);
        search.setOnClickListener(this);
        cancked.setOnClickListener(this);
        //search_box.addTextChangedListener(new MyTextWatcher(search_box));
        Search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (search_box.getText().toString().length()>=3) {
                    Constants.itemhaddername=new String("");
                    Constants.itemhaddername=search_box.getText().toString();
                    VolleyMethods vm = new VolleyMethods(FirstMain.this);
                    vm.search(search_box.getText().toString());

                } else {

                }

            }
        });

    }

    private void validateName() {

        if (search_box.getText().toString().length()>=3) {
            VolleyMethods vm = new VolleyMethods(this);
            vm.search(search_box.getText().toString());

        } else {

        }



    }
    public  void popup(){

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle("");
        builder.setCancelable(false);
        builder.setMessage("Items Not Found..");
        builder.setPositiveButton("Discard", null);
        builder.setPositiveButton("Discard",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.show();
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
                Intent intent = new Intent(FirstMain.this, CheckOut.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
            break;
            case R.id.notification: {
                if(Constants.isUserLogIn){
                    Intent intent = new Intent(FirstMain.this, Myorder.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }else {
                    Constants.logincontrol=1;
                    Intent intent = new Intent(FirstMain.this, Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }

            }
            break;
            case R.id.wishlist: {


                if(Constants.isUserLogIn){
                    Intent intent = new Intent(FirstMain.this, Wishlistactivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }else {
                    Constants.logincontrol=2;
                    Intent intent = new Intent(FirstMain.this, Login.class);
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
                Intent intent = new Intent(FirstMain.this, Home.class);
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

            }
            break;
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
    public void JSON_DATA_WEB_CALL(){
        try{
       JSONArray jsonArray=new JSONArray(Constants.sub_category);
            Log.d(TAG,"sub_category RESPONSE:"+Constants.sub_category);
            JSON_PARSE_DATA_AFTER_WEBCALL(jsonArray);}
        catch (JSONException e){

        }

    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){
        for(int i = 0; i<array.length(); i++) {
            GetDataAdapterFirst GetDataAdapter2 = new GetDataAdapterFirst();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                GetDataAdapter2.setstatus(json.getString("status"));
                GetDataAdapter2.setcat_id(json.getString("cat_id"));
                GetDataAdapter2.setfull_desp(json.getString("full_desp"));
                GetDataAdapter2.setsub_cat_id(json.getString("sub_cat_id"));
                GetDataAdapter2.setsub_cat_name(json.getString("sub_cat_name"));
                GetDataAdapter2.setsub_cat_image(json.getString("sub_cat_image"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);
        }
        recyclerViewadapter = new RecyclerViewAdapterFirst(GetDataAdapter1, this);
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