package com.getlosthere.weatherme.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.getlosthere.weatherme.R;
import com.getlosthere.weatherme.adapters.WeatherPointsAdapter;
import com.getlosthere.weatherme.fragments.CurrentWeatherFragment;
import com.getlosthere.weatherme.fragments.ForecastWeatherFragment;
import com.getlosthere.weatherme.models.WeatherPoint;

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
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flCurrentWeather, new CurrentWeatherFragment());
        ft.replace(R.id.flForecastWeather, new ForecastWeatherFragment());
        ft.commit();
    }
}
