/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.*;

public class Main extends JFrame{

    private Game main;
    
    Main () {
        super("Test Frame");
        setBounds(100,100,500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container con = this.getContentPane();
        main = new Game();
        con.add(main);
        
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed (KeyEvent e) {
                winKeyPress(e);
            }
            
            public void keyReleased (KeyEvent e) {
                winKeyRelease(e);
            }
        });
        
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new Main();
    }
    
    private void winKeyPress (KeyEvent e) {
        main.keyPressed(e);
    }
    
    private void winKeyRelease (KeyEvent e) {
        main.keyReleased(e);
    }
}
