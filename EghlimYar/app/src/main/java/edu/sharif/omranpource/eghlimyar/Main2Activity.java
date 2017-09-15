package edu.sharif.omranpource.eghlimyar;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Map;

import res.RequestSend;

import static res.RequestSend.getTehranAirQuality;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Map<String,Integer> map = RequestSend.map;
        if (map != null && RequestSend.weatherCon != null){

            //weather image
            ImageView weatherImg = (ImageView) findViewById(R.id.weather_img);
            TextView desc = (TextView) findViewById(R.id.weather_desc);
            switch (RequestSend.weatherCon){
                case "Clear":
                    weatherImg.setImageResource(R.drawable.contrast);
                    desc.setText("هوای صاف");
                    break;
                case "Rainy":
                    weatherImg.setImageResource(R.drawable.rain);
                    desc.setText("هوای بارانی");
                    break;
                case "Cloudy":
                    desc.setText("هوای ابری");
                    weatherImg.setImageResource(R.drawable.clouds);
                    break;
                case "Snowy":
                    desc.setText("هوای برفی");
                    weatherImg.setImageResource(R.drawable.snowflake);
                    break;
            }
            desc.append("\n"+"دمای فعلی"+"    :    "+map.get("temp"));
            desc.append("\n"+"حداکثر دما"+"    :    "+map.get("temp_max"));
            desc.append("\n"+"حداقل دما"+"    :    "+map.get("temp_min"));
            desc.append("\n"+"سرعت باد"+"    :    "+map.get("wind"));


            //bar statistics
            BarChart barChart = (BarChart) findViewById(R.id.barchart);

            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(new Float(map.get("pm10")),0));
            entries.add(new BarEntry(new Float(map.get("pm25")),1));
            entries.add(new BarEntry(new Float(map.get("co")),2));
            entries.add(new BarEntry(new Float(map.get("o3")),3));
            entries.add(new BarEntry(new Float(map.get("no2")),4));
            entries.add(new BarEntry(new Float(map.get("uv")),5));

            BarDataSet bardataset = new BarDataSet(entries, "Cells");
            ArrayList<String> labels = new ArrayList<>();
            labels.add("pm10");
            labels.add("pm25");
            labels.add("co");
            labels.add("o3");
            labels.add("no2");
            labels.add("uv");

            BarData data = new BarData(labels,bardataset);
            barChart.setData(data); // set the data and list of labels into chart

            barChart.setDescription("نمودار وضعیت آلاینده ها");  // set the description
            bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
            barChart.animateY(5000);

            Button recomBtn = (Button) findViewById(R.id.analysis);
            recomBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(),RecomActivity.class));
                }
            });
        }
        else {
            TextView textView = (TextView) findViewById(R.id.errortxt);
            textView.setText("مشکل اینترنت");

        }
    }
}

class Req extends AsyncTask{
    @Override
    protected Object doInBackground(Object[] objects) {
        RequestSend.getStats();
        return RequestSend.map;
    }
}
