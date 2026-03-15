package com.quantitymeasurement.compatibility;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.quantitymeasurement.core.LengthUnit;
import com.quantitymeasurement.core.QuantityLength;

/*
 * YardEqualityTest:
 * -----------------
 * This test class checks equality behavior for QuantityLength
 * with extended length units like Yards and Centimeters.
 *
 * It verifies:
 * - same unit equality
 * - cross-unit equivalent equality
 * - non-equivalent values
 * - transitive equality
 * - null handling
 * - constructor validation for null unit
 * - complex multi-unit equality cases
 */
public class YardEqualityTest {

	/*
	 * Checks equality for same unit and same value in Yards.
	 */
	@Test
	void testEquality_YardToYard_SameValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
		QuantityLength q2 = new QuantityLength(1.0, LengthUnit.YARDS);
		assertTrue(q1.equals(q2));
	}

	/*
	 * Checks inequality for same unit but different value in Yards.
	 */
	@Test
	void testEquality_YardToYard_DifferentValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
		QuantityLength q2 = new QuantityLength(2.0, LengthUnit.YARDS);
		assertFalse(q1.equals(q2));
	}

	/*
	 * Checks equality across Yard and Feet.
	 */
	@Test
	void testEquality_YardToFeet_EquivalentValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
		QuantityLength q2 = new QuantityLength(3.0, LengthUnit.FEET);
		assertTrue(q1.equals(q2));
	}

	/*
	 * Checks equality across Feet and Yard.
	 */
	@Test
	void testEquality_FeetToYard_EquivalentValue() {
		QuantityLength q1 = new QuantityLength(3.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(1.0, LengthUnit.YARDS);
		assertTrue(q1.equals(q2));
	}

	/*
	 * Checks equality across Yard and Inches.
	 */
	@Test
	void testEquality_YardToInches_EquivalentValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
		QuantityLength q2 = new QuantityLength(36.0, LengthUnit.INCH);
		assertTrue(q1.equals(q2));
	}

	/*
	 * Checks equality across Inches and Yard.
	 */
	@Test
	void testEquality_InchesToYard_EquivalentValue() {
		QuantityLength q1 = new QuantityLength(36.0, LengthUnit.INCH);
		QuantityLength q2 = new QuantityLength(1.0, LengthUnit.YARDS);
		assertTrue(q1.equals(q2));
	}

	/*
	 * Checks inequality across Yard and Feet for non-equivalent values.
	 */
	@Test
	void testEquality_YardToFeet_NonEquivalentValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
		QuantityLength q2 = new QuantityLength(2.0, LengthUnit.FEET);
		assertFalse(q1.equals(q2));
	}

	/*
	 * Checks equality across Centimeters and Inches.
	 */
	@Test
	void testEquality_centimetersToInches_EquivalentValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
		QuantityLength q2 = new QuantityLength(0.393701, LengthUnit.INCH);
		assertTrue(q1.equals(q2));
	}

	/*
	 * Checks inequality across Centimeters and Feet.
	 */
	@Test
	void testEquality_centimetersToFeet_NonEquivalentValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
		QuantityLength q2 = new QuantityLength(1.0, LengthUnit.FEET);
		assertFalse(q1.equals(q2));
	}

	/*
	 * Checks transitive equality across Yard, Feet, and Inches.
	 */
	@Test
	void testEquality_MultiUnit_TransitiveProperty() {
		QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARDS);
		QuantityLength feet = new QuantityLength(3.0, LengthUnit.FEET);
		QuantityLength inch = new QuantityLength(36.0, LengthUnit.INCH);

		assertTrue(yard.equals(feet));
		assertTrue(feet.equals(inch));
		assertTrue(yard.equals(inch));
	}

	/*
	 * Checks constructor validation for null unit with Yard.
	 */
	@Test
	void testEquality_YardWithNullUnit() {
		assertThrows(IllegalArgumentException.class, () -> {
			new QuantityLength(1.0, null);
		});
	}

	/*
	 * Checks reflexive equality for Yard.
	 */
	@Test
	void testEquality_YardSameReference() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
		assertTrue(q1.equals(q1));
	}

	/*
	 * Checks null comparison for Yard.
	 */
	@Test
	void testEquality_YardNullComparison() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
		assertFalse(q1.equals(null));
	}

	/*
	 * Checks constructor validation for null unit with Centimeters.
	 */
	@Test
	void testEquality_CentimetersWithNullUnit() {
		assertThrows(IllegalArgumentException.class, () -> {
			new QuantityLength(1.0, null);
		});
	}

	/*
	 * Checks reflexive equality for Centimeters.
	 */
	@Test
	void testEquality_CentimetersSameReference() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
		assertTrue(q1.equals(q1));
	}

	/*
	 * Checks null comparison for Centimeters.
	 */
	@Test
	void testEquality_CentimetersNullComparison() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
		assertFalse(q1.equals(null));
	}

	/*
	 * Checks a more complex multi-unit equality scenario.
	 */
	@Test
	void testEquality_AllUnits_ComplexScenario() {
		QuantityLength yard = new QuantityLength(2.0, LengthUnit.YARDS);
		QuantityLength feet = new QuantityLength(6.0, LengthUnit.FEET);
		QuantityLength inch = new QuantityLength(72.0, LengthUnit.INCH);

		assertTrue(yard.equals(feet));
		assertTrue(feet.equals(inch));
		assertTrue(yard.equals(inch));
	}
}