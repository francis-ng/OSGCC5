/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing;

public class Missile implements Runnable{
    private Thread missile;
    private double posx, posy, disx, disy, slope, angle;
    private final int speed = 10; // lower, missile faster
    
    public Missile(int startx, int starty, int endx, int endy) {
        posx = startx + 20;
        posy = starty + 20;
        disy = endy - starty;
        disx = endx - startx;
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
    }
 
    public int getPosx() {
        return (int)posx;
    }
    
    public int getPosy() {
        return (int)posy;
    }
}
