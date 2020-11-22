import java.util.Random;
import javafx.geometry.Point2D;

public class Person
{
    //----- FIELDS -----
    private final PopulationManager manager;
    private Point2D currentPosition;
    private Point2D targetPosition;
    private Point2D velocity;
    private HealthState state;
    private int frame;
    
    //----- CONSTRUCTORS -----
    public Person(Person person)
    {
        this(person.getManager(), person.getCurrentPosition(), person.getTargetPosition(),
            person.getSpeed(), person.getState());
    }
    
    public Person(PopulationManager manager, Point2D startPosition, Point2D targetPosition, double speed,
        HealthState state)
    {
        frame = 0;
        this.manager = manager;
        
        this.currentPosition = new Point2D(startPosition.getX(), startPosition.getY());
        this.targetPosition = new Point2D(targetPosition.getX(), targetPosition.getY());
        calculateVelocity(speed);
        setState(state);
    }
    
    //----- METHODS -----
    // Get & Set
    public PopulationManager getManager()
    {
        return manager;
    }
    
    public Random getRandom()
    {
        return getManager().getRandom();
    }
    
    public Point2D getCurrentPosition()
    {
        return currentPosition;
    }
    
    public void setCurrentPosition(double x, double y)
    {
        currentPosition = new Point2D(x, y);
        calculateVelocity();
    }
    
    public void setCurrentPosition(Point2D position)
    {
        currentPosition = new Point2D(position.getX(), position.getY());
        calculateVelocity();
    }
    
    public Point2D getTargetPosition()
    {
        return targetPosition;
    }
    
    public void setTargetPosition(double x, double y)
    {
        targetPosition = new Point2D(x, y);
        calculateVelocity();
    }
    
    public void setTargetPosition(Point2D position)
    {
        targetPosition = new Point2D(position.getX(), position.getY());
        calculateVelocity();
    }
    
    public Point2D getVelocity()
    {
        return velocity;
    }
    
    private void setVelocity(double x, double y)
    {
        velocity = new Point2D(x, y);
    }
    
    private void setVelocity(Point2D velocity)
    {
        this.velocity = new Point2D(velocity.getX(), velocity.getY());
    }
    
    private void calculateVelocity()
    {
        calculateVelocity(getSpeed());
    }
    
    private void calculateVelocity(double speed)
    {
        var dir = getTargetPosition().subtract(getCurrentPosition());
        var vel = dir.normalize().multiply(speed);
        
        setVelocity(vel);
    }
    
    public double getSpeed()
    {
        return getVelocity().magnitude();
    }
    
    public void setSpeed(double speed)
    {
        setVelocity(getDirection().multiply(speed));
    }
    
    public Point2D getDirection()
    {
        return getVelocity().normalize();
    }
    
    public HealthState getState()
    {
        return state;
    }
    
    public void setState(HealthState state)
    {
        this.state = state;
    }
    
    // Update
    public void move(double dt)
    {
        frame++;
        
        if (state != HealthState.SELF_ISOLATING && state != HealthState.DECEASED)
        {
            var dist = currentPosition.distance(targetPosition);
            
            if (dist <= getSpeed())
            {
                var maxX = manager.getBounds().getX();
                var maxY = manager.getBounds().getY();
                var minSpeed = manager.getMinSpeed();
                var maxSpeed = manager.getMaxSpeed();
                
                var targetX = getRandom().nextDouble() * maxX;
                var targetY = getRandom().nextDouble() * maxY;
                var speed = (getRandom().nextDouble() * (maxSpeed - minSpeed)) + minSpeed;
                
                setCurrentPosition(getTargetPosition());
                setTargetPosition(new Point2D(targetX, targetY));
                setSpeed(speed);
    
                System.out.println(String.format("Frame: %d\nCurrPos: %s\nTargetPos: %s\nSpeed: "
                    + "%s\n", frame, getCurrentPosition(), getTargetPosition(), velocity));
            }
            else
            {
                // Does not apply delta time to the translation.
                setCurrentPosition(getCurrentPosition().add(getVelocity()));
                
                // Applies delta time to the translation.
                //var delta = getVelocity().multiply(dt);
                //setCurrentPosition(getCurrentPosition().add(delta));
            }
        }
    }
}