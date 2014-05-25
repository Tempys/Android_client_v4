package com.example.app;


    import java.io.BufferedReader;
    import java.io.ByteArrayOutputStream;
    import java.io.FileNotFoundException;
    import java.io.IOException;
    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    // import kg.ut.amanjet.Helpers.UserStateData;
   // import kg.ut.amanjet.Models.UserPhotoModel;

    import org.apache.http.HttpResponse;
    import org.apache.http.NameValuePair;
    import org.apache.http.client.HttpClient;
    import org.apache.http.client.methods.HttpPost;
    import org.apache.http.entity.ByteArrayEntity;
    import org.apache.http.entity.StringEntity;
    import org.apache.http.entity.mime.HttpMultipartMode;
    import org.apache.http.entity.mime.MultipartEntity;
    import org.apache.http.entity.mime.content.ByteArrayBody;
    import org.apache.http.entity.mime.content.StringBody;
    import org.apache.http.impl.client.DefaultHttpClient;
    import org.apache.http.message.BasicNameValuePair;

    import android.app.Activity;
    import android.content.Intent;
    import android.database.Cursor;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.net.Uri;
    import android.os.AsyncTask;
    import android.os.Bundle;
    import android.provider.MediaStore;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.example.app.entity.Sing_up_entity;
    import com.google.gson.Gson;

public class SendPhotoTask extends Activity implements View.OnClickListener  {

         conn Task;
       private   byte[] imageInByte;
       private String answer;
       Button send_f;
       TextView textView;

        // this is the action code we use in our intent,
        // this way we know we're looking at the response from our own action
        private static final int SELECT_PICTURE = 1;

    private    String selectedImagePath ;
        private Bitmap bitmap;

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sendphoto);

          send_f =  (Button) findViewById(R.id.send_f);
          send_f.setOnClickListener(this);
          textView = (TextView) findViewById(R.id.textView);
        }

        public void onClick(View v) {
                            // in onCreate or any event where your want the user to
                            // select a file
            switch(v.getId()) {
                case R.id.send_f:
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,
                            "Select Picture"), SELECT_PICTURE);
                    Log.e("TAG", "Start sending image");
                    //  Log.e("TAG", "lenght of image"+ selectedImagePath.length );

                    break;
            }
                                  }

        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (resultCode == RESULT_OK) {
                if (requestCode == SELECT_PICTURE) {
                    Uri selectedImageUri = data.getData();
                    selectedImagePath = getPath(selectedImageUri);
                    if (selectedImagePath.length()>0){
                        bitmap = BitmapFactory.decodeFile(selectedImagePath);

                        Bitmap bmpCompressed = Bitmap.createScaledBitmap(bitmap, 640, 480, true);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();

                        bmpCompressed.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                        imageInByte = bos.toByteArray();
                    }
                }

                Task = new conn();
                Task.execute(ApiUrls.SendimageUrl+"/owner/"+ Sing_up_entity.instance.getLogin());
            }

        }

        /**
         * helper to retrieve the path of an image URI
         */
        public String getPath(Uri uri) {
            // just some safety built in
            if( uri == null ) {
                // TODO perform some logging or show user feedback
                return null;
            }
            // try to retrieve the image from the media store first
            // this will only work for images selected from gallery
            String[] projection = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            if( cursor != null ){
                int column_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            }
            // this is our fallback here
            return uri.getPath();
        }



private class conn extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... url) {
      //  Intent i = new Intent(
      //  Intent.ACTION_PICK,
      //  android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
      //  startActivityForResult(i, RESULT_LOAD_IMAGE);
        return Send_post.Post_image(url[0],imageInByte);
    }

    @Override
    protected void onPostExecute(String result) {

        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        Log.e("TAG", "YEAGGGG!!!!!!!!!!");
        textView.setText(result);
       // tt.setText(result);
    }
}
}

