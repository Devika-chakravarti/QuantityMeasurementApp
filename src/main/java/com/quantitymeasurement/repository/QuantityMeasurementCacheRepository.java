package com.quantitymeasurement.repository;

import java.util.List;

import com.quantitymeasurement.entity.QuantityMeasurementEntity;

/**
 * QuantityMeasurementCacheRepository does the following: 1. Implements the
 * IQuantityMeasurementRepository interface, providing concrete implementations
 * for the defined methods 2. Maintains an in-memory cache of
 * QuantityMeasurementEntity objects for quick access 3. Provides a singleton
 * instance of the repository to ensure a single point of access 4. Handles
 * persistence by saving entities to disk and loading them back into memory when
 * the repository is initialized 5. Uses a custom AppendableObjectOutputStream
 * to prevent writing a header when appending to an existing file, ensuring the
 * integrity of the data file for sequential reads 6. Provides a method to
 * retrieve all stored QuantityMeasurementEntity instances from the cache 7.
 * Serves as the data access layer for the application, abstracting away
 * database interactions and providing a clean interface for managing quantity
 * measurement data. 8. Can be expanded in the future to include additional
 * methods for retrieving, updating, and deleting entities as needed.
 */
public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {
	// In-memory cache to store QuantityMeasurementEntity objects for quick access
	// inside the file system.
	public static final String FILE_NAME = "quantity_measurement_repo.ser";

	// Holds the cached QuantityMeasurementEntity objects in memory for quick
	// access.
	List<QuantityMeasurementEntity> quantityMeasurementEntityCache;

	// Singleton instance of the repository.
	private static QuantityMeasurementCacheRepository instance;

	/**
	 * Custom ObjectOutputStream that doesn't write a header when appending to an
	 * existing file. This is necessary to avoid corrupting the file when multiple
	 * objects are written sequentially. The header is only written if the file is
	 * new or empty. If the file already exists and has content, the stream is reset
	 * instead of writing a new header.
	 */
	class AppendableObjectOutputStream extends java.io.ObjectOutputStream {
		public AppendableObjectOutputStream(java.io.OutputStream out) throws java.io.IOException {
			super(out);
		}

		@Override
		protected void writeStreamHeader() throws java.io.IOException {
			// Don't write header when appending to existing file
			// Only write header if file is new/empty
			java.io.File file = new java.io.File(QuantityMeasurementCacheRepository.FILE_NAME);
			if (!file.exists() || file.length() == 0) {
				super.writeStreamHeader();
			} else {
				reset(); // Just reset instead of writing header
			}
		}
	}

	// Private constructor to prevent instantiation from outside the class
	private QuantityMeasurementCacheRepository() {
		// Initialize the in-memory cache
		quantityMeasurementEntityCache = new java.util.ArrayList<>();

		// Load any existing data from disk
		loadFromDisk();
	}

	/**
	 * Get the singleton instance of the QuantityMeasurementCacheRepository.
	 *
	 * @return the singleton instance of the repository
	 */
	public static QuantityMeasurementCacheRepository getInstance() {
		if (instance == null) {
			instance = new QuantityMeasurementCacheRepository();
		}
		return instance;
	}

	@Override
	public void save(QuantityMeasurementEntity entity) {
		quantityMeasurementEntityCache.add(entity);
		saveToDisk(entity);
	}

	@Override
	public List<QuantityMeasurementEntity> getAllMeasurements() {
		return quantityMeasurementEntityCache;
	}

	/**
	 * Saves a QuantityMeasurementEntity to disk by appending it to the existing
	 * file.
	 *
	 * This method is called internally by the save method after adding the entity
	 * to the in-memory cache, ensuring that the entity is both cached for quick
	 * access and persisted to disk for long-term storage.
	 *
	 * The saveToDisk method appends the new entity to the existing file without
	 * overwriting previous data, ensuring that all entities are preserved. The
	 * AppendableObjectOutputStream is used to prevent writing a header when
	 * appending to an existing file, maintaining the integrity of the data file for
	 * sequential reads.
	 */
	private void saveToDisk(QuantityMeasurementEntity entity) {
		// Append the new entity to the existing file without overwriting previous data
		try (java.io.FileOutputStream fos = new java.io.FileOutputStream(FILE_NAME, true);
				AppendableObjectOutputStream oos = new AppendableObjectOutputStream(fos)) {
			oos.writeObject(entity);
		} catch (java.io.IOException e) {
			System.err.println("Error saving entity: " + e.getMessage());
		}
	}

	// Method to load the in-memory cache from disk when the repository is
	// initialized
	private void loadFromDisk() {
		java.io.File file = new java.io.File(FILE_NAME);
		if (file.exists()) {
			try (java.io.FileInputStream fis = new java.io.FileInputStream(FILE_NAME);
					java.io.ObjectInputStream ois = new java.io.ObjectInputStream(fis)) {
				while (true) {
					try {
						QuantityMeasurementEntity entity = (QuantityMeasurementEntity) ois.readObject();
						quantityMeasurementEntityCache.add(entity);
					} catch (java.io.EOFException e) {
						// End of file reached
						break;
					}
				}
				System.out.println("Loaded " + quantityMeasurementEntityCache.size()
						+ " quantity measurement entities from storage");
			} catch (java.io.IOException | ClassNotFoundException ex) {
				System.err.println("Error loading quantity measurement entities: " + ex.getMessage());
			}
		}
	}

	// Main method for testing purposes
	public static void main(String[] args) {
	}
}