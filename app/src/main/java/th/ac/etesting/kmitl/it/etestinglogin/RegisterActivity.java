package th.ac.etesting.kmitl.it.etestinglogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.client.android.IntentIntegrator;
import com.google.zxing.client.android.IntentResult;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

public class RegisterActivity extends Activity implements AsyncResponse {

    final EtestingFunctions func = new EtestingFunctions();

    EditText idEdt,otpEdt;
    ImageView otp_status;
    Button registerBtn, cancelBtn, scanBtn;
    TextView statusTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        idEdt = (EditText) findViewById(R.id.idEdt);
        otpEdt = (EditText) findViewById(R.id.otpEdt);

        statusTxt = (TextView) findViewById(R.id.statusTxt);

        otp_status = (ImageView) findViewById(R.id.otp_status);

        scanBtn = (Button) findViewById(R.id.scanBtn);
        cancelBtn = (Button)findViewById(R.id.cancelBtn);
        registerBtn = (Button)findViewById(R.id.registerBtn);

        final VarSession session = (VarSession)getApplicationContext();
        //Toast.makeText(getApplicationContext(), "private_key => "+session.getPrivateKey(), Toast.LENGTH_LONG).show();

        registerBtn.setEnabled(false);
        registerBtn.setClickable(false);
        registerBtn.setBackgroundColor(0xFFE0E0E0);

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
                registerTask.execute(session.getRegisterServerIp()+"eexam/tester/tester.register.php");
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp_status.setImageResource(R.drawable.error);
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
    }

    public void processFinish(String result){
        //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
        scanBtn.setEnabled(true);
        scanBtn.setClickable(true);
        scanBtn.setBackgroundColor(0xFFF3AA36);
        otp_status.setImageResource(R.drawable.error);

        Log.i("Register",result);
        if(result.equals("")){
            statusTxt.setText("Sorry!, Server Is Down, Please Contact Administrator.");
            Toast.makeText(getApplicationContext(),"Sorry!, Server Is Down, Please Contact Administrator.",Toast.LENGTH_SHORT).show();
        }
        if(result.toLowerCase().contains("Notice".toLowerCase())){
            statusTxt.setText("Server Error, Please Contact Administrator.");
            Toast.makeText(getApplicationContext(),"Server Error, Please Contact Administrator.",Toast.LENGTH_SHORT).show();
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
                otp_status.setImageResource(R.drawable.ban);
                statusTxt.setText("Incorrect OTP Or This Tester Id Had Registed Already");
                Toast.makeText(getApplicationContext(),"Incorrect OTP Or This Tester Id Had Registed Already",Toast.LENGTH_SHORT).show();
            }

            else if(status.equals("notHaveTest")){
                statusTxt.setText("You Don't Have A Test Now");
                Toast.makeText(getApplicationContext(),"You Don't Have A Test Now",Toast.LENGTH_SHORT).show();
            }

            else if(status.equals("overTime")){
                statusTxt.setText("You Register Overtime!!");
                Toast.makeText(getApplicationContext(),"You Register Overtime!!",Toast.LENGTH_SHORT).show();
            }

            else if(status.equals("Error MAC Address")){
                statusTxt.setText("You Don't Have Sit");
                Toast.makeText(getApplicationContext(),"You Don't Have Sit",Toast.LENGTH_SHORT).show();
            }
            else{
                statusTxt.setText("You Can't Register Your Device!");
                Toast.makeText(getApplicationContext(),"You Can't Register Your Device!",Toast.LENGTH_SHORT).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void scanBarcode(View view) {
        statusTxt.setText("");
        new IntentIntegrator(this).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,
                resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                if(result.getContents().length() == 6){
                    otp_status.setImageResource(R.drawable.ok);

                    scanBtn.setEnabled(false);
                    scanBtn.setClickable(false);
                    scanBtn.setBackgroundColor(0xFFE0E0E0);

                    registerBtn.setEnabled(true);
                    registerBtn.setClickable(true);
                    registerBtn.setBackgroundColor(0xFF336E7B);

                    otpEdt.setText(result.getContents());
                }else{
                    otp_status.setImageResource(R.drawable.error);
                }

                Toast.makeText(this, "Scanned: " + result.getContents(),
                        Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
