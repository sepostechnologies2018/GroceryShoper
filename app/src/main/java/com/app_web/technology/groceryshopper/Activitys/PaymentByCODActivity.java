package com.app_web.technology.groceryshopper.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app_web.technology.groceryshopper.Database.DataBaseHelper;
import com.app_web.technology.groceryshopper.R;
import com.app_web.technology.groceryshopper.util.Constants;

public class PaymentByCODActivity extends AppCompatActivity  implements View.OnClickListener {

    private static final String TAG= PaymentByCODActivity.class.getSimpleName();


    Button btnCancel,btnConfirm;
    public static TextView response;
    TextView amount,deliveryType,paymentMethod,Discount;
    public static TextView total_amount,delivery_charge,discounttext,Delivery_Charge;
    DataBaseHelper dbHelper;
    public static String imei,dType;
    ProgressDialog pDialog;
    private String paymentAmount;
    String discount,deliveryCharge;
    public static final int PAYPAL_REQUEST_CODE = 123;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_by_cod);

        init();


        DataBaseHelper dbHelper = new DataBaseHelper(this);
        Bundle bundle = dbHelper.getItemQuantity();
        Constants.totalAmount=bundle.getString("TotalPrice");
        Float t=Float.parseFloat(bundle.getString("TotalPrice"));
        Float tg=Float.parseFloat(bundle.getString("discount"));
        Float yu=t+tg;
        amount.setText("£ " + String.format("%.2f",yu ) );

        if(getIntent().getExtras().getString("DeliveryType").equals("D"))
        {
            deliveryType.setText("Delivery");
            discounttext.setText("£ " + bundle.getString("discount"));
            deliveryType.setText("Delivery");
            Delivery_Charge.setText("£ " +Constants.DeliveryCharge);
            Float tt=Float.parseFloat(bundle.getString("TotalPrice"));

            Log.d(TAG,"DeliveryCharge"+Constants.DeliveryCharge);

          //  total_amount.setText("£ " +String.valueOf(tt+Float.parseFloat(Constants.DeliveryCharge)));
            total_amount.setText("£"+String.format ("%.2f", tt+Float.parseFloat(Constants.DeliveryCharge)));

            Log.d(TAG,"DDDDDDDDDDDDDDD");

        }

        else
        {
            deliveryType.setText("Collection");
            paymentMethod.setText("Offline");
            discounttext.setText("£ " + bundle.getString("discount"));
            Delivery_Charge.setText("£0.00");
            Float tt=Float.parseFloat(bundle.getString("TotalPrice"));
            total_amount.setText("£ " +String.valueOf(tt+0.00f));

            Log.d(TAG,"CCCCCCCCCCCCCCCCC");
        }



        // defineWidget();
        btnConfirm.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

    }

    private void init()
    {
        dbHelper = new DataBaseHelper(this);
        btnCancel=(Button)findViewById(R.id.btn_cancel_payment);
        btnConfirm=(Button)findViewById(R.id.btn_confirm_payment);
        amount = (TextView) findViewById(R.id.paypal_amount);
        discounttext = (TextView) findViewById(R.id.paypal_discount);
        delivery_charge = (TextView) findViewById(R.id.paypal_delivery_charge);
        total_amount = (TextView) findViewById(R.id.paypal_total_amount);
        deliveryType = (TextView) findViewById(R.id.paypal_delivery_type);
        paymentMethod = (TextView) findViewById(R.id.paypal_payment_method);
        Delivery_Charge= findViewById(R.id.Delivery_Charge);
        response = (TextView) findViewById(R.id.response);
        Discount=(TextView)findViewById(R.id.discount);
        discounttext.setText(Constants.dis+"%");


    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {

            case R.id.btn_cancel_payment:

                this.finish();

                break;

            case R.id.btn_confirm_payment:


   Intent in = new Intent(this, OrderReviewPage.class);
                                in.putExtra("DeliveryType", getIntent().getExtras().get("DeliveryType").toString());
                                in.putExtra("PaymentType", "COD");
                                startActivity(in);
                                this.finish();


                break;


        }

    }
}
