package com.matrixdeveloper.tajika;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.matrixdeveloper.tajika.adapter.CoinsWalletAdapter;
import com.matrixdeveloper.tajika.adapter.MyBookingAdapter;
import com.matrixdeveloper.tajika.model.BookingModel;
import com.matrixdeveloper.tajika.model.CoinsWalletModel;

public class BookedFragment extends Fragment {

    RecyclerView rvBooked;
    MyBookingAdapter bookingAdapter;
    public BookedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_booked, container, false);
        BookingModel[] bookingModels = new BookingModel[]{
                new BookingModel("1", R.drawable.plumbing, "Danil Lawrenece", "Mayur Bihar, Dehli, India", "Catering","Booked"),
                new BookingModel("1", R.drawable.airtel_money, "Danil Lawrenece", "Mayur Bihar, Dehli, India", "Catering","cancelled"),
                new BookingModel("1", R.drawable.provider_image_2x, "Danil Lawrenece", "Mayur Bihar, Dehli, India", "Catering","Completed"),
                new BookingModel("1", R.drawable.flag_central_african_republic, "Danil Lawrenece", "Mayur Bihar, Dehli, India", "Catering","Booked"),
                new BookingModel("1", R.drawable.flag_american_samoa, "Danil Lawrenece", "Mayur Bihar, Dehli, India", "Catering","Cancelled"),
         };

        rvBooked=view.findViewById(R.id.rv_serviceBooked);
        bookingAdapter = new MyBookingAdapter(getContext(), bookingModels);
        rvBooked.setHasFixedSize(true);
        rvBooked.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvBooked.setAdapter(bookingAdapter);
        return view;
    }
}