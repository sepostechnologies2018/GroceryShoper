package com.app_web.technology.groceryshopper.Activitys;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app_web.technology.groceryshopper.Addapter.Itemsubadapter;
import com.app_web.technology.groceryshopper.Database.DataBaseHelper;
import com.app_web.technology.groceryshopper.Fragments.Contactus;
import com.app_web.technology.groceryshopper.Fragments.Notification;
import com.app_web.technology.groceryshopper.GetterSetter.Subitem;
import com.app_web.technology.groceryshopper.R;
import com.app_web.technology.groceryshopper.util.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
public class ItemsActivity extends AppCompatActivity implements View.OnClickListener {
    GridView grid;
    LinearLayout wishlist,shop_cart,search,contact_us,more,notification,home,menu;
    private ArrayList<Subitem> feedItemList;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    Itemsubadapter mAdapter;
    String img="";
    LinearLayout back;

    public TextView txt_item_quantity,wishtext;
    public static DataBaseHelper dbHelper;
    private final long millisecondsToShowSplash = 2000L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        getSupportActionBar().hide();
        init();
        img=getIntent().getExtras().get("itemdata").toString();
        feedItemList = new ArrayList<Subitem>();
        prepareAlbums();
        wishlist.setOnClickListener(this);
        shop_cart.setOnClickListener(this);
        search.setOnClickListener(this);
        more.setOnClickListener(this);
        notification.setOnClickListener(this);
        home.setOnClickListener(this);
        mRecyclerView = (RecyclerView)findViewById(R.id.itemview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new Itemsubadapter(ItemsActivity.this, feedItemList);
        mRecyclerView.setAdapter(mAdapter);
        back.setOnClickListener(this);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
    public void init(){
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
    }

    public void popuplpogpout(String mess) {
        LayoutInflater inflater = (LayoutInflater)getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.notificationadditem, null);Typeface face= Typeface.createFromAsset(getAssets(), "fonts/aaa.ttf");
        TextView notify=(TextView)view.findViewById(R.id.notificationtext);
        notify.setTypeface(face);
        notify.setText(mess);
        final Dialog mBottomSheetDialog = new Dialog(this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        mBottomSheetDialog.show();
        Constants cons = new Constants();
        txt_item_quantity.setVisibility(View.VISIBLE);
        txt_item_quantity.setText(cons.getQuantity(this));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBottomSheetDialog.dismiss();

            }
        }, millisecondsToShowSplash);
    }
    public void popuplpogpoutwish(String mess) {
        LayoutInflater inflater = (LayoutInflater)getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.notificationadditem, null);Typeface face= Typeface.createFromAsset(getAssets(), "fonts/aaa.ttf");
        TextView notify=(TextView)view.findViewById(R.id.notificationtext);
        notify.setTypeface(face);
        notify.setText(mess);
        final Dialog mBottomSheetDialog = new Dialog(this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.TOP);
        mBottomSheetDialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        mBottomSheetDialog.show();
        Constants cons = new Constants();
        wishtext.setVisibility(View.VISIBLE);
        wishtext.setText(cons.getQuantitywish(this));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBottomSheetDialog.dismiss();

            }
        }, millisecondsToShowSplash);
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
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shop_cart: {

                Intent intent = new Intent(ItemsActivity.this, CheckOut.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            }
            break;

            case R.id.wishlist: {





                if(Constants.isUserLogIn){
                    Intent intent = new Intent(ItemsActivity.this, Wishlistactivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                }else {


                    Constants.logincontrol=2;
                    Intent intent = new Intent(ItemsActivity.this, Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }

            }
            break;
           /* case R.id.notification: {
                Intent intent = new Intent(ItemsActivity.this, Myorder.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
            break;*/
            case R.id.contact_us: {
                Intent intent = new Intent(ItemsActivity.this, Contact.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
            break;

            case R.id.more: {
                Contactus frag = new Contactus();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.subitem,frag,"Contactus");
                transaction.addToBackStack(null);
                transaction.commit();


            }
            break;
            case R.id.notification: {

                Notification frag = new Notification();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.subitem,frag,"More");
                transaction.addToBackStack(null);
                transaction.commit();



            }
            break;
            case R.id.home: {
                Intent intent = new Intent(ItemsActivity.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            break;
            case R.id.menusub: {
                Intent intent = new Intent(ItemsActivity.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
            break;

        }
    }

    private void prepareAlbums() {
try{
        JSONArray response =new JSONArray(img);
        for (int i = 0; i <response.length(); i++) {
            try {

                JSONObject jobj = response.getJSONObject(i);
                String price = jobj.getString("price").toString();
                String itemnam = jobj.getString("item_name").toString();

                String itemimages = "http://www.satyamtechnologies.co.uk/egrocery/apps_data/item_images/"+jobj.getString("item_pic").toString();
                Subitem a = new Subitem(itemnam,itemimages,price, jobj.getString("item_id").toString());
                feedItemList.add(a);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }}
catch (JSONException e){

    }








    }


}
