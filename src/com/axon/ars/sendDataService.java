package com.axon.ars;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class sendDataService {
	private final String NAMESPACE = "http://tempuri.org/";
	private final String URL = "http://hackthonTest.azurewebsites.net/WebServices/WebService.asmx";

	// private final String URL = "http://localhost/dataTransfer.asmx";
	private final String SOAP_ACTION = "http://tempuri.org/acceptData";
	private final String METHOD_NAME = "acceptData";
	private String TAG = "MUZAMMIL";
	private String ans;

	public String sendData(String NIC, String Name, String accidentCategory,
			String accidentDescription, String len, String lat,
			String streetAddress, String strBase64, String strBase642) {
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		PropertyInfo addData = new PropertyInfo();
		addData.setName("phoneNumber");
		addData.setValue(NIC);
		addData.setType(String.class);
		request.addProperty(addData);

		PropertyInfo addName = new PropertyInfo();
		addName.setName("Name");
		addName.setValue(Name);
		addName.setType(String.class);
		request.addProperty(addName);

		PropertyInfo addType = new PropertyInfo();
		addType.setName("type");
		addType.setValue(accidentCategory);
		addType.setType(String.class);
		request.addProperty(addType);

		PropertyInfo addDescription = new PropertyInfo();
		addDescription.setName("description");
		addDescription.setValue(accidentDescription);
		addDescription.setType(String.class);
		request.addProperty(addDescription);

		PropertyInfo addLongitude = new PropertyInfo();
		addLongitude.setName("longitude");
		addLongitude.setValue(len);
		addLongitude.setType(float.class);
		request.addProperty(addLongitude);

		PropertyInfo addLatitude = new PropertyInfo();
		addLatitude.setName("latitude");
		addLatitude.setValue(lat);
		addLatitude.setType(float.class);
		request.addProperty(addLatitude);

		PropertyInfo addAddress = new PropertyInfo();
		addAddress.setName("address");
		addAddress.setValue(streetAddress);
		addData.setType(String.class);
		request.addProperty(addAddress);

		PropertyInfo addImage1 = new PropertyInfo();
		addImage1.setName("strBase64");
		addImage1.setValue(strBase64);
		addImage1.setType(String.class);
		request.addProperty(addImage1);

		PropertyInfo addImage2 = new PropertyInfo();
		addImage2.setName("strBase642");
		addImage2.setValue(strBase642);
		addImage2.setType(String.class);
		request.addProperty(addImage2);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;

		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive responsed = (SoapPrimitive) envelope.getResponse();
			ans = responsed.toString();

		} catch (Exception e) {
			return e.toString();
			// response.setText("not getting data");
		}

		return ans;
	}

}
