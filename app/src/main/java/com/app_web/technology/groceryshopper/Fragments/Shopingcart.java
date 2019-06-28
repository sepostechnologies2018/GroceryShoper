package com.app_web.technology.groceryshopper.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app_web.technology.groceryshopper.Activitys.Home;
import com.app_web.technology.groceryshopper.Addapter.CheckOutAdapter;
import com.app_web.technology.groceryshopper.Database.DataBaseHelper;
import com.app_web.technology.groceryshopper.Model.OrderItemModel;
import com.app_web.technology.groceryshopper.R;

import java.util.ArrayList;


public class Shopingcart extends Fragment implements View.OnClickListener {

    LinearLayout wishlist,shop_cart,search,contact_us,more,notification,home,back;
    public static ArrayList<OrderItemModel> orderItemList;
    public static DataBaseHelper dbHelper;
    public static CheckOutAdapter checkOutAdapter;
    public static RecyclerView orderCatRecycler;
    private final long millisecondsToShowSplash = 2000L;
    ProgressDialog pDialog;
    //ArrayList<CardViewModel> listitems = new ArrayList<CardViewModel>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupListItems();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (orderItemList.size() > 0 ) {


            View  v=inflater.inflate(R.layout.fragment_shopingcart, container, false);
            shop_cart=(LinearLayout)v.findViewById(R.id.shop_cart);
            wishlist=(LinearLayout)v.findViewById(R.id.wishlist);
            search=(LinearLayout)v.findViewById(R.id.search);
            //contact_us=(LinearLayout)v.findViewById(R.id.contact_us);
            more=(LinearLayout)v.findViewById(R.id.more);
            notification=(LinearLayout)v.findViewById(R.id.notification);
            home=(LinearLayout)v.findViewById(R.id.home);
            back=(LinearLayout)v.findViewById(R.id.menusub);
            wishlist.setOnClickListener(this);
            shop_cart.setOnClickListener(this);
            search.setOnClickListener(this);
          //  contact_us.setOnClickListener(this);
            more.setOnClickListener(this);
            notification.setOnClickListener(this);
            home.setOnClickListener(this);
            back.setOnClickListener(this);
            RecyclerView recList = (RecyclerView)v.findViewById(R.id.check_out_recycler);
            recList.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            if (orderItemList.size() > 0 & recList != null) {
                recList.setAdapter(new MyAdapter(orderItemList));
            }
            recList.setLayoutManager(llm);


            return v;

        }
        else {

            View  v=inflater.inflate(R.layout.empty_cart, container, false);

            return v;
        }


    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shop_cart: {
                Shopingcart frag = new Shopingcart();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.shoping,frag,"Shopingcart");
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.addToBackStack(null);
                transaction.commit();

            }
            break;

            case R.id.wishlist: {
                Wishlist frag = new Wishlist();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.shoping,frag,"Wishlist");
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.addToBackStack(null);
                transaction.commit();
            }
            break;

            case R.id.contact_us: {
                Contactus frag = new Contactus();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.shoping,frag,"Contactus");
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.addToBackStack(null);
                transaction.commit();
            }
            break;

            case R.id.more: {
                More frag = new More();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.shoping,frag,"More");
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.addToBackStack(null);
                transaction.commit();

            }
            break;
            case R.id.home: {
                Intent intent = new Intent(getActivity(), Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().startActivity(intent);
            }
            break;
            case R.id.menusub: {
                getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
            }
            break;

        }
    }
    public class MyAdapter extends RecyclerView.Adapter<ViewHolder> {
        private ArrayList<OrderItemModel> list;

        public MyAdapter(ArrayList<OrderItemModel> myDataset) {
            list = myDataset;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_items, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder,final int position) {
          final   OrderItemModel orderItemModel=orderItemList.get(position);

              String t=orderItemModel.getItemName();

              holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    DataBaseHelper dbHendler = new DataBaseHelper(getActivity());
                    dbHendler.deleteSingleItem(orderItemList.get(position));
                    pDialog = new ProgressDialog(getActivity());
                    pDialog.setMessage("Loading...");
                    pDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pDialog.dismiss();

                            Shopingcart frag = new Shopingcart();
                            FragmentManager manager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.add(R.id.shoping,frag,"Shopingcart");
                            transaction.commit();

                        }
                    }, millisecondsToShowSplash);

                  }
            });



        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView delete;
        TextView itemname;
        public ViewHolder(View v) {
            super(v);

            delete=(ImageView)v.findViewById(R.id.cancle);
            itemname=(TextView)v.findViewById(R.id.grid_text);



        }
    }
    public void setupListItems() {
        dbHelper = new DataBaseHelper(getActivity());
        orderItemList = new ArrayList<OrderItemModel>();
        orderItemList = dbHelper.getOrder();


    }



}
