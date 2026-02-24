# Quantity Measurement App  
**Use Case 2: Feet and Inches Measurement Equality**

## UC2 Overview
This use case **extends UC1** by adding support for equality checks on **Inches** — while keeping **Feet** equality as it was.  

## Project Structure:
```
src  
 ├── main  
 │    └── java/
 │         └── com/
 │              └── quantitymeasurement
 │                      └── Feet.java
 │                      └── Inches.java
 │                      └── QuantityMeasurementApp.java
 │  
 └── test  
 │    └── java/
 │         └── com/
 │              └── quantitymeasurement
 │                        └── FeetEqualityTest.java
 │                        └── InchesEqualityTest.java 
 ├── .gitignore
 |── pom.xml
 └── README.md

```

**Important**:  
- Feet and Inches are **treated completely separately**  
- We are **not** comparing Feet with Inches in this use case  
- Each unit has its own dedicated class (`Feet` and `Inches`)  
- Goal is to reuse the same equality pattern learned in UC1 for a new unit

## Features Implemented in UC2
- Created a new immutable class `Inches` similar to `Feet`  
- Implemented proper `equals()` and `hashCode()` in `Inches` class  
- Added separate equality demo for Inches in `main()` method  
- Kept hard-coded values (no user input yet)  
- Added dedicated JUnit test class for Inches equality  
- Maintained full test coverage similar to UC1 (same value, different value, null, wrong type, same reference)

## Preconditions
- `QuantityMeasurementApp` class is instantiated  
- Two numerical values in feet OR two numerical values in inches are hard-coded  

## Main Flow
1. `main()` calls equality check for two Feet values  
2. `main()` calls equality check for two Inches values  
3. Each unit class (`Feet` / `Inches`) validates input type implicitly via `equals()`  
4. Compares values using safe `Double.compare()`  
5. Returns true/false result for each comparison  

## Postconditions
- Returns `true` if both values in the same unit are equal  
- Returns `false` otherwise  
- Supports inch-to-inch and feet-to-feet equality (separately)

## Key Concepts Learned / Tested in UC2
1. **Object Equality** — Correctly overriding `equals()` from `Object` class (applied to a second unit type: Inches)
2. **Floating-point Comparison** — Using `Double.compare()` instead of `==` to handle double precision safely
3. **Null Checking** — Preventing `NullPointerException` by checking `obj == null`
4. **Type Checking** — Using `getClass() != obj.getClass()` to ensure type safety (only Inches compares with Inches)
5. **Encapsulation & Immutability** — `private final double value` with no way to modify after creation
6. **Equality Contract** (must satisfy all 5 rules — now validated for both Feet and Inches classes):
   - Reflexive: `a.equals(a)` → true
   - Symmetric: if `a.equals(b)` then `b.equals(a)`
   - Transitive: if `a.equals(b)` and `b.equals(c)` then `a.equals(c)`
   - Consistent: multiple calls with same inputs return same result
   - `a.equals(null)` → false
7. **DRY Principle Violation (Design Insight)** — Noticing repeated code in `Feet` and `Inches` classes, highlighting the need for a better abstraction (generic Quantity or unit enum) in upcoming use cases
