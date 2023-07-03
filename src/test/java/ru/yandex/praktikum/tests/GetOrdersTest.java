package ru.yandex.praktikum.tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static ru.yandex.praktikum.Constants.SCOOTER_URL;

public class GetOrdersTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = SCOOTER_URL;
    }

    @Test
    @DisplayName("Order array is not empty")
    public void arrayOfOrdersIsNotEmpty() {
        Response response =
                given()
                        .when()
                        .queryParam("limit", "1")
                        .get("/api/v1/orders");

        response.then()
                .assertThat()
                .body("orders", notNullValue())
                .and()
                .statusCode(HttpStatus.SC_OK);

    }

}
