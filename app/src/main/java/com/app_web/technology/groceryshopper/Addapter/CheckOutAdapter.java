package com.app_web.technology.groceryshopper.Addapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.app_web.technology.groceryshopper.Activitys.CheckOut;
import com.app_web.technology.groceryshopper.Activitys.Items;
import com.app_web.technology.groceryshopper.Database.DataBaseHelper;
import com.app_web.technology.groceryshopper.Database.Wishlistdatabase;
import com.app_web.technology.groceryshopper.Model.OrderItemModel;
import com.app_web.technology.groceryshopper.Model.WishlistOrderItemModel;
import com.app_web.technology.groceryshopper.R;
import com.app_web.technology.groceryshopper.util.AppController;
import com.app_web.technology.groceryshopper.util.Constants;
import com.app_web.technology.groceryshopper.util.Pref;
import com.app_web.technology.groceryshopper.util.VolleyMethods;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckOutAdapter extends RecyclerView.Adapter<CheckOutAdapter.OrderViewHolder> {
    private final long millisecondsToShowSplash = 500L;
    Context context;
    public ArrayList<OrderItemModel> orderCatList;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    ProgressDialog pDialog;
    public CheckOutAdapter(Context context, ArrayList<OrderItemModel> orderCatList) {
        this.context = context;
        this.orderCatList = orderCatList;
    }

    @Override
    public int getItemCount() {
        return orderCatList.size();
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_out_row, parent, false);
        OrderViewHolder vh = new OrderViewHolder(itemView);
        return vh;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onBindViewHolder(final OrderViewHolder holder, final int position) {
        imageLoader = AppController.getInstance().getImageLoader();

        if(Constants.salesrestricted){

           if(orderCatList.get(position).getItemParentCategory().equals("1")) {
               holder.holelayout.setBackgroundColor(Color.parseColor("#FFE7967A"));
               Animation startAnimation = AnimationUtils.loadAnimation(context, R.anim.blinking_animation);
               holder.holelayout.startAnimation(startAnimation);


           }

        }
        if(!orderCatList.get(position).getoffer_type().equals("0")){
            if(orderCatList.get(position).getoffer_type().equals("1")){

                Constants cons = new Constants();
                String i = cons.singleitemquntity(context, orderCatList.get(position).getItemId());
                String noOfProductInPackage,discription
                        ,offerImg,OfferPakagePrice,OfferPendingmsg,removeOfferMethod="";
                String addOfferMethod,alwaysCheck,minimumItemCount,offerAvailedImage,title="";
                try{

                    JSONObject jsonObject = new JSONObject(orderCatList.get(position).getoffer_typethree());
                    offerAvailedImage=jsonObject.getString("offerAvailedImage");
                    offerImg=jsonObject.getString("offerImg");


                if(!i.isEmpty()){

                    if ( (Integer.parseInt(i)/2)*2 == Integer.parseInt(i) ){


                            System.out.println("Even");
                            holder.offerimgcart.setImageUrl("https://www.groceryshopper.info/apps_data/offer_images/"+offerAvailedImage, imageLoader);
                            holder.offertext.setText("Offer Applied");
                            holder.offertext.setBackgroundResource(R.color.green);
                            holder.offertext.setTextColor(context.getResources().getColor(R.color.white));




                    }
                    else
                    {


                            holder.offerimgcart.setImageUrl("https://www.groceryshopper.info/apps_data/offer_images/"+offerImg, imageLoader);
                            System.out.println("Odd");
                            holder.offertext.setText("Add One More For Free..");
                            ObjectAnimator anim = ObjectAnimator.ofInt(holder.offertext, "backgroundColor", Color.WHITE, Color.RED,
                                    Color.WHITE);
                            anim.setDuration(1500);
                            anim.setEvaluator(new ArgbEvaluator());
                            anim.setRepeatMode(Animation.REVERSE);
                            anim.setRepeatCount(Animation.INFINITE);
                            anim.start();








                    }
                }  }catch (JSONException g){

                }

            }
            else {

                if(orderCatList.get(position).getoffer_type().equals("2")){

                    Constants cons = new Constants();
                    String noOfProductInPackage,discription
                            ,offerImg,OfferPakagePrice,OfferPendingmsg,removeOfferMethod="";
                    String addOfferMethod,alwaysCheck,minimumItemCount,offerAvailedImage,title="";
                    String i = cons.singleitemquntity(context, orderCatList.get(position).getItemId());

                    try{

                        JSONObject jsonObject = new JSONObject(orderCatList.get(position).getoffer_typethree());
                        offerAvailedImage=jsonObject.getString("offerAvailedImage");
                        offerImg=jsonObject.getString("offerImg");


                    if(!i.isEmpty()){

                        if ( (Integer.parseInt(i)/2)*2 == Integer.parseInt(i) ){
                            System.out.println("Even");
                            holder.offerimgcart.setImageUrl("https://www.groceryshopper.info/apps_data/offer_images/"+offerAvailedImage, imageLoader);
                            holder.offertext.setText("Offer Applied");
                            holder.offertext.setBackgroundResource(R.color.green);
                            holder.offertext.setTextColor(context.getResources().getColor(R.color.white));

                        }
                        else
                        {
                            holder.offerimgcart.setImageUrl("https://www.groceryshopper.info/apps_data/offer_images/"+offerImg, imageLoader);
                            System.out.println("Odd");
                            holder.offertext.setText("Add One More For Half Price ..");
                            ObjectAnimator anim = ObjectAnimator.ofInt(holder.offertext, "backgroundColor", Color.WHITE, Color.RED,
                                    Color.WHITE);
                            anim.setDuration(1500);
                            anim.setEvaluator(new ArgbEvaluator());
                            anim.setRepeatMode(Animation.REVERSE);
                            anim.setRepeatCount(Animation.INFINITE);
                            anim.start();

                        }
                    }  }catch (JSONException g){

                    }

                }
                else {
                    if(orderCatList.get(position).getoffer_type().equals("3")){
                        Constants constants=new Constants();
                        String applice = constants.offersquanity(context,orderCatList.get(position).getoffer_type(),orderCatList.get(position).getoffer_typetwo());
                        if(!applice.isEmpty()) {
                        String noOfProductInPackage, discription, offerImg, OfferPakagePrice, OfferPendingmsg, removeOfferMethod = "";
                        String addOfferMethod, alwaysCheck, minimumItemCount, offerAvailedImage, title = "";
                        try {
                            JSONObject jsonObject = new JSONObject(orderCatList.get(position).getoffer_typethree());
                            noOfProductInPackage = jsonObject.getString("noOfProductInPackage");
                            offerAvailedImage = jsonObject.getString("offerAvailedImage");
                            offerImg = jsonObject.getString("offerImg");


                        if(Integer.parseInt(applice)>=Integer.parseInt(noOfProductInPackage)){

                            holder.offerimgcart.setImageUrl("https://www.groceryshopper.info/apps_data/offer_images/"+offerAvailedImage, imageLoader);
                            holder.offertext.setText("Offer Applied");
                            holder.offertext.setBackgroundResource(R.color.green);
                            holder.offertext.setTextColor(context.getResources().getColor(R.color.white));

                        }
                        else {

                            int remider=Integer.parseInt(noOfProductInPackage)-Integer.parseInt(applice);
                            holder.offerimgcart.setImageUrl("https://www.groceryshopper.info/apps_data/offer_images/"+offerImg, imageLoader);
                            holder.offertext.setText("Add "+remider+" more product to complete offer");
                            ObjectAnimator anim = ObjectAnimator.ofInt(holder.offertext, "backgroundColor", Color.WHITE, Color.RED,
                                    Color.WHITE);
                            anim.setDuration(1500);
                            anim.setEvaluator(new ArgbEvaluator());
                            anim.setRepeatMode(Animation.REVERSE);
                            anim.setRepeatCount(Animation.INFINITE);
                            anim.start();

                        }

                        }
                        catch (JSONException e){}

                    }}else {
                    }
                }
            }
        }
        else {

        }

        holder.offerimgcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    JSONObject jsonObject = new JSONObject(orderCatList.get(position).getoffer_typethree());
                    Constants.discription=jsonObject.getString("discription");
                }catch (JSONException e){

                }
                String off=orderCatList.get(position).getoffer_typethree();
                if(orderCatList.get(position).getoffer_typethree().equals("")||orderCatList.get(position).getoffer_typethree().equals("{}")){

                }else {
                    VolleyMethods vm = new VolleyMethods(context);
                    vm.offeritem(orderCatList.get(position).getoffer_type(),orderCatList.get(position).getoffer_typetwo(),0);
                }
            }
        });
        Typeface face1= Typeface.createFromAsset(context.getAssets(), "fonts/aaa.ttf");
        holder.offertext.setTypeface(face1);
        holder.itemname.setText( orderCatList.get(position).getItemName());
        holder.itemname.setTypeface(face1);
        DecimalFormat df = new DecimalFormat("0.00");
        holder.itemprice.setText(orderCatList.get(position).getItemQuantity()+" x "+orderCatList.get(position).getItemPrice() +"     =   £ "+String.valueOf( df.format(Float.parseFloat(orderCatList.get(position).getItemPrice())*Integer.parseInt(orderCatList.get(position).getItemQuantity()))));
        holder.itemprice.setTypeface(face1);
        holder.quanty.setText(orderCatList.get(position).getItemQuantity());
        holder.quanty.setTypeface(face1);
        holder.product.setImageUrl("https://www.groceryshopper.info/apps_data/item_images/"+orderCatList.get(position).getSpecial_instruction(), imageLoader);

        if(!orderCatList.get(position).getSpecial_instruction().equals("")){
            holder.product.setBackground(context.getResources().getDrawable(R.drawable.offerstranf));
        }
        else {
            holder.product.setBackground(context.getResources().getDrawable(R.drawable.defaultimg));
        }
        holder.offertextamount.setText("- £ "+df.format(Float.parseFloat(orderCatList.get(position).getSpecialTips()))+"\n"+"£ " + df.format(((Float.parseFloat(orderCatList.get(position).getItemPrice())*Integer.parseInt(orderCatList.get(position).getItemQuantity()))-Float.parseFloat(orderCatList.get(position).getSpecialTips()))));
        holder.offertextamount.setTypeface(face1);
        holder.itemadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
                scale.setDuration(500);
                scale.setInterpolator(new OvershootInterpolator());
                holder.product.startAnimation(scale);
                if(!orderCatList.get(position).getoffer_type().equals("0")){

                    if(orderCatList.get(position).getoffer_type().equals("1")){

                        String getbasket_limit=orderCatList.get(position).getFree_toppings();
                        if(!getbasket_limit.equals("0")&&!getbasket_limit.equals("")){
                            Constants cons = new Constants();
                            String i = cons.singleitemquntity(context, orderCatList.get(position).getItemId());
                            if (Integer.parseInt(i) < Integer.parseInt(getbasket_limit)) {

                                {

                                    if ((Integer.parseInt(i) / 2) * 2 == Integer.parseInt(i)) {
                                        System.out.println("Even");
                                        DataBaseHelper dbHelper = new DataBaseHelper(context);
                                        OrderItemModel isExist = dbHelper.getSingleItem(orderCatList.get(position).getItemId(), "", "", "", orderCatList.get(position).getItemPrice());
                                        if (isExist != null) {
                                            int quantity;
                                            quantity = Integer.parseInt(isExist.getItemQuantity()) + 1;
                                            isExist.setItemQuantity("" + quantity);
                                            dbHelper.upDateOrder(isExist);

                                        }
                                        DataBaseHelper dbHendler = new DataBaseHelper(context);
                                        CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
                                        CheckOut.orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);
                                        CheckOut.txtQuantity.setText(cons.getQuantity(context));
                                        Bundle bundle = dbHendler.getItemQuantity();
                                        CheckOut.total.setText("£" + bundle.getString("TotalPrice"));
                                    } else {
                                        System.out.println("odd");
                                       // Toast.makeText(context, "odd", Toast.LENGTH_SHORT).show();
                                        DataBaseHelper dbHelper = new DataBaseHelper(context);
                                        OrderItemModel isExist = dbHelper.getSingleItem(orderCatList.get(position).getItemId(), "", "", "", orderCatList.get(position).getItemPrice());

                                        String k = cons.singleitemquntity(context, orderCatList.get(position).getItemId());
                                        int perfect=Integer.parseInt(k)+1;
                                        float ty=Float.parseFloat(orderCatList.get(position).getItemPrice())*(perfect/2);
                                        if (isExist != null) {
                                            int quantity;
                                            quantity = Integer.parseInt(isExist.getItemQuantity()) + 1;
                                            isExist.setItemQuantity("" + quantity);
                                            isExist.setSpecialTips(String.valueOf(ty));
                                            dbHelper.upDateOrder(isExist);

                                        }
                                        DataBaseHelper dbHendler = new DataBaseHelper(context);
                                        CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
                                        CheckOut.orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);
                                        CheckOut.txtQuantity.setText(cons.getQuantity(context));
                                        Bundle bundle = dbHendler.getItemQuantity();
                                        CheckOut.total.setText("£" + bundle.getString("TotalPrice"));
                                    }


                                }

                            }
                            else {

                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage(orderCatList.get(position).getID());
                                String negativeText = "OK";
                                builder.setNegativeButton(negativeText,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });

                                AlertDialog dialog = builder.create();

                                dialog.show();


                            }
                        }
                        else {


                            Constants cons = new Constants();
                            String i = cons.singleitemquntity(context, orderCatList.get(position).getItemId());

                            if ((Integer.parseInt(i) / 2) * 2 == Integer.parseInt(i)) {
                                System.out.println("Even");
                               // Toast.makeText(context, "Even", Toast.LENGTH_SHORT).show();

                                DataBaseHelper dbHelper = new DataBaseHelper(context);
                                OrderItemModel isExist = dbHelper.getSingleItem(orderCatList.get(position).getItemId(), "", "", "", orderCatList.get(position).getItemPrice());
                                if (isExist != null) {
                                    int quantity;
                                    quantity = Integer.parseInt(isExist.getItemQuantity()) + 1;
                                    isExist.setItemQuantity("" + quantity);
                                    dbHelper.upDateOrder(isExist);

                                }
                                DataBaseHelper dbHendler = new DataBaseHelper(context);
                                CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
                                CheckOut.orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);
                                CheckOut.txtQuantity.setText(cons.getQuantity(context));
                                Bundle bundle = dbHendler.getItemQuantity();
                                CheckOut.total.setText("£" + bundle.getString("TotalPrice"));
                            } else {
                                System.out.println("odd");
                               // Toast.makeText(context, "odd", Toast.LENGTH_SHORT).show();
                                DataBaseHelper dbHelper = new DataBaseHelper(context);
                                OrderItemModel isExist = dbHelper.getSingleItem(orderCatList.get(position).getItemId(), "", "", "", orderCatList.get(position).getItemPrice());

                                String k = cons.singleitemquntity(context, orderCatList.get(position).getItemId());
                                int perfect=Integer.parseInt(k)+1;
                                float ty=Float.parseFloat(orderCatList.get(position).getItemPrice())*(perfect/2);
                                if (isExist != null) {
                                    int quantity;
                                    quantity = Integer.parseInt(isExist.getItemQuantity()) + 1;
                                    isExist.setItemQuantity("" + quantity);
                                    isExist.setSpecialTips(String.valueOf(ty));
                                    dbHelper.upDateOrder(isExist);

                                }
                                DataBaseHelper dbHendler = new DataBaseHelper(context);
                                CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
                                CheckOut.orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);
                                CheckOut.txtQuantity.setText(cons.getQuantity(context));
                                Bundle bundle = dbHendler.getItemQuantity();
                                CheckOut.total.setText("£" + bundle.getString("TotalPrice"));
                            }


                        }  }
                    else {

                        if(orderCatList.get(position).getoffer_type().equals("2")){

                            String getbasket_limit=orderCatList.get(position).getFree_toppings();
                            if(!getbasket_limit.equals("0")&&!getbasket_limit.equals("")){
                                Constants cons = new Constants();
                                String i = cons.singleitemquntity(context, orderCatList.get(position).getItemId());

                                if (Integer.parseInt(i) < Integer.parseInt(getbasket_limit)) {

                                    {

                                        if ((Integer.parseInt(i) / 2) * 2 == Integer.parseInt(i)) {
                                            System.out.println("Even");
                                         //   Toast.makeText(context, "Even", Toast.LENGTH_SHORT).show();

                                            DataBaseHelper dbHelper = new DataBaseHelper(context);
                                            OrderItemModel isExist = dbHelper.getSingleItem(orderCatList.get(position).getItemId(), "", "", "", orderCatList.get(position).getItemPrice());
                                            if (isExist != null) {
                                                int quantity;
                                                quantity = Integer.parseInt(isExist.getItemQuantity()) + 1;
                                                isExist.setItemQuantity("" + quantity);
                                                dbHelper.upDateOrder(isExist);

                                            }
                                            DataBaseHelper dbHendler = new DataBaseHelper(context);
                                            CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
                                            CheckOut.orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);
                                            CheckOut.txtQuantity.setText(cons.getQuantity(context));
                                            Bundle bundle = dbHendler.getItemQuantity();
                                            CheckOut.total.setText("£" + bundle.getString("TotalPrice"));
                                        } else {
                                            System.out.println("odd");
                                            //Toast.makeText(context, "odd", Toast.LENGTH_SHORT).show();
                                            DataBaseHelper dbHelper = new DataBaseHelper(context);
                                            OrderItemModel isExist = dbHelper.getSingleItem(orderCatList.get(position).getItemId(), "", "", "", orderCatList.get(position).getItemPrice());

                                            String k = cons.singleitemquntity(context, orderCatList.get(position).getItemId());
                                            int perfect=Integer.parseInt(k)+1;
                                            float ty=(Float.parseFloat(orderCatList.get(position).getItemPrice())*(perfect/2))/2;
                                            if (isExist != null) {
                                                int quantity;
                                                quantity = Integer.parseInt(isExist.getItemQuantity()) + 1;
                                                isExist.setItemQuantity("" + quantity);
                                                isExist.setSpecialTips(String.valueOf(ty));
                                                dbHelper.upDateOrder(isExist);

                                            }
                                            DataBaseHelper dbHendler = new DataBaseHelper(context);
                                            CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
                                            CheckOut.orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);
                                            CheckOut.txtQuantity.setText(cons.getQuantity(context));
                                            Bundle bundle = dbHendler.getItemQuantity();
                                            CheckOut.total.setText("£" + bundle.getString("TotalPrice"));
                                        }}
                                }
                                else {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setMessage(orderCatList.get(position).getID());
                                    String negativeText = "OK";
                                    builder.setNegativeButton(negativeText,
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            });

                                    AlertDialog dialog = builder.create();

                                    dialog.show();


                                }
                            }
                            else {


                                Constants cons = new Constants();
                                String i = cons.singleitemquntity(context, orderCatList.get(position).getItemId());

                                if ((Integer.parseInt(i) / 2) * 2 == Integer.parseInt(i)) {
                                    System.out.println("Even");
                                   // Toast.makeText(context, "Even", Toast.LENGTH_SHORT).show();

                                    DataBaseHelper dbHelper = new DataBaseHelper(context);
                                    OrderItemModel isExist = dbHelper.getSingleItem(orderCatList.get(position).getItemId(), "", "", "", orderCatList.get(position).getItemPrice());
                                    if (isExist != null) {
                                        int quantity;
                                        quantity = Integer.parseInt(isExist.getItemQuantity()) + 1;
                                        isExist.setItemQuantity("" + quantity);
                                        dbHelper.upDateOrder(isExist);

                                    }
                                    DataBaseHelper dbHendler = new DataBaseHelper(context);
                                    CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
                                    CheckOut.orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);
                                    CheckOut.txtQuantity.setText(cons.getQuantity(context));
                                    Bundle bundle = dbHendler.getItemQuantity();
                                    CheckOut.total.setText("£" + bundle.getString("TotalPrice"));
                                } else {
                                    System.out.println("odd");
                                  //  Toast.makeText(context, "odd", Toast.LENGTH_SHORT).show();
                                    DataBaseHelper dbHelper = new DataBaseHelper(context);
                                    OrderItemModel isExist = dbHelper.getSingleItem(orderCatList.get(position).getItemId(), "", "", "", orderCatList.get(position).getItemPrice());

                                    String k = cons.singleitemquntity(context, orderCatList.get(position).getItemId());
                                    int perfect=Integer.parseInt(k)+1;
                                    float ty=(Float.parseFloat(orderCatList.get(position).getItemPrice())*(perfect/2))/2;
                                    if (isExist != null) {
                                        int quantity;
                                        quantity = Integer.parseInt(isExist.getItemQuantity()) + 1;
                                        isExist.setItemQuantity("" + quantity);
                                        isExist.setSpecialTips(String.valueOf(ty));
                                        dbHelper.upDateOrder(isExist);

                                    }
                                    DataBaseHelper dbHendler = new DataBaseHelper(context);
                                    CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
                                    CheckOut.orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);
                                    CheckOut.txtQuantity.setText(cons.getQuantity(context));
                                    Bundle bundle = dbHendler.getItemQuantity();
                                    CheckOut.total.setText("£" + bundle.getString("TotalPrice"));
                                }


                            }  }
                        else {

                            if(orderCatList.get(position).getoffer_type().equals("3")){

                                String getbasket_limit=orderCatList.get(position).getFree_toppings();
                                if(!getbasket_limit.equals("0")&&!getbasket_limit.equals("")){
                                    Constants cons = new Constants();
                                    String i = cons.singleitemquntity(context, orderCatList.get(position).getItemId());
                                    if (Integer.parseInt(i) < Integer.parseInt(getbasket_limit)) {

                                        {


                                                System.out.println("odd");

                                                DataBaseHelper dbHelper = new DataBaseHelper(context);
                                                OrderItemModel isExist = dbHelper.getSingleItem(orderCatList.get(position).getItemId(), "", "", "", orderCatList.get(position).getItemPrice());
                                            if (isExist != null) {
                                                int quantity;
                                                quantity = Integer.parseInt(isExist.getItemQuantity()) + 1;
                                                isExist.setItemQuantity("" + quantity);
                                                dbHelper.upDateOrder(isExist);
                                                dbHelper.getofferOrder(orderCatList.get(position).getoffer_type(),orderCatList.get(position).getoffer_typetwo());
                                                for(int l=0;l<Constants.offerid.size();l++){

                                                    DataBaseHelper db = new DataBaseHelper(context);
                                                    OrderItemModel is= db.getSingleItem(Constants.offerid.get(l), "", "", "", Constants.offersprice.get(l));
                                                    is.setSpecialTips(String.valueOf(Constants.offersdis.get(l)));
                                                    dbHelper.upDateOrder(is);


                                                }

                                            }
                                                DataBaseHelper dbHendler = new DataBaseHelper(context);
                                                CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
                                                CheckOut.orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);
                                                CheckOut.txtQuantity.setText(cons.getQuantity(context));
                                                Bundle bundle = dbHendler.getItemQuantity();
                                                CheckOut.total.setText("£" + bundle.getString("TotalPrice"));



                                        }










                                    }
                                    else {

                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setMessage(orderCatList.get(position).getID());
                                        String negativeText = "OK";
                                        builder.setNegativeButton(negativeText,
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }
                                                });

                                        AlertDialog dialog = builder.create();

                                        dialog.show();


                                    }
                                }
                                else {


                                    Constants cons = new Constants();
                                    String i = cons.singleitemquntity(context, orderCatList.get(position).getItemId());
                                    DataBaseHelper dbHelper = new DataBaseHelper(context);
                                    OrderItemModel isExist = dbHelper.getSingleItem(orderCatList.get(position).getItemId(), "", "", "", orderCatList.get(position).getItemPrice());
                                    if (isExist != null) {

                                        {
                                                int quantity;
                                                quantity = Integer.parseInt(isExist.getItemQuantity()) + 1;
                                                isExist.setItemQuantity("" + quantity);

                                                dbHelper.upDateOrder(isExist);
                                                dbHelper.getofferOrder(orderCatList.get(position).getoffer_type(),orderCatList.get(position).getoffer_typetwo());
                                            if(Constants.offerid.size()>0){
                                                for(int l=0;l<Constants.offerid.size();l++)
                                                {
                                                    String price=Constants.offersprice.get(l);
                                                    String id=Constants.offerid.get(l);
                                                    DataBaseHelper db = new DataBaseHelper(context);
                                                    OrderItemModel is= db.getSingleItem(Constants.offerid.get(l), "", "", "", Constants.offersprice.get(l));
                                                    is.setSpecialTips(String.valueOf(Constants.offersdis.get(l)));
                                                    is.setoffer_complete("true");
                                                    db.updateofferprice(is);


                                                }

                                            }
                                            Constants.offerid.clear();
                                            Constants.offersdis.clear();
                                            Constants.offersprice.clear();}

                                        }




                                        DataBaseHelper dbHendler = new DataBaseHelper(context);
                                        CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
                                        CheckOut.orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);
                                        CheckOut.txtQuantity.setText(cons.getQuantity(context));
                                        Bundle bundle = dbHendler.getItemQuantity();
                                        CheckOut.total.setText("£" + bundle.getString("TotalPrice"));



                                }  }
                            else {



                            }


                        }


                    }


                }


else {


                String getbasket_limit=orderCatList.get(position).getFree_toppings();
                if(!getbasket_limit.equals("0")&&!getbasket_limit.equals("")){
                Constants cons = new Constants();
                String i = cons.singleitemquntity(context, orderCatList.get(position).getItemId());
                    if (Integer.parseInt(i) < Integer.parseInt(getbasket_limit)) {
                        {
                            DataBaseHelper dbHelper = new DataBaseHelper(context);
                            OrderItemModel isExist = dbHelper.getSingleItem(orderCatList.get(position).getItemId(), "", "", "", orderCatList.get(position).getItemPrice());
                            if (isExist != null) {
                                int quantity;
                                quantity = Integer.parseInt(isExist.getItemQuantity()) + 1;
                                isExist.setItemQuantity("" + quantity);
                                dbHelper.upDateOrder(isExist);

                            }

                            DataBaseHelper dbHendler = new DataBaseHelper(context);
                            CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
                            CheckOut.orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);
                            CheckOut.txtQuantity.setText(cons.getQuantity(context));
                            Bundle bundle = dbHendler.getItemQuantity();
                            CheckOut.total.setText("£" + bundle.getString("TotalPrice"));


                        }
                    }
                    else {


                        {


                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage(orderCatList.get(position).getID());
                            String negativeText = "OK";
                            builder.setNegativeButton(negativeText,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });

                            AlertDialog dialog = builder.create();

                            dialog.show();
                        }
                    }

                }


                else {


                    DataBaseHelper dbHelper = new DataBaseHelper(context);
                OrderItemModel isExist = dbHelper.getSingleItem(orderCatList.get(position).getItemId(), "", "", "", orderCatList.get(position).getItemPrice());
                if (isExist != null) {
                    int quantity;
                    quantity = Integer.parseInt(isExist.getItemQuantity()) + 1;
                    isExist.setItemQuantity("" + quantity);
                    dbHelper.upDateOrder(isExist);




                }

                Constants cons = new Constants();
                DataBaseHelper dbHendler = new DataBaseHelper(context);
                CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
                CheckOut.orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);
                CheckOut.txtQuantity.setText(cons.getQuantity(context));
                Bundle bundle = dbHendler.getItemQuantity();
                CheckOut.total.setText("£" + bundle.getString("TotalPrice"));


            }}}
        });

        holder.itemremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
                scale.setDuration(500);
                scale.setInterpolator(new OvershootInterpolator());
                holder.product.startAnimation(scale);
                int j=Integer.parseInt(holder.quanty.getText().toString());
                if(j==1){

                    holder.itemremove.setClickable(false);

                }
                else {

                DataBaseHelper dbHelper = new DataBaseHelper(context);
                OrderItemModel isExist = dbHelper.getSingleItem(orderCatList.get(position).getItemId(), "", "", "", orderCatList.get(position).getItemPrice());
                if (isExist != null) {
                    int quantity;
                    quantity = Integer.parseInt(isExist.getItemQuantity()) - 1;
                    String i=isExist.getItemQuantity();


                    if(orderCatList.get(position).getoffer_type().equals("1"))  {
                        if ((Integer.parseInt(i) / 2) * 2 == Integer.parseInt(i)) {



                            int perfect=Integer.parseInt(i)-1;
                            float ty=Float.parseFloat(orderCatList.get(position).getItemPrice())*(perfect/2);
                            if (isExist != null) {

                                isExist.setItemQuantity("" + quantity);
                                isExist.setSpecialTips(String.valueOf(ty));
                                dbHelper.upDateOrder(isExist);

                            }




                        }
                        else {


                            isExist.setItemQuantity("" + quantity);
                            dbHelper.upDateOrder(isExist);



                        }


                    }
                    else if(orderCatList.get(position).getoffer_type().equals("2")){
                        if ((Integer.parseInt(i) / 2) * 2 == Integer.parseInt(i)) {



                            int perfect=Integer.parseInt(i)-1;
                            float ty=(Float.parseFloat(orderCatList.get(position).getItemPrice())*(perfect/2))/2;
                            if (isExist != null) {

                                isExist.setItemQuantity("" + quantity);
                                isExist.setSpecialTips(String.valueOf(ty));
                                dbHelper.upDateOrder(isExist);

                            }




                        }
                        else {
                            isExist.setItemQuantity("" + quantity);
                            dbHelper.upDateOrder(isExist);

                        }


                    }
                    else if(orderCatList.get(position).getoffer_type().equals("3")){

                        quantity = Integer.parseInt(isExist.getItemQuantity()) - 1;
                        isExist.setItemQuantity("" + quantity);

                        dbHelper.upDateOrder(isExist);
                        dbHelper.getofferOrder(orderCatList.get(position).getoffer_type(),orderCatList.get(position).getoffer_typetwo());
                        for(int l=0;l<Constants.offerid.size();l++){

                            DataBaseHelper db = new DataBaseHelper(context);
                            OrderItemModel is= db.getSingleItem(Constants.offerid.get(l), "", "", "", Constants.offersprice.get(l));
                            is.setSpecialTips(String.valueOf(Constants.offersdis.get(l)));
                            dbHelper.upDateOrder(is);


                        }}
                    else {
                        isExist.setItemQuantity("" + quantity);
                        dbHelper.upDateOrder(isExist);
                    }








                }
                else {



                }

                Constants cons = new Constants();
                DataBaseHelper dbHendler = new DataBaseHelper(context);
                CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
                CheckOut.orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);
                CheckOut.txtQuantity.setText(cons.getQuantity(context));
                Bundle bundle = dbHendler.getItemQuantity();
                CheckOut.total.setText("£" + bundle.getString("TotalPrice"));


            }}
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!orderCatList.get(position).getoffer_type().equals("0")){
                    {

                        if(orderCatList.get(position).getoffer_type().equals("1")){
                            Constants cons = new Constants();
                            String i = cons.singleitemquntity(context, orderCatList.get(position).getItemId());

                                if ((Integer.parseInt(i) / 2) * 2 == Integer.parseInt(i)) {

                                    System.out.println("Even");
                                  //  Toast.makeText(context, "Even", Toast.LENGTH_SHORT).show();


                                    pDialog = new ProgressDialog(context);
                                    pDialog.setMessage("Loading...");
                                    pDialog.show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            pDialog.dismiss();

                                            Constants cons = new Constants();
                                            DataBaseHelper dbHendler = new DataBaseHelper(context);
                                            dbHendler.deleteSingleItem(orderCatList.get(position));
                                            CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
                                            CheckOut.orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);
                                            CheckOut.txtQuantity.setText(cons.getQuantity(context));
                                            Bundle bundle = dbHendler.getItemQuantity();
                                            CheckOut.total.setText("£" + bundle.getString("TotalPrice"));
                                            ((CheckOut)context).check();
                                        }
                                    }, millisecondsToShowSplash);

                                } else {
                                    System.out.println("odd");
                                    pDialog = new ProgressDialog(context);
                                    pDialog.setMessage("Loading...");
                                    pDialog.show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            pDialog.dismiss();

                                            Constants cons = new Constants();
                                            DataBaseHelper dbHendler = new DataBaseHelper(context);
                                            dbHendler.deleteSingleItem(orderCatList.get(position));
                                            CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
                                            CheckOut.orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);
                                            CheckOut.txtQuantity.setText(cons.getQuantity(context));
                                            Bundle bundle = dbHendler.getItemQuantity();
                                            CheckOut.total.setText("£" + bundle.getString("TotalPrice"));
                                            ((CheckOut)context).check();

                                        }
                                    }, millisecondsToShowSplash);



                                }


                            }
                        else {

                            if(orderCatList.get(position).getoffer_type().equals("2")){
                                pDialog = new ProgressDialog(context);
                                pDialog.setMessage("Loading...");
                                pDialog.show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        pDialog.dismiss();

                                        Constants cons = new Constants();
                                        DataBaseHelper dbHendler = new DataBaseHelper(context);
                                        dbHendler.deleteSingleItem(orderCatList.get(position));
                                        CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
                                        CheckOut.orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);
                                        CheckOut.txtQuantity.setText(cons.getQuantity(context));
                                        Bundle bundle = dbHendler.getItemQuantity();
                                        CheckOut.total.setText("£" + bundle.getString("TotalPrice"));
                                        ((CheckOut)context).check();
                                    }
                                }, millisecondsToShowSplash);

                            }
                            else {

                                if(orderCatList.get(position).getoffer_type().equals("3")){


                                    pDialog = new ProgressDialog(context);
                                    pDialog.setMessage("Loading...");
                                    pDialog.show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            pDialog.dismiss();

                                            Constants cons = new Constants();
                                            DataBaseHelper dbHendler = new DataBaseHelper(context);
                                            dbHendler.deleteSingleItem(orderCatList.get(position));
                                            if(orderCatList.get(position).getoffer_typethree().equals("")||orderCatList.get(position).getoffer_typethree().equals("{}")){

                                            }
                                            else {

                                                dbHendler.getofferOrder(orderCatList.get(position).getoffer_type(),orderCatList.get(position).getoffer_typetwo());
                                            }

                                            for(int l=0;l<Constants.offerid.size();l++){

                                                DataBaseHelper db = new DataBaseHelper(context);
                                                OrderItemModel is= db.getSingleItem(Constants.offerid.get(l), "", "", "", Constants.offersprice.get(l));
                                                is.setSpecialTips(String.valueOf(Constants.offersdis.get(l)));
                                                dbHendler.upDateOrder(is);


                                            }
                                            DataBaseHelper dbHelper = new DataBaseHelper(context);
                                            OrderItemModel isExist = dbHelper.getSingleItem(orderCatList.get(position).getItemId(), "", "", "", orderCatList.get(position).getItemPrice());
                                            CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
                                            CheckOut.orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);
                                            CheckOut.txtQuantity.setText(cons.getQuantity(context));
                                            Bundle bundle = dbHendler.getItemQuantity();
                                            CheckOut.total.setText("£" + bundle.getString("TotalPrice"));
                                            ((CheckOut)context).check();
                                        }
                                    }, millisecondsToShowSplash);

                                }


                            }


                        }


                    }


                }
               else {


                pDialog = new ProgressDialog(context);
                pDialog.setMessage("Loading...");
                pDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pDialog.dismiss();
                        Constants cons = new Constants();
                        DataBaseHelper dbHendler = new DataBaseHelper(context);
                        dbHendler.deleteSingleItem(orderCatList.get(position));
                        CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
                        CheckOut.orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);
                        CheckOut.txtQuantity.setText(cons.getQuantity(context));
                        Bundle bundle = dbHendler.getItemQuantity();
                        CheckOut.total.setText("£" + bundle.getString("TotalPrice"));
                        ((CheckOut)context).check();
                    }
                }, millisecondsToShowSplash);

            }}
        });


        holder.movetowishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                {

                    if(Constants.isUserLogIn)
                    {



                        final ProgressDialog  pDialog = new ProgressDialog(context);
                        pDialog.setMessage("Loading...");
                        final String tag_string_req = "string_req";
                        String url ="https://www.groceryshopper.info/apps_data/add_wishlist_android_api.php" ;

                        pDialog.show();
                        final StringRequest strReq = new StringRequest(Request.Method.POST,
                                url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pDialog.hide();
                                Log.d("TAG@123", response.toString());

                                try {
                                    JSONObject jsonObject = new JSONObject(response.toString());
                                    if (jsonObject.getString("response").toString().equals("1")) {
                                        Toast.makeText(context,jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
                                        AlbumsAdapter.wistitem.add(orderCatList.get(position).getItemId());
                                        DataBaseHelper dbHendler = new DataBaseHelper(context);
                                        dbHendler.deleteSingleItem(orderCatList.get(position));
                                        ((CheckOut)context).recreate();
                                    }
                                    else {
                                        Toast.makeText(context,jsonObject.getString("msg"),Toast.LENGTH_LONG).show();

                                    }

                                }catch (JSONException g){

                                }



                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("TAG@123", error.toString());
                                Toast.makeText(context, "Internet Connection Not Working Properly", Toast.LENGTH_SHORT).show();
                                VolleyLog.d("TAG", "Error: " + error.getMessage());
                                pDialog.hide();
                            }

                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                Pref pref= new Pref(context);
                                params.put("user_id",pref.getUserId());
                                params.put("items", getBaseString(Integer.parseInt(orderCatList.get(position).getItemId())));
                                Log.d("TAG@123", params.toString());
                                String d=params.toString();
                                return params;
                            }
                        };

                        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
                    }
                    else {

                        ((CheckOut)context).popuplpogpoutwish(" was added to your WishList");
                    }










                }



            } /*{


                String off=orderCatList.get(position).getoffer_type();
                AlbumsAdapter.wistitem.add(orderCatList.get(position).getItemId());
                Wishlistdatabase dbHelper = new Wishlistdatabase(context);
                WishlistOrderItemModel item = new WishlistOrderItemModel();
                item.setItemId(orderCatList.get(position).getItemId());
                item.setItemQuantity("1");
                item.setItemName(orderCatList.get(position).getItemName());
                item.setItemPrice(orderCatList.get(position).getItemPrice());
                item.setoffer_type(orderCatList.get(position).getoffer_type());
                item.setoffer_typetwo(orderCatList.get(position).getoffer_typetwo());
                item.setoffer_typethree(orderCatList.get(position).getoffer_typethree());
                item.setItemParentCategory("");
                item.setoffer_complete("false");
                item.setSubItems("");
                item.setItemBase("");
                item.setItemBasePrice("");
                item.setItemBasePizzaIndex("");
                item.setBase("");
                item.setSize("");
                item.setID("");
                item.setFree_toppings("");
                item.setSpecial_instruction(orderCatList.get(position).getSpecial_instruction());
                item.setSpecialTips("0.0");
                dbHelper.addItemToOrder(item);
                pDialog = new ProgressDialog(context);
                pDialog.setMessage("Loading...");
                pDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pDialog.dismiss();
                        Constants cons = new Constants();
                        DataBaseHelper dbHendler = new DataBaseHelper(context);
                        dbHendler.deleteSingleItem(orderCatList.get(position));
                        CheckOut.checkOutAdapter = new CheckOutAdapter(context, dbHendler.getOrder());
                        CheckOut.orderCatRecycler.setAdapter(CheckOut.checkOutAdapter);
                        CheckOut.txtQuantity.setText(cons.getQuantity(context));
                        Bundle bundle = dbHendler.getItemQuantity();
                        CheckOut.total.setText("£" + bundle.getString("TotalPrice"));
                        ((CheckOut)context).check();
                    }
                }, millisecondsToShowSplash);













            }*/
        });

    }
    public class OrderViewHolder extends RecyclerView.ViewHolder {
        Button btnCancel, btnDelete;
        ImageView itemadd,itemremove;
LinearLayout holelayout;
        public TextView itemname, itemprice,movetowishlist,delete,offertext,quanty,offertextamount;
        NetworkImageView product,offerimgcart;
        public OrderViewHolder(View view) {
            super(view);
            product=(NetworkImageView)view.findViewById(R.id.grid_image_cart);
            itemname=(TextView)view.findViewById(R.id.grid_text_cart);
            itemprice=(TextView)view.findViewById(R.id.price);
            delete=(TextView)view.findViewById(R.id.delete);
            offerimgcart=(NetworkImageView)view.findViewById(R.id.offerimgcart);
            itemadd=(ImageView)view.findViewById(R.id.add);
            itemremove=(ImageView)view.findViewById(R.id.remove);
            movetowishlist=(TextView)view.findViewById(R.id.wishlist);
            offertext=(TextView)view.findViewById(R.id.offertext);
            quanty=(TextView)view.findViewById(R.id.quanty);
            offertextamount=(TextView)view.findViewById(R.id.offertextamount);
            holelayout=(LinearLayout)view.findViewById(R.id.holelayout);
        }
    }

    public String getBaseString(int id) {
        String baseString = new String();
        if (AlbumsAdapter.wistitem.size() > 0) {
            for (int i = 0; i < AlbumsAdapter.wistitem.size(); i++) {
                if (baseString.equals("")) {
                    baseString =String.valueOf(id)+ "," + AlbumsAdapter.wistitem.get(i);
                } else {
                    baseString = baseString + "," + AlbumsAdapter.wistitem.get(i);
                }
            }


        } else {

            baseString=String.valueOf(id);
        }
        return baseString;
    }


}
