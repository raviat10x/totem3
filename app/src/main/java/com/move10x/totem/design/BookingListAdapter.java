package com.move10x.totem.design;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.move10x.totem.R;
import com.move10x.totem.models.Booking;
import com.move10x.totem.services.MiscService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ravi on 10/21/2015.
 */
public class BookingListAdapter extends BaseAdapter {

    private List<Booking> bookingList;
    private LayoutInflater inflater = null;
    private Context context;

    public BookingListAdapter(Context context, List<Booking> bookingList) {
        // TODO Auto-generated constructor stub
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.bookingList = bookingList;
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return bookingList.size();
    }

    @Override
    public Booking getItem(int position) {
        // TODO Auto-generated method stub
        return bookingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Booking currentBooking = this.bookingList.get(position);
        View rowView = inflater.inflate(R.layout.booking_list_adapter, null);

        //Set date, time
        Date pickupTime = new Date(), pickupDate = new Date();
        TextView txtBookingTime = (TextView)rowView.findViewById(R.id.txtBookingTime);
        try {
            pickupTime = new SimpleDateFormat("hh:mm:ss", Locale.UK).parse(currentBooking.getTime());
            pickupDate = new SimpleDateFormat("yyyy-MM-dd", Locale.UK).parse(currentBooking.getDate());
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        String time = new SimpleDateFormat("hh:mm aa", Locale.UK).format(pickupTime) + " " + new SimpleDateFormat("EEE, dd MMM y", Locale.UK).format(pickupDate);
        txtBookingTime.setText(time);

        //Set status.
        TextView txtStatus = (TextView)rowView.findViewById(R.id.txtBookingStatus);
        txtStatus.setText(currentBooking.getStatus());
        if(currentBooking.getStatus().toLowerCase().equals("complete")){
            txtStatus.setTextColor(context.getResources().getColor(R.color.green));
        }else if(currentBooking.getStatus().toLowerCase().equals("canceled")){
            txtStatus.setTextColor(context.getResources().getColor(R.color.red));
        }

        //Set reference
        TextView txtReference = (TextView)rowView.findViewById(R.id.txtReferenceId);
        txtReference.setText(currentBooking.getReference().toUpperCase());

        //set pickup
        MiscService miscService = new MiscService();
        TextView txtPickupLocation = (TextView)rowView.findViewById(R.id.txtPickupLocation);
        txtPickupLocation.setText(miscService.shortenAddress(currentBooking.getPickup()));

        //set drop
        TextView txtDropLocation = (TextView)rowView.findViewById(R.id.txtDropLocation);
        txtDropLocation.setText(miscService.shortenAddress(currentBooking.getDrop()));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("bookingListAdapter", "Open booking details.");
                Intent intent = new Intent(v.getContext().getApplicationContext(), BookingDetailsActivity.class);
                intent.putExtra("bookingRef", currentBooking.getReference());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);
            }
        });

        return rowView;
    }
}
