package com.example.shoplist;

public class app {
    package com.example.jmd_mapbox;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin;
import com.mapbox.maps.plugin.LocationPuck2D;
import com.mapbox.maps.plugin.gestures.OnMapClickListener;
import com.mapbox.geojson.Point;

import java.util.List;

    public class MainActivity extends AppCompatActivity implements PermissionsListener, OnMapClickListener {

        private MapView mapView;
        private MapboxMap mapboxMap;
        private PermissionsManager permissionsManager;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_main);
            mapView = findViewById(R.id.mapView);

            // Load the Mapbox map style
            mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, style -> {
                mapboxMap = mapView.getMapboxMap();
                enableLocationComponent(style);
            });

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        @Override
        public void onExplanationNeeded(List<String> permissionToExplain) {
            // Handle permission explanation here
        }

        @Override
        public void onPermissionResult(boolean granted) {
            if (granted) {
                // Enable location component after permission is granted
                enableLocationComponent(mapboxMap.getStyle());
            }
            else {
                Toast.makeText(getApplicationContext(), "Permission not granted", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        @Override
        public boolean onMapClick(@NonNull Point point) {
            // Handle map click events
            return false;
        }

        private void enableLocationComponent(@NonNull Style loadedMapStyle) {
            if (PermissionsManager.areLocationPermissionsGranted(this)) {
                LocationComponentPlugin locationComponent = mapView.getPlugin("locationComponent");
                if (locationComponent != null) {
                    locationComponent.setLocationPuck(new LocationPuck2D.Builder()
                            .bearingImage(getDrawable(R.drawable.ic_launcher_foreground))
                            .build());
                    locationComponent.setEnabled(true);
                }
                locationComponent.activateLocationComponent(this, loadedMapStyle);
                locationComponent.setLocationComponentEnabled(true);
                locationComponent.setCameraMode(CameraMode.TRACKING);
            } else {
                permissionsManager = new PermissionsManager(this);
                permissionsManager.requestLocationPermissions(this);
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            permissionsManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }

        @Override
        protected void onStart() {
            super.onStart();
        }

        @Override
        protected void onResume() {
            super.onResume();
        }

        @Override
        protected void onPause() {
            super.onPause();
        }

        @Override
        protected void onStop() {
            super.onStop();
        }

        @Override
        public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
            super.onSaveInstanceState(outState, outPersistentState);
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
        }

        @Override
        public void onLowMemory() {
            super.onLowMemory();
        }
    }



}
