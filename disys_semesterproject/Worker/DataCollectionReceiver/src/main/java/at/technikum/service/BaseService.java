package at.technikum.service;


import at.technikum.communication.Consumer;
import at.technikum.communication.Producer;

public abstract class BaseService implements Runnable {

    private final int stationNumber;
    private final String stationData;

    private final String brokerUrl;

    public BaseService(int stationNumber, String StationData, String brokerUrl) {
        this.stationNumber = stationNumber;
        this.stationData = stationData;
        this.brokerUrl = brokerUrl;
    }

    @Override
    public void run() {
        while (true) {
            execute(stationNumber, stationData, brokerUrl);
        }
    }

    public void execute(int stationNumber, String stationData, String brokerUrl) {
        String input = Consumer.receive(stationNumber, 10000, brokerUrl);

        if (null == input) {
            return;
        }

        String output = executeInternal(input);
        Producer.send(output, stationData, brokerUrl);
    }

    protected abstract String executeInternal(String input);
}
