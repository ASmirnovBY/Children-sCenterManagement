package com.smirnovsoft.centerclientapp.utils.dbutils;

import com.smirnovsoft.centerclientapp.data.Account;
import org.json.JSONObject;


import java.io.IOException;


public class CommandAccount {
    private Account account;
    private JsonRequestBuilder jsonBuilder = new JsonRequestBuilder();

    public CommandAccount() throws IOException {

    }

    public void sendRequestAccount(Account account) throws IOException {

        if (account != null) {
            jsonBuilder.getWriter().object().key("db").value("account")
                    .key("command").value("access")
                    .key("login").value(account.getLogin())
                    .key("password").value(account.getPassword())
                    .endObject();
            jsonBuilder.flushOutput();
            jsonBuilder.flushOutput();
        }
    }

    public boolean getAcces() throws IOException {

        boolean isGood = false;
        String access = null;
        JSONObject objectResponce = (JSONObject) jsonBuilder.getReader().nextValue();
        access = objectResponce.getString("access");

        if (access.equals("Y")) {
            isGood = true;
        }
       jsonBuilder.closeReader();
        return isGood;
    }
}
