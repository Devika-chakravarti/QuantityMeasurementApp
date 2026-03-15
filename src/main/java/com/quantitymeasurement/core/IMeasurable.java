package com.quantitymeasurement.core;

public interface IMeasurable {

	double getConversionFactor();

	double convertToBaseUnit(double value);

	double convertFromBaseUnit(double baseValue);

	String getUnitName();

	/*
	 * UC15: Returns the measurement type of the unit.
	 */
	String getMeasurementType();

	/*
	 * UC15: Returns the unit instance based on the given unit name.
	 */
	IMeasurable getUnitInstance(String unitName);

	/*
	 * Old UC14 behavior kept same: By default, arithmetic is supported.
	 */
	default boolean supportsArithmetic() {
		SupportsArithmetic supportsArithmetic = () -> true;
		return supportsArithmetic.isSupported();
	}

	/*
	 * Old UC14 behavior kept same: By default, no restriction is applied.
	 */
	default void validateOperationSupport(String operation) {
		// default: all operations supported
	}
}