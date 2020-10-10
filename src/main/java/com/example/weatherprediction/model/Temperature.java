package com.example.weatherprediction.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Temperature {
	
	String datetime;
	float min_temp;
	float max_temp;
	String weather;
	

}
