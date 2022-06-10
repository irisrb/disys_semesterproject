package at.technikum.javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OrderController {

    private static final String API = "http://localhost:8080/orders";

    @FXML
    private TextField foodInput;

    @FXML
    private TextField drinkInput;

    @FXML
    private ListView<String> orderList;

    @FXML
    private void orderFood() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(API + "/food/" + foodInput.getText()))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        foodInput.setText("");
    }

    @FXML
    private void orderDrink() {

    }

    @FXML
    private void updateList() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(API))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        // System.out.println(response.body());
        JSONArray jsonArray = new JSONArray(response.body());

        ObservableList<String> orders = FXCollections.observableArrayList();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            orders.add(jsonObject.toString());
        }

        orderList.setItems(orders);
    }
}
