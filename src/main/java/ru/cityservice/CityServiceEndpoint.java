package ru.cityservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import ru.cityservice.addcitydata.*;

@Endpoint
public class CityServiceEndpoint {

	static final String namespaceUri = "http://www.cityservice.ru/addcitydata";
	private CityServiceModel model;

	@Autowired
	public CityServiceEndpoint(CityServiceModel model) {
		this.model = model;
	}

	@PayloadRoot( namespace = namespaceUri, localPart = "AddCityDataRequest")
	@ResponsePayload
	public AddCityDataResponse addCity(@RequestPayload AddCityDataRequest request) {
		
		AddCityDataResponse response = new AddCityDataResponse();
		
		// Prepare city to addition
		List<ru.cityservice.addcitydata.City> reqCityList = request.getCity();
		ArrayList<City> cityList = new ArrayList<City>();
		
		for (ru.cityservice.addcitydata.City reqCityElement : reqCityList ) {
			City city = new City();
			city.setCityName(reqCityElement.getCityName());
			city.setNumberOfCitizens(reqCityElement.getNumberOfCitizens());
			cityList.add(city);
		}
		
		// Add city
		int addedRecords = model.AddRecords(cityList, response.getMessage());
		
		if ( addedRecords > 0) {
			response.setSuccess(true);
		}
		else {
			response.setSuccess(false);
		}
		
		return response;
	}
}