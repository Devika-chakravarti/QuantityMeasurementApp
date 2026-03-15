package com.quantitymeasurement.compatibility;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.quantitymeasurement.core.LengthUnit;
import com.quantitymeasurement.core.QuantityLength;

/*
 * LengthUnitRefactoredTest:
 * -------------------------
 * This test class checks the refactored length-unit design.
 *
 * It verifies:
 * - enum constant conversion factors
 * - base-unit conversion methods
 * - refactored QuantityLength behavior
 * - backward compatibility with earlier UCs
 * - architectural separation across categories
 * - round-trip conversion
 * - enum immutability
 */
public class LengthUnitRefactoredTest {

	private static final double EPSILON = 1e-6;

	/*
	 * LENGTH UNIT CONSTANT TESTS -------------------------- Checks FEET conversion
	 * factor.
	 */
	@Test
	void testLengthUnitEnum_FeetConstant() {
		assertEquals(1.0, LengthUnit.FEET.getConversionFactor(), EPSILON);
	}

	/*
	 * Checks INCH conversion factor.
	 */
	@Test
	void testLengthUnitEnum_InchesConstant() {
		assertEquals(1.0 / 12.0, LengthUnit.INCH.getConversionFactor(), EPSILON);
	}

	/*
	 * Checks YARDS conversion factor.
	 */
	@Test
	void testLengthUnitEnum_YardsConstant() {
		assertEquals(3.0, LengthUnit.YARDS.getConversionFactor(), EPSILON);
	}

	/*
	 * Checks CENTIMETERS conversion factor.
	 */
	@Test
	void testLengthUnitEnum_CentimetersConstant() {
		assertEquals(1.0 / 30.48, LengthUnit.CENTIMETERS.getConversionFactor(), EPSILON);
	}

	/*
	 * convertToBaseUnit TESTS ----------------------- Checks Feet to Feet
	 * conversion.
	 */
	@Test
	void testConvertToBaseUnit_FeetToFeet() {
		assertEquals(5.0, LengthUnit.FEET.convertToBaseUnit(5.0), EPSILON);
	}

	/*
	 * Checks Inches to Feet conversion.
	 */
	@Test
	void testConvertToBaseUnit_InchesToFeet() {
		assertEquals(1.0, LengthUnit.INCH.convertToBaseUnit(12.0), EPSILON);
	}

	/*
	 * Checks Yards to Feet conversion.
	 */
	@Test
	void testConvertToBaseUnit_YardsToFeet() {
		assertEquals(3.0, LengthUnit.YARDS.convertToBaseUnit(1.0), EPSILON);
	}

	/*
	 * Checks Centimeters to Feet conversion.
	 */
	@Test
	void testConvertToBaseUnit_CentimetersToFeet() {
		assertEquals(1.0, LengthUnit.CENTIMETERS.convertToBaseUnit(30.48), EPSILON);
	}

	/*
	 * convertFromBaseUnit TESTS ------------------------- Checks Feet to Feet
	 * conversion.
	 */
	@Test
	void testConvertFromBaseUnit_FeetToFeet() {
		assertEquals(2.0, LengthUnit.FEET.convertFromBaseUnit(2.0), EPSILON);
	}

	/*
	 * Checks Feet to Inches conversion.
	 */
	@Test
	void testConvertFromBaseUnit_FeetToInches() {
		assertEquals(12.0, LengthUnit.INCH.convertFromBaseUnit(1.0), EPSILON);
	}

	/*
	 * Checks Feet to Yards conversion.
	 */
	@Test
	void testConvertFromBaseUnit_FeetToYards() {
		assertEquals(1.0, LengthUnit.YARDS.convertFromBaseUnit(3.0), EPSILON);
	}

	/*
	 * Checks Feet to Centimeters conversion.
	 */
	@Test
	void testConvertFromBaseUnit_FeetToCentimeters() {
		assertEquals(30.48, LengthUnit.CENTIMETERS.convertFromBaseUnit(1.0), EPSILON);
	}

	/*
	 * REFACTORED QuantityLength TESTS ------------------------------- Checks
	 * equality across Feet and Inches.
	 */
	@Test
	void testQuantityLengthRefactored_Equality() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);
		assertEquals(q1, q2);
	}

	/*
	 * Checks convertTo behavior in refactored QuantityLength.
	 */
	@Test
	void testQuantityLengthRefactored_ConvertTo() {
		QuantityLength result = new QuantityLength(1.0, LengthUnit.FEET).convertTo(LengthUnit.INCH);

		assertEquals(12.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.INCH, result.getUnit());
	}

	/*
	 * Checks add behavior in refactored QuantityLength.
	 */
	@Test
	void testQuantityLengthRefactored_Add() {
		QuantityLength result = new QuantityLength(1.0, LengthUnit.FEET).add(new QuantityLength(12.0, LengthUnit.INCH),
				LengthUnit.FEET);

		assertEquals(2.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	/*
	 * Checks add with target unit behavior in refactored QuantityLength.
	 */
	@Test
	void testQuantityLengthRefactored_AddWithTargetUnit() {
		QuantityLength result = new QuantityLength(1.0, LengthUnit.FEET).add(new QuantityLength(12.0, LengthUnit.INCH),
				LengthUnit.YARDS);

		assertEquals(2.0 / 3.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.YARDS, result.getUnit());
	}

	/*
	 * Checks constructor validation for null unit.
	 */
	@Test
	void testQuantityLengthRefactored_NullUnit() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength(1.0, null));
	}

	/*
	 * Checks constructor validation for invalid value.
	 */
	@Test
	void testQuantityLengthRefactored_InvalidValue() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength(Double.NaN, LengthUnit.FEET));
	}

	/*
	 * BACKWARD COMPATIBILITY TESTS ---------------------------- Checks old equality
	 * behavior remains same.
	 */
	@Test
	void testBackwardCompatibility_UC1EqualityTests() {
		assertEquals(new QuantityLength(1, LengthUnit.FEET), new QuantityLength(12, LengthUnit.INCH));

		assertEquals(new QuantityLength(3, LengthUnit.FEET), new QuantityLength(1, LengthUnit.YARDS));
	}

	/*
	 * Checks old conversion behavior remains same.
	 */
	@Test
	void testBackwardCompatibility_UC5ConversionTests() {
		assertEquals(new QuantityLength(12, LengthUnit.INCH),
				new QuantityLength(1, LengthUnit.FEET).convertTo(LengthUnit.INCH));
	}

	/*
	 * Checks old addition behavior remains same.
	 */
	@Test
	void testBackwardCompatibility_UC6AdditionTests() {
		assertEquals(new QuantityLength(2, LengthUnit.FEET),
				new QuantityLength(1, LengthUnit.FEET).add(new QuantityLength(12, LengthUnit.INCH)));
	}

	/*
	 * Checks old target-unit addition behavior remains same.
	 */
	@Test
	void testBackwardCompatibility_UC7AdditionWithTargetUnitTests() {
		assertEquals(new QuantityLength(2.0 / 3.0, LengthUnit.YARDS),
				new QuantityLength(1, LengthUnit.FEET).add(new QuantityLength(12, LengthUnit.INCH), LengthUnit.YARDS));
	}

	/*
	 * ARCHITECTURAL SCALABILITY ------------------------- Dummy enum used to check
	 * category separation.
	 */
	enum DummyWeightUnit {
		KG, GRAM
	}

	/*
	 * Checks that multiple categories remain architecturally separate.
	 */
	@Test
	void testArchitecturalScalability_MultipleCategories() {
		assertNotNull(DummyWeightUnit.KG);
		assertNotEquals(LengthUnit.FEET.getClass(), DummyWeightUnit.KG.getClass());
	}

	/*
	 * ROUND TRIP CONVERSION --------------------- Checks conversion to Centimeters
	 * and back to Feet.
	 */
	@Test
	void testRoundTripConversion_RefactoredDesign() {
		QuantityLength original = new QuantityLength(5.0, LengthUnit.FEET);

		QuantityLength converted = original.convertTo(LengthUnit.CENTIMETERS).convertTo(LengthUnit.FEET);

		assertEquals(original.getValue(), converted.getValue(), EPSILON);
	}

	/*
	 * ENUM IMMUTABILITY ----------------- Checks that enum object remains the same
	 * and factor stays unchanged.
	 */
	@Test
	void testUnitImmutability() {
		LengthUnit unit = LengthUnit.FEET;
		assertEquals(1.0, unit.getConversionFactor(), EPSILON);
		assertSame(unit, LengthUnit.valueOf("FEET"));
	}
}