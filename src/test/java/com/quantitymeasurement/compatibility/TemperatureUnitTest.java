package com.quantitymeasurement.compatibility;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.quantitymeasurement.core.LengthUnit;
import com.quantitymeasurement.core.Quantity;
import com.quantitymeasurement.core.TemperatureUnit;

/*
 * TemperatureUnitTest:
 * --------------------
 * This test class checks temperature behavior in the generic Quantity class.
 *
 * It verifies:
 * - equality across temperature units
 * - conversion across temperature units
 * - round-trip conversion
 * - unsupported arithmetic operations
 * - cross-category equality prevention
 */
public class TemperatureUnitTest {

	private static final double EPSILON = 1e-6;

	/*
	 * EQUALITY -------- Checks 0 Celsius equals 32 Fahrenheit.
	 */
	@Test
	void testTemperatureEquality_CelsiusToFahrenheit_0C_32F() {
		assertTrue(
				new Quantity<>(0.0, TemperatureUnit.CELSIUS).equals(new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT)));
	}

	/*
	 * EQUALITY -------- Checks 212 Fahrenheit equals 100 Celsius.
	 */
	@Test
	void testTemperatureEquality_FahrenheitToCelsius_212F_100C() {
		assertTrue(new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT)
				.equals(new Quantity<>(100.0, TemperatureUnit.CELSIUS)));
	}

	/*
	 * EQUALITY -------- Checks 0 Celsius equals 273.15 Kelvin.
	 */
	@Test
	void testTemperatureEquality_CelsiusToKelvin_0C_273_15K() {
		assertTrue(new Quantity<>(0.0, TemperatureUnit.CELSIUS).equals(new Quantity<>(273.15, TemperatureUnit.KELVIN)));
	}

	/*
	 * EQUALITY -------- Checks the special equal point at -40.
	 */
	@Test
	void testTemperatureEquality_Negative40_EqualPoint() {
		assertTrue(new Quantity<>(-40.0, TemperatureUnit.CELSIUS)
				.equals(new Quantity<>(-40.0, TemperatureUnit.FAHRENHEIT)));
	}

	/*
	 * CONVERSION ---------- Checks Celsius to Fahrenheit conversion.
	 */
	@Test
	void testTemperatureConversion_CelsiusToFahrenheit() {
		Quantity<TemperatureUnit> result = new Quantity<>(100.0, TemperatureUnit.CELSIUS)
				.convertTo(TemperatureUnit.FAHRENHEIT);

		assertEquals(212.0, result.getValue(), EPSILON);
		assertEquals(TemperatureUnit.FAHRENHEIT, result.getUnit());
	}

	/*
	 * CONVERSION ---------- Checks Fahrenheit to Celsius conversion.
	 */
	@Test
	void testTemperatureConversion_FahrenheitToCelsius() {
		Quantity<TemperatureUnit> result = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT)
				.convertTo(TemperatureUnit.CELSIUS);

		assertEquals(0.0, result.getValue(), EPSILON);
		assertEquals(TemperatureUnit.CELSIUS, result.getUnit());
	}

	/*
	 * CONVERSION ---------- Checks Kelvin to Celsius conversion.
	 */
	@Test
	void testTemperatureConversion_KelvinToCelsius() {
		Quantity<TemperatureUnit> result = new Quantity<>(273.15, TemperatureUnit.KELVIN)
				.convertTo(TemperatureUnit.CELSIUS);

		assertEquals(0.0, result.getValue(), EPSILON);
	}

	/*
	 * CONVERSION ---------- Checks Celsius to Kelvin conversion.
	 */
	@Test
	void testTemperatureConversion_CelsiusToKelvin() {
		Quantity<TemperatureUnit> result = new Quantity<>(0.0, TemperatureUnit.CELSIUS)
				.convertTo(TemperatureUnit.KELVIN);

		assertEquals(273.15, result.getValue(), EPSILON);
	}

	/*
	 * CONVERSION ---------- Checks round-trip conversion.
	 */
	@Test
	void testTemperatureConversion_RoundTrip() {
		Quantity<TemperatureUnit> original = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
		Quantity<TemperatureUnit> roundTrip = original.convertTo(TemperatureUnit.FAHRENHEIT)
				.convertTo(TemperatureUnit.CELSIUS);

		assertEquals(original.getValue(), roundTrip.getValue(), EPSILON);
	}

	/*
	 * UNSUPPORTED OPERATIONS ---------------------- Temperature addition should
	 * throw UnsupportedOperationException.
	 */
	@Test
	void testTemperatureUnsupported_Add_Throws() {
		assertThrows(UnsupportedOperationException.class, () -> new Quantity<>(100.0, TemperatureUnit.CELSIUS)
				.add(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
	}

	/*
	 * UNSUPPORTED OPERATIONS ---------------------- Temperature subtraction should
	 * throw UnsupportedOperationException.
	 */
	@Test
	void testTemperatureUnsupported_Subtract_Throws() {
		assertThrows(UnsupportedOperationException.class, () -> new Quantity<>(100.0, TemperatureUnit.CELSIUS)
				.subtract(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
	}

	/*
	 * UNSUPPORTED OPERATIONS ---------------------- Temperature division should
	 * throw UnsupportedOperationException.
	 */
	@Test
	void testTemperatureUnsupported_Divide_Throws() {
		assertThrows(UnsupportedOperationException.class, () -> new Quantity<>(100.0, TemperatureUnit.CELSIUS)
				.divide(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
	}

	/*
	 * CROSS-CATEGORY PREVENTION ------------------------- Temperature and Length
	 * should not be equal.
	 */
	@Test
	void testTemperatureVsLength_IncompatibleEqualsFalse() {
		assertFalse(new Quantity<>(100.0, TemperatureUnit.CELSIUS).equals(new Quantity<>(100.0, LengthUnit.FEET)));
	}
}