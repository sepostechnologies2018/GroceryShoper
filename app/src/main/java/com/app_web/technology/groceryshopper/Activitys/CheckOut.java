package com.app_web.technology.groceryshopper.Activitys;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.app_web.technology.groceryshopper.Addapter.CheckOutAdapter;
import com.app_web.technology.groceryshopper.Database.DataBaseHelper;
import com.app_web.technology.groceryshopper.Model.OrderItemModel;
import com.app_web.technology.groceryshopper.R;
import com.app_web.technology.groceryshopper.util.Constants;
import com.app_web.technology.groceryshopper.util.VolleyMethods;

import java.util.ArrayList;
public class CheckOut extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG= CheckOut.class.getSimpleName();

    TextView cancelOrder, btnShopStatus;
    public static TextView totalPrice;
    public static DataBaseHelper dbHelper;
    public static CheckOutAdapter checkOutAdapter;
    public static RecyclerView orderCatRecycler;
    public static ArrayList<OrderItemModel> orderItemList;
    public static String shopStatus;
    LinearLayout emapty,cartitem;
    public static TextView txtQuantity;
    TextView txtCategoryName ,textlog,name;
    LinearLayout back;
    public static TextView txt_item_quantity,wishtext,total;
    private final long millisecondsToShowSplash = 2000L;
    LinearLayout llMenu, llAboutUs, llContactUs, llCheckOut,llFindOut;;
    LinearLayout wishlist,shop_cart,search,contact_us,more,notification,home,menu;
    TextView checkout;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        Log.d(TAG,"111111111111111");
        getSupportActionBar().hide();
        init();
        name.setText("Your Cart Items");
        wishlist.setOnClickListener(this);
        shop_cart.setOnClickListener(this);
        search.setOnClickListener(this);
        //contact_us.setOnClickListener(this);
        more.setOnClickListener(this);
        notification.setOnClickListener(this);
        home.setOnClickListener(this);
        back.setOnClickListener(this);
        checkout.setOnClickListener(this);
      //  bindAdapter();
        VolleyMethods volleyMethods=new VolleyMethods(this);
       // volleyMethods.getinfo();


        volleyMethods.getStoreStatus();


    }
    @Override
    protected void onResume() {
        super.onResume();
        bindAdapter();
        Constants cons = new Constants();
        String i=cons.getQuantity(this);
        Bundle bundle = dbHelper.getItemQuantity();

        total.setText("£" + bundle.getString("TotalPrice"));
        if(i.equals("")){
            txt_item_quantity.setVisibility(View.GONE);
            cartitem.setVisibility(View.GONE);
            emapty.setVisibility(View.VISIBLE);
            orderCatRecycler.setVisibility(View.GONE);
        }
        else {
            orderCatRecycler.setVisibility(View.VISIBLE);
            cartitem.setVisibility(View.VISIBLE);
            txt_item_quantity.setText(cons.getQuantity(this));
            emapty.setVisibility(View.GONE);
        }
        String j=cons.getQuantitywish(this);
        if(j.equals("")){
            wishtext.setVisibility(View.VISIBLE);
        }
        else {
            wishtext.setText(j);
        }
        /*Intent intent = getIntent();
        finish();
        startActivity(intent);*/
    }


    public  void check(){
        Constants cons = new Constants();
        String i=cons.getQuantity(this);
        Bundle bundle = dbHelper.getItemQuantity();

        total.setText("£" + bundle.getString("TotalPrice"));
        if(i.equals("")){
            txt_item_quantity.setVisibility(View.GONE);
            cartitem.setVisibility(View.GONE);
            emapty.setVisibility(View.VISIBLE);
            orderCatRecycler.setVisibility(View.GONE);
        }
        else {
            orderCatRecycler.setVisibility(View.VISIBLE);
            cartitem.setVisibility(View.VISIBLE);
            txt_item_quantity.setText(cons.getQuantity(this));
            emapty.setVisibility(View.GONE);
        }
        String j=cons.getQuantitywish(this);
        if(j.equals("")){
            wishtext.setVisibility(View.VISIBLE);
        }
        else {
            wishtext.setText(j);
        }

    }
    public void init() {
        dbHelper = new DataBaseHelper(this);
        orderItemList = new ArrayList<OrderItemModel>();
        orderCatRecycler = (RecyclerView) findViewById(R.id.check_out_recycler);
        txt_item_quantity=(TextView)findViewById(R.id.txt_item_quantity);
        emapty=(LinearLayout)findViewById(R.id.emapty);
        cartitem=(LinearLayout)findViewById(R.id.cartitem);
        txtQuantity=(TextView)findViewById(R.id.txt_item_quantity);
        back=(LinearLayout)findViewById(R.id.menusub);
        checkout=(TextView)findViewById(R.id.checkout);
        wishtext=(TextView)findViewById(R.id.wishquanity);
        shop_cart=(LinearLayout)findViewById(R.id.shop_cart);
        wishlist=(LinearLayout)findViewById(R.id.wishlist);
        search=(LinearLayout)findViewById(R.id.search);
        more=(LinearLayout)findViewById(R.id.more);
        notification=(LinearLayout)findViewById(R.id.notification);
        home=(LinearLayout)findViewById(R.id.home);
        menu=(LinearLayout)findViewById(R.id.menu);
        total=(TextView)findViewById(R.id.total);
        name=(TextView)findViewById(R.id.name);
        imageView=(ImageView)findViewById(R.id.speec);
        imageView.setVisibility(View.GONE);

        if(Constants.isUserLogIn){
            checkout.setBackgroundColor(getResources().getColor(R.color.hadder));

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //check();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menusub: {
                finish();
            }
            break;
            case R.id.checkout: {

                Constants cons = new Constants();
                String i=cons.getQuantity(this);
              if(i.equals("")){

                  LayoutInflater inflater = getLayoutInflater();

                  View layout = inflater.inflate(R.layout.custom_toast_emmpty,
                          (ViewGroup) findViewById(R.id.custom_toast_layout_id_empty));
                  Toast toast = new Toast(getApplicationContext());
                  toast.setDuration(Toast.LENGTH_SHORT);
                  toast.setView(layout);
                  toast.show();

              }
                else {


              Constants constants=new Constants();
              String  time= constants.singleitemquntitytime(this,"1") ;
                 // Toast.makeText(CheckOut.this,"time"+time,Toast.LENGTH_SHORT).show();

            if(!time.equals("0")&&Constants.time.equals("1")){
                popup();
            }
                  else {

        if(Constants.isUserLogIn){

         Intent intent = new Intent(CheckOut.this, DeliveryOption.class);
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         startActivity(intent);

        }else {


                 Constants.logincontrol=1;
                Intent intent = new Intent(CheckOut.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

          }}
                }

            }
            break;
            case R.id.shop_cart: {

                Intent intent = new Intent(CheckOut.this, CheckOut.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            }
            break;

            case R.id.wishlist: {






                if(Constants.isUserLogIn){
                    Intent intent = new Intent(CheckOut.this, Wishlistactivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }else {


                    Constants.logincontrol=2;
                    Intent intent = new Intent(CheckOut.this, Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }

            }
            break;


        }
    }

    //Method to bind Adapter
    public void bindAdapter() {
        orderItemList = dbHelper.getOrder();
        checkOutAdapter = new CheckOutAdapter(CheckOut.this, orderItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        orderCatRecycler.setLayoutManager(mLayoutManager);
        orderCatRecycler.setItemAnimator(new DefaultItemAnimator());
        orderCatRecycler.setAdapter(checkOutAdapter);




    }


    public  void popup(){

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle("");
        builder.setCancelable(false);
        builder.setMessage(Constants.messages);
        builder.setPositiveButton("Discard", null);
        builder.setPositiveButton("Discard",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         Constants.salesrestricted=true;
                         recreate();
                    }
                });
        builder.show();
    }

    public Double calculateTotalPrice(ArrayList<OrderItemModel> itemList) {
        Double totalPrice = new Double(0.0);

        for (OrderItemModel item : itemList) {
            totalPrice += (new Double(item.getItemPrice()) * (new Double(item.getItemQuantity())));
        }
        return totalPrice;
    }
    public void popuplpogpout(String mess)
    {
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


    public void popuplpogpoutwish(String mess) {


        AlertDialog.Builder builder =
                new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle("");
        builder.setCancelable(false);
        builder.setMessage("Please Login First To Add item in your wishlist");
        builder.setPositiveButton("OK", null);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(CheckOut.this, Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
        builder.show();

    }

}
