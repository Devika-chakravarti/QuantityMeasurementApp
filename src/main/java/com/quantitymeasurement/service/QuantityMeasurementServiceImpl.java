package com.quantitymeasurement.service;

import com.quantitymeasurement.core.IMeasurable;
import com.quantitymeasurement.core.LengthUnit;
import com.quantitymeasurement.core.TemperatureUnit;
import com.quantitymeasurement.core.VolumeUnit;
import com.quantitymeasurement.core.WeightUnit;
import com.quantitymeasurement.entity.QuantityModel;
import com.quantitymeasurement.dto.QuantityDTO;
import com.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.quantitymeasurement.exception.QuantityMeasurementException;
import com.quantitymeasurement.repository.IQuantityMeasurementRepository;

/*
 * QuantityMeasurementServiceImpl:
 * -------------------------------
 * This class contains the core business logic for quantity measurement operations.
 *
 * In UC15, the service layer is responsible for:
 * - accepting QuantityDTO input
 * - converting DTO into internal QuantityModel
 * - performing comparison, conversion, addition, subtraction, and division
 * - validating arithmetic operations
 * - saving operation history in repository
 * - returning standardized results
 *
 * This class implements IQuantityMeasurementService.
 */
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

	private final IQuantityMeasurementRepository repository;

	/*
	 * UC15: Repository is injected through constructor.
	 */
	public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
		this.repository = repository;
	}

	/*
	 * UC15: Private enum used to group the type of operation being handled.
	 */
	private enum Operation {
		COMPARISON, CONVERSION, ARITHMETIC
	}

	/*
	 * Compares two quantities.
	 *
	 * Returns true if both quantities are equal after conversion to their base
	 * unit.
	 */
	@Override
	public boolean compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		try {
			QuantityModel<IMeasurable> thisQuantity = getQuantityModel(thisQuantityDTO);
			QuantityModel<IMeasurable> thatQuantity = getQuantityModel(thatQuantityDTO);

			boolean result = compare(thisQuantity, thatQuantity);

			repository.save(new QuantityMeasurementEntity(thisQuantity, thatQuantity, "COMPARE",
					result ? "Equal" : "Not Equal"));

			return result;
		} catch (Exception e) {
			QuantityModel<IMeasurable> thisQuantity = getQuantityModel(thisQuantityDTO);
			QuantityModel<IMeasurable> thatQuantity = getQuantityModel(thatQuantityDTO);

			repository.save(new QuantityMeasurementEntity(thisQuantity, thatQuantity, "COMPARE", e.getMessage(), true));

			throw new QuantityMeasurementException(e.getMessage(), e);
		}
	}

	/*
	 * Private generic compare helper.
	 *
	 * Converts both quantities to base unit and compares them.
	 */
	private <U extends IMeasurable> boolean compare(QuantityModel<U> thisQuantity, QuantityModel<U> thatQuantity) {
		double thisBaseValue = thisQuantity.unit.convertToBaseUnit(thisQuantity.value);
		double thatBaseValue = thatQuantity.unit.convertToBaseUnit(thatQuantity.value);

		return Math.abs(thisBaseValue - thatBaseValue) < 0.000001;
	}

	/*
	 * Converts the first quantity into the unit of the second DTO.
	 *
	 * Returns the converted result as QuantityDTO.
	 */
	@Override
	public QuantityDTO convert(QuantityDTO thisQuantityDTO, QuantityDTO thatUnitDTO) {
		try {
			QuantityModel<IMeasurable> thisQuantity = getQuantityModel(thisQuantityDTO);
			QuantityModel<IMeasurable> thatUnit = getQuantityModel(thatUnitDTO);

			double convertedValue = convertTo(thisQuantity, thatUnit.unit);

			repository.save(new QuantityMeasurementEntity(thisQuantity, thatUnit, "CONVERT",
					new QuantityModel<>(convertedValue, thatUnit.unit)));

			return new QuantityDTO(convertedValue, thatUnit.unit.getUnitName(), thatUnit.unit.getMeasurementType());
		} catch (Exception e) {
			QuantityModel<IMeasurable> thisQuantity = getQuantityModel(thisQuantityDTO);
			QuantityModel<IMeasurable> thatUnit = getQuantityModel(thatUnitDTO);

			repository.save(new QuantityMeasurementEntity(thisQuantity, thatUnit, "CONVERT", e.getMessage(), true));

			throw new QuantityMeasurementException(e.getMessage(), e);
		}
	}

	/*
	 * Private generic conversion helper.
	 *
	 * For temperature, a separate helper is used because conversion is non-linear.
	 * For other units, base-unit conversion is used.
	 */
	private <U extends IMeasurable> double convertTo(QuantityModel<U> thisQuantity, U targetUnit) {
		if (thisQuantity.unit instanceof TemperatureUnit && targetUnit instanceof TemperatureUnit) {
			return convertTemperatureUnit(thisQuantity.value, (TemperatureUnit) thisQuantity.unit,
					(TemperatureUnit) targetUnit);
		}

		double baseValue = thisQuantity.unit.convertToBaseUnit(thisQuantity.value);
		return targetUnit.convertFromBaseUnit(baseValue);
	}

	/*
	 * Temperature conversion helper.
	 *
	 * Converts source temperature to base Celsius and then to target unit.
	 */
	private <U extends IMeasurable> double convertTemperatureUnit(double value, TemperatureUnit sourceUnit,
			TemperatureUnit targetUnit) {
		double baseValue = sourceUnit.convertToBaseUnit(value);
		return targetUnit.convertFromBaseUnit(baseValue);
	}

	/*
	 * Adds two quantities.
	 *
	 * Result is returned in the first quantity unit.
	 */
	@Override
	public QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		return add(thisQuantityDTO, thatQuantityDTO, thisQuantityDTO);
	}

	/*
	 * Adds two quantities and returns result in target unit.
	 */
	@Override
	public QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO) {
		try {
			QuantityModel<IMeasurable> thisQuantity = getQuantityModel(thisQuantityDTO);
			QuantityModel<IMeasurable> thatQuantity = getQuantityModel(thatQuantityDTO);
			QuantityModel<IMeasurable> targetUnit = getQuantityModel(targetUnitDTO);

			validateArithmeticOperands(thisQuantity, thatQuantity, targetUnit, ArithmeticOperation.ADD);

			double resultValue = performArithmetic(thisQuantity, thatQuantity, targetUnit.unit,
					ArithmeticOperation.ADD);

			QuantityModel<IMeasurable> resultQuantity = new QuantityModel<>(resultValue, targetUnit.unit);

			repository.save(new QuantityMeasurementEntity(thisQuantity, thatQuantity, "ADD", resultQuantity));

			return new QuantityDTO(resultValue, targetUnit.unit.getUnitName(), targetUnit.unit.getMeasurementType());
		} catch (Exception e) {
			QuantityModel<IMeasurable> thisQuantity = getQuantityModel(thisQuantityDTO);
			QuantityModel<IMeasurable> thatQuantity = getQuantityModel(thatQuantityDTO);

			repository.save(new QuantityMeasurementEntity(thisQuantity, thatQuantity, "ADD", e.getMessage(), true));

			throw new QuantityMeasurementException(e.getMessage(), e);
		}
	}

	/*
	 * Subtracts the second quantity from the first quantity.
	 *
	 * Result is returned in the first quantity unit.
	 */
	@Override
	public QuantityDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		return subtract(thisQuantityDTO, thatQuantityDTO, thisQuantityDTO);
	}

	/*
	 * Subtracts the second quantity from the first quantity and returns result in
	 * target unit.
	 */
	@Override
	public QuantityDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO) {
		try {
			QuantityModel<IMeasurable> thisQuantity = getQuantityModel(thisQuantityDTO);
			QuantityModel<IMeasurable> thatQuantity = getQuantityModel(thatQuantityDTO);
			QuantityModel<IMeasurable> targetUnit = getQuantityModel(targetUnitDTO);

			validateArithmeticOperands(thisQuantity, thatQuantity, targetUnit, ArithmeticOperation.SUBTRACT);

			double resultValue = performArithmetic(thisQuantity, thatQuantity, targetUnit.unit,
					ArithmeticOperation.SUBTRACT);

			QuantityModel<IMeasurable> resultQuantity = new QuantityModel<>(resultValue, targetUnit.unit);

			repository.save(new QuantityMeasurementEntity(thisQuantity, thatQuantity, "SUBTRACT", resultQuantity));

			return new QuantityDTO(resultValue, targetUnit.unit.getUnitName(), targetUnit.unit.getMeasurementType());
		} catch (Exception e) {
			QuantityModel<IMeasurable> thisQuantity = getQuantityModel(thisQuantityDTO);
			QuantityModel<IMeasurable> thatQuantity = getQuantityModel(thatQuantityDTO);

			repository
					.save(new QuantityMeasurementEntity(thisQuantity, thatQuantity, "SUBTRACT", e.getMessage(), true));

			throw new QuantityMeasurementException(e.getMessage(), e);
		}
	}

	/*
	 * Divides the first quantity by the second quantity.
	 *
	 * Returns the result as double.
	 */
	@Override
	public double divide(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		try {
			QuantityModel<IMeasurable> thisQuantity = getQuantityModel(thisQuantityDTO);
			QuantityModel<IMeasurable> thatQuantity = getQuantityModel(thatQuantityDTO);

			validateArithmeticOperands(thisQuantity, thatQuantity, null, ArithmeticOperation.DIVIDE);

			double resultValue = performArithmetic(thisQuantity, thatQuantity, null, ArithmeticOperation.DIVIDE);

			repository.save(new QuantityMeasurementEntity(thisQuantity, thatQuantity, "DIVIDE", resultValue));

			return resultValue;
		} catch (Exception e) {
			QuantityModel<IMeasurable> thisQuantity = getQuantityModel(thisQuantityDTO);
			QuantityModel<IMeasurable> thatQuantity = getQuantityModel(thatQuantityDTO);

			repository.save(new QuantityMeasurementEntity(thisQuantity, thatQuantity, "DIVIDE", e.getMessage(), true));

			throw new QuantityMeasurementException(e.getMessage(), e);
		}
	}

	/*
	 * Converts QuantityDTO into internal QuantityModel.
	 *
	 * This method maps DTO unit names into actual application units.
	 */
	private QuantityModel<IMeasurable> getQuantityModel(QuantityDTO quantity) {
		if (quantity == null) {
			throw new QuantityMeasurementException("QuantityDTO cannot be null");
		}

		String measurementType = quantity.getMeasurementType();
		String unit = quantity.getUnit();

		if ("LengthUnit".equals(measurementType)) {
			if ("INCHES".equals(unit)) {
				unit = "INCH";
			}
			return new QuantityModel<>(quantity.getValue(), (IMeasurable) LengthUnit.FEET.getUnitInstance(unit));
		}

		if ("WeightUnit".equals(measurementType)) {
			if ("GRAMS".equals(unit)) {
				unit = "GRAM";
			}
			if ("KILOGRAMS".equals(unit)) {
				unit = "KILOGRAM";
			}
			if ("POUNDS".equals(unit)) {
				unit = "POUND";
			}
			return new QuantityModel<>(quantity.getValue(), (IMeasurable) WeightUnit.KILOGRAM.getUnitInstance(unit));
		}

		if ("VolumeUnit".equals(measurementType)) {
			if ("LITRES".equals(unit)) {
				unit = "LITRE";
			}
			if ("MILLILITRES".equals(unit)) {
				unit = "MILLILITRE";
			}
			if ("GALLONS".equals(unit)) {
				unit = "GALLON";
			}
			return new QuantityModel<>(quantity.getValue(), (IMeasurable) VolumeUnit.LITRE.getUnitInstance(unit));
		}

		if ("TemperatureUnit".equals(measurementType)) {
			return new QuantityModel<>(quantity.getValue(),
					(IMeasurable) TemperatureUnit.CELSIUS.getUnitInstance(unit));
		}

		throw new QuantityMeasurementException("Unsupported measurement type");
	}

	/*
	 * Validates arithmetic operands before add, subtract, or divide.
	 *
	 * It checks: - same measurement type - arithmetic support for the units -
	 * target unit compatibility when target is present
	 */
	private <U extends IMeasurable> void validateArithmeticOperands(QuantityModel<U> thisQuantity,
			QuantityModel<U> thatQuantity, QuantityModel<U> targetUnit, ArithmeticOperation operation) {

		if (!thisQuantity.unit.getMeasurementType().equals(thatQuantity.unit.getMeasurementType())) {
			throw new QuantityMeasurementException("Different measurement types are not allowed");
		}

		thisQuantity.unit.validateOperationSupport(operation.name());
		thatQuantity.unit.validateOperationSupport(operation.name());

		if (targetUnit != null
				&& !thisQuantity.unit.getMeasurementType().equals(targetUnit.unit.getMeasurementType())) {
			throw new QuantityMeasurementException("Different measurement types are not allowed");
		}
	}

	/*
	 * Private enum for arithmetic operations.
	 */
	private enum ArithmeticOperation {
		ADD, SUBTRACT, DIVIDE
	}

	/*
	 * Performs arithmetic operation after converting quantities to base unit.
	 *
	 * For ADD and SUBTRACT: - result is converted into target unit
	 *
	 * For DIVIDE: - result is returned directly as ratio
	 */
	private <U extends IMeasurable> double performArithmetic(QuantityModel<U> thisQuantity,
			QuantityModel<U> thatQuantity, U targetUnit, ArithmeticOperation operation) {

		double thisBaseValue = thisQuantity.unit.convertToBaseUnit(thisQuantity.value);
		double thatBaseValue = thatQuantity.unit.convertToBaseUnit(thatQuantity.value);

		switch (operation) {
		case ADD:
			return targetUnit.convertFromBaseUnit(thisBaseValue + thatBaseValue);

		case SUBTRACT:
			return targetUnit.convertFromBaseUnit(thisBaseValue - thatBaseValue);

		case DIVIDE:
			if (Math.abs(thatBaseValue) < 0.000001) {
				throw new QuantityMeasurementException("Cannot divide by zero quantity");
			}
			return thisBaseValue / thatBaseValue;

		default:
			throw new QuantityMeasurementException("Unsupported arithmetic operation");
		}
	}

	/*
	 * Main method for testing purposes.
	 */
	public static void main(String[] args) {
	}
}