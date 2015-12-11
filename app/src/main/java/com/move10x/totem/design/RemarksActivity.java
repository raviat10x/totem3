//package com.move10x.totem.design;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.move10x.totem.R;
//import com.move10x.totem.models.Remark;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class RemarksActivity extends Move10xActivity {
//
//    final private String logTag="RemarksActivity";
//    ListView remarksList;
//    LinearLayout remarksListContiner;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_remarks);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        setTitle("Remarks");
//
//        remarksList = (ListView) findViewById(R.id.remarksList);
//        remarksListContiner = (LinearLayout) findViewById(R.id.remarksListContainer);
//
//        getRemarks();
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Log.d("driverDetails", "onmenuitem click. " + item.getItemId() + "," + R.id.home);
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    //method to get the remarks for the driver
//    public void getRemarks() {
//        Log.d(logTag, "Inside get remarks");
//        List<Remark> remarks = new ArrayList<Remark>();
//        remarks.add(Remark.addNewRemark("he was not on time","2400hrs,wed"));
//
//        remarks.add(Remark.addNewRemark("not on time because of something","1000hrs,tue"));
//
//        remarks.add(Remark.addNewRemark("here is the third remark about something that is supposed to matter","7300hrs,fri"));
//
//        remarks.add(Remark.addNewRemark("fourth remark about something that doesn't matter at all","6600hrs,fri"));
//
//        remarksList.setAdapter(new remarksListAdapter(getApplicationContext(), remarks));
//        Log.d(logTag, "Inside get remarks" + remarks);
//    }
//
//    //adapter for the remarks list
//    public class remarksListAdapter extends BaseAdapter {
//
//        private List<Remark> remarksList;
//        private LayoutInflater inflater = null;
//
//        public remarksListAdapter(Context context, List<Remark> remarksList) {
//            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            this.remarksList = remarksList;
//        }
//
//        @Override
//        public int getCount() {
//            return remarksList.size();
//        }
//
//        @Override
//        public Remark getItem(int position) {
//            return remarksList.get(position);
//        }
//
//        @Override
//        public long getItemId(int postion) {
//            return postion;
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//            Remark currentRemark = remarksList.get(position);
//            View rowView = inflater.inflate(R.layout.remarks_list, null);
//
//            TextView extraRemark = ((TextView) rowView.findViewById(R.id.extraRemark));
//            extraRemark.setText(currentRemark.getRemark());
//            Log.d(logTag, "currentRemark" + currentRemark.getRemark());
//
//            TextView extraRemarkTime = ((TextView) rowView.findViewById(R.id.remarkTime));
//            extraRemarkTime.setText(currentRemark.getTime());
//            Log.d(logTag, "currentRemarkTime" + currentRemark.getTime());
//            return rowView;
///*
//            TextView extraRemark1 = ((TextView) rowView.findViewById(R.id.extraRemark1));
//            extraRemark1.setText(currentRemark);
//            Log.d(logTag, "currentRemark" + currentRemark);
//
//            TextView extraRemark2 = ((TextView) rowView.findViewById(R.id.extraRemark2));
//            extraRemark2.setText(currentRemark);
//            Log.d(logTag, "currentRemark" + currentRemark);
//
//            TextView extraRemark3 = ((TextView) rowView.findViewById(R.id.extraRemark3));
//            extraRemark3.setText(currentRemark);
//            Log.d(logTag, "currentRemark" + currentRemark);
//            */
//        }
//    }
//
//}
