package com.smirnovsoft.centerclientapp.utils;

import javafx.scene.control.Alert;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class ConnectionServer {
    private static Socket clientSocket;

    private static final String PATH_PROPERTIES = "connection.properties";

    private ConnectionServer(){}

    public static Socket createConnection() throws IOException {
        Properties prop = new Properties();
        FileReader reader = null;
        String host = null;
        String strPort = null;
        int port = 0;
        try {
            reader = new FileReader(new File(PATH_PROPERTIES));
            prop.load(reader);
            host = prop.getProperty("host");
            strPort = prop.getProperty("port");
            port = Integer.valueOf(strPort);


        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

        try {
            clientSocket = new Socket(host, port);
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Connection is lost, try start app again!");
            alert.showAndWait();
        }

        reader.close();
        return clientSocket;
    }

}
