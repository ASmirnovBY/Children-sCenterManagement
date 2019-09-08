package com.smirnovsoft.centerserver.utils.dbutils;

import com.smirnovsoft.centerserver.data.Account;
import com.smirnovsoft.centerserver.server.ServerConnection;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class ProcessorAccount extends ProccessorSpecific {
    private final String pathDb = "DB/account.json";
    private Account account;
    private JSONTokener tokener;
    private JSONObject object;
    private JSONWriter writer;
    private OutputStreamWriter outputStreamWriter;

    public ProcessorAccount(JSONTokener tokener, JSONObject object) {
        this.tokener = tokener;
        this.object = object;
    }

    private boolean getAccess() throws IOException {

        String login = object.getString("login");
        String password = object.getString("password");
        account = new Account(login, password);
        outputStreamWriter = new OutputStreamWriter(ServerConnection.getOutputSocket());
        writer = new JSONWriter(outputStreamWriter);
        boolean isRightAccount = false;

        for (Account ac : Account.getAllAccount()) {

            if ((ac.getLogin().equals(account.getLogin()))
                    && (ac.getPassword().equals(account.getPassword()))){
               isRightAccount = true;
           }
            System.out.println(isRightAccount);

        }

        if(isRightAccount == true) {
            writer.object().key("access").value("Y").endObject();
        } else {
            writer.object().key("access").value("N").endObject();
        }

        outputStreamWriter.flush();
        outputStreamWriter.close();

        return isRightAccount;
    }

    @Override
    public void operation(String operation) {

        switch(operation){
            case "access":
                try {
                    getAccess();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("есть запрос");
                break;
        }
    }
}
