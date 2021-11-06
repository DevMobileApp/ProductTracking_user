package com.example.producttracking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.producttracking.API.ApiServiceFactory;
import com.example.producttracking.Models.LoginModel;
import com.example.producttracking.Models.getattribute;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    ApiServiceFactory.ApiService apicall = ApiServiceFactory.getApiService();

    EditText username, password;

    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = findViewById(R.id.UserName);
        password = findViewById(R.id.Password);

//        LinearLayout parentLayout = (LinearLayout)findViewById(R.id.parent);
//        TextView textView = new TextView(Login.this);
//        textView.setText("Child Row");
//        textView.setTextSize(20f);
//
//        parentLayout.addView(textView);
    }

    public void login_btn(View view) {

//        startActivity(new Intent(Login.this,test_post_body.class));

//        username.setText("enduser");
//        password.setText("123");

//        username.setText("vss@gmail.com");
//        password.setText("123");

        String username_text = username.getText().toString().trim();
        String password_text = password.getText().toString().trim();

        //For End User
        if (username_text.equals("enduser") && password_text.equals("123"))
        {
            startActivity(new Intent(Login.this,Product_item_monitor.class));
        }

        Log.d("Value ", "Val " + username_text.toString());


        if (!username_text.equals("") && !password_text.equals("")) {
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            if (username.getText().toString().matches(emailPattern) )
            {
                login_API();
                //startActivity(new Intent(Login.this, user.class));
            }
            else
                Toast.makeText(this, "Username is Invalid", Toast.LENGTH_SHORT).show();
        }

        else if (username_text.equals("") && password_text.equals("")) {
            Toast.makeText(getBaseContext(), "Fields are required", Toast.LENGTH_SHORT).show();
            username.requestFocus();
        } else if (username_text.equals("")) {
            Toast.makeText(getBaseContext(), "username is empty", Toast.LENGTH_SHORT).show();
            username.requestFocus();
        } else if (password_text.equals("")) {
            Toast.makeText(getBaseContext(), "Password is empty", Toast.LENGTH_SHORT).show();
            password.requestFocus();
        }





//        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//
////        int n=0;
////        if (n==0)
////        {
////            LinearLayout ll = (LinearLayout) findViewById(R.id.parent);
////            ll.removeAllViews();
////
////        }
//
//
        //---------------------------------------------------------

    }

    public void new_user(View view) {
        finish();
        startActivity(new Intent(Login.this, Register.class));
        animated();

    }

    public void login_API()
    {


            LoginModel loginModel = new LoginModel();
            loginModel.password = password.getText().toString();
            loginModel.username = username.getText().toString();

            //------------------choose role API code ---------------------

            apicall.getLogin(loginModel).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.code() == HttpURLConnection.HTTP_OK) {

                        try {

                            Toast.makeText(getBaseContext(), "Login Success", Toast.LENGTH_SHORT).show();

                            json = response.body().string();

                            JSONObject jsonObject = new JSONObject(json);
                            JSONObject offerObject = jsonObject.getJSONObject("data");

                           // JSONObject businessObject = offerObject.getJSONObject("objectData");

                            String nameValue = offerObject.getString("_id");
                            String isDatawriter = offerObject.getString("isDatawriter");
                            System.out.println(nameValue);


                            HashMap<String, String> map = new HashMap<>();

                            map.put("_id", offerObject.getString("_id").toString());
                            map.put("roleId",offerObject.getString("roleId").toString());
                            map.put("firstName", offerObject.getString("firstName").toString());
                            map.put("lastName", offerObject.getString("lastName").toString());
                            map.put("email", offerObject.getString("email").toString());
                            map.put("location",offerObject.getString("location").toString());
                            map.put("createdAt", offerObject.getString("createdAt").toString());
              //                            if( !offerObject.getString("WarehouseName").equals(JSONObject.NULL));
//                            {
//                                map.put("WarehouseName" ,offerObject.getString("WarehouseName"));
//                            }
//                            if( !offerObject.getString("StorageName").equals(JSONObject.NULL));
//                            {
//                                map.put("StorageName" ,offerObject.getString("StorageName"));
//                            }
//                            if (warehouse != null)
//                            {
//                                map.put("WarehouseName" ,warehouse);
//                            }
//                           else if (storage != null)
//                            {
//                                map.put("StorageName" ,storage);
//                            }
//                            else if (manefacturer != null)
//                            {
//                                map.put("CompanyName" ,manefacturer);
//                            }
//                            else if (retailer != null)
//                            {
//                                map.put("ShopName" ,retailer);
//                            }
                            map.put("isDatawriter", offerObject.getString("isDatawriter").toString());


                            //=========== sharedpreferences ===========

                            SharedPreferences sharedpreferences;
                            sharedpreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();

                            editor.putString("_id", offerObject.getString("_id").toString());
                            editor.putString("roleId", offerObject.getString("roleId").toString());
                            editor.putString("firstName", offerObject.getString("firstName").toString());
                            editor.putString("lastName", offerObject.getString("lastName").toString());
                            editor.putString("email", offerObject.getString("email").toString());
                            editor.putString("location", offerObject.getString("location").toString());
                            editor.putString("createdAt", offerObject.getString("createdAt").toString());
                            editor.putString("isDatawriter", offerObject.getString("isDatawriter").toString());
                            editor.apply();

                            //======================

                           // startActivity(new Intent(Login.this,manufactory_user.class));
                            Intent i = new Intent(Login.this, manufactory_user.class);
                            i.putExtra("map", map);
                            finish();
                            startActivity(i);

                            animated();


                            //takes a data from response
//                          String  id = jsonObject.get("_id").toString();
//                            String  roleid = jsonObject.get("roleId").toString();
//                            String  firstname = jsonObject.get("firstName").toString();
//                            String  lastname = jsonObject.get("lastName").toString();

//                            Log.d("value","val"+id+""+roleid+""+firstname+""+lastname);
                            // ---------------------------------------



                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        catch (JSONException e) {
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
                        String json = null;
                        try {
                            json = response.errorBody().string();
                            JSONObject jsonObject = new JSONObject(json);
                            String msg = jsonObject.getString("message");
                            Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getBaseContext(), "server not active", Toast.LENGTH_SHORT).show();
                }


            });

        }

        public void animated()
        {
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        finish();
    }

}
