package org.androidtown.lbs.map;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;


/**
 * ���� ��ġ�� ������ �����ִ� ����� ���� �� �� �ֽ��ϴ�.
 * 
 * Google APIs ���� �ϳ��� �÷������� �����ؾ� �մϴ�.
 * ���۸� v2�� ����ϱ� ���� ���� ���� ������ �־�� �մϴ�.
 * �Ŵ��佺Ʈ ���� �ȿ� �ִ� Ű ���� PC�� �´� ������ ���� �߱޹޾Ƽ� �־�� �մϴ�.
 * 
 * @author Mike
 */
public class MainActivity extends Activity {
  
	private GoogleMap map;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ���� ��ü ����
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
             
        // ��ġ Ȯ���Ͽ� ��ġ ǥ�� ����
        startLocationService();
    }
 
    
    /**
     * ���� ��ġ Ȯ���� ���� ������ �޼ҵ�
     */
    private void startLocationService() {
    	// ��ġ ������ ��ü ����
    	LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		// ������ ��ü ����
    	GPSListener gpsListener = new GPSListener();
		long minTime = 10000;
		float minDistance = 0;

		// GPS ��� ��ġ ��û
		manager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER,
					minTime,
					minDistance,
					gpsListener);
		
		// ��Ʈ��ũ ��� ��ġ ��û
		manager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER,
				minTime,
				minDistance,
				gpsListener);

		Toast.makeText(getApplicationContext(), "��ġ Ȯ�� ������. �α׸� Ȯ���ϼ���.", Toast.LENGTH_SHORT).show();
    }

    /**
     * ������ ����
     */
	private class GPSListener implements LocationListener {
		/**
		 * ��ġ ������ Ȯ�εǾ��� �� ȣ��Ǵ� �޼ҵ�
		 */
	    public void onLocationChanged(Location location) {
			Double latitude = location.getLatitude();
			Double longitude = location.getLongitude();

			String msg = "Latitude : "+ latitude + "\nLongitude:"+ longitude;
			Log.i("GPSLocationService", msg);
			
			// ���� ��ġ�� ������ �����ֱ� ���� ������ �޼ҵ� ȣ��
			showCurrentLocation(latitude, longitude);
		}

	    public void onProviderDisabled(String provider) {
	    }

	    public void onProviderEnabled(String provider) {
	    }

	    public void onStatusChanged(String provider, int status, Bundle extras) {
	    }

	}

	/**
	 * ���� ��ġ�� ������ �����ֱ� ���� ������ �޼ҵ�
	 * 
	 * @param latitude
	 * @param longitude
	 */
	private void showCurrentLocation(Double latitude, Double longitude) {
		// ���� ��ġ�� �̿��� LatLon ��ü ����
		LatLng curPoint = new LatLng(latitude, longitude);

		map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));

		// ���� ���� ����. �������� ��쿡�� GoogleMap.MAP_TYPE_TERRAIN, ���� ������ ��쿡�� GoogleMap.MAP_TYPE_SATELLITE
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL); 
		
	}


	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
