package scooter.tests;

import io.qameta.allure.junit4.DisplayName;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import scooter.helpers.Initializer;
import scooter.models.create_courier.CourierResponse;
import scooter.models.login_courier.LoginCourierResponse;

import static scooter.couriers.CouriersHelper.*;
import static scooter.helpers.Constants.*;

public class CreateCourierTest extends Initializer {

    @Test
    @DisplayName("Check success creating new courier")
    public void successCreateCourier() {
        CourierResponse courierResponse = createCourier(LOGIN, PASSWORD, FIRST_NAME, HttpStatus.SC_CREATED);
        LoginCourierResponse loginCourierResponse = loginResponse(LOGIN, PASSWORD, HttpStatus.SC_OK);

        Assert.assertTrue("Ошибка создания курьера", courierResponse.getOk());
        deleteCourier(String.valueOf(loginCourierResponse.getId()));
    }

    @Test
    @DisplayName("Check creating two similar couriers")
    public void negativeCreateSimilarCouriers() {
        createCourier(LOGIN, PASSWORD, FIRST_NAME, HttpStatus.SC_CREATED);

        CourierResponse courierResponse = createCourier(LOGIN, PASSWORD, FIRST_NAME, HttpStatus.SC_CONFLICT);
        LoginCourierResponse loginCourierResponse = loginResponse(LOGIN, PASSWORD, HttpStatus.SC_OK);

        Assert.assertEquals(ERROR_SIMILAR_COURIERS, courierResponse.getMessage());
        deleteCourier(String.valueOf(loginCourierResponse.getId()));
    }

    @Test
    @DisplayName("Check creating courier without login")
    public void negativeCreateCourierWithoutLogin() {
        CourierResponse courierResponse = createCourier("", PASSWORD, FIRST_NAME, HttpStatus.SC_BAD_REQUEST);

        if (courierResponse.getMessage() != null) {
            Assert.assertEquals(ERROR_NOT_ENOUGH_INPUT_DATES, courierResponse.getMessage());
        } else {
            LoginCourierResponse loginCourierResponse = loginResponse("", PASSWORD, HttpStatus.SC_OK);
            deleteCourier(String.valueOf(loginCourierResponse.getId()));
            Assert.fail("Курьер не может быть создан без логина");
        }
    }

    @Test
    @DisplayName("Check creating courier without password")
    public void negativeCreateCourierWithoutPassword() {
        CourierResponse courierResponse = createCourier(LOGIN, "", FIRST_NAME, HttpStatus.SC_BAD_REQUEST);

        if (courierResponse.getMessage() != null) {
            Assert.assertEquals(ERROR_NOT_ENOUGH_INPUT_DATES, courierResponse.getMessage());
        } else {
            LoginCourierResponse loginCourierResponse = loginResponse(LOGIN, "", HttpStatus.SC_OK);
            deleteCourier(String.valueOf(loginCourierResponse.getId()));
            Assert.fail("Курьер не может быть создан без пароля");
        }
    }
}
