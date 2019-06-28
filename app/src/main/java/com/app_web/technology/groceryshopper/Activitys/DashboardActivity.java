package com.app_web.technology.groceryshopper.Activitys;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.app_web.technology.groceryshopper.MainActivity;
import com.app_web.technology.groceryshopper.R;
import com.app_web.technology.groceryshopper.util.AppController;
import com.app_web.technology.groceryshopper.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {
    private static final String TAG= DashboardActivity.class.getCanonicalName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        update("Grocery Shopper", "3.0");
    }



    public void update(final String AppName,final String version) {

        String tag_string_req = "update";

        String url ="http://www.satyamtechnologies.co.uk/Dashboard/apps_data/check_app_version_api.php";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,"TAG@123:"+response);
                Log.d(TAG,"RESPONSE:"+response);
                String op=response;


                try{
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.getString("IsHardStop").equals("0"))
                    {

                        if(jsonResponse.getString("maintenance").equals("0"))
                        {



                            Constants.minimum_delivery_amount= jsonResponse.getString("minimum_delivery_amount");
                            Constants.phoneNo=  jsonResponse.getString("phone_number");
                            Constants.SAND_BOX_ID= jsonResponse.getString("sendBoxId");
                            Constants.PRODUCTION_ID= jsonResponse.getString("productionId");
                            Constants.isTifinEnable= jsonResponse.getString("isPayPalEnvironmentProduction");
                            Constants.OnlinePaymentEnable= jsonResponse.getString("OnlinePaymentEnable");


                            if(jsonResponse.getString("isPayPalEnvironmentProduction").equals("1"))
                            {
                                Constants.Paypal_id= jsonResponse.getString("productionId");

                            }
                            else
                            {
                                Constants.mode=false;
                                Constants.Paypal_id= "AUgrvDW4858xrcFHQCC6LDYF4Fkf63S7VNsUOGVLPCZrV3ZELJZKoiTWgKTkT-LySbfS103cGdBR5bwJ";

                            }



                     /*       if(parm.length()==0){
                                Constants.isUserLogIn=false;
                                context.startActivity(new Intent(context, Home.class));
                            }
                            else {
                                Constants.userData=parm;
                                Constants.isUserLogIn=true;
                                context.startActivity(new Intent(context, Home.class));
                            }*/



                        }else {

                          //  ((MainActivity)context).maintenance();

                        }


                    }
                    else {



                        if(jsonResponse.getString("maintenance").toString().equals("0")){

                            String PlayStoreVersion=jsonResponse.getString("PlayStoreVersion").toString();
                            //  ((MainActivity)context).cartitemsheet(PlayStoreVersion);

                        }

                        else {
                          //  ((MainActivity)context).maintenance();
                        }

                    }
                }catch (JSONException e){
                    Log.d(TAG,"ERROROROR:"+ e.getMessage());

                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                Log.d(TAG,"ERROROROR:"+ error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("AppName", AppName);
                params.put("version", version);
                params.put("os", "Android");
                String dd=params.toString();
                Log.d("TAG", dd);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
