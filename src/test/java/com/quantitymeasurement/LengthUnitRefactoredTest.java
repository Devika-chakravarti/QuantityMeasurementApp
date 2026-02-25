package com.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class LengthUnitRefactoredTest {

	private static final double EPSILON = 1e-6;

	// -------------------- LengthUnit Constant Tests --------------------

	@Test
	void testLengthUnitEnum_FeetConstant() {
		assertEquals(1.0, LengthUnit.FEET.getConversionFactor(), EPSILON);
	}

	@Test
	void testLengthUnitEnum_InchesConstant() {
		assertEquals(1.0 / 12.0, LengthUnit.INCH.getConversionFactor(), EPSILON);
	}

	@Test
	void testLengthUnitEnum_YardsConstant() {
		assertEquals(3.0, LengthUnit.YARDS.getConversionFactor(), EPSILON);
	}

	@Test
	void testLengthUnitEnum_CentimetersConstant() {
		assertEquals(1.0 / 30.48, LengthUnit.CENTIMETERS.getConversionFactor(), EPSILON);
	}

	// -------------------- convertToBaseUnit Tests --------------------

	@Test
	void testConvertToBaseUnit_FeetToFeet() {
		assertEquals(5.0, LengthUnit.FEET.convertToBaseUnit(5.0), EPSILON);
	}

	@Test
	void testConvertToBaseUnit_InchesToFeet() {
		assertEquals(1.0, LengthUnit.INCH.convertToBaseUnit(12.0), EPSILON);
	}

	@Test
	void testConvertToBaseUnit_YardsToFeet() {
		assertEquals(3.0, LengthUnit.YARDS.convertToBaseUnit(1.0), EPSILON);
	}

	@Test
	void testConvertToBaseUnit_CentimetersToFeet() {
		assertEquals(1.0, LengthUnit.CENTIMETERS.convertToBaseUnit(30.48), EPSILON);
	}

	// -------------------- convertFromBaseUnit Tests --------------------

	@Test
	void testConvertFromBaseUnit_FeetToFeet() {
		assertEquals(2.0, LengthUnit.FEET.convertFromBaseUnit(2.0), EPSILON);
	}

	@Test
	void testConvertFromBaseUnit_FeetToInches() {
		assertEquals(12.0, LengthUnit.INCH.convertFromBaseUnit(1.0), EPSILON);
	}

	@Test
	void testConvertFromBaseUnit_FeetToYards() {
		assertEquals(1.0, LengthUnit.YARDS.convertFromBaseUnit(3.0), EPSILON);
	}

	@Test
	void testConvertFromBaseUnit_FeetToCentimeters() {
		assertEquals(30.48, LengthUnit.CENTIMETERS.convertFromBaseUnit(1.0), EPSILON);
	}

	// -------------------- Refactored QuantityLength Tests --------------------

	@Test
	void testQuantityLengthRefactored_Equality() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);
		assertEquals(q1, q2);
	}

	@Test
	void testQuantityLengthRefactored_ConvertTo() {
		QuantityLength result = new QuantityLength(1.0, LengthUnit.FEET).convertTo(LengthUnit.INCH);

		assertEquals(12.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.INCH, result.getUnit());
	}

	@Test
	void testQuantityLengthRefactored_Add() {
		QuantityLength result = new QuantityLength(1.0, LengthUnit.FEET).add(new QuantityLength(12.0, LengthUnit.INCH),
				LengthUnit.FEET);

		assertEquals(2.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	void testQuantityLengthRefactored_AddWithTargetUnit() {
		QuantityLength result = new QuantityLength(1.0, LengthUnit.FEET).add(new QuantityLength(12.0, LengthUnit.INCH),
				LengthUnit.YARDS);

		assertEquals(2.0 / 3.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.YARDS, result.getUnit());
	}

	@Test
	void testQuantityLengthRefactored_NullUnit() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength(1.0, null));
	}

	@Test
	void testQuantityLengthRefactored_InvalidValue() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength(Double.NaN, LengthUnit.FEET));
	}

	// -------------------- Backward Compatibility Tests --------------------

	@Test
	void testBackwardCompatibility_UC1EqualityTests() {
		assertEquals(new QuantityLength(1, LengthUnit.FEET), new QuantityLength(12, LengthUnit.INCH));

		assertEquals(new QuantityLength(3, LengthUnit.FEET), new QuantityLength(1, LengthUnit.YARDS));
	}

	@Test
	void testBackwardCompatibility_UC5ConversionTests() {
		assertEquals(new QuantityLength(12, LengthUnit.INCH),
				new QuantityLength(1, LengthUnit.FEET).convertTo(LengthUnit.INCH));
	}

	@Test
	void testBackwardCompatibility_UC6AdditionTests() {
		assertEquals(new QuantityLength(2, LengthUnit.FEET),
				new QuantityLength(1, LengthUnit.FEET).add(new QuantityLength(12, LengthUnit.INCH)));
	}

	@Test
	void testBackwardCompatibility_UC7AdditionWithTargetUnitTests() {
		assertEquals(new QuantityLength(2.0 / 3.0, LengthUnit.YARDS),
				new QuantityLength(1, LengthUnit.FEET).add(new QuantityLength(12, LengthUnit.INCH), LengthUnit.YARDS));
	}

	// -------------------- Architectural Scalability --------------------

	enum DummyWeightUnit {
		KG, GRAM
	}

	@Test
	void testArchitecturalScalability_MultipleCategories() {
		assertNotNull(DummyWeightUnit.KG);
		assertNotEquals(LengthUnit.FEET.getClass(), DummyWeightUnit.KG.getClass());
	}

	// -------------------- Round Trip Conversion --------------------

	@Test
	void testRoundTripConversion_RefactoredDesign() {
		QuantityLength original = new QuantityLength(5.0, LengthUnit.FEET);

		QuantityLength converted = original.convertTo(LengthUnit.CENTIMETERS).convertTo(LengthUnit.FEET);

		assertEquals(original.getValue(), converted.getValue(), EPSILON);
	}

	// -------------------- Enum Immutability --------------------

	@Test
	void testUnitImmutability() {
		LengthUnit unit = LengthUnit.FEET;
		assertEquals(1.0, unit.getConversionFactor(), EPSILON);
		assertSame(unit, LengthUnit.valueOf("FEET"));
	}
}