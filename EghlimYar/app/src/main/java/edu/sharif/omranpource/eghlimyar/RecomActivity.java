package edu.sharif.omranpource.eghlimyar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import res.RequestSend;

public class RecomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recom);

        TextView recomms = (TextView) findViewById(R.id.recom_desc);
        recomms.setText(getSuggestion(RequestSend.map,RequestSend.weatherCon));
    }


    public String getSuggestion(Map<String, Integer> arr, String weather) {
        String result="";
        Random random = new Random();
        if (weather.equals("Clear")){
            result = "در هوای آفتابی:"+ "\n";
            for (String thewether: getResources().getStringArray(R.array.solution_for_sunny))
                result+=thewether + "\n";
            result += "\n";
        }
        else if (weather.equals("Snowy")){
            result = "در هوای برفی:"+ "\n";
            for (String thewether: getResources().getStringArray(R.array.solution_for_snowy))
                result+=thewether + "\n";
            result += "\n";
        }
        else if (weather.equals("Rainy")){
            result = "در هوای بارانی:"+ "\n";
            for (String thewether: getResources().getStringArray(R.array.solution_for_rainy))
                result+=thewether + "\n";
            result += "\n";
        }


        for(Map.Entry<String,Integer> entry : arr.entrySet()) {
            String type = entry.getKey();
            double value = entry.getValue();
            if (type.equals("co")) {
                String[] sa = getResources().getStringArray(R.array.related_to_co);
                int r = random.nextInt(sa.length);
                if (value > 9.5) result += "CO :\n" +sa[r] + "\n\n";
            }
            if (type.equals("no2")) {
                String[] sa = getResources().getStringArray(R.array.related_to_no2);
                int r = random.nextInt(sa.length);
                if (value > 0.101) result += "NO2:\n" +sa[r] + "\n\n";
            }
            if (type.equals("pm10")) {
                String[] sa = getResources().getStringArray(R.array.related_to_pm10);
                int r = random.nextInt(sa.length);
                if (value > 155) result += "PM10:\n" + sa[r] + "\n\n";
            }
            if (type.equals("o3")) {
                String[] sa = getResources().getStringArray(R.array.related_to_o3);
                int r = random.nextInt(sa.length);
                if (value > 125) result += "O3:\n"+ sa[r] + "\n\n";
            }
            if (type.equals("uv")) {
                String[] sa = getResources().getStringArray(R.array.related_to_uv);
                String[] pa = getResources().getStringArray(R.array.solution_for_uv);
                int r = random.nextInt(sa.length);
                if (value > 9) result += "UVIndex:\n"+sa[r] + "\n" + pa[r] + "\n\n";
            }

            if (type.equals("pm25")) {
                String[] sa = getResources().getStringArray(R.array.related_to_pm10);
                int r = random.nextInt(sa.length);
                if (value > 35.1) result +="PM2.5:\n" + sa[r] + "\n\n";
            }
//
//            if (type.equals("temp")) {
//                String[] sa = getResources().getStringArray(R.array.related_to_temp);
//                int r = random.nextInt(sa.length);
//                if (value > 10 && value < 50) result += " ";
//            }
//            if (type.equals("wind")) {
//                String[] sa = getResources().getStringArray(R.array.related_to_wind);
//                int r = random.nextInt(sa.length);
//                if (value > 50 && value <= 100) result += " ";
//            }
        }
        return result;
    }
    public static int dangerOf(String type, double value) { //return 0(good) to 6(dangerous). if not recognized, returns -1
        if (type.equals("co")) {
            if (value < 2.2) return 0;
            else if (value < 9.2) return 1;
            else if (value < 12.4) return 2;
            else if (value < 15.2) return 3;
            else if (value < 30.4) return 4;
            else if (value < 40.5) return 5;
            else return 6;
        }
        else if (type.equals("no2")) {
            if (value < 0.054) return 0;
            else if (value < 0.1) return 1;
            else if (value < 0.36) return 2;
            else if (value < 0.65) return 3;
            else if (value < 1.24) return 4;
            else if (value < 1.65) return 5;
            else return 6;
        }
        else if (type.equals("pm10")) {
            if (value < 52) return 0;
            else if (value < 152) return 1;
            else if (value < 254) return 2;
            else if (value < 354) return 3;
            else if (value < 425) return 4;
            else if (value < 504) return 5;
            else return 6;
        }
        else if (type.equals("pm25")) {
            if (value < 15.4) return 0;
            else if (value < 35) return 1;
            else if (value < 65.4) return 2;
            else if (value < 150.4) return 3;
            else if (value < 250.4) return 4;
            else if (value < 350.4) return 5;
            else return 6;
        }
        else if (type.equals("o3")) {
            if (value < 0.164) return 1;
            else if (value < 0.204) return 2;
            else if (value < 0.404) return 3;
            else if (value < 0.505) return 5;
            else return 6;
        }
        else if (type.equals("uv")) {
            if (value < 2) return 0;
            else if (value < 3) return 1;
            else if (value < 5) return 2;
            else if (value < 7) return 3;
            else if (value < 9) return 4;
            else if (value <= 10) return 5;
            else return 6;
        }
        else if (type.equals("temp")) {
            if (value < -10) return 0;
            else if (value < 0) return 1;
            else if (value < 10) return 2;
            else if (value < 20) return 3;
            else if (value < 30) return 4;
            else if (value < 40) return 5;
            else return 6;
        }
        return -1;
    }

}
