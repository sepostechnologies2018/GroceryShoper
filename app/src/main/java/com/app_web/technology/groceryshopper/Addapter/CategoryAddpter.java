package com.app_web.technology.groceryshopper.Addapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.app_web.technology.groceryshopper.Model.Services;
import com.app_web.technology.groceryshopper.R;
import com.app_web.technology.groceryshopper.util.AppController;
import com.app_web.technology.groceryshopper.util.Constants;
import com.app_web.technology.groceryshopper.util.VolleyMethods;

import java.util.List;

/**
 * Created by Tejveer on 7/5/2017.
 *
 */
public class CategoryAddpter extends RecyclerView.Adapter<CategoryAddpter.ViewHolder> {

    private static final String TAG= CategoryAddpter.class.getSimpleName();

    Context context;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    List<Services> getDataAdapter;
    public CategoryAddpter(List<Services> getDataAdapter, Context context){
        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( final ViewHolder holder, int position) {
        final Services getDataAdapter1 =  getDataAdapter.get(position);
        imageLoader = AppController.getInstance().getImageLoader();
        holder.thumbnail.setImageUrl("https://www.groceryshopper.info/apps_data/category_images/"+getDataAdapter1.getcat_image(), imageLoader);
        holder.NameTextView.setText(getDataAdapter1.getcat_name());
      //  Typeface face1= Typeface.createFromAsset(context.getAssets(), "fonts/aaa.ttf");
      //  holder.NameTextView.setTypeface(face1);
        if(!getDataAdapter1.getcat_image().equals("")){
            holder.thumbnail.setBackground(context.getResources().getDrawable(R.drawable.offerstranf));
        }
        else {
            holder.thumbnail.setBackground( context.getResources().getDrawable(R.drawable.defaultimg));
        }




       // holder.discriptionfirst.setTypeface(face1);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String po=getDataAdapter1.getis_promo();
                String promo=getDataAdapter1.getcountry_id();
                if(getDataAdapter1.gethas_subcat().equals("1")){
                    ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
                    scale.setDuration(500);
                    scale.setInterpolator(new OvershootInterpolator());
                    holder.thumbnail.startAnimation(scale);
                    Constants.itemname=new String("");
                    Constants.itemname=getDataAdapter1.getcat_name();


                    if(getDataAdapter1.getis_promo().equals("1")){

                        Log.d(TAG,"AAAAAAAAAAAAAAAAAAAAA");

                        VolleyMethods vm = new VolleyMethods(context);
                        vm.sub_cat_id(getDataAdapter1.getcat_id(),"",getDataAdapter1.getcountry_id());
                     /*   VolleyMethods vm = new VolleyMethods(context);
                        vm.getSpecialCases(getDataAdapter1.getcat_id(),getDataAdapter1.getcountry_id());*/
                    }else {
                        Log.d(TAG,"BBBBBBBBBBBBBBBBBBBBBB");
                        VolleyMethods vm = new VolleyMethods(context);
                        vm.getSpecialCases(getDataAdapter1.getcat_id(),"");
                    }

                }else {
                    ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
                    scale.setDuration(500);
                    scale.setInterpolator(new OvershootInterpolator());
                    holder.thumbnail.startAnimation(scale);
                    Constants.itemname=new String("");
                    Constants.itemname=getDataAdapter1.getcat_name();
                    if(getDataAdapter1.getis_promo().equals("1")){

                        VolleyMethods vm = new VolleyMethods(context);
                        vm.sub_cat_id(getDataAdapter1.getcat_id(),"",getDataAdapter1.getcountry_id());
                    }else {
                        VolleyMethods vm = new VolleyMethods(context);
                        vm.sub_cat_id(getDataAdapter1.getcat_id(),"","");
                    }

                }
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
        NetworkImageView thumbnail;
        public ViewHolder(View itemView) {
            super(itemView);
            NameTextView = (TextView) itemView.findViewById(R.id.textView4) ;
            thumbnail=(NetworkImageView)itemView.findViewById(R.id.mainimg);
            layout=(LinearLayout)itemView.findViewById(R.id.cotegory);
            discriptionfirst= (TextView) itemView.findViewById(R.id.discriptionfirst) ;
        }
    }
}