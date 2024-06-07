package com.data_management;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;

public interface DataReader {
    /**
     * Starts reading data from the websocket server.
     *
     * @param dataStorage the storage where data will be stored
     * @throws IOException if there is an error reading the data
     */
    void startReading(DataStorage dataStorage) throws IOException;
}
