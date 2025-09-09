package com.example.weatherapp.network;

import com.google.gson.annotations.SerializedName;
import java.util.Collections;
import java.util.List;

public class AiRequest {

    @SerializedName("contents")
    public List<Content> contents;

    public AiRequest(String prompt) {
        this.contents = Collections.singletonList(
                Content.ofUserText(prompt)
        );
    }

    public static class Content {
        @SerializedName("role")
        public String role; // 可省略，預設 user；為穩妥仍填 "user"

        @SerializedName("parts")
        public List<Part> parts;

        static Content ofUserText(String text) {
            Content c = new Content();
            c.role = "user";
            c.parts = Collections.singletonList(new Part(text));
            return c;
        }
    }

    public static class Part {
        @SerializedName("text")
        public String text;
        public Part(String text) { this.text = text; }
    }
}
