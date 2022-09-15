package com.example.yena.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;


public class two_class extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_page);

        TextView gender = (TextView) findViewById(R.id.gender_text);
        TextView wheather = (TextView) findViewById(R.id.wheather);
        TextView disease =(TextView)findViewById(R.id.simple_disease);

        //넘어온 값
        gender.setText(getIntent().getStringExtra(("age").toString()));
        gender.append(" ");

        //시간 가져오기
        long now = System.currentTimeMillis();

        //여성일 경우
        if (getIntent().getStringExtra("gender").toString().equals("woman"))
            gender.append("여성이 주의해야 할 질병은");//getIntent()로 값을 받아와서 호

        //남성일 경우
        if (getIntent().getStringExtra("gender").toString().equals("man"))
            gender.append("남성이 주의해야 할 질병은");//getIntent()로 값을 받아와서 호롤ㄹ롤롤

        //간단한 날씨 가져오기! 오전날씨
        new wheather.WeatherConnection(wheather).execute("https://weather.naver.com/rgn/cityWetrMain.nhn", "#content > table > tbody > tr:nth-child(10) > td:nth-child(2) > ul");


        //질병 표시
        new DBconnect(disease).execute("http://222.105.179.156/disease_out.php");


        Button button_d = (Button) findViewById(R.id.detali_disease);//버튼 선언

        button_d.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), disease.class);//intent 선언으로 화면 전환
                startActivity(intent);

            }

        });

        Button button_r = (Button) findViewById(R.id.detali_wheather);//버튼 선언

        //클릭시 다른 인텐드로 넘김
        button_r.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), wheather.class);
                startActivity(intent);

            }
        });


    }


    //종료 이벤트 처리

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alert_ex = new AlertDialog.Builder(this);
        alert_ex.setMessage("정말로 종료하시겠습니까?");

        alert_ex.setPositiveButton("취소", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert_ex.setNegativeButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                AppKill();

            }
        }).setTitle("DR.W");

        AlertDialog alert = alert_ex.create();
        alert.show();

    }
    //앱종료 함수
    public void AppKill()
    {
        ActivityCompat.finishAffinity(this);
        //프로세스 종료
        System.runFinalization();
        System.exit(0);
    }


}
