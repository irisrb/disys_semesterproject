package at.technikum;

import at.technikum.service.DataCollectionReceiverService;

public class Main {

    private final static String BROKER_URL = "tcp://localhost:6616";

    public static void main(String[] args) {
        DataCollectionReceiverService DataCollectionReceiverService = new DataCollectionReceiverService(
                "DATACOLLECTIONDISPATCHER"
                , "DATACOLLECTIONRECEIVER"
                , "PDFGENERATOR", BROKER_URL);
        DataCollectionReceiverService.run();

    }
}