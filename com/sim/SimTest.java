package com.sim;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 *
 * @author Emily Gallagher
 * @version %I%
 * @since 1.1
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
        var canvas = new Canvas(900, 900);
        var root = new HBox(canvas);
        var scene = new Scene(root, 1000, 1000);
        var simManager = new SimManager();
        
        root.setStyle("-fx-font-size:20; -fx-background-color: rgb(90%,90%,100%);");
        
        simManager.setCanvas(canvas);
        simManager.setDisease();
        simManager.setPrecautionsModifier();
        simManager.setMaxDays(-1);
        
        stage.setTitle("Simulation Test");
        stage.setScene(scene);
        stage.show();
    }
}