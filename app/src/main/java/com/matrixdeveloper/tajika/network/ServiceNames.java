package com.matrixdeveloper.tajika.network;


public class ServiceNames {

    public static String PRODUCTION_API = "http://93.188.162.41/tajika/api";

    public static final String API_VERSION = "/v1";

    private static final String API = PRODUCTION_API + API_VERSION;

    public static final String LOGIN =  API + "/login";
    public static final String USER_REGISTRATION = API +"/createAccount";
    public static final String SEND_OTP =  API + "/sendotp";
    public static final String VALIDATE_OTP =  API + "/validateotp";
    public static final String SET_PASSWORD =  API + "";
    public static final String PROFILE =  API + "";
    public static final String CATEGORY =  API + "";
    public static final String FILTER_CATEGORY =  API + "";
    public static final String SERVICE_LIST =  API + "";
    public static final String SERVICE_DETAILS =  API + "";


    public static boolean is = true;

}