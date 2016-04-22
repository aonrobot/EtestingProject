package info.androidhive.etesting;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

import com.wroclawstudio.kioskmode.KioskActivity;

public class PrepareActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrepareActivity.this, MainActivity.class));
                //KioskActivity.startKioskActivity(PrepareActivity.this, MainActivity.class);
            }
        });

    }

}
