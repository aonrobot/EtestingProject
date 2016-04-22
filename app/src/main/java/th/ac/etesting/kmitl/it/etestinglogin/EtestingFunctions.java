package th.ac.etesting.kmitl.it.etestinglogin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by PaRaganDa on 20/2/2559.
 */
public class EtestingFunctions{

    public String getIMEI(Context context){

        TelephonyManager mngr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mngr.getDeviceId();
        return imei;

    }

    public void ConnectWIFI(Context context){

        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        // setup a wifi configuration
        WifiConfiguration wc = new WifiConfiguration();
        wc.SSID = "\"etesting\"";
        wc.preSharedKey = "\"1234567890\"";
        wc.status = WifiConfiguration.Status.ENABLED;
        wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        wc.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        // connect to and enable the connection
        int netId = wifiManager.addNetwork(wc);

        if (wifiManager.isWifiEnabled()) { //---wifi is turned on---
            //---disconnect it first---
            wifiManager.disconnect();
        } else { //---wifi is turned off---
            //---turn on wifi---
            wifiManager.setWifiEnabled(true);
        }

        wifiManager.enableNetwork(netId, true);
        wifiManager.startScan();

    }

    public boolean checkWIFI(Context context){

        boolean connected = false;

        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        Toast.makeText(context, "wifi info " + wifiInfo, Toast.LENGTH_LONG).show();
        if (wifiInfo != null) {
            Toast.makeText(context, "Wifiname is " + wifiInfo.getSSID(), Toast.LENGTH_LONG).show();
            connected = wifiInfo.getSSID().equals("\"etesting\"");
        }
        return connected;
    }
}
