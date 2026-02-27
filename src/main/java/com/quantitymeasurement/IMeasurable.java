package com.quantitymeasurement;

public interface IMeasurable {

	double getConversionFactor();

	double convertToBaseUnit(double value);

	double convertFromBaseUnit(double baseValue);

	String getUnitName();

	// Optional capability method (default: allowed)
	default boolean supportsArithmetic() {
		SupportsArithmetic supportsArithmetic = () -> true; // default: supports
		return supportsArithmetic.isSupported();
	}

	// Optional validation hook (default: does nothing)
	default void validateOperationSupport(String operation) {
		// default: all operations supported
	}
}