package com.getlosthere.weatherme.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.getlosthere.weatherme.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ImageView ivCurrentImage;
    private TextView tvCurrentHighTemp;
    private final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=Atlanta,GA&units=imperial&appId=";
    private final String API_KEY = getResources().getString(R.string.open_weather_api_key);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateView();
    }

    private void populateView(){
        // http://api.openweathermap.org/data/2.5/weather?q=Atlanta,GA&units=imperial&appId=
        ivCurrentImage = (ImageView) findViewById(R.id.ivCurrentImage);
        tvCurrentHighTemp = (TextView) findViewById(R.id.tvCurrentHighTemp);
        downloadResponseFromNetwork();
    }

    private class NetworkAsyncTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... addresses){
            StringBuilder stringBuilder = new StringBuilder();

            try {
                URL url = new URL(addresses[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                return stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        protected void onPostExecute(String result) {
            // This method is executed in the UIThread
            // with access to the result of the long running task
            // DO SOMETHING WITH STRING RESPONSE
            Log.d("DEBUG",result);
        }
    }

    private void downloadResponseFromNetwork() {
        new NetworkAsyncTask().execute(BASE_URL + API_KEY);
    }
}
