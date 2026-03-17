package com.app.quantitymeasurement.unit;

import java.util.function.Function;

/**
 * TemperatureUnit
 *
 * Enum representing supported temperature units in the Quantity Measurement
 * system.
 *
 * Temperature conversion is non-linear, unlike length, weight, or volume.
 * Therefore, each unit stores functional logic to: - convert a value to the
 * base unit (Celsius) - convert a value from the base unit (Celsius) back to
 * the target unit
 *
 * Supported units: - CELSIUS - FAHRENHEIT - KELVIN
 *
 * Temperature units do not support arithmetic operations such as: - Addition -
 * Subtraction - Division
 *
 * Only comparison and conversion operations are valid for temperature.
 *
 * @author Developer
 * @version 16.0
 * @since 1.0
 */
public enum TemperatureUnit implements IMeasurable {

	CELSIUS("Celsius", c -> c, c -> c),
	FAHRENHEIT("Fahrenheit", f -> (f - 32.0) * 5.0 / 9.0, c -> (c * 9.0 / 5.0) + 32.0),
	KELVIN("Kelvin", k -> k - 273.15, c -> c + 273.15);

	private final String unitName;
	private final Function<Double, Double> toBaseCelsius;
	private final Function<Double, Double> fromBaseCelsius;

	/**
	 * Constructs a TemperatureUnit with conversion functions.
	 *
	 * @param unitName        display name of the unit
	 * @param toBaseCelsius   function to convert the current unit to Celsius
	 * @param fromBaseCelsius function to convert Celsius to the current unit
	 */
	TemperatureUnit(String unitName, Function<Double, Double> toBaseCelsius, Function<Double, Double> fromBaseCelsius) {
		this.unitName = unitName;
		this.toBaseCelsius = toBaseCelsius;
		this.fromBaseCelsius = fromBaseCelsius;
	}

	/**
	 * Returns the conversion factor for this unit.
	 *
	 * Since temperature conversion is non-linear, this method returns 1.0 and
	 * actual conversion is handled by the functional conversion methods.
	 *
	 * @return 1.0 for temperature units
	 */
	@Override
	public double getConversionFactor() {
		return 1.0;
	}

	/**
	 * Converts the given temperature value to base unit Celsius.
	 *
	 * @param value temperature in current unit
	 * @return equivalent value in Celsius
	 */
	@Override
	public double convertToBaseUnit(double value) {
		return toBaseCelsius.apply(value);
	}

	/**
	 * Converts the given Celsius value to the current unit.
	 *
	 * @param baseValue temperature in Celsius
	 * @return equivalent value in the current temperature unit
	 */
	@Override
	public double convertFromBaseUnit(double baseValue) {
		return fromBaseCelsius.apply(baseValue);
	}

	/**
	 * Returns the display name of the unit.
	 *
	 * @return unit name
	 */
	@Override
	public String getUnitName() {
		return name();
	}

	/**
	 * Returns the measurement type of this unit.
	 *
	 * @return measurement type name
	 */
	@Override
	public String getMeasurementType() {
		return this.getClass().getSimpleName();
	}

	/**
	 * Returns the unit instance corresponding to the given enum name.
	 *
	 * @param unitName enum constant name
	 * @return measurable unit instance
	 */
	@Override
	public IMeasurable getUnitInstance(String unitName) {
		return TemperatureUnit.valueOf(unitName);
	}

	/**
	 * Temperature units do not support arithmetic operations.
	 *
	 * @return false always
	 */
	@Override
	public boolean supportsArithmetic() {
		return false;
	}

	/**
	 * Throws exception because temperature does not support arithmetic operations.
	 *
	 * @param operation operation being attempted
	 * @throws UnsupportedOperationException always for temperature arithmetic
	 */
	@Override
	public void validateOperationSupport(String operation) {
		throw new UnsupportedOperationException("Temperature does not support arithmetic operation: " + operation);
	}
}