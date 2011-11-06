package drawing;

public class Enemy2 extends Enemy implements Runnable{
    
    public Enemy2(int px, int py, int w, int h, int targetx, int targety){
        super(px,py,w,h,targetx,targety);
        delay = 30;
        health = 20; 
        missiletype = 3;
    }
}
