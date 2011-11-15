package drawing;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame{

    private Game main = new Game();
    
    Main () {
        super("Meow Meow Panic");
        setBounds(100,100,600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container con = this.getContentPane();
        con.add(main);
        
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed (KeyEvent e) {
                winKeyPress(e);
            }
            @Override
            public void keyReleased (KeyEvent e) {
                winKeyRelease(e);
            }
        });
        
        setResizable(false);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new Main();
    }
    
    private void winKeyRelease(KeyEvent e) {
        main.keyRelease(e);
    }
    
    private void winKeyPress(KeyEvent e) {
        main.keyPress(e);
    }
}
