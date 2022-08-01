package ru.cityservice;

public enum ComparisonType {
	EQ("="), 
	LT("<"), 
	GT(">"), 
	LE("<="), 
	GE(">=");

	private String comparsion;

	ComparisonType(String comparsion) {
		this.comparsion = comparsion;
	}

	public String getComparsion() {
		return comparsion;
	}
}
