package ru.cityservice;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;

@Component
public class CityServiceModel {
	private Connection con;
	private Statement stmt;

	@PostConstruct
	public void CityServiceModelInit() {
		try {
			this.con = DriverManager.getConnection("jdbc:hsqldb:file:/src/main/database/CityDB", "sa", "");
			this.stmt = this.con.createStatement();
			DatabaseMetaData dbm = con.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "CITYDATA", null);

			if (tables.next() == false) {
				stmt.executeUpdate(
						"CREATE TABLE CityData ( CityName VARCHAR(50) NOT NULL, Citizens INT, PRIMARY KEY (CityName));");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public int AddRecords(ArrayList<City> cityList, List<String> messages) {
		int result = 0;
		String message = null;
		ArrayList<String> cityNameList = new ArrayList<>();

		// Checks
		// 1. City has name
		Iterator<City> cityListIterator  = cityList.iterator();
		while (cityListIterator.hasNext()) {
			City cityElement = cityListIterator.next();
			if (cityElement.getCityName() == null || cityElement.getCityName().isBlank() == true
					|| cityElement.getCityName().isEmpty() == true) {
				message = "Element " + cityList.indexOf(cityElement) + " hasn't CityName";
				messages.add(message);
				cityListIterator.remove();
				continue;
			}
			cityNameList.add(cityElement.getCityName());
		}

		if (cityList.isEmpty() == true) {
			return result;
		}

		// 2. City doesn't exist in DB
		CityRequest cityRequest = new CityRequest();

		cityRequest.setCityNameList(cityNameList);
		ArrayList<City> cityExistList = this.GetRecords(cityRequest);
		if (cityExistList.isEmpty() == false) {
			for (City cityExistElement : cityExistList) {
				message = "City " + cityExistElement.getCityName() + " already exists";
				messages.add(message);
			}
			cityList.removeAll(cityExistList);
		}

		if (cityList.isEmpty() == true) {
			return result;
		}
		
		
		// get SqlQuery
		String sqlQuery = "INSERT INTO CityData(CityName, Citizens) VALUES ";
		String sqlValues = new String();
		for (City cityElement : cityList) {
			if (sqlValues.isEmpty() == false) {
				sqlValues = sqlValues + ",";
			}

			sqlValues = sqlValues + "('" + cityElement.getCityName() + "'," + cityElement.getNumberOfCitizens() + ")";
		}

		sqlQuery = sqlQuery + sqlValues;		
		
		// execute Query
		try {
			result = this.stmt.executeUpdate(sqlQuery);
			this.con.commit();
			message = result + "records inserted";
			messages.add(message);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return result;

	}

	public ArrayList<City> GetRecords(CityRequest cityRequest) {
		ArrayList<City> cityList = new ArrayList<>();
		ResultSet resultSet = null;

		// Get sqlQuery
		String sqlQuery = "SELECT CityName, Citizens FROM CityData ";

		// Add condition to sqlQuery
		if (cityRequest.getCityNameList() != null && cityRequest.getCityNameList().isEmpty() == false) {
			sqlQuery = sqlQuery + " WHERE CityName IN (";

			String sqlValues = new String();

			for (String cityNameElement : cityRequest.getCityNameList()) {
				if (sqlValues.isEmpty() == false) {
					sqlValues = sqlValues + ",";
				}
				sqlValues = sqlValues + "'" + cityNameElement + "'";
			}
			sqlQuery = sqlQuery + sqlValues + ")";
		} else if (cityRequest.getComparisonType() != null) {
			sqlQuery = sqlQuery + " WHERE Citizens " + cityRequest.getComparisonType().getComparsion() + " "
					+ cityRequest.getNumberOfCitizens();

		}

		// execute query
		try {
			resultSet = this.stmt.executeQuery(sqlQuery);

			while (resultSet.next()) {
				City city = new City();
				city.setCityName(resultSet.getString("CityName"));
				city.setNumberOfCitizens(resultSet.getInt("Citizens"));
				;
				cityList.add(city);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return cityList;
	}

}
