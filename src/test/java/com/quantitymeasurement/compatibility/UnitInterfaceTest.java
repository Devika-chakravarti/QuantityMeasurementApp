package com.quantitymeasurement.compatibility;

import org.junit.jupiter.api.Test;

import com.quantitymeasurement.core.LengthUnit;
import com.quantitymeasurement.core.Quantity;
import com.quantitymeasurement.core.WeightUnit;

import static org.junit.jupiter.api.Assertions.*;

/*
 * UnitInterfaceTest:
 * ------------------
 * This test class checks the generic Quantity class with different
 * unit categories that implement IMeasurable.
 *
 * It verifies:
 * - length equality
 * - length conversion
 * - length addition
 * - weight equality
 * - weight conversion
 * - weight addition
 * - cross-category equality prevention
 * - constructor validation
 */
public class UnitInterfaceTest {

	/*
	 * Checks generic length equality across Feet and Inches.
	 */
	@Test
	void testLengthEquality_FeetAndInches() {
		assertTrue(new Quantity<>(1.0, LengthUnit.FEET).equals(new Quantity<>(12.0, LengthUnit.INCH)));
	}

	/*
	 * Checks generic length conversion from Feet to Inches.
	 */
	@Test
	void testLengthConversion_FeetToInches() {
		Quantity<LengthUnit> converted = new Quantity<>(1.0, LengthUnit.FEET).convertTo(LengthUnit.INCH);

		assertEquals(12.00, converted.getValue(), 1e-6);
		assertEquals(LengthUnit.INCH, converted.getUnit());
	}

	/*
	 * Checks generic length addition with explicit target Feet.
	 */
	@Test
	void testLengthAddition_ExplicitTargetFeet() {
		Quantity<LengthUnit> sum = new Quantity<>(1.0, LengthUnit.FEET).add(new Quantity<>(12.0, LengthUnit.INCH),
				LengthUnit.FEET);

		assertEquals(2.00, sum.getValue(), 1e-6);
		assertEquals(LengthUnit.FEET, sum.getUnit());
	}

	/*
	 * Checks generic weight equality across Kilogram and Gram.
	 */
	@Test
	void testWeightEquality_KgAndGram() {
		assertTrue(new Quantity<>(1.0, WeightUnit.KILOGRAM).equals(new Quantity<>(1000.0, WeightUnit.GRAM)));
	}

	/*
	 * Checks generic weight conversion from Kilogram to Gram.
	 */
	@Test
	void testWeightConversion_KgToGram() {
		Quantity<WeightUnit> converted = new Quantity<>(1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM);

		assertEquals(1000.00, converted.getValue(), 1e-6);
		assertEquals(WeightUnit.GRAM, converted.getUnit());
	}

	/*
	 * Checks generic weight addition with explicit target Kilogram.
	 */
	@Test
	void testWeightAddition_ExplicitTargetKg() {
		Quantity<WeightUnit> sum = new Quantity<>(1.0, WeightUnit.KILOGRAM).add(new Quantity<>(1000.0, WeightUnit.GRAM),
				WeightUnit.KILOGRAM);

		assertEquals(2.00, sum.getValue(), 1e-6);
		assertEquals(WeightUnit.KILOGRAM, sum.getUnit());
	}

	/*
	 * Checks that equality across different categories returns false.
	 */
	@Test
	void testCrossCategoryPrevention_Runtime() {
		Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);

		assertFalse(length.equals((Object) weight));
	}

	/*
	 * Checks constructor validation when unit is null.
	 */
	@Test
	void testConstructorValidation_NullUnit() {
		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(1.0, (LengthUnit) null));
	}

	/*
	 * Checks constructor validation when value is invalid.
	 */
	@Test
	void testConstructorValidation_InvalidValue() {
		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(Double.NaN, LengthUnit.FEET));
	}
}