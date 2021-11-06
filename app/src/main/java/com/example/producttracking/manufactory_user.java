package com.example.producttracking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.producttracking.API.ApiServiceFactory;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class manufactory_user extends AppCompatActivity {

    ImageView add_manufactory_item , scanner_logo;

    String user_id , roleId , qrcode , isDatawriter;

    //non_manufactory_user
    EditText product_location , product_name , product_details ;

    TextView QRcode_label;

    LinearLayout non_manufactory_user , manufactory_user;

    ApiServiceFactory.ApiService apicall = ApiServiceFactory.getApiService();

    TextView user_name, user_role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manufactory_user);

        //non_manufactory_user
        product_location = findViewById(R.id.product_location);

        product_name = findViewById(R.id.product_name);

        product_details = findViewById(R.id.product_details);

        non_manufactory_user = findViewById(R.id.non_manufactory_user);
        non_manufactory_user.setVisibility(INVISIBLE);

        QRcode_label= findViewById(R.id.QRcode_label);

        manufactory_user = findViewById(R.id.manufactory_user);
        manufactory_user.setVisibility(INVISIBLE);

        user_name  = findViewById(R.id.user_name);
        user_role  = findViewById(R.id.user_role);

        add_manufactory_item=findViewById(R.id.add_manufactory_item);
        add_manufactory_item.setVisibility(INVISIBLE);

        scanner_logo=findViewById(R.id.scanner_logo);
        scanner_logo.setVisibility(INVISIBLE);

     //
        Intent intent_scan = getIntent();
        user_id = intent_scan.getStringExtra("user_id");
        roleId = intent_scan.getStringExtra("roleId");
        qrcode = intent_scan.getStringExtra("QRCode_ID");
        isDatawriter = intent_scan.getStringExtra("isDatawriter");

        if ( qrcode != null)
        {
            QRcode_label.setText(qrcode);
        }
        else {
            //----- read a hashmap from login screen -----

            Intent intent_login = getIntent();
            HashMap<String, String> hashMap = (HashMap<String, String>) intent_login.getSerializableExtra("map");
            // Log.v("roleId", hashMap.get("roleId"));

            user_id = (hashMap.get("_id"));
            roleId = (hashMap.get("roleId"));

            isDatawriter = (hashMap.get("isDatawriter"));

            user_name.setText(hashMap.get("firstName"));
            user_role.setText(hashMap.get("email"));
        }

        if(isDatawriter.equals("true"))
        {
            add_manufactory_item.setVisibility(VISIBLE);

            non_manufactory_user.setVisibility(GONE);

            manufactory_user.setVisibility(VISIBLE);

            scanner_logo.setVisibility(INVISIBLE);
        }
       else if(isDatawriter.equals("false"))
        {
            add_manufactory_item.setVisibility(INVISIBLE);

            non_manufactory_user.setVisibility(VISIBLE);

            manufactory_user.setVisibility(INVISIBLE);

            scanner_logo.setVisibility(VISIBLE);
        }

       //        String warehouse = (hashMap.get("WarehouseName"));
//        String storage = (hashMap.get("StorageName"));
//        String manefacturer = (hashMap.get("CompanyName"));
//        String retailer = (hashMap.get("ShopName"));
//
//        if (warehouse != null)
//        {
//
//        }
//        else if (storage != null)
//        {
//
//        }
//        else if (manefacturer != null)
//        {
//
//        }
//        else if (retailer != null)
//        {
//
//        }


    }



    public void add_item(View view)
    {
       // startActivity(new Intent(manufactory_user.this,CreateProduct.class));
        Intent i = new Intent(manufactory_user.this, CreateProduct.class);
        i.putExtra("user_id", user_id);
        i.putExtra("roleId", roleId);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }
    public void scnner(View view)
    {
        finish();
        //startActivity(new Intent(manufactory_user.this,scanner.class));
        Intent i = new Intent(manufactory_user.this, scanner.class);
        i.putExtra("user_id", user_id);
        i.putExtra("roleId", roleId);
        i.putExtra("isDatawriter", isDatawriter);
        finish();
        startActivity(i);


    }

    public void update_product_non_manufac(View view)
    {
        if ( qrcode != null)
        {
            call_api_product_trans();
        }
        else
            Toast.makeText(this, "Scan a QRCode and submit", Toast.LENGTH_SHORT).show();

    }

    public void call_api_product_trans()
    {

        String product_name_str = product_name.getText().toString().trim();
        String product_details_str = product_details.getText().toString().trim();
        String product_location_str = product_location.getText().toString().trim();


        JsonObject jsonObject1 = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();

        //
        jsonObject1.addProperty("qrCode", qrcode);
        jsonObject1.addProperty("userId", user_id);
        jsonObject1.addProperty("roleId", roleId);
        jsonObject1.addProperty("product_name", product_name_str.toString());
        jsonObject1.addProperty("product_details",product_details_str.toString());
        jsonObject1.addProperty("product_location",product_location_str.toString());


        apicall.Create_transaction(jsonObject1).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == HttpURLConnection.HTTP_OK) {
                    String json;
                    try {
                        json = response.body().string();


                        Toast.makeText(getBaseContext(), "Product Transaction success", Toast.LENGTH_SHORT).show();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else if (response.code() == HttpURLConnection.HTTP_BAD_REQUEST) {
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
                }
                else {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(manufactory_user.this , Login.class));

        this.overridePendingTransition(R.anim.animation_enter,
                R.anim.animation_leave);
    }

}
