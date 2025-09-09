package com.example.weatherapp.network;

import com.example.weatherapp.model.AiResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AiService {
    // 例如 model 用 gemini-1.5-flash（快速、便宜）
    @POST("v1beta/models/{model}:generateContent")
    Call<AiResponse> generateContent(
            @Path("model") String model,
            @Query("key") String apiKey,
            @Body AiRequest body
    );
}

