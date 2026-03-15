package com.quantitymeasurement.core;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable {

	CELSIUS("Celsius", c -> c, c -> c),
	FAHRENHEIT("Fahrenheit", f -> (f - 32.0) * 5.0 / 9.0, c -> (c * 9.0 / 5.0) + 32.0),
	KELVIN("Kelvin", k -> k - 273.15, c -> c + 273.15);

	private final String unitName;
	private final Function<Double, Double> toBaseCelsius;
	private final Function<Double, Double> fromBaseCelsius;

	TemperatureUnit(String unitName, Function<Double, Double> toBaseCelsius, Function<Double, Double> fromBaseCelsius) {
		this.unitName = unitName;
		this.toBaseCelsius = toBaseCelsius;
		this.fromBaseCelsius = fromBaseCelsius;
	}

	@Override
	public double getConversionFactor() {
		return 1.0;
	}

	@Override
	public double convertToBaseUnit(double value) {
		return toBaseCelsius.apply(value);
	}

	@Override
	public double convertFromBaseUnit(double baseValue) {
		return fromBaseCelsius.apply(baseValue);
	}

	@Override
	public String getUnitName() {
		return unitName;
	}

	/*
	 * UC15: Returns the measurement type of this unit.
	 */
	@Override
	public String getMeasurementType() {
		return this.getClass().getSimpleName();
	}

	/*
	 * UC15: Returns the unit instance from the given unit name.
	 */
	@Override
	public IMeasurable getUnitInstance(String unitName) {
		return TemperatureUnit.valueOf(unitName);
	}

	/*
	 * Old UC14 behavior kept same: Temperature does not support arithmetic
	 * operations.
	 */
	@Override
	public boolean supportsArithmetic() {
		SupportsArithmetic supportsArithmetic = () -> false;
		return supportsArithmetic.isSupported();
	}

	/*
	 * Old UC14 behavior kept same: Temperature supports only equality and
	 * conversion here.
	 */
	@Override
	public void validateOperationSupport(String operation) {
		throw new UnsupportedOperationException("Temperature does not support arithmetic operation: " + operation);
	}
}