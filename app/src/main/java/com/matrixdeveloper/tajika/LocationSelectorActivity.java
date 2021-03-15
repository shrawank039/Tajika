package com.matrixdeveloper.tajika;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.arsy.maps_library.MapRipple;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.matrixdeveloper.tajika.adapter.PlaceAutoSuggestAdapter;
import com.matrixdeveloper.tajika.location.LiveGpsTracker;
import com.matrixdeveloper.tajika.model.AddressBean;
import com.matrixdeveloper.tajika.model.PlaceBean;
import com.matrixdeveloper.tajika.utils.AppConstants;
import com.matrixdeveloper.tajika.widget.BottomSheetDialog;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class LocationSelectorActivity extends FragmentActivity
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerDragListener, View.OnClickListener,
        BottomSheetDialog.BottomSheetListener {
    private GoogleMap mMap;
    private MapRipple mapRipple;
    protected ProgressDialog mProgressDialog;
    TextView edtAddress;
    MaterialProgressBar progressBar;
    private LatLng mSelectedLatLng;
    private AddressBean mSelectedAddress = null;
    private FloatingActionButton confirmLocation;
    String cityName,areaName,countryName,stateName,postalCode,fullAddress;
    PlaceBean placeBean;
    private int locationSelect;
    private int searchType;
    private Location location;
    private String lati,longi;
   // private LocationViewModel locationViewModel;
    public static AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selector);

        confirmLocation = findViewById(R.id.confirm_location);
        confirmLocation.setOnClickListener(this);
        edtAddress = (TextView) findViewById(R.id.edt_location);
        progressBar = (MaterialProgressBar) findViewById(R.id.progressbar);

        searchType = getIntent().getIntExtra("search_type", 0);
      //  locationViewModel = new ViewModelProvider(this).get(LocationViewModel.class);

        /**
         * Initialize Places. For simplicity, the API key is hard-coded. In a production
         * environment we recommend using a secure mechanism to manage API keys.
         */

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.place_api_key));
            Log.d("initialize","running");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);


        autoCompleteTextView = findViewById(R.id.autocomplete);
        PlaceAutoSuggestAdapter adapter = new PlaceAutoSuggestAdapter(LocationSelectorActivity.this, R.layout.location_list);
        autoCompleteTextView.setAdapter(adapter);

    }


    public void showProgressBar(String message) {
        if (!isFinishing()) {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(LocationSelectorActivity.this);
                mProgressDialog.setCancelable(false);
            }
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        }
    }

    public void hideProgressBar() {
        if (!isFinishing()) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerDragListener(this);
        mMap.getUiSettings().setCompassEnabled(false);

        initLocation();
    }

    private void initLocation() {
        showProgressBar("Please wait");
        LiveGpsTracker.getInstance(LocationSelectorActivity.this, new LiveGpsTracker.LocationUpdate() {
            @Override
            public void onLocationFound(double latitide, double longitude) {
                hideProgressBar();
                animateMarker(new LatLng(latitide, longitude));
            }

            @Override
            public void OnLocationSettingNotFound() {
                Toast.makeText(LocationSelectorActivity.this, "Please enable location setting and retry", Toast.LENGTH_SHORT).show();
                hideProgressBar();
            }

            @Override
            public void onPopupPermission() {
                Toast.makeText(LocationSelectorActivity.this, "Please enable location permissions", Toast.LENGTH_SHORT).show();
                hideProgressBar();
            }
        }).initLocationUpdate();
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_location_on_black);
        assert background != null;
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        //  Drawable vectorDrawable = ContextCompat.getDrawable(context, R.drawable.ic_location_on_black);
        //  assert vectorDrawable != null;
        //vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        //  vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public void animateMarker(LatLng location) {
        this.mSelectedLatLng = location;

        progressBar.setVisibility(View.VISIBLE);
        edtAddress.setText("");
        mMap.clear();
        if (mapRipple == null) {
            mapRipple = new MapRipple(mMap, location, getApplicationContext());
        }
        if (mapRipple.isAnimationRunning()) {
            mapRipple.stopRippleMapAnimation();
        }
        mapRipple.withLatLng(location);
        mapRipple.withNumberOfRipples(2);
        mapRipple.withFillColor(Color.argb(50, 41, 128, 185));
        mapRipple.withStrokeColor(Color.rgb(230, 126, 34));
        mapRipple.withStrokewidth(10);      // 10dp
        mapRipple.withDistance(400);      // 2000 metres radius
        mapRipple.withRippleDuration(5000);    //12000ms
        mapRipple.withTransparency(0.5f);
        mapRipple.startRippleMapAnimation();
//        mMap.addMarker(new MarkerOptions().position(location).title("Current Location").
//                draggable(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)));
//        //resizing Bitmap image and setting to add marker
        /*  image to bitmap compressed */
        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.ic_marker);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 90, 150, true);


        mMap.addMarker(new MarkerOptions()
                .position(location)
                .title("Current Location")
                .draggable(true)
                .icon(bitmapDescriptorFromVector(this)));

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        final CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(location)      // Sets the center of the map to Mountain View
                .zoom(16)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(20)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder

//        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new
//                CameraPosition(location,(float)16, (float)30, (float)0)));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        },100);

      /*  GeocodingLocation locationAddress = new GeocodingLocation();
        locationAddress.getAddressFromLocation(location.latitude,location.longitude,
                getApplicationContext(), new GeocoderHandler());*/
        Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addresses;
        placeBean = new PlaceBean();
        try {
            addresses = gcd.getFromLocation(location.latitude,
                    location.longitude, 1);
            if (addresses.size() > 0) {
                progressBar.setVisibility(View.GONE);
                // Toast.makeText(this, "running "+mSelectedAddress.getAddress_1(), Toast.LENGTH_SHORT).show();
                areaName = addresses.get(0).getFeatureName();
                fullAddress=addresses.get(0).getAddressLine(0);
                cityName = addresses.get(0).getLocality();
                countryName = addresses.get(0).getCountryName();
                stateName = addresses.get(0).getAdminArea();
                postalCode = addresses.get(0).getPostalCode();

                mSelectedAddress = new AddressBean(location.latitude, location.longitude, fullAddress, "",
                        cityName, postalCode,
                        stateName, countryName);
                //  ((LogisticsApplication) getApplicationContext()).saveSelectedAddress(mAddress);
                edtAddress.setText(mSelectedAddress.getAddress_1());

                AppConstants.address=mSelectedAddress.getAddress_1();
                AppConstants.country=countryName;
                AppConstants.zip=postalCode;
                AppConstants.state=stateName;
                AppConstants.city=cityName;

                locationSelect = AppConstants.LOCATION_SELECTED_ONITEMCLICK;

                placeBean.setAddress(String.valueOf(mSelectedAddress.getAddress_1()));
                placeBean.setLatitude(String.valueOf(location.latitude));
                placeBean.setLongitude(String.valueOf(location.longitude));
                lati=String.valueOf(location.latitude);
                longi=String.valueOf(location.longitude);
                placeBean.setName( fullAddress);

            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onMapClick(LatLng latLng) {
        animateMarker(latLng);
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        //animateMarker(marker.getPosition());
        mapRipple.stopRippleMapAnimation();
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        //animateMarker(marker.getPosition());
        mapRipple.stopRippleMapAnimation();
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        animateMarker(marker.getPosition());
    }

    public void getcurrentlocation(View view) {
        initLocation();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_location:
                if (mSelectedLatLng != null) {

                    final String note_id = UUID.randomUUID().toString();
                 //   location = new Location(note_id, mSelectedAddress.getAddress_1(), lati, longi);

                    Intent intent = new Intent();
                    intent.putExtra("location", placeBean);
                    intent.putExtra("locationSelect", locationSelect);
                    setResult(RESULT_OK, intent);
                    finish();

//                //    Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
//                    BottomSheetDialog bottomSheet = new BottomSheetDialog();
//                   // bottomSheet.setCancelable(false);
//                    FrameLayout bottomSheett =
//                            findViewById(com.google.android.material.R.id.design_bottom_sheet);
//                    if (null != bottomSheett) {
//                        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheett);
//                        behavior.setHideable(false);
//                    }
//
//                    bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
                }
                break;
        }
    }

    @Override
    public void onButtonClicked(String text) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //  initLocation();
        //  Toast.makeText(this, "Resume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}