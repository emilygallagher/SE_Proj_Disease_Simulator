import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.geometry.Point2D;

public final class PopulationManager
{
    //----- FIELDS -----
    private static Random rand;
    private static Point2D bounds;
    private static double minSpeed;
    private static double maxSpeed;
    private static List<Person> people;
    
    static
    {
        rand = new Random();
        bounds = Point2D.ZERO;
        people = new ArrayList<Person>();
    }
    
    //----- CONSTRUCTORS -----
    private PopulationManager() { }
    
    //----- METHODS -----
    // Get & Set
    public static Random getRandom()
    {
        return rand;
    }
    
    public static Point2D getBounds()
    {
        return bounds;
    }
    
    public static void setBounds(double x, double y)
    {
        bounds = new Point2D(x, y);
    }
    
    public static void setBounds(Point2D bounds)
    {
        PopulationManager.bounds = new Point2D(bounds.getX(), bounds.getY());
    }
    
    public static double getMinSpeed()
    {
        return minSpeed;
    }
    
    public static void setMinSpeed(double speed)
    {
        minSpeed = speed;
    }
    
    public static double getMaxSpeed()
    {
        return maxSpeed;
    }
    
    public static void setMaxSpeed(double speed)
    {
        maxSpeed = speed;
    }
    
    // Updates
    public static void update()
    {
        for (Person person : people)
        {
            person.move();
        }
        
        for (int i = 0; i < people.size() - 1; i++)
        {
            for (int j = i; j < people.size(); j++)
            {
                var personI = people.get(i);
                var personJ = people.get(j);
                
                tryInfection(personI, personJ);
                tryInfection(personJ, personI);
            }
        }
    }
    
    private static void tryInfection(Person infected, Person exposed)
    {
        var infectedState = infected.getState();
        var exposedState = exposed.getState();
        
        if ((infectedState == HealthState.ASYMPTOMATIC || infectedState == HealthState.SYMPTOMATIC)
            && exposedState == HealthState.HEALTHY)
        {
        
        }
    }
}