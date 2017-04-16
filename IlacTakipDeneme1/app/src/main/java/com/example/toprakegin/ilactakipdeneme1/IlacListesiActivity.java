package com.example.toprakegin.ilactakipdeneme1;

import static com.example.toprakegin.ilactakipdeneme1.Constant.COLUMN_BARKOD;
import static com.example.toprakegin.ilactakipdeneme1.Constant.COLUMN_ILAC_ISMI;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class IlacListesiActivity extends AppCompatActivity {
    DBAdapter myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilac_listesi);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.RefreshTumIlaclarfab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                myDb.deleteAllIlaclar();
                populateDB();
                Toast.makeText(getBaseContext(), "İlaç Verileri Veritabanına Girildi!", Toast.LENGTH_SHORT).show();
                populateList();
            }
        });

        openDb();
        populateList();
    }

    private void populateDB() {
        myDb.insertRowTumIlaclar("ARVELES 25 mg", 8699832090055L);
        myDb.insertRowTumIlaclar("AUGMENTIN BID 1000 mg", 8699522095711L);
        myDb.insertRowTumIlaclar("BELOC ZOK 50 mg", 8699786030367L);
        myDb.insertRowTumIlaclar("BENEXOL B12", 8699546095100L);
        myDb.insertRowTumIlaclar("COLDAWAY COLD & FLU", 8699514093251L);
        myDb.insertRowTumIlaclar("CALPOL 120 mg", 8699522705009L);
        myDb.insertRowTumIlaclar("DIAFORMIN 1000 mg", 8699543090092L);
        myDb.insertRowTumIlaclar("DRAMAMINE 50 mg", 8699543010045L);
        myDb.insertRowTumIlaclar("ENDOL 25 mg", 8699525150585L);
        myDb.insertRowTumIlaclar("ETOL FORT 400 mg", 8699540091115L);
        myDb.insertRowTumIlaclar("FOLBIOL 5 mg", 8699508010509L);
        myDb.insertRowTumIlaclar("FURACIN % 0.2", 8699502380103L);
        myDb.insertRowTumIlaclar("GENKORT 10 mg", 8699783010249L);
        myDb.insertRowTumIlaclar("GAMAFLEX 400 mg", 8699514090465L);
        myDb.insertRowTumIlaclar("HAMETAN 30 gr", 8699514385721L);
        myDb.insertRowTumIlaclar("HITRIZIN 10 mg", 8699525092373L);
        myDb.insertRowTumIlaclar("INSIDON 50 mg", 8699504120042L);
        myDb.insertRowTumIlaclar("ISOPTIN 40 mg", 8699548090806L);
        myDb.insertRowTumIlaclar("JETOSEL 2 ml", 8699788751420L);
        myDb.insertRowTumIlaclar("JELIGRA 100 mg", 8680955340028L);
        myDb.insertRowTumIlaclar("KAPRIL 25 mg", 8699541010207L);
        myDb.insertRowTumIlaclar("KLOROBEN Gargara", 8699580640069L);
        myDb.insertRowTumIlaclar("LUSTRAL 50 mg", 8699532095473L);
        myDb.insertRowTumIlaclar("LANSOR 30 mg", 8699536160085L);
        myDb.insertRowTumIlaclar("MAJEZIK 100 mg", 8699536090115L);
        myDb.insertRowTumIlaclar("METPAMID 10 mg", 8699506012055L);
        myDb.insertRowTumIlaclar("MINOSET PLUS", 8699546015610L);
        myDb.insertRowTumIlaclar("NEURONTIN 600 mg", 8699532095527L);
        myDb.insertRowTumIlaclar("NERVIUM 5 mg", 8699511010145L);
        myDb.insertRowTumIlaclar("OSEFLU 75 mg", 8697932150129L);
        myDb.insertRowTumIlaclar("ORNISID 250 mg", 8699514092315L);
        myDb.insertRowTumIlaclar("PROZAC 20 mg", 8699673150116L);
        myDb.insertRowTumIlaclar("PROGESTAN 200 mg", 8699828190394L);
        myDb.insertRowTumIlaclar("RIVOTRIL 2 mg", 8699505010670L);
        myDb.insertRowTumIlaclar("RENNIE 680 mg", 8699546085767L);
        myDb.insertRowTumIlaclar("SUDAFED 30 mg", 8699522571253L);
        myDb.insertRowTumIlaclar("SALOFALK 500 mg", 8699543040073L);
        myDb.insertRowTumIlaclar("THERAFLU FORTE", 8699504090208L);
        myDb.insertRowTumIlaclar("TRAVOGEN 30 gr", 8697529350246L);
        myDb.insertRowTumIlaclar("ULCURAN 50 mg", 8699518750402L);
        myDb.insertRowTumIlaclar("WINCEF 200 mg", 8697927090188L);
        myDb.insertRowTumIlaclar("WELRIS 2 mg", 8697936092227L);
        myDb.insertRowTumIlaclar("XENICAL 120 mg", 8699505152981L);
        myDb.insertRowTumIlaclar("XOLAIR 150 mg", 8699504270020L);
        myDb.insertRowTumIlaclar("VENTOLIN Inhaler 100 mcg", 8699522521456L);
        myDb.insertRowTumIlaclar("VERRUTOL 15 gr", 8699561650032L);
        myDb.insertRowTumIlaclar("YOMESAN 500 mg", 8699546011247L);
        myDb.insertRowTumIlaclar("YONDELIS 1 mg", 8699593775123L);
        myDb.insertRowTumIlaclar("ZYBAN 150 mg", 8699522036073L);
        myDb.insertRowTumIlaclar("ZOLAX 200 mg", 8699536150116L);
    }


    public void populateList() {
        Cursor cursor = myDb.getAllRowsIlaclar();
        String[] fromFieldNames = new String[] {DBAdapter.KEY_ILAC_ISIM, DBAdapter.KEY_BARKOD};
        int[] toViewsIDs = new int[] {R.id.tvIlacIsmi, R.id.tvIlacSaati};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.fragment_ilac_kutusu_list,
                cursor, fromFieldNames, toViewsIDs, 0);
        ListView myList = (ListView) findViewById(R.id.listviewtumilaclar);
        myList.setAdapter(myCursorAdapter);

    }

    private void openDb(){
        myDb = new DBAdapter(this);
        myDb.open();
    }
}