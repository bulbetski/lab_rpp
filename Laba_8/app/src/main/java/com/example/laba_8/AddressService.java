package com.example.laba_8;

import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.geojson.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressService {
    Point point;
    List<String> result;
    boolean loading;
    public List<String> findResults(final String name) {
        MapboxGeocoding mapboxGeocoding = MapboxGeocoding.builder()
                .accessToken("pk.eyJ1Ijoic3ZhcnR2YWxwIiwiYSI6ImNrNzBxZnI1cjAxNmQzbW53Ymk3eHc0cWUifQ.Q-LZeeCAItahb7zeZELYgg")
                .query(name).build();
        loading = true;
        mapboxGeocoding.enqueueCall(new Callback<GeocodingResponse>() {
            @Override
            public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {
                result = new ArrayList<>();
                List<CarmenFeature> features = response.body().features();
                for(CarmenFeature feature : features) {
                    result.add(feature.placeName());
                }
                loading = false;
            }

            @Override
            public void onFailure(Call<GeocodingResponse> call, Throwable t) {
                result = new ArrayList<>();
                result.add("fff");
                result.add("afsad");
                loading = false;
            }
        });
        while(loading) {
            try {
                TimeUnit.MILLISECONDS.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public Point getPoint(String address) {
        MapboxGeocoding mapboxGeocoding = MapboxGeocoding.builder()
                .accessToken("pk.eyJ1Ijoic3ZhcnR2YWxwIiwiYSI6ImNrNzBxZnI1cjAxNmQzbW53Ymk3eHc0cWUifQ.Q-LZeeCAItahb7zeZELYgg")
                .query(address).build();
        loading = true;
        mapboxGeocoding.enqueueCall(new Callback<GeocodingResponse>() {
            @Override
            public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {
                List<CarmenFeature> features = response.body().features();
                if(response.body().features().size() > 0) {
                    point = features.get(0).center();
                } else {
                    point = null;
                }
                loading = false;
            }

            @Override
            public void onFailure(Call<GeocodingResponse> call, Throwable t) {
                point = null;
                loading = false;
            }
        });
        while(loading) {
            try {
                TimeUnit.MILLISECONDS.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return point;
    }
}
