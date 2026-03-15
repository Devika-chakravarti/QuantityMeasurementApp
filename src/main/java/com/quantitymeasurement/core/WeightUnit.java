package com.quantitymeasurement.core;

public enum WeightUnit implements IMeasurable {

	KILOGRAM(1.0), GRAM(0.001), POUND(0.453592);

	private final double conversionFactor;

	WeightUnit(double conversionFactor) {
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
		return WeightUnit.valueOf(unitName);
	}
}