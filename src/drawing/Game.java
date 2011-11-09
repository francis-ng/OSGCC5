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
    BufferedImage playerimg, bgimg, camera, gun, magic, devil, cross;
    int mousex, mousey;
    int score,spawntime;
    int[] enemynumber = new int[5];
    ArrayList<Missile> missile;
    int missilenumber = 0;
    int missiletype = 1;
    int health;
    boolean bossdead, bossexists;
    
    public Game() {
        URL p1 = this.getClass().getResource("player.png");
        URL bg = this.getClass().getResource("bg.png");
        URL camera1 = this.getClass().getResource("cam.png");
        URL gun1 = this.getClass().getResource("gun.png");
        URL magic1 = this.getClass().getResource("magic.png");
        URL devil1 = this.getClass().getResource("devil.png");
        URL cross1 = this.getClass().getResource("cross.png");
        try {
            playerimg = ImageIO.read(p1);
            bgimg = ImageIO.read(bg);
            camera = ImageIO.read(camera1);
            gun = ImageIO.read(gun1);
            magic = ImageIO.read(magic1);
            devil = ImageIO.read(devil1);
            cross = ImageIO.read(cross1);
        } catch (IOException e) {
            System.out.println("error");
        }
        score = 0;
        health = 500;
        bossdead = false;
        bossexists = false;
        main = new Thread(this);
        player = new Player(40, 40);
        missile = new ArrayList<Missile>();
        main.start();
    }
    
    public void paintComponent (Graphics g) {
        if (health <= 0) {
            Graphics2D g2 = (Graphics2D)g;
            g.setColor(Color.black);
            g.fillRect(0,0,600,600);
            g.setColor(Color.red);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Font font = new Font("Serif", Font.PLAIN, 50);
            g2.setFont(font);
            g2.drawString("GAME OVER", 150, 200);
            g2.drawString("Score",250,280);
            g2.drawString(Integer.toString(score),250,320);
        }else
        if (score >= 2000) {
            Graphics2D g2 = (Graphics2D)g;
            g.setColor(Color.yellow);
            g.fillRect(0,0,600,600);
            g.setColor(Color.red);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Font font = new Font("Serif", Font.PLAIN, 50);
            g2.setFont(font);
            g2.drawString("YOU WIN", 150, 200);
            g2.drawString("Score",250,280);
            g2.drawString(Integer.toString(score),250,320);
        }
        else {
            Graphics2D g2 = (Graphics2D)g;
            setOpaque(true);
            super.paintComponent(g);
            g.drawImage(bgimg, 0, 0, this.getWidth(), this.getHeight(), null);
            g.setColor(Color.red);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Font font = new Font("Serif", Font.PLAIN, 20);
            g2.setFont(font);
            g2.drawString("Score",10,20);
            g2.drawString("Health", 520,20);
            g2.drawString(Integer.toString(score),10,40);
            g2.drawString(Integer.toString(health), 520,40);
            if (missilenumber == 0) {
                g.drawImage(camera,280,10,30,30, null);
            } else {
                switch (missiletype) {
                    case 1:
                        g.drawImage(camera,280,10,30,30, null);
                        break;
                    case 2:
                        g.drawImage(gun,280,10,30,30, null);
                        break;
                    case 3:
                        g.drawImage(magic,280,10,30,30, null);
                        break;
                    case 4:
                        g.drawImage(devil,280,10,30,30, null);
                        break;
                    case 5:
                        g.drawImage(cross,280,10,30,30, null);
                        break;
                }
            }
            g2.drawString(Integer.toString(missilenumber),320,30);
            g.drawImage(playerimg, player.getPosx(), player.getPosy(), player.getWidth(), player.getHeight(), null);
            for(int i = 0; i < enemyvector.size(); i++){
                enemy = (Enemy) enemyvector.get(i);
                g.drawImage(enemy.myimg, enemy.getPosx(), enemy.getPosy(), enemy.getWidth(), enemy.getHeight(), null);
            }
            for(int i = 0; i < missile.size(); i++){
                g.drawImage(missile.get(i).myimg, missile.get(i).getPosx(), missile.get(i).getPosy(), missile.get(i).getWidth()+10, missile.get(i).getHeight()+10, null);
            }
            g.drawRect(mousex-15, mousey-35,20,20);
        }
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
    	if(missilenumber <= 0)
    		missiletype = 1;
    	
    	if(missiletype == 1){
    		missile.add(new Camera(player.getPosx(), player.getPosy(), e.getX()-25, e.getY()-45));
    	}else{
    		if(missiletype == 2){
    			missile.add(new Gun(player.getPosx(), player.getPosy(), e.getX()-25, e.getY()-45));
    		}
    		if(missiletype == 3){
    			missile.add(new Mag(player.getPosx(), player.getPosy(), e.getX()-25, e.getY()-45));
    		}
                if(missiletype == 4){
    			missile.add(new Devil(player.getPosx(), player.getPosy(), e.getX()-25, e.getY()-45));
    		}
                if(missiletype == 5){
                        missile.add(new Cross(player.getPosx(), player.getPosy(), e.getX()-25, e.getY()-45));
                }
    		missilenumber--;
    		
    	}
    }
    
    public void run() {
        Date D = new Date();
        long reference = D.getTime() - 2000;
        long now;
        Random R = new Random();
        
        while (true) {
            if (health <= 0 || score >= 2000) {
                break;
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
            if (score < 150) {
                enemynumber[0] = 3;
                enemynumber[1] = 2;
                enemynumber[2] = 0;
                enemynumber[3] = 0;
                spawntime = 7000;
            }
            else if (score >= 150 && score < 600) {
                enemynumber[0] = 0;
                enemynumber[1] = 3;
                enemynumber[2] = 2;
                enemynumber[3] = 0;
                spawntime = 5000;
            }
            else {
                enemynumber[0] = 0;
                enemynumber[1] = 1;
                enemynumber[2] = 2;
                enemynumber[3] = 2;
                spawntime = 5000;
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
                        if(i == 0){
                        	CatGun mini1 = new CatGun(enemyx,enemyy,40,40,player.getPosx(),player.getPosy());
                        	enemyvector.add(mini1);
                        }
                        else if(i == 1){
                        	CatMag mini2 = new CatMag(enemyx,enemyy,40,40,player.getPosx(),player.getPosy());
                            enemyvector.add(mini2);
                        }
                        else if(i == 2){
                        	Catvil mini3 = new Catvil(enemyx,enemyy,40,40,player.getPosx(),player.getPosy());
                            enemyvector.add(mini3);
                        }
                        else if(i == 3){
                        	Catgel mini4 = new Catgel(enemyx,enemyy,40,40,player.getPosx(),player.getPosy());
                            enemyvector.add(mini4);
                        }
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
                    health -= e.damage;
                    System.out.println("health = " + health);
                }
            }
            
            for(int i = 0; i < missile.size(); i++){
                for(int j = 0; j < enemyvector.size(); j++){
            		if(missile.get(i).box.intersects(enemyvector.elementAt(j).box)){
            			missile.get(i).destroy();
            			enemyvector.elementAt(j).health -= missile.get(i).damage;
                        if (enemyvector.elementAt(j).health <= 0) {
                            enemyvector.elementAt(j).kill();
                            score += enemyvector.elementAt(j).score;
                            System.out.println(score);
                        }
                        if(missiletype == 1){
                        	missiletype = enemyvector.elementAt(j).missiletype;
                        	missilenumber = 10;
                        }
                        System.out.println("missiletype " + missiletype);
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
            
            repaint();
            
        }
        repaint();
    }
}