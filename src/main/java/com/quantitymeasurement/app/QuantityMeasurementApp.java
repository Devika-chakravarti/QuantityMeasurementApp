package com.quantitymeasurement.app;

import com.quantitymeasurement.controller.QuantityMeasurementController;
import com.quantitymeasurement.dto.QuantityDTO;
import com.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.quantitymeasurement.service.IQuantityMeasurementService;
import com.quantitymeasurement.service.QuantityMeasurementServiceImpl;

/*
 * QuantityMeasurementApp:
 * -----------------------
 * This is the application layer entry point in UC15.
 *
 * Purpose of this class:
 * ----------------------
 * - create repository object
 * - create service object
 * - create controller object
 * - create DTO input objects
 * - call controller methods
 *
 * This class should not contain business logic.
 * It only starts the layered flow of the application.
 */
public class QuantityMeasurementApp {

	/*
	 * Main method: ------------ Starts the UC15 layered Quantity Measurement
	 * Application.
	 */
	public static void main(String[] args) {

		/*
		 * Step 1: Create repository layer object.
		 */
		IQuantityMeasurementRepository repository = QuantityMeasurementCacheRepository.getInstance();

		/*
		 * Step 2: Create service layer object by injecting repository.
		 */
		IQuantityMeasurementService service = new QuantityMeasurementServiceImpl(repository);

		/*
		 * Step 3: Create controller layer object by injecting service.
		 */
		QuantityMeasurementController controller = new QuantityMeasurementController(service);

		/*
		 * Step 4: Create sample DTO objects and call controller methods.
		 */

		QuantityDTO oneFoot = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
		QuantityDTO twelveInches = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
		QuantityDTO inchTarget = new QuantityDTO(0.0, QuantityDTO.LengthUnit.INCHES);
		QuantityDTO yardTarget = new QuantityDTO(0.0, QuantityDTO.LengthUnit.YARDS);
		QuantityDTO feetTarget = new QuantityDTO(0.0, QuantityDTO.LengthUnit.FEET);

		controller.performComparison(oneFoot, twelveInches);
		controller.performConversion(oneFoot, inchTarget);
		controller.performAddition(oneFoot, twelveInches);
		controller.performAddition(oneFoot, twelveInches, yardTarget);
		controller.performSubtraction(oneFoot, twelveInches);
		controller.performSubtraction(oneFoot, twelveInches, feetTarget);
		controller.performDivision(oneFoot, twelveInches);
	}
}