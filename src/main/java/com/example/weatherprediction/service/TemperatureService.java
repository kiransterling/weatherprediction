package com.example.weatherprediction.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.weatherprediction.model.Temperature;
import com.example.weatherprediction.model.TemperatureFinal;
import com.google.gson.Gson;

@Service
public class TemperatureService {

	JSONObject jsonObject;
	JSONArray jsonArray;
	
	private static final Logger logger = LoggerFactory.getLogger(TemperatureService.class);

	public String Getprediction(String result) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
       //Getting next 3 days 
		Date today = new Date();
		String tomorrow = formatter.format(new Date(today.getTime() + (1000 * 60 * 60 * 24)));
		String dayAfterTomorrow = formatter.format(new Date(today.getTime() + (2000 * 60 * 60 * 24)));
		String twoDaysAfterTomorrow = formatter.format(new Date(today.getTime() + (3000 * 60 * 60 * 24)));

		//Storing mins , maxs and weather for tomorrow in a list
		List<Float> tomorrow_mins = new ArrayList<Float>();
		List<Float> tomorrow_maxs = new ArrayList<Float>();
		List<String> tomorrow_weather = new ArrayList<String>();

		//Storing mins , maxs and weather for dayafter tomorrow in a list
		List<Float> dayAfter_tomorrow_mins = new ArrayList<Float>();
		List<Float> dayAfter_tomorrow_maxs = new ArrayList<Float>();
		List<String> dayafter_tomorrow_weather = new ArrayList<String>();

		//Storing mins , maxs and weather for 2 days tomorrow in a list
		List<Float> twodaysAfter_tomorrow_mins = new ArrayList<Float>();
		List<Float> twodaysAdayfter_tomorrow_maxs = new ArrayList<Float>();
		List<String> twodaysAftertomorrow_weather = new ArrayList<String>();

		
		//Final result will be stored here
		List<TemperatureFinal> finalResults = new ArrayList<TemperatureFinal>();

		jsonArray = new JSONObject(result).getJSONArray("list");
		Temperature temperature;

		for (int i = 0; i < jsonArray.length(); i++) {

			List<Temperature> temperatureTomorrowPerThreeHours = new ArrayList<Temperature>();
			List<Temperature> temperatureDayAfterTomorrowPerThreeHours = new ArrayList<Temperature>();
			List<Temperature> temperatureTwoDaysAfterTomorrowPerThreeHours = new ArrayList<Temperature>();

			//Capturing mins ,maxs ,datetime and weather data for tomorrow
			if (jsonArray.getJSONObject(i).get("dt_txt").toString().startsWith(tomorrow)) {
				temperature = new Temperature();

				temperature.setMin_temp(
						Float.parseFloat(jsonArray.getJSONObject(i).getJSONObject("main").get("temp_min").toString()));
				tomorrow_mins.add(temperature.getMin_temp());
				temperature.setMax_temp(
						Float.parseFloat(jsonArray.getJSONObject(i).getJSONObject("main").get("temp_max").toString()));
				tomorrow_maxs.add(temperature.getMax_temp());
				temperature.setDatetime(jsonArray.getJSONObject(i).get("dt_txt").toString());
				temperature.setWeather(
						jsonArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).get("main").toString());
				tomorrow_weather.add(temperature.getWeather());
				temperatureTomorrowPerThreeHours.add(temperature);
				logger.info(temperature.toString());

			}

			//Capturing mins ,maxs ,datetime and weather data  for day after tomorrow
			if (jsonArray.getJSONObject(i).get("dt_txt").toString().startsWith(dayAfterTomorrow)) {
				temperature = new Temperature();

				temperature.setMin_temp(
						Float.parseFloat(jsonArray.getJSONObject(i).getJSONObject("main").get("temp_min").toString()));
				dayAfter_tomorrow_mins.add(temperature.getMin_temp());
				temperature.setMax_temp(
						Float.parseFloat(jsonArray.getJSONObject(i).getJSONObject("main").get("temp_max").toString()));
				dayAfter_tomorrow_maxs.add(temperature.getMax_temp());
				temperature.setDatetime(jsonArray.getJSONObject(i).get("dt_txt").toString());
				temperature.setWeather(
						jsonArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).get("main").toString());
				dayafter_tomorrow_weather.add(temperature.getWeather());
				temperatureDayAfterTomorrowPerThreeHours.add(temperature);
				logger.info(temperature.toString());
			}

			//Capturing mins ,maxs ,datetime and weather data  for 2  days after tomorrow
			if (jsonArray.getJSONObject(i).get("dt_txt").toString().startsWith(twoDaysAfterTomorrow)) {
				temperature = new Temperature();

				temperature.setMin_temp(
						Float.parseFloat(jsonArray.getJSONObject(i).getJSONObject("main").get("temp_min").toString()));
				twodaysAfter_tomorrow_mins.add(temperature.getMin_temp());
				temperature.setMax_temp(
						Float.parseFloat(jsonArray.getJSONObject(i).getJSONObject("main").get("temp_max").toString()));
				twodaysAdayfter_tomorrow_maxs.add(temperature.getMax_temp());
				temperature.setDatetime(jsonArray.getJSONObject(i).get("dt_txt").toString());
				temperature.setWeather(
						jsonArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).get("main").toString());
				twodaysAftertomorrow_weather.add(temperature.getWeather());
				temperatureTwoDaysAfterTomorrowPerThreeHours.add(temperature);
                logger.info(temperature.toString());
			}
		}

		// Calculate the min and max temperature for every day and predict weather
        // This logic could be improved with loops 
		
		TemperatureFinal calculateResults = new TemperatureFinal();
        //Calculate mins out of all the mins in tomorow
		calculateResults.setDay_min_temp(Collections.min(tomorrow_mins));
		//Calculate mins out of all the maxs in tomorow
		calculateResults.setDay_max_temp(Collections.max(tomorrow_maxs));
		calculateResults.setDate(tomorrow);

		if (calculateResults.getDay_max_temp() >= 40)
			calculateResults.setPrediction("Take suns cream");
		else if (tomorrow_weather.contains("Rain"))
			calculateResults.setPrediction("Please take an umbrella");
		else
			calculateResults.setPrediction("Its a nice day");

		finalResults.add(calculateResults);

		/* ======================================================== */

		calculateResults = new TemperatureFinal();

		//Calculate mins out of all the mins in day after tomorrow
		calculateResults.setDay_min_temp(Collections.min(dayAfter_tomorrow_mins));
		//Calculate mins out of all the maxs in day after tomorrow
		calculateResults.setDay_max_temp(Collections.max(dayAfter_tomorrow_maxs));
		calculateResults.setDate(dayAfterTomorrow);

		if (calculateResults.getDay_max_temp() >= 40)
			calculateResults.setPrediction("Take suns cream");
		else if (dayafter_tomorrow_weather.contains("Rain"))
			calculateResults.setPrediction("Please take an umbrella");
		else
			calculateResults.setPrediction("Its a nice day");

		finalResults.add(calculateResults);
		
		/* ======================================================== */

		calculateResults = new TemperatureFinal();

		//Calculate mins out of all the mins in 2 days after tomorow
		calculateResults.setDay_min_temp(Collections.min(twodaysAfter_tomorrow_mins));
		
		//Calculate mins out of all the maxs in day after tomorrow
		calculateResults.setDay_max_temp(Collections.max(twodaysAdayfter_tomorrow_maxs));
		calculateResults.setDate(twoDaysAfterTomorrow);

		if (calculateResults.getDay_max_temp() >= 40)
			calculateResults.setPrediction("Take suns cream");
		else if (twodaysAftertomorrow_weather.contains("Rain"))
			calculateResults.setPrediction("Please take an umbrella");
		else
			calculateResults.setPrediction("Its a nice day");

		finalResults.add(calculateResults);
		
		logger.info(finalResults.toString());
		
		return new Gson().toJson(finalResults);
		
		

		

	}

}
