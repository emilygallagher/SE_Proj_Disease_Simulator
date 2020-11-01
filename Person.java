import java.util.Random;
import javafx.geometry.Point2D;

public final class Person
{
    //----- FIELDS -----
    private Point2D currentPosition;
    private Point2D targetPosition;
    private Point2D velocity;
    private HealthState state;
    
    //----- CONSTRUCTORS -----
    public Person()
    {
    }
    
    public Person(Person person)
    {
    
    }
    
    public Person(Point2D currentPosition, Point2D targetPosition,
        double speed, HealthState state)
    {
    
    }
    
    //----- METHODS -----
    // INSTANCE
    // Get & Set
    public Point2D getCurrentPosition()
    {
        return currentPosition;
    }
    
    private void setCurrentPosition(double x, double y)
    {
        currentPosition = new Point2D(x, y);
    }
    
    private void setCurrentPosition(Point2D position)
    {
        currentPosition = new Point2D(position.getX(), position.getY());
    }
    
    public Point2D getTargetPosition()
    {
        return targetPosition;
    }
    
    private void setTargetPosition(double x, double y)
    {
        targetPosition = new Point2D(x, y);
    }
    
    private void setTargetPosition(Point2D position)
    {
        targetPosition = new Point2D(position.getX(), position.getY());
    }
    
    public Point2D getVelocity()
    {
        return velocity;
    }
    
    private void setVelocity(double speed, Point2D direction)
    {
        velocity = direction.normalize().multiply(speed);
    }
    
    public double getSpeed()
    {
        return velocity.magnitude();
    }
    
    public Point2D getDirection()
    {
        return velocity.normalize();
    }
    
    public HealthState getState()
    {
        return state;
    }
    
    public void setState(HealthState state)
    {
        this.state = state;
    }
    
    // Updates
    void move()
    {
        if (state != HealthState.DECEASED)
        {
            var dist = currentPosition.distance(targetPosition);
            if (dist <= getSpeed())
            {
                setCurrentPosition(getTargetPosition());
                //setTargetPosition(randomPosition());
                //setVelocity()
            }
        }
    }
    
    private Point2D randomPosition()
    {
        var rand = PopulationManager.getRandom();
        var bounds = PopulationManager.getBounds();
        
        var x = rand.nextDouble() * bounds.getX();
        var y = rand.nextDouble() * bounds.getY();
        
        return new Point2D(x, y);
    }
}