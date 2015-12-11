package com.move10x.totem.design;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.move10x.totem.R;
import com.move10x.totem.models.CurrentProfile;
import com.move10x.totem.models.Customer;
import com.move10x.totem.models.JsonHttpResponseHandler;
import com.move10x.totem.models.Url;
import com.move10x.totem.services.AsyncHttpService;
import com.move10x.totem.services.CurrentProfileService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class LeadFragment extends Fragment {


    private static final String TAG = "CustomerFragment";
    LinearLayout customerListContainer;

    ListView customerList;
    ProgressBar progressBar;
    FloatingActionButton floatingActionButton;
    View view;
    Context context;

    public LeadFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // TODO: Rename and change types and number of parameters
    public static LeadFragment newInstance(String param1, String param2) {
        LeadFragment fragment = new LeadFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        CurrentProfile currentProfile = new CurrentProfileService(getActivity().getApplicationContext()).getCurrentProfile();
        view = inflater.inflate(R.layout.fragment_lead, container, false);
        getActivity().setTitle("Leads");
        customerListContainer = (LinearLayout) view.findViewById(R.id.customerListContainer);
        customerList = (ListView) view.findViewById(R.id.customerList);
        progressBar = (ProgressBar) view.findViewById(R.id.driversProgress);

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingAddButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCustomerAddClick();
            }
        });


        showProgress(true);
        return view;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            Log.d(TAG, "Inside showProgress()");

            customerListContainer.setVisibility(show ? View.GONE : View.VISIBLE);
            customerListContainer.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    customerListContainer.setVisibility(show ? View.GONE : View.VISIBLE);
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
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            customerListContainer.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "At Resume.");
        getCustomerList();
    }

    public void onCustomerAddClick() {
        Log.d(TAG, "on Add driver click.");

        Intent intent = new Intent(getActivity().getApplicationContext(), NewLeadActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }

    private void getCustomerList() {

        //Request Parameters.
        CurrentProfile currentProfile = new CurrentProfileService(getActivity().getApplicationContext()).getCurrentProfile();
        Log.d(TAG, "Fetch Customers for user: " + currentProfile.toString());
        String uid = currentProfile.getUserId();
        Log.d(TAG, "Fetch Customers for userId: " + uid);
        RequestParams requestParameters = new RequestParams();
        requestParameters.put("crmId", currentProfile.getUserId());
        requestParameters.put("tag", "usersList");

        AsyncHttpService.post(Url.totemApiUrl, requestParameters, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray.
                Log.d(TAG, "Parsing getcustomerList() response:  " + response);
                try {
                    Log.d(TAG, "Inside try block: " + response);
                    if (response.getString("success") != null && response.getString("success").equals("1")) {
                        Log.d(TAG, "Inside Success");
                        JSONArray jsoncustomerList = response.getJSONArray("msg");
                        List<Customer> customers = new ArrayList<Customer>();
                        for (int i = 0; i < jsoncustomerList.length(); i++) {
                            customers.add(Customer.decodeJsonForList(jsoncustomerList.getJSONObject(i)));
                        }
                        customerList.setAdapter(new customerListAdapter(getActivity().getApplicationContext(), customers));
                    }
                } catch (JSONException ex) {
                    Log.d(TAG, "Inside try block");
                }
                Log.d(TAG, "After try catch block ProgressBar is going to be Invisible");
                showProgress(false);
                customerListContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    /**
     * Created by Ravi on 10/11/2015.
     */
    public class customerListAdapter extends BaseAdapter {

        private List<Customer> customerList;
        private LayoutInflater inflater = null;

        public customerListAdapter(Context context, List<Customer> customerList) {
            // TODO Auto-generated constructor stub
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.customerList = customerList;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return customerList.size();
        }

        @Override
        public Customer getItem(int position) {
            // TODO Auto-generated method stub
            return customerList.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Customer currentCustomer = this.customerList.get(position);
            View rowView = inflater.inflate(R.layout.leadlist, null);

            TextView txtCustomerName = (TextView) rowView.findViewById(R.id.customerName);
            Log.d(TAG, "Getting Cusotmers Name" +txtCustomerName);
            txtCustomerName.setText(currentCustomer.getFirstName() + " " + currentCustomer.getLastName());
            Log.d(TAG, "Getting Cusotmers Name" +currentCustomer.getFirstName() + " " + currentCustomer.getLastName());

            TextView txtCustomerPhoneNumber = ((TextView) rowView.findViewById(R.id.customerPhoneNumber));
            txtCustomerPhoneNumber.setText(currentCustomer.getMobile());
            Log.d(TAG, currentCustomer.getMobile());

            TextView txtCustArea = ((TextView) rowView.findViewById(R.id.txtCustomerArea));
            txtCustArea.setText(currentCustomer.getArea());
            Log.d(TAG, currentCustomer.getArea());

            Log.d(TAG, "Comparing Customer Status: " + currentCustomer.getFirstName() + " " + currentCustomer.getLastName() + " " + currentCustomer.getMobile() + " " + currentCustomer.getArea());
//            ImageView imageView = (ImageView) rowView.findViewById(R.id.imgDriverPhoto);

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });

            LinearLayout customerDetailsContainer = (LinearLayout) rowView.findViewById(R.id.customerDetailsContainer);
            customerDetailsContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "On Customer container click.");
                    Intent intent = new Intent(getActivity().getApplicationContext(), LeadDetailsActivity.class);
                    intent.putExtra("customerUid", currentCustomer.getUniqueId());
                    //intent.putExtra("customerUid", currentCustomer.getCustomerUid());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Log.d(TAG, "Customer UID: " + currentCustomer.getUniqueId());
                    startActivity(intent);
                }
            });
            return rowView;
        }
    }
}
