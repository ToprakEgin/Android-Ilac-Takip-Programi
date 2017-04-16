package com.example.toprakegin.ilactakipdeneme1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class SabahActivity extends AppCompatActivity {
    DBAdapter myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sabah);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.IlacEklemeFABSabah);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplication(), IlacEklemeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        openDb();
        populateList();
    }

    public void populateList() {
        Cursor cursor = myDb.getSabahRowsSaatler();
        String[] fromFieldNames = new String[] {DBAdapter.KEY_ISIM, DBAdapter.KEY_DOZHR, DBAdapter.KEY_DOZMIN};
        int[] toViewsIDs = new int[] {R.id.IPIlacınAdi, R.id.IPSaat, R.id.IPDakika};
        SimpleCursorAdapter  myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.ilac_plani_listeleme,
                cursor, fromFieldNames, toViewsIDs, 0);
        ListView myList = (ListView) findViewById(R.id.listviewsabah);
        myList.setAdapter(myCursorAdapter);

    }

    private void openDb(){
        myDb = new DBAdapter(this);
        myDb.open();
    }

}
