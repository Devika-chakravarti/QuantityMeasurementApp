package com.quantitymeasurement.core;

import java.util.Objects;

/*
 * Feet:
 * -----
 * This class represents a measurement in Feet.
 *
 * Purpose of this class:
 * ----------------------
 * - store a feet value
 * - support value-based equality
 *
 * This is an old UC14 file.
 * Since UC15 mainly introduces layered architecture,
 * this file is kept same as required.
 */
public class Feet {

	private final double value;

	/*
	 * Constructor: ------------ Creates a Feet object with the given value.
	 */
	public Feet(double value) {
		this.value = value;
	}

	/*
	 * equals: ------- Compares two Feet objects based on their value.
	 */
	@Override
	public boolean equals(Object obj) {

		/*
		 * Reflexive check: same object means equal.
		 */
		if (this == obj)
			return true;

		/*
		 * Null and type safety check.
		 */
		if (obj == null || getClass() != obj.getClass())
			return false;

		Feet other = (Feet) obj;

		/*
		 * Compare stored double values safely.
		 */
		return Double.compare(this.value, other.value) == 0;
	}

	/*
	 * hashCode: --------- Generates hash code based on value.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
}