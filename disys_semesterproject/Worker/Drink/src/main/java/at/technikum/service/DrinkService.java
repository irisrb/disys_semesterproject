package at.technikum.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class DrinkService extends BaseService {

    private final String id;

    private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/dist?user=distuser&password=distpw";

    public DrinkService(String inDestination, String outDestination, String brokerUrl) {
        super(inDestination, outDestination, brokerUrl);

        this.id = UUID.randomUUID().toString();

        System.out.println("Drink Worker (" + this.id + ") started...");
    }

    @Override
    protected String executeInternal(String input) {
        String jobId = UUID.randomUUID().toString();

        try (Connection conn = connect()) {
            String sql = "INSERT INTO orders (type, name, status, job_id, worker_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, "DRINK");
            preparedStatement.setString(2, input);
            preparedStatement.setString(3, "working...");
            preparedStatement.setString(4, jobId);
            preparedStatement.setString(5, this.id);

            preparedStatement.execute();
        } catch (SQLException e) {
            return "error";
        }

        try {
            Random r = new Random();
            int low = 10000;
            int high = 30000;
            int result = r.nextInt(high-low) + low;

            Thread.sleep(result);
        } catch (InterruptedException e) {
            return "error";
        }

        try (Connection conn = connect()) {
            String sql = "UPDATE orders SET status = ?, last_updated = ? WHERE job_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, "done!");
            preparedStatement.setObject(2, LocalDateTime.now());
            preparedStatement.setString(3, jobId);

            preparedStatement.execute();
        } catch (SQLException e) {
            return "error";
        }

        return "ok";
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_CONNECTION);
    }
}
