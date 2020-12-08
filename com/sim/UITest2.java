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
 * @author John Kulins, Thomas Kohut
 * @version 1.0
 * @since 0.1
 */
public class UITest2 extends Application
{
    private String el = "", el2 = "";
    private boolean isValid, isValid2;
    private final int MAXPOP = 2500;

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

    public void start(Stage mainStage) throws Exception
    {
        //Sets up application
        mainStage.setTitle("Disease Simulator");

        VBox root2 = new VBox();
        root2.setStyle( "-fx-font-size:20; -fx-background-color: rgb(80%,80%,80%);" );

        BorderPane root = new BorderPane();
        Scene mainScene = new Scene(root, 1350, 1000);

        mainStage.setScene( mainScene );
        MenuBar bar = new MenuBar();
        root.setTop(bar);
        root.setCenter(root2);

        GridPane designArea = new GridPane();
        designArea.setHgap(5);
        designArea.setVgap(9);
        designArea.setPadding( new Insets(4) );

        // ------------------Days section-------------------------------------------
        CheckBox CheckDay = new CheckBox("Simulate days?");

        // -------------------Button--------------------------------------
        Button startButton = new Button("initialize");
        Button startBtn= new Button("Start");
        Button pauseBtn = new Button("Pause");

        // region where the sim will go. it is white to display the locale
        int canvasWidth = 800;
        int canvasHeight = 800;
        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(Color.WHITE);
        context.fillRect(0,0, canvasWidth,canvasHeight);

        SimManager simManager = new SimManager();
        simManager.setCanvas(canvas);
        //Inserts UI into screen
        HBox row2 = new HBox();// adds in the names for HBoxes
        row2.getChildren().addAll(designArea, canvas);
        root2.getChildren().addAll(row2);

        //Label array
        Label[] labs = new Label[5];
        String[] labStr = {"Disease:","Preventative Measure:","Total Population","Infected Population","Days to Simulate"};

        //TextField array
        TextField[] tf = new TextField[3];

        //ComboBox array
        ComboBox<String>[] cb = new ComboBox[2];

        //Assigns UI objects
        for(int x=0; x<labs.length;x++) {
            labs[x] = new Label(labStr[x]);

            if(x<2) {
                cb[x] = new ComboBox<String>();
                cb[x].setValue("Choose one");
            }
            //Handles text fields
            if(x<3) {
                tf[x] = new TextField();tf[x].setMaxWidth(80);

                //Forces numeric only entries
                tf[x].textProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue,
                        String newValue) {
                            for(int y=0; y<tf.length; y++) {
                                if (!newValue.matches("\\d*")) {
                                    tf[y].setText(newValue.replaceAll("[^\\d]", ""));
                                }
                            }
                        }
                    });
            }
        }

        //Adds options to combo boxes
        cb[0].getItems().addAll("Choose one", Databases.getDiseaseCopy(0).getName(), Databases.getDiseaseCopy(1).getName());
        cb[1].getItems().addAll("Choose one", Databases.getPrecautionCopy(0).getName(),
            Databases.getPrecautionCopy(1).getName(), Databases.getPrecautionCopy(2).getName(),
            Databases.getPrecautionCopy(3).getName());
        //these 2 are specially for the updating of the stats
        Label dayLabel = new Label("Day: 0");
        Label countsLabel = new Label();
        //Sets Default value

        designArea.addRow(0,labs[0], cb[0]);
        designArea.addRow(1,labs[1], cb[1]);
        designArea.addRow(2,labs[2], tf[0]);
        designArea.addRow(3, labs[3], tf[1]);
        designArea.addRow(4, labs[4], tf[2]);
        designArea.addRow(5, CheckDay);
        designArea.addRow(6, startButton, startBtn, pauseBtn );
        designArea.addRow(7, dayLabel);//this is part of the stats
        designArea.addRow(8, countsLabel); //this is part of the stats

        // event and listener to activate on changes.

        // most generic functional interface: no inputs, no output, contains method run()
        Runnable updateFunction =
            () ->
            {
                //to obtain text box data it is a simple .getText function.

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
                infoAlert.setContentText("1. Select a disease and preventative measure from the dropdown list. \n 2. Enter the population and total number days you wish to be simulated. \n 3. Press the start button to start the simulation.");

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

        //the timer////////////////////////////////// my marker
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
        //Variables used to start
        String[] dData = new String[2];
        int[] iData = new int[3];
        Alert ea = new Alert( AlertType.ERROR );
        ea.setTitle("Error!");
        Stage as = (Stage)ea.getDialogPane().getScene().getWindow();

        //start button functionality
        startButton.setOnAction((ActionEvent event) ->
            {
                if(!TFValidationCheck(labs, tf, cb, ea)) { //|| !CBTValidationCheck(labs, cb, ea)) {
                    ea.showAndWait();
                } else {
                    //this is all pulled from the simTest class and modeled after it for simplicity reasons
                    Disease disease= new Disease();
                    SimSettings simSettings = new SimSettings();
                    for (int cycle=0; cycle < Databases.getDiseasesLength();cycle++){
                        if(cb[0].getValue()==Databases.getDiseaseCopy(cycle).getName())//"" is the name in the dropdown box also they will be if statements.
                        {
                            disease = new Disease(Databases.getDiseaseCopy(cycle));
                        }
                    }
                    for (int rotate=0; rotate < Databases.getPrecautionsLength();rotate++){
                        if(cb[1].getValue()==Databases.getPrecautionCopy(rotate).getName())//"" is the name in the dropdown box also they will be if statements.
                        {
                            simSettings = new SimSettings(disease, Databases.getPrecautionCopy(rotate).getModifier() ,
                                false,Integer.parseInt(tf[0].getText()), Integer.parseInt(tf[1].getText()), -1, 90,10,2.0, 5.0,false);
                            //null, 0.0, false, 0, 0, -1, 90, 1.0, 2.0, 7.5, false
                        }
                    }                    //
                    simManager.setSimSettings(simSettings);
                    simManager.newSimulation();
                    dayLabel.setText("Day: 1");
                    
                    countsLabel.setText(simManager.getPopulationManager().statusCountsFormatted());
                }

            });
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

        mainStage.show();
    }

    /**Checks each user input is valid.
     * True: All inputs are valid
     * False: At least one input is invalid. Adds invalid entry to string to be used in Alert Box.
     */
    private boolean TFValidationCheck(Label[] labs, TextField[] tf, ComboBox<String>[] cbt, Alert ea) {
        el = "";
        isValid = true;

        //Checks if each entry is invalid
        for (int x = 0; x < tf.length; x++) {
            //Handles ComboBoxes
            if(x==0) {
                //Checks if an option was chosen in each Combo Box
                if(cbt[x].getValue()=="Choose one")
                {
                    el = el + "Please select a " + labs[x].getText() + ".\n";
                    isValid = false;

                    if(cbt[x+1].getValue()=="Choose one") {
                        el = el + "Please select a " + labs[x+1].getText() + ".\n";
                    }
                }
            }

            //Checks for empty TextFields or negative numbers
            if(tf[x].getText().isEmpty() || Integer.parseInt(tf[x].getText()) < 0) {
                el = el + labs[x+2].getText() + " is invalid. Please enter a valid number.\n";

                isValid = false;
            }

            //Checks if Infected Population exceeds Total Population
            if(x==1 && !tf[x].getText().isEmpty() &&
            Integer.parseInt(tf[1].getText()) > Integer.parseInt(tf[0].getText())) {
                el = el + "Infected Population cannot exceed Total Population.\n";
                isValid = false;
            }

            //Checks if any value exceeds max
            if(x <= 1 && !tf[x].getText().isEmpty() &&
            Integer.parseInt(tf[x].getText()) > MAXPOP) {
                el = el + labs[x+2].getText() +
                " exceeds the maximum limit. Please lower your entry by at least "
                + (Integer.parseInt(tf[x].getText()) - MAXPOP) + ".\n";
                isValid = false;
            }

            //Displays error message if anything is caught
            if(x == tf.length-1 && !isValid) {
                ea.setContentText(el);
                return isValid;
            }
        }

        return isValid;
    }
}