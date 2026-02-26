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

	public static void demonstrateLengthFeatures() {

		Quantity<LengthUnit> oneFoot = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> twelveInch = new Quantity<>(12.0, LengthUnit.INCH);

		demonstrateEquality(oneFoot, twelveInch);
		demonstrateConversion(oneFoot, LengthUnit.INCH);
		demonstrateAddition(oneFoot, twelveInch);
		demonstrateAdditionWithTarget(oneFoot, twelveInch, LengthUnit.YARDS);
	}

	public static void demonstrateWeightFeatures() {

		Quantity<WeightUnit> kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> gram = new Quantity<>(1000.0, WeightUnit.GRAM);

		demonstrateEquality(kg, gram);
		demonstrateConversion(kg, WeightUnit.GRAM);
		demonstrateAddition(kg, gram);
		demonstrateAdditionWithTarget(kg, gram, WeightUnit.GRAM);
	}

	public static void demonstrateVolumeFeatures() {

		Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> milli = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> gallon = new Quantity<>(1.0, VolumeUnit.GALLON);

		demonstrateEquality(litre, milli);
		demonstrateEquality(litre, new Quantity<>(0.264172, VolumeUnit.GALLON));

		demonstrateConversion(litre, VolumeUnit.MILLILITRE);
		demonstrateConversion(gallon, VolumeUnit.LITRE);
		demonstrateConversion(milli, VolumeUnit.GALLON);

		demonstrateAddition(litre, milli);
		demonstrateAdditionWithTarget(litre, gallon, VolumeUnit.MILLILITRE);
		demonstrateAdditionWithTarget(milli, gallon, VolumeUnit.GALLON);
	}

	public static void main(String[] args) {
		demonstrateLengthFeatures();
		demonstrateWeightFeatures();
		demonstrateVolumeFeatures();
	}
}