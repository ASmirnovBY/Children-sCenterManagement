package com.smirnovsoft.centerclientapp.uijavafx.base;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WindowsManager {

    public final static WindowsManager manager = new WindowsManager();
    private Map<WindowProperties, Stage> allWindows = new HashMap<>();
    private Map<WindowProperties, Stage> allStage = new HashMap<>();

    private WindowsManager() {
       WindowProperties[] arrayProperties = WindowProperties.values();
       for(WindowProperties window : arrayProperties) {
           try {
               initWindow(window);
           } catch (IOException e) {
               throw new RuntimeException("Some Error has been occured during initialization of the Application's UI",
                            e);
           }
       }

    };

    public static WindowsManager getManager() {
        return manager;
    }

    private void initWindow(WindowProperties window) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(window.getFxmlLocation()));
        Stage stage = new Stage();
        stage.setTitle(window.getTitle());
        stage.setScene(new Scene(root, window.getWidth(), window.getHeight()));
        allWindows.put(window, stage);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        allStage.put(window, stage);
    }

    public void showWindow(WindowProperties window){
        Stage stage = allWindows.get(window);
        if (stage != null) {
            stage.show();
        }
    }

    public void hideWindow(WindowProperties window){
        Stage stage = allWindows.get(window);
        if (stage != null) {
            stage.hide();
        }
    }

    public Stage getStage(WindowProperties window){
        Stage stage = allStage.get(window);
        if (stage != null) {
            return stage;
        }

        return stage;
    }

    public void showUpdateWindow(WindowProperties window)  {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(window.getFxmlLocation()));
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        } finally {
            Stage stage = new Stage();
            stage.setTitle(window.getTitle());
            stage.setScene(new Scene(root, window.getWidth(), window.getHeight()));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }

        }

}
