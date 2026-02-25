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

	// UC5 Conversion
	public static double convert(double value, LengthUnit source, LengthUnit target) {
		validate(value, source);
		if (target == null)
			throw new IllegalArgumentException("Target unit cannot be null");

		double valueInFeet = source.toFeet(value);
		return valueInFeet / target.toFeet(1.0);
	}

	public QuantityLength convertTo(LengthUnit targetUnit) {
		double convertedValue = convert(this.value, this.unit, targetUnit);
		return new QuantityLength(convertedValue, targetUnit);
	}

	// UC6: Addition (implicit target = first operand unit)
	public QuantityLength add(QuantityLength other) {
		if (other == null)
			throw new IllegalArgumentException("Other quantity cannot be null");

		return add(other, this.unit);
	}

	// UC7: Addition with explicit target unit
	public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
		if (other == null)
			throw new IllegalArgumentException("Other quantity cannot be null");
		if (targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null");

		double resultValue = addInBaseAndConvert(other, targetUnit);
		return new QuantityLength(resultValue, targetUnit);
	}

	// Private utility method (DRY principle)
	private double addInBaseAndConvert(QuantityLength other, LengthUnit targetUnit) {
		double sumInFeet = this.toBaseUnit() + other.toBaseUnit();
		return sumInFeet / targetUnit.toFeet(1.0);
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
		return "Quantity(" + value + ", " + unit + ")";
	}

	public double getValue() {
		return value;
	}

	public LengthUnit getUnit() {
		return unit;
	}
}