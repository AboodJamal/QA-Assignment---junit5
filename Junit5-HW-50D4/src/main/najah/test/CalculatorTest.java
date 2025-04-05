package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import main.najah.code.Calculator;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;

@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Calculator Tests")
public class CalculatorTest {
	private Calculator calculator;

	@BeforeEach
	void setup() {
		calculator = new Calculator();
	}

	@Test
	@Order(1)
	@DisplayName("Test addition with multiple numbers")
	void testAddMultipleNumbers() {
		int result = calculator.add(1, 2, 3, 4);
		assertEquals(10, result, "Sum should be 10");
	}

	@ParameterizedTest(name = "add({0}, {1}, {2}) = {3}")
	@CsvSource({
			"1, 2, 3, 6",
			"0, 0, 0, 0",
			"-1, -2, -3, -6",
			"100, 200, 300, 600"
	})
	@Order(2)
	@DisplayName("Parameterized Test for addition")
	void testAddParameterized(int a, int b, int c, int expected) {
		int result = calculator.add(a, b, c);
		assertEquals(expected, result);
	}

	@Test
	@Order(3)
	@DisplayName("Test division with valid input")
	void testDivideValid() {
		int result = calculator.divide(10, 2);
		assertEquals(5, result, "10/2 should equal 5");
	}

	@ParameterizedTest(name = "{0} / {1} = {2}")
	@CsvSource({
			"10, 2, 5",
			"100, 10, 10",
			"-20, 5, -4",
			"0, 5, 0"
	})
	@Order(4)
	@DisplayName("Parameterized division test")
	void testDivisionParameterized(int a, int b, int expected) {
		assertEquals(expected, calculator.divide(a, b));
	}

	@Test
	@Order(5)
	@DisplayName("Test division by zero")
	void testDivideByZero() {
		Exception exception = assertThrows(ArithmeticException.class, () -> {
			calculator.divide(10, 0);
		});
		assertEquals("Cannot divide by zero", exception.getMessage());
	}

	@Test
	@Order(6)
	@DisplayName("Test factorial with valid input")
	void testFactorialValid() {
		int result = calculator.factorial(5);
		assertEquals(120, result, "Factorial of 5 should be 120");
	}

	@Test
	@Order(7)
	@DisplayName("Test factorial of zero")
	void testFactorialZero() {
		int result = calculator.factorial(0);
		assertEquals(1, result, "Factorial of 0 should be 1");
	}

	@Test
	@Order(8)
	@DisplayName("Test factorial with negative input")
	void testFactorialInvalid() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			calculator.factorial(-1);});
		assertEquals("Negative input", exception.getMessage());
	}

	@Test
	@Order(9)
	@DisplayName("Test addition operation within timeout")
	void testAdditionTimeout() {
		assertTimeout(Duration.ofMillis(100), () -> {
			int result = calculator.add(1, 2, 3);
			assertEquals(6, result);
		});
	}

	@Test
	@Order(10)
	@DisplayName("Test multiple assertions for add and factorial")
	void testMultipleAssertions() {
		assertAll("Calculator operations",
				() -> assertEquals(6, calculator.add(1, 2, 3), "Addition failed"),
				() -> assertEquals(24, calculator.factorial(4), "Factorial failed"));
	}

	@Test
	@Order(11)
	@Disabled("Disabled test example - incorrect expected result for division. Fix by correcting the expected value to 2 (10 / 5).")
	@DisplayName("Disabled test for incorrect division result")
	void testDisabledIncorrectDivision() {
	    assertEquals(3, calculator.divide(10, 5)); // Incorrect: 10 / 5 = 2, not 3
	}

}
