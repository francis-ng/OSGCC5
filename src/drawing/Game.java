package drawing;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.util.Vector;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.net.URL;
import java.awt.MouseInfo;
import java.awt.geom.AffineTransform;

public class Game extends JPanel implements Runnable, MouseMotionListener {
    Thread main;
    Player player;
    Vector enemyvector = new Vector();
    Enemy enemy;
    BufferedImage playerimg, bgimg;
    AffineTransform tx;
    AffineTransformOp op;
    int mousex, mousey;
    
    public Game() {
        URL p1 = this.getClass().getResource("player.png");
        URL bg = this.getClass().getResource("bg.png");
        try {
            playerimg = ImageIO.read(p1);
            bgimg = ImageIO.read(bg);
        } catch (IOException e) {
            System.out.println("error");
        }
        addMouseMotionListener(this);
        main = new Thread(this);
        player = new Player(40, 40);
        main.start();
    }
    
    public void paintComponent (Graphics g) {
        setOpaque(true);
        super.paintComponent(g);
        g.drawImage(bgimg, 0, 0, this.getWidth(), this.getHeight(), null);
        g.setColor(Color.red);
        g.drawImage(playerimg, player.getPosx(), player.getPosy(), player.getWidth(), player.getHeight(), null);
        for(int i = 0; i < enemyvector.size(); i++){
            enemy = (Enemy) enemyvector.get(i);
            g.fillOval(enemy.getPosx(), enemy.getPosy(), enemy.getWidth(), enemy.getHeight());
        }
        g.drawRect(mousex-10, mousey-10,20,20);
    }
    
    public void keyPressed (KeyEvent e) {
        player.keyPressed(e);
    }
    
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }
    
    public void mouseMoved(MouseEvent e) {
        mousex = e.getX();
        mousey = e.getY();
    }
    
    public void mouseDragged(MouseEvent e) {
        mousex = e.getX();
        mousey = e.getY();
    }
    
    public void run() {
        Enemy1 mini1 = new Enemy1(50,50,20,20,player.getPosx(),player.getPosy());
        enemyvector.add(mini1);
        while (true) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
            
            for(int i = 0; i < enemyvector.size(); i++){
                enemy = (Enemy) enemyvector.get(i);
                enemy.playerx = player.getPosx();
                enemy.playery = player.getPosy();
            }
            repaint();
            
        }
    }
}