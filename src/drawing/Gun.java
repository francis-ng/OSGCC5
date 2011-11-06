package drawing;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Gun extends Missile implements Runnable{
    
    public Gun (int startx, int starty, int endx, int endy) {
        super(startx, starty, endx, endy);
        speed = 5;
        damage = 5;
        width = 4;
        height = 4;
        box = new Rectangle(width, height);
        URL p1 = this.getClass().getResource("gun.png");
        try {
            myimg = ImageIO.read(p1);
        } catch (IOException e) {}
    }
}
