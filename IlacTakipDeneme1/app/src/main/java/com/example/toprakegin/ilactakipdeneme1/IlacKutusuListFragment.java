package com.example.toprakegin.ilactakipdeneme1;

import static com.example.toprakegin.ilactakipdeneme1.Constant.COLUMN_ILAC_ISMI_KUTU;
import static com.example.toprakegin.ilactakipdeneme1.Constant.COLUMN_ILAC_SAATI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.widget.TextView;

/**
 *
 * @author Paresh N. Mayani
 */
public class IlacKutusuListFragment extends BaseAdapter {
    public ArrayList<HashMap> list;
    Activity activity;

    public IlacKutusuListFragment(Activity activity, ArrayList<HashMap> list) {
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
        TextView ilacIsmi;
        TextView ilacSaati;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_ilac_kutusu_list, null);
            holder = new ViewHolder();
            holder.ilacIsmi = (TextView) convertView.findViewById(R.id.tvIlacIsmi);
            holder.ilacSaati = (TextView) convertView.findViewById(R.id.tvIlacSaati);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap map = list.get(position);
        holder.ilacIsmi.setText(map.get(COLUMN_ILAC_ISMI_KUTU).toString());
        holder.ilacSaati.setText(map.get(COLUMN_ILAC_SAATI).toString());

        return convertView;
    }

}