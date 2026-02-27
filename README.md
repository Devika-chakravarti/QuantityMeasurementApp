# Quantity Measurement App
**Use Case 14: Temperature Measurement with Selective Arithmetic Support and IMeasurable Refactoring**

## UC14 Overview
This use case introduces **temperature** as a fourth measurement category (CELSIUS, FAHRENHEIT, KELVIN), while revealing and fixing a key limitation in the `IMeasurable` interface from UC10–UC13.

Temperature supports:
- Equality comparison (cross-unit)
- Unit-to-unit conversion

But **does not support** meaningful arithmetic operations (add/subtract/divide on absolute temperatures are nonsensical, though differences can be added).

UC14 refactors `IMeasurable` to make arithmetic **optional** via default methods and a new functional interface `SupportsArithmetic`, allowing `TemperatureUnit` to disable arithmetic gracefully (throws `UnsupportedOperationException`), while length/weight/volume continue full support.

The design demonstrates how to evolve interfaces non-destructively and handle category-specific constraints elegantly.

## Project Structure:

## Project Structure:
```
src  
 ├── main  
 │    └── java/
 │         └── com/
 │              └── quantitymeasurement
 │                      └── Feet.java
 │                      └── Inches.java
 │                      └── LengthUnit.java
 │                      └── QuantityLength.java
 │                      └── WeightUnit.java
 │                      └── IMeasurable.java
 │                      └── VolumeUnit.java
 │                      └── TemperatureUnit.java
 │                      └── SupportsArithmetic.java
 │                      └── Quantity.java
 │                      └── QuantityWeight.java
 │                      └── QuantityMeasurementApp.java
 │  
 └── test  
 │    └── java/
 │         └── com/
 │              └── quantitymeasurement
 │                        └── FeetEqualityTest.java
 │                        └── InchesEqualityTest.java
 │                        └── QuantityLengthTest.java
 │                        └── YardEqualityTest.java
 │                        └── ConversionTest.java
 │                        └── AdditionTest.java
 │                        └── TargetUnitSpecificationTest.java
 │                        └── LengthUnitRefactoredTest.java
 │                        └── WeightEqualityTest.java
 │                        └── QuantityTest.java
 │                        └── VolumeTest.java
 │                        └── TemperatureUnitTest.java
 │                        └── ArithmeticOperationTest.java
 │                        └── ArithmeticOperationLogicTest.java
 ├── .gitignore
 |── pom.xml
 └── README.md

```

## Features Implemented in UC14
- Refactored `IMeasurable` interface:  
  - Added default methods for optional arithmetic support  
  - Added default `supportsArithmetic()` returning true (overridable)  
  - Added `validateOperationSupport(String operation)` default method (overridable to throw)  
- Created new `@FunctionalInterface SupportsArithmetic`  
  - Lambda-based capability query: `() -> true` (default) or `() -> false` (temperature)  
- Created standalone `TemperatureUnit` enum implementing `IMeasurable`  
  - Units: CELSIUS (base), FAHRENHEIT, KELVIN  
  - Accurate non-linear conversion formulas (C ↔ F, C ↔ K)  
  - Lambda: `SupportsArithmetic supportsArithmetic = () -> false;` (disables arithmetic)  
  - Overrides `validateOperationSupport()` to throw on add/subtract/divide  
- Updated `Quantity<U>` arithmetic methods (`add`, `subtract`, `divide`):  
  - Call `unit.validateOperationSupport(operation)` before proceeding  
  - Temperature throws `UnsupportedOperationException` early  
- No changes to public API or existing category behavior  
- Added temperature demo in `QuantityMeasurementApp` using generic methods  
- New test class `TemperatureUnitTest` covering equality, conversion, unsupported ops, cross-category safety  
- Backward compatibility: length, weight, volume (UC1–UC13) unaffected

## Preconditions
- `Quantity<U extends IMeasurable>` class and `IMeasurable` interface from UC13  
- `LengthUnit`, `WeightUnit`, `VolumeUnit` enums  
- Temperature supports only equality & conversion (no arithmetic)

## Main Flow
1. Call `.equals()` → compares via base unit (Celsius) + EPSILON  
2. Call `.convertTo(targetUnit)` → delegates to unit conversion formulas  
3. Call `.add()`, `.subtract()`, `.divide()` →  
   - First checks `unit.validateOperationSupport(operation)`  
   - Temperature throws `UnsupportedOperationException`  
   - Other categories proceed normally  
4. Return new immutable `Quantity<U>` (convert) or double (divide)

## Postconditions
- Temperature equality & conversion accurate  
- Arithmetic on temperature throws clear `UnsupportedOperationException`  
- Length/weight/volume arithmetic unchanged  
- Cross-category comparisons prevented  
- Immutability preserved  
- No regressions in UC1–UC13 features  
- Scalable pattern: future categories can selectively disable operations via lambda

## Key Concepts Learned / Tested in UC14
1. **Functional Interface & Lambda**  
   - `SupportsArithmetic` + lambda for capability query (`() -> false` for temperature)  
2. **Default Methods in Interfaces**  
   - Optional arithmetic support without breaking existing enums  
3. **Interface Segregation Principle (ISP)**  
   - Arithmetic made optional → no forced dummy implementations  
4. **Non-Linear Conversions**  
   - Temperature uses offset + scale (C ↔ F: °F = °C × 9/5 + 32)  
5. **Capability-Based Design**  
   - Units declare supported operations → fail-fast & clear errors  
6. **Backward Compatibility**  
   - Existing units inherit default `true` for arithmetic  
7. **Exception Semantics**  
   - `UnsupportedOperationException` clearly communicates intent  
8. **Category-Specific Constraints**  
   - Temperature only equality & conversion → realistic domain handling  

## Key Concepts Tested
- Celsius-to-Celsius same/different equality  
- Fahrenheit-to-Fahrenheit equality  
- Cross-unit equality (C ↔ F, C ↔ K, F ↔ K)  
- Temperature conversion accuracy (all pairs)  
- Round-trip conversion preservation  
- Unsupported add/subtract/divide throw `UnsupportedOperationException`  
- Temperature vs. length/weight/volume incompatibility  
- Null/invalid input validation  
- Zero/negative/large value conversion  
- HashCode consistency with equals  
- All temperature unit combinations  
- Precision tolerance after conversion  
- Backward compatibility (UC1–UC13 tests pass)  
- Default arithmetic support for non-temperature units  
- `SupportsArithmetic` lambda correctly disables ops for temperature  
