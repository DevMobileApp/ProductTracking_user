package com.example.producttracking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.producttracking.API.ApiServiceFactory;
import com.example.producttracking.Models.RegisterModel;
import com.example.producttracking.Models.attributes;
import com.example.producttracking.Models.get_attributeby_id;
import com.example.producttracking.Models.getattribute;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.example.producttracking.Models.Rollid_model;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.VISIBLE;

public class Register extends AppCompatActivity {

    LinearLayout myLinearLayout;

    public int numberOfLines = 3;

    // dynamic editext
    int count;
    EditText ed;
    List<EditText> allEds = new ArrayList<EditText>();

    TextView tv;
    List<TextView> allTVs = new ArrayList<TextView>();

    String[] myList;
    String ID_attributeId, roleId;


    ApiServiceFactory.ApiService apicall = ApiServiceFactory.getApiService();

    String[] Rolechoose_Id = new String[27];
    String[] roleType = new String[27];
    String[] CreatedAt = new String[27];
    String[] isAdmin = new String[27];

    int role_count;
    String role_id;

    // Spinner Drop down elements

    List<String> roleType_list = new ArrayList<String>();

    Spinner spinner;
    String[] dynamic_txt_box ,textbox_label, attrib_key ;
    ArrayList list = new ArrayList();
    List<attributes> pilot;
    int editext_crt_count = 0;

    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        myLinearLayout=findViewById(R.id.dynamic_layout);

        spinner = findViewById(R.id.rolechoose);

        submit = findViewById(R.id.submit);

       // submit.setEnabled(false);
        submit.setVisibility(View.INVISIBLE);

        // add_edittext();

       // drop_box_value_role();
        //------------------choose role API code ---------------------

        apicall.getroleid().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == HttpURLConnection.HTTP_OK) {
                    String json;
                    try {
                        json = response.body().string();

                        // JSONObject jsonObject = new JSONObject(json);
                        //  roleid = jsonObject.get("id").toString();

                        Gson gson = new GsonBuilder().setPrettyPrinting().create();

                        Type listType = new TypeToken<List<Rollid_model>>() {
                        }.getType();


                        List<Rollid_model> posts = gson.fromJson(json, listType);

                        //   String length=posts.get(0).getID();

                         role_count= posts.size() ;

                        // find the id of Project Categories from the list
                        for (int id = 0; id < role_count; id++) {
                            Rolechoose_Id[id] = posts.get(id).getID();
                        }

                        // find the name of Project Categories from the list
                        for (int count = 0; count < role_count; count++) {
                            roleType[count] = posts.get(count).getCategoryName();
                        }


                        //call a spinner to add a values in API
                        drop_box_value_role();


                      //  Toast.makeText(getBaseContext(), "GetProjectCategories", Toast.LENGTH_SHORT).show();


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

        //---------------------------------------------------------
    }

    public void  drop_box_value_role()
    {

       // Adding items to spinner in list

        roleType_list.add("select a role");
        for (int names = 0; names < role_count; names++)
        {
            roleType_list.add(roleType[names]);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_color, roleType_list);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_text_color);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        // on selected item in spinner to send a selected item catagory ID to the Api
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
              //  Toast.makeText(parent.getContext(), "Selected: " + tutorialsName,  Toast.LENGTH_LONG).show();

                if (editext_crt_count > 0 )
                {

                    LinearLayout ll = (LinearLayout) findViewById(R.id.dynamic_layout);
                    ll.removeAllViews();

                }

                String item = parent.getItemAtPosition(position).toString();

                for (int z = 0; z < role_count; z++)
                {
                    if (item == roleType[z]) {
                        role_id = Rolechoose_Id[z];
                        break;
                    }
                }

                //------------------choose role API code ---------------------
              if (role_id != null)
              {
                  apicall.pass_roleid(role_id).enqueue(new Callback<ResponseBody>() {
                      @Override
                      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                          if (response.code() == HttpURLConnection.HTTP_OK) {
                              String json;
                              try {
                                  json = response.body().string();

                                  //  Gson gson = new GsonBuilder().setPrettyPrinting().create();

                                  Gson gson = new Gson();

                                  getattribute generalInfoObject = gson.fromJson(json, getattribute.class);

                                  ID_attributeId = generalInfoObject.get_id();
                                  roleId = generalInfoObject.getRoleId();


                                  // it is used for json object of array of object
                                  pilot = generalInfoObject.getattributes();

                                  editext_crt_count = pilot.size();

                                  textbox_label = new String[editext_crt_count];
                                  for (int cnt = 0; cnt < editext_crt_count; cnt++) {
                                      //textbox_label.add(pilot.get(cnt).getlabel().toString());
                                      textbox_label[cnt] = pilot.get(cnt).getkey().toString();
                                  }

                                  attrib_key = new String[editext_crt_count];
                                  for (int cnt = 0; cnt < editext_crt_count; cnt++) {
                                      //  attrib_key.add(pilot.get(cnt).getkey().toString());
                                      attrib_key[cnt] = pilot.get(cnt).getkey().toString();
                                  }

                                  String text = pilot.get(0).getdescription().toString();


                                  //REMOVE A PREVIOUS data in the list
                                  list.removeAll(list);

                                  //get a hint for Edittext on dynamic create
                                  for (int i = 0; i < editext_crt_count; i++) {
                                      //  dynamic_txt_box[i] = pilot.get(i).getlabel().toString();
                                      list.add(pilot.get(i).getlabel().toString());
                                  }
//
                                  add_edittext();
                                  // ---------------------------------------

                                  // Toast.makeText(getBaseContext(), "GetProjectCategories", Toast.LENGTH_SHORT).show();

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


                //---------------------------------------------------------

            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });


    }


    public void register_btn(View view)
    {
//        for(int i = 0; i < ed.length; i++){
//
//            Log.d("Value ","Val " + ed[i].getText());
//        }
       // startActivity(new Intent(Register.this, Login.class));
    }



    public void add_edittext()
    {

//        EditText ed[] = new EditText[count];

        // Emptying an ArrayList in Java
        if(allEds.size() > 0)
        {
            allEds.removeAll(allEds);
        }


        // plus for index zero is not takes a value from api
        count = editext_crt_count ;

        //list id object convert into array
       // String[] arr = new String[count];

        for (int i = 0; i < count; i++) {


            //--------------textview------------------
            tv = new TextView(Register.this);
            allTVs.add(tv);
           // tv.setBackgroundResource(R.drawable.editext_bg);
            tv.setTextColor(Color.WHITE);
            tv.setId(i+100);
            tv.setText(list.get(i).toString());
            //tv.setHint(list.get(i).toString());
            tv.setPadding(10,10,10,10);
            tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            myLinearLayout.addView(tv);

            //--------------------------------

           // arr[i] = list.get(i).toString();

            ed = new EditText(Register.this);
            allEds.add(ed);
//            ed.setBackgroundResource(R.color.white);
            ed.setBackgroundResource(R.drawable.editext_bg);
            ed.setTextColor(Color.BLACK);
            ed.setId(i);
            ed.setHint(list.get(i).toString());
            ed.setPadding(10,10,10,10);
            ed.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            myLinearLayout.addView(ed);

//            ed[i] = new EditText(Register.this);
//
//            ed[i].setBackgroundResource(R.drawable.editext_bg);
//            ed[i].setTextColor(Color.BLACK);
//            ed[i].setId(i);
//            ed[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT));
//            myLinearLayout.addView(ed[i]);
        }


        submit.setVisibility(VISIBLE);
//        submit.setEnabled(true);

        // it is used for fatching a values of EditText by user ( not Used in Here)
        String[] editext_box1 = new String[allEds.size()];
        for (int i = 0; i < allEds.size(); i++) {
            editext_box1[i] = allEds.get(i).getText().toString();
            Log.d("Value ", "Val " + editext_box1[i].toString());
        }

    }

    public void submit(View view)
    {

        if( ed != null ) {
            String[] editext_box = new String[allEds.size()];
            // String[] strings = new String[](allEds.size());

            for (int i = 0; i < allEds.size(); i++) {
                editext_box[i] = allEds.get(i).getText().toString();
                Log.d("Value ", "Val " + editext_box[i].toString());
            }
            //------------------Dynamic json object post in body----------------------------

            JsonObject jsonObject = new JsonObject();

            int z = allEds.size();


            jsonObject.addProperty("roleId", roleId);
            jsonObject.addProperty("attributeId", ID_attributeId);

            for (int i=1 ; i <= z ;i++)
            {
                jsonObject.addProperty(attrib_key[i-1].toString(), editext_box[i-1].toString());

//                jsonObject.addProperty("password", "test");
            }

            // Using the Retrofit
            apicall.postRawJSON(jsonObject).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.code() == HttpURLConnection.HTTP_OK) {
                        String json;
                        try {
                            json = response.body().string();



                            Toast.makeText(getBaseContext(), "Register success", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(Register.this,Login.class));

                            finish();


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

            //----------------------------------------------
//            RegisterModel registerModel = new RegisterModel();
//                    registerModel.roleId = roleId;
//                    // get data from textbox
//            registerModel.firstName = editext_box[0].toString();
//                    registerModel.lastName = editext_box[1].toString();
//            registerModel.email  = editext_box[2].toString();
//                    registerModel.password = editext_box[3].toString();
//            registerModel.location = editext_box[4].toString();
//                    //registerModel.companyname= editext_box[5].toString();
//                    registerModel.attributeId =ID_attributeId;

            //----------------------api----------------------

//            apicall.register(registerModel).enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                    if (response.code() == HttpURLConnection.HTTP_OK) {
//                        String json;
//                        try {
////                            json = response.body().string();
////
////                            //  Gson gson = new GsonBuilder().setPrettyPrinting().create();
////
////                            Gson gson = new Gson();
//
//                            json = response.body().string();
//                            JSONObject jsonObject = new JSONObject(json);
//
//                            Toast.makeText(getBaseContext(), "Success", Toast.LENGTH_SHORT).show();
//
//
//
//                            // ---------------------------------------
//
//                            Toast.makeText(getBaseContext(), "GetProjectCategories", Toast.LENGTH_SHORT).show();
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                    else if (response.code() == HttpURLConnection.HTTP_BAD_REQUEST) {
//                        String json = null;
//                        try {
//                            json = response.errorBody().string();
//                            JSONObject jsonObject = new JSONObject(json);
//                            String msg = jsonObject.getString("error_description");
//                            Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    else {
//                        int statuscode = response.code();
//                        Toast.makeText(getBaseContext(), "status code " + statuscode + "\n" + "login failed", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Toast.makeText(getBaseContext(), "server not active", Toast.LENGTH_SHORT).show();
//                }
//
//
//            });

            //-------------------------------------------------
        }
        else
            Toast.makeText(this, "Fiels are required", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        startActivity(new Intent(Register.this , Login.class));
        finish();
        this.overridePendingTransition(R.anim.animation_enter,
                R.anim.animation_leave);
    }

}
