package drawing;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;


public class Cross extends Missile implements Runnable {
    public Cross (int startx, int starty, int endx, int endy) {
        super(startx, starty, endx, endy);
        speed = 8;
        damage = 20;
        width = 8;
        height = 8;
        box = new Rectangle(width, height);
        URL p1 = this.getClass().getResource("cross.png");
        try {
            myimg = ImageIO.read(p1);
        } catch (IOException e) {}
    }
}
