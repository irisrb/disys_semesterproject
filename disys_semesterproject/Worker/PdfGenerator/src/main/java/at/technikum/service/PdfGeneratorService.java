package at.technikum.service;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;

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
    public static final String HEADER = "Ausstellungsdatum: " + new Date();
    public static final String COMPANYADDRESS = "Teststreet 123" + "\n1010 Vienna";
    public static final String FOOTER = "UID 123456789, ECars Ltd" + "\ninvoice incl 20% VAT";

    public PdfGeneratorService(String inDestination, String outDestination, String brokerUrl) {
        super(inDestination, outDestination, brokerUrl);

        this.id = UUID.randomUUID().toString();

        System.out.println("PdfGeneratorService Worker (" + this.id + ") started...");
    }

    @Override
    protected String executeInternal(String input) throws IOException{

        System.out.println("Data received:" + input);
        JSONObject jo = new JSONObject(input);

        String filename = new Date().getTime()+"_invoice.pdf"; // Date Time format könnte angepasst werden

        PdfWriter writer = new PdfWriter(filename);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph(HEADER));

        Paragraph companyName = new Paragraph("ECars Ltd")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(14)
                .setBold()
                .setFontColor(ColorConstants.BLUE);
        document.add(companyName);

        document.add(new Paragraph(COMPANYADDRESS));

        Paragraph title = new Paragraph("Invoice")
                .setFontSize(18)
                .setBold();
        document.add(title);

        Paragraph customerDataTitle = new Paragraph("Customer Data")
                .setFontSize(14)
                .setBold()
                .setFontColor(ColorConstants.BLUE);
        document.add(customerDataTitle);

        Object objCustomerData = jo.get("customerData");
        String customer = String.valueOf(objCustomerData);

//        remove char from string for later use
        customer = customer.replace("\"", "").replace("[{","").replace("}]","");
        String[] words = customer.split(",");

//      customer variables
        String firstname_text = words[2].substring(10);
        String lastname_text = words[5].substring(9);
        String zip_text = words[0].substring(4);
        String country_text = words[1].substring(8);
        String city_text = words[4].substring(5);
        String address_text = words[3].substring(8);


        Paragraph firstname = new Paragraph("Firstname:")
                .setFontSize(12)
                .setBold()
                .setFontColor(ColorConstants.GRAY);
        document.add(firstname);
        document.add(new Paragraph(firstname_text));

        Paragraph lastname = new Paragraph("Lastname:")
                .setFontSize(12)
                .setBold()
                .setFontColor(ColorConstants.GRAY);
        document.add(lastname);
        document.add(new Paragraph(lastname_text));

        Paragraph address = new Paragraph("Address:")
                .setFontSize(12)
                .setBold()
                .setFontColor(ColorConstants.GRAY);
        document.add(address);
        document.add(new Paragraph(address_text));

        Paragraph zip = new Paragraph("Zip:")
                .setFontSize(12)
                .setBold()
                .setFontColor(ColorConstants.GRAY);
        document.add(zip);
        document.add(new Paragraph(zip_text));

        Paragraph city = new Paragraph("City:")
                .setFontSize(12)
                .setBold()
                .setFontColor(ColorConstants.GRAY);
        document.add(city);
        document.add(new Paragraph(city_text));

        Paragraph country = new Paragraph("Country:")
                .setFontSize(12)
                .setBold()
                .setFontColor(ColorConstants.GRAY);
        document.add(country);
        document.add(new Paragraph(country_text));

//        Charging Data table
        Paragraph chargingDataTitle = new Paragraph("Charging Data")
                .setFontSize(14)
                .setBold()
                .setFontColor(ColorConstants.BLUE);
        document.add(chargingDataTitle);

        document.add(new Paragraph(FOOTER));

        document.close();
        System.out.println("Write pdf to file: " + filename);
        return "";
    }
}
