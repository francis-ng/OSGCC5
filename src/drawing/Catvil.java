package drawing;

import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Catvil extends Enemy implements Runnable{
    public Catvil(int px, int py, int w, int h, int targetx, int targety){
        super(px,py,w,h,targetx,targety);
        delay = 25;
        maxHealth = 30;
        health = maxHealth;
        missiletype = 4;
        score = 30;
        damage = 3;
        URL p1 = this.getClass().getResource("catvil.png");
        try {
            myimg = ImageIO.read(p1);
        } catch (IOException e) {}
    }
}
