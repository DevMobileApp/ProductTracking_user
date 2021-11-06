package com.example.producttracking.API;



import com.example.producttracking.Models.LoginModel;
import com.example.producttracking.Models.RegisterModel;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.example.producttracking.Static_variables.roleid_str;

//import com.example.nuvuw2.Models.Fb_SignupModel;
//import com.example.nuvuw2.Models.RegisterModel;
//import com.example.login.Models.RegisModel;

public class ApiServiceFactory {



    private static ApiService apiService_instance;

    public static ApiService getApiService() {

        if (apiService_instance == null) {
            synchronized (ApiServiceFactory.class) {
                if (apiService_instance == null) {
                    apiService_instance = getRetrofit().create(ApiService.class);
                }
            }
        }
        return apiService_instance;
    }



    public interface ApiService {

        //login
        @POST("login")
        Call<ResponseBody> getLogin(@Body LoginModel changePasswordModel);


        //choose a role --- getuserrole
        @GET("getuserrole")
        Call<ResponseBody> getroleid();



        @GET("getattributebyid/{id}")
        Call<ResponseBody> pass_roleid(@Path("id") String id);


//        @FormUrlEncoded
//        @POST("login")
//        Call<ResponseBody> RegisterUser(@Body JsonObject jsonBody , @Header("Content-Type: application/raw")String content );


        // post a dynamic edittext values in json object in BODY
        @POST("userregister")
        Call<ResponseBody> postRawJSON(@Body JsonObject locationPost);


        //Get a product category list
        @GET("getallproductcategory")
        Call<ResponseBody> getproductcategory();

        // post a dynamic edittext values in json object in BODY
        @POST("createproduct")
        Call<ResponseBody> Create_product(@Body JsonObject locationPost);


        // post a dynamic edittext values in json object in BODY
        @POST("createtransaction")
        Call<ResponseBody> Create_transaction(@Body JsonObject locationPost);

        //http://192.168.0.8:3002/api/v1/gettransaction/{qrcode}
        @GET("gettransaction/{qrcode}")
        Call<ResponseBody> trans_history(@Path("qrcode") String qrcode);
    }

    private static Retrofit getRetrofit() {
        OkHttpClient client = new OkHttpClient();
        client.connectTimeoutMillis();
        client.readTimeoutMillis();
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        return new Retrofit.Builder()
                .baseUrl("http://192.168.43.18:3002/api/V1/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
