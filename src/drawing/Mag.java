package drawing;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Mag extends Missile implements Runnable{
    
    public Mag (int startx, int starty, int endx, int endy) {
        super(startx, starty, endx, endy);
        speed = 6;
        damage = 10;
        width = 10;
        height = 10;
        box = new Rectangle(width, height);
        URL p1 = this.getClass().getResource("magic.png");
        try {
            myimg = ImageIO.read(p1);
        } catch (IOException e) {}
    }
}
