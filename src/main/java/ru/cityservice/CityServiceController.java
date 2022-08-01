package ru.cityservice;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityServiceController {
	private CityServiceModel model;

	@Autowired
	public CityServiceController(CityServiceModel model) {
		this.model = model;
	}

	@GetMapping("/GetCityData")
	public ArrayList<City> GetCityData(@RequestParam(value = "CityName", defaultValue = "") String cityName,
			@RequestParam(value = "NumberOfCitizens", defaultValue = "0") int numberOfCitizens,
			@RequestParam(value = "ComparisonType", defaultValue = "") ComparisonType compType) {

		// Prepare request
		CityRequest request = new CityRequest();
		if (cityName.isBlank() == false ) {
			ArrayList<String> cityNameList = new ArrayList<String>();
			cityNameList.add(cityName);
			request.setCityNameList(cityNameList);
		}

		if (compType != null) {
			request.setComparisonType(compType);
			request.setNumberOfCitizens(numberOfCitizens);
		}

		// Do request
		ArrayList<City> cityList = model.GetRecords(request);

		return cityList;
	}
}
