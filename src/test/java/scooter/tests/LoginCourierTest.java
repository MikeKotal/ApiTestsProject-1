package scooter.tests;

import io.qameta.allure.junit4.DisplayName;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import scooter.helpers.Initializer;
import scooter.models.login_courier.LoginCourierResponse;

import static scooter.couriers.CouriersHelper.*;
import static scooter.helpers.Constants.*;

public class LoginCourierTest extends Initializer {

    @Test
    @DisplayName("Check success login courier")
    public void successLoginCourier() {
        createCourier(LOGIN, PASSWORD, FIRST_NAME, HttpStatus.SC_CREATED);
        LoginCourierResponse loginCourierResponse = loginResponse(LOGIN, PASSWORD, HttpStatus.SC_OK);

        Assert.assertNotNull("ID не получен", loginCourierResponse.getId());
        deleteCourier(String.valueOf(loginCourierResponse.getId()));
    }

    @Test
    @DisplayName("Check response with invalid login and password")
    public void negativeInvalidLoginAndPassword() {
        LoginCourierResponse loginCourierResponse = loginResponse("test", "test", HttpStatus.SC_NOT_FOUND);

        Assert.assertEquals(ERROR_LOGIN_NOT_FOUND, loginCourierResponse.getMessage());
    }

    @Test
    @DisplayName("Check empty login")
    public void negativeLoginIsEmpty() {
        LoginCourierResponse loginCourierResponse = loginResponse("", "test", HttpStatus.SC_BAD_REQUEST);

        Assert.assertEquals(ERROR_LOGIN, loginCourierResponse.getMessage());
    }

    @Test
    @DisplayName("Check empty password")
    public void negativePasswordIsEmpty() {
        LoginCourierResponse loginCourierResponse = loginResponse("test", "", HttpStatus.SC_BAD_REQUEST);

        Assert.assertEquals(ERROR_LOGIN, loginCourierResponse.getMessage());
    }
}
