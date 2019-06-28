package com.app_web.technology.groceryshopper.Addapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.app_web.technology.groceryshopper.Activitys.ItemsActivity;
import com.app_web.technology.groceryshopper.Database.DataBaseHelper;
import com.app_web.technology.groceryshopper.Database.Wishlistdatabase;
import com.app_web.technology.groceryshopper.GetterSetter.Subitem;
import com.app_web.technology.groceryshopper.Model.OrderItemModel;
import com.app_web.technology.groceryshopper.Model.WishlistOrderItemModel;
import com.app_web.technology.groceryshopper.R;
import com.app_web.technology.groceryshopper.util.AppController;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
public  class Itemsubadapter extends RecyclerView.Adapter<Itemsubadapter.MyViewHolder> {
    private Context mContext;
    private List<Subitem> albumList;
    public  static  ArrayList<String> wistitem=new ArrayList<>();
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    Bitmap bitmap;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, additem,price;
        public ImageView   wishimages,addtocart;
        NetworkImageView thumbnail;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.grid_text);
            price=(TextView)view.findViewById(R.id.item_price) ;
            thumbnail = (NetworkImageView) view
                    .findViewById(R.id.grid_image);
            additem=(TextView)view.findViewById(R.id.add);
            wishimages= (ImageView) view.findViewById(R.id.image_action_like);
            addtocart=(ImageView)view.findViewById(R.id.addtocart);
        }
    }
    public Itemsubadapter(Context mContext, List<Subitem> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_single, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
       final Subitem album = albumList.get(position);
        imageLoader = AppController.getInstance().getImageLoader();
        holder.title.setText(album.getName());
        holder.price.setText("£ "+album.getThumbnail());
        holder.thumbnail.setImageUrl(album.getNumOfSongs(),imageLoader);


        if (wistitem.contains(album.getName())) {
            holder.wishimages.setImageResource(R.drawable.like);
        } else {
            holder.wishimages.setImageResource(R.drawable.heartblack);
        }
        holder.addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.addtocart.setImageResource(R.drawable.wishicon);
                DataBaseHelper dbHelper = new DataBaseHelper(mContext);
                OrderItemModel item = new OrderItemModel();
                item.setItemId(album.getid());
                item.setItemQuantity("1");
                item.setItemName(album.getName());
                item.setItemPrice(album.getThumbnail());
                item.setItemParentCategory("");
                item.setSubItems("");
                item.setItemBase("");
                item.setItemBasePrice("");
                item.setItemBasePizzaIndex("");
                item.setBase("");
                item.setSize("");
                item.setID("");
                item.setFree_toppings("");
                item.setSpecial_instruction(album.getNumOfSongs());
                item.setSpecialTips("");
                dbHelper.addItemToOrder(item);

                ((ItemsActivity)mContext).popuplpogpout(album.getName().toString()+" was added to your Order");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        holder.addtocart.setImageResource(R.drawable.addicon);

                    }

                }, 500L);

            }
        });
        holder.wishimages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                wistitem.add(album.getName());
                holder.wishimages.setImageResource(R.drawable.like);
                Wishlistdatabase dbHelper = new Wishlistdatabase(mContext);
                WishlistOrderItemModel item = new WishlistOrderItemModel();
                item.setItemId("");
                item.setItemQuantity("1");
                item.setItemName(album.getName());
                item.setItemPrice(album.getThumbnail());
                item.setItemParentCategory("");
                item.setSubItems("");
                item.setItemBase("");
                item.setItemBasePrice("");
                item.setItemBasePizzaIndex("");
                item.setBase("");
                item.setSize("");
                item.setID("");
                item.setFree_toppings("");
                item.setSpecial_instruction(album.getNumOfSongs());
                item.setSpecialTips("");
                dbHelper.addItemToOrder(item);

                ((ItemsActivity)mContext).popuplpogpoutwish(album.getName().toString()+" was added to your WishList");

            }


















               /* Wishlistdatabase dbHelper = new Wishlistdatabase(mContext);
                WishlistOrderItemModel item = new WishlistOrderItemModel();
                item.setItemQuantity("1");
                String t=album.getName();
                item.setItemName(album.getName());
                item.setItemPrice("");
                dbHelper.addItemToOrder(item);*/




        });
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }



}


