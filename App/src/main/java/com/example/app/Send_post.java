package com.example.app;

import android.util.Log;

import com.example.app.entity.Signup;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by angel on 17.05.2014.
 */
public class Send_post {


    public static String Post_image(String url,byte[] imageInByte)
    {
        InputStream in = null;

        HttpClient client = new DefaultHttpClient();
        // List<NameValuePair> paramList = new ArrayList<NameValuePair>();

        Map paramList = new HashMap();
        String result = "";
        try{
            //  paramList.add(new BasicNameValuePair("provider", "custom"));

            HttpPost post = new HttpPost(url);

            //convert parameters into JSON object


            Log.e("TAG", "" + imageInByte.length);

            MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE); //MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

            multipartEntity.addPart("Description", new StringBody("name233"));
            multipartEntity.addPart("Data",  new ByteArrayBody(imageInByte, "temp.jpg"));

            post.setEntity(multipartEntity);
            // post.setEntity(new UrlEncodedFormEntity(paramList));
            //0==   post.setEntity(new StringEntity(json));
            // post.setHeader("Accept", "application/json");
            // post.setHeader("Content-type", "application/json");

            HttpResponse resp = client.execute(post);
            in = resp.getEntity().getContent();
            if (in != null)
                result = convertInputStreamToString(in);
            else
                result = "Dont work";
        } catch (Exception e){Log.e("TAG", "ERORRRRRRRRR!!!!!!!!!!"+e.getLocalizedMessage());}

        return result;
    }

    public static String Post_registration(String url,Signup r)
    {
        InputStream in = null;
        HttpClient client = new DefaultHttpClient();
        // List<NameValuePair> paramList = new ArrayList<NameValuePair>();

        Map paramList = new HashMap();
        String result = "";
        try {


            //  paramList.add(new BasicNameValuePair("provider", "custom"));

            HttpPost post = new HttpPost(url);



            //convert parameters into JSON object
            Gson gson = new Gson();
            String json = gson.toJson(r);
            Log.e("TAG", json);

            // post.setEntity(new UrlEncodedFormEntity(paramList));
            post.setEntity(new StringEntity(json));
            // post.setHeader("Accept", "application/json");
            // post.setHeader("Content-type", "application/json");

            HttpResponse resp = client.execute(post);
            in = resp.getEntity().getContent();
            if (in != null)
                result = convertInputStreamToString(in);
            else
                result = "Dont work";
        } catch (Exception e){Log.e("TAG", "ERORRRRRRRRR!!!!!!!!!!"+e.getLocalizedMessage());}

        return result;
    }

    public static String Post_sign_in(String url,String login,String password)
    {
        InputStream in = null;
        HttpClient client = new DefaultHttpClient();
        // List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        Map<String,String> map = new HashMap<String, String>();
        map.put("login",login);
        map.put("password",password);

        String result = "";
        try {
            //  paramList.add(new BasicNameValuePair("provider", "custom"));
            HttpPost post = new HttpPost(url);
            //convert parameters into JSON object
            Gson gson = new Gson();
            String json = gson.toJson(map);
            Log.e("TAG", json);

            // post.setEntity(new UrlEncodedFormEntity(paramList));
            post.setEntity(new StringEntity(json));
            // post.setHeader("Accept", "application/json");
            // post.setHeader("Content-type", "application/json");

            HttpResponse resp = client.execute(post);
            in = resp.getEntity().getContent();
            if (in != null)
                result = convertInputStreamToString(in);
            else
                result = "Dont work";
        } catch (Exception e){Log.e("TAG", "ERORRRRRRRRR!!!!!!!!!!"+e.getLocalizedMessage());}

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }



}
