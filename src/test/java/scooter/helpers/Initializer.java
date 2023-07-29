package scooter.helpers;

import io.restassured.RestAssured;
import org.junit.Before;

import static scooter.helpers.Constants.SCOOTER_URL;

public class Initializer {

    @Before
    public void setUp() {
        RestAssured.baseURI = SCOOTER_URL;
    }

}
