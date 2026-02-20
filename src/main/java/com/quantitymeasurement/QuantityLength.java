package com.quantitymeasurement;

/**
 * Generic class representing a length measurement with value and unit type.
 */
public class QuantityLength {

	private final double value;
	private final LengthUnit unit;

	// Constructor with validation
	public QuantityLength(double value, LengthUnit unit) {

		if (unit == null) {
			throw new IllegalArgumentException("Unit cannot be null");
		}

		this.value = value;
		this.unit = unit;
	}

	// Converts current value to base unit (Feet)
	private double toFeet() {
		return unit.toFeet(value);
	}

	// Value-based equality with unit conversion
	@Override
	public boolean equals(Object obj) {

		// Reflexive
		if (this == obj) {
			return true;
		}
		// Null check
		if (obj == null) {
			return false;
		}
		// Type check
		if (getClass() != obj.getClass()) {
			return false;
		}

		QuantityLength other = (QuantityLength) obj;

		// Compare converted values
		return Double.compare(this.toFeet(), other.toFeet()) == 0;
	}
}