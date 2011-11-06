package drawing;

public class Enemy implements Runnable{
    Thread enemy;
    boolean dead;
    int posx, posy, width, height, health, playerx, playery, delay;
    
    public Enemy(int px, int py, int w, int h, int targetx, int targety) {
        posx = px;
        posy = py;
        width = w;
        height = h;
        playerx = targetx;
        playery = targety;
        enemy = new Thread(this);
        enemy.start();
    }
    
    public void run() {
        while(!dead) {
            try {
                Thread.sleep(delay);
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