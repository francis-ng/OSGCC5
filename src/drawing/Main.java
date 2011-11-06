package drawing;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame{

    private Game main = new Game();
    
    Main () {
        super("Meow Meow Shutter");
        setBounds(100,100,600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container con = this.getContentPane();
        con.add(main);
        
        this.addMouseListener(new MouseAdapter(){
            public void mousePressed (MouseEvent e){
                
                winMousePressed(e);
            }
        });
        
        this.addMouseMotionListener(new MouseAdapter(){
            public void mouseMoved (MouseEvent e){
                winMouseMoved(e);
            }
            
            public void mouseDragged (MouseEvent e){
                winMouseDragged(e);
            }
        });
        
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed (KeyEvent e) {
                winKeyPress(e);
            }
            
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
    
    private void winKeyPress (KeyEvent e){
        main.keyPressed(e);
    }
    
    private void winKeyRelease (KeyEvent e) {
        main.keyReleased(e);
    }
    
    private void winMousePressed (MouseEvent e){
        main.mousePressed(e);
    }
    
    private void winMouseMoved (MouseEvent e){
        main.mouseMoved(e);
    }
    
    private void winMouseDragged (MouseEvent e){
        main.mouseDragged(e);
    }
}
