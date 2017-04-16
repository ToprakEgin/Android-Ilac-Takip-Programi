package com.example.toprakegin.ilactakipdeneme1;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Calendar;
import java.util.List;

public class IlacEklemeActivity extends AppCompatActivity {
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    CustomAutoCompleteView myAutoComplete;
    ArrayAdapter<String> myAdapter;
    String[] item = new String[] {"Please search..."};

    static int DIALOG_ID = 0;

    DBAdapter myDB;

    int hour_T1T1, hour_T2T1,hour_T2T2, hour_T3T1, hour_T3T2, hour_T3T3, hour_T4T1, hour_T4T2,
            hour_T4T3, hour_T4T4;

    int minute_T1T1, minute_T2T1,minute_T2T2, minute_T3T1, minute_T3T2, minute_T3T3, minute_T4T1,
            minute_T4T2, minute_T4T3, minute_T4T4;

    //////////// Arama Çubuğu
    AutoCompleteTextView acTextView;

    //////////// Tab'ların Bitti butonları
    Button BittiTab1;
    Button BittiTab2;
    Button BittiTab3;
    Button BittiTab4;


    //////////// Tabloların zamanı ayarlama butonları
    Button ZaT1T1;

    Button ZaT2T1;
    Button ZaT2T2;

    Button ZaT3T1;
    Button ZaT3T2;
    Button ZaT3T3;

    Button ZaT4T1;
    Button ZaT4T2;
    Button ZaT4T3;
    Button ZaT4T4;
    ////////////

    //////////// Tabların TextViewleri
    TextView T1T1 ;

    TextView T2T1 ;
    TextView T2T2 ;

    TextView T3T1 ;
    TextView T3T2 ;
    TextView T3T3 ;

    TextView T4T1 ;
    TextView T4T2 ;
    TextView T4T3 ;
    TextView T4T4 ;
    ////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ilac_ekleme);
        final Activity activity = this;

        myAutoComplete = (CustomAutoCompleteView) findViewById(R.id.AutoCompleteSV);

        // add the listener so it will tries to suggest while the user types
        myAutoComplete.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this));

        // set our adapter
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
        myAutoComplete.setAdapter(myAdapter);

        FloatingActionButton BarkodFab = (FloatingActionButton) findViewById(R.id.BarkodFAB);
        BarkodFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES);
                integrator.setPrompt("İlacın Barkodunu Okutunuz");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.Tab1);
        spec.setIndicator("Günde 1 Kere");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.Tab2);
        spec.setIndicator("Günde 2 Kere");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.Tab3);
        spec.setIndicator("Günde 3 Kere");
        host.addTab(spec);

        //Tab 4
        spec = host.newTabSpec("Tab Four");
        spec.setContent(R.id.Tab4);
        spec.setIndicator("Günde 4 Kere");
        host.addTab(spec);

        showTimePickerDialog();

        openDb();                                                                                   //Aktivite oluştuğunda databaseyi açtık

        DBAtamaT4();

    }

    ///////////////////////////////////////////////////////////  AutoCompleteTextView için yazılan kod
    public String[] getItemsFromDb(String searchTerm){

        // add items on the array dynamically
        List<MyObject> products = myDB.read(searchTerm);
        int rowCount = products.size();

        String[] item = new String[rowCount];
        int x = 0;

        for (MyObject record : products) {

            item[x] = record.objectName;
            x++;
        }

        return item;
    }

    /////////////////////////////////////////////////////////////Barkod Okuma İçin Yazılan Kod
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        acTextView = (AutoCompleteTextView) findViewById(R.id.AutoCompleteSV);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String results = result.getContents().toString();
        String dbresults;
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "Barkod okuma işlemini iptal ettiniz", Toast.LENGTH_SHORT).show();
            }
            else {
                dbresults = myDB.barcodeToName(results);
                acTextView.setText(dbresults);
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /////////////////////////////////////////////////////////////Time Picker İçin Yazılan Kod

    @Override
    public Dialog onCreateDialog(int id){
        if (DIALOG_ID == 11)
            return new TimePickerDialog(IlacEklemeActivity.this, TimePickerListener_T1T1, hour_T1T1, minute_T1T1, false);
        else if (DIALOG_ID == 21)
            return new TimePickerDialog(IlacEklemeActivity.this, TimePickerListener_T2T1, hour_T2T1, minute_T2T1, false);
        else if (DIALOG_ID == 22)
            return new TimePickerDialog(IlacEklemeActivity.this, TimePickerListener_T2T2, hour_T2T2, minute_T2T2, false);
        else if (DIALOG_ID == 31)
            return new TimePickerDialog(IlacEklemeActivity.this, TimePickerListener_T3T1, hour_T3T1, minute_T3T1, false);
        else if (DIALOG_ID == 32)
            return new TimePickerDialog(IlacEklemeActivity.this, TimePickerListener_T3T2, hour_T3T2, minute_T3T2, false);
        else if (DIALOG_ID == 33)
            return new TimePickerDialog(IlacEklemeActivity.this, TimePickerListener_T3T3, hour_T3T3, minute_T3T3, false);
        else if (DIALOG_ID == 41)
            return new TimePickerDialog(IlacEklemeActivity.this, TimePickerListener_T4T1, hour_T4T1, minute_T4T1, false);
        else if (DIALOG_ID == 42)
            return new TimePickerDialog(IlacEklemeActivity.this, TimePickerListener_T4T2, hour_T4T2, minute_T4T2, false);
        else if (DIALOG_ID == 43)
            return new TimePickerDialog(IlacEklemeActivity.this, TimePickerListener_T4T3, hour_T4T3, minute_T4T3, false);
        else if (DIALOG_ID == 44)
            return new TimePickerDialog(IlacEklemeActivity.this, TimePickerListener_T4T4, hour_T4T4, minute_T4T4, false);

        return null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////TAB1
    protected TimePickerDialog.OnTimeSetListener TimePickerListener_T1T1=
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    T1T1 = (TextView) findViewById(R.id.Tab1Tv1);
                    hour_T1T1 = hourOfDay;
                    minute_T1T1 = minute;
                    T1T1.setText(String.format("1.   %02d:%02d ", hour_T1T1, minute_T1T1));
                }
            };

    ////////////////////////////////////////////////////////////////////////////////////////////////TAB2
    protected TimePickerDialog.OnTimeSetListener TimePickerListener_T2T1=
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    T2T1 = (TextView) findViewById(R.id.Tab2Tv1);
                    hour_T2T1 = hourOfDay;
                    minute_T2T1 = minute;
                    T2T1.setText(String.format("1.   %02d:%02d ", hour_T2T1, minute_T2T1));
                }
            };
    protected TimePickerDialog.OnTimeSetListener TimePickerListener_T2T2=
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    T2T2 = (TextView) findViewById(R.id.Tab2Tv2);
                    hour_T2T2 = hourOfDay;
                    minute_T2T2 = minute;
                    T2T2.setText(String.format("2.   %02d:%02d ", hour_T2T2, minute_T2T2));
                }
            };

    ////////////////////////////////////////////////////////////////////////////////////////////////TAB3
    protected TimePickerDialog.OnTimeSetListener TimePickerListener_T3T1=
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    T3T1 = (TextView) findViewById(R.id.Tab3Tv1);
                    hour_T3T1 = hourOfDay;
                    minute_T3T1 = minute;
                    T3T1.setText(String.format("1.   %02d:%02d ", hour_T3T1, minute_T3T1));
                }
            };
    protected TimePickerDialog.OnTimeSetListener TimePickerListener_T3T2=
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    T3T2 = (TextView) findViewById(R.id.Tab3Tv2);
                    hour_T3T2 = hourOfDay;
                    minute_T3T2 = minute;
                    T3T2.setText(String.format("2.   %02d:%02d ", hour_T3T2, minute_T3T2));
                }
            };
    protected TimePickerDialog.OnTimeSetListener TimePickerListener_T3T3=
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    T3T3 = (TextView) findViewById(R.id.Tab3Tv3);
                    hour_T3T3 = hourOfDay;
                    minute_T3T3 = minute;
                    T3T3.setText(String.format("3.   %02d:%02d ", hour_T3T3, minute_T3T3));
                }
            };

    ////////////////////////////////////////////////////////////////////////////////////////////////TAB4
    protected TimePickerDialog.OnTimeSetListener TimePickerListener_T4T1=
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    T4T1 = (TextView) findViewById(R.id.Tab4Tv1);
                    hour_T4T1 = hourOfDay;
                    minute_T4T1 = minute;
                    T4T1.setText(String.format("1.   %02d:%02d ", hour_T4T1, minute_T4T1));
                }
            };
    protected TimePickerDialog.OnTimeSetListener TimePickerListener_T4T2=
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    T4T2 = (TextView) findViewById(R.id.Tab4Tv2);
                    hour_T4T2 = hourOfDay;
                    minute_T4T2 = minute;
                    T4T2.setText(String.format("2.   %02d:%02d ", hour_T4T2, minute_T4T2));
                }
            };
    protected TimePickerDialog.OnTimeSetListener TimePickerListener_T4T3=
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    T4T3 = (TextView) findViewById(R.id.Tab4Tv3);
                    hour_T4T3 = hourOfDay;
                    minute_T4T3 = minute;
                    T4T3.setText(String.format("3.   %02d:%02d ", hour_T4T3, minute_T4T3));
                }
            };
    protected TimePickerDialog.OnTimeSetListener TimePickerListener_T4T4=
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    T4T4 = (TextView) findViewById(R.id.Tab4Tv4);
                    hour_T4T4 = hourOfDay;
                    minute_T4T4 = minute;
                    T4T4.setText(String.format("4.   %02d:%02d ", hour_T4T4, minute_T4T4));
                }
            };
    ////////////////////////////////////////////////////////////////////////////////////////////////TAB SET TEXT İŞİ BİTER
    public void showTimePickerDialog(){
        ZaT1T1 = (Button) findViewById(R.id.ZaT1T1);

        ZaT2T1 = (Button) findViewById(R.id.ZaT2T1);
        ZaT2T2 = (Button) findViewById(R.id.ZaT2T2);

        ZaT3T1 = (Button) findViewById(R.id.ZaT3T1);
        ZaT3T2 = (Button) findViewById(R.id.ZaT3T2);
        ZaT3T3 = (Button) findViewById(R.id.ZaT3T3);

        ZaT4T1 = (Button) findViewById(R.id.ZaT4T1);
        ZaT4T2 = (Button) findViewById(R.id.ZaT4T2);
        ZaT4T3 = (Button) findViewById(R.id.ZaT4T3);
        ZaT4T4 = (Button) findViewById(R.id.ZaT4T4);

                                                                //////////TAB1
        ZaT1T1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DIALOG_ID = 11;
                showDialog(DIALOG_ID);
            }
        });
                                                                //////////TAB2
        ZaT2T1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DIALOG_ID = 21;
                showDialog(DIALOG_ID);
            }
        });
        ZaT2T2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DIALOG_ID = 22;
                showDialog(DIALOG_ID);
            }
        });
                                                                //////////TAB3
        ZaT3T1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DIALOG_ID = 31;
                showDialog(DIALOG_ID);
            }
        });
        ZaT3T2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DIALOG_ID = 32;
                showDialog(DIALOG_ID);
            }
        });
        ZaT3T3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DIALOG_ID = 33;
                showDialog(DIALOG_ID);
            }
        });
                                                                ////////////TAB4
        ZaT4T1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DIALOG_ID = 41;
                showDialog(DIALOG_ID);
            }
        });
        ZaT4T2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DIALOG_ID = 42;
                showDialog(DIALOG_ID);
            }
        });
        ZaT4T3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DIALOG_ID = 43;
                showDialog(DIALOG_ID);
            }
        });
        ZaT4T4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DIALOG_ID = 44;
                showDialog(DIALOG_ID);
            }
        });
    }
    ///////////////////////////////////////////////////////////////Time Picker bitiş

    /////////////////////////////////////////////////////////////Database Atamaları Kodu
    public void DBAtamaT4(){
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        final Intent intent = new Intent(this, Alarm_Receiver.class);
        final Calendar calendar = Calendar.getInstance();

        acTextView = (AutoCompleteTextView) findViewById(R.id.AutoCompleteSV);
        BittiTab4 = (Button) findViewById(R.id.BittiTab4);
        BittiTab4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (acTextView.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Lütfen Bir İlaç İsmi Giriniz!", Toast.LENGTH_SHORT).show();
                }
                else {
                    myDB.insertRowSaatler(acTextView.getText().toString(), hour_T4T1, minute_T4T1);
                    myDB.insertRowSaatler(acTextView.getText().toString(), hour_T4T2, minute_T4T2);
                    myDB.insertRowSaatler(acTextView.getText().toString(), hour_T4T3, minute_T4T3);
                    myDB.insertRowSaatler(acTextView.getText().toString(), hour_T4T4, minute_T4T4);
                    int Alarm_ID = myDB.alarmID(acTextView.getText().toString());

                    calendar.set(Calendar.HOUR_OF_DAY, hour_T4T1);
                    calendar.set(Calendar.MINUTE, minute_T4T1);

                    pendingIntent = PendingIntent.getBroadcast(IlacEklemeActivity.this, Alarm_ID, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    Alarm_ID += 1;
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//////////////////////////////////////////////////////////////
                    calendar.set(Calendar.HOUR_OF_DAY, hour_T4T2);
                    calendar.set(Calendar.MINUTE, minute_T4T2);

                    pendingIntent = PendingIntent.getBroadcast(IlacEklemeActivity.this, Alarm_ID, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    Alarm_ID += 1;
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//////////////////////////////////////////////////////////////
                    calendar.set(Calendar.HOUR_OF_DAY, hour_T4T3);
                    calendar.set(Calendar.MINUTE, minute_T4T3);

                    pendingIntent = PendingIntent.getBroadcast(IlacEklemeActivity.this, Alarm_ID, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    Alarm_ID += 1;
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//////////////////////////////////////////////////////////////
                    calendar.set(Calendar.HOUR_OF_DAY, hour_T4T4);
                    calendar.set(Calendar.MINUTE, minute_T4T4);

                    pendingIntent = PendingIntent.getBroadcast(IlacEklemeActivity.this, Alarm_ID, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                    IlacEklemeActivity.this.finish();
                }
            }
        });

        BittiTab3 = (Button) findViewById(R.id.BittiTab3);
        BittiTab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (acTextView.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Lütfen Bir İlaç İsmi Giriniz!", Toast.LENGTH_SHORT).show();
                }
                else {
                    myDB.insertRowSaatler(acTextView.getText().toString(), hour_T3T1, minute_T3T1);
                    myDB.insertRowSaatler(acTextView.getText().toString(), hour_T3T2, minute_T3T2);
                    myDB.insertRowSaatler(acTextView.getText().toString(), hour_T3T3, minute_T3T3);
                    int Alarm_ID = myDB.alarmID(acTextView.getText().toString());

                    calendar.set(Calendar.HOUR_OF_DAY, hour_T3T1);
                    calendar.set(Calendar.MINUTE, minute_T3T1);

                    pendingIntent = PendingIntent.getBroadcast(IlacEklemeActivity.this, Alarm_ID, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    Alarm_ID += 1;
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
///////////////////////////////////////////////////////////////
                    calendar.set(Calendar.HOUR_OF_DAY, hour_T3T2);
                    calendar.set(Calendar.MINUTE, minute_T3T2);

                    pendingIntent = PendingIntent.getBroadcast(IlacEklemeActivity.this, Alarm_ID, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    Alarm_ID += 1;
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
///////////////////////////////////////////////////////////////
                    calendar.set(Calendar.HOUR_OF_DAY, hour_T3T3);
                    calendar.set(Calendar.MINUTE, minute_T3T3);

                    pendingIntent = PendingIntent.getBroadcast(IlacEklemeActivity.this, Alarm_ID, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                    IlacEklemeActivity.this.finish();
                }
            }
        });

        BittiTab2 = (Button) findViewById(R.id.BittiTab2);
        BittiTab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (acTextView.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Lütfen Bir İlaç İsmi Giriniz!", Toast.LENGTH_SHORT).show();
                }
                else {
                    myDB.insertRowSaatler(acTextView.getText().toString(), hour_T2T1, minute_T2T1);
                    myDB.insertRowSaatler(acTextView.getText().toString(), hour_T2T2, minute_T2T2);
                    int Alarm_ID = myDB.alarmID(acTextView.getText().toString());

                    calendar.set(Calendar.HOUR_OF_DAY, hour_T2T1);
                    calendar.set(Calendar.MINUTE, minute_T2T1);

                    pendingIntent = PendingIntent.getBroadcast(IlacEklemeActivity.this, Alarm_ID, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    Alarm_ID += 1;
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    /////////////////////////////
                    calendar.set(Calendar.HOUR_OF_DAY, hour_T2T2);
                    calendar.set(Calendar.MINUTE, minute_T2T2);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                    pendingIntent = PendingIntent.getBroadcast(IlacEklemeActivity.this, Alarm_ID, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                    IlacEklemeActivity.this.finish();
                }
            }
        });

        BittiTab1 = (Button) findViewById(R.id.BittiTab1);
        BittiTab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (acTextView.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Lütfen Bir İlaç İsmi Giriniz!", Toast.LENGTH_SHORT).show();
                }
                else {
                    myDB.insertRowSaatler(acTextView.getText().toString(), hour_T1T1, minute_T1T1);
                    int Alarm_ID = myDB.alarmID(acTextView.getText().toString());

                    calendar.set(Calendar.HOUR_OF_DAY, hour_T1T1);
                    calendar.set(Calendar.MINUTE, minute_T1T1);

                    intent.putExtra("extra", "alarm on");

                    pendingIntent = PendingIntent.getBroadcast(IlacEklemeActivity.this, Alarm_ID, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                    IlacEklemeActivity.this.finish();
                }
            }
        });
     }

    private void openDb(){
        myDB = new DBAdapter(this);
        myDB.open();
    }
    /////////////////////////////////////////////////////////////Database Atamaları Bitiş
}
