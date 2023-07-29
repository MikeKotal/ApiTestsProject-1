package scooter.helpers;

public interface Constants {

    String SCOOTER_URL = "http://qa-scooter.praktikum-services.ru";
    String COURIER = "/api/v1/courier";
    String COURIER_LOGIN = "/api/v1/courier/login";
    String ORDERS = "/api/v1/orders";
    String BLACK = "BLACK";
    String GREY = "GREY";
    String LOGIN = "Pupa";
    String PASSWORD = "12345";
    String FIRST_NAME = "Lupa";

    String ERROR_SIMILAR_COURIERS = "Этот логин уже используется. Попробуйте другой.";
    String ERROR_NOT_ENOUGH_INPUT_DATES = "Недостаточно данных для создания учетной записи";
    String ERROR_LOGIN_NOT_FOUND = "Учетная запись не найдена";
    String ERROR_LOGIN = "Недостаточно данных для входа";

}
