package com.quantitymeasurement.compatibility;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.quantitymeasurement.core.LengthUnit;
import com.quantitymeasurement.core.Quantity;
import com.quantitymeasurement.core.VolumeUnit;
import com.quantitymeasurement.core.WeightUnit;

/*
 * VolumeTest:
 * -----------
 * This test class checks the generic Quantity class for VolumeUnit.
 *
 * It verifies:
 * - equality across volume units
 * - conversions across volume units
 * - round-trip conversion
 * - addition with same and different units
 * - explicit target unit addition
 * - zero and negative value handling
 * - volume unit conversion factors
 */
public class VolumeTest {

	private static final double EPSILON = 1e-6;

	/*
	 * Checks equality for same unit and same value.
	 */
	@Test
	void testEquality_LitreToLitre_SameValue() {
		assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE).equals(new Quantity<>(1.0, VolumeUnit.LITRE)));
	}

	/*
	 * Checks inequality for same unit but different value.
	 */
	@Test
	void testEquality_LitreToLitre_DifferentValue() {
		assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE).equals(new Quantity<>(2.0, VolumeUnit.LITRE)));
	}

	/*
	 * Checks equality across Litre and Millilitre.
	 */
	@Test
	void testEquality_LitreToMillilitre() {
		assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE).equals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)));
	}

	/*
	 * Checks equality across Litre and Gallon.
	 */
	@Test
	void testEquality_LitreToGallon() {
		assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE).equals(new Quantity<>(0.264172, VolumeUnit.GALLON)));
	}

	/*
	 * Checks equality for zero values.
	 */
	@Test
	void testEquality_ZeroValue() {
		assertTrue(new Quantity<>(0.0, VolumeUnit.LITRE).equals(new Quantity<>(0.0, VolumeUnit.MILLILITRE)));
	}

	/*
	 * Checks equality for negative values.
	 */
	@Test
	void testEquality_NegativeValue() {
		assertTrue(new Quantity<>(-1.0, VolumeUnit.LITRE).equals(new Quantity<>(-1000.0, VolumeUnit.MILLILITRE)));
	}

	/*
	 * Checks reflexive equality.
	 */
	@Test
	void testEquality_SameReference() {
		Quantity<VolumeUnit> v = new Quantity<>(1.0, VolumeUnit.LITRE);
		assertTrue(v.equals(v));
	}

	/*
	 * Checks comparison with null.
	 */
	@Test
	void testEquality_NullComparison() {
		assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE).equals(null));
	}

	/*
	 * Checks equality against length category.
	 */
	@Test
	void testEquality_VolumeVsLength() {
		assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE).equals(new Quantity<>(1.0, LengthUnit.FEET)));
	}

	/*
	 * Checks equality against weight category.
	 */
	@Test
	void testEquality_VolumeVsWeight() {
		assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE).equals(new Quantity<>(1.0, WeightUnit.KILOGRAM)));
	}

	/*
	 * Checks conversion from Litre to Millilitre.
	 */
	@Test
	void testConversion_LitreToMillilitre() {
		Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE).convertTo(VolumeUnit.MILLILITRE);

		assertEquals(1000.0, result.getValue(), EPSILON);
	}

	/*
	 * Checks conversion from Millilitre to Litre.
	 */
	@Test
	void testConversion_MillilitreToLitre() {
		Quantity<VolumeUnit> result = new Quantity<>(1000.0, VolumeUnit.MILLILITRE).convertTo(VolumeUnit.LITRE);

		assertEquals(1.0, result.getValue(), EPSILON);
	}

	/*
	 * Checks conversion from Gallon to Litre.
	 */
	@Test
	void testConversion_GallonToLitre() {
		Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.GALLON).convertTo(VolumeUnit.LITRE);

		assertEquals(3.78541, result.getValue(), EPSILON);
	}

	/*
	 * Checks conversion from Litre to Gallon.
	 */
	@Test
	void testConversion_LitreToGallon() {
		Quantity<VolumeUnit> result = new Quantity<>(3.78541, VolumeUnit.LITRE).convertTo(VolumeUnit.GALLON);

		assertEquals(1.0, result.getValue(), EPSILON);
	}

	/*
	 * Checks round-trip conversion.
	 */
	@Test
	void testConversion_RoundTrip() {
		Quantity<VolumeUnit> original = new Quantity<>(1.5, VolumeUnit.LITRE);

		Quantity<VolumeUnit> roundTrip = original.convertTo(VolumeUnit.MILLILITRE).convertTo(VolumeUnit.LITRE);

		assertEquals(original.getValue(), roundTrip.getValue(), EPSILON);
	}

	/*
	 * Checks addition with same unit.
	 */
	@Test
	void testAddition_SameUnit() {
		Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE).add(new Quantity<>(2.0, VolumeUnit.LITRE));

		assertEquals(3.0, result.getValue(), EPSILON);
	}

	/*
	 * Checks addition across Litre and Millilitre.
	 */
	@Test
	void testAddition_CrossUnit() {
		Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
				.add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE));

		assertEquals(2.0, result.getValue(), EPSILON);
	}

	/*
	 * Checks addition with explicit target Millilitre.
	 */
	@Test
	void testAddition_ExplicitTarget_Millilitre() {
		Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
				.add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), VolumeUnit.MILLILITRE);

		assertEquals(2000.0, result.getValue(), EPSILON);
	}

	/*
	 * Checks addition across Gallon and Litre.
	 */
	@Test
	void testAddition_GallonPlusLitre() {
		Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.GALLON)
				.add(new Quantity<>(3.78541, VolumeUnit.LITRE), VolumeUnit.GALLON);

		assertEquals(2.0, result.getValue(), EPSILON);
	}

	/*
	 * Checks addition with zero value.
	 */
	@Test
	void testAddition_WithZero() {
		Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE)
				.add(new Quantity<>(0.0, VolumeUnit.MILLILITRE));

		assertEquals(5.0, result.getValue(), EPSILON);
	}

	/*
	 * Checks addition with negative value.
	 */
	@Test
	void testAddition_NegativeValues() {
		Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE)
				.add(new Quantity<>(-2000.0, VolumeUnit.MILLILITRE));

		assertEquals(3.0, result.getValue(), EPSILON);
	}

	/*
	 * Checks Litre conversion factor.
	 */
	@Test
	void testVolumeUnit_LitreFactor() {
		assertEquals(1.0, VolumeUnit.LITRE.getConversionFactor(), EPSILON);
	}

	/*
	 * Checks Millilitre conversion factor.
	 */
	@Test
	void testVolumeUnit_MillilitreFactor() {
		assertEquals(0.001, VolumeUnit.MILLILITRE.getConversionFactor(), EPSILON);
	}

	/*
	 * Checks Gallon conversion factor.
	 */
	@Test
	void testVolumeUnit_GallonFactor() {
		assertEquals(3.78541, VolumeUnit.GALLON.getConversionFactor(), EPSILON);
	}
}