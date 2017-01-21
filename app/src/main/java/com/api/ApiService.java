package com.api;

import com.app.annotation.apt.ApiFactory;
import com.base.entity.CreatedResult;
import com.base.entity.DataArr;
import com.base.entity.Face;
import com.model.Image;
import com.model.Comment;
import com.model.CommentInfo;
import com.model.ImageInfo;
import com.model.Message;
import com.model.MessageInfo;
import com.model._User;

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
@ApiFactory
public interface ApiService {
    @GET("login")
    Observable<_User> login(@Query("username") String username, @Query("password") String password);

    @POST("users")
    Observable<CreatedResult> createUser(@Body _User user);

    @GET("users")
    Observable<DataArr<_User>> getAllUser(@Query("skip") int skip, @Query("limit") int limit);

    @GET("classes/Image")
    Observable<DataArr<ImageInfo>> getAllImages(@Query("where") String where, @Query("skip") int skip, @Query("limit") int limit, @Query("order") String order);

    @POST("classes/Image")
    Observable<CreatedResult> createArticle(@Body Image mArticle);

    @GET("classes/Comment")
    Observable<DataArr<CommentInfo>> getCommentList(@Query("include") String include, @Query("where") String where, @Query("skip") int skip, @Query("limit") int limit);

    @POST("classes/Comment")
    Observable<CreatedResult> createComment(@Body Comment mComment);


    @Headers("Content-Type: image/png")
    @POST("files/{name}")
    Observable<CreatedResult> upFile(@Path("name") String name, @Body RequestBody body);


    @PUT("users/{uid}")
    Observable<CreatedResult> upUser(@Header("X-LC-Session") String session, @Path("uid") String uid, @Body Face face);


    @POST("classes/Message")
    Observable<CreatedResult> createMessage(@Body Message mComment);

    @GET("classes/Message")
    Observable<DataArr<MessageInfo>> getMessageList(@Query("include") String include, @Query("where") String where, @Query("skip") int skip, @Query("limit") int limit, @Query("order") String order);

}
