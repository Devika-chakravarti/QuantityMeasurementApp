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

	public Quantity<U> add(Quantity<U> other) {
		return add(other, this.unit);
	}

	public Quantity<U> add(Quantity<U> other, U targetUnit) {
		validateOtherAndTarget(other, targetUnit);

		double sumBase = this.toBaseUnit() + other.toBaseUnit();
		double result = targetUnit.convertFromBaseUnit(sumBase);

		return new Quantity<>(round(result), targetUnit);
	}

	public Quantity<U> subtract(Quantity<U> other) {
		return subtract(other, this.unit);
	}

	public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
		validateOtherAndTarget(other, targetUnit);

		double diffBase = this.toBaseUnit() - other.toBaseUnit();
		double result = targetUnit.convertFromBaseUnit(diffBase);

		return new Quantity<>(round(result), targetUnit);
	}

	public double divide(Quantity<U> other) {
		if (other == null)
			throw new IllegalArgumentException("Other quantity cannot be null");

		validateSameCategory(other);

		double divisorBase = other.toBaseUnit();
		if (Math.abs(divisorBase) < EPSILON)
			throw new ArithmeticException("Cannot divide by zero quantity");

		double dividendBase = this.toBaseUnit();
		return dividendBase / divisorBase;
	}

	private void validateOtherAndTarget(Quantity<U> other, U targetUnit) {
		if (other == null)
			throw new IllegalArgumentException("Other quantity cannot be null");
		if (targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null");

		validateSameCategory(other);
	}

	private void validateSameCategory(Quantity<?> other) {
		if (other.unit == null)
			throw new IllegalArgumentException("Unit cannot be null");
		if (!this.unit.getClass().equals(other.unit.getClass()))
			throw new IllegalArgumentException("Cross-category operation is not allowed");
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		Quantity<?> other = (Quantity<?>) obj;

		// prevent cross-category comparison
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