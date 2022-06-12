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

    public DataCollectionReceiverService(int stationNumber, String stationData, String brokerUrl) {
        super(stationNumber, stationData, brokerUrl);

        this.id = UUID.randomUUID().toString();

        System.out.println("DataCollectionReceiver Worker (" + this.id + ") started...");
    }

    @Override
    protected String executeInternal(String input) {

        System.out.println("message was: " + input);
//        JSONArray jo = new JSONArray("[" + input + "]");
        JSONObject jo = new JSONObject(input);
        // read station ids!
        String myStationIds = "";
        Object value = jo.get("stations");
        JSONArray arr = ((JSONArray) value);
        for (int i = 0; i < arr.length(); i ++){
            myStationIds = myStationIds + arr.getJSONObject(i).get("stationid").toString() + ",";
        }
        if (myStationIds.length() > 0){
            myStationIds = myStationIds.substring(0, myStationIds.length() - 1);
        }

        String customerid = jo.get("customerid").toString();

        JSONObject chargingData = new JSONObject();
//        String jobId = UUID.randomUUID().toString();

        try (Connection conn = connect()) {
            //String sql = "INSERT INTO orders (type, name, status, job_id, worker_id) VALUES (?, ?, ?, ?, ?)";
            String sql = "SELECT sum(c.kwh) as kwh, c.chargingdate as chargingdate, c.station_id as station_id \n" +
                    "FROM public.charging c \n" +
                    "where c.customer_id = ? and station_id in (" + myStationIds + ") \n" +
                    "group by c.chargingdate, c.station_id \n";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, Integer.parseInt(customerid));

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                JSONObject cd = new JSONObject();
                cd.put("kwh", resultSet.getFloat ("kwh"));
                cd.put("chargingdate", resultSet.getString("chargingdate"));
                cd.put("stationId", resultSet.getInt("station_id"));
                chargingData.append("chargingData", cd);
            }
        } catch (SQLException e) {
            return "error";
        }

        try {
            Random r = new Random();
            int low = 3000;
            int high = 5000;
            int result = r.nextInt(high-low) + low;

            Thread.sleep(result);
        } catch (InterruptedException e) {
            return "error";
        }
        
        try {
            PdfWriter writer = new PdfWriter(System.getProperty("user.home") + "/Customer_id" + jobId + ".pdf");
            PdfDocument pdf = new PdfDocument(writer);
            Document doc = new Document(pdf);

            doc.add(new Paragraph(myStationIds).setFontSize(14).setBold());
            doc.add(new Paragraph(chargingData.format(DateTimeFormatter.ISO_DATE_TIME)));
            doc.add(new Paragraph(.setFontColor(StyleConstants.ColorConstants.RED));

            doc.close();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        
        System.out.println("send data from stationdatacollector to receiver: " + chargingData.toString());
        return chargingData.toString();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_CONNECTION);
    }
}
