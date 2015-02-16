package com.example.arp.start.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import  com.example.arp.start.data.DBContract.pat_table;

/**
 * Created by Arp on 2/15/2015.
 */
public class dataProvider extends ContentProvider


{

    public static final int addition = 100;


    private table_DBHelper db;

    private static final UriMatcher mUriMatcher = buildUriMatcher();  //could not understand the function

    public static UriMatcher buildUriMatcher()
    {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DBContract.Content_Auth;

        matcher.addURI(authority, DBContract.Path_information, addition);


        return matcher;
    }

    @Override
    public boolean onCreate() {
        db = new table_DBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {

        Cursor retCur;


                retCur = db.getReadableDatabase().query(
                        pat_table.TB_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
            retCur.setNotificationUri(getContext().getContentResolver(),uri);

        return retCur;
    }

    @Override
   public String getType(Uri uri)
    {
        final int match =mUriMatcher.match(uri);
        switch(match)
        {
            case addition:
            {
                return pat_table.Content_Type_Dir;


            }

            default:
                throw new UnsupportedOperationException("unknown uri"+uri);
        }
    }



    @Override
    public Uri insert(Uri uri, ContentValues contentValues)
    {


        Uri retUri;
        final SQLiteDatabase db = new table_DBHelper(getContext()).getWritableDatabase();
        final int match = mUriMatcher.match(uri);
        switch (match) {
            case (addition): {
                long _id = db.insert(DBContract.pat_table.TB_NAME, null, contentValues);
                if (_id > 0)
                    retUri = DBContract.pat_table.buildTableFromId(_id);
                else
                    throw new SQLException("Failed to insert row to " + uri);
                break;

            }

            default:

                throw new UnsupportedOperationException("Unknown Uri:" + uri);





        }
        getContext().getContentResolver().notifyChange(uri, null);
        return retUri;
    }


    @Override
     public  int delete (Uri uri,String selection,String[] strings)
    {
        return 0;
    }

    @Override
    public  int update (Uri uri,ContentValues contentValues,String s,String[] strings)
    {
        return 0 ;
    }


    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = new table_DBHelper(getContext()).getWritableDatabase();
        final int match = mUriMatcher.match(uri);

        switch (match) {
            case addition: {
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(DBContract.pat_table.TB_NAME, null,value);
                        if (-1 != _id)
                        {
                            returnCount++;
                        }

                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                getContext().getContentResolver().notifyChange(uri, null);

            }
            default:
                return super.bulkInsert(uri, values);
        }
    }
}


