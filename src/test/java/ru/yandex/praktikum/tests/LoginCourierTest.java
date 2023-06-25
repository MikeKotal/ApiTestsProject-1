package ru.yandex.praktikum.tests;

import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.models.createCourier.CourierRequest;
import ru.yandex.praktikum.models.loginCourier.LoginCourierRequest;
import ru.yandex.praktikum.models.loginCourier.LoginCourierResponse;

import static io.restassured.RestAssured.given;
import static ru.yandex.praktikum.Constants.SCOOTER_URL;
import static ru.yandex.praktikum.helpers.DataPicker.getData;

public class LoginCourierTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = SCOOTER_URL;
    }

    @Step("Create test courier before login courier")
    public void createCourier(CourierRequest courierRequest) {
        given()
                .header("Content-type", "application/json")
                .body(courierRequest)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Delete test courier after login courier")
    public void deleteCourier(String id) {
        given().when().delete("/api/v1/courier/" + id).then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    @DisplayName("Check success login courier")
    public void successLoginCourier() {
        CourierRequest courierRequest =
                new Gson().fromJson(getData("createCourier", "createCourierRequest"), CourierRequest.class);
        createCourier(courierRequest);
        LoginCourierRequest loginCourierRequest = new LoginCourierRequest();
        loginCourierRequest.setLogin(courierRequest.getLogin());
        loginCourierRequest.setPassword(courierRequest.getPassword());

        LoginCourierResponse loginCourierResponse =
                given()
                        .header("Content-type", "application/json")
                        .body(loginCourierRequest)
                        .when()
                        .post("/api/v1/courier/login")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .body()
                        .as(LoginCourierResponse.class);

        Assert.assertNotNull("ID не получен", loginCourierResponse.getId());
        deleteCourier(String.valueOf(loginCourierResponse.getId()));
    }

    @Test
    @DisplayName("Check response with invalid login and password")
    public void negativeInvalidLoginAndPassword() {
        LoginCourierRequest loginCourierRequest =
                new Gson().fromJson(getData("loginCourier", "negativeInvalidLoginAndPassword"), LoginCourierRequest.class);
        String expectedMessage = "Учетная запись не найдена";

        LoginCourierResponse loginCourierResponse =
                given()
                        .header("Content-type", "application/json")
                        .body(loginCourierRequest)
                        .when()
                        .post("/api/v1/courier/login")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.SC_NOT_FOUND)
                        .extract()
                        .body()
                        .as(LoginCourierResponse.class);

        Assert.assertEquals(expectedMessage, loginCourierResponse.getMessage());
    }

    @Test
    @DisplayName("Check empty login")
    public void negativeLoginIsEmpty() {
        LoginCourierRequest loginCourierRequest =
                new Gson().fromJson(getData("loginCourier", "negativeLoginIsEmpty"), LoginCourierRequest.class);
        String expectedMessage = "Недостаточно данных для входа";

        LoginCourierResponse loginCourierResponse =
                given()
                        .header("Content-type", "application/json")
                        .body(loginCourierRequest)
                        .when()
                        .post("/api/v1/courier/login")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.SC_BAD_REQUEST)
                        .extract()
                        .body()
                        .as(LoginCourierResponse.class);

        Assert.assertEquals(expectedMessage, loginCourierResponse.getMessage());
    }

    @Test
    @DisplayName("Check empty password")
    public void negativePasswordIsEmpty() {
        LoginCourierRequest loginCourierRequest =
                new Gson().fromJson(getData("loginCourier", "negativePasswordIsEmpty"), LoginCourierRequest.class);
        String expectedMessage = "Недостаточно данных для входа";

        LoginCourierResponse loginCourierResponse =
                given()
                        .header("Content-type", "application/json")
                        .body(loginCourierRequest)
                        .when()
                        .post("/api/v1/courier/login")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.SC_BAD_REQUEST)
                        .extract()
                        .body()
                        .as(LoginCourierResponse.class);

        Assert.assertEquals(expectedMessage, loginCourierResponse.getMessage());
    }

}
