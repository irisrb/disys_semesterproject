package at.technikum.service;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import at.technikum.communication.Producer;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.text.StyleConstants;

public class DataCollectionReceiverService extends BaseService {

    private final String id;

    private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/dist?user=distuser&password=distpw";

    public DataCollectionReceiverService(String inDestination1, String inDestination2, String outDestination, String brokerUrl) {
        super(inDestination1, inDestination2, outDestination, brokerUrl);

        this.id = UUID.randomUUID().toString();

        System.out.println("DataCollectionReceiverService Worker (" + this.id + ") started...");
    }

    @Override
    protected String executeInternal(String input) {

        JSONObject jo = new JSONObject(input);
        String customerid = jo.get("customerid").toString();

        try (Connection conn = connect()) {
            //String sql = "INSERT INTO orders (type, name, status, job_id, worker_id) VALUES (?, ?, ?, ?, ?)";
            String sql = "select firstname, lastname, address , zip , city,country  from public.customer c where id = ?;";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, Integer.parseInt(customerid));

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                JSONObject c = new JSONObject();
                c.put("firstname", resultSet.getString ("firstname"));
                c.put("lastname", resultSet.getString("lastname"));
                c.put("address", resultSet.getString("address"));
                c.put("zip", resultSet.getInt("zip"));
                c.put("city", resultSet.getString("city"));
                c.put("country", resultSet.getString("country"));
                jo.append("customerData", c);
            }
        } catch (SQLException e) {
            return "error";
        }
        return jo.toString();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_CONNECTION);
    }
}
