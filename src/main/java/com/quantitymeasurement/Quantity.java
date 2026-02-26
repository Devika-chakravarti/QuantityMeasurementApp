package com.quantitymeasurement;

import java.util.Objects;

public final class Quantity<U extends IMeasurable> {

	private static final double EPSILON = 1e-6;

	private final double value;
	private final U unit;

	public Quantity(double value, U unit) {
		if (unit == null)
			throw new IllegalArgumentException("Unit cannot be null");
		if (Double.isNaN(value) || Double.isInfinite(value))
			throw new IllegalArgumentException("Invalid numeric value");

		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public U getUnit() {
		return unit;
	}

	private double toBaseUnit() {
		return unit.convertToBaseUnit(value);
	}

	public Quantity<U> convertTo(U targetUnit) {
		if (targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null");

		double baseValue = toBaseUnit();
		double converted = targetUnit.convertFromBaseUnit(baseValue);

		return new Quantity<>(round(converted), targetUnit);
	}

	// ---------------- UC13: Centralized Operation Enum ----------------

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

	// ---------------- UC13: Centralized Validation ----------------

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

	// ---------------- UC13: Core Base Arithmetic Helper ----------------

	private double performBaseArithmetic(Quantity<U> other, ArithmeticOperation operation) {
		double aBase = this.toBaseUnit();
		double bBase = other.toBaseUnit();
		return operation.compute(aBase, bBase);
	}

	public Quantity<U> add(Quantity<U> other) {
		return add(other, this.unit);
	}

	public Quantity<U> add(Quantity<U> other, U targetUnit) {
		validateArithmeticOperands(other, targetUnit, true);

		double baseResult = performBaseArithmetic(other, ArithmeticOperation.ADD);
		double converted = targetUnit.convertFromBaseUnit(baseResult);

		return new Quantity<>(round(converted), targetUnit);
	}

	public Quantity<U> subtract(Quantity<U> other) {
		return subtract(other, this.unit);
	}

	public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
		validateArithmeticOperands(other, targetUnit, true);

		double baseResult = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
		double converted = targetUnit.convertFromBaseUnit(baseResult);

		return new Quantity<>(round(converted), targetUnit);
	}

	public double divide(Quantity<U> other) {
		validateArithmeticOperands(other, null, false);

		return performBaseArithmetic(other, ArithmeticOperation.DIVIDE); // dimensionless scalar
	}

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

	@Override
	public int hashCode() {
		double base = round(toBaseUnit());
		return Objects.hash(base, unit.getClass());
	}

	@Override
	public String toString() {
		return "Quantity(" + value + ", " + unit.getUnitName() + ")";
	}

	private double round(double value) {
		return Math.round(value * 100000.0) / 100000.0;
	}
}