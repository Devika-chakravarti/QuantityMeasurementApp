package com.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TemperatureUnitTest {

	private static final double EPSILON = 1e-6;

	// ---------------- Equality ----------------

	@Test
	void testTemperatureEquality_CelsiusToFahrenheit_0C_32F() {
		assertTrue(
				new Quantity<>(0.0, TemperatureUnit.CELSIUS).equals(new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT)));
	}

	@Test
	void testTemperatureEquality_FahrenheitToCelsius_212F_100C() {
		assertTrue(new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT)
				.equals(new Quantity<>(100.0, TemperatureUnit.CELSIUS)));
	}

	@Test
	void testTemperatureEquality_CelsiusToKelvin_0C_273_15K() {
		assertTrue(new Quantity<>(0.0, TemperatureUnit.CELSIUS).equals(new Quantity<>(273.15, TemperatureUnit.KELVIN)));
	}

	@Test
	void testTemperatureEquality_Negative40_EqualPoint() {
		assertTrue(new Quantity<>(-40.0, TemperatureUnit.CELSIUS)
				.equals(new Quantity<>(-40.0, TemperatureUnit.FAHRENHEIT)));
	}

	// ---------------- Conversion ----------------

	@Test
	void testTemperatureConversion_CelsiusToFahrenheit() {
		Quantity<TemperatureUnit> result = new Quantity<>(100.0, TemperatureUnit.CELSIUS)
				.convertTo(TemperatureUnit.FAHRENHEIT);

		assertEquals(212.0, result.getValue(), EPSILON);
		assertEquals(TemperatureUnit.FAHRENHEIT, result.getUnit());
	}

	@Test
	void testTemperatureConversion_FahrenheitToCelsius() {
		Quantity<TemperatureUnit> result = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT)
				.convertTo(TemperatureUnit.CELSIUS);

		assertEquals(0.0, result.getValue(), EPSILON);
		assertEquals(TemperatureUnit.CELSIUS, result.getUnit());
	}

	@Test
	void testTemperatureConversion_KelvinToCelsius() {
		Quantity<TemperatureUnit> result = new Quantity<>(273.15, TemperatureUnit.KELVIN)
				.convertTo(TemperatureUnit.CELSIUS);

		assertEquals(0.0, result.getValue(), EPSILON);
	}

	@Test
	void testTemperatureConversion_CelsiusToKelvin() {
		Quantity<TemperatureUnit> result = new Quantity<>(0.0, TemperatureUnit.CELSIUS)
				.convertTo(TemperatureUnit.KELVIN);

		assertEquals(273.15, result.getValue(), EPSILON);
	}

	@Test
	void testTemperatureConversion_RoundTrip() {
		Quantity<TemperatureUnit> original = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
		Quantity<TemperatureUnit> roundTrip = original.convertTo(TemperatureUnit.FAHRENHEIT)
				.convertTo(TemperatureUnit.CELSIUS);

		assertEquals(original.getValue(), roundTrip.getValue(), EPSILON);
	}

	// ---------------- Unsupported Operations ----------------

	@Test
	void testTemperatureUnsupported_Add_Throws() {
		assertThrows(UnsupportedOperationException.class, () -> new Quantity<>(100.0, TemperatureUnit.CELSIUS)
				.add(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
	}

	@Test
	void testTemperatureUnsupported_Subtract_Throws() {
		assertThrows(UnsupportedOperationException.class, () -> new Quantity<>(100.0, TemperatureUnit.CELSIUS)
				.subtract(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
	}

	@Test
	void testTemperatureUnsupported_Divide_Throws() {
		assertThrows(UnsupportedOperationException.class, () -> new Quantity<>(100.0, TemperatureUnit.CELSIUS)
				.divide(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
	}

	// ---------------- Cross-category prevention ----------------

	@Test
	void testTemperatureVsLength_IncompatibleEqualsFalse() {
		assertFalse(new Quantity<>(100.0, TemperatureUnit.CELSIUS).equals(new Quantity<>(100.0, LengthUnit.FEET)));
	}
}