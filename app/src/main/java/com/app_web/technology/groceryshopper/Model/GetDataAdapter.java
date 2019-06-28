package com.app_web.technology.groceryshopper.Model;

/**
 * Created by JUNED on 6/16/2016.
 */
public class GetDataAdapter {


    String cat_id;
    String cat_desc;
    String cat_image;
    String cat_name;
    String cat_order;
    String has_subcat;
    String status;


    public String getcat_id() {

        return cat_id;
    }

    public void setcat_id(String cat_id) {

        this.cat_id = cat_id;
    }



    public String getcat_desc() {

        return cat_desc;
    }

    public void setcat_desc(String cat_desc) {

        this.cat_desc = cat_desc;
    }

    public String getcat_image() {

        return cat_image;
    }

    public void setcat_image(String cat_image) {

        this.cat_image = cat_image;
    }
    public String getcat_name() {

        return cat_name;
    }

    public void setcat_name(String cat_name) {

        this.cat_name = cat_name;
    }
    public String getcat_order() {

        return cat_order;
    }

    public void setcat_order(String cat_order) {

        this.cat_order = cat_order;
    }

    public String gethas_subcat() {

        return has_subcat;
    }

    public void sethas_subcat(String status) {

        this.has_subcat = status;
    }
    public String getstatus() {

        return status;
    }

    public void setstatus(String status) {

        this.status = status;
    }
}