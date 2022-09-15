package com.example.yena.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class disease extends Activity {

    String myJSON;

    private static final String TAG_RESULTS ="result";
    private static final String TAG_disease ="disease";
    private static final String TAG_information ="information";
    private static final String TAG_preventive="preventive";
    private static final String TAG_symptom="symptom";


    //태그별로 저장할 map
    static public HashMap<String, String> persons = new HashMap();

    JSONArray jsonArray = null;

    ArrayList<HashMap<String, String>> personList;

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        setContentView(R.layout.detail_disease_info);
        list=(ListView) findViewById(R.id.detail);

        personList = new ArrayList<HashMap<String, String>>();

        TextView another;

        getData("http://222.105.179.156/disease_out.php");

        another = (TextView)findViewById(R.id.another);//텍스트 객체 생성

        //!!페이지 이동시 넘어가는 페이지 지정!!
        another.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(view.isClickable()){
                    Intent intent = new Intent(getApplicationContext(),another.class);
                    startActivity(intent);
                }
            }
        });//<-!!

    }

    //DB연결
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
                String symptom = c.getString(TAG_symptom);

                persons.put(TAG_disease, disease);
                persons.put(TAG_preventive, preventive);
                persons.put(TAG_information, information);
                persons.put(TAG_symptom,symptom);


            }

            //!!리스트 표시!!
            String [] disease = {"설명","증상","예방방법"};

            //!!listadapter로 실제로 리스트에 들어갈 내용을 담는다!!
            ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,disease);


            //지정했던 list의 아이디를 가져온다. 객체를 만들어준다
            ListView listView=(ListView) findViewById(R.id.detail);


             listView.setAdapter(adapter);//어뎁터로 리스트 안에 넣는다


        //!!클릭했을때 이벤트 발생!!
        listView.setOnItemClickListener(

                new AdapterView.OnItemClickListener(){

                    TextView detail = (TextView)findViewById(R.id.detail_disease);

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id){

                        if(parent.getItemAtPosition(i).toString().equals("설명"))
                            //parent.getItemAtPosition(i).toString()으로 객체값 넘길 수 있을 것 같다
                            detail.setText(persons.get(TAG_information).toString());

                        if(parent.getItemAtPosition(i).toString().equals("증상"))
                            detail.setText(persons.get(TAG_symptom).toString());

                        if(parent.getItemAtPosition(i).toString().equals("예방방법"))
                            detail.setText(persons.get(TAG_preventive).toString());

                        //클릭한 아이템에 대한 객체를 만들어준다
                        String item =String.valueOf(parent.getItemAtPosition(i));

                    }
                }
        );


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }//<-//

    //!! 데이터를 가져온다!! AsyncTask


    public void getData(final String url) {


        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;

                try {

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
            GetDataJSON g = new GetDataJSON();
            g.execute(url);

    }

}

