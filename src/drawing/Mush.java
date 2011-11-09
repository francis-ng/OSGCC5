package drawing;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Mush extends Enemy implements Runnable{
    public Mush(int px, int py, int w, int h, int targetx, int targety){
        super(px,py,w,h,targetx,targety);
        delay = 45;
        maxHealth = 1500;
        health = maxHealth;
        missiletype = 1;
        score = 200;
        damage = 5;
        URL p1 = this.getClass().getResource("mush.png");
        try {
            myimg = ImageIO.read(p1);
        } catch (IOException e) {}
    }
}
