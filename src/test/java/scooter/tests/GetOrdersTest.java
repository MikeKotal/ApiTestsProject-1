package scooter.tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Test;
import scooter.helpers.Initializer;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static scooter.helpers.Constants.ORDERS;

public class GetOrdersTest extends Initializer {

    @Test
    @DisplayName("Order array is not empty")
    public void arrayOfOrdersIsNotEmpty() {
        Response response =
                given()
                        .when()
                        .queryParam("limit", "1")
                        .get(ORDERS);

        response.then()
                .assertThat()
                .body("orders", notNullValue())
                .and()
                .statusCode(HttpStatus.SC_OK);

    }
}
