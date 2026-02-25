package com.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TargetUnitSpecificationTest {

	private static final double EPS = 0.001;

	@Test
	void testAddition_ExplicitTargetUnit_Feet() {
		QuantityLength result = new QuantityLength(1.0, LengthUnit.FEET).add(new QuantityLength(12.0, LengthUnit.INCH),
				LengthUnit.FEET);

		assertEquals(2.0, result.getValue(), EPS);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	void testAddition_ExplicitTargetUnit_Inches() {
		QuantityLength result = new QuantityLength(1.0, LengthUnit.FEET).add(new QuantityLength(12.0, LengthUnit.INCH),
				LengthUnit.INCH);

		assertEquals(24.0, result.getValue(), EPS);
		assertEquals(LengthUnit.INCH, result.getUnit());
	}

	@Test
	void testAddition_ExplicitTargetUnit_Yards() {
		QuantityLength result = new QuantityLength(1.0, LengthUnit.FEET).add(new QuantityLength(12.0, LengthUnit.INCH),
				LengthUnit.YARDS);

		assertEquals(0.667, result.getValue(), EPS);
		assertEquals(LengthUnit.YARDS, result.getUnit());
	}

	@Test
	void testAddition_ExplicitTargetUnit_Centimeters() {
		QuantityLength result = new QuantityLength(1.0, LengthUnit.INCH).add(new QuantityLength(1.0, LengthUnit.INCH),
				LengthUnit.CENTIMETERS);

		assertEquals(5.08, result.getValue(), 0.01);
		assertEquals(LengthUnit.CENTIMETERS, result.getUnit());
	}

	@Test
	void testAddition_ExplicitTargetUnit_SameAsFirstOperand() {
		QuantityLength result = new QuantityLength(2.0, LengthUnit.YARDS).add(new QuantityLength(3.0, LengthUnit.FEET),
				LengthUnit.YARDS);

		assertEquals(3.0, result.getValue(), EPS);
		assertEquals(LengthUnit.YARDS, result.getUnit());
	}

	@Test
	void testAddition_ExplicitTargetUnit_SameAsSecondOperand() {
		QuantityLength result = new QuantityLength(2.0, LengthUnit.YARDS).add(new QuantityLength(3.0, LengthUnit.FEET),
				LengthUnit.FEET);

		assertEquals(9.0, result.getValue(), EPS);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	void testAddition_ExplicitTargetUnit_Commutativity() {
		QuantityLength result1 = new QuantityLength(1.0, LengthUnit.FEET).add(new QuantityLength(12.0, LengthUnit.INCH),
				LengthUnit.YARDS);

		QuantityLength result2 = new QuantityLength(12.0, LengthUnit.INCH).add(new QuantityLength(1.0, LengthUnit.FEET),
				LengthUnit.YARDS);

		assertEquals(result1, result2);
	}

	@Test
	void testAddition_ExplicitTargetUnit_WithZero() {
		QuantityLength result = new QuantityLength(5.0, LengthUnit.FEET).add(new QuantityLength(0.0, LengthUnit.INCH),
				LengthUnit.YARDS);

		assertEquals(1.667, result.getValue(), EPS);
		assertEquals(LengthUnit.YARDS, result.getUnit());
	}

	@Test
	void testAddition_ExplicitTargetUnit_NegativeValues() {
		QuantityLength result = new QuantityLength(5.0, LengthUnit.FEET).add(new QuantityLength(-2.0, LengthUnit.FEET),
				LengthUnit.INCH);

		assertEquals(36.0, result.getValue(), EPS);
		assertEquals(LengthUnit.INCH, result.getUnit());
	}

	@Test
	void testAddition_ExplicitTargetUnit_NullTargetUnit() {
		assertThrows(IllegalArgumentException.class,
				() -> new QuantityLength(1.0, LengthUnit.FEET).add(new QuantityLength(12.0, LengthUnit.INCH), null));
	}

	@Test
	void testAddition_ExplicitTargetUnit_LargeToSmallScale() {
		QuantityLength result = new QuantityLength(1000.0, LengthUnit.FEET)
				.add(new QuantityLength(500.0, LengthUnit.FEET), LengthUnit.INCH);

		assertEquals(18000.0, result.getValue(), EPS);
	}

	@Test
	void testAddition_ExplicitTargetUnit_SmallToLargeScale() {
		QuantityLength result = new QuantityLength(12.0, LengthUnit.INCH).add(new QuantityLength(12.0, LengthUnit.INCH),
				LengthUnit.YARDS);

		assertEquals(0.667, result.getValue(), EPS);
	}

	@Test
	void testAddition_ExplicitTargetUnit_AllUnitCombinations() {
		for (LengthUnit unit1 : LengthUnit.values()) {
			for (LengthUnit unit2 : LengthUnit.values()) {
				for (LengthUnit target : LengthUnit.values()) {

					QuantityLength q1 = new QuantityLength(1.0, unit1);
					QuantityLength q2 = new QuantityLength(2.0, unit2);

					QuantityLength result = q1.add(q2, target);

					double expected = q1.convertTo(target).getValue() + q2.convertTo(target).getValue();

					assertEquals(expected, result.getValue(), EPS);
					assertEquals(target, result.getUnit());
				}
			}
		}
	}

	@Test
	void testAddition_ExplicitTargetUnit_PrecisionTolerance() {
		QuantityLength result = new QuantityLength(2.54, LengthUnit.CENTIMETERS)
				.add(new QuantityLength(1.0, LengthUnit.INCH), LengthUnit.CENTIMETERS);

		assertEquals(5.08, result.getValue(), 0.01);
	}
}