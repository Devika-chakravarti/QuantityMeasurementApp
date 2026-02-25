package com.quantitymeasurement;

import java.util.Objects;

/**
 * Immutable value object representing weight measurement.
 */
public final class QuantityWeight {

	private static final double EPSILON = 1e-6;

	private final double value;
	private final WeightUnit unit;

	public QuantityWeight(double value, WeightUnit unit) {
		validate(value, unit);
		this.value = value;
		this.unit = unit;
	}

	private static void validate(double value, WeightUnit unit) {
		if (!Double.isFinite(value))
			throw new IllegalArgumentException("Value must be finite");
		if (unit == null)
			throw new IllegalArgumentException("Unit cannot be null");
	}

	private double toBaseUnit() {
		return unit.convertToBaseUnit(value);
	}

	// Conversion
	public QuantityWeight convertTo(WeightUnit targetUnit) {
		if (targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null");

		double baseValue = this.toBaseUnit();
		double converted = targetUnit.convertFromBaseUnit(baseValue);

		return new QuantityWeight(converted, targetUnit);
	}

	// Addition (implicit target = this.unit)
	public QuantityWeight add(QuantityWeight other) {
		if (other == null)
			throw new IllegalArgumentException("Other weight cannot be null");

		return add(other, this.unit);
	}

	// Addition with explicit target unit
	public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {
		if (other == null)
			throw new IllegalArgumentException("Other weight cannot be null");
		if (targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null");

		double sumInKg = this.toBaseUnit() + other.toBaseUnit();
		double converted = targetUnit.convertFromBaseUnit(sumInKg);

		return new QuantityWeight(converted, targetUnit);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		QuantityWeight other = (QuantityWeight) obj;

		double thisKg = Math.round(this.toBaseUnit() * 10000.0) / 10000.0;
		double otherKg = Math.round(other.toBaseUnit() * 10000.0) / 10000.0;

		return Double.compare(thisKg, otherKg) == 0;
	}

	@Override
	public int hashCode() {
		return Double.hashCode(Math.round(toBaseUnit() / EPSILON));
	}

	@Override
	public String toString() {
		return "Quantity(" + value + ", " + unit + ")";
	}

	public double getValue() {
		return value;
	}

	public WeightUnit getUnit() {
		return unit;
	}
}