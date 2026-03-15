package com.quantitymeasurement.controller;

import com.quantitymeasurement.dto.QuantityDTO;
import com.quantitymeasurement.service.IQuantityMeasurementService;

/*
 * QuantityMeasurementController:
 * ------------------------------
 * This class belongs to the Controller Layer in UC15.
 *
 * Role of this class:
 * -------------------
 * - receive quantity input in DTO form
 * - call the service layer
 * - display the result in a simple way
 *
 * In UC15, the controller should not contain business logic.
 * It should only delegate the work to the service layer.
 */
public class QuantityMeasurementController {

	private final IQuantityMeasurementService quantityMeasurementService;

	/*
	 * Constructor injection: ---------------------- The controller receives the
	 * service dependency from outside.
	 */
	public QuantityMeasurementController(IQuantityMeasurementService quantityMeasurementService) {
		this.quantityMeasurementService = quantityMeasurementService;
	}

	/*
	 * Performs comparison of two quantities.
	 */
	public void performComparison(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		boolean result = quantityMeasurementService.compare(thisQuantityDTO, thatQuantityDTO);
		System.out.println("Comparison Result: " + result);
	}

	/*
	 * Performs conversion of one quantity into the target unit.
	 */
	public void performConversion(QuantityDTO thisQuantityDTO, QuantityDTO thatUnitDTO) {
		QuantityDTO result = quantityMeasurementService.convert(thisQuantityDTO, thatUnitDTO);
		System.out.println("Conversion Result: " + result);
	}

	/*
	 * Performs addition of two quantities.
	 */
	public void performAddition(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		QuantityDTO result = quantityMeasurementService.add(thisQuantityDTO, thatQuantityDTO);
		System.out.println("Addition Result: " + result);
	}

	/*
	 * Performs addition of two quantities in the given target unit.
	 */
	public void performAddition(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO) {
		QuantityDTO result = quantityMeasurementService.add(thisQuantityDTO, thatQuantityDTO, targetUnitDTO);
		System.out.println("Addition Result: " + result);
	}

	/*
	 * Performs subtraction of two quantities.
	 */
	public void performSubtraction(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		QuantityDTO result = quantityMeasurementService.subtract(thisQuantityDTO, thatQuantityDTO);
		System.out.println("Subtraction Result: " + result);
	}

	/*
	 * Performs subtraction of two quantities in the given target unit.
	 */
	public void performSubtraction(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO,
			QuantityDTO targetUnitDTO) {
		QuantityDTO result = quantityMeasurementService.subtract(thisQuantityDTO, thatQuantityDTO, targetUnitDTO);
		System.out.println("Subtraction Result: " + result);
	}

	/*
	 * Performs division of two quantities.
	 */
	public void performDivision(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		double result = quantityMeasurementService.divide(thisQuantityDTO, thatQuantityDTO);
		System.out.println("Division Result: " + result);
	}

	/*
	 * Main method for testing purposes.
	 */
	public static void main(String[] args) {
	}
}