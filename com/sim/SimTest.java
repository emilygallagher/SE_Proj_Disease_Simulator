package com.sim;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.HBox;
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
        HBox root = new HBox(canvas);
        Scene scene = new Scene(root, 1000, 1000);
        SimManager simManager = new SimManager();
        
        SimSettings simSettings = new SimSettings(null, 0.0, false, 30, 0, -1, 90, 10.0, 2.0, 7.5,
            true);
        
        root.setStyle("-fx-font-size:20; -fx-background-color: rgb(90%,90%,100%);");
        
        simManager.setSimSettings(simSettings);
        simManager.setCanvas(canvas);
        
        
        simManager.getPopulationManager().initialize();
        
        
        new AnimationTimer()
        {
            long startingNanoTime = System.nanoTime();
            
            @Override
            public void handle(long now)
            {
                simManager.update();
            }
        }.start();
        
        
        
        // TODO
        
        
        stage.setTitle("Simulation Test");
        stage.setScene(scene);
        stage.show();
    }
}