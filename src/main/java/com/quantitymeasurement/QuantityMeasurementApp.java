package com.quantitymeasurement;

public class QuantityMeasurementApp {

	// UC1: Feet equality
	public static void demonstrateFeetEquality() {
		Feet feet1 = new Feet(1.0);
		Feet feet2 = new Feet(1.0);

		System.out.println("Input: 1.0 ft and 1.0 ft");
		System.out.println("Output: Equal (" + feet1.equals(feet2) + ")");
		System.out.println();
	}

	// UC2: Inches equality
	public static void demonstrateInchEquality() {
		Inches inch1 = new Inches(1.0);
		Inches inch2 = new Inches(1.0);

		System.out.println("Input: 1.0 inch and 1.0 inch");
		System.out.println("Output: Equal (" + inch1.equals(inch2) + ")");
		System.out.println();
	}

	// UC3: Feet and Inch comparison
	public static void demonstrateCrossUnitEquality() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

		System.out.println("Input: Quantity(1.0, FEET) and Quantity(12.0, INCH)");
		System.out.println("Output: Equal (" + q1.equals(q2) + ")");
		System.out.println();
	}

	// UC4: Additional comparisons
	public static void demonstrateAdditionalComparisons() {

		QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARDS);
		QuantityLength feet = new QuantityLength(3.0, LengthUnit.FEET);

		System.out.println("Input: Quantity(1.0, YARDS) and Quantity(3.0, FEET)");
		System.out.println("Output: Equal (" + yard.equals(feet) + ")");
		System.out.println();

		QuantityLength cm = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
		QuantityLength inchEquivalent = new QuantityLength(0.393701, LengthUnit.INCH);

		System.out.println("Input: Quantity(1.0, CENTIMETERS) and Quantity(0.393701, INCH)");
		System.out.println("Output: Equal (" + cm.equals(inchEquivalent) + ")");
		System.out.println();
	}

	// UC5: Conversion feature
	public static void demonstrateConversion() {

		double feetToInch = QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCH);
		System.out.println("Conversion: 1.0 FEET to INCH");
		System.out.println("Output: " + feetToInch);
		System.out.println();

		double inchToFeet = QuantityLength.convert(24.0, LengthUnit.INCH, LengthUnit.FEET);
		System.out.println("Conversion: 24.0 INCH to FEET");
		System.out.println("Output: " + inchToFeet);
		System.out.println();

		double yardToInch = QuantityLength.convert(1.0, LengthUnit.YARDS, LengthUnit.INCH);
		System.out.println("Conversion: 1.0 YARDS to INCH");
		System.out.println("Output: " + yardToInch);
		System.out.println();
	}

	public static void main(String[] args) {

		demonstrateFeetEquality();
		demonstrateInchEquality();
		demonstrateCrossUnitEquality();
		demonstrateAdditionalComparisons();
		demonstrateConversion();
	}
}