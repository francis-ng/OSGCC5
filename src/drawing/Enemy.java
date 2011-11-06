package drawing;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Enemy implements Runnable{
    Thread enemy;
    boolean dead;
    int posx, posy, width, height, health, playerx, playery, delay;
    Rectangle box;
    int missiletype;
    BufferedImage myimg;
    int score, damage;
    
    public Enemy(int px, int py, int w, int h, int targetx, int targety) {
        posx = px;
        posy = py;
        width = w;
        height = h;
        box = new Rectangle(px,py,w,h);
        playerx = targetx;
        playery = targety;
        enemy = new Thread(this);
        enemy.start();
    }
    
    public void run() {
        while(!dead) {
            try {
                Thread.sleep(20);
            }catch (InterruptedException e) {
            }
            chase();
        }
    }
    
    public void chase() {
        if((playerx + 10) > posx)
            posx++;
        else posx--;
        if((playery + 10) > posy)
            posy++;
        else posy--;
        box.setLocation(posx, posy);
    }
 
    public void setPosx(int px) {
        posx = px;
    }
    
    public void setPosy(int py) {
        posy = py;
    }
    
    public void setPlayerx(int px) {
        playerx = px;
    }
    
    public void setPlayery(int py) {
        playery = py;
    }
    
    public void setHealth(int hp) {
        health = hp;
    }
    
    public int getPosx() {
        return posx;
    }
    
    public int getPosy() {
        return posy;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getHealth() {
        return health;
    }
    
    public void kill(){
        dead = true;
    }
    
    public boolean getDead(){
    	return dead;
    }
}