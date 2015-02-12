package com.example.arp.start;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Arp on 2/8/2015.
 */
public class menu2_Fragment extends Fragment {
    public static final String LOG_TAG = "MyApp";
    View rootview;
    public menu2_Fragment()
    {

    }
    @Override
    public void onCreate(Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }//for a refresh menu ,has a menu


    @Override
    public  void onCreateOptionsMenu(Menu menu,MenuInflater inflater)
    {
        inflater.inflate(R.menu.refresh,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)

    {
        int id= item.getItemId();
        if(id == R.id.action_refresh)
        {
            FetchWeatherTask weatherTask = new FetchWeatherTask();
            weatherTask.execute("94043");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview=inflater.inflate(R.layout.menu2_layout,container,false);

        String[] checkinglist = { "name-arpan","name-shreya","name-piyush"};
        List<String> checklist = new ArrayList<String>(Arrays.asList(checkinglist));
       ArrayAdapter checkingAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_all,
                R.id.list_all,
                checklist);
        ListView listView = (ListView) rootview.findViewById(R.id.all_companies);
        listView.setAdapter(checkingAdapter);

        return rootview;
    }



    public class  FetchWeatherTask extends AsyncTask<String,Void,String> {



        @Override
        protected String doInBackground(String... params)
        {
            if(params.length==0)
            {
                return null;
            }
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String forecastJsonStr = null;



            try {

                final String FORECAST_BASE_URL= "http://codeworld.uphero.com/testi.php";


                Uri builtUri=Uri.parse(FORECAST_BASE_URL).buildUpon().build();


                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                Log.v(LOG_TAG,"built URI"+ urlConnection.toString());
                urlConnection.setRequestMethod("GET");
                //error here in connection
                InputStream inputStream = urlConnection.getInputStream();
                Log.v(LOG_TAG,"inputstream is "+inputStream.toString());
                StringBuffer buffer = new StringBuffer();
                Log.v(LOG_TAG,"buffer is"+ buffer.toString());
                if (inputStream == null) {
                    forecastJsonStr=null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");

                }
                if (buffer.length() == 0) {
                    forecastJsonStr=null;
                }
                forecastJsonStr = buffer.toString();

            } catch (IOException e) {


            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    }
                    catch (final IOException e)
                    {

                    }
                }

            }

            Log.v(LOG_TAG,"built URI"+ forecastJsonStr);
            return forecastJsonStr;

        }
    }
}
