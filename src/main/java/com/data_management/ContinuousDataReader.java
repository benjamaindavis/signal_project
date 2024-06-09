package com.data_management;

import org.java_websocket.client.WebSocketClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ContinuousDataReader implements DataReader {

    MyWebSocketClient client;
    /**
     * @param server the server where the data comes from
     * @throws IOException
     */
    public ContinuousDataReader(String server) throws URISyntaxException {
        this.client = new MyWebSocketClient(new URI(server));
    }

    /**
     * @param storage the storage where data will be stored
     */
    @Override
    public void startReading(DataStorage storage) {
        client.setStorage(storage);
        client.connect();
    }
}
