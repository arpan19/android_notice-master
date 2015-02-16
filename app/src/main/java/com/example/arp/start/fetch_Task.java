package com.example.arp.start;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.arp.start.data.DBContract;;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

/**
 * Created by Arp on 2/15/2015.
 */



public class  fetch_Task extends AsyncTask<String,Void,Void >
{    public JSONArray weatherArray;
    String forecastJsonStr = null;
    Vector<ContentValues> cVVector = new Vector<ContentValues>(weatherArray.length());
    String[] resultStrs = new String[weatherArray.length()];



    Context mContext;
    public fetch_Task(Context context)
    {
        mContext = context;
    }


    @Override
    protected Void doInBackground(String... params)
    {
        if(params.length==0)
        {
            return null;
        }
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;



        try
        {

            final String FORECAST_BASE_URL = "http://codeworld.uphero.com/details.php";


            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon().build();


            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            //error here in connection
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            StringBuffer buffer = new StringBuffer();

            if (inputStream == null) {
                return  null;

            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");

            }
            if (buffer.length() == 0) {
                    return null;
            }
            forecastJsonStr = buffer.toString();


        } catch (IOException e) {
            return  null;

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {

                }
            }

        }





        try {
            weatherArray = new JSONArray(forecastJsonStr);


            for (int i =0;i<weatherArray.length();i++) {
                JSONObject details = weatherArray.getJSONObject(i);
                String serial_number = details.getString("serial_number");
                String company_name = details.getString("company_name");
                String dat = details.getString("dat");
                String eligibility_criteria = details.getString("eligibility_criteria");
                String branch = details.getString("branch");
                String salary = details.getString("salary");
                String deadline = details.getString("deadline");
                String other_info = details.getString("other_info");



                ContentValues contentValues  = new ContentValues();

                contentValues.put(DBContract.pat_table.COL_SERIAL_NUMBER,serial_number);
                contentValues.put(DBContract.pat_table.COL_COMPANY_NAME,company_name);
                contentValues.put(DBContract.pat_table.COL_DATE,dat);
                contentValues.put(DBContract.pat_table.COL_ELIGIBILITY_CRITERIA,eligibility_criteria);
                contentValues.put(DBContract.pat_table.COL_BRANCHES,branch);
                contentValues.put(DBContract.pat_table.COL_SALARY,salary);
                contentValues.put(DBContract.pat_table.COL_DEADLINE,deadline);
                contentValues.put(DBContract.pat_table.COL_OTHER_INFO,other_info);





            }
                if(cVVector.size()>0)
                {
                    ContentValues[] cvArray = new ContentValues[cVVector.size()];
                    cVVector.toArray(cvArray);
                    mContext.getContentResolver().bulkInsert(DBContract.pat_table.Content_Uri,cvArray);
                }
        } catch (JSONException e) {
        }
            return null;

    }

}
