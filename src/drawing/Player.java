package drawing;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Player implements Runnable{
    private Thread player;
    private int posx, posy, width, height;
    boolean movel, mover, moveu, moved;
    private final int move = 2;
    Rectangle box;
    
    public Player(int w, int h) {
        posx = 300;
        posy = 300;
        width = w;
        height = h;
        box = new Rectangle(posx, posy, w, h);
        movel = false;
        mover = false;
        moveu = false;
        moved = false;
        player = new Thread(this);
        player.start();
    }
    
    public void move() {
        if (moveu) {
            posy -= move;
            box.translate(0,-move);
        }
        if (moved) {
            posy += move;
            box.translate(0,move);
        }
        if (movel) {
            posx -= move;
            box.translate(-move,0);
        }
        if (mover) {
            posx += move;
            box.translate(move,0);
        }
    }
    
    public void run() {
        while(true) {
            try {
                Thread.sleep(10);
            }catch (InterruptedException e) {
            }
            move();
        }
    }
    
    public void keyPressed (KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'w':
                moveu = true;
                break;
            case 's':
                moved = true;
                break;
            case 'a':
                movel = true;
                break;
            case 'd':
                mover = true;
                break;
            default:
                break;
        }
    }
    
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'w':
                moveu = false;
                break;
            case 's':
                moved = false;
                break;
            case 'a':
                movel = false;
                break;
            case 'd':
                mover = false;
                break;
            default:
                break;
        }
    }
    
    public void setPosx(int px) {
        posx = px;
    }
    
    public void setPosy(int py) {
        posy = py;
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
}
