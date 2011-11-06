package drawing;
import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.util.Vector;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Game extends JPanel implements Runnable {
    Thread main;
    Player player;
    Vector enemyvector = new Vector();
    Enemy enemy;
    BufferedImage playerimg;
    
    public Game() {
        URL url = this.getClass().getResource("player.png");
        try {
            playerimg = ImageIO.read(url);
        } catch (IOException e) {
            System.out.println("error");
        }
        main = new Thread(this);
        player = new Player(40, 40);
        main.start();
    }
    
    public void paintComponent (Graphics g) {
        setOpaque(true);
        super.paintComponent(g);
        setBackground(Color.green);
        g.setColor(Color.red);
        g.drawImage(playerimg, player.getPosx(), player.getPosy(), player.getWidth(), player.getHeight(), null);
        for(int i = 0; i < enemyvector.size(); i++){
            enemy = (Enemy) enemyvector.get(i);
            g.fillOval(enemy.getPosx(), enemy.getPosy(), enemy.getWidth(), enemy.getHeight());
        }
    }
    
    public void keyPressed (KeyEvent e) {
        player.keyPressed(e);
    }
    
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
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
            //player.setPosx(player.getPosx() + 5);
            for(int i = 0; i < enemyvector.size(); i++){
                enemy = (Enemy) enemyvector.get(i);
                enemy.playerx = player.getPosx();
                enemy.playery = player.getPosy();
            }
            repaint();
            
        }
    }
}