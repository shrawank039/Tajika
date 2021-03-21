package com.matrixdeveloper.tajika.app;

import android.app.AlarmManager;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.matrixdeveloper.tajika.config.Config;
import com.matrixdeveloper.tajika.utils.AppConstants;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class App extends Application {

    public static final String CHANNEL_ID = "exampleServiceChannel";
    public static final int SERVER_CONNECTION_AVAILABLE = 0;
    public static final int NETWORK_NOT_AVAILABLE = 1;
    public static final int AUTH_TOKEN_NOT_AVAILABLE = 2;
    private static final String TAG = "AppCls";

    public static final int DATE_FORMAT_0 = 0;
    public static final int DATE_FORMAT_1 = 1;
    public static final int DATE_FORMAT_2 = 2;
    public static final int DATE_FORMAT_3 = 3;
    public static final int DATE_FORMAT_4 = 4;

    public static final int TIME_FORMAT_0 = 0;


    private final Thread.UncaughtExceptionHandler defaultHandler;

    private static App instance;

    Resources r;
    float px;
    int width;
    int height;

    private GoogleApiClient googleApiClient;

    private boolean isDemo;

    public boolean isDemo() {
        return isDemo;
    }

    public void setDemo(boolean demo) {
        isDemo = demo;
    }

    private static Context mContext;
    private static App mInstance;
    private RequestQueue mRequestQueue;


    public static Context getContext() {
        return mContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mContext = this;
        mInstance = this;

    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }

    public void setGoogleApiClient(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }


    public float getPx() {
        return px;
    }

    public void setPx(float px) {
        this.px = px;
    }

    public App() {
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // setup handler for uncaught exception
        Thread.UncaughtExceptionHandler _unCaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {

                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>IN EXCEPTION HANDLER>>>>>>>>>>>>>>>>>>>>>");
                final Writer result = new StringWriter();
                final PrintWriter printWriter = new PrintWriter(result);
                ex.printStackTrace(printWriter);
                printWriter.close();

                ex.printStackTrace();
                Toast.makeText(getApplicationContext(), "App Crashed!", Toast.LENGTH_SHORT).show();
                //	    			restart(getApplicationContext(), 2000);
                //	check(getApplicationContext(), 1);
                checkForToken();

                //	android.os.Process.killProcess(android.os.Process.myPid());
                defaultHandler.uncaughtException(thread, ex);
            }
        };
        Thread.setDefaultUncaughtExceptionHandler(_unCaughtExceptionHandler);

    }

    public static App getInstance() {

        if (instance == null)
            instance = new App();
        return instance;
    }


    public static void restart(Context context, int delay) {
        if (delay == 0) {
            delay = 1;
        }
        Intent restartIntent = context.getPackageManager()
                .getLaunchIntentForPackage(context.getPackageName());
        PendingIntent intent = PendingIntent.getActivity(
                context, 0,
                restartIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC, System.currentTimeMillis() + delay, intent);
        System.exit(2);
    }

    public static void check(Context context, int delay) {
        if (delay == 0) {
            delay = 1;
        }
        System.exit(0);
    }


    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

//        FacebookSdk.sdkInitialize(this.getApplicationContext());

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        checkForToken();

        setDefaultFont();

        r = getResources();
        px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, r.getDisplayMetrics());
        width = r.getDisplayMetrics().widthPixels;
        height = r.getDisplayMetrics().heightPixels;

        createNotificationChannel();

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Example Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }

    }

    private void setDefaultFont() {

        try {

            final Typeface bold = Typeface.createFromAsset(getAssets(), "Roboto-Bold.ttf");
            final Typeface italic = Typeface.createFromAsset(getAssets(), "Roboto-Italic.ttf");
            final Typeface boldItalic = Typeface.createFromAsset(getAssets(), "Roboto-BoldItalic.ttf");
            final Typeface regular = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");

            final Typeface normal = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");
            final Typeface monospace = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
            final Typeface serif = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");
            final Typeface sansSerif = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");
            final Typeface sans = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");

            Field DEFAULT = Typeface.class.getDeclaredField("DEFAULT");
            DEFAULT.setAccessible(true);
            DEFAULT.set(null, regular);

            Field DEFAULT_BOLD = Typeface.class.getDeclaredField("DEFAULT_BOLD");
            DEFAULT_BOLD.setAccessible(true);
            DEFAULT_BOLD.set(null, bold);

            Field SERIF = Typeface.class.getDeclaredField("SERIF");
            SERIF.setAccessible(true);
            SERIF.set(null, serif);

            Field SANS_SERIF = Typeface.class.getDeclaredField("SANS_SERIF");
            SANS_SERIF.setAccessible(true);
            SANS_SERIF.set(null, sansSerif);

            Field SANS = Typeface.class.getDeclaredField("SANS");
            SANS.setAccessible(true);
            SANS.set(null, sans);

            Field NORMAL = Typeface.class.getDeclaredField("NORMAL");
            NORMAL.setAccessible(true);
            NORMAL.set(null, normal);

            Field MONOSPACE = Typeface.class.getDeclaredField("MONOSPACE");
            MONOSPACE.setAccessible(true);
            MONOSPACE.set(null, monospace);

            Field sDefaults = Typeface.class.getDeclaredField("sDefaults");
            sDefaults.setAccessible(true);
            sDefaults.set(null, new Typeface[]{
                    regular, bold, italic, boldItalic, monospace, serif, sansSerif, normal, sans
            });

        } catch (Throwable e) {
            //cannot crash app if there is a failure with overriding the default font!
            System.out.println(e);
        }
    }

    public static boolean checkForToken() {
        Context context = getInstance().getApplicationContext();
        SharedPreferences prfs = context.getSharedPreferences(AppConstants.PREFERENCE_NAME_SESSION, Context.MODE_PRIVATE);
        String token = prfs.getString(AppConstants.PREFERENCE_KEY_SESSION_TOKEN, "");
        String fcmID = prfs.getString(AppConstants.PREFERENCE_KEY_SESSION_FCM_ID, "");
        String email = prfs.getString(AppConstants.PREFERENCE_KEY_SESSION_EMAIL, "");
        String userID = prfs.getString(AppConstants.PREFERENCE_KEY_SESSION_USERID, "");
        String profilePhoto = prfs.getString(AppConstants.PREFERENCE_KEY_SESSION_PROFILE_PHOTO, "");
//        String coverPhoto = prfs.getString(AppConstants.PREFERENCE_KEY_SESSION_COVER_PHOTO, "");
        String name = prfs.getString(AppConstants.PREFERENCE_KEY_SESSION_NAME, "");
        String firstName = prfs.getString(AppConstants.PREFERENCE_KEY_SESSION_FIRSTNAME, "");
        String lastName = prfs.getString(AppConstants.PREFERENCE_KEY_SESSION_LASTNAME, "");
        String phone = prfs.getString(AppConstants.PREFERENCE_KEY_SESSION_PHONE, "");
        String password = prfs.getString(AppConstants.PREFERENCE_KEY_SESSION_PASSWORD, "");
        String gender = prfs.getString(AppConstants.PREFERENCE_KEY_SESSION_GENDER, "");
        String DOB = prfs.getString(AppConstants.PREFERENCE_KEY_SESSION_DOB, "");
        String locale = prfs.getString(AppConstants.PREFERENCE_KEY_SESSION_LOCALE, Locale.getDefault().getLanguage());
//        boolean isFirstTime = prfs.getBoolean(AppConstants.PREFERENCE_KEY_SESSION_IS_FIRST_TIME, true);
        boolean isPhoneVerified = prfs.getBoolean(AppConstants.PREFERENCE_KEY_SESSION_IS_PHONE_VERIFIED, false);
//        boolean isPremiumMember = prfs.getBoolean(AppConstants.PREFERENCE_KEY_SESSION_IS_PREMIUM_MEMBER, false);
      /*  boolean isEmailVerified = prfs.getBoolean(AppConstants.PREFERENCE_IS_EMAIL_VERIFIED, false);
        boolean isRegistrationCompleted = prfs.getBoolean(AppConstants.PREFERENCE_IS_REGISTRATION_COMPLETED, false);
*/
        Log.i(TAG, "checkForToken: " + prfs.getAll());
        if (!"".equals(token)) {
            Config.getInstance().setAuthToken(token);
            Config.getInstance().setUserID(userID);
            Config.getInstance().setFcmID(fcmID);
            Config.getInstance().setProfilePhoto(profilePhoto);
            Config.getInstance().setEmail(email);
            Config.getInstance().setName(name);
            Config.getInstance().setFirstName(firstName);
            Config.getInstance().setLastName(lastName);
            Config.getInstance().setPassword(password);
            Config.getInstance().setPhone(phone);
            Config.getInstance().setGender(gender);
            Config.getInstance().setDOB(DOB);
            Config.getInstance().setLocale(locale);
            Config.getInstance().setPhoneVerified(isPhoneVerified);

            if (Config.getInstance().getCurrentLatitude() == null) {
                Config.getInstance().setCurrentLatitude("");
                Config.getInstance().setCurrentLongitude("");
            }
            return true;
        } else {
            if (Config.getInstance().getCurrentLatitude() == null) {
                Config.getInstance().setCurrentLatitude("");
                Config.getInstance().setCurrentLongitude("");
            }
            return false;
        }

    }

    public static void saveToken(Context context) {

        System.out.println("SAVE STARTED");
        SharedPreferences preferences = context.getSharedPreferences(AppConstants.PREFERENCE_NAME_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_TOKEN, Config.getInstance().getAuthToken());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_FCM_ID, Config.getInstance().getFcmID());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_USERID, Config.getInstance().getUserID());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_PROFILE_PHOTO, Config.getInstance().getProfilePhoto());
//        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_COVER_PHOTO, Config.getInstance().getCoverPhoto());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_EMAIL, Config.getInstance().getEmail());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_FIRSTNAME, Config.getInstance().getFirstName());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_LASTNAME, Config.getInstance().getLastName());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_NAME, Config.getInstance().getName());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_PHONE, Config.getInstance().getPhone());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_ADDRESS, Config.getInstance().getAddress());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_GENDER, Config.getInstance().getGender());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_DOB, Config.getInstance().getDOB());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_LOCALE, Config.getInstance().getLocale());
//        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_GCM_ID, Config.getInstance().getGCMID());
//        editor.putBoolean(AppConstants.PREFERENCE_KEY_SESSION_IS_FIRST_TIME, Config.getInstance().isFirstTime());
        editor.putBoolean(AppConstants.PREFERENCE_KEY_SESSION_IS_PHONE_VERIFIED, Config.getInstance().isPhoneVerified());
//        editor.putBoolean(AppConstants.PREFERENCE_KEY_SESSION_IS_PREMIUM_MEMBER, Config.getInstance().isPremiumMember());
        editor.commit();
        System.out.println("SAVE COMPLETE");
    }

//    public static void saveToken(AuthBean authBean) {
//        Context context = getInstance().getApplicationContext();
//        FileOp fileOp = new FileOp(context);
//        setConfig(authBean);
//        Log.i(TAG, "saveToken: AuthBean : " + new Gson().toJson(authBean));
//
//        System.out.println("SAVE STARTED");
//        SharedPreferences preferences = context.getSharedPreferences(AppConstants.PREFERENCE_NAME_SESSION, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.clear();
//        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_TOKEN, authBean.getAuthToken());
//        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_FCM_ID, Config.getInstance().getFcmID());
//        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_USERID, authBean.getUserID());
//        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_LOCALE, Config.getInstance().getLocale());
//        editor.putBoolean(AppConstants.PREFERENCE_KEY_SESSION_IS_PHONE_VERIFIED, authBean.isPhoneVerified());
//        editor.commit();
//        System.out.println("SAVE COMPLETE");
//    }

//    private static void setConfig(AuthBean authBean) {
//        Config.getInstance().setAuthToken(authBean.getAuthToken());
//        Config.getInstance().setUserID(authBean.getUserID());
//        Config.getInstance().setProfilePhoto(authBean.getProfilePhoto());
////        Config.getInstance().setCoverPhoto(authBean.getCoverPhoto());
//        Config.getInstance().setEmail(authBean.getEmail());
//        Config.getInstance().setName(authBean.getName());
//        Config.getInstance().setFirstName(authBean.getFirstName());
//        Config.getInstance().setLastName(authBean.getLastName());
////            Config.getInstance().setUsername(username);
//        Config.getInstance().setPhone(authBean.getPhone());
//        Config.getInstance().setAddress(authBean.getAddress());
//        Config.getInstance().setGender(authBean.getGender());
//        Config.getInstance().setDOB(authBean.getDOB());
//        Config.getInstance().setPhoneVerified(authBean.isPhoneVerified());
////        Config.getInstance().setPremiumMember(authBean.isPremiumMember());
////            Config.getInstance().setEmailVerified(isEmailVerified);
////            Config.getInstance().setRegistrationCompleted(isRegistrationCompleted);
////        Config.getInstance().setFirstTime(false);
//        if (Config.getInstance().getCurrentLatitude() == null) {
//            Config.getInstance().setCurrentLatitude("");
//            Config.getInstance().setCurrentLongitude("");
//        }
//
//    }

    public static void logout() {
        Context context = getInstance().getApplicationContext();
        SharedPreferences preferences = context.getSharedPreferences(AppConstants.PREFERENCE_NAME_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        //editor.remove(AppConstants.PREFERENCE_KEY_SESSION_TOKEN);
        editor.commit();

        Config.clear();

//        new DBHandler(context).clearDatabase();
//        Digits.logout();
//        FCMRefreshTask fcmRefreshTask = new FCMRefreshTask();
//        fcmRefreshTask.execute();
        FirebaseAuth.getInstance().signOut();
        clearApplicationData(context);
//        restart(context, 500);

    }


    public static void clearApplicationData(Context context) {
        File cache = context.getFilesDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                //		if (!s.equals("lib")) {
                (new File(appDir, s)).delete();
                //		}
            }
        }
    }
}
