package com.app_web.technology.groceryshopper.Addapter;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.app_web.technology.groceryshopper.Model.GetDataAdapterFirst;
import com.app_web.technology.groceryshopper.R;
import com.app_web.technology.groceryshopper.util.AppController;
import com.app_web.technology.groceryshopper.util.Constants;
import com.app_web.technology.groceryshopper.util.VolleyMethods;
import java.util.List;
public class RecyclerViewAdapterFirst extends RecyclerView.Adapter<RecyclerViewAdapterFirst.ViewHolder> {

    Context context;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    List<GetDataAdapterFirst> getDataAdapter;
    public RecyclerViewAdapterFirst(List<GetDataAdapterFirst> getDataAdapter, Context context){
        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items_first, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( final ViewHolder holder, int position) {
        final GetDataAdapterFirst getDataAdapter1 =  getDataAdapter.get(position);
        imageLoader = AppController.getInstance().getImageLoader();
        holder.thumbnail.setImageUrl("http://grocerydrop.co.uk/grocery_shopper/category_images/"+getDataAdapter1.getsub_cat_image(), imageLoader);
        holder.NameTextView.setText(getDataAdapter1.getsub_cat_name());
       // Typeface face1= Typeface.createFromAsset(context.getAssets(), "fonts/aaa.ttf");
       // holder.NameTextView.setTypeface(face1);
       // holder.discriptionfirst.setTypeface(face1);
        if(getDataAdapter1.getfull_desp().equals("")){
            holder.discriptionfirst.setVisibility(View.GONE);
        }else {
            holder.discriptionfirst.setText(getDataAdapter1.getfull_desp());
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
                scale.setDuration(500);
                scale.setInterpolator(new OvershootInterpolator());
                holder.thumbnail.startAnimation(scale);
                Constants.itemhaddername=new String("");
                Constants.itemhaddername=getDataAdapter1.getsub_cat_name();
                VolleyMethods vm = new VolleyMethods(context);
                vm.sub_cat_id(getDataAdapter1.getcat_id(),getDataAdapter1.getsub_cat_id(),Constants.country_id);
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