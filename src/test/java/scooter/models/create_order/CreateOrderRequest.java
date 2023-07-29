package scooter.models.create_order;

import java.util.ArrayList;

public class CreateOrderRequest {

    private String firstName = "Pupa";
    private String lastName = "Lupa";
    private String address = "Ленина, д.1";
    private String metroStation = "1";
    private String phone = "+7 800 355 35 35";
    private Integer rentTime = 5;
    private String deliveryDate = "2020-06-06";
    private String comment = "Test";
    private ArrayList<String> color;

    public CreateOrderRequest() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public void setMetroStation(String metroStation) {
        this.metroStation = metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getRentTime() {
        return rentTime;
    }

    public void setRentTime(Integer rentTime) {
        this.rentTime = rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ArrayList<String> getColor() {
        return color;
    }

    public void setColor(ArrayList<String> color) {
        this.color = color;
    }
}
