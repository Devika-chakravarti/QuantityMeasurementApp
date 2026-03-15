package com.quantitymeasurement.core;

/*
 * SupportsArithmetic:
 * -------------------
 * This is a functional interface used to represent whether
 * arithmetic operations are supported for a measurable unit.
 *
 * Why this interface is needed:
 * -----------------------------
 * In the existing flow:
 * - IMeasurable uses this inside the default supportsArithmetic() method
 * - TemperatureUnit uses this to return false because temperature
 *   does not support arithmetic operations like add, subtract, divide
 *
 * Since only one abstract method is present, this interface can be
 * used with lambda expressions.
 */
@FunctionalInterface
public interface SupportsArithmetic {

	/*
	 * Returns true if arithmetic is supported for the unit, otherwise returns
	 * false.
	 */
	boolean isSupported();
}