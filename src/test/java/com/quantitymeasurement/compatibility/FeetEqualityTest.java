package com.quantitymeasurement.compatibility;

import org.junit.jupiter.api.Test;

import com.quantitymeasurement.core.Feet;

import static org.junit.jupiter.api.Assertions.*;

/*
 * FeetEqualityTest:
 * -----------------
 * This test class checks equality behavior for the Feet class.
 *
 * It verifies:
 * - same value equality
 * - different value inequality
 * - null comparison
 * - non-Feet object comparison
 * - same reference comparison
 */
public class FeetEqualityTest {

	/*
	 * Checks equality when both Feet objects have same value.
	 */
	@Test
	public void testFeetEquality_SameValue() {
		Feet feet1 = new Feet(1.0);
		Feet feet2 = new Feet(1.0);
		assertTrue(feet1.equals(feet2));
	}

	/*
	 * Checks inequality when Feet objects have different values.
	 */
	@Test
	public void testFeetEquality_DifferentValue() {
		Feet feet1 = new Feet(1.0);
		Feet feet2 = new Feet(2.0);
		assertFalse(feet1.equals(feet2));
	}

	/*
	 * Checks comparison with null.
	 */
	@Test
	public void testFeetEquality_NullComparison() {
		Feet feet = new Feet(1.0);
		assertFalse(feet.equals(null));
	}

	/*
	 * Checks comparison with a different type object.
	 */
	@Test
	public void testFeetEquality_NonNumericInput() {
		Feet feet = new Feet(1.0);
		String str = "Devika";
		assertFalse(feet.equals(str));
	}

	/*
	 * Checks reflexive property using same reference.
	 */
	@Test
	public void testFeetEquality_SameReference() {
		Feet feet = new Feet(1.0);
		assertTrue(feet.equals(feet));
	}
}