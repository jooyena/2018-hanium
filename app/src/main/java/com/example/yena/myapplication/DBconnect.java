package com.example.yena.myapplication;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.HashMap;

//!!시간남을 때 코드 재사용, 예쁘게 고치기!!

public class DBconnect extends AsyncTask<String, Void, String> {

    String myJSON;

    private static final String TAG_RESULTS ="result";
    private static final String TAG_disease ="disease";
    private static final String TAG_information ="information";
    private static final String TAG_preventive="preventive";

    JSONArray jsonArray = null;

    TextView textView;

    HashMap<String, String> persons = new HashMap();

    //생성자
    DBconnect(TextView textView){

        this.textView=textView;

    }

    protected void showList() {

        try {

            JSONObject jsonObj = new JSONObject(myJSON);

            //!!tag 넘긴 array 값 별로 값을 가져온다
            jsonArray = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i <jsonArray.length(); i++) {

                JSONObject c = jsonArray.getJSONObject(i);

                //json 형태로 값을 가져온다.
                String disease = c.getString(TAG_disease);
                String preventive = c.getString(TAG_preventive);
                String information = c.getString(TAG_information);

                persons.put(TAG_disease, disease);
                persons.put(TAG_preventive, preventive);
                persons.put(TAG_information, information);

                textView.setText(persons.get(TAG_disease));


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(String... params) {

        String uri = params[0];

        BufferedReader bufferedReader = null;

        try {
            //주소 받아오기
            URL url = new URL(uri);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            InputStream inputStream =con.getInputStream();

            StringBuilder sb = new StringBuilder();

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String json;

            while ((json = bufferedReader.readLine()) != null) {
                sb.append(json + "\n");
            }

            bufferedReader.close();
            con.disconnect();

            return sb.toString().trim();

        } catch (Exception e) {

            return null;

        }
    }
    //위의 결과값을 가져오는 메소드
    @Override
    protected void onPostExecute(String result) {
        if(result==null){

        }
        else{
            myJSON = result;
            showList();

        }
    }


}
