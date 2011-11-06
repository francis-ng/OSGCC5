package drawing;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Devil extends Missile implements Runnable{
    public Devil (int startx, int starty, int endx, int endy) {
        super(startx, starty, endx, endy);
        speed = 8;
        damage = 15;
        width = 7;
        height = 7;
        box = new Rectangle(width, height);
        URL p1 = this.getClass().getResource("devil.png");
        try {
            myimg = ImageIO.read(p1);
        } catch (IOException e) {}
    }
}
