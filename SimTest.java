import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 *
 * @author Emily Gallagher
 * @version 11/15/2020
 * @since 11/15/2020
 */
public class SimTest extends Application
{
    //----- FIELDS -----
    private Canvas canvas;
    private GraphicsContext gc;
    
    private PopulationManager populationManager;
    
    private boolean debugMode;
    private double circleRadius;
    private double circleOffset;
    
    //----- CONSTRUCTORS -----
    public SimTest()
    {
        super();
        
        debugMode = true;
        circleRadius = 40.0;
        circleOffset = circleRadius / 2.0;
    }
    
    //----- METHODS -----
    
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
    
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        var startNanoTime = System.nanoTime();
        var root = new HBox();
        var scene = new Scene(root, 1000, 1000);
        
        canvas = new Canvas(900, 900);
        gc = canvas.getGraphicsContext2D();
        populationManager = new PopulationManager(1, canvas.getWidth(),
            canvas.getHeight(), 1.75, 6.25);
    
        var people = populationManager.getPeopleUnmodifiable();
        System.out.println(people);
        
        root.setStyle("-fx-font-size:20; -fx-background-color: rgb(90%,90%,100%);");
        root.getChildren().add(canvas);
        
        // Program loop
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                var dt = (currentNanoTime - startNanoTime) / 1000000000.0;
                populationManager.update(dt);
                
                // Canvas drawing logic
                clearCanvas();
                for (var person : populationManager.getPeopleUnmodifiable())
                {
                    drawCircle(person.getCurrentPosition().getX(),
                        person.getCurrentPosition().getY(),
                        person.getState().getFillColor(),
                        person.getState().getOutlineColor());
                }
            }
        }.start();
    
        primaryStage.setTitle("Sim Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void clearCanvas()
    {
        if (gc != null)
        {
            gc.setFill(Color.WHITE);
            gc.setLineWidth(0.0);
            gc.fillRect(0.0, 0.0, canvas.getWidth(), canvas.getHeight());
            gc.strokeRect(0.0, 0.0, canvas.getWidth(), canvas.getHeight());
        }
    }
    
    private void drawCircle(double x, double y, Color fillColor, Color strokeColor)
    {
        var centerX = x - circleOffset;
        var centerY = y - circleOffset;
        
        if (fillColor != null)
        {
            gc.setFill(fillColor);
            gc.fillOval(centerX, centerY, circleRadius, circleRadius);
        }
        
        if (strokeColor != null)
        {
            gc.setStroke(strokeColor);
            gc.setLineWidth(2.0);
            gc.strokeOval(centerX, centerY, circleRadius, circleRadius);
        }
    }
}