package com.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdditionTest {

	private static final double EPSILON = 1e-6;

	@Test
	void givenFeetAndInch_WhenAdded_ResultInFeet() {

		QuantityLength oneFoot = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength twelveInch = new QuantityLength(12.0, LengthUnit.INCH);
		QuantityLength result = oneFoot.add(twelveInch, LengthUnit.FEET);
		assertEquals(2.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	void givenFeetAndInch_WhenAdded_ResultInInch() {

		QuantityLength oneFoot = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength twelveInch = new QuantityLength(12.0, LengthUnit.INCH);
		QuantityLength result = oneFoot.add(twelveInch, LengthUnit.INCH);
		assertEquals(24.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.INCH, result.getUnit());
	}

	@Test
	void givenYardAndFeet_WhenAdded_ResultInYard() {

		QuantityLength oneYard = new QuantityLength(1.0, LengthUnit.YARDS);
		QuantityLength threeFeet = new QuantityLength(3.0, LengthUnit.FEET);
		QuantityLength result = oneYard.add(threeFeet, LengthUnit.YARDS);
		assertEquals(2.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.YARDS, result.getUnit());
	}

	@Test
	void givenCentimeterAndInch_WhenAdded_ResultInInch() {

		QuantityLength cm = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
		QuantityLength inch = new QuantityLength(1.0, LengthUnit.INCH);
		QuantityLength result = cm.add(inch, LengthUnit.INCH);
		assertEquals(2.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.INCH, result.getUnit());
	}

	@Test
	void givenNullQuantity_WhenAdded_ShouldThrowException() {

		QuantityLength oneFoot = new QuantityLength(1.0, LengthUnit.FEET);
		assertThrows(NullPointerException.class, () -> oneFoot.add(null, LengthUnit.FEET));
	}

	@Test
	void givenNullResultUnit_WhenAdded_ShouldThrowException() {

		QuantityLength oneFoot = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength oneInch = new QuantityLength(1.0, LengthUnit.INCH);
		assertThrows(NullPointerException.class, () -> oneFoot.add(oneInch, null));
	}
}