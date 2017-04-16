package com.example.toprakegin.ilactakipdeneme1;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

        import java.util.ArrayList;
        import java.util.List;

public class DBAdapter {

    private static final String TAG = "DBAdapter"; //used for logging database version changes

    // Field Names Saatler:
    public static final String KEY_ROWID = "_id";
    public static final String KEY_ISIM = "ilac_isim";
    public static final String KEY_DOZHR = "dozhr";
    public static final String KEY_DOZMIN = "dozmin";

    // Field Names Ilaclar
    public static final String KEY_ROWID_ILAC = "_id";
    public static final String KEY_ILAC_ISIM = "ilac_ismi";
    public static final String KEY_BARKOD = "barkod";



    public static final String[] ALL_KEYS_SAATLER = new String[] {KEY_ROWID, KEY_ISIM, KEY_DOZHR, KEY_DOZMIN};

    public static final String[] ALL_KEYS_ILACLAR = new String[] {KEY_ROWID_ILAC, KEY_ILAC_ISIM, KEY_BARKOD};

    // Column Numbers for each Field Name:
    public static final int COL_ROWID = 0;
    public static final int COL_ISIM = 1;
    public static final int COL_DOZHR = 2;
    public static final int COL_DOZMIN = 3;

    public static final int COL_ROWID_ILAC = 0;
    public static final int COL_ILAC_ISIM = 1;
    public static final int COL_BARKOD = 2;

    // DataBase info:
    public static final String DATABASE_NAME = "ilacTakip";
    public static final String DATABASE_TABLE_SAATLER = "ilacSaatler";
    public static final String DATABASE_TABLE_ILACLAR = "ilaclar";
    public static final int DATABASE_VERSION = 8; // The version number must be incremented each time a change to DB structure occurs.

    //SQL statement to create database
    private static final String DATABASE_CREATE_SQL =
            "CREATE TABLE " + DATABASE_TABLE_SAATLER + " ( " + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_ISIM + " TEXT NOT NULL, "
                    + KEY_DOZHR + " INTEGER,"  + KEY_DOZMIN + " INTEGER );";

    private static final String DATABASE_CREATE_ILACLAR =
            "CREATE TABLE " + DATABASE_TABLE_ILACLAR + " ( "
                    + KEY_ROWID_ILAC + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_ILAC_ISIM + " TEXT, "
                    + KEY_BARKOD + " INTEGER );";


    private final Context context;
    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;


    public DBAdapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Open the database connection.
    public DBAdapter open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
    }

    // Add a new set of values to be inserted into the database.
    public long insertRowSaatler(String isim, int dozhr, int dozmin) {
        ContentValues initialValues = new ContentValues();

        initialValues.put(KEY_ISIM, isim);
        initialValues.put(KEY_DOZHR, dozhr);
        initialValues.put(KEY_DOZMIN, dozmin);

        // Insert the data into the database.
        return db.insert(DATABASE_TABLE_SAATLER, null, initialValues);
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRowSaatler(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DATABASE_TABLE_SAATLER, where, null) != 0;
    }

    public void deleteAllSaatler() {
        Cursor c = getAllRowsSaatler();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteRowSaatler(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    // Return all data in the database.
    public Cursor getAllRowsSaatler() {
        String where = null;
        Cursor c = 	db.query(true, DATABASE_TABLE_SAATLER, ALL_KEYS_SAATLER, where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getSabahRowsSaatler() {
        String where = "dozhr > 5 AND dozhr < 12";
        Cursor c = 	db.query(true, DATABASE_TABLE_SAATLER, ALL_KEYS_SAATLER, where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getOgleRowsSaatler() {
        String where = "dozhr > 11 AND dozhr < 18";
        Cursor c = 	db.query(true, DATABASE_TABLE_SAATLER, ALL_KEYS_SAATLER, where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getAksamRowsSaatler() {
        String where = "dozhr > 17 AND dozhr < 24";
        Cursor c = 	db.query(true, DATABASE_TABLE_SAATLER, ALL_KEYS_SAATLER, where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getGeceRowsSaatler() {
        String where = "dozhr >= 0 AND dozhr < 6";
        Cursor c = 	db.query(true, DATABASE_TABLE_SAATLER, ALL_KEYS_SAATLER, where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getAllRowsIlaclar(){
        String where = null;
        Cursor c = db.query(true, DATABASE_TABLE_ILACLAR, ALL_KEYS_ILACLAR, where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Get a specific row (by rowId)
    public Cursor getRowSaatler(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = 	db.query(true, DATABASE_TABLE_SAATLER, ALL_KEYS_SAATLER,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public long insertRowTumIlaclar(String isim, long barkod) {
        ContentValues initialValues = new ContentValues();

        initialValues.put(KEY_ILAC_ISIM, isim);
        initialValues.put(KEY_BARKOD, barkod);

        // Insert the data into the database.
        return db.insert(DATABASE_TABLE_ILACLAR, null, initialValues);
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRowIlaclar(long rowId) {
        String where = KEY_ROWID_ILAC + "=" + rowId;
        return db.delete(DATABASE_TABLE_ILACLAR, where, null) != 0;
    }

    public void deleteAllIlaclar() {
        Cursor c = getAllRowsIlaclar();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID_ILAC);
        if (c.moveToFirst()) {
            do {
                deleteRowIlaclar(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    public String barcodeToName(String barkod){
        String ilacismi;
        String mQuery = "SELECT ilac_ismi FROM ilaclar WHERE barkod = ?";
        Cursor mCur = db.rawQuery(mQuery, new String[]{barkod});
        mCur.moveToFirst();
        while ( !mCur.isAfterLast()) {
            ilacismi= mCur.getString(mCur.getColumnIndex("ilac_ismi"));
            mCur.moveToNext();
            return ilacismi;
        }
        return null;
    }

    public int alarmID(String ilacIsmi){
        int alarmid;
        String mQuery = "SELECT _id FROM ilacSaatler WHERE ilac_isim = ?";
        Cursor mCur = db.rawQuery(mQuery, new String[]{ilacIsmi});
        mCur.moveToFirst();
        while ( !mCur.isAfterLast()) {
            alarmid = mCur.getInt(mCur.getColumnIndex("_id"));
            return alarmid;
        }
        return 100;
    }

    public List<MyObject> read(String searchTerm) {

        List<MyObject> recordsList = new ArrayList<MyObject>();

        // select query
        String sql = "";
        sql += "SELECT * FROM " + DATABASE_TABLE_ILACLAR;
        sql += " WHERE " + KEY_ILAC_ISIM + " LIKE '%" + searchTerm + "%'";
        sql += " ORDER BY " + KEY_ROWID_ILAC + " DESC";
        sql += " LIMIT 0,5";

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                // int productId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(fieldProductId)));
                String objectName = cursor.getString(cursor.getColumnIndex(KEY_ILAC_ISIM));
                MyObject myObject = new MyObject(objectName);

                // add to list
                recordsList.add(myObject);

            } while (cursor.moveToNext());
        }

        // return the list of records
        return recordsList;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            // Creating the tables
            _db.execSQL(DATABASE_CREATE_SQL);
            _db.execSQL(DATABASE_CREATE_ILACLAR);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_SAATLER);
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_ILACLAR);

            // Recreate new database:
            onCreate(_db);
        }
    }


}
