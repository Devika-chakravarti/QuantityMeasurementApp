package com.quantitymeasurement;

/**
 * Main application class responsible for checking equality of Feet and Inches
 * values.
 */
public class QuantityMeasurementApp {

	public static void main(String[] args) {

		// UC1: Feet equality check
		Feet feet1 = new Feet(1.0);
		Feet feet2 = new Feet(1.0);

		boolean result = feet1.equals(feet2);

		System.out.println("Input: 1.0 ft and 1.0 ft");
		System.out.println("Output: Equal (" + result + ")");

		// UC2: Inches equality check (New feature added)
		Inches inch1 = new Inches(1.0);
		Inches inch2 = new Inches(1.0);

		boolean inchResult = inch1.equals(inch2);

		System.out.println("Input: 1.0 inch and 1.0 inch");
		System.out.println("Output: Equal (" + inchResult + ")");

		// UC3: Generic Quantity Length
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

		boolean quantityResult = q1.equals(q2);

		System.out.println("Input: Quantity(1.0, FEET) and Quantity(12.0, INCH)");
		System.out.println("Output: Equal (" + quantityResult + ")");
	}
}