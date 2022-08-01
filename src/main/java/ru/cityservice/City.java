package ru.cityservice;

import java.util.Objects;

public class City {
	private String cityName;
	private int numberOfCitizens;
	
	public City() {
	}
	
	@Override 
	public String toString() {
		
		return this.cityName + "" + this.numberOfCitizens;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		City other = (City) obj;
		return Objects.equals(cityName, other.cityName);
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getNumberOfCitizens() {
		return numberOfCitizens;
	}

	public void setNumberOfCitizens(int numberOfCitizens) {
		this.numberOfCitizens = numberOfCitizens;
	}
	

	

}
