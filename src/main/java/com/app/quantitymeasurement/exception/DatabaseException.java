package com.app.quantitymeasurement.exception;

/* This exception will be used to encapsulate any exceptions that occur during database operations
 * and provide meaningful error messages to the upper layers of the application.*/

public class DatabaseException extends QuantityMeasurementException {

	public DatabaseException(String message) {
		super(message);
	}

	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public static DatabaseException connectionFailed(String details, Throwable cause) {
		return new DatabaseException("Database connection failed: " + details, cause);
	}

	public static DatabaseException queryFailed(String query, Throwable cause) {
		return new DatabaseException("Query execution failed: " + query, cause);
	}

	public static DatabaseException transactionFailed(String operation, Throwable cause) {
		return new DatabaseException("Transaction failed during " + operation, cause);
	}

}
