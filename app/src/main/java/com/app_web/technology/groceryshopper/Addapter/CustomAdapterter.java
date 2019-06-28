package com.app_web.technology.groceryshopper.Addapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.app_web.technology.groceryshopper.GetterSetter.CountreGetterSetter;
import com.app_web.technology.groceryshopper.R;
import com.app_web.technology.groceryshopper.util.AppController;
import com.app_web.technology.groceryshopper.util.Constants;
import com.app_web.technology.groceryshopper.util.VolleyMethods;
import com.daimajia.slider.library.Animations.BaseAnimationInterface;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CustomAdapterter extends RecyclerView.Adapter<CustomAdapterter.ViewHolder> {
    private static final String TAG = "CustomAdapterter";

    private String[] mDataSet;
    private int[] mDataSetTypes;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    public static final int WEATHER = -1;
    public static final int SCORE = 1;
    public static final int NEWS = 1;
    private Activity activity;
    int j;
    public  static  int i;
    private LayoutInflater inflater;
    private List<CountreGetterSetter> feedItems;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

    public class WeatherViewHolder extends ViewHolder {

        private SliderLayout imageSlider;

        public WeatherViewHolder(View v) {
            super(v);

            this.imageSlider = (SliderLayout)v.findViewById(R.id.slider);

        }
    }

    public class ScoreViewHolder extends ViewHolder {

        TextView name;
        TextView discription;
        TextView statusMsg;
        TextView url;
        NetworkImageView profilePic;
        LinearLayout lv;
        // FeedImageView feedImageView;




        public ScoreViewHolder(View v) {
            super(v);


            this.name =  (TextView)v.findViewById(R.id.name);

            this.discription=(TextView)v.findViewById(R.id.discription);

            this.profilePic = (NetworkImageView) v
                    .findViewById(R.id.profilePic);
            this.lv = (LinearLayout) v
                    .findViewById(R.id.main);
        }
    }

    public class NewsViewHolder extends ViewHolder {
        TextView headline;
        Button read_more;

        public NewsViewHolder(View v) {
            super(v);
            this.headline = (TextView) v.findViewById(R.id.headline);
            this.read_more = (Button) v.findViewById(R.id.read_more);
        }
    }


    public CustomAdapterter(Activity activity, String[]dataSet, int[] dataSetTypes, List<CountreGetterSetter> feedItems) {
        mDataSet = dataSet;
        mDataSetTypes = dataSetTypes;
        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;


        if (viewType == WEATHER) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.weather_card, viewGroup, false);

            return new WeatherViewHolder(v);
        }  else {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.score_card, viewGroup, false);
            return new ScoreViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();



        if (viewHolder.getItemViewType() == WEATHER) {

            final   WeatherViewHolder holder = (WeatherViewHolder) viewHolder;

try {
    JSONArray jsonArray = new JSONArray(Constants.banners);


    for (int i=0;i<jsonArray.length();i++) {
        JSONObject jsonObject=jsonArray.getJSONObject(i);
        String name = jsonObject.getString("banner_name");
        TextSliderView textSliderView = new TextSliderView(activity);
        textSliderView
                .description("")
                .image("https://www.groceryshopper.info/apps_data/banners/"+jsonObject.getString("banner_name"))

                .setScaleType(BaseSliderView.ScaleType.Fit)
                .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView baseSliderView) {

                    }
                });

        textSliderView.bundle(new Bundle());
        textSliderView.getBundle()
                .putString("extra", name);


        holder.imageSlider.addSlider(textSliderView);
    }
}catch (JSONException e){
    e.getMessage().toString();
}
            holder.imageSlider.setPresetTransformer(SliderLayout.Transformer.Default);
            //   holder.imageSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            holder.imageSlider.setCustomAnimation(new BaseAnimationInterface() {
                @Override
                public void onPrepareCurrentItemLeaveScreen(View current) {

                }

                @Override
                public void onPrepareNextItemShowInScreen(View next) {

                }

                @Override
                public void onCurrentItemDisappear(View view) {

                }

                @Override
                public void onNextItemAppear(View view) {

                }
            });
            holder.imageSlider.setDuration(1800);
            holder.imageSlider.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {



                }

                @Override
                public void onPageScrollStateChanged(int i) {




                }
            });



        }

        else {
            final ScoreViewHolder holder = (ScoreViewHolder) viewHolder;
            final CountreGetterSetter item = feedItems.get(position-1);
            final int t=position-1;
           // Typeface face1= Typeface.createFromAsset(activity.getAssets(), "fonts/aaa.ttf");
           // holder.name.setTypeface(face1);
            holder.name.setText(item.getcountry_name());
            holder.name.setVisibility(View.VISIBLE);
           // holder.discription.setTypeface(face1);
            holder.profilePic.setVisibility(View.VISIBLE);
            holder.profilePic.setImageUrl("https://www.groceryshopper.info/apps_data/country/"+item.getcountry_flag(), imageLoader);
            holder.lv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
                    scale.setDuration(500);
                    scale.setInterpolator(new OvershootInterpolator());
                    holder.profilePic.startAnimation(scale);
                    VolleyMethods vm = new VolleyMethods(activity);
                    vm.mainitem(item.getcountry_id(),item.getcountry_name());



                }
            });
        }
    }

    @Override
    public int getItemCount() {
        // return mDataSet.length;
        int h=feedItems.size();

        return  feedItems.size()+1;
    }

    @Override
    public int getItemViewType(int position) {

        int h=mDataSetTypes[position];
        return mDataSetTypes[position];
    }
}