package com.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class VolumeTest {
	private static final double EPSILON = 1e-6;

	@Test
	void testEquality_LitreToLitre_SameValue() {
		assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE).equals(new Quantity<>(1.0, VolumeUnit.LITRE)));
	}

	@Test
	void testEquality_LitreToLitre_DifferentValue() {
		assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE).equals(new Quantity<>(2.0, VolumeUnit.LITRE)));
	}

	@Test
	void testEquality_LitreToMillilitre() {
		assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE).equals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)));
	}

	@Test
	void testEquality_LitreToGallon() {
		assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE).equals(new Quantity<>(0.264172, VolumeUnit.GALLON)));
	}

	@Test
	void testEquality_ZeroValue() {
		assertTrue(new Quantity<>(0.0, VolumeUnit.LITRE).equals(new Quantity<>(0.0, VolumeUnit.MILLILITRE)));
	}

	@Test
	void testEquality_NegativeValue() {
		assertTrue(new Quantity<>(-1.0, VolumeUnit.LITRE).equals(new Quantity<>(-1000.0, VolumeUnit.MILLILITRE)));
	}

	@Test
	void testEquality_SameReference() {
		Quantity<VolumeUnit> v = new Quantity<>(1.0, VolumeUnit.LITRE);
		assertTrue(v.equals(v));
	}

	@Test
	void testEquality_NullComparison() {
		assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE).equals(null));
	}

	@Test
	void testEquality_VolumeVsLength() {
		assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE).equals(new Quantity<>(1.0, LengthUnit.FEET)));
	}

	@Test
	void testEquality_VolumeVsWeight() {
		assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE).equals(new Quantity<>(1.0, WeightUnit.KILOGRAM)));
	}

	@Test
	void testConversion_LitreToMillilitre() {
		Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE).convertTo(VolumeUnit.MILLILITRE);

		assertEquals(1000.0, result.getValue(), EPSILON);
	}

	@Test
	void testConversion_MillilitreToLitre() {
		Quantity<VolumeUnit> result = new Quantity<>(1000.0, VolumeUnit.MILLILITRE).convertTo(VolumeUnit.LITRE);

		assertEquals(1.0, result.getValue(), EPSILON);
	}

	@Test
	void testConversion_GallonToLitre() {
		Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.GALLON).convertTo(VolumeUnit.LITRE);

		assertEquals(3.78541, result.getValue(), EPSILON);
	}

	@Test
	void testConversion_LitreToGallon() {
		Quantity<VolumeUnit> result = new Quantity<>(3.78541, VolumeUnit.LITRE).convertTo(VolumeUnit.GALLON);

		assertEquals(1.0, result.getValue(), EPSILON);
	}

	@Test
	void testConversion_RoundTrip() {
		Quantity<VolumeUnit> original = new Quantity<>(1.5, VolumeUnit.LITRE);

		Quantity<VolumeUnit> roundTrip = original.convertTo(VolumeUnit.MILLILITRE).convertTo(VolumeUnit.LITRE);

		assertEquals(original.getValue(), roundTrip.getValue(), EPSILON);
	}

	@Test
	void testAddition_SameUnit() {
		Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE).add(new Quantity<>(2.0, VolumeUnit.LITRE));

		assertEquals(3.0, result.getValue(), EPSILON);
	}

	@Test
	void testAddition_CrossUnit() {
		Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
				.add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE));

		assertEquals(2.0, result.getValue(), EPSILON);
	}

	@Test
	void testAddition_ExplicitTarget_Millilitre() {
		Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
				.add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), VolumeUnit.MILLILITRE);

		assertEquals(2000.0, result.getValue(), EPSILON);
	}

	@Test
	void testAddition_GallonPlusLitre() {
		Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.GALLON)
				.add(new Quantity<>(3.78541, VolumeUnit.LITRE), VolumeUnit.GALLON);

		assertEquals(2.0, result.getValue(), EPSILON);
	}

	@Test
	void testAddition_WithZero() {
		Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE)
				.add(new Quantity<>(0.0, VolumeUnit.MILLILITRE));

		assertEquals(5.0, result.getValue(), EPSILON);
	}

	@Test
	void testAddition_NegativeValues() {
		Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE)
				.add(new Quantity<>(-2000.0, VolumeUnit.MILLILITRE));

		assertEquals(3.0, result.getValue(), EPSILON);
	}

	@Test
	void testVolumeUnit_LitreFactor() {
		assertEquals(1.0, VolumeUnit.LITRE.getConversionFactor(), EPSILON);
	}

	@Test
	void testVolumeUnit_MillilitreFactor() {
		assertEquals(0.001, VolumeUnit.MILLILITRE.getConversionFactor(), EPSILON);
	}

	@Test
	void testVolumeUnit_GallonFactor() {
		assertEquals(3.78541, VolumeUnit.GALLON.getConversionFactor(), EPSILON);
	}
}