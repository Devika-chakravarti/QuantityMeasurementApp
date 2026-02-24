package com.quantitymeasurement;

import java.util.Objects;

/**
 * Immutable value object representing a length measurement.
 */
public final class QuantityLength {

	private static final double EPSILON = 1e-6;

	private final double value;
	private final LengthUnit unit;

	public QuantityLength(double value, LengthUnit unit) {
		validate(value, unit);
		this.value = value;
		this.unit = unit;
	}

	/**
	 * Static conversion method.
	 */
	public static double convert(double value, LengthUnit source, LengthUnit target) {
		validate(value, source);
		Objects.requireNonNull(target, "Target unit cannot be null");

		double valueInFeet = source.toFeet(value);
		return valueInFeet / target.toFeet(1.0);
	}

	/**
	 * Convert to another unit.
	 */
	public QuantityLength convertTo(LengthUnit targetUnit) {
		double convertedValue = convert(this.value, this.unit, targetUnit);
		return new QuantityLength(convertedValue, targetUnit);
	}

	/**
	 * UC6: Add two quantities and return result in given unit.
	 */
	public QuantityLength add(QuantityLength other, LengthUnit resultUnit) {
		Objects.requireNonNull(other, "Other quantity cannot be null");
		Objects.requireNonNull(resultUnit, "Result unit cannot be null");

		double totalInFeet = this.toBaseUnit() + other.toBaseUnit();
		double resultValue = totalInFeet / resultUnit.toFeet(1.0);

		return new QuantityLength(resultValue, resultUnit);
	}

	private double toBaseUnit() {
		return unit.toFeet(value);
	}

	private static void validate(double value, LengthUnit unit) {
		if (!Double.isFinite(value))
			throw new IllegalArgumentException("Value must be finite");
		if (unit == null)
			throw new IllegalArgumentException("Unit cannot be null");
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof QuantityLength))
			return false;

		QuantityLength other = (QuantityLength) obj;
		return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPSILON;
	}

	@Override
	public int hashCode() {
		return Double.hashCode(Math.round(toBaseUnit() / EPSILON));
	}

	@Override
	public String toString() {
		return value + " " + unit;
	}

	public double getValue() {
		return value;
	}

	public LengthUnit getUnit() {
		return unit;
	}
}