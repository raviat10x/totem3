package com.move10x.totem.design;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.move10x.totem.R;
import com.move10x.totem.models.CurrentProfile;
import com.move10x.totem.services.CurrentProfileService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Move10xActivity
        implements DriverFragment.OnFragmentInteractionListener {

    FrameLayout fragementHolder;
    Spinner driverFilterSpinner;
    DrawerLayout drarwerLayout;
    ListView drawerListView;
    ArrayList<DrawerListItem> drawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("MOVE10X VRM Panel");

        //Init drawer layout.
        drarwerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerListView = (ListView) findViewById(R.id.left_drawer);
        //Set Adapter
        drawerList = getDrawerListItems();
        DrawerAdapter adapter = new DrawerAdapter(this, R.layout.drawer_list_item, drarwerLayout, drawerList);
        drawerListView.setAdapter(adapter);

        //Init Drawer Toggle.
        mDrawerToggle = new ActionBarDrawerToggle(this, drarwerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_close);
        drarwerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        //Load Profile
        Log.d("mainActivity", "Loading Home Fragement");
        fragementHolder = (FrameLayout) findViewById(R.id.fragementHolder);
        //driverFilterSpinner = (Spinner) findViewById(R.id.driverFilterSpinner);


        if(getIntent().getStringExtra("loadFragment") == null){
            //Load default fragment.
            Fragment fg = new HomeFragment();
            android.app.FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragementHolder, fg);
            fragmentTransaction.commit();
            Log.d("mainActivity", "Fragement Loaded Successfully.");
        }else {
            Log.d("mainActivigty", "Load fragment: " + getIntent().getStringExtra("loadFragment"));
            if (getIntent().getStringExtra("loadFragment").equals("drivers")) {
                Fragment fg = new DriverFragment();
                android.app.FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragementHolder, fg);
                fragmentTransaction.commit();
                Log.d("mainActivity", "Fragement Loaded Successfully.");
            }
        }
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = super.onCreateView(parent, name, context, attrs);

        return view;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        Log.d("mainActivity", "On Crate options menu.");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void finishActivity(){
        finish();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d("mainActivity", "At onFragmentInteraction of Home Fragememt.");
    }

    public ArrayList<DrawerListItem> getDrawerListItems() {
        ArrayList<DrawerListItem> drawerList = new ArrayList<DrawerListItem>();
        drawerList.add(new DrawerListItem("User", R.drawable.icon_account_circle));
        drawerList.add(new DrawerListItem("Home", R.drawable.icon_home));
        drawerList.add(new DrawerListItem("Drivers", R.drawable.icon_account_circle));

        drawerList.add(new DrawerListItem("Customers", R.drawable.icon_person_pin));

        drawerList.add(new DrawerListItem("DriversOnMap", R.drawable.icon_person_pin));
        //drawerList.add(new DrawerListItem("Activity", R.drawable.icon_history));
        drawerList.add(new DrawerListItem("Logout", R.drawable.icon_power_settings));
        return drawerList;
    }

    public class DrawerAdapter extends ArrayAdapter<DrawerListItem> {

        DrawerLayout drawer;
        Context context;
        List<DrawerListItem> drawerItemList;
        int layoutResID;

        public DrawerAdapter(Context context, int layoutResourceID, DrawerLayout drawer,
                             List<DrawerListItem> listItems) {
            super(context, layoutResourceID, listItems);
            this.context = context;
            this.drawerItemList = listItems;
            this.drawer = drawer;
            this.layoutResID = layoutResourceID;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            DrawerItemHolder drawerHolder;
            View view = convertView;

            if (view == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                drawerHolder = new DrawerItemHolder();

                view = inflater.inflate(layoutResID, parent, false);
                drawerHolder.ItemName = (TextView) view
                        .findViewById(R.id.drawer_itemName);
                drawerHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);
                drawerHolder.profileLayout = (LinearLayout) view.findViewById(R.id.profileLayout);
                drawerHolder.menuLayout = (LinearLayout) view.findViewById(R.id.menuLayout);
                drawerHolder.userName = (TextView) view.findViewById(R.id.drawer_userName);
                drawerHolder.userRole = (TextView) view.findViewById(R.id.drawer_userRole);
                //drawerHolder.userImage= (ImageView) view.findViewById(R.id.drawer_userImage);
                view.setTag(drawerHolder);

            } else {
                drawerHolder = (DrawerItemHolder) view.getTag();
            }

            DrawerListItem dItem = (DrawerListItem) this.drawerItemList.get(position);

            drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
                    dItem.getImgResID()));
            drawerHolder.ItemName.setText(dItem.getItemName());

            //Check if user item.
            if (dItem.getItemName().equals("User")) {
                //Show user section and hide MenuSection
                //LinearLayout profileLayout = view.drawerHolder;
                drawerHolder.profileLayout.setVisibility(View.VISIBLE);
                drawerHolder.menuLayout.setVisibility(View.GONE);

                CurrentProfile cr = new CurrentProfileService(view.getContext()).getCurrentProfile();
                drawerHolder.userName.setText(cr.getUserName());
                drawerHolder.userRole.setText(cr.getUserType().toUpperCase());
            } else {
                //set listener
                drawerHolder.menuLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DrawerListItem selectedItem = (DrawerListItem) drawerItemList.get(position);
                        Log.d("drawerClick", "drawer item clicked. Position: " + selectedItem);

                        if (selectedItem.getItemName().toLowerCase().equals("home")) {          //Home
                            Log.i("mainActivity", "on Home Button Click.");
                            Log.d("mainActivity", "Changing Fragement to Home Fragement");
                            fragementHolder = (FrameLayout) findViewById(R.id.fragementHolder);
                            Fragment fg = new HomeFragment();
                            android.app.FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragementHolder, fg).addToBackStack(null);
                            fragmentTransaction.commit();
                            Log.d("mainActivity", "Finished Changing Fragement to Home Fragement.");

                        } else if (selectedItem.getItemName().toLowerCase().equals("drivers")) {    //Drivers
                            Log.d("mainActivity", "Changing Fragement to Drivers Fragement");
                            fragementHolder = (FrameLayout) findViewById(R.id.fragementHolder);
                            Fragment fg = new DriverFragment();
                            android.app.FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragementHolder, fg).addToBackStack(null);
                            fragmentTransaction.commit();
                            Log.d("mainActivity", "Finished Changing Fragement to Drivers Fragement.");
                        }

                        else if (selectedItem.getItemName().toLowerCase().equals("customers")) {    //Drivers
                            Log.d("mainActivity", "Changing Fragement to Customers Fragement");
                            fragementHolder = (FrameLayout) findViewById(R.id.fragementHolder);
                            Fragment fg = new CustomerFragment();
                            android.app.FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragementHolder, fg).addToBackStack(null);
                            fragmentTransaction.commit();
                            Log.d("mainActivity", "Finished Changing Fragement to Customers Fragement.");
                        }

                        else if (selectedItem.getItemName().toLowerCase().equals("driversonmap")) {       //Drivers on map
                           /* Log.d("mainActivity", "Changing Fragement to Drivers Om Fragement");
                            fragementHolder = (FrameLayout) findViewById(R.id.fragementHolder);
                            Fragment fg = new DriverOnMapFragment();
                            android.app.FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragementHolder, fg).addToBackStack(null);
                            fragmentTransaction.commit();
                            Log.d("mainActivity", "Finished Changing Fragement to Drivers On Map Fragement.");*/
                            Log.d("mainActivity", "on Activity Button Click.");
                            Intent intent = new Intent(v.getContext(), DriversOnMapActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            v.getContext().startActivity(intent);
                        /*} else if (selectedItem.getItemName().toLowerCase().equals("activity")) {   //Activity
                            Log.d("mainActivity", "on Activity Button Click.");
                            Intent intent = new Intent(this, DriversOnMapActivity.class);
                            this.startActivity(intent);*/
                        } else if (selectedItem.getItemName().toLowerCase().equals("logout")) {
                            Log.d("mainActivity", "on Logout Button Click.");
                            // Clear profile.
                            new CurrentProfileService(getApplicationContext()).ClearProfile();
                            //Redirect to login button.
                            Intent intent = new Intent(v.getContext(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            v.getContext().startActivity(intent);
                            ((MainActivity) v.getContext()).finish();
                        }
                        drarwerLayout.closeDrawer(Gravity.LEFT);
                    }
                });
            }

            return view;
        }

        private class DrawerItemHolder {

            TextView ItemName;
            ImageView icon, userImage;
            LinearLayout profileLayout, menuLayout;
            TextView userName, userRole;
        }
    }

}

