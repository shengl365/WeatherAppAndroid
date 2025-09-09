package com.example.weatherapp.network;

// CwbApiService.java
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import com.example.weatherapp.model.CwbForecastResponse;

public interface CwbApiService {
    // 範例：取得36小時天氣預報（實際 endpoint 以 opendata 文件為主）
    @GET("v1/rest/datastore/F-C0032-001") // 範例 resource id, 請以文件確認
    Call<CwbForecastResponse> getForecast(
            @Query("Authorization") String apiKey,
            @Query("locationName") String locationName // 可改成 region code 等
    );
}
