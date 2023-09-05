package com.example.myapplication.setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class CustomerListAdapter extends BaseAdapter {
    Context context;
    ArrayList<CustomerORM> itemArrayList;
    ViewHolder viewholder;

    class ViewHolder{
        TextView textView_number;
        TextView textView_name;
        RadioButton radioButton;
        TextView textView_ok;
    }
    public CustomerListAdapter(Context context, ArrayList<CustomerORM> list_itemArrayList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.setting_customer_item,null);

            viewholder = new ViewHolder();

            viewholder.textView_number = (TextView)convertView.findViewById(R.id.textView_number);
            viewholder.textView_name = (TextView)convertView.findViewById(R.id.textView_name);
            viewholder.radioButton = (RadioButton)convertView.findViewById(R.id.radioButton);
            viewholder.textView_ok = (TextView)convertView.findViewById(R.id.textView_ok);
            convertView.setTag(viewholder);
        }else{
            viewholder = (ViewHolder)convertView.getTag();
        }
        viewholder.textView_number.setText(String.valueOf(itemArrayList.get(i).getCustomerNumber()));
        viewholder.textView_name.setText(itemArrayList.get(i).getCustomerName());
        if(itemArrayList.get(i).isRepresentation())
            viewholder.radioButton.setChecked(true);
        else
            viewholder.radioButton.setChecked(false);
        viewholder.textView_ok.setText(itemArrayList.get(i).getIsOk());

        return convertView;
    }
}
