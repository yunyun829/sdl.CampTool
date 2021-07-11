package jp.ac.titech.itpro.sdl.camptool;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ThirdFragment extends Fragment implements OnMapReadyCallback {
    String TAG = getTag();

    private final static String[] PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private final static int REQ_PERMISSIONS = 9999;

    private MapView mViewModel;
    private GoogleMap mMap;

    private FusedLocationProviderClient locationClient;
    private LocationRequest request;
    private LocationCallback callback;

    private MarkerOptions MOption;
    private Marker marker;


    public static ThirdFragment newInstance() {
        return new ThirdFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.thierd_fragment, container, false);
    }

    @Override
    public void onViewCreated(@Nullable View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //mViewModel = new ViewModelProvider(this).get(MapView.class);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.SMFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Log.d(TAG, "fail to start map");
        }


        locationClient = LocationServices.getFusedLocationProviderClient(this.getContext());
        if (locationClient != null) {
            Log.d(TAG, "this is location client");
        }

        request = LocationRequest.create();
        request.setInterval(10000L);
        request.setFastestInterval(5000L);
        request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        callback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                if (mMap == null) {
                    return;
                }
                //Log.d(TAG, "get location " + ll.latitude + "," + ll.longitude);

                if(marker==null){
                    MOption.position(ll);
                    marker = mMap.addMarker(MOption);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(ll));
                }else{
                    marker.setPosition(ll);
                }

            }
        };
        MOption = new MarkerOptions();
        MOption.title("title");
        MOption.snippet("");

        FloatingActionButton RLButton = getView().findViewById(R.id.RisetLocationButton);
        RLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15f));
        Log.d(TAG,"success map create");
        mMap = googleMap;
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.68,139.76),15));
    }

    private void startLocationUpdate(boolean reqPermission) {
        for (String permission : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this.getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                if (reqPermission) {
                    ActivityCompat.requestPermissions(this.getActivity(), PERMISSIONS, REQ_PERMISSIONS);
                } else {
                    String text = getString(R.string.toast_requires_permission_format, permission);
                    Toast.makeText(this.getActivity(), text, Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
        locationClient.requestLocationUpdates(request, callback, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        startLocationUpdate(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdate();
    }

    @Override
    public void onRequestPermissionsResult(int reqCode, @NonNull String[] permissions, @NonNull int[] grants) {
        if (reqCode == REQ_PERMISSIONS) {
            startLocationUpdate(false);
        }
    }

    private void stopLocationUpdate() {
        locationClient.removeLocationUpdates(callback);
    }
}