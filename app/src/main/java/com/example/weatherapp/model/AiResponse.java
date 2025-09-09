package com.example.weatherapp.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AiResponse {

    @SerializedName("candidates")
    public List<Candidate> candidates;

    public String firstTextOr(String fallback) {
        if (candidates != null && !candidates.isEmpty()) {
            Candidate c = candidates.get(0);
            if (c.content != null && c.content.parts != null && !c.content.parts.isEmpty()) {
                String t = c.content.parts.get(0).text;
                if (t != null && !t.isEmpty()) return t;
            }
        }
        return fallback;
    }

    public static class Candidate {
        @SerializedName("content")
        public Content content;
    }

    public static class Content {
        @SerializedName("parts")
        public List<Part> parts;
    }

    public static class Part {
        @SerializedName("text")
        public String text;
    }
}
