package com.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConversionTest {

	private static final double EPSILON = 1e-6;

	@Test
	void testConversion_FeetToInch() {
		assertEquals(12.0, QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCH), EPSILON);
	}

	@Test
	void testConversion_InchToFeet() {
		assertEquals(2.0, QuantityLength.convert(24.0, LengthUnit.INCH, LengthUnit.FEET), EPSILON);
	}

	@Test
	void testConversion_YardsToInch() {
		assertEquals(36.0, QuantityLength.convert(1.0, LengthUnit.YARDS, LengthUnit.INCH), EPSILON);
	}

	@Test
	void testConversion_ZeroValue() {
		assertEquals(0.0, QuantityLength.convert(0.0, LengthUnit.FEET, LengthUnit.INCH), EPSILON);
	}

	@Test
	void testConversion_NegativeValue() {
		assertEquals(-12.0, QuantityLength.convert(-1.0, LengthUnit.FEET, LengthUnit.INCH), EPSILON);
	}

	@Test
	void testConversion_InvalidUnit_Throws() {
		assertThrows(IllegalArgumentException.class, () -> QuantityLength.convert(1.0, null, LengthUnit.FEET));
	}

	@Test
	void testConversion_NaNOrInfinite_Throws() {
		assertThrows(IllegalArgumentException.class,
				() -> QuantityLength.convert(Double.NaN, LengthUnit.FEET, LengthUnit.INCH));
	}
}