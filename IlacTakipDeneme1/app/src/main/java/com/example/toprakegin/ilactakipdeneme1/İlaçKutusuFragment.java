package com.example.toprakegin.ilactakipdeneme1;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class İlaçKutusuFragment extends Fragment {

    public final static String ILAC_FRAGMENT_TO_LOAD_EXTRA = "com.example.toprakegin.ilactakipdeneme1.Fragment_To_Load";
    public enum FragmentToLaunch{SABAH, OGLE, AKSAM, GECE}

    ImageButton sabahButton, aksamButton, ogleButton, geceButton;
    TextView txtView;

    public İlaçKutusuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ilac_kutusu, container, false);

        //      En alta tarihi yazdırdım
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c.getTime());
        txtView = (TextView) rootView.findViewById(R.id.ilac_kutusu_tarih);
        txtView.setText(formattedDate);
        txtView.setGravity(Gravity.CENTER);
        txtView.setTextSize(20);


        //      Buttonları gerektiği yerlere atıyorum
        sabahButton = (ImageButton) rootView.findViewById(R.id.ButtonSabah);
        ogleButton = (ImageButton) rootView.findViewById(R.id.ButtonOgle);
        aksamButton = (ImageButton) rootView.findViewById(R.id.ButtonAksam);
        geceButton = (ImageButton) rootView.findViewById(R.id.ButtonGece);

        //      Buttonlara ne yapması gerektiğini söylüyorum
        sabahButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View s){
                Intent intent = new Intent(getActivity().getApplication(), SabahActivity.class);
               //intent.putExtra(ILAC_FRAGMENT_TO_LOAD_EXTRA, FragmentToLaunch.SABAH);                  //Önceden Fragment oldukları için varlar belki tekrardan kullanırım
                getActivity().startActivity(intent);
            }
        });

        ogleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View o){
                Intent intent = new Intent(getActivity().getApplication(), OgleActivity.class);
               //intent.putExtra(ILAC_FRAGMENT_TO_LOAD_EXTRA, FragmentToLaunch.OGLE);
                getActivity().startActivity(intent);
            }
        });

        aksamButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View a){
                Intent intent = new Intent(getActivity().getApplication(), AksamActivity.class);
                //intent.putExtra(ILAC_FRAGMENT_TO_LOAD_EXTRA, FragmentToLaunch.AKSAM);
                getActivity().startActivity(intent);
            }
        });

        geceButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View g){
                Intent intent = new Intent(getActivity().getApplication(), GeceActivity.class);
                //intent.putExtra(ILAC_FRAGMENT_TO_LOAD_EXTRA, FragmentToLaunch.GECE);
                getActivity().startActivity(intent);
            }
        });
        //      Buttonlar şu an gerekeni yapmalı

        // Inflate the layout for this fragment

        return rootView;
    }

}
