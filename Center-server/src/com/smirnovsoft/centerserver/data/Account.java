package com.smirnovsoft.centerserver.data;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private String login;
    private String password;
    private static List<Account> listAccount = null;
    private static final String pathDb = "DB/account.json";


    public Account(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Account{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static List<Account> getAllAccount(){
        listAccount = new ArrayList<>();
        FileReader reader = null;
        JSONTokener tokener = null;
        JSONArray arrayAccount = null;
        JSONObject object = null;
        String login = null;
        String password = null;

        try {
            reader = new FileReader(pathDb);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        tokener = new JSONTokener(reader);
        arrayAccount = (JSONArray) tokener.nextValue();

        for(int i = 0; i < arrayAccount.length(); i++){
            object = ((JSONObject) arrayAccount.get(i));
            login = object.getString("login");
            password = object.getString("password");
            listAccount.add(new Account(login, password));
        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listAccount;
    }
}
