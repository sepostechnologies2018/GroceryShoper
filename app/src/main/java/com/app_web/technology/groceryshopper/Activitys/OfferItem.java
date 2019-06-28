package com.app_web.technology.groceryshopper.Activitys;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.app_web.technology.groceryshopper.Addapter.offeritemadapter;
import com.app_web.technology.groceryshopper.GetterSetter.offermodel;
import com.app_web.technology.groceryshopper.R;
import com.app_web.technology.groceryshopper.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OfferItem extends AppCompatActivity {
    private List<offermodel> albumList;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    private RecyclerView recyclerView;
    TextView datile,back;
    RecyclerView.Adapter recyclerViewadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_item);

        getSupportActionBar().hide();
        datile=(TextView)findViewById(R.id.datile);
        back=(TextView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        datile.setText(Constants.discription);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        albumList=new ArrayList<>();
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        JSON_DATA_WEB_CALL();

    }
    protected void onResume()
    {
        super.onResume();
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    protected void onPause()
    {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }

    public void JSON_DATA_WEB_CALL(){
        try{
            JSONArray jsonArray=new JSONArray(Constants.offeritemdata);
            JSON_PARSE_DATA_AFTER_WEBCALL(jsonArray);}
        catch (JSONException e){

        }






    }


    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){
        for(int i = 0; i<array.length(); i++) {
            offermodel GetDataAdapter2 = new offermodel();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                GetDataAdapter2.setage_restriction(json.getString("age_restriction"));
                GetDataAdapter2.setbasket_limit(json.getString("basket_limit"));
                GetDataAdapter2.setCatId(json.getString("cat_id"));
                //   GetDataAdapter2.setItemFullDescription(json.getString("desc"));
                GetDataAdapter2.setoffer_type(json.getString("offer_type"));
                GetDataAdapter2.setItemId(json.getString("item_id"));
                GetDataAdapter2.setItemName(json.getString("item_name"));
                GetDataAdapter2.setitem_pic("https://www.groceryshopper.info/apps_data/item_images/"+json.getString("item_pic"));
                GetDataAdapter2.setItemPrice(json.getString("price"));
                GetDataAdapter2.setrestrict_sales(json.getString("restrict_sales"));
                GetDataAdapter2.setsize(json.getString("size"));
                GetDataAdapter2.setstatus(json.getString("status"));
                GetDataAdapter2.setSubCatId(json.getString("sub_cat_id"));
                GetDataAdapter2.setage_restrict_msg(json.getString("age_restrict_msg"));
                GetDataAdapter2.setbaseket_limit_msg(json.getString("baseket_limit_msg"));
                GetDataAdapter2.setsmall_desc(json.getString("small_desc"));
                GetDataAdapter2.setrestrict_sales_msg(json.getString("restrict_sales_msg"));
                GetDataAdapter2.setbaseket_limit_msg(json.getString("baseket_limit_msg"));
                GetDataAdapter2.setout_of_stock(json.getString("out_of_stock"));
                GetDataAdapter2.setOfferId(json.getString("OfferId"));
                GetDataAdapter2.setOfferDeatils(json.getJSONObject("OfferDeatils").toString());





            } catch (JSONException e) {
                e.printStackTrace();
            }
            albumList.add(GetDataAdapter2);
        }
        recyclerViewadapter = new offeritemadapter(albumList, this);
        recyclerView.setAdapter(recyclerViewadapter);
    }

}
