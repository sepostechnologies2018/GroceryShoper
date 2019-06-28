package com.app_web.technology.groceryshopper.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import com.app_web.technology.groceryshopper.Model.OrderItemModel;
import com.app_web.technology.groceryshopper.util.Constants;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int DATABASEVERSION = 1;
    private static final String DATABASENAME = "sirkar_db";
    private static final String ORDERTABLE = "order_table";
    //Order Table Columb Name
    private static final String KEY = "key";
    private static final String ITEM_ID = "item_id";
    private static final String ITEM_NAME = "item_name";
    private static final String PRICE = "price";
    private static final String QUANTITY = "quality";
    private static final String PARENTCATEGORY = "parent_category";
    private static final String SUB_ITEMS = "sub_items";
    private static final String ITEM_BASE = "item_base";
    private static final String ITEM_BASE_PRICE = "items_base_price";
    private static final String SPECIAL_TIPS = "special_tips";
    private static final String ITEM_BASE_INDEX="item_base_index";
    private static final String Free_toppings="free_toppings";
    private static final String Special_instruction="special_instruction";
    private static final String ID="id";
    private static final String Base="base";
    private static final String Size="size";
    private static final String offer_type="offer_type";
    private static final String offer_typetwo="offer_typetwo";
    private static final String offer_typethree="offer_typethree";
    private static final String offer_complete="offer_complete";

    public static final String CREATE_ORDERTABLE_TABLE = "CREATE TABLE " + ORDERTABLE + "("
            + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ITEM_ID + " TEXT,"
            + ITEM_NAME + " TEXT,"
            + PRICE + " TEXT,"
            + QUANTITY + " TEXT,"
            + PARENTCATEGORY + " TEXT,"
            + SUB_ITEMS + " TEXT,"
            + ITEM_BASE + " TEXT,"
            + ITEM_BASE_PRICE + " TEXT,"
            + ITEM_BASE_INDEX + " TEXT,"
            + SPECIAL_TIPS + " TEXT,"
            + Base + " TEXT,"
            + Size + " TEXT,"
            + Free_toppings + " TEXT,"
            + Special_instruction + " TEXT,"
            + offer_type + " TEXT,"
            + offer_typetwo + " TEXT,"
            + offer_typethree + " TEXT,"
            + offer_complete + " TEXT,"
            + ID + " TEXT" + ")";


    // + ITEM_BASE_INDEX + "sin TEXT,"
    //  + SPECIAL_TIPS + " TEXT" + ")";
    //
    // Default constructor of DataBaseHelper
    public DataBaseHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ORDERTABLE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + ORDERTABLE);

        // Create tables again
        onCreate(db);
    }

    // Adding new Item To the Order
    public void addItemToOrder(OrderItemModel order) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ITEM_ID, order.getItemId());
        values.put(ITEM_NAME, order.getItemName());
        values.put(PRICE, order.getItemPrice());
        values.put(QUANTITY, order.getItemQuantity());
        values.put(PARENTCATEGORY, order.getItemParentCategory());
        values.put(SUB_ITEMS, order.getSubItems());
        values.put(ITEM_BASE, order.getItemBase());
        values.put(ITEM_BASE_PRICE, order.getItemBasePrice());
        values.put(ITEM_BASE_INDEX,order.getItemBasePizzaIndex());
        values.put(SPECIAL_TIPS, order.getSpecialTips());
        values.put(Base, order.getBase());
        values.put(Size, order.getSize());
        values.put(Free_toppings, order.getFree_toppings());
        values.put(Special_instruction, order.getSpecial_instruction());
        values.put(offer_type, order.getoffer_type());
        values.put(offer_typetwo, order.getoffer_typetwo());
        values.put(offer_typethree, order.getoffer_typethree());
        values.put(offer_complete, order.getoffer_complete());
        values.put(ID, order.getID());
        db.insert(ORDERTABLE, null, values);
        db.close(); // Closing database connection

    }


    public ArrayList<OrderItemModel> getOrder() {
        ArrayList<OrderItemModel> itemList = new ArrayList<OrderItemModel>();

        String selectQuery = "SELECT  * FROM " + ORDERTABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                OrderItemModel item = new OrderItemModel();

                item.setKey(cursor.getString(0));
                item.setItemId(cursor.getString(1));
                item.setItemName(cursor.getString(2));
                item.setItemPrice(cursor.getString(3));
                item.setItemQuantity(cursor.getString(4));
                item.setItemParentCategory(cursor.getString(5));
                item.setSubItems(cursor.getString(6));
                item.setItemBase(cursor.getString(7));
                item.setItemBasePrice(cursor.getString(8));
                item.setItemBasePizzaIndex(cursor.getString(9));
                item.setSpecialTips(cursor.getString(10));
                item.setBase(cursor.getString(11));
                item.setSize(cursor.getString(12));
                item.setFree_toppings(cursor.getString(13));
                item.setSpecial_instruction(cursor.getString(14));
                item.setoffer_type(cursor.getString(15));
                item.setoffer_typetwo(cursor.getString(16));
                item.setoffer_typethree(cursor.getString(17));
                item.setoffer_complete(cursor.getString(18));
                item.setID(cursor.getString(19));
                itemList.add(item);
            } while (cursor.moveToNext());
        }

        return itemList;
    }

    /*public void  getofferOrder(String itemoffertype,String itemofferid) {
        ArrayList<OrderItemModel> itemList = new ArrayList<OrderItemModel>();
        String selectQuery = "SELECT  * FROM " + ORDERTABLE + " WHERE " + offer_typetwo + "='" + itemofferid + "'";
        int count=0;
        int totalItems = 0;
        Constants.offerid=new ArrayList<>();
        Constants.offersdis=new ArrayList<>();
        Constants.offersprice=new ArrayList<>();
        ArrayList<String> itemid=new ArrayList<>();
        ArrayList<String> itemprice=new ArrayList<>();
        ArrayList<String> itemquanity=new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                try {
                    String ItemId=cursor.getString(1);
                    String ItemName=cursor.getString(2);
                    String ItemPrice=cursor.getString(3);
                    String ItemQuantity=cursor.getString(4);
                    String offer_type=cursor.getString(15);
                    String OfferId=cursor.getString(16);
                    String OfferDeatils=cursor.getString(17);
                    String offer_complete=cursor.getString(18);
                    JSONObject jsonObject = new JSONObject(OfferDeatils);
                    String noOfProductInPackage=jsonObject.getString("noOfProductInPackage");
                    String OfferPakagePrice=jsonObject.getString("OfferPakagePrice");





                }
                catch (Exception e){

                }












            } while (
                    cursor.moveToNext()
                    );
        }





    }*/

   public void  getofferOrder(String itemoffertype,String itemofferid) {
        ArrayList<OrderItemModel> itemList = new ArrayList<OrderItemModel>();
        String selectQuery = "SELECT  * FROM " + ORDERTABLE;
        int count=0;
        String applice = value(itemofferid);
        int totalItems = 0;
        boolean offerappli=false;
        Constants.offerid=new ArrayList<>();
        Constants.offersdis=new ArrayList<>();
        Constants.offersprice=new ArrayList<>();
        ArrayList<String> itemid=new ArrayList<>();
        ArrayList<String> itemprice=new ArrayList<>();
        ArrayList<String> itemquanity=new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                try {
                String ItemId=cursor.getString(1);
                String ItemName=cursor.getString(2);
                String ItemPrice=cursor.getString(3);
                String ItemQuantity=cursor.getString(4);
                String offer_type=cursor.getString(15);
                String OfferId=cursor.getString(16);
                String OfferDeatils=cursor.getString(17);
                String offer_complete=cursor.getString(18);
                if(itemoffertype.equals(offer_type)&&itemofferid.equals(OfferId)){
                        JSONObject jsonObject = new JSONObject(OfferDeatils);
                        String noOfProductInPackage=jsonObject.getString("noOfProductInPackage");
                        String OfferPakagePrice=jsonObject.getString("OfferPakagePrice");
                        itemid.add(ItemId);
                        itemprice.add(ItemPrice);
                        itemquanity.add(ItemQuantity);
                        totalItems +=Integer.parseInt(ItemQuantity);
                    Log.d("TAG@123", "totalItems=="+totalItems);
                    Log.d("TAG@123", "count=="+count);
                    Log.d("TAG@123", "###############################");



                    if(Integer.parseInt(applice)<Integer.parseInt(noOfProductInPackage)){

                        Constants.offerid.add(ItemId);
                        Constants.offersdis.add(String.valueOf("0.0"));
                        Constants.offersprice.add(ItemPrice);

                    }
                    else {






                    int remider = Integer.parseInt(applice)%Integer.parseInt(noOfProductInPackage);
                          if(remider==0){
                              float unitprice=Float.parseFloat(OfferPakagePrice)/Integer.parseInt(noOfProductInPackage);
                              Log.d("TAG@123", "unitprice=="+unitprice);
                              for(int i=0;i<itemid.size();i++){
                                  Log.d("TAG@123", "step=="+i);
                                  String iten_id=itemid.get(i);
                                  float p= Float.parseFloat(itemprice.get(i));
                                  int q=Integer.parseInt(itemquanity.get(i));
                                      float jh = q * unitprice;
                                      float total = q * p;
                                      float discount = total - jh;
                                      Constants.offerid.add(iten_id);
                                      Constants.offersdis.add(String.valueOf(discount));
                                      Constants.offersprice.add(itemprice.get(i));

                                      Log.d("TAG@123", "print=="+"#######");
                                      Log.d("TAG@123", "count=="+count);
                                      Log.d("TAG@123", "jh=="+jh);
                                      Log.d("TAG@123", "total=="+total);
                                      Log.d("TAG@123", "discount=="+discount);
                              }

                              int kt = totalItems % Integer.parseInt(noOfProductInPackage);
                              totalItems=0;


                          }
                    else{
                              float unitprice=Float.parseFloat(OfferPakagePrice)/Integer.parseInt(noOfProductInPackage);
                              int  totalitem=Integer.parseInt(value(itemofferid));
                              int numberofposibleset=(totalitem-(totalitem%Integer.parseInt(noOfProductInPackage)))/Integer.parseInt(noOfProductInPackage);
                              int itencount=numberofposibleset*Integer.parseInt(noOfProductInPackage);
                              float dis=0;
                              boolean flag=false;
                              int innercount=0;
                              int q = Integer.parseInt(ItemQuantity);

                              for(int j = 0; j<q; j++){
                                  if(count<itencount){
                                      count=count+1;
                                      dis=dis+unitprice;
                                      innercount=innercount+1;

                                  }
                                  else {

                                  if(flag==false&&innercount>0){
                                      flag=true;
                                      float p= Float.parseFloat(ItemPrice);
                                      float price=p*(q-j);
                                      dis=dis+price;
                                  }
                                      else {

                                      if(flag==false){
                                          float p= Float.parseFloat(ItemPrice);
                                          float price=p*q;
                                          dis=dis+price;
                                          flag=true;}

                                      }
                                  }
                              }

                              float p= Float.parseFloat(ItemPrice);
                              dis=(p*q)-dis;
                              Constants.offerid.add(ItemId);
                              Constants.offersdis.add(String.valueOf(dis));
                              Constants.offersprice.add(ItemPrice);
                          } }


                    }






                }
                catch (JSONException e){

                    String erroe=e.getMessage().toString();
                }












            } while (
                    cursor.moveToNext()
                    );
        }





    }


    public OrderItemModel getSingleItem(String itemID, String itemParentCategory, String subItem, String baseItem,String price) {
      //  String selectQuery = "SELECT  * FROM " + ORDERTABLE + " WHERE " + ITEM_ID + "='" + itemID + "'" + " AND " + PARENTCATEGORY + "='" + itemParentCategory + "'" + " AND " + PRICE + "='" + price + "'" + " AND " + ITEM_BASE + "='" + baseItem + "'";

       String selectQuery = "SELECT  * FROM " + ORDERTABLE + " WHERE " + ITEM_ID + "='" + itemID + "'" + " AND "  + PRICE + "='" + price + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int i=cursor.getCount();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            OrderItemModel item = new OrderItemModel();
            item.setKey(cursor.getString(0));
            item.setItemId(cursor.getString(1));
            item.setItemName(cursor.getString(2));
            item.setItemPrice(cursor.getString(3));
            item.setItemQuantity(cursor.getString(4));
            item.setItemParentCategory(cursor.getString(5));
            item.setSubItems(cursor.getString(6));
            item.setItemBase(cursor.getString(7));
            item.setItemBasePrice(cursor.getString(8));
            item.setItemBasePizzaIndex(cursor.getString(9));
            item.setSpecialTips(cursor.getString(10));
            item.setBase(cursor.getString(11));
            item.setSize(cursor.getString(12));
            item.setFree_toppings(cursor.getString(13));
            item.setSpecial_instruction(cursor.getString(14));
            item.setoffer_type(cursor.getString(15));
            item.setoffer_typetwo(cursor.getString(16));
            item.setoffer_typethree(cursor.getString(17));
            item.setoffer_complete(cursor.getString(18));
            item.setID(cursor.getString(19));

            return item;
        } else
            return null;
    }



    public OrderItemModel getSingleItemdelete(String itemID) {

        String selectQuery = "SELECT  * FROM " + ORDERTABLE + " WHERE " + ITEM_ID + "='" + itemID +  "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int i=cursor.getCount();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            OrderItemModel item = new OrderItemModel();
            item.setKey(cursor.getString(0));
            item.setItemId(cursor.getString(1));
            item.setItemName(cursor.getString(2));
            item.setItemPrice(cursor.getString(3));
            item.setItemQuantity(cursor.getString(4));
            item.setItemParentCategory(cursor.getString(5));
            item.setSubItems(cursor.getString(6));
            item.setItemBase(cursor.getString(7));
            item.setItemBasePrice(cursor.getString(8));
            item.setItemBasePizzaIndex(cursor.getString(9));
            item.setSpecialTips(cursor.getString(10));
            item.setBase(cursor.getString(11));
            item.setSize(cursor.getString(12));
            item.setFree_toppings(cursor.getString(13));
            item.setSpecial_instruction(cursor.getString(14));
            item.setoffer_type(cursor.getString(15));
            item.setoffer_typetwo(cursor.getString(16));
            item.setoffer_typethree(cursor.getString(17));
            item.setoffer_complete(cursor.getString(18));
            item.setID(cursor.getString(19));

            return item;
        } else
            return null;
    }








    // Getting single contact_icon
    public OrderItemModel getSingleofferItem(String itemID, String itemParentCategory, String subItem, String baseItem,String price) {

        String selectQuery = "SELECT  * FROM " + ORDERTABLE + " WHERE " + ITEM_ID + "='" + itemID + "'" + " AND " + PARENTCATEGORY + "='" + itemParentCategory + "'" + " AND " + ITEM_BASE + "='" + baseItem + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int i=cursor.getCount();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            OrderItemModel item = new OrderItemModel();
            item.setKey(cursor.getString(0));
            item.setItemId(cursor.getString(1));
            item.setItemName(cursor.getString(2));
            item.setItemPrice(cursor.getString(3));
            item.setItemQuantity(cursor.getString(4));
            item.setItemParentCategory(cursor.getString(5));
            item.setSubItems(cursor.getString(6));
            item.setItemBase(cursor.getString(7));
            item.setItemBasePrice(cursor.getString(8));
            item.setItemBasePizzaIndex(cursor.getString(9));
            item.setSpecialTips(cursor.getString(10));
            item.setBase(cursor.getString(11));
            item.setSize(cursor.getString(12));
            item.setFree_toppings(cursor.getString(13));
            item.setSpecial_instruction(cursor.getString(14));
            item.setoffer_type(cursor.getString(15));
            item.setoffer_typetwo(cursor.getString(16));
            item.setoffer_typethree(cursor.getString(17));
            item.setoffer_complete(cursor.getString(18));
            item.setID(cursor.getString(19));

            return item;
        } else
            return null;
    }




    public OrderItemModel getSingleItemupudate(String itemID) {

        String selectQuery = "SELECT  * FROM " + ORDERTABLE + " WHERE " + ITEM_ID + "='" + itemID + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            OrderItemModel item = new OrderItemModel();
            item.setKey(cursor.getString(0));
            item.setItemId(cursor.getString(1));
            item.setItemName(cursor.getString(2));
            item.setItemPrice(cursor.getString(3));
            item.setItemQuantity(cursor.getString(4));
            item.setItemParentCategory(cursor.getString(5));
            item.setSubItems(cursor.getString(6));
            item.setItemBase(cursor.getString(7));
            item.setItemBasePrice(cursor.getString(8));
            item.setItemBasePizzaIndex(cursor.getString(9));
            item.setSpecialTips(cursor.getString(10));
            item.setBase(cursor.getString(11));
            item.setSize(cursor.getString(12));
            item.setFree_toppings(cursor.getString(13));
            item.setSpecial_instruction(cursor.getString(14));
            item.setoffer_type(cursor.getString(15));
            item.setoffer_typetwo(cursor.getString(16));
            item.setoffer_typethree(cursor.getString(17));
            item.setoffer_complete(cursor.getString(18));
            item.setID(cursor.getString(19));

            return item;
        } else
            return null;
    }
    public Bundle singleitemquntity(String id) {
        Bundle bundle = new Bundle();
        int totalItems = 0;
        float totalPrice = 0.0f;
        String selectQuery = "SELECT  * FROM " + ORDERTABLE + " WHERE " + ITEM_ID + "='" + id + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                totalItems += Integer.parseInt(cursor.getString(4));
                totalPrice += (Float.parseFloat(cursor.getString(3)) * (Integer.parseInt(cursor.getString(4))) + getBaseItemsPrice(Integer.parseInt(cursor.getString(4)),cursor.getString(8)));
            } while (cursor.moveToNext());
        }

        bundle.putInt("TotalItem", totalItems);

        return bundle;
    }


    public Bundle singleitemtimerestrict() {
        Bundle bundle = new Bundle();
        int totalItems = 0;
        float totalPrice = 0.0f;
        String selectQuery = "SELECT  * FROM " + ORDERTABLE + " WHERE " + PARENTCATEGORY + "='" + "1" + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                totalItems += Integer.parseInt(cursor.getString(4));
                totalPrice += (Float.parseFloat(cursor.getString(3)) * (Integer.parseInt(cursor.getString(4))) + getBaseItemsPrice(Integer.parseInt(cursor.getString(4)),cursor.getString(8)));
            } while (cursor.moveToNext());
        }

        bundle.putInt("TotalItem", totalItems);

        return bundle;
    }






    public Bundle offertype(String offer_type_valu,String offer_id) {
        Bundle bundle = new Bundle();
        int totalItems = 0;
        float totalPrice = 0.0f;
        String selectQuery = "SELECT  * FROM " + ORDERTABLE + " WHERE " + offer_type + "='" + offer_type_valu + "'" + " AND " + offer_typetwo + "='" + offer_id + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int i=cursor.getCount();
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    totalItems += Integer.parseInt(cursor.getString(4));
                    totalPrice += (Float.parseFloat(cursor.getString(3)) * (Integer.parseInt(cursor.getString(4))) + getBaseItemsPrice(Integer.parseInt(cursor.getString(4)),cursor.getString(8)));
                } while (cursor.moveToNext());
            }
        }





        bundle.putInt("TotalItem", totalItems);

        return bundle;
    }


    public String value(String id) {
        Bundle bundle = new Bundle();
        int totalItems = 0;
        String number=new String("");
        float totalPrice = 0.0f;
        String selectQuery = "SELECT  * FROM " + ORDERTABLE + " WHERE " + offer_typetwo + "='" + id + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {

                totalItems += Integer.parseInt(cursor.getString(4));
                totalPrice += (Float.parseFloat(cursor.getString(3)) * (Integer.parseInt(cursor.getString(4))) + getBaseItemsPrice(Integer.parseInt(cursor.getString(4)),cursor.getString(8)));
            } while (cursor.moveToNext());
        }
        number=String.valueOf(totalItems);
        return number;
    }





    public void upDateOrder(OrderItemModel order) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QUANTITY, order.getItemQuantity());
        values.put(SPECIAL_TIPS, order.getSpecialTips());
        values.put(offer_complete, order.getoffer_complete());

        db.update(ORDERTABLE, values, KEY + " = ?",
                new String[]{String.valueOf(order.getKey())});
    }
    public void updateofferprice(OrderItemModel order) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SPECIAL_TIPS, order.getSpecialTips());
        values.put(offer_complete, order.getoffer_complete());

        db.update(ORDERTABLE, values, KEY + " = ?",
                new String[]{String.valueOf(order.getKey())});
    }


    public Bundle getItemQuantity()
    {
        Bundle bundle = new Bundle();
        int totalItems = 0;
        float totalPrice = 0.0f;
        float freetotalPrice = 0.0f;
        float discount=0.0f;
        String selectQuery = "SELECT  * FROM " + ORDERTABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String toppingPrice = new String();


        if (cursor.moveToFirst()) {
            do {

                freetotalPrice+=Float.parseFloat(cursor.getString(10)) ;
                totalItems += Integer.parseInt(cursor.getString(4));
                totalPrice += (Float.parseFloat(cursor.getString(3)) * (Integer.parseInt(cursor.getString(4))) + getBaseItemsPrice(Integer.parseInt(cursor.getString(4)),cursor.getString(8)));
            }
            while (cursor.moveToNext());
        }

        bundle.putInt("TotalItem", totalItems);
        bundle.putString("discount",roundOffTo2DecPlaces(freetotalPrice));
        bundle.putString("netTotalPrice", roundOffTo2DecPlaces(totalPrice+freetotalPrice));
        bundle.putString("TotalPrice", roundOffTo2DecPlaces(totalPrice-freetotalPrice));


        return bundle;
    }
    //Method to delete All records from Order Table
    public void deleteOrder() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + ORDERTABLE);
    }

    //Method to delete Single Order
    public void deleteSingleItem(OrderItemModel orderItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ORDERTABLE, KEY + " = ?", new String[]{String.valueOf(orderItem.getKey())});
        db.close();
    }

    //Round up to two places
    String roundOffTo2DecPlaces(float val) {
        return String.format("%.2f", val);
    }

    //Method to get Price of Base Items
    public float getBaseItemsPrice(int quantity,String basePrice) {
        float f = 0.0f;

        String price = basePrice;
        if (basePrice.equals("")) {
            //Do Nothing There is no BaseItemPrice
        } else {
            basePrice = basePrice + ",";
            basePrice = basePrice.replaceAll(",", "-");
            while (!basePrice.equals("")) {
                f = f + Float.parseFloat(basePrice.substring(0, basePrice.indexOf("-")))*quantity;
                basePrice = basePrice.substring(basePrice.indexOf("-") + 1, basePrice.length());
            }
        }
        return f;
    }
}
