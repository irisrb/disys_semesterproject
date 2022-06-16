package at.technikum.datacollectiondispatcherapi.controller;

import at.technikum.datacollectiondispatcherapi.queue.communication.Producer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONObject;

@RestController
public class DataCollectionDispatcherController {

    private final static String BROKER_URL = "tcp://localhost:6616";
    private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/dist?user=distuser&password=distpw";

    @PostMapping("/invoices/{customerid}")
    public String create(@PathVariable String customerid) {

        System.out.println("customer data received");
        JSONObject stationDataCollection = new JSONObject();
        stationDataCollection.put("requestid", UUID.randomUUID().toString());
        stationDataCollection.put("customerid", customerid);
        stationDataCollection = readAll(stationDataCollection);

        String sendJson = stationDataCollection.toString();
        System.out.println("data is: " + sendJson);

        Producer.send(sendJson, "DATACOLLECTIONDISPATCHER", BROKER_URL);
        Producer.send(sendJson, "STATIONDATACOLLECTOR", BROKER_URL);

        return sendJson;
    }

    public JSONObject readAll(JSONObject jo) {

        try (Connection conn = connect()) {
            String sql = "SELECT * FROM public.station";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                JSONObject station = new JSONObject();
                station.put("available", resultSet.getString("available"));
                station.put("stationid", resultSet.getInt("id"));
                jo.append("stations", station);
            }

        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return jo;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_CONNECTION);
    }
}
