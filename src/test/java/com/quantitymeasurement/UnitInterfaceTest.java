package com.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnitInterfaceTest {

	@Test
	void testLengthEquality_FeetAndInches() {
		assertTrue(new Quantity<>(1.0, LengthUnit.FEET).equals(new Quantity<>(12.0, LengthUnit.INCH)));
	}

	@Test
	void testLengthConversion_FeetToInches() {
		Quantity<LengthUnit> converted = new Quantity<>(1.0, LengthUnit.FEET).convertTo(LengthUnit.INCH);

		assertEquals(12.00, converted.getValue(), 1e-6);
		assertEquals(LengthUnit.INCH, converted.getUnit());
	}

	@Test
	void testLengthAddition_ExplicitTargetFeet() {
		Quantity<LengthUnit> sum = new Quantity<>(1.0, LengthUnit.FEET).add(new Quantity<>(12.0, LengthUnit.INCH),
				LengthUnit.FEET);

		assertEquals(2.00, sum.getValue(), 1e-6);
		assertEquals(LengthUnit.FEET, sum.getUnit());
	}

	@Test
	void testWeightEquality_KgAndGram() {
		assertTrue(new Quantity<>(1.0, WeightUnit.KILOGRAM).equals(new Quantity<>(1000.0, WeightUnit.GRAM)));
	}

	@Test
	void testWeightConversion_KgToGram() {
		Quantity<WeightUnit> converted = new Quantity<>(1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM);

		assertEquals(1000.00, converted.getValue(), 1e-6);
		assertEquals(WeightUnit.GRAM, converted.getUnit());
	}

	@Test
	void testWeightAddition_ExplicitTargetKg() {
		Quantity<WeightUnit> sum = new Quantity<>(1.0, WeightUnit.KILOGRAM).add(new Quantity<>(1000.0, WeightUnit.GRAM),
				WeightUnit.KILOGRAM);

		assertEquals(2.00, sum.getValue(), 1e-6);
		assertEquals(WeightUnit.KILOGRAM, sum.getUnit());
	}

	@Test
	void testCrossCategoryPrevention_Runtime() {
		Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);

		assertFalse(length.equals((Object) weight));
	}

	@Test
	void testConstructorValidation_NullUnit() {
		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(1.0, (LengthUnit) null));
	}

	@Test
	void testConstructorValidation_InvalidValue() {
		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(Double.NaN, LengthUnit.FEET));
	}
}