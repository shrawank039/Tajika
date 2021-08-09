package com.matrixdeveloper.tajika;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.adapter.MyBookingAdapter;
import com.matrixdeveloper.tajika.model.BookingModel;

public class RequestedFragment extends Fragment {

    RecyclerView rvRequested;
    MyBookingAdapter bookingAdapter;

    public RequestedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requested, container, false);
        rvRequested = view.findViewById(R.id.rv_serviceRequested);
        BookingModel[] bookingModels = new BookingModel[]{
                new BookingModel("1", R.drawable.provider_image_2x, "Danil Lawrenece", "Mayur Bihar, Dehli, India", "Catering", "Declined"),
                new BookingModel("1", R.drawable.flag_central_african_republic, "Danil Lawrenece", "Mayur Bihar, Dehli, India", "Catering", "Accepted"),
                new BookingModel("1", R.drawable.flag_american_samoa, "Danil Lawrenece", "Mayur Bihar, Dehli, India", "Catering", "Pending"),
                new BookingModel("1", R.drawable.plumbing, "Danil Lawrenece", "Mayur Bihar, Dehli, India", "Catering", "Accepted"),
                new BookingModel("1", R.drawable.airtel_money, "Danil Lawrenece", "Mayur Bihar, Dehli, India", "Catering", "Pending"),
        };
        bookingAdapter = new MyBookingAdapter(getContext(), bookingModels);
        rvRequested.setHasFixedSize(true);
        rvRequested.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvRequested.setAdapter(bookingAdapter);
        return view;
    }
}