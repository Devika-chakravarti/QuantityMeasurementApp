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

	// ---------------- UC12: SUBTRACTION DEMOS ----------------

	public static <U extends IMeasurable> void demonstrateSubtraction(Quantity<U> q1, Quantity<U> q2) {
		System.out.println("Input: " + q1 + " subtract " + q2);
		System.out.println("Output: " + q1.subtract(q2));
		System.out.println();
	}

	public static <U extends IMeasurable> void demonstrateSubtractionWithTarget(Quantity<U> q1, Quantity<U> q2,
			U targetUnit) {
		System.out.println("Input: subtract(" + q1 + ", " + q2 + ", " + targetUnit.getUnitName() + ")");
		System.out.println("Output: " + q1.subtract(q2, targetUnit));
		System.out.println();
	}

	// ---------------- UC12: DIVISION DEMO ----------------

	public static <U extends IMeasurable> void demonstrateDivision(Quantity<U> q1, Quantity<U> q2) {
		System.out.println("Input: " + q1 + " divide " + q2);
		System.out.println("Output: " + q1.divide(q2));
		System.out.println();
	}

	// Length Demo (UC1–UC8 behavior preserved through UC10 generic design)
	public static void demonstrateLengthFeatures() {

		Quantity<LengthUnit> oneFoot = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> twelveInch = new Quantity<>(12.0, LengthUnit.INCH);

		demonstrateEquality(oneFoot, twelveInch);
		demonstrateConversion(oneFoot, LengthUnit.INCH);
		demonstrateAddition(oneFoot, twelveInch);
		demonstrateAdditionWithTarget(oneFoot, twelveInch, LengthUnit.YARDS);

		// UC12
		demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(6.0, LengthUnit.INCH));
		demonstrateSubtractionWithTarget(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(6.0, LengthUnit.INCH),
				LengthUnit.INCH);
		demonstrateDivision(new Quantity<>(24.0, LengthUnit.INCH), new Quantity<>(2.0, LengthUnit.FEET));
	}

	// Weight Demo (UC9 behavior preserved through UC10 generic design)
	public static void demonstrateWeightFeatures() {

		Quantity<WeightUnit> kg = new Quantity<>(10.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> gram = new Quantity<>(5000.0, WeightUnit.GRAM);

		demonstrateEquality(new Quantity<>(1.0, WeightUnit.KILOGRAM), new Quantity<>(1000.0, WeightUnit.GRAM));
		demonstrateConversion(new Quantity<>(1.0, WeightUnit.KILOGRAM), WeightUnit.GRAM);
		demonstrateAddition(new Quantity<>(1.0, WeightUnit.KILOGRAM), new Quantity<>(1000.0, WeightUnit.GRAM));
		demonstrateAdditionWithTarget(new Quantity<>(1.0, WeightUnit.KILOGRAM), new Quantity<>(1000.0, WeightUnit.GRAM),
				WeightUnit.GRAM);

		// UC12
		demonstrateSubtraction(kg, gram); // 10kg - 5000g = 5kg
		demonstrateSubtractionWithTarget(kg, gram, WeightUnit.GRAM); // 5000g
		demonstrateDivision(new Quantity<>(10.0, WeightUnit.KILOGRAM), new Quantity<>(5.0, WeightUnit.KILOGRAM)); // 2.0
	}

	// Volume Demo (UC11)
	public static void demonstrateVolumeFeatures() {

		Quantity<VolumeUnit> litre = new Quantity<>(5.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> milli = new Quantity<>(500.0, VolumeUnit.MILLILITRE);

		demonstrateEquality(new Quantity<>(1.0, VolumeUnit.LITRE), new Quantity<>(1000.0, VolumeUnit.MILLILITRE));
		demonstrateConversion(new Quantity<>(1.0, VolumeUnit.GALLON), VolumeUnit.LITRE);
		demonstrateAddition(new Quantity<>(1.0, VolumeUnit.LITRE), new Quantity<>(1000.0, VolumeUnit.MILLILITRE));

		// UC12
		demonstrateSubtraction(litre, milli); // 5L - 500mL = 4.5L
		demonstrateSubtractionWithTarget(new Quantity<>(5.0, VolumeUnit.LITRE), new Quantity<>(2.0, VolumeUnit.LITRE),
				VolumeUnit.MILLILITRE); // 3000mL
		demonstrateDivision(new Quantity<>(5.0, VolumeUnit.LITRE), new Quantity<>(10.0, VolumeUnit.LITRE)); // 0.5
	}

	public static void main(String[] args) {
		demonstrateLengthFeatures();
		demonstrateWeightFeatures();
		demonstrateVolumeFeatures();
	}
}