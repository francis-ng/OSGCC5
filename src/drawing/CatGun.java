package drawing;

import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class CatGun extends Enemy implements Runnable{
    
    public CatGun(int px, int py, int w, int h, int targetx, int targety){
        super(px,py,w,h,targetx,targety);
        delay = 30;
        maxHealth = 10; 
        health = maxHealth;
        missiletype = 2;
        score = 10;
        damage = 1;
        URL p1 = this.getClass().getResource("catgun.png");
        try {
            myimg = ImageIO.read(p1);
        } catch (IOException e) {}
    }
}
