package com.quantitymeasurement;

/**
 * Enum representing supported length units. Conversion factor is relative to
 * base unit (Feet).
 */
public enum LengthUnit {

	FEET(1.0), INCH(1.0 / 12.0), YARDS(3.0), CENTIMETERS(0.393701 / 12.0);

	// Conversion factor to feet
	private final double toFeetFactor;

	LengthUnit(double toFeetFactor) {
		this.toFeetFactor = toFeetFactor;
	}

	// Converts given value to feet
	public double toFeet(double value) {
		return value * toFeetFactor;
	}
}