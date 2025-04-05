package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;
import main.najah.code.Product;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

@DisplayName("Product Tests")
public class ProductTest {

    private Product product;

    @BeforeEach
    void setup() {
        product = new Product("TestProduct", 100.0);
        System.out.println("Setting up ProductTest");
    }

    @AfterEach
    void teardown() {
        System.out.println("Tearing down ProductTest");
    }

    @Test
    @DisplayName("Test product creation with valid price")
    void testProductCreationValid() {
        assertEquals("TestProduct", product.getName());
        assertEquals(100.0, product.getPrice());
        assertEquals(0.0, product.getDiscount(), 0.001);
        assertEquals(100.0, product.getFinalPrice(), 0.001);
    }

    @Test
    @DisplayName("Test product creation with negative price")
    void testProductCreationInvalid() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Product("InvalidProduct", -50.0);
        });
        assertEquals("Price must be non-negative", exception.getMessage());
    }

    @ParameterizedTest(name = "Apply discount {0}% should result in final price {1}")
    @CsvSource({
        "0, 100.0",
        "10, 90.0",
        "20, 80.0",
        "50, 50.0"
    })
    @DisplayName("Parameterized Test for applying valid discounts")
    void testApplyDiscountParameterized(double discount, double expectedFinalPrice) {
        product.applyDiscount(discount);
        assertEquals(discount, product.getDiscount(), 0.001);
        assertEquals(expectedFinalPrice, product.getFinalPrice(), 0.001);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-10, -1, 51, 100})
    @DisplayName("Test applying invalid discounts")
    void testApplyInvalidDiscounts(double invalidDiscount) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            product.applyDiscount(invalidDiscount);
        });
        assertEquals("Invalid discount", exception.getMessage());
    }

    @Test
    @DisplayName("Test discount application within timeout")
    void testDiscountTimeout() {
        assertTimeout(Duration.ofMillis(100), () -> {
            product.applyDiscount(10);
            assertEquals(90.0, product.getFinalPrice(), 0.001);
        });
    }

    @Test
    @Disabled("Intentionally failing test – Fix: Either update expected value to match 10% discount or adjust discount logic if necessary")
    @DisplayName("Disabled test with incorrect expected discount")
    void testIntentionalFailure() {
        product.applyDiscount(10); // Applies a 10% discount
        assertEquals(80.0, product.getFinalPrice(), 0.001); // Will fail if original price ≠ 100.0
    }

    @Test
    @DisplayName("Test multiple assertions on product fields")
    void testMultipleAssertions() {
        product.applyDiscount(20);
        assertAll("Product properties",
            () -> assertEquals("TestProduct", product.getName()),
            () -> assertEquals(100.0, product.getPrice()),
            () -> assertEquals(20.0, product.getDiscount(), 0.001),
            () -> assertEquals(80.0, product.getFinalPrice(), 0.001)
        );
    }

    @Test
    @DisplayName("Test updating discount multiple times")
    void testUpdateDiscount() {
        product.applyDiscount(10);
        assertEquals(90.0, product.getFinalPrice(), 0.001);

        product.applyDiscount(30);
        assertEquals(70.0, product.getFinalPrice(), 0.001);
    }
}
