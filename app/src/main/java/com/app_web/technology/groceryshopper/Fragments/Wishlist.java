package com.app_web.technology.groceryshopper.Fragments;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app_web.technology.groceryshopper.Activitys.Home;
import com.app_web.technology.groceryshopper.Model.CardViewModel;
import com.app_web.technology.groceryshopper.R;

import java.util.ArrayList;

public class Wishlist extends Fragment implements View.OnClickListener {

    LinearLayout wishlist,shop_cart,search,contact_us,more,notification,home,back;
    ArrayList<CardViewModel> listitems = new ArrayList<CardViewModel>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupListItems();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  v=inflater.inflate(R.layout.fragment_wishlist, container, false);
        shop_cart=(LinearLayout)v.findViewById(R.id.shop_cart);
        wishlist=(LinearLayout)v.findViewById(R.id.wishlist);
        search=(LinearLayout)v.findViewById(R.id.search);
       // contact_us=(LinearLayout)v.findViewById(R.id.contact_us);
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
     /*   RecyclerView recList = (RecyclerView)v.findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        if (listitems.size() > 0 & recList != null) {
            recList.setAdapter(new MyAdapter(listitems));
        }
        recList.setLayoutManager(llm);*/















        return v;
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
                transaction.add(R.id.wishlistlayout,frag,"Shopingcart");
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.addToBackStack(null);
                transaction.commit();

            }
            break;

            case R.id.wishlist: {
                Wishlist frag = new Wishlist();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.wishlistlayout,frag,"Wishlist");
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.addToBackStack(null);
                transaction.commit();
            }
            break;

            case R.id.contact_us: {
                Contactus frag = new Contactus();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.wishlistlayout,frag,"Contactus");
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.addToBackStack(null);
                transaction.commit();
            }
            break;

            case R.id.more: {

                More frag = new More();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.wishlistlayout,frag,"More");
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
        private ArrayList<CardViewModel> list;

        public MyAdapter(ArrayList<CardViewModel> myDataset) {
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
        public void onBindViewHolder(final ViewHolder holder, int position) {

            holder.text_card_name.setText(list.get(position).getName());

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView text_card_name;
        public ImageView image_card_cover;
        public ImageView image_action_like;
        public ImageView image_action_flag;
        public ImageView image_action_share;
        public Toolbar maintoolbar;
        public ViewHolder(View v) {
            super(v);
           /* text_card_name = (TextView) v.findViewById(R.id.text_card_name);
            image_card_cover = (ImageView) v.findViewById(R.id.image_card_cover);
            image_action_like = (ImageView) v.findViewById(R.id.image_action_like);
            image_action_flag = (ImageView) v.findViewById(R.id.image_action_flag);
            image_action_share = (ImageView) v.findViewById(R.id.image_action_share);
            maintoolbar = (Toolbar) v.findViewById(R.id.card_toolbar);*/

        }
    }
    public void setupListItems() {
        listitems.clear();
        CardViewModel item1 = new CardViewModel();
        item1.setName("Dhawal Sodha Parmar");
        item1.setThumbnail(R.drawable.logo);
        item1.setNumOfSongs(0);

        listitems.add(item1);
        CardViewModel item2 = new CardViewModel();
        item1.setName("Dhawal Sodha Parmar");
        item1.setThumbnail(R.drawable.logo);
        item1.setNumOfSongs(0);

        listitems.add(item2);
        CardViewModel item4 = new CardViewModel();
        item1.setName("Dhawal Sodha Parmar");
        item1.setNumOfSongs(0);
        listitems.add(item4);
    }
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        final Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
    private int getThemePrimaryColor() {
        final TypedValue typedValue = new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        int[] attribute = new int[]{R.attr.colorPrimary};
        final TypedArray array = getActivity().obtainStyledAttributes(typedValue.resourceId, attribute);
        return array.getColor(0, Color.MAGENTA);
    }

}
