package com.android.erp.Network;

import com.android.erp.Network.Response.CategoriesResponse;
import com.android.erp.Network.Response.ClientsResponse;
import com.android.erp.Network.Response.Users;
import com.android.erp.Network.Response.HomeResponse;
import com.android.erp.Network.Response.LoginResponse;
import com.android.erp.Network.Response.ResultResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("login.php/")
    Observable<LoginResponse> login(@Field("username") String username,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("forgotpassrequest.php/")
    Observable<LoginResponse> forgotPassword(@Field("username") String username);

    @FormUrlEncoded
    @POST("nbposts.php/")
    Observable<HomeResponse> getMain(@Field("userId") String userId,
                                     @Field("month") String month,
                                     @Field("year") String year);

    @FormUrlEncoded
    @POST("getallposts.php/")
    Observable<List<CategoriesResponse>> getPosts(@Field("userId") String userId,
                                                  @Field("categoryId") String categoryId,
                                                  @Field("month") String month,
                                                  @Field("year") String year);


    @FormUrlEncoded
    @POST("allusers.php/")
    Observable<ClientsResponse> getClients(@Field("adminId") String adminId);

    @FormUrlEncoded
    @POST("addpost.php/")
    Observable<ResultResponse> add(@Field("userId") String userId,
                                   @Field("categoryId") String categoryId,
                                   @Field("title") String title,
                                   @Field("text") String text,
                                   @Field("price") String price,
                                   @Field("date") String date,
                                   @Field("checking") String checking);

    @FormUrlEncoded
    @POST("adduser.php/")
    Observable<ResultResponse> addUser(@Field("userId") String userId,
                                       @Field("password") String password,
                                       @Field("adminId") String adminId,
                                       @Field("displayname") String displayName,
                                       @Field("paketId") String paketId,
                                       @Field("telephone") String telephone,
                                   @Field("username") String username,
                                   @Field("address") String address,
                                   @Field("site") String site);
}
