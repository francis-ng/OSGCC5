package drawing;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Spore extends Missile implements Runnable{
    public Spore (int startx, int starty, int endx, int endy) {
        super(startx, starty, endx, endy);
        speed = 7;
        damage = 20;
        width = 12;
        height = 12;
        box = new Rectangle(width, height);
        URL p1 = this.getClass().getResource("spore.png");
        try {
            myimg = ImageIO.read(p1);
        } catch (IOException e) {}
    }
}
