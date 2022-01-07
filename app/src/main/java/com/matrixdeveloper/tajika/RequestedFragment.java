package com.matrixdeveloper.tajika;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.adapter.MyBookingAdapter;
import com.matrixdeveloper.tajika.adapter.UpcomingJobAdapter;
import com.matrixdeveloper.tajika.model.BookingModel;
import com.matrixdeveloper.tajika.model.ServiceRequestList;
import com.matrixdeveloper.tajika.model.UpcomingJob;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.MySingleton;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.RecyclerTouchListener;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RequestedFragment extends Fragment {

    RecyclerView rvRequested;
    MyBookingAdapter bookingAdapter;
    String TAG = "RequestedFrag";
    List<ServiceRequestList> requestLists;
    PrefManager pref;

    public RequestedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requested, container, false);

        rvRequested = view.findViewById(R.id.rv_serviceRequested);
        pref = new PrefManager(getContext());

        requestLists = new ArrayList<>();
        bookingAdapter = new MyBookingAdapter(getContext(), requestLists);
        rvRequested.setHasFixedSize(true);
        rvRequested.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvRequested.setAdapter(bookingAdapter);

        getRequestData();

        rvRequested.addOnItemTouchListener(new RecyclerTouchListener(getContext(), rvRequested, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ServiceRequestList serviceRequestList = requestLists.get(position);

                startActivity(new Intent(getContext(), BookingDetailsActivity.class)
                        .putExtra("status", serviceRequestList.getStatus())
                        .putExtra("booking_id", String.valueOf(serviceRequestList.getId())));

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return view;
    }

    private void getRequestData() {

        JSONObject data = new JSONObject();
        try {
            data.put("user_id", pref.getString("id"));
            data.put("type", "Pending");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(getContext(), ServiceNames.GET_SERVICE_REQUEST, data, response -> {

            Utils.log(TAG, response.toString());

            JSONArray requestArray;

            try {

                requestArray = response.getJSONArray("data");


                for (int i = 0; i < requestArray.length(); i++) {

                    try {

                        ServiceRequestList serviceList = MySingleton.getGson().fromJson(requestArray.getJSONObject(i).toString(), ServiceRequestList.class);
                        requestLists.add(serviceList);

                        bookingAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        });
    }
}