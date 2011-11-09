package drawing;

import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Catgel extends Enemy implements Runnable{
    public Catgel(int px, int py, int w, int h, int targetx, int targety){
        super(px,py,w,h,targetx,targety);
        delay = 20;
        maxHealth = 40; 
        health = maxHealth;
        missiletype = 5;
        score = 40;
        damage = 4;
        URL p1 = this.getClass().getResource("catgel.png");
        try {
            myimg = ImageIO.read(p1);
        } catch (IOException e) {}
    }
}
