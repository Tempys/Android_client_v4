package com.example.app;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app.entity.Sing_up_entity;

public class MainActivity extends Activity implements View.OnClickListener {

    Button  btnSingIn;
    Button btnregistration;
    EditText etPass;
    EditText etUsername;
    private String answer = "";
    final private String TAG = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);

        btnSingIn =  (Button) findViewById(R.id.btnSingIn);
        btnSingIn.setOnClickListener(this);
        btnregistration = (Button) findViewById(R.id.btnregistration);
        btnregistration.setOnClickListener(this);

        etPass = (EditText) findViewById(R.id.etPass);
        etUsername= (EditText) findViewById(R.id.etUserName);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnSingIn:
             //   Intent intent = new Intent(MainActivity.this,test.class);
             //   startActivity(intent);

                Sing_up_entity.instance.setLogin(etUsername.getText().toString());
                Sing_up_entity.instance.setPassword(etPass.getText().toString());
                Log.e("TAG",Sing_up_entity.instance.getLogin()+" "+Sing_up_entity.instance.getPassword());
                conn Test = new conn();
                Test.execute(ApiUrls.Sendingsignin);

                if(answer.equals("ok")) {
                    Intent intent = new Intent(MainActivity.this,SendPhotoTask.class);
                    startActivity(intent);
                    break;
                }
                 else Toast.makeText(MainActivity.this,"Please enter right login or password",Toast.LENGTH_LONG).show(); ;
                break;
            case R.id.btnregistration:
                Intent intent1 = new Intent(MainActivity.this,test.class);
                startActivity(intent1);
                break;
        }
    }


    private class conn extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            return Send_post.Post_sign_in(url[0],Sing_up_entity.instance.getLogin(),Sing_up_entity.instance.getPassword());
        }

        @Override
        protected void onPostExecute(String result) {
            answer = result;
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            Log.e("TAG", "YEAGGGG!!!!!!!!!!");
          //  tt.setText(result);
        }
    }

}
