package at.technikum;

import at.technikum.service.FoodService;

public class Main {

    private final static String BROKER_URL = "tcp://localhost:6616";

    public static void main(String[] args) {
        FoodService foodService = new FoodService("FOOD", "DONE", BROKER_URL);
        foodService.run();
    }
}