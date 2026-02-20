package com.quantitymeasurement;

import java.util.Objects;

/**
 * Represents a Feet measurement. Immutable and supports value-based equality.
 */
public class Feet {

	private final double value;

	public Feet(double value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {

		// Reflexive property
		if (this == obj)
			return true;

		// Null and type safety check
		if (obj == null || getClass() != obj.getClass())
			return false;

		Feet other = (Feet) obj;

		// Floating-point comparison
		return Double.compare(this.value, other.value) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
}