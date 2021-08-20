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
import com.matrixdeveloper.tajika.model.ServiceRequestList;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.MySingleton;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookedFragment extends Fragment {

    RecyclerView rvBooked;
    MyBookingAdapter bookingAdapter;
    String TAG = "BookedFrag";
    List<ServiceRequestList> requestLists;
    PrefManager pref;

    public BookedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_booked, container, false);

        pref = new PrefManager(getContext());

        requestLists = new ArrayList<>();
        rvBooked=view.findViewById(R.id.rv_serviceBooked);
        bookingAdapter = new MyBookingAdapter(getContext(), requestLists);
        rvBooked.setHasFixedSize(true);
        rvBooked.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvBooked.setAdapter(bookingAdapter);
        getRequestData();

        return view;
    }

    private void getRequestData() {

        JSONObject data = new JSONObject();
        try {
            data.put("user_id", pref.getString("id"));
            data.put("type", "Booked");
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