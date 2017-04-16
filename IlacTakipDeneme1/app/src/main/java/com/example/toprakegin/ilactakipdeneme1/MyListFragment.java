package com.example.toprakegin.ilactakipdeneme1;

import static com.example.toprakegin.ilactakipdeneme1.Constant.COLUMN_ILAC_ISMI;
import static com.example.toprakegin.ilactakipdeneme1.Constant.COLUMN_BARKOD;


import android.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import java.lang.reflect.Array;

/* public class MyListFragment extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_list_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(getActivity(),
                R.array.Ilaclar, android.R.layout.simple_list_item_1);
        setListAdapter(adapter1);
    }
} */

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 *
 * @author Paresh N. Mayani
 */
public class MyListFragment extends BaseAdapter {
    public ArrayList<HashMap> list;
    Activity activity;

    public MyListFragment(Activity activity, ArrayList<HashMap> list) {
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    private class ViewHolder {
        TextView txtFirst;
        TextView txtSecond;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.my_list_fragment, null);
            holder = new ViewHolder();
            holder.txtFirst = (TextView) convertView.findViewById(R.id.FirstText);
            holder.txtSecond = (TextView) convertView.findViewById(R.id.SecondText);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap map = list.get(position);
        holder.txtFirst.setText(map.get(COLUMN_ILAC_ISMI).toString());
        holder.txtSecond.setText(map.get(COLUMN_BARKOD).toString());

        return convertView;
    }

}