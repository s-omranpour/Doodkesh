package res;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by soroushomranpour on 9/15/2017 AD.
 */

public class RequestSend {
    private static final String USER_AGENT = "Mozilla/5.0";
    public static Map<String,Integer> map;
    public static String weatherCon;

    public static String sendGet(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //String temp = Jsoup.parse(response.toString()).text();
        return response.toString();
    }

    public static String tehranWeather(){
        String url = "http://api.openweathermap.org/data/2.5/weather?q=Tehran&appid=4383652ad0f6f9316e20342b1431b97c&units=metric";
        try {
            return sendGet(url);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getAirConditionByCity(){
        String url = "https://api.waqi.info/feed/tehran/?token=804c6c0fb86615d907039c05ec0b23569dc32a09";
        try {
            return sendGet(url);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getAirConditionStation(double lat1,double lon1,double lat2,double lon2){
        String url = "https://api.waqi.info/map/bounds/?latlng="+lat1+","+lon1+","+lat2+","+lon2+"&token=804c6c0fb86615d907039c05ec0b23569dc32a09";
        try {
            System.out.println(url);
            return sendGet(url);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String TehranUVI(){
        String url = "http://api.openweathermap.org/data/2.5/uvi?appid=4383652ad0f6f9316e20342b1431b97c&lat=35.7033683&lon=51.3515813";
        try {
            return sendGet(url);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String,Integer> getTehranAirQuality(){
        String result = "{\"status\":\"ok\",\"data\":{\"idx\":7720,\"aqi\":1,\"time\":{\"v\":1491955200,\"s\":\"2017-04-12 00:00:00\",\"tz\":\"+04:30\"},\"city\":{\"name\":\"Tehran Setad\",\"url\":\"http:\\/\\/aqicn.org\\/city\\/iran\\/tehran\\/setad\\/\",\"geo\":[\"35.72708\",\"51.4312\"]},\"attributions\":[{\"name\":\"Tehran AQCC - (Air Quality Control Company) - \\u0633\\u0627\\u0645\\u0627\\u0646\\u0647 \\u06a9\\u0646\\u062a\\u0631\\u0644 \\u06a9\\u06cc\\u0641\\u06cc\\u062a \\u0647\\u0648\\u0627\\u06cc \\u062a\\u0647\\u0631\\u0627\\u0646\",\"url\":\"http:\\/\\/air.tehran.ir\\/\"},{\"name\":\"World Air Quality Index Project\",\"url\":\"http:\\/\\/waqi.info\\/\"}],\"iaqi\":{\"pm25\":{\"v\":26},\"pm10\":{\"v\":50},\"o3\":{\"v\":1},\"no2\":{\"v\":35},\"co\":{\"v\":17},\"t\":{\"v\":20},\"p\":{\"v\":1019},\"h\":{\"v\":32}},\"dominentpol\":\"o3\"}}";
        String uv = TehranUVI();
        String weather = tehranWeather();
        try {
            JSONObject weatherObj = new JSONObject(weather);
            JSONObject object = new JSONObject(result);
            JSONObject object1 = new JSONObject(uv);
            JSONObject jsonObject = object.getJSONObject("data").getJSONObject("iaqi");
            Map<String,Integer> map = new HashMap<>();
            map.put("no2",jsonObject.getJSONObject("no2").getInt("v"));
            map.put("co",jsonObject.getJSONObject("co").getInt("v"));
            map.put("o3",jsonObject.getJSONObject("o3").getInt("v"));
            map.put("pm25",jsonObject.getJSONObject("pm25").getInt("v"));
            map.put("pm10",jsonObject.getJSONObject("pm10").getInt("v"));
            map.put("uv",new Double(object1.getDouble("value")).intValue());
            map.put("temp",new Double(weatherObj.getJSONObject("main").getDouble("temp")).intValue());
            map.put("temp_max",new Double(weatherObj.getJSONObject("main").getDouble("temp_max")).intValue());
            map.put("temp_min",new Double(weatherObj.getJSONObject("main").getDouble("temp_min")).intValue());
            map.put("wind",new Double(weatherObj.getJSONObject("wind").getDouble("speed")).intValue());
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                String key = entry.getKey().toString();
                Integer value = entry.getValue();
                System.out.println(key + " : " + value);
            }
            System.out.println(weatherObj.getJSONArray("weather").getJSONObject(0).getString("main"));
            return map;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static String weatherCondition(){
        String weather = tehranWeather();
        try {
            JSONObject object = new JSONObject(weather);
            return object.getJSONArray("weather").getJSONObject(0).getString("main");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static void getStats(){
        map = getTehranAirQuality();
        weatherCon = weatherCondition();
    }

}
