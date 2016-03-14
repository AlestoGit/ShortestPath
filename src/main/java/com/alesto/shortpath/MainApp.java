package com.alesto.shortpath;

import com.alesto.shortpath.graph.Graph;
import com.alesto.shortpath.view.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created on 16.02.2016.
 */
public class MainApp extends Application {

    //contains all nodes and connections
    private Graph graph;

    //reference to the main controller
    private MainController mainController;

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Find the Shortest Path");

        initRootLayout();
        initMainLayout();

        graph = new Graph(mainController.getMainPane());
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));

            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void initMainLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/main.fxml"));

            AnchorPane mainLayout = loader.load();

            rootLayout.setCenter(mainLayout);

            //creating reference to this object in controller
            mainController = loader.getController();
            mainController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Graph getGraph() {
        return graph;
    }
}
