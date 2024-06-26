package com.matrixdeveloper.tajika;

import static com.matrixdeveloper.tajika.utils.Global.compareAddOne;
import static com.matrixdeveloper.tajika.utils.Global.compareAddOneServiceID;
import static com.matrixdeveloper.tajika.utils.Global.compareAddTwo;
import static com.matrixdeveloper.tajika.utils.Global.compareAddTwoServiceID;
import static com.matrixdeveloper.tajika.utils.Global.firstItemToCompare;
import static com.matrixdeveloper.tajika.utils.Global.secondItemToCompare;

import android.app.AlertDialog;
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
import android.view.Window;
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

import com.bumptech.glide.Glide;
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
import com.matrixdeveloper.tajika.adapter.ServiceImageAdapter;
import com.matrixdeveloper.tajika.adapter.SubscriptionAdapter;
import com.matrixdeveloper.tajika.location.LiveGpsTracker;
import com.matrixdeveloper.tajika.model.AddressBean;
import com.matrixdeveloper.tajika.model.PlaceBean;
import com.matrixdeveloper.tajika.model.ServiceImageModel;
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
    private LinearLayout searchProviderCategory, viewDetails, noProviderFound, providerDetails, moreDetailsGoods, moreDetailsService, spRecommendedService, inner;
    private View view, view1;
    ArrayList<LatLng> pointer = new ArrayList<>();
    private List<ServiceProvider> serviceProviderList;
    private final String TAG = "LocationSelectorAct";
    private BottomSheetBehavior behavior, behavior2;
    private String service_name, service_id, service_type;
    int height;
    private ImageView img;
    private TextView edtSearch, recommendedByTajika, addToCompare;
    private int peekHeight = 0;
    ServiceProviderDetails serviceProviderDetails;
    private CardView jointToProvideService, referToProvideService;
    PrefManager pref;
    Button btnGetDetailsGoods, btnChat, btnRequestService;
    private RecyclerView recSubscription;
    private LinearLayout parentTwoService, parentOneService, parentOneGoods;
    int availableHeight;
    String subscriptionStatus, planID;
    private final int PAYMENT_REQUEST = 1;
    private RecyclerView rvServiceImages;

    private ImageView spProviderImage, gpGoodsProviderImage;
    private TextView spProviderName, spServiceName, spDistance, spRating, spAbout, spJobsCompleted, spEducation, spAddress, spSkills;
    private TextView gpGoodsProviderName, gpServiceName, gpDistance, gpRating, gpAbout, gpProductName, gpProductCost, gpCustomerAddress, gpProductCategory, gpProductDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selector);

        pref = new PrefManager(this);

        serviceProviderList = new ArrayList<>();

        View bottomSheet = findViewById(R.id.bottom_sheet_service);
        behavior = BottomSheetBehavior.from(bottomSheet);
        View bottomSheet2 = findViewById(R.id.bottom_sheet_goods);
        behavior2 = BottomSheetBehavior.from(bottomSheet2);

        initViews();
        initListeners();

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.place_api_key));
            Log.d("initialize", "running");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

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
                parentTwoService.setVisibility(View.GONE);
            }
        });

        btnGetDetailsGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for subscribed =0
                //for UnSubscribed =1
                //for subscriptionExpired =2
                checkSubscription(subscriptionStatus);
            }
        });

        getSubscriptionDetails();

    }

    private void checkSubscription(String subscriptionStatus) {
        if (subscriptionStatus.equals("subscribed")) {
            behavior2.setState(BottomSheetBehavior.STATE_EXPANDED);
            moreDetailsGoods.setVisibility(View.VISIBLE);
            recSubscription.setVisibility(View.GONE);
            btnGetDetailsGoods.setVisibility(View.GONE);
        } else if (subscriptionStatus.equals("unsubscribed") || subscriptionStatus.equals("")) {
            showSubscriptionPayOneTimeAlert();
        } else if (subscriptionStatus.equals("expired")) {
            showSubscriptionAlert();
        }
    }

    private void showSubscriptionPayOneTimeAlert() {

        final Dialog dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_pay_onetime_charge);

        Button payNow = dialog.findViewById(R.id.btn_payNow);
        ImageView dialogCancel = dialog.findViewById(R.id.iv_dialogCancel);

        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior2.setState(BottomSheetBehavior.STATE_EXPANDED);
                recSubscription.setVisibility(View.VISIBLE);
                moreDetailsGoods.setVisibility(View.GONE);
                btnGetDetailsGoods.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });
        dialogCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    public void showSubscriptionAlert() {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_payment_notice);

        Button makePayment = dialog.findViewById(R.id.btn_makePayment);
        ImageView dialogCancel = dialog.findViewById(R.id.iv_dialogCancel);

        makePayment.setOnClickListener(v -> {
            behavior2.setState(BottomSheetBehavior.STATE_EXPANDED);
            recSubscription.setVisibility(View.VISIBLE);
            moreDetailsGoods.setVisibility(View.GONE);
            btnGetDetailsGoods.setVisibility(View.GONE);
            dialog.dismiss();
        });
        dialogCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();

    }

    private void showSubscriptionSuccessAlert() {
        behavior2.setState(BottomSheetBehavior.STATE_EXPANDED);
        moreDetailsGoods.setVisibility(View.VISIBLE);
        recSubscription.setVisibility(View.GONE);
        btnGetDetailsGoods.setVisibility(View.GONE);

        final Dialog dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_payment_successful);

        Button close = dialog.findViewById(R.id.btn_close);
        ImageView dialogCancel = dialog.findViewById(R.id.iv_dialogCancel);

        dialogCancel.setOnClickListener(v -> dialog.dismiss());
        close.setOnClickListener(v -> dialog.dismiss());
        dialog.show();

    }

    public void getSubscription(String id, String amount, String title) {
        planID = id;
        Intent intent = new Intent(getApplicationContext(), PaymentWebViewActivity.class);
            intent.putExtra("amount", amount);
            intent.putExtra("order_desc", title);
            startActivityForResult(intent, PAYMENT_REQUEST);
    }

    private void initViews() {

        service_name = getIntent().getStringExtra("service_name");
        service_id = getIntent().getStringExtra("service_id");
        service_type = getIntent().getStringExtra("service_type");

        edtAddress = findViewById(R.id.edt_location);
        inner = findViewById(R.id.inner);
        gotoCurrentLocation = findViewById(R.id.iv_gotoCurrentLocation);
        backPress = findViewById(R.id.iv_backPress);
        requestService = findViewById(R.id.txt_requestService);
        moreDetailsGoods = findViewById(R.id.ll_moreDetailsGoods);
        edtSearch = findViewById(R.id.edt_search);
        edtSearch.setText(service_name);
        searchProviderCategory = findViewById(R.id.ll_searchProviderCategory);

        //bottom sheet Common
        viewDetails = findViewById(R.id.ll_viewDetails);
        noProviderFound = findViewById(R.id.ll_noProviderFound);
        providerDetails = findViewById(R.id.ll_providerDetails);
        addToCompare = findViewById(R.id.txt_addToCompare);
        jointToProvideService = findViewById(R.id.cv_joinToProvideService);
        referToProvideService = findViewById(R.id.cv_referToProvideService);
        btnChat = findViewById(R.id.btn_chat);
        btnRequestService = findViewById(R.id.btn_requestService);
        btnGetDetailsGoods = findViewById(R.id.btn_getDetailsGoods);
        recSubscription = findViewById(R.id.rv_subscription);
        parentTwoService = findViewById(R.id.ll_twoService);
        recommendedByTajika = findViewById(R.id.txt_recommendedByTajika);
        parentOneGoods = findViewById(R.id.ll_oneGoods);

        //BottomSheet Service Provider
        spProviderImage = findViewById(R.id.iv_spProviderImage);
        spProviderName = findViewById(R.id.txt_spProviderName);
        spServiceName = findViewById(R.id.txt_spServiceName);
        spDistance = findViewById(R.id.txt_spDistance);
        spRating = findViewById(R.id.txt_spRating);
        spAbout = findViewById(R.id.txt_spAbout);
        spJobsCompleted = findViewById(R.id.txt_spJobsCompleted);
        spEducation = findViewById(R.id.txt_spEducation);
        spAddress = findViewById(R.id.txt_spAddress);
        spSkills = findViewById(R.id.txt_spSkills);
        parentOneService = findViewById(R.id.ll_oneService);
        moreDetailsService = findViewById(R.id.ll_moreDetailsService);
        spRecommendedService = findViewById(R.id.ll_spRecommendedService);
        rvServiceImages = findViewById(R.id.rv_serviceProviderImages);


        //BottomSheet Goods Provider
        gpGoodsProviderImage = findViewById(R.id.iv_gpGoodsProviderImage);
        gpGoodsProviderName = findViewById(R.id.txt_gpGoodsProviderName);
        gpServiceName = findViewById(R.id.txt_gpServiceName);
        gpDistance = findViewById(R.id.txt_gpDistance);
        gpRating = findViewById(R.id.txt_gpRating);
        gpAbout = findViewById(R.id.txt_gpAbout);
        gpProductName = findViewById(R.id.txt_gpProductName);
        gpProductCost = findViewById(R.id.txt_gpProductCost);
        gpCustomerAddress = findViewById(R.id.txt_gpCustomerAddress);
        gpProductCategory = findViewById(R.id.txt_gpProductCategory);
        gpProductDetails = findViewById(R.id.txt_gpProductDetails);

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
        addToCompare.setOnClickListener(view -> {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            initiatePopUp();
        });

        gotoCurrentLocation.setOnClickListener(view -> initLocation());

        backPress.setOnClickListener(view -> finish());

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
                    parentTwoService.setVisibility(View.GONE);
                } else {
                    moreDetailsService.setVisibility(View.GONE);
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
                    checkSubscription(subscriptionStatus);
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
            data.put("type", service_type);
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

                    recommendedByTajika.setVisibility(View.VISIBLE);
                    ServiceProvider serviceProvider1 = MySingleton.getGson().fromJson(response.getJSONObject("recommended").toString(), ServiceProvider.class);
                    getServiceProviderDetails(serviceProvider1.getId().toString(), serviceProvider1.getBusinessCategories(), lat, longi);
                    selected_id = serviceProvider1.getId().toString();

                    for (int i = 0; i < jsonarray.length(); i++) {
                        try {
                            ServiceProvider serviceProvider = MySingleton.getGson().fromJson(jsonarray.getJSONObject(i).toString(), ServiceProvider.class);
                            LatLng latLng = new LatLng(Double.parseDouble(serviceProvider.getLatitude()), Double.parseDouble(serviceProvider.getLongitude()));
                            Marker m = mMap.addMarker(new MarkerOptions().position(latLng).icon(providerImage(this)));
                            serviceProviderList.add(serviceProvider);

                            if (m != null) {
                                m.setTag(serviceProvider.getUserId());
                            }

                            mMap.setOnMarkerClickListener(m1 -> {

                                selected_id = String.valueOf(m1.getTag());

                                recommendedByTajika.setVisibility(View.INVISIBLE);

                                providerDetails.setVisibility(View.VISIBLE);

                                getServiceProviderDetails(selected_id, serviceProvider1.getBusinessCategories(), lat, longi);


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
                                    }
                                });
                                return true;
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
        final Dialog dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_provider_compare_list);

        TextView addOne = dialog.findViewById(R.id.txt_addOne);
        TextView addTwo = dialog.findViewById(R.id.txt_addTwo);
        TextView compareProviders = dialog.findViewById(R.id.txt_compareProviders);
        ImageView closeDialog = dialog.findViewById(R.id.iv_closeDialog);


        addOne.setText(compareAddOne);
        addTwo.setText(compareAddTwo);

        addOne.setOnClickListener(v -> {
            if (compareAddOne.equals("Click to add first provider")) {
                firstItemToCompare.add(serviceProviderDetails);
                compareAddOne = serviceProviderDetails.getName();
                compareAddOneServiceID = service_id;
                addOne.setText(compareAddOne);
            } else {
                new AlertDialog.Builder(LocationSelectorActivity.this)
                        .setCancelable(false)
                        .setTitle("Warning!!")
                        .setMessage("Do you want to replace the provider?")
                        .setPositiveButton(android.R.string.yes, (dialog1, which) -> {
                            firstItemToCompare.clear();
                            firstItemToCompare.add(serviceProviderDetails);
                            compareAddOne = serviceProviderDetails.getName();
                            compareAddOneServiceID = service_id;
                            addOne.setText(compareAddOne);
                        })
                        .setNegativeButton(android.R.string.no, (dialog1, which) -> dialog1.dismiss())
                        .create()
                        .show();
            }
        });

        addTwo.setOnClickListener(v -> {
            if (compareAddTwo.equals("Click to add second provider")) {
                secondItemToCompare.add(serviceProviderDetails);
                compareAddTwo = serviceProviderDetails.getName();
                compareAddTwoServiceID = service_id;
                addTwo.setText(compareAddTwo);
            } else {
                new AlertDialog.Builder(LocationSelectorActivity.this)
                        .setCancelable(false)
                        .setTitle("Warning!!")
                        .setMessage("Do you want to replace the provider?")
                        .setPositiveButton(android.R.string.yes, (dialog1, which) -> {
                            secondItemToCompare.clear();
                            secondItemToCompare.add(serviceProviderDetails);
                            compareAddTwo = serviceProviderDetails.getName();
                            compareAddTwoServiceID = service_id;
                            addTwo.setText(compareAddTwo);
                        })
                        .setNegativeButton(android.R.string.no, (dialog1, which) -> dialog1.dismiss())
                        .create()
                        .show();
            }
        });
        compareProviders.setOnClickListener(v -> {
            if(addOne.getText().toString().equals("Click to add first provider") || addTwo.getText().toString().equals("Click to add second provider")){
                Toast.makeText(this, "Please add 2 providers for comparison.", Toast.LENGTH_SHORT).show();
            }else{
                startActivity(new Intent(LocationSelectorActivity.this, CompareListActivity.class));
            }
        });
        closeDialog.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void getServiceProviderDetails(String providerID, String service_id, String latitude, String longitude) {
        JSONObject data = new JSONObject();
        try {
            data.put("user_id", providerID);
            data.put("uid", pref.getString("id"));
            data.put("service_type", service_type);
            data.put("latitude", latitude);
            data.put("longitude", longitude);
            data.put("service_id", service_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.SERVICE_PROVIDER_DETAILS, data, response -> {

            Utils.log(TAG, response.toString());

            try {

                serviceProviderDetails = MySingleton.getGson().fromJson(response.getJSONObject("data").toString(), ServiceProviderDetails.class);

                Glide.with(this).load(ServiceNames.PRODUCTION_API + serviceProviderDetails.getProfileimage()).placeholder(R.drawable.provider_image_1x).into(spProviderImage);

                //for service
                spProviderName.setText(serviceProviderDetails.getName());
                spServiceName.setText(serviceProviderDetails.getBusinessCategories());
                spDistance.setText(serviceProviderDetails.getDistance());
                spRating.setText(serviceProviderDetails.getRating() + " ratings");
                spAbout.setText(serviceProviderDetails.getAbout());
                spJobsCompleted.setText(String.valueOf(serviceProviderDetails.getJobCompleted()));
                spEducation.setText(serviceProviderDetails.getEducationLevel());
                spAddress.setText(serviceProviderDetails.getServiceArea());

                String skills = "";
                if (serviceProviderDetails.getSkills() != null) {
                    for (int i = 0; i < serviceProviderDetails.getSkills().size(); i++) {
                        skills = skills +"\n"+ serviceProviderDetails.getSkills().get(i);
                        spSkills.setText(skills.trim());
                    }
                }

                List<ServiceImageModel> serviceImageModels = new ArrayList<>();
                if (serviceProviderDetails.getServiceimage() != null) {
                    for (int j = 0; j < serviceProviderDetails.getServiceimage().size(); j++) {
                        serviceImageModels.add(new ServiceImageModel(serviceProviderDetails.getServiceimage().get(j)));
                    }
                }

                ServiceImageAdapter serviceImageAdapter = new ServiceImageAdapter(this, serviceImageModels);
                GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 1);
                gridLayoutManager1.setOrientation(RecyclerView.HORIZONTAL);
                rvServiceImages.setHasFixedSize(true);
                rvServiceImages.setLayoutManager(gridLayoutManager1);
                rvServiceImages.setAdapter(serviceImageAdapter);

                // for goods
                Glide.with(this).load(ServiceNames.PRODUCTION_API + serviceProviderDetails.getProfileimage()).placeholder(R.drawable.provider_image_1x).into(gpGoodsProviderImage);

                gpGoodsProviderName.setText(serviceProviderDetails.getName());
                gpServiceName.setText(serviceProviderDetails.getBusinessCategories());
                gpDistance.setText(serviceProviderDetails.getDistance());
                gpRating.setText(serviceProviderDetails.getRating() + " ratings");
                gpAbout.setText(serviceProviderDetails.getAbout());
                //gpProductName.setText(serviceProviderDetails.);
                gpProductCost.setText(serviceProviderDetails.getMinimumCharge());
                gpProductDetails.setText(String.valueOf(serviceProviderDetails.getServiceDescription()));
                gpProductCategory.setText(serviceProviderDetails.getBusinessCategories());
                gpCustomerAddress.setText(serviceProviderDetails.getServiceArea());
                subscriptionStatus = serviceProviderDetails.getSubscription();

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

         /*GeocodingLocation locationAddress = new GeocodingLocation();
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
                //((LogisticsApplication) getApplicationContext()).saveSelectedAddress(mAddress);
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
        //initLocation();
        //Toast.makeText(this, "Resume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAYMENT_REQUEST) {
            if (resultCode == 1) {
                addSubscription();
            } else {
                Utils.toast(getApplicationContext(), "Payment Failed!!!");
            }
        }

    }

    public void addSubscription() {

        JSONObject data = new JSONObject();
        try {
            data.put("user_id", pref.getString("id"));
            data.put("plan_id", planID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiCall.postMethod(this, ServiceNames.ADD_SUBSCRIPTION, data, response -> {
            Utils.log(TAG, response.toString());
            try {
                JSONObject jsonObject = response.getJSONObject("data");
                if (response.optString("status").equals("200")) {
                    showSubscriptionSuccessAlert();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });

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