package com.quantitymeasurement.core;

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
		return value * conversionFactor;
	}

	@Override
	public double convertFromBaseUnit(double baseValue) {
		return baseValue / conversionFactor;
	}

	@Override
	public String getUnitName() {
		return this.name();
	}

	/*
	 * UC15: Returns the measurement type of this unit.
	 */
	@Override
	public String getMeasurementType() {
		return this.getClass().getSimpleName();
	}

	/*
	 * UC15: Returns the unit instance from the given unit name.
	 */
	@Override
	public IMeasurable getUnitInstance(String unitName) {
		return LengthUnit.valueOf(unitName);
	}
}