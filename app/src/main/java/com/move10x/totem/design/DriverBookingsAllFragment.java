package com.move10x.totem.design;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.move10x.totem.R;
import com.move10x.totem.models.Booking;

import java.util.List;


public class DriverBookingsAllFragment extends Fragment {

    private View view;
    private List<Booking> bookings;

    public DriverBookingsAllFragment() {
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
        view = inflater.inflate(R.layout.fragment_driver_bookings_all, container, false);
        Log.d("allBookingFragment", "no of bookings: " + bookings.size());
        ListView txtBookingList = (ListView) view.findViewById(R.id.bookingList);
        txtBookingList.setAdapter(new BookingListAdapter(getActivity().getApplicationContext(), bookings));
        return view;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

}
