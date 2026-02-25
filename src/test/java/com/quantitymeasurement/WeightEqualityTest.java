package com.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WeightEqualityTest {

	private static final double EPS = 1e-6;

	// 1–5 Equality Tests
	@Test
	void testEquality_KgToGram() {
		assertEquals(new QuantityWeight(1.0, WeightUnit.KILOGRAM), new QuantityWeight(1000.0, WeightUnit.GRAM));
	}

	@Test
	void testEquality_KgToPound() {
		assertEquals(new QuantityWeight(1.0, WeightUnit.KILOGRAM), new QuantityWeight(2.20462, WeightUnit.POUND));
	}

	@Test
	void testEquality_SameUnit() {
		assertEquals(new QuantityWeight(5.0, WeightUnit.GRAM), new QuantityWeight(5.0, WeightUnit.GRAM));
	}

	@Test
	void testInequality_DifferentValues() {
		assertNotEquals(new QuantityWeight(1.0, WeightUnit.KILOGRAM), new QuantityWeight(2.0, WeightUnit.KILOGRAM));
	}

	@Test
	void testCategorySafety_LengthNotEqualToWeight() {
		QuantityWeight weight = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
		QuantityLength length = new QuantityLength(1.0, LengthUnit.FEET);
		assertNotEquals(weight, length);
	}

	// 6–11 Conversion Tests
	@Test
	void testConversion_KgToGram() {
		QuantityWeight result = new QuantityWeight(1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM);

		assertEquals(1000.0, result.getValue(), EPS);
	}

	@Test
	void testConversion_GramToKg() {
		QuantityWeight result = new QuantityWeight(2000.0, WeightUnit.GRAM).convertTo(WeightUnit.KILOGRAM);

		assertEquals(2.0, result.getValue(), EPS);
	}

	@Test
	void testConversion_PoundToKg() {
		QuantityWeight result = new QuantityWeight(2.20462, WeightUnit.POUND).convertTo(WeightUnit.KILOGRAM);

		assertEquals(1.0, result.getValue(), 0.01);
	}

	@Test
	void testConversion_KgToPound() {
		QuantityWeight result = new QuantityWeight(1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.POUND);

		assertEquals(2.20462, result.getValue(), 0.01);
	}

	@Test
	void testConversion_Zero() {
		QuantityWeight result = new QuantityWeight(0.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM);

		assertEquals(0.0, result.getValue(), EPS);
	}

	@Test
	void testConversion_Negative() {
		QuantityWeight result = new QuantityWeight(-1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM);

		assertEquals(-1000.0, result.getValue(), EPS);
	}

	// 12–18 Addition Tests
	@Test
	void testAddition_ImplicitTarget() {
		QuantityWeight result = new QuantityWeight(1.0, WeightUnit.KILOGRAM)
				.add(new QuantityWeight(1000.0, WeightUnit.GRAM));

		assertEquals(2.0, result.getValue(), EPS);
		assertEquals(WeightUnit.KILOGRAM, result.getUnit());
	}

	@Test
	void testAddition_ExplicitTarget_Gram() {
		QuantityWeight result = new QuantityWeight(1.0, WeightUnit.KILOGRAM)
				.add(new QuantityWeight(1.0, WeightUnit.KILOGRAM), WeightUnit.GRAM);

		assertEquals(2000.0, result.getValue(), EPS);
	}

	@Test
	void testAddition_ExplicitTarget_Pound() {
		QuantityWeight result = new QuantityWeight(1.0, WeightUnit.KILOGRAM)
				.add(new QuantityWeight(1.0, WeightUnit.KILOGRAM), WeightUnit.POUND);

		assertEquals(4.40924, result.getValue(), 0.01);
	}

	@Test
	void testAddition_WithZero() {
		QuantityWeight result = new QuantityWeight(5.0, WeightUnit.KILOGRAM)
				.add(new QuantityWeight(0.0, WeightUnit.GRAM));

		assertEquals(5.0, result.getValue(), EPS);
	}

	@Test
	void testAddition_NegativeValues() {
		QuantityWeight result = new QuantityWeight(5.0, WeightUnit.KILOGRAM)
				.add(new QuantityWeight(-2.0, WeightUnit.KILOGRAM));

		assertEquals(3.0, result.getValue(), EPS);
	}

	@Test
	void testAddition_Commutativity() {
		QuantityWeight a = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
		QuantityWeight b = new QuantityWeight(500.0, WeightUnit.GRAM);

		assertEquals(a.add(b), b.add(a));
	}

	@Test
	void testAddition_LargeValues() {
		QuantityWeight result = new QuantityWeight(1000.0, WeightUnit.KILOGRAM)
				.add(new QuantityWeight(500.0, WeightUnit.KILOGRAM));

		assertEquals(1500.0, result.getValue(), EPS);
	}

	// 19–23 Validation Tests
	@Test
	void testNullUnitInConstructor() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityWeight(1.0, null));
	}

	@Test
	void testNullTargetUnitInConvert() {
		assertThrows(IllegalArgumentException.class,
				() -> new QuantityWeight(1.0, WeightUnit.KILOGRAM).convertTo(null));
	}

	@Test
	void testNullOtherInAdd() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityWeight(1.0, WeightUnit.KILOGRAM).add(null));
	}

	@Test
	void testNullTargetUnitInAdd() {
		assertThrows(IllegalArgumentException.class,
				() -> new QuantityWeight(1.0, WeightUnit.KILOGRAM).add(new QuantityWeight(1.0, WeightUnit.GRAM), null));
	}

	@Test
	void testInvalidNaNValue() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityWeight(Double.NaN, WeightUnit.KILOGRAM));
	}

	// 24–28 Advanced Coverage

	@Test
	void testHashCodeConsistency() {
		QuantityWeight a = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
		QuantityWeight b = new QuantityWeight(1000.0, WeightUnit.GRAM);

		assertEquals(a.hashCode(), b.hashCode());
	}

	@Test
	void testEquals_SameReference() {
		QuantityWeight weight = new QuantityWeight(1.0, WeightUnit.KILOGRAM);

		assertEquals(weight, weight);
	}

	@Test
	void testEquals_NullComparison() {
		QuantityWeight weight = new QuantityWeight(1.0, WeightUnit.KILOGRAM);

		assertNotEquals(weight, null);
	}

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

	@Test
	void testPrecisionTolerance() {
		QuantityWeight result = new QuantityWeight(0.453592, WeightUnit.KILOGRAM).convertTo(WeightUnit.POUND);

		assertEquals(1.0, result.getValue(), 0.01);
	}
}