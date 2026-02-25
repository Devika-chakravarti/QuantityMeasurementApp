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

	// UC5: Conversion feature (updated)
	public static void demonstrateConversion() {

		QuantityLength feetToInch = new QuantityLength(1.0, LengthUnit.FEET).convertTo(LengthUnit.INCH);
		System.out.println("Conversion: 1.0 FEET to INCH");
		System.out.println("Output: " + feetToInch.getValue());
		System.out.println();

		QuantityLength inchToFeet = new QuantityLength(24.0, LengthUnit.INCH).convertTo(LengthUnit.FEET);
		System.out.println("Conversion: 24.0 INCH to FEET");
		System.out.println("Output: " + inchToFeet.getValue());
		System.out.println();

		QuantityLength yardToInch = new QuantityLength(1.0, LengthUnit.YARDS).convertTo(LengthUnit.INCH);
		System.out.println("Conversion: 1.0 YARDS to INCH");
		System.out.println("Output: " + yardToInch.getValue());
		System.out.println();
	}

	// UC6: Addition feature
	public static void demonstrateAddition() {

		QuantityLength oneFoot = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength twelveInch = new QuantityLength(12.0, LengthUnit.INCH);

		QuantityLength resultInFeet = oneFoot.add(twelveInch, LengthUnit.FEET);
		System.out.println("Addition: 1.0 FEET + 12.0 INCH in FEET");
		System.out.println("Output: " + resultInFeet.getValue());
		System.out.println();

		QuantityLength resultInInch = oneFoot.add(twelveInch, LengthUnit.INCH);
		System.out.println("Addition: 1.0 FEET + 12.0 INCH in INCH");
		System.out.println("Output: " + resultInInch.getValue());
		System.out.println();
	}

	public static void demonstrateTargetUnitAddition() {

		System.out.println("Input: add(Quantity(1.0, FEET), Quantity(12.0, INCH), FEET)");
		System.out.println("Output: " + new QuantityLength(1.0, LengthUnit.FEET)
				.add(new QuantityLength(12.0, LengthUnit.INCH), LengthUnit.FEET));

		System.out.println("Input: add(Quantity(1.0, FEET), Quantity(12.0, INCH), INCH)");
		System.out.println("Output: " + new QuantityLength(1.0, LengthUnit.FEET)
				.add(new QuantityLength(12.0, LengthUnit.INCH), LengthUnit.INCH));

		System.out.println("Input: add(Quantity(1.0, FEET), Quantity(12.0, INCH), YARDS)");
		System.out.println("Output: " + new QuantityLength(1.0, LengthUnit.FEET)
				.add(new QuantityLength(12.0, LengthUnit.INCH), LengthUnit.YARDS));
		System.out.println();
	}

	// UC9: Weight Equality & Conversion
	public static void demonstrateWeightFeatures() {

		QuantityWeight kg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
		QuantityWeight gram = new QuantityWeight(1000.0, WeightUnit.GRAM);

		System.out.println("Weight Equality:");
		System.out.println("1 KG equals 1000 G -> " + kg.equals(gram));
		System.out.println();

		System.out.println("Weight Conversion:");
		System.out.println("1 KG to Gram -> " + kg.convertTo(WeightUnit.GRAM));
		System.out.println(
				"2 Pound to Kilogram -> " + new QuantityWeight(2.0, WeightUnit.POUND).convertTo(WeightUnit.KILOGRAM));
		System.out.println();

		System.out.println("Weight Addition:");
		System.out.println("1 KG + 1000 G -> " + kg.add(gram));
		System.out.println("1 KG + 1000 G in Gram -> " + kg.add(gram, WeightUnit.GRAM));
		System.out.println();
	}

	public static void main(String[] args) {

		demonstrateFeetEquality();
		demonstrateInchEquality();
		demonstrateCrossUnitEquality();
		demonstrateAdditionalComparisons();
		demonstrateConversion();
		demonstrateAddition();
		demonstrateTargetUnitAddition();
		demonstrateWeightFeatures();
	}
}