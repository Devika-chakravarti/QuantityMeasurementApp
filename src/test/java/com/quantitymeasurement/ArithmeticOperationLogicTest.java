package com.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ArithmeticOperationLogicTest {

	private static final double EPSILON = 1e-6;

	// ---------------- Behavior preserved: ADD ----------------

	@Test
	void testAdd_CrossUnit_Length() {
		Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.FEET).add(new Quantity<>(12.0, LengthUnit.INCH));

		assertEquals(2.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	void testAdd_ExplicitTarget_Weight_Gram() {
		Quantity<WeightUnit> result = new Quantity<>(10.0, WeightUnit.KILOGRAM)
				.add(new Quantity<>(5000.0, WeightUnit.GRAM), WeightUnit.GRAM);

		assertEquals(15000.0, result.getValue(), EPSILON);
		assertEquals(WeightUnit.GRAM, result.getUnit());
	}

	// ---------------- Behavior preserved: SUBTRACT ----------------

	@Test
	void testSubtract_CrossUnit_Length_ImplicitFeet() {
		Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
				.subtract(new Quantity<>(6.0, LengthUnit.INCH));

		assertEquals(9.5, result.getValue(), EPSILON);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	void testSubtract_ExplicitTarget_Volume_MilliLitre() {
		Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE)
				.subtract(new Quantity<>(2.0, VolumeUnit.LITRE), VolumeUnit.MILLILITRE);

		assertEquals(3000.0, result.getValue(), EPSILON);
		assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
	}

	@Test
	void testSubtract_NegativeResult() {
		Quantity<WeightUnit> result = new Quantity<>(2.0, WeightUnit.KILOGRAM)
				.subtract(new Quantity<>(5.0, WeightUnit.KILOGRAM));

		assertEquals(-3.0, result.getValue(), EPSILON);
	}

	// ---------------- Behavior preserved: DIVIDE ----------------

	@Test
	void testDivide_SameUnit() {
		double ratio = new Quantity<>(10.0, WeightUnit.KILOGRAM).divide(new Quantity<>(5.0, WeightUnit.KILOGRAM));

		assertEquals(2.0, ratio, EPSILON);
	}

	@Test
	void testDivide_CrossUnit_Length_EqualsOne() {
		double ratio = new Quantity<>(24.0, LengthUnit.INCH).divide(new Quantity<>(2.0, LengthUnit.FEET));

		assertEquals(1.0, ratio, EPSILON);
	}

	@Test
	void testDivide_ByZero_Throws() {
		assertThrows(ArithmeticException.class,
				() -> new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(0.0, LengthUnit.FEET)));
	}

	// ---------------- Validation consistency ----------------

	@Test
	void testValidation_NullOperand_Add_Subtract_Divide() {
		Quantity<LengthUnit> q = new Quantity<>(10.0, LengthUnit.FEET);

		IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> q.add(null));
		IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> q.subtract(null));
		IllegalArgumentException ex3 = assertThrows(IllegalArgumentException.class, () -> q.divide(null));

		assertEquals("Other quantity cannot be null", ex1.getMessage());
		assertEquals("Other quantity cannot be null", ex2.getMessage());
		assertEquals("Other quantity cannot be null", ex3.getMessage());
	}

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void testValidation_CrossCategory_Consistent() {
		Quantity rawLength = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity rawWeight = new Quantity<>(5.0, WeightUnit.KILOGRAM);

		IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> rawLength.add(rawWeight));
		IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class,
				() -> rawLength.subtract(rawWeight));
		IllegalArgumentException ex3 = assertThrows(IllegalArgumentException.class, () -> rawLength.divide(rawWeight));

		assertEquals("Cross-category operation is not allowed", ex1.getMessage());
		assertEquals("Cross-category operation is not allowed", ex2.getMessage());
		assertEquals("Cross-category operation is not allowed", ex3.getMessage());
	}

	@Test
	void testValidation_NullTargetUnit_AddSubtract() {
		Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> b = new Quantity<>(500.0, VolumeUnit.MILLILITRE);

		IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> a.add(b, null));
		IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> a.subtract(b, null));

		assertEquals("Target unit cannot be null", ex1.getMessage());
		assertEquals("Target unit cannot be null", ex2.getMessage());
	}

	// ---------------- Immutability sanity ----------------

	@Test
	void testImmutability_AfterOperations() {
		Quantity<VolumeUnit> a = new Quantity<>(5.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> b = new Quantity<>(500.0, VolumeUnit.MILLILITRE);

		Quantity<VolumeUnit> sub = a.subtract(b);
		double div = a.divide(new Quantity<>(10.0, VolumeUnit.LITRE));

		assertEquals(5.0, a.getValue(), EPSILON);
		assertEquals(500.0, b.getValue(), EPSILON);

		assertEquals(4.5, sub.getValue(), EPSILON);
		assertEquals(0.5, div, EPSILON);
	}
}