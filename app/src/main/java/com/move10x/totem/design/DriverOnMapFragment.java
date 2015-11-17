package com.move10x.totem.design;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.RequestParams;
import com.move10x.totem.R;
import com.move10x.totem.models.CurrentProfile;
import com.move10x.totem.models.Driver;
import com.move10x.totem.models.JsonHttpResponseHandler;
import com.move10x.totem.models.Url;
import com.move10x.totem.services.AsyncHttpService;
import com.move10x.totem.services.CurrentProfileService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class DriverOnMapFragment extends Fragment {

    private GoogleMap map;
    private MapView mapView;
    private HashMap<Marker, Driver> driverMarkers = new HashMap<Marker, Driver>();
    ProgressBar progressBar;

    View view;
    Context context;

    public DriverOnMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_driver_on_map, container, false);
        getActivity().setTitle("Drivers On Map");

        mapView = (MapView) view.findViewById(R.id.googleMapView);
        progressBar = (ProgressBar) view.findViewById(R.id.driversProgress);
        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        mapView.onCreate(savedInstanceState);
        MapsInitializer.initialize(this.getActivity());
        //Initialize map.
        map = mapView.getMap();

        //Fetch drivers of customer.
        showProgress(true);
        getDriverList();
        return view;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mapView.setVisibility(show ? View.GONE : View.VISIBLE);
            mapView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mapView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

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
            mapView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void getDriverList() {
        //Request Parameters.
        CurrentProfile currentProfile = new CurrentProfileService(getActivity().getApplicationContext()).getCurrentProfile();
        Log.d("driverFragment", "Fetch drivers for user: " + currentProfile.toString());
        String uid = currentProfile.getUserId();
        Log.d("driverFragment", "Fetch drivers for userId: " + uid);
        RequestParams loginParameters = new RequestParams();
        loginParameters.put("uid", uid);
        loginParameters.put("role", currentProfile.getUserType());
        loginParameters.put("tag", "vrm_get_drivers_1");

        //Async Driverlist fetch.
        AsyncHttpService.get(Url.apiBaseUrl, loginParameters, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray.
                Log.d("driverFragment", "Parsing getDriverList() response: " + response);
                try {

                    if (response.getString("success") != null && response.getString("success").equals("1")) {
                        JSONArray jsonDriverList = response.getJSONArray("drivers");
                        List<Driver> drivers = new ArrayList<Driver>();
                        for (int i = 0; i < jsonDriverList.length(); i++) {
                            drivers.add(Driver.decodeJsonForMap(jsonDriverList.getJSONObject(i)));
                        }

                        // Set load location to Mumbai and move the camera.
                        LatLng mumbai = new LatLng(19.114678, 72.904930);
                        CameraUpdate center =
                                CameraUpdateFactory.newLatLngZoom(mumbai, 10);
                        map.animateCamera(center);

                        //Add marker for each driver.
                        for (Driver driver : drivers) {
                            LatLng driverLoation = new LatLng(driver.getLattitude(), driver.getLongitude());
                            Marker marker = map.addMarker(new MarkerOptions().position(driverLoation).title(driver.getFirstName() + " " + driver.getLastName()));

                            //Set marker green if available.
                            if (driver.getDutyStatus() == Driver.DutyStatus_Available) {
                                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                            } else if (driver.getDutyStatus() != Driver.DutyStatus_Offduty) {
                                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                            }
                            marker.setAlpha((float) 0.8);
                            driverMarkers.put(marker, driver);

                            map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

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
                                    View v = ((LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE)).inflate(R.layout.googlemap_info_window, null);
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

                            map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                                @Override
                                public void onInfoWindowClick(Marker marker) {
                                    Log.d("driverOnMap", "show details popup.");
                                    Intent intent = new Intent(context, DriverDetailsActivity.class);
                                    Driver driver = driverMarkers.get(marker);
                                    intent.putExtra("driverUid", driver.getuId());
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    Log.d("driverOnMap", "Driver UID: " + driver.getuId());
                                    startActivity(intent);
                                }
                            });

                        }
                    }
                } catch (JSONException ex) {

                }
                showProgress(false);
            }

               /* @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                    // Pull out the first event on the public timeline
                    JSONObject firstEvent = timeline.get(0);
                    String tweetText = firstEvent.getString("text");

                    // Do something with the response
                    System.out.println(tweetText);
                }*/
        });
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
