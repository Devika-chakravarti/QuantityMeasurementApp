package com.quantitymeasurement.service;

import com.quantitymeasurement.dto.QuantityDTO;

/*
 * IQuantityMeasurementService:
 * ---------------------------
 * This interface defines the service contract for quantity measurement operations.
 *
 * In UC15, the service layer handles:
 * - comparison
 * - conversion
 * - addition
 * - subtraction
 * - division
 *
 * It accepts QuantityDTO objects as input.
 * It returns boolean, QuantityDTO, or double depending on the operation,
 * exactly as shown in the UC15 service skeleton.
 */
public interface IQuantityMeasurementService {

	/*
	 * Compare two quantities after conversion to a common base unit.
	 *
	 * Returns true if both quantities are equal after conversion, otherwise returns
	 * false.
	 */
	boolean compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

	/*
	 * Convert this quantity to the target quantity unit.
	 *
	 * Returns the converted quantity as QuantityDTO.
	 */
	QuantityDTO convert(QuantityDTO thisQuantityDTO, QuantityDTO thatUnitDTO);

	/*
	 * Add two quantities.
	 *
	 * Returns the result in the default resulting unit as QuantityDTO.
	 */
	QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

	/*
	 * Add two quantities and return the result in the specified target unit.
	 *
	 * Returns the result as QuantityDTO.
	 */
	QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO);

	/*
	 * Subtract the second quantity from the first quantity.
	 *
	 * Returns the result in the default resulting unit as QuantityDTO.
	 */
	QuantityDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

	/*
	 * Subtract the second quantity from the first quantity and return the result in
	 * the specified target unit.
	 *
	 * Returns the result as QuantityDTO.
	 */
	QuantityDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO);

	/*
	 * Divide the first quantity by the second quantity.
	 *
	 * Returns the division result as double.
	 */
	double divide(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

	/*
	 * Main method for testing purposes.
	 */
	public static void main(String[] args) {
	}
}