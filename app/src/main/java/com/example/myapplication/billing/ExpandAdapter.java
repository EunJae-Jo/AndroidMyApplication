package com.example.myapplication.billing;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;

import java.util.ArrayList;

/**
 * Created by JSY on 2016-02-04.
 */
public class ExpandAdapter extends BaseExpandableListAdapter {
    private Context context;
    private int groupLayout = 0;
    private int chlidLayout = 0;
    private ArrayList<BillingDataGroup> dataList;
    private LayoutInflater myinf = null;
    private String[] header = {"Month","Step","Usage(kWh)","Billing(₩)"};
    private TableLayout[] tableLayout = null;
    public ExpandAdapter(Context context,int groupLay,int chlidLay,ArrayList<BillingDataGroup> dataList, TableLayout[] tableLayout){
        this.dataList = dataList;
        this.groupLayout = groupLay;
        this.chlidLayout = chlidLay;
        this.context = context;
        this.myinf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.tableLayout = tableLayout;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if(convertView == null){
            convertView = myinf.inflate(this.groupLayout, parent, false);
        }
        TextView groupName = (TextView)convertView.findViewById(R.id.header_title);
        groupName.setText(dataList.get(groupPosition).groupName);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if(convertView == null){
            convertView = myinf.inflate(this.chlidLayout, parent, false);
        }
        Log.d("TableLayout",groupPosition +"   "+childPosition);
        TableLayout tableLayout = (TableLayout)convertView.findViewById(R.id.tableLayout);

        while(tableLayout.getChildCount() > 1)
        {
            tableLayout.removeView((TableRow)tableLayout.getChildAt(1));
        }
        String[] temp = dataList.get(groupPosition).data;


        for(int i=0;i<temp.length;i++)
        {
            String[] data = temp[i].split(",");
            TableRow tableRow = new TableRow(tableLayout.getContext());
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            for(int j=0;j<data.length;j++)
            {
                TextView textView = new TextView(tableLayout.getContext());
                textView.setText(data[j]);
                textView.setGravity(Gravity.CENTER);
                //textView.setBackground(ContextCompat.getDrawable(convertView.getContext(),R.drawable.table_inside));
                tableRow.addView(textView);
            }
            tableLayout.addView(tableRow);
        }

        return convertView;
    }
    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return dataList.get(groupPosition).data;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        return 1;
    }

    @Override
    public BillingDataGroup getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return dataList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return dataList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }

}
