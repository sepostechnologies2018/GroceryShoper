package com.app_web.technology.groceryshopper.Addapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.app_web.technology.groceryshopper.Model.GetDataAdapter;
import com.app_web.technology.groceryshopper.R;
import com.app_web.technology.groceryshopper.util.AppController;
import com.app_web.technology.groceryshopper.util.VolleyMethods;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    List<GetDataAdapter> getDataAdapter;

    public RecyclerViewAdapter(List<GetDataAdapter> getDataAdapter, Context context){

        super();

        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

       final GetDataAdapter getDataAdapter1 =  getDataAdapter.get(position);
        imageLoader = AppController.getInstance().getImageLoader();

        holder.thumbnail.setImageUrl("http://www.grocerydrop.co.uk/apps_data/category_images/"+getDataAdapter1.getcat_image(), imageLoader);
        Typeface face1= Typeface.createFromAsset(context.getAssets(), "fonts/aaa.ttf");
        holder.NameTextView.setTypeface(face1);
        holder.NameTextView.setText(getDataAdapter1.getcat_name());

         holder.layout.setOnClickListener(new View.OnClickListener() {
         @Override
        public void onClick(View view) {

             /*if(getDataAdapter1.gethas_subcat().equals("1")){
                 VolleyMethods vm = new VolleyMethods(context);
                 vm.getSpecialCases(getDataAdapter1.getcat_id(),"");
             }else {

                 VolleyMethods vm = new VolleyMethods(context);
                 vm.sub_cat_id(getDataAdapter1.getcat_id(),"");
             }*/




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
        public TextView PhoneNumberTextView;
        public TextView SubjectTextView;
        public LinearLayout layout;
        NetworkImageView thumbnail;

        public ViewHolder(View itemView) {

            super(itemView);

           // IdTextView = (TextView) itemView.findViewById(R.id.textView2) ;
            NameTextView = (TextView) itemView.findViewById(R.id.textView4) ;
            thumbnail=(NetworkImageView)itemView.findViewById(R.id.mainimg);
            layout=(LinearLayout)itemView.findViewById(R.id.cotegory);



        }
    }
}