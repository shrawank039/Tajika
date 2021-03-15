package com.matrixdeveloper.tajika.adapter;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.maps.model.LatLng;
import com.matrixdeveloper.tajika.LocationSelectorActivity;
import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.location.PlaceApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.matrixdeveloper.tajika.LocationSelectorActivity.autoCompleteTextView;


public class PlaceAutoSuggestAdapter extends ArrayAdapter implements Filterable {

    ArrayList<String> results;
    int resource;
    Context context;
    String value;

    TextView locName;
    PlaceApi placeApi = new PlaceApi();

    public PlaceAutoSuggestAdapter(Context context, int resId) {
        super(context, resId);
        this.context = context;
        this.resource = resId;

    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public String getItem(int pos) {
        return results.get(pos);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.location_list, null);
        locName = view.findViewById(R.id.tv_seggested_Location);
        locName.setText(getItem(position));

        if (position==4){
            view.findViewById(R.id.location_lis_view).setVisibility(View.GONE);
        }

        // final LatLng latLng = new LatLng(132, 212);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = getItem(position);

                openPlace(getItem(position));
                autoCompleteTextView.setText("");
                if(value.contains(",")) {
                     autoCompleteTextView.setHint(value.substring(0, value.indexOf(",")));
                }else{
                    autoCompleteTextView.setHint(value);
                }
                results.clear();

            }
        });
        return view;
    }

    private void openPlace(String item) {
        //Toast.makeText(context, ""+value, Toast.LENGTH_SHORT).show();
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List addressList = geocoder.getFromLocationName(item, 1);
            if (addressList != null && addressList.size() > 0) {

                Address address = (Address) addressList.get(0);

                if (address.getLongitude() != 0 && address.getLatitude() != 0) {
                    ((LocationSelectorActivity) context).animateMarker(new LatLng(address.getLatitude(), address.getLongitude()));
                } else {
                    Toast.makeText(context, "No latlng", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (IOException e) {
            Toast.makeText(context, "Unable to connect to Geocoder", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    results = placeApi.autoComplete(constraint.toString());

                    filterResults.values = results;
                    filterResults.count = results.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }
}