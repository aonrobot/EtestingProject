package info.androidhive.etesting;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class HomeFragment extends Fragment {
	
	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        WebView view = (WebView)  rootView.findViewById(R.id.webview);
        view.getSettings(). setJavaScriptEnabled(true);
        view.setWebViewClient(new MyBrowser());
        view.loadUrl("http://tao.soracity.bike/tao/Main/login");

        view.getSettings().setBuiltInZoomControls(true);
        view.getSettings().setSupportZoom(true);





        return rootView;
    }


    private  class  MyBrowser  extends WebViewClient
    {@Override
     public boolean shouldOverrideUrlLoading (WebView view, String url) {
        view.loadUrl (url);
        return  true;
    }
    }


}
