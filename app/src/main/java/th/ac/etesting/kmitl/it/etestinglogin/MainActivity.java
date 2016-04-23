package th.ac.etesting.kmitl.it.etestinglogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;
import com.wroclawstudio.kioskmode.KioskActivity;

import java.util.HashMap;

import info.androidhive.etesting.R;

public class MainActivity extends Activity {

    final EtestingFunctions func = new EtestingFunctions();

    public String private_key = "EtestingProjectPrivateKey";

    public String login_password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        if (!func.checkWIFI(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "Don't Find or Disconnect etesting Wifi Station. Please Contact Administrator.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(MainActivity.this,PrepairActivity.class));
            finish();
        }

        final VarSession session = (VarSession)getApplicationContext();

        session.setPrivateKey(private_key);

        final WebView mWebView = (WebView) findViewById(R.id.webview);
        final Button registerBtn = (Button)findViewById(R.id.registerBtn);
        final Button loginBtn = (Button) findViewById(R.id.loginBtn);
        final Button showBtn = (Button)findViewById(R.id.showBtn);
        final Button hideBtn = (Button)findViewById(R.id.hideBtn);

        loginBtn.setVisibility(View.GONE);//loginBtn.setEnabled(false);

        mWebView.clearCache(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.loadUrl("http://www.google.com");

        mWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);

        mWebView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {

                String user = "it kmitl";
                String pwd = "AONB0tB0t";
                view.loadUrl("javascript:var insert = document.getElementById('lst-ib').value = '" + user + "';");
                view.loadUrl("javascript:(function(){" +
                        "l=document.getElementById('`azq~A`Q1mA');" +
                        "e=document.createEvent('HTMLEvents');" +
                        "e.initEvent('click',true,true);" +
                        "l.dispatchEvent(e);" +
                        "})()");
            }

        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KioskActivity.startKioskActivity(th.ac.etesting.kmitl.it.etestinglogin.MainActivity.this, info.androidhive.etesting.MainActivity.class);
            }
        });
        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.setVisibility(View.VISIBLE);
            }
        });
        hideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        final VarSession session = (VarSession)getApplicationContext();

        login_password = session.getPassword();

        //Toast.makeText(getApplicationContext(), "Welcome back to login page", Toast.LENGTH_LONG).show();

        //Toast.makeText(getApplicationContext(), "password => "+session.getPassword(), Toast.LENGTH_LONG).show();
        final Button registerBtn = (Button)findViewById(R.id.registerBtn);
        final Button loginBtn = (Button) findViewById(R.id.loginBtn);

        if(session.getPassword().equals("")){
            registerBtn.setEnabled(true);
            loginBtn.setVisibility(View.GONE);//loginBtn.setEnabled(false);
        }else{
            registerBtn.setVisibility(View.GONE);
            loginBtn.setVisibility(View.VISIBLE);//loginBtn.setEnabled(true);
        }
    }
}
