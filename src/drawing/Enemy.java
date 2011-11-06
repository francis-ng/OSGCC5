/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing;

/**
 *
 * @author Francis
 */
public class Enemy implements Runnable{
    private Thread enemy;
    private int posx, posy, width, height, health, playerx, playery;
    private double speed = 0.2;
    
    public Enemy(int px, int py, int w, int h, int hp, int targetx, int targety) {
        posx = px;
        posy = py;
        width = w;
        height = h;
        health = hp;
        playerx = targetx;
        playery = targety;
        enemy = new Thread(this);
        enemy.start();
    }
    
    public void run() {
        while(true) {
            try {
                Thread.sleep(10);
            }catch (InterruptedException e) {
            }
            chase();
        }
    }
    
    public void chase() {
        int dirx = playerx - posx;
        int diry = playery - posy;
        posx += dirx * speed;
        posy += diry * speed;
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
}
