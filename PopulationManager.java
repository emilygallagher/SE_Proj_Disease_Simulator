import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javafx.geometry.Point2D;

public class PopulationManager
{
    //----- FIELDS -----
    private final Random rand;
    
    private List<Person> people;
    private Point2D bounds;
    private double minSpeed;
    private double maxSpeed;
    
    //----- CONSTRUCTORS -----
    public PopulationManager(double maxX, double maxY, double minSpeed,
        double maxSpeed)
    {
        this(0, maxX, maxY, minSpeed, maxSpeed);
    }
    
    public PopulationManager(int populationSize, double maxX, double maxY, double minSpeed,
        double maxSpeed)
    {
        rand = new Random();
        bounds = new Point2D(maxX, maxY);
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        
        if (populationSize > 0)
        {
            people = new ArrayList<>(populationSize);
            for (int i = 0; i < populationSize; i++)
            {
                addPerson();
            }
        }
        else
        {
            people = new ArrayList<>();
        }
    }
    
    public PopulationManager(Point2D bounds, double minSpeed, double maxSpeed)
    {
        this(0, bounds, minSpeed, maxSpeed);
    }
    
    public PopulationManager(int populationSize, Point2D bounds, double minSpeed, double maxSpeed)
    {
        this(populationSize, bounds.getX(), bounds.getY(), minSpeed, maxSpeed);
    }
    
    //----- METHODS -----
    // Get & Set
    public Random getRandom()
    {
        return rand;
    }
    
    public List<Person> getPeopleUnmodifiable()
    {
        return Collections.unmodifiableList(people);
    }
    
    public Point2D getBounds()
    {
        return bounds;
    }
    
    public void setBounds(double x, double y)
    {
        bounds = new Point2D(x, y);
    }
    
    public void setBounds(Point2D bounds)
    {
        this.bounds = new Point2D(bounds.getX(), bounds.getY());
    }
    
    public double getMinSpeed()
    {
        return minSpeed;
    }
    
    public void setMinSpeed(double speed)
    {
        minSpeed = speed;
    }
    
    public double getMaxSpeed()
    {
        return maxSpeed;
    }
    
    public void setMaxSpeed(double speed)
    {
        maxSpeed = speed;
    }
    
    // Population
    public Person addPerson()
    {
        var startX = getRandom().nextDouble() * getBounds().getX();
        var startY = getRandom().nextDouble() * getBounds().getY();
        var targetX = getRandom().nextDouble() * getBounds().getX();
        var targetY = getRandom().nextDouble() * getBounds().getY();
        var speed = (getRandom().nextDouble() * (getMaxSpeed() - getMinSpeed())) + getMinSpeed();
        
        // Also randomize this later.
        var state = HealthState.HEALTHY;
        
        return addPerson(new Point2D(startX, startY), new Point2D(targetX, targetY), speed, state);
    }
    
    public Person addPerson(Point2D startPosition, Point2D targetPosition, double speed,
        HealthState state)
    {
        var person = new Person(this, startPosition, targetPosition, speed, state);
        var successful = people.add(person);
        return successful ? person : null;
    }
    
    public boolean removePerson(int index)
    {
        var person = people.remove(index);
        return person != null;
    }
    
    public boolean removePerson(Person person)
    {
        return people.remove(person);
    }
    
    // Update
    public void update(double dt)
    {
        for (Person person : people)
        {
            person.move(dt);
        }
        
        for (int i = 0; i < people.size() - 1; i++)
        {
            for (int j = i + 1; j < people.size(); j++)
            {
                var personI = people.get(i);
                var personJ = people.get(j);
                
                infectionCheck(personI, personJ);
                infectionCheck(personJ, personI);
            }
        }
    }
    
    public void infectionCheck(Person infected, Person exposed)
    {
        if (infected == null || exposed == null)
        {
            return;
        }
        else
        {
            var infectedState = infected.getState();
            var exposedState = exposed.getState();
            
            if ((infectedState == HealthState.ASYMPTOMATIC
                || infectedState == HealthState.SYMPTOMATIC)
                && exposedState == HealthState.HEALTHY)
            {
                // attempt the infection.
            }
        }
    }
}