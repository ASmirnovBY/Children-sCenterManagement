package com.smirnovsoft.centerserver.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection {

    private static int PORT = 8080;

    private static ServerSocket serverSocket;
    private static Socket clientSocket;

    public ServerConnection() throws IOException {
        serverSocket = new ServerSocket(PORT);

    }

    public static ServerSocket getServerSocket() {
        return serverSocket;
    }

    public static Socket getClientSocket() throws IOException {
        return clientSocket = serverSocket.accept();
    }

    public static InputStream getInputSocket() throws IOException {
        return clientSocket.getInputStream();
    }

    public static OutputStream getOutputSocket() throws IOException {
        return clientSocket.getOutputStream();
    }

    public static void closeSocket() throws IOException {
        getInputSocket().close();
    }
}
