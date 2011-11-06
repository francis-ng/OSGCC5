package drawing;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.util.*;
import java.util.Random;
import java.util.Date;
import java.util.Vector;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.net.URL;
import java.util.Iterator;

public class Game extends JPanel implements Runnable{
    Thread main;
    Player player;
    Vector<Enemy> enemyvector = new Vector();
    Enemy enemy;
    BufferedImage playerimg, bgimg;
    int mousex, mousey;
    int score,spawntime;
    int[] enemynumber = new int[1];
    ArrayList<Missile> missile;
    
    public Game() {
        URL p1 = this.getClass().getResource("player.png");
        URL bg = this.getClass().getResource("bg.png");
        try {
            playerimg = ImageIO.read(p1);
            bgimg = ImageIO.read(bg);
        } catch (IOException e) {
            System.out.println("error");
        }
        main = new Thread(this);
        player = new Player(40, 40);
        missile = new ArrayList<Missile>();
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
        for(int i = 0; i < missile.size(); i++){
            g.fillOval(missile.get(i).getPosx(), missile.get(i).getPosy(), missile.get(i).getWidth(), missile.get(i).getHeight());
        }
        g.drawRect(mousex-15, mousey-35,20,20);
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
    
    public void mousePressed(MouseEvent e){
        //add a thread, add into arraylist/vector
        missile.add(new Missile1(player.getPosx(), player.getPosy(), e.getX()-25, e.getY()-45));
    }
    
    public void run() {
        Date D = new Date();
        long reference = D.getTime();
        long now;
        Random R = new Random();
        
        spawntime = 5000;
        enemynumber[0] = 5;
        
        while (true) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
            
            D = new Date();
            now = D.getTime();
            if((now - reference) > spawntime){
                for(int i = 0; i < enemynumber.length;i++){
                    for(int j = 0; j < enemynumber[i];j++){
                        int enemyx, enemyy;
                        if(R.nextInt(2) == 0){
                            enemyx = R.nextInt(620);
                            if(R.nextInt(2) == 0)
                                enemyy = -20;
                            else enemyy = 620;
                        }else{
                            enemyy = R.nextInt(620);
                            if(R.nextInt(2) == 0)
                                enemyx = -20;
                            else enemyx = 620;
                        }
                        Enemy1 mini1 = new Enemy1(enemyx,enemyy,20,20,player.getPosx(),player.getPosy());
                        enemyvector.add(mini1);
                    }
                }
                reference = now;
            }
            
            for(int i = 0; i < enemyvector.size(); i++){
                enemy = (Enemy) enemyvector.get(i);
                enemy.playerx = player.getPosx();
                enemy.playery = player.getPosy();
            }
            
            for (Enemy e:enemyvector) {
                if (player.box.intersects(e.box)) {
                    System.out.println("Ouch");
                }
            }
            
            for(int i = 0; i < missile.size(); i++){
                for(int j = 0; j < enemyvector.size(); j++){
            		if(missile.get(i).box.intersects(enemyvector.elementAt(j).box)){
            			missile.get(i).destroy();
            			enemyvector.elementAt(j).kill();
            		}
            	}
            }
            for(int i = 0; i < missile.size(); i++){
            	if(missile.get(i).getDestroy()){
            		missile.remove(i);
            	}
            }
            for(int i = 0; i < enemyvector.size(); i++){
            	if(enemyvector.elementAt(i).getDead()){
            		enemyvector.remove(i);
            	}
            }
            System.out.println("missile number" + missile.size());
            System.out.println("enemy number" + enemyvector.size());
           
            repaint();
            
        }
    }
}