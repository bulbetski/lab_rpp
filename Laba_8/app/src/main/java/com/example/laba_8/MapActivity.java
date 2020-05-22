package com.example.laba_8;

import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mapbox.core.constants.Constants.PRECISION_6;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineCap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;

public class MapActivity extends AppCompatActivity {
    private MapView mapView;
    private DirectionsRoute directionsRoute;
    private MapboxMap map;
    private MapboxDirections client;
    private Point origin;
    private Point destination;
    private PermissionsManager permissionsManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1Ijoic3ZhcnR2YWxwIiwiYSI6ImNrNzBxZnI1cjAxNmQzbW53Ymk3eHc0cWUifQ.Q-LZeeCAItahb7zeZELYgg");
        setContentView(R.layout.activity_map);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                map = mapboxMap;
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                      PointTask task = new PointTask();
                      task.execute(style);
                    }
                });
            }
        });
    }
    private void initSource(Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource("11"));
        GeoJsonSource source = new GeoJsonSource("22", FeatureCollection.fromFeatures(
                new Feature[]{
                Feature.fromGeometry(origin),
                Feature.fromGeometry(destination)}));
        loadedMapStyle.addSource(source);
    }
    private void initLayers(Style loadedMapStyle) {
        LineLayer routeLayer = new LineLayer("route_layer", "11");
        routeLayer.setProperties(lineCap(Property.LINE_CAP_ROUND),
                lineJoin(Property.LINE_JOIN_ROUND),
                lineWidth(5f),
                lineColor(Color.BLACK));
        loadedMapStyle.addLayer(routeLayer);

        loadedMapStyle.addImage("pin-icon-id", getDrawable(R.drawable.mapbox_marker_icon_default));

        loadedMapStyle.addLayer(new SymbolLayer("icon_layer", "22").withProperties(
                iconImage("pin-icon-id"),
                iconIgnorePlacement(true),
                iconAllowOverlap(true)
        ));
    }
    private void getRoute(final MapboxMap mapboxMap, Point origin, Point destination) {
        client = MapboxDirections.builder()
                .origin(origin)
                .destination(destination)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .accessToken("pk.eyJ1Ijoic3ZhcnR2YWxwIiwiYSI6ImNrNzBxZnI1cjAxNmQzbW53Ymk3eHc0cWUifQ.Q-LZeeCAItahb7zeZELYgg")
                .build();
        client.enqueueCall(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                if(response.body().routes().size() == 0) {
                    Toast.makeText(MapActivity.this, "NOT FOUND", Toast.LENGTH_SHORT).show();
                    return;
                }
                directionsRoute = response.body().routes().get(0);
                if(mapboxMap != null) {
                    mapboxMap.getStyle(new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {
                            GeoJsonSource source = style.getSourceAs("11");
                            if(source != null) {
                                source.setGeoJson(LineString.fromPolyline(directionsRoute.geometry(), PRECISION_6));
                            } else {
                                Toast.makeText(MapActivity.this, "NOT FOUND", Toast.LENGTH_LONG);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                finish();
            }
        });
    }

    private void displayDeviceLocation(Style loadedMapStyle, MapboxMap mapboxMap) {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            LocationComponent locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(LocationComponentActivationOptions.builder(
                    this, loadedMapStyle).build());
            locationComponent.setLocationComponentEnabled(true);
            locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.COMPASS);
            Location location = locationComponent.getLastKnownLocation();
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mapboxMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(new CameraPosition.Builder()
                            .target(latLng)
                            .zoom(15)
                            .build()), 2000);
        } else {
            permissionsManager = new PermissionsManager(new PermissionsListener() {
                @Override
                public void onExplanationNeeded(List<String> permissionsToExplain) {
                    Toast.makeText(MapActivity.this,R.string.permission, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onPermissionResult(boolean granted) {
                    Style style = map.getStyle();
                    if (granted && style != null) {
                        displayDeviceLocation(style, map);
                    } else {
                        Toast.makeText(MapActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }
                }
            });
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (client != null) {
            client.cancelCall();
        }
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    public class PointTask extends AsyncTask<Style, Void, Style> {

        @Override
        protected Style doInBackground(Style... styles) {
            Style style = styles[0];
            AddressService addressService = new AddressService();
            String from = getIntent().getStringExtra("from");
            String to = getIntent().getStringExtra("to");
            origin = addressService.getPoint(from);
            destination = addressService.getPoint(to);
            return style;
        }

        @Override
        protected void onPostExecute(Style style) {
            if(origin != null && destination != null) {
                initSource(style);
                initLayers(style);
                getRoute(map, origin, destination);
                displayDeviceLocation(style, map);
            } else {
                Toast.makeText(MapActivity.this, "NOT FOUND", Toast.LENGTH_LONG).show();
            }
        }
    }
}
