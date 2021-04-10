package com.matrixdeveloper.tajika.network;


public class ServiceNames {

    public static String PRODUCTION_API = "http://93.188.162.41/tajika/";
    public static final String API_VERSION = "api/v1";

    private static final String API = PRODUCTION_API + API_VERSION;

    public static final String LOGIN = API + "/login";
    public static final String USER_REGISTRATION = API + "/createAccount";
    public static final String ABOUT_US = API + "/getaboutus";
    public static final String PRIVACY_POLICY = API + "/getprivacypolicy";
    public static final String SEND_OTP = API + "/sendotp";
    public static final String VALIDATE_OTP = API + "/validateotp";
    public static final String SET_PASSWORD = API + "/setPassword";
    public static final String GET_USER_PROFILE = API + "/getProfile";
    public static final String UPDATE_PROFILE = API + "/updateProfile";
    public static final String CATEGORY = API + "/getCategoryList";
    public static final String FILTER_SERVICE_LIST = API + "/filterServiceList";
    public static final String BANNER = API + "/getbanner";
    public static final String SERVICE_LIST = API + "/getServiceList";
    public static final String SERVICE_PROVIDER_DETAILS = API + "/getServiceProviderInfo";
    public static final String SERVICE_PROVIDER_LIST = API + "/getAllServiceProviderByService";
    public static final String SUBMIT_SERVICE_REQUEST = API + "/submitServiceRequest";
    public static final String SAVE_FCM_TOKEN = API + "/save_fcmtoken";
    public static final String NOTIFICATION_LIST = API + "/notificationList";
    public static final String DELETE_NOTIFICATION = API + "/removeNotification";
    public static final String BOOKING_DETAILS = API + "/servicebookingDetails";

    // SP
    public static final String REGISTER_SERVICE_PROVIDER_INDI = API + "/createServiceProviderAccount";
    public static final String BUSINESS_SERVICE_PROVIDER_DETAILS = API + "/getServiceBusinessInfo";
    public static final String GET_SERVICE_REQUEST = API + "/getAllServiceRequest";
    public static final String GET_SERVICE_REQUEST_DETAILS = API + "/getParticularServiceRequestDetails";
    public static final String CHANGE_SERVICE_REQUEST_STATUS = API + "/changeServiceRequestStatus";
    public static final String UPDATE_SERVICE_BOOKING_DETAILS = API + "/updateservicebookingdetails";
    public static final String CANCEL_SERVICE_REQUEST = API + "/cancelservicebooking";
    public static final String ADD_SERVICE_RATING = API + "/addservicerating";
    public static final String PROVIDER_ALL_BOOKING = API + "/serviceProviderAllBooking";
    public static final String PROVIDER_SERVICE_LIST = API + "/myServiceList";
    public static final String ADD_NEW_SERVICE = API + "/saveService";
    public static final String UPDATE_SERVICE = API + "/updateService";
    public static final String DELETE_SERVICE = API + "/deleteService";
    public static final String GET_PROVIDER_PROFILE_IND = API + "/getServiceProfileDetails";
    public static final String UPDATE_PROVIDER_PROFILE_IND = API + "/updateServiceProviderDetails";
    public static final String UPDATE_LIVE_STATUS = API + "/updateOnline";
    public static final String HOME_SCREEN_DATA_INDI = API + "/getServiceProviderHomeScreen";
    public static final String WALLET_DETAILS = API + "/getWalletDetails";
    public static final String ALL_TRANSACTIONS = API + "/allTranscationList";
    public static final String ADD_CREDIT = API + "/addCreditWallet";

    public static final String REGISTER_SERVICE_PROVIDER_BUSI = API + "/createServiceBusinessAccount";
    public static final String HOME_SCREEN_DATA_BUSI = API + "/getServiceBusinessHomeScreen";
    public static final String GET_PROVIDER_PROFILE_BUSI = API + "/getServiceBusinessProfileDetails";
    public static final String UPDATE_PROVIDER_PROFILE_BUSI = API + "/updateServiceBuisnessDetails";

    public static boolean is = true;

}