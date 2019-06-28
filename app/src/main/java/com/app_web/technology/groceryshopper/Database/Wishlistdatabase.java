package com.app_web.technology.groceryshopper.Database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import com.app_web.technology.groceryshopper.Model.WishlistOrderItemModel;

import java.util.ArrayList;

/**
 * Created by Rachit on 4/10/2017.
 */
    public class Wishlistdatabase extends SQLiteOpenHelper {
    private static final int DATABASEVERSION = 1;
    private static final String DATABASENAME = "sirkar_db_table";
    private static final String ORDERTABLE = "order_tabl";
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
    private static final String offer_type="offer_type";
    private static final String offer_typetwo="offer_typetwo";
    private static final String offer_typethree="offer_typethree";
    private static final String offer_complete="offer_complete";
    private static final String ID="id";
    private static final String Base="base";
    private static final String Size="size";
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


    // + ITEM_BASE_INDEX + " TEXT,"
    //  + SPECIAL_TIPS + " TEXT" + ")";
    //
    // Default constructor of DataBaseHelper
    public Wishlistdatabase(Context context) {
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
    public void addItemToOrder(WishlistOrderItemModel order) {
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


    public ArrayList<WishlistOrderItemModel> getOrder() {
        ArrayList<WishlistOrderItemModel> itemList = new ArrayList<WishlistOrderItemModel>();

        String selectQuery = "SELECT  * FROM " + ORDERTABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                WishlistOrderItemModel item = new WishlistOrderItemModel();
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
            }
            while (cursor.moveToNext());
        }

        return itemList;
    }


    public Bundle getItemQuantity()
    {
        Bundle bundle = new Bundle();
        int totalItems = 0;
        float totalPrice = 0.0f;
        float freetotalPrice = 0.0f;
        // Select All Query
        String selectQuery = "SELECT  * FROM " + ORDERTABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String toppingPrice = new String();


        if (cursor.moveToFirst()) {
            do {

                freetotalPrice+=Float.parseFloat(cursor.getString(10)) ;
                totalItems += Integer.parseInt(cursor.getString(4));
                totalPrice += (Float.parseFloat(cursor.getString(3)) * (Integer.parseInt(cursor.getString(4))) + getBaseItemsPrice(Integer.parseInt(cursor.getString(4)),cursor.getString(8)));
            } while (cursor.moveToNext());
        }

        bundle.putInt("TotalItem", totalItems);
        bundle.putString("TotalPrice", roundOffTo2DecPlaces(totalPrice-freetotalPrice));


        return bundle;
    }
    //Method to delete All records from Order Table
    public void deleteOrder() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + ORDERTABLE);
    }

    //Method to delete Single Order
    public void deleteSingleItem(WishlistOrderItemModel orderItem) {
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


        return f;
    }
}




   /* public static final String CREATE_ORDERTABLE_TABLE = "CREATE TABLE " + ORDERTABLE + "("
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

            + offer_complete + " TEXT,"
            + ID + " TEXT" + ")";
    public Wishlistdatabase(Context context) {
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
    public void addItemToOrder(WishlistOrderItemModel order) {
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
        db.close();
    }

    // Getting All OrderItems
    public ArrayList<WishlistOrderItemModel> getOrder() {
        ArrayList<WishlistOrderItemModel> itemList = new ArrayList<WishlistOrderItemModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + ORDERTABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
       int i=cursor.getCount();

        if (cursor.moveToFirst()) {
            do {
                WishlistOrderItemModel item = new WishlistOrderItemModel();
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

    // Getting single contact_icon
    public WishlistOrderItemModel getSingleItem(String itemID, String itemParentCategory, String subItem, String baseItem) {

        String selectQuery = "SELECT  * FROM " + ORDERTABLE + " WHERE " + ITEM_ID + "='" + itemID + "'" + " AND " + PARENTCATEGORY + "='" + itemParentCategory + "'" + " AND " + SUB_ITEMS + "='" + subItem + "'" + " AND " + ITEM_BASE + "='" + baseItem + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int i=cursor.getCount();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            WishlistOrderItemModel item = new WishlistOrderItemModel();

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





    public WishlistOrderItemModel getSingleItemupudate(String itemID) {

        String selectQuery = "SELECT  * FROM " + ORDERTABLE + " WHERE " + ITEM_ID + "='" + itemID + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            WishlistOrderItemModel item = new WishlistOrderItemModel();

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

    //Method to update data base Quantity
    public void upDateOrder(WishlistOrderItemModel order) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QUANTITY, order.getItemQuantity());
        // updating row
        db.update(ORDERTABLE, values, KEY + " = ?",
                new String[]{String.valueOf(order.getKey())});
    }

    //Methos to get Total count of product
    public Bundle getItemQuantity() {
        Bundle bundle = new Bundle();
        int totalItems = 0;
        float totalPrice = 0.0f;
        // Select All Query
        String selectQuery = "SELECT  * FROM " + ORDERTABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String toppingPrice = new String();


        if (cursor.moveToFirst()) {
            do {
                totalItems += Integer.parseInt(cursor.getString(4));
                totalPrice += (Float.parseFloat(cursor.getString(3)) * (Integer.parseInt(cursor.getString(4))) + getBaseItemsPrice(Integer.parseInt(cursor.getString(4)),cursor.getString(8)));
            } while (cursor.moveToNext());
        }
        // return contact_icon list
        bundle.putInt("TotalItem", totalItems);
        bundle.putString("TotalPrice", roundOffTo2DecPlaces(totalPrice));
        return bundle;
    }

    //Method to delete All records from Order Table
    public void deleteOrder() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + ORDERTABLE);
    }

    //Method to delete Single Order
    public void deleteSingleItem(WishlistOrderItemModel orderItem) {
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
*/