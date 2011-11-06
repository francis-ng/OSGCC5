package drawing;
import java.awt.Rectangle;

public class Missile implements Runnable{
    private Thread missile;
    private double posx, posy, disx, disy, slope, angle;
    private final int speed = 10; // lower, missile faster
    private int width, height;
    Rectangle box;
    
    public Missile(int startx, int starty, int endx, int endy, int w, int h) {
        posx = startx + 20;
        posy = starty + 20;
        disy = endy - starty;
        disx = endx - startx;
        width = w;
        height = h;
        box = new Rectangle(width, height);
        slope = disy/disx;
        angle = Math.atan(slope);
        missile = new Thread(this);
        missile.start();
    }
    
    public void run() {
        while(true) {
            try {
                Thread.sleep(speed);
            }catch (InterruptedException e) {
            }
            move();
        }
    }
    
    public void move() {
        if(disx > 0){
    		posx += 1 * Math.cos(angle);
    		posy += 1 * Math.sin(angle);
    	}else if(disx < 0){
    		posx -= 1 * Math.cos(angle);
    		posy += 1 * Math.sin(-angle);
    	}else{
    		posy += 1 * Math.sin(angle);
    	}
        box.setLocation((int)posx,(int)posy);
    }
 
    public int getPosx() {
        return (int)posx;
    }
    
    public int getPosy() {
        return (int)posy;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
}
