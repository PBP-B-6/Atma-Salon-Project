package com.example.atmasalon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.palettes.RangeColors;
import com.example.atmasalon.databinding.ActivityAboutBinding;
import com.example.atmasalon.preferences.UserPreference;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.ArrayList;
import java.util.List;

public class AboutActivity extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener, View.OnClickListener {

    private ActivityAboutBinding binding;
    private PermissionsManager permissionsManager;
    private MapboxMap mapboxMap;
    private MapView mapView;
    private UserPreference userPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.APP_TOKEN));

        binding = DataBindingUtil.setContentView(this, R.layout.activity_about);
        binding.btnKembali.setOnClickListener(this);

        userPref = new UserPreference(this);

        if(userPref.GetURLProfilePic() != null)
        {
            Bitmap img = null;
            img = Base64ToBitmap(userPref.GetURLProfilePic());
            if(img != null)
            {
                binding.profileImage.setImageBitmap(img);
            }
        }

        mapView = binding.mapView;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        Cartesian cartesian = AnyChart.column();
        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("2016", 2150));
        data.add(new ValueDataEntry("2017", 4300));
        data.add(new ValueDataEntry("2018", 5500));
        data.add(new ValueDataEntry("2019", 4900));
        data.add(new ValueDataEntry("2020", 6270));
        data.add(new ValueDataEntry("2021", 7150));
        Column column = cartesian.column(data);
        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value}{groupsSeparator: }");
        cartesian.animation(true);
        cartesian.title("Chart Penggunaan Aplikasi");
        cartesian.yScale().minimum(0d);
        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);
        cartesian.xAxis(0).title("Tahun");
        cartesian.yAxis(0).title("Pengguna");

        //Add Color
        RangeColors palette = RangeColors.instantiate();
        palette.items("#008C6E", "");
        palette.count(10);

        RangeColors palette2 = RangeColors.instantiate();
        palette2.items("#FAFAFA", "#EDA70A");
        palette2.count(10);

        cartesian.palette(palette);
        cartesian.yGrid(0).palette(palette2);

        binding.chart.setChart(cartesian);
        binding.chart.setProgressBar(binding.progressbar);
    }



    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap){
        AboutActivity.this.mapboxMap = mapboxMap;

        mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(-7.779467,110.416046))
                .title("Atma Salon"));

        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded(){
            @Override
            public void onStyleLoaded(@NonNull Style style) {


                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(-7.779467,110.416046))
                        .zoom(14)
                        .tilt(20)
                        .build();

                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000);
                mapboxMap.getUiSettings().setAllGesturesEnabled(false);
//                enableLocationComponent(style);
            }
        });

    }

    private Bitmap Base64ToBitmap(String encodedImage)
    {
        if(encodedImage.isEmpty() || encodedImage == null){return null;}
        try
        {
            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            return decodedByte;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle){
        if(PermissionsManager.areLocationPermissionsGranted(this)){
            LocationComponentOptions customLocaitioLocationComponentOptions = LocationComponentOptions.builder(this).pulseEnabled(true).build();

            LocationComponent locationComponent = mapboxMap.getLocationComponent();

            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(this, loadedMapStyle).
                            locationComponentOptions(customLocaitioLocationComponentOptions).build());

            locationComponent.setLocationComponentEnabled(false);

            locationComponent.setCameraMode(CameraMode.TRACKING);

            locationComponent.setRenderMode(RenderMode.NORMAL);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if(granted){
            mapboxMap.getStyle(new Style.OnStyleLoaded(){
                @Override
                public void onStyleLoaded(@NonNull Style style){
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    protected void onStart(){
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory(){
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnKembali)
        {
            Intent move = new Intent(this, ContainerActivity.class);
            startActivity(move);
            finish();
        }
    }
}