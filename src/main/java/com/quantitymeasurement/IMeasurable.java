package com.quantitymeasurement;

public interface IMeasurable {

	double getConversionFactor(); // relative to base unit

	default double convertToBaseUnit(double value) {
		return value * getConversionFactor();
	}

	default double convertFromBaseUnit(double baseValue) {
		return baseValue / getConversionFactor();
	}

	String getUnitName();
}