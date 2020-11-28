package com.sim;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.beans.value.*;
import javafx.event.*;
import javafx.animation.*;
import javafx.geometry.*;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
//images additions
// new imports to work with images.
import javafx.scene.image.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author John Kulins
 * @version 1.0
 * @since 0.1
 */
public class UITest extends Application
{
    public static void main(String[] args)
    {
        try
        {
            launch(args);
        }
        catch (Exception error)
        {
            error.printStackTrace();
        }
        finally
        {
            System.exit(0);
        }
    }

    public void start(Stage mainStage)
    {
        mainStage.setTitle("Disease Simulator");

        VBox root2 = new VBox();
        root2.setStyle( "-fx-font-size:20; -fx-background-color: rgb(80%,80%,80%);" );

        BorderPane root = new BorderPane();
        Scene mainScene = new Scene(root, 1000, 500);

        mainStage.setScene( mainScene );
        MenuBar bar = new MenuBar();
        root.setTop(bar);
        root.setCenter(root2);

        // custom code below --------------------------------------------

        GridPane designArea = new GridPane();
        designArea.setHgap(5);
        designArea.setVgap(9);
        designArea.setPadding( new Insets(8) );

        // --------------------------------------------------------------

        Label customLabel = new Label("Text:");

        TextField customText = new TextField("Sample Text");

        // Disease/Prevention Options (drop down list) --------------------------------------------------
        Label disOpt = new Label("Diseases:");
        ComboBox<String> disOpts = new ComboBox<String>();
        disOpts.getItems().addAll("Choose one", "Covid-19", "Disease 2", "Disease 3");
        disOpts.setValue("Choose one");

        Label prevOpt = new Label("Preventative Measure:");
        ComboBox<String> prevOpts = new ComboBox<String>();
        prevOpts.getItems().addAll("Choose one", "Masks", "Distance", "Masks and Distace");
        prevOpts.setValue("Choose one");

        // ------------------Population Information-----------------------------
        Label TotPop = new Label("Total Population");
        Label InfPop = new Label("Infected Population");
        HBox row1 = new HBox();
        TextField TPint  = new TextField();TPint.setMaxWidth(80);
        TextField IPint  = new TextField();IPint.setMaxWidth(80);

        // force the field to be numeric only
        TPint.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue,
                String newValue) {
                    if (!newValue.matches("\\d*")) {
                        TPint.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });

        // force the field to be numeric only
        IPint.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue,
                String newValue) {
                    if (!newValue.matches("\\d*")) {
                        IPint.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });
        // ------------------Days section-------------------------------------------

        Label DayTxt = new Label   ("Days to Simulate");
        TextField DayInt  = new TextField();DayInt.setMaxWidth(80);
        CheckBox CheckDay = new CheckBox("Simulate days?");
        // force the field to be numeric only
        DayInt.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue,
                String newValue) {
                    if (!newValue.matches("\\d*")) {
                        DayInt.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });

        // -------------------Button--------------------------------------
        Button startButton = new Button("Start");
        // --------------------------------------------------------------
        designArea.addRow( 0,disOpt,disOpts  );
        designArea.addRow( 1,prevOpt, prevOpts  );
        designArea.addRow( 2,TotPop,TPint, InfPop,IPint  );
        designArea.addRow( 3, DayTxt,DayInt, CheckDay);
        designArea.addRow( 4,startButton );

        // region where the sim will go. it is white to display the locale
        int canvasWidth = 300;
        int canvasHeight = 300;
        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill( Color.WHITE );
        context.fillRect(0,0, canvasWidth,canvasHeight);
        HBox row2 = new HBox();// adds in the names for HBoxes
        row2.getChildren().addAll( designArea, canvas );
        root2.getChildren().addAll(row2);

        // event and listener to activate on changes.

        // most generic functional interface: no inputs, no output, contains method run()
        Runnable updateFunction =
            () ->
            {
            };

        EventHandler<ActionEvent> renderHandler = (ActionEvent event) ->{
                updateFunction.run();
            };

        ChangeListener<Object> renderListener =
            (ObservableValue<? extends Object> ov, Object oldValue, Object newValue) ->
            {
                updateFunction.run();
            };

        // This is the menu bar section that is at the top
        Menu aboutMenu = new Menu("Options");

        //Handles Option MenuItems, names, and images
        MenuItem[] optMenu = new MenuItem[3];
        Image[] optImg = new Image[3];
        String[] optName = {"Help","Credits","Quit"};
        String[] imgName = {"help.png","information.png","cross.png"};
        for(int x = 0; x < optMenu.length; x++) {
            optMenu[x] = new MenuItem(optName[x]);
            optImg[x] = new Image("icons/" + imgName[x]);

            optMenu[x].setGraphic(new ImageView(optImg[x]));
            aboutMenu.getItems().addAll(optMenu[x]);
        }

        bar.getMenus().addAll(aboutMenu);

        //Displays Help Section
        optMenu[0].setOnAction(
            (ActionEvent event) ->
            {
                // Alert containing information the user must read.
                Alert infoAlert = new Alert( AlertType.INFORMATION );
                infoAlert.setTitle("Help");
                infoAlert.setHeaderText("How do I use this program?");
                infoAlert.setContentText("1. Select a disease and preventative measure from the dropdown list.\n2. Enter the population and total number days you wish to be simulated.\n3. Press the start button to start the simulation.");

                Stage alertStage = (Stage)infoAlert.getDialogPane().getScene().getWindow();
                infoAlert.showAndWait();
            });      

        //Displays Credit Section
        optMenu[1].setOnAction(
            (ActionEvent event) ->
            {
                // Alert containing information the user must read.
                Alert infoAlert = new Alert( AlertType.INFORMATION );
                infoAlert.setTitle("Credits");
                infoAlert.setHeaderText("Credits");
                infoAlert.setContentText("Enter Finalized Credits");

                Stage alertStage = (Stage)infoAlert.getDialogPane().getScene().getWindow();
                infoAlert.showAndWait();
            });     

        //Closes Application
        // to add a shortcut key combination to a menu item:
        optMenu[2].setAccelerator(new KeyCodeCombination( KeyCode.Q, KeyCodeCombination.CONTROL_DOWN ));

        optMenu[2].setOnAction(
            (ActionEvent event) ->
            {
                System.exit(0);// this exits the program
            }); 

        mainStage.show();
    }
}
