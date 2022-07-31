package com.satks.pasv;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        Scene landingScene = new Scene(root, 500, 500);
        primaryStage.setScene(landingScene);
        primaryStage.setTitle("PASV");
        primaryStage.resizableProperty().setValue(Boolean.FALSE);
        primaryStage.setMaximized(true);
        
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}