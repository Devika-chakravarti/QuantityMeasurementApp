package com.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test class for QuantityLength. Validates equality across different units.
 */
public class QuantityLengthEqualityTest {

	// Same unit, same value (Feet)
	@Test
	void testEquality_FeetToFeet_SameValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(1.0, LengthUnit.FEET);
		assertTrue(q1.equals(q2));
	}

	// Same unit, same value (Inch)
	@Test
	void testEquality_InchToInch_SameValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.INCH);
		QuantityLength q2 = new QuantityLength(1.0, LengthUnit.INCH);
		assertTrue(q1.equals(q2));
	}

	// Cross-unit equivalent value (Feet to Inch)
	@Test
	void testEquality_FeetToInch_EquivalentValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);
		assertTrue(q1.equals(q2));
	}

	// Cross-unit equivalent value (Inch to Feet)
	@Test
	void testEquality_InchToFeet_EquivalentValue() {
		QuantityLength q1 = new QuantityLength(12.0, LengthUnit.INCH);
		QuantityLength q2 = new QuantityLength(1.0, LengthUnit.FEET);
		assertTrue(q1.equals(q2));
	}

	// Different values (Feet)
	@Test
	void testEquality_FeetToFeet_DifferentValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(2.0, LengthUnit.FEET);
		assertFalse(q1.equals(q2));
	}

	// Different values (Inch)
	@Test
	void testEquality_InchToInch_DifferentValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.INCH);
		QuantityLength q2 = new QuantityLength(2.0, LengthUnit.INCH);
		assertFalse(q1.equals(q2));
	}

	// Same reference check (Reflexive property)
	@Test
	void testEquality_SameReference() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		assertTrue(q1.equals(q1));
	}

	// Null comparison
	@Test
	void testEquality_NullComparison() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		assertFalse(q1.equals(null));
	}

	// Null unit validation
	@Test
	void testEquality_NullUnit() {
		assertThrows(IllegalArgumentException.class, () -> {
			new QuantityLength(1.0, null);
		});
	}
}