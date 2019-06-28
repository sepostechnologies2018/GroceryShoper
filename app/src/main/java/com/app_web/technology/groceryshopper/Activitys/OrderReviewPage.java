package com.app_web.technology.groceryshopper.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app_web.technology.groceryshopper.R;
import com.app_web.technology.groceryshopper.util.Constants;
import com.app_web.technology.groceryshopper.util.VolleyMethods;

import org.json.JSONException;
import org.json.JSONObject;

public class OrderReviewPage extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG= OrderReviewPage.class.getSimpleName();

    public static TextView txtResponse;
    LinearLayout llHome,llBack;
    TextView txtCategoryName,textlog;
    ImageView logimg;
    public static TextView txtQuantity;
    LinearLayout llMenu, llAboutUs, llContactUs, llCheckOut,llFindOut;;
    TextView login;
    ImageView imageView;
    private AlertDialog myDialog;
    private View alertView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_review_page);
        //Code to Hide Action Bar
        getSupportActionBar().hide();
        init();

        if(getIntent().getExtras().get("PaymentType").toString().equals("ONLINE")){
            Intent intent = getIntent();
            try {
                JSONObject jsonDetails = new JSONObject(intent.getStringExtra("PaymentDetails"));
                showDetails(jsonDetails.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));

            } catch (JSONException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        else{


            VolleyMethods vm=new VolleyMethods(this);
            Constants.paymentStatus = "offline";
            Constants.Transaction_id = "";

            Log.d(TAG,"DeliveryType"+getIntent().getExtras().get("DeliveryType").toString());



            vm.placeOrder(getIntent().getExtras().get("DeliveryType").toString(),getIntent().getExtras().get("PaymentType").toString(),"","");
        }





        /*VolleyMethods vm=new VolleyMethods(this);
        Constants.paymentStatus = "offline";
        Constants.Transaction_id = "";
        vm.placeOrder(getIntent().getExtras().get("DeliveryType").toString(),getIntent().getExtras().get("PaymentType").toString(),getIntent().getExtras().get("Time").toString());*/
    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }



    private void showDetails(JSONObject jsonDetails, String paymentAmount) throws JSONException {


        if(jsonDetails.getString("state").equals("approved")){
            Constants.paymentStatus = "approved";
            Constants.Transaction_id = jsonDetails.getString("id");
            VolleyMethods vm=new VolleyMethods(this);

            Log.d(TAG,"DeliveryType"+getIntent().getExtras().get("DeliveryType").toString());

            vm.placeOrder(getIntent().getExtras().get("DeliveryType").toString(),getIntent().getExtras().get("PaymentType").toString(),"","");
        }
        else {
            Constants.paymentStatus = "offline";
            Constants.Transaction_id = "";
            VolleyMethods vm=new VolleyMethods(this);

            Log.d(TAG,"DeliveryType"+getIntent().getExtras().get("DeliveryType").toString());


            vm.placeOrder(getIntent().getExtras().get("DeliveryType").toString(),getIntent().getExtras().get("PaymentType").toString(),"","");
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        Constants cons = new Constants();


    }

    public void init(){
        llBack=(LinearLayout)findViewById(R.id.ll_back);
        llHome=(LinearLayout)findViewById(R.id.ll_home);
        txtCategoryName = (TextView) findViewById(R.id.txt_activity_name);
        txtQuantity = (TextView) findViewById(R.id.txt_item_quantity);
        imageView=(ImageView)findViewById(R.id.speec);
        imageView.setVisibility(View.GONE);

    }

    //Click Listener of Widgets
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:{
                finish();
            }break;


        }
    }

    public  void popuptext1(String Messges){
        LayoutInflater inflater = LayoutInflater.from(OrderReviewPage.this);
        View view = inflater.inflate(R.layout.dialog_layout_resonse, null);
        TextView textView=(TextView)view.findViewById(R.id.response);
        textView.setText(Messges);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(alertView);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.drawable.logo);
        builder.setView(view);
        builder.setCancelable(false);
        myDialog=builder.create();
        final AlertDialog alertDialog = builder.create();
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                alertDialog.dismiss();
                Intent intent=new Intent(OrderReviewPage.this, Home.class);
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        builder.show();

    }

}
