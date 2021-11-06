package com.example.producttracking.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.producttracking.Models.Model;
import com.example.producttracking.R;

import java.util.ArrayList;

public class RecordListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Model> recordList;

    public RecordListAdapter(Context context, int layout, ArrayList<Model> recordList) {
        this.context = context;
        this.layout = layout;
        this.recordList = recordList;
    }

    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int i) {
        return recordList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView company_name,location,date,describe,id_item ,time;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if (row==null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);
            holder.company_name = row.findViewById(R.id.company_name);
            holder.location = row.findViewById(R.id.location);
            holder.date = row.findViewById(R.id.date_txt);
            holder.time = row.findViewById(R.id.time_txt);
            holder.describe = row.findViewById(R.id.describe);
            holder.imageView = row.findViewById(R.id.image);
            holder.id_item = row.findViewById(R.id.id_item);
            row.setTag(holder);

//             SwipeLayout swipeLayout;
//            swipeLayout = (SwipeLayout)row.findViewById(R.id.swipe_layout);

            //    swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

        }
        else {
            holder = (ViewHolder)row.getTag();
        }

        Model model = recordList.get(i);

        holder.company_name.setText(model.getcompany_name());
        holder.location.setText(model.getlocation_transfer());
        holder.date.setText(model.getdate());
        holder.time.setText(model.gettime());
        holder.describe.setText(model.getdescribe());


//        int e=model.getId();
//        String numberAsString = new Integer(e).toString();
        holder.id_item.setText(model.getId());

        if( model.getImage()!=null )
        {
            byte[] recordImage = model.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(recordImage, 0, recordImage.length);
            holder.imageView.setImageBitmap(bitmap);
        }

        return row;
    }

}
