package com.quantitymeasurement;

/**
 * Standalone LengthUnit enum responsible for all unit conversions. Base unit:
 * FEET
 */
public enum LengthUnit {

	FEET(1.0), INCH(1.0 / 12.0), YARDS(3.0), CENTIMETERS(1.0 / 30.48);

	// Conversion factor relative to base unit (feet)
	private final double conversionFactor;

	LengthUnit(double conversionFactor) {
		this.conversionFactor = conversionFactor;
	}

	// Returns conversion factor to base unit
	public double getConversionFactor() {
		return conversionFactor;
	}

	// Converts value in this unit to base unit (feet)
	public double convertToBaseUnit(double value) {
		return value * conversionFactor;
	}

	// Converts value from base unit (feet) to this unit
	public double convertFromBaseUnit(double baseValue) {
		return baseValue / conversionFactor;
	}
}