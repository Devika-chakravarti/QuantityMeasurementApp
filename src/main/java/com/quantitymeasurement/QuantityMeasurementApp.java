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

	// ---------------- UC14: TEMPERATURE DEMO ----------------

	public static void demonstrateTemperatureFeatures() {

		Quantity<TemperatureUnit> c0 = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
		Quantity<TemperatureUnit> f32 = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
		Quantity<TemperatureUnit> k273_15 = new Quantity<>(273.15, TemperatureUnit.KELVIN);

		Quantity<TemperatureUnit> c100 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
		Quantity<TemperatureUnit> f212 = new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT);
		Quantity<TemperatureUnit> k373_15 = new Quantity<>(373.15, TemperatureUnit.KELVIN);

		System.out.println("----- Temperature Equality -----");
		demonstrateEquality(c0, f32);
		demonstrateEquality(c0, k273_15);
		demonstrateEquality(c100, f212);
		demonstrateEquality(c100, k373_15);

		System.out.println("----- Temperature Conversion (All Unit Pairs) -----");
		demonstrateConversion(c0, TemperatureUnit.FAHRENHEIT);
		demonstrateConversion(c0, TemperatureUnit.KELVIN);

		demonstrateConversion(f32, TemperatureUnit.CELSIUS);
		demonstrateConversion(f32, TemperatureUnit.KELVIN);

		demonstrateConversion(k273_15, TemperatureUnit.CELSIUS);
		demonstrateConversion(k273_15, TemperatureUnit.FAHRENHEIT);

		System.out.println("----- Temperature Unsupported Operations -----");
		try {
			System.out.println(
					new Quantity<>(100.0, TemperatureUnit.CELSIUS).add(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
		} catch (UnsupportedOperationException e) {
			System.out.println("Expected Error (ADD): " + e.getMessage());
		}

		try {
			System.out.println(new Quantity<>(100.0, TemperatureUnit.CELSIUS)
					.subtract(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
		} catch (UnsupportedOperationException e) {
			System.out.println("Expected Error (SUBTRACT): " + e.getMessage());
		}

		try {
			System.out.println(new Quantity<>(100.0, TemperatureUnit.CELSIUS)
					.divide(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
		} catch (UnsupportedOperationException e) {
			System.out.println("Expected Error (DIVIDE): " + e.getMessage());
		}

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
		demonstrateTemperatureFeatures();
	}
}