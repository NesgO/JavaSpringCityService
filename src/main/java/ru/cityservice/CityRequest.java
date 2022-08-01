package ru.cityservice;

import java.util.ArrayList;

public class CityRequest {
	private ArrayList<String> cityNameList;
	private ComparisonType comparisonType;
	private int numberOfCitizens;

	public CityRequest() {
	}

	public ArrayList<String> getCityNameList() {
		return cityNameList;
	}

	public void setCityNameList(ArrayList<String> cityNameList) {
		this.cityNameList = cityNameList;
	}

	public ComparisonType getComparisonType() {
		return comparisonType;
	}

	public void setComparisonType(ComparisonType comparisonType) {
		this.comparisonType = comparisonType;
	}

	public int getNumberOfCitizens() {
		return numberOfCitizens;
	}

	public void setNumberOfCitizens(int numberOfCitizens) {
		this.numberOfCitizens = numberOfCitizens;
	}

}
