package com.example.arp.start.test;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import com.example.arp.start.data.DBContract.pat_table;
import com.example.arp.start.data.table_DBHelper;

import java.util.Map;
import java.util.Set;

/**
 * Created by Arp on 2/15/2015.
 */
public class TestDB extends AndroidTestCase {
    public static final String LOG_TAG = "Home";
    public void testCreateDb() throws Throwable {

        mContext.deleteDatabase(table_DBHelper.DATABASE_NAME);
        SQLiteDatabase db = new table_DBHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();

    }


    //validation

    ContentValues gettableContentValues()
    {

        final String company_name = "faculty 1";
        final String date = "19/12/2014";
        final String eligibility_criteria = "hometown";
        final String branches = "Guwahati";
        final String salary = "20/12/2014";
        final String deadline = "30/12/2014";
        final String other_info = "11";
        ContentValues values =new ContentValues();
        values.put(pat_table.COL_COMPANY_NAME,company_name);
        values.put(pat_table.COL_DATE,date);
        values.put(pat_table.COL_ELIGIBILITY_CRITERIA,eligibility_criteria);
        values.put(pat_table.COL_BRANCHES,branches);
        values.put(pat_table.COL_SALARY,salary);
        values.put(pat_table.COL_DEADLINE,deadline);
        values.put(pat_table.COL_OTHER_INFO,other_info);
        return values;

    }

    static  public  void validateCursor(ContentValues expectedValues, Cursor valueCursor)
    {
        Set<Map.Entry<String,Object>> valueSet = expectedValues.valueSet();
        for(Map.Entry<String,Object> entry : valueSet)
        {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse(-1 == idx);
            String expectedValue=entry.getValue().toString();
            assertEquals(expectedValue , valueCursor.getString(idx));
        }

    }

    public void testInsertDb() {

        final String company_name = "faculty 1";
        final String date = "19/12/2014";
        final String eligibility_criteria = "hometown";
        final String branches = "Guwahati";
        final String salary = "20/12/2014";
        final String deadline = "30/12/2014";
        final String other_info = "11";



        SQLiteDatabase db = new table_DBHelper(this.mContext).getWritableDatabase();
        ContentValues values =gettableContentValues();



        long returnRowNum;
        returnRowNum = db.insert(pat_table.TB_NAME, null, values);

        assertTrue(returnRowNum != -1);

        Log.i(LOG_TAG, "abc" + returnRowNum);



       Cursor cursor= mContext.getContentResolver().query();

        if(cursor.moveToFirst())
        {
            validateCursor(values,cursor);
        }
        else
        {
            fail("No Values");
        }

    }





    }