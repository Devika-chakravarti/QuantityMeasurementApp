# Quantity Measurement App
**Use Case 13: Centralized Arithmetic Logic to Enforce DRY in Quantity Operations**

## UC13 Overview
This use case refactors the arithmetic operations (addition, subtraction, division) from UC12 to eliminate code duplication and fully enforce the **DRY (Don't Repeat Yourself)** principle.

Instead of repeating validation, category checks, base-unit conversion, and result preparation across multiple methods, UC13 introduces a **centralized private helper method** that handles all common logic.  
Public methods now simply call the helper with the appropriate operation type — improving maintainability, reducing bug risk, and creating a scalable pattern for future operations (multiplication, modulo, etc.).

The public API and behavior remain **unchanged** from UC12; only internal implementation is optimized.

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
 │                        └── ArithmeticOperationTest.java
 │                        └── ArithmeticOperationLogicTest.java
 ├── .gitignore
 |── pom.xml
 └── README.md

```

## Features Implemented in UC13
- Created private enum `ArithmeticOperation` (ADD, SUBTRACT, DIVIDE) with lambda-based `compute(double a, double b)`  
- Added centralized private helper `performBaseArithmetic(Quantity<U> other, ArithmeticOperation operation)`  
  - Handles validation, base-unit conversion, arithmetic execution, and result preparation  
- Refactored public methods (`add`, `subtract`, `divide`) to delegate to helper  
  - No duplication of validation or conversion logic  
- Private validation helper `validateArithmeticOperands(Quantity<U> other, U targetUnit, boolean targetRequired)`  
- No changes to public API signatures or behavior  
- Updated tests in `ArithmeticOperationLogicTest` to verify delegation & consistency  
- Backward compatibility: all UC12 tests pass unchanged

## Preconditions
- Arithmetic operations (add, subtract, divide) from UC12 fully functional  
- `Quantity<U extends IMeasurable>` class from UC10  
- `IMeasurable` interface and all unit enums (LengthUnit, WeightUnit, VolumeUnit)  
- Behavior must remain identical after refactoring

## Main Flow
1. Public method called (e.g. `add(other, targetUnit)`)  
2. Validate inputs via `validateArithmeticOperands()`  
3. Delegate to `performBaseArithmetic(other, ADD)`  
4. Helper:  
   - Convert both to base unit  
   - Execute `operation.compute(aBase, bBase)`  
   - Return base-unit result  
5. Public method converts base result to target unit  
6. Return new `Quantity<U>`

## Postconditions
- Arithmetic operations delegate to single centralized helper  
- Validation & conversion logic implemented once  
- No code duplication across add/subtract/divide  
- Public API & behavior identical to UC12  
- Future operations (multiply, etc.) reuse same helper pattern  
- DRY principle fully enforced  
- All categories (length, weight, volume) unaffected

## Key Concepts Learned / Tested in UC13
1. **DRY Principle Enforcement**  
   - Common logic centralized → changes affect all operations uniformly  
2. **Lambda Expressions**  
   - Concise operation definitions in `ArithmeticOperation` enum  
3. **Functional Interface**  
   - `DoubleBinaryOperator` used for clean operation dispatch  
4. **Enum-Based Operation Dispatch**  
   - Type-safe alternative to if/switch chains  
5. **Private Helper Methods**  
   - Encapsulate validation & computation → cleaner public methods  
6. **Refactoring Without Behavior Change**  
   - Internal restructure preserves external behavior  
7. **Consistent Error Handling**  
   - Unified validation → same exceptions/messages everywhere  
8. **Scalability for Future Operations**  
   - New ops add enum constant → reuse helper  

## Key Concepts Tested
- Delegation: add/subtract/divide call helper correctly  
- Validation consistency across all operations  
- Enum operation computation (ADD/SUBTRACT/DIVIDE)  
- Addition/subtraction behavior preserved (UC12 tests pass)  
- Division behavior preserved (UC12 tests pass)  
- Null operand/target validation uniform  
- Cross-category prevention uniform  
- Finiteness validation uniform  
- Immutability after operations  
- Rounding consistency (add/subtract)  
- Division by zero exception  
- Large/small magnitude handling  
- Chained operations support  
- Code duplication eliminated (inspection)  
- Helper private visibility & encapsulation  
- Backward compatibility (all UC12 tests unchanged)
