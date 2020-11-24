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
        mainStage.setTitle("Insert Title HERE");

        VBox root2 = new VBox();
        root2.setStyle( "-fx-font-size:20; -fx-background-color: rgb(90%,90%,100%);" );

        BorderPane root = new BorderPane();
        Scene mainScene = new Scene(root, 900, 900);

        mainStage.setScene( mainScene );
        MenuBar bar = new MenuBar();
        root.setTop(bar);
        root.setCenter(root2);
        
        // custom code below --------------------------------------------

        GridPane designArea = new GridPane();
        designArea.setHgap(9);
        designArea.setVgap(9);
        designArea.setPadding( new Insets(8) );

        // --------------------------------------------------------------

        Label customLabel = new Label("Text:");

        TextField customText = new TextField("Sample Text");
        // combo box (drop down list) effects        --------------------------------------------------

        Label  DesText= new Label("Desieases:");

        ComboBox<String> DesSelect = new ComboBox<String>();
        DesSelect.getItems().addAll("Covid-19", "Insert1", "Insrt2");
        DesSelect.setValue("Covid-19");
        // ------------------Population Information-----------------------------
        Label TotPop = new Label     ("Total Population");
        Label InfPop = new Label     ("Infected Population");
        HBox row1 = new HBox();
        HBox row2 = new HBox();// adds in the names for HBoxes
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
        // --------------------Preventitive measures-----------------------------------------
        VBox PreventMeasurechecks= new VBox();
        CheckBox M1 = new CheckBox("Measure one");
        CheckBox M2 = new CheckBox("Measure two ");
        CheckBox M3 = new CheckBox("Measure Three?");
        CheckBox M4 = new CheckBox("Measure Four?");
        PreventMeasurechecks.getChildren().addAll(M1,M2,M3,M4);
        // -------------------Button--------------------------------------
        Button startButton = new Button("Start");
        // --------------------------------------------------------------
        designArea.addRow( 0,DesText,DesSelect  );
        designArea.addRow( 1,TotPop,TPint, InfPop,IPint  );
        designArea.addRow( 2, DayTxt,DayInt, CheckDay);
        designArea.addRow( 3, PreventMeasurechecks );
        designArea.addRow( 4,startButton );

        // region where text is drawn
        int canvasWidth = 800;
        int canvasHeight = 200;
        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        GraphicsContext context = canvas.getGraphicsContext2D();

        root2.getChildren().addAll( designArea, canvas );

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

        /////////////////////////////////
        // This is the menu bar section that is at the top
        Menu fileMenu = new Menu("File");
        Menu aboutMenu = new Menu("About");
        MenuItem quitItem = new MenuItem("Quit");
        quitItem.setGraphic(new ImageView( new Image("icons/cross.png") ) );

        MenuItem aboutItem = new MenuItem("About this program...");
        aboutItem.setGraphic(new ImageView( new Image("icons/help.png") ) );

        bar.getMenus().addAll(fileMenu, aboutMenu);

        aboutMenu.getItems().add( aboutItem );// to add a shortcut key combination to a menu item:

        // to add a shortcut key combination to a menu item:
        quitItem.setAccelerator(new KeyCodeCombination( KeyCode.Q, KeyCodeCombination.CONTROL_DOWN ));

        quitItem.setOnAction(
            (ActionEvent event) ->
            {
                System.exit(0);// this exits the program
            }
        );

        aboutItem.setOnAction(
            (ActionEvent event) ->
            {
                // Alert containing information the user must read.
                Alert infoAlert = new Alert( AlertType.INFORMATION );
                infoAlert.setTitle("HELP WINDOW");
                infoAlert.setHeaderText("Welcome to The WINDOW");
                infoAlert.setContentText("SPEECH");

                Stage alertStage = (Stage)infoAlert.getDialogPane().getScene().getWindow();
                infoAlert.showAndWait();
            }
        );
        
        
        
        
        ////////////////////////////////////////////////////////////////
        // custom code above --------------------------------------------

        mainStage.show();
    }
}
