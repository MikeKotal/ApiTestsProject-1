package ru.yandex.praktikum.tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.models.createOrder.CreateOrderRequest;
import ru.yandex.praktikum.models.createOrder.CreateOrderResponse;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static ru.yandex.praktikum.Constants.*;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final ArrayList<String> colors;

    public CreateOrderTest(String firstColor, String secondColor) {
        ArrayList<String> colors = new ArrayList<>();
        colors.add(firstColor);
        colors.add(secondColor);
        this.colors = colors;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderDates() {
        return new Object[][]{
                {BLACK, null},
                {GREY, null},
                {BLACK, GREY},
                {null, null}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = SCOOTER_URL;
    }

    @Test
    @DisplayName("Check success creating orders")
    public void successCreateOrder() {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setColor(colors);

        CreateOrderResponse createOrderResponse =
                given()
                        .header("Content-type", "application/json")
                        .body(createOrderRequest)
                        .when()
                        .post("/api/v1/orders")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.SC_CREATED)
                        .extract()
                        .body()
                        .as(CreateOrderResponse.class);

        Assert.assertNotNull("Номер заказа не создан", createOrderResponse.getTrack());
    }

}
