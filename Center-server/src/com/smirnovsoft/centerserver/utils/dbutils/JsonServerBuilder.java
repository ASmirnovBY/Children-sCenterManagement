package com.smirnovsoft.centerserver.utils.dbutils;

import com.smirnovsoft.centerserver.server.ServerConnection;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class JsonServerBuilder {
    private JSONWriter writer;
    private JSONTokener tokener;
    private JSONObject jsonObject;
    private OutputStreamWriter outputStreamWriter;
    private InputStreamReader inputStreamReader;
    private Socket clientSocket;
    private ServerConnection serverConnection;
    private String command;

    public JsonServerBuilder() throws IOException {
        clientSocket = serverConnection.getClientSocket();
        outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream());
        writer = new JSONWriter(outputStreamWriter);
        inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
        tokener = new JSONTokener(inputStreamReader);
    }

    public JSONTokener getTokener() {
        return tokener;
    }

    public void flush(){
        try {
            outputStreamWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONWriter getWriter() {
        return writer;
    }
}
