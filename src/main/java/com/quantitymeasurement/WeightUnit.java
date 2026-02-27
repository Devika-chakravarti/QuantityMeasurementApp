package com.quantitymeasurement;

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
		// Convert to KILOGRAM (base unit)
		return value * conversionFactor;
	}

	@Override
	public double convertFromBaseUnit(double baseValue) {
		// Convert from KILOGRAM to current unit
		return baseValue / conversionFactor;
	}

	@Override
	public String getUnitName() {
		return this.name();
	}
}