package org.androidtown.lbs.map;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * ���� ��ġ�� ������ �����ְ� �� ���� �������̸� �߰��ϴ� ����� ���� �� �� �ֽ��ϴ�.
 * �� ��ġ ǥ�ø� �� �ݴϴ�.
 * ���� ������ �̿��� ��ħ���� ȭ�鿡 ǥ���մϴ�.
 * 
 * Google APIs ���� �ϳ��� �÷������� �����ؾ� �մϴ�.
 * ���۸� v2�� ����ϱ� ���� ���� ���� ������ �־�� �մϴ�.
 * �Ŵ��佺Ʈ ���� �ȿ� �ִ� Ű ���� PC�� �´� ������ ���� �߱޹޾Ƽ� �־�� �մϴ�.
 * 
 * @author Mike
 */
public class MainActivity extends Activity {
  
	private RelativeLayout mainLayout;
	private GoogleMap map;
	
	private CompassView mCompassView;
    private SensorManager mSensorManager;
    private boolean mCompassEnabled;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ���� ���̾ƿ� ��ü ����
        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        
        // ���� ��ü ����
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
             

   	 	// ���� ������ ��ü ����
   	 	mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

		// ��ħ���� ǥ���� �� ����
		boolean sideBottom = true;
	   	mCompassView = new CompassView(this);
	    mCompassView.setVisibility(View.VISIBLE);

	    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
	    		RelativeLayout.LayoutParams.WRAP_CONTENT, 
	    		RelativeLayout.LayoutParams.WRAP_CONTENT);
	    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
	    params.addRule(sideBottom ? RelativeLayout.ALIGN_PARENT_BOTTOM : RelativeLayout.ALIGN_PARENT_TOP);
	    
	    mainLayout.addView(mCompassView, params);
	    

	    mCompassEnabled = true;
	    
        
        
        // ��ġ Ȯ���Ͽ� ��ġ ǥ�� ����
        startLocationService();
    }

    @Override
	public void onResume() {
		super.onResume();

		// �� ��ġ �ڵ� ǥ�� enable
		map.setMyLocationEnabled(true);
		if(mCompassEnabled) {
            mSensorManager.registerListener(mListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);
		}
	}

	@Override
	public void onPause() {
		super.onPause();

		// �� ��ġ �ڵ� ǥ�� disable
		map.setMyLocationEnabled(false);
		if(mCompassEnabled) {
			mSensorManager.unregisterListener(mListener);
		}
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
		
		// ���� ��ġ ������ �������� ǥ���ϱ� ���� ������ �޼ҵ�
		showAllBankItems(latitude, longitude);
	}


	/**
	 * �������� ǥ���ϱ� ���� ������ �޼ҵ�
	 */
	private void showAllBankItems(Double latitude, Double longitude) {
		MarkerOptions marker = new MarkerOptions();
		marker.position(new LatLng(latitude+0.001, longitude+0.001));
		marker.title("�� ������ : \n ��������(����������)\n");
		marker.snippet("�� �ּ� : \n ����� ���Ǳ� �����뵿");
		marker.draggable(true);
		marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.bank));
		
		map.addMarker(marker);
	}

	
	/**
	 * ������ ������ �ޱ� ���� ������ ��ü ����
	 */
	private final SensorEventListener mListener = new SensorEventListener() {
        private int iOrientation = -1;

        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        // ������ ���� ���� �� �ֵ��� ȣ��Ǵ� �޼ҵ�
        public void onSensorChanged(SensorEvent event) {
            if (iOrientation < 0) {
                    iOrientation = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
            }
            
            mCompassView.setAzimuth(event.values[0] + 90 * iOrientation);
            mCompassView.invalidate();

        }

	};

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
