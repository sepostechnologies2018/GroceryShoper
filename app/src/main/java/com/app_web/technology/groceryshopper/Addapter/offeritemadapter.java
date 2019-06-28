package com.app_web.technology.groceryshopper.Addapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.app_web.technology.groceryshopper.Database.DataBaseHelper;
import com.app_web.technology.groceryshopper.GetterSetter.offermodel;
import com.app_web.technology.groceryshopper.Model.OrderItemModel;
import com.app_web.technology.groceryshopper.R;
import com.app_web.technology.groceryshopper.util.AppController;
import com.app_web.technology.groceryshopper.util.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class offeritemadapter extends RecyclerView.Adapter<offeritemadapter.ViewHolder> {

    Context context;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    List<offermodel> getDataAdapter;
    public offeritemadapter(List<offermodel> getDataAdapter, Context context){
        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.offeritem, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( final ViewHolder holder, int position) {
        final offermodel getDataAdapter1 =  getDataAdapter.get(position);


        DataBaseHelper dbHelper = new DataBaseHelper(context);
        OrderItemModel isExist = dbHelper.getSingleItem(getDataAdapter1.getItemId(), "", "", "", getDataAdapter1.getItemPrice());
        if (isExist != null) {
            holder.thumbnail.setImageResource(R.drawable.wishicon);
        }
        else {
            holder.thumbnail.setImageResource(R.drawable.add);
        }
        imageLoader = AppController.getInstance().getImageLoader();
        Typeface face1= Typeface.createFromAsset(context.getAssets(), "fonts/aaa.ttf");
        holder.NameTextView.setText(getDataAdapter1.getItemName());
        holder.NameTextView.setTypeface(face1);
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!getDataAdapter1.getout_of_stock().equals("0")){

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(getDataAdapter1.getItemName()+" is Out Of Stock");
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

                    if(!getDataAdapter1.getrestrict_sales().equals("0")){
                        String time1 = new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());
                        String substring = time1.substring(Math.max(time1.length() - 2, 0));
                        if(substring.equals("PM")){
                            try{
                                SimpleDateFormat parser = new SimpleDateFormat("hh:mm");
                                Date ten = parser.parse(time1);
                                Date eighteen = parser.parse(getDataAdapter1.getrestrict_sales());
                                String d1=ten.toString();
                                String d2=eighteen.toString();
                                if (ten.after(eighteen)) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setMessage(getDataAdapter1.getrestrict_sales_msg());
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
                                    {
                                        DataBaseHelper dbHelper = new DataBaseHelper(context);
                                        OrderItemModel isExist = dbHelper.getSingleItem(getDataAdapter1.getItemId(), "", "", "", getDataAdapter1.getItemPrice());
                                        if (isExist != null) {}
                                        else {
                                            OrderItemModel item = new OrderItemModel();
                                            item.setItemId(getDataAdapter1.getItemId());
                                            item.setItemQuantity("1");
                                            item.setItemName(getDataAdapter1.getItemName());
                                            item.setItemPrice(getDataAdapter1.getItemPrice());
                                            item.setoffer_type(getDataAdapter1.getoffer_type());
                                            item.setoffer_typetwo(getDataAdapter1.getOfferId());
                                            item.setoffer_typethree(getDataAdapter1.getOfferDeatils());
                                            item.setoffer_complete("false");
                                            item.setItemParentCategory("");
                                            item.setSubItems("");
                                            item.setItemBase("");
                                            item.setItemBasePrice("");
                                            item.setItemBasePizzaIndex("");
                                            item.setBase("");
                                            item.setSize("");
                                            item.setID("");
                                            item.setFree_toppings("");
                                            item.setSpecial_instruction(getDataAdapter1.getitem_pic());
                                            item.setSpecialTips("0.0");
                                            dbHelper.addItemToOrder(item);


                                        }

                                    }
                                    holder.thumbnail.setImageResource(R.drawable.wishicon);
                                    Constants cons = new Constants();



                                }

                            }
                            catch (ParseException p){
                                String e=p.getMessage().toString();

                            }






                        }else {









                        }


                    }else {







                        if(!getDataAdapter1.getage_restriction().equals("")&&!getDataAdapter1.getage_restriction().equals("0")){
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                                builder.setMessage(getDataAdapter1.getage_restrict_msg());

                                String positiveText = "I am at least"+getDataAdapter1.getage_restriction();
                                builder.setPositiveButton(positiveText,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                {DataBaseHelper dbHelper = new DataBaseHelper(context);
                                                    OrderItemModel isExist = dbHelper.getSingleItem(getDataAdapter1.getItemId(), "", "", "", getDataAdapter1.getItemPrice());
                                                    if (isExist != null) {}




                                                    else {
                                                        OrderItemModel item = new OrderItemModel();
                                                        item.setItemId(getDataAdapter1.getItemId());
                                                        item.setItemQuantity("1");
                                                        item.setItemName(getDataAdapter1.getItemName());
                                                        item.setItemPrice(getDataAdapter1.getItemPrice());
                                                        item.setoffer_type(getDataAdapter1.getoffer_type());
                                                        item.setoffer_typetwo(getDataAdapter1.getOfferId());
                                                        item.setoffer_typethree(getDataAdapter1.getOfferDeatils());
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
                                                        item.setSpecial_instruction(getDataAdapter1.getitem_pic());
                                                        item.setSpecialTips("0.0");
                                                        dbHelper.addItemToOrder(item);

                                                    }
                                                    holder.thumbnail.setImageResource(R.drawable.wishicon);
                                                    Constants cons = new Constants();
                                                   // ((Items)context).txt_item_quantity.setVisibility(View.VISIBLE);
                                                   // ((Items)context).txt_item_quantity.setText(cons.getQuantity(context));
                                                   // ((Items) context).popuplpogpout(getDataAdapter1.getItemName().toString() + " was added to your WishList");

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


                            if(!getDataAdapter1.getbasket_limit().equals("0")){

                                {

                                    Constants cons = new Constants();
                                    String k = cons.singleitemquntity(context, getDataAdapter1.getItemId());
                                    if(k.equals("")){
                                        k="1";
                                    }
                                    String j = getDataAdapter1.getbasket_limit();
                                    if (Integer.parseInt(k) < Integer.parseInt(j)) {
                                        {


                                            DataBaseHelper dbHelper = new DataBaseHelper(context);
                                            OrderItemModel isExist = dbHelper.getSingleItem(getDataAdapter1.getItemId(), "", "", "", getDataAdapter1.getItemPrice());
                                            if (isExist != null) {}




                                            else {
                                                OrderItemModel item = new OrderItemModel();
                                                item.setItemId(getDataAdapter1.getItemId());
                                                item.setItemQuantity("1");
                                                item.setItemName(getDataAdapter1.getItemName());
                                                item.setItemPrice(getDataAdapter1.getItemPrice());
                                                item.setoffer_type(getDataAdapter1.getoffer_type());
                                                item.setoffer_typetwo(getDataAdapter1.getOfferId());
                                                item.setoffer_typethree(getDataAdapter1.getOfferDeatils());
                                                item.setItemParentCategory("");
                                                item.setoffer_complete("false");
                                                item.setSubItems("");
                                                item.setItemBase("");
                                                item.setItemBasePrice("");
                                                item.setItemBasePizzaIndex("");
                                                item.setBase("");
                                                item.setSize("");
                                                item.setID(getDataAdapter1.getbaseket_limit_msg());
                                                item.setFree_toppings(getDataAdapter1.getbasket_limit());
                                                item.setSpecial_instruction(getDataAdapter1.getitem_pic());
                                                item.setSpecialTips("0.0");
                                                dbHelper.addItemToOrder(item);



                                            }
                                            holder.thumbnail.setImageResource(R.drawable.wishicon);
                                           // ((Items) context).popuplpogpout(getDataAdapter1.getItemName().toString() + " was added to your WishList");
                                           // Constants constants = new Constants();
                                           // ((Items)context).txt_item_quantity.setVisibility(View.VISIBLE);
                                           // ((Items)context).txt_item_quantity.setText(constants.getQuantity(context));

                                        }

                                    } else {


                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setMessage(getDataAdapter1.getbaseket_limit_msg());
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


                                DataBaseHelper dbHelper = new DataBaseHelper(context);
                                OrderItemModel isExist = dbHelper.getSingleItem(getDataAdapter1.getItemId(), "", "", "", getDataAdapter1.getItemPrice());
                                if (isExist != null) {}




                                else {

                                    if(getDataAdapter1.getoffer_type().equals("3")){
                                        String price=getDataAdapter1.getItemPrice();
                                        OrderItemModel item = new OrderItemModel();
                                        item.setItemId(getDataAdapter1.getItemId());
                                        item.setItemQuantity("1");
                                        item.setItemName(getDataAdapter1.getItemName());
                                        item.setItemPrice(getDataAdapter1.getItemPrice());
                                        item.setoffer_type(getDataAdapter1.getoffer_type());
                                        item.setoffer_typetwo(getDataAdapter1.getOfferId());
                                        item.setoffer_typethree(getDataAdapter1.getOfferDeatils());
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
                                        item.setSpecial_instruction(getDataAdapter1.getitem_pic());
                                        item.setSpecialTips("0.0");
                                        dbHelper.addItemToOrder(item);
                                        dbHelper.getofferOrder(getDataAdapter1.getoffer_type(),getDataAdapter1.getOfferId());
                                        if(Constants.offerid.size()>0){
                                            for(int l=0;l<Constants.offerid.size();l++)
                                            {

                                                DataBaseHelper db = new DataBaseHelper(context);
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
                                        item.setItemId(getDataAdapter1.getItemId());
                                        item.setItemQuantity("1");
                                        item.setItemName(getDataAdapter1.getItemName());
                                        item.setItemPrice(getDataAdapter1.getItemPrice());
                                        item.setoffer_type(getDataAdapter1.getoffer_type());
                                        item.setoffer_typetwo(getDataAdapter1.getOfferId());
                                        item.setoffer_typethree(getDataAdapter1.getOfferDeatils());
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
                                        item.setSpecial_instruction(getDataAdapter1.getitem_pic());
                                        item.setSpecialTips("0.0");
                                        dbHelper.addItemToOrder(item);


                                    }

                                   // ((Items) context).popuplpogpout(getDataAdapter1.getItemName().toString() + " was added to your WishList");
                                    holder.thumbnail.setImageResource(R.drawable.wishicon);
                                    Constants cons = new Constants();
                                   // ((Items) context).txt_item_quantity.setVisibility(View.VISIBLE);
                                    //((Items) context).txt_item_quantity.setText(cons.getQuantity(context));
                                    }

                            }}}}
            }
        });






    }

    @Override
    public int getItemCount() {

        return getDataAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView IdTextView;
        public TextView NameTextView;
        public TextView discriptionfirst;
        public TextView SubjectTextView;
        public LinearLayout layout;
        ImageView thumbnail;
        public ViewHolder(View itemView) {
            super(itemView);
            NameTextView = (TextView) itemView.findViewById(R.id.itemname) ;
            thumbnail=(ImageView)itemView.findViewById(R.id.itemimagrs);
        }
    }
}