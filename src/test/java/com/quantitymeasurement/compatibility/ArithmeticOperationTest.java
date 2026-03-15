package com.quantitymeasurement.compatibility;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.quantitymeasurement.core.LengthUnit;
import com.quantitymeasurement.core.Quantity;
import com.quantitymeasurement.core.VolumeUnit;
import com.quantitymeasurement.core.WeightUnit;

/*
 * ArithmeticOperationTest:
 * ------------------------
 * This test class validates subtraction and division behavior
 * for the generic Quantity class.
 *
 * It checks:
 * - length, weight, and volume arithmetic
 * - negative and zero results
 * - non-commutative behavior
 * - null handling
 * - cross-category safety
 * - divide-by-zero handling
 * - basic immutability
 */
public class ArithmeticOperationTest {

	private static final double EPSILON = 1e-6;

	/*
	 * SUBTRACTION: LENGTH ------------------- Feet minus inches, result in feet.
	 */
	@Test
	void testSubtraction_CrossUnit_FeetMinusInches_ImplicitTargetFeet() {
		Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
				.subtract(new Quantity<>(6.0, LengthUnit.INCH));
		assertEquals(9.5, result.getValue(), EPSILON);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	/*
	 * SUBTRACTION: LENGTH ------------------- Feet minus inches, result in inches.
	 */
	@Test
	void testSubtraction_CrossUnit_FeetMinusInches_ExplicitTargetInch() {
		Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
				.subtract(new Quantity<>(6.0, LengthUnit.INCH), LengthUnit.INCH);
		assertEquals(114.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.INCH, result.getUnit());
	}

	/*
	 * SUBTRACTION: LENGTH ------------------- Negative result case.
	 */
	@Test
	void testSubtraction_ResultNegative() {
		Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
				.subtract(new Quantity<>(10.0, LengthUnit.FEET));
		assertEquals(-5.0, result.getValue(), EPSILON);
	}

	/*
	 * SUBTRACTION: LENGTH ------------------- Zero result across different units.
	 */
	@Test
	void testSubtraction_ResultZero_CrossUnit() {
		Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
				.subtract(new Quantity<>(120.0, LengthUnit.INCH));
		assertEquals(0.0, result.getValue(), EPSILON);
	}

	/*
	 * SUBTRACTION: LENGTH ------------------- Identity subtraction with zero.
	 */
	@Test
	void testSubtraction_IdentitySubtractZero() {
		Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
				.subtract(new Quantity<>(0.0, LengthUnit.INCH));
		assertEquals(5.0, result.getValue(), EPSILON);
	}

	/*
	 * SUBTRACTION: LENGTH ------------------- Subtraction should not be
	 * commutative.
	 */
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

	/*
	 * SUBTRACTION: WEIGHT ------------------- Kilogram minus gram, result in
	 * kilogram.
	 */
	@Test
	void testSubtraction_Weight_KgMinusGram_ImplicitKg() {
		Quantity<WeightUnit> result = new Quantity<>(10.0, WeightUnit.KILOGRAM)
				.subtract(new Quantity<>(5000.0, WeightUnit.GRAM));
		assertEquals(5.0, result.getValue(), EPSILON);
		assertEquals(WeightUnit.KILOGRAM, result.getUnit());
	}

	/*
	 * SUBTRACTION: WEIGHT ------------------- Kilogram minus gram, result in gram.
	 */
	@Test
	void testSubtraction_Weight_KgMinusGram_ExplicitGram() {
		Quantity<WeightUnit> result = new Quantity<>(10.0, WeightUnit.KILOGRAM)
				.subtract(new Quantity<>(5000.0, WeightUnit.GRAM), WeightUnit.GRAM);
		assertEquals(5000.0, result.getValue(), EPSILON);
		assertEquals(WeightUnit.GRAM, result.getUnit());
	}

	/*
	 * SUBTRACTION: VOLUME ------------------- Litre minus millilitre, result in
	 * litre.
	 */
	@Test
	void testSubtraction_Volume_LitreMinusMilli_ImplicitLitre() {
		Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE)
				.subtract(new Quantity<>(500.0, VolumeUnit.MILLILITRE));
		assertEquals(4.5, result.getValue(), EPSILON);
		assertEquals(VolumeUnit.LITRE, result.getUnit());
	}

	/*
	 * SUBTRACTION: VOLUME ------------------- Litre minus litre, result in
	 * millilitre.
	 */
	@Test
	void testSubtraction_Volume_ExplicitMillilitre() {
		Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE)
				.subtract(new Quantity<>(2.0, VolumeUnit.LITRE), VolumeUnit.MILLILITRE);
		assertEquals(3000.0, result.getValue(), EPSILON);
		assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
	}

	/*
	 * DIVISION: LENGTH ---------------- Same-unit division.
	 */
	@Test
	void testDivision_SameUnit() {
		double ratio = new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(2.0, LengthUnit.FEET));
		assertEquals(5.0, ratio, EPSILON);
	}

	/*
	 * DIVISION: LENGTH ---------------- Cross-unit division where ratio is one.
	 */
	@Test
	void testDivision_CrossUnit_InchesByFeet_EqualsOne() {
		double ratio = new Quantity<>(24.0, LengthUnit.INCH).divide(new Quantity<>(2.0, LengthUnit.FEET));
		assertEquals(1.0, ratio, EPSILON);
	}

	/*
	 * DIVISION: LENGTH ---------------- Ratio less than one.
	 */
	@Test
	void testDivision_RatioLessThanOne() {
		double ratio = new Quantity<>(5.0, LengthUnit.FEET).divide(new Quantity<>(10.0, LengthUnit.FEET));
		assertEquals(0.5, ratio, EPSILON);
	}

	/*
	 * DIVISION: LENGTH ---------------- Division should not be commutative.
	 */
	@Test
	void testDivision_NonCommutative() {
		double aByB = new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(5.0, LengthUnit.FEET));
		double bByA = new Quantity<>(5.0, LengthUnit.FEET).divide(new Quantity<>(10.0, LengthUnit.FEET));
		assertEquals(2.0, aByB, EPSILON);
		assertEquals(0.5, bByA, EPSILON);
	}

	/*
	 * DIVISION: WEIGHT ---------------- Cross-unit ratio where result is one.
	 */
	@Test
	void testDivision_Weight_CrossUnit_KgByGram_EqualsOne() {
		double ratio = new Quantity<>(2.0, WeightUnit.KILOGRAM).divide(new Quantity<>(2000.0, WeightUnit.GRAM));
		assertEquals(1.0, ratio, EPSILON);
	}

	/*
	 * DIVISION: VOLUME ---------------- Cross-unit ratio where result is one.
	 */
	@Test
	void testDivision_Volume_MilliByLitre_EqualsOne() {
		double ratio = new Quantity<>(1000.0, VolumeUnit.MILLILITRE).divide(new Quantity<>(1.0, VolumeUnit.LITRE));
		assertEquals(1.0, ratio, EPSILON);
	}

	/*
	 * ERROR CASE ---------- Null operand for subtraction.
	 */
	@Test
	void testSubtract_NullOperand_Throws() {
		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(10.0, LengthUnit.FEET).subtract(null));
	}

	/*
	 * ERROR CASE ---------- Null target unit for subtraction.
	 */
	@Test
	void testSubtract_NullTarget_Throws() {
		assertThrows(IllegalArgumentException.class,
				() -> new Quantity<>(10.0, LengthUnit.FEET).subtract(new Quantity<>(5.0, LengthUnit.FEET), null));
	}

	/*
	 * ERROR CASE ---------- Null operand for division.
	 */
	@Test
	void testDivide_NullOperand_Throws() {
		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(10.0, LengthUnit.FEET).divide(null));
	}

	/*
	 * ERROR CASE ---------- Divide by zero.
	 */
	@Test
	void testDivide_ByZero_Throws() {
		assertThrows(ArithmeticException.class,
				() -> new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(0.0, LengthUnit.FEET)));
	}

	/*
	 * ERROR CASE ---------- Cross-category subtraction must fail.
	 */
	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void testCrossCategory_Subtract_Throws() {
		Quantity rawLength = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity rawWeight = new Quantity<>(5.0, WeightUnit.KILOGRAM);

		assertThrows(IllegalArgumentException.class, () -> rawLength.subtract(rawWeight));
	}

	/*
	 * ERROR CASE ---------- Cross-category division must fail.
	 */
	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void testCrossCategory_Divide_Throws() {
		Quantity rawLength = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity rawWeight = new Quantity<>(5.0, WeightUnit.KILOGRAM);

		assertThrows(IllegalArgumentException.class, () -> rawLength.divide(rawWeight));
	}

	/*
	 * IMMUTABILITY ------------ Original objects should remain unchanged after
	 * subtraction.
	 */
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