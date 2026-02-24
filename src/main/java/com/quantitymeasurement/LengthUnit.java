package com.quantitymeasurement;

public enum LengthUnit {

	FEET(1.0), INCH(1.0 / 12.0), YARDS(3.0), CENTIMETERS(0.393701 / 12.0);

	private final double toFeetFactor;

	LengthUnit(double toFeetFactor) {
		this.toFeetFactor = toFeetFactor;
	}

	public double toFeet(double value) {
		return value * toFeetFactor;
	}

	public double fromFeet(double feetValue) {
		return feetValue / toFeetFactor;
	}
}