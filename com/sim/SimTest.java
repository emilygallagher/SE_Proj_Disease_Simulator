package com.sim;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
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
        Canvas canvas = new Canvas(900, 900);
        Label dayLabel = new Label("Day: 0");
        VBox root = new VBox(canvas, dayLabel);
        Scene scene = new Scene(root, 1000, 1000);
        
        Disease disease = new Disease();
        SimSettings simSettings = new SimSettings();
        SimManager simManager = new SimManager();
        
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
        
        simManager.setSimSettings(simSettings);
        simManager.setCanvas(canvas);
        
        simManager.getPopulationManager().initialize();
        
        root.setStyle("-fx-font-size:20; -fx-background-color: rgb(90%,90%,100%);");
        
        new AnimationTimer()
        {
            long startingNanoTime = System.nanoTime();
            
            @Override
            public void handle(long now)
            {
                long currentDay = simManager.getFrameCount() / simSettings.getDayLength() + 1;
                
                simManager.update();
                dayLabel.setText("Day: " + currentDay);
            }
        }.start();
        
        
        
        // TODO
        
        
        stage.setTitle("Simulation Test");
        stage.setScene(scene);
        stage.show();
    }
}