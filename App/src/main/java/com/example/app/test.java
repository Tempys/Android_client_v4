package com.example.app;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.example.app.entity.Signup;
import com.google.gson.Gson;


public class test extends Activity implements View.OnClickListener {

    TextView tt;
    Button register;
    conn Task;
    final private String TAG = "login";
    EditText reg_name;
    EditText reg_surname;
    EditText reg_login;
    EditText reg_password;
    Signup r;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        register = (Button) findViewById(R.id.btnRegister);
        reg_name= (EditText) findViewById(R.id.reg_name);
        reg_surname= (EditText) findViewById(R.id.reg_surname);
        reg_login= (EditText) findViewById(R.id.reg_login);
        reg_password= (EditText) findViewById(R.id.reg_password);


        register.setOnClickListener(this);

        // check if you are connected or not
     //   if(isConnected()){
       //     conn.setBackgroundColor(0xFF00CC00);
         //   conn.setText("You are conncted");
       // }
       // else{
         //   conn.setText("You are NOT conncted");
       // }


 }

    @Override
    public void onClick(final View v) {
        switch(v.getId()){
            case R.id.btnRegister:
                 r = new Signup();
                r.name =     reg_name.getText().toString();
                r.surname =  reg_surname.getText().toString();
                r.password=  reg_password.getText().toString();
                r.login =    reg_login.getText().toString();
                Task = new conn();
                Task.execute(ApiUrls.RegisterUrl);
                break;
    }
}

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }



    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }


    private class conn extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

         return Send_post.Post_registration(url[0],r);
        }

        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            Log.e("TAG", "YEAGGGG!!!!!!!!!!");
            tt.setText(result);
        }
        }

    }







