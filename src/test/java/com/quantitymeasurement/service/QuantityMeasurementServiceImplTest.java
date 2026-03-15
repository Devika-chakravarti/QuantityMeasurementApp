package com.quantitymeasurement.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.quantitymeasurement.dto.QuantityDTO;
import com.quantitymeasurement.exception.QuantityMeasurementException;
import com.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.quantitymeasurement.repository.QuantityMeasurementCacheRepository;

/*
 * QuantityMeasurementServiceImplTest:
 * -----------------------------------
 * This test class checks the UC15 service layer behavior.
 *
 * It verifies:
 * - comparison
 * - conversion
 * - addition
 * - subtraction
 * - division
 * - error handling
 *
 * The service uses repository dependency,
 * so the repository object is created before each test.
 */
public class QuantityMeasurementServiceImplTest {

	private IQuantityMeasurementRepository repository;
	private IQuantityMeasurementService service;

	/*
	 * Before each test: ----------------- Create repository and service objects.
	 */
	@BeforeEach
	void setUp() {
		repository = QuantityMeasurementCacheRepository.getInstance();
		service = new QuantityMeasurementServiceImpl(repository);
	}

	/*
	 * Checks comparison for equivalent length quantities.
	 */
	@Test
	void testCompare_EqualLengthQuantities() {
		QuantityDTO oneFoot = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
		QuantityDTO twelveInches = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);

		assertTrue(service.compare(oneFoot, twelveInches));
	}

	/*
	 * Checks conversion from Feet to Inches.
	 */
	@Test
	void testConvert_LengthFeetToInches() {
		QuantityDTO oneFoot = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
		QuantityDTO target = new QuantityDTO(0.0, QuantityDTO.LengthUnit.INCHES);

		QuantityDTO result = service.convert(oneFoot, target);

		assertEquals(12.0, result.getValue(), 1e-6);
		assertEquals("INCH", result.getUnit());
	}

	/*
	 * Checks addition using default target unit.
	 */
	@Test
	void testAdd_LengthDefaultTarget() {
		QuantityDTO oneFoot = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
		QuantityDTO twelveInches = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);

		QuantityDTO result = service.add(oneFoot, twelveInches);

		assertEquals(2.0, result.getValue(), 1e-6);
		assertEquals("FEET", result.getUnit());
	}

	/*
	 * Checks addition using explicit target unit.
	 */
	@Test
	void testAdd_LengthExplicitTarget() {
		QuantityDTO oneFoot = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
		QuantityDTO twelveInches = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
		QuantityDTO target = new QuantityDTO(0.0, QuantityDTO.LengthUnit.YARDS);

		QuantityDTO result = service.add(oneFoot, twelveInches, target);

		assertEquals(0.6666666666666666, result.getValue(), 1e-6);
		assertEquals("YARDS", result.getUnit());
	}

	/*
	 * Checks subtraction using default target unit.
	 */
	@Test
	void testSubtract_LengthDefaultTarget() {
		QuantityDTO tenFeet = new QuantityDTO(10.0, QuantityDTO.LengthUnit.FEET);
		QuantityDTO sixInches = new QuantityDTO(6.0, QuantityDTO.LengthUnit.INCHES);

		QuantityDTO result = service.subtract(tenFeet, sixInches);

		assertEquals(9.5, result.getValue(), 1e-6);
		assertEquals("FEET", result.getUnit());
	}

	/*
	 * Checks division for equivalent length quantities.
	 */
	@Test
	void testDivide_LengthQuantities() {
		QuantityDTO twentyFourInches = new QuantityDTO(24.0, QuantityDTO.LengthUnit.INCHES);
		QuantityDTO twoFeet = new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET);

		double result = service.divide(twentyFourInches, twoFeet);

		assertEquals(1.0, result, 1e-6);
	}

	/*
	 * Checks error when different measurement types are used.
	 */
	@Test
	void testAdd_DifferentMeasurementTypes_Throws() {
		QuantityDTO length = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
		QuantityDTO weight = new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAMS);

		assertThrows(QuantityMeasurementException.class, () -> service.add(length, weight));
	}

	/*
	 * Checks error when dividing by zero quantity.
	 */
	@Test
	void testDivide_ByZero_Throws() {
		QuantityDTO tenFeet = new QuantityDTO(10.0, QuantityDTO.LengthUnit.FEET);
		QuantityDTO zeroFeet = new QuantityDTO(0.0, QuantityDTO.LengthUnit.FEET);

		assertThrows(QuantityMeasurementException.class, () -> service.divide(tenFeet, zeroFeet));
	}

	/*
	 * Checks unsupported arithmetic for temperature.
	 */
	@Test
	void testTemperature_Add_Throws() {
		QuantityDTO c100 = new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS);
		QuantityDTO c50 = new QuantityDTO(50.0, QuantityDTO.TemperatureUnit.CELSIUS);

		assertThrows(QuantityMeasurementException.class, () -> service.add(c100, c50));
	}
}