package com.example.yena.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class another extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.another_info);

        final String [] kind_of_age = {"10대미만","10대","20대","30대","40대","50대","60대","70대"};

        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, kind_of_age);//
        //listadapter로 실제로 리스트에 들어갈 내용을 담는다

        ListView listView=(ListView) findViewById(R.id.another_list);//지정했던 list의 아이디를 가져온다. 객체를 만들어준다

        listView.setAdapter(adapter);//어뎁터로 리스트 안에 넣는다
        listView.setOnItemClickListener(

                new AdapterView.OnItemClickListener(){

                    TextView detail = (TextView)findViewById(R.id.info);

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id){

                        if(parent.getItemAtPosition(i).toString().equals("10대미만"))
                            //parent.getItemAtPosition(i).toString()으로 객체값 넘길 수 있을 것 같다
                            detail.setText("another_info about 10대미만");

                        if(parent.getItemAtPosition(i).toString().equals("10대"))
                            detail.setText("another_info about 10대.");

                        if(parent.getItemAtPosition(i).toString().equals("20대"))
                            detail.setText("another_info about 20대.");
                        if(parent.getItemAtPosition(i).toString().equals("30대"))
                            detail.setText("another_info about 30대.");
                        if(parent.getItemAtPosition(i).toString().equals("40대"))
                            detail.setText("another_info about 40대.");
                        if(parent.getItemAtPosition(i).toString().equals("50대"))
                            detail.setText("another_info about 50대.");
                        if(parent.getItemAtPosition(i).toString().equals("60대"))
                            detail.setText("another_info about 50대.");
                        if(parent.getItemAtPosition(i).toString().equals("70대"))
                            detail.setText("another_info about 50대.");



                        String item =String.valueOf(parent.getItemAtPosition(i));//클릭한 아이템에 대한 객체를 만들어준다


                    }
                }
        );

    }
}
