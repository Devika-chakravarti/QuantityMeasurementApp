package com.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConversionTest {

	private static final double EPSILON = 1e-6;

	@Test
	void testConversion_FeetToInch() {
		QuantityLength result = new QuantityLength(1.0, LengthUnit.FEET).convertTo(LengthUnit.INCH);

		assertEquals(12.0, result.getValue(), EPSILON);
	}

	@Test
	void testConversion_InchToFeet() {
		QuantityLength result = new QuantityLength(24.0, LengthUnit.INCH).convertTo(LengthUnit.FEET);

		assertEquals(2.0, result.getValue(), EPSILON);
	}

	@Test
	void testConversion_YardsToInch() {
		QuantityLength result = new QuantityLength(1.0, LengthUnit.YARDS).convertTo(LengthUnit.INCH);

		assertEquals(36.0, result.getValue(), EPSILON);
	}

	@Test
	void testConversion_ZeroValue() {
		QuantityLength result = new QuantityLength(0.0, LengthUnit.FEET).convertTo(LengthUnit.INCH);

		assertEquals(0.0, result.getValue(), EPSILON);
	}

	@Test
	void testConversion_NegativeValue() {
		QuantityLength result = new QuantityLength(-1.0, LengthUnit.FEET).convertTo(LengthUnit.INCH);

		assertEquals(-12.0, result.getValue(), EPSILON);
	}

	@Test
	void testConversion_InvalidUnit_Throws() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength(1.0, null));
	}

	@Test
	void testConversion_NaNOrInfinite_Throws() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength(Double.NaN, LengthUnit.FEET));
	}
}