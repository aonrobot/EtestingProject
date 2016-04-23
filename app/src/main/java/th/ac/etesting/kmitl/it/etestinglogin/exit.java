package th.ac.etesting.kmitl.it.etestinglogin;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.provider.Settings;

/**
 * Created by jakubszczygiel on 15/11/2014.
 */
public class Exit extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.exit(1);
    }

}
