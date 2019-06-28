package com.app_web.technology.groceryshopper.Addapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.app_web.technology.groceryshopper.Activitys.Items;
import com.app_web.technology.groceryshopper.Activitys.Wishlistactivity;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Wishlist extends RecyclerView.Adapter<Wishlist.OrderViewHolder> {
    private final long millisecondsToShowSplash = 50L;
    Context context;
    public ArrayList<WishlistOrderItemModel> orderCatList;
    ProgressDialog pDialog;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    public Wishlist(Context context, ArrayList<WishlistOrderItemModel> orderCatList) {
        this.context = context;
        this.orderCatList = orderCatList;
    }

    @Override
    public int getItemCount() {
        return orderCatList.size();
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlistshow, parent, false);
        OrderViewHolder vh = new OrderViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final OrderViewHolder holder, final int position) {
        imageLoader = AppController.getInstance().getImageLoader();
        holder.itemname.setText( orderCatList.get(position).getItemName());
        holder.product.setImageUrl("https://www.groceryshopper.info/apps_data/item_images/"+orderCatList.get(position).getSpecial_instruction(), imageLoader);
        holder.itemprice.setText(" Price    £ "+orderCatList.get(position).getItemPrice());
        Typeface face1= Typeface.createFromAsset(context.getAssets(), "fonts/aaa.ttf");
        holder.itemname.setTypeface(face1);
        holder.itemprice.setTypeface(face1);
       // holder.itemname.setTypeface(face1);
        if(!orderCatList.get(position).getSpecial_instruction().equals("")){
            holder.product.setBackground(context.getResources().getDrawable(R.drawable.offerstranf));
        }
        else {
            holder.product.setBackground(context.getResources().getDrawable(R.drawable.defaultimg));
        }

        String offerdaitle=orderCatList.get(position).getoffer_typethree();
        if(offerdaitle.equals("")||offerdaitle.equals("{}")){
            holder.offerimgcart.setImageUrl("https://www.groceryshopper.info/apps_data/offer_images/", imageLoader);
        }
        else {
            try{
                String noOfProductInPackage
                        ,offerImg,OfferPakagePrice,OfferPendingmsg,removeOfferMethod="";
                String addOfferMethod,alwaysCheck,minimumItemCount,offerAvailedImage,title="";
                JSONObject jsonObject = new JSONObject(offerdaitle);

                noOfProductInPackage=jsonObject.getString("noOfProductInPackage");
                addOfferMethod =jsonObject.getString("addOfferMethod");
                alwaysCheck=jsonObject.getString("alwaysCheck");
                Constants.discription=jsonObject.getString("discription");
                minimumItemCount=jsonObject.getString("minimumItemCount");
                offerAvailedImage=jsonObject.getString("offerAvailedImage");
                offerImg=jsonObject.getString("offerImg");
                OfferPakagePrice=jsonObject.getString("OfferPakagePrice");
                OfferPendingmsg=jsonObject.getString("OfferPendingmsg");
                removeOfferMethod=jsonObject.getString("removeOfferMethod");
                title=jsonObject.getString("title");
                holder.offerimgcart.setImageUrl("https://www.groceryshopper.info/apps_data/offer_images/"+offerImg, imageLoader);

            }catch (JSONException e){


            }
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

        holder.move.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               pDialog = new ProgressDialog(context);
               pDialog.setMessage("Loading...");
               pDialog.show();
               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       pDialog.dismiss();
                      if(!orderCatList.get(position).getSubItems().equals("0")){
                          AlertDialog.Builder builder = new AlertDialog.Builder(context);
                          builder.setMessage(orderCatList.get(position).getItemName()+" is Out Of Stock");
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
else {
                          boolean flag=false;
                          try {
                              Date mToday = new Date();

                              SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
                              String curTime = sdf.format(mToday);
                              Date start = sdf.parse("10:00 PM");
                              Date end = sdf.parse("10:00 AM");
                              Date userDate = sdf.parse(curTime);

                              if(end.before(start))
                              {
                                  Calendar mCal = Calendar.getInstance();
                                  mCal.setTime(end);
                                  mCal.add(Calendar.DAY_OF_YEAR, 1);
                                  end.setTime(mCal.getTimeInMillis());
                              }

                              if (userDate.after(start) && userDate.before(end)) {

                                  flag=true;
                              }
                              else{
                                  flag=false;
                              }
                          } catch (ParseException e) {

                          }


                          String d=orderCatList.get(position).getItemBase();
                          if(!orderCatList.get(position).getItemBase().equals("0")){

                              String time1 = new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());
                              String substring = time1.substring(Math.max(time1.length() - 2, 0));
                              try{

                                  AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                  builder.setMessage(orderCatList.get(position).getItemBasePrice());
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
                              catch (Exception p){

                                  String e=p.getMessage().toString();

                              }
                          }
                          else {
                              if(!orderCatList.get(position).getItemBasePizzaIndex().equals("")&&!orderCatList.get(position).getItemBasePizzaIndex().equals("0")&&Constants.agerestricted){
                                  {
                                      AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                      builder.setMessage(orderCatList.get(position).getBase());
                                      String positiveText = "I am at least"+orderCatList.get(position).getItemBasePizzaIndex();
                                      builder.setPositiveButton(positiveText,
                                              new DialogInterface.OnClickListener() {
                                                  @Override
                                                  public void onClick(DialogInterface dialog, int which) {
                                                      Constants.agerestricted=false;
                                                      if(!orderCatList.get(position).getoffer_complete().equals("0")){

                                                          Constants cons = new Constants();
                                                          String k = cons.singleitemquntity(context,orderCatList.get(position).getItemId());
                                                          if(k.equals("")){
                                                              k="1";
                                                          }
                                                          String j = orderCatList.get(position).getoffer_complete();
                                                          if (Integer.parseInt(k) < Integer.parseInt(j)) {

                                                              AlbumsAdapter.wistitem.remove(orderCatList.get(position).getItemId());
                                                              DataBaseHelper dbHelper = new DataBaseHelper(context);
                                                              OrderItemModel item = new OrderItemModel();
                                                              item.setItemId(orderCatList.get(position).getItemId());
                                                              item.setItemQuantity("1");
                                                              item.setItemName(orderCatList.get(position).getItemName());
                                                              item.setItemPrice(orderCatList.get(position).getItemPrice());
                                                              item.setoffer_type(orderCatList.get(position).getoffer_type());
                                                              item.setoffer_typetwo(orderCatList.get(position).getoffer_typetwo());
                                                              item.setoffer_typethree(orderCatList.get(position).getoffer_typethree());
                                                              item.setItemParentCategory(orderCatList.get(position).getItemParentCategory());
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
                                                              Wishlistdatabase dbHendler = new Wishlistdatabase(context);
                                                              dbHendler.deleteSingleItem(orderCatList.get(position));
                                                              addtocart(position);





                                                                } else {


                                                              AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                                              builder.setMessage(orderCatList.get(position).getSize());
                                                              String negativeText = "OK";
                                                              builder.setNegativeButton(negativeText,
                                                                      new DialogInterface.OnClickListener() {
                                                                          @Override
                                                                          public void onClick(DialogInterface dialog, int which) {

                                                                          }
                                                                      });

                                                              AlertDialog dialogg = builder.create();

                                                              dialogg.show();
                                                          }





                                                      }else {

                                                          AlbumsAdapter.wistitem.remove(orderCatList.get(position).getItemId());
                                                          DataBaseHelper dbHelper = new DataBaseHelper(context);
                                                          OrderItemModel item = new OrderItemModel();
                                                          item.setItemId(orderCatList.get(position).getItemId());
                                                          item.setItemQuantity("1");
                                                          item.setItemName(orderCatList.get(position).getItemName());
                                                          item.setItemPrice(orderCatList.get(position).getItemPrice());
                                                          item.setoffer_type(orderCatList.get(position).getoffer_type());
                                                          item.setoffer_typetwo(orderCatList.get(position).getoffer_typetwo());
                                                          item.setoffer_typethree(orderCatList.get(position).getoffer_typethree());
                                                          item.setItemParentCategory(orderCatList.get(position).getItemParentCategory());
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
                                                          Wishlistdatabase dbHendler = new Wishlistdatabase(context);
                                                          dbHendler.deleteSingleItem(orderCatList.get(position));
                                                          addtocart(position);
                                                      }
}
                                              });

                                      String negativeText = "Cancel";
                                      builder.setNegativeButton(negativeText,
                                              new DialogInterface.OnClickListener() {
                                                  @Override
                                                  public void onClick(DialogInterface dialog, int which) {

                                                  }
                                              });

                                      AlertDialog dialog = builder.create();

                                      dialog.show();
                                  }
                              }else {

                                  if(!orderCatList.get(position).getoffer_complete().equals("0")){

                                      Constants cons = new Constants();
                                      String k = cons.singleitemquntity(context,orderCatList.get(position).getItemId());
                                      if(k.equals("")){
                                          k="1";
                                      }
                                      String j = orderCatList.get(position).getoffer_complete();
                                      if (Integer.parseInt(k) < Integer.parseInt(j)) {

                                          AlbumsAdapter.wistitem.remove(orderCatList.get(position).getItemId());
                                          DataBaseHelper dbHelper = new DataBaseHelper(context);
                                          OrderItemModel item = new OrderItemModel();
                                          item.setItemId(orderCatList.get(position).getItemId());
                                          item.setItemQuantity("1");
                                          item.setItemName(orderCatList.get(position).getItemName());
                                          item.setItemPrice(orderCatList.get(position).getItemPrice());
                                          item.setoffer_type(orderCatList.get(position).getoffer_type());
                                          item.setoffer_typetwo(orderCatList.get(position).getoffer_typetwo());
                                          item.setoffer_typethree(orderCatList.get(position).getoffer_typethree());
                                          item.setItemParentCategory(orderCatList.get(position).getItemParentCategory());
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
                                          Wishlistdatabase dbHendler = new Wishlistdatabase(context);
                                          dbHendler.deleteSingleItem(orderCatList.get(position));
                                          addtocart(position);

                                      }
                                      else {
                                          {


                                              AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                              builder.setMessage(orderCatList.get(position).getSize());
                                              String negativeText = "OK";
                                              builder.setNegativeButton(negativeText,
                                                      new DialogInterface.OnClickListener() {
                                                          @Override
                                                          public void onClick(DialogInterface dialog, int which) {

                                                          }
                                                      });

                                              AlertDialog dialogg = builder.create();

                                              dialogg.show();
                                          }
                                      }




                                  }else {

                                      AlbumsAdapter.wistitem.remove(orderCatList.get(position).getItemId());
                                      DataBaseHelper dbHelper = new DataBaseHelper(context);
                                      OrderItemModel item = new OrderItemModel();
                                      item.setItemId(orderCatList.get(position).getItemId());
                                      item.setItemQuantity("1");
                                      item.setItemName(orderCatList.get(position).getItemName());
                                      item.setItemPrice(orderCatList.get(position).getItemPrice());
                                      item.setoffer_type(orderCatList.get(position).getoffer_type());
                                      item.setoffer_typetwo(orderCatList.get(position).getoffer_typetwo());
                                      item.setoffer_typethree(orderCatList.get(position).getoffer_typethree());
                                      item.setItemParentCategory(orderCatList.get(position).getItemParentCategory());
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
                                      Wishlistdatabase dbHendler = new Wishlistdatabase(context);
                                      dbHendler.deleteSingleItem(orderCatList.get(position));
                                      addtocart(position);


                                  }















                              }






                          }









                      }













                   }
               }, millisecondsToShowSplash);





           }
       });




        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                {final ProgressDialog  pDialog = new ProgressDialog(context);
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
                                    Wishlistdatabase dbHendler = new Wishlistdatabase(context);
                                    dbHendler.deleteSingleItem(orderCatList.get(position));
                                    ((Wishlistactivity)context).recreate();
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
            }
        });


    }

    public String getBaseString(int id) {
        String baseString = new String();

        AlbumsAdapter.wistitem.remove(String.valueOf(id));

        if (AlbumsAdapter.wistitem.size() > 0) {
            for (int i = 0; i < AlbumsAdapter.wistitem.size(); i++) {
                if (baseString.equals("")) {
                    baseString =AlbumsAdapter.wistitem.get(i);
                } else {
                    baseString = baseString + "," + AlbumsAdapter.wistitem.get(i);
                }
            }


        } else {

            baseString="";
        }
        return baseString;
    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }


    public class OrderViewHolder extends RecyclerView.ViewHolder {
        Button btnCancel, btnDelete;
        TextView delete,move;
        public TextView itemname, itemprice;
        NetworkImageView product,offerimgcart;
        public OrderViewHolder(View view) {
            super(view);
            product=(NetworkImageView)view.findViewById(R.id.grid_image_cart);
            itemname=(TextView)view.findViewById(R.id.grid_text_cart);
            itemprice=(TextView)view.findViewById(R.id.price);
            delete=(TextView) view.findViewById(R.id.deletetext);
            move=(TextView)view.findViewById(R.id.addtocart);
            offerimgcart=(NetworkImageView)view.findViewById(R.id.offerimgcart);
        }
    }

    public  void addtocart( final  int t){

        AlbumsAdapter.wistitem.remove(orderCatList.get(t).getItemId());

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
                            Wishlistdatabase dbHendler = new Wishlistdatabase(context);
                            dbHendler.deleteSingleItem(orderCatList.get(t));
                            ((Wishlistactivity)context).recreate();
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
                    params.put("items", getBaseString(Integer.parseInt(orderCatList.get(t).getItemId())));
                    Log.d("TAG@123", params.toString());
                    String d=params.toString();
                    return params;
                }
            };

            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        }
    }



}
