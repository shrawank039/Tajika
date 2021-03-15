package com.matrixdeveloper.tajika.model;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;


public class AddressBean {

	private static final long serialVersionUID = -608511678296149395L;
	@Expose
	double longitude;
	@Expose
	double latitude;
	@Expose
	String addressType;
	@Expose
	String customer_id;
	@Expose
	String address_id = "";
	@Expose
	String firstname = "";
	@Expose
	String lastname = "";
	@Expose
	String flat_house_no = "";
	@Expose
	String address_1 = "";
	@Expose
	String address_2 = "";
	@Expose
	String city = "";
	@Expose
	String postcode = "";
	@Expose
	String country_name = "";

	@Expose
	String zone_name="";

	@Expose
	String email="";

	@Expose
	String mobile="";
	private boolean activeShoppingAddressOfCustomers;



	public AddressBean(double lat, double longitude, String address_1, String address_2, String city, String postcode,
                       String statename, String countryName) {
		this.latitude =lat;
		this.longitude =longitude;
		this.address_id = address_id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.address_1 = address_1;
		this.address_2 = address_2;
		this.city = city;
		this.postcode = postcode;
		this.zone_name = statename;
		this.country_name = countryName;
	}

	public String getCountry_name() {
		return country_name;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public void setAddress_id(String address_id) {
		this.address_id = address_id;
	}

	public String getAddress_id() {
		return address_id;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getAddress_1() {
		  if(TextUtils.isEmpty(address_1)){
		  	return "";
		  }
		return address_1;
	}

	public void setFlat_house_no(String flat_house_no) {
		this.flat_house_no = flat_house_no;
	}

	public String getFlat_house_no() {
		if(TextUtils.isEmpty(flat_house_no)){
			return "";
		}
		return flat_house_no;
	}

	public String getAddress_2() {
		return address_2;
	}

	public String getCity() {
		return city;
	}

	public String getPostcode() {
		return postcode;
	}

	public String getZone_name() {
		return zone_name;
	}

	public String getCountryname() {
		return country_name;
	}

	@Override
	public String toString() {
		return String.format("%s , %s , %s , %s , %s, %s, %s",
				getFirstname(),
				getLastname(),
				getAddress_1(),
				getAddress_2(),
				getCity(),
				getZone_name(),
				getPostcode());
	}

	public String getEmail() {
		return email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setAddress_1(String address_1) {
		this.address_1 = address_1;
	}

	public void setAddress_2(String address_2) {
		this.address_2 = address_2;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}

	public void setZone_name(String zone_name) {
		this.zone_name = zone_name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public String getAddressType() {
		  if(TextUtils.isEmpty(addressType)){
		  	return  "";
		  }
		return addressType;
	}

	public void setActiveShoppingAddressOfCustomers(boolean b) {
		this.activeShoppingAddressOfCustomers = b;
	}

	public boolean isActiveShoppingAddressOfCustomers() {
		return activeShoppingAddressOfCustomers;
	}
}
