package com.quantitymeasurement.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.quantitymeasurement.core.LengthUnit;

/*
 * QuantityMeasurementEntityTest:
 * ------------------------------
 * This test class checks the basic behavior of QuantityMeasurementEntity.
 *
 * It verifies:
 * - entity creation for string result
 * - entity creation for quantity result
 * - entity creation for numeric result
 * - entity creation for error case
 * - equals behavior
 * - toString contains important values
 *
 * Since QuantityMeasurementEntity is a UC15 data holder class,
 * the tests focus on stored field values and object behavior.
 */
public class QuantityMeasurementEntityTest {

	/*
	 * Checks entity creation when the operation result is a string.
	 */
	@Test
	void testEntityCreation_WithStringResult() {
		QuantityModel<LengthUnit> first = new QuantityModel<>(1.0, LengthUnit.FEET);
		QuantityModel<LengthUnit> second = new QuantityModel<>(12.0, LengthUnit.INCH);

		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(first, second, "COMPARE", "Equal");

		assertEquals(1.0, entity.thisValue, 1e-6);
		assertEquals("FEET", entity.thisUnit);
		assertEquals("LengthUnit", entity.thisMeasurementType);

		assertEquals(12.0, entity.thatValue, 1e-6);
		assertEquals("INCH", entity.thatUnit);
		assertEquals("LengthUnit", entity.thatMeasurementType);

		assertEquals("COMPARE", entity.operation);
		assertEquals("Equal", entity.resultString);
		assertFalse(entity.isError);
		assertNull(entity.errorMessage);
	}

	/*
	 * Checks entity creation when the operation result is another quantity model.
	 */
	@Test
	void testEntityCreation_WithQuantityResult() {
		QuantityModel<LengthUnit> first = new QuantityModel<>(1.0, LengthUnit.FEET);
		QuantityModel<LengthUnit> second = new QuantityModel<>(12.0, LengthUnit.INCH);
		QuantityModel<LengthUnit> result = new QuantityModel<>(2.0, LengthUnit.FEET);

		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(first, second, "ADD", result);

		assertEquals("ADD", entity.operation);
		assertEquals(2.0, entity.resultValue, 1e-6);
		assertEquals("FEET", entity.resultUnit);
		assertEquals("LengthUnit", entity.resultMeasurementType);
		assertFalse(entity.isError);
	}

	/*
	 * Checks entity creation when the operation result is a numeric value.
	 */
	@Test
	void testEntityCreation_WithDoubleResult() {
		QuantityModel<LengthUnit> first = new QuantityModel<>(24.0, LengthUnit.INCH);
		QuantityModel<LengthUnit> second = new QuantityModel<>(2.0, LengthUnit.FEET);

		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(first, second, "DIVIDE", 1.0);

		assertEquals("DIVIDE", entity.operation);
		assertEquals(1.0, entity.resultValue, 1e-6);
		assertFalse(entity.isError);
	}

	/*
	 * Checks entity creation for an error case.
	 */
	@Test
	void testEntityCreation_WithError() {
		QuantityModel<LengthUnit> first = new QuantityModel<>(10.0, LengthUnit.FEET);
		QuantityModel<LengthUnit> second = new QuantityModel<>(0.0, LengthUnit.FEET);

		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(first, second, "DIVIDE",
				"Cannot divide by zero quantity", true);

		assertEquals("DIVIDE", entity.operation);
		assertTrue(entity.isError);
		assertEquals("Cannot divide by zero quantity", entity.errorMessage);
	}

	/*
	 * Checks equals behavior for two identical entities.
	 */
	@Test
	void testEntityEquals() {
		QuantityModel<LengthUnit> first1 = new QuantityModel<>(1.0, LengthUnit.FEET);
		QuantityModel<LengthUnit> second1 = new QuantityModel<>(12.0, LengthUnit.INCH);

		QuantityModel<LengthUnit> first2 = new QuantityModel<>(1.0, LengthUnit.FEET);
		QuantityModel<LengthUnit> second2 = new QuantityModel<>(12.0, LengthUnit.INCH);

		QuantityMeasurementEntity entity1 = new QuantityMeasurementEntity(first1, second1, "COMPARE", "Equal");
		QuantityMeasurementEntity entity2 = new QuantityMeasurementEntity(first2, second2, "COMPARE", "Equal");

		assertEquals(entity1, entity2);
	}

	/*
	 * Checks that toString contains useful field values.
	 */
	@Test
	void testEntityToString() {
		QuantityModel<LengthUnit> first = new QuantityModel<>(1.0, LengthUnit.FEET);
		QuantityModel<LengthUnit> second = new QuantityModel<>(12.0, LengthUnit.INCH);

		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(first, second, "COMPARE", "Equal");

		String text = entity.toString();

		assertTrue(text.contains("thisValue=1.0"));
		assertTrue(text.contains("thatValue=12.0"));
		assertTrue(text.contains("operation=COMPARE"));
		assertTrue(text.contains("resultString=Equal"));
	}
}