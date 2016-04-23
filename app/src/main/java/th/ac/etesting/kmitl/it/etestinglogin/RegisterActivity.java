package th.ac.etesting.kmitl.it.etestinglogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import info.androidhive.etesting.R;

public class RegisterActivity extends Activity implements AsyncResponse {

    final EtestingFunctions func = new EtestingFunctions();

    EditText idEdt,otpEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        idEdt = (EditText) findViewById(R.id.idEdt);
        otpEdt = (EditText) findViewById(R.id.otpEdt);

        final Button cancelBtn = (Button)findViewById(R.id.cancelBtn);
        final Button registerBtn = (Button)findViewById(R.id.registerBtn);

        final VarSession session = (VarSession)getApplicationContext();
        //Toast.makeText(getApplicationContext(), "private_key => "+session.getPrivateKey(), Toast.LENGTH_LONG).show();



        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, String> postData = new HashMap<String, String>();

                postData.put("id", idEdt.getText().toString());
                postData.put("otp", otpEdt.getText().toString());
                postData.put("imei", func.getIMEI(getApplicationContext()));
                postData.put("private", session.getPrivateKey());

                //Keep Tester Id and OTP
                session.setTesterId(idEdt.getText().toString().trim());
                session.setOtp(otpEdt.getText().toString().trim());

                PostResponseAsyncTask registerTask = new PostResponseAsyncTask(RegisterActivity.this, postData);
                registerTask.execute("http://192.168.1.131/eexam/tester/tester.register.php");
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
    }

    public void processFinish(String result){
        //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
        Log.i("Register",result);
        if(result.equals("")){
            Toast.makeText(getApplicationContext(),"Sorry!, Server Is Down, Please Contact Administrator.",Toast.LENGTH_LONG).show();
        }
        if(result.toLowerCase().contains("Notice".toLowerCase())){
            Toast.makeText(getApplicationContext(),"Server Error, Please Contact Administrator.",Toast.LENGTH_LONG).show();
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        }
        try {

            JSONObject reader = new JSONObject(result);
            String status = reader.getString("status");
            String login_password = reader.getString("password");
            String testerId = reader.getString("testerId");
            String testId = reader.getString("testId");

            if(status.equals("yes")){
                final VarSession session = (VarSession)getApplicationContext();
                session.setPassword(login_password.toString().trim());

                //session.setTestId(testId.toString().trim());

                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
            }
            else if(status.equals("wrongOTP")){
                Toast.makeText(getApplicationContext(),"Incorrect Tester Id or OTP",Toast.LENGTH_LONG).show();
            }
            else if(status.equals("notHaveTest")){
                Toast.makeText(getApplicationContext(),"You Don't Have A Test Now",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"You Can't RegisterActivity Your Device!",Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
