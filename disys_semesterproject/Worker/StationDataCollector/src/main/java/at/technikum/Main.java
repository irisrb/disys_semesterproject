package at.technikum;

import at.technikum.service.StationDataCollectorService;

public class Main {

    private final static String BROKER_URL = "tcp://localhost:6616";

    public static void main(String[] args) {
        StationDataCollectorService StationDataCollectorService = new StationDataCollectorService("STATIONDATACOLLECTOR", "DATACOLLECTIONRECEIVER", BROKER_URL);
        StationDataCollectorService.run();
    }
}