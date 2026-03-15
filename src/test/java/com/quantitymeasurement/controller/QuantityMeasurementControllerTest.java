package com.quantitymeasurement.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.quantitymeasurement.dto.QuantityDTO;
import com.quantitymeasurement.service.IQuantityMeasurementService;

/*
 * QuantityMeasurementControllerTest:
 * ----------------------------------
 * This test class checks the controller layer behavior in UC15.
 *
 * The controller should:
 * - accept QuantityDTO input
 * - delegate work to service layer
 * - not contain business logic
 *
 * To test this, a simple fake service is used.
 */
public class QuantityMeasurementControllerTest {

	/*
	 * FakeService: ------------ This test helper class implements the service
	 * interface and returns fixed values so that controller delegation can be
	 * tested clearly.
	 */
	private static class FakeService implements IQuantityMeasurementService {

		boolean compareCalled = false;
		boolean convertCalled = false;
		boolean addCalled = false;
		boolean addTargetCalled = false;
		boolean subtractCalled = false;
		boolean subtractTargetCalled = false;
		boolean divideCalled = false;

		@Override
		public boolean compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
			compareCalled = true;
			return true;
		}

		@Override
		public QuantityDTO convert(QuantityDTO thisQuantityDTO, QuantityDTO thatUnitDTO) {
			convertCalled = true;
			return new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
		}

		@Override
		public QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
			addCalled = true;
			return new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET);
		}

		@Override
		public QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO) {
			addTargetCalled = true;
			return new QuantityDTO(24.0, QuantityDTO.LengthUnit.INCHES);
		}

		@Override
		public QuantityDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
			subtractCalled = true;
			return new QuantityDTO(9.5, QuantityDTO.LengthUnit.FEET);
		}

		@Override
		public QuantityDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO,
				QuantityDTO targetUnitDTO) {
			subtractTargetCalled = true;
			return new QuantityDTO(114.0, QuantityDTO.LengthUnit.INCHES);
		}

		@Override
		public double divide(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
			divideCalled = true;
			return 1.0;
		}
	}

	/*
	 * Checks that controller delegates comparison to service.
	 */
	@Test
	void testPerformComparison() {
		FakeService service = new FakeService();
		QuantityMeasurementController controller = new QuantityMeasurementController(service);

		controller.performComparison(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));

		assertTrue(service.compareCalled);
	}

	/*
	 * Checks that controller delegates conversion to service.
	 */
	@Test
	void testPerformConversion() {
		FakeService service = new FakeService();
		QuantityMeasurementController controller = new QuantityMeasurementController(service);

		controller.performConversion(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(0.0, QuantityDTO.LengthUnit.INCHES));

		assertTrue(service.convertCalled);
	}

	/*
	 * Checks that controller delegates addition to service.
	 */
	@Test
	void testPerformAddition() {
		FakeService service = new FakeService();
		QuantityMeasurementController controller = new QuantityMeasurementController(service);

		controller.performAddition(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));

		assertTrue(service.addCalled);
	}

	/*
	 * Checks that controller delegates addition with target unit to service.
	 */
	@Test
	void testPerformAdditionWithTarget() {
		FakeService service = new FakeService();
		QuantityMeasurementController controller = new QuantityMeasurementController(service);

		controller.performAddition(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES),
				new QuantityDTO(0.0, QuantityDTO.LengthUnit.INCHES));

		assertTrue(service.addTargetCalled);
	}

	/*
	 * Checks that controller delegates subtraction to service.
	 */
	@Test
	void testPerformSubtraction() {
		FakeService service = new FakeService();
		QuantityMeasurementController controller = new QuantityMeasurementController(service);

		controller.performSubtraction(new QuantityDTO(10.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(6.0, QuantityDTO.LengthUnit.INCHES));

		assertTrue(service.subtractCalled);
	}

	/*
	 * Checks that controller delegates subtraction with target unit to service.
	 */
	@Test
	void testPerformSubtractionWithTarget() {
		FakeService service = new FakeService();
		QuantityMeasurementController controller = new QuantityMeasurementController(service);

		controller.performSubtraction(new QuantityDTO(10.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(6.0, QuantityDTO.LengthUnit.INCHES),
				new QuantityDTO(0.0, QuantityDTO.LengthUnit.INCHES));

		assertTrue(service.subtractTargetCalled);
	}

	/*
	 * Checks that controller delegates division to service.
	 */
	@Test
	void testPerformDivision() {
		FakeService service = new FakeService();
		QuantityMeasurementController controller = new QuantityMeasurementController(service);

		controller.performDivision(new QuantityDTO(24.0, QuantityDTO.LengthUnit.INCHES),
				new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET));

		assertTrue(service.divideCalled);
	}
}