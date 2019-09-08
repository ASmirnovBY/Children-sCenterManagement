package com.smirnovsoft.centerclientapp.uijavafx.models;

import com.smirnovsoft.centerclientapp.uijavafx.base.WindowProperties;
import com.smirnovsoft.centerclientapp.uijavafx.base.WindowsManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class LoginForm extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
       WindowsManager.getManager().showWindow(WindowProperties.LoginWindow);
    }
}
