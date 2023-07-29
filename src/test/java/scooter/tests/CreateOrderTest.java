package scooter.tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import scooter.helpers.Initializer;
import scooter.models.create_order.CreateOrderResponse;
import scooter.helpers.Constants;

import java.util.ArrayList;

import static scooter.couriers.CouriersHelper.orderResponse;

@RunWith(Parameterized.class)
public class CreateOrderTest extends Initializer {

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
                {Constants.BLACK, null},
                {Constants.GREY, null},
                {Constants.BLACK, Constants.GREY},
                {null, null}
        };
    }

    @Test
    @DisplayName("Check success creating orders")
    public void successCreateOrder() {
        CreateOrderResponse createOrderResponse = orderResponse(colors);

        Assert.assertNotNull("Номер заказа не создан", createOrderResponse.getTrack());
    }
}
