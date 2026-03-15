package com.quantitymeasurement.compatibility;

import org.junit.jupiter.api.Test;

import com.quantitymeasurement.core.LengthUnit;
import com.quantitymeasurement.core.QuantityLength;
import com.quantitymeasurement.core.QuantityWeight;
import com.quantitymeasurement.core.WeightUnit;

import static org.junit.jupiter.api.Assertions.*;

/*
 * WeightEqualityTest:
 * -------------------
 * This test class checks equality, conversion, addition,
 * validation, and hashCode behavior for QuantityWeight.
 *
 * It verifies:
 * - equality across weight units
 * - conversion across weight units
 * - addition with implicit and explicit targets
 * - validation for null and invalid values
 * - hashCode consistency
 * - same reference and null comparison
 * - all unit combinations
 * - precision tolerance
 */
public class WeightEqualityTest {

	private static final double EPS = 1e-6;

	/*
	 * EQUALITY TESTS -------------- Checks equality across Kilogram and Gram.
	 */
	@Test
	void testEquality_KgToGram() {
		assertEquals(new QuantityWeight(1.0, WeightUnit.KILOGRAM), new QuantityWeight(1000.0, WeightUnit.GRAM));
	}

	/*
	 * Checks equality across Kilogram and Pound.
	 */
	@Test
	void testEquality_KgToPound() {
		assertEquals(new QuantityWeight(1.0, WeightUnit.KILOGRAM), new QuantityWeight(2.20462, WeightUnit.POUND));
	}

	/*
	 * Checks equality for same unit and same value.
	 */
	@Test
	void testEquality_SameUnit() {
		assertEquals(new QuantityWeight(5.0, WeightUnit.GRAM), new QuantityWeight(5.0, WeightUnit.GRAM));
	}

	/*
	 * Checks inequality for different values.
	 */
	@Test
	void testInequality_DifferentValues() {
		assertNotEquals(new QuantityWeight(1.0, WeightUnit.KILOGRAM), new QuantityWeight(2.0, WeightUnit.KILOGRAM));
	}

	/*
	 * Checks that QuantityWeight is not equal to QuantityLength.
	 */
	@Test
	void testCategorySafety_LengthNotEqualToWeight() {
		QuantityWeight weight = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
		QuantityLength length = new QuantityLength(1.0, LengthUnit.FEET);
		assertNotEquals(weight, length);
	}

	/*
	 * CONVERSION TESTS ---------------- Checks Kilogram to Gram conversion.
	 */
	@Test
	void testConversion_KgToGram() {
		QuantityWeight result = new QuantityWeight(1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM);

		assertEquals(1000.0, result.getValue(), EPS);
	}

	/*
	 * Checks Gram to Kilogram conversion.
	 */
	@Test
	void testConversion_GramToKg() {
		QuantityWeight result = new QuantityWeight(2000.0, WeightUnit.GRAM).convertTo(WeightUnit.KILOGRAM);

		assertEquals(2.0, result.getValue(), EPS);
	}

	/*
	 * Checks Pound to Kilogram conversion.
	 */
	@Test
	void testConversion_PoundToKg() {
		QuantityWeight result = new QuantityWeight(2.20462, WeightUnit.POUND).convertTo(WeightUnit.KILOGRAM);

		assertEquals(1.0, result.getValue(), 0.01);
	}

	/*
	 * Checks Kilogram to Pound conversion.
	 */
	@Test
	void testConversion_KgToPound() {
		QuantityWeight result = new QuantityWeight(1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.POUND);

		assertEquals(2.20462, result.getValue(), 0.01);
	}

	/*
	 * Checks conversion with zero value.
	 */
	@Test
	void testConversion_Zero() {
		QuantityWeight result = new QuantityWeight(0.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM);

		assertEquals(0.0, result.getValue(), EPS);
	}

	/*
	 * Checks conversion with negative value.
	 */
	@Test
	void testConversion_Negative() {
		QuantityWeight result = new QuantityWeight(-1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM);

		assertEquals(-1000.0, result.getValue(), EPS);
	}

	/*
	 * ADDITION TESTS -------------- Checks addition with implicit target.
	 */
	@Test
	void testAddition_ImplicitTarget() {
		QuantityWeight result = new QuantityWeight(1.0, WeightUnit.KILOGRAM)
				.add(new QuantityWeight(1000.0, WeightUnit.GRAM));

		assertEquals(2.0, result.getValue(), EPS);
		assertEquals(WeightUnit.KILOGRAM, result.getUnit());
	}

	/*
	 * Checks addition with explicit target Gram.
	 */
	@Test
	void testAddition_ExplicitTarget_Gram() {
		QuantityWeight result = new QuantityWeight(1.0, WeightUnit.KILOGRAM)
				.add(new QuantityWeight(1.0, WeightUnit.KILOGRAM), WeightUnit.GRAM);

		assertEquals(2000.0, result.getValue(), EPS);
	}

	/*
	 * Checks addition with explicit target Pound.
	 */
	@Test
	void testAddition_ExplicitTarget_Pound() {
		QuantityWeight result = new QuantityWeight(1.0, WeightUnit.KILOGRAM)
				.add(new QuantityWeight(1.0, WeightUnit.KILOGRAM), WeightUnit.POUND);

		assertEquals(4.40924, result.getValue(), 0.01);
	}

	/*
	 * Checks addition with zero value.
	 */
	@Test
	void testAddition_WithZero() {
		QuantityWeight result = new QuantityWeight(5.0, WeightUnit.KILOGRAM)
				.add(new QuantityWeight(0.0, WeightUnit.GRAM));

		assertEquals(5.0, result.getValue(), EPS);
	}

	/*
	 * Checks addition with negative value.
	 */
	@Test
	void testAddition_NegativeValues() {
		QuantityWeight result = new QuantityWeight(5.0, WeightUnit.KILOGRAM)
				.add(new QuantityWeight(-2.0, WeightUnit.KILOGRAM));

		assertEquals(3.0, result.getValue(), EPS);
	}

	/*
	 * Checks commutativity of addition.
	 */
	@Test
	void testAddition_Commutativity() {
		QuantityWeight a = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
		QuantityWeight b = new QuantityWeight(500.0, WeightUnit.GRAM);

		assertEquals(a.add(b), b.add(a));
	}

	/*
	 * Checks addition with large values.
	 */
	@Test
	void testAddition_LargeValues() {
		QuantityWeight result = new QuantityWeight(1000.0, WeightUnit.KILOGRAM)
				.add(new QuantityWeight(500.0, WeightUnit.KILOGRAM));

		assertEquals(1500.0, result.getValue(), EPS);
	}

	/*
	 * VALIDATION TESTS ---------------- Checks constructor validation for null
	 * unit.
	 */
	@Test
	void testNullUnitInConstructor() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityWeight(1.0, null));
	}

	/*
	 * Checks convert validation for null target unit.
	 */
	@Test
	void testNullTargetUnitInConvert() {
		assertThrows(IllegalArgumentException.class,
				() -> new QuantityWeight(1.0, WeightUnit.KILOGRAM).convertTo(null));
	}

	/*
	 * Checks add validation for null other weight.
	 */
	@Test
	void testNullOtherInAdd() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityWeight(1.0, WeightUnit.KILOGRAM).add(null));
	}

	/*
	 * Checks add validation for null target unit.
	 */
	@Test
	void testNullTargetUnitInAdd() {
		assertThrows(IllegalArgumentException.class,
				() -> new QuantityWeight(1.0, WeightUnit.KILOGRAM).add(new QuantityWeight(1.0, WeightUnit.GRAM), null));
	}

	/*
	 * Checks constructor validation for invalid numeric value.
	 */
	@Test
	void testInvalidNaNValue() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityWeight(Double.NaN, WeightUnit.KILOGRAM));
	}

	/*
	 * ADVANCED COVERAGE ----------------- Checks hashCode consistency for equal
	 * objects.
	 */
	@Test
	void testHashCodeConsistency() {
		QuantityWeight a = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
		QuantityWeight b = new QuantityWeight(1000.0, WeightUnit.GRAM);

		assertEquals(a.hashCode(), b.hashCode());
	}

	/*
	 * Checks same reference equality.
	 */
	@Test
	void testEquals_SameReference() {
		QuantityWeight weight = new QuantityWeight(1.0, WeightUnit.KILOGRAM);

		assertEquals(weight, weight);
	}

	/*
	 * Checks null comparison.
	 */
	@Test
	void testEquals_NullComparison() {
		QuantityWeight weight = new QuantityWeight(1.0, WeightUnit.KILOGRAM);

		assertNotEquals(weight, null);
	}

	/*
	 * Checks all unit combinations for addition.
	 */
	@Test
	void testAllUnitCombinations_Addition() {
		for (WeightUnit u1 : WeightUnit.values()) {
			for (WeightUnit u2 : WeightUnit.values()) {

				QuantityWeight q1 = new QuantityWeight(1.0, u1);
				QuantityWeight q2 = new QuantityWeight(2.0, u2);

				QuantityWeight result = q1.add(q2, WeightUnit.KILOGRAM);

				double expected = u1.convertToBaseUnit(1.0) + u2.convertToBaseUnit(2.0);

				assertEquals(expected, result.getValue(), EPS);
			}
		}
	}

	/*
	 * Checks precision tolerance for conversion.
	 */
	@Test
	void testPrecisionTolerance() {
		QuantityWeight result = new QuantityWeight(0.453592, WeightUnit.KILOGRAM).convertTo(WeightUnit.POUND);

		assertEquals(1.0, result.getValue(), 0.01);
	}
}