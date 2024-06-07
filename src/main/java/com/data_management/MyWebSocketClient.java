package com.data_management;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class MyWebSocketClient extends WebSocketClient {
    private DataStorage storage;

    public MyWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    /**
     * @param serverHandshake
     */
    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("Client is Connected");
    }

    /**
     * @param message
     */
    @Override
    public void onMessage(String message) {
        System.out.println(message);

        String[] messageArray = message.split(",");
        int patientID = Integer.parseInt(messageArray[0]);
        long timeStamp = Long.parseLong(messageArray[1]);
        String type = messageArray[2];
        if(messageArray[3].endsWith("%")) {
            messageArray[3] = messageArray[3].substring(0, messageArray[3].length() -1);
        }
        double value = Double.parseDouble(messageArray[3]);
        storage.addPatientData(patientID, value, type, timeStamp);
    }

    /**
     * @param i
     * @param s
     * @param b
     */
    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("Connection is Closed");
    }

    /**
     * @param e
     */
    @Override
    public void onError(Exception e) {
        System.out.println("There is an Error"+e);
    }

    public void setStorage(DataStorage storage) {
        this.storage = storage;
    }
}
