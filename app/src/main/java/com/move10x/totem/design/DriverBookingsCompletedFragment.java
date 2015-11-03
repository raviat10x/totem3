package com.move10x.totem.design;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.move10x.totem.R;
import com.move10x.totem.models.Booking;

import java.util.List;


public class DriverBookingsCompletedFragment extends Fragment {

    private View view;
    private List<Booking> bookings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_driver_bookings_completed, container, false);
        ListView txtBookingList = (ListView) view.findViewById(R.id.completedBookingList);
        txtBookingList.setAdapter(new BookingListAdapter(getActivity().getApplicationContext(), bookings));
        return view;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

}
