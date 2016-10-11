package com.getlosthere.weatherme.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.getlosthere.weatherme.R;
import com.getlosthere.weatherme.adapters.WeatherPointsAdapter;
import com.getlosthere.weatherme.models.WeatherPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ImageView ivCurrentImage;
    private TextView tvCurrentHighTemp;
    private TextView tvCurrentLowTemp;
    private TextView tvCurrentText;
    private TextView tvCurrentDate;
    private ArrayList<WeatherPoint> weatherPoints;
    private WeatherPointsAdapter adapter;
    private RecyclerView rvWeatherPoints;

    private final String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvWeatherPoints = (RecyclerView) findViewById(R.id.rvWeatherPoints);
        weatherPoints = new ArrayList<>();
        adapter = new WeatherPointsAdapter(this, weatherPoints);
        rvWeatherPoints.setAdapter(adapter);
        rvWeatherPoints.setLayoutManager(new LinearLayoutManager(this));
        getCurrentWeatherData();
        getFiveDayForecastData();
    }

    private void populateCurrentWeather(JSONObject jsonObject){
        ivCurrentImage = (ImageView) findViewById(R.id.ivCurrentImage);
        tvCurrentHighTemp = (TextView) findViewById(R.id.tvCurrentHighTemp);
        tvCurrentLowTemp = (TextView) findViewById(R.id.tvCurrentLowTemp);
        tvCurrentText = (TextView) findViewById(R.id.tvCurrentText);
        tvCurrentDate = (TextView) findViewById(R.id.tvCurrentDate);
        WeatherPoint currentWeather = WeatherPoint.currentFromJSONObject(jsonObject);
        tvCurrentText.setText(currentWeather.getText());
        tvCurrentHighTemp.setText(currentWeather.getMaxTemp()+"°");
        tvCurrentLowTemp.setText(currentWeather.getMinTemp()+"°");
        tvCurrentDate.setText(currentWeather.getDateWeekday() + ", " + currentWeather.getDateMonthDay());
        ivCurrentImage.setImageDrawable(getDrawable(currentWeather.getIconArtResource()));
    }

    private void populateForecastWeather(JSONArray jsonArray){
        addAll(WeatherPoint.fromJSONArray(jsonArray));
    }

    private class CurrentWeatherTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... addresses) {
            StringBuilder stringBuilder = new StringBuilder();
            InputStream inputStream = null;
            try {
                URL url = new URL(addresses[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                if (inputStream != null)
                    inputStream.close();
                return stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        protected void onPostExecute(String result) {
            try {
                JSONObject resultObject = new JSONObject(result);
//                Log.d("DEBUG", resultObject.toString());
                populateCurrentWeather(resultObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private class ForecastWeatherTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... addresses) {
            StringBuilder stringBuilder = new StringBuilder();
            InputStream inputStream = null;
            try {
                URL url = new URL(addresses[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                if (inputStream != null)
                    inputStream.close();
                return stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        protected void onPostExecute(String result) {
            try {
                JSONObject resultObject = new JSONObject(result);
//                Log.d("DEBUG", resultObject.toString());
                JSONArray resultArray = resultObject.getJSONArray("list");
                populateForecastWeather(resultArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void getCurrentWeatherData() {
        final String API_KEY = getResources().getString(R.string.open_weather_api_key);
        new CurrentWeatherTask().execute(BASE_URL + "weather?q=Atlanta,GA&units=imperial&appId=" + API_KEY);
    }

    private void getFiveDayForecastData() {
        final String API_KEY = getResources().getString(R.string.open_weather_api_key);
        new ForecastWeatherTask().execute(BASE_URL + "forecast/daily?q=Atlanta,ga&units=imperial&cnt=5&appId=" + API_KEY);
    }

    public void addAll(ArrayList<WeatherPoint> newWeatherPoints) {
        int curSize = adapter.getItemCount();
        weatherPoints.addAll(newWeatherPoints);
        adapter.notifyItemRangeInserted(curSize, newWeatherPoints.size() - 1);
    }
}
