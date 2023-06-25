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
import ru.yandex.praktikum.models.createCourier.CourierResponse;
import ru.yandex.praktikum.models.loginCourier.LoginCourierRequest;
import ru.yandex.praktikum.models.loginCourier.LoginCourierResponse;

import static io.restassured.RestAssured.given;
import static ru.yandex.praktikum.Constants.SCOOTER_URL;
import static ru.yandex.praktikum.helpers.DataPicker.getData;

public class CreateCourierTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = SCOOTER_URL;
    }

    @Step("Delete test courier after create courier")
    public void deleteCourier(String login, String password) {
        LoginCourierRequest loginCourierRequest = new LoginCourierRequest();
        loginCourierRequest.setLogin(login);
        loginCourierRequest.setPassword(password);

        LoginCourierResponse loginCourierResponse =
                given()
                        .header("Content-type", "application/json")
                        .body(loginCourierRequest)
                        .when()
                        .post("/api/v1/courier/login")
                        .body()
                        .as(LoginCourierResponse.class);

        String id = String.valueOf(loginCourierResponse.getId());
        given().when().delete("/api/v1/courier/" + id).then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    @DisplayName("Check success creating new courier")
    public void successCreateCourier() {
        CourierRequest courierRequest =
                new Gson().fromJson(getData("createCourier", "createCourierRequest"), CourierRequest.class);

        CourierResponse courierResponse =
                given()
                        .header("Content-type", "application/json")
                        .body(courierRequest)
                        .when()
                        .post("/api/v1/courier")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.SC_CREATED)
                        .extract()
                        .body()
                        .as(CourierResponse.class);

        Assert.assertTrue("Ошибка создания курьера", courierResponse.getOk());
        deleteCourier(courierRequest.getLogin(), courierRequest.getPassword());
    }

    @Test
    @DisplayName("Check creating two similar couriers")
    public void negativeCreateSimilarCouriers() {
        CourierRequest courierRequest =
                new Gson().fromJson(getData("createCourier", "createCourierRequest"), CourierRequest.class);
        String expectedMessage = "Этот логин уже используется. Попробуйте другой.";

        given()
                .header("Content-type", "application/json")
                .body(courierRequest)
                .when()
                .post("/api/v1/courier");

        CourierResponse courierResponse =
                given()
                        .header("Content-type", "application/json")
                        .body(courierRequest)
                        .when()
                        .post("/api/v1/courier")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.SC_CONFLICT)
                        .extract()
                        .body()
                        .as(CourierResponse.class);

        Assert.assertEquals(expectedMessage, courierResponse.getMessage());
        deleteCourier(courierRequest.getLogin(), courierRequest.getPassword());
    }

    @Test
    @DisplayName("Check creating courier without login")
    public void negativeCreateCourierWithoutLogin() {
        CourierRequest courierRequest =
                new Gson().fromJson(getData("createCourier", "courierRequestWithoutLogin"), CourierRequest.class);
        String expectedMessage = "Недостаточно данных для создания учетной записи";

        CourierResponse courierResponse =
                given()
                        .header("Content-type", "application/json")
                        .body(courierRequest)
                        .when()
                        .post("/api/v1/courier")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.SC_BAD_REQUEST)
                        .extract()
                        .body()
                        .as(CourierResponse.class);

        if (courierResponse.getMessage() != null) {
            Assert.assertEquals(expectedMessage, courierResponse.getMessage());
        } else {
            deleteCourier(courierRequest.getLogin(), courierRequest.getPassword());
            Assert.fail("Курьер не может быть создан без логина");
        }
    }

    @Test
    @DisplayName("Check creating courier without password")
    public void negativeCreateCourierWithoutPassword() {
        CourierRequest courierRequest =
                new Gson().fromJson(getData("createCourier", "courierRequestWithoutPassword"), CourierRequest.class);
        String expectedMessage = "Недостаточно данных для создания учетной записи";

        CourierResponse courierResponse =
                given()
                        .header("Content-type", "application/json")
                        .body(courierRequest)
                        .when()
                        .post("/api/v1/courier")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.SC_BAD_REQUEST)
                        .extract()
                        .body()
                        .as(CourierResponse.class);

        if (courierResponse.getMessage() != null) {
            Assert.assertEquals(expectedMessage, courierResponse.getMessage());
        } else {
            deleteCourier(courierRequest.getLogin(), courierRequest.getPassword());
            Assert.fail("Курьер не может быть создан без пароля");
        }
    }
}
