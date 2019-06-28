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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app_web.technology.groceryshopper.Addapter.Wishlist;
import com.app_web.technology.groceryshopper.Database.DataBaseHelper;
import com.app_web.technology.groceryshopper.Database.Wishlistdatabase;
import com.app_web.technology.groceryshopper.Fragments.Contactus;
import com.app_web.technology.groceryshopper.Model.WishlistOrderItemModel;
import com.app_web.technology.groceryshopper.R;
import com.app_web.technology.groceryshopper.util.Constants;
import com.app_web.technology.groceryshopper.util.VolleyMethods;

import java.util.ArrayList;

public class Wishlistactivity extends AppCompatActivity implements View.OnClickListener {
    TextView cancelOrder, btnShopStatus;
    public static TextView totalPrice;
    public static Wishlistdatabase dbHelper;
    public static Wishlist checkOutAdapter;
    public static RecyclerView orderCatRecycler;
    public static ArrayList<WishlistOrderItemModel> orderItemList;
    public static String shopStatus;
    LinearLayout emapty,cartitem;
    public static DataBaseHelper dbHelpert;
    public static TextView txtQuantity,txtWebsite,txt_item_quantity,txt_item_price;
    TextView txtCategoryName ,textlog,name;
    ImageView speec;
    LinearLayout wishlist,shop_cart,search,contact_us,more,notification,home,menu;
    LinearLayout back;
    private final long millisecondsToShowSplash = 2000L;
    LinearLayout llMenu, llAboutUs, llContactUs, llCheckOut,llFindOut;;
    TextView checkout;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlistactivity);
        getSupportActionBar().hide();
        init();
        name.setText("Your WishList");
        back.setOnClickListener(this);
        shop_cart.setOnClickListener(this);
        emapty.setOnClickListener(this);
        dbHelper=new Wishlistdatabase(this);
        dbHelper.deleteOrder();
        VolleyMethods volleyMethods=new VolleyMethods(this);
        volleyMethods.wishlistitem("");
      //  bindAdapter();
    }
    @Override
    protected void onResume() {
        super.onResume();
      /* // bindAdapter();
        Constants cons = new Constants();
        String i=cons.getQuantity(this);
        String j=cons.getQuantitywish(this);
        dbHelpert = new DataBaseHelper(this);
        Bundle bundlet = dbHelpert.getItemQuantity();

        txt_item_price.setText("£" + bundlet.getString("TotalPrice"));
        if(i.equals("")){
            txt_item_quantity.setVisibility(View.GONE);
        }
        else {
            txt_item_quantity.setText(cons.getQuantity(this));}
        if(j.equals("")){
            cartitem.setVisibility(View.VISIBLE);
            txtWebsite.setVisibility(View.VISIBLE);
            emapty.setVisibility(View.VISIBLE);
        }
        else {
            txtWebsite.setText(j);
        }*/
    }
    public void init() {
        txt_item_price=(TextView)findViewById(R.id.txt_item_price);
        orderItemList = new ArrayList<WishlistOrderItemModel>();
        orderCatRecycler = (RecyclerView) findViewById(R.id.check_out_recycler);
        shop_cart=(LinearLayout)findViewById(R.id.shop_cart);
        emapty=(LinearLayout)findViewById(R.id.emapty);
        cartitem=(LinearLayout)findViewById(R.id.cartitem);
        name=(TextView)findViewById(R.id.name);
        back=(LinearLayout)findViewById(R.id.menusub);
        checkout=(TextView)findViewById(R.id.checkout);
        txtWebsite=(TextView)findViewById(R.id.wishquanity);
        txt_item_quantity=(TextView)findViewById(R.id.txt_item_quantity);
        speec=(ImageView)findViewById(R.id.speec);
        speec.setVisibility(View.GONE);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menusub: {
                finish();
            }

            break;


            case R.id.emapty: {
                Intent intent = new Intent(Wishlistactivity.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            break;

            case R.id.checkout: {
                Contactus frag = new Contactus();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.add(R.id.shoping,frag,"Contactus");
                transaction.addToBackStack(null);
                transaction.commit();
            }
            break;
            case R.id.notification: {


                if(Constants.isUserLogIn){
                    Intent intent = new Intent(Wishlistactivity.this, Myorder.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }else {
                    Constants.logincontrol=3;
                    Intent intent = new Intent(Wishlistactivity.this, Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }







            }
            break;
            case R.id.shop_cart: {

                Intent intent = new Intent(Wishlistactivity.this, CheckOut.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            }
            break;

        }
    }
    public  void check(){
        Constants cons = new Constants();
        String i=cons.getQuantity(this);
        String j=cons.getQuantitywish(this);
        if(i.equals("")){
            txt_item_quantity.setVisibility(View.GONE);
        }
        else {
            txt_item_quantity.setText(cons.getQuantity(this));
        }
        if(j.equals("")){
            cartitem.setVisibility(View.GONE);
            txtWebsite.setVisibility(View.GONE);
            emapty.setVisibility(View.VISIBLE);
        }
        else {
            txtWebsite.setText(j);}
    }

    public void bindAdapter() {
        orderItemList = dbHelper.getOrder();
        int noofItems = orderItemList.size();
        checkOutAdapter = new Wishlist(Wishlistactivity.this, orderItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        orderCatRecycler.setLayoutManager(mLayoutManager);
        orderCatRecycler.setItemAnimator(new DefaultItemAnimator());
        orderCatRecycler.setAdapter(checkOutAdapter);


        if(noofItems==0){
            cartitem.setVisibility(View.GONE);
            txtWebsite.setVisibility(View.GONE);
            emapty.setVisibility(View.VISIBLE);
        }
        else {
            txtWebsite .setVisibility(View.VISIBLE);
            txtWebsite.setText(String.valueOf(noofItems));}



    }

    public Double calculateTotalPrice(ArrayList<WishlistOrderItemModel> itemList) {
        Double totalPrice = new Double(0.0);

        for (WishlistOrderItemModel item : itemList) {
            totalPrice += (new Double(item.getItemPrice()) * (new Double(item.getItemQuantity())));
        }
        return totalPrice;
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
        mBottomSheetDialog.getWindow().setGravity(Gravity.TOP);
        mBottomSheetDialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        mBottomSheetDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBottomSheetDialog.dismiss();

            }
        }, millisecondsToShowSplash);




    }
}
