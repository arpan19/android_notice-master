package com.example.arp.start.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import  com.example.arp.start.data.DBContract.pat_table;


/**
 * Created by Arp on 2/14/2015.
 */
public class table_DBHelper extends SQLiteOpenHelper
{
    public static  final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "pat";


    public table_DBHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String createTB_Qry = "CREATE TABLE IF NOT EXISTS " + pat_table.TB_NAME + " ( " +
                pat_table._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                pat_table.COL_SERIAL_NUMBER + " TEXT NOT NULL, " +
                pat_table.COL_COMPANY_NAME + " TEXT NOT NULL, " +
                pat_table.COL_DATE + " TEXT NOT NULL, " +
                pat_table.COL_ELIGIBILITY_CRITERIA + " TEXT NOT NULL, " +
                pat_table.COL_BRANCHES + " TEXT NOT NULL, " +
                pat_table.COL_SALARY + " TEXT NOT NULL, " +
                pat_table.COL_DEADLINE + " TEXT," +
                pat_table.COL_OTHER_INFO + " TEXT, " +
                " UNIQUE (  " + pat_table.COL_COMPANY_NAME+ " ) ON CONFLICT IGNORE );";

        sqLiteDatabase.execSQL(createTB_Qry);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + pat_table.TB_NAME);
        onCreate(sqLiteDatabase);

    }
}
