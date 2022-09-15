package com.example.yena.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    public static String connection_URL ="http://222.105.179.156/disease_out.php";

    private String[] age_list = {"10대 미만","10대","20대","30대","40대","50대","60대","70대"};//old를 누를시에 나타날 리스트 목록
    private String[] biological_gender = {"woman","man"};
    private String[] location_list = {"서울경기","서해5도","강원영서","강원영동","충청북도","충청남도","경상북도","경상남도","울릉독도","전라북도","전라남도","제주"};
    private Map<String,String> parameters;


    private HashMap<String,String> map = new HashMap<String,String>();//정보를 넣을 hashmap
    public Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

//register을 보여준다
        setContentView(R.layout.register);


//register버튼에 대한 객체
        Button RegButton =(Button) findViewById(R.id.regbutton);
        final TextView text_age = (TextView) findViewById(R.id.age);
        final TextView gender = (TextView) findViewById(R.id.gender_text);
        final TextView location =(TextView)findViewById(R.id.location);

/*
        //초기화
        for(int i=1;i<=12;i++){


            MainActivity.location_map.put(location_list[i-1],"#content > table > tbody > tr:nth-child("+i+") > td:nth-child(2) > ul");
        }
*/

        //나이선택
        text_age.setOnClickListener(new View.OnClickListener(){


    //age 리스트 목록에 대한 구현
            @Override
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("나이를 선택해 주세요");

                builder.setItems(age_list, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //나이대와 성별 gender을 담을 map
                       map.put("age",age_list[i].toString());

                        text_age.setText(age_list[i]);
                        Toast.makeText(getApplicationContext(),age_list[i],Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


        //성별선택

        gender.setOnClickListener(new View.OnClickListener(){

            //gender 리스트 목록에 대한 구현
            @Override
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("성별을 선택해 주세요");

                builder.setItems(biological_gender, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //나이대와 성별 gender을 담을 map
                        map.put("gender",biological_gender[i].toString());

                        gender.setText(biological_gender[i]);
                        Toast.makeText(getApplicationContext(),biological_gender[i],Toast.LENGTH_SHORT).show();

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


        //지역선택
        location.setOnClickListener(new View.OnClickListener(){

            //age 리스트 목록에 대한 구현
            @Override
            public void onClick(View view){

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("지역를 선택해 주세요");

                builder.setItems(location_list, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //지역을 담은 맵
                        map.put("location",location_list[i].toString());
                        location.setText(location_list[i]);
                        Toast.makeText(getApplicationContext(),location_list[i],Toast.LENGTH_SHORT).show();

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });



        //버튼 클릭시 넘어가는 이벤트
        RegButton.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view){

                Intent intent = new Intent(getApplicationContext(),two_class.class);//two_class로 가는 거
                Intent intent2= new Intent(getApplicationContext(),wheather.class);//wheather로 가는 거

              try{

                    phpRequest php_request = new phpRequest(connection_URL);
                    String result= php_request.PhPtest(map.get("gender"),map.get("age"));



                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                //다음 화면에다가 age를 넘긴다..!
                intent.putExtra("age",map.get("age"));

                //다음 화면에다가 gender을 넘긴다..!
                intent.putExtra("gender",map.get("gender"));

                //다음 화면에다가 location을 넘긴다..!
                intent.putExtra("location",map.get("location"));
                intent2.putExtra("location",map.get("location"));


                startActivity(intent);//다음 지정한 intent 넘긴다
            }

        });

    }

}