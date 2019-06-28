package com.app_web.technology.groceryshopper.Activitys;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app_web.technology.groceryshopper.Database.DataBaseHelper;
import com.app_web.technology.groceryshopper.R;
import com.app_web.technology.groceryshopper.util.Constants;
import com.app_web.technology.groceryshopper.util.Pref;
import com.app_web.technology.groceryshopper.util.VolleyMethods;
import java.util.HashMap;
public class DeliveryOption extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG= DeliveryOption.class.getSimpleName();

public  static  String DeliveryType="D";
    RadioButton radioDelivery, radioCollect,radio_table;
    Button btnContinue;
    RadioGroup radioGroup;
       ImageView imageView;
    TextView wishtext,name;
    EditText popstcpode,instrucation;
    static boolean flag=true;
    LinearLayout back,wishlist,pincode;
    Pref pref;

    boolean type=true;
    public static DataBaseHelper dbHelper;
    public static String checkInTime;
    public static String checkOutTime;
    public static String CollectionCycle;
    public static String shopStatus;
    public static String DeliveryCycle;
    int time;
    public static boolean stor= true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_option);
        init();
        getSupportActionBar().hide();

       if(Constants.type){
           popstcpode.setVisibility(View.VISIBLE);
        }
        else {
           popstcpode.setVisibility(View.GONE);
        }
        back.setOnClickListener(this);
        wishlist.setOnClickListener(this);


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 pref= new Pref(getApplicationContext());
                 pref.setINSTRUCATION(instrucation.getText().toString());



              if(flag){
                  if (Constants.type)
                  {
                if (popstcpode.getText().toString().equals(""))
                {
                    popstcpode.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Enter  Postcode ", Toast.LENGTH_SHORT).show();
                }
                else
                    {

                        popstcpode.setVisibility(View.VISIBLE);
                        if(popstcpode.getText().length()<6)
                        {
                            Toast.makeText(getApplicationContext(),"Post code should be greter then 6 characher!",Toast.LENGTH_SHORT).show();
                        }
                        else if(popstcpode.getText().length()>8)
                        {
                            Toast.makeText(getApplicationContext(),"Post code should be less then  8 characher!",Toast.LENGTH_SHORT).show();
                        }
                        else {


                            dbHelper = new DataBaseHelper(DeliveryOption.this);
                            Bundle bundle = dbHelper.getItemQuantity();
                            Float t1 = Float.parseFloat(bundle.getString("TotalPrice"));

                            if (t1 < Float.parseFloat(Constants.minimum_delivery_amount)) {
                                openBottomSheetamount("For Delivery Minimum" +" "+

                                        "Order Amount Should be £ " + Constants.minimum_delivery_amount);
                            }
                            else
                            {
                                Constants.postal = new String("");
                                Constants.postal = popstcpode.getText().toString();
                                pref= new Pref(getApplicationContext());
                                pref.setPostCode(popstcpode.getText().toString());
                                VolleyMethods vm = new VolleyMethods(DeliveryOption.this);
                                String t = Constants.postal;
                                HashMap<String, String> userInfo = new HashMap<String, String>();
                                userInfo.put("postcode",Constants.postal);
                                vm.postalcode(userInfo,"1");


                            }





                        }




                    }

            }

            else {
                      Log.d(TAG,"DDDDDDDDDDDDDDDDDDDDDD");

                      dbHelper = new DataBaseHelper(DeliveryOption.this);
                      Bundle bundle = dbHelper.getItemQuantity();
                      Float t1 = Float.parseFloat(bundle.getString("TotalPrice"));

                      if (t1 < Float.parseFloat(Constants.minimum_delivery_amount)) {
                          openBottomSheetamount("For Delivery Order" +

                                  "Minimum Amount Should be £ " + Constants.minimum_delivery_amount);
                      }
                      else
                      {





                          Constants.type=false;
                          Intent i=new Intent(DeliveryOption.this,DeliveryDetailAddress.class);
                          i.putExtra("DeliveryType", DeliveryType);
                          i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                          startActivity(i);
                      }






                }}else {

                  Intent i=new Intent(DeliveryOption.this,DeliveryDetailAddress.class);
                  i.putExtra("DeliveryType", DeliveryType);
                  i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                  startActivity(i);
              }

            }


        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.radio_delivery: {
                        DeliveryType=new String("");
                        DeliveryType="D";
                        flag=true;


                        if (Constants.type)
                        {
                            popstcpode.setVisibility(View.VISIBLE);
                        }
                        else {
                            popstcpode.setVisibility(View.GONE);

                        }


                    }
                    break;
                    case R.id.radio_collect: {
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        flag=false;
                        popstcpode.setVisibility(View.GONE);
                        DeliveryType=new String("");
                        DeliveryType="C";

                       }
                    break;
                    case R.id.radio_table: {
                    }
                    break;
                }
            }
        });




    }
    @Override
    protected void onStart(){
        super.onStart();;
        if (Constants.type){
            pincode.setVisibility(View.VISIBLE);

        }
        else {
            pincode.setVisibility(View.GONE);

        }

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menusub: {
                finish();
            }
            break;
            case R.id.wishlist: {

                if(Constants.isUserLogIn){
                    Intent intent = new Intent(DeliveryOption.this, Wishlistactivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                }else {


                    Constants.logincontrol=2;
                    Intent intent = new Intent(DeliveryOption.this, Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }



            }
            break;

        }
    }




    @Override
    protected void onResume() {
        super.onResume();

   /*     if(Constants.OrderDisabled.equals("1"))
        {
            openBottomSheet();

        }
        else
        {
            btnContinue.setText("Continue");
            stor=false;
        }
*/










        Constants cons = new Constants();
        String j=cons.getQuantitywish(this);
        if(j.equals("")){
            wishtext.setVisibility(View.GONE);
        }
        else {
            wishtext.setText(j);
        }

/*

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.radio_delivery: {
                        DeliveryType=new String("");
                        DeliveryType="D";
                        flag=true;


                        if (Constants.type){


                            popstcpode.setVisibility(View.VISIBLE);
                        }
                        else {
                            popstcpode.setVisibility(View.GONE);

                        }


                    }
                    break;
                    case R.id.radio_collect: {
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        flag=false;
                        popstcpode.setVisibility(View.GONE);
                        DeliveryType=new String("");
                        DeliveryType="C";

                    }
                    break;
                    case R.id.radio_table: {
                    }
                    break;
                }
            }
        });
*/







    }
    public  void popup(String Messges, final  String status){
        LayoutInflater inflater = LayoutInflater.from(DeliveryOption.this);
        View view = inflater.inflate(R.layout.custom, null);
        TextView textView=(TextView)view.findViewById(R.id.tv);
        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/aaa.ttf");
        textView.setTypeface(face);
        textView.setText(Messges);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                if(status.equals("1"))
                {
                       Constants.type=false;
                        btnContinue.setText("Continue");
                        pincode.setVisibility(View.GONE);


                }
                else {


                }

                alertDialog.dismiss();

            }
        });

        builder.show();




    }

    public  void init(){
        btnContinue = (Button) findViewById(R.id.btn_continue);
        radioCollect = (RadioButton) findViewById(R.id.radio_collect);
        radio_table = (RadioButton) findViewById(R.id.radio_table);
        radioDelivery = (RadioButton) findViewById(R.id.radio_delivery);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group_delivery_option);
        back=(LinearLayout)findViewById(R.id.menusub);
        wishtext=(TextView)findViewById(R.id.wishquanity);
        wishlist=(LinearLayout)findViewById(R.id.wishlist);
        pincode=(LinearLayout)findViewById(R.id.pincode);
        popstcpode=(EditText)findViewById(R.id.popstcpode);
        imageView=(ImageView)findViewById(R.id.speec);
        imageView.setVisibility(View.GONE);
        name=(TextView)findViewById(R.id.name);
        name.setText("Check Out");
        DeliveryType="D";
        instrucation= findViewById(R.id.instrucation);
    }

    public void openBottomSheet() {


        Pref pref= new Pref(getApplicationContext());
        Log.d(TAG,"CHECKiNtIME:"+checkInTime);
        View view = getLayoutInflater().inflate(R.layout.storestatus, null);
        LinearLayout le=(LinearLayout) view.findViewById(R.id.poupOk) ;
        TextView Add_pin = (TextView)view.findViewById(R.id.text);


        popstcpode.setEnabled(false);
        radioDelivery.setClickable(false);
        radioCollect.setClickable(false);
        radioDelivery.setEnabled(false);
        radioCollect.setEnabled(false);
        btnContinue.setEnabled(false);
        instrucation.setEnabled(false);
        Add_pin.setText("Sorry, we are currently closed, however you can still place an order and we will process it when we open at " +checkInTime);

        final Dialog mBottomSheetDialog = new Dialog(DeliveryOption.this);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.setCanceledOnTouchOutside(false);

        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.TOP);
        mBottomSheetDialog.show();
        le.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBottomSheetDialog.dismiss();
                finish();
            }
        });

    }

    public void openBottomSheetamount(String mes) {
        View view = getLayoutInflater().inflate(R.layout.store_status1, null);
        LinearLayout le=(LinearLayout) view.findViewById(R.id.poupOk12) ;
        TextView Add_pin = (TextView)view.findViewById(R.id.text);
        Add_pin.setText(mes);
        final Dialog mBottomSheetDialog = new Dialog(DeliveryOption.this,R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.setTitle("");
        mBottomSheetDialog.setCanceledOnTouchOutside(false);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.CENTER);
        mBottomSheetDialog.show();


        le.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mBottomSheetDialog.dismiss();


            }
        });

    }





}
