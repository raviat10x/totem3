package com.move10x.totem.design;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.android.gms.maps.model.Marker;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.move10x.totem.R;
import com.move10x.totem.models.CurrentProfile;
import com.move10x.totem.models.Driver;
import com.move10x.totem.models.Url;
import com.move10x.totem.services.AsyncHttpService;
import com.move10x.totem.services.CurrentProfileService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class
        DriversOnMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private HashMap<Marker, Driver> driverMarkers = new HashMap<Marker, Driver>();
    ProgressBar progressBar;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers_on_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingRefreshButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress(true);
                loadDriverMarkers();
            }
        });


        mapFragment.getMapAsync(this);

        progressBar = (ProgressBar) findViewById(R.id.driversProgress);
        showProgress(true);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        loadDriverMarkers();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            /*driverListContainer.setVisibility(show ? View.GONE : View.VISIBLE);
            driverListContainer.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    driverListContainer.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });
*/
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            progressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            //driverListContainer.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public void loadDriverMarkers(){

        //Clear current markers.
        for (Marker marker: driverMarkers.keySet()) {
            marker.remove();
        }

        //Async Driverlist fetch.
        //Request Parameters.
        CurrentProfile currentProfile = new CurrentProfileService(getApplicationContext()).getCurrentProfile();
        Log.d("driverFragment", "Fetch drivers for user: " + currentProfile.toString());
        String uid = currentProfile.getUserId();
        Log.d("driverFragment", "Fetch drivers for userId: " + uid);
        RequestParams loginParameters = new RequestParams();
        loginParameters.put("uid", uid);
        loginParameters.put("tag", "vrm_get_drivers");
        AsyncHttpService.get(Url.apiBaseUrl, loginParameters, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray.
                Log.d("driverFragment", "Parsing getDriverList() response. ");
                try {

                    if (response.getString("success") != null && response.getString("success").equals("1")) {
                        JSONArray jsonDriverList = response.getJSONArray("drivers");
                        List<Driver> drivers = new ArrayList<Driver>();
                        for (int i = 0; i < jsonDriverList.length(); i++) {
                            drivers.add(Driver.decodeJsonForMap(jsonDriverList.getJSONObject(i)));
                        }

                        // Add a marker in Mumbai and move the camera
                        LatLng mumbai = new LatLng(19.114678, 72.904930);
                        CameraUpdate center =
                                CameraUpdateFactory.newLatLngZoom(mumbai, 10);
                        mMap.animateCamera(center);

                        for (Driver driver : drivers) {
                            LatLng driverLoation = new LatLng(driver.getLattitude(), driver.getLongitude());
                            Marker marker = mMap.addMarker(new MarkerOptions().position(driverLoation).title(driver.getFirstName() + " " + driver.getLastName()));
                            Log.d("driversOnMap", "Driver duty Status: " + driver.getDutyStatus());
                            //Set marker green if available.
                            if (driver.getDutyStatus().toUpperCase().equals(Driver.DutyStatus_Available)) {
                                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                            } else if (!driver.getDutyStatus().toUpperCase().equals(Driver.DutyStatus_Offduty)) {
                                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                            }
                            marker.setAlpha((float) 0.8);
                            driverMarkers.put(marker, driver);

                            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                                // Use default InfoWindow frame
                                @Override
                                public View getInfoWindow(Marker args) {
                                    return null;
                                }

                                // Defines the contents of the InfoWindow
                                @Override
                                public View getInfoContents(Marker args) {
                                    Log.d("driverOnMap", "return data for given marker.");

                                    final Marker currentMareker = args;

                                    // Getting view from the layout file info_window_layout
                                    View v = getLayoutInflater().inflate(R.layout.googlemap_info_window, null);
                                    v.setAlpha((float) 0.7);
                                    final Driver driver = driverMarkers.get(args);

                                    TextView txtDriverName = (TextView) v.findViewById(R.id.driverName);
                                    txtDriverName.setText(driver.getFirstName() + " " + driver.getLastName());
                                    //Set marker blue if available.
                                    if (driver.getDutyStatus().toUpperCase().equals(Driver.DutyStatus_Available)) {
                                        txtDriverName.setTextColor(getResources().getColor(R.color.green));
                                    } else if (!driver.getDutyStatus().toUpperCase().equals(Driver.DutyStatus_Offduty)) {
                                        txtDriverName.setTextColor(getResources().getColor(R.color.blue));
                                    } else {
                                        txtDriverName.setTextColor(getResources().getColor(R.color.red));
                                    }

                                    TextView txtDriverDetails = (TextView) v.findViewById(R.id.driverDetails);
                                    txtDriverDetails.setText(driver.getPlan() + ", " + driver.getCategory() + ", " + driver.getTempoMake() + " " + driver.getTempoModel());

                                    TextView txtDriverStatus = (TextView) v.findViewById(R.id.driverStatus);
                                    txtDriverStatus.setText(driver.getWorkStatus() + " " + driver.getDutyStatus());


                                    return v;
                                }
                            });

                            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                                @Override
                                public void onInfoWindowClick(Marker marker) {
                                    Log.d("driverOnMap", "show details popup.");
                                    Intent intent = new Intent(getApplicationContext(), DriverDetailsActivity.class);
                                    Driver driver = driverMarkers.get(marker);
                                    intent.putExtra("driverUid", driver.getuId());
                                    Log.d("driverOnMap", "Driver UID: " + driver.getuId());
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                } catch (JSONException ex) {
                    Toast.makeText(getBaseContext(), "Failed to load drivers.", Toast.LENGTH_LONG);
                }
                showProgress(false);
            }


        });
    }

}
