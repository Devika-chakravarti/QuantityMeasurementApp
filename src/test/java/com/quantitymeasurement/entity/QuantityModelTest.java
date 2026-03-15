package com.quantitymeasurement.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.quantitymeasurement.core.LengthUnit;
import com.quantitymeasurement.core.TemperatureUnit;
import com.quantitymeasurement.core.WeightUnit;

/*
 * QuantityModelTest:
 * ------------------
 * This test class checks the basic behavior of QuantityModel.
 *
 * It verifies:
 * - object creation
 * - stored value
 * - stored unit
 * - support for different measurable unit types
 * - toString output
 *
 * QuantityModel is a UC15 POJO-style class, so the tests mainly
 * focus on data holding behavior.
 */
public class QuantityModelTest {

	/*
	 * Checks that QuantityModel stores value and unit correctly for a length
	 * quantity.
	 */
	@Test
	void testQuantityModel_Creation_Length() {
		QuantityModel<LengthUnit> model = new QuantityModel<>(10.0, LengthUnit.FEET);

		assertEquals(10.0, model.getValue(), 1e-6);
		assertEquals(LengthUnit.FEET, model.getUnit());
	}

	/*
	 * Checks that QuantityModel stores value and unit correctly for a weight
	 * quantity.
	 */
	@Test
	void testQuantityModel_Creation_Weight() {
		QuantityModel<WeightUnit> model = new QuantityModel<>(5.0, WeightUnit.KILOGRAM);

		assertEquals(5.0, model.getValue(), 1e-6);
		assertEquals(WeightUnit.KILOGRAM, model.getUnit());
	}

	/*
	 * Checks that QuantityModel stores value and unit correctly for a temperature
	 * quantity.
	 */
	@Test
	void testQuantityModel_Creation_Temperature() {
		QuantityModel<TemperatureUnit> model = new QuantityModel<>(100.0, TemperatureUnit.CELSIUS);

		assertEquals(100.0, model.getValue(), 1e-6);
		assertEquals(TemperatureUnit.CELSIUS, model.getUnit());
	}

	/*
	 * Checks the toString output format.
	 */
	@Test
	void testQuantityModel_ToString() {
		QuantityModel<LengthUnit> model = new QuantityModel<>(12.0, LengthUnit.INCH);

		assertEquals("QuantityModel [value=12.0, unit=INCH]", model.toString());
	}
}