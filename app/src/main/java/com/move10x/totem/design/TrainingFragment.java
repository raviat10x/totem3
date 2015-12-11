//package com.move10x.totem.design;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.app.Fragment;
//import android.support.design.widget.FloatingActionButton;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.LinearLayout;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.loopj.android.http.RequestParams;
//import com.move10x.totem.R;
//import com.move10x.totem.models.CurrentProfile;
//import com.move10x.totem.models.Customer;
//import com.move10x.totem.models.Driver;
//import com.move10x.totem.models.JsonHttpResponseHandler;
//import com.move10x.totem.models.Url;
//import com.move10x.totem.services.AsyncHttpService;
//import com.move10x.totem.services.CurrentProfileService;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import cz.msebera.android.httpclient.Header;
//
//public class TrainingFragment extends Fragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//    private static final String TAG = "TrainingFragment";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
////    private OnFragmentInteractionListener mListener;
//
//    View view;
//    private FloatingActionButton floatingActionButton;
//    private LinearLayout trainingListContainer;
//    private ListView trainingList;
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment TrainingFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static TrainingFragment newInstance(String param1, String param2) {
//        TrainingFragment fragment = new TrainingFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    public TrainingFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//
//        view = inflater.inflate(R.layout.fragment_training, container, false);
//        getActivity().setTitle("Training List");
//        floatingActionButton = (FloatingActionButton)view.findViewById(R.id.floatingAddButton);
//        trainingListContainer = (LinearLayout) view.findViewById(R.id.trainingListContainer);
//        trainingList = (ListView) view.findViewById(R.id.trainingList);
//
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addTrainings();
//            }
//        });
//
//        getActivity().getIntent().getStringExtra("name");
//        return view;
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        getCompletedTrainingList();
//    }
//
//    private void addTrainings() {
//        Log.d("driverFragment", "on Add driver click.");
//        Intent intent = new Intent(getActivity().getApplicationContext(), AddTrainingActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        this.startActivity(intent);
//    }
//
//    public void getCompletedTrainingList() {
//
//        CurrentProfile currentProfile = new CurrentProfileService(getActivity().getApplicationContext()).getCurrentProfile();
//        Log.d(TAG, "Fetch Customers for user: " + currentProfile.toString());
//        String uid = currentProfile.getUserId();
//        Log.d(TAG, "Fetch Customers for userId: " + uid);
//        RequestParams requestParameters = new RequestParams();
//        requestParameters.put("crmId", currentProfile.getUserId());
//        requestParameters.put("tag", "usersList");
//
//        AsyncHttpService.post(Url.totemApiUrl, requestParameters, new JsonHttpResponseHandler() {
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                // If the response is JSONObject instead of expected JSONArray.
//                Log.d(TAG, "Parsing getcustomerList() response:  " + response);
//                try {
//                    Log.d(TAG, "Inside try block: " + response);
//                    if (response.getString("success") != null && response.getString("success").equals("1")) {
//                        Log.d(TAG, "Inside Success");
//                        JSONArray jsoncustomerList = response.getJSONArray("msg");
//                        List<Driver> drivers = new ArrayList<Driver>();
//                        for (int i = 0; i < jsoncustomerList.length(); i++) {
//                            drivers.add(Driver.decodeJsonForList(jsoncustomerList.getJSONObject(i)));
//                        }
//                        trainingList.setAdapter(new trainingListAdapter(getActivity().getApplicationContext(), drivers));
//                    }
//                } catch (JSONException ex) {
//                    Log.d(TAG, "Inside try block");
//                }
//                Log.d(TAG, "After try catch block ProgressBar is going to be Invisible");
//                trainingListContainer.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//            }
//        });
//
//    }
//
//    public class trainingListAdapter extends BaseAdapter {
//
//        private List<Driver> trainingList;
//        private LayoutInflater inflater = null;
//
//        public trainingListAdapter(Context context, List<Driver> customerList) {
//            // TODO Auto-generated constructor stub
//            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            this.trainingList = trainingList;
//        }
//
//        @Override
//        public int getCount() {
//            // TODO Auto-generated method stub
//            return trainingList.size();
//        }
//
//        @Override
//        public Driver getItem(int position) {
//            // TODO Auto-generated method stub
//            return trainingList.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            // TODO Auto-generated method stub
//            return position;
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//            final Driver currentDriver = this.trainingList.get(position);
//            View rowView = inflater.inflate(R.layout.traininglist, null);
//
//            TextView txtDriverName = (TextView) rowView.findViewById(R.id.driverName);
//            txtDriverName.setText(currentDriver.getFirstName() + " " + currentDriver.getLastName());
//            Log.d(TAG, currentDriver.getFirstName() + " " + currentDriver.getLastName());
//
//            TextView txtMobile = ((TextView) rowView.findViewById(R.id.driverPhoneNumber));
//            txtMobile.setText(currentDriver.getMobileNo());
//            Log.d(TAG, currentDriver.getMobileNo());
//
//            TextView txtRemarks = ((TextView) rowView.findViewById(R.id.txtRemarks));
//            txtRemarks.setText(currentDriver.getRemarks());
//            Log.d(TAG, currentDriver.getRemarks());
//
//            Log.d(TAG, "Comparing Customer Status: " + currentDriver.getFirstName() + " " + currentDriver.getMobileNo() + " " + currentDriver.getRemarks());
////            ImageView imageView = (ImageView) rowView.findViewById(R.id.imgDriverPhoto);
//
//            rowView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                }
//            });
//
////            LinearLayout customerDetailsContainer = (LinearLayout) rowView.findViewById(R.id.customerDetailsContainer);
////            customerDetailsContainer.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    Log.d(TAG, "On Customer container click.");
////                    Intent intent = new Intent(getActivity().getApplicationContext(), LeadDetailsActivity.class);
////                    intent.putExtra("customerUid", currentCustomer.getUniqueId());
////                    //intent.putExtra("customerUid", currentCustomer.getCustomerUid());
////                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                    Log.d(TAG, "Customer UID: " + currentCustomer.getUniqueId());
////                    startActivity(intent);
////                }
////            });
//            return rowView;
//        }
//    }
//}
