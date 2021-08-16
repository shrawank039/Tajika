package com.matrixdeveloper.tajika;

import static com.matrixdeveloper.tajika.utils.Global.compareAddOne;
import static com.matrixdeveloper.tajika.utils.Global.compareAddOneID;
import static com.matrixdeveloper.tajika.utils.Global.compareAddTwo;
import static com.matrixdeveloper.tajika.utils.Global.compareAddTwoID;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.matrixdeveloper.tajika.adapter.SubscriptionAdapter;
import com.matrixdeveloper.tajika.location.LiveGpsTracker;
import com.matrixdeveloper.tajika.model.AddressBean;
import com.matrixdeveloper.tajika.model.PlaceBean;
import com.matrixdeveloper.tajika.model.ServiceProvider;
import com.matrixdeveloper.tajika.model.ServiceProviderDetails;
import com.matrixdeveloper.tajika.model.SubscriptionModel;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.MySingleton;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;
import com.matrixdeveloper.tajika.widget.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationSelectorActivity extends FragmentActivity
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener,
        BottomSheetDialog.BottomSheetListener {
    private GoogleMap mMap;
    //   private MapRipple mapRipple;
    TextView edtAddress, requestService;
    private LatLng mSelectedLatLng;
    private AddressBean mSelectedAddress = null;
    String cityName, areaName, countryName, stateName, postalCode, fullAddress;
    PlaceBean placeBean;
    private Location location;
    private String selected_id;
    private ImageView gotoCurrentLocation, backPress;
    private LinearLayout searchProviderCategory, viewDetails, noProviderFound, providerDetails, moreDetailsGoods, moreDetailsService, recommendedService, inner;
    private View view, view1;
    ArrayList<LatLng> pointer = new ArrayList<>();
    private List<ServiceProvider> serviceProviderList;
    private String TAG = "LocationSelectorAct";
    private BottomSheetBehavior behavior, behavior2;
    private String service_name, service_id, service_type;
    int height;
    private ImageView img;
    private TextView edtSearch, recommendedByTajika;
    private int peekHeight = 0;
    private TextView txtProviderName, txtRating, txtServiceName, txtDistance, txtAbout, txtJobComp, txtEdu, txtAdd, txtSkill, compare;
    ServiceProviderDetails serviceProviderDetails;
    private CardView jointToProvideService, referToProvideService;
    PrefManager pref;
    Button btnGetDetailsGoods, btnChat, btnRequestService;
    private RecyclerView recSubscription;
    private LinearLayout parentTwoService, parentOneService, parentOneGoods;
    int availableHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selector);

        pref = new PrefManager(this);

        service_name = getIntent().getStringExtra("service_name");
        service_id = getIntent().getStringExtra("service_id");
        service_type = getIntent().getStringExtra("service_type");
        edtAddress = findViewById(R.id.edt_location);
        inner = findViewById(R.id.inner);
        gotoCurrentLocation = findViewById(R.id.iv_gotoCurrentLocation);
        backPress = findViewById(R.id.iv_backPress);
        requestService = findViewById(R.id.txt_requestService);
        //view = findViewById(R.id.view_viewDetails);
        moreDetailsGoods = findViewById(R.id.ll_moreDetailsGoods);
        moreDetailsService = findViewById(R.id.ll_moreDetailsService);
        recommendedService = findViewById(R.id.ll_recommendedService);
        edtSearch = findViewById(R.id.edt_search);
        edtSearch.setText(service_name);
        searchProviderCategory = findViewById(R.id.ll_searchProviderCategory);
        //      llBsGoodsProvider = findViewById(R.id.bottom_sheetGoods);
        //      llBsServiceProvider = findViewById(R.id.bottom_sheetService);

        //bottom sheet
        viewDetails = findViewById(R.id.ll_viewDetails);
        noProviderFound = findViewById(R.id.ll_noProviderFound);
        providerDetails = findViewById(R.id.ll_providerDetails);
        txtProviderName = findViewById(R.id.txt_provider_name);
        txtRating = findViewById(R.id.txt_rating);
        compare = findViewById(R.id.txt_addToCompare);
        txtServiceName = findViewById(R.id.txt_service_name);
        txtDistance = findViewById(R.id.txt_distance);
        txtAbout = findViewById(R.id.txt_about);
        txtJobComp = findViewById(R.id.txt_jobs_completed);
        txtEdu = findViewById(R.id.txt_education);
        txtAdd = findViewById(R.id.txt_address);
        txtSkill = findViewById(R.id.txt_skill);
        jointToProvideService = findViewById(R.id.cv_joinToProvideService);
        referToProvideService = findViewById(R.id.cv_referToProvideService);
        btnChat = findViewById(R.id.btn_chat);
        btnRequestService = findViewById(R.id.btn_requestService);
        btnGetDetailsGoods = findViewById(R.id.btn_getDetailsGoods);
        recSubscription = findViewById(R.id.rv_subscription);
        parentTwoService = findViewById(R.id.ll_twoService);
        recommendedByTajika = findViewById(R.id.txt_recommendedByTajika);
        parentOneService = findViewById(R.id.ll_oneService);
        parentOneGoods = findViewById(R.id.ll_oneGoods);

        serviceProviderList = new ArrayList<>();

        View bottomSheet = findViewById(R.id.bottom_sheet_service);
        behavior = BottomSheetBehavior.from(bottomSheet);
        View bottomSheet2 = findViewById(R.id.bottom_sheet_goods);
        behavior2 = BottomSheetBehavior.from(bottomSheet2);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.place_api_key));
            Log.d("initialize", "running");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        initListeners();

        if (service_type.equals("goods")) {
            bottomSheet.setVisibility(View.GONE);
        } else if (service_type.equals("service")) {
            bottomSheet2.setVisibility(View.GONE);
        }

        parentOneService.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                availableHeight = parentOneService.getMeasuredHeight();
                if (availableHeight > 0) {
                    parentOneService.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    peekHeight = availableHeight;
                    //save height here and do whatever you want with it
                }
            }
        });
        parentTwoService.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                availableHeight = parentTwoService.getMeasuredHeight() + recommendedByTajika.getMeasuredHeight();
                if (availableHeight > 0) {
                    parentTwoService.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    peekHeight += availableHeight;
                }
            }
        });

        parentOneGoods.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //+60 for bottom margin of button
                availableHeight = parentOneGoods.getMeasuredHeight() + btnGetDetailsGoods.getMeasuredHeight();
                if (availableHeight > 0) {
                    parentOneGoods.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    peekHeight = peekHeight + availableHeight + 60;
                }
            }
        });

        viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                moreDetailsService.setVisibility(View.VISIBLE);
                recommendedByTajika.setVisibility(View.VISIBLE);
                parentTwoService.setVisibility(View.GONE);
            }
        });

        btnGetDetailsGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior2.setState(BottomSheetBehavior.STATE_EXPANDED);
                moreDetailsGoods.setVisibility(View.VISIBLE);
                recSubscription.setVisibility(View.VISIBLE);
                btnGetDetailsGoods.setVisibility(View.GONE);
            }
        });

        getSubscriptionDetails();

    }

    private void getSubscriptionDetails() {
        ApiCall.getMethod(this, ServiceNames.SUBSCRIPTION_PLAN, response -> {
            Utils.log(TAG, response.toString());
            JSONArray jsonarray = null;
            try {
                jsonarray = response.getJSONArray("data");
                List<SubscriptionModel> subModel = new ArrayList<>();

                for (int i = 0; i < jsonarray.length(); i++) {
                    SubscriptionModel subscriptionModel = MySingleton.getGson().fromJson(jsonarray.getJSONObject(i).toString(), SubscriptionModel.class);
                    subModel.add(subscriptionModel);

                }
                SubscriptionAdapter subAdapter = new SubscriptionAdapter(LocationSelectorActivity.this, subModel);
                recSubscription.setHasFixedSize(true);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
                gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                recSubscription.setLayoutManager(gridLayoutManager);
                recSubscription.setItemAnimator(new DefaultItemAnimator());
                recSubscription.setAdapter(subAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    private void initListeners() {
        searchProviderCategory.setOnClickListener(view -> startActivity(new Intent(LocationSelectorActivity.this, SearchActivity.class)));
        compare.setOnClickListener(view -> {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            initiatePopUp();
        });

        gotoCurrentLocation.setOnClickListener(view -> initLocation());

        backPress.setOnClickListener(view -> onBackPressed());

        requestService.setOnClickListener(view -> startActivity(new Intent(LocationSelectorActivity.this, RequestServiceActivity.class)
                .putExtra("provider_id", selected_id)
                .putExtra("service_name", service_name)
                .putExtra("service_id", service_id)));

        btnRequestService.setOnClickListener(view -> startActivity(new Intent(LocationSelectorActivity.this, RequestServiceActivity.class)
                .putExtra("provider_id", selected_id)
                .putExtra("service_name", service_name)
                .putExtra("service_id", service_id)));

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    moreDetailsService.setVisibility(View.VISIBLE);
                    recommendedByTajika.setVisibility(View.VISIBLE);
                    parentTwoService.setVisibility(View.GONE);
                } else {
                    moreDetailsService.setVisibility(View.GONE);
                    recommendedByTajika.setVisibility(View.VISIBLE);
                    parentTwoService.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });
        behavior2.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                if (behavior2.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    moreDetailsGoods.setVisibility(View.VISIBLE);
                    recSubscription.setVisibility(View.VISIBLE);
                    btnGetDetailsGoods.setVisibility(View.GONE);
                } else {
                    moreDetailsGoods.setVisibility(View.GONE);
                    recSubscription.setVisibility(View.GONE);
                    btnGetDetailsGoods.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });


        jointToProvideService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pref.setString("id", "");
                Intent intent = new Intent(LocationSelectorActivity.this, LandingPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        referToProvideService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "App Link will be pasted here");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Refer service provider via");
                startActivity(Intent.createChooser(shareIntent, "App Link will be pasted here"));
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.getUiSettings().setCompassEnabled(false);

        googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        getApplicationContext(), R.raw.gray_map));

        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);

        initLocation();

    }

    private void initLocation() {
        LiveGpsTracker.getInstance(LocationSelectorActivity.this, new LiveGpsTracker.LocationUpdate() {
            @Override
            public void onLocationFound(double latitide, double longitude) {
                animateMarker(new LatLng(latitide, longitude));
            }

            @Override
            public void OnLocationSettingNotFound() {
                Toast.makeText(LocationSelectorActivity.this, "Please enable location setting and retry", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPopupPermission() {
                Toast.makeText(LocationSelectorActivity.this, "Please enable location permissions", Toast.LENGTH_SHORT).show();
            }
        }).initLocationUpdate();
    }

    private void getServiceProvider(String lat, String longi) {

        JSONObject data = new JSONObject();
        try {
            data.put("service_id", service_id);
            data.put("latitude", lat);
            data.put("longitude", longi);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.SERVICE_PROVIDER_LIST, data, response -> {
            Utils.log(TAG, response.toString());
            JSONArray jsonarray = null;
            try {
                jsonarray = response.getJSONArray("data");
                if (jsonarray.length() < 1) {
                    noProviderFound.setVisibility(View.VISIBLE);
                    inner.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            inner.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            providerDetails.setVisibility(View.GONE);
                            View hidden = inner.getChildAt(1);
                            behavior.setPeekHeight(hidden.getTop());
                        }
                    });
                } else {
                    for (int i = 0; i < jsonarray.length(); i++) {
                        try {
                            ServiceProvider serviceProvider = MySingleton.getGson().fromJson(jsonarray.getJSONObject(i).toString(), ServiceProvider.class);
                            LatLng latLng = new LatLng(Double.parseDouble(serviceProvider.getLatitude()), Double.parseDouble(serviceProvider.getLongitude()));
                            Marker m = mMap.addMarker(new MarkerOptions().position(latLng).icon(providerImage(this)));
                            serviceProviderList.add(serviceProvider);

                            m.setTag(serviceProvider.getUserId());

                            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(Marker m) {

                                    selected_id = String.valueOf(m.getTag());

                                    providerDetails.setVisibility(View.VISIBLE);

                                    getServiceProviderDetails(selected_id);

                                    if (service_type.equals("goods")) {
                                        behavior2.setPeekHeight(peekHeight);
                                    } else if (service_type.equals("service")) {
                                        behavior.setPeekHeight(peekHeight);
                                    }

                                    viewDetails.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                            moreDetailsService.setVisibility(View.VISIBLE);
                                            recommendedService.setVisibility(View.VISIBLE);
                                        }
                                    });
                                    return true;
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    private void initiatePopUp() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_provider_compare_list);

        TextView addOne = dialog.findViewById(R.id.txt_addOne);
        TextView addTwo = dialog.findViewById(R.id.txt_addTwo);
        TextView compareProviders = dialog.findViewById(R.id.txt_compareProviders);
        ImageView closeDialog = dialog.findViewById(R.id.iv_closeDialog);


        addOne.setText(compareAddOne);
        addTwo.setText(compareAddTwo);

        addOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compareAddOne = serviceProviderDetails.getName();
                compareAddOneID = serviceProviderDetails.getId().toString();
                addOne.setText(compareAddOne);
            }
        });
        addTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compareAddTwo = serviceProviderDetails.getName();
                compareAddTwoID = serviceProviderDetails.getId().toString();
                addTwo.setText(compareAddTwo);
            }
        });
        compareProviders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LocationSelectorActivity.this, CompareListActivity.class));
            }
        });
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void getServiceProviderDetails(String providerID) {
        JSONObject data = new JSONObject();
        try {
            data.put("user_id", providerID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.SERVICE_PROVIDER_DETAILS, data, response -> {

            Utils.log(TAG, response.toString());

            try {

                serviceProviderDetails = MySingleton.getGson().fromJson(response.getJSONObject("data").toString(), ServiceProviderDetails.class);

                txtProviderName.setText(serviceProviderDetails.getName());
                txtRating.setText(serviceProviderDetails.getRating() + " ratings");
                txtServiceName.setText(serviceProviderDetails.getBusinessCategories());
                txtDistance.setText(serviceProviderDetails.getDistance());
                txtAbout.setText(serviceProviderDetails.getAbout());
                txtJobComp.setText(String.valueOf(serviceProviderDetails.getJobCompleted()));
                txtEdu.setText(serviceProviderDetails.getEducationLevel());
                txtAdd.setText(serviceProviderDetails.getServiceArea());
                txtSkill.setText(serviceProviderDetails.getServiceDescription());

            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
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

    private BitmapDescriptor providerImage(Context ctx) {

        Drawable background = ContextCompat.getDrawable(ctx, R.drawable.provider_image_1x);
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

        edtAddress.setText("");
        mMap.clear();
        getServiceProvider(String.valueOf(location.latitude), String.valueOf(location.longitude));

        // mapRipple effect
    /*
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
        */


//        mMap.addMarker(new MarkerOptions().position(location).title("Current Location").
//                draggable(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)));
//        //resizing Bitmap image and setting to add marker
        /*  image to bitmap compressed */

//        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_marker);
//        Bitmap b = bitmapdraw.getBitmap();
//        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 90, 150, true);


        mMap.addMarker(new MarkerOptions()
                .position(location)
                .title("Current Location")
                .draggable(true));
        //.icon(bitmapDescriptorFromVector(this)));
        //.icon(bitmapDescriptorFromVector(this)));

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        final CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(location)      // Sets the center of the map to Mountain View
                .zoom(16)                   // Sets the zoom
                .bearing(0)                // Sets the orientation of the camera to east
                .tilt(20)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder

//        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new
//                CameraPosition(location,(float)16, (float)30, (float)0)));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        }, 100);

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
                // Toast.makeText(this, "running "+mSelectedAddress.getAddress_1(), Toast.LENGTH_SHORT).show();
                areaName = addresses.get(0).getFeatureName();
                fullAddress = addresses.get(0).getAddressLine(0);
                cityName = addresses.get(0).getLocality();
                countryName = addresses.get(0).getCountryName();
                stateName = addresses.get(0).getAdminArea();
                postalCode = addresses.get(0).getPostalCode();

                mSelectedAddress = new AddressBean(location.latitude, location.longitude, fullAddress, "",
                        cityName, postalCode,
                        stateName, countryName);
                //  ((LogisticsApplication) getApplicationContext()).saveSelectedAddress(mAddress);
                edtAddress.setText(mSelectedAddress.getAddress_1());

//                AppConstants.address = mSelectedAddress.getAddress_1();
//                AppConstants.country = countryName;
//                AppConstants.zip = postalCode;
//                AppConstants.state = stateName;
//                AppConstants.city = cityName;


                placeBean.setAddress(String.valueOf(mSelectedAddress.getAddress_1()));
                placeBean.setLatitude(String.valueOf(location.latitude));
                placeBean.setLongitude(String.valueOf(location.longitude));
                placeBean.setName(fullAddress);

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

    private int toPixels(int sheetHeight) {
        height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sheetHeight, getResources().getDisplayMetrics());
        return height;

    }

    private int toDP(int sheetHeight) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return Math.round(sheetHeight / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public void llClick(View view) {
    }
}