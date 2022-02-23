package com.matrixdeveloper.tajika.utils;

import com.matrixdeveloper.tajika.model.ServiceProvider;
import com.matrixdeveloper.tajika.model.ServiceProviderDetails;

import java.util.ArrayList;
import java.util.List;

public class Global {

    public static String user_id = "id";
    public static String email = "email";
    public static String phone = "phone";
    public static String token = "token";
    public static String role = "role";
    public static String name = "name";
    public static String rating = "rating";
    public static String profileImage = "profileImage";

    //For comparison of providers
    public static String compareAddOne = "Click to add first provider";
    public static String compareAddOneServiceID = "";
    public static String compareAddTwo = "Click to add second provider";
    public static String compareAddTwoServiceID = "";
    public static List<ServiceProviderDetails> firstItemToCompare=new ArrayList<>();
    public static List<ServiceProviderDetails> secondItemToCompare=new ArrayList<>();

}
