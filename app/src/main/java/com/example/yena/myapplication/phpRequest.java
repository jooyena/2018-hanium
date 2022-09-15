package com.example.yena.myapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

public class phpRequest {

    private URL url;

    public phpRequest(String url) throws MalformedURLException { this.url = new URL(url); }

    private String readStream(InputStream in) throws IOException {

        //쓰레드 안전..? 이엿나 하여간 그거
        StringBuilder jsonHtml = new StringBuilder();

        //reader객체 생성
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String line = null;

        while((line = reader.readLine()) != null)
            jsonHtml.append(line);

        reader.close();
        return jsonHtml.toString();
    }

    public String PhPtest(final String data1, final String data2){

        try {

            //php로 "age"와 "gender"이름으로 보낸다
            String postData = "age" + data1 + "&" + "gender=" + data2;
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //post 타입으로 보냄
            conn.setRequestMethod("POST");

            //연결 기본설정
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //<-
            //outputstream으로 연결된 url에 UTF-8형으로 쓴다
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));

            //버퍼지우기
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        }

        //예외 예외 홀롤로로ㅗ로롤ㄹ롤
        catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }
}
