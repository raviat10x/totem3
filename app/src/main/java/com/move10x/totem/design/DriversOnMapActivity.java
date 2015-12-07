package com.move10x.totem.design;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import java.util.Iterator;
import java.util.List;

import com.google.android.gms.maps.model.Marker;
import com.loopj.android.http.RequestParams;
import com.move10x.totem.R;
import com.move10x.totem.models.CurrentProfile;
import com.move10x.totem.models.Driver;
import com.move10x.totem.models.JsonHttpResponseHandler;
import com.move10x.totem.models.Url;
import com.move10x.totem.services.AsyncHttpService;
import com.move10x.totem.services.AsyncImageLoaderService;
import com.move10x.totem.services.CurrentProfileService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class DriversOnMapActivity extends Move10xActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private HashMap<Marker, Driver> driverMarkers = new HashMap<Marker, Driver>();
    ProgressBar progressBar;
    SupportMapFragment mapFragment;
    ListView driverList;
    List<Driver> availableDriverList;
    List<Driver> offDutyDriverList;
    List<Driver> onDutyDriverList;
    List<Driver> allDriverList;
    List<Driver> pendingDriverList;
    List<Driver> terminatedDriverList;
    private static final String TAG = "DriverOnMapActivity";


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sample, menu);
        return true;
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
        loginParameters.put("role", currentProfile.getUserType());
        loginParameters.put("tag", "vrm_get_drivers_1");

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
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    Log.d("driverOnMap", "Driver UID: " + driver.getuId());
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                }
                catch (JSONException ex) {
                    Toast.makeText(getBaseContext(), "Failed to load drivers.", Toast.LENGTH_LONG);
                }
                onFilterChange();

                showProgress(false);
            }


        });
    }

    private void onFilterChange() {
    Log.d(TAG, "Inside OnFilterChange");
        Iterator<Marker> iterator = driverMarkers.keySet().iterator();
        while(iterator.hasNext()){
            Marker m = iterator.next();
            m.setVisible(false);
            Driver d = driverMarkers.get(m);
            Log.d(TAG, "print drivers " +d.getWorkStatus());
            Log.d(TAG, "print drivers " +d.getDutyStatus());
            if (d.getWorkStatus().equals(Driver.WorkStatus_Terminated)) {
                Log.d(TAG, "Terminated drivers " +d.getWorkStatus());
                Log.d(TAG, "Terminated drivers " +d.getDutyStatus());

                m.setVisible(true);
            }
            else if (d.getWorkStatus().equals(Driver.DutyStatus_Pending_Verify)){
                m.setVisible(true);
            }  else if (d.getWorkStatus().equals(Driver.WorkStatus_Active)) {
                if (d.getDutyStatus().equals(Driver.DutyStatus_Available)) {
                    Log.d(TAG, "Inside Available");
                    m.setVisible(true);
                } else if (d.getDutyStatus().equals(Driver.DutyStatus_COMPLETE) || d.getDutyStatus().equals(Driver.DutyStatus_Outside) || d.getDutyStatus().equals(Driver.DutyStatus_TOWARDS_DROP) || d.getDutyStatus().equals(Driver.DutyStatus_TOWARDS_LOADING) || d.getDutyStatus().equals(Driver.DutyStatus_TOWARDS_TOPICKUP) || d.getDutyStatus().equals(Driver.DutyStatus_Training) || d.getDutyStatus().equals(Driver.DutyStatus_UNLOADING)) {
                    Log.d(TAG, "Inside All");
                    m.setVisible(true);
                } else if (d.getDutyStatus().equals(Driver.DutyStatus_Offduty)) {
                    Log.d(TAG, "Inside OffDuty");
                    m.setVisible(true);
                }
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionAvailable:
                Log.d(TAG, "actionAvailable");
                Iterator<Marker> iterator = driverMarkers.keySet().iterator();
                Log.d(TAG, "Available drivers : " +iterator);
                Marker m = iterator.next();
                m.setVisible(false);
                break;
            case R.id.actionOffDuty:
                Log.d(TAG, "actionOffDuty");
                Iterator<Marker> iterator1 = driverMarkers.keySet().iterator();
                Log.d(TAG, "OffDuty drivers : " +iterator1);
                Marker m1 = iterator1.next();
                m1.setVisible(false);
                break;
            case R.id.actionOnDuty:
                Log.d(TAG, "actionOnDuty");
                Iterator<Marker> iterator2 = driverMarkers.keySet().iterator();
                Log.d(TAG, "OnDuty drivers : " +iterator2);
                Marker m2 = iterator2.next();
                m2.setVisible(false);
                break;
            case R.id.actionPending:
                Log.d(TAG, "actionPending");
                Iterator<Marker> iterator3 = driverMarkers.keySet().iterator();
                Log.d(TAG, "Pending drivers : " +iterator3);
                Marker m3 = iterator3.next();
                m3.setVisible(false);
                break;
            case R.id.actionTerminated:
                Log.d(TAG, "actionTerminate");
                Iterator<Marker> iterator4 = driverMarkers.keySet().iterator();
                Log.d(TAG, "Terminated drivers : " +iterator4);
                Marker m4 = iterator4.next();
                m4.setVisible(false);
                break;
            case android.R.id.home:
                finish();
                break;
            default:
            case R.id.actionAllDrivers:
                Log.d(TAG, "actionAllDrivers");
                driverList.setAdapter(new DriverListAdapter(DriversOnMapActivity.this, allDriverList));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

public class DriverListAdapter extends BaseAdapter {

    private List<Driver> driverList;
    private LayoutInflater inflater = null;

    public DriverListAdapter(Context context, List<Driver> driverList) {
        // TODO Auto-generated constructor stub
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.driverList = driverList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return driverList.size();
    }

    @Override
    public Driver getItem(int position) {
        // TODO Auto-generated method stub
        return driverList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Driver currentDriver = this.driverList.get(position);
        View rowView = inflater.inflate(R.layout.driverlist, null);

        TextView txtDriverName = (TextView) rowView.findViewById(R.id.driverName);
        txtDriverName.setText(currentDriver.getFirstName() + " " + currentDriver.getLastName());
        Log.d("driverList", currentDriver.getFirstName() + " " + currentDriver.getLastName());

        TextView txtDriverPhoneNumber = ((TextView) rowView.findViewById(R.id.driverPhoneNumber));
        txtDriverPhoneNumber.setText(currentDriver.getMobileNo());
        Log.d("driverList", currentDriver.getMobileNo());

        TextView txtWorkStatus = ((TextView) rowView.findViewById(R.id.txtWorkStatus));
        TextView txtDutyStatus = ((TextView) rowView.findViewById(R.id.txtDutyStatus));
        txtWorkStatus.setText(currentDriver.getWorkStatus());
        txtDutyStatus.setText(currentDriver.getDutyStatus());
        txtDutyStatus.setVisibility(View.VISIBLE);

        Log.d("driverFragment", "Comparing driver Status: " + currentDriver.getWorkStatus().toUpperCase() + ", " + Driver.WorkStatus_Active);
        if (!currentDriver.getWorkStatus().equals(Driver.WorkStatus_Active)) {
            //IF driver not active, set color red for work and hide duty status.
            txtWorkStatus.setTextColor(getResources().getColor(R.color.red));
            txtDutyStatus.setVisibility(View.GONE);
        } else {
            //IF driver active, set work status green.
            txtWorkStatus.setVisibility(View.GONE);
            if (currentDriver.getDutyStatus().toUpperCase().equals(Driver.DutyStatus_Available)) {
                //IF driver active and avilable, set color to green.
                txtDutyStatus.setTextColor(getResources().getColor(R.color.green));
            } else if (currentDriver.getDutyStatus().toUpperCase().equals(Driver.DutyStatus_Offduty)) {
                //IF driver active and offduty, set color to red.
                txtDutyStatus.setTextColor(getResources().getColor(R.color.red));
            } else {
                txtDutyStatus.setTextColor(getResources().getColor(R.color.blue));
            }
        }

        //Set image circular
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imgDriverPhoto);
            /*Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim);
            imageView.startAnimation(fadeInAnimation);*/
        AsyncImageLoaderService task = new AsyncImageLoaderService(imageView, currentDriver.getImagePath());
        task.execute();

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        LinearLayout driverDetailsContainer = (LinearLayout) rowView.findViewById(R.id.driverDetailsContainer);
        driverDetailsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("driverFragment", "On Driver container click.");
                Intent intent = new Intent(DriversOnMapActivity.this, DriverDetailsActivity.class);
                intent.putExtra("driverUid", currentDriver.getuId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Log.d("driverOnMap", "Driver UID: " + currentDriver.getuId());
                startActivity(intent);
            }
        });
        return rowView;
    }
}
}
