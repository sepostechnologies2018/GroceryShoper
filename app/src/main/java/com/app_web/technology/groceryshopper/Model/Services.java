package com.app_web.technology.groceryshopper.Model;
public class Services {
    private int id;
    private String name, status, image, profilePic, country_id, url;
    String cat_id;
    String cat_desc;
    String cat_image;
    String cat_name;
    String cat_order,is_promo;

    String has_subcat;
    public Services() {
    }

    public Services(int id, String name, String image, String status,
                    String profilePic, String timeStamp, String url,String is_promo
                    ,String cat_id, String cat_desc, String cat_image, String cat_name, String cat_order, String has_subcat) {
        super();
        this.id = id;
        this.name = name;
        this.image = image;
        this.status = status;
        this.profilePic = profilePic;
        this.country_id = timeStamp;
        this.url = url;
        this.is_promo=is_promo;
        this.cat_id = cat_id;
        this.cat_desc = cat_desc;
        this.cat_image = cat_image;
        this.cat_name = cat_name;
        this.cat_order = cat_order;
        this.has_subcat = has_subcat;
    }
    public String getis_promo() {
        return is_promo;
    }

    public void setis_promo(String is_promo) {
        this.is_promo = is_promo;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImge() {
        return image;
    }

    public void setImge(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getcountry_id() {
        return country_id;
    }

    public void setcountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
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