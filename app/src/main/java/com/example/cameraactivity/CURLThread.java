package com.example.cameraactivity;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class CURLThread extends Thread{
    JSONObject jsonObject;
    String data;
    CURLThread (String data) {
        this.data = data;
    }

    public void run() {
        try {
            Log.d("sended", "sended");
            URL url = new URL("https://4wlcb6ginx.apigw.ntruss.com/custom/v1/19083/27e189c52f57addff4d0a53dea856628eea8826e4b7a2f222f436f0a14625497/document/receipt");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("X-OCR-SECRET", "RWljdWlYRm5jTHdoZ1pvV0paaGpsQUtubFZoWVhkSnc=");

            JSONObject json = new JSONObject();
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());
            JSONObject image = new JSONObject();
            image.put("format", "jpg");
            //image.put("url", "https://kr.object.ncloudstorage.com/ocr-ci-test/sample/1.jpg"); // image should be public, otherwise, should use data
            // FileInputStream inputStream = new FileInputStream("YOUR_IMAGE_FILE");
            // byte[] buffer = new byte[inputStream.available()];
            // inputStream.read(buffer);
            // inputStream.close();
            image.put("data", data);
            image.put("name", "demo");
            JSONArray images = new JSONArray();
            images.put(image);
            json.put("images", images);
            String postParams = json.toString();

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            Log.d("response", String.valueOf(response));

            JsonParsing jsonParsing = new JsonParsing(new JSONObject(String.valueOf(response)));
            jsonParsing.parseToString();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
