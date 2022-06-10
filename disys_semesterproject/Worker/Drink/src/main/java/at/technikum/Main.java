package at.technikum;

import at.technikum.service.DrinkService;

public class Main {
    private final static String BROKER_URL = "tcp://localhost:6616";

    public static void main(String[] args) {
        DrinkService foodService = new DrinkService("DRINK", "DONE", BROKER_URL);
        foodService.run();
    }
}