package com.example.myapplication.setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class UseGoalListAdapter extends BaseAdapter {
    Context context;
    ArrayList<UseGoalORM> itemArrayList;
    ViewHolder viewholder;

    class ViewHolder{
        TextView textView_title;
        TextView textView_content;
        TextView textView_date;
    }
    public UseGoalListAdapter(Context context, ArrayList<UseGoalORM> list_itemArrayList) {
        this.context = context;
        this.itemArrayList = list_itemArrayList;
    }

    @Override
    public int getCount() {
        return this.itemArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.setting_usegoal_item,null);

            viewholder = new ViewHolder();

            viewholder.textView_title = (TextView)convertView.findViewById(R.id.textView_title);
            viewholder.textView_content = (TextView)convertView.findViewById(R.id.textView_content);
            viewholder.textView_date = (TextView)convertView.findViewById(R.id.textView_date);
            convertView.setTag(viewholder);
        }else{
            viewholder = (ViewHolder)convertView.getTag();
        }
        viewholder.textView_title.setText(itemArrayList.get(i).getTitle());
        viewholder.textView_content.setText(itemArrayList.get(i).getContent());
        viewholder.textView_date.setText(itemArrayList.get(i).getDate());

        return convertView;
    }
}
