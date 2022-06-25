package at.technikum;

import at.technikum.service.PdfGeneratorService;

public class Main {

    private final static String BROKER_URL = "tcp://localhost:6616";

    public static void main(String[] args) {
        PdfGeneratorService PdfGeneratorService = new PdfGeneratorService (
                "DATACOLLECTIONRECEIVER"
                , "DONE"
                , BROKER_URL);
        PdfGeneratorService.run();
    }
}