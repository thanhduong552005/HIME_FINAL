package com.example.hime_droid.helpers;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Upload extends AsyncTask<File, Integer, String> {
    public String doInBackground(File... photos) {
//        String API_UPLOAD = "https://6820-27-78-200-229.ap.ngrok.io/v1/upload";
        String API_UPLOAD = new Constant().API_DOMAIN + "/v1/upload";
        File photo = photos[0];

        Log.d("photo path", photo.getAbsolutePath());
        Log.d("photo name", photo.getName());

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image", photo.getName(),
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File(photo.getAbsolutePath())))
                .build();
        Request request = new Request.Builder()
                .url(API_UPLOAD)
                .method("POST", body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String resStr = response.body().string();
            Log.d("upload resp ne", resStr);
            Log.d("upload resp ne", String.valueOf(response.body()));
            return  resStr;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

//    public String doInBackground(File... photos) {
//        String API_UPLOAD = "https://6820-27-78-200-229.ap.ngrok.io/v1/upload";
//
//        File photo = photos[0];
//        Log.d("image path", photo.getAbsolutePath());
//        URL url = null;
//        try {
//            url = new URL(API_UPLOAD);
//            HttpURLConnection connection = null;
//            DataOutputStream outputStream = null;
//            String lineEnd = "\r\n";
//            String twoHyphens = "--";
//            String boundary = "*****";
//            Log.d("debug 1", "fuck");
//            int bytesRead, bytesAvailable, bufferSize;
//            byte[] buffer;
//            int maxBufferSize = 1 * 1024 * 1024;
//
//            FileInputStream fileInputStream = null;
//            fileInputStream = new FileInputStream(photo);
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.setDoOutput(true);
//            connection.setUseCaches(false);
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Connection", "Keep-Alive");
//            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
//            Log.d("debug 2", "fuck");
//
//            outputStream = new DataOutputStream(connection.getOutputStream());
//            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
//            outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + photo.getAbsolutePath() + "\"" + lineEnd);
//            outputStream.writeBytes(lineEnd);
//            Log.d("debug 3", "fuck");
//
//            bytesAvailable = fileInputStream.available();
//            bufferSize = Math.min(bytesAvailable, maxBufferSize);
//            buffer = new byte[bufferSize];
//            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//            Log.d("debug 4", "fuck");
//
//            while (bytesRead > 0) {
//                outputStream.write(buffer, 0, bufferSize);
//                bytesAvailable = fileInputStream.available();
//                bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//            }
//
//            outputStream.writeBytes(lineEnd);
//            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
//
//            Log.d("debug 5.-1", "fuck");
//            int serverResponseCode = connection.getResponseCode();
//            Log.d("debug 5.0", "fuck");
//            outputStream.flush();
//            outputStream.close();
//
//            Log.d("debug 5.1", "fuck");
//
//            String serverResponseMessage = connection.getResponseMessage();
//
//            Log.d("response", String.valueOf(serverResponseCode));
//
//            Log.d("debug 6", "fuck");
//
//            fileInputStream.close();
//            Log.d("debug 7", "fuck");
//
//            switch (serverResponseCode) {
//                case 200:
//                    return serverResponseMessage;
//
//                default:
//                    Log.d("debug 9", "fuck");
//                    Log.d("status", serverResponseMessage);
//                    return "";
//            }
//        } catch (FileNotFoundException e) {
//            Log.d("debug 10", "fuck");
//            e.printStackTrace();
//        } catch (ProtocolException e) {
//            Log.d("debug 11", "fuck");
//            e.printStackTrace();
//        } catch (MalformedURLException e) {
//            Log.d("debug 12", "fuck");
//            e.printStackTrace();
//        } catch (IOException e) {
//            Log.d("debug 13", "fuck");
//            e.printStackTrace();
//        }
//
//        return "";
//    }
}
