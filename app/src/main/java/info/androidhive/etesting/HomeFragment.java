package info.androidhive.etesting;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import th.ac.etesting.kmitl.it.etestinglogin.EtestingFunctions;
import th.ac.etesting.kmitl.it.etestinglogin.VarSession;

public class HomeFragment extends Fragment {

	public HomeFragment(){}

    //public MainActivity main = (MainActivity) getActivity();

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        //Get Password From MainActivity
        MainActivity main = (MainActivity) getActivity();
        //Toast.makeText(getActivity().getBaseContext(), main.getTpass(), Toast.LENGTH_LONG).show();

        //Create Webview
        WebView mWebView = (WebView)  rootView.findViewById(R.id.webview);
        mWebView.clearCache(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.loadUrl("http://192.168.1.115/tao/Main/login");

        mWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);

        mWebView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                MainActivity main = (MainActivity) getActivity();
                String user = main.getTid();
                String pwd = main.getTpass();
                view.loadUrl("javascript:var x = document.getElementById('login').value = '" + user + "';var y = document.getElementById('password').value='" + pwd + "';");
                //view.loadUrl("javascript:var insert = document.getElementById('lst-ib').value = '" + user + "';");
                view.loadUrl("javascript:(function(){" +
                        "l=document.getElementById('connect');" +
                        "e=document.createEvent('HTMLEvents');" +
                        "e.initEvent('click',true,true);" +
                        "l.dispatchEvent(e);" +
                        "})()");
            }

        });

        /*WebView view = (WebView)  rootView.findViewById(R.id.webview);
        view.getSettings(). setJavaScriptEnabled(true);
        view.setWebViewClient(new MyBrowser());
        view.loadUrl("http://192.168.1.115/taoDelivery/DeliveryServer/index");

        view.getSettings().setBuiltInZoomControls(true);
        view.getSettings().setSupportZoom(true);*/



        return rootView;
    }


    /*private  class  MyBrowser  extends WebViewClient
    {@Override
     public boolean shouldOverrideUrlLoading (WebView view, String url) {
        view.loadUrl (url);
        return  true;
    }
    }*/

}
