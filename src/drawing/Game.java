package drawing;
import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;

public class Game extends JPanel implements Runnable {
    Thread main;
    Player player;
    
    public Game() {
        main = new Thread(this);
        player = new Player(20, 20);
        main.start();
    }
    
    public void paintComponent (Graphics g) {
        setOpaque(true);
        super.paintComponent(g);
        setBackground(Color.green);
        g.setColor(Color.red);
        g.fillOval(player.getPosx(), player.getPosy(), player.getWidth(), player.getHeight());
    }
    
    public void keyPressed (KeyEvent e) {
        /*switch (e.getKeyCode()) {
            case 'w':
                player.moveu = true;
                break;
            case 's':
                player.moved = true;
                break;
            case 'a':
                player.movel = true;
                break;
            case 'd':
                player.mover = true;
                break;
            default:
                break;
        }*/
        player.keyPressed(e);
    }
    
    public void keyReleased(KeyEvent e) {
        /*switch (e.getKeyCode()) {
            case 'w':
                player.moveu = false;
                break;
            case 's':
                player.moved = false;
                break;
            case 'a':
                player.movel = false;
                break;
            case 'd':
                player.mover = false;
                break;
            default:
                break;
        }*/
        player.keyReleased(e);
    }
    
    public void run() {
        while (true) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
            //player.setPosx(player.getPosx() + 5);
            repaint();
        }
    }
}
