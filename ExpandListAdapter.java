package com.freeoffer.app.adapter;

import java.util.ArrayList;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freeoffer.app.R;
import com.freeoffer.app.general.CommonUtilities;
import com.freeoffer.app.general.GeneralFunction;
import com.freeoffer.app.general.GlobalVar;
import com.freeoffer.app.model.Child;
import com.freeoffer.app.model.Group;

/**
 * Created by sc-android on 2/20/2016.
 */
public class ExpandListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Group> groups;

    public ExpandListAdapter(Context context, ArrayList<Group> groups) {
        this.context = context;
        this.groups = groups;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<Child> chList = groups.get(groupPosition).getItems();
        return chList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        Child child = (Child) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expand_list_child, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.country_name);
        ImageView iv = (ImageView) convertView.findViewById(R.id.flag);
        LinearLayout ll_row=(LinearLayout)convertView.findViewById(R.id.ll_row);

        String strSelectedValue= GlobalVar.getMyStringPref(context, CommonUtilities.ExpandChildSelectValue);
        if(strSelectedValue.equalsIgnoreCase(child.getName().toString())){
            ll_row.setBackgroundColor(context.getResources().getColor(R.color.gray));
        }
        else{
            ll_row.setBackgroundColor(context.getResources().getColor(R.color.white));
        }


        tv.setText(Html.fromHtml(child.getName().toString()));
        iv.setImageResource(child.getImage());
        if(child.getName().equalsIgnoreCase("LOGOUT")) {
            if (GeneralFunction.isEmptyCheck(GlobalVar.getMyStringPref(context, CommonUtilities.loginuserEmil))) {
                ll_row.setVisibility(View.INVISIBLE);
            }
            else{
                ll_row.setVisibility(View.VISIBLE);
            }
        }



        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<Child> chList = groups.get(groupPosition).getItems();
        return chList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Group group = (Group) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.expandlist_items_header, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.group_name);
        tv.setText(group.getName());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



}
