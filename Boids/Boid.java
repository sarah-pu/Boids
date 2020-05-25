import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class Boid here.
 * 
 * @author Sarah Pu 
 * @version (a version number or a date)
 */
public class Boid  extends SmoothMover
{
    private final int MARGIN = 100;
    private final int SEPARATION = 2;
    private final double FORCE = 0.2;
    private final double MAX_SPEED = 5.0;
    private final double MIN_SPEED = 2.0;
    
    public Boid()
    {
        //give the boid a random angle
        int angle = Greenfoot.getRandomNumber(360);
        //give the boid a random velocity between 2.0 and 5.0
        double length = Greenfoot.getRandomNumber(30)/10.0 + 2;
        //given these values, create a new vector
        Vector v = new Vector(angle, length);
        //add this force
        addForce(v);
    }
    /**
     * Act - do whatever the Boid wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        separation(FORCE*12, SEPARATION*10);
        alignment(FORCE/4, MARGIN/4);
        cohesion(FORCE*4, MARGIN);
        checkEdges();
        avoidTrees(FORCE*10, MARGIN/4);
        move();
        //rotate the bird's angle based on this
        setRotation(getMovement().getDirection());
        //set a max and min speed for our boids
        if(getSpeed() > MAX_SPEED)
        {
            getMovement().setLength(MAX_SPEED);
        }
        if(getSpeed() < MIN_SPEED)
        {
            getMovement().setLength(MIN_SPEED);
        }
    }     
    
    public void avoidTrees(double force, int margin)
    {
        double treeX = 0;
        double treeY = 0;
        int angle = 0;
        List <Tree> trees = getObjectsInRange(margin, Tree.class);
        //if there are boids in the list
        if(trees.size() > 0)
        {
            for(Tree t : trees)
            {
                treeX = t.getExactX();
                treeY = t.getExactY();
            }
            double distX = treeX - this.getExactX();
            double distY = treeY - this.getExactY();
            //figure out the angle using the tangent ratio in an integer
            angle = (int)Math.toDegrees(Math.atan2(distY, distX));
            //create a new vector based on the angle plus 180, as well as the force variable,
            Vector v = new Vector(angle+180, force);
            addForce(v);
        }
    }
    
    public void alignment(double force, int margin)
    {
        double avg = 0;
        //if there any other boids near
        List <Boid> boids = getObjectsInRange(margin, Boid.class);
        if(boids.size() > 0)
        {
            //find out all of these boids' average direction
            for(Boid b : boids)
            {
                avg = avg + b.getMovement().getDirection();
            }
            avg = avg/boids.size();
            //add a force to this boid in that average direction
            addForce(new Vector((int)avg, force));
        }
    }
    
    public void separation(double force, int separation)
    {
        //get a list of the boids within near this boid
        List <Boid> boids = getObjectsInRange(separation, Boid.class);
        //if there are boids in the list
        if(boids.size() > 0)
        {
            //go through the list and add up all of their x values, and all of their y values
            double totalX = 0;
            double totalY = 0;
            for(Boid b : boids)
            {
                totalX = totalX + b.getExactX();
                totalY = totalY + b.getExactY();
            }
            //find the average of these
            double avgX = totalX/boids.size();
            double avgY = totalY/boids.size();
            //subtract the x and y location of this boid from the average x and y values
            double distX = avgX - this.getExactX();
            double distY = avgY - this.getExactY();
            //figure out the angle using the tangent ratio in an integer
            int angle = (int)Math.toDegrees(Math.atan2(distY, distX));
            //create a new vector based on the angle plus 180, as well as the force variable,
            Vector v = new Vector(angle+180, force);
            //and add the force to this boid
            addForce(v);
        }
    }
    
    public void cohesion(double force, int margin)
    {
         //check to see if there are any boids around you
         List <Boid> boids = getObjectsInRange(MARGIN, Boid.class);
         //if there are any boids in this list
         if(boids.size() > 0)
         {
             //create variables for the total x and y values of each, so that you can then get their average location
             double totalX = 0;
             double totalY = 0;
             //figure out the distance between the average point and you for both x and y
             for(Boid b : boids)
             {
                 totalX = totalX + b.getExactX();
                 totalY = totalY + b.getExactY();
             }
             double avgX = totalX/boids.size();
             double avgY = totalY/boids.size();
             //calculate the angle towards that average point
             double distX = avgX - this.getExactX();
             double distY = avgY - this.getExactY();
             int angle = (int)Math.toDegrees(Math.atan2(distY, distX));
             //apply a force to this boid in this direction
             addForce(new Vector(angle, force));
         }
    }
    
    public void checkEdges()
    {
        //if the boid is flying too close to the left side of the world, have it fly in the opposite direction
        if (getX()<MARGIN) {
            addForce( new Vector(0,FORCE) );
        }
        //if the boid is flying too close to the top of the world, have it fly in the opposite direction
        if (getY()<MARGIN) {
            addForce( new Vector(90,FORCE) );
        }   
        //if the boid is flying too close to the right side of the world, have it fly in the opposite direction
        if (getX()>getWorld().getWidth()-MARGIN) {
            addForce( new Vector(180,FORCE) );
        }
        //if the boid is flying too close to the bottom of the world, have it fly in the opposite direction
        if (getY()>getWorld().getHeight()-MARGIN) {
            addForce( new Vector(270,FORCE) );
        }         
    }
}
