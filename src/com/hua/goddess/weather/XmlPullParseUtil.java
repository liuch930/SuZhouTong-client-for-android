package com.hua.goddess.weather;

import java.io.ByteArrayInputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.hua.goddess.vo.WeatherInfo;

public class XmlPullParseUtil {
	public static WeatherInfo parseWeatherInfo(String result) {
		WeatherInfo allWeather = null;
		try {
			// 获取XmlPullParser的实例
			XmlPullParser xmlPullParser = XmlPullParserFactory.newInstance()
					.newPullParser();
			// 设置输入流 xml文件
			xmlPullParser.setInput(new ByteArrayInputStream(result.getBytes()),
					"UTF-8");
			// 开始解析
			int eventType = xmlPullParser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				String nodeName = xmlPullParser.getName();
				switch (eventType) {

				case XmlPullParser.START_DOCUMENT: // 文档开始
					allWeather = new WeatherInfo();
					break;

				case XmlPullParser.START_TAG:// 开始节点
					if (nodeName.equals("city")) {
						allWeather.setCity(xmlPullParser.nextText());
					} else if (nodeName.equals("yujing")) {
						allWeather.setYujing(xmlPullParser.nextText());
					} else if (nodeName.equals("alarmtext")) {
						allWeather.setAlarmtext(xmlPullParser.nextText());
					} else if (nodeName.equals("warning")) {
						allWeather.setWarning(xmlPullParser.nextText());
					} else if (nodeName.equals("temp0")) {
						allWeather.setTemp0(xmlPullParser.nextText());
					} else if (nodeName.equals("temp1")) {
						allWeather.setTemp1(xmlPullParser.nextText());
					} else if (nodeName.equals("temp2")) {
						allWeather.setTemp2(xmlPullParser.nextText());
					} else if (nodeName.equals("temp3")) {
						allWeather.setTemp3(xmlPullParser.nextText());
					} else if (nodeName.equals("temp4")) {
						allWeather.setTemp4(xmlPullParser.nextText());
					} else if (nodeName.equals("temp5")) {
						allWeather.setTemp5(xmlPullParser.nextText());
					} else if (nodeName.equals("temp6")) {
						allWeather.setTemp6(xmlPullParser.nextText());
					} else if (nodeName.equals("weather0")) {
						allWeather.setWeather0(xmlPullParser.nextText());
					} else if (nodeName.equals("weather1")) {
						allWeather.setWeather1(xmlPullParser.nextText());
					} else if (nodeName.equals("weather2")) {
						allWeather.setWeather2(xmlPullParser.nextText());
					} else if (nodeName.equals("weather3")) {
						allWeather.setWeather3(xmlPullParser.nextText());
					} else if (nodeName.equals("weather4")) {
						allWeather.setWeather4(xmlPullParser.nextText());
					} else if (nodeName.equals("weather5")) {
						allWeather.setWeather5(xmlPullParser.nextText());
					} else if (nodeName.equals("weather6")) {
						allWeather.setWeather6(xmlPullParser.nextText());
					} else if (nodeName.equals("wind0")) {
						allWeather.setWind0(xmlPullParser.nextText());
					} else if (nodeName.equals("wind1")) {
						allWeather.setWind1(xmlPullParser.nextText());
					} else if (nodeName.equals("wind2")) {
						allWeather.setWind2(xmlPullParser.nextText());
					} else if (nodeName.equals("wind3")) {
						allWeather.setWind3(xmlPullParser.nextText());
					} else if (nodeName.equals("wind4")) {
						allWeather.setWind4(xmlPullParser.nextText());
					} else if (nodeName.equals("wind5")) {
						allWeather.setWind5(xmlPullParser.nextText());
					} else if (nodeName.equals("wind6")) {
						allWeather.setWind6(xmlPullParser.nextText());
					} else if (nodeName.equals("intime")) {
						allWeather.setIntime(xmlPullParser.nextText());
					} else if (nodeName.equals("tempNow")) {
						allWeather.setTempNow(xmlPullParser.nextText());
					} else if (nodeName.equals("shidu")) {
						allWeather.setShidu(xmlPullParser.nextText());
					} else if (nodeName.equals("winNow")) {
						allWeather.setWinNow(xmlPullParser.nextText());
					} else if (nodeName.equals("feelTemp")) {
						allWeather.setFeelTemp(xmlPullParser.nextText());
					} else if (nodeName.equals("shiduNow")) {
						allWeather.setShiduNow(xmlPullParser.nextText());
					} else if (nodeName.equals("todaySun")) {
						allWeather.setTodaySun(xmlPullParser.nextText());
					} else if (nodeName.equals("tomorrowSun")) {
						allWeather.setTomorrowSun(xmlPullParser.nextText());
					} else if (nodeName.equals("AQIData")) {
						allWeather.setAQIData(xmlPullParser.nextText());
					} else if (nodeName.equals("PM2Dot5Data")) {
						allWeather.setPM2Dot5Data(xmlPullParser.nextText());
					} else if (nodeName.equals("PM10Data")) {
						allWeather.setPM10Data(xmlPullParser.nextText());
					}
					break;

				case XmlPullParser.END_TAG:// 结束节点
					break;

				default:
					break;
				}
				eventType = xmlPullParser.next();
			}
		} catch (Exception e) {
			//解析出错会返回空
			e.printStackTrace();
		}
		return allWeather;
	}
}
