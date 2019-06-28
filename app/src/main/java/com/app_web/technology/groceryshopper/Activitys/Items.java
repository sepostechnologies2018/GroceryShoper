package com.app_web.technology.groceryshopper.Activitys;

import android.animation.Animator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app_web.technology.groceryshopper.Addapter.AlbumsAdapter;
import com.app_web.technology.groceryshopper.Database.DataBaseHelper;
import com.app_web.technology.groceryshopper.GetterSetter.ItemModel;
import com.app_web.technology.groceryshopper.R;
import com.app_web.technology.groceryshopper.util.CircleAnimationUtil;
import com.app_web.technology.groceryshopper.util.Constants;
import com.app_web.technology.groceryshopper.util.VolleyMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
public class Items extends AppCompatActivity implements View.OnClickListener
{

    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<ItemModel> albumList;
    LinearLayout menusub;
    ImageView cancked;
    Button Search_button;
    EditText search_box ;
    LinearLayout wishlist,shop_cart,mainmenu,search,contact_us,more,notification,menu,search_boxd;
    public static DataBaseHelper dbHelper;
    public TextView txt_item_quantity,wishtext,name,txt_item_price;
    private final long millisecondsToShowSplash = 2000L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items2);
        init();
        menusub.setOnClickListener(this);
        shop_cart.setOnClickListener(this);
        wishlist.setOnClickListener(this);
        notification.setOnClickListener(this);
        more.setOnClickListener(this);

        mainmenu.setOnClickListener(this);
        if(getIntent().getExtras().get("data").toString().equals("N")){
            popup();
            name.setText("");
        }
        else {
            name.setText(Constants.itemhaddername);
        }
        getSupportActionBar().hide();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(this, albumList);
        prepareAlbums();
        menusub.setOnClickListener(this);
        search.setOnClickListener(this);
        cancked.setOnClickListener(this);
        //search_box.addTextChangedListener(new MyTextWatcher(search_box));
        Search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search_box.getText().toString().length()>=3) {
                    Constants.itemhaddername=new String("");
                    Constants.itemhaddername=search_box.getText().toString();
                    VolleyMethods vm = new VolleyMethods(Items.this);
                    vm.search(search_box.getText().toString());

                } else {

                }

            }
        });

    }

    private void validateName() {

        if (search_box.getText().toString().length()>=3) {
            VolleyMethods vm = new VolleyMethods(this);
            vm.search(search_box.getText().toString());

        } else {

        }



    }

    public  void popup(){

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle("");
        builder.setCancelable(false);
        builder.setMessage("Items Not Found..");
        builder.setPositiveButton("Discard", null);
        builder.setPositiveButton("Discard",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.show();
    }
public  void init(){
    txt_item_price=(TextView)findViewById(R.id.txt_item_price);
    menusub=(LinearLayout)findViewById(R.id.menusub);
    txt_item_quantity=(TextView)findViewById(R.id.txt_item_quantity);
    wishtext=(TextView)findViewById(R.id.wishquanity);
    name=(TextView)findViewById(R.id.name);
    shop_cart=(LinearLayout)findViewById(R.id.shop_cart);
    wishlist=(LinearLayout)findViewById(R.id.wishlist);
    mainmenu=(LinearLayout)findViewById(R.id.home);
    notification=(LinearLayout)findViewById(R.id.notification);
    more=(LinearLayout)findViewById(R.id.more);
    search=(LinearLayout)findViewById(R.id.search);
    search_boxd=(LinearLayout)findViewById(R.id.search_boxd);
    cancked=(ImageView)findViewById(R.id.cancked);
    search_boxd.setVisibility(View.GONE);
    search_box=(EditText)findViewById(R.id.search_box);
    Search_button=(Button)findViewById(R.id.search_button);
}

    private void prepareAlbums() {
        try{
            JSONArray jsonArray=new JSONArray(Constants.itemdata);
            JSON_PARSE_DATA_AFTER_WEBCALL(jsonArray);}
        catch (JSONException e){

        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.menusub: {
                finish();

            }
            break;
            case R.id.shop_cart: {

                Intent intent = new Intent(Items.this, CheckOut.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
            break;
             case R.id.notification: {
                 if(Constants.isUserLogIn){
                     Intent intent = new Intent(Items.this, Myorder.class);
                     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     startActivity(intent);

                 }else {
                     Constants.logincontrol=1;
                     Intent intent = new Intent(Items.this, Login.class);
                     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     startActivity(intent);

                 }

            }
            break;
            case R.id.wishlist: {

                if(Constants.isUserLogIn){
                    Intent intent = new Intent(Items.this, Wishlistactivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }else {
                    Constants.logincontrol=2;
                    Intent intent = new Intent(Items.this, Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }

            }
            break;
            case R.id.more: {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, R.string.app_name);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "click here to visit  https://play.google.com/store/apps/details?id=com.app_web.technology.groceryshopper");
                startActivity(Intent.createChooser(sharingIntent, "Share via"));


            }
            break;
            case R.id.home: {
                Intent intent = new Intent(Items.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            }
            break;
            case R.id.search: {
                search_boxd.setVisibility(View.VISIBLE);
            }
            break;
            case R.id.cancked: {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                search_boxd.setVisibility(View.GONE);
            }
            break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Constants cons = new Constants();
        String i=cons.getQuantity(this);
        dbHelper = new DataBaseHelper(this);
        Bundle bundle = dbHelper.getItemQuantity();
        txt_item_price.setText("£" + bundle.getString("TotalPrice"));
        if(i.equals("")){
            txt_item_quantity.setVisibility(View.GONE);
        }
        else {
            txt_item_quantity.setVisibility(View.VISIBLE);
            txt_item_quantity.setText(cons.getQuantity(this));
        }
        String j=cons.getQuantitywish(this);
        if(j.equals("")){
            wishtext.setVisibility(View.GONE);
        }
        else {
            wishtext.setVisibility(View.VISIBLE);
            wishtext.setText(j);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }


    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){
        for(int i = 0; i<array.length(); i++) {
            ItemModel GetDataAdapter2 = new ItemModel();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                GetDataAdapter2.setage_restriction(json.getString("age_restriction"));
                GetDataAdapter2.setbasket_limit(json.getString("basket_limit"));
                GetDataAdapter2.setCatId(json.getString("cat_id"));
             //   GetDataAdapter2.setItemFullDescription(json.getString("desc"));
                GetDataAdapter2.setoffer_type(json.getString("offer_type"));
                GetDataAdapter2.setItemId(json.getString("item_id"));
                GetDataAdapter2.setItemName(json.getString("item_name"));
                GetDataAdapter2.setitem_pic(json.getString("item_pic"));
                GetDataAdapter2.setItemPrice(json.getString("price"));

                GetDataAdapter2.setrestrict_sales(json.getString("restrict_sales"));
                GetDataAdapter2.setsize(json.getString("size"));
                GetDataAdapter2.setstatus(json.getString("status"));
                GetDataAdapter2.setSubCatId(json.getString("sub_cat_id"));
                GetDataAdapter2.setage_restrict_msg(json.getString("age_restrict_msg"));
                GetDataAdapter2.setbaseket_limit_msg(json.getString("baseket_limit_msg"));
                GetDataAdapter2.setsmall_desc(json.getString("small_desc"));
                GetDataAdapter2.setrestrict_sales_msg(json.getString("restrict_sales_msg"));
                GetDataAdapter2.setbaseket_limit_msg(json.getString("baseket_limit_msg"));
                GetDataAdapter2.setout_of_stock(json.getString("out_of_stock"));
                GetDataAdapter2.setOfferId(json.getString("OfferId"));
                GetDataAdapter2.setisTimeRistrictedItem(json.getString("isTimeRistrictedItem"));
                //GetDataAdapter2.setOfferDeatils(json.getJSONObject("OfferDeatils").toString());
                GetDataAdapter2.setOfferDeatils(json.getString("OfferDeatils"));
                String nameitem=json.getString("item_name");

                String OfferDeatils=json.getString("OfferDeatils");


            } catch (JSONException e) {
                e.printStackTrace();
            }
            albumList.add(GetDataAdapter2);
        }
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(5), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        adapter.setActionListener(new AlbumsAdapter.ProductItemActionListener() {
            @Override
            public void onItemTap(ImageView imageView) {
                if (imageView != null)
                    makeFlyAnimation(imageView);
            }
        });



    }
    public void popuplpogpout(String mess) {



        LayoutInflater inflater = getLayoutInflater();

        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_layout_id));
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

    }


    private void makeFlyAnimation(ImageView targetView) {

        RelativeLayout destView = (RelativeLayout) findViewById(R.id.ll_check);

        new CircleAnimationUtil().attachActivity(this).setTargetView(targetView).setMoveDuration(500).setDestView(destView).setAnimationListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                addItemToCart();
                //Toast.makeText(Items.this, "Continue Shopping...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).startAnimation();


    }


    private void addItemToCart() {
        adapter = new AlbumsAdapter(this, albumList);
        recyclerView.setAdapter(adapter);
        adapter.setActionListener(new AlbumsAdapter.ProductItemActionListener() {
            @Override
            public void onItemTap(ImageView imageView) {
                if (imageView != null)
                    makeFlyAnimation(imageView);
            }
        });

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
                        Intent intent = new Intent(Items.this, Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
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
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) {
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.search_box:
                    validateName();
                    break;



            }
        }
    }

    public void update()
    {
        Constants cons = new Constants();
        String i=cons.getQuantity(this);
        dbHelper = new DataBaseHelper(this);
        Bundle bundle = dbHelper.getItemQuantity();
        txt_item_price.setText("£" + bundle.getString("TotalPrice"));


    }
}