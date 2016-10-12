package com.getlosthere.weatherme.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.getlosthere.weatherme.R;
import com.getlosthere.weatherme.adapters.WeatherPointsAdapter;
import com.getlosthere.weatherme.helpers.NetworkHelper;
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

public class ForecastWeatherFragment extends Fragment {
    private View view;
    private final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private ArrayList<WeatherPoint> weatherPoints;
    private WeatherPointsAdapter adapter;
    private RecyclerView rvWeatherPoints;

    public ForecastWeatherFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forecast_weather, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.view = view;
        rvWeatherPoints = (RecyclerView) view.findViewById(R.id.rvWeatherPoints);
        weatherPoints = new ArrayList<>();
        adapter = new WeatherPointsAdapter(getActivity(), weatherPoints);
        rvWeatherPoints.setAdapter(adapter);
        rvWeatherPoints.setLayoutManager(new LinearLayoutManager(getActivity()));
        getFiveDayForecastData();
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

    private void populateForecastWeather(JSONArray jsonArray){
        addAll(WeatherPoint.forecastFromJSONArray(jsonArray));
    }

    private void getFiveDayForecastData() {
        final String API_KEY = getResources().getString(R.string.open_weather_api_key);
        if (NetworkHelper.isOnline() && NetworkHelper.isNetworkAvailable(getActivity())) {
            new ForecastWeatherTask().execute(BASE_URL + "forecast/daily?q=Atlanta,ga&units=imperial&cnt=6&appId=" + API_KEY);
        }
        else {
            Toast.makeText(getActivity(), "You're offline :( Check your network connection",Toast.LENGTH_LONG).show();
        }
    }

    public void addAll(ArrayList<WeatherPoint> newWeatherPoints) {
        int curSize = adapter.getItemCount();
        weatherPoints.addAll(newWeatherPoints);
        adapter.notifyItemRangeInserted(curSize, newWeatherPoints.size() - 1);
    }
}
