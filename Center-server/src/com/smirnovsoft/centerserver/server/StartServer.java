package com.smirnovsoft.centerserver.server;

import com.smirnovsoft.centerserver.utils.dbutils.ProccessorSpecific;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.*;
import java.net.Socket;


public class StartServer {


    public static void main(String[] args) throws IOException {

        ServerConnection connection = new ServerConnection();
        Socket socket = null;
        OutputStreamWriter outputStreamWriter;

        Runnable clientHandler = () -> {
            InputStreamReader inputStreamReader = null;
            try {
                inputStreamReader = new InputStreamReader(ServerConnection.getInputSocket());
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONTokener tokener = new JSONTokener(inputStreamReader);
            JSONObject object = (JSONObject) tokener.nextValue();
            String dbName = object.getString("db");
            String command = object.getString("command");
            ProccessorSpecific specific = ProccessorSpecific.getSpecific(dbName, tokener, object);
            specific.operation(command);
            specific = null;
        };

        while (true) {
            try {
                socket = ServerConnection.getClientSocket();
                Thread runServer = new Thread(clientHandler);
                runServer.run();
                socket.close();

            } catch (Exception e) {
                continue;
            }
            }
        }
    }

