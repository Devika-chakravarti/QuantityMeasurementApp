package com.app.quantitymeasurement.exception;

/*
 * QuantityMeasurementException:
 * -----------------------------
 * This is a custom exception class used for quantity measurement related errors.
 *
 * It is used when:
 * - invalid measurements are given
 * - unit conversion fails
 * - any other quantity-related operation fails
 */
public class QuantityMeasurementException extends RuntimeException {

	public QuantityMeasurementException(String message) {
		super(message);
	}

	public QuantityMeasurementException(String message, Throwable cause) {
		super(message, cause);
	}

	/*
	 * Main method for testing purposes.
	 */
	public static void main(String[] args) {
		try {
			throw new QuantityMeasurementException("This is a test exception for quantity measurement");
		} catch (QuantityMeasurementException ex) {
			System.out.println("Caught Quantity,easurementException: " + ex.getMessage());
		}
	}
}