package com.quantitymeasurement.compatibility;

import org.junit.jupiter.api.Test;

import com.quantitymeasurement.core.LengthUnit;
import com.quantitymeasurement.core.QuantityLength;

import static org.junit.jupiter.api.Assertions.*;

/*
 * ConversionTest:
 * ---------------
 * This test class checks conversion behavior for QuantityLength.
 *
 * It verifies:
 * - conversion across supported length units
 * - zero and negative value conversion
 * - constructor validation for invalid inputs
 */
public class ConversionTest {

	private static final double EPSILON = 1e-6;

	/*
	 * Checks conversion from Feet to Inch.
	 */
	@Test
	void testConversion_FeetToInch() {
		QuantityLength result = new QuantityLength(1.0, LengthUnit.FEET).convertTo(LengthUnit.INCH);

		assertEquals(12.0, result.getValue(), EPSILON);
	}

	/*
	 * Checks conversion from Inch to Feet.
	 */
	@Test
	void testConversion_InchToFeet() {
		QuantityLength result = new QuantityLength(24.0, LengthUnit.INCH).convertTo(LengthUnit.FEET);

		assertEquals(2.0, result.getValue(), EPSILON);
	}

	/*
	 * Checks conversion from Yards to Inch.
	 */
	@Test
	void testConversion_YardsToInch() {
		QuantityLength result = new QuantityLength(1.0, LengthUnit.YARDS).convertTo(LengthUnit.INCH);

		assertEquals(36.0, result.getValue(), EPSILON);
	}

	/*
	 * Checks conversion when value is zero.
	 */
	@Test
	void testConversion_ZeroValue() {
		QuantityLength result = new QuantityLength(0.0, LengthUnit.FEET).convertTo(LengthUnit.INCH);

		assertEquals(0.0, result.getValue(), EPSILON);
	}

	/*
	 * Checks conversion when value is negative.
	 */
	@Test
	void testConversion_NegativeValue() {
		QuantityLength result = new QuantityLength(-1.0, LengthUnit.FEET).convertTo(LengthUnit.INCH);

		assertEquals(-12.0, result.getValue(), EPSILON);
	}

	/*
	 * Checks constructor validation for null unit.
	 */
	@Test
	void testConversion_InvalidUnit_Throws() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength(1.0, null));
	}

	/*
	 * Checks constructor validation for invalid numeric value.
	 */
	@Test
	void testConversion_NaNOrInfinite_Throws() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength(Double.NaN, LengthUnit.FEET));
	}
}