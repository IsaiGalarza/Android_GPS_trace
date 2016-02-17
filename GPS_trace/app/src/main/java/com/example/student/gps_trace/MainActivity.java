package com.example.student.gps_trace;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    EditText useNameInput;
    Button traceStart;
    Button traceStop;
    Button traceView;

    LocationManager locationManager ;
    LocationReceiver receiver = new LocationReceiver();

    String userName;
    String latitude;
    String longitude;

    URL url;
    HttpURLConnection httpURLConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        useNameInput = (EditText) findViewById(R.id.editText);
        traceStart = (Button) findViewById( R.id.start );
        traceStop = (Button) findViewById( R.id.stop );
        traceView = (Button) findViewById( R.id.view);

        traceStop.setClickable(false);
        traceView.setClickable(false);

        traceStart.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = useNameInput.getText().toString();
                if (userName.isEmpty())
                    Toast.makeText(MainActivity.this, "請輸入使用者名稱", Toast.LENGTH_LONG).show();
                else {
                    traceStop.setClickable(true);
                    traceView.setClickable(true);
                    traceStart.setClickable(false);
                    useNameInput.setEnabled(false);

                    locationManager = (LocationManager) MainActivity.this.getSystemService(Context.LOCATION_SERVICE);
                    String gpsDevice = locationManager.getAllProviders().get(1);
                    locationManager.requestLocationUpdates(gpsDevice, 0, 0.0f, receiver);
                }
            }
        });


        traceStop.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                useNameInput.setEnabled(true);
                traceStart.setClickable(true);
                locationManager.removeUpdates(receiver);
            }
        });


    }

    private class LocationReceiver implements LocationListener{
        @Override
        public void onLocationChanged(Location location) {
            longitude = String.valueOf(location.getLongitude());
            latitude = String.valueOf(location.getLatitude());
            try {
                url = new URL("http://192.168.0.102:8084/Gpsgeter/gpsupdate.jsp?username="+userName+"&longitude="+longitude+"&latitude="+latitude);
                new HttpUpdate().execute(url);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    private class HttpUpdate extends AsyncTask<URL,Void,Void>{
        @Override
        protected Void doInBackground(URL... params) {
            try{
                Log.i("URL",params[0].toString());
                httpURLConnection = (HttpURLConnection) params[0].openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();
            }catch (IOException e){
                Log.i("IOEXception",e.getMessage());
            }
            return null;
        }
    }
}
