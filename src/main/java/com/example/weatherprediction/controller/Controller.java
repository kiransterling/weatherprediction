package com.example.weatherprediction.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import com.example.weatherprediction.service.TemperatureService;

@RestController
@RequestMapping("/api")
public class Controller {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	TemperatureService temperatureService;

	@Value("${api.url}")
	private String apiUrl;

	@Value("${api.key}")
	private String apiKey;
	
	private static final Logger logger = LoggerFactory.getLogger(Controller.class);

	@GetMapping("/{city}")
	public ResponseEntity<String> getTemperatures(@PathVariable String city) {

		String result;
		String resultFinal;

		try {
			result= restTemplate.getForObject(apiUrl + "?q=" + city + "&appid=" + apiKey + "&units=metric",String.class);
			resultFinal=temperatureService.Getprediction(result);
			return new ResponseEntity<String>(resultFinal, new HttpHeaders(),
					HttpStatus.OK);
		} catch (HttpStatusCodeException e) {
			
			logger.error(e.getResponseBodyAsString());
			return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
					.body(e.getResponseBodyAsString());
		}

	}

}
