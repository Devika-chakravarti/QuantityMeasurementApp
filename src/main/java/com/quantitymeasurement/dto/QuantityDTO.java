package com.quantitymeasurement.dto;

/*
 * QuantityDTO:
 * ------------
 * This is a Data Transfer Object used in UC15 layered architecture.
 *
 * Purpose:
 * --------
 * - hold quantity value
 * - hold quantity unit
 * - hold measurement type
 *
 * This DTO contains its own measurable-unit interface and unit enums
 * so that quantity input/output can be represented in a self-contained way.
 */
public class QuantityDTO {

	/*
	 * IMeasurableUnit: ---------------- This interface is used inside QuantityDTO
	 * to represent measurable units.
	 *
	 * It provides: - unit name - measurement type
	 */
	public interface IMeasurableUnit {

		String getUnitName();

		String getMeasurementType();
	}

	/*
	 * LengthUnit: ----------- DTO enum for length units.
	 */
	public enum LengthUnit implements IMeasurableUnit {
		FEET, INCHES, YARDS, CENTIMETERS;

		@Override
		public String getUnitName() {
			return this.name();
		}

		@Override
		public String getMeasurementType() {
			return this.getClass().getSimpleName();
		}
	}

	/*
	 * VolumeUnit: ----------- DTO enum for volume units.
	 */
	public enum VolumeUnit implements IMeasurableUnit {
		LITRES, MILLILITRES, GALLONS;

		@Override
		public String getUnitName() {
			return this.name();
		}

		@Override
		public String getMeasurementType() {
			return this.getClass().getSimpleName();
		}
	}

	/*
	 * WeightUnit: ----------- DTO enum for weight units.
	 */
	public enum WeightUnit implements IMeasurableUnit {
		GRAMS, KILOGRAMS, POUNDS, OUNCES;

		@Override
		public String getUnitName() {
			return this.name();
		}

		@Override
		public String getMeasurementType() {
			return this.getClass().getSimpleName();
		}
	}

	/*
	 * TemperatureUnit: ---------------- DTO enum for temperature units.
	 */
	public enum TemperatureUnit implements IMeasurableUnit {
		CELSIUS, FAHRENHEIT, KELVIN;

		@Override
		public String getUnitName() {
			return this.name();
		}

		@Override
		public String getMeasurementType() {
			return this.getClass().getSimpleName();
		}
	}

	public double value;
	public String unit;
	public String measurementType;

	public QuantityDTO(double value, IMeasurableUnit unit) {
		this.value = value;
		this.unit = unit.getUnitName();
		this.measurementType = unit.getMeasurementType();
	}

	public QuantityDTO(double value, String unit, String measurementType) {
		this.value = value;
		this.unit = unit;
		this.measurementType = measurementType;
	}

	public double getValue() {
		return value;
	}

	public String getUnit() {
		return unit;
	}

	public String getMeasurementType() {
		return measurementType;
	}

	@Override
	public String toString() {
		return "QuantityDTO [value=" + value + ", unit=" + unit + ", measurementType=" + measurementType + "]";
	}

	/*
	 * Main method for testing purposes.
	 */
	public static void main(String[] args) {
	}
}