package com.example.yena.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class wheather extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // 건들이지 말자
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wheather);
        // <--


        TextView textView_wheather = (TextView) findViewById(R.id.text_wheather);
        TextView textView_wheather2 = (TextView) findViewById(R.id.text_wheather2);
        TextView textView_fine =(TextView)findViewById(R.id.text_other);
        TextView textView_fine2 =(TextView)findViewById(R.id.text_other2);
        TextView textView_fine3 =(TextView)findViewById(R.id.text_other3);
        TextView textView_fine4 =(TextView)findViewById(R.id.text_other4);
        TextView textView_fine5 =(TextView)findViewById(R.id.text_other5);

        TextView textView_fine6 =(TextView)findViewById(R.id.text_other6);
        TextView textView_fine7 =(TextView)findViewById(R.id.text_other7);
        TextView textView_fine8 =(TextView)findViewById(R.id.text_other8);
        TextView textView_fine9 =(TextView)findViewById(R.id.text_other9);
        TextView textView_fine10 =(TextView)findViewById(R.id.text_other10);



        TextView textView_ex =(TextView)findViewById(R.id.text);




        //!! 코드 고칠 부분 !! 전라북도 오전 날씨로 고정해 놓기 일단은.....//

       new wheather.WeatherConnection(textView_wheather).execute("https://weather.naver.com/rgn/cityWetrMain.nhn","#content > table > tbody > tr:nth-child(10) > td:nth-child(2) > ul");

       //!!전라북도 오후 날씨//
        new wheather.WeatherConnection(textView_wheather2).execute("https://weather.naver.com/rgn/cityWetrMain.nhn","#content > table > tbody > tr:nth-child(10) > td.line > ul");


        //!!오늘 오후 !!
        new fine_dust(textView_fine).execute("https://weather.naver.com/air/airFcast.nhn", "#WTR_AIR_HDAY_FCAST > table > tbody > tr > td:nth-child(1) > div > ul > li:nth-child(1)");
        new fine_dust(textView_fine2).execute("https://weather.naver.com/air/airFcast.nhn", "#WTR_AIR_HDAY_FCAST > table > tbody > tr > td:nth-child(1) > div > ul > li:nth-child(2)");
        new fine_dust(textView_fine3).execute("https://weather.naver.com/air/airFcast.nhn", "#WTR_AIR_HDAY_FCAST > table > tbody > tr > td:nth-child(1) > div > ul > li:nth-child(3)");
        new fine_dust(textView_fine4).execute("https://weather.naver.com/air/airFcast.nhn", "#WTR_AIR_HDAY_FCAST > table > tbody > tr > td:nth-child(1) > div > ul > li:nth-child(4)");
        new fine_dust(textView_fine5).execute("https://weather.naver.com/air/airFcast.nhn", "#WTR_AIR_HDAY_FCAST > table > tbody > tr > td:nth-child(1) > div > ul > li:nth-child(5)");

        //!!내일 오전!!
        new fine_dust(textView_fine6).execute("https://weather.naver.com/air/airFcast.nhn", "#WTR_AIR_HDAY_FCAST > table > tbody > tr > td:nth-child(2) > div > ul > li:nth-child(1)");
        new fine_dust(textView_fine7).execute("https://weather.naver.com/air/airFcast.nhn", "#WTR_AIR_HDAY_FCAST > table > tbody > tr > td:nth-child(2) > div > ul > li:nth-child(2)");
        new fine_dust(textView_fine8).execute("https://weather.naver.com/air/airFcast.nhn", "#WTR_AIR_HDAY_FCAST > table > tbody > tr > td:nth-child(2) > div > ul > li:nth-child(3)");
        new fine_dust(textView_fine9).execute("https://weather.naver.com/air/airFcast.nhn", "#WTR_AIR_HDAY_FCAST > table > tbody > tr > td:nth-child(2) > div > ul > li:nth-child(4)");
        new fine_dust(textView_fine10).execute("https://weather.naver.com/air/airFcast.nhn", "#WTR_AIR_HDAY_FCAST > table > tbody > tr > td:nth-child(2) > div > ul > li:nth-child(5)");


    }


    //!!내부 클래스, 파싱작업!!

    // 네트워크 작업은 AsyncTask 를 사용해야 한다
    public static class WeatherConnection extends AsyncTask<String, String, String>
    {
        //몇 번째 지역인지 받아온다.
        int num;
        TextView textview;
        //순서대로 리스트에 값을 담는다
        ArrayList<String> list = new ArrayList<>();

        //생성자
        public WeatherConnection(TextView text) {
            this.textview = text;
            this.num = num;
        }

        // 백그라운드에서 작업하게 한다
        @Override
        protected String doInBackground(String... params) {

            String URL = params[0];
            String E1 = params[1];
            String result = "";

            // Jsoup을 이용한 날씨데이터 Pasing하기.
            try {

                Document document = Jsoup.connect(URL).get();//주소를 받아온다

                Elements elements = document.select(E1);//html내에서 테그를 결정


                // 띄어쓰기를 해준다. 출력해 주는것 from 영상
                for (Element element : elements) {

                    result = result + element.text() + "\n";

                    //순서대로 저장하기,숫자 붙임
                    list.add(element.text());
                }

                return list.get(num);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textview.setText(s);

        }
    }

    public static class fine_dust extends AsyncTask<String, String, String> {

        TextView textview;

        //생성자
        public fine_dust(TextView textView){

            this.textview=textView;
        }

        // 백그라운드에서 작업하게 한다
        @Override
        protected String doInBackground(String... params) {

            String URL = params[0];
            String E1 = params[1];
            String result = "";

            // Jsoup을 이용한 날씨데이터 Pasing하기.
            try {

                Document document = Jsoup.connect(URL).get();//주소를 받아온다

                Elements elements = document.select(E1);//html내에서 테그를 결정

                return elements.text();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textview.setText(s);

        }
    }
}
