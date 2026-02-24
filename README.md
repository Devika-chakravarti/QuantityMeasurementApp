# Quantity Measurement App  
**Use Case 3: Generic Quantity Class for DRY Principle**

## UC3 Overview
This use case **refactors** the code from UC1 and UC2 to eliminate **code duplication** between `Feet` and `Inches` classes.  

Instead of separate classes with nearly identical `equals()` and constructor logic, we introduce:  
- A `LengthUnit` enum to define supported units + their conversion factors  
- A single generic `QuantityLength` class that handles **any length unit**

Goal: Apply **DRY (Don't Repeat Yourself)** principle, improve maintainability, and make it easy to add new units later — while still supporting cross-unit equality (e.g., 1 ft == 12 inches).

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
 │                      └── QuantityMeasurementApp.java
 │  
 └── test  
 │    └── java/
 │         └── com/
 │              └── quantitymeasurement
 │                        └── FeetEqualityTest.java
 │                        └── InchesEqualityTest.java
 │                        └── QuantityLengthTest.java 
 ├── .gitignore
 |── pom.xml
 └── README.md

```

## Features Implemented in UC3
- Created `LengthUnit` enum with conversion factors relative to base unit (Feet)  
- Created single immutable `QuantityLength` class (value + unit)  
- Overrode `equals()` to compare by converting both values to base unit (Feet)  
- Added constructor validation (unit cannot be null)  
- Demonstrated cross-unit equality in `main()`  
- Wrote comprehensive JUnit tests covering same-unit, cross-unit, edge cases  
- Preserved backward compatibility (old UC1 & UC2 tests still pass if kept)

## Preconditions
- `QuantityMeasurementApp` class is instantiated  
- Two numerical values with their unit types (from `LengthUnit` enum) are provided  

## Main Flow
1. Create `QuantityLength` objects with value + unit  
2. Validate unit is not null  
3. Convert both values to base unit (Feet) using enum factors  
4. Compare converted values using `Double.compare()`  
5. Return true if equal, false otherwise  

## Postconditions
- Returns `true` if two quantities represent the **same length** (even in different units)  
- Returns `false` otherwise  
- Code duplication removed → single place for equality + conversion logic  
- Easy to add new units (just add to enum)

## Key Concepts Learned / Tested in UC3
1. **DRY Principle (Don't Repeat Yourself)**  
   - Eliminates nearly identical code duplication from separate `Feet` and `Inches` classes  
   - Consolidates constructor, value storage, and equality logic into one class  
   - Reduces maintenance effort and risk of inconsistent changes  
2. **Enum Usage for Type-Safe Units**  
   - `LengthUnit` enum defines supported units with their conversion factors  
   - Provides compile-time safety instead of magic strings  
   - Encapsulates conversion logic (`toFeet()`) inside the enum  
3. **Abstraction & Single Class for Multiple Units**  
   - One `QuantityLength` class handles all length measurements (polymorphic via enum)  
   - Hides unit conversion details from the client code  
4. **Cross-Unit Value-Based Equality**  
   - Overrides `equals()` to convert both quantities to a common base unit (Feet) before comparison  
   - Enables true semantic equality: 1.0 FEET == 12.0 INCH  
5. **Encapsulation**  
   - Bundles value and unit together in a single immutable object  
   - `private final` fields + constructor validation (no null unit)  
6. **Equals Method Override (Advanced)**  
   - Safe null/type checks + conversion-based comparison  
   - Uses `Double.compare()` for floating-point precision  
7. **Equality Contract (extended for cross-unit)**  
   - Reflexive: `a.equals(a)` → true  
   - Symmetric: if `a.equals(b)` then `b.equals(a)` (works across units)  
   - Transitive: if `a.equals(b)` and `b.equals(c)` then `a.equals(c)`  
   - Consistent: same inputs always give same result  
   - `a.equals(null)` → false  
8. **Input Validation & Defensive Programming**  
   - Throws `IllegalArgumentException` for null unit  
   - Ensures only supported units (from enum) are used  
9. **Refactoring Best Practices**  
   - Safely replaced multiple similar classes with one scalable class  
   - Maintained backward compatibility (old tests still valid if kept)  
   - Improved scalability: new units added by just extending the enum  
10. **Single Responsibility Principle**  
    - `QuantityLength` is responsible only for representing and comparing lengths  
    - Enum handles unit definitions and conversions  
