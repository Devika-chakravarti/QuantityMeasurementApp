package com.quantitymeasurement;

public class QuantityMeasurementApp {

	public static <U extends IMeasurable> void demonstrateEquality(Quantity<U> q1, Quantity<U> q2) {
		System.out.println("Input: " + q1 + " and " + q2);
		System.out.println("Output: Equal (" + q1.equals(q2) + ")");
		System.out.println();
	}

	public static <U extends IMeasurable> void demonstrateConversion(Quantity<U> quantity, U targetUnit) {
		System.out.println("Input: " + quantity + " convertTo(" + targetUnit.getUnitName() + ")");
		System.out.println("Output: " + quantity.convertTo(targetUnit));
		System.out.println();
	}

	public static <U extends IMeasurable> void demonstrateAddition(Quantity<U> q1, Quantity<U> q2) {
		System.out.println("Input: " + q1 + " add " + q2);
		System.out.println("Output: " + q1.add(q2));
		System.out.println();
	}

	public static <U extends IMeasurable> void demonstrateAdditionWithTarget(Quantity<U> q1, Quantity<U> q2,
			U targetUnit) {
		System.out.println("Input: add(" + q1 + ", " + q2 + ", " + targetUnit.getUnitName() + ")");
		System.out.println("Output: " + q1.add(q2, targetUnit));
		System.out.println();
	}

	// Length Demo (UC1–UC8 behavior preserved through UC10 generic design)
	public static void demonstrateLengthFeatures() {

		Quantity<LengthUnit> feet1 = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> feet2 = new Quantity<>(1.0, LengthUnit.FEET);

		Quantity<LengthUnit> inch1 = new Quantity<>(1.0, LengthUnit.INCH);
		Quantity<LengthUnit> inch2 = new Quantity<>(1.0, LengthUnit.INCH);

		Quantity<LengthUnit> oneFoot = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> twelveInch = new Quantity<>(12.0, LengthUnit.INCH);

		Quantity<LengthUnit> oneYard = new Quantity<>(1.0, LengthUnit.YARDS);
		Quantity<LengthUnit> threeFeet = new Quantity<>(3.0, LengthUnit.FEET);

		Quantity<LengthUnit> oneCm = new Quantity<>(1.0, LengthUnit.CENTIMETERS);
		Quantity<LengthUnit> inchEquivalent = new Quantity<>(0.393701, LengthUnit.INCH);

		// UC1
		demonstrateEquality(feet1, feet2);

		// UC2
		demonstrateEquality(inch1, inch2);

		// UC3
		demonstrateEquality(oneFoot, twelveInch);

		// UC4
		demonstrateEquality(oneYard, threeFeet);
		demonstrateEquality(oneCm, inchEquivalent);

		// UC5
		demonstrateConversion(new Quantity<>(1.0, LengthUnit.FEET), LengthUnit.INCH);
		demonstrateConversion(new Quantity<>(24.0, LengthUnit.INCH), LengthUnit.FEET);
		demonstrateConversion(new Quantity<>(1.0, LengthUnit.YARDS), LengthUnit.INCH);

		// UC6
		demonstrateAddition(oneFoot, twelveInch);

		// UC7 (explicit target unit)
		demonstrateAdditionWithTarget(oneFoot, twelveInch, LengthUnit.FEET);
		demonstrateAdditionWithTarget(oneFoot, twelveInch, LengthUnit.INCH);
		demonstrateAdditionWithTarget(oneFoot, twelveInch, LengthUnit.YARDS);
	}

	// Weight Demo (UC9 behavior preserved through UC10 generic design)
	public static void demonstrateWeightFeatures() {

		Quantity<WeightUnit> kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> gram = new Quantity<>(1000.0, WeightUnit.GRAM);

		demonstrateEquality(kg, gram);

		demonstrateConversion(kg, WeightUnit.GRAM);
		demonstrateConversion(new Quantity<>(2.0, WeightUnit.POUND), WeightUnit.KILOGRAM);

		demonstrateAddition(kg, gram);

		demonstrateAdditionWithTarget(kg, gram, WeightUnit.GRAM);
		demonstrateAdditionWithTarget(new Quantity<>(1.0, WeightUnit.POUND), new Quantity<>(453.592, WeightUnit.GRAM),
				WeightUnit.POUND);
	}

	public static void main(String[] args) {
		demonstrateLengthFeatures();
		demonstrateWeightFeatures();
	}
}