package drawing;

import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class CatMag extends Enemy implements Runnable{
    
    public CatMag(int px, int py, int w, int h, int targetx, int targety){
        super(px,py,w,h,targetx,targety);
        delay = 45;
        health = 20; 
        missiletype = 3;
        score = 20;
        damage = 2;
        URL p1 = this.getClass().getResource("catmag.png");
        try {
            myimg = ImageIO.read(p1);
        } catch (IOException e) {}
    }
}
