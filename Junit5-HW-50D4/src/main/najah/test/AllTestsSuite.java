package main.najah.test;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        CalculatorTest.class,
        ProductTest.class,
        UserServiceTest.class,
        RecipeBookTest.class
})
public class AllTestsSuite {
    // This class is used only as a holder for the above annotations to run all
    // tests together.
}
