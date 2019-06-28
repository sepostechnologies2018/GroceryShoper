package com.app_web.technology.groceryshopper.Activitys;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.app_web.technology.groceryshopper.Addapter.CustomAdapterter;
import com.app_web.technology.groceryshopper.Addapter.RecyclerAdapter;
import com.app_web.technology.groceryshopper.Database.DataBaseHelper;
import com.app_web.technology.groceryshopper.Fragments.Contactus;
import com.app_web.technology.groceryshopper.GetterSetter.Album;
import com.app_web.technology.groceryshopper.GetterSetter.CountreGetterSetter;
import com.app_web.technology.groceryshopper.Model.GetDataAdapter;
import com.app_web.technology.groceryshopper.R;
import com.app_web.technology.groceryshopper.util.Constants;
import com.app_web.technology.groceryshopper.util.Pref;
import com.app_web.technology.groceryshopper.util.VolleyMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener,SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG= Home.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerAdapter mAdapter;
    CustomAdapterter mAdaptere;
    private Context context;
    ImageView cancked;
    public static String checkInTimeHome;
    RequestQueue requestQueue ;
    RecyclerView.Adapter recyclerViewadapter;
    LinearLayout wishlist,shop_cart,search,mainmenu,contact_us,more,notification,menu,search_boxd;
    public ArrayList<Album> feedItemList;
    EditText searchBox;
    DrawerLayout drawer;
    private List<CountreGetterSetter> feedItems;
    private int mDataSetTypes[] = {-1, 1, 2, 3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31}; //view types
    private View navHeader;
    private TextView txtName, txtWebsite,
            txt_item_price,
            txt_item_quantity,wishtext;
    NavigationView navigationView;
    ProgressDialog pDialog;
    JsonArrayRequest jsonArrayRequest ;
    List<GetDataAdapter> GetDataAdapter1;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String[] mDataset = {"29 degrees", "Seahawks 24 - 27 Bengals",
            "Flash missing, vanishes in crisis", "Half Life 3 announced"};
    //String GET_JSON_DATA_HTTP_URL = "http://grocerydrop.co.uk/apps_data/category.php";
    String GET_JSON_DATA_HTTP_URL = "https://www.groceryshopper.info/apps_data/country.php";
    String banner = "https://www.groceryshopper.info/apps_data/banners_android_api.php";
    public static DataBaseHelper dbHelper;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    private ImageView imgNavHeaderBg, imgProfile;
    Pref pref;
    private static final String urlNavHeaderBg = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";
    private static final String urlProfileImg = "https://scontent.fdel1-1.fna.fbcdn.net/v/t1.0-9/13925173_884318025005839_7585810614142996859_n.jpg?oh=082fe7954bcb9ba37a99f1452a605537&oe=597335FE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        pref= new Pref(this);
   /*     Runtime rt = Runtime.getRuntime();
        long maxMemory = rt.maxMemory();
        Log.v("onCreate", "maxMemory:" + Long.toString(maxMemory));



        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        int memoryClass = am.getMemoryClass();
        Log.v("onCreate", "memoryClass:" + Integer.toString(memoryClass));*/





        VolleyMethods volleyMethods=new VolleyMethods(this);
        volleyMethods.getinfo();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name1);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);
        init();
        getSupportActionBar().hide();
        GetDataAdapter1 = new ArrayList<>();
        JSON_DATA_WEB_CALL();
        shop_cart.setOnClickListener(this);
        wishlist.setOnClickListener(this);
        contact_us.setOnClickListener(this);
        notification.setOnClickListener(this);
        search.setOnClickListener(this);
        menu.setOnClickListener(this);
        more.setOnClickListener(this);
        cancked.setOnClickListener(this);
        mainmenu.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        prepareAlbumstest();

                                    }
                                }
        );


        if (navigationView != null) {
            Menu menu = navigationView.getMenu();
            if(Constants.isUserLogIn){
                menu.findItem(R.id.Logout).setTitle("Logout");
                menu.findItem(R.id.Logout).setIcon(R.drawable.logoutnavi);
            }
            else {
                menu.findItem(R.id.Logout).setTitle("Log In");
                menu.findItem(R.id.Logout).setIcon(R.drawable.logoutt);
            }

            navigationView.setNavigationItemSelectedListener(this);
        }
    }


    @Override
    public void onRefresh() {
        prepareAlbumstest();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shop_cart: {
                Intent intent = new Intent(Home.this, CheckOut.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
            break;

            case R.id.wishlist: {


                if(pref.getISuserLogin().equals("1"))
                {
                    Intent intent = new Intent(Home.this, Wishlistactivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }else {

                    Constants.logincontrol=2;
                    Intent intent = new Intent(Home.this, Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }

            }
            break;
            case R.id.notification: {
                if(pref.getISuserLogin().equals("1")){

                    Intent intent = new Intent(Home.this, Myorder.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }else {

                    Constants.logincontrol=0;
                    Intent intent = new Intent(Home.this, Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }

            }
            break;

            case R.id.contact_us: {

                Intent intent = new Intent(Home.this, Contact.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
            break;
            case R.id.cancked: {
                search_boxd.setVisibility(View.GONE);

            }
            break;


            case R.id.search: {

                search_boxd.setVisibility(View.VISIBLE);

            }
            break;
            case R.id.menu: {

                drawer.openDrawer(Gravity.LEFT);

            }
            break;
            case R.id.more: {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, R.string.app_name);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "click here to visit  https://play.google.com/store/apps/details?id=com.app_web.technology.groceryshopper");
                startActivity(Intent.createChooser(sharingIntent, "Share via"));




             /*   Intent intent = new Intent(Home.this, AboutUs.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
*/
            }
            break;
            case R.id.home: {

                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
            break;

        }
    }

    public void init(){
        txt_item_price=(TextView)findViewById(R.id.txt_item_price);
        shop_cart=(LinearLayout)findViewById(R.id.shop_cart);
        wishlist=(LinearLayout)findViewById(R.id.wishlist);
        search=(LinearLayout)findViewById(R.id.search);
        contact_us=(LinearLayout)findViewById(R.id.contact_us);
        more=(LinearLayout)findViewById(R.id.more);
        notification=(LinearLayout)findViewById(R.id.notification);
        menu=(LinearLayout)findViewById(R.id.menu);
        txt_item_quantity=(TextView)findViewById(R.id.txt_item_quantity);
        wishtext=(TextView)findViewById(R.id.wishquanitymain);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        search_boxd=(LinearLayout)findViewById(R.id.search_boxd);
        search_boxd.setVisibility(View.GONE);
        searchBox = (EditText)findViewById(R.id.search_box);
        cancked=(ImageView)findViewById(R.id.cancked);
        mainmenu=(LinearLayout)findViewById(R.id.home);
    }

    public void JSON_DATA_WEB_CALL(){

        pDialog = new ProgressDialog(Home.this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("TAG@123", response.toString());
                        banner(response);
                    }
                },
                  new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.hide();
                    }
                });

        requestQueue = Volley.newRequestQueue(this);
        jsonArrayRequest.setShouldCache(false);
        requestQueue.add(jsonArrayRequest);
    }

    public void banner(final  JSONArray jsonArray){
        jsonArrayRequest = new JsonArrayRequest(banner,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        pDialog.hide();
                        Log.d("TAG@123", response.toString());
                         Constants.banners=response.toString();
                         JSON_PARSE_DATA_AFTER_WEBCALL(jsonArray);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.hide();
                    }
                });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }



    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        feedItems = new ArrayList<CountreGetterSetter>();
        for(int i = 0; i<array.length(); i++) {
            JSONObject json = null;
            CountreGetterSetter item = new CountreGetterSetter();
            try {
                json = array.getJSONObject(i);

                item.setcountry_flag(json.getString("country_flag"));
                item.setcountry_id(json.getString("country_id"));
                item.setcountry_name(json.getString("country_name"));

                feedItems.add(item);
            } catch (JSONException e) {

                e.printStackTrace();
            }

        }
int i=feedItems.size();
        mRecyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(recyclerViewlayoutManager);
        mAdaptere = new CustomAdapterter(this,mDataset, mDataSetTypes,feedItems);
        mRecyclerView.setAdapter(mAdaptere);


    }


 @Override
    protected void onResume() {
        super.onResume();
/*
     if(Constants.OrderDisabled.equals("1"))
     {
         openBottomSheet();
     }
     else
     {

     }*/






     Constants cons = new Constants();
     String i=cons.getQuantity(this);
     dbHelper = new DataBaseHelper(this);
     Bundle bundle = dbHelper.getItemQuantity();

     txt_item_price.setText("£" + bundle.getString("TotalPrice"));
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
    protected void onPostResume() {
        super.onPostResume();
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
    protected void onRestart() {
        super.onRestart();
      /*  Intent intent = getIntent();
        finish();
        startActivity(intent);*/
        Constants cons = new Constants();
        String i=cons.getQuantity(this);

        if(i.equals("")){
            txt_item_quantity.setVisibility(View.GONE);
        }
        else {
            txt_item_quantity.setText(cons.getQuantity(this));
        }
        String j=cons.getQuantitywish(this);
        if(j.equals("")){
            wishtext.setVisibility(View.GONE);
        }
        else {
            wishtext.setText(j);}
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Intent intent = getIntent();
            finish();
            // startActivity(intent);



        }
    }
    public String getQuantity(Context context) {
        DataBaseHelper helper = new DataBaseHelper(context);
        Bundle bundle = helper.getItemQuantity();
        if (bundle.getInt("TotalItem") > 0) {
            return "" + bundle.getInt("TotalItem");
        } else {
            return "";
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

       if (id == R.id.Account) {
            Contactus frag = new Contactus();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
            transaction.add(R.id.mainlayout,frag,"Contactus");
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.About) {

            Intent intent = new Intent(Home.this, AboutUs.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);


        }else if (id == R.id.home) {

           Intent intent = getIntent();
           finish();
           startActivity(intent);

       } else if (id == R.id.Contact) {

            Intent intent = new Intent(Home.this, Contact.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);




        }

       else if (id == R.id.share) {

           Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
           sharingIntent.setType("text/plain");
           sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, R.string.app_name);
           sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "click here to visit  https://play.google.com/store/apps/details?id=com.app_web.technology.groceryshopper");
           startActivity(Intent.createChooser(sharingIntent, "Share via"));



       }



       else if (id == R.id.Logout) {

           if(Constants.isUserLogIn){
               popup();


           }else {
               Menu menu = navigationView.getMenu();
               menu.findItem(R.id.Logout).setTitle("Logout");
               menu.findItem(R.id.Logout).setIcon(R.drawable.logoutnavi);
               drawer.closeDrawer(GravityCompat.START);
               Constants.logincontrol=0;
               Intent intent = new Intent(Home.this, Login.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(intent);

           }
       }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public  void popup(){

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle("");
        builder.setCancelable(false);
        builder.setMessage("Do you want to logout");
        builder.setPositiveButton("OK", null);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Constants.isUserLogIn=false;
                        Menu menu = navigationView.getMenu();
                        menu.findItem(R.id.Logout).setTitle("Log In");
                        menu.findItem(R.id.Logout).setIcon(R.drawable.logoutt);
                        Constants.userData=new String("");
                        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = shared.edit();
                        editor.putString("MailId",  "");
                        editor.putString("full_name", "");
                        editor.putString("Profile",  "");
                        editor.putString("Address", "");
                        editor.commit();
                        finish();
                    }
                });

        builder.setNegativeButton("Discard",null);
        builder.setNegativeButton("Discard",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }





    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount) {
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position >= spanCount) {
                    outRect.top = spacing;
                }
            }
        }
    }
    private int dpToPx(int dp) {
        Resources r = getApplication().getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    public  void prepareAlbumstest(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 500);
    }


    public void openBottomSheet() {
        Log.d(TAG,"CHECKiNtIME:"+Constants.time);
        View view = getLayoutInflater().inflate(R.layout.storestatus, null);
        LinearLayout le=(LinearLayout) view.findViewById(R.id.poupOk) ;
        TextView Add_pin = (TextView)view.findViewById(R.id.text);


     /*   popstcpode.setEnabled(false);
        radioDelivery.setClickable(false);
        radioCollect.setClickable(false);
        radioDelivery.setEnabled(false);
        radioCollect.setEnabled(false);
        btnContinue.setEnabled(false);
        instrucation.setEnabled(false);*/
        Add_pin.setText("Sorry, we are currently closed, however you can still place an order and we will process it when we open at " +checkInTimeHome);

        final Dialog mBottomSheetDialog = new Dialog(Home.this);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
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
