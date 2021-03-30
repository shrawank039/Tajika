package com.matrixdeveloper.tajika.network;


public class ServiceNames {

    public static String PRODUCTION_API = "http://93.188.162.41/tajika/";
    public static final String API_VERSION = "api/v1";

    private static final String API = PRODUCTION_API + API_VERSION;

    public static final String LOGIN = API + "/login";
    public static final String USER_REGISTRATION = API + "/createAccount";
    public static final String SEND_OTP = API + "/sendotp";
    public static final String ABOUT_US = API + "/getaboutus";
    public static final String PRIVACY_POLICY = API + "/getprivacypolicy";
    public static final String VALIDATE_OTP = API + "/validateotp";
    public static final String SET_PASSWORD = API + "/setPassword";
    public static final String PROFILE = API + "";
    public static final String CATEGORY = API + "/getCategoryList";
    public static final String FILTER_SERVICE_LIST = API + "/filterServiceList";
    public static final String BANNER = API + "/getbanner";
    public static final String SERVICE_LIST = API + "/getServiceList";
    public static final String SERVICE_PROVIDER_DETAILS = API + "/getServiceProviderInfo";
    public static final String SERVICE_PROVIDER_LIST = API + "/getAllServiceProviderByService";
    public static final String SUBMIT_SERVICE_REQUEST = API + "/submitServiceRequest";
    public static final String SAVE_FCM_TOKEN = API + "/save_fcmtoken";
    public static final String NOTIFICATION_LIST = API + "/notificationList";
    public static final String BOOKING_DETAILS = API + "/servicebookingDetails";

    // SP
    public static final String HOME_SCREEN_DATA = API + "/getServiceProviderHomeScreen";


    public static boolean is = true;

}