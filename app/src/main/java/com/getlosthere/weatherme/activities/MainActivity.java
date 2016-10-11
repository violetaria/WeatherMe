package com.getlosthere.weatherme.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.getlosthere.weatherme.R;
import com.getlosthere.weatherme.helpers.DateFormatHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;

public class MainActivity extends AppCompatActivity {
    private ImageView ivCurrentImage;
    private TextView tvCurrentHighTemp;
    private TextView tvCurrentLowTemp;
    private TextView tvCurrentText;
    private TextView tvCurrentDate;
    private final String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getCurrentWeatherData();
    }

    private void populateCurrentWeather(JSONObject jsonObject){
        ivCurrentImage = (ImageView) findViewById(R.id.ivCurrentImage);
        tvCurrentHighTemp = (TextView) findViewById(R.id.tvCurrentHighTemp);
        tvCurrentLowTemp = (TextView) findViewById(R.id.tvCurrentLowTemp);
        tvCurrentText = (TextView) findViewById(R.id.tvCurrentText);
        tvCurrentDate = (TextView) findViewById(R.id.tvCurrentDate);
        try {
            JSONObject weatherObject = jsonObject.getJSONArray("weather").getJSONObject(0);
            String weatherIcon = weatherObject.getString("icon");
            switch(weatherIcon) {
                case "01d":
                    ivCurrentImage.setImageResource(R.mipmap.art_clear);
                    break;
                case "02d":
                case "03d":
                    ivCurrentImage.setImageResource(R.mipmap.art_light_clouds);
                    break;
                case "04d":
                    ivCurrentImage.setImageResource(R.mipmap.art_clouds);
                    break;
                case "09d":
                    ivCurrentImage.setImageResource(R.mipmap.art_light_rain);
                    break;
                case "10d":
                    ivCurrentImage.setImageResource(R.mipmap.art_rain);
                    break;
                case "11d":
                    ivCurrentImage.setImageResource(R.mipmap.art_storm);
                    break;
                case "13d":
                    ivCurrentImage.setImageResource(R.mipmap.art_snow);
                    break;
                case "50d":
                    ivCurrentImage.setImageResource(R.mipmap.art_fog);
                    break;
                default:
                    ivCurrentImage.setImageResource(R.mipmap.ic_launcher);
                    break;
            }

            String weatherText = weatherObject.getString("main");
            tvCurrentText.setText(weatherText);

            JSONObject mainArray = jsonObject.getJSONObject("main");
            String maxTemp = mainArray.getString("temp_max");
            tvCurrentHighTemp.setText(maxTemp);

            String minTemp = mainArray.getString("temp_min");
            tvCurrentLowTemp.setText(minTemp);

            long currentTime = jsonObject.getLong("dt");
            String currentDate = DateFormatHelper.formatDate(currentTime);
            tvCurrentDate.setText(currentDate);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private class OpenWeatherAPIClient extends AsyncTask<String, Void, String> {

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
                Log.d("DEBUG", resultObject.toString());
                populateCurrentWeather(resultObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void getCurrentWeatherData() {
        final String API_KEY = getResources().getString(R.string.open_weather_api_key);
        new OpenWeatherAPIClient().execute(BASE_URL + "weather?q=Atlanta,GA&units=imperial&appId=" + API_KEY);
    }

    private void getFiveDayForecastData() {
        final String API_KEY = getResources().getString(R.string.open_weather_api_key);
        new OpenWeatherAPIClient().execute(BASE_URL + "forecast/daily?q=Atlanta,ga&units=imperial&cnt=5&appId=" + API_KEY);
    }
}
