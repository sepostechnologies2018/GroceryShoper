package com.app_web.technology.groceryshopper.Addapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.app_web.technology.groceryshopper.Activitys.Items;
import com.app_web.technology.groceryshopper.Database.DataBaseHelper;
import com.app_web.technology.groceryshopper.Database.Wishlistdatabase;
import com.app_web.technology.groceryshopper.GetterSetter.ItemModel;
import com.app_web.technology.groceryshopper.Model.OrderItemModel;
import com.app_web.technology.groceryshopper.Model.WishlistOrderItemModel;
import com.app_web.technology.groceryshopper.util.AppController;
import com.app_web.technology.groceryshopper.util.Constants;
import com.app_web.technology.groceryshopper.util.Pref;
import com.app_web.technology.groceryshopper.util.UtillClass;
import com.app_web.technology.groceryshopper.util.VolleyMethods;
import com.app_web.technology.groceryshopper.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {
    public  static ArrayList<String> wistitem=new ArrayList<>();
    public  static ArrayList<String> agerestrictsale=new ArrayList<>();
    private Context mContext;
    private ProductItemActionListener actionListener;
    private List<ItemModel> albumList;
    final int sdk = android.os.Build.VERSION.SDK_INT;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count,subdis,quanitytext;
        public  NetworkImageView thumbnail,offerimg;
        public ImageView   wishimages,addtocart,remove;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            subdis= (TextView) view.findViewById(R.id.subdis);
            thumbnail = (NetworkImageView) view.findViewById(R.id.thumbnail);
            wishimages= (ImageView) view.findViewById(R.id.wish);
            addtocart=(ImageView)view.findViewById(R.id.addtocart);
            offerimg=(NetworkImageView)view.findViewById(R.id.offerimg);
            remove= (ImageView) view.findViewById(R.id.remove);
            quanitytext = (TextView) view.findViewById(R.id.quanitytext);
        }
    }


    public AlbumsAdapter(Context mContext, List<ItemModel> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }
    public void setActionListener(ProductItemActionListener actionListener) {
        this.actionListener = actionListener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position)
    {
     final ItemModel album = albumList.get(position);
        Constants.discription=new String("");
        imageLoader = AppController.getInstance().getImageLoader();
        String name=album.getItemName();
        Constants cons = new Constants();
        holder.quanitytext.setText(cons.singleitemquntity(mContext, album.getItemId()));
        Typeface face1= Typeface.createFromAsset(mContext.getAssets(), "fonts/aaa.ttf");
        holder.title.setTypeface(face1);
        holder.count.setTypeface(face1);
        holder.title.setText(album.getItemName());
        holder.count.setText("£ "+album.getItemPrice());
        holder.subdis.setText(album.getsmall_desc());
        String offerdaitle=album.getOfferDeatils();
        if(offerdaitle.equals("")||offerdaitle.equals("{}")){
            holder.offerimg.setImageUrl("https://www.groceryshopper.info/apps_data/offer_images/", imageLoader);
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
             holder.offerimg.setImageUrl("https://www.groceryshopper.info/apps_data/offer_images/"+offerImg, imageLoader);

        }catch (JSONException e){


            }
        }
        if (!album.getout_of_stock().equals("0")) {
            holder.subdis.setText("Out Of Stock");
            holder.subdis.setTextColor(Color.parseColor("#d70707"));


        }

        if (wistitem.contains(album.getItemId())) {
            holder.wishimages.setImageResource(R.drawable.like);
        } else {
            holder.wishimages.setImageResource(R.drawable.heartblack);
        }
        String pho=album.getitem_pic();
        holder.thumbnail.setImageUrl("https://www.groceryshopper.info/apps_data/item_images/"+album.getitem_pic(), imageLoader);
        if(!album.getitem_pic().equals("")){

           holder.thumbnail.setBackground( mContext.getResources().getDrawable(R.drawable.offerstranf));

       }
        else {

           holder.thumbnail.setBackground( mContext.getResources().getDrawable(R.drawable.defaultimg));

       }




        holder.offerimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{

                    JSONObject jsonObject = new JSONObject(album.getOfferDeatils());
                    Constants.discription=jsonObject.getString("discription");
                }catch (JSONException e){

                }
                String off=album.getOfferDeatils();
                if(album.getOfferDeatils().equals("")||album.getOfferDeatils().equals("{}")){

                }else {
                    VolleyMethods vm = new VolleyMethods(mContext);
                    vm.offeritem(album.getoffer_type(),album.getOfferId(),0);

                }





            }
        });






////////////////////add to cart////////////////


        holder.addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

if(!album.getout_of_stock().equals("0")){

    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
    builder.setMessage(album.getItemName()+" is Out Of Stock");
    String negativeText = "OK";
    builder.setNegativeButton(negativeText,
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

    AlertDialog dialog = builder.create();
    dialog.show();


}else {

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
    } catch (ParseException e)
    {

    }


String  restrict_sales=album.getrestrict_sales();

    if(!album.getrestrict_sales().equals("0")){

        String time1 = new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());
        String substring = time1.substring(Math.max(time1.length() - 2, 0));
        try{

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage(album.getrestrict_sales_msg());
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
    }else {



        if(!album.getage_restriction().equals("")&&Constants.agerestricted&&!album.getage_restriction().equals("0")){
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage(album.getage_restrict_msg());
                        String positiveText = "I am at least"+album.getage_restriction();
                        builder.setPositiveButton(positiveText,
                                        new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Constants.agerestricted=false;
                                        agerestrictsale.add(album.getItemId());
                                        {
                                            DataBaseHelper dbHelper = new DataBaseHelper(mContext);
                                            OrderItemModel isExist = dbHelper.getSingleItem(album.getItemId(), "", "", "", album.getItemPrice());
                                            if (isExist != null) {
                                                int quantity;
                                                quantity = Integer.parseInt(isExist.getItemQuantity()) + 1;
                                                String i=isExist.getItemQuantity();
                                                if(album.getoffer_type().equals("1"))  {
                                                    if ((Integer.parseInt(i) / 2) * 2 == Integer.parseInt(i)) {
                                                        isExist.setItemQuantity("" + quantity);
                                                        dbHelper.upDateOrder(isExist);
                                                    }
                                                    else {
                                                        int perfect=Integer.parseInt(i)+1;
                                                        float ty=Float.parseFloat(album.getItemPrice())*(perfect/2);
                                                        if (isExist != null) {
                                                            isExist.setItemQuantity("" + quantity);
                                                            isExist.setSpecialTips(String.valueOf(ty));
                                                            dbHelper.upDateOrder(isExist);

                                                        }
                                                    }

                                                    holder.addtocart.setImageResource(R.drawable.wishicon);
                                                    Constants cons = new Constants();
                                                    ((Items)mContext).txt_item_quantity.setVisibility(View.VISIBLE);
                                                    ((Items)mContext).txt_item_quantity.setText(cons.getQuantity(mContext));
                                                    holder.quanitytext.setText(cons.singleitemquntity(mContext, album.getItemId()));

                                                    Bundle bundle = dbHelper.getItemQuantity();
                                                    ((Items)mContext).txt_item_price.setText("£" + bundle.getString("TotalPrice"));
                                                    ((Items) mContext).popuplpogpout(album.getItemName().toString() + " was added to your WishList");
                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {

                                                            holder.addtocart.setImageResource(R.drawable.add);

                                                        }

                                                    }, 100L);
                                                }
                                                else  if(album.getoffer_type().equals("2")){
                                                    if ((Integer.parseInt(i) / 2) * 2 == Integer.parseInt(i)) {

                                                        isExist.setItemQuantity("" + quantity);
                                                        dbHelper.upDateOrder(isExist);



                                                    }
                                                    else {
                                                        int perfect=Integer.parseInt(i)+1;
                                                        float ty=(Float.parseFloat(album.getItemPrice())*(perfect/2))/2;
                                                        if (isExist != null) {

                                                            isExist.setItemQuantity("" + quantity);
                                                            isExist.setSpecialTips(String.valueOf(ty));
                                                            dbHelper.upDateOrder(isExist);



                                                        }




                                                    }

                                                    holder.addtocart.setImageResource(R.drawable.wishicon);
                                                    Constants cons = new Constants();
                                                    ((Items)mContext).txt_item_quantity.setVisibility(View.VISIBLE);
                                                    ((Items)mContext).txt_item_quantity.setText(cons.getQuantity(mContext));
                                                    holder.quanitytext.setText(cons.singleitemquntity(mContext, album.getItemId()));
                                                    Bundle bundle = dbHelper.getItemQuantity();
                                                    ((Items)mContext).txt_item_price.setText("£" + bundle.getString("TotalPrice"));
                                                    ((Items) mContext).popuplpogpout(album.getItemName().toString() + " was added to your Cart");
                                                    new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        holder.addtocart.setImageResource(R.drawable.add);

                                                    }

                                                }, 100L);

                                            }

                                                else  if(album.getoffer_type().equals("3")){{
                                                    isExist.setItemQuantity("" + quantity);
                                                    //isExist.setoffer_complete("" + "false");
                                                    dbHelper.upDateOrder(isExist);
                                                    dbHelper.getofferOrder(album.getoffer_type(),album.getOfferId());
                                                    if(Constants.offerid.size()>0){
                                                        for(int l=0;l<Constants.offerid.size();l++)
                                                        {
                                                            String price=Constants.offersprice.get(l);
                                                            String id=Constants.offerid.get(l);
                                                            DataBaseHelper db = new DataBaseHelper(mContext);
                                                            OrderItemModel is= db.getSingleItem(Constants.offerid.get(l), "", "", "", Constants.offersprice.get(l));
                                                            is.setSpecialTips(String.valueOf(Constants.offersdis.get(l)));
                                                            is.setoffer_complete("true");
                                                            db.updateofferprice(is);


                                                        }

                                                    }
                                                    Constants.offerid.clear();
                                                    Constants.offersdis.clear();
                                                    Constants.offersprice.clear();

                                                }
                                                    holder.addtocart.setImageResource(R.drawable.wishicon);
                                                    Constants cons = new Constants();
                                                    ((Items)mContext).txt_item_quantity.setVisibility(View.VISIBLE);
                                                    ((Items)mContext).txt_item_quantity.setText(cons.getQuantity(mContext));
                                                    holder.quanitytext.setText(cons.singleitemquntity(mContext, album.getItemId()));
                                                    Bundle bundle = dbHelper.getItemQuantity();
                                                    ((Items)mContext).txt_item_price.setText("£" + bundle.getString("TotalPrice"));
                                                    ((Items) mContext).popuplpogpout(album.getItemName().toString() + " was added to your Cart");
                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {

                                                            holder.addtocart.setImageResource(R.drawable.add);

                                                        }

                                                    }, 100L);
                                                }

                                                else {
                                                    isExist.setItemQuantity("" + quantity);
                                                    dbHelper.upDateOrder(isExist);
                                                    holder.addtocart.setImageResource(R.drawable.wishicon);
                                                    Constants cons = new Constants();
                                                    ((Items)mContext).txt_item_quantity.setVisibility(View.VISIBLE);
                                                    ((Items)mContext).txt_item_quantity.setText(cons.getQuantity(mContext));
                                                    holder.quanitytext.setText(cons.singleitemquntity(mContext, album.getItemId()));
                                                    Bundle bundle = dbHelper.getItemQuantity();
                                                    ((Items)mContext).txt_item_price.setText("£" + bundle.getString("TotalPrice"));
                                                    ((Items) mContext).popuplpogpout(album.getItemName().toString() + " was added to your Cart");
                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {

                                                            holder.addtocart.setImageResource(R.drawable.add);

                                                        }

                                                    }, 100L);



                                                }








                                            }




                                            else {
                                            OrderItemModel item = new OrderItemModel();
                                            item.setItemId(album.getItemId());
                                            item.setItemQuantity("1");
                                            item.setItemName(album.getItemName());
                                            item.setItemPrice(album.getItemPrice());
                                            item.setoffer_type(album.getoffer_type());
                                            item.setoffer_typetwo(album.getOfferId());
                                            item.setoffer_typethree(album.getOfferDeatils());
                                            item.setItemParentCategory(album.getisTimeRistrictedItem());
                                            item.setoffer_complete("false");
                                            item.setSubItems("");
                                            item.setItemBase("");
                                            item.setItemBasePrice("");
                                            item.setItemBasePizzaIndex("");
                                            item.setBase("");
                                            item.setSize("");
                                              item.setID(album.getbaseket_limit_msg());
                                              item.setFree_toppings(album.getbasket_limit());
                                            item.setSpecial_instruction(album.getitem_pic());
                                            item.setSpecialTips("0.0");
                                            dbHelper.addItemToOrder(item);

                                        }


                                            holder.addtocart.setImageResource(R.drawable.wishicon);
                                            Constants cons = new Constants();
                                            ((Items)mContext).txt_item_quantity.setVisibility(View.VISIBLE);
                                            ((Items)mContext).txt_item_quantity.setText(cons.getQuantity(mContext));
                                            String i_quantity=cons.singleitemquntity(mContext, album.getItemId());
                                            holder.quanitytext.setText(cons.singleitemquntity(mContext, album.getItemId()));
                                            Bundle bundle = dbHelper.getItemQuantity();
                                            ((Items)mContext).txt_item_price.setText("£" + bundle.getString("TotalPrice"));
                                            ((Items) mContext).popuplpogpout(album.getItemName().toString() + " was added to your Cart");
                                            ((Items)mContext).update();
                                            if(actionListener!=null){
                                                actionListener.onItemTap(holder.thumbnail);
                                            }

                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {

                                                    holder.addtocart.setImageResource(R.drawable.add);

                                                }

                                            }, 100L);
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
                }

                else {


                    if(!album.getbasket_limit().equals("0")){

                        {

                            Constants cons = new Constants();
                            String k = cons.singleitemquntity(mContext, album.getItemId());
                            if(k.equals("")){
                            k="1";
                        }
                            String j = album.getbasket_limit();
                            if (Integer.parseInt(k) < Integer.parseInt(j)) {
                                {


                                    DataBaseHelper dbHelper = new DataBaseHelper(mContext);
                                    OrderItemModel isExist = dbHelper.getSingleItem(album.getItemId(), "", "", "", album.getItemPrice());
                                    if (isExist != null) {
                                        int quantity;
                                        quantity = Integer.parseInt(isExist.getItemQuantity()) + 1;
                                        String i=isExist.getItemQuantity();
                                        if(album.getoffer_type().equals("1"))  {
                                            if ((Integer.parseInt(i) / 2) * 2 == Integer.parseInt(i)) {

                                                isExist.setItemQuantity("" + quantity);
                                                dbHelper.upDateOrder(isExist);



                                            }
                                            else {





                                                int perfect=Integer.parseInt(i)+1;
                                                float ty=Float.parseFloat(album.getItemPrice())*(perfect/2);
                                                if (isExist != null) {

                                                    isExist.setItemQuantity("" + quantity);
                                                    isExist.setSpecialTips(String.valueOf(ty));
                                                    dbHelper.upDateOrder(isExist);

                                                }




                                            }

                                            holder.addtocart.setImageResource(R.drawable.wishicon);

                                            ((Items)mContext).txt_item_quantity.setVisibility(View.VISIBLE);
                                            ((Items)mContext).txt_item_quantity.setText(cons.getQuantity(mContext));
                                            holder.quanitytext.setText(cons.singleitemquntity(mContext, album.getItemId()));

                                            Bundle bundle = dbHelper.getItemQuantity();
                                            ((Items)mContext).txt_item_price.setText("£" + bundle.getString("TotalPrice"));
                                            ((Items) mContext).popuplpogpout(album.getItemName().toString() + " was added to your Cart");
                                            ((Items)mContext).update();
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {

                                                    holder.addtocart.setImageResource(R.drawable.add);

                                                }

                                            }, 100L);
                                        }
                                        else  if(album.getoffer_type().equals("2")){
                                            if ((Integer.parseInt(i) / 2) * 2 == Integer.parseInt(i)) {

                                                isExist.setItemQuantity("" + quantity);
                                                dbHelper.upDateOrder(isExist);



                                            }
                                            else {





                                                int perfect=Integer.parseInt(i)+1;
                                                float ty=(Float.parseFloat(album.getItemPrice())*(perfect/2))/2;
                                                if (isExist != null) {

                                                    isExist.setItemQuantity("" + quantity);
                                                    isExist.setSpecialTips(String.valueOf(ty));
                                                    dbHelper.upDateOrder(isExist);

                                                }




                                            }

                                            holder.addtocart.setImageResource(R.drawable.wishicon);

                                            ((Items)mContext).txt_item_quantity.setVisibility(View.VISIBLE);
                                            ((Items)mContext).txt_item_quantity.setText(cons.getQuantity(mContext));
                                            holder.quanitytext.setText(cons.singleitemquntity(mContext, album.getItemId()));

                                            Bundle bundle = dbHelper.getItemQuantity();
                                            ((Items)mContext).txt_item_price.setText("£" + bundle.getString("TotalPrice"));

                                            ((Items) mContext).popuplpogpout(album.getItemName().toString() + " was added to your Cart");
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {

                                                    holder.addtocart.setImageResource(R.drawable.add);

                                                }

                                            }, 100L);

                                        }

                                        else  if(album.getoffer_type().equals("3")){{
                                            isExist.setItemQuantity("" + quantity);
                                            isExist.setoffer_complete("" + "false");
                                            dbHelper.upDateOrder(isExist);
                                            dbHelper.getofferOrder(album.getoffer_type(),album.getOfferId());
                                            if(Constants.offerid.size()>0){
                                                for(int l=0;l<Constants.offerid.size();l++)
                                                {
                                                    String price=Constants.offersprice.get(l);
                                                    String id=Constants.offerid.get(l);
                                                    DataBaseHelper db = new DataBaseHelper(mContext);
                                                    OrderItemModel is= db.getSingleItem(Constants.offerid.get(l), "", "", "", Constants.offersprice.get(l));
                                                    is.setSpecialTips(String.valueOf(Constants.offersdis.get(l)));
                                                    is.setoffer_complete("true");
                                                    db.updateofferprice(is);


                                                }

                                            }
                                            Constants.offerid.clear();
                                            Constants.offersdis.clear();
                                            Constants.offersprice.clear();

                                        }
                                            holder.addtocart.setImageResource(R.drawable.wishicon);
                                            ((Items)mContext).txt_item_quantity.setVisibility(View.VISIBLE);
                                            ((Items)mContext).txt_item_quantity.setText(cons.getQuantity(mContext));
                                            holder.quanitytext.setText(cons.singleitemquntity(mContext, album.getItemId()));

                                            Bundle bundle = dbHelper.getItemQuantity();
                                            ((Items)mContext).txt_item_price.setText("£" + bundle.getString("TotalPrice"));
                                            ((Items) mContext).popuplpogpout(album.getItemName().toString() + " was added to your Cart");
                                            ((Items)mContext).update();
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {

                                                    holder.addtocart.setImageResource(R.drawable.add);

                                                }

                                            }, 100L);
                                        }

                                        else {
                                            isExist.setItemQuantity("" + quantity);
                                            dbHelper.upDateOrder(isExist);

                                            holder.addtocart.setImageResource(R.drawable.wishicon);
                                            ((Items)mContext).txt_item_quantity.setVisibility(View.VISIBLE);
                                            ((Items)mContext).txt_item_quantity.setText(cons.getQuantity(mContext));
                                            holder.quanitytext.setText(cons.singleitemquntity(mContext, album.getItemId()));

                                            Bundle bundle = dbHelper.getItemQuantity();
                                            ((Items)mContext).txt_item_price.setText("£" + bundle.getString("TotalPrice"));
                                            ((Items) mContext).popuplpogpout(album.getItemName().toString() + " was added to your Cart");
                                            ((Items)mContext).update();
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {

                                                    holder.addtocart.setImageResource(R.drawable.add);

                                                }

                                            }, 100L);
                                        }
                                    }
                                    else {
                                    OrderItemModel item = new OrderItemModel();
                                    item.setItemId(album.getItemId());
                                    item.setItemQuantity("1");
                                    item.setItemName(album.getItemName());
                                    item.setItemPrice(album.getItemPrice());
                                    item.setoffer_type(album.getoffer_type());
                                    item.setoffer_typetwo(album.getOfferId());
                                    item.setoffer_typethree(album.getOfferDeatils());
                                        item.setItemParentCategory(album.getisTimeRistrictedItem());
                                    item.setoffer_complete("false");
                                    item.setSubItems("");
                                    item.setItemBase("");
                                    item.setItemBasePrice("");
                                    item.setItemBasePizzaIndex("");
                                    item.setBase("");
                                    item.setSize("");
                                    item.setID(album.getbaseket_limit_msg());
                                    item.setFree_toppings(album.getbasket_limit());
                                    item.setSpecial_instruction(album.getitem_pic());
                                    item.setSpecialTips("0.0");
                                    dbHelper.addItemToOrder(item);

                                        holder.addtocart.setImageResource(R.drawable.wishicon);
                                        ((Items) mContext).popuplpogpout(album.getItemName().toString() + " was added to your Cart");
                                        ((Items)mContext).update();
                                        Constants constants = new Constants();
                                        holder.quanitytext.setText(cons.singleitemquntity(mContext, album.getItemId()));

                                        ((Items)mContext).txt_item_quantity.setVisibility(View.VISIBLE);
                                        ((Items)mContext).txt_item_quantity.setText(constants.getQuantity(mContext));
                                        if(actionListener!=null){
                                            actionListener.onItemTap(holder.thumbnail);}
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {

                                                holder.addtocart.setImageResource(R.drawable.add);

                                            }

                                        }, 100L);

                                }
                                    holder.addtocart.setImageResource(R.drawable.wishicon);
                                    ((Items) mContext).popuplpogpout(album.getItemName().toString() + " was added to your Cart");
                                    ((Items)mContext).update();

                                    Constants constants = new Constants();
                                    holder.quanitytext.setText(cons.singleitemquntity(mContext, album.getItemId()));

                                    ((Items)mContext).txt_item_quantity.setVisibility(View.VISIBLE);
                                    ((Items)mContext).txt_item_quantity.setText(constants.getQuantity(mContext));
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            holder.addtocart.setImageResource(R.drawable.add);

                                        }

                                    }, 100L);
                                }

                            } else {


                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                builder.setMessage(album.getbaseket_limit_msg());
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
                    }else {


                        DataBaseHelper dbHelper = new DataBaseHelper(mContext);
                        OrderItemModel isExist = dbHelper.getSingleItem(album.getItemId(), "", "", "", album.getItemPrice());
                        if (isExist != null) {
                            int quantity;
                            quantity = Integer.parseInt(isExist.getItemQuantity()) + 1;
                            String i=isExist.getItemQuantity();
                            if(album.getoffer_type().equals("1"))  {
                                if ((Integer.parseInt(i) / 2) * 2 == Integer.parseInt(i)) {

                                    isExist.setItemQuantity("" + quantity);
                                    dbHelper.upDateOrder(isExist);
                                }
                                else {

                                    int perfect=Integer.parseInt(i)+1;
                                    float ty=Float.parseFloat(album.getItemPrice())*(perfect/2);
                                    if (isExist != null) {

                                        isExist.setItemQuantity("" + quantity);
                                        isExist.setSpecialTips(String.valueOf(ty));
                                        dbHelper.upDateOrder(isExist);

                                    }




                                }
                                holder.addtocart.setImageResource(R.drawable.wishicon);
                                Constants cons = new Constants();
                                ((Items)mContext).txt_item_quantity.setVisibility(View.VISIBLE);
                                holder.quanitytext.setText(cons.singleitemquntity(mContext, album.getItemId()));

                                ((Items)mContext).txt_item_quantity.setText(cons.getQuantity(mContext));
                                Bundle bundle = dbHelper.getItemQuantity();
                                ((Items)mContext).txt_item_price.setText("£" + bundle.getString("TotalPrice"));
                                holder.quanitytext.setText(cons.singleitemquntity(mContext, album.getItemId()));
                                ((Items) mContext).popuplpogpout(album.getItemName().toString() + " was added to your Cart");
                                ((Items)mContext).update();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        holder.addtocart.setImageResource(R.drawable.add);

                                    }

                                }, 100L);

                            }        else  if(album.getoffer_type().equals("2")){
                                if ((Integer.parseInt(i) / 2) * 2 == Integer.parseInt(i)) {

                                    isExist.setItemQuantity("" + quantity);
                                    dbHelper.upDateOrder(isExist);



                                }
                                else {





                                    int perfect=Integer.parseInt(i)+1;
                                    float ty=(Float.parseFloat(album.getItemPrice())*(perfect/2))/2;
                                    if (isExist != null) {
                                        isExist.setItemQuantity("" + quantity);
                                        isExist.setSpecialTips(String.valueOf(ty));
                                        dbHelper.upDateOrder(isExist);

                                    }




                                }

                                holder.addtocart.setImageResource(R.drawable.wishicon);
                                Constants cons = new Constants();
                                ((Items)mContext).txt_item_quantity.setVisibility(View.VISIBLE);
                                ((Items)mContext).txt_item_quantity.setText(cons.getQuantity(mContext));
                                holder.quanitytext.setText(cons.singleitemquntity(mContext, album.getItemId()));
                                Bundle bundle = dbHelper.getItemQuantity();
                                ((Items)mContext).txt_item_price.setText("£" + bundle.getString("TotalPrice"));
                                holder.quanitytext.setText(cons.singleitemquntity(mContext, album.getItemId()));
                                ((Items) mContext).popuplpogpout(album.getItemName().toString() + " was added to your Cart");
                                ((Items)mContext).update();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        holder.addtocart.setImageResource(R.drawable.add);

                                    }

                                }, 100L);

                            }
                            else  if(album.getoffer_type().equals("3")){
                                isExist.setItemQuantity("" + quantity);
                                dbHelper.upDateOrder(isExist);
                                dbHelper.getofferOrder(album.getoffer_type(),album.getOfferId());
                                if(Constants.offerid.size()>0){
                                    for(int l=0;l<Constants.offerid.size();l++)
                                    {
                                        String price=Constants.offersprice.get(l);
                                        String id=Constants.offerid.get(l);
                                        DataBaseHelper db = new DataBaseHelper(mContext);
                                        OrderItemModel is= db.getSingleItem(Constants.offerid.get(l), "", "", "", Constants.offersprice.get(l));
                                        is.setSpecialTips(String.valueOf(Constants.offersdis.get(l)));
                                        is.setoffer_complete("true");
                                        db.updateofferprice(is);


                                    }

                                }
                                Constants.offerid.clear();
                                Constants.offersdis.clear();
                                Constants.offersprice.clear();
                                ((Items) mContext).popuplpogpout(album.getItemName().toString() + " was added to your Cart");
                                ((Items)mContext).update();
                                holder.addtocart.setImageResource(R.drawable.wishicon);
                                Constants cons = new Constants();
                                holder.quanitytext.setText(cons.singleitemquntity(mContext, album.getItemId()));
                                ((Items) mContext).txt_item_quantity.setVisibility(View.VISIBLE);
                                ((Items) mContext).txt_item_quantity.setText(cons.getQuantity(mContext));
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        holder.addtocart.setImageResource(R.drawable.add);
                                    }

                                }, 100L);

                            }
                            else {
                                isExist.setItemQuantity("" + quantity);
                                dbHelper.upDateOrder(isExist);



                                ((Items) mContext).popuplpogpout(album.getItemName().toString() + " was added to your Cart");
                                ((Items)mContext).update();
                                holder.addtocart.setImageResource(R.drawable.wishicon);
                                Constants cons = new Constants();
                                ((Items) mContext).txt_item_quantity.setVisibility(View.VISIBLE);
                                ((Items) mContext).txt_item_quantity.setText(cons.getQuantity(mContext));
                                holder.quanitytext.setText(cons.singleitemquntity(mContext, album.getItemId()));
                                Bundle bundle = dbHelper.getItemQuantity();
                                ((Items)mContext).txt_item_price.setText("£" + bundle.getString("TotalPrice"));
                                holder.quanitytext.setText(cons.singleitemquntity(mContext, album.getItemId()));


                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        holder.addtocart.setImageResource(R.drawable.add);
                                    }

                                }, 100L);

                            }








                        }




                        else {

                            if(album.getoffer_type().equals("3")){
                                OrderItemModel item = new OrderItemModel();
                                item.setItemId(album.getItemId());
                                item.setItemQuantity("1");
                                item.setItemName(album.getItemName());
                                item.setItemPrice(album.getItemPrice());
                                item.setoffer_type(album.getoffer_type());
                                item.setoffer_typetwo(album.getOfferId());
                                item.setoffer_typethree(album.getOfferDeatils());
                                item.setItemParentCategory(album.getisTimeRistrictedItem());
                                item.setoffer_complete("false");
                                item.setSubItems("");
                                item.setItemBase("");
                                item.setItemBasePrice("");
                                item.setItemBasePizzaIndex("");
                                item.setBase("");
                                item.setSize("");
                                item.setID(album.getbaseket_limit_msg());
                                item.setFree_toppings(album.getbasket_limit());
                                item.setSpecial_instruction(album.getitem_pic());
                                item.setSpecialTips("0.0");
                                dbHelper.addItemToOrder(item);
                                Constants constants=new Constants();
                                String applice = constants.offersquanity(mContext,album.getoffer_type(),album.getOfferId());
                                if(!applice.isEmpty()){


                                }
                                dbHelper.getofferOrder(album.getoffer_type(),album.getOfferId());
                                if(Constants.offerid.size()>0){
                                    for(int l=0;l<Constants.offerid.size();l++)
                                    {
                                        DataBaseHelper db = new DataBaseHelper(mContext);
                                        OrderItemModel is= db.getSingleItem(Constants.offerid.get(l), "", "", "", Constants.offersprice.get(l));
                                        is.setSpecialTips(String.valueOf(Constants.offersdis.get(l)));
                                        dbHelper.upDateOrder(is);


                                    }


                                }
                                Constants.offerid.clear();
                                Constants.offersdis.clear();
                                Constants.offersprice.clear();


                            }
                            else {
                            OrderItemModel item = new OrderItemModel();
                            item.setItemId(album.getItemId());
                            item.setItemQuantity("1");
                            item.setItemName(album.getItemName());
                            item.setItemPrice(album.getItemPrice());
                            item.setoffer_type(album.getoffer_type());
                            item.setoffer_typetwo(album.getOfferId());
                            item.setoffer_typethree(album.getOfferDeatils());
                            item.setItemParentCategory(album.getisTimeRistrictedItem());
                            item.setoffer_complete("false");
                            item.setSubItems("");
                            item.setItemBase("");
                            item.setItemBasePrice("");
                            item.setItemBasePizzaIndex("");
                            item.setBase("");
                            item.setSize("");
                                item.setID(album.getbaseket_limit_msg());
                                item.setFree_toppings(album.getbasket_limit());
                            item.setSpecial_instruction(album.getitem_pic());
                            item.setSpecialTips("0.0");
                            dbHelper.addItemToOrder(item);


                        }

                        ((Items) mContext).popuplpogpout(album.getItemName().toString() + " was added to your Cart");
                            ((Items)mContext).update();
                        holder.addtocart.setImageResource(R.drawable.wishicon);
                        Constants cons = new Constants();
                        ((Items) mContext).txt_item_quantity.setVisibility(View.VISIBLE);
                        ((Items) mContext).txt_item_quantity.setText(cons.getQuantity(mContext));
                            holder.quanitytext.setText(cons.singleitemquntity(mContext, album.getItemId()));
                            Bundle bundle = dbHelper.getItemQuantity();
                            ((Items)mContext).txt_item_price.setText("£" + bundle.getString("TotalPrice"));
                            holder.quanitytext.setText(cons.singleitemquntity(mContext, album.getItemId()));
                            holder.addtocart.setImageResource(R.drawable.wishicon);
                            if(actionListener!=null){
                                actionListener.onItemTap(holder.thumbnail);}
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                holder.addtocart.setImageResource(R.drawable.add);
                            }

                        }, 100L);

                        }

                    }}}}
        }});





        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean cond=false;
                ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
                scale.setDuration(500);
                scale.setInterpolator(new OvershootInterpolator());
                holder.thumbnail.startAnimation(scale);

                Constants constants=new Constants();
                String qui=constants.singleitemquntity(mContext, album.getItemId());

                if(qui.equals("0")){

                }else {


                  if(qui.equals("1"))  {


                      DataBaseHelper dbHelperr = new DataBaseHelper(mContext);
                      OrderItemModel isExistr = dbHelperr.getSingleItem(album.getItemId(), "", "", "", album.getItemPrice());

                      dbHelperr.deleteSingleItem(isExistr);
                      holder.quanitytext.setText("0");
                      Constants cons = new Constants();
                      ((Items) mContext).txt_item_quantity.setVisibility(View.VISIBLE);
                      ((Items) mContext).txt_item_quantity.setText(cons.getQuantity(mContext));
                      Bundle bundle = dbHelperr.getItemQuantity();
                      ((Items) mContext).txt_item_price.setText("£" + bundle.getString("TotalPrice"));
                      ((Items)mContext).update();
                       holder.remove.setImageResource(R.drawable.clear);
                      new Handler().postDelayed(new Runnable() {
                          @Override
                          public void run() {
                              holder.remove.setImageResource(R.drawable.remove);
                          }

                      }, 100L);

                  }else {




                    if (!album.getout_of_stock().equals("0")&&cond) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage(album.getItemName() + " is Out Of Stock");
                        String negativeText = "OK";
                        builder.setNegativeButton(negativeText,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                        AlertDialog dialog = builder.create();
                        dialog.show();


                    } else {

                        boolean flag=false;
                        try {
                            Date date = new Date();

                            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");

                            String curTime = sdf.format(date);
                            Date userDate = sdf.parse(curTime);
                            Date end = sdf.parse("10:00 PM");
                            Date start = sdf.parse("10:00 AM");
                            if(userDate.after(end)&& userDate.before(start)){
                                flag=true;
                            }
                        }

                        catch (ParseException e){

                        }

                        if (!album.getrestrict_sales().equals("0")&&cond) {
                            String time1 = new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());
                            String substring = time1.substring(Math.max(time1.length() - 2, 0));
                            try {

                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                builder.setMessage(album.getrestrict_sales_msg());
                                String negativeText = "OK";
                                builder.setNegativeButton(negativeText,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });

                                AlertDialog dialog = builder.create();
                                dialog.show();


                            } catch (Exception p) {
                                String e = p.getMessage().toString();

                            }
                        } else {
                            if (!album.getage_restriction().equals("")&&cond) {} else {


                                if (!album.getbasket_limit().equals("0")&&cond) {} else {


                                    DataBaseHelper dbHelper = new DataBaseHelper(mContext);

                                    OrderItemModel isExist = dbHelper.getSingleItemdelete(album.getItemId());
                                    if (isExist != null) {
                                        int quantity;
                                        quantity = Integer.parseInt(isExist.getItemQuantity()) -1;
                                        String i = isExist.getItemQuantity();
                                        if (album.getoffer_type().equals("1")) {
                                            if ((Integer.parseInt(i) / 2) * 2 == Integer.parseInt(i)) {

                                                isExist.setItemQuantity("" + quantity);
                                                dbHelper.upDateOrder(isExist);
                                            } else {

                                                int perfect = Integer.parseInt(i) + 1;
                                                float ty = Float.parseFloat(album.getItemPrice()) * (perfect / 2);
                                                if (isExist != null) {

                                                    isExist.setItemQuantity("" + quantity);
                                                    isExist.setSpecialTips(String.valueOf(ty));
                                                    dbHelper.upDateOrder(isExist);

                                                }


                                            }
                                            holder.quanitytext.setText(String.valueOf(quantity));
                                            Constants cons = new Constants();
                                            ((Items) mContext).txt_item_quantity.setVisibility(View.VISIBLE);
                                            ((Items) mContext).txt_item_quantity.setText(cons.getQuantity(mContext));
                                            Bundle bundle = dbHelper.getItemQuantity();
                                            ((Items) mContext).txt_item_price.setText("£" + bundle.getString("TotalPrice"));
                                            ((Items)mContext).update();
                                            holder.remove.setImageResource(R.drawable.clear);
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    holder.remove.setImageResource(R.drawable.remove);
                                                }

                                            }, 100L);

                                        } else if (album.getoffer_type().equals("2")) {
                                            if ((Integer.parseInt(i) / 2) * 2 == Integer.parseInt(i)) {

                                                isExist.setItemQuantity("" + quantity);
                                                dbHelper.upDateOrder(isExist);


                                            } else {


                                                int perfect = Integer.parseInt(i) + 1;
                                                float ty = (Float.parseFloat(album.getItemPrice()) * (perfect / 2)) / 2;
                                                if (isExist != null) {
                                                    isExist.setItemQuantity("" + quantity);
                                                    isExist.setSpecialTips(String.valueOf(ty));
                                                    dbHelper.upDateOrder(isExist);

                                                }


                                            }
                                            holder.quanitytext.setText(String.valueOf(quantity));
                                            Constants cons = new Constants();
                                            ((Items) mContext).txt_item_quantity.setVisibility(View.VISIBLE);
                                            ((Items) mContext).txt_item_quantity.setText(cons.getQuantity(mContext));
                                            Bundle bundle = dbHelper.getItemQuantity();
                                            ((Items) mContext).txt_item_price.setText("£" + bundle.getString("TotalPrice"));
                                            ((Items)mContext).update();

                                            holder.remove.setImageResource(R.drawable.clear);
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    holder.remove.setImageResource(R.drawable.remove);
                                                }

                                            }, 100L);
                                        } else if (album.getoffer_type().equals("3")) {
                                            isExist.setItemQuantity("" + quantity);
                                            dbHelper.upDateOrder(isExist);
                                            dbHelper.getofferOrder(album.getoffer_type(), album.getOfferId());
                                            if (Constants.offerid.size() > 0) {
                                                for (int l = 0; l < Constants.offerid.size(); l++) {
                                                    String price = Constants.offersprice.get(l);
                                                    String id = Constants.offerid.get(l);
                                                    DataBaseHelper db = new DataBaseHelper(mContext);
                                                    OrderItemModel is = db.getSingleItem(Constants.offerid.get(l), "", "", "", Constants.offersprice.get(l));
                                                    is.setSpecialTips(String.valueOf(Constants.offersdis.get(l)));
                                                    is.setoffer_complete("true");
                                                    db.updateofferprice(is);


                                                }

                                            }
                                            Constants.offerid.clear();
                                            Constants.offersdis.clear();
                                            Constants.offersprice.clear();
                                            Constants cons = new Constants();
                                            holder.quanitytext.setText(String.valueOf(quantity));
                                            ((Items) mContext).txt_item_quantity.setVisibility(View.VISIBLE);
                                            ((Items) mContext).txt_item_quantity.setText(cons.getQuantity(mContext));
                                            Bundle bundle = dbHelper.getItemQuantity();
                                            ((Items) mContext).txt_item_price.setText("£" + bundle.getString("TotalPrice"));
                                            ((Items)mContext).update();
                                            holder.remove.setImageResource(R.drawable.clear);
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    holder.remove.setImageResource(R.drawable.remove);
                                                }

                                            }, 100L);

                                        } else {
                                            isExist.setItemQuantity("" + quantity);
                                            dbHelper.upDateOrder(isExist);

                                            holder.quanitytext.setText(String.valueOf(quantity));
                                            Constants cons = new Constants();
                                            ((Items) mContext).txt_item_quantity.setVisibility(View.VISIBLE);
                                            ((Items) mContext).txt_item_quantity.setText(cons.getQuantity(mContext));
                                            Bundle bundle = dbHelper.getItemQuantity();
                                            ((Items) mContext).txt_item_price.setText("£" + bundle.getString("TotalPrice"));
                                            ((Items)mContext).update();
                                            holder.remove.setImageResource(R.drawable.clear);
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    holder.remove.setImageResource(R.drawable.remove);
                                                }

                                            }, 100L);
                                        }


                                    } else {
                                        Toast.makeText(mContext,"Delete",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                        }
                    }}}

            }


        });









        /////////////////////

        holder.wishimages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


if(wistitem.contains(album.getItemId())){

    Toast.makeText(mContext, "Item are Already in Wishlist", Toast.LENGTH_SHORT).show();




}else {


    if (Constants.isUserLogIn) {


        final ProgressDialog pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
        final String tag_string_req = "string_req";
        String url = "https://www.groceryshopper.info/apps_data/add_wishlist_android_api.php";

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
                        Toast.makeText(mContext, jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
                        wistitem.add(album.getItemId());
                        ((Items) mContext).wishtext.setVisibility(View.VISIBLE);
                        ((Items) mContext).wishtext.setText(String.valueOf(wistitem.size()));
                        holder.wishimages.setImageResource(R.drawable.like);
                    } else {
                        Toast.makeText(mContext, jsonObject.getString("msg"), Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException g) {

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG@123", error.toString());
                Toast.makeText(mContext, "Internet Connection Not Working Properly", Toast.LENGTH_SHORT).show();
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                pDialog.hide();
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                Pref pref= new Pref(mContext);
                params.put("user_id",pref.getUserId());
                params.put("items", getBaseString(Integer.parseInt(album.getItemId())));
                Log.d("TAG@123", params.toString());
                String d = params.toString();
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    } else {
         Constants.logincontrol=0;
        ((Items) mContext).popuplpogpoutwish(album.getItemName().toString() + " was added to your WishList");
    }

}






                /*if (wistitem.contains(album.getItemId())) {
                    holder.wishimages.setImageResource(R.drawable.heartblack);
                    wistitem.remove(album.getItemId());

                } else {
                    String off=album.getoffer_type();
                    wistitem.add(album.getItemId());
                    holder.wishimages.setImageResource(R.drawable.like);
                    Wishlistdatabase dbHelper = new Wishlistdatabase(mContext);
                    WishlistOrderItemModel item = new WishlistOrderItemModel();
                    item.setItemId(album.getItemId());
                    item.setItemQuantity("1");
                    item.setItemName(album.getItemName());
                    item.setItemPrice(album.getItemPrice());
                    item.setoffer_type(album.getoffer_type());
                    item.setoffer_typetwo(album.getOfferId());
                    item.setoffer_typethree(album.getOfferDeatils());
                    item.setItemParentCategory(album.getisTimeRistrictedItem());
                    item.setoffer_complete("false");
                    item.setSubItems("");
                    item.setItemBase("");
                    item.setItemBasePrice("");
                    item.setItemBasePizzaIndex("");
                    item.setBase("");
                    item.setSize("");
                    item.setID(album.getbaseket_limit_msg());
                    item.setFree_toppings(album.getbasket_limit());
                    item.setSpecial_instruction(album.getitem_pic());
                    item.setSpecialTips("0.0");

                    dbHelper.addItemToOrder(item);

                    ((Items)mContext).popuplpogpoutwish(album.getItemName().toString() + " was added to your WishList");
                     Constants cons = new Constants();
                    ((Items)mContext).wishtext.setVisibility(View.VISIBLE);
                    String q=cons.getQuantitywish(mContext);
                    ((Items) mContext).wishtext.setText(cons.getQuantitywish(mContext));

                }
*/

            }


        });




    }


    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {

            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public interface ProductItemActionListener{
        void onItemTap(ImageView imageView);
    }


    public String getBaseString(int id) {
        String baseString = new String();
        if (wistitem.size() > 0) {
            for (int i = 0; i < wistitem.size(); i++) {
                if (baseString.equals("")) {
                    baseString =String.valueOf(id)+ "," + wistitem.get(i);
                } else {
                    baseString = baseString + "," + wistitem.get(i);
                }
            }


        } else {

            baseString=String.valueOf(id);
        }
        return baseString;
    }
}