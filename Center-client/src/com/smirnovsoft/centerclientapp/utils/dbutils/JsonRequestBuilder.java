package com.smirnovsoft.centerclientapp.utils.dbutils;

import com.smirnovsoft.centerclientapp.utils.ConnectionServer;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class JsonRequestBuilder {
    private JSONWriter writer;
    private OutputStreamWriter outputStreamWriter;
    private InputStreamReader inputStreamReader;
    private Socket clientSocket;
    private JSONTokener tokener;

    public JsonRequestBuilder() throws IOException {
        clientSocket = ConnectionServer.createConnection();
        outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream());
        writer = new JSONWriter(outputStreamWriter);
        //добавил сюда, нужно проверить
    }

    public JSONWriter getWriter() {
        return writer;
    }

    public JSONTokener getReader() throws IOException {
        inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
        tokener = new JSONTokener(inputStreamReader);
        return tokener;
    }

    public void flushOutput(){
        try {
            outputStreamWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeWriter() {
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

    public void closeReader (){
        try {
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public OutputStreamWriter getOutput() {
        return outputStreamWriter;
    }
}
