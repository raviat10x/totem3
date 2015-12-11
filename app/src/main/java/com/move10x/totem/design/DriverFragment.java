package com.move10x.totem.design;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
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
import org.w3c.dom.Text;

import java.net.URISyntaxException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import cz.msebera.android.httpclient.Header;

public class DriverFragment extends Fragment {

    private static final String TAG = "DriverFragment";
    LinearLayout driverListContainer;

    public String currentStatus = "Pending";

    List<Driver> availableDriverList;
    List<Driver> offDutyDriverList;
    List<Driver> activeDriverList;
    List<Driver> onDutyDriverList;
    List<Driver> allDriverList;
    List<Driver> pendingDriverList;
    List<Driver> terminatedDriverList;
    List<Driver> drivers;
    List<Driver> allExceptTerminatedDriverList;

    ListView driverList;
    ProgressBar progressBar;
    FloatingActionButton floatingActionButton;
    View view;
    Context context;

    private OnFragmentInteractionListener mListener;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DriverFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DriverFragment newInstance(String param1, String param2) {
        DriverFragment fragment = new DriverFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public DriverFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        CurrentProfile currentProfile = new CurrentProfileService(getActivity().getApplicationContext()).getCurrentProfile();
        view = inflater.inflate(R.layout.fragment_driver, container, false);
        getActivity().setTitle("Drivers");
        driverListContainer = (LinearLayout) view.findViewById(R.id.driverListContainer);
        driverList = (ListView) view.findViewById(R.id.driverList);
//        txtDutyStatus = (TextView)view.findViewById(R.id.txtDutyStatus);
        progressBar = (ProgressBar) view.findViewById(R.id.driversProgress);

        availableDriverList = new ArrayList<Driver>();
        offDutyDriverList = new ArrayList<Driver>();
        activeDriverList = new ArrayList<Driver>();
        onDutyDriverList = new ArrayList<Driver>();
        allDriverList = new ArrayList<Driver>();
        pendingDriverList = new ArrayList<Driver>();
        terminatedDriverList = new ArrayList<Driver>();
        allExceptTerminatedDriverList = new ArrayList<Driver>();

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingAddButton);
        if (currentProfile.getUserType().equals("VRM")) {
            floatingActionButton.setVisibility(View.VISIBLE);
        } else {
            floatingActionButton.setVisibility(View.GONE);
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDriverAddClick();
            }
        });


        fetchPendingDriverlist(currentStatus);

        //Fetch drivers of customer.
        showProgress(true);
        getDriverList();

        setHasOptionsMenu(true);

        return view;
    }

    private void fetchPendingDriverlist(String currentStatus) {
        Log.d(TAG, "Inside fetchPendingDriverList");
//        if (currentStatus.equals("Pending")) {
        driverList.setAdapter(new DriverListAdapter(getActivity().getApplicationContext(), pendingDriverList));
//        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public String getTimeString(Driver driver) {
        Log.d(TAG, "google time: " + driver.getOnLineTime() + ", Seconds: " + driver.getOnLineTime());

        //Parse driver time.
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");

        Date driverDate = new Date();
        try {
            driverDate = dateFormat.parse(driver.getOnLineTime());
            Log.d(TAG, "driverDate : " + dateFormat.format(driverDate));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//Get current time

        Date dt = Calendar.getInstance().getTime();
        Log.d(TAG, "dt value : " +dt.getTime());
        Log.d(TAG, "driverDate value : " +driverDate.getTime());

        long millSeconds =  (dt.getTime() - driverDate.getTime());
        Log.d(TAG, "MilliSeconds : " +millSeconds);
        long seconds =  millSeconds/1000;
        Log.d(TAG, "Seconds : " +seconds);
        long minutes = seconds / 60;
        Log.d(TAG, "Minutes : " +minutes);
        long hours = minutes / 60;
        Log.d(TAG, "Hours : " +hours);
        long days = hours / 24;
        Log.d(TAG, "Days : " +days);
        String response = "";
        if (hours >= 1) {
            response += hours + " hr ";
        }
        response += (minutes + 5) + " min";
        return response;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            driverListContainer.setVisibility(show ? View.GONE : View.VISIBLE);
            driverListContainer.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    driverListContainer.setVisibility(show ? View.GONE : View.VISIBLE);
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
            driverListContainer.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public void updateText(String text) {
        Log.d(TAG, "Inside updateText");
        driverList.setAdapter(new DriverListAdapter(getActivity().getApplicationContext(), pendingDriverList));
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


    public void onDriverAddClick() {
        Log.d(TAG, "on Add driver click.");
        Intent intent = new Intent(getActivity().getApplicationContext(), NewDriverActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d(TAG, "onCreateOptionsMenu");
        inflater.inflate(R.menu.menu_sample, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionAvailable:
                Log.d(TAG, "actionAvailable");
                driverList.setAdapter(new DriverListAdapter(getActivity().getApplicationContext(), availableDriverList));

                break;
            case R.id.actionOffDuty:
                Log.d(TAG, "actionOffDuty");
                driverList.setAdapter(new DriverListAdapter(getActivity().getApplicationContext(), offDutyDriverList));
                break;
            case R.id.actionOnDuty:
                Log.d(TAG, "actionOnDuty");
                driverList.setAdapter(new DriverListAdapter(getActivity().getApplicationContext(), onDutyDriverList));
                break;
            case R.id.actionActive:
                Log.d(TAG, "actionActive");
                driverList.setAdapter(new DriverListAdapter(getActivity().getApplicationContext(), activeDriverList));
                break;
            case R.id.actionPending:
                Log.d(TAG, "actionPending");
                driverList.setAdapter(new DriverListAdapter(getActivity().getApplicationContext(), pendingDriverList));
                break;
            case R.id.actionTerminated:
                Log.d(TAG, "actionTerminate");
                driverList.setAdapter(new DriverListAdapter(getActivity().getApplicationContext(), terminatedDriverList));
                break;
//            default:
            case R.id.actionAllDrivers:
                Log.d(TAG, "actionAllDrivers");
                driverList.setAdapter(new DriverListAdapter(getActivity().getApplicationContext(), allDriverList));
                break;
            default:
                Log.d(TAG, "allExceptTerminatedDriverList");
                driverList.setAdapter(new DriverListAdapter(getActivity().getApplicationContext(), allExceptTerminatedDriverList));
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void getDriverList() {
        //Request Parameters.
        CurrentProfile currentProfile = new CurrentProfileService(getActivity().getApplicationContext()).getCurrentProfile();
        Log.d(TAG, "Fetch drivers for user: " + currentProfile.toString());
        String uid = currentProfile.getUserId();
        Log.d(TAG, "Fetch drivers for userId: " + uid);
        RequestParams loginParameters = new RequestParams();
        loginParameters.put("uid", uid);
        Log.d(TAG, "uid is  : " + uid);
        loginParameters.put("role", currentProfile.getUserType());
        loginParameters.put("tag", "vrm_get_drivers_1");

        //Async Driverlist fetch.
        AsyncHttpService.get(Url.apiBaseUrl, loginParameters, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray.
                Log.d("driverFragment", "Parsing getDriverList() response. ");
                try {
                    if (response.getString("success") != null && response.getString("success").equals("1")) {
                        JSONArray jsonDriverList = response.getJSONArray("drivers");
                        List<Driver> drivers = new ArrayList<Driver>();
                        Log.d(TAG, "Inside ListView");
                        for (int i = 0; i < jsonDriverList.length(); i++) {
                            Driver temp = Driver.decodeJsonForList(jsonDriverList.getJSONObject(i));
                            Log.d(TAG, "Temp");


                            if (temp.getWorkStatus().equals(Driver.WorkStatus_Terminated)) {
                                Log.d(TAG, "Inside Terminate");
                                terminatedDriverList.add(temp);

                            }
//                            else if ((temp.getWorkStatus().equals(Driver.DutyStatus_Pending_Verify)) || (temp.getWorkStatus().equals(Driver.WorkStatus_Active))) {
//                                allExceptTerminatedDriverList.add(temp);
//                            }
                            else if (temp.getWorkStatus().equals(Driver.DutyStatus_Pending_Verify)) {
                                Log.d(TAG, "Inside Pending");
                                pendingDriverList.add(temp);
                            } else if (temp.getWorkStatus().equals(Driver.WorkStatus_Active)) {
                                activeDriverList.add(temp);

                                if (temp.getDutyStatus().equals(Driver.DutyStatus_Available)) {
                                    Log.d(TAG, "Inside Available");
                                    availableDriverList.add(temp);
                                } else if (temp.getDutyStatus().equals(Driver.DutyStatus_COMPLETE) || temp.getDutyStatus().equals(Driver.DutyStatus_Outside) || temp.getDutyStatus().equals(Driver.DutyStatus_TOWARDS_DROP) || temp.getDutyStatus().equals(Driver.DutyStatus_TOWARDS_LOADING) || temp.getDutyStatus().equals(Driver.DutyStatus_TOWARDS_TOPICKUP) || temp.getDutyStatus().equals(Driver.DutyStatus_Training) || temp.getDutyStatus().equals(Driver.DutyStatus_UNLOADING)) {
                                    Log.d(TAG, "Inside All");
                                    onDutyDriverList.add(temp);
                                } else if (temp.getDutyStatus().equals(Driver.DutyStatus_Offduty)) {
                                    Log.d(TAG, "Inside OffDuty");
                                    offDutyDriverList.add(temp);
                                }
//                                activeDriverList.add(temp);
                            } else Log.d(TAG, "Inside Outside");

                            allDriverList.add(temp);

                            drivers.add(Driver.decodeJsonForList(jsonDriverList.getJSONObject(i)));
                        }
                        Bundle arguments = getArguments();
                        if (arguments != null && arguments.containsKey("pending")) {
                            String userId = arguments.getString("pending");
                            Log.d(TAG, "Getting data from bundle");
                            displayPendingDrivers();
                        } else if (arguments != null && arguments.containsKey("offduty")) {
                            String userId = arguments.getString("offduty");
                            Log.d(TAG, "Getting data from bundle");
                            displayOffDutyDrivers();
                        } else if (arguments != null && arguments.containsKey("onduty")) {
                            String userId = arguments.getString("onduty");
                            Log.d(TAG, "Getting data from bundle");
                            displayOnDutyDrivers();
                        } else if (arguments != null && arguments.containsKey("active")) {
                            String userId = arguments.getString("active");
                            Log.d(TAG, "Getting data from bundle");
                            displayActiveDrivers();
                        } else if (arguments != null && arguments.containsKey("available")) {
                            String userId = arguments.getString("available");
                            Log.d(TAG, "Getting data from bundle");
                            displayAvailableDrivers();
                        } else if (arguments != null && arguments.containsKey("terminated")) {
                            String userId = arguments.getString("terminated");
                            Log.d(TAG, "Getting data from bundle");
                            displayTerminatedDrivers();
                        } else {
                            driverList.setAdapter(new DriverListAdapter(getActivity().getApplicationContext(), drivers));
                        }

                    }
                } catch (JSONException ex) {

                }
                showProgress(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void displayPendingDrivers() {
        Log.i(TAG, "displayPendingDrivers");
        driverList.setAdapter(new DriverListAdapter(getActivity().getApplicationContext(), pendingDriverList));
    }

    public void displayAvailableDrivers() {
        Log.i(TAG, "displayAvailableDrivers");
        driverList.setAdapter(new DriverListAdapter(getActivity().getApplicationContext(), availableDriverList));
    }

    public void displayOnDutyDrivers() {
        Log.i(TAG, "displayOnDutyDrivers");
        driverList.setAdapter(new DriverListAdapter(getActivity().getApplicationContext(), onDutyDriverList));
    }

    public void displayActiveDrivers() {
        Log.i(TAG, "displayActiveDrivers");
        driverList.setAdapter(new DriverListAdapter(getActivity().getApplicationContext(), activeDriverList));
    }

    public void displayAllExceptTerminatedDriverList() {
        Log.i(TAG, "displayTerminatedDrivers");
        driverList.setAdapter(new DriverListAdapter(getActivity().getApplicationContext(), allExceptTerminatedDriverList));
    }

    public void displayOffDutyDrivers() {
        Log.i(TAG, "displayOffDutyDrivers");
        driverList.setAdapter(new DriverListAdapter(getActivity().getApplicationContext(), offDutyDriverList));
    }

    public void displayTerminatedDrivers() {
        Log.i(TAG, "displayTerminatedDrivers");
        driverList.setAdapter(new DriverListAdapter(getActivity().getApplicationContext(), terminatedDriverList));
    }

    /**
     * Created by Ravi on 10/11/2015.
     */
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
            Log.d(TAG, currentDriver.getFirstName() + " " + currentDriver.getLastName());

            TextView txtDriverPhoneNumber = ((TextView) rowView.findViewById(R.id.driverPhoneNumber));
            txtDriverPhoneNumber.setText(currentDriver.getMobileNo());
            Log.d(TAG, currentDriver.getMobileNo());

            TextView txtOnlineTime = ((TextView) rowView.findViewById(R.id.txtPing));
            Log.d(TAG, getTimeString(currentDriver));
            txtOnlineTime.setText(getTimeString(currentDriver));
            Log.d(TAG, getTimeString(currentDriver));

            TextView txtWorkStatus = ((TextView) rowView.findViewById(R.id.txtWorkStatus));
            TextView txtDutyStatus = ((TextView) rowView.findViewById(R.id.txtDutyStatus));
            txtWorkStatus.setText(currentDriver.getWorkStatus());
            txtDutyStatus.setText(currentDriver.getDutyStatus());
            txtDutyStatus.setVisibility(View.VISIBLE);

            Log.d(TAG, "Comparing driver Status: " + currentDriver.getWorkStatus().toUpperCase() + ", " + Driver.WorkStatus_Active);
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
                    Log.d(TAG, "On Driver container click.");
                    Intent intent = new Intent(getActivity().getApplicationContext(), DriverDetailsActivity.class);
                    intent.putExtra("driverUid", currentDriver.getuId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Log.d(TAG, "Driver UID: " + currentDriver.getuId());
                    startActivity(intent);
                }
            });
            return rowView;
        }
    }
}