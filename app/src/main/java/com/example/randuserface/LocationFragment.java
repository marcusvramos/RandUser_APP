package com.example.randuserface;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.randuserface.models.UserResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationFragment extends Fragment {
    private MapView mapView;
    private FusedLocationProviderClient fusedLocationClient;
    private Location deviceLocation;
    private UserResponse.User selectedUser;
    private TextView distanceTextView;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_location, container, false);

        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);

        distanceTextView = view.findViewById(R.id.distance_text_view);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        selectedUser = MainActivity.selectedUser;

        getDeviceLocation();

        return view;
    }

    private void getDeviceLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            deviceLocation = location;
                            mapView.getMapAsync(googleMap -> {
                                displayUserLocation(googleMap);
                            });
                        } else {
                            Toast.makeText(getActivity(), "Não foi possível obter sua localização.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            // Solicitar permissão
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getDeviceLocation();
            } else {
                Toast.makeText(getActivity(), "Permissão de localização não concedida.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void displayUserLocation(GoogleMap googleMap) {
        if (selectedUser != null && selectedUser.location != null && selectedUser.location.coordinates != null) {
            if (deviceLocation != null) {
                try {
                    String latitudeStr = selectedUser.location.coordinates.latitude.replace(",", ".");
                    String longitudeStr = selectedUser.location.coordinates.longitude.replace(",", ".");

                    double userLat = Double.parseDouble(latitudeStr);
                    double userLon = Double.parseDouble(longitudeStr);

                    LatLng userLatLng = new LatLng(userLat, userLon);
                    LatLng deviceLatLng = new LatLng(deviceLocation.getLatitude(), deviceLocation.getLongitude());

                    BitmapDescriptor userIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
                    BitmapDescriptor deviceIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);

                    googleMap.addMarker(new MarkerOptions()
                            .position(userLatLng)
                            .title("Usuário")
                            .icon(userIcon));

                    googleMap.addMarker(new MarkerOptions()
                            .position(deviceLatLng)
                            .title("Você")
                            .icon(deviceIcon));

                    LatLngBounds bounds = new LatLngBounds.Builder()
                            .include(userLatLng)
                            .include(deviceLatLng)
                            .build();
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));

                    float[] results = new float[1];
                    Location.distanceBetween(userLat, userLon,
                            deviceLocation.getLatitude(), deviceLocation.getLongitude(), results);

                    distanceTextView.setText(String.format("Distância: %.2f km", results[0] / 1000));
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "Coordenadas inválidas.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Não foi possível obter sua localização.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Informações de localização do usuário não disponíveis.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
        super.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
