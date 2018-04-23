package com.axon.ars;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class webServices {
	private final String NAMESPACE = "http://tempuri.org/";
	private final String URL = "http://hackthonTest.azurewebsites.net/WebServices/WebService.asmx";

	private final String sendDataSOAP_ACTION = "http://tempuri.org/acceptData";
	private final String sendDataMETHOD_NAME = "acceptData";
	private final String getDataSOAP_ACTION = "http://tempuri.org/getIncident";
	private final String getDataMETHOD_NAME = "getIncident";

	private final String getRssSOAP_ACTION = "http://tempuri.org/getRss";
	private final String getRssMETHOD_NAME = "getRss";

	
	private String TAG = "MUZAMMIL";
	private String ans;

	public ArrayList<IncidentInfo> getData(int id) {
		int count = 0, a = 0;
		try {
			SoapObject request = new SoapObject(NAMESPACE, getDataMETHOD_NAME);
			PropertyInfo addData = new PropertyInfo();
			addData.setName("Id");
			addData.setValue(id);
			addData.setType(Integer.class);
			request.addProperty(addData);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;

			envelope.setOutputSoapObject(request);
			envelope.addMapping(NAMESPACE, "IncidentInfo",
					new IncidentInfo().getClass());

			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.call(getDataSOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.getResponse();
			if (response != null) {
				ArrayList<IncidentInfo> IncidentsInfo = new ArrayList<IncidentInfo>();
				count = response.getPropertyCount();
				for (int i = 0; i < count; i++) {
					SoapObject responseChild = (SoapObject) response
							.getProperty(i);
					IncidentInfo incidentInfo = new IncidentInfo();
					incidentInfo.Id = Integer.parseInt(responseChild
							.getProperty(0).toString());
					incidentInfo.Latitude = responseChild.getProperty(1)
							.toString();
					incidentInfo.Longitude = responseChild.getProperty(2)
							.toString();
					incidentInfo.Title = responseChild.getProperty(3)
							.toString();
					incidentInfo.Info = responseChild.getProperty(4).toString();
					IncidentsInfo.add(incidentInfo);
				}

				return IncidentsInfo;
			} else {
				return null;
			}

			//

		} catch (Exception e) {
			e.printStackTrace();

		}

		return null;
	}

	public ArrayList<Feeds> getRss() {
		int count = 0, a = 0;
		try {
			SoapObject request = new SoapObject(NAMESPACE, getRssMETHOD_NAME);
			

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;

			envelope.setOutputSoapObject(request);
			envelope.addMapping(NAMESPACE, "Feeds",
					new Feeds().getClass());

			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.call(getRssSOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.getResponse();
			if (response != null) {
				ArrayList<Feeds> feedsList = new ArrayList<Feeds>();
				count = response.getPropertyCount();
				for (int i = 0; i < count; i++) {
					SoapObject responseChild = (SoapObject) response
							.getProperty(i);
					Feeds feeds = new Feeds();
					feeds.Title = responseChild.getProperty(0)
							.toString();
					feeds.Description = responseChild.getProperty(1)
							.toString();
					
					feedsList.add(feeds);
				}

				return feedsList;
			} else {
				return null;
			}

			//

		} catch (Exception e) {
			e.printStackTrace();

		}

		return null;
	}

	public String sendData(String NIC, String Name, String accidentCategory,
			String accidentDescription, String len, String lat,
			String streetAddress, String strBase64, String strBase642) {
		SoapObject request = new SoapObject(NAMESPACE, sendDataMETHOD_NAME);
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
			androidHttpTransport.call(sendDataSOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.bodyIn;
			ans = response.getProperty(0).toString();

		} catch (Exception e) {
			e.printStackTrace();
			// response.setText("not getting data");
		}

		return ans;
	}

}
