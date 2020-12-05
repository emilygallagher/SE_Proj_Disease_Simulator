package com.sim;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Class for testing a simulation without taking input from the user.
 *
 * @author Emily Gallagher
 * @version 1.1
 * @since 0.2
 */
public class SimTest extends Application
{
    //----- FIELDS -----
    private static int exitCode_;
    
    //----- CONSTRUCTORS -----
    static
    {
        exitCode_ = 0;
    }
    
    public SimTest()
    {
        super();
    }
    
    //----- METHODS -----
    // STATIC
    public static void main(String[] args)
    {
        try
        {
            launch(args);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            System.exit(exitCode_);
        }
    }
    
    // INSTANCE
    @Override
    public void start(Stage stage) throws Exception
    {
        Button initBtn = new Button("Initialize");
        Button startBtn = new Button("Start");
        Button pauseBtn = new Button("Pause");
        Canvas canvas = new Canvas(900, 900);
        Label dayLabel = new Label("Day: 0");
        Label countsLabel = new Label();
        HBox btnRow = new HBox(initBtn, startBtn, pauseBtn);
        VBox root = new VBox(canvas, btnRow, dayLabel, countsLabel);
        Scene scene = new Scene(root, 1000, 1000);
        
        Disease disease = new Disease();
        SimSettings simSettings = new SimSettings();
        SimManager simManager = new SimManager();
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0.0, 0.0, canvas.getWidth(), canvas.getHeight());
        
        disease.setName("CUSTOM");
        disease.setSpreadDistance(100.0);
        disease.setInfectionRate(0.1f);
        disease.setAsymptomaticRate(0.1f);
        disease.setDeathRate(0.2f);
        disease.setIncubationDays(3);
        disease.setInfectionDays(4);
        
        simSettings.setDisease(disease);
        simSettings.setTotalModifier(0.0);
        simSettings.setSelfIsolationActive(false);
        simSettings.setTotalPopulation(30);
        simSettings.setStartingInfected(3);
        simSettings.setMaxDays(-1);
        simSettings.setDayLength(90);
        simSettings.setPersonRadius(10.0);
        simSettings.setMinMoveSpeed(2.0);
        simSettings.setMaxMoveSpeed(5.0);
        simSettings.setDebugActive(true);
    
        simManager.setCanvas(canvas);
    
        AnimationTimer simAnimTimer = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                simManager.update();
                dayLabel.setText("Day: " + (simManager.getDay() + 1));
                countsLabel.setText(simManager.getPopulationManager().statusCountsFormatted());
            }
        };
        
        initBtn.setOnAction
        (
            (ActionEvent event) ->
            {
                simManager.setSimSettings(simSettings);
                simManager.newSimulation();
                dayLabel.setText("Day: 1");
                countsLabel.setText(simManager.getPopulationManager().statusCountsFormatted());
            }
        );
        
        startBtn.setOnAction
        (
            (ActionEvent event) ->
            {
                simManager.startSimulation();
                simAnimTimer.start();
            }
        );
        
        pauseBtn.setOnAction
        (
            (ActionEvent event) ->
            {
                simManager.pauseSimulation();
                simAnimTimer.stop();
            }
        );
        
        root.setStyle("-fx-font-size:20; -fx-background-color: rgb(90%,90%,100%);");
        
        stage.setTitle("Simulation Test");
        stage.setScene(scene);
        stage.show();
    }
}