package com.app_web.technology.groceryshopper.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app_web.technology.groceryshopper.Activitys.Home;
import com.app_web.technology.groceryshopper.R;


public class Contactus extends Fragment implements View.OnClickListener {

    LinearLayout wishlist,shop_cart,search,contact_us,more,notification,home,menu,back;
    DrawerLayout drawer;
    TextView txtWebsite,txt_item_quantity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  v=inflater.inflate(R.layout.login, container, false);
        drawer = (DrawerLayout)v.findViewById(R.id.drawer_layout);
        menu=(LinearLayout)v.findViewById(R.id.menu);
        shop_cart=(LinearLayout)v.findViewById(R.id.shop_cart);
        wishlist=(LinearLayout)v.findViewById(R.id.wishlist);
        search=(LinearLayout)v.findViewById(R.id.search);
      //  contact_us=(LinearLayout)v.findViewById(R.id.contact_us);
        more=(LinearLayout)v.findViewById(R.id.more);
        notification=(LinearLayout)v.findViewById(R.id.notification);
        home=(LinearLayout)v.findViewById(R.id.home);
        txtWebsite=(TextView)v.findViewById(R.id.wishquanity);
        txt_item_quantity=(TextView)v.findViewById(R.id.txt_item_quantity);
        txtWebsite.setVisibility(View.GONE);
        txt_item_quantity.setVisibility(View.GONE);

        wishlist.setOnClickListener(this);
        shop_cart.setOnClickListener(this);
        back=(LinearLayout)v.findViewById(R.id.menusub);
        search.setOnClickListener(this);
//        contact_us.setOnClickListener(this);
        more.setOnClickListener(this);
        notification.setOnClickListener(this);
        home.setOnClickListener(this);
        back.setOnClickListener(this);
        return v;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           /* case R.id.shop_cart: {

                Shopingcart frag = new Shopingcart();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.log_in,frag,"Shopingcart");
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.addToBackStack(null);
                transaction.commit();

            }
            break;

            case R.id.wishlist: {
                Wishlist frag = new Wishlist();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.log_in,frag,"Wishlist");
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.addToBackStack(null);
                transaction.commit();
            }
            break;

            case R.id.notification: {
                Notification frag = new Notification();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.log_in,frag,"Notification");
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.addToBackStack(null);
                transaction.commit();
            }
            break;

            case R.id.more: {

                More frag = new More();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.add(R.id.log_in,frag,"More");
                transaction.addToBackStack(null);
                transaction.commit();

            }
            break;*/
            case R.id.home: {
                Intent intent = new Intent(getActivity(), Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().startActivity(intent);
            }
            break;
          /*  case R.id.menu: {
                drawer.openDrawer(Gravity.LEFT);
            }
            break;*/
            case R.id.menusub: {
                getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
            }
            break;
        }
    }


}
