import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import javax.swing.*;

/**
 * Write a description of class Ground here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ground  extends World
{
    
    /**
     * Constructor for objects of class Ground.
     * 
     */
    public Ground()
    {    
        // Create a new world with 20x20 cells with a cell size of 10x10 pixels.
        super(800, 600, 1); 
       // String response = JOptionPane.showInputDialog(new JFrame(),"How many boids do you want?");
       // int n = Integer.parseInt(response);
        int n = 25;
        populate(n);
        for(int i = 0; i < 50; i++)
        {
            addObject(new Tree(), Greenfoot.getRandomNumber(getWidth()),Greenfoot.getRandomNumber(getHeight()));
        }
    }
    
    public void populate(int n)
    {
        for (int b=0; b<n; b++) {
            int w = getWidth()/3;
            int x = Greenfoot.getRandomNumber(w) + w;
            int h = getHeight()/3;
            int y = Greenfoot.getRandomNumber(h) + h;
            addObject(new Boid(),x,y);
        }
    }
}
