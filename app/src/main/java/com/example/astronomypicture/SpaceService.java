package com.example.astronomypicture;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SpaceService {
    @GET("/planetary/apod")
    Call<SpaceInfo> getAPOD(@Query("api_key") String key);
}
