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
        Scene mainScene = new Scene(root, 1250, 1000);

        mainStage.setScene( mainScene );
        MenuBar bar = new MenuBar();
        root.setTop(bar);
        root.setCenter(root2);

        // custom code below --------------------------------------------
        GridPane designArea = new GridPane();
        designArea.setHgap(5);
        designArea.setVgap(9);
        designArea.setPadding( new Insets(8) );

        // ------------------Population Information-----------------------------
        HBox row1 = new HBox();
        // ------------------Days section-------------------------------------------
        //TextField DayInt  = new TextField();DayInt.setMaxWidth(80);
        CheckBox CheckDay = new CheckBox("Simulate days?");

        // -------------------Button--------------------------------------
        Button startButton = new Button("Start");
        // --------------------------------------------------------------

        // region where the sim will go. it is white to display the locale
        int canvasWidth = 800;
        int canvasHeight = 800;
        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill( Color.WHITE );
        context.fillRect(0,0, canvasWidth,canvasHeight);
        HBox row2 = new HBox();// adds in the names for HBoxes
        row2.getChildren().addAll(designArea, canvas);
        root2.getChildren().addAll(row2);

        //Label array
        Label[] labs = new Label[5];
        String[] labStr = {"Diseases:","Preventative Measure:","Total Population","Infected Population","Days to Simulate"};

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
        cb[0].getItems().addAll("Choose one", "Covid-19", "Disease 2", "Disease 3");
        cb[1].getItems().addAll("Choose one", "Masks", "Distance", "Masks and Distance");

        //Sets Default value

        designArea.addRow( 0,labs[0],cb[0]);
        designArea.addRow( 1,labs[1], cb[1]);
        designArea.addRow( 2,labs[2],tf[0]);
        designArea.addRow( 3, labs[3],tf[1]);  
        designArea.addRow( 4, labs[4],tf[2]);
        designArea.addRow( 5, CheckDay);
        designArea.addRow( 6,startButton );

        // event and listener to activate on changes.

        // most generic functional interface: no inputs, no output, contains method run()
        Runnable updateFunction =
            () ->
            {
                //to obtain text box data it is a simple .getText function.
                if(cb[0].getValue()=="")//"" is the name in the dropdown box also they will be if statements.
                {}
                if ( CheckDay.isSelected() )//checking if a box was checked if it is it will run this code
                {   }
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

        //start button functionality
        startButton.setOnAction((ActionEvent event) ->
            {

            
            });
        mainStage.show();
    }
}
