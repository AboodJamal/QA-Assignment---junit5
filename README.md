# 🧪 JUnit 5 Testing Assignment — Spring 2024/2025

This repository contains the solution for **Homework Assignment #2** for the course **Software Testing and Quality Assurance** at **Najah University**, Spring Semester 2024/2025.

---

## 📌 Assignment Overview

This project implements a JUnit 5 test suite that covers the following classes from `main.najah.code`:

- `Calculator`
- `Product`
- `UserService`
- `RecipeBook`

Each test class includes:
- ✅ Tests for **valid and invalid input**
- ✅ Use of **@DisplayName** for descriptive test names
- ✅ **@ParameterizedTest** with `@CsvSource`
- ✅ **Timeout tests** using `assertTimeout`
- ✅ **Multiple assertions** via `assertAll`
- ✅ **@BeforeAll**, **@AfterAll**, **@BeforeEach**, **@AfterEach** lifecycle hooks
- ✅ One **intentionally failing test** marked with `@Disabled` and an explanation on how to fix it
- ✅ A **test suite** using `@Suite` to run all tests together
- ✅ One test class marked with `@Execution(ExecutionMode.CONCURRENT)` for **parallel execution**
- ✅ **Ordered tests** using `@Order` in `CalculatorTest`
