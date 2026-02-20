package com.quantitymeasurement;

/**
 * Main application class responsible for checking equality of feet values.
 */
public class QuantityMeasurementApp {

	public static void main(String[] args) {

		Feet feet1 = new Feet(1.0);
		Feet feet2 = new Feet(1.0);

		boolean result = feet1.equals(feet2);

		System.out.println("Input: 1.0 ft and 1.0 ft");
		System.out.println("Output: Equal (" + result + ")");
	}
}