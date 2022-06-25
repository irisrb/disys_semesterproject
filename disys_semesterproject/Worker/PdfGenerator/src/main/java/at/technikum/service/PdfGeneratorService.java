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

// copied from iText
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;


public class PdfGeneratorService extends BaseService {

    private final String id;
    public static final String LOREM_IPSUM_TEXT = "Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";


    private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/dist?user=distuser&password=distpw";

    public PdfGeneratorService(String inDestination, String outDestination, String brokerUrl) {
        super(inDestination, outDestination, brokerUrl);

        this.id = UUID.randomUUID().toString();

        System.out.println("PdfGeneratorService Worker (" + this.id + ") started...");
    }

//    @Override
//    protected String executeInternal(String input) {
//
//        JSONObject jo = new JSONObject(input);
//
//        // declare variables
//        String customerid = jo.get("customerid").toString(); // wird das überhaupt benötigt?
//        String firstname = "";
//        String lastname = "";
//        String address = "";
//        String zip = "";
//        String city = "";
//        String country = "";
//        String myCustomerData = "";
//
//        String kwh = "";
//        String chargingdate = "";
//        String stationid = "";
//        String myStationIds = "";
//
//        Object valueChargingData = jo.get("chargingData");
//        JSONArray arrChargingData = ((JSONArray) valueChargingData);
//        for (int i = 0; i < arrChargingData.length(); i ++){
//            myStationIds = myStationIds + arrChargingData.getJSONObject(i).get("stationid").toString() + ",";
//        }
//
//        // CustomerData ist redundant ? kann man die Daten könnte man auch von DB Abfragen
//        Object valueCustomerData = jo.get("customerData");
//        JSONArray arrCustomerData = ((JSONArray) valueCustomerData);
//        for (int i = 0; i < arrCustomerData.length(); i ++){
//            myCustomerData = myCustomerData + arrCustomerData.getJSONObject(i).toString() + ",";
//        }
//
//        // instanctiate variables
//        String filename = new Date().getTime()+"_"+customerid+".pdf";
//        firstname = assertEquals
//
//        PdfWriter writer = new PdfWriter(filename);
//        PdfDocument pdf = new PdfDocument(writer);
//        Document document = new Document(pdf);
//
//        return jo.toString();
//    }

        @Override
        protected String executeInternal(String input) {

        JSONObject jo = new JSONObject(input);

        // declare variables
        String customerid = jo.get("customerid").toString(); // wird das überhaupt benötigt?

        String kwh = "";
        String chargingdate = "";
        String stationid = "";
        String myStationIds = "";

        Object valueChargingData = jo.get("chargingData");
        JSONArray arrChargingData = ((JSONArray) valueChargingData);
        for (int i = 0; i < arrChargingData.length(); i ++){
            myStationIds = myStationIds + arrChargingData.getJSONObject(i).get("stationid").toString() + ",";
        }

        // CustomerData ist redundant ? kann man die Daten könnte man auch von DB Abfragen
        Object valueCustomerData = jo.get("customerData");
        JSONArray arrCustomerData = ((JSONArray) valueCustomerData);

        try {
            String firstname = Array.get(arrCustomerData, 2);
            String lastname = Array.get(arrCustomerData, 5);
            String address = Array.get(arrCustomerData, 3);
            String zip = Array.get(arrCustomerData, 0);
            String city = Array.get(arrCustomerData, 4);
            String country = Array.get(arrCustomerData, 1);
        } catch (Exception e) {
            // throws Exception
            System.out.println("Exception : " + e);
        }


        // instanctiate variables
        String filename = new Date().getTime()+"_"+customerid+".pdf";
        firstname = assertEquals

        PdfWriter writer = new PdfWriter(filename);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        Paragraph header = new Paragraph("Invoice DISYS")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(14)
                .setBold();
        document.add(header);
        document.add(new Paragraph(LOREM_IPSUM_TEXT));

        document.close();


        return jo.toString();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_CONNECTION);
    }
}
