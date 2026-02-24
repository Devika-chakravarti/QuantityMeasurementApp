# Quantity Measurement App  
**Use Case 4: Extended Unit Support**

## UC4 Overview
This use case extends UC3 by adding two new length units — **YARDS** and **CENTIMETERS** — to the `LengthUnit` enum.

The key goal is to prove that the **generic design** from UC3 is **scalable**:  
- New units are added **without modifying** `QuantityLength` class  
- No code duplication  
- Equality comparisons work seamlessly across **all units** (feet, inches, yards, centimeters)

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
 │                        └── YardEqualityTest.java 
 ├── .gitignore
 |── pom.xml
 └── README.md

```

## Features Implemented in UC4
- Added `YARDS` to `LengthUnit` enum with conversion factor `3.0` (1 yard = 3 feet)  
- Added `CENTIMETERS` to `LengthUnit` enum with conversion factor `0.393701 / 12.0` (1 cm ≈ 0.032808 feet)  
- No changes needed in `QuantityLength` class — existing `equals()` logic handles new units automatically  
- Updated demo in `QuantityMeasurementApp` to show cross-unit equality with new units  
- Comprehensive test cases added to verify new unit combinations  
- Backward compatibility: all UC1–UC3 functionality and tests remain valid

## Preconditions
- `QuantityLength` class and `LengthUnit` enum from UC3  
- Two `QuantityLength` objects with any supported unit (feet, inches, yards, centimeters)  

## Main Flow
1. Create `QuantityLength` objects with new units (YARDS or CENTIMETERS)  
2. Call `.equals()` on them  
3. Internally: both values converted to base unit (Feet)  
4. Compare converted values using `Double.compare()` + epsilon tolerance  
5. Return true/false based on physical equivalence

## Postconditions
- Correct equality result (true/false) across all unit combinations  
- Yard-to-yard, yard-to-feet, yard-to-inches, cm-to-cm, cm-to-inches, etc. all supported  
- Adding new units requires **only enum modification** — no class changes  
- Code remains DRY and maintainable

## Key Concepts Learned / Tested in UC4
1. **Scalability of Generic Design**  
   - New units added with **zero changes** to core logic class  
2. **Conversion Factor Management**  
   - Centralized in enum → consistent, no duplication  
3. **Unit Relationships & Mathematics**  
   - 1 yard = 3 feet = 36 inches  
   - 1 cm ≈ 0.393701 inches ≈ 0.032808 feet  
4. **Enum Extensibility**  
   - Enums allow safe, type-safe addition of constants  
5. **DRY Principle Validation**  
   - Proves generic design eliminates need for separate classes  
6. **Backward Compatibility**  
   - Existing feet/inches equality still works perfectly  
7. **Cross-Unit & Transitive Equality**  
   - If A = B and B = C → A = C (e.g., 1 yard = 3 feet = 36 inches)  

## Key Concepts Tested
- Yard-to-yard same/different value equality  
- Yard-to-feet, yard-to-inches equivalent value equality  
- Centimeter-to-centimeter same/different value equality  
- Centimeter-to-inches equivalent value equality  
- Multi-unit transitive property  
- Null unit / null comparison handling  
- Reflexive property (object equals itself)  
- Complex scenarios combining all four units
