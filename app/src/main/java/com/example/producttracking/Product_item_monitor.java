package com.example.producttracking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.producttracking.API.ApiServiceFactory;
import com.example.producttracking.Adapter.RecordListAdapter;
import com.example.producttracking.Models.Model;
import com.example.producttracking.Models.dynamic_transaction_data;
import com.example.producttracking.Models.getattribute;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class Product_item_monitor extends AppCompatActivity {

    ApiServiceFactory.ApiService apicall = ApiServiceFactory.getApiService();

    String QRCode_ID;

    JSONObject jObj3;

    // for listview of transaction

    NonScrollListView itemlist;

    ArrayList<Model> mList;

    RecordListAdapter mAdapter = null;

    //bind a data of listview

    String product_name , json ;

    TextView productname  , producted_in_text , Quantity_text , category_text , Product_Price_txt;

    HashMap<String, String> productDetails = new HashMap<>();
    //HashMap<String, String> transaction = new HashMap<>();

    ArrayList transaction = new ArrayList();

    ArrayList manufacture = new ArrayList();

    ArrayList time_arr = new ArrayList();

    RelativeLayout ful_detail_product;

    // dynamin_textview_manufac_details
    LinearLayout dynamin_textview_manufac_details ;

    TextView tv;

    List<TextView> allTVs = new ArrayList<TextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_item_monitor);

        ful_detail_product = findViewById(R.id.ful_detail_product);

        dynamin_textview_manufac_details  = findViewById(R.id.dynamin_textview_manufac_details);

        itemlist = (NonScrollListView) findViewById(R.id.itemlist);

        mList = new ArrayList<>();

        //
        productname = findViewById(R.id.productname);

        producted_in_text = findViewById(R.id.producted_in_text);
        Quantity_text = findViewById(R.id.Quantity_text);
        category_text  = findViewById(R.id.category_text);
        Product_Price_txt = findViewById(R.id.Product_Price_txt);

        // QRCode data from scanner class
        Intent intent = getIntent();
        QRCode_ID = intent.getStringExtra("QRCode_ID");

         QRCode_ID = "76616562";

        ful_detail_product.setVisibility(INVISIBLE);
        itemlist.setVisibility(INVISIBLE);

        if ( QRCode_ID != null)
        {

            Product_Trans_history();
        }
        else
        {
            ful_detail_product.setVisibility(INVISIBLE);
            itemlist.setVisibility(INVISIBLE);
        }
    }


    public void Scan_QRCode(View view)
    {
        Intent i = new Intent(Product_item_monitor.this, scanner.class);
        i.putExtra("isEnduser", "true");
        finish();
        startActivity(i);
    }

    public void Product_Trans_history()
    {
       // QRCode_ID="QR00102";

        apicall.trans_history(QRCode_ID).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == HttpURLConnection.HTTP_OK) {
//                    String json;
                    try {
                        ful_detail_product.setVisibility(VISIBLE);
                        itemlist.setVisibility(VISIBLE);

                        json = response.body().string();

                        //---------------- Manufactory data-----------------

                        JSONObject jsonObject = new JSONObject(json);

                        JSONObject data = jsonObject.getJSONObject("data");

                        JSONObject prouct = data.getJSONObject("prouct");

                        String id =prouct.getString("_id");

                        product_name  = prouct.getString("productName");

                        String createdAt_str=prouct.optString("createdAt");

                        String[] parts = createdAt_str.split("T");

                        System.out.println("Date: " + parts[0]);
                        //2020-02-24T14:04:15.874Z

                        String str = parts[1] ;

                        String[] time = str.split(":");

                        String str2 = time[0]+ ":" + time[1] ;

                        //-------productDetails-------

                        JSONObject productDetails = prouct.getJSONObject("productDetails");
                        //  product_name =productDetails.getString("product_name");

                        Iterator<String> keys1 = productDetails.keys();

                        for(int i = 0; i<productDetails.names().length(); i++)
                        {
                            Log.d("key = " + productDetails.names().getString(i), " value =  " + productDetails.get(productDetails.names().getString(i)));

                           // productDetails.put( productDetails.names().getString(i) , productDetails.get(productDetails.names().getString(i)).toString());

                            manufacture.add(productDetails.get(productDetails.names().getString(i)).toString());
                        }

                        //----------Product full details-----------

                        String producted_in_str = productDetails.getString("product_describ");
//                        String Product_Price_str = productDetails.getString("productPrize");
//                        String Quantity_str = productDetails.getString("productSize");
//                        String category_str = productDetails.getString("category");

                        producted_in_text.setText(producted_in_str);
//                        Product_Price_txt.setText(Product_Price_str);
//                        Quantity_text.setText(Quantity_str);
//                        category_text.setText(category_str);



                        //dynamicalyy create a textview to a values from manufacture arraylist

//                        int count = manufacture.size() ;
//
//                        for (int i = 0; i < count; i++)
//                        {
//                            //--------------textview------------------
//                            tv = new TextView(Product_item_monitor.this);
//                            allTVs.add(tv);
//                            // tv.setBackgroundResource(R.drawable.editext_bg);
//                            tv.setTextColor(Color.BLACK);
//                            tv.setId(i);
//                            tv.setText(manufacture.get(i).toString());
//                            //tv.setHint(list.get(i).toString());
//                            tv.setPadding(10, 10, 10, 10);
//                            tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                                    ViewGroup.LayoutParams.WRAP_CONTENT));
//                            dynamin_textview_manufac_details.addView(tv);
//
//                        }

                        // manufacture details display in listview

                        JSONObject prouctcreater = data.getJSONObject("prouctcreater");
                        String companyName =prouctcreater.getString("companyName");
                         String companyLocation =prouctcreater.getString("companyLocation");
                         String companyDescription =prouctcreater.getString("companyDescription");

                        mList.add(new Model(id,companyName.toString() ,companyLocation.toString(), companyDescription.toString(), parts[0] , str2));   //   mList.add(new Model(id,product_name.toString() , manufacture.get(1).toString(), manufacture.get(0).toString(), parts[0] , str2));

                        mAdapter = new RecordListAdapter(Product_item_monitor.this, R.layout.list_row, mList);     //------------using Adapter

                        itemlist.setAdapter(mAdapter);


                        //-------------product full details - bind data in top of screen ------------------

                        productname.setText(product_name);



                        // read a data of json object of array

                        //---------------- Retrier users data -----------------

                        JSONArray jsonArray = data.getJSONArray("transactionDetails");
                        int length = jsonArray.length();

                        for(int i=0; i<length; i++)
                        {
                            JSONObject jObj = jsonArray.getJSONObject(i);
                            String id2 = jObj.optString("_id");
                            String name=jObj.optString("userId");

                            String createdAt_str2=jObj.optString("createdAt");

                            String[] parts2 = createdAt_str2.split("T");
                            //2020-02-24T14:04:15.874Z

                            String time_str = parts2[1].substring(0,5) ;

                            jObj3 = jObj.getJSONObject("transaction");
                            //String product = jObj.optString("shopName");

                            Iterator<String> keys = jObj3.keys();

                            for( int ii = 0; ii<jObj3.names().length(); ii++)
                            {
                                Log.d("key = " + jObj3.names().getString(ii), " value =  " + jObj3.get(jObj3.names().getString(ii)));
                                transaction.add(jObj3.get(jObj3.names().getString(ii)).toString());
                            }

                            //Model(  id,  company_name,  location_transfer,   describe,   date,  time )
                            mList.add(new Model(id2, transaction.get(0).toString(), transaction.get(1).toString(), transaction.get(2).toString(), parts2[0] , time_str ));

                        transaction.removeAll(transaction);

                        }

                        mAdapter = new RecordListAdapter(Product_item_monitor.this, R.layout.list_row, mList);     //------------using Adapter

                        itemlist.setAdapter(mAdapter);

//                        JSONObject jsonRootObject = new JSONObject(json);
//                        //Get the instance of JSONArray that contains JSONObjects
//                        JSONArray jsonArray = jsonRootObject.optJSONArray("data");

//                        JSONObject jsonObject = new JSONObject(json);
//                        JSONObject data = jsonObject.getJSONObject("data");
//
//                        JSONObject prouct = data.getJSONObject("prouct");
//                        String nameValue2 =prouct.getString("_id");
//
//                        //productDetails
//                        JSONObject productDetails = prouct.getJSONObject("productDetails");
//                        String product_name =productDetails.getString("product_name");
//                        String product_describ =productDetails.getString("product_describ");
//
//                        Log.d("Value ", "Val " + product_name.toString());
//                        Log.d("Value ", "Val " + product_describ.toString());
//
//                        //---------------- Retrier users data -----------------
//
//                        // read a data of json object of array
//                        JSONArray jsonArray = data.getJSONArray("transactionDetails");
//                        int length = jsonArray.length();
//
//                        for(int i=0; i<length; i++)
//                        {
//                            JSONObject jObj = jsonArray.getJSONObject(i);
//                            String id = jObj.optString("_id");
//                            String name=jObj.optString("userId");
//
//                            jObj3 = jObj.getJSONObject("transaction");
//                            String product = jObj.optString("shopName");
//                        }
//
//                        // it is used for read json object and get a key name and value (dynamic data)
//                        Iterator<String> keys = jObj3.keys();
//                        while(keys.hasNext())
//                        {
//                            String key = keys.next();
////                if (jObj3.get(key) instanceof JSONObject)
////                {   // do something with jsonObject here
//                            for(int i = 0; i<jObj3.names().length(); i++)
//                            {
//                                Log.d("key = " + jObj3.names().getString(i), " value =  " + jObj3.get(jObj3.names().getString(i)));
//                            }
//                            // }
//                        }
//
//                        bind_data_listview();

                        Toast.makeText(getBaseContext(), "Success to set Spinner value", Toast.LENGTH_SHORT).show();

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
        this.finish();

        finish();

    }

    public void bind_data_listview()
    {

        //-------------------Listview---------------------------

        itemlist = (NonScrollListView) findViewById(R.id.itemlist);

        mList = new ArrayList<>();

        String company_name_arr[] = {"manufactured"};

        String location_transfer_arr[] = {"Mumbai-India", "Mumbai-India", "hyderabad-India", "Bengalore-India", "Chennai-India"};

        String describe_arr[] = {"this is a product describtion", "this is a product describtion", "this is a product describtion", "this is a product describtion", "this is a product describtion"};

        String mTitle[] = {"Product_1", "Product_2", "Product_3", "Product_4", "Product_5"};

       // String date_arr [] = {"21/02/2020", "23/02/2020", "25/02/2020", "27/02/2020", "29/02/2020"};

        Integer[] mThumbIds = { R.drawable.soap, R.drawable.makeup,
                R.drawable.product, R.drawable.product};

        for (int i = 0; i < 5; i++)
        {
            int id = i;
            String company_name = company_name_arr[i].toString();
            String location_transfer = location_transfer_arr[i].toString();
            String describe = describe_arr[i].toString();

            String name = mTitle[i].toString();
          //  String date = createdAt.get(i).toString();
            String phone = " Description of project";
//             byte[] image = mThumbIds[i];
            //add to list
           // mList.add(new Model(id, company_name , location_transfer, describe, date));

        }

        mAdapter = new RecordListAdapter(Product_item_monitor.this, R.layout.list_row, mList);     //------------using Adapter

        itemlist.setAdapter(mAdapter);
        //------------------------------------------------------

    }

}
