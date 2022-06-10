package at.technikum.orderapi.controller;

import at.technikum.orderapi.dto.Order;
import at.technikum.orderapi.queue.communication.Producer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {

    private final static String BROKER_URL = "tcp://localhost:6616";
    private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/dist?user=distuser&password=distpw";

    @PostMapping("/orders/{type}/{name}")
    public Order create(@PathVariable String type, @PathVariable String name) {

        Producer.send(name, type.toUpperCase(), BROKER_URL);

        return new Order(type, name);
    }

    @GetMapping("/orders")
    public List<Order> readAll() {
        List<Order> orders = new ArrayList<>();

        try (Connection conn = connect()) {
            String sql = "SELECT * FROm orders";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Order order = new Order();

                order.type = resultSet.getString("type");
                order.name = resultSet.getString("name");
                order.status = resultSet.getString("status");

                orders.add(order);
            }

        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return orders;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_CONNECTION);
    }
}
