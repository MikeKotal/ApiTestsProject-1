package ru.yandex.praktikum.tests;

import com.google.gson.Gson;
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

import static io.restassured.RestAssured.given;
import static ru.yandex.praktikum.helpers.DataPicker.getData;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    CreateOrderRequest createOrderRequest;

    public CreateOrderTest(CreateOrderRequest createOrderRequest) {
        this.createOrderRequest = createOrderRequest;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderDates() {
        String endPath = "createOrder";
        return new Object[][]{
                {new Gson().fromJson(getData(endPath, "successCreateOrderBlack"), CreateOrderRequest.class)},
                {new Gson().fromJson(getData(endPath, "successCreateOrderGrey"), CreateOrderRequest.class)},
                {new Gson().fromJson(getData(endPath, "successCreateOrderBlackAndGrey"), CreateOrderRequest.class)},
                {new Gson().fromJson(getData(endPath, "successCreateOrderWithoutColor"), CreateOrderRequest.class)}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Check success creating orders")
    public void successCreateOrder() {
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
