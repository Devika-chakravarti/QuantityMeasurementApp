package com.quantitymeasurement.core;

/*
 * QuantityLength:
 * ---------------
 * This class represents a length quantity using:
 * - numeric value
 * - LengthUnit
 *
 * Purpose of this class:
 * ----------------------
 * - support length conversion
 * - support length addition
 * - support equality across different length units
 *
 * This is an old UC14 file.
 * Since UC15 mainly focuses on layered architecture,
 * this file is kept same as required.
 */
public final class QuantityLength {

	private static final double EPSILON = 1e-6;

	private final double value;
	private final LengthUnit unit;

	/*
	 * Constructor: ------------ Creates a QuantityLength object after validation.
	 */
	public QuantityLength(double value, LengthUnit unit) {
		validate(value, unit);
		this.value = value;
		this.unit = unit;
	}

	/*
	 * convertTo: ---------- Converts the current length into the given target unit.
	 */
	public QuantityLength convertTo(LengthUnit targetUnit) {
		if (targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null");

		double valueInFeet = unit.convertToBaseUnit(this.value);
		double convertedValue = targetUnit.convertFromBaseUnit(valueInFeet);

		return new QuantityLength(convertedValue, targetUnit);
	}

	/*
	 * add: ---- Adds another length and returns the result in the current unit.
	 */
	public QuantityLength add(QuantityLength other) {
		if (other == null)
			throw new IllegalArgumentException("Other quantity cannot be null");

		return add(other, this.unit);
	}

	/*
	 * add: ---- Adds another length and returns the result in the target unit.
	 */
	public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
		if (other == null)
			throw new IllegalArgumentException("Other quantity cannot be null");
		if (targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null");

		double resultValue = addInBaseAndConvert(other, targetUnit);
		return new QuantityLength(resultValue, targetUnit);
	}

	/*
	 * addInBaseAndConvert: -------------------- Helper method that: - converts both
	 * lengths into base unit - adds them - converts the result into target unit
	 */
	private double addInBaseAndConvert(QuantityLength other, LengthUnit targetUnit) {
		double sumInFeet = this.toBaseUnit() + other.toBaseUnit();
		return targetUnit.convertFromBaseUnit(sumInFeet);
	}

	/*
	 * toBaseUnit: ----------- Converts the current value into base unit.
	 */
	private double toBaseUnit() {
		return unit.convertToBaseUnit(value);
	}

	/*
	 * validate: --------- Validates: - value must be finite - unit must not be null
	 */
	private static void validate(double value, LengthUnit unit) {
		if (!Double.isFinite(value))
			throw new IllegalArgumentException("Value must be finite");
		if (unit == null)
			throw new IllegalArgumentException("Unit cannot be null");
	}

	/*
	 * equals: ------- Two QuantityLength objects are equal if their base-unit
	 * values are equal within tolerance.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof QuantityLength))
			return false;

		QuantityLength other = (QuantityLength) obj;
		return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPSILON;
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
	public LengthUnit getUnit() {
		return unit;
	}
}