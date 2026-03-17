package com.app.quantitymeasurement.quantity;

import java.util.Objects;

import com.app.quantitymeasurement.unit.IMeasurable;

/*
 * Quantity:
 * ---------
 * This is a generic quantity class that works with any unit
 * that implements IMeasurable.
 *
 * Purpose of this class:
 * ----------------------
 * - store value and unit together
 * - support comparison across same measurement category
 * - support conversion
 * - support addition
 * - support subtraction
 * - support division
 *
 * This is an old UC14 file.
 * It is kept same because UC15 mainly introduces layered architecture
 * and old behavior should remain preserved unless explicitly changed.
 */
public final class Quantity<U extends IMeasurable> {

	private static final double EPSILON = 1e-6;

	private final double value;
	private final U unit;

	/*
	 * Constructor: ------------ Creates a Quantity object using value and unit.
	 *
	 * Validation: - unit cannot be null - value cannot be NaN or infinite
	 */
	public Quantity(double value, U unit) {
		if (unit == null)
			throw new IllegalArgumentException("Unit cannot be null");
		if (Double.isNaN(value) || Double.isInfinite(value))
			throw new IllegalArgumentException("Invalid numeric value");

		this.value = value;
		this.unit = unit;
	}

	/*
	 * Returns the stored value.
	 */
	public double getValue() {
		return value;
	}

	/*
	 * Returns the stored unit.
	 */
	public U getUnit() {
		return unit;
	}

	/*
	 * Converts the current quantity into base unit.
	 */
	private double toBaseUnit() {
		return unit.convertToBaseUnit(value);
	}

	/*
	 * Converts the current quantity into the given target unit.
	 */
	public Quantity<U> convertTo(U targetUnit) {
		if (targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null");

		double baseValue = toBaseUnit();
		double converted = targetUnit.convertFromBaseUnit(baseValue);

		return new Quantity<>(round(converted), targetUnit);
	}

	/*
	 * ArithmeticOperation: -------------------- Internal enum used to centralize
	 * arithmetic logic.
	 */
	private enum ArithmeticOperation {
		ADD {
			@Override
			double compute(double a, double b) {
				return a + b;
			}
		},
		SUBTRACT {
			@Override
			double compute(double a, double b) {
				return a - b;
			}
		},
		DIVIDE {
			@Override
			double compute(double a, double b) {
				if (Math.abs(b) < EPSILON)
					throw new ArithmeticException("Cannot divide by zero quantity");
				return a / b;
			}
		};

		abstract double compute(double a, double b);
	}

	/*
	 * Validates operands before arithmetic operations.
	 *
	 * Checks: - other quantity should not be null - both units should not be null -
	 * both quantities should belong to same category - numeric values should be
	 * valid - target unit should not be null when required
	 */
	private void validateArithmeticOperands(Quantity<?> other, U targetUnit, boolean targetUnitRequired) {
		if (other == null)
			throw new IllegalArgumentException("Other quantity cannot be null");

		if (this.unit == null || other.unit == null)
			throw new IllegalArgumentException("Unit cannot be null");

		if (!this.unit.getClass().equals(other.unit.getClass()))
			throw new IllegalArgumentException("Cross-category operation is not allowed");

		if (Double.isNaN(this.value) || Double.isInfinite(this.value) || Double.isNaN(other.value)
				|| Double.isInfinite(other.value))
			throw new IllegalArgumentException("Invalid numeric value");

		if (targetUnitRequired && targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null");
	}

	/*
	 * Validates whether this unit category supports the given arithmetic operation.
	 *
	 * Example: Temperature throws UnsupportedOperationException here.
	 */
	private void validateOperationSupport(Quantity<U> other, ArithmeticOperation operation) {
		this.unit.validateOperationSupport(operation.name());
		other.unit.validateOperationSupport(operation.name());
	}

	/*
	 * Performs arithmetic after converting both quantities into base unit.
	 */
	private double performBaseArithmetic(Quantity<U> other, ArithmeticOperation operation) {
		double aBase = this.toBaseUnit();
		double bBase = other.toBaseUnit();
		return operation.compute(aBase, bBase);
	}

	/*
	 * Adds two quantities and returns result in this unit.
	 */
	public Quantity<U> add(Quantity<U> other) {
		return add(other, this.unit);
	}

	/*
	 * Adds two quantities and returns result in target unit.
	 */
	public Quantity<U> add(Quantity<U> other, U targetUnit) {
		validateArithmeticOperands(other, targetUnit, true);
		validateOperationSupport(other, ArithmeticOperation.ADD);

		double baseResult = performBaseArithmetic(other, ArithmeticOperation.ADD);
		double converted = targetUnit.convertFromBaseUnit(baseResult);

		return new Quantity<>(round(converted), targetUnit);
	}

	/*
	 * Subtracts second quantity from first and returns result in this unit.
	 */
	public Quantity<U> subtract(Quantity<U> other) {
		return subtract(other, this.unit);
	}

	/*
	 * Subtracts second quantity from first and returns result in target unit.
	 */
	public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
		validateArithmeticOperands(other, targetUnit, true);
		validateOperationSupport(other, ArithmeticOperation.SUBTRACT);

		double baseResult = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
		double converted = targetUnit.convertFromBaseUnit(baseResult);

		return new Quantity<>(round(converted), targetUnit);
	}

	/*
	 * Divides first quantity by second quantity and returns scalar result.
	 */
	public double divide(Quantity<U> other) {
		validateArithmeticOperands(other, null, false);
		validateOperationSupport(other, ArithmeticOperation.DIVIDE);

		return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
	}

	/*
	 * equals: ------- Two quantities are equal if: - they are of same class - their
	 * unit categories are same - their base-unit values differ within tolerance
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		Quantity<?> other = (Quantity<?>) obj;

		if (!this.unit.getClass().equals(other.unit.getClass()))
			return false;

		return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPSILON;
	}

	/*
	 * hashCode: --------- Uses rounded base-unit value and unit category.
	 */
	@Override
	public int hashCode() {
		double base = round(toBaseUnit());
		return Objects.hash(base, unit.getClass());
	}

	/*
	 * toString: --------- Returns quantity in readable format.
	 */
	@Override
	public String toString() {
		return "Quantity(" + value + ", " + unit.getUnitName() + ")";
	}

	/*
	 * Rounds value to 5 decimal places.
	 */
	private double round(double value) {
		return Math.round(value * 100000.0) / 100000.0;
	}
}