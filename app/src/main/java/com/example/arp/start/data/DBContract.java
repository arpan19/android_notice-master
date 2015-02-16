package com.example.arp.start.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;


/**
 * Created by Arp on 2/14/2015.
 */
public class DBContract
{
    public static final String Content_Auth = "com.example.arp.start";

    public static final Uri Base_Content_Uri = Uri.parse("content://"+Content_Auth);

    public static final String Path_information = "information";

    public static final class pat_table implements BaseColumns
    {

        public static final Uri Content_Uri = Base_Content_Uri.buildUpon().appendPath(Path_information).build();

        public static final String Content_Type_Dir = "vnd.android.cursor.dir/"+Content_Auth+"/"+Path_information;
        public static final String Content_Type_Item = "vnd.android.cursor.item/"+Content_Auth+"/"+Path_information;

        public static final String TB_NAME = "information";
        public static final String COL_SERIAL_NUMBER = "serial_no";
        public static final String COL_COMPANY_NAME = "company_name";//text not null
        public static final String COL_DATE = "date";//text
        public static final String COL_ELIGIBILITY_CRITERIA = "eligibility_criteria";//text not null
        public static final String COL_BRANCHES = "branches";//text not null
        public static final String COL_SALARY = "salary";//text not null
        public static final String COL_DEADLINE= "deadline";//text not null
        public static final String COL_OTHER_INFO = "other_info";//text not null


        public static Uri buildTableFromId(long _id)
        {
            return ContentUris.withAppendedId(Content_Uri, _id);
        }

        public static Uri buildTableWithDate(String exitDate)
        {
            return Content_Uri.buildUpon().appendPath(exitDate).build();
        }

        public static String getExitDateFromUri(Uri uri)
        {
            return uri.getPathSegments().get(1);
        }

    }

}
