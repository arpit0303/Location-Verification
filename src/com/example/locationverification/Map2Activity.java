package com.example.locationverification;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class Map2Activity extends Activity {

	GoogleMap map;
	private double latitude;
	private double longitude;
	String loc;
	private static final String TAG = MapActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		getActionBar().hide();

		loc = getIntent().getStringExtra("location");

		Log.i("hello", loc);

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		// map.setMyLocationEnabled(true);

		LocationManager locMngr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		// Create a criteria object to retrieve provider
		Criteria criteria = new Criteria();

		// Get the name of the best provider
		String provider = locMngr.getBestProvider(criteria, true);

		// Get Current Location
		Location myLocation = locMngr.getLastKnownLocation(provider);

		latitude = myLocation.getLatitude();
		longitude = myLocation.getLongitude();
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(latitude, longitude)).zoom(14).build();

		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

		map.addMarker(new MarkerOptions().position(
				new LatLng(latitude, longitude)).title("hi"));

	}

	public class GetMapTask extends AsyncTask<Object, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(Object... params) {
			int responseCode = -1;
			JSONObject jsonresponse = null;
			try {
				// connecting to a URL

				URL mapurl = new URL(
						"http://maps.googleapis.com/maps/api/geocode/json?address="
								+ loc + "&sensor=true");
				HttpURLConnection connection = (HttpURLConnection) mapurl
						.openConnection();
				connection.connect();

				// Get the response code which said connection is done or not
				responseCode = connection.getResponseCode();

				Log.i("main", responseCode + " ");
				if (responseCode == HttpURLConnection.HTTP_OK) {
					InputStream inputstream = connection.getInputStream();
					Reader reader = new InputStreamReader(inputstream);
					int contetntlength = connection.getContentLength();
					char[] charArray = new char[contetntlength];
					reader.read(charArray);
					String ReasponseData = new String(charArray);

					Log.i(TAG, ReasponseData);
					jsonresponse = new JSONObject(ReasponseData);
				} else {
					Log.i(TAG, "unsuccessful HTTP Response code: "
							+ responseCode);
				}

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "Exception caught: ", e);
			} catch (IOException e) {
				Log.e(TAG, "Exception caught: ", e);
			} catch (Exception e) {
				Log.e(TAG, "Exception caught: ", e);
			}
			return jsonresponse;
		}
	}

}
