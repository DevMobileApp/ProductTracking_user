package com.example.producttracking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.producttracking.API.ApiServiceFactory;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class test_post_body extends AppCompatActivity {

    ApiServiceFactory.ApiService apicall = ApiServiceFactory.getApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_post_body);

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("email","test@gmail.com");

        jsonObject.addProperty("password", "test");

        // Using the Retrofit
        apicall.postRawJSON(jsonObject).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == HttpURLConnection.HTTP_OK) {
                    String json;
                    try {
                        json = response.body().string();



                        Toast.makeText(getBaseContext(), "GetProjectCategories", Toast.LENGTH_SHORT).show();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (response.code() == HttpURLConnection.HTTP_BAD_REQUEST) {
                    String json = null;
                    try {
                        json = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(json);
                        String msg = jsonObject.getString("error_description");
                        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    int statuscode = response.code();
                    Toast.makeText(getBaseContext(), "status code " + statuscode + "\n" + "login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getBaseContext(), "server not active", Toast.LENGTH_SHORT).show();
            }


        });
    }
}
