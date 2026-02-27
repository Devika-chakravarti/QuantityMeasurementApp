package com.quantitymeasurement;

public enum LengthUnit implements IMeasurable {

	FEET(1.0), INCH(1.0 / 12.0), YARDS(3.0), CENTIMETERS(0.393701 / 12.0);

	private final double conversionFactor;

	LengthUnit(double conversionFactor) {
		this.conversionFactor = conversionFactor;
	}

	@Override
	public double getConversionFactor() {
		return conversionFactor;
	}

	@Override
	public double convertToBaseUnit(double value) {
		return value * conversionFactor; // base = FEET
	}

	@Override
	public double convertFromBaseUnit(double baseValue) {
		return baseValue / conversionFactor; // from FEET to this unit
	}

	@Override
	public String getUnitName() {
		return this.name();
	}
}