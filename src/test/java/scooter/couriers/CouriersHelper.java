package scooter.couriers;

import io.qameta.allure.Step;
import org.apache.http.HttpStatus;
import scooter.models.create_courier.CourierRequest;
import scooter.models.create_courier.CourierResponse;
import scooter.models.create_order.CreateOrderRequest;
import scooter.models.create_order.CreateOrderResponse;
import scooter.models.login_courier.LoginCourierRequest;
import scooter.models.login_courier.LoginCourierResponse;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static scooter.helpers.Constants.*;

public class CouriersHelper {

    @Step("Create courier for test")
    public static CourierResponse createCourier(String login, String password, String firstName, int expectedStatus) {
        return given()
                .header("Content-type", "application/json")
                .body(new CourierRequest(login, password, firstName))
                .when()
                .post(COURIER)
                .then()
                .assertThat()
                .statusCode(expectedStatus)
                .extract()
                .body()
                .as(CourierResponse.class);
    }

    @Step("Delete test courier after test")
    public static void deleteCourier(String id) {
        given().when().delete(COURIER + "/" + id).then().statusCode(HttpStatus.SC_OK);
    }

    @Step("Login user and get ID")
    public static LoginCourierResponse loginResponse(String login, String password, int expectedStatus) {
        return given()
                .header("Content-type", "application/json")
                .body(new LoginCourierRequest(login, password))
                .when()
                .post(COURIER_LOGIN)
                .then()
                .assertThat()
                .statusCode(expectedStatus)
                .extract()
                .body()
                .as(LoginCourierResponse.class);
    }

    @Step("Create order for test")
    public static CreateOrderResponse orderResponse(ArrayList<String> colors) {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setColor(colors);
        return given()
                .header("Content-type", "application/json")
                .body(createOrderRequest)
                .when()
                .post(ORDERS)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .body()
                .as(CreateOrderResponse.class);
    }

}
