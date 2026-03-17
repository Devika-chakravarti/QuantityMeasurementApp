package com.app.quantitymeasurement.unit;

/**
 * VolumeUnit
 *
 * Enum representing supported volume measurement units in the Quantity
 * Measurement system.
 *
 * This enum implements IMeasurable and SupportsArithmetic, which means volume
 * units: - can be converted to and from a base unit - support arithmetic
 * operations such as addition, subtraction, and division
 *
 * Base unit for volume: - LITRE
 *
 * Supported units: - LITRE - MILLILITRE - GALLON
 *
 * Each enum constant stores: - a conversion factor relative to the base unit -
 * a display name for user-friendly output
 *
 * @author Developer
 * @version 16.0
 * @since 1.0
 */
public enum VolumeUnit implements IMeasurable, SupportsArithmetic {

	LITRE(1.0, "Litre"), MILLILITRE(0.001, "Millilitre"), GALLON(3.78541, "Gallon");

	private final double conversionFactor;
	private final String unitName;

	/**
	 * Constructs a VolumeUnit with its conversion factor and display name.
	 *
	 * @param conversionFactor factor used to convert this unit to base unit litre
	 * @param unitName         display name of the unit
	 */
	VolumeUnit(double conversionFactor, String unitName) {
		this.conversionFactor = conversionFactor;
		this.unitName = unitName;
	}

	/**
	 * Returns the conversion factor of this unit relative to the base unit.
	 *
	 * @return conversion factor
	 */
	@Override
	public double getConversionFactor() {
		return conversionFactor;
	}

	/**
	 * Converts the given value in the current unit to the base unit litre.
	 *
	 * @param value value in current unit
	 * @return equivalent value in litre
	 */
	@Override
	public double convertToBaseUnit(double value) {
		return value * conversionFactor;
	}

	/**
	 * Converts the given base unit litre value to the current unit.
	 *
	 * @param baseValue value in litre
	 * @return equivalent value in current unit
	 */
	@Override
	public double convertFromBaseUnit(double baseValue) {
		return baseValue / conversionFactor;
	}

	/**
	 * Returns the display name of the volume unit.
	 *
	 * @return unit name
	 */
	@Override
	public String getUnitName() {
		return unitName;
	}

	/**
	 * Returns the measurement type of this unit.
	 *
	 * Example: VolumeUnit
	 *
	 * @return measurement type name
	 */
	@Override
	public String getMeasurementType() {
		return this.getClass().getSimpleName();
	}

	/**
	 * Returns the measurable unit instance corresponding to the given enum name.
	 *
	 * @param unitName enum constant name
	 * @return measurable unit instance
	 */
	@Override
	public IMeasurable getUnitInstance(String unitName) {
		return VolumeUnit.valueOf(unitName);
	}
}