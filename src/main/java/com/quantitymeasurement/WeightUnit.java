package com.quantitymeasurement;

/**
 * Enum representing weight units. Base unit: KILOGRAM
 */
public enum WeightUnit {

	KILOGRAM(1.0), GRAM(0.001), POUND(0.453592);

	private final double conversionFactorToKg;

	WeightUnit(double conversionFactorToKg) {
		this.conversionFactorToKg = conversionFactorToKg;
	}

	public double getConversionFactor() {
		return conversionFactorToKg;
	}

	// Convert given value to base unit (Kilogram)
	public double convertToBaseUnit(double value) {
		return value * conversionFactorToKg;
	}

	// Convert from base unit (Kilogram) to this unit
	public double convertFromBaseUnit(double baseValue) {
		return baseValue / conversionFactorToKg;
	}
}