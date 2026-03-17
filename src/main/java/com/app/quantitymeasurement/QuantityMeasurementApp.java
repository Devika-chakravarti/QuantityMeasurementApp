package com.app.quantitymeasurement;

import java.util.List;
import java.util.logging.Logger;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.app.quantitymeasurement.util.DatabaseConfig;

/**
 * QuantityMeasurementApp
 *
 * Main application class responsible for initializing and running the Quantity
 * Measurement system. Acts as the Application Layer entry point and coordinates
 * the initialization of core components following an N-Tier architecture.
 *
 * UC16 enhancements over UC15: - Uses ApplicationConfig to determine which
 * repository implementation to use (cache or database) based on the
 * "repository.type" property in application.properties. - Switches to
 * QuantityMeasurementDatabaseRepository by default for persistent storage. -
 * Added structured logging for application lifecycle events. - Added
 * closeResources() for graceful shutdown and connection pool cleanup. - Added
 * deleteAllMeasurements() for test reset capability. - Main method now reports
 * all stored measurements and calls deleteAllMeasurements() at end.
 *
 * Responsibilities: - Initializing the repository layer (cache or database
 * based on config) - Creating the service layer - Creating the controller layer
 * - Providing a singleton application instance - Executing sample quantity
 * measurement operations
 *
 * @author Developer
 * @version 16.0
 * @since 1.0
 */
public class QuantityMeasurementApp {

	/**
	 * Logger for recording application lifecycle events.
	 */
	private static final Logger logger = Logger.getLogger(QuantityMeasurementApp.class.getName());

	/** Singleton application instance */
	private static QuantityMeasurementApp instance;

	/** Controller layer — handles all user-facing operations */
	public QuantityMeasurementController controller;

	/** Repository layer — persists measurement operation history */
	public IQuantityMeasurementRepository repository;

	/**
	 * Private constructor used to initialize the application components.
	 *
	 * Reads the "repository.type" property from ApplicationConfig. If the value is
	 * "database", it initializes the QuantityMeasurementDatabaseRepository.
	 * Otherwise, it falls back to the in-memory QuantityMeasurementCacheRepository.
	 *
	 * Creates the service layer and controller layer after the repository is ready.
	 */
	private QuantityMeasurementApp() {
		/*
		 * Read the repository type from the centralized application configuration. This
		 * allows switching storage without changing any code.
		 */
		DatabaseConfig config = DatabaseConfig.getInstance();
		String repoType = config.getProperty("repository.type", "database");

		logger.info("Initializing repository type: " + repoType);

		if ("database".equalsIgnoreCase(repoType)) {
			/*
			 * Use database-backed repository for persistent, JDBC-based storage. This is
			 * the default for UC16.
			 */
			this.repository = QuantityMeasurementDatabaseRepository.getInstance();
			logger.info("Using QuantityMeasurementDatabaseRepository.");
		} else {
			/*
			 * Fall back to in-memory cache repository — useful for development or testing
			 * without any database setup.
			 */
			this.repository = QuantityMeasurementCacheRepository.getInstance();
			logger.info("Using QuantityMeasurementCacheRepository.");
		}

		QuantityMeasurementServiceImpl service = new QuantityMeasurementServiceImpl(this.repository);
		this.controller = new QuantityMeasurementController(service);

		logger.info("QuantityMeasurementApp components initialized successfully.");
	}

	/**
	 * Returns the singleton instance of the application.
	 *
	 * @return application instance
	 */
	public static QuantityMeasurementApp getInstance() {
		if (instance == null) {
			instance = new QuantityMeasurementApp();
		}
		return instance;
	}

	/**
	 * Returns the controller held by this application instance. Used by integration
	 * tests to access the controller without re-initializing.
	 *
	 * @return the QuantityMeasurementController
	 */
	public QuantityMeasurementController getController() {
		return controller;
	}

	/**
	 * Returns the repository held by this application instance. Used by integration
	 * tests to verify persistence directly.
	 *
	 * @return the IQuantityMeasurementRepository
	 */
	public IQuantityMeasurementRepository getRepository() {
		return repository;
	}

	/**
	 * Closes all resources held by the repository — primarily the database
	 * connection pool.
	 *
	 * Should be called before the application exits to ensure no connections are
	 * leaked. Safe to call on cache repositories.
	 */
	public void closeResources() {
		if (repository != null) {
			repository.releaseResources();
			logger.info("Repository resources released. Application shutting down.");
		}
		/* Reset singleton so tests can create a fresh instance */
		instance = null;
	}

	/**
	 * Deletes all measurements from the repository.
	 *
	 * Useful for testing purposes to clear the repository before running tests, or
	 * to reset the application state between demonstration runs.
	 */
	public void deleteAllMeasurements() {
		repository.deleteAll();
		logger.info("All measurements deleted from repository.");
	}

	/**
	 * Entry point of the Quantity Measurement Application.
	 *
	 * Demonstrates execution of different measurement operations including
	 * comparison, conversion, arithmetic calculations, and retrieval of stored
	 * measurement records.
	 *
	 * @param args command line arguments
	 */
	public static void main(String[] args) {

		logger.info("\n**** Quantity Measurement Application Started (UC16) ****\n");

		QuantityMeasurementApp app = QuantityMeasurementApp.getInstance();
		QuantityMeasurementController controller = app.controller;

		/*
		 * Report repository statistics at startup.
		 */
		logger.info("Repository stats: " + app.repository.getPoolStatistics());

		/*
		 * ------------------------------------------------------------------ Example 1:
		 * Length Equality — 2 FEET vs 24 INCHES
		 * ------------------------------------------------------------------
		 */
		QuantityDTO quantity1 = new QuantityDTO(2, QuantityDTO.LengthUnit.FEET.getUnitName(),
				QuantityDTO.LengthUnit.FEET.getMeasurementType());
		QuantityDTO quantity2 = new QuantityDTO(24, QuantityDTO.LengthUnit.INCHES.getUnitName(),
				QuantityDTO.LengthUnit.INCHES.getMeasurementType());
		controller.demonstrateComparison(quantity1, quantity2);
		System.out.println();

		/*
		 * ------------------------------------------------------------------ Example 2:
		 * Temperature Conversion — 0°C to °F
		 * ------------------------------------------------------------------
		 */
		QuantityDTO temp1 = new QuantityDTO(0, QuantityDTO.TemperatureUnit.CELSIUS.getUnitName(),
				QuantityDTO.TemperatureUnit.CELSIUS.getMeasurementType());
		QuantityDTO temp2 = new QuantityDTO(0, QuantityDTO.TemperatureUnit.FAHRENHEIT.getUnitName(),
				QuantityDTO.TemperatureUnit.FAHRENHEIT.getMeasurementType());
		controller.demonstrateConversion(temp1, temp2);
		System.out.println();

		/*
		 * ------------------------------------------------------------------ Example 3:
		 * Temperature Addition — should produce an error
		 * ------------------------------------------------------------------
		 */
		QuantityDTO tempTarget = new QuantityDTO(0, QuantityDTO.TemperatureUnit.CELSIUS.getUnitName(),
				QuantityDTO.TemperatureUnit.CELSIUS.getMeasurementType());
		controller.demonstrateAddition(temp1, temp2, tempTarget);
		System.out.println();

		/*
		 * ------------------------------------------------------------------ Example 4:
		 * Cross-Category Addition — length + weight
		 * ------------------------------------------------------------------
		 */
		QuantityDTO weightQuantity = new QuantityDTO(10, QuantityDTO.WeightUnit.KILOGRAM.getUnitName(),
				QuantityDTO.WeightUnit.KILOGRAM.getMeasurementType());
		controller.demonstrateAddition(quantity1, weightQuantity);
		System.out.println();

		/*
		 * ------------------------------------------------------------------ Example 5:
		 * Full length operation suite
		 * ------------------------------------------------------------------
		 */
		QuantityDTO yardsTarget = new QuantityDTO(0, QuantityDTO.LengthUnit.YARDS.getUnitName(),
				QuantityDTO.LengthUnit.YARDS.getMeasurementType());

		controller.demonstrateConversion(quantity2, yardsTarget);
		System.out.println();

		controller.demonstrateAddition(quantity1, quantity2);
		System.out.println();

		controller.demonstrateAddition(quantity1, quantity2, yardsTarget);
		System.out.println();

		controller.demonstrateSubtraction(quantity1, quantity2);
		System.out.println();

		controller.demonstrateDivision(quantity1, quantity2);
		System.out.println();

		/*
		 * ------------------------------------------------------------------ Report all
		 * measurements now stored in the repository
		 * ------------------------------------------------------------------
		 */
		System.out.println("---- Stored Measurements ----");
		List<QuantityMeasurementEntity> allMeasurements = app.repository.getAllMeasurements();
		allMeasurements.forEach(System.out::println);

		logger.info("Total measurements stored: " + app.repository.getTotalCount());
		logger.info("Repository stats after run: " + app.repository.getPoolStatistics());

		/*
		 * ------------------------------------------------------------------ Clean up
		 * for demo/testing
		 * ------------------------------------------------------------------
		 */
		app.deleteAllMeasurements();
		logger.info("Measurements cleared after demo run.");

		app.closeResources();
		logger.info("\n**** Quantity Measurement Application Stopped ****\n");
	}
}