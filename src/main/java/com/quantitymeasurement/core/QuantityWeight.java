package com.quantitymeasurement.core;

/*
 * QuantityWeight:
 * ---------------
 * This class represents a weight quantity using:
 * - numeric value
 * - WeightUnit
 *
 * Purpose of this class:
 * ----------------------
 * - support weight conversion
 * - support weight addition
 * - support equality across different weight units
 *
 * This is an old UC14 file.
 * Since UC15 mainly focuses on layered architecture,
 * this file is kept same as required.
 */
public final class QuantityWeight {

	private static final double EPSILON = 1e-6;

	private final double value;
	private final WeightUnit unit;

	/*
	 * Constructor: ------------ Creates a QuantityWeight object after validation.
	 */
	public QuantityWeight(double value, WeightUnit unit) {
		validate(value, unit);
		this.value = value;
		this.unit = unit;
	}

	/*
	 * validate: --------- Validates: - value must be finite - unit must not be null
	 */
	private static void validate(double value, WeightUnit unit) {
		if (!Double.isFinite(value))
			throw new IllegalArgumentException("Value must be finite");
		if (unit == null)
			throw new IllegalArgumentException("Unit cannot be null");
	}

	/*
	 * toBaseUnit: ----------- Converts the current value into base unit.
	 */
	private double toBaseUnit() {
		return unit.convertToBaseUnit(value);
	}

	/*
	 * convertTo: ---------- Converts the current weight into the given target unit.
	 */
	public QuantityWeight convertTo(WeightUnit targetUnit) {
		if (targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null");

		double baseValue = this.toBaseUnit();
		double converted = targetUnit.convertFromBaseUnit(baseValue);

		return new QuantityWeight(converted, targetUnit);
	}

	/*
	 * add: ---- Adds another weight and returns the result in the current unit.
	 */
	public QuantityWeight add(QuantityWeight other) {
		if (other == null)
			throw new IllegalArgumentException("Other weight cannot be null");

		return add(other, this.unit);
	}

	/*
	 * add: ---- Adds another weight and returns the result in the target unit.
	 */
	public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {
		if (other == null)
			throw new IllegalArgumentException("Other weight cannot be null");
		if (targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null");

		double sumInKg = this.toBaseUnit() + other.toBaseUnit();
		double converted = targetUnit.convertFromBaseUnit(sumInKg);

		return new QuantityWeight(converted, targetUnit);
	}

	/*
	 * equals: ------- Two QuantityWeight objects are equal if their base-unit
	 * values are equal after rounding.
	 */
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

	/*
	 * hashCode: --------- Generates hash code using rounded base value.
	 */
	@Override
	public int hashCode() {
		return Double.hashCode(Math.round(toBaseUnit() / EPSILON));
	}

	/*
	 * toString: --------- Returns readable quantity string.
	 */
	@Override
	public String toString() {
		return "Quantity(" + value + ", " + unit + ")";
	}

	/*
	 * Returns stored value.
	 */
	public double getValue() {
		return value;
	}

	/*
	 * Returns stored unit.
	 */
	public WeightUnit getUnit() {
		return unit;
	}
}