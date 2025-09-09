package com.example.weatherapp.util;

// WeatherUtils.java
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import com.example.weatherapp.model.CwbForecastResponse;
public class WeatherUtils {

    public static String getTodayDayWeather(CwbForecastResponse response, String input) {
        return getWeatherByIndex(response, 0, input);
    }

    public static String getTodayNightWeather(CwbForecastResponse response, String input) {
        return getWeatherByIndex(response, 1, input);
    }

    private static String getWeatherByIndex(CwbForecastResponse response, int index, String input) {
        if (response == null || response.records == null || response.records.location == null || response.records.location.isEmpty()) {
            return "無資料";
        }
        CwbForecastResponse.Location location = response.records.location.get(0);
        for (CwbForecastResponse.WeatherElement we : location.weatherElement) {
            if (input.equals(we.elementName)) {
                List<CwbForecastResponse.Time> times = we.time;
                if (times.size() > index) {
                    return times.get(index).parameter.parameterName;
                }
            }
        }
        return "無資料";
    }
}


