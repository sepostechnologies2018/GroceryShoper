package com.app_web.technology.groceryshopper.Activitys;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app_web.technology.groceryshopper.R;
import com.app_web.technology.groceryshopper.util.Constants;
import com.app_web.technology.groceryshopper.util.Pref;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.app_web.technology.groceryshopper.Activitys.DeliveryOption.DeliveryType;

public class DeliveryDetailAddress extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG= DeliveryDetailAddress.class.getSimpleName();

    Button btnSubmitAddress;
    RadioButton radioPayOnline;
    RadioButton radioCashOnDelivery;
    RadioGroup radioGroupPayMentOption;
    LinearLayout llHome,llBack,llOptionDelivery;
    EditText edtName, edtEmail, edtPhoneNo, edtHouse, edtStreet, edtCity, edtDeliveryNote, edtPostCode, edtState;
    TextView login,Amount,Discount,Deliverycharges,Netpayble;;
    String Time;
    public static String imei,dType;
    ImageView imageView;
    TextView txtQuantity,textaddress,collecttext  ,title_delivery,deleverytype,name;
    ImageView logimg;
    TextView txtCategoryName,wishtext;
    LinearLayout back,wishlist;
    LinearLayout llMenu, llAboutUs, llContactUs, llCheckOut,llFindOut;;

    boolean result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_detail_address);
        init();
        back.setOnClickListener(this);
        wishlist.setOnClickListener(this);
        btnSubmitAddress.setOnClickListener(this);
        getSupportActionBar().hide();
        if(getIntent().getExtras().get("DeliveryType").toString().equals("C")){

            radioCashOnDelivery.setText("Pay Later");
            textaddress.setVisibility(View.GONE);
            llOptionDelivery.setVisibility(View.GONE);
        }
        else{

           // textaddress.setVisibility(View.VISIBLE);
            llOptionDelivery.setVisibility(View.VISIBLE);
            try {
                JSONObject jObj=new JSONObject(Constants.userData);

                if (Constants.postal.equals("")) {
                    edtPostCode.setText(jObj.getString("post"));
                } else {
                    edtPostCode.setText(Constants.postal);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public void fillUserData(){
        try {
            JSONObject jObj=new JSONObject(Constants.userData);
            edtName.setText(jObj.getString("full_name"));
            edtEmail.setText(jObj.getString("mail_id"));
            edtPhoneNo.setText(jObj.getString("contact_no"));
            edtHouse.setText(jObj.getString("house_no"));
            edtStreet.setText(jObj.getString("street"));
            edtCity.setText(jObj.getString("city"));
            edtState.setText(jObj.getString("state"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Constants cons = new Constants();
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


            case R.id.btn_submit_delivery_address: {
                int selectedRadioId = radioGroupPayMentOption.getCheckedRadioButtonId();

                switch (selectedRadioId) {

                    case R.id.radio_cash_on_delivery: {


                        if (DeliveryType.equals("D")) {
                            Log.d(TAG, "DDDDDDDDDDDDDDDDDDDD");
                            if (checkDeliveryInfo()) {


                                Intent intent = new Intent(getApplicationContext(), PaymentByCODActivity.class);
                                intent.putExtra("DeliveryType", "D");
                                intent.putExtra("PaymentType", "Offline");
                                startActivity(intent);


                            } else {







                            }


                            break;


                        } else {


                            Log.d(TAG, "CCCCCCCCCCCCCCCCCC");

                            if (edtName.length() == 0) {
                                Toast.makeText(this, "Please enter name", Toast.LENGTH_LONG).show();
                            } else if (edtEmail.length() == 0) {
                                Toast.makeText(this, "Please enter mail", Toast.LENGTH_LONG).show();
                            } else if (edtPhoneNo.length() == 0) {
                                Toast.makeText(this, "Please enter phone number", Toast.LENGTH_LONG).show();
                            } else {
                                Intent in = new Intent(DeliveryDetailAddress.this, PaymentByCODActivity.class);
                                in.putExtra("DeliveryType", getIntent().getExtras().get("DeliveryType").toString());
                                in.putExtra("PaymentType", "Offline");
                                startActivity(in);
                            }


                            break;


                        }





                    }

                    case R.id.radio_pay_online: {


                        Log.d(TAG, "CCCCCCCCCCCCCCCCCC");

                        if (edtName.length() == 0) {
                            Toast.makeText(this, "Please enter name", Toast.LENGTH_LONG).show();
                        } else if (edtEmail.length() == 0) {
                            Toast.makeText(this, "Please enter mail", Toast.LENGTH_LONG).show();
                        } else if (edtPhoneNo.length() == 0) {
                            Toast.makeText(this, "Please enter phone number", Toast.LENGTH_LONG).show();
                        } else {

                        /*    Constants.addressMap = new HashMap<>();
                             result = false;


                            Constants.addressMap.put("Full_name", edtName.getText().toString());
                            Constants.addressMap.put("Email_id", edtEmail.getText().toString());
                            Constants.addressMap.put("User_id", getUserId());
                            Constants.addressMap.put("Contact_no", edtPhoneNo.getText().toString());
                            Constants.addressMap.put("House", edtHouse.getText().toString());
                            Constants.addressMap.put("Street", edtStreet.getText().toString());
                            Constants.addressMap.put("City", edtCity.getText().toString());
                            Constants.addressMap.put("Post_code",edtPostCode.getText().toString());
                            Constants.addressMap.put("State", "");
                            Constants.addressMap.put("Delivery_detail","test by chetan.");
                            result = true;*/







                        /*    Intent in = new Intent(DeliveryDetailAddress.this, PayMentActivity.class);
                            in.putExtra("DeliveryType", getIntent().getExtras().get("DeliveryType").toString());
                            in.putExtra("PaymentType", "ONLINE");
                            startActivity(in);*/
                        }




                        break;


                    }






                }


                Constants.addressMap = new HashMap<>();
                result = false;
                Pref pref= new Pref(DeliveryDetailAddress.this);

                Constants.addressMap.put("Full_name", edtName.getText().toString());
                Constants.addressMap.put("Email_id", edtEmail.getText().toString());
                Constants.addressMap.put("User_id", getUserId());
                Constants.addressMap.put("Contact_no", edtPhoneNo.getText().toString());
                Constants.addressMap.put("House", edtHouse.getText().toString());
                Constants.addressMap.put("Street", edtStreet.getText().toString());
                Constants.addressMap.put("City", edtCity.getText().toString());
                Constants.addressMap.put("Post_code",edtPostCode.getText().toString());
                Constants.addressMap.put("State", "");
                Constants.addressMap.put("Delivery_detail",pref.getINSTRUCATION());
                result = true;


            }
            case R.id.menusub:
                finish();

            break;
            case R.id.wishlist: {

                if(Constants.isUserLogIn){
                    Intent intent = new Intent(DeliveryDetailAddress.this, Wishlistactivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                }else {


                    Constants.logincontrol=2;
                    Intent intent = new Intent(DeliveryDetailAddress.this, Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }


            }
            break;

        }
    }





    public boolean checkDeliveryInfo() {
        boolean result = false;

        if (edtName.getText().toString().equals("") || edtName.getText().toString().equals("Full name")) {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_LONG).show();
        } else {
            if (edtEmail.getText().toString().equals("") || edtEmail.getText().toString().equals("Email id")) {
                Toast.makeText(this, "Please enter mail", Toast.LENGTH_LONG).show();
            } else {
                if (edtPhoneNo.getText().toString().equals("") || edtPhoneNo.getText().toString().equals("Contact no")) {
                    Toast.makeText(this, "Please enter phone number", Toast.LENGTH_LONG).show();
                } else {
                    if (edtHouse.getText().toString().equals("") || edtHouse.getText().toString().equals("House")) {
                        Toast.makeText(this, "Please enter house number", Toast.LENGTH_LONG).show();
                    } else {
                        if (edtStreet.getText().toString().equals("") || edtStreet.getText().toString().equals("Street")) {
                            Toast.makeText(this, "Please enter street", Toast.LENGTH_LONG).show();
                        } else {
                            if (edtCity.getText().toString().equals("") || edtCity.getText().toString().equals("City")) {
                                Toast.makeText(this, "Please enter city name", Toast.LENGTH_LONG).show();
                            } else {
                                if (edtPostCode.getText().toString().equals("") || edtPostCode.getText().toString().equals("Post code")) {
                                    Toast.makeText(this, "Please enter post code", Toast.LENGTH_LONG).show();
                                } else {


                                    if (edtPostCode.getText().toString().equals("") || edtPostCode.getText().toString().equals("Post code")) {
                                        Toast.makeText(this, "Please enter state name", Toast.LENGTH_LONG).show();
                                    } else
                                        {
                                        Constants.addressMap = new HashMap<>();

                                            Pref pref= new Pref(getApplicationContext());

                                        Constants.addressMap.put("Full_name", edtName.getText().toString());
                                        Constants.addressMap.put("Email_id", edtEmail.getText().toString());
                                        Constants.addressMap.put("User_id", getUserId());
                                        Constants.addressMap.put("Contact_no", edtPhoneNo.getText().toString());
                                        Constants.addressMap.put("House", edtHouse.getText().toString());
                                        Constants.addressMap.put("Street", edtStreet.getText().toString());
                                        Constants.addressMap.put("City", edtCity.getText().toString());
                                        Constants.addressMap.put("Post_code",edtPostCode.getText().toString());
                                        Constants.addressMap.put("State", "");
                                        Constants.addressMap.put("Delivery_detail",pref.getINSTRUCATION());
                                        result = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
    public String getUserId(){
        String userId=new String();
        try {
            JSONObject jObj=new JSONObject(Constants.userData);
            userId=jObj.getString("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userId;
    }
    public void init() {
        edtPostCode = (EditText) findViewById(R.id.edt_pin_code_address_page);
        edtName = (EditText) findViewById(R.id.edt_full_name);
        edtEmail = (EditText) findViewById(R.id.edt_email_id);
        edtPhoneNo = (EditText) findViewById(R.id.edt_phone_no);
        edtHouse = (EditText) findViewById(R.id.edt_house);
        edtStreet = (EditText) findViewById(R.id.edt_street);
        edtCity = (EditText) findViewById(R.id.edt_city);
        edtState = (EditText) findViewById(R.id.edt_state);
        edtDeliveryNote = (EditText) findViewById(R.id.edt_delivery_note);
        radioPayOnline = (RadioButton) findViewById(R.id.radio_pay_online);
        radioCashOnDelivery = (RadioButton) findViewById(R.id.radio_cash_on_delivery);
        radioGroupPayMentOption = (RadioGroup) findViewById(R.id.radio_group_payment_option);
        btnSubmitAddress = (Button) findViewById(R.id.btn_submit_delivery_address);
        llBack=(LinearLayout)findViewById(R.id.ll_back);
        llHome=(LinearLayout)findViewById(R.id.ll_home);
        txtCategoryName = (TextView) findViewById(R.id.txt_activity_name);
        txtQuantity = (TextView) findViewById(R.id.txt_item_quantity);
        llOptionDelivery=(LinearLayout)findViewById(R.id.option_delivery);
        textaddress=(TextView)findViewById(R.id.textadd);
        collecttext=(TextView)findViewById(R.id.textdelevery);
        title_delivery=(TextView)findViewById(R.id.title_delivery);
        Typeface typeface= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/aaa.ttf");
        collecttext.setTypeface(typeface);
        Typeface typeface1= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/aaa.ttf");
        title_delivery.setTypeface(typeface1);
        Amount=(TextView)findViewById(R.id.amount);
        Discount=(TextView)findViewById(R.id.discount);
        Deliverycharges=(TextView)findViewById(R.id.delivery_charge);
        Netpayble=(TextView)findViewById(R.id.net_payable);
        deleverytype=(TextView)findViewById(R.id.deleverytype);
        back=(LinearLayout)findViewById(R.id.menusub);
        wishtext=(TextView)findViewById(R.id.wishquanity);
        wishlist=(LinearLayout)findViewById(R.id.wishlist);
        name=(TextView)findViewById(R.id.name);
        imageView=(ImageView)findViewById(R.id.speec);
        imageView.setVisibility(View.GONE);
        name.setText("Check Out");


        if(Constants.OnlinePaymentEnable.equals("0"))
        {
            radioPayOnline.setVisibility(View.GONE);
        }
        else
        {
            radioPayOnline.setVisibility(View.VISIBLE);

        }

        radioPayOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                popup("Please Pay the Driver at your Door with  Card or Cash Thanks");

            }
        });

       /* radioGroupPayMentOption.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                if (checkedId == R.id.radio_pay_online)
                {
                    Log.d(TAG,"online payment");

                 popup("You can payment thorough card machine at the time of the delivery");

                   //  Toast.makeText(getApplicationContext(),"You can payment thorough card machine at the time of the delivery",Toast.LENGTH_LONG).show();

                 *//*   radioPayOnline.setChecked(true);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DeliveryDetailAddress.this);
                    alertDialogBuilder.setMessage("You can payment thorough card machine at the time of the delivery");

                            alertDialogBuilder.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {

                                            Log.d(TAG,"11111111111111");


                                           // Toast.makeText(DeliveryDetailAddress.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                                        }

                                    });

                  *//**//*  alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
*//**//*

                    radioCashOnDelivery.setChecked(false);
                    Log.d(TAG,"22222222222222");
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                    Log.d(TAG,"3333333333333333");
*//*
















                }
                else
                {
                    Log.d(TAG,"pay later");
                }



            }
        });*/







        fillUserData();
    }


/*    public  void popup(String msg) {

        LayoutInflater inflater = LayoutInflater.from(DeliveryDetailAddress.this);
        View subView = inflater.inflate(R.layout.popup5, null);
        TextView textView= subView.findViewById(R.id.msg);
        RelativeLayout relativeLayout123= subView.findViewById(R.id.relative111);
        textView.setText(msg);
        final Dialog mBottomSheetDialog = new Dialog(DeliveryDetailAddress.this,R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(subView);
        //mBottomSheetDialog.setCancelable(true);
       // mBottomSheetDialog.setCanceledOnTouchOutside(false);
        mBottomSheetDialog.setTitle("");
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.CENTER);
        mBottomSheetDialog.show();

        relativeLayout123.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG,"11111111111");

                mBottomSheetDialog.dismiss();
                radioPayOnline.setChecked(false);
                radioCashOnDelivery.setChecked(true);





            }
        });







    }*/


    public  void popup(String Messges){
        LayoutInflater inflater = LayoutInflater.from(DeliveryDetailAddress.this);
        View view = inflater.inflate(R.layout.custom, null);
        TextView textView=(TextView)view.findViewById(R.id.tv);
        RelativeLayout relativeLayout= view.findViewById(R.id.layout_btn);
        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/aaa.ttf");
        textView.setTypeface(face);
        textView.setText(Messges);
        final AlertDialog.Builder builder = new AlertDialog.Builder(DeliveryDetailAddress.this);

        builder.setView(view);
    builder.setCancelable(false);
       final AlertDialog alertDialog = builder.create();

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                alertDialog.dismiss();


            }
        });



        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                radioPayOnline.setChecked(false);
                radioCashOnDelivery.setChecked(true);
                alertDialog.dismiss();

            }
        });


        builder.show();



    }




}
