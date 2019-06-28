package com.app_web.technology.groceryshopper.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app_web.technology.groceryshopper.R;

public class Search extends Fragment implements View.OnClickListener {

    LinearLayout wishlist,shop_cart,search,contact_us,more,notification,home;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  v=inflater.inflate(R.layout.fragment_search, container, false);

        shop_cart=(LinearLayout)v.findViewById(R.id.shop_cart);
        wishlist=(LinearLayout)v.findViewById(R.id.wishlist);
        search=(LinearLayout)v.findViewById(R.id.search);
       // contact_us=(LinearLayout)v.findViewById(R.id.contact_us);
        more=(LinearLayout)v.findViewById(R.id.more);
        notification=(LinearLayout)v.findViewById(R.id.notification);
        home=(LinearLayout)v.findViewById(R.id.home);
        wishlist.setOnClickListener(this);
        shop_cart.setOnClickListener(this);
        search.setOnClickListener(this);
        //contact_us.setOnClickListener(this);
        more.setOnClickListener(this);
        notification.setOnClickListener(this);
        home.setOnClickListener(this);








        return v;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shop_cart: {

                Shopingcart frag = new Shopingcart();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.search,frag,"Test Fragment");
                transaction.commit();

            }
            break;

            case R.id.wishlist: {
                Wishlist frag = new Wishlist();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.search,frag,"Test Fragment");
                transaction.commit();
            }
            break;

            case R.id.contact_us: {
                Contactus frag = new Contactus();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.search,frag,"Test Fragment");
                transaction.commit();
            }
            break;

            case R.id.more: {

                More frag = new More();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.search,frag,"Test Fragment");
                transaction.commit();



            }
            break;


        }
    }


}
