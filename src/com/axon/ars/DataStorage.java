package com.axon.ars;

public class DataStorage {
	private static double latData;
	private static double lngData;
	private static String addressData;

	public void setData(double lat, double lng, String add) {

		latData = lat;
		lngData = lng;
		addressData = add;

	}

	public double getLatData() {
		return latData;
	}

	public double getLngData() {
		return lngData;
	}

	public  String getAddressData() {
		return addressData;
	}

}
