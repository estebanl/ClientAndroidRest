package com.example.spartan_117.servicesomee;


import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spartan_117.servicesomee.model.Flower;
import com.example.spartan_117.servicesomee.parsers.FlowerJSONParser;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ListActivity {
    TextView output;
    ProgressBar progressBar;
    List<MyTask> tasks;

    List<Flower> flowerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output = (TextView) findViewById(R.id.output);
        output.setMovementMethod(new ScrollingMovementMethod());
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        tasks = new ArrayList<>();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu)
        {
            if (isOnline())
            {
                requestData("http://services.hanselandpetal.com/secure/flowers.json");
            }
            else
            {
                Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
            }

        }
        return false;
    }


    private void requestData(String uri) {

        MyTask myTask = new MyTask();
        myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,uri);
       // myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"Param 1", "PAram 2", "Param 3");
    }

    public void updateDisplay()
    {
        /*
        if (flowerList != null)
        {
            for (Flower flower : flowerList) {
                output.append(flower.getName() + "  " + flower.getCategory() + " " + flower.getPhoto() + "\n");
            }
        }
*/
        FlowerAdapter flowerAdapter = new FlowerAdapter(this, R.layout.item_flower, flowerList);
        setListAdapter(flowerAdapter);
    }

    protected boolean isOnline()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting())
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    private class MyTask extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
          //  updateDisplay("Start AsynTask");

            if (tasks.size() == 0)
            {
                progressBar.setVisibility(View.VISIBLE);
            }
            tasks.add(this);
        }

        @Override
        protected String doInBackground(String... params) {


         /*   for (int i = 0; i < params.length; i++ )
            {
                publishProgress("Working with "+ params[i]);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
              return "Task Complete !";
            */
          String content = HttpManager.getData(params[0], "feeduser","feedpassword");
            return content;
        }

        @Override
        protected void onPostExecute(String s) {


            tasks.remove(this);
            if (tasks.size() == 0) {
                progressBar.setVisibility(View.INVISIBLE);
            }
            if (s == null)
            {
                Toast.makeText(MainActivity.this, "Can't connect to web service",Toast.LENGTH_LONG).show();
                return;
            }
            flowerList = FlowerJSONParser.parseFeed(s);
            updateDisplay();

        }

        @Override
        protected void onProgressUpdate(String... values) {
     //       updateDisplay(values[0]);
        }
    }
}
