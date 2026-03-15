package com.quantitymeasurement.entity;

import com.quantitymeasurement.entity.QuantityModel;

/**
 * UC 15 Enhancement: Creation of QuantityMeasurementEntity – A comprehensive
 * data holder for quantity measurement operations, including operands,
 * operation type, result, and error information. This class is designed to be
 * immutable and serializable, ensuring that once an instance is created, its
 * state cannot be modified and it can be easily persisted to disk.
 */
public class QuantityMeasurementEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public double thisValue;
	public String thisUnit;
	public String thisMeasurementType;
	public double thatValue;
	public String thatUnit;
	public String thatMeasurementType;

	// e.g., "COMPARE", "CONVER", "ADD", "SUBTRACT", "DEVIDE"
	public String operation;

	public double resultValue;
	public String resultUnit;
	public String resultMeasurementType;

	// For comparison results like "Equal" or "Not Equal"
	public String resultString;

	// Flag to indicate if an error occurred during the operation
	public boolean isError;

	// For capturing any error messages during operations
	public String errorMessage;

	/**
	 * Constructor to initialize the QuantityMeasurementEntity with the given
	 * quantities, operation, and result for a single operand operation (e.g.,
	 * comparison and conversion).
	 *
	 * This constructor is used predominantly for Comparison operations where both
	 * thisQuantity and thatQuantity are involved in the operation and a result is
	 * produced.
	 *
	 * @param thisQuantity the first quantity model involved in the operation
	 * @param thatQuantity the second quantity model involved in the operation
	 * @param operation    the type of operation performed (e.g., "COMPARE",
	 *                     "CONVERT")
	 * @param result       the result of the operation as a string (e.g., "Equal",
	 *                     "Not Equal")
	 */
	public QuantityMeasurementEntity(QuantityModel<?> thisQuantity, QuantityModel<?> thatQuantity, String operation,
			String result) {
		this(thisQuantity, thatQuantity, operation);
		this.resultString = result;
	}

	/**
	 * Constructor to initialize the QuantityMeasurementEntity with the given
	 * quantities, operation, and result for a double operand operation (e.g.,
	 * addition, subtraction, division).
	 *
	 * This constructor is used predominantly for Addition and Subtraction
	 * operations where both thisQuantity and thatQuantity are involved in the
	 * operation and a result is produced.
	 *
	 * @param thisQuantity the first quantity model involved in the operation
	 * @param thatQuantity the second quantity model involved in the operation
	 * @param operation    the type of operation performed (e.g., "COMPARE",
	 *                     "CONVERT")
	 * @param result       the resulting quantity model of the operation
	 */
	public QuantityMeasurementEntity(QuantityModel<?> thisQuantity, QuantityModel<?> thatQuantity, String operation,
			QuantityModel<?> result) {
		this(thisQuantity, thatQuantity, operation);
		this.resultValue = result.value;
		this.resultUnit = result.unit.getUnitName();
		this.resultMeasurementType = result.unit.getMeasurementType();
	}

	/**
	 * Constructor to initialize the QuantityMeasurementEntity with the given
	 * quantities, operation, and result.
	 *
	 * This constructor is used predominantly for Comparison operations where both
	 * thisQuantity and thatQuantity are involved in the operation and a result is
	 * produced.
	 *
	 * @param thisQuantity the first quantity model involved in the operation
	 * @param thatQuantity the second quantity model involved in the operation
	 * @param operation    the type of operation performed (e.g., "COMPARE",
	 *                     "CONVERT")
	 * @param result       the result value of the operation
	 */
	public QuantityMeasurementEntity(QuantityModel<?> thisQuantity, QuantityModel<?> thatQuantity, String operation,
			double result) {
		this(thisQuantity, thatQuantity, operation);
		this.resultValue = result;
	}

	/**
	 * Constructor to initialize the QuantityMeasurementEntity with the given
	 * quantities, operation, and error information.
	 *
	 * This constructor is used predominantly for error cases where an operation
	 * fails.
	 *
	 * @param thisQuantity the first quantity model involved in the operation
	 * @param thatQuantity the second quantity model involved in the operation
	 * @param operation    the type of operation performed (e.g., "COMPARE",
	 *                     "CONVERT")
	 * @param errorMessage the error message describing why the operation failed
	 * @param isError      whether this entity represents an error case
	 */
	public QuantityMeasurementEntity(QuantityModel<?> thisQuantity, QuantityModel<?> thatQuantity, String operation,
			String errorMessage, boolean isError) {
		this(thisQuantity, thatQuantity, operation);
		this.errorMessage = errorMessage;
		this.isError = isError;
	}

	/**
	 * Mostly called by other constructors to initialize the common fields of the
	 * entity with the given quantities and operation.
	 *
	 * @param thisQuantity the first quantity model involved in the operation
	 * @param thatQuantity the second quantity model involved in the operation
	 * @param operation    the type of operation performed (e.g., "COMPARE",
	 *                     "CONVERT")
	 */
	public QuantityMeasurementEntity(QuantityModel<?> thisQuantity, QuantityModel<?> thatQuantity, String operation) {
		this.thisValue = thisQuantity.value;
		this.thisUnit = thisQuantity.unit.getUnitName();
		this.thisMeasurementType = thisQuantity.unit.getMeasurementType();
		this.thatValue = thatQuantity.value;
		this.thatUnit = thatQuantity.unit.getUnitName();
		this.thatMeasurementType = thatQuantity.unit.getMeasurementType();
		this.operation = operation;
	}

	/**
	 * Override the equals method to compare QuantityMeasurementEntity instances
	 * based on their fields.
	 *
	 * This method is essential for ensuring that two QuantityMeasurementEntity
	 * instances are considered equal if they have the same input values and
	 * operations, regardless of their memory addresses. This is particularly
	 * important when storing instances in collections that rely on equality checks,
	 * such as sets or when checking for duplicates in the repository.
	 *
	 * @param obj the object to compare with this instance
	 * @return true if the objects are equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		QuantityMeasurementEntity other = (QuantityMeasurementEntity) obj;

		return Double.compare(thisValue, other.thisValue) == 0 && java.util.Objects.equals(thisUnit, other.thisUnit)
				&& java.util.Objects.equals(thisMeasurementType, other.thisMeasurementType)
				&& Double.compare(thatValue, other.thatValue) == 0 && java.util.Objects.equals(thatUnit, other.thatUnit)
				&& java.util.Objects.equals(thatMeasurementType, other.thatMeasurementType)
				&& java.util.Objects.equals(operation, other.operation)
				&& Double.compare(resultValue, other.resultValue) == 0
				&& java.util.Objects.equals(resultUnit, other.resultUnit)
				&& java.util.Objects.equals(resultMeasurementType, other.resultMeasurementType)
				&& java.util.Objects.equals(resultString, other.resultString) && isError == other.isError
				&& java.util.Objects.equals(errorMessage, other.errorMessage);
	}

	/**
	 * Override the toString method to provide a readable representation of the
	 * QuantityMeasurementEntity, including the operation, the input quantities, and
	 * the result or error information.
	 *
	 * This method is useful for logging and debugging purposes, allowing developers
	 * to easily understand the details of the quantity measurement operation and
	 * its outcome when printed or logged.
	 *
	 * @return a string representation of the QuantityMeasurementEntity
	 */
	@Override
	public String toString() {
		return "QuantityMeasurementEntity [thisValue=" + thisValue + ", thisUnit=" + thisUnit + ", thisMeasurementType="
				+ thisMeasurementType + ", thatValue=" + thatValue + ", thatUnit=" + thatUnit + ", thatMeasurementType="
				+ thatMeasurementType + ", operation=" + operation + ", resultValue=" + resultValue + ", resultUnit="
				+ resultUnit + ", resultMeasurementType=" + resultMeasurementType + ", resultString=" + resultString
				+ ", isError=" + isError + ", errorMessage=" + errorMessage + "]";
	}

	// Main method for testing purposes
	public static void main(String[] args) {
	}
}