package com.example.producttracking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.producttracking.API.ApiServiceFactory;
import com.example.producttracking.Models.Rollid_model;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class CreateProduct extends AppCompatActivity {

    ApiServiceFactory.ApiService apicall = ApiServiceFactory.getApiService();

    List<String> product_catagory_name  = new ArrayList<String>();

    List<String> product_catagory_id  = new ArrayList<String>();

    Spinner product_cata_spn;

    int product_cata_items_count;

    String seleted_product_catagory_id , user_id , roleId;

    String Qrcode_barcode_id;

    EditText product_name ,product_descri , product_location , product_price;

    TextView QRCode_Label ;

    int position_spinner_select;

    String position_spinner_select2;

    LinearLayout dynamic_editext_layout;
    EditText ed;
    List<EditText> allEds = new ArrayList<EditText>();
    int edittext_id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        product_cata_spn= (Spinner)findViewById(R.id.product_cata_spn);

        dynamic_editext_layout = findViewById(R.id.dynamic_editext_layout);

        product_name =findViewById(R.id.product_name);
        product_descri =findViewById(R.id.product_descri);
        product_location =findViewById(R.id.product_location);
        product_price =findViewById(R.id.product_price);

        QRCode_Label =findViewById(R.id.QRcode_label);

        Api_post_create_product();

        Intent intent = getIntent();
        Qrcode_barcode_id=intent.getStringExtra("QRCode_ID");

        if (Qrcode_barcode_id != null)
        {
            QRCode_Label.setText(Qrcode_barcode_id);

        }

        else
            Toast.makeText(this, "Scan a QRCode", Toast.LENGTH_SHORT).show();
    }




    public void Api_post_create_product()
    {
        apicall.getproductcategory().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                  if (response.code() == HttpURLConnection.HTTP_OK) {
                    String json;
                    try {
                        json = response.body().string();

                        JSONObject  jsonRootObject = new JSONObject(json);

                        //Get the instance of JSONArray that contains JSONObjects
                        JSONArray jsonArray = jsonRootObject.optJSONArray("data");


                        product_cata_items_count =  jsonArray.length();
                        //Iterate the jsonArray and print the info of JSONObjects
                        for(int i=0; i < jsonArray.length(); i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

//                            int id = Integer.parseInt(jsonObject.optString("_id").toString());
                            String id = jsonObject.optString("_id").toString();

                            String categoryType = jsonObject.optString("categoryType").toString();

                            String description = jsonObject.optString("description").toString();

                            String createdAt = jsonObject.optString("createdAt").toString();


                            //create a list for add a values for spinner
                            product_catagory_name.add(categoryType.toString());

                            product_catagory_id.add(id.toString());

                        }

                        set_spinner_values();

                     //   Toast.makeText(getBaseContext(), "Success to set Spinner value", Toast.LENGTH_SHORT).show();

                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    catch ( JSONException e)
                    {
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
                   // int statuscode = response.code();
                   // Toast.makeText(getBaseContext(),  "login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getBaseContext(), "server not active", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void set_spinner_values()
    {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_color, product_catagory_name);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_text_color);

        // attaching data adapter to spinner
        product_cata_spn.setAdapter(dataAdapter);

//  ----------------   user selected item on spinner to pass scanner class and return takes here  -----------------------

        Intent intent = getIntent();
        position_spinner_select2=intent.getStringExtra("selected_spiner_item");
        if(position_spinner_select2 != null)
        {
            position_spinner_select=Integer.parseInt(position_spinner_select2);
            product_cata_spn.setSelection(position_spinner_select);
        }
//  ----------------     ---------------------------------------     -----------------------

        product_cata_spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();

                //this loop is used for get a product catagory ID
                for (int z = 0; z < product_cata_items_count; z++)
                {
                    if (item.equals(product_catagory_name.get(z))) {
                        seleted_product_catagory_id = product_catagory_id.get(z);
                        position_spinner_select = z;
                        break;
                    }
                }
//                Toast.makeText(parent.getContext(), "Selected: " + item,  Toast.LENGTH_LONG).show();
//                Toast.makeText(parent.getContext(), "Selected: " + seleted_product_catagory_id,  Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
    }

  //------------------------ Part 2 --------------------------

    public void create_product_Scan_QRCode(View view)
    {
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        roleId = intent.getStringExtra("roleId");

        position_spinner_select2 = String.valueOf(position_spinner_select);

       // startActivity(new Intent(CreateProduct.this,scanner.class));
        Intent i = new Intent(CreateProduct.this, scanner.class);
        i.putExtra("user_id", user_id);
        i.putExtra("roleId", roleId);
        i.putExtra("selected_spiner_item", position_spinner_select2);
        finish();
        startActivity(i);

    }
    public void create_product_Submit_btn(View view)
    {

        // Get from scanner class
        Intent intent = getIntent();
        user_id=intent.getStringExtra("user_id");
        roleId=intent.getStringExtra("roleId");
        Qrcode_barcode_id=intent.getStringExtra("QRCode_ID");

        if (Qrcode_barcode_id != null)
        {
           // product_name.setText(Qrcode_barcode_id);
            create_product_post();
        }

        else
            Toast.makeText(this, "Scan a QRCode ", Toast.LENGTH_SHORT).show();
    }

    public void create_product_post()
            {
                 // Qrcode_barcode_id = "asdsd" ;
                String  product_name_str =  product_name.getText().toString().trim();
                String product_descri_str = product_descri.getText().toString().trim();
                String product_location_str = product_location.getText().toString().trim();
                String product_price_str = product_price.getText().toString().trim();

                //------------------Dynamic json object post in body----------------------------

                JsonObject jsonObject1 = new JsonObject();
                JsonObject jsonObject2 = new JsonObject();

                //
                 jsonObject1.addProperty("categoryId", seleted_product_catagory_id);
                jsonObject1.addProperty("userId", user_id);
                jsonObject1.addProperty("roleId", roleId);

                jsonObject1.addProperty("qrCode", Qrcode_barcode_id);
                jsonObject1.addProperty("productName", product_name_str);

                 jsonObject1.add("productDetails", jsonObject2);
               // jsonObject2.addProperty("product_name", "toy");
                jsonObject2.addProperty("product_describ", product_descri_str);
                jsonObject2.addProperty("product_location", product_location_str);
                jsonObject2.addProperty("product_price", product_price_str);

//                for (int j =0 ; j < allEds.size()-1 ; j++)
//                {
//                    jsonObject2.addProperty( allEds.get(j).getText().toString(), allEds.get(j).getText().toString());
//                }


                // Using the Retrofit
                apicall.Create_product(jsonObject1).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.code() == HttpURLConnection.HTTP_OK) {
                            String json;
                            try {
                                json = response.body().string();

                                Toast.makeText(getBaseContext(), "Product Register success", Toast.LENGTH_SHORT).show();

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
//                            String json = null;
//                            try {
//                                json = response.errorBody().string();
//                                JSONObject jsonObject = new JSONObject(json);
//                                String msg = jsonObject.getString("message");
//                                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
//
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }

                            Toast.makeText(getBaseContext(), "Product is Exist ", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getBaseContext(), "server not active", Toast.LENGTH_SHORT).show();
                    }

                });

                //----------------------------------------------

            }

    public void add_editext(View view)
    {
        edittext_id++;
        ed = new EditText(CreateProduct.this);
        allEds.add(ed);
//            ed.setBackgroundResource(R.color.white);
        ed.setBackgroundResource(R.drawable.editext_bg);
        ed.setTextColor(Color.BLACK);
        ed.setId(edittext_id);
        ed.setPaddingRelative(20,20,20,20);
        ed.setPadding(10,10,10,10);
        ed.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT,
                WRAP_CONTENT ));

       // dynamic_editext_layout.addView(ed);

       // TextView tv = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        params.setMargins(0, 20, 0, 0);
        dynamic_editext_layout.addView(ed, params);

        String[] editext_box1 = new String[allEds.size()];

        for(int i = 0; i < allEds.size(); i++)
        {
            editext_box1[i] = allEds.get(i).getText().toString();
            Log.d("Value ", "Val " + editext_box1[i].toString());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        startActivity(new Intent(CreateProduct.this , Login.class));
        finish();
        this.overridePendingTransition(R.anim.animation_enter,
                R.anim.animation_leave);
    }

}
