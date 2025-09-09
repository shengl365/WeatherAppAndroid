package com.example.weatherapp.model;

// CwbForecastResponse.java (使用 GSON 解析)
import java.util.List;

public class CwbForecastResponse {
    public Records records;

    public static class Records {
        public List<Location> location;
    }

    public static class Location {
        public String locationName;
        public List<WeatherElement> weatherElement;
    }

    public static class WeatherElement {
        public String elementName; // e.g. "Wx", "PoP", "MinT", "MaxT"
        public List<Time> time;
    }

    public static class Time {
        public String startTime;
        public String endTime;
        public Parameter parameter;
    }

    public static class Parameter {
        public String parameterName; // e.g. "Sunny" or "10"
        public String parameterValue;
    }
}
