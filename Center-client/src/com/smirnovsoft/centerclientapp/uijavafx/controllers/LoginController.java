package com.smirnovsoft.centerclientapp.uijavafx.controllers;

import com.smirnovsoft.centerclientapp.data.Account;
import com.smirnovsoft.centerclientapp.uijavafx.base.WindowProperties;
import com.smirnovsoft.centerclientapp.uijavafx.base.WindowsManager;


import com.smirnovsoft.centerclientapp.utils.dbutils.CommandAccount;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONWriter;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController {

    private Account ac;
    private Socket clientSocket;
    private JSONWriter jsonWriter;
    private InputStream inputStream;
    private BufferedReader reader;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label autorization;

    @FXML
    private TextField tPassword;

    @FXML
    private Button bLogin;

    @FXML
    private TextField tUserName;

    @FXML
    void initialize()  {

        bLogin.setOnAction(event -> {
            CommandAccount cmAccount = null;
            Account account = null;
            String login = tUserName.getText();
            String password = tPassword.getText();
            account = new Account(login,password);

            try {
                cmAccount = new CommandAccount();
                cmAccount.sendRequestAccount(account);
                if (cmAccount.getAcces() == true) {
                    WindowsManager.getManager().hideWindow(WindowProperties.LoginWindow);
                    WindowsManager.getManager().showWindow(WindowProperties.MainWindow);
                } else {
                    autorization.setText("NO");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
