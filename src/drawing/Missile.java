package drawing;
import java.awt.Rectangle;

public class Missile implements Runnable{
    private Thread missile;
    private double posx, posy, disx, disy, slope, angle;
    int speed, damage; // lower, missile faster
    int width, height;
    Rectangle box;
    private boolean destroyed;
    
    public Missile(int startx, int starty, int endx, int endy) {
        posx = startx + 20;
        posy = starty + 20;
        disy = endy - starty;
        disx = endx - startx;
        destroyed = false;
        slope = disy/disx;
        angle = Math.atan(slope);
        missile = new Thread(this);
        missile.start();
    }
    
    public void run() {
        while(!destroyed) {
            try {
                Thread.sleep(20);
            }catch (InterruptedException e) {
            }
            move();
        }
    }
    
    public void move() {
        if(disx > 0){
    		posx += speed * Math.cos(angle);
    		posy += speed * Math.sin(angle);
    	}else if(disx < 0){
    		posx -= speed * Math.cos(angle);
    		posy += speed * Math.sin(-angle);
    	}else{
    		posy += speed * Math.sin(angle);
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
    
    public void destroy() {
        destroyed = true;
    }
}
