package com.api;

import com.data.CreatedResult;
import com.data.Data;
import com.data.entity.Comment;
import com.data.entity.CommentInfo;
import com.data.entity.Image;
import com.data.entity._User;
import com.ui.user.UserModel;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/3/23.
 */
public interface ApiService {
    @GET("login")
    Observable<_User> login(@Query("username") String username, @Query("password") String password);

    @POST("users")
    Observable<CreatedResult> createUser(@Body _User user);


    @GET("users")
    Observable<Data<_User>> getAllUser(@Query("skip") int skip, @Query("limit") int limit);

    @GET("classes/Image")
    Observable<Data<Image>> getAllImages(@Query("where") String where, @Query("order") String order, @Query("skip") int skip, @Query("limit") int limit);


    @GET("classes/Comment")
    Observable<Data<CommentInfo>> getCommentList(@Query("include") String include, @Query("where") String where, @Query("skip") int skip, @Query("limit") int limit);


    @POST("classes/Comment")
    Observable<CreatedResult> createComment(@Body Comment mComment);


    @Headers("Content-Type: image/png")
    @POST("files/{name}")
    Observable<CreatedResult> upFile(@Path("name") String name, @Body RequestBody  body);


    @PUT("users/{uid}")
    Observable<CreatedResult> upUser(@Header("X-LC-Session") String session, @Path("uid") String uid, @Body UserModel.Face face);
}
