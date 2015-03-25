package com.hua.goddess.weather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hua.goddess.R;
import com.hua.goddess.fragment.WeatherFragment;
import com.hua.goddess.vo.WeatherInfo;

public class SecondWeatherFragment extends Fragment {
	private TextView weekTv4, weekTv5, weekTv6;
	private ImageView weather_imgIv4, weather_imgIv5, weather_imgIv6;
	private TextView temperatureTv4, temperatureTv5, temperatureTv6;
	private TextView climateTv4, climateTv5, climateTv6;
	private TextView windTv4, windTv5, windTv6;
	private WeatherFragment wFragment;
	private WeatherInfo weatherinfo;
	
	public SecondWeatherFragment(WeatherFragment wFragment,WeatherInfo weatherinfo) {
		this.wFragment = wFragment;
		this.weatherinfo = weatherinfo;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.biz_plugin_weather_item,
				container, false);
		View view1 = view.findViewById(R.id.subitem1);
		View view2 = view.findViewById(R.id.subitem2);
		View view3 = view.findViewById(R.id.subitem3);

		weekTv4 = (TextView) view1.findViewById(R.id.week);
		weekTv5 = (TextView) view2.findViewById(R.id.week);
		weekTv6 = (TextView) view3.findViewById(R.id.week);

		weather_imgIv4 = (ImageView) view1.findViewById(R.id.weather_img);
		weather_imgIv5 = (ImageView) view2.findViewById(R.id.weather_img);
		weather_imgIv6 = (ImageView) view3.findViewById(R.id.weather_img);
		temperatureTv4 = (TextView) view1.findViewById(R.id.temperature);
		temperatureTv5 = (TextView) view2.findViewById(R.id.temperature);
		temperatureTv6 = (TextView) view3.findViewById(R.id.temperature);

		climateTv4 = (TextView) view1.findViewById(R.id.climate);
		climateTv5 = (TextView) view2.findViewById(R.id.climate);
		climateTv6 = (TextView) view3.findViewById(R.id.climate);

		windTv4 = (TextView) view1.findViewById(R.id.wind);
		windTv5 = (TextView) view2.findViewById(R.id.wind);
		windTv6 = (TextView) view3.findViewById(R.id.wind);
		updateWeather();
		return view;
	}

	public void updateWeather() {
		weekTv4.setText(TimeUtil.getWeek(4, TimeUtil.XING_QI));
		weekTv5.setText(TimeUtil.getWeek(5, TimeUtil.XING_QI));
		weekTv6.setText(TimeUtil.getWeek(6, TimeUtil.XING_QI));
		if (weatherinfo != null) {
			weather_imgIv4.setImageResource(wFragment
					.getWeatherIcon(weatherinfo.getWeather4()));
			weather_imgIv5.setImageResource(wFragment
					.getWeatherIcon(weatherinfo.getWeather5()));
			weather_imgIv6.setImageResource(wFragment
					.getWeatherIcon(weatherinfo.getWeather6()));

			climateTv4.setText(weatherinfo.getWeather4());
			climateTv5.setText(weatherinfo.getWeather5());
			climateTv6.setText(weatherinfo.getWeather6());

			temperatureTv4.setText(weatherinfo.getTemp4());
			temperatureTv5.setText(weatherinfo.getTemp5());
			temperatureTv6.setText(weatherinfo.getTemp6());

			windTv4.setText(weatherinfo.getWind5());
			windTv5.setText(weatherinfo.getWind6());
			windTv6.setText(weatherinfo.getWind6());
		} else {
			weather_imgIv4.setImageResource(R.drawable.na);
			weather_imgIv5.setImageResource(R.drawable.na);
			weather_imgIv6.setImageResource(R.drawable.na);

			climateTv4.setText("N/A");
			climateTv5.setText("N/A");
			climateTv6.setText("N/A");

			temperatureTv4.setText("N/A");
			temperatureTv5.setText("N/A");
			temperatureTv6.setText("N/A");

			windTv4.setText("N/A");
			windTv5.setText("N/A");
			windTv6.setText("N/A");
		}
	}

}
