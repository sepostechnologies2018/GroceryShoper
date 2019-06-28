package com.app_web.technology.groceryshopper.Activitys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app_web.technology.groceryshopper.Database.DataBaseHelper;
import com.app_web.technology.groceryshopper.R;
import com.app_web.technology.groceryshopper.util.Constants;
import com.app_web.technology.groceryshopper.util.PayPalConfig;
import com.app_web.technology.groceryshopper.util.Pref;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class
PayMentActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = PayMentActivity.class.getSimpleName();

    Button btnCancel,btnConfirm;
    public static TextView response;
    TextView amount,deliveryType,paymentMethod,Discount;
    public static TextView total_amount,delivery_charge,discounttext;
    DataBaseHelper dbHelper;
    public static String imei,dType;
    ProgressDialog pDialog;
    private String paymentAmount;
    String discount,deliveryCharge;
    public static final int PAYPAL_REQUEST_CODE = 123;
    private static PayPalConfiguration config;


/*
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId("AUgrvDW4858xrcFHQCC6LDYF4Fkf63S7VNsUOGVLPCZrV3ZELJZKoiTWgKTkT-LySbfS103cGdBR5bwJ")
            .acceptCreditCards(false)
            .merchantName("Grocery Shopper");*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_ment);
        Pref pref= new Pref(getApplicationContext());
        if (Constants.mode) {
            config = new PayPalConfiguration()
                    .environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)
                    .clientId(Constants.Paypal_id)
                    .acceptCreditCards(pref.getIsCreditCard())
                    .merchantName("Grocery Shopper");
            Log.d(TAG,"PRODUCATION  ID:"+Constants.Paypal_id);
        }
        else
        {
            config = new PayPalConfiguration()
                    .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                    .clientId(Constants.Paypal_id)
                    .acceptCreditCards(pref.getIsCreditCard())
                    .merchantName("Grocery Shopper");

            Log.d(TAG,"SANDbOX ID:"+Constants.Paypal_id);

        }









        init();


        DataBaseHelper dbHelper = new DataBaseHelper(this);
        Bundle bundle = dbHelper.getItemQuantity();
        Constants.totalAmount=bundle.getString("TotalPrice");
        Float t=Float.parseFloat(bundle.getString("TotalPrice"));
        Float tg=Float.parseFloat(bundle.getString("discount"));
        Float yu=t+tg;
        amount.setText("£ " +yu );

        if(getIntent().getExtras().getString("DeliveryType").equals("D"))
            deliveryType.setText("Delivery");
        else
            deliveryType.setText("Collection");
        paymentMethod.setText("ONLINE");
        discounttext.setText("£ " + bundle.getString("discount"));

        total_amount.setText("£ " + bundle.getString("TotalPrice"));
       // defineWidget();
        btnConfirm.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }



    private void getPayment() {


        String t=Constants.totalAmount;
        paymentAmount =  Constants.totalAmount;
        PayPalPayment payment = new PayPalPayment((new BigDecimal(String.valueOf(paymentAmount))), "GBP", "Grocery Shopper",
                PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);

                        //Starting a new activity for the payment details and also putting the payment details with intent
                        startActivity(new Intent(this, OrderReviewPage.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("DeliveryType", getIntent().getExtras().get("DeliveryType").toString())
                                .putExtra("PaymentType", "ONLINE")
                                .putExtra("Time", "")
                                .putExtra("PaymentAmount", paymentAmount));

                    } catch (JSONException e) {


                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

    //Method to reference XML widgets
    public void init(){
        dbHelper = new DataBaseHelper(this);
        btnCancel=(Button)findViewById(R.id.btn_cancel_payment);
        btnConfirm=(Button)findViewById(R.id.btn_confirm_payment);
        amount = (TextView) findViewById(R.id.paypal_amount);
        discounttext = (TextView) findViewById(R.id.paypal_discount);
        delivery_charge = (TextView) findViewById(R.id.paypal_delivery_charge);
        total_amount = (TextView) findViewById(R.id.paypal_total_amount);
        deliveryType = (TextView) findViewById(R.id.paypal_delivery_type);
        paymentMethod = (TextView) findViewById(R.id.paypal_payment_method);
        response = (TextView) findViewById(R.id.response);
        Discount=(TextView)findViewById(R.id.discount);
        discounttext.setText(Constants.dis+"%");
    }

    //
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_cancel_payment:
                {
                this.finish();
            }break;

            case R.id.btn_confirm_payment:{
                //Proceed to Payment.
                getPayment();
            }
        }
    }

    public  void Mode(){

    }
}
