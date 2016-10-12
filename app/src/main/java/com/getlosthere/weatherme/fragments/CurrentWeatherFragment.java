package com.getlosthere.weatherme.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.getlosthere.weatherme.R;
import com.getlosthere.weatherme.adapters.WeatherPointsAdapter;
import com.getlosthere.weatherme.helpers.NetworkHelper;
import com.getlosthere.weatherme.models.WeatherPoint;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentWeatherFragment extends Fragment {
    private ImageView ivCurrentImage;
    private TextView tvCurrentHighTemp;
    private TextView tvCurrentLowTemp;
    private TextView tvCurrentText;
    private TextView tvCurrentDate;
    private ArrayList<WeatherPoint> weatherPoints;
    private WeatherPointsAdapter adapter;
    private View view;
    private final String BASE_URL = "http://api.openweathermap.org/data/2.5/";


    public CurrentWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_current_weather, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.view = view;
        getCurrentWeatherData();
    }

    private void populateCurrentWeather(JSONObject jsonObject){
        ivCurrentImage = (ImageView) view.findViewById(R.id.ivCurrentImage);
        tvCurrentHighTemp = (TextView) view.findViewById(R.id.tvCurrentHighTemp);
        tvCurrentLowTemp = (TextView) view.findViewById(R.id.tvCurrentLowTemp);
        tvCurrentText = (TextView) view.findViewById(R.id.tvCurrentText);
        tvCurrentDate = (TextView) view.findViewById(R.id.tvCurrentDate);
        WeatherPoint currentWeather = WeatherPoint.currentFromJSONObject(jsonObject);
        tvCurrentText.setText(currentWeather.getText());
        tvCurrentHighTemp.setText(currentWeather.getMaxTemp());
        tvCurrentLowTemp.setText(currentWeather.getMinTemp());
        tvCurrentDate.setText(currentWeather.getDateWeekday() + ", " + currentWeather.getDateMonthDay());
        ivCurrentImage.setImageDrawable(getActivity().getResources().getDrawable(currentWeather.getIconArtResource()));
    }

    private void getCurrentWeatherData() {
        final String API_KEY = getResources().getString(R.string.open_weather_api_key);
        if (NetworkHelper.isOnline() && NetworkHelper.isNetworkAvailable(getActivity())) {
            new CurrentWeatherTask().execute(BASE_URL + "weather?q=Atlanta,GA&units=imperial&appId=" + API_KEY);
        } else {
            Toast.makeText(getActivity(), "You're offline :( Check your network connection",Toast.LENGTH_LONG).show();
        }
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
}
