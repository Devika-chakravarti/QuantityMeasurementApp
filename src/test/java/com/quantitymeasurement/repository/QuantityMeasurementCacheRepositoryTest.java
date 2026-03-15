package com.quantitymeasurement.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.quantitymeasurement.core.LengthUnit;
import com.quantitymeasurement.entity.QuantityModel;
import com.quantitymeasurement.entity.QuantityMeasurementEntity;

/*
 * QuantityMeasurementCacheRepositoryTest:
 * ---------------------------------------
 * This test class checks the UC15 cache repository behavior.
 *
 * It verifies:
 * - singleton behavior
 * - save operation
 * - in-memory cache retrieval
 * - file-based loading after repository recreation
 *
 * Since the repository is singleton and also stores data in file,
 * each test starts with a clean state.
 */
public class QuantityMeasurementCacheRepositoryTest {

	/*
	 * Before each test: ----------------- - delete persisted file if it exists -
	 * reset singleton instance using reflection
	 *
	 * This helps every test start independently.
	 */
	@BeforeEach
	void setUp() throws Exception {
		File file = new File(QuantityMeasurementCacheRepository.FILE_NAME);
		if (file.exists()) {
			file.delete();
		}

		Field instanceField = QuantityMeasurementCacheRepository.class.getDeclaredField("instance");
		instanceField.setAccessible(true);
		instanceField.set(null, null);
	}

	/*
	 * Checks that repository returns the same singleton instance.
	 */
	@Test
	void testSingletonInstance() {
		QuantityMeasurementCacheRepository repo1 = QuantityMeasurementCacheRepository.getInstance();
		QuantityMeasurementCacheRepository repo2 = QuantityMeasurementCacheRepository.getInstance();

		assertSame(repo1, repo2);
	}

	/*
	 * Checks that saving an entity stores it in the in-memory cache.
	 */
	@Test
	void testSaveAndGetAllMeasurements() {
		QuantityMeasurementCacheRepository repository = QuantityMeasurementCacheRepository.getInstance();

		QuantityModel<LengthUnit> first = new QuantityModel<>(1.0, LengthUnit.FEET);
		QuantityModel<LengthUnit> second = new QuantityModel<>(12.0, LengthUnit.INCH);

		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(first, second, "COMPARE", "Equal");

		repository.save(entity);

		List<QuantityMeasurementEntity> allMeasurements = repository.getAllMeasurements();

		assertEquals(1, allMeasurements.size());
		assertEquals(entity, allMeasurements.get(0));
	}

	/*
	 * Checks that repository loads saved data from disk when singleton is
	 * recreated.
	 */
	@Test
	void testLoadFromDiskAfterRecreation() throws Exception {
		QuantityMeasurementCacheRepository repository = QuantityMeasurementCacheRepository.getInstance();

		QuantityModel<LengthUnit> first = new QuantityModel<>(10.0, LengthUnit.FEET);
		QuantityModel<LengthUnit> second = new QuantityModel<>(6.0, LengthUnit.INCH);

		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(first, second, "SUBTRACT", 9.5);

		repository.save(entity);

		/*
		 * Reset singleton so repository is created again.
		 */
		Field instanceField = QuantityMeasurementCacheRepository.class.getDeclaredField("instance");
		instanceField.setAccessible(true);
		instanceField.set(null, null);

		QuantityMeasurementCacheRepository recreatedRepository = QuantityMeasurementCacheRepository.getInstance();
		List<QuantityMeasurementEntity> loadedMeasurements = recreatedRepository.getAllMeasurements();

		assertEquals(1, loadedMeasurements.size());
		assertEquals(entity, loadedMeasurements.get(0));
	}
}