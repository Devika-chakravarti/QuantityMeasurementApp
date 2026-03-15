package com.quantitymeasurement.compatibility;

import org.junit.jupiter.api.Test;

import com.quantitymeasurement.core.Inches;

import static org.junit.jupiter.api.Assertions.*;

/*
 * InchesEqualityTest:
 * -------------------
 * This test class checks equality behavior for the Inches class.
 *
 * It verifies:
 * - same value equality
 * - different value inequality
 * - null comparison
 * - non-Inches object comparison
 * - same reference comparison
 */
public class InchesEqualityTest {

	/*
	 * Checks equality when both Inches objects have same value.
	 */
	@Test
	public void testEquality_SameValue() {
		Inches inch1 = new Inches(1.0);
		Inches inch2 = new Inches(1.0);
		assertTrue(inch1.equals(inch2));
	}

	/*
	 * Checks inequality when Inches objects have different values.
	 */
	@Test
	public void testEquality_DifferentValue() {
		Inches inch1 = new Inches(1.0);
		Inches inch2 = new Inches(2.0);
		assertFalse(inch1.equals(inch2));
	}

	/*
	 * Checks comparison with null.
	 */
	@Test
	public void testEquality_NullComparison() {
		Inches inch = new Inches(1.0);
		assertFalse(inch.equals(null));
	}

	/*
	 * Checks comparison with a different type object.
	 */
	@Test
	public void testEquality_NonNumericInput() {
		Inches inch = new Inches(1.0);
		String str = "Devika";
		assertFalse(inch.equals(str));
	}

	/*
	 * Checks reflexive property using same reference.
	 */
	@Test
	public void testEquality_SameReference() {
		Inches inch = new Inches(1.0);
		assertTrue(inch.equals(inch));
	}
}