package com.matrixdeveloper.tajika.utils;


import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.app.App;
import com.matrixdeveloper.tajika.network.ServiceNames;

public class AppConstants {


    public static final String UTF_8 = "UTF-8";
    public static final String MIME_TYPE_HTML = "text/html";

    public static final String SEPARATOR = "/";
    public static final String SPACE = " ";
    public static final String AND = "and";
    public static final String BASE_URL = ServiceNames.PRODUCTION_API;

    public static final int DRIVER_TYPE_DRIVER_CUM_OWNER = 0;
    public static final int DRIVER_TYPE_NON_DRIVING_PARTNER = 1;

    public static final int DOCUMENT_TYPE_VEHICLE_REGISTRATION_BACK = 1; //Vehicle Reg (Back)
    public static int VERSION_CODE = 0;
    public static final int DOCUMENT_TYPE_POLICE_CLEARANCE_CERTIFICATE = 2;
    public static final int DOCUMENT_TYPE_FITNESS_CERTIFICATE = 3;
    public static final int DOCUMENT_TYPE_VEHICLE_REGISTRATION_FRONT = 4;
    public static final int DOCUMENT_TYPE_VEHICLE_PERMIT = 5;
    public static final int DOCUMENT_TYPE_COMMERCIAL_INSURANCE = 6;
    public static final int DOCUMENT_TYPE_TAX_RECEIPT = 7;
    public static final int DOCUMENT_TYPE_PASS_BOOK = 8;
    public static final int DOCUMENT_TYPE_DRIVER_LICENCE_WITH_BADGE_NUMBER = 9;
    public static final int DOCUMENT_TYPE_BACKGROUND_CHECK_CONSENT_FORM = 10;
    public static final int DOCUMENT_TYPE_PAN_CARD = 11;
    public static final int DOCUMENT_TYPE_NO_OBJECTION_CERTIFICATE = 12;
    public static final int DOCUMENT_TYPE_DRIVER_AADHAR_FRONT = 13;
    public static final int DOCUMENT_TYPE_DRIVER_AADHAR_BACK = 14;
    public static final int DOCUMENT_TYPE_OWNER_AADHAR_FRONT = 15;
    public static final int DOCUMENT_TYPE_OWNER_AADHAR_BACK = 16;
    public static final int DOCUMENT_TYPE_POLLUTION_CERTIFICATE = 17;
    public static final int DOCUMENT_TYPE_NUMBER_PLATE = 18;

    public static final int DOCUMENT_STATUS_NOT_UPLOADED = 0;
    public static final int DOCUMENT_STATUS_PENDING_APPROVAL = 1;
    public static final int DOCUMENT_STATUS_APPROVED = 2;
    public static final int DOCUMENT_STATUS_REJECTED = 3;

    public static final int TRIP_STATUS_CANCELLED = 0;
    public static final int TRIP_STATUS_PENDING = 1;
    public static final int TRIP_STATUS_IN_PROGRESS = 2;
    public static final int TRIP_STATUS_COMPLETED = 3;

    public static final int DRIVER_STATUS_ACCEPTED = 0;
    public static final int DRIVER_STATUS_ARRIVED = 1;
    public static final int DRIVER_STATUS_STARTED = 2;
    public static final int DRIVER_STATUS_ENDED = 3;
    public static final int DRIVER_STATUS_CASH_ACCEPTED = 4;


    public static final int RIDER_FEEDBACK_ISSUES = 0;
    public static final int RIDER_FEEDBACK_COMMENTS = 1;

    public static final int APP_STATUS_IDLE = 0;
    public static final int APP_STATUS_ASSIGNED = 1;

    public static String EXTRA_PROFILE_TO_IMAGE_MEDIA_LIST = "imageList";
    public static String EXTRA_PROFILE_TO_IMAGE_SELECTED_POSITION = "imagePosition";
    public static String EXTRA_PROFILE_TO_IMAGE_LIST_SIZE = "imageListSize";


    public static final String PREFERENCE_KEY_SESSION_FCM_ID = "fcm_id";
    public static final String PREFERENCE_KEY_SESSION_TOKEN = "auth_token";
    public static final String PREFERENCE_KEY_SESSION_DEVICE_ID = "device_id";
    public static final String PREFERENCE_KEY_SESSION_DEVICE_SECRET = "device_secret";
    public static final String PREFERENCE_KEY_SESSION_ACCESSTOKEN = "access_token";
    public static final String PREFERENCE_KEY_SESSION_REFRESHTOKEN = "refresh_token";
    public static final String PREFERENCE_KEY_SESSION_USERNAME = "username";
    public static final String PREFERENCE_KEY_SESSION_NAME = "name";
    public static final String PREFERENCE_KEY_SESSION_FIRSTNAME = "firstname";
    public static final String PREFERENCE_KEY_SESSION_LASTNAME = "lastname";
    public static final String PREFERENCE_KEY_SESSION_EMAIL = "email";
    public static final String PREFERENCE_KEY_SESSION_PHONE = "phone";
    public static final String PREFERENCE_KEY_SESSION_ADDRESS = "address";
    public static final String PREFERENCE_KEY_SESSION_PROFILE_PHOTO = "profile_photo";
    public static final String PREFERENCE_KEY_SESSION_COVER_PHOTO = "cover_photo";
    public static final String PREFERENCE_KEY_SESSION_USERID = "userid";
    public static final String PREFERENCE_KEY_SESSION_PASSWORD = "password";
    public static final String PREFERENCE_KEY_SESSION_GENDER = "gender";
    public static final String PREFERENCE_KEY_SESSION_IS_FIRST_TIME = "is_first_time";
    public static final String PREFERENCE_KEY_SESSION_IS_PHONE_VERIFIED = "is_phone_verified";
    public static final String PREFERENCE_KEY_SESSION_DOB = "DOB";
    public static final String PREFERENCE_KEY_SESSION_LOCALE = "locale";
    public static final String PREFERENCE_NAME_SESSION = "session";


    public static int YEAR_START = 1950;

    public static String MALE = "m";
    public static String FEMALE = "f";

    private static final String COUNTRY_LIST = "{\"countries\":[{\"name\":\"India\",\"dial_code\":\"+91\",\"code\":\"IN\"}]}";


}
