package th.ac.etesting.kmitl.it.etestinglogin;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by PaRaganDa on 16/2/2559.
 */
public class VarSession extends Application{

    public String password="", private_key;

    public String testerId="", testId="", otp="";

    public String getPassword(){
        return password;
    }

    public void setPassword(String _password){
        password = _password;
    }

    public String getPrivateKey(){
        return private_key;
    }

    public void setPrivateKey(String _private){
        private_key = _private;
    }


    public String getTesterId(){
        return testerId;
    }
    public void setTesterId(String _id){
        testerId = _id;
    }

    public String getTestId(){
        return testId;
    }
    public void setTestId(String _id){
        testId = _id;
    }

    public String getOtp(){
        return otp;
    }
    public void setOtp(String _otp){
        otp = _otp;
    }

}

