package com.example.weatherapp;// MainActivity.java (重點示範)
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;

import com.example.weatherapp.model.CwbForecastResponse;
import com.example.weatherapp.model.AiResponse;
import com.example.weatherapp.network.AiRequest;
import com.example.weatherapp.network.AiService;
import com.example.weatherapp.network.CwbClient;
import com.example.weatherapp.network.AiClient;
import com.example.weatherapp.util.WeatherUtils;
import com.example.weatherapp.network.CwbApiService;
import android.util.Log;

import retrofit2.*;

import java.util.*;


public class MainActivity extends AppCompatActivity {
    private TextView dayWeatherWx, dayWeatherTemp, dayWeatherPop, nightWeatherWx, nightWeatherTemp, nightWeatherPop, outdoorSuggestionText;
    private String suggestionRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dayWeatherWx = findViewById(R.id.tvDayWx);
        dayWeatherTemp = findViewById(R.id.tvDayTemp);
        dayWeatherPop = findViewById(R.id.tvDayPop);
        nightWeatherWx = findViewById(R.id.tvNightWx);
        nightWeatherTemp = findViewById(R.id.tvNightTemp);
        nightWeatherPop = findViewById(R.id.tvNightPop);
        outdoorSuggestionText = findViewById(R.id.outdoorSuggestionText);

        fetchWeather("臺北市"); // 或 locationName
        fetchAIAdvices();
    }

    private void fetchWeather(String locationName) {
        CwbApiService service = CwbClient.getClient().create(CwbApiService.class);
        Call<CwbForecastResponse> call = service.getForecast(CwbClient.getCwbApiKey(), locationName);
        call.enqueue(new Callback<CwbForecastResponse>() {
            @Override
            public void onResponse(Call<CwbForecastResponse> call, Response<CwbForecastResponse> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.e("Weather", "API response error");
                    return;
                }
                Log.d("Weather", response.body().toString());
                // 簡化邏輯：找到特定 location，合併元素時間段，拆日/夜
                CwbForecastResponse.Records rec = response.body().records;
                if (rec == null || rec.location == null || rec.location.isEmpty()) return;
                CwbForecastResponse.Location loc = rec.location.get(0);

                if (response.isSuccessful() && response.body() != null) {
                    dayWeatherWx.setText(WeatherUtils.getTodayDayWeather(response.body(), "Wx"));
                    nightWeatherWx.setText(WeatherUtils.getTodayNightWeather(response.body(), "Wx"));
                    dayWeatherTemp.setText("高溫: " + WeatherUtils.getTodayDayWeather(response.body(), "MaxT") + "低溫: " + WeatherUtils.getTodayDayWeather(response.body(), "MinT"));
                    nightWeatherTemp.setText("高溫: " + WeatherUtils.getTodayNightWeather(response.body(), "MaxT") + "低溫: " + WeatherUtils.getTodayDayWeather(response.body(), "MinT"));
                    dayWeatherPop.setText(WeatherUtils.getTodayDayWeather(response.body(), "Pop"));
                    nightWeatherPop.setText(WeatherUtils.getTodayNightWeather(response.body(), "Pop"));
                } else {
                    dayWeatherWx.setText("取得資料失敗");
                    nightWeatherWx.setText("");
                }
            }
            @Override
            public void onFailure(Call<CwbForecastResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void fetchAIAdvices(){
        AiService aiService = AiClient.getService();
        AiRequest request = new AiRequest("白天" + dayWeatherWx + "晚上" + nightWeatherWx + "，請提供出門建議");
        String GEMINI_MODEL = "gemini-1.5-flash";

        aiService.generateContent(GEMINI_MODEL, AiClient.getAiApiKey(), request)
                .enqueue(new Callback<AiResponse>() {
            @Override
            public void onResponse(Call<AiResponse> call, Response<AiResponse> response) {
                Log.e("Weather", "onResponse");
                if (response.isSuccessful()) {
                    String suggestion = response.body().firstTextOr("（暫無建議）");
                    outdoorSuggestionText.setText("出門建議：" + suggestion);
                }
            }

            @Override
            public void onFailure(Call<AiResponse> call, Throwable t) {
                outdoorSuggestionText.setText("建議：取得建議失敗");
            }
        });

    }
}
