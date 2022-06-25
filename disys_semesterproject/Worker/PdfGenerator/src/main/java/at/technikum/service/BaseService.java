package at.technikum.service;


import at.technikum.communication.Consumer;
import at.technikum.communication.Producer;

import java.util.List;
import java.util.ArrayList;

import org.json.JSONObject;

public abstract class BaseService implements Runnable {

    private final String inDestination1;
    private final String inDestination2;
    private final String outDestination;
    private final String brokerUrl;

    private List<JSONObject> allRequestData  = new ArrayList<>();

    public BaseService(String inDestination,  String outDestination, String brokerUrl) {
        this.inDestination = inDestination;
        this.outDestination = outDestination;
        this.brokerUrl = brokerUrl;
    }

    @Override
    public void run() {
        while (true) {
            execute(inDestination, outDestination, brokerUrl);
        }
    }

    public void execute(String inDestination, String outDestination, String brokerUrl) {
        String input = Consumer.receive(inDestination, 10000, brokerUrl);

        if (input != null){
            System.out.println("message1 was: " + input);
            JSONObject jo = new JSONObject(input);
            // append new data to list
            allRequestData.add(jo);
        }

        if (input2 != null){
            System.out.println("message2 was: " + input2);
            JSONObject jo = new JSONObject(input2);
            String requestid = jo.get("requestid").toString();
            // search requestid in allRequestData
            for (int i = 0; i < allRequestData.size(); i++) {
                JSONObject data = allRequestData.get(i);
                System.out.println("allRequestData " + data.toString());
                String requestidData = data.get("requestid").toString();
                if (requestid.equals(requestidData)){
                    String customerid = data.get("customerid").toString();
                    allRequestData.remove(i);
                    // get customer data from database
                    jo.put("customerid", customerid);
                    String output = executeInternal(jo.toString());
                    System.out.println("to sender " + output);
                    Producer.send(output, outDestination, brokerUrl);
                }
            }
        }
        // return null if all input is null
        return;
    }

    protected abstract String executeInternal(String input);
}
