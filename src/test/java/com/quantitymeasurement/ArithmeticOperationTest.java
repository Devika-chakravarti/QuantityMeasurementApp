package com.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ArithmeticOperationTest {

	private static final double EPSILON = 1e-6;

	// ---------------- SUBTRACTION: LENGTH ----------------

	@Test
	void testSubtraction_CrossUnit_FeetMinusInches_ImplicitTargetFeet() {
		Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
				.subtract(new Quantity<>(6.0, LengthUnit.INCH));
		assertEquals(9.5, result.getValue(), EPSILON);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	void testSubtraction_CrossUnit_FeetMinusInches_ExplicitTargetInch() {
		Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
				.subtract(new Quantity<>(6.0, LengthUnit.INCH), LengthUnit.INCH);
		assertEquals(114.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.INCH, result.getUnit());
	}

	@Test
	void testSubtraction_ResultNegative() {
		Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
				.subtract(new Quantity<>(10.0, LengthUnit.FEET));
		assertEquals(-5.0, result.getValue(), EPSILON);
	}

	@Test
	void testSubtraction_ResultZero_CrossUnit() {
		Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
				.subtract(new Quantity<>(120.0, LengthUnit.INCH));
		assertEquals(0.0, result.getValue(), EPSILON);
	}

	@Test
	void testSubtraction_IdentitySubtractZero() {
		Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
				.subtract(new Quantity<>(0.0, LengthUnit.INCH));
		assertEquals(5.0, result.getValue(), EPSILON);
	}

	@Test
	void testSubtraction_NonCommutative() {
		Quantity<LengthUnit> aMinusB = new Quantity<>(10.0, LengthUnit.FEET)
				.subtract(new Quantity<>(5.0, LengthUnit.FEET));
		Quantity<LengthUnit> bMinusA = new Quantity<>(5.0, LengthUnit.FEET)
				.subtract(new Quantity<>(10.0, LengthUnit.FEET));
		assertNotEquals(aMinusB.getValue(), bMinusA.getValue(), EPSILON);
		assertEquals(5.0, aMinusB.getValue(), EPSILON);
		assertEquals(-5.0, bMinusA.getValue(), EPSILON);
	}

	// ---------------- SUBTRACTION: WEIGHT ----------------

	@Test
	void testSubtraction_Weight_KgMinusGram_ImplicitKg() {
		Quantity<WeightUnit> result = new Quantity<>(10.0, WeightUnit.KILOGRAM)
				.subtract(new Quantity<>(5000.0, WeightUnit.GRAM));
		assertEquals(5.0, result.getValue(), EPSILON);
		assertEquals(WeightUnit.KILOGRAM, result.getUnit());
	}

	@Test
	void testSubtraction_Weight_KgMinusGram_ExplicitGram() {
		Quantity<WeightUnit> result = new Quantity<>(10.0, WeightUnit.KILOGRAM)
				.subtract(new Quantity<>(5000.0, WeightUnit.GRAM), WeightUnit.GRAM);
		assertEquals(5000.0, result.getValue(), EPSILON);
		assertEquals(WeightUnit.GRAM, result.getUnit());
	}

	// ---------------- SUBTRACTION: VOLUME ----------------

	@Test
	void testSubtraction_Volume_LitreMinusMilli_ImplicitLitre() {
		Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE)
				.subtract(new Quantity<>(500.0, VolumeUnit.MILLILITRE));
		assertEquals(4.5, result.getValue(), EPSILON);
		assertEquals(VolumeUnit.LITRE, result.getUnit());
	}

	@Test
	void testSubtraction_Volume_ExplicitMillilitre() {
		Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE)
				.subtract(new Quantity<>(2.0, VolumeUnit.LITRE), VolumeUnit.MILLILITRE);
		assertEquals(3000.0, result.getValue(), EPSILON);
		assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
	}

	// ---------------- DIVISION: LENGTH ----------------

	@Test
	void testDivision_SameUnit() {
		double ratio = new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(2.0, LengthUnit.FEET));
		assertEquals(5.0, ratio, EPSILON);
	}

	@Test
	void testDivision_CrossUnit_InchesByFeet_EqualsOne() {
		double ratio = new Quantity<>(24.0, LengthUnit.INCH).divide(new Quantity<>(2.0, LengthUnit.FEET));
		assertEquals(1.0, ratio, EPSILON);
	}

	@Test
	void testDivision_RatioLessThanOne() {
		double ratio = new Quantity<>(5.0, LengthUnit.FEET).divide(new Quantity<>(10.0, LengthUnit.FEET));
		assertEquals(0.5, ratio, EPSILON);
	}

	@Test
	void testDivision_NonCommutative() {
		double aByB = new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(5.0, LengthUnit.FEET));
		double bByA = new Quantity<>(5.0, LengthUnit.FEET).divide(new Quantity<>(10.0, LengthUnit.FEET));
		assertEquals(2.0, aByB, EPSILON);
		assertEquals(0.5, bByA, EPSILON);
	}

	// ---------------- DIVISION: WEIGHT & VOLUME ----------------

	@Test
	void testDivision_Weight_CrossUnit_KgByGram_EqualsOne() {
		double ratio = new Quantity<>(2.0, WeightUnit.KILOGRAM).divide(new Quantity<>(2000.0, WeightUnit.GRAM));
		assertEquals(1.0, ratio, EPSILON);
	}

	@Test
	void testDivision_Volume_MilliByLitre_EqualsOne() {
		double ratio = new Quantity<>(1000.0, VolumeUnit.MILLILITRE).divide(new Quantity<>(1.0, VolumeUnit.LITRE));
		assertEquals(1.0, ratio, EPSILON);
	}

	// ---------------- ERROR CASES ----------------

	@Test
	void testSubtract_NullOperand_Throws() {
		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(10.0, LengthUnit.FEET).subtract(null));
	}

	@Test
	void testSubtract_NullTarget_Throws() {
		assertThrows(IllegalArgumentException.class,
				() -> new Quantity<>(10.0, LengthUnit.FEET).subtract(new Quantity<>(5.0, LengthUnit.FEET), null));
	}

	@Test
	void testDivide_NullOperand_Throws() {
		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(10.0, LengthUnit.FEET).divide(null));
	}

	@Test
	void testDivide_ByZero_Throws() {
		assertThrows(ArithmeticException.class,
				() -> new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(0.0, LengthUnit.FEET)));
	}

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void testCrossCategory_Subtract_Throws() {
		Quantity rawLength = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity rawWeight = new Quantity<>(5.0, WeightUnit.KILOGRAM);

		assertThrows(IllegalArgumentException.class, () -> rawLength.subtract(rawWeight));
	}

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void testCrossCategory_Divide_Throws() {
		Quantity rawLength = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity rawWeight = new Quantity<>(5.0, WeightUnit.KILOGRAM);

		assertThrows(IllegalArgumentException.class, () -> rawLength.divide(rawWeight));
	}

	// ---------------- IMMUTABILITY (basic) ----------------

	@Test
	void testImmutability_Subtract_DoesNotChangeOriginals() {
		Quantity<VolumeUnit> a = new Quantity<>(5.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> b = new Quantity<>(500.0, VolumeUnit.MILLILITRE);

		Quantity<VolumeUnit> result = a.subtract(b);

		assertEquals(5.0, a.getValue(), EPSILON);
		assertEquals(500.0, b.getValue(), EPSILON);
		assertEquals(4.5, result.getValue(), EPSILON);
	}
}