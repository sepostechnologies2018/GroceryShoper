package com.app_web.technology.groceryshopper.interfaces;

import com.app_web.technology.groceryshopper.Model.DesboardModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Seo on 03-01-2018.
 */

public interface API {

    String BASE_URL="http://www.satyamtechnologies.co.uk/Dashboard/apps_data/";

   @FormUrlEncoded
    @POST("check_app_version_api.php")
    Call<DesboardModel> desboardData(@Field("AppName")String AppName,
                                     @Field("version")String version,
                                     @Field("os")String os);



}
