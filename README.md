# Quantity Measurement App 

**Use Case 1: Feet Measurement Equality**

## UC1 Overview
The **QuantityMeasurementApp** checks whether two numerical values measured in **feet** are equal.  
This is the very first increment of the app - focused only on equality comparison for the **Feet** unit.

Goal: Learn proper object equality in Java, handle floating-point values safely, and write clean, testable code.

## Project Structure:

src  
 ├── main  
 │    └── java/
 │         └── com/
 │              └── quantitymeasurement
 │                      └── Feet.java
 │                      └── QuantityMeasurementApp.java
 │  
 └── test  
 │    └── java/
 │         └── com/
 │              └── quantitymeasurement
 │                        └── FeetEqualityTest.java 
 ├── .gitignore
 └── pom.xml

## Features Implemented in UC1
- Represents a measurement in **feet** using a dedicated `Feet` class  
- Makes the `Feet` class **immutable** (value is `private final`)  
- Overrides `equals()` method correctly to compare two `Feet` objects based on their value  
- Uses `Double.compare()` for safe floating-point equality (instead of `==`)  
- Implements `hashCode()` consistently with `equals()`  
- Includes basic demo in `main()` method with hard-coded values  
- Comes with comprehensive JUnit 5 unit tests covering key equality scenarios  

## Preconditions
- `QuantityMeasurementApp` class is instantiated  
- Two numerical values in feet are provided  

## Postconditions
- Returns `true` if both values are equal  
- Returns `false` otherwise  

## Key Concepts Learned / Tested in UC1

1. **Object Equality** — Correctly overriding `equals()` from `Object` class  
2. **Floating-point Comparison** — Using `Double.compare()` instead of `==`  
3. **Null Checking** — Prevents `NullPointerException`  
4. **Type Checking** — Ensures comparison only with same type (`getClass()`)  
5. **Encapsulation & Immutability** — `private final` field + no setters  
6. **Equality Contract** (must satisfy all 5 rules):  
   - Reflexive: `a.equals(a)` → true  
   - Symmetric: if `a.equals(b)` then `b.equals(a)`  
   - Transitive: if `a.equals(b)` and `b.equals(c)` then `a.equals(c)`  
   - Consistent: same inputs → same result every time  
   - `a.equals(null)` → false   
