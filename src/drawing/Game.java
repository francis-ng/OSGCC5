package drawing;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.Random;
import java.util.Date;
import java.util.Vector;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.net.URL;
import javax.sound.sampled.*;

public class Game extends JPanel implements Runnable{
    Thread main;
    Player player;
    Vector<Enemy> enemyvector;
    Enemy enemy;
    BufferedImage playerimg, bgimg, camera, gun, magic, devil, cross, winbg, losebg, mainbg;
    BufferedImage[] startbut, exitbut, helpbut, retbut;
    int mousex, mousey, score,spawntime,mushMissileSpawnTime, starti = 0, helpi = 0, exiti = 0, reti = 0;
    int[] enemynumber;
    ArrayList<Missile> missile, mushMissile;
    int missilenumber = 0, missiletype = 1;
    double health, healthBar, maxHealth = 500;
    boolean menu, win, lose, game, bossexists;
    long now, bossNow, bossReference = 0, reference;
    Date D;
    Random R;
    Mush mini5;
    Clip bgm, gamebgm;
    
    public Game() {
        startbut = new BufferedImage[2];
        helpbut = new BufferedImage[2];
        exitbut = new BufferedImage[2];
        retbut = new BufferedImage[2];
        URL p1 = this.getClass().getResource("player.png");
        URL bg = this.getClass().getResource("bg.png");
        URL camera1 = this.getClass().getResource("cam.png");
        URL gun1 = this.getClass().getResource("gun.png");
        URL magic1 = this.getClass().getResource("magic.png");
        URL devil1 = this.getClass().getResource("devil.png");
        URL cross1 = this.getClass().getResource("cross.png");
        URL win1 = this.getClass().getResource("win.png");
        URL lose1 = this.getClass().getResource("lose.png");
        URL main1 = this.getClass().getResource("mainbg.png");
        URL start = this.getClass().getResource("startbut.png");
        URL starthl = this.getClass().getResource("startbuthl.png");
        URL help = this.getClass().getResource("helpbut.png");
        URL helphl = this.getClass().getResource("helpbuthl.png");
        URL exit = this.getClass().getResource("exitbut.png");
        URL exithl = this.getClass().getResource("exitbuthl.png");
        URL back = this.getClass().getResource("retbut.png");
        URL backhl = this.getClass().getResource("retbuthl.png");
        URL bgmus = this.getClass().getResource("bgm.wav");
        URL gamemus = this.getClass().getResource("battlebgm.wav");
        try {
            playerimg = ImageIO.read(p1);
            bgimg = ImageIO.read(bg);
            camera = ImageIO.read(camera1);
            gun = ImageIO.read(gun1);
            magic = ImageIO.read(magic1);
            devil = ImageIO.read(devil1);
            cross = ImageIO.read(cross1);
            winbg = ImageIO.read(win1);
            losebg = ImageIO.read(lose1);
            mainbg = ImageIO.read(main1);
            startbut[0] = ImageIO.read(start);
            startbut[1] = ImageIO.read(starthl);
            helpbut[0] = ImageIO.read(help);
            helpbut[1] = ImageIO.read(helphl);
            exitbut[0] = ImageIO.read(exit);
            exitbut[1] = ImageIO.read(exithl);
            retbut[0] = ImageIO.read(back);
            retbut[1] = ImageIO.read(backhl);
            AudioInputStream bgsound = AudioSystem.getAudioInputStream(bgmus);
            AudioInputStream gbgsound = AudioSystem.getAudioInputStream(gamemus);
            bgm = AudioSystem.getClip();
            bgm.open(bgsound);
            gamebgm = AudioSystem.getClip();
            gamebgm.open(gbgsound);
        } catch (IOException e) {
            System.out.println("error");
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        score = 0;
        health = maxHealth;
        bossexists = false;
        main = new Thread(this);
        player = new Player(40, 40);
        missile = new ArrayList<Missile>();
        mushMissile = new ArrayList<Missile>();
        enemyvector = new Vector();
        enemynumber = new int[5];
        game = false;
        win = false;
        lose = false;
        menu = true;
        this.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed (MouseEvent e){
                mousePress(e);
            }
        });
        this.addMouseMotionListener(new MouseAdapter(){
            @Override
            public void mouseMoved (MouseEvent e){
                mouseMove(e);
            }
            @Override
            public void mouseDragged (MouseEvent e){
                mouseDrag(e);
            }
        });
        main.start();
    }
    
    @Override
    public void paintComponent (Graphics g) {
        if (lose) {
            Graphics2D g2 = (Graphics2D)g;
            g.drawImage(losebg,0,0,this.getWidth(),this.getHeight(),null);
            g.setColor(Color.red);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Font font = new Font("Serif", Font.PLAIN, 40);
            g2.setFont(font);
            g2.drawString(Integer.toString(score),220,140);
            g.drawImage(retbut[reti],250,500,150,45,null);
        }
        else if (win) {
            Graphics2D g2 = (Graphics2D)g;
            g.drawImage(winbg,0,0,this.getWidth(),this.getHeight(),null);
            g.setColor(Color.red);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Font font = new Font("Serif", Font.PLAIN, 40);
            g2.setFont(font);
            g2.drawString(Integer.toString(score),220,140);
            g.drawImage(retbut[reti],250,500,150,45,null);
        }
        else if (game) {
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
            g.drawRect(mousex-10, mousey-10,20,20);
        }
        else if (menu) {
            g.drawImage(mainbg,0,0,this.getWidth(),this.getHeight(),null);
            g.drawImage(startbut[starti],400,250,150,45,null);
            g.drawImage(helpbut[helpi],400,350,150,45,null);
            g.drawImage(exitbut[exiti],400,450,150,45,null);
        }
    }
    
    public void keyPress (KeyEvent e) {
        player.keyPressed(e);
    }
    
    public void keyRelease(KeyEvent e) {
        player.keyReleased(e);
    }
    
    public void mouseMove(MouseEvent e) {
        mousex = e.getX();
        mousey = e.getY();
    }
    
    public void mouseDrag(MouseEvent e) {
        mousex = e.getX();
        mousey = e.getY();
    }
    
    public void mousePress(MouseEvent e){
        if (game) {
            if(missilenumber <= 0)
                    missiletype = 1;

            if(missiletype == 1){
                    missile.add(new Camera(player.getPosx(), player.getPosy(), e.getX()-40, e.getY()-25));
            }else{
                    if(missiletype == 2){
                            missile.add(new Gun(player.getPosx(), player.getPosy(), e.getX()-40, e.getY()-25));
                    }
                    if(missiletype == 3){
                            missile.add(new Mag(player.getPosx(), player.getPosy(), e.getX()-40, e.getY()-25));
                    }
                    if(missiletype == 4){
                            missile.add(new Devil(player.getPosx(), player.getPosy(), e.getX()-40, e.getY()-25));
                    }
                    if(missiletype == 5){
                            missile.add(new Cross(player.getPosx(), player.getPosy(), e.getX()-40, e.getY()-25));
                    }
                    missilenumber--;
            }
        }
        else if (lose||win) {
            if (mousex <= 400 && mousex >= 250 && mousey <= 545 && mousey >= 500) {
                score = 0;
                health = maxHealth;
                bossexists = false;
                main = new Thread(this);
                player = new Player(40, 40);
                missile = new ArrayList<Missile>();
                mushMissile = new ArrayList<Missile>();
                enemyvector = new Vector();
                enemynumber = new int[5];
                game = false;
                win = false;
                lose = false;
                menu = true;
                gamebgm.stop();
                gamebgm.setFramePosition(0);
                bgm.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
        else if (menu) {
            if (mousex <= 550 && mousex >= 400 && mousey <= 295 && mousey >= 250) {
                game = true;
                win = false;
                lose = false;
                menu = false;
                bgm.stop();
                bgm.setFramePosition(0);
                gamebgm.loop(Clip.LOOP_CONTINUOUSLY);
            }
            if (mousex <= 550 && mousex >= 400 && mousey <= 495 && mousey >= 450) {
                System.exit(0);
            }
        }
    }
    
    @Override
    public void run() {
        D = new Date();
        reference = D.getTime() - 2000;
        R = new Random();
        mini5 = new Mush(0,0,100,100,player.getPosx(),player.getPosy());
        mushMissileSpawnTime = 500;
        bgm.loop(Clip.LOOP_CONTINUOUSLY);
        while (true) {
            while (game) {
                if (health <= 0) {
                    game = false;
                    win = false;
                    lose = true;
                    menu = false;
                }
                if (mini5.getDead()) {
                    game = false;
                    win = true;
                    lose = false;
                    menu = false;
                }
                rungame();
            }
            while (lose||win) {
                if (mousex <= 400 && mousex >= 250 && mousey <= 545 && mousey >= 500) {
                    reti = 1;
                }
                else reti = 0;
                repaint();
            }
            while (menu) {
                if (mousex <= 550 && mousex >= 400 && mousey <= 295 && mousey >= 250) {
                    starti = 1;
                }
                else starti = 0;
                if (mousex <= 550 && mousex >= 400 && mousey <= 395 && mousey >= 350) {
                    helpi = 1;
                }
                else helpi = 0;
                if (mousex <= 550 && mousex >= 400 && mousey <= 495 && mousey >= 450) {
                    exiti = 1;
                }
                else exiti = 0;
                repaint();
            }
        }
    }
    
    void rungame() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
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
}