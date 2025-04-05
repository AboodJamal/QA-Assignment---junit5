package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import main.najah.code.UserService;
import java.time.Duration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("User Service Tests")
public class UserServiceTest {
	private UserService userService;

	@BeforeAll
	static void setupAll() {
		System.out.println("Starting UserService tests...");
	}

	@AfterAll
	static void teardownAll() {
		System.out.println("Finished UserService tests.");
	}

	@BeforeEach
	void setup() {
		userService = new UserService();
        System.out.println("Setting up BeforeEach on userService");
	}
	
	@AfterEach
    void teardown() {
        System.out.println("Tearing down UserServiceTest");
    }

	@ParameterizedTest(name = "isValidEmail: {0}")
	@ValueSource(strings = { "test@example.com", "user@domain.co", "email@sub.domain.com" })
	@DisplayName("Parameterized Test for valid emails")
	void testValidEmails(String email) {
		assertTrue(userService.isValidEmail(email), "Email should be valid");
	}

	@ParameterizedTest(name = "isValidEmail should be false for: {0}")
	@ValueSource(strings = { "", "plainaddress", "missingatsign.com", "missingdot@com" })
	@DisplayName("Parameterized Test for invalid emails")
	void testInvalidEmails(String email) {
		assertFalse(userService.isValidEmail(email), "Email should be invalid");
	}

	@Test
	@DisplayName("Test authentication with valid credentials")
	void testAuthenticateValid() {
		assertTrue(userService.authenticate("admin", "1234"), "Authentication should succeed");
	}

	@Test
	@DisplayName("Test authentication with invalid credentials")
	void testAuthenticateInvalid() {
		assertFalse(userService.authenticate("user", "wrong"), "Authentication should fail");
	}

	@Test
	@DisplayName("Test email validation within timeout")
	void testEmailValidationTimeout() {
		assertTimeout(Duration.ofMillis(100), () -> {
			assertTrue(userService.isValidEmail("test@example.com"));
		});
	}

	@Test
	@DisplayName("Test multiple assertions on user service")
	void testMultipleAssertions() {
		assertAll("UserService tests",
				() -> assertTrue(userService.isValidEmail("valid@domain.com")),
				() -> assertFalse(userService.isValidEmail("invalid")),
				() -> assertTrue(userService.authenticate("admin", "1234")),
				() -> assertFalse(userService.authenticate("user", "pass")));
	}
}
