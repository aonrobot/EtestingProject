package th.ac.etesting.kmitl.it.etestinglogin;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import info.androidhive.etesting.MainActivity;
import info.androidhive.etesting.R;

public class PrepairActivity extends AppCompatActivity {

    final EtestingFunctions func = new EtestingFunctions();

    private int splashTime = 3000;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepair);

        thread = new Thread(runable);
        thread.start();
        //finish();
    }

    public Runnable runable = new Runnable() {
        public void run() {
            try {
                Thread.sleep(splashTime);
                String ssid;
                do {

                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();

                    ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                    ssid = wifiInfo.getSSID();

                    if (mWifi.isConnected()) {
                        if (!ssid.equals("\"etesting\"")) {
                            func.ConnectWIFI(getApplicationContext());
                        }
                    } else {
                        func.ConnectWIFI(getApplicationContext());
                    }

                    Thread.sleep(5000);


                }while(!func.checkWIFI(getApplicationContext()));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                startActivity(new Intent(PrepairActivity.this, th.ac.etesting.kmitl.it.etestinglogin.MainActivity.class));
                finish();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    };
}
