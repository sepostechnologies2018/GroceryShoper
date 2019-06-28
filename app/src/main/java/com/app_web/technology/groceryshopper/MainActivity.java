package com.app_web.technology.groceryshopper;  // com.app_web.technology.groceryshopper

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.app_web.technology.groceryshopper.Activitys.Home;
import com.app_web.technology.groceryshopper.Addapter.RecyclerAdapter;
import com.app_web.technology.groceryshopper.Database.DataBaseHelper;
import com.app_web.technology.groceryshopper.Database.Wishlistdatabase;
import com.app_web.technology.groceryshopper.Fragments.Contactus;
import com.app_web.technology.groceryshopper.Fragments.More;
import com.app_web.technology.groceryshopper.Fragments.Notification;
import com.app_web.technology.groceryshopper.Fragments.Shopingcart;
import com.app_web.technology.groceryshopper.Fragments.Wishlist;
import com.app_web.technology.groceryshopper.GetterSetter.Album;
import com.app_web.technology.groceryshopper.Model.DesboardModel;
import com.app_web.technology.groceryshopper.interfaces.API;
import com.app_web.technology.groceryshopper.util.AppController;
import com.app_web.technology.groceryshopper.util.Constants;
import com.app_web.technology.groceryshopper.util.Pref;
import com.app_web.technology.groceryshopper.util.VolleyMethods;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();


    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerAdapter mAdapter;
    private Context context;
    LinearLayout wishlist, shop_cart, search, contact_us, more, notification;
    private ArrayList<Album> feedItemList;
    TextView postalcodetext, skip;
    EditText Postalcodeedite;
    ProgressDialog pDialog;
    public static String string = "";
    Retrofit retrofit;
    String s1, s;
    String currentVersion;
    String playstoreVersion;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog= new ProgressDialog(this);
        progressDialog.setTitle("loading...");
        progressDialog.show();


        retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        VolleyMethods volleyMethods=new VolleyMethods(this);
        volleyMethods.getStoreStatus();

      //  volleyMethods.update("Grocery Shopper", "3.0");


        PackageManager packageManager = this.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
            Log.d("@1234", packageInfo.toString());
            s = packageInfo.versionName.toString();
            string = s;
            Resources appR = this.getResources();
            CharSequence txt = appR.getText(appR.getIdentifier("app_name",
                    "string", this.getPackageName()));
            s1 = txt.toString();



        } catch (PackageManager.NameNotFoundException ex) {
        } catch (Exception e) {
            String me = e.getMessage().toString();

        }


        DesboardApi("Grocery Shopper", "3.0");

        getSupportActionBar().hide();
        postalcodetext = (TextView) findViewById(R.id.button);
        skip = (TextView) findViewById(R.id.skip);
        Postalcodeedite = (EditText) findViewById(R.id.editText);
        //ab251er
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (!isNetworkAvailable()) {
            // Create an Alert Dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Set the Alert Dialog Message
            builder.setMessage("Internet Connection Required")
                    .setCancelable(false)
                    .setPositiveButton("Retry",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                }
                            });
            builder.setNegativeButton("Discard",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int id) {

                            MainActivity.this.finish();

                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            getApplicationVersionName(MainActivity.this);

        }
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.type = true;
                Constants.postal = new String("");
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                final String parm = sharedPreferences.getString("Address", "");

                if (parm.length() == 0) {
                    try {
                        DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this);
                        dbHelper.deleteOrder();
                        Wishlistdatabase wishlistdatabase = new Wishlistdatabase(MainActivity.this);
                        wishlistdatabase.deleteOrder();
                    } catch (Exception e) {

                    }
                    Constants.isUserLogIn = false;
                    Intent in = new Intent(MainActivity.this, Home.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(in);

                } else {
                    try {
                        try {
                            DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this);
                            dbHelper.deleteOrder();
                            Wishlistdatabase wishlistdatabase = new Wishlistdatabase(MainActivity.this);
                            wishlistdatabase.deleteOrder();
                        } catch (Exception e) {
                        }
                        Constants.userData = parm;
                        Constants.isUserLogIn = true;
                        JSONObject jsonObject = new JSONObject(parm);

                        Pref pref = new Pref(MainActivity.this);
                        pref.setUserId(jsonObject.getString("user_id"));
                        Intent in = new Intent(MainActivity.this, Home.class);
                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(in);
                    } catch (JSONException j) {

                    }
                }
            }
        });
        postalcodetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Postalcodeedite.getText().toString().length() == 0) {

                    Toast.makeText(MainActivity.this, "Enter PostCode", Toast.LENGTH_LONG).show();


                } else if (Postalcodeedite.getText().length() < 6) {
                    Toast.makeText(getApplicationContext(), "Post code should be greter then 6 characher!", Toast.LENGTH_SHORT).show();
                } else if (Postalcodeedite.getText().length() > 8) {
                    Toast.makeText(getApplicationContext(), "Post code should be less then  8 characher!", Toast.LENGTH_SHORT).show();
                } else {

                    Constants.postal = new String("");
                    Constants.postal = Postalcodeedite.getText().toString();
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    final String parm = sharedPreferences.getString("Address", "");
                    if (parm.length() == 0) {
                        Constants.isUserLogIn = false;
                        VolleyMethods vm = new VolleyMethods(MainActivity.this);
                        HashMap<String, String> userInfo = new HashMap<String, String>();
                        userInfo.put("postcode", Postalcodeedite.getText().toString());
                        Postalcodeedite.setText("");
                        vm.postalcode(userInfo, "0");

                    } else {
                        Constants.userData = parm;
                        Constants.isUserLogIn = true;
                        VolleyMethods vm = new VolleyMethods(MainActivity.this);
                        HashMap<String, String> userInfo = new HashMap<String, String>();
                        userInfo.put("postcode", Postalcodeedite.getText().toString());
                        Constants.postal = new String("");
                        Constants.postal = Postalcodeedite.getText().toString();
                        Postalcodeedite.setText("");
                        vm.postalcode(userInfo, "0");
                    }
                }

            }
        });

    }

    public static void getApplicationVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            Log.d("@1234", packageInfo.toString());
            String s = packageInfo.versionName.toString();
            string = s;
            Resources appR = context.getResources();
            CharSequence txt = appR.getText(appR.getIdentifier("app_name",
                    "string", context.getPackageName()));
            String s1 = txt.toString();
            VolleyMethods vm = new VolleyMethods(context);
            //  vm.update(s1,s);


        } catch (PackageManager.NameNotFoundException ex) {
        } catch (Exception e) {
            String me = e.getMessage().toString();

        }

    }

    public void maintenance() {
       /* LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.maintenance, null);
        final Dialog mBottomSheetDialog = new Dialog(this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.CENTER);
        mBottomSheetDialog.show();*/
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.maintenance, null);
        final Dialog mBottomSheetDialog = new Dialog(this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.CENTER);
        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                MainActivity.this.finish();
            }
        });


    }


    public void cartitemsheet(final String PlayStoreVersion) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.updatesheet, null);
        TextView textView = (TextView) view.findViewById(R.id.version);
        Button update = (Button) view.findViewById(R.id.update);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/aaa.ttf");
        textView.setText("Current Version " + s);
        update.setText("Update Latest Version " +PlayStoreVersion);
        textView.setTypeface(face);
        final Dialog mBottomSheetDialog = new Dialog(this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.CENTER);
        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                MainActivity.this.finish();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if (PlayStoreVersion.equals(string)) {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    final String parm = sharedPreferences.getString("Address", "");
                    if (parm.length() == 0) {
                        Constants.isUserLogIn = false;
                    } else {
                        Constants.userData = parm;
                        Constants.isUserLogIn = true;
                    }


                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.app_web.technology.groceryshopper"));
                    startActivity(intent);

                }

            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shop_cart: {
                Shopingcart frag = new Shopingcart();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.add(R.id.mainlayout, frag, "Shopingcart");
                transaction.addToBackStack(null);
                transaction.commit();

            }
            break;

            case R.id.wishlist: {
                Wishlist frag = new Wishlist();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.add(R.id.mainlayout, frag, "Wishlist");
                transaction.addToBackStack(null);
                transaction.commit();
            }
            break;

            case R.id.contact_us: {
                Contactus frag = new Contactus();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.add(R.id.mainlayout, frag, "Contactus");
                transaction.addToBackStack(null);
                transaction.commit();
            }
            break;

            case R.id.more: {

                More frag = new More();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.add(R.id.mainlayout, frag, "More");
                transaction.addToBackStack(null);
                transaction.commit();


            }
            case R.id.notification: {

                Notification frag = new Notification();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.add(R.id.mainlayout, frag, "More");
                transaction.addToBackStack(null);
                transaction.commit();


            }
            break;


        }
    }

    public void init() {


    }


    private void DesboardApi(String appname, final String version) {

        API api = retrofit.create(API.class);

        Call<DesboardModel> data = api.desboardData("Grocery Shopper", s, "Android");
        data.enqueue(new Callback<DesboardModel>() {
            @Override
            public void onResponse(Call<DesboardModel> call, retrofit2.Response<DesboardModel> response) {

                Log.d(TAG, "response:" + new Gson().toJson(response.body()));

                String responsee = new Gson().toJson(response.body());
                if (response.body() != null) {
                    progressDialog.dismiss();
                    if (response.body().getIsHardStop().toString().equals("0")) {
                        if (response.body().getMaintenance().toString().equals("0")) {
                          /*  SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                            final String parm = sharedPreferences.getString("Address", "") ;*/
                            Constants.minimum_delivery_amount= response.body().getMinimumDeliveryAmount();
                            Constants.phoneNo = response.body().getPhoneNumber();
                            Constants.SAND_BOX_ID = response.body().getSendBoxId();
                            Constants.PRODUCTION_ID = response.body().getProductionId();
                            Constants.isTifinEnable = response.body().getIsPayPalEnvironmentProduction();
                            Constants.OnlinePaymentEnable = response.body().getOnlinePaymentEnable();
                            String isCreditCard = response.body().getIsCreditCard();
                            Log.d(TAG, "isCreditCard" + isCreditCard);
                            Pref pref = new Pref(getApplicationContext());
                            if (isCreditCard.equals("1")) {
                                pref.setIsCreditCard(true);
                            } else {
                                pref.setIsCreditCard(false);
                            }


                            if (response.body().getIsPayPalEnvironmentProduction().equals("1")) {
                                Constants.Paypal_id = response.body().getProductionId();

                            } else {
                                Constants.mode = false;
                                Constants.Paypal_id = "AUgrvDW4858xrcFHQCC6LDYF4Fkf63S7VNsUOGVLPCZrV3ZELJZKoiTWgKTkT-LySbfS103cGdBR5bwJ";

                            }


                          /*  if(parm.length()==0){
                                Constants.isUserLogIn=false;
                                context.startActivity(new Intent(context, Home.class));
                            }
                            else {
                                Constants.userData=parm;
                                Constants.isUserLogIn=true;
                                context.startActivity(new Intent(context, Home.class));
                            }*/


                        } else {

                            maintenance();

                        }




                    }


                    else {
                        if (response.body().getMaintenance().toString().equals("0")) {
                            String PlayStoreVersion=response.body().getPlayStoreVersion();

                            Log.d(TAG,"PlayStoreVersion"+PlayStoreVersion);

                            cartitemsheet(PlayStoreVersion);
                        } else {
                            maintenance();
                        }


                    }


                }


            }

            @Override
            public void onFailure(Call<DesboardModel> call, Throwable t) {

            }
        });


    }


    public class GetVersionCode extends AsyncTask<Void, String, String> {

        @Override

        protected String doInBackground(Void... voids) {

            String newVersion = null;

            try {

                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName() + "&hl=it").timeout(30000)

                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")


                        .referrer("http://www.google.com")

                        .get()
                        .select("div[itemprop=softwareVersion]")
                        .first()
                        .ownText();

                return newVersion;

            } catch (Exception e) {

                return newVersion;

            }

        }


        @Override

        protected void onPostExecute(String onlineVersion) {

            super.onPostExecute(onlineVersion);

            if (onlineVersion != null && !onlineVersion.isEmpty()) {

                if (Float.valueOf(currentVersion) < Float.valueOf(onlineVersion)) {

                    //show dialog

                }

            }

            Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);
            playstoreVersion= onlineVersion;

        }


    }
}