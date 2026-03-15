package com.quantitymeasurement.core;

/*
 * Inches:
 * -------
 * This class represents a measurement in Inches.
 *
 * Purpose of this class:
 * ----------------------
 * - store an inches value
 * - support value-based equality
 *
 * This is an old UC14 file.
 * Since UC15 mainly introduces layered architecture,
 * this file is kept same as required.
 */
public class Inches {

	private final double value;

	/*
	 * Constructor: ------------ Creates an Inches object with the given value.
	 */
	public Inches(double value) {
		this.value = value;
	}

	/*
	 * equals: ------- Compares two Inches objects based on their value.
	 */
	@Override
	public boolean equals(Object obj) {

		/*
		 * Same reference means both are equal.
		 */
		if (this == obj) {
			return true;
		}

		/*
		 * Null check.
		 */
		if (obj == null) {
			return false;
		}

		/*
		 * Type check.
		 */
		if (getClass() != obj.getClass()) {
			return false;
		}

		/*
		 * Cast and compare stored values.
		 */
		Inches other = (Inches) obj;
		return Double.compare(this.value, other.value) == 0;
	}
}