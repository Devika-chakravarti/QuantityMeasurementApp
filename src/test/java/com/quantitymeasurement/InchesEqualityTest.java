package com.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Inches equality (UC2).
 */
public class InchesEqualityTest {

	@Test
	public void testEquality_SameValue() {
		// Same value comparison
		Inches inch1 = new Inches(1.0);
		Inches inch2 = new Inches(1.0);
		assertTrue(inch1.equals(inch2));
	}

	@Test
	public void testEquality_DifferentValue() {
		// Different value comparison
		Inches inch1 = new Inches(1.0);
		Inches inch2 = new Inches(2.0);
		assertFalse(inch1.equals(inch2));
	}

	@Test
	public void testEquality_NullComparison() {
		// Null comparison check
		Inches inch = new Inches(1.0);
		assertFalse(inch.equals(null));
	}

	@Test
	public void testEquality_NonNumericInput() {
		// Different type comparison
		Inches inch = new Inches(1.0);
		String str = "Devika";
		assertFalse(inch.equals(str));
	}

	@Test
	public void testEquality_SameReference() {
		// Reflexive property check
		Inches inch = new Inches(1.0);
		assertTrue(inch.equals(inch));
	}
}