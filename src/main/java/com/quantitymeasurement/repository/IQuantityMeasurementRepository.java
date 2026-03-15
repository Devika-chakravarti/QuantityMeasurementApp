package com.quantitymeasurement.repository;

import java.util.List;

import com.quantitymeasurement.entity.QuantityMeasurementEntity;

/*
 * IQuantityMeasurementRepository:
 * -------------------------------
 * This interface defines the repository contract for quantity measurement data.
 *
 * Why this interface is needed:
 * -----------------------------
 * In UC15, repository layer is introduced so that data storage logic is separated
 * from business logic.
 *
 * This interface is used by the service layer to:
 * - save a quantity measurement operation
 * - get all saved quantity measurement operations
 *
 * This keeps the service layer loosely coupled with the actual repository
 * implementation.
 */
public interface IQuantityMeasurementRepository {

	/*
	 * Saves one quantity measurement entity into the repository.
	 */
	void save(QuantityMeasurementEntity entity);

	/*
	 * Returns all stored quantity measurement entities.
	 */
	List<QuantityMeasurementEntity> getAllMeasurements();
}