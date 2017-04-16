package com.example.toprakegin.ilactakipdeneme1;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;

/**
 * Created by Toprak Egin on 10.12.2016.
 */

public class CustomAutoCompleteTextChangedListener implements TextWatcher {
    public static final String TAG = "CustomAutoCompleteTextChangedListener.java";
    Context context;

    public CustomAutoCompleteTextChangedListener(Context context){
        this.context = context;
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence userInput, int start, int before, int count) {
        IlacEklemeActivity ilacEklemeActivity = ((IlacEklemeActivity) context);

        // query the database based on the user input
        ilacEklemeActivity.item = ilacEklemeActivity.getItemsFromDb(userInput.toString());

        // update the adapater
        ilacEklemeActivity.myAdapter.notifyDataSetChanged();
        ilacEklemeActivity.myAdapter = new ArrayAdapter<String>(ilacEklemeActivity, android.R.layout.simple_dropdown_item_1line, ilacEklemeActivity.item);
        ilacEklemeActivity.myAutoComplete.setAdapter(ilacEklemeActivity.myAdapter);

    }
}
