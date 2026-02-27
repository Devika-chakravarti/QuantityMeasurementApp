package com.quantitymeasurement;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable {

	CELSIUS("Celsius", c -> c, // C -> base(C)
			c -> c// base(C) -> C
	), FAHRENHEIT("Fahrenheit", f -> (f - 32.0) * 5.0 / 9.0, // F -> C
			c -> (c * 9.0 / 5.0) + 32.0// C -> F
	), KELVIN("Kelvin", k -> k - 273.15, // K -> C
			c -> c + 273.15// C -> K
	);

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
		return 1.0; // temperature is non-linear; factor not meaningful here
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

	@Override
	public boolean supportsArithmetic() {
		SupportsArithmetic supportsArithmetic = () -> false;
		return supportsArithmetic.isSupported();
	}

	@Override
	public void validateOperationSupport(String operation) {
		// Temperature supports equality & conversion only in this UC
		throw new UnsupportedOperationException("Temperature does not support arithmetic operation: " + operation);
	}
}