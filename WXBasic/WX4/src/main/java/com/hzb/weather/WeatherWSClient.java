package com.hzb.weather;
 
import java.util.List;

import com.hzb.weather.ArrayOfString;
import com.hzb.weather.WeatherWS;
import com.hzb.weather.WeatherWSSoap;
 
public class WeatherWSClient {
 
   public static void main(String[] args) {
        //创建一个WeatherWS工厂
        WeatherWS factory = new WeatherWS();
        //根据工厂创建一个WeatherWSSoap对象
        WeatherWSSoap weatherWSSoap = factory.getWeatherWSSoap();
        //调用WebService提供的getWeather方法获取南宁市的天气预报情况
        ArrayOfString weatherInfo = weatherWSSoap.getWeather("广州", null);
        List<String> lstWeatherInfo = weatherInfo.getString();
        //遍历天气预报信息
        for (String string : lstWeatherInfo) {
            System.out.println(string);
            System.out.println("------------------------");
        }
    }
}
