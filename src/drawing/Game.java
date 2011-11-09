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

public class Game extends JPanel implements Runnable{
    Thread main;
    Player player;
    Vector<Enemy> enemyvector = new Vector();
    Enemy enemy;
    BufferedImage playerimg, bgimg, camera, gun, magic, devil, cross;
    int mousex, mousey;
    int score,spawntime,mushMissileSpawnTime;
    int[] enemynumber = new int[5];
    ArrayList<Missile> missile, mushMissile;
    int missilenumber = 0;
    int missiletype = 1;
    double maxHealth = 500;
    double health, healthBar;
    boolean win, bossexists;
    
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
        health = maxHealth;
        win = false;
        bossexists = false;
        main = new Thread(this);
        player = new Player(40, 40);
        missile = new ArrayList<Missile>();
        mushMissile = new ArrayList<Missile>();
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
            g2.drawString("GAME OVER", 155, 220);
            g2.drawString("Score  " + Integer.toString(score),215,300);
        }else
        if (win) {
            Graphics2D g2 = (Graphics2D)g;
            g.setColor(Color.yellow);
            g.fillRect(0,0,600,600);
            g.setColor(Color.red);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Font font = new Font("Serif", Font.PLAIN, 50);
            g2.setFont(font);
            g2.drawString("YOU WIN", 190, 220);
            g2.drawString("Score  " + Integer.toString(score),215,300);
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
            g2.drawString(Double.toString(health), 520,40);
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
            healthBar = health/maxHealth*player.getWidth();
            g.fillRect(player.getPosx(), player.getPosy() - 5, (int)healthBar, 5);
            g.drawImage(playerimg, player.getPosx(), player.getPosy(), player.getWidth(), player.getHeight(), null);
            for(int i = 0; i < enemyvector.size(); i++){
                enemy = (Enemy) enemyvector.get(i);
                healthBar = enemy.getHealth()/enemy.getmaxHealth()*enemy.getWidth();
                g.fillRect(enemy.getPosx(), enemy.getPosy() - 5, (int)healthBar, 5);
                g.drawImage(enemy.myimg, enemy.getPosx(), enemy.getPosy(), enemy.getWidth(), enemy.getHeight(), null);
            }
            for(int i = 0; i < missile.size(); i++){
                g.drawImage(missile.get(i).myimg, missile.get(i).getPosx(), missile.get(i).getPosy(), missile.get(i).getWidth()+10, missile.get(i).getHeight()+10, null);
            }
            for(int i = 0; i < mushMissile.size(); i++){
                g.drawImage(mushMissile.get(i).myimg, mushMissile.get(i).getPosx(), mushMissile.get(i).getPosy(), mushMissile.get(i).getWidth()+10, mushMissile.get(i).getHeight()+10, null);
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
        long now, bossNow, bossReference = 0;
        Random R = new Random();
        Mush mini5 = new Mush(0,0,100,100,player.getPosx(),player.getPosy());
        mushMissileSpawnTime = 500;
        
        while (true) {
            if (health <= 0) {
                break;
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
            if(mini5.getDead()){
            	win = true;
            	break;
            }
            enemynumber[4] = 0;
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
            else if (score >= 600 && score < 1400){
                enemynumber[0] = 0;
                enemynumber[1] = 2;
                enemynumber[2] = 2;
                enemynumber[3] = 2;
                spawntime = 4000;
            }
            else if (score >= 1400) {
            	enemynumber[0] = 2;
                enemynumber[1] = 2;
                enemynumber[2] = 1;
                enemynumber[3] = 1;
                spawntime = 5000;
            	if (!bossexists) {
            		enemynumber[4] = 1;
            	}
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
                        else if(i == 4){
                        	mini5 = new Mush(enemyx,enemyy,100,100,player.getPosx(),player.getPosy());
                            enemyvector.add(mini5);
                            bossexists = true;
                            bossReference = D.getTime();
                        }
                    }
                }
                reference = now;
            }
            
            bossNow = D.getTime();
            
            if((bossNow - bossReference) > mushMissileSpawnTime && bossexists){
                mushMissile.add(new Spore(mini5.getPosx()+(mini5.getWidth()/2),mini5.getPosy()+(mini5.getHeight()/2),player.getPosx(), player.getPosy()));
                bossReference = now;
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
            
            for (Missile m:mushMissile) {
                if (player.box.intersects(m.box)) {
                    health -= m.damage;
                    m.destroy();
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
            for(int i = 0; i < mushMissile.size(); i++){
                if(mushMissile.get(i).getDestroy()){
            		mushMissile.remove(i);
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