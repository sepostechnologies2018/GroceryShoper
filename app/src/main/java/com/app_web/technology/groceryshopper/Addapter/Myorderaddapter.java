package com.app_web.technology.groceryshopper.Addapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.app_web.technology.groceryshopper.GetterSetter.myordergettersetter;
import com.app_web.technology.groceryshopper.R;
import com.app_web.technology.groceryshopper.util.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Myorderaddapter extends RecyclerView.Adapter<Myorderaddapter.ViewHolder> {

    Context context;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    List<myordergettersetter> getDataAdapter;
    public Myorderaddapter(List<myordergettersetter> getDataAdapter, Context context){
        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.myouderlayout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final myordergettersetter getDataAdapter1 =  getDataAdapter.get(position);
        imageLoader = AppController.getInstance().getImageLoader();
        holder.Name.setText("Name -"+getDataAdapter1.getfull_name());
        holder.email.setText("Email Id -"+getDataAdapter1.getemail());
        holder.phone.setText("Contact -"+getDataAdapter1.getphone());
        holder.date.setText("Date -"+getDataAdapter1.gettrans_on());
        holder.amount.setText("Amount - £ "+getDataAdapter1.getamount());
        String d=getDataAdapter1.getDelivery_Address();
        Typeface face1= Typeface.createFromAsset(context.getAssets(), "fonts/aaa.ttf");
        holder.Name.setTypeface(face1);
        holder.email.setTypeface(face1);
        holder.phone.setTypeface(face1);
        holder.date.setTypeface(face1);
        holder.amount.setTypeface(face1);



    holder.viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popup(getDataAdapter1.getItems(),getDataAdapter1.getDelivery_Address());
            }
        });
        holder.lierarlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popup(getDataAdapter1.getItems(),getDataAdapter1.getDelivery_Address());
            }
        });
    }


    public  void popup(String items,String Addres){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.service, null);

        LinearLayout llDynamicLayout,addresview;
        llDynamicLayout = (LinearLayout) subView.findViewById(R.id.adedview);
        addresview = (LinearLayout) subView.findViewById(R.id.cotegory);
        TextView full_name,house_no,phone,postcode,rev_city,street;
        full_name=(TextView)subView.findViewById(R.id.full_name);
        house_no=(TextView)subView.findViewById(R.id.house_no);
        phone=(TextView)subView.findViewById(R.id.phone);
        postcode=(TextView)subView.findViewById(R.id.postcode);
        rev_city=(TextView)subView.findViewById(R.id.rev_city);
        street=(TextView)subView.findViewById(R.id.street);

        Typeface face1= Typeface.createFromAsset(context.getAssets(), "fonts/aaa.ttf");
        full_name.setTypeface(face1);
        house_no.setTypeface(face1);
        phone.setTypeface(face1);
        postcode.setTypeface(face1);
        street.setTypeface(face1);
        rev_city.setTypeface(face1);



        try{
            JSONArray arr = new JSONArray(items);
            for (int i = 0; i < arr.length(); i++) {

                try {

                    JSONObject jobj = arr.getJSONObject(i);
                    TextView txt = new TextView(context);
                    txt.setText(" "+(i+1)+"-"+jobj.getString("item_name") +"\n Unit Price- £"+jobj.getString("unit_price")+"\n Quantity -"  +jobj.getString("qty") );
                    //txt.setTextColor(ContextCompat.getColor(context, R.color.black));
                    txt.setTextColor(Color.BLACK);
                    txt.setTextSize(14);
                    //txt.setTypeface(face1);
                    txt.setGravity(Gravity.LEFT);
                    llDynamicLayout.addView(txt);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }catch (JSONException e){

        }
try {


    if(!Addres.equals("{}")){
        addresview.setVisibility(View.VISIBLE);
        JSONObject jsonObject = new JSONObject(Addres);
        full_name.setText("Full Name -"+jsonObject.getString("full_name"));
        house_no.setText("House No -"+jsonObject.getString("house_no"));
        phone.setText("Phone -"+jsonObject.getString("phone"));
        postcode.setText("Postcode -"+jsonObject.getString("postcode"));
        rev_city.setText("City -"+jsonObject.getString("rev_city"));
        street.setText("Street -"+jsonObject.getString("street"));
    }else {
        addresview.setVisibility(View.GONE);
    }



}catch (JSONException e){

}


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Items");

        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });



        builder.show();
    }




    @Override
    public int getItemCount() {

        return getDataAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView viewall;
        public TextView phone;
        public TextView date;
        public TextView amount,email,Name;
        public LinearLayout layout,lierarlayout;

        public ViewHolder(View itemView) {
            super(itemView);
            Name = (TextView) itemView.findViewById(R.id.Name) ;
            email = (TextView) itemView.findViewById(R.id.email) ;
            phone = (TextView) itemView.findViewById(R.id.phone) ;
            date = (TextView) itemView.findViewById(R.id.date) ;
            amount = (TextView) itemView.findViewById(R.id.amount) ;
            viewall= (TextView) itemView.findViewById(R.id.viewall) ;
            layout=(LinearLayout)itemView.findViewById(R.id.cotegory);
            lierarlayout=(LinearLayout)itemView.findViewById(R.id.lierarlayout);
        }
    }
}