package com.varvet.barcodereadersample;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by rb on 01.11.17.
 */

public class AnnounceTransactionTask extends AsyncTask<String, Void, Void> {

@Override
protected Void doInBackground(String... params) {

    try {
        URL url = new URL(params[0]);
        String tx= params[1];

        String postData =tx;
        android.util.Log.i("NEM", postData);
        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setDoOutput(true);
        conn.setChunkedStreamingMode(0);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        OutputStream stream = new java.io.BufferedOutputStream(conn.getOutputStream());
        OutputStreamWriter wr = new OutputStreamWriter(stream);
        wr.write(postData);
        wr.flush();

        android.util.Log.i("NEM", "Wrote postData");

        int status = conn.getResponseCode();

        java.io.InputStream in;
        if(status >= HttpURLConnection.HTTP_BAD_REQUEST)
            in = conn.getErrorStream();
        else
            in = conn.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line = null;

        // Read Server Response
        while ((line = reader.readLine()) != null) {
            // Append server response in string
            sb.append(line + "\n");
        }


        String text = sb.toString();
        reader.close();

        android.util.Log.i("NEM",text );
        conn.disconnect();
        android.util.Log.i("NEM", "Disconnected");


    } catch (MalformedURLException e) {
        e.printStackTrace();

    } catch (IOException e) {
        e.printStackTrace();

    }
    return null;
}







}
