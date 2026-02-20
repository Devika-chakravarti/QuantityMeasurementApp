package com.quantitymeasurement;

/**
 * Represents measurement in Inches.
 */
public class Inches {

	private final double value;

	public Inches(double value) {
		this.value = value;
	}

	// Overrides equals method for value comparison
	@Override
	public boolean equals(Object obj) {

		// Check same reference
		if (this == obj) {
			return true;
		}
		// Check null
		if (obj == null) {
			return false;
		}
		// Check type
		if (getClass() != obj.getClass()) {
			return false;
		}

		// Cast and compare values
		Inches other = (Inches) obj;

		return Double.compare(this.value, other.value) == 0;
	}
}