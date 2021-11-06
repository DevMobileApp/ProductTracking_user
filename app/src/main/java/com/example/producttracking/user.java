package com.example.producttracking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.producttracking.Adapter.RecordListAdapter;
import com.example.producttracking.Models.Model;

import java.util.ArrayList;
import java.util.Scanner;

public class user extends AppCompatActivity {

    NonScrollListView itemlist;

    ArrayList<Model> mList;

    RecordListAdapter mAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //-------------------Listview---------------------------

       // itemlist=(findViewById(R.id.itemlist));

        itemlist = (NonScrollListView) findViewById(R.id.itemlist);

        mList = new ArrayList<>();

        String company_name_arr[] = {"manufactured", "retriler1", "wholesale", "supermarket", "smallshop"};

        String mTitle[] = {"Product1", "Product1_2", "Product1_3", "Product_4", "Youtube"};

        Integer[] mThumbIds = { R.drawable.soap, R.drawable.makeup,
                R.drawable.product, R.drawable.product};

        for (int i = 0; i < 5; i++)
        {
            int id = i;
            String company_name = mTitle[i].toString();
            String name = mTitle[i].toString();
            String age = "26";
            String phone = " Description of project";
//             byte[] image = mThumbIds[i];
            //add to list
          //  mList.add(new Model(id, company_name , name, age, phone));

        }
        mAdapter = new RecordListAdapter(user.this, R.layout.list_row, mList);     //------------using Adapter

        itemlist.setAdapter(mAdapter);
      //------------------------------------------------------

    }

    public void scnner(View view)
    {
        startActivity(new Intent(user.this, scanner.class));
    }
}
