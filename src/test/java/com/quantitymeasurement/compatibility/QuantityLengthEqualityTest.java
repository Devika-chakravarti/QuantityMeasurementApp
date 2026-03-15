package com.quantitymeasurement.compatibility;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.quantitymeasurement.core.LengthUnit;
import com.quantitymeasurement.core.QuantityLength;

/*
 * QuantityLengthEqualityTest:
 * ---------------------------
 * This test class checks equality behavior for QuantityLength.
 *
 * It verifies:
 * - same unit same value equality
 * - cross-unit equivalent value equality
 * - different value inequality
 * - same reference comparison
 * - null comparison
 * - constructor validation for null unit
 */
public class QuantityLengthEqualityTest {

	/*
	 * Checks equality for same unit and same value in Feet.
	 */
	@Test
	void testEquality_FeetToFeet_SameValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(1.0, LengthUnit.FEET);
		assertTrue(q1.equals(q2));
	}

	/*
	 * Checks equality for same unit and same value in Inch.
	 */
	@Test
	void testEquality_InchToInch_SameValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.INCH);
		QuantityLength q2 = new QuantityLength(1.0, LengthUnit.INCH);
		assertTrue(q1.equals(q2));
	}

	/*
	 * Checks equality for equivalent values across Feet and Inch.
	 */
	@Test
	void testEquality_FeetToInch_EquivalentValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);
		assertTrue(q1.equals(q2));
	}

	/*
	 * Checks equality for equivalent values across Inch and Feet.
	 */
	@Test
	void testEquality_InchToFeet_EquivalentValue() {
		QuantityLength q1 = new QuantityLength(12.0, LengthUnit.INCH);
		QuantityLength q2 = new QuantityLength(1.0, LengthUnit.FEET);
		assertTrue(q1.equals(q2));
	}

	/*
	 * Checks inequality for different values in Feet.
	 */
	@Test
	void testEquality_FeetToFeet_DifferentValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(2.0, LengthUnit.FEET);
		assertFalse(q1.equals(q2));
	}

	/*
	 * Checks inequality for different values in Inch.
	 */
	@Test
	void testEquality_InchToInch_DifferentValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.INCH);
		QuantityLength q2 = new QuantityLength(2.0, LengthUnit.INCH);
		assertFalse(q1.equals(q2));
	}

	/*
	 * Checks reflexive property using same reference.
	 */
	@Test
	void testEquality_SameReference() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		assertTrue(q1.equals(q1));
	}

	/*
	 * Checks comparison with null.
	 */
	@Test
	void testEquality_NullComparison() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		assertFalse(q1.equals(null));
	}

	/*
	 * Checks constructor validation for null unit.
	 */
	@Test
	void testEquality_NullUnit() {
		assertThrows(IllegalArgumentException.class, () -> {
			new QuantityLength(1.0, null);
		});
	}
}