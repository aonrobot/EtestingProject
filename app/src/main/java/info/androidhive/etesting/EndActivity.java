package info.androidhive.etesting;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;
import com.wroclawstudio.kioskmode.KioskActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.jar.Manifest;

public class EndActivity extends KioskActivity implements AsyncResponse{

    public String tid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        final Button endBtn = (Button)findViewById(R.id.end);

        Intent i=getIntent();
        tid = i.getStringExtra("tid");

        //Toast.makeText(getApplicationContext(), tid, Toast.LENGTH_LONG).show();

        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, String> postData = new HashMap<String, String>();

                postData.put("testerId", tid);

                PostResponseAsyncTask registerTask = new PostResponseAsyncTask(EndActivity.this, postData);
                registerTask.execute("http://192.168.1.131/eexam/tester/tester.end.php");
            }
        });

    }

    @Override
    public void processFinish(String result) {
        Log.i("End",result);
        if(result.toLowerCase().contains("Host 'aonrobot' is not allowed to connect to this MySQL server".toLowerCase())){
            Toast.makeText(getApplicationContext(),"Can't Connect MySQL Server, Please Contact Administrator.",Toast.LENGTH_LONG).show();
        }
        try {

            JSONObject reader = new JSONObject(result);
            String status = reader.getString("status");

            if(status.equals("yes")){
                startActivity(new Intent(EndActivity.this, th.ac.etesting.kmitl.it.etestinglogin.MainActivity.class));
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(),"Please Sent Your Test First!!",Toast.LENGTH_LONG).show();
                startActivity(new Intent(EndActivity.this, info.androidhive.etesting.MainActivity.class));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
