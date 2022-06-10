package at.technikum.orderapi.dto;

public class Order {

    public String type;
    public String name;
    public String status;

    public Order() {
    }

    public Order(
            String type,
            String name
    ) {
        this.type = type;
        this.name = name;
        this.status = "send...";
    }


}
