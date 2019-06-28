package com.app_web.technology.groceryshopper.Addapter;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.app_web.technology.groceryshopper.GetterSetter.Album;
import com.app_web.technology.groceryshopper.R;
import com.app_web.technology.groceryshopper.util.AppController;

import java.util.ArrayList;
import java.util.List;
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapterter";

    private String[] mDataSet;
    private int[] mDataSetTypes;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    public static final int NEWS = 1;
    private Activity activity;
    private AlbumsAdapter adapter;
    private List<Album> albumList;
    int j;
    public  static  int i;

    private LayoutInflater inflater;
    private List<Album> feedItems;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

    public class NewsViewHolder extends ViewHolder {


        RecyclerView recyclerView;
        public NewsViewHolder(View v) {
            super(v);
            albumList = new ArrayList<>();
            prepareAlbums();
            //adapter = new AlbumsAdapter(activity, albumList);
            this. recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view);
        }
    }


    public RecyclerAdapter(Activity activity,String[]dataSet,List<Album> feedItems) {
        mDataSet = dataSet;

        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;


            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.category_template_two, viewGroup, false);

            return new NewsViewHolder(v);



    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {


        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();



/*
        if (viewHolder.getItemViewType() == WEATHER) {


            final   WeatherViewHolder holder = (WeatherViewHolder) viewHolder;
            HashMap<String,String> url_maps = new HashMap<String, String>();

            HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
           // file_maps.put("Hannibal",R.drawable.hannibal);
            file_maps.put("Big Bang Theory",R.drawable.bigbang);
            file_maps.put("House of Cards",R.drawable.house);


            for(String name : file_maps.keySet()){
                TextSliderView textSliderView = new TextSliderView(activity);

                textSliderView
                        .description(name)
                        .image(file_maps.get(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                            @Override
                            public void onSliderClick(BaseSliderView baseSliderView) {

                            }
                        });


                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra",name);

                holder.imageSlider.addSlider(textSliderView);
            }
            holder.imageSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
            holder.imageSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            holder.imageSlider.setCustomAnimation(new DescriptionAnimation());
            holder.imageSlider.setDuration(1500);
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
            final Handler h = new Handler();
            int delay = 3000; //milliseconds

            h.postDelayed(new Runnable(){
                public void run(){
                    h.postDelayed(this,3000);
                }
            }, delay);
        }
*/
        if(viewHolder.getItemViewType() == NEWS){
            NewsViewHolder holder = (NewsViewHolder) viewHolder;
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 1);
            holder. recyclerView.setLayoutManager(mLayoutManager);
            holder.recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
            holder.recyclerView.setAdapter(adapter);
            

        }

    }

    @Override
    public int getItemCount() {
        return  feedItems.size();
    }


    private void prepareAlbums() {

/*

        try {
            JSONArray jsonArray = new JSONArray(Constants.responce);


            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jobj = jsonArray.getJSONObject(i);

                    String cateName = jobj.getString("category").toString();
                    String itemimages = "http://www.satyamtechnologies.co.uk/egrocery/apps_data/item_images/"+jobj.getString("cat_image").toString();
                    String CatItems = jobj.getJSONArray("Items").toString();
                    Album a = new Album(cateName, itemimages, CatItems);
                    albumList.add(a);


                } catch (JSONException e) {
                    String t=e.getMessage().toString();
                    e.printStackTrace();
                }
            }

        }catch (JSONException r){

        }
*/




    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
    private int dpToPx(int dp) {
        Resources r = activity.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}