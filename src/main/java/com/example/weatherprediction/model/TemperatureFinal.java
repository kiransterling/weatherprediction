package com.example.weatherprediction.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TemperatureFinal {
	
	String date;
	float day_min_temp;
	float day_max_temp;
	String prediction;
	

}
